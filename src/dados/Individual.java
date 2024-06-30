package src.dados;

public class Individual extends Cliente {
    private String cpf;

    public Individual(int codigo, String nome, String cpf) {
        super(codigo, nome, 1);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double calculaDesconto(int qtdRobos) {
        if (qtdRobos > 1) {
            return 0.05;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "cpf='" + cpf + '\'' +
                "} " + super.toString();
    }
}
