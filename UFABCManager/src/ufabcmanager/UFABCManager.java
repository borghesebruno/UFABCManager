package ufabcmanager;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import javax.swing.*;

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

    public static void main(String[] args) throws InterruptedException 
    {
        startMainContainer("127.0.0.1", Profile.LOCAL_PORT, "UFABC");
        
        JOptionPane.showMessageDialog(null, "Bem vindo ao UFABCManager!\nPronto para começar?");
        TURMAS = Integer.parseInt(JOptionPane.showInputDialog("Qual a quantidade de turmas?"));
        SALAS = Integer.parseInt(JOptionPane.showInputDialog("Qual a quantidade de salas?"));
        DOCENTES = Integer.parseInt(JOptionPane.showInputDialog("Qual a quantidade de docentes?"));
        
        for(int i = 1; i <= TURMAS; i++) {
            addAgent(containerController, "Turma-"+i, Turma.class.getName(), null );
        }
        for(int i = 1; i <= SALAS; i++) {
            addAgent(containerController, "Sala-"+i, Sala.class.getName(), null );
        }
        for(int i = 1; i <= DOCENTES; i++) {
            addAgent(containerController, "Docente-"+i, Docente.class.getName(), null );
        }

        //adicionando agente RMA
        //addAgent(containerController, "rma", "jade.tools.rma.rma", null);
        addAgent(containerController, "rma", jade.tools.rma.rma.class.getName(), null);
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
}
