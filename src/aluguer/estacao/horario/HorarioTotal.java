package aluguer.estacao.horario;

import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;

public class HorarioTotal implements TipoHorario {
    @Override
    public HorarioSemanal getHorario() {
        return HorarioSemanal.sempreAberto();
    }

    @Override
    public boolean estaAberta(LocalDateTime hora) {
        return true;
    }

    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return false;
    }

    @Override
    public boolean estaAbertaComExtensao(LocalDateTime hora) {
        return true;
    }

    @Override
    public long getCustoExtensao(long precoVeiculo) {
        return 0;
    }
}
