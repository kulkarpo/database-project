package de.tum.in.dbpra.finalProject.controller;


import de.tum.in.dbpra.finalProject.model.bean.FestivalUser;
import de.tum.in.dbpra.finalProject.model.bean.TimeslotBean;
import de.tum.in.dbpra.finalProject.model.dao.TimeslotDAO;
import de.tum.in.dbpra.finalProject.model.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TimeslotServlet")
public class TimeslotServlet extends HttpServlet {
    //all tmeslots in the festival
    public List<TimeslotBean> festivalTimeslots;
    //all timeslots in the individual timetale of the visitor
    public List<TimeslotBean> individualTimeslots;
    //all timeslots in the individual timetale of the visitor after insert or delete
    public List<TimeslotBean> individualTimeslotsUpdated;
    //message to be displayed after insert or delete
    public String successMessage;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            TimeslotDAO slotDAO = new TimeslotDAO();
            UserDAO user = new UserDAO();
            //get visitorid from the current session
            FestivalUser fest = new FestivalUser();
            fest.setEmail(request.getSession().getAttribute("username").toString());
            user.getCurrentUserId(fest);

            int visitorID = -1;

            try {
                visitorID= fest.getUserid();
                //if add button is clicked
                if (request.getParameter("slotID") != null) {
                    String slotID = request.getParameter("slotID");
                    int slotIDint = Integer.parseInt(slotID);

                    // try to insert the entry with id slotIDint
                    // in the individual timetable of the visitor with id visitorID
                    // and return success message
                    successMessage = slotDAO.insertIndividualTimeslot(slotIDint, visitorID);
                    request.setAttribute("message", successMessage);
                }
                //if delete button is clicked
                else if (request.getParameter("slotIDdel") != null) {
                    String slotID = request.getParameter("slotIDdel");
                    int slotIDint = Integer.parseInt(slotID);

                    // try to delete the entry with id slotIDint
                    // from the individual timetable of the visitor with id visitorID
                    // and return success message
                    successMessage = slotDAO.deleteIndividualTimeslot(slotIDint, visitorID);
                    request.setAttribute("message", successMessage);
                }

                //if no column is clicked on, order by timeslotid
                String individualColumnToOrderBy = "timeslotid";
                individualTimeslotsUpdated = slotDAO.listIndividualTimeslots(individualColumnToOrderBy, visitorID);
                request.setAttribute("Itimeslots", individualTimeslotsUpdated);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        catch (UserDAO.UserNotFoundException e){
            request.setAttribute("error", e.toString());
        }
        catch (Exception e){
            request.setAttribute("error", e.toString());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/updatedIndividualTimetable.jsp");
        dispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try{
            TimeslotDAO slotDAO = new TimeslotDAO();
            UserDAO user = new UserDAO();
            //get visitor id from the current session
            FestivalUser fest = new FestivalUser();
            fest.setEmail(request.getSession().getAttribute("username").toString());
            user.getCurrentUserId(fest);

            //in case the visitor id is invalid
            int visitorID = -1;

            try {
                visitorID = fest.getUserid();

                if(request.getParameter("orderby") != null) {
                    //order the festival schdule table by the column chosen by the visitor
                    String columnToOrderBy = request.getParameter("orderby");
                    festivalTimeslots = slotDAO.listFestivalTimeslots(columnToOrderBy);
                    request.setAttribute("Ftimeslots", festivalTimeslots);
                }else {
                    //if no column is clicked on, order the festival schdule table by timeslotid
                    String columnToOrderBy = "timeslotid";
                    festivalTimeslots = slotDAO.listFestivalTimeslots(columnToOrderBy);
                    request.setAttribute("Ftimeslots", festivalTimeslots);
                }


                if(request.getParameter("iorderby") != null) {
                    //order the festival schdule table by the column chosen by the visitor
                    String individualColumnToOrderBy = request.getParameter("iorderby");
                    individualTimeslots = slotDAO.listIndividualTimeslots(individualColumnToOrderBy, visitorID);
                    request.setAttribute("Itimeslots", individualTimeslots);
                }else {
                    //if no column is clicked on, order the festival schdule table by timeslotid
                    String individualColumnToOrderBy = "timeslotid";
                    individualTimeslots = slotDAO.listIndividualTimeslots(individualColumnToOrderBy, visitorID);
                    request.setAttribute("Itimeslots", individualTimeslots);
                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        catch (UserDAO.UserNotFoundException e){
            request.setAttribute("error", e.toString());
        }
        catch (Exception e){
            request.setAttribute("error", e.toString());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Timeslot.jsp");
        dispatcher.forward(request, response);
    }
}
