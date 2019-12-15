package de.tum.in.dbpra.finalProject.controller;

import de.tum.in.dbpra.finalProject.model.bean.FestivalUser;
import de.tum.in.dbpra.finalProject.model.dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;


@WebServlet(name = "Login")
public class Login extends HttpServlet {

    final protected static char[] hexArray = "0123456789ABCDEF"
            .toCharArray();

    // to get the hex of the hash from bytes
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    // hash password with md5 and return the hex
    public static String hashPassword(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(in.getBytes());
            byte[] out = md.digest();
            return bytesToHex(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            // get the username and password from form
            String username = request.getParameter("username");
            String password = hashPassword(request.getParameter("password"));

            UserDAO us = new UserDAO();
            FestivalUser user = new FestivalUser();

            // get the type of the user
            String type = us.findUser(user, username, password);

            // invalid user
            if(type.equals("")) {
                HttpSession session = request.getSession();

                // returns error message to login
                session.setAttribute("loginStatus", "failed");

                // redirects back to login informing login failed
                response.sendRedirect("index.jsp");
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("loginStatus", "success");

                // to provide the unique username of user
                session.setAttribute("username", username);

                // to help check the access type of user
                session.setAttribute("type", user.getType());

                // redirect user to appropriate page depending on access type
                if(user.getType().equals("visitor")) {
                    response.sendRedirect("visitorHomePage.jsp");
                }
                if(user.getType().equals("provider")) {
                    response.sendRedirect("providerHomePage.jsp");
                }
                if(user.getType().equals("organizer")) {
                    response.sendRedirect("organizerHomePage.jsp");
                }
            }

        }
        catch(UserDAO.UserNotFoundException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




    }
}
