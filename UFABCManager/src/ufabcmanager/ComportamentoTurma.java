/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufabcmanager;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static ufabcmanager.UFABCManager.arq;
import static ufabcmanager.UFABCManager.gravarArq;
/**
 *
 * @author lucas
 */
public class ComportamentoTurma extends SimpleBehaviour {
        public Turma t;
        public boolean fim = false;

        ComportamentoTurma(Turma a){
            super(a);
            t=a;
        }
        
        public void EnviarProposta(Turma t, String Receptor){
            System.out.println(myAgent.getLocalName() +": Preparando para enviar uma proposta ao Receptor" + Receptor);
            // Criação do objeto ACLMessage
            ACLMessage mensagem = new ACLMessage(ACLMessage.PROPOSE);
            //Preencher os campos necesários da mensagem
            mensagem.setSender(myAgent.getAID());
            mensagem.addReceiver(new AID(Receptor,AID.ISLOCALNAME));
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
                if(resposta.getContent().equals("Aceito")){ 
                    if (t.getFase()==1){
                        t.setDocenteAceito(resposta.getSender().getLocalName());
                        System.out.println("A proposta foi aceita");
                    }
                    else if(t.getFase()==2){
                        t.setSalaAceita(resposta.getSender().getLocalName());
                    }
                }
            }
        }
        
        public void FinalizarProposta(Turma t)throws IOException{

            ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);
            //Preencher os campos necesários da mensagem
            mensagem.setSender(myAgent.getAID());
            mensagem.addReceiver(new AID(t.DocenteAceito,AID.ISLOCALNAME));
            mensagem.setLanguage("Portugues");
            mensagem.setContent(t.getHorarios());
            mensagem.setOntology("Concluir");
            mensagem.setEncoding(t.DocenteAceito);
            mensagem.setProtocol(t.SalaAceita);
            //Envia a mensagem aos destinatarios
            System.out.println(myAgent.getLocalName() +": Enviando Confirmação ao Docente");
            System.out.println(myAgent.getLocalName() + "\n" + mensagem.toString());
            myAgent.send(mensagem);   



            //Preencher os campos necesários da mensagem
            ACLMessage mensagem2 = new ACLMessage(ACLMessage.INFORM);
            mensagem2.setSender(myAgent.getAID());
            mensagem2.addReceiver(new AID(t.SalaAceita,AID.ISLOCALNAME));
            mensagem2.setLanguage("Portugues");
            mensagem2.setContent(t.getHorarios());
            mensagem2.setOntology("Concluir");
            mensagem2.setEncoding(t.DocenteAceito);
            mensagem2.setProtocol(t.SalaAceita);
            //Envia a mensagem aos destinatarios
            System.out.println(myAgent.getLocalName() +": Enviando Confirmação a Sala");
            System.out.println(myAgent.getLocalName() + "\n" + mensagem2.toString());
            myAgent.send(mensagem2);  

            

            
            UFABCManager.Horarios += t.getLocalName() + " ||| " + t.getDocenteAceito() + " ||| " +t.getSalaAceita() + "\n";
        }
        
        
        public void BuscarServico(Turma t){
                DFAgentDescription agenteDescription = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType(t.getType()); //defino o tipo de servico
                if(t.getFase()==2)
                    sd.setName("Sala"); 
                    
                else
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
                //System.out.println("Teste, Disciplina" + t.getMensagem()+ ": "  + out);
                t.setServicos(out);
                
        }
    
           @Override
        public void action()
        {
        String s;
        t.Type = "Lecionar Disciplinas";
        //t.addBehaviour(new BuscarServico(t));
        
        BuscarServico(t);
        s="";
        try
        {
           Thread.sleep(5000);
        }
        catch(Exception e)
        {
           System.out.println("Erro: " + e);
        }
        //for(int j = 0;s=="";j++)
        s = t.getServicos();
        System.out.println("Servicos: " + s);
        String[] split = s.split(";");
        for(int i =0;i< split.length;i++){
            t.setFase(1);
            
            //System.out.println("Enviando proposta a: "+ split[i]);
            //t.addBehaviour(new EnviarProposta(t,split[i]));
            EnviarProposta(t, split[i]);
            try
            {
               Thread.sleep(5000);
            }
            catch(Exception e)
            {
               System.out.println("Erro: " + e);
            }
            if(t.DocenteAceito!=""){
                t.Type = "Receber turmas";
                t.setFase(2);
                //t.addBehaviour(new BuscarServico(t));
                BuscarServico(t);
                try
                {
                   Thread.sleep(5000);
                }
                catch(Exception e)
                {
                   System.out.println("Erro: " + e);
                }
                //while(s.equals("")){
                    s = t.getServicos();
                //}
                //System.out.println("Servicos: "+s);
                String[] split2 = s.split(";");
                
                for(int j = (int) Math.floor(Math.random() *split2.length);j< split2.length;j++){
                    
                    //t.addBehaviour(new EnviarProposta(t,split2[j]));
                    EnviarProposta(t,split2[j]);
                    try
                    {
                       Thread.sleep(5000);
                    }
                    catch(Exception e)
                    {
                       System.out.println("Erro: " + e);
                    }
                    if(t.SalaAceita!=""){
                        t.setFase(3);
                        try {
                            //t.addBehaviour(new FinalizarProposta(t,t.DocenteAceito,t.SalaAceita));
                            FinalizarProposta(t);
                            t.SalaAceita = "";
                            t.DocenteAceito = "";
                        } catch (IOException ex) {
                            System.out.println(ex.toString());
                        }
                        break;
                    }
                    
                }
                if (t.SalaAceita=="")
                    t.DocenteAceito = "";
            }
            t.SalaAceita="";t.DocenteAceito="";
        }
        fim =true;
    }

    @Override
    public boolean done() {
        return fim;
    }
    
}
