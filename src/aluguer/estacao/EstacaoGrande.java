package aluguer.estacao;

import aluguer.estacao.horario.HorarioTotal;

public class EstacaoGrande extends EstacaoBase {
    public EstacaoGrande(String id, String nome) {
        super(id, nome, new HorarioTotal());
    }
}
