package view;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.Avaliacao;
import dao.AvaliacaoDAO;
import service.AnalisadorFeedback;
public class TelaCadastro extends JFrame {
    private final JTextArea comentario = new JTextArea(8,35);
    private final JComboBox<String> contrato = new JComboBox<>(new String[]{"Avulso","Semanal","Mensal"});
    private final JComboBox<String> veiculo = new JComboBox<>(new String[]{"Carro pequeno","Carro grande","Moto"});
    public TelaCadastro() {
        super("Cadastro de avaliações"); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel p = new JPanel(new BorderLayout(10,10)); p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JPanel campos = new JPanel(new GridLayout(2,2,5,5)); campos.add(new JLabel("Contrato")); campos.add(contrato); campos.add(new JLabel("Veículo")); campos.add(veiculo); p.add(campos,BorderLayout.NORTH);
        comentario.setLineWrap(true); comentario.setWrapStyleWord(true); JScrollPane scroll = new JScrollPane(comentario); scroll.setBorder(BorderFactory.createTitledBorder("Comentário (uma avaliação por linha para lote)")); p.add(scroll);
        JPanel botoes = new JPanel(); JButton salvar = new JButton("Analisar e cadastrar"), lote = new JButton("Cadastrar lote"); botoes.add(salvar); botoes.add(lote); p.add(botoes,BorderLayout.SOUTH);
        salvar.addActionListener(e -> cadastrar(false)); lote.addActionListener(e -> cadastrar(true)); add(p); pack(); setLocationRelativeTo(null);
    }
    private void cadastrar(boolean lote) {
        String texto = comentario.getText().trim();
        if (texto.isEmpty()) { JOptionPane.showMessageDialog(this,"Digite um comentário."); return; }
        String[] linhas = lote ? texto.split("\\R") : new String[]{texto};
        ArrayList<Avaliacao> lista = new ArrayList<>();
        for (String linha : linhas) if (!linha.isBlank()) lista.add(new Avaliacao(linha,AnalisadorFeedback.categoria(linha),AnalisadorFeedback.sentimento(linha),contrato.getSelectedItem().toString(),veiculo.getSelectedItem().toString()));
        if (lista.size()>1000) { JOptionPane.showMessageDialog(this,"Limite de 1000 avaliações."); return; }
        try {
            new AvaliacaoDAO().salvar(lista); comentario.setText("");
            String mensagem = lista.size()+" avaliação(ões) cadastrada(s).";
            if (lista.size()==1) mensagem += "\nCategoria: "+lista.get(0).categoria()+"\nSentimento: "+lista.get(0).sentimento();
            JOptionPane.showMessageDialog(this,mensagem);
        } catch (Exception e) { JOptionPane.showMessageDialog(this,"Erro ao salvar: "+e.getMessage()); }
    }
}
