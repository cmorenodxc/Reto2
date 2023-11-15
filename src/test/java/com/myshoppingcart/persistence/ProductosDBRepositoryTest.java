package com.myshoppingcart.persistence;

import com.myshoppingcart.exception.ProductNotFoundException;
import com.myshoppingcart.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ProductosDBRepositoryTest {

    private IProductoRepository repo;

    @BeforeEach
    void sepUp() throws IOException {
        repo = new ProductoDBRepository();
    }

    @Test
    public void dadosProductos_cuandogetUserProductsUsuarioEnDB_entoncesProductos() throws Exception {
        List<Producto> productos = repo.getUserProducts(1);

        System.out.println(productos);

        assertThat(productos.size(), greaterThan(0));

    }

    @Test
    public void dadosProductos_cuandogetUserProductsUsuarioNoEnDB_entoncesVacio() throws Exception {
        List<Producto> productos = repo.getUserProducts(100);

        System.out.println(productos);

        assertThat(productos.size(), is(0));

    }

    @Test
    void dadosProductos_cuandoinsertarProductoEnDB_entoncesIdValido() throws Exception {
         Producto pro1 = new Producto(null, "6639","uevo","nuevo",99.99,90);

        repo.insertProduco(pro1);

        System.out.println(pro1);

        assertThat(pro1.getMid(), greaterThan(0));
    }
    @Test
    void dadosProductos_cuandoupdatearProductoEnDB_entoncesIdValido() throws Exception {
        Producto pro = repo.getProducto(1);
        pro.setMarca("Update");


        repo.updateProducto(pro);

        assertThat(pro.getMarca(), is("Update"));
    }
    @Test
    void dadosProductos_cuandogetProductosEnDB_entoncesProducto() throws Exception {
        Producto pro = repo.getProducto(1);

        System.out.println(pro);

        assertThat(pro.getCodigo(), is("6639"));

    }
    @Test
    void dadoProducto_cuandoDelete_entonces_Ok() throws Exception {
        int id = 12;
        boolean ok = repo.deleteProducto(id);

        assertThat(ok, is(true));
    }

   @Test
    void dadoProductoNoExistente_cuandoActualiza_entonces_Excepccion() throws ProductNotFoundException, Exception {
        Producto pro = repo.getProducto(12);
        pro.setMarca("marca nuevo");


        assertThrows(Exception.class, () -> {
            repo.updateProducto(pro);
        });

    }

}
