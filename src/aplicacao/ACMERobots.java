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
                String[] dados = linha.split(",");
                switch (dados[0]) {
                    case "R":
                        if (dados[1].equalsIgnoreCase("Agricola")) {
                            Robo agricola = new Agricola(
                                    Integer.parseInt(dados[2]),
                                    dados[3],
                                    Double.parseDouble(dados[4]),
                                    Double.parseDouble(dados[5]),
                                    dados[6]
                            );
                            cadastrarRobo(agricola);
                        } else if (dados[1].equalsIgnoreCase("Domestico")) {
                            Robo domestico = new Domestico(
                                    Integer.parseInt(dados[2]),
                                    dados[3],
                                    Double.parseDouble(dados[4]),
                                    Integer.parseInt(dados[5])
                            );
                            cadastrarRobo(domestico);
                        } else if (dados[1].equalsIgnoreCase("Industrial")) {
                            Robo industrial = new Industrial(
                                    Integer.parseInt(dados[2]),
                                    dados[3],
                                    Double.parseDouble(dados[4]),
                                    dados[5]
                            );
                            cadastrarRobo(industrial);
                        }
                        break;
                    case "C":
                        if (dados[1].equalsIgnoreCase("Empresarial")) {
                            Cliente empresarial = new Empresarial(
                                    Integer.parseInt(dados[2]),
                                    dados[3],
                                    Integer.parseInt(dados[4])
                            );
                            cadastrarCliente(empresarial);
                        } else if (dados[1].equalsIgnoreCase("Individual")) {
                            Cliente individual = new Individual(
                                    Integer.parseInt(dados[2]),
                                    dados[3],
                                    dados[4]
                            );
                            cadastrarCliente(individual);
                        }
                        break;
                    case "L":
                        int numero = Integer.parseInt(dados[2]);
                        Cliente cliente = null;
                        for (Cliente c : clientes) {
                            if (c.getCodigo() == Integer.parseInt(dados[3])) {
                                cliente = c;
                                break;
                            }
                        }
                        if (cliente == null) {
                            System.out.println("Erro: Cliente não encontrado.");
                            continue;
                        }
                        Date dataInicio = new Date(Long.parseLong(dados[4]));
                        Date dataFim = new Date(Long.parseLong(dados[5]));
                        List<Robo> robosLocacao = new ArrayList<>();
                        for (int i = 6; i < dados.length; i++) {
                            int idRobo = Integer.parseInt(dados[i]);
                            Robo robo = null;
                            for (Robo r : robos) {
                                if (r.getId() == idRobo) {
                                    robo = r;
                                    break;
                                }
                            }
                            if (robo != null) {
                                robosLocacao.add(robo);
                            } else {
                                System.out.println("Erro: Robô com id " + idRobo + " não encontrado.");
                            }
                        }
                        Locacao locacao = new Locacao(numero, Status.CADASTRADA, dataInicio, dataFim);
                        locacao.setCliente(cliente);
                        locacao.setRobos(robosLocacao);
                        cadastrarLocacao(locacao);
                        break;
                }
            }
            System.out.println("Dados carregados com sucesso.");
            mostrarRelatorioGeral();
        } catch (IOException e) {
            System.out.println("Erro: Problema na carga de dados.");
            e.printStackTrace();
        }
    }

    public void salvarDados(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo + ".txt"))) {
            for (Robo robo : robos) {
                if (robo instanceof Agricola) {
                    Agricola agricola = (Agricola) robo;
                    writer.printf("R,Agricola,%d,%s,%.2f,%.2f,%s\n",
                            agricola.getId(), agricola.getModelo(), agricola.getValorDiario(),
                            agricola.getArea(), agricola.getUso());
                } else if (robo instanceof Domestico) {
                    Domestico domestico = (Domestico) robo;
                    writer.printf("R,Domestico,%d,%s,%.2f,%d\n",
                            domestico.getId(), domestico.getModelo(), domestico.getValorDiario(),
                            domestico.getNivel());
                } else if (robo instanceof Industrial) {
                    Industrial industrial = (Industrial) robo;
                    writer.printf("R,Industrial,%d,%s,%.2f,%s\n",
                            industrial.getId(), industrial.getModelo(), industrial.getValorDiario(),
                            industrial.getSetor());
                }
            }
            for (Cliente cliente : clientes) {
                if (cliente instanceof Empresarial) {
                    Empresarial empresarial = (Empresarial) cliente;
                    writer.printf("C,Empresarial,%d,%s,%d\n",
                            empresarial.getCodigo(), empresarial.getNome(), empresarial.getAno());
                } else if (cliente instanceof Individual) {
                    Individual individual = (Individual) cliente;
                    writer.printf("C,Individual,%d,%s,%s\n",
                            individual.getCodigo(), individual.getNome(), individual.getCpf());
                }
            }
            for (Locacao locacao : locacoes) {
                writer.printf("L,%d,%d,%d,%d,%d",
                        locacao.getNumero(), locacao.getCliente().getCodigo(),
                        locacao.getDataInicio().getTime(), locacao.getDataFim().getTime());
                for (Robo robo : locacao.getRobos()) {
                    writer.printf(",%d", robo.getId());
                }
                writer.println();
            }
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro: Problema no salvamento de dados.");
            e.printStackTrace();
        }
    }

    public void carregarDados(String nomeArquivo) {
        realizarCargaDeDadosIniciais(nomeArquivo);
    }

    public void finalizarSistema() {
        System.out.println("Sistema finalizado.");
        System.exit(0);
    }
}