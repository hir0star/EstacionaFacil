package service;
public class Indicadores {
    public static double satisfacao(int positivos, int total) {
        return total == 0 ? 0 : 100.0 * positivos / total;
    }
}
