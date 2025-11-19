package aluguer.estacao.extensao;

import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface Extensao {
    public boolean estaHorarioExtendido(LocalDateTime hora, HorarioSemanal horario);
}
