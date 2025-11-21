package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class EstacaoPequena extends EstacaoGrande {   
    private HorarioSemanal horario;
    private Extensao extensao;
    private PrecarioExtensao precoExtensao;
    
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
        if(horario.estaDentroHorario(hora))
            return true;

        if(extensao == null)
            return false;
        
        return extensao.estaHorarioExtendido(hora, horario);
    }

    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return extensao.estaHorarioExtendido(hora, horario);
    }
}
