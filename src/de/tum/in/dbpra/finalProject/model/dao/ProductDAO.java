package de.tum.in.dbpra.finalProject.model.dao;

import de.tum.in.dbpra.base.model.dao.BaseDAO;
import de.tum.in.dbpra.finalProject.model.bean.ProductBean;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

public class ProductDAO extends BaseDAO {

    /**
     * Attempts to insert an entry into the products table  and corresponding insertion to registers table
     * returns a success message
     *
     * @param providerID - the id of the logged provider
     * @param  productName - name of the product
     * @param  vendor - optional name of the vendor
     * @param  unitPrice - unit price of the product
     * @param productType - type of the product
     * @param extraDescription - optional extra description
     * @param quantity - quantity of products to be inserted
     *                   ...
     * @return a success message stating whether the insertion was successful or not
     */

    public String addProduct(int providerID, String productName, String vendor,
                             Double unitPrice, String productType, String extraDescription, int quantity) {
        String success = "";
        //ArrayList<String> ids = new ArrayList<>();

        try {

            Connection conn = getConnection();
            conn.setAutoCommit(false);

            // add an entry into product table

            PreparedStatement stmt = conn.prepareStatement("insert into product(name, vendor, " +
                    "price, type, remquantity, extradescription) values (?, ?, ?, ?::producttype, ?, ?) ");

            stmt.setString(1, productName);
            stmt.setString(2, vendor);
            stmt.setDouble(3, unitPrice);
            stmt.setString(4, productType);
            stmt.setInt(5, 0);
            stmt.setString(6, extraDescription);

            int result = stmt.executeUpdate();
            if (result > 0) {
                // if successful get the product id from above insertion
                PreparedStatement stmt3 = conn.prepareStatement("select currval('productid_seq')");

                ResultSet rs = stmt3.executeQuery();

                if (rs.next()) {
                    int productID = rs.getInt(1);

                    //make a corresponding entry into registers table

                    PreparedStatement stmt2 = conn.prepareStatement("insert into registers(providerid,productid,quantity)" +
                            " values (?, ?, ?) ");
                    stmt2.setInt(1, providerID);
                    stmt2.setInt(2, productID);
                    stmt2.setInt(3, quantity);
                    result = stmt2.executeUpdate();
                    if (result > 0) {
                        // commit and return true only of both insertion are successful
                        success = "Product Registered Successfully";
                        conn.commit();
                    } else {
                        // rollback both the insertions if insert to registers fails
                        success = "Oops! Something Went Wrong";
                        conn.rollback();
                    }
                    stmt2.close();
                    stmt3.close();
                } else {
                    // rollback
                    success = "Oops! Something Went Wrong";
                    conn.rollback();
                }

                stmt.close();
                conn.close();
            }
        } catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
        }

        return success;
    }



    /**
     * Attempts to insert an entry into the registers table and returns a success message
     *
     * @param productID - the id of the product  to be inserted
     * @param providerID - the id of the logged provider
     * @return a success message stating whether the insertion was successful or not
     */

    //when the user clicks on "add" button after inserting quantity into
    //numeric field (a positive integer), this method is called
    //which inserts a new row in registers table with a given quantity
    // for the logged-in user and for the specified product(given by row)

    public String addQuantity(int quantity, int providerID, int productID) {
        String success = "";
        //ArrayList<String> ids = new ArrayList<>();

        try {

            Connection conn = getConnection();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement("insert into registers(providerid,productid,quantity) values (?, ?, ?) ");
            stmt.setInt(1, providerID);
            stmt.setInt(2, productID);
            stmt.setInt(3, quantity);
            int result = stmt.executeUpdate();
            if (result > 0) {
                success = "Quantity added successfully.";
                conn.commit();
            } else {
                success = "Oops! something went wrong!";
                conn.rollback();
            }

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Query table products and registers from the DB in order to get all products
     *
     * @param column - column to order by
     * @return array list ProductBeans with all product provider has registered
     */
    public List<ProductBean> listRegisteredProducts(String column, int providerID) {
        List<ProductBean> products = new ArrayList<ProductBean>();

        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);


            PreparedStatement stmt = conn.prepareStatement("select p.productid, p.name, p.price, p.remquantity, r.regquantity " +
                    "from (select productid, sum(quantity) as regquantity " +
                           "from registers where providerid = "+providerID+ "group by productid) r , product p " +
                    "where p.productid = r.productid order by p."+column);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                ProductBean product = new ProductBean();
                product.setProductId(rs.getInt("productid"));
                product.setProductName(rs.getString("name"));
                product.setUnitPrice(rs.getDouble("price"));
                product.setRemainingQuantity(rs.getInt("remquantity"));
                product.setRegisteredQuantity(rs.getInt("regquantity"));
                products.add(product);
            }
            conn.commit();
            rs.close();
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            products = new ArrayList<>();
        }
        return products;
    }

}
