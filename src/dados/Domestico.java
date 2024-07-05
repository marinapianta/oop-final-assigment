package src.dados;

public class Domestico extends Robo {
    private int nivel;

    public Domestico(int id, String modelo, double valorDiario, int nivel) {
        super(id, modelo, 1, valorDiario);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public double calculaLocacao(int dias) {
        double valorNivel;
        switch (nivel) {
            case 1:
                valorNivel = 10.00;
                break;
            case 2:
                valorNivel = 20.00;
                break;
            case 3:
                valorNivel = 50.00;
                break;
            default:
                throw new IllegalArgumentException("Nível inválido: " + nivel);
        }
        return dias * valorNivel;
    }

    @Override
    public String toCSV() {
        return getId() + "," + getModelo() + "," + getValorDiario() + ",1," + nivel;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Domestico \n" + "Nivel: " + nivel;
    }
}
