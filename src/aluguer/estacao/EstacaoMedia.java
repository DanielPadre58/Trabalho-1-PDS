package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class EstacaoMedia extends EstacaoGrande {    
    private final HorarioSemanal horario;
    private final Extensao extensao;
    private final PrecarioExtensao precoExtensao;
    
    public EstacaoMedia(String id, String nome, HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        super(id, nome);

        for(int i = 1; i <= 7; i++) {
            DayOfWeek d = DayOfWeek.of(i);
            if(horario.getHorarioDia(d).contem(LocalDateTime.MAX.toLocalTime()) && 
                    horario.getHorarioDia(d).contem(LocalDateTime.MIN.toLocalTime()))
                throw new IllegalArgumentException("Uma estação média não pode ficar aberta o dia todo");
        }
            
        this.horario = horario;
        
        this.extensao = extensao;
        this.precoExtensao = precoExtensao;
    }
    
    @Override
    public HorarioSemanal getHorario() {
        return horario;
    }

    @Override
    public boolean estaAberta(LocalDateTime hora){
        return horario.estaDentroHorario(hora);
    }

    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return extensao.estaHorarioExtendido(hora, horario);
    }

    @Override
    public boolean estaAbertaComExtensao(LocalDateTime hora) {
        return estaAbertaEmExtensao(hora) || estaAberta(hora);
    }
    
    @Override
    public long getCustoExtensao(long precoVeiculo) {
        return precoExtensao.calcularCusto(precoVeiculo);
    }
}
