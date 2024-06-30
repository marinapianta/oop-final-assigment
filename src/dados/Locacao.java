package src.dados;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Locacao {
    private int numero;
    private Status situacao;
    private Date dataInicio;
    private Date dataFim;
    private List<Robo> robos;
    private Cliente cliente;

    public Locacao(int numero, Status situacao, Date dataInicio, Date dataFim, Cliente cliente, List<Robo> osRobos) {
        this.numero = numero;
        this.situacao = situacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.robos = robos;
        this.cliente = this.cliente;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Status getSituacao() {
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

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Robo> getRobos() {
        return robos;
    }

    public void setRobos(List<Robo> robos) {
        this.robos = robos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public long getDias() {
        long diffInMillies = Math.abs(dataFim.getTime() - dataInicio.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
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
}


