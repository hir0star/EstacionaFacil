# EstacionaFácil
Projeto Java Swing (Java 17 ou superior) com Maven e MySQL.
1. Execute estacionafacil.sql no HeidiSQL conectado ao MySQL.
2. Abra esta pasta pelo File > Open Project no NetBeans.
3. Em conexao/Conexao.java, configure usuário e senha locais do MySQL. Não publique senhas reais.
4. Aguarde o Maven baixar o MySQL Connector/J (internet necessária na primeira execução).
5. Abra view/TelaPrincipal.java e escolha Run File (Shift+F6).
Cadastro: uma avaliação ou lote de até 1000 linhas com o mesmo contrato e veículo. No painel, selecione filtros e Atualizar. O limite dispara alertas visuais, não notificações externas.
Classificação por palavras-chave: pode errar comentários ambíguos, sarcasmo e múltiplos serviços. O limite não é persistido. As telas são Swing criadas por código, não pelo editor Design de JFrame Form. A conexão e o desempenho de 1000 avaliações/minuto precisam ser testados no MySQL local.
