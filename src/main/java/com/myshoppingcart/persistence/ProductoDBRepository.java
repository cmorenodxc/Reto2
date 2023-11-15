package com.myshoppingcart.persistence;

import com.myshoppingcart.exception.ProductNotFoundException;
import com.myshoppingcart.model.Producto;
import com.myshoppingcart.properties.PropertyValues;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class ProductoDBRepository implements IProductoRepository {
    private static String connUrl;

    public ProductoDBRepository() throws IOException {
        PropertyValues props = new PropertyValues();
        connUrl = props.getPropValues().getProperty("db_url");
    }
    @Override
    public Producto getProducto(int pid) throws ProductNotFoundException, Exception {
        Producto pro = null;

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM producto p WHERE p.pid='" + pid +"' LIMIT 1")
        ) {
            if (rs.next()) {
                pro = new Producto(

                        rs.getInt("pid"),
                        rs.getString("codigo"),
                        rs.getString("marca"),
                        rs.getString("tipo"),
                        rs.getInt("precio"),
                        rs.getInt("existencias")
                );
            } else {
                throw new ProductNotFoundException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return pro;
    }


    @Override
    public List<Producto> getProducts() throws Exception {
        List<Producto> listADevolver = new ArrayList<>();
        String sql = "SELECT p.* FROM producto p WHERE 1";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                // ordenes sql
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
        ) {
            while (rs.next()) {
                listADevolver.add(new Producto(rs.getInt("mid"), rs.getString("codigo"), rs.getString("marca"),
                        rs.getString("tipo"), rs.getInt("precio"), rs.getInt("existencias")));
            }
        }

        return listADevolver;
    }

    @Override
    public List<Producto> getUserProducts(int uid) throws Exception {
        List<Producto> listADevolver = new ArrayList<>();
        String sql = "SELECT p.* FROM producto p INNER JOIN compra c ON c.producto=p.pid WHERE c.usuario=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, uid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listADevolver.add(new Producto(
                        rs.getInt("pid"),
                        rs.getString("codigo"),
                        rs.getString("marca"),
                        rs.getString("tipo"),
                        rs.getInt("precio"),
                        rs.getInt("existencias")
                ));
            }
        }

        return listADevolver;
    }

    public Producto insertProduco(Producto nuevoProducto) throws Exception {

        String sql = "INSERT INTO producto values (NULL,?,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {


            stmt.setInt(1, Integer.parseInt(nuevoProducto.getCodigo()));
            stmt.setString(2, nuevoProducto.getMarca());
            stmt.setString(3, nuevoProducto.getTipo());
            stmt.setDouble(4, nuevoProducto.getPrecio());
            stmt.setInt(5, nuevoProducto.getExistencias());


            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                nuevoProducto.setMid(genKeys.getInt(1));
            } else {
                throw new SQLException("producto creado erroneamente!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return nuevoProducto;
    }


    public Producto updateProducto(Producto unProducto) throws Exception {
        String sql = "UPDATE producto set codigo=?, marca=?, tipo=?, precio=?, existencias=? WHERE pid=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, Integer.parseInt(unProducto.getCodigo()));
            stmt.setString(2, unProducto.getMarca());
            stmt.setString(3, unProducto.getTipo());
            stmt.setDouble(4, unProducto.getPrecio());
            stmt.setInt(5, unProducto.getExistencias());
            stmt.setInt(6, unProducto.getMid());

            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return unProducto;
    }

    public boolean deleteProducto(Integer pid) throws Exception {
       String sql = "DELETE FROM producto WHERE pid=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, pid);

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if(rows<=0){
                throw new ProductNotFoundException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }

}

