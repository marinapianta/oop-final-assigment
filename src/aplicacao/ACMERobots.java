package src.aplicacao;
import java.util.*;
import src.dados.*;


public class ACMERobots {
    private List<Robo> robos;
    private List<Cliente> clientes;
    private Queue<Locacao> locacoesPendentes;
    private List<Locacao> locacoes;

    public ACMERobots() {
        this.robos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoesPendentes = new LinkedList<>();
        this.locacoes = new ArrayList<>();
    }

    public void cadastrarRobo(Robo robo) {
        for (Robo r : robos) {
            if (r.getId() == robo.getId()) {
                System.out.println("Erro: Robô com id " + robo.getId() + " já existe.");
                return;
            }
        }
        robos.add(robo);
        robos.sort(Comparator.comparingInt(Robo::getId));
        System.out.println("Robô cadastrado com sucesso!");
    }

    public void cadastrarCliente(Cliente cliente) {
        for (Cliente c : clientes) {
            if (c.getCodigo() == cliente.getCodigo()) {
                System.out.println("Erro: Cliente com código " + cliente.getCodigo() + " já existe.");
                return;
            }
        }
        clientes.add(cliente);
        clientes.sort(Comparator.comparingInt(Cliente::getCodigo));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void cadastrarLocacao(Locacao locacao) {
        for (Locacao l : locacoes) {
            if (l.getNumero() == locacao.getNumero()) {
                System.out.println("Erro: Locação com número " + locacao.getNumero() + " já existe.");
                return;
            }
        }
        locacoesPendentes.add(locacao);
        locacoes.add(locacao);
        System.out.println("Locação cadastrada com sucesso!");
    }

    public void processarLocacoes() {
        if (locacoesPendentes.isEmpty()) {
            System.out.println("Erro: Não há locações na fila de locações pendentes.");
            return;
        }

        Locacao locacao = locacoesPendentes.poll();
        boolean todosRobosDisponiveis = true;

        for (Robo robo : locacao.getRobos()) {
            // Verifica se o robô está disponível (a lógica de disponibilidade deve ser implementada)
            // Se não estiver disponível, marca todosRobosDisponiveis como false
        }

        if (todosRobosDisponiveis) {
            locacao.setSituacao(Status.EXECUTANDO);
            System.out.println("Locação " + locacao.getNumero() + " está agora EXECUTANDO.");
        } else {
            locacoesPendentes.add(locacao);
            System.out.println("Erro: Nem todos os robôs estão disponíveis. Locação " + locacao.getNumero() + " retornada para a fila de locações pendentes.");
        }
    }

    public void mostrarRelatorioGeral() {
        if (robos.isEmpty() && clientes.isEmpty() && locacoes.isEmpty()) {
            System.out.println("Erro: Não há dados cadastrados.");
            return;
        }

        System.out.println("Robôs cadastrados:");
        for (Robo robo : robos) {
            System.out.println(robo);
        }

        System.out.println("\nClientes cadastrados:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }

        System.out.println("\nLocações cadastradas:");
        for (Locacao locacao : locacoes) {
            System.out.println(locacao);
        }
    }
}