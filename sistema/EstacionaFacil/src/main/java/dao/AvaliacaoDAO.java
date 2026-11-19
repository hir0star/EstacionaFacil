package dao;
import conexao.Conexao;
import model.Avaliacao;
import java.sql.*;
import java.util.*;
public class AvaliacaoDAO {
    public record Resumo(String categoria, int total, int positivos, int negativos) {}
    public void salvar(List<Avaliacao> lista) throws SQLException {
        String sql = "INSERT INTO avaliacao (comentario,categoria,sentimento,contrato,veiculo) VALUES (?,?,?,?,?)";
        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql)) {
            c.setAutoCommit(false);
            try {
                for (Avaliacao a : lista) {
                    p.setString(1,a.comentario()); p.setString(2,a.categoria()); p.setString(3,a.sentimento());
                    p.setString(4,a.contrato()); p.setString(5,a.veiculo()); p.addBatch();
                }
                p.executeBatch(); c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }
    public List<Resumo> consultar(java.time.LocalDate inicio, java.time.LocalDate fim, String contrato, String veiculo) throws SQLException {
        String sql = "SELECT categoria, COUNT(*) total, SUM(sentimento='Positivo') positivos, SUM(sentimento='Negativo') negativos FROM avaliacao WHERE DATE(data_avaliacao) BETWEEN ? AND ? AND (?='Todos' OR contrato=?) AND (?='Todos' OR veiculo=?) GROUP BY categoria ORDER BY categoria";
        List<Resumo> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setDate(1,java.sql.Date.valueOf(inicio)); p.setDate(2,java.sql.Date.valueOf(fim));
            p.setString(3,contrato); p.setString(4,contrato); p.setString(5,veiculo); p.setString(6,veiculo);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) lista.add(new Resumo(r.getString(1),r.getInt(2),r.getInt(3),r.getInt(4)));
            }
        }
        return lista;
    }
}
