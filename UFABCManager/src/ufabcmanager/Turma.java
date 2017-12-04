package ufabcmanager;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class Turma extends Agent {
    public static String DISCIPLINA = "";
    public static String HORARIOS = "";
    public String Servicos = "";
    public String DocenteAceito="";
    public String SalaAceita = "";
    public String Type="";
    public int fase;
    
    @Override
    public void setup() 
    {
        String s;
        System.out.println("Novo agente Turma inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
        HORARIOS = (String) args[1];
        Type = "Lecionar Disciplinas";
        
        try
        {
            Thread.sleep(10000);
        }
        catch(Exception e)
        {
            System.out.println("Erro: " + e);
        }
        s="";
        addBehaviour(new BuscarServico(this));
        /*for(int j = 0;s=="";j++)
             s = getServicos();*/
        System.out.println("Servicos: " + s);
        String[] split = s.split(";");
        for(int i =0;i< split.length;i++){
            
            setFase(1);
            System.out.println("Enviando proposta a: "+ split[i]);
            addBehaviour(new EnviarProposta(this,split[i]));
            if(DocenteAceito!=""){
                Type = "Receber turmas";
                addBehaviour(new BuscarServico(this));
                /*while(s.equals("")){
                    s = getServicos();
                }*/
                System.out.println("Servicos: "+s);
                String[] split2 = s.split(";");
                
                for(int j =0;j< split2.length;j++){
                    setFase(2);
                    addBehaviour(new EnviarProposta(this,split2[j]));
                    if(SalaAceita!=""){
                        setFase(3);
                        addBehaviour(new FinalizarProposta(this,DocenteAceito,SalaAceita));
                        //Escrever a afirmação de proposta
                        break;
                    }
                    
                }
                if (SalaAceita=="")
                    DocenteAceito = "";
            }
        }
        if(DocenteAceito!=""){

        }
    }
    
    
    public static String getDISCIPLINA() {
        return DISCIPLINA;
    }

    public String getHorarios() {
        return HORARIOS;
    }

    public void setHorarios(String Horarios) {
        this.HORARIOS = Horarios;
    }
    
    public String getMensagem(){
        return this.DISCIPLINA;
    }

    public String getServicos() {
        return Servicos;
    }

    public void setServicos(String aDocentes) {
        Servicos = aDocentes;
    }

    public String getDocenteAceito() {
        return DocenteAceito;
    }

    public void setDocenteAceito(String DocenteAceito) {
        this.DocenteAceito = DocenteAceito;
    }

    public String getSalaAceita() {
        return SalaAceita;
    }

    public void setSalaAceita(String SalaAceita) {
        this.SalaAceita = SalaAceita;
    }

    public String getType() {
        return Type;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }
    
    public void search() {
        DFAgentDescription agenteDescription = new DFAgentDescription();

        ServiceDescription sd = new ServiceDescription();
        sd.setType(this.getType());
        sd.setName(this.getMensagem());

        agenteDescription.addServices(sd);
        DFAgentDescription[] result;
        result =null;
            try {
                result = DFService.search(this, agenteDescription);
            } catch (FIPAException ex) {
                //Logger.getLogger(BuscarServico.class.getName()).log(Level.SEVERE, null, ex);
            }

        String out="";

        for (int i = 0; i < result.length; i++)
        {
            out = out + result[i].getName().getLocalName() + ";";
        }
        System.out.println("Teste: " + out);
        this.setServicos(out);
    }
}