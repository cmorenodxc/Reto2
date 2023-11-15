package com.myshoppingcart.persistence;

import com.myshoppingcart.exception.ProductNotFoundException;
import com.myshoppingcart.exception.UsuarioNotFoundException;
import com.myshoppingcart.model.Producto;
import com.myshoppingcart.model.Usuario;

import java.util.List;

public interface IProductoRepository {
    public List<Producto> getProducts() throws Exception;
    public List<Producto> getUserProducts(int uid) throws Exception;
    public Producto insertProduco(Producto nuevoProducto) throws Exception;

    public Producto updateProducto(Producto unProducto) throws Exception;

    public Producto getProducto(int pid) throws ProductNotFoundException,Exception;

    public boolean deleteProducto(Integer pid) throws Exception;

}
