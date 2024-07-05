package src.aplicacao;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.io.IOException;

import src.dados.*;

public class ACMERobotsGUI extends JFrame {
    private ACMERobots acmeRobots;
    private JTextArea outputArea;
    private JComboBox<String> fileFormatComboBox;

    public ACMERobotsGUI() {
        acmeRobots = new ACMERobots();
        setTitle("ACME Robots Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(10, 1));
        add(mainPanel, BorderLayout.NORTH);

        JButton btnCadastrarRobo = new JButton("Cadastrar Novo Robô");
        JButton btnCadastrarCliente = new JButton("Cadastrar Novo Cliente");
        JButton btnCadastrarLocacao = new JButton("Cadastrar Nova Locação");
        JButton btnProcessarLocacoes = new JButton("Processar Locações");
        JButton btnMostrarRelatorio = new JButton("Mostrar Relatório Geral");
        JButton btnConsultarLocacoes = new JButton("Consultar Todas as Locações");
        JButton btnAlterarSituacao = new JButton("Alterar Situação de Locação");
        JButton btnCargaDados = new JButton("Realizar Carga de Dados Iniciais");
        JButton btnSalvarDados = new JButton("Salvar Dados");
        JButton btnFinalizarSistema = new JButton("Finalizar Sistema");

        mainPanel.add(btnCadastrarRobo);
        mainPanel.add(btnCadastrarCliente);
        mainPanel.add(btnCadastrarLocacao);
        mainPanel.add(btnProcessarLocacoes);
        mainPanel.add(btnMostrarRelatorio);
        mainPanel.add(btnConsultarLocacoes);
        mainPanel.add(btnAlterarSituacao);
        mainPanel.add(btnCargaDados);
        mainPanel.add(btnSalvarDados);
        mainPanel.add(btnFinalizarSistema);

        // ComboBox for file format selection
        fileFormatComboBox = new JComboBox<>(new String[]{"CSV"});
        mainPanel.add(new JLabel("Formato de Arquivo:"));
        mainPanel.add(fileFormatComboBox);

        // Action Listeners for each button
        btnCadastrarRobo.addActionListener(e -> cadastrarRobo());
        btnCadastrarCliente.addActionListener(e -> cadastrarCliente());
        btnCadastrarLocacao.addActionListener(e -> cadastrarLocacao());
        btnProcessarLocacoes.addActionListener(e -> processarLocacoes());
        btnMostrarRelatorio.addActionListener(e -> mostrarRelatorioGeral());
        btnConsultarLocacoes.addActionListener(e -> consultarLocacoes());
        btnAlterarSituacao.addActionListener(e -> alterarSituacaoLocacao());
        btnCargaDados.addActionListener(e -> realizarCargaDeDadosIniciais());
        btnSalvarDados.addActionListener(e -> salvarDados());
        btnFinalizarSistema.addActionListener(e -> finalizarSistema());

        // Create the output area
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cadastrarRobo() {
        JTextField idField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField valorDiarioField = new JTextField();
        JTextField tipoField = new JTextField();
        JTextField atributoField = new JTextField();
        JTextField usoField = new JTextField();

        Object[] fields = {
                "ID:", idField,
                "Modelo:", modeloField,
                "Valor Diário:", valorDiarioField,
                "Tipo (1 - Domestico, 2 - Industrial, 3 - Agricola):", tipoField,
                "Atributo (nível para Domestico, setor para Industrial, área para Agricola):", atributoField,
                "Uso (apenas para Agricola):", usoField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Cadastrar Robô", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String modelo = modeloField.getText();
                double valorDiario = Double.parseDouble(valorDiarioField.getText());
                int tipo = Integer.parseInt(tipoField.getText());
                String atributo = atributoField.getText();
                String uso = usoField.getText();

                Robo robo;
                switch (tipo) {
                    case 1: // Domestico
                        int nivel = Integer.parseInt(atributo);
                        robo = new Domestico(id, modelo, valorDiario, nivel);
                        break;
                    case 2: // Industrial
                        String setor = atributo;
                        robo = new Industrial(id, modelo, valorDiario, setor);
                        break;
                    case 3: // Agricola
                        double area = Double.parseDouble(atributo);
                        robo = new Agricola(id, modelo, valorDiario, area, uso);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Tipo de robô inválido!");
                        return;
                }

                acmeRobots.cadastrarRobo(robo);
                appendOutput("Robô cadastrado com sucesso.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Formato de número inválido!");
            }
        }
    }

    private void cadastrarCliente() {
        JTextField codigoField = new JTextField();
        JTextField nomeField = new JTextField();
        JTextField tipoField = new JTextField();
        JTextField atributoField = new JTextField();

        Object[] fields = {
                "Código:", codigoField,
                "Nome:", nomeField,
                "Tipo (1 - Individual, 2 - Empresarial):", tipoField,
                "Atributo (CPF para Individual, Ano para Empresarial):", atributoField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Cadastrar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int codigo = Integer.parseInt(codigoField.getText());
                String nome = nomeField.getText();
                int tipo = Integer.parseInt(tipoField.getText());
                String atributo = atributoField.getText();

                Cliente cliente;
                if (tipo == 1) {
                    cliente = new Individual(codigo, nome, atributo);
                } else if (tipo == 2) {
                    int ano = Integer.parseInt(atributo);
                    cliente = new Empresarial(codigo, nome, ano);
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de cliente inválido!");
                    return;
                }

                acmeRobots.cadastrarCliente(cliente);
                appendOutput("Cliente cadastrado com sucesso.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Formato de número inválido!");
            }
        }
    }

