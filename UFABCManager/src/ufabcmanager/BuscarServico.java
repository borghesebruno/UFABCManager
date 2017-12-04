package ufabcmanager;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscarServico extends SimpleBehaviour 
{
    private boolean fim = false;
    Turma t;
    BuscarServico(Turma a){
        super(a);
        t = a;
    }
    
    
    @Override
    public void action() {
        DFAgentDescription agenteDescription = new DFAgentDescription();

        ServiceDescription sd = new ServiceDescription();
        sd.setType(t.getType());
        sd.setName(t.getMensagem());

        agenteDescription.addServices(sd);
        DFAgentDescription[] result;
        result =null;
            try {
                result = DFService.search(myAgent, agenteDescription);
            } catch (FIPAException ex) {
                //Logger.getLogger(BuscarServico.class.getName()).log(Level.SEVERE, null, ex);
            }

        String out="";

        for (int i = 0; i < result.length; i++)
        {
            out = out + result[i].getName().getLocalName() + ";";
        }
        System.out.println("Teste: " + out);
        t.setServicos(out);
        fim = true;
   }

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