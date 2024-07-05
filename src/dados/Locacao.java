package src.dados;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class Locacao {
    private int numero;
    private Status situacao;
    private Date dataInicio;
    private Date dataFim;
    private List<Robo> robos;
    private Cliente cliente;

    public Locacao(int numero, Status situacao, Date dataInicio, Date dataFim, Cliente cliente, List<Robo> robos) {
        this.numero = numero;
        this.situacao = situacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.robos = (robos != null) ? robos : new ArrayList<>();
        this.cliente = cliente;
    }

    public int getNumero() {
        return numero;
    }

    public Status getSituacao() {
         situacao.getNome();
         return situacao;
    }

    public void setSituacao(Status situacao) {
        if ((this.situacao == Status.FINALIZADA || this.situacao == Status.CANCELADA) && situacao != this.situacao) {
            throw new IllegalStateException("Locação finalizada ou cancelada não pode mudar de situação");
        }
        this.situacao = situacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public List<Robo> getRobos() {
        return robos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public long getDias() {
        long diffInMillies = Math.abs(dataFim.getTime() - dataInicio.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(numero).append(",")
                .append(sdf.format(dataInicio)).append(",")
                .append(sdf.format(dataFim)).append(",")
                .append(cliente.getCodigo());

        for (Robo robo : robos) {
            sb.append(",").append(robo.getId());
        }
        return sb.toString();
    }

    public double calculaValorFinal() {
        long dias = getDias();
        double total = 0.0;
        for (Robo robo : robos) {
            total += robo.calculaLocacao((int) dias);
        }
        double desconto = cliente.calculaDesconto(robos.size());
        return total * (1 - desconto);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Número: ").append(numero).append("\n")
                .append("Situação: ").append(situacao.name().charAt(0) + situacao.name().substring(1).toLowerCase()).append("\n")
                .append("Data Inicial: ").append(formatDate(dataInicio)).append("\n")
                .append("Data Final: ").append(formatDate(dataFim)).append("\n")
                .append("\nRobôs:\n");
        for (Robo robo : robos) {
            sb.append(robo).append("\n");
        }
        sb.append("\nCliente:\n").append(cliente);
        return sb.toString();
    }
}