package de.tum.in.dbpra.finalProject.model.dao;

import de.tum.in.dbpra.base.model.dao.BaseDAO;
import de.tum.in.dbpra.finalProject.model.bean.ApplicationBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends BaseDAO {

    /**
     * Query table application from the DB in order to get all festival applications
     *
     * @return  array list of ApplicationBeans with all applications
     */

    public List<ApplicationBean> listApplications (){
        List<ApplicationBean> applications = new ArrayList<ApplicationBean>();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            //query to select all applications
            PreparedStatement stmt = conn.prepareStatement("select applicationid, name, contact, address, wisheddate, letter, sellingindicator, result, type " +
                    "from application order by name");

            ResultSet rs = stmt.executeQuery();

            //fill beans one by one and add to the arraylist
            while (rs.next()) {
                    ApplicationBean application = new ApplicationBean();
                    application.setName(rs.getString("name"));
                    application.setAddress(rs.getString("address"));
                    application.setContact(rs.getString("contact"));
                    application.setLetter(rs.getString("letter"));
                    application.setResult(rs.getString("result"));
                    application.setType(rs.getString("type"));
                    application.setWisheddate(rs.getDate("wisheddate"));
                    application.setApplicationid(rs.getInt("applicationid"));
                    applications.add(application);
                }
                conn.commit();
                rs.close();
                stmt.close();
                conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            applications = new ArrayList<>();
        }
        return applications;
    }


    /**
     * Query table application from the DB in order to get a particular festival application
     * 
     * @param applicationid - id of the particular application that is desired to be shown in full
     * @return  array list of ApplicationBeans with all applications
     */
    public List<ApplicationBean> listApplication (int applicationid){
        List<ApplicationBean> applications = new ArrayList<ApplicationBean>();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            //query to get the particular application
            PreparedStatement stmt = conn.prepareStatement("select applicationid, name, contact, address, wisheddate, letter, sellingindicator, result, type " +
                    "from application where applicationid = ?");
            stmt.setInt(1, applicationid);

            ResultSet rs = stmt.executeQuery();

            //fill the bean
            while (rs.next()) {
                ApplicationBean application = new ApplicationBean();
                application.setName(rs.getString("name"));
                application.setAddress(rs.getString("address"));
                application.setContact(rs.getString("contact"));
                application.setLetter(rs.getString("letter"));
                application.setResult(rs.getString("result"));
                application.setType(rs.getString("type"));
                application.setWisheddate(rs.getDate("wisheddate"));
                application.setApplicationid(rs.getInt("applicationid"));
                applications.add(application);
            }
            conn.commit();
            rs.close();
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            applications = new ArrayList<>();
        }
        return applications;
    }

    /**
     * update table application in order to update the status of a particular application
     * 
     * @param applicationid - id of the particular application that is desired to be shown in full
     * @param status - new status
     * @return  array list of ApplicationBeans with a single application. used arraylist to be able to pass the return value to the same method afterwards.
     */
    public String updateStatus(int applicationid, String status) {
        String success = "";

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            //query to check if same status already set
            PreparedStatement stmt = conn.prepareStatement("select applicationid from application where applicationid = ? and result = CAST(? AS resulttype)");
            stmt.setInt(1, applicationid);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
        
            //status already set
            if (rs.next()) {
                success = "Status already set.";
                stmt.close();
                
            //query to update the status
            } else {
                PreparedStatement stmt2 = conn.prepareStatement("update application set result = CAST(? AS resulttype) where applicationid = ?");
                stmt2.setString(1, status);
                stmt2.setInt(2, applicationid);
                int rs2 = stmt2.executeUpdate();
                if (rs2 == 1)
                {
                    success = "Update successful.";
                    conn.commit();
                }
                    else {
                        success = "Something went wrong";
                        conn.rollback();
                }
                stmt2.close();
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
