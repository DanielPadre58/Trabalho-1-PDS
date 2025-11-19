package aluguer.estacao.extensao;

import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExtensaoTotal implements Extensao {
    @Override
    public boolean estaHorarioExtendido(LocalDateTime hora, HorarioSemanal horario) {
        return true;
    }
}
