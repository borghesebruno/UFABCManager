package ufabcmanager;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author b.borghese
 */
public class UFABCManager {
    static ContainerController containerController;
    static AgentController agentController;

    public static void main(String[] args) throws InterruptedException 
    {
        startMainContainer("127.0.0.1", Profile.LOCAL_PORT, "UFABC");
        addAgent(containerController, "Turma", Turma.class.getName(), null );
        addAgent(containerController, "Sala", Sala.class.getName(), null );
        addAgent(containerController, "Docente", Docente.class.getName(), null );

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
