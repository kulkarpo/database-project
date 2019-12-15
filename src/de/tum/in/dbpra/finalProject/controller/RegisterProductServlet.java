package de.tum.in.dbpra.finalProject.controller;


import de.tum.in.dbpra.finalProject.model.bean.FestivalUser;
import de.tum.in.dbpra.finalProject.model.bean.ProductBean;
import de.tum.in.dbpra.finalProject.model.dao.ProductDAO;
import de.tum.in.dbpra.finalProject.model.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

@WebServlet(name = "RegisterProductServlet")
public class RegisterProductServlet extends HttpServlet {

    public List<ProductBean> products;
    public List<ProductBean> updatedProducts;
    public String successMessage;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try{
            ProductDAO productDAO = new ProductDAO();
            UserDAO user = new UserDAO();
            FestivalUser fest = new FestivalUser();
            fest.setEmail(request.getSession().getAttribute("username").toString());

            user.getCurrentUserId(fest);

            int providerID = -1;

            try {
                providerID = fest.getUserid();

                if(request.getParameter("orderby") != null) {
                    //order the products by the column chosen by the provider
                    String columnToOrderBy = request.getParameter("orderby");
                    products = productDAO.listRegisteredProducts(columnToOrderBy, providerID);
                    request.setAttribute("RProducts",products);
                }else {
                    //if no column is clicked on, order the products by product names
                    String columnToOrderBy = "name";
                    products = productDAO.listRegisteredProducts(columnToOrderBy, providerID);
                    request.setAttribute("RProducts",products);
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Product.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ProductDAO productDAO = new ProductDAO();
            UserDAO user = new UserDAO();
            FestivalUser fest = new FestivalUser();
            fest.setEmail(request.getSession().getAttribute("username").toString());

            user.getCurrentUserId(fest);

            int providerID = -1;

            try {
                providerID= fest.getUserid();
                //if add button is clicked
                if (request.getParameter("quantity") != null) {
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    int productID = Integer.parseInt(request.getParameter("productid"));
                    //add the specified qunatity into database (into registers table)
                    successMessage = productDAO.addQuantity(quantity, providerID, productID);

                    //refresh the view after insertion, and order the view by default - "name" (product name)
                    String columnToOrderBy = "name";
                    updatedProducts = productDAO.listRegisteredProducts(columnToOrderBy, providerID);
                    request.setAttribute("RProducts", updatedProducts);
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Product.jsp");
        dispatcher.forward(request, response);

    }

}
