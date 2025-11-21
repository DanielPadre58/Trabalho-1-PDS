package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.util.Objects;

public class EstacaoPequena extends EstacaoGrande {   
    private HorarioSemanal horario;
    private Extensao extensao;
    private PrecarioExtensao precoExtensao;
    
    public EstacaoPequena(String id, String nome, HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        super(id, nome);
        
        if(horario.getHorarioDia(DayOfWeek.SUNDAY) != null || horario.getHorarioDia(DayOfWeek.SATURDAY) != null)
            throw new IllegalArgumentException("Uma estação pequena pode apenas ficar aberta em dias úteis");
        this.horario = horario;
        
        this.extensao = Objects.requireNonNull(extensao);
        this.precoExtensao = Objects.requireNonNull(precoExtensao);
    }
    
    public HorarioSemanal getHorario() {
        return horario;
    }
}