    private void cadastrarLocacao() {
        List<Cliente> clientes = acmeRobots.getClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há clientes cadastrados!");
            return;
        }

        String[] clienteOptions = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
        String selectedCliente = (String) JOptionPane.showInputDialog(this, "Selecione um cliente:", "Cadastrar Locação", JOptionPane.PLAIN_MESSAGE, null, clienteOptions, clienteOptions[0]);
        if (selectedCliente == null) {
            return;
        }

        Cliente cliente = clientes.stream().filter(c -> c.getNome().equals(selectedCliente)).findFirst().orElse(null);

        JTextField numeroField = new JTextField();
        JTextField dataInicioField = new JTextField();
        JTextField dataFimField = new JTextField();

        Object[] fields = {
                "Número da Locação:", numeroField,
                "Data de Início (dd/MM/yyyy):", dataInicioField,
                "Data de Fim (dd/MM/yyyy):", dataFimField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Cadastrar Locação", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int numero = Integer.parseInt(numeroField.getText());
                String dataInicio = dataInicioField.getText();
                String dataFim = dataFimField.getText();

                List<Robo> robos = acmeRobots.getRobos();
                String[] roboOptions = robos.stream().map(r -> r.getModelo() + " (ID: " + r.getId() + ")").toArray(String[]::new);
                JList<String> roboList = new JList<>(roboOptions);
                roboList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                JOptionPane.showMessageDialog(this, new JScrollPane(roboList), "Selecione os Robôs", JOptionPane.PLAIN_MESSAGE);
                List<String> selectedRobos = roboList.getSelectedValuesList();
                if (selectedRobos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum robô selecionado!");
                    return;
                }

                List<Robo> osRobos = new ArrayList<>();
                for (String selectedRobo : selectedRobos) {
                    int roboId = Integer.parseInt(selectedRobo.substring(selectedRobo.indexOf("ID: ") + 4, selectedRobo.indexOf(")")));
                    Robo robo = robos.stream().filter(r -> r.getId() == roboId).findFirst().orElse(null);
                    if (robo != null) {
                        osRobos.add(robo);
                    }
                }

                Locacao locacao = new Locacao(numero, Status.CADASTRADA, new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio), new SimpleDateFormat("dd/MM/yyyy").parse(dataFim), cliente, osRobos);
                acmeRobots.cadastrarLocacao(locacao);
                appendOutput("Locação cadastrada com sucesso.");
            } catch (IllegalArgumentException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de número ou data inválido!");
            }
        }
    }

    private void processarLocacoes() {
        acmeRobots.processarLocacoes();
        appendOutput("Locações processadas.");
    }

    private void mostrarRelatorioGeral() {
        String relatorio = acmeRobots.mostrarRelatorioGeral();
        appendOutput(relatorio);
    }

    private void consultarLocacoes() {
        String locacoes = acmeRobots.consultarLocacoes();
        appendOutput(locacoes);
    }

    private void alterarSituacaoLocacao() {
        JTextField numeroField = new JTextField();
        JTextField situacaoField = new JTextField();

        Object[] fields = {
                "Número da Locação:", numeroField,
                "Nova Situação (CADASTRADA, EXECUTANDO, FINALIZADA):", situacaoField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Alterar Situação de Locação", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int numero = Integer.parseInt(numeroField.getText());
                String situacao = situacaoField.getText();

                acmeRobots.alterarSituacaoLocacao(numero, situacao);
                appendOutput("Situação da locação alterada para " + situacao + ".");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Formato de número ou situação inválido!");
            }
        }
    }

    private void realizarCargaDeDadosIniciais() {
        String nomeArquivo = JOptionPane.showInputDialog("Informe o nome base dos arquivos de dados (sem extensão):");

        String formato = (String) fileFormatComboBox.getSelectedItem();
        if ("CSV".equals(formato)) {
            acmeRobots.setNomeArquivos(nomeArquivo + "-CLIENTES.CSV", nomeArquivo + "-ROBOS.CSV", nomeArquivo + "-LOCACOES.CSV");
            acmeRobots.realizarCargaDeDadosIniciais();
            outputArea.setText(acmeRobots.mostrarRelatorioGeral());
        } else {
            JOptionPane.showMessageDialog(this, "Formato de arquivo inválido!");
        }
    }

    private void salvarDados() {
        String nomeArquivo = JOptionPane.showInputDialog("Informe o nome base dos arquivos de dados (sem extensão):");

        String formato = (String) fileFormatComboBox.getSelectedItem();
        if ("CSV".equals(formato)) {
            acmeRobots.setNomeArquivos(nomeArquivo + "-CLIENTES.CSV", nomeArquivo + "-ROBOS.CSV", nomeArquivo + "-LOCACOES.CSV");
            acmeRobots.salvarDados();
            appendOutput("Dados salvos com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Formato de arquivo inválido!");
        }
    }

    private void finalizarSistema() {
        acmeRobots.finalizarSistema();
        appendOutput("Sistema finalizado.");
    }

    private void appendOutput(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ACMERobotsGUI::new);
    }
}
