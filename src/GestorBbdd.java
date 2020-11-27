import java.sql.*;

public class GestorBbdd {

    private int sumaIngresos;
    private int contRegistros;
    private int idRegistro;

    public void lectura(){
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement consulta = conexion.createStatement();
            ResultSet registro = consulta.executeQuery("select email, ingresos from empleados");
            while(registro.next()) {
                sumaIngresos += registro.getInt("ingresos");
                contRegistros++;
            }
            conexion.close();
        }catch(SQLException e) {
            e.printStackTrace();

        }
    }

    public void lecturaConcurrente(int auxInicial, int auxFinal){
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement consulta = conexion.createStatement();
            ResultSet registro = consulta.executeQuery("select email, ingresos from empleados where id between " + auxInicial + " and " + auxFinal);
            while(registro.next()) {
                sumaIngresos += registro.getInt("ingresos");
                contRegistros++;
            }
            conexion.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSumaIngresos(){
        return sumaIngresos;
    }

    public int getNumeroRegistros(){
        return contRegistros;
    }

}
