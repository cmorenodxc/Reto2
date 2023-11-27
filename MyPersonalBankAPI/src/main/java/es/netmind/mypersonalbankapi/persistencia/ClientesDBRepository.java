package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;

import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.properties.PropertyValues;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ClientesDBRepository implements IClientesRepo{

    @Value("${db_url}")
    private String db_url1;


 /*   public ClientesDBRepository() throws Exception {
        PropertyValues props = new PropertyValues();
        db_url = props.getPropValues().getProperty("db_url");
    }*/
    @Override
    public Empresa insertClientesEmpresa(Empresa nuevoClienteEmpresa) throws Exception {
         String sql = "INSERT INTO cliente values (?,NULL,?,?,?,?,?,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url1);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, "Empresa");
            stmt.setInt(2, nuevoClienteEmpresa.isActivo() ? 1 : 0);
            stmt.setString(3, nuevoClienteEmpresa.getAlta().toString());
            stmt.setString(4, nuevoClienteEmpresa.getDireccion());
            stmt.setString(5, nuevoClienteEmpresa.getEmail());
            stmt.setInt(6, nuevoClienteEmpresa.isMoroso() ? 1 : 0);
            stmt.setString(7, nuevoClienteEmpresa.getNombre());
            stmt.setString(8, nuevoClienteEmpresa.getCif());
            stmt.setString(9, null);
            stmt.setString(10, null);


            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                nuevoClienteEmpresa.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Cliente creado erroneamente!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return nuevoClienteEmpresa;
    }

    public List<Empresa> getListaClienteEmpresa() throws Exception {

        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM cliente c WHERE c.dtype = 'Empresa'";

        try (
                Connection conn = DriverManager.getConnection(db_url1);
                PreparedStatement stmt = conn.prepareStatement(sql);

        ) {
            //stmt.setString(1, "%" + iniciales + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                empresas.add(
                        new Empresa(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("email"),
                                rs.getString("direccion"),
                                rs.getDate("alta").toLocalDate(),
                                rs.getBoolean("activo"),
                                rs.getBoolean("moroso"),
                                rs.getString("cif"),
                                (null))

                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return empresas;
    }

    @Override
    public List<Cliente> getAll() {
        return null;
    }

    @Override
    public Cliente getClientById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Cliente addClient(Cliente cliente) throws Exception {
        return null;
    }

    @Override
    public boolean deleteClient(Cliente cliente) throws Exception {
        return false;
    }

    @Override
    public Cliente updateClient(Cliente cliente) throws Exception {
        return null;
    }
}
