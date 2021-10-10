
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.*;
import util.Conexion;

public class ControladorJuego {

    Conexion conexion;
    public int correct;
    public int cat;
    public boolean resultadoDb;
    public boolean resultadoId;
    public boolean calificacion;
    private HashMap<Integer,PreguntasModel> hashMapPregunta;
    Pregunta mostrada = new Pregunta();
    Jugador jugadorActual = new Jugador();
        
    
    public void limpiar(){
        jtPregunta.setText("");
        jtPregunta.requestFocus();
        jtRespuesta1.setText("");
        jtRespuesta2.setText("");
        jtRespuesta3.setText("");
        jtRespuesta4.setText("");
        rbCorrecta1.setSelected(false);
        rbCorrecta2.setSelected(false);
        rbCorrecta3.setSelected(false);
        rbCorrecta4.setSelected(false);
        rbCategoria1.setSelected(false);
        rbCategoria2.setSelected(false);
        rbCategoria3.setSelected(false);
        rbCategoria4.setSelected(false);
        rbCategoria5.setSelected(false);

        jtNombreJugador.setText("");
        jtIdJugador.setText("");
        jtPreguntaRonda.setText("");
        jtRespuesta1Ronda.setText("");
        jtRespuesta2Ronda.setText("");
        jtRespuesta3Ronda.setText("");
        jtRespuesta4Ronda.setText("");
        rbR1.setSelected(false);
        rbR2.setSelected(false);
        rbR3.setSelected(false);
        rbR4.setSelected(false);
    }
    //-------------------------------------------------------------------------------------------------------------------
    public void calificar(int seleccion, int correcta){
        if(seleccion == correcta){
            //return true;
            calificacion = true;
        } 
        else {
            calificacion = false;
        }
    }
    //-------------------------------------------------------------------------------------------------------------------
    public void consultarJugador(int id) {
        String sql;
        ResultSet registros;
        try {
            String url = "./src/db/dbJuegoPreguntas.db";
            conexion = new Conexion(url);
            conexion.conectar();
            Statement  stmt = conexion.getConexion().createStatement();

            sql = "SELECT * FROM JUGADORES  where id ='"+id+"'";
            registros = stmt.executeQuery(sql);

            if (registros.next()) {
                
                //btnIniciar.setDisable(true);

                jtNombreJugador.setText(registros.getString(1));
                jtIdJugador.setText(String.valueOf(registros.getInt(2)));
                jtPuntajeJugador.setText(String.valueOf(registros.getInt(3)));
                jtNivelJugador.setText(String.valueOf(registros.getInt(4)));
                jugadorActual.setNombre(registros.getString(1));
                jugadorActual.setId(registros.getInt(2));
                jugadorActual.setPuntos(registros.getInt(3));
                jugadorActual.setRonda(registros.getInt(4));
                jugadorActual.setEstado(registros.getString(5));   
            }
            
            else {
                JOptionPane.showMessageDialog(null, "No se encontraron registros","INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);   
            }
            stmt.close();
            conexion.cerrarConexion();

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }    
    }
    //------------------------------------------------------------------------------------------------------------------
    public void mostrarPregunta(int categ){
        String sql;
        ResultSet registros;
        if(jugadorActual.getRonda()>5){
            JOptionPane.showMessageDialog(null,"Felicidades Haz Ganado!!!\n"+"Puntuación final: "+jugadorActual.getPuntos() + "Puntos.");
            int ptraidos = jugadorActual.getPuntos();
            System.out.println("Puntos traidos: " + ptraidos);
            System.exit(0);
        }
        
        
        hashMapPregunta = new HashMap<>();
        
        
        try {
            String url = "C:/Users/karlo/Documents/SOFKA/Reto Preg/dbJuegoPreguntas.db";
            conexion = new Conexion(url);
            conexion.conectar();
            Statement  stmt = conexion.getConexion().createStatement();

            sql = "SELECT * FROM PREGUNTAS where categoria ='"+categ+"'";
            registros = stmt.executeQuery(sql);

            int contador=0;
            while (registros.next()) {
            //for(int i=0; i<preguntas.length; i++){
                //btnIniciar.setDisable(true);
                
                hashMapPregunta.put(contador, new PreguntasModel(
                                                                registros.getString(1),
                                                                registros.getString(2),
                                                                registros.getString(3),
                                                                registros.getString(4),
                                                                registros.getString(5),
                                                                registros.getInt(6),
                                                                registros.getInt(7),
                                                                registros.getInt(8)
                                                                )
                                    );
                contador++;   
           
            }

            int escogidaR = 0;
            escogidaR =(int)(Math.random()*(contador-1));

            jtPreguntaRonda.setText(hashMapPregunta.get(escogidaR).getConten());
            jtRespuesta1Ronda.setText(hashMapPregunta.get(escogidaR).getRes1());
            jtRespuesta2Ronda.setText(hashMapPregunta.get(escogidaR).getRes2());
            jtRespuesta3Ronda.setText(hashMapPregunta.get(escogidaR).getRes3());
            jtRespuesta4Ronda.setText(hashMapPregunta.get(escogidaR).getRes4());
            
            int retorno = hashMapPregunta.get(escogidaR).getCorr();
            mostrada.setOpCorrecta(retorno);
            mostrada.setValor(hashMapPregunta.get(escogidaR).getVal());
            
            stmt.close();
            conexion.cerrarConexion();
  
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
    }
    //------------------------------------------------------------------------------------------------------------------
    public void verificarRegistro(){
        ResultSet registros;
        try {
            String url = "C:/Users/karlo/Documents/SOFKA/Reto Preg/dbJuegoPreguntas.db";
            conexion = new Conexion(url);
            conexion.conectar();
            Statement  stmt = conexion.getConexion().createStatement();

            String sqlVerificacion = "SELECT * FROM JUGADORES WHERE id ='"+jtIdJugador.getText()+"'";
            registros = stmt.executeQuery(sqlVerificacion);

            if (registros.next()) {
                
                JOptionPane.showMessageDialog(null,"Ya hay un jugador registrado con este ID","INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
                resultadoId = false;
            }
            
            else {
                resultadoId = true;
                  
            }
            stmt.close();
            conexion.cerrarConexion();

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    //------------------------------------------------------------------------------------------------------------------------------------
    private void comprobarBancoPreguntas(){
        
        int cantidad1, cantidad2, cantidad3, cantidad4, cantidad5;
        
        String sql;
        ResultSet registros;
        try {
            String url = "C:/Users/karlo/Documents/SOFKA/Reto Preg/dbJuegoPreguntas.db";
            conexion = new Conexion(url);
            conexion.conectar();
            Statement  stmt = conexion.getConexion().createStatement();

            sql = "SELECT COUNT(categoria) FROM PREGUNTAS WHERE CATEGORIA=1;";
            registros = stmt.executeQuery(sql);
            cantidad1 = registros.getInt(1);

            sql = "SELECT COUNT(categoria) FROM PREGUNTAS WHERE CATEGORIA=2;";
            registros = stmt.executeQuery(sql);
            cantidad2 = registros.getInt(1);

            sql = "SELECT COUNT(categoria) FROM PREGUNTAS WHERE CATEGORIA=3;";
            registros = stmt.executeQuery(sql);
            cantidad3 = registros.getInt(1);

            sql = "SELECT COUNT(categoria) FROM PREGUNTAS WHERE CATEGORIA=4;";
            registros = stmt.executeQuery(sql);
            cantidad4 = registros.getInt(1);

            sql = "SELECT COUNT(categoria) FROM PREGUNTAS WHERE CATEGORIA=5;";
            registros = stmt.executeQuery(sql);
            cantidad5 = registros.getInt(1);
         
            if(cantidad1<5 || cantidad2<5 || cantidad3<5 || cantidad4<5 || cantidad5<5){
                JOptionPane.showMessageDialog(null,"Aún no puedes iniciar\n"
                + "Cada categoría debe tener mínimo 5 preguntas\n"
                + "Categoría 1: " + cantidad1 + " preguntas\n"
                + "Categoría 2: " + cantidad2 + " preguntas\n"
                + "Categoría 3: " + cantidad3 + " preguntas\n"
                + "Categoría 4: " + cantidad4 + " preguntas\n"
                + "Categoría 5: " + cantidad5 + " preguntas\n");
                resultadoDb = false;
                
            }
            else{
                resultadoDb = true;
            }
            
            stmt.close();
            conexion.cerrarConexion();
   
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    private boolean validarPregunta(){

        int resul1=0;
        int resul2=0;
        int resul3=0;
        boolean resulFinal;

        if(jtPregunta.getText()=="" ||jtRespuesta1.getText()=="" || jtRespuesta2.getText()=="" || jtRespuesta3.getText()=="" || jtRespuesta4.getText()==""){
            JOptionPane.showMessageDialog(null,"Por favor, rellene todos los campos.");
            resul1=0;
        }
        else {
            resul1=1;
            //validarCorrecta---------------------------------------------------------------------------------------------------------
            if(rbCorrecta1.isSelected()) {
                correct = 1;
                resul2=1;
                                
            }
            else if(rbCorrecta2.isSelected()) {
                correct = 2;
                resul2=1;
                               
            }
            else if(rbCorrecta3.isSelected()) {
                correct = 3;
                resul2=1;
                                
            }
            else if(rbCorrecta4.isSelected()) {
                correct = 4;
                resul2=1;
                               
            }
            else{
                JOptionPane.showMessageDialog(null,"Indique cual es la respuesta correcta"); 
                resul2=0;
            }
            
        //------------------------------------validarCategoria-----------------------------------------------------------------
            if(rbCategoria1.isSelected()) {
                cat = 1;
                resul3=1;
                
            }
            else if(rbCategoria2.isSelected()) {
                cat = 2;
                resul3=1;
               
            }
            else if(rbCategoria3.isSelected()) {
                cat = 3;
                resul3=1;
                
            }
            else if(rbCategoria4.isSelected()) {
                cat = 4;
                resul3=1;
                
            }
            else if(rbCategoria5.isSelected()) {
                cat = 5;
                resul3=1;
               
            }
            else{
                JOptionPane.showMessageDialog(null,"Seleccione una categoria para la pregunta");
                resul3=0; 
            }
        }
        if((resul1 + resul2 + resul3) < 3){
            resulFinal = false;
        }
        else{
            resulFinal = true;
        }
        return resulFinal;
        
    }
    //------------------------------------------------------------------------------------------------------------------
    private void validarJugador(){
        if(rbSi.isSelected()==false && rbNo.isSelected()==false ) {
            JOptionPane.showMessageDialog(null,"Seleccione si es un jugador nuevo o no");
        }
       
        else{
            JOptionPane.showMessageDialog(null,"Por favor, ingrese sus datos correctamente");  
        }
        
    }
    //------------------------------------------------------------------------------------------------------------------
    public static boolean validarId (String valor)
    {
        boolean respuesta;
        
        if (valor.matches("[0-9]*"))
        {
            respuesta = true;
        }
        else
        {
            respuesta = false;
        }    
        
        return respuesta;
    }  
    //------------------------------------------------------------------------------------------------------------------  

    @FXML
    private RadioButton rbCorrecta4;

    @FXML
    private RadioButton rbCorrecta3;

    @FXML
    private RadioButton rbCorrecta2;

    @FXML
    private ToggleGroup categoria;

    @FXML
    private TextField jtNivelJugador;

    @FXML
    private TextField jtPuntajeJugador;

    @FXML
    private TextField jtPreguntaRonda;

    @FXML
    private TextField jtRespuesta1Ronda;
   
    @FXML
    private TextField jtRespuesta2Ronda;

    @FXML
    private TextField jtRespuesta3Ronda;

    @FXML
    private TextField jtRespuesta4Ronda;

    @FXML
    private RadioButton rbR1;    

    @FXML
    private RadioButton rbR2;     
    
    @FXML
    private RadioButton rbR3;

    @FXML
    private RadioButton rbR4;

    @FXML
    private Button btnEnviar;

    @FXML
    private RadioButton rbCorrecta1;

    @FXML
    private RadioButton rbCategoria2;

    @FXML
    private RadioButton rbCategoria1;

    @FXML
    private RadioButton rbCategoria4;

    @FXML
    private RadioButton rbCategoria3;

    @FXML
    private ToggleGroup correcta;

    @FXML
    private RadioButton rbCategoria5;

    @FXML
    private TextField jtRespuesta1;

    @FXML
    private TextField jtRespuesta4;

    @FXML
    private TextField jtRespuesta2;

    @FXML
    private Button btnGuardarPregunta;
   
    @FXML
    private Button btnRetirarse;

    @FXML
    private TextField jtPregunta;

    @FXML
    private TextField jtRespuesta3;

    @FXML
    private TextField jtIdJugador;

    @FXML
    private RadioButton rbNo;
    
    @FXML
    private TextField jtNombreJugador;

    

    @FXML
    private Button btnIniciar;
    
    @FXML
    private RadioButton rbSi;

    @FXML
    void clic_Categoria(ActionEvent event) {

    }

    
    @FXML
    void clic_Correcta(ActionEvent event) {

    }
    //------------------------------------------------------------------------------------------------------------------
    @FXML
    void clicSi(ActionEvent event) {
        JOptionPane.showMessageDialog(null,"Ingrese su Nombre e Id y luego presione 'Iniciar'");
        limpiar();
        if(rbSi.isSelected()){
            jtNombreJugador.setEditable(true);
            jtIdJugador.setEditable(true);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    @FXML
    void clicNo(ActionEvent event) {
        JOptionPane.showMessageDialog(null,"Ingrese su Id y luego presione 'Iniciar'");
        limpiar();
        if(rbNo.isSelected()){
            jtNombreJugador.setEditable(false);
            jtIdJugador.setEditable(true);
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    @FXML
    void clicIniciar(ActionEvent event) {
        if(jtIdJugador.getText()==""){
            
            validarJugador();
        }
        
        else if(rbSi.isSelected() && jtNombreJugador.getText()==""){
            JOptionPane.showMessageDialog(null, "Debes ingresar los datos completos");
        }

        else {
            String idCaptado = jtIdJugador.getText();
            boolean respuesta = validarId(idCaptado);
            if(respuesta == false){
                JOptionPane.showMessageDialog(null, "Id incorrecto, ingrese un numero");
            }
            else {
                if(rbSi.isSelected()){
                    verificarRegistro();
                    if(resultadoId){
                        comprobarBancoPreguntas();

                        if(resultadoDb){

                            Jugador jugador = new Jugador(jtNombreJugador.getText(),Integer.parseInt(jtIdJugador.getText()),0,1,"Activo");
                            //------------------------------------------------------------------------------------------------
                            String url = "C:/Users/karlo/Documents/SOFKA/Reto Preg/dbJuegoPreguntas.db";
                            conexion = new Conexion(url);
                            conexion.conectar();
                            conexion.registrarJugador(jugador);
                            JOptionPane.showMessageDialog(null, "Buena suerte...");
                            jtNivelJugador.setText("1");
                            jtPuntajeJugador.setText("0");
                                                                  
                            jtNombreJugador.setEditable(false);
                            jtIdJugador.setEditable(false);
                            btnIniciar.setDisable(true);
    
                            mostrarPregunta(jugador.getRonda());
                            //------------------------------------------------------------------------------------------------
                        }
                    }
                }
                else{
                    consultarJugador(Integer.parseInt(jtIdJugador.getText()));
                    mostrarPregunta(Integer.parseInt(jtNivelJugador.getText()));
                }
            }
        }
      
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    void clic_btnEnviarRespuesta(ActionEvent event) {
        int opEscogida;
        if(rbR1.isSelected()==false && rbR2.isSelected()==false && rbR3.isSelected()==false && rbR4.isSelected()==false) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una respuesta");
        }
        else if(rbR1.isSelected()){
            opEscogida = 1;
            calificar(opEscogida,mostrada.getOpCorrecta());
            if(calificacion){

                JOptionPane.showMessageDialog(null,"Avanzas");
                jugadorActual.setRonda(jugadorActual.getRonda()+1);
                jugadorActual.setPuntos(jugadorActual.getPuntos()+mostrada.getValor());    
                jtPuntajeJugador.setText(String.valueOf(jugadorActual.getPuntos()));
                jtNivelJugador.setText(String.valueOf(jugadorActual.getRonda()));
                   
                mostrarPregunta(jugadorActual.getRonda());  
           
            }
            else{
                JOptionPane.showMessageDialog(null,"Pierdes");
                jugadorActual.setPuntos(0);
            }   
        }
        else if (rbR2.isSelected()){
            opEscogida = 2;
            calificar(opEscogida,mostrada.getOpCorrecta());
            if(calificacion){
                               
               JOptionPane.showMessageDialog(null,"Avanzas");
                jugadorActual.setRonda(jugadorActual.getRonda()+1);
                jugadorActual.setPuntos(jugadorActual.getPuntos()+mostrada.getValor());
                jtPuntajeJugador.setText(String.valueOf(jugadorActual.getPuntos()));
                jtNivelJugador.setText(String.valueOf(jugadorActual.getRonda()));
                
                mostrarPregunta(jugadorActual.getRonda()); 
            }
            else{
                JOptionPane.showMessageDialog(null,"Pierdes");
                jugadorActual.setPuntos(0);
            }  
        }
        else if (rbR3.isSelected()){
            opEscogida = 3;
            calificar(opEscogida,mostrada.getOpCorrecta());
            if(calificacion){
                            
                JOptionPane.showMessageDialog(null,"Avanzas");
                jugadorActual.setRonda(jugadorActual.getRonda()+1);
                jugadorActual.setPuntos(jugadorActual.getPuntos()+mostrada.getValor());
                jtPuntajeJugador.setText(String.valueOf(jugadorActual.getPuntos()));
                jtNivelJugador.setText(String.valueOf(jugadorActual.getRonda()));
                
                mostrarPregunta(jugadorActual.getRonda());  
            }
            else{
                JOptionPane.showMessageDialog(null,"Pierdes");
                jugadorActual.setPuntos(0);
            }  
        }
        else if (rbR4.isSelected()){
            opEscogida = 4;
            calificar(opEscogida,mostrada.getOpCorrecta());
            if(calificacion){
                               
                JOptionPane.showMessageDialog(null,"Avanzas");
                jugadorActual.setRonda(jugadorActual.getRonda()+1);
                jugadorActual.setPuntos(jugadorActual.getPuntos()+mostrada.getValor());
                jtPuntajeJugador.setText(String.valueOf(jugadorActual.getPuntos()));
                jtNivelJugador.setText(String.valueOf(jugadorActual.getRonda()));
                
                mostrarPregunta(jugadorActual.getRonda());  
            }
            else{
                JOptionPane.showMessageDialog(null,"Pierdes");
                jugadorActual.setPuntos(0);
            }  
        }
  
    }
    
    @FXML
    void clic_btnRetirarse(ActionEvent event) {
        if(jtPreguntaRonda.getText()==""){
            JOptionPane.showMessageDialog(null, "Gracias por participar");
            System.exit(0);
        }
        else{
            int ptraidos = jugadorActual.getPuntos();
            JOptionPane.showMessageDialog(null,"Gracias por participar!\n"+"Puntos finales: " + ptraidos);
        
            System.exit(0); 
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------
    @FXML
    void clic_btnGuardarPregunta(ActionEvent event) {
       if(validarPregunta()){
            int valorPreg = cat*10;

            Pregunta pregunta = new Pregunta(jtPregunta.getText(), jtRespuesta1.getText(), jtRespuesta2.getText(), jtRespuesta3.getText(), jtRespuesta4.getText(), cat, correct, valorPreg);
            
            //-------------------------------------------------------------------------------------------------------------------
            String url = "C:/Users/karlo/Documents/SOFKA/Reto Preg/dbJuegoPreguntas.db";
            conexion = new Conexion(url);
            conexion.conectar();
            conexion.insertar(pregunta);
            JOptionPane.showMessageDialog(null, "Pregunta Registrada");
            limpiar();
            //------------------------------------------------------------------------------------------------------------------
        }
   }
}
