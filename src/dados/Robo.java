package src.dados;

public abstract class Robo {
    private int id;
    private int tipo;
    private String modelo;
    private double valorDiario;

    public Robo(int id, String modelo, int tipo, double valorDiario) {
        this.id = id;
        this.modelo = modelo;
        this.valorDiario = valorDiario;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public abstract double calculaLocacao(int dias);

    public abstract String toCSV();

    @Override
    public String toString() {
        return "ID: " + id + "\n" + "Modelo: " + modelo + "\n" + "Valor Diário: " + valorDiario;
    }
}
