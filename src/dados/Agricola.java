package src.dados;

public class Agricola extends Robo{
    private double area;
    private String uso;

    public Agricola(int id, String modelo, double valorDiario, double area, String uso) {
        super(id, modelo, 3, valorDiario);
        this.area = area;
        this.uso = uso;
    }

    public double getArea() {
        return area;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    @Override
    public double calculaLocacao(int dias) {
        return dias * area * 10.00;
    }

    public String toCSV() {
        return getId() + "," + getModelo() + "," + getValorDiario() + ",3," + area + "," + uso;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Agricola \n" + "Área: " + area + "\n" + "Uso: " + uso;
    }
}