package src.dados;

public class Industrial extends Robo {
    private String setor;

    public Industrial(int id, String modelo, double valorDiario, String setor) {
        super(id, modelo, 2, valorDiario);
        this.setor = setor;
    }

    public String getSetor() {
        return setor;
    }

    @Override
    public double calculaLocacao(int dias) {
        return dias * 90.00;
    }

    @Override
    public String toCSV() {
        return getId() + "," + getModelo() + "," + getValorDiario() + ",2," + setor;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Industrial \n" + "Setor: " + setor;
    }
}
