package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.AID;
import jade.lang.acl.*;

public class FinalizarProposta extends SimpleBehaviour
{
    boolean fim = false;
    Turma t = new Turma();
    private String Docente;
    private String Sala;
    public FinalizarProposta (Turma a, String Docente, String Sala)
    {
        super(a);
        t =  a;
        this.Docente = Docente;
        this.Sala = Sala;
    }   
   
    @Override
    public void action()
    {
        System.out.println(myAgent.getLocalName() +": Preparando para enviar uma proposta ao Receptor");
        // Criação do objeto ACLMessage
        ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);
        //Preencher os campos necesários da mensagem
        mensagem.setSender(myAgent.getAID());
        mensagem.addReceiver(new AID(this.Docente,AID.ISLOCALNAME));
        mensagem.setLanguage("Portugues");
        mensagem.setContent(t.getHorarios());
        mensagem.setOntology("Concluir");
        //Envia a mensagem aos destinatarios
        System.out.println(myAgent.getLocalName() +": Enviando proposta ao Receptor");
        System.out.println(myAgent.getLocalName() + "\n" + mensagem.toString());
        myAgent.send(mensagem);   


        System.out.println(myAgent.getLocalName() +": Preparando para enviar uma proposta ao Receptor");
        // Criação do objeto ACLMessage

        //Preencher os campos necesários da mensagem
        mensagem.setSender(myAgent.getAID());
        mensagem.addReceiver(new AID(this.Sala,AID.ISLOCALNAME));
        mensagem.setLanguage("Portugues");
        mensagem.setContent(t.getHorarios());
        mensagem.setOntology("Concluir");
        //Envia a mensagem aos destinatarios
        System.out.println(myAgent.getLocalName() +": Enviando proposta ao Receptor");
        System.out.println(myAgent.getLocalName() + "\n" + mensagem.toString());
        myAgent.send(mensagem);  


        fim = true;
    }
   
   @Override
   public boolean done()
   {
      return fim;
   }
}