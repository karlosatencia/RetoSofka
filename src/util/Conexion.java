package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Jugador;
import model.Pregunta;

public class Conexion {
    private String url;
    private Connection conexion;
            
    public Conexion(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public void insertar(Pregunta ob) {
        String sql = "INSERT INTO PREGUNTAS (contenido, respuesta1, respuesta2, respuesta3, respuesta4, categoria, opCorrecta, valor) VALUES (?,?,?,?,?,?,?,?)";
                   
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, ob.getContenido());
            ps.setString(2, ob.getRespuesta1());
            ps.setString(3, ob.getRespuesta2());
            ps.setString(4, ob.getRespuesta3());
            ps.setString(5, ob.getRespuesta4());
            ps.setInt(6, ob.getCategoria());
            ps.setInt(7, ob.getOpCorrecta());
            ps.setInt(8, ob.getValor());
            ps.execute();
            System.out.println("Datos ingresados correctamente...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } //Fin insertar

    public void registrarJugador(Jugador jug) {
        String sql = "INSERT INTO JUGADORES (nombre, id, puntos, ronda, estado) VALUES (?,?,?,?,?)";
                   
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, jug.getNombre());
            ps.setInt(2, jug.getId());
            ps.setInt(3, jug.getPuntos());
            ps.setInt(4, jug.getRonda());
            ps.setString(5, jug.getEstado());
            ps.execute();

            
            System.out.println("Datos ingresados correctamente...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } //Fin insertar
    
  
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void conectar(){
        try{
            Class.forName("org.sqlite.JDBC");
            conexion  = DriverManager.getConnection("jdbc:sqlite:"+url);
            //System.out.println("Conexión a la base de datos exitosa!");
            
        }catch (SQLException e){
            System.out.println("Error de conexión con la base de datos: " + e);
        }catch (ClassNotFoundException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cerrarConexion(){
        try{
            conexion.close();
        }catch(SQLException e){
            System.out.println("Error al cerrar la conexión con la base de datos: " + e);
        }
    }
}