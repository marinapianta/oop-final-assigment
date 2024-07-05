package src.dados;

public abstract class Cliente {
    private int tipo;
    private int codigo;
    private String nome;

    public Cliente(int codigo, String nome, int tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public abstract double calculaDesconto(int qtdRobos);

    public abstract String toCSV();

    @Override
    public String toString() {
        return "Codigo: " + codigo + "\n" + "Nome:" + nome;
    }
}
