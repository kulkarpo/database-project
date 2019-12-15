package de.tum.in.dbpra.finalProject.model.dao;

import de.tum.in.dbpra.base.model.dao.BaseDAO;
import de.tum.in.dbpra.finalProject.model.bean.FestivalUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends BaseDAO {


    /**
     * Query table festival_user from the DB in order to get the id of a user from their username
     *
     * @param user - festivaluser object whose id will be filled in if username is found in database
     */
    public void getCurrentUserId(FestivalUser user) throws UserNotFoundException, SQLException, ClassNotFoundException{

        String query = "SELECT userid FROM festival_user WHERE email = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, user.getEmail());
            rs = pstmt.executeQuery();

            // if user is found
            if (rs.next()) {
                user.setUserid(rs.getInt("userid"));
            } else {
                throw new UserNotFoundException("There is no User with email " + user.getEmail() + "!");
            }
            con.commit();
        }
        catch(Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        finally {
            try { rs.close(); } catch(Exception e1) {}
            try { pstmt.close(); } catch(Exception e2) {}
            try { con.close(); } catch(Exception e3) {}
        }
    }


    /**
     * Query table festival_user from the DB to check if login information belongs to a valid user
     *
     * @param user - festival_user object
     * @param username - username from form
     * @param password - hashed password from form
     * @return  String type of the user - if user doesn't exist, type is blank
     */
    public String findUser(FestivalUser user, String username, String password) throws UserNotFoundException, SQLException, ClassNotFoundException {

        String query = "SELECT * FROM festival_user WHERE email = ? AND password = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String type="";

        try {
            con = getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            // if user is found
            if (rs.next()) {
                user.setType(rs.getString("type"));
                type = user.getType();
            } else {
                throw new UserNotFoundException("There is no User with email " + username + "!");
            }
            con.commit();
        }
        catch(Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        finally {
            try { rs.close(); } catch(Exception e1) {}
            try { pstmt.close(); } catch(Exception e2) {}
            try { con.close(); } catch(Exception e3) {}
        return type;
        }
    }

    public static class UserNotFoundException extends Throwable {

        private static final long serialVersionUID = 1L;

        UserNotFoundException(String message) {
                super(message);
            }
    }

}
