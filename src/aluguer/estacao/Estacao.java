package aluguer.estacao;

import aluguer.viatura.Viatura;
import pds.tempo.HorarioSemanal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Estacao {
    private final String id;
    private final String nome;
    private final HorarioSemanal horario;
    private Estacao central;
    private final List<Viatura> viaturas;

    protected Estacao(String id, String nome, HorarioSemanal horario) {
        this.id = Objects.requireNonNull(id);
        this.nome = Objects.requireNonNull(nome);
        this.horario = Objects.requireNonNull(horario);
        this.central = null;
        this.viaturas = new ArrayList<>();
    }

    public String getId() { 
        return id; 
    }
    
    public String getNome(){ 
        return nome; 
    }
    
    public HorarioSemanal getHorario() { 
        return horario; 
    }
    
    public Estacao getCentral() { 
        return central; 
    }

    public void setCentral(Estacao central) {
        this.central = central;
    }

    public void adicionarViatura(Viatura v) {
        viaturas.add(v);
    }

    public List<Viatura> getViaturas() {
        return Collections.unmodifiableList(viaturas);
    }
}
