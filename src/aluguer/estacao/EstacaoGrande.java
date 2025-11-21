package aluguer.estacao;

import aluguer.Aluguer;
import aluguer.viatura.Viatura;
import aluguer.viatura.ViaturaIndisponivel;
import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EstacaoGrande implements Estacao {
    private final String id;
    private final String nome;
    private Estacao central;
    private final List<Viatura> viaturas;
    private final List<ViaturaIndisponivel> viaturasIndisponiveis;

    public EstacaoGrande(String id, String nome) {
        this.id = Objects.requireNonNull(id);
        this.nome = nome;
        this.viaturas = new ArrayList<>();
        this.viaturasIndisponiveis = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNome(){
        return nome;
    }

    @Override
    public HorarioSemanal getHorario() {
        return HorarioSemanal.sempreAberto();
    }

    @Override
    public boolean estaAberta(LocalDateTime hora) {
        return true;
    }
    
    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return false;
    }

    @Override
    public Estacao getCentral() {
        return central;
    }

    @Override
    public void setCentral(Estacao central) {
        this.central = central;
    }

    @Override
    public void adicionarViatura(Viatura viatura) {
        viaturas.add(Objects.requireNonNull(viatura));
    }

    @Override
    public List<Viatura> getViaturas() {
        return Collections.unmodifiableList(viaturas);
    }

    @Override
    public List<ViaturaIndisponivel> getViaturasIndisponiveis() {
        return Collections.unmodifiableList(viaturasIndisponiveis);
    }
    
    @Override
    public void adicionarViaturaIndisponivel(ViaturaIndisponivel viatura) {
        viaturasIndisponiveis.add(viatura);
    }
}
