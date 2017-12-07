package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.*;
import java.util.Iterator;
import java.util.Set;

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
            if(mensagem.getOntology()!="Concluir"){
                String mes = mensagem.getContent();
                String[] split = mes.split(";");
                for(int i=0; i<split.length;i++){
                        try
                        {
                           Thread.sleep(5000);
                        }
                        catch(Exception e)
                        {
                           System.out.println("Erro: " + e);
                        }
                    if(t!=null){
                        if(!"".equals(t.getDay(split[i]).Sala)){
                            teste=false;
                        }
                    }
                    else if(s!=null){
                            if(!"".equals(s.getDay(split[i]).Turma)){
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
                if(t!=null && TemHorario(t))
                    t.addBehaviour(new ReceberProposta(t));
                else if(s!=null && TemHorario(s))
                    s.addBehaviour(new ReceberProposta(s));

                fim = true;
                }
            else{
                String mes = mensagem.getContent();
                String[] split = mes.split(";");
                for(int i=0; i<split.length;i++){
                    if(t!=null){
                        Horario hora = new Horario(mensagem.getProtocol(),mensagem.getSender().getLocalName());
                        t.mapaNomes.put(split[i], hora);
                    }
                    else if(s!=null){
                        HorarioSala hora2 = new HorarioSala(mensagem.getEncoding(),mensagem.getSender().getLocalName());
                        s.mapaNomes.put(split[i], hora2);
                    }   
                }
                
                
                
                
            }
        }
        else
        {
            System.out.println("Receptor: Bloqueado para esperar receber mensagem.....");
            block();
        }
   } 
   public boolean TemHorario(Docente t){
        boolean teste = false;
       		Set<String> chaves = t.mapaNomes.keySet();
		for (Iterator<String> iterator = chaves.iterator(); iterator.hasNext();)
		{
			String chave = iterator.next();
			if(chave != null)
				if (t.mapaNomes.get(chave).Turma=="")
                                    teste = true;
		}
        return teste;

   }
   
      public boolean TemHorario(Sala t){
        boolean teste = false;
       		Set<String> chaves = t.mapaNomes.keySet();
		for (Iterator<String> iterator = chaves.iterator(); iterator.hasNext();)
		{
			String chave = iterator.next();
			if(chave != null)
				if (t.mapaNomes.get(chave).Turma=="")
                                    teste = true;
		}
        return teste;

   }
   
   @Override
   public boolean done()
   {
      return fim;
   }
}