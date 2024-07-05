package src.aplicacao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import src.dados.*;

public class ACMERobots {
    private List<Robo> robos;
    private List<Cliente> clientes;
    private Queue<Locacao> locacoesPendentes;
    private List<Locacao> locacoes;
    private String nomeArquivoClientes;
    private String nomeArquivoRobos;
    private String nomeArquivoLocacoes;


    public ACMERobots() {
        this.robos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoesPendentes = new LinkedList<>();
        this.locacoes = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Robo> getRobos() {
        return robos;
    }

    public void cadastrarRobo(Robo robo) {
        for (Robo r : robos) {
            if (r.getId() == robo.getId()) {
                System.out.println("Erro: Robô com id " + robo.getId() + " já existe.");
                return;
            }
        }

        switch (robo.getTipo()) {
            case 1: // Domestico
                if (robo instanceof Domestico) {
                    Domestico domestico = (Domestico) robo;
                    if (domestico.getNivel() <= 0) {
                        System.out.println("Erro: Nível inválido para robô doméstico.");
                        return;
                    }
                }
                break;
            case 2: // Industrial
                if (robo instanceof Industrial) {
                    Industrial industrial = (Industrial) robo;
                    if (industrial.getSetor() == null || industrial.getSetor().isEmpty()) {
                        System.out.println("Erro: Setor inválido para robô industrial.");
                        return;
                    }
                }
                break;
            case 3: // Agricola
                if (robo instanceof Agricola) {
                    Agricola agricola = (Agricola) robo;
                    if (agricola.getArea() <= 0) {
                        System.out.println("Erro: Área inválida para robô agrícola.");
                        return;
                    }
                    if (agricola.getUso() == null || agricola.getUso().isEmpty()) {
                        System.out.println("Erro: Uso inválido para robô agrícola.");
                        return;
                    }
                }
                break;
            default:
                System.out.println("Erro: Tipo de robô desconhecido.");
                return;
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

    public String mostrarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();

        if (robos.isEmpty() && clientes.isEmpty() && locacoes.isEmpty()) {
            return "Erro: Não há dados cadastrados.";
        }

        if (!robos.isEmpty()) {
            relatorio.append("Robôs cadastrados:\n");
            for (Robo robo : robos) {
                relatorio.append(robo).append("\n\n");
            }
        } else {
            relatorio.append("Não há robôs cadastrados.\n");
        }

        if (!clientes.isEmpty()) {
            relatorio.append("\nClientes cadastrados:\n");
            for (Cliente cliente : clientes) {
                relatorio.append(cliente).append("\n\n");
            }
        } else {
            relatorio.append("Não há clientes cadastrados.\n");
        }

        if (!locacoes.isEmpty()) {
            relatorio.append("\nLocações cadastradas:\n");
            for (Locacao locacao : locacoes) {
                relatorio.append(locacao).append("\n\n");
            }
        } else {
            relatorio.append("Não há locações cadastradas.\n");
        }

        return relatorio.toString();
    }

    public String consultarLocacoes() {
        StringBuilder consulta = new StringBuilder();

        if (locacoes.isEmpty()) {
            return "Erro: Não há locações cadastradas.";
        }

        for (Locacao locacao : locacoes) {
            consulta.append(locacao).append("\n");
            Cliente cliente = locacao.getCliente();
            consulta.append("Cliente: ").append(cliente).append("\n");

            if (!locacao.getRobos().isEmpty()) {
                consulta.append("Robôs alocados:\n");
                for (Robo robo : locacao.getRobos()) {
                    consulta.append(robo).append("\n");
                }
                consulta.append("Valor final da locação: ").append(locacao.calculaValorFinal()).append("\n");
            }
        }

        return consulta.toString();
    }

    public void alterarSituacaoLocacao(int numeroLocacao, String novaSituacaoStr) {
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

        try {
            Status novaSituacao = Status.valueOf(novaSituacaoStr.toUpperCase());
            locacao.setSituacao(novaSituacao);
            System.out.println("Situação da locação alterada com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Situação inválida. Use uma das seguintes opções: " + Arrays.toString(Status.values()));
        }
    }

    public void realizarCargaDeDadosIniciais() {
        try {
            carregarClientes();
            carregarRobos();
            carregarLocacoes();
            System.out.println("Carga de dados iniciais realizada com sucesso.");
            System.out.println(mostrarRelatorioGeral());
        } catch (IOException | ParseException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public void salvarDados() {
        try {
            salvarClientes(nomeArquivoClientes);
            salvarRobos(nomeArquivoRobos);
            salvarLocacoes(nomeArquivoLocacoes);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void setNomeArquivos(String nomeArquivoClientes, String nomeArquivoRobos, String nomeArquivoLocacoes) {
        this.nomeArquivoClientes = nomeArquivoClientes;
        this.nomeArquivoRobos = nomeArquivoRobos;
        this.nomeArquivoLocacoes = nomeArquivoLocacoes;
    }

    private void carregarClientes() throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(nomeArquivoClientes));
        for (String linha : linhas) {
            String[] dados = linha.split(",");
            int codigo = Integer.parseInt(dados[0]);
            String nome = dados[1];
            String tipo = dados[2];
            if (tipo.equals("Individual")) {
                String cpf = dados[3];
                clientes.add(new Individual(codigo, nome, cpf));
            } else {
                int ano = Integer.parseInt(dados[3]);
                clientes.add(new Empresarial(codigo, nome, ano));
            }
        }
    }

    private void carregarRobos() throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(nomeArquivoRobos));
        for (String linha : linhas) {
            String[] dados = linha.split(",");
            int id = Integer.parseInt(dados[0]);
            String modelo = dados[1];
            double valorDiario = Double.parseDouble(dados[2]);
            int tipo = Integer.parseInt(dados[3]);
            switch (tipo) {
                case 1:
                    int nivel = Integer.parseInt(dados[4]);
                    robos.add(new Domestico(id, modelo, valorDiario, nivel));
                    break;
                case 2:
                    String setor = dados[4];
                    robos.add(new Industrial(id, modelo, valorDiario, setor));
                    break;
                case 3:
                    double area = Double.parseDouble(dados[4]);
                    String uso = dados[5];
                    robos.add(new Agricola(id, modelo, valorDiario, area, uso));
                    break;
            }
        }
    }

    private void carregarLocacoes() throws IOException, ParseException {
        List<String> linhas = Files.readAllLines(Paths.get(nomeArquivoLocacoes));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (String linha : linhas) {
            String[] dados = linha.split(",");
            int numero = Integer.parseInt(dados[0]);
            Date dataInicio = sdf.parse(dados[1]);
            Date dataFim = sdf.parse(dados[2]);
            int codigoCliente = Integer.parseInt(dados[3]);
            Cliente cliente = clientes.stream().filter(c -> c.getCodigo() == codigoCliente).findFirst().orElse(null);
            if (cliente != null) {
                List<Robo> robosLocados = new ArrayList<>();
                for (int i = 4; i < dados.length; i++) {
                    int idRobo = Integer.parseInt(dados[i]);
                    Robo robo = robos.stream().filter(r -> r.getId() == idRobo).findFirst().orElse(null);
                    if (robo != null) {
                        robosLocados.add(robo);
                    }
                }
                Locacao locacao = new Locacao(numero, Status.CADASTRADA, dataInicio, dataFim, cliente, robosLocados);
                locacoes.add(locacao);
                locacoesPendentes.add(locacao);
            }
        }
    }

    private void salvarClientes(String nomeArquivo) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo));
        for (Cliente cliente : clientes) {
            writer.write(cliente.toCSV());
            writer.newLine();
        }
        writer.close();
    }

    private void salvarRobos(String nomeArquivo) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo));
        for (Robo robo : robos) {
            writer.write(robo.toCSV());
            writer.newLine();
        }
        writer.close();
    }

    private void salvarLocacoes(String nomeArquivo) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo));
        for (Locacao locacao : locacoes) {
            writer.write(locacao.toCSV());
            writer.newLine();
        }
        writer.close();
    }

    public void finalizarSistema() {
        System.out.println("Sistema finalizado.");
        System.exit(0);
    }
}