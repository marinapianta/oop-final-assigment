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

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(double valorDiario) {
        this.valorDiario = valorDiario;
    }

    public abstract double calculaLocacao(int dias);

    @Override
    public String toString() {
        return "Robo{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", valorDiario=" + valorDiario +
                '}';
    }
}
