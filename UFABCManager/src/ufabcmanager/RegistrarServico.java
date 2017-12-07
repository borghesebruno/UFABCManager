package ufabcmanager;


import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class RegistrarServico extends SimpleBehaviour 
{
    private boolean fim = false;
    private Docente t = new Docente();
    private Sala e = new Sala();
    private String Agente ="";
    RegistrarServico(Docente a){
        super(a);
        t=a;
        Agente = "Docente";
    }
    
    
    RegistrarServico(Sala a){
        super(a);
        e=a;
        Agente = "Sala";
    }

    RegistrarServico() { }
    
   
    @Override
   public void action() 
   {    
       
      
 
      //Descrição do agente que oferecera um serviço (Servico 1)
      DFAgentDescription dfd = new DFAgentDescription();
      dfd.setName(myAgent.getAID()); //Informamos a AID do agente
      
      //Definição e descrição do serviço
      ServiceDescription sd = new ServiceDescription();
      if(Agente == "Docente"){
        //System.out.println("Registrando t: " + t.getType() + " " + t.getMensagem());
        sd.setType(t.getType()); //Tipo do Servico
        sd.setName(t.getMensagem()); //Nome do Servico
      }
      else{
        //System.out.println("Registrando e: " + e.getType() + " " + e.getMensagem());
        sd.setType(e.getType()); //Tipo do Servico
        sd.setName(e.getMensagem()); //Nome do Servico  
      }
      // Propriedades do servico
     
      //Inserção do serviço na lista de serviços da descricao do agente
      dfd.addServices(sd);
      
            
      //Vamos registrar o agente no DF
      try 
      {
         //register(agente que oferece, descricao)
         DFService.register(myAgent, dfd);
         fim = true;
      } 
      catch (FIPAException e) 
      {
         e.printStackTrace();
      }
      fim = true;
   }//Fim do metodo setup()
   
   protected void takeDown()
   {
      try
      {
         DFService.deregister(myAgent); 
      }
      catch ( FIPAException e )
      {
         e.printStackTrace() ;
      }
   }

    @Override
    public boolean done() {
        return fim;
    }
}