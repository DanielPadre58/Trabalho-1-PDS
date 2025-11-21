package aluguer.estacao.extensao;

import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;

public interface Extensao {
    public boolean estaHorarioExtendido(LocalDateTime hora, HorarioSemanal horario);
}
