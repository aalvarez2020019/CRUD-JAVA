/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.PreparedStatement;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

/**
 *
 * @author Usuario
 */
public class Cliente extends Persona {

    private String nit;
    private int id;
    Conexion cn;

    public Cliente() {
    }

    public Cliente(int id, String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id = id;
        this.nit = nit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    //CRUD
    @Override
    public void crear() {
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "insert into clientes(nit,nombres,apellidos,direccion,telefono,fecha_nacimiento) VALUES (?,?,?,?,?,?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            int executar = parametro.executeUpdate();
            System.out.println("INGRESO EXITOSO..." + Integer.toString(executar));
            cn.cerrar_conexion();

        } catch (Exception ex) {
            System.out.println("Error en crear: " + ex.getMessage());
        }
    }

    @Override
    public DefaultTableModel leer() {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "select * from clientes;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);

            String encabezado[] = {"id", "nit", "nombres", "apellidos", "direccion", "telefono", "nacimiento"};
            tabla.setColumnIdentifiers(encabezado);

            String datos[] = new String[7];
            while (consulta.next()) {
                datos[0] = consulta.getString("id_cliente");
                datos[1] = consulta.getString("nit");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();

        } catch (SQLException ex) {
            cn.cerrar_conexion();
            System.out.println("Error leer" + ex.getMessage());
        }
        return tabla;
    }
    //CRUD
    //@Override
    /*protected String[] crear(){
        try{
                //codigo fuente
                String datos[]= new String[6];
                datos[0] = getNit();
                datos[1] = getNombres();
                datos[2] = getApellidos();
                datos[3] = getDireccion();
                datos[4] = getTelefono();
                datos[5] = this.getFecha_nacimiento();
                return datos;
        } catch (Exception ex){
            System.out.println("Algo salio mal : " + ex.getMessage());
            return null;
        }
    }*/

}
