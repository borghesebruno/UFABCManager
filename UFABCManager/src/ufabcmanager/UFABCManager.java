package ufabcmanager;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import javax.swing.*;
import java.awt.GridLayout;

/**
 *
 * @author b.borghese
 */
public class UFABCManager {
    static ContainerController containerController;
    static AgentController agentController;
    static int TURMAS;
    static int SALAS;
    static int DOCENTES;
    static String[] args;
    static String nomeDocente;
    static String disciplinaDocente;
    static String nomeTurma;
    static String disciplinaTurma;

    public static void main(String[] args) throws InterruptedException 
    {
        JOptionPane.showMessageDialog(null, "Bem vindo ao UFABCManager!\nPronto para começar?");
        
        startMainContainer("127.0.0.1", Profile.LOCAL_PORT, "UFABC");
        addAgent(containerController, "rma", jade.tools.rma.rma.class.getName(), null);
        
        TURMAS = Integer.parseInt(JOptionPane.showInputDialog("Qual a quantidade de turmas?"));
        SALAS = Integer.parseInt(JOptionPane.showInputDialog("Qual a quantidade de salas?"));
        DOCENTES = Integer.parseInt(JOptionPane.showInputDialog("Qual a quantidade de docentes?"));
        
        for(int i = 1; i <= DOCENTES; i++) {
            boolean preenchido = false;
            while(preenchido == false) {
                preenchido = docenteForm();
            }
            args = new String[1];
            args[0] = disciplinaDocente;
            addAgent(containerController, nomeDocente, Docente.class.getName(), args );
            nomeDocente = "";
        }
        for(int i = 1; i <= TURMAS; i++) {
            boolean preenchido = false;
            while(preenchido == false) {
                preenchido = turmaForm();
            }
            args = new String[1];
            args[0] = disciplinaTurma;
            addAgent(containerController, "Turma-"+nomeTurma, Turma.class.getName(), args );
            nomeTurma = "";
        }
        for(int i = 1; i <= SALAS; i++) {
            addAgent(containerController, "Sala-"+i, Sala.class.getName(), null );
        }
    }

    public static void startMainContainer(String host, String port, String name) {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, host);
        profile.setParameter(Profile.MAIN_PORT, port);
        profile.setParameter(Profile.PLATFORM_ID, name);
        
        containerController = runtime.createMainContainer(profile);
    }

    public static void addAgent(ContainerController cc, String agent, String classe, Object[] args) {
        try {
            agentController = cc.createNewAgent(agent, classe, args);
            agentController.start();
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }
    
    private static boolean docenteForm() {
        JTextField nome = new JTextField("");
        JTextField disciplina = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new JLabel("Nome do docente:"));
        panel.add(nome);
        panel.add(new JLabel("Disciplina que lecionará:"));
        panel.add(disciplina);
        int result = JOptionPane.showConfirmDialog(null, panel, "Informações de Docente",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !nome.getText().equals("") && !disciplina.getText().equals("")) {
                nomeDocente = nome.getText();
                disciplinaDocente = disciplina.getText();
                return true;
        } else {
            return false;
        }
    }
    
    private static boolean turmaForm() {
        JTextField nome = new JTextField("");
        JTextField disciplina = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new JLabel("Nome da turma:"));
        panel.add(nome);
        panel.add(new JLabel("Disciplina que será lecionada para ela:"));
        panel.add(disciplina);
        int result = JOptionPane.showConfirmDialog(null, panel, "Informações de Turma",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !nome.getText().equals("") && !disciplina.getText().equals("")) {
                nomeTurma = nome.getText();
                disciplinaTurma = disciplina.getText();
                return true;
        } else {
            return false;
        }
    }
}
