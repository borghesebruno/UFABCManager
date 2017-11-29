package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.AID;
import jade.lang.acl.*;

public class EnviarProposta extends SimpleBehaviour
{
   boolean fim = false;
   Turma t = new Turma();
   public EnviarProposta (Agent a)
   {
      super(a);
      t = (Turma) a;
   }   
   
   @Override
   public void action()
   {
      System.out.println(myAgent.getLocalName() +": Preparando para enviar uma mensagem ao Receptor");
      // Criação do objeto ACLMessage
      ACLMessage mensagem = new ACLMessage(ACLMessage.PROPOSE);
      //Preencher os campos necesários da mensagem
      mensagem.setSender(myAgent.getAID());
      mensagem.addReceiver(new AID("Receptor",AID.ISLOCALNAME));
      mensagem.setLanguage("Portugues");
      mensagem.setContent("Olá, como você vai Receptor?");
      t.getHorarios();
      //Envia a mensagem aos destinatarios
      System.out.println(myAgent.getLocalName() +": Enviando olá ao Receptor");
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