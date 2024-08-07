package src.dados;

public class Individual extends Cliente {
    private String cpf;

    public Individual(int codigo, String nome, String cpf) {
        super(codigo, nome, 1);
        this.cpf = cpf;
    }

    public double calculaDesconto(int qtdRobos) {
        if (qtdRobos > 1) {
            return 0.05;
        }
        return 0.0;
    }

    public String toCSV() {
        return getCodigo() + "," + getNome() + ",Individual," + cpf;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Individual \n" + "CPF: " + cpf;
    }
}