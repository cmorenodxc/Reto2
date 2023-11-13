package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

class ClientesDBRepositoryTest {

    private IClientesRepo repo;

    @BeforeEach
    void setUp() throws Exception {

        repo = new ClientesDBRepository();
    }

    @Test
    void dadosClientesEmpresa_cuandoinsertarClientesEmpresaEnDB_entoncesIdValido() throws Exception {
        Empresa emp = new Empresa(null, "La Mar Salada SL", "lms@s.com",
                "Calle SI 3", LocalDate.now(), true, false, "J12345678", new String[]{"Dev", "Marketing"});
        repo.insertClientesEmpresa(emp);

        System.out.println(emp);

        assertThat(emp.getId(), greaterThan(0));
    }

}