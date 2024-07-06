package src;

import src.aplicacao.ACMERobotsGUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ACMERobotsGUI::new);
    }
}