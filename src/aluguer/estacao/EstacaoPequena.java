package aluguer.estacao;

import aluguer.estacao.extensao.Extensao;
import aluguer.estacao.extensao.PrecarioExtensao;
import pds.tempo.HorarioSemanal;

import java.util.Objects;

public class EstacaoPequena extends Estacao {   
    private Extensao extensao;
    private PrecarioExtensao precoExtensao;
    
    public EstacaoPequena(String id, String nome, HorarioSemanal horario, Extensao extensao, PrecarioExtensao precoExtensao) {
        super(id, nome, horario);
        this.extensao = Objects.requireNonNull(extensao);
        this.precoExtensao = Objects.requireNonNull(precoExtensao);
    }
}
