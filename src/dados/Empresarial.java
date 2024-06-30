package src.dados;

public class Empresarial extends Cliente {
    private int ano;

    public Empresarial(int codigo, String nome, int ano) {
        super(codigo, nome, 2);
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public double calculaDesconto(int qtdRobos) {
        if (qtdRobos >= 2 && qtdRobos <= 9) {
            return 0.03;
        } else if (qtdRobos > 10) {
            return 0.07;
        }
        return 0.0;
    }
    @Override
    public String toString() {
        return "Empresarial{" +
                "ano=" + ano +
                "} " + super.toString();
    }
}
