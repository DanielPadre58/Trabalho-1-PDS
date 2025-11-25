package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import aluguer.estacao.horario.HorarioLimitado;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class EstacaoPequena extends EstacaoBase {
    public EstacaoPequena(String id, String nome, HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        super(id, nome, new HorarioLimitado(horario, extensao, precoExtensao));
        validarHorario(horario);
    }
    
    private void validarHorario(HorarioSemanal horario) {
        if(horario.getHorarioDia(DayOfWeek.SUNDAY).getInicio() != LocalTime.MIDNIGHT && horario.getHorarioDia(DayOfWeek.SUNDAY).getFim() != LocalTime.MIDNIGHT ||
                horario.getHorarioDia(DayOfWeek.SATURDAY).getInicio() != LocalTime.MIDNIGHT && horario.getHorarioDia(DayOfWeek.SATURDAY).getFim() != LocalTime.MIDNIGHT)
            throw new IllegalArgumentException("Uma estação pequena pode apenas ficar aberta em dias úteis");
    }
}
