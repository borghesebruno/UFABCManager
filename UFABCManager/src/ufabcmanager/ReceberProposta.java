package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.*;

public class ReceberProposta extends SimpleBehaviour
{
   private boolean fim = false;
   private Docente t=null;
   private Sala s=null;
   public ReceberProposta (Docente a)
   {
        super(a);
        t = a;
   }
   public ReceberProposta (Sala a)
   {
        super(a);
        s = a;
   }  
   
   @Override
   public void action()
   {
        System.out.println(myAgent.getLocalName() + ": Preparando para receber mensagens");      
        //Obtem a primeira mensagem da fila de mensagens
        ACLMessage mensagem = myAgent.receive();
        if (mensagem!= null)
        {
            boolean teste = true;
            System.out.println(myAgent.getLocalName() + ": Acaba de receber a seguinte mensagem: ");
            System.out.println(mensagem.toString());
            String mes = mensagem.getContent();
            String[] split = mes.split(";");
            for(int i=0; i<split.length;i++){
                if(t!=null){
                    if(t.getDay(split[i]).Sala!=""){
                        teste=false;
                    }
                }
                else if(s!=null){
                    if(s.getDay(split[i]).Docente!=""){
                        teste=false;
                    }
                }   
            }
            
            ACLMessage resposta = mensagem.createReply();
            resposta.setPerformative( ACLMessage.INFORM );
            if (teste)
                resposta.setContent("Aceito");
            else
                resposta.setContent("Não Aceito");
            myAgent.send(resposta);
            System.out.println(myAgent.getLocalName() +": Enviei a seguinte resposta "
                    + "ao Receptor");
            System.out.println(resposta.toString());
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