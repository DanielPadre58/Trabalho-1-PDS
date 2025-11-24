package aluguer.estacao.extensao;

import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;

public class ExtensaoTotal implements Extensao {
    @Override
    public boolean estaHorarioExtendido(LocalDateTime hora, HorarioSemanal horario) {
        return !horario.estaDentroHorario(hora);
    }
}
