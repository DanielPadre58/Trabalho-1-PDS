package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class EstacaoMedia extends EstacaoGrande {    
    private HorarioSemanal horario;
    private Extensao extensao;
    private PrecarioExtensao precoExtensao;
    
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
        if(horario.estaDentroHorario(hora))
            return true;

        if(extensao == null)
            return false;

        return extensao.estaHorarioExtendido(hora, horario);
    }

    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return extensao.estaHorarioExtendido(hora, horario);
    }
}
