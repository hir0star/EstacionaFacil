package view;
import javax.swing.*;
import java.awt.*;
public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        super("EstacionaFácil"); setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(0,1,10,10)); p.setBorder(BorderFactory.createEmptyBorder(25,45,25,45));
        JLabel titulo = new JLabel("EstacionaFácil", SwingConstants.CENTER); titulo.setFont(new Font("SansSerif",Font.BOLD,28)); p.add(titulo);
        p.add(new JLabel("Sistema de gestão de satisfação",SwingConstants.CENTER));
        JButton cadastro = new JButton("Cadastrar avaliação"), painel = new JButton("Painel gerencial"), sair = new JButton("Sair");
        cadastro.addActionListener(e -> new TelaCadastro().setVisible(true)); painel.addActionListener(e -> new TelaGerencial().setVisible(true)); sair.addActionListener(e -> dispose());
        p.add(cadastro); p.add(painel); p.add(sair); add(p); setSize(440,330); setLocationRelativeTo(null);
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true)); }
}
