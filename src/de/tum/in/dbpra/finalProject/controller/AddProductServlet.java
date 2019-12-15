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

@WebServlet(name = "AddProductServlet")
public class AddProductServlet extends HttpServlet {

    public List<ProductBean> products;
    public List<ProductBean> updatedProducts;
    public String successMessage;


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
                //if add button is clicked, get the parameters from the form
                String productName = request.getParameter("productname");
                String vendor = request.getParameter("vendor");
                Double unitPrice = Double.parseDouble(request.getParameter("unitprice"));
                String productType = request.getParameter("type");
                String extraDescription = request.getParameter("extradescription");
                int registeredQuantity = Integer.parseInt(request.getParameter("quantity"));

                //insert into products
                successMessage = productDAO.addProduct(providerID, productName, vendor,
                        unitPrice, productType, extraDescription,
                        registeredQuantity);

                //refresh the view after insertion, and order the view by default - "name" (product name)
                    String columnToOrderBy = "name";
                    updatedProducts = productDAO.listRegisteredProducts(columnToOrderBy, providerID);
                    request.setAttribute("RProducts", updatedProducts);
                    request.setAttribute("message", successMessage);
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
