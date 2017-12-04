package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.AID;
import jade.lang.acl.*;

public class EnviarProposta extends SimpleBehaviour
{
   boolean fim = false;
   Turma t = new Turma();
   private String Receptor;
   public EnviarProposta (Turma a, String Receptor)
   {
      super(a);
      t =  a;
      this.Receptor = Receptor;
   }   
   
   @Override
   public void action()
   {
        System.out.println(myAgent.getLocalName() +": Preparando para enviar uma proposta ao Receptor" + this.Receptor);
        // Criação do objeto ACLMessage
        ACLMessage mensagem = new ACLMessage(ACLMessage.PROPOSE);
        //Preencher os campos necesários da mensagem
        mensagem.setSender(myAgent.getAID());
        mensagem.addReceiver(new AID(this.Receptor,AID.ISLOCALNAME));
        mensagem.setLanguage("Portugues");
        mensagem.setContent(t.getHorarios());
        mensagem.setOntology("Proposta");
        //Envia a mensagem aos destinatarios
        System.out.println(myAgent.getLocalName() +": Enviando proposta ao Receptor");
        System.out.println(myAgent.getLocalName() + "\n" + mensagem.toString());
        myAgent.send(mensagem);
        ACLMessage resposta = myAgent.blockingReceive();
        if (resposta!= null)
        {
            if(resposta.getContent()=="Aceito"){ 
                if (t.getFase()==1){
                    t.setDocenteAceito(myAgent.getAID().getLocalName());
                }
                else if(t.getFase()==2){
                    t.setSalaAceita(myAgent.getAID().getLocalName());
                }
            }
        }
        fim = true;
    }
   
   @Override
   public boolean done()
   {
      return fim;
   }
}