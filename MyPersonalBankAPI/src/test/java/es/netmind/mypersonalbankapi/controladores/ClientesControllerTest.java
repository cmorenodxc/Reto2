package es.netmind.mypersonalbankapi.controladores;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ClientesControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));


    }

    @Test
    @Order(1)
    void dadosClientesenRepo_cuandosepidelista_entonceslistaOK() {

        ClientesController.mostrarLista();
        System.out.println(outContent);
        System.out.println("\nLista de clientes:");
        System.out.println("───────────────────────────────────");
        assertThat(outContent.toString(), containsString("Lista de clientes:"));
        assertThat(outContent.toString(), containsString("───────────────────────────────────"));
        assertThat(outContent.toString(), containsString("(1) Juan Juanez 1"));
        assertThat(outContent.toString(), containsString("(2) Luisa Perez 2"));
        assertThat(outContent.toString(), containsString("(3) Servicios Informatico SL 3"));

    }

    @Test
    @Order(2)
    void dadosClientesenRepo_cuandosepidelista_entonceslistaNOK() {

        ClientesController.eliminar(1);
        ClientesController.eliminar(2);
        ClientesController.eliminar(3);

        ClientesController.mostrarLista();
        System.out.println(outContent);
        System.out.println("\nLista de clientes:");
        System.out.println("───────────────────────────────────");
        assertThat(outContent.toString(), containsString("Lista de clientes:"));
        assertThat(outContent.toString(), containsString("───────────────────────────────────"));
        assertNotEquals(outContent.toString(), containsString("("));


    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }



}