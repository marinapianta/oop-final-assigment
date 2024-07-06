package src.aplicacao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import src.dados.*;

public class ACMERobotsGUI extends JFrame {
    private ACMERobots acmeRobots;
    private JTextArea outputArea;

    public ACMERobotsGUI() {
        acmeRobots = new ACMERobots();
        setTitle("ACME Robots Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("robo-icon.png"));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = createMainPanel();
        add(mainPanel);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = createOutputArea();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 230, 250));
        return mainPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        buttonPanel.setBackground(new Color(230, 230, 250));

        JButton[] buttons = {
                createButton("Cadastrar Novo Robô"),
                createButton("Cadastrar Novo Cliente"),
                createButton("Cadastrar Nova Locação"),
                createButton("Processar Locações"),
                createButton("Mostrar Relatório Geral"),
                createButton("Consultar Todas as Locações"),
                createButton("Alterar Situação de Locação"),
                createButton("Realizar Carga de Dados Iniciais"),
                createButton("Salvar Dados"),
                createButton("Finalizar Sistema")
        };

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        buttons[0].addActionListener(e -> cadastrarRobo());
        buttons[1].addActionListener(e -> cadastrarCliente());
        buttons[2].addActionListener(e -> cadastrarLocacao());
        buttons[3].addActionListener(e -> processarLocacoes());
        buttons[4].addActionListener(e -> mostrarRelatorioGeral());
        buttons[5].addActionListener(e -> consultarLocacoes());
        buttons[6].addActionListener(e -> alterarSituacaoLocacao());
        buttons[7].addActionListener(e -> realizarCargaDeDadosIniciais());
        buttons[8].addActionListener(e -> salvarDados());
        buttons[9].addActionListener(e -> finalizarSistema());

        return buttonPanel;
    }

    private JScrollPane createOutputArea() {
        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(240, 255, 255));
        outputArea.setForeground(new Color(0, 0, 0));
        outputArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(255, 192, 203));
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(255, 192, 203));
                } else {
                    g.setColor(new Color(147, 112, 219));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(new Color(123, 104, 238));
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Helvetica", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void setDialogColors() {
        UIManager.put("OptionPane.background", new Color(230, 230, 250));
        UIManager.put("Panel.background", new Color(230, 230, 250));
        UIManager.put("Button.background", new Color(147, 112, 219));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("TextField.background", new Color(255, 255, 255));
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("ComboBox.background", new Color(255, 255, 255));
        UIManager.put("ComboBox.foreground", Color.BLACK);
    }

    private void cadastrarRobo() {
        setDialogColors();
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
        setDialogColors();
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
        setDialogColors();
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
        setDialogColors();
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
        setDialogColors();
        String nomeArquivo = JOptionPane.showInputDialog("Informe o nome base dos arquivos de dados (sem extensão):");

        acmeRobots.setNomeArquivos(nomeArquivo + "-CLIENTES.CSV", nomeArquivo + "-ROBOS.CSV", nomeArquivo + "-LOCACOES.CSV");
        acmeRobots.realizarCargaDeDadosIniciais();
        outputArea.setText(acmeRobots.mostrarRelatorioGeral());
    }

    private void salvarDados() {
        setDialogColors();
        String nomeArquivo = JOptionPane.showInputDialog("Informe o nome base dos arquivos de dados (sem extensão):");

        acmeRobots.setNomeArquivos(nomeArquivo + "-CLIENTES.CSV", nomeArquivo + "-ROBOS.CSV", nomeArquivo + "-LOCACOES.CSV");
        acmeRobots.salvarDados();
        appendOutput("Dados salvos com sucesso.");
    }

    private void finalizarSistema() {
        acmeRobots.finalizarSistema();
        appendOutput("Sistema finalizado.");
    }

    private void appendOutput(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

}
