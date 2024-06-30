package src.aplicacao;

import java.io.*;
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

        while (!locacoesPendentes.isEmpty()) {
            Locacao locacao = locacoesPendentes.poll();
            List<Robo> robosAlocados = new ArrayList<>();
            boolean allAvailable = true;

            for (Robo robo : locacao.getRobos()) {
                if (robo instanceof Agricola) {
                    Agricola agricola = (Agricola) robo;
                    if (!"disponivel".equals(agricola.getUso())) {
                        allAvailable = false;
                        break;
                    } else {
                        robosAlocados.add(agricola);
                    }
                } else {
                    robosAlocados.add(robo);
                }
            }

            if (allAvailable) {
                for (Robo robo : robosAlocados) {
                    if (robo instanceof Agricola) {
                        Agricola agricola = (Agricola) robo;
                        agricola.setUso("indisponivel");
                    }
                }
                locacao.setSituacao(Status.EXECUTANDO);
            } else {
                for (Robo robo : robosAlocados) {
                    if (robo instanceof Agricola) {
                        Agricola agricola = (Agricola) robo;
                        agricola.setUso("disponivel");
                    }
                }
                locacoesPendentes.add(locacao);
            }
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

    public void consultarLocacoes() {
        if (locacoes.isEmpty()) {
            System.out.println("Erro: Não há locações cadastradas.");
            return;
        }

        for (Locacao locacao : locacoes) {
            System.out.println(locacao);
            Cliente cliente = locacao.getCliente();
            System.out.println("Cliente: " + cliente);

            if (!locacao.getRobos().isEmpty()) {
                System.out.println("Robôs alocados:");
                for (Robo robo : locacao.getRobos()) {
                    System.out.println(robo);
                }
                System.out.println("Valor final da locação: " + locacao.calculaValorFinal());
            }
        }
    }

    public void alterarSituacaoLocacao(int numeroLocacao, Status novaSituacao) {
        Locacao locacao = null;
        for (Locacao l : locacoes) {
            if (l.getNumero() == numeroLocacao) {
                locacao = l;
                break;
            }
        }

        if (locacao == null) {
            System.out.println("Erro: Não há locação com o número indicado.");
            return;
        }

        if (locacao.getSituacao() == Status.FINALIZADA || locacao.getSituacao() == Status.CANCELADA) {
            System.out.println("Erro: A situação da locação não pode ser alterada.");
            return;
        }

        locacao.setSituacao(novaSituacao);
        System.out.println("Situação da locação alterada com sucesso.");
    }

    public void realizarCargaDeDadosIniciais(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo + ".txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Parse and load data from file
                // Example of parsing code
                // String[] dados = linha.split(",");
                // Parse and add data to respective lists
            }
            System.out.println("Dados carregados com sucesso.");

            mostrarRelatorioGeral();
        } catch (IOException e) {
            System.out.println("Erro: Problema na carga de dados.");
        }
    }

    public void salvarDados(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo + ".txt"))) {
            for (Robo robo : robos) {
                writer.println(robo);
            }
            for (Cliente cliente : clientes) {
                writer.println(cliente);
            }
            for (Locacao locacao : locacoes) {
                writer.println(locacao);
            }
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro: Problema no salvamento de dados.");
        }
    }

    public void carregarDados(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo + ".txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Parse and load data from file
                // Example of parsing code
                // String[] dados = linha.split(",");
                // Parse and add data to respective lists
            }
            System.out.println("Dados carregados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro: Problema no carregamento de dados.");
        }
    }

    public void finalizarSistema() {
        System.out.println("Sistema finalizado.");
        System.exit(0);
    }

}
