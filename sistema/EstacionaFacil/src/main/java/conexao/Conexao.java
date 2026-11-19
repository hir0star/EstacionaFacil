package conexao;
import java.sql.*;
public class Conexao {
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/estacionafacil", "root", "");
    }
}
