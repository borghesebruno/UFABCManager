package ufabcmanager;

import jade.core.Agent;


public class Turma extends Agent {
    public volatile String DISCIPLINA = "";
    public volatile String HORARIOS = "";
    public volatile String Servicos = "";
    public volatile String DocenteAceito="";
    public volatile String SalaAceita = "";
    public volatile String Type="";
    public volatile int fase;
    
    @Override
    public void setup() 
    {
        String s;
        System.out.println("Novo agente Turma inicializado.");
        Object[] args = getArguments();
        DISCIPLINA = (String) args[0];
        HORARIOS = (String) args[1];
        addBehaviour(new ComportamentoTurma(this));
    }
    
    
    public String getDISCIPLINA() {
        return DISCIPLINA;
    }

    public String getHorarios() {
        return HORARIOS;
    }


    
    public String getMensagem(){
        return DISCIPLINA;
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

    public String getServicos() {
        return Servicos;
    }

    public void setServicos(String Servicos) {
        this.Servicos = Servicos;
    }

}
