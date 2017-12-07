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
    /*Este tempo de sincronização é importante para dar um tempo
      *para o AgenteRegistro iniciar suas atividades. Isto porque o 
      *AgenteBusca ira solicitar servicos do AgenteRegistro
      */
    
     /* try
      {
         Thread.sleep(10000);
      }
      catch(Exception e)
      {
         System.out.println("Erro: " + e);
      }*/

     //Criação de um objeto capaz de ser manipulado pelo DF 
     DFAgentDescription agenteDescription = new DFAgentDescription();

     //crio um objeto contendo a descricao do servico
     ServiceDescription sd = new ServiceDescription();
     sd.setType(t.getType()); //defino o tipo de servico
     sd.setName(t.getMensagem()); 
     /*Neste momento poderia definir outras caracteristicas
     * do servico buscado, para filtrar melhor a busca.  */
     
     //adiciono o servico no objeto da classe DFAgentDescription
     agenteDescription.addServices(sd);
     //Vou buscar pelos agentes
     //A busca retorna um array DFAgentDescription
     //O parametro this indica o agente que esta realizando a busca
     DFAgentDescription[] result;
     result =null;
        try {
            result = DFService.search(myAgent, agenteDescription);
        } catch (FIPAException ex) {
          //  Logger.getLogger(BuscarServico.class.getName()).log(Level.SEVERE, null, ex);
        }
     //Imprimo os resultados
     String out="";
     
     for (int i = 0; i < result.length; i++)
     {
         out = out + result[i].getName().getLocalName() + ";";
         
     } //fim do laco for
     System.out.println("Teste, Disciplina" + t.getMensagem()+ ": "  + out);
     t.setServicos(out);
     t=null;
     fim = true;
   }//Fim do metodo setup()}

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

  


      
}//Fim da classe AgenteBusca