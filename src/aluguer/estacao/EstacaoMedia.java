package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import aluguer.estacao.horario.HorarioLimitado;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class EstacaoMedia extends EstacaoBase {    
    public EstacaoMedia(String id, String nome, HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        super(id, nome, new HorarioLimitado(horario, extensao, precoExtensao));
        validarHorario(horario);
    }

    private void validarHorario(HorarioSemanal horario) {
        for(int i = 1; i <= 7; i++) {
            DayOfWeek dia = DayOfWeek.of(i);
            if(horario.getHorarioDia(dia).contem(LocalTime.MAX) &&
                    horario.getHorarioDia(dia).contem(LocalTime.MIDNIGHT))
                throw new IllegalArgumentException("Uma estação média não pode ficar aberta o dia todo");
        }
    }
}
