package com.myshoppingcart.persistence;

import com.myshoppingcart.model.Compra;
import com.myshoppingcart.properties.PropertyValues;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class CompraDBRepository implements ICompraRepository {
    private static String connUrl;

    public CompraDBRepository() throws IOException {
        PropertyValues props = new PropertyValues();
        connUrl = props.getPropValues().getProperty("db_url");
    }

    @Override
    public Compra insertCompra(Compra nuevaCompra) throws Exception {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connUrl);
            conn.setAutoCommit(false);

            // OBTENEMOS EL PRODUCTO


            // INSERTAR EN COMPRA


            // ACTUALIZAR SALDO DE USUARIO


            // ACTUALIZAR EXISTENCIAS DE PRODUCTO

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            if(conn!=null) conn.close();
        }

        return nuevaCompra;
    }
}
