package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EstacaoPequena extends EstacaoGrande {   
    private final HorarioSemanal horario;
    private final Extensao extensao;
    private final PrecarioExtensao precoExtensao;
    
    public EstacaoPequena(String id, String nome, HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        super(id, nome);
        
        if(horario.getHorarioDia(DayOfWeek.SUNDAY).getInicio() != LocalTime.MIDNIGHT && horario.getHorarioDia(DayOfWeek.SUNDAY).getFim() != LocalTime.MIDNIGHT || 
                horario.getHorarioDia(DayOfWeek.SATURDAY).getInicio() != LocalTime.MIDNIGHT && horario.getHorarioDia(DayOfWeek.SATURDAY).getFim() != LocalTime.MIDNIGHT)
            throw new IllegalArgumentException("Uma estação pequena pode apenas ficar aberta em dias úteis");
        this.horario = horario;
        
        this.extensao = extensao;
        this.precoExtensao = precoExtensao;
    }
    
    @Override
    public HorarioSemanal getHorario() {
        return horario;
    }
    
    @Override
    public boolean estaAberta(LocalDateTime hora){
        return horario.estaDentroHorario(hora);
    }

    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return extensao != null ? extensao.estaHorarioExtendido(hora, horario) : false;
    }

    @Override
    public boolean estaAbertaComExtensao(LocalDateTime hora) {
        return estaAbertaEmExtensao(hora) || estaAberta(hora);
    }

    @Override
    public long getCustoExtensao(long precoVeiculo) {
        return precoExtensao != null ? precoExtensao.calcularCusto(precoVeiculo) : 0;
    }
}
