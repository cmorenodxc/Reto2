package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ClientesDBRepositoryTest {
    @Autowired
    private IClientesRepo repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
    }


    /*@BeforeEach
    void setUp() throws Exception {

        repo = new ClientesDBRepository();
    } */

    @Test
    void dadosClientesEmpresa_cuandoinsertarClientesEmpresaEnDB_entoncesIdValido() throws Exception {
        Empresa emp = new Empresa(null, "La Mar Salada SL", "lms@s.com",
                "Calle SI 3", LocalDate.now(), true, false, "J12345678", new String[]{"Dev", "Marketing"});
        repo.insertClientesEmpresa(emp);

        System.out.println(emp);

        assertThat(emp.getId(), greaterThan(0));
    }

}