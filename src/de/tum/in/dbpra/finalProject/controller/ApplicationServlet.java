package de.tum.in.dbpra.finalProject.controller;


import de.tum.in.dbpra.finalProject.model.bean.FestivalUser;
import de.tum.in.dbpra.finalProject.model.bean.ApplicationBean;
import de.tum.in.dbpra.finalProject.model.dao.ApplicationDAO;
import de.tum.in.dbpra.finalProject.model.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
    public List<ApplicationBean> festivalApplications;
    public String successMessage;


    /**
     * method to execute when sending user input through button clicks
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ApplicationDAO applicationDAO = new ApplicationDAO();
            UserDAO user = new UserDAO();
            FestivalUser fest = new FestivalUser();
            fest.setEmail(request.getSession().getAttribute("username").toString());

            user.getCurrentUserId(fest);

            try {
                //details button clicked
                if (request.getParameter("applicationID_details") != null) {
                    //get the id of that application
                    String applicationID = request.getParameter("applicationID_details");
                    request.setAttribute("rowid", applicationID);
                    request.setAttribute("detailed", true);
                    request.setAttribute("message", successMessage);
                }

                //accept button clicked
                else if (request.getParameter("applicationID_accept") != null) {
                    //get the id of that application
                    String applicationID = request.getParameter("applicationID_accept");
                    int applicationIDint = Integer.parseInt(applicationID);
                    //update the status of the application
                    successMessage = applicationDAO.updateStatus(applicationIDint, "accepted");
                    request.setAttribute("message", successMessage);
                }
                
                //reject button clicked
                else if (request.getParameter("applicationID_reject") != null) {
                    //get the id of that application
                    String applicationID = request.getParameter("applicationID_reject");
                    int applicationIDint = Integer.parseInt(applicationID);
                    //update the status of the application
                    successMessage = applicationDAO.updateStatus(applicationIDint, "rejected");
                    request.setAttribute("message", successMessage);
                }
                
                //postpone button clicked
                else if (request.getParameter("applicationID_postpone") != null) {
                    //get the id of that application
                    String applicationID = request.getParameter("applicationID_postpone");
                    int applicationIDint = Integer.parseInt(applicationID);
                    //update the status of the application
                    successMessage = applicationDAO.updateStatus(applicationIDint, "waiting");
                    request.setAttribute("message", successMessage);
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

        updateApplicationsList(request, response);
        
        if (request.getAttribute("rowid") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/detailedView.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Application.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * method to execute when loading the page. needs to update the applications table
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateApplicationsList(request, response);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Application.jsp");
        dispatcher.forward(request, response);
    }


    /**
     * method to update the applications list
     */

    protected void updateApplicationsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try{
            ApplicationDAO appDAO = new ApplicationDAO();
            UserDAO user = new UserDAO();
            FestivalUser fest = new FestivalUser();
            fest.setEmail(request.getSession().getAttribute("username").toString());

            user.getCurrentUserId(fest);

            try {
                //if this was called after a button click from the post method - need to fill the detailed view
                if (request.getAttribute("rowid") != null) {
                    //get the details of the particular application
                    festivalApplications = appDAO.listApplication(Integer.parseInt((String)request.getAttribute("rowid")));
                    //fill the detailed view
                    request.setAttribute("Details", festivalApplications);
                //if this was called from the get method - need to fill the full application list
                } else {
                    //get all the applications
                    festivalApplications = appDAO.listApplications();
                    //fill the applications table
                    request.setAttribute("Fapplications", festivalApplications);
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
    }
}
