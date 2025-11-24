package aluguer.estacao.extensao;

import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExtensaoPorHora implements Extensao {
    private final int horasExtendidas;
    
    public ExtensaoPorHora(int horasExtendidas) {
        this.horasExtendidas = horasExtendidas;
    }
    
    @Override
    public boolean estaHorarioExtendido(LocalDateTime momento, HorarioSemanal horario) {
        if (horario.estaDentroHorario(momento))
            return false;

        DayOfWeek dia = momento.getDayOfWeek();
        LocalTime hora = momento.toLocalTime();
        
        if (horario.getHorarioDia(dia).eVazio())
            return false;
        
        LocalTime abertura = horario.getHorarioDia(dia).getInicio();
        if ((hora.equals(abertura.minusHours(horasExtendidas)) || hora.isAfter(abertura.minusHours(horasExtendidas)))
                && hora.isBefore(abertura))
            return true;

        LocalTime fechamento = horario.getHorarioDia(dia).getFim();
        return hora.isAfter(fechamento) && hora.isBefore(fechamento.plusHours(horasExtendidas));
    }
}
