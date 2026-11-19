package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import dao.AvaliacaoDAO;
import service.Indicadores;
public class TelaGerencial extends JFrame {
    private final JTextField inicio = new JTextField("2000-01-01"), fim = new JTextField(LocalDate.now().toString());
    private final JComboBox<String> contrato = new JComboBox<>(new String[]{"Todos","Avulso","Semanal","Mensal"}), veiculo = new JComboBox<>(new String[]{"Todos","Carro pequeno","Carro grande","Moto"});
    private final JSpinner limite = new JSpinner(new SpinnerNumberModel(60,0,100,5));
    private final JLabel resumo = new JLabel("Clique em Atualizar para consultar.");
    private final DefaultTableModel tabela = new DefaultTableModel(new String[]{"Serviço","Total","Positivas","Negativas","Neutras","Satisfação","Alerta"},0) { public boolean isCellEditable(int r,int c){return false;} };
    public TelaGerencial() {
        super("Painel gerencial"); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel p = new JPanel(new BorderLayout(10,10)); p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JPanel filtros = new JPanel(new GridLayout(3,4,5,5)); filtros.add(new JLabel("Início (AAAA-MM-DD)")); filtros.add(inicio); filtros.add(new JLabel("Fim (AAAA-MM-DD)")); filtros.add(fim); filtros.add(new JLabel("Contrato")); filtros.add(contrato); filtros.add(new JLabel("Veículo")); filtros.add(veiculo); filtros.add(new JLabel("Limite (%)")); filtros.add(limite); JButton atualizar = new JButton("Atualizar"); filtros.add(atualizar); p.add(filtros,BorderLayout.NORTH);
        p.add(new JScrollPane(new JTable(tabela))); p.add(resumo,BorderLayout.SOUTH); atualizar.addActionListener(e -> atualizar()); limite.addChangeListener(e -> atualizar()); add(p); setSize(950,440); setLocationRelativeTo(null); atualizar();
    }
    private void atualizar() {
        try {
            LocalDate de = LocalDate.parse(inicio.getText().trim()), ate = LocalDate.parse(fim.getText().trim());
            if (de.isAfter(ate)) throw new IllegalArgumentException("A data inicial deve ser anterior à final.");
            var lista = new AvaliacaoDAO().consultar(de,ate,contrato.getSelectedItem().toString(),veiculo.getSelectedItem().toString());
            tabela.setRowCount(0); int total=0, positivos=0;
            for (var r: lista) {
                double taxa = Indicadores.satisfacao(r.positivos(),r.total()); total+=r.total(); positivos+=r.positivos();
                tabela.addRow(new Object[]{r.categoria(),r.total(),r.positivos(),r.negativos(),r.total()-r.positivos()-r.negativos(),String.format("%.1f%%",taxa),taxa<(Integer)limite.getValue()?"Abaixo do limite":"OK"});
            }
            resumo.setText("Total: "+total+" | Satisfação geral: "+(total==0?"Sem dados":String.format("%.1f%%",Indicadores.satisfacao(positivos,total))));
        } catch(Exception e) { tabela.setRowCount(0); resumo.setText("Consulta indisponível"); JOptionPane.showMessageDialog(this,"Erro: "+e.getMessage()); }
    }
}
