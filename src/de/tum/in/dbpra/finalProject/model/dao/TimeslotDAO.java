package de.tum.in.dbpra.finalProject.model.dao;

import de.tum.in.dbpra.base.model.dao.BaseDAO;
import de.tum.in.dbpra.finalProject.model.bean.TimeslotBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeslotDAO extends BaseDAO {


    /**
     * Query table timeslot from the DB in order to get all festival timeslots
     *
     * @param column - column to order the table with all time slots for the festival by
     * @return  array list TimeslotBeans with all time slots for the festival
     */
    public List<TimeslotBean> listFestivalTimeslots(String column){
        List<TimeslotBean> timeslots = new ArrayList<TimeslotBean>();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            // when the user clicks on "band", he wants the table sorted by band name and not bandid
            // this check is needed in order to be able to order the tale by band name instead of band id
            if(column.equals("bandid")) {
                column = "a.name";
            }
            PreparedStatement stmt = conn.prepareStatement("select t.timeslotid, t.begintime,  t.endtime, t.stageid, a.name as band " +
                    "from timeslot as t, provider as p, application as a " +
                    "where t.bandid = p.providerid and p.applicationid = a.applicationid " +
                    "order by  " + column);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                    TimeslotBean timeslot = new TimeslotBean();
                    timeslot.setTimeslotid(rs.getInt("timeslotid"));
                    timeslot.setDateBegin(rs.getDate("begintime"));
                    timeslot.setDateEnd(rs.getDate("endtime"));
                    timeslot.setBegintime(rs.getTime("begintime"));
                    timeslot.setEndtime(rs.getTime("endtime"));
                    timeslot.setStageid(rs.getInt("stageid"));
                    timeslot.setBand(rs.getString("band"));
                    timeslots.add(timeslot);
                }
                conn.commit();
                rs.close();
                stmt.close();
                conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            timeslots = new ArrayList<>();
        }
        return timeslots;
    }


    /**
     * Query table individualtimetable from the DB in order to get all individual timeslots
     *
     * @param column - column to order the individualtimetable by
     * @param visitorID - id of the logged in visitor (always valid after the login is successful)
     * @return array list of TimeslotBeans holding all individual tmeslots
     */
    public List<TimeslotBean> listIndividualTimeslots(String column, int visitorID){
        List timeslots = new ArrayList();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            // when the user clicks on "band", he wants the table sorted by band name and not bandid
            // this check is needed in order to be able to order the tale by band name instead of band id
            if(column.equals("bandid")) {
                column = "a.name";
            }
            else {
                column = "t." + column;
            }
            PreparedStatement stmt = conn.prepareStatement("select i.timeslotid, t.begintime, t.endtime, t.stageid, a.name as band " +
                    "from individualtimetable as i, timeslot as t, provider as p, application as a " +
                    "where i.visitorid = " + visitorID + " " +
                    "and i.timeslotid = t.timeslotid and t.bandid = p.providerid and p.applicationid = a.applicationid " +
                    "order by " + column);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TimeslotBean timeslot = new TimeslotBean();
                timeslot.setTimeslotid(rs.getInt("timeslotid"));
                timeslot.setDateBegin(rs.getDate("begintime"));
                timeslot.setBegintime(rs.getTime("begintime"));
                timeslot.setEndtime(rs.getTime("endtime"));
                timeslot.setStageid(rs.getInt("stageid"));
                timeslot.setBand(rs.getString("band"));
                timeslots.add(timeslot);
            }
            conn.commit();
            rs.close();
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            timeslots = new ArrayList<>();
        }
        return timeslots;
    }


    /**
     * Attempts to insert an entry into the individual timetable of the visitor and returns a success message
     *
     * @param timeslotID - the id of the timeslot to be inserted into the individual timetable of the visitor
     * @param visitorID - the id of the logged visitor
     * @return a success message stating whether the insertion was successful or not
     */
    public String insertIndividualTimeslot(int timeslotID, int visitorID) {
        String success = "";
        ArrayList<String> ids = new ArrayList<>();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            //we need all ids of the timeslots in the individual table together with the visitor id
            //in order to see if the tuple (visitorid,timeslotid) is already there when trying to insert it
            PreparedStatement stmt1 = conn.prepareStatement("select * from individualtimetable" );
            ResultSet rs1 = stmt1.executeQuery();;
            while (rs1.next()){
                String tid = rs1.getString("timeslotid");
                String vid = rs1.getString("visitorid");
                ids.add(tid+"+"+vid);
            }

            //if the tuple is there, insertion should not be possible
            if(ids.contains(Integer.toString(timeslotID)+"+"+ Integer.toString(visitorID))){
                success = "This timeslot is already in your schedule.";
                conn.rollback();
            }
            //otherwise add the entry to the table
            else{
                PreparedStatement stmt = conn.prepareStatement("insert into individualtimetable values (?, ?) ");
                stmt.setInt(1,visitorID);
                stmt.setInt(2, timeslotID);
                stmt.executeUpdate();
                success = "Insertion successful.";
                conn.commit();
                stmt.close();
            }

            rs1.close();
            stmt1.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    /**
     * Attempts to delete an entry from the individual timetable of the visitor and returns a success message
     *
     * @param timeslotID - id of the timeslot to be deleted from the individual timetable of the visitor
     * @param visitorID - id of the logged visitor
     * @return a success message whether the deletion was successful or not
     */
    public String deleteIndividualTimeslot(int timeslotID, int visitorID) {
        String success = "";
        ArrayList<String> ids = new ArrayList<String>();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            //we need all ids of the timeslots in the individual table together with the visitor id
            //in order to see if the tuple (visitorid, timeslotid) is in the table when trying to delete it
            //we can't delete entries that are not there
            PreparedStatement stmt1 = conn.prepareStatement("select * from individualtimetable" );
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()){
                String tid = rs1.getString("timeslotid");
                String vid = rs1.getString("visitorid");
                ids.add(tid+"+"+vid);
            }

            //if the entry is not in the table, no delete and
            //return the corresponding message
            if(!ids.contains(Integer.toString(timeslotID)+"+"+ Integer.toString(visitorID))){
                success = "This timeslot is not in your schedule.";
                conn.rollback();

            }
            //if the entry is in the table, delete and
            //return the corresponding message
            else{
                PreparedStatement stmt = conn.prepareStatement("delete from individualtimetable where visitorid = ? and timeslotid = ? ");
                stmt.setInt(1,visitorID);
                stmt.setInt(2, timeslotID);
                int rs = stmt.executeUpdate();
                success = "Deletion successful.";
                conn.commit();
                stmt.close();
            }

            rs1.close();
            stmt1.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

}
