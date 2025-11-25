package aluguer.estacao.horario;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;
import java.util.Objects;

public class HorarioLimitado implements TipoHorario{
    private final HorarioSemanal horario;
    private final Extensao extensao;
    private final PrecarioExtensao precoExtensao;
    
    public HorarioLimitado(HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        this.horario = Objects.requireNonNull(horario);
        this.extensao = extensao;
        this.precoExtensao = precoExtensao;
    }

    @Override
    public HorarioSemanal getHorario() {
        return horario;
    }

    @Override
    public boolean estaAberta(LocalDateTime hora) {
        return horario.estaDentroHorario(hora);
    }

    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return extensao != null ? extensao.estaHorarioExtendido(hora, horario) : false;
    }

    @Override
    public boolean estaAbertaComExtensao(LocalDateTime hora) {
        return estaAberta(hora) || estaAbertaEmExtensao(hora);
    }

    @Override
    public long getCustoExtensao(long precoVeiculo) {
        return precoExtensao != null ? precoExtensao.calcularCusto(precoVeiculo) : 0;
    }
}
