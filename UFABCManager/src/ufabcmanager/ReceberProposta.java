package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.*;

public class ReceberProposta extends SimpleBehaviour
{
   private boolean fim = false;
   public ReceberProposta (Agent a)
   {
      super(a);
   }   
   
   @Override
   public void action()
   {
      System.out.println(myAgent.getLocalName() + ": Preparando para receber mensagens");      
      //Obtem a primeira mensagem da fila de mensagens
      ACLMessage mensagem = myAgent.receive();
      if (mensagem!= null)
      {
         System.out.println(myAgent.getLocalName() + ": Acaba de receber a seguinte mensagem: ");
         System.out.println(mensagem.toString());
         fim = true;
      }
      else
      {
         System.out.println("Receptor: Bloqueado para esperar receber mensagem.....");
         block();
      }
   } 
   
   @Override
   public boolean done()
   {
      return fim;
   }
}