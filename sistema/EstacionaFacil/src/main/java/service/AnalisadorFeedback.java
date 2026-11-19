package service;
import java.text.Normalizer;
import java.util.Locale;
public class AnalisadorFeedback {
    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.toLowerCase(Locale.ROOT), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }
    public static String categoria(String texto) {
        String t = normalizar(texto);
        String[] nomes = {"Vaga avulsa", "Vaga coberta", "Lava-jato", "Estética", "Manobrista", "App/reserva", "Pernoite", "Contrato mensal"};
        String[] regras = {"avulsa|descoberta", "coberta", "lava|lavagem", "estetica|polimento", "manobrista", "app|aplicativo|reserva", "pernoite", "mensal|contrato"};
        for (int i = 0; i < regras.length; i++) {
            if (t.matches("(?s).*(" + regras[i] + ").*")) return nomes[i];
        }
        return "Não identificada";
    }
    public static String sentimento(String texto) {
        String t = normalizar(texto);
        if (t.matches("(?s).*(ruim|pessim|demora|grosseir|horrivel|problema|nao gostei|nao foi bom|nao e bom|caro|atraso).*") ) return "Negativo";
        if (t.matches("(?s).*(excelente|otim|\\bbom\\b|\\bboa\\b|adorei|gostei|rapido).*") ) return "Positivo";
        return "Neutro";
    }
}
