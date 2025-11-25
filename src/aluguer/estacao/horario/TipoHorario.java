package aluguer.estacao.horario;

import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;

public interface TipoHorario {
    HorarioSemanal getHorario();
    boolean estaAberta(LocalDateTime hora);
    boolean estaAbertaEmExtensao(LocalDateTime hora);
    boolean estaAbertaComExtensao(LocalDateTime hora);
    long getCustoExtensao(long precoVeiculo);
}
