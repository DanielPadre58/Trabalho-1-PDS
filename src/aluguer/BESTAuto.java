package aluguer;

import aluguer.estacao.Estacao;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que representa o sistema
 */
public class BESTAuto {
    private final List<Estacao> estacoes;
    private final List<Aluguer> alugueres;

    public BESTAuto() {
        estacoes = new ArrayList<>();
        alugueres = new ArrayList<>();
    }
    
    public void adicionarEstacao(Estacao estacao) {
        estacoes.add(estacao);
    }

    public Estacao getEstacao(String id) {
        return estacoes.stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }
    
    public List<Estacao> getEstacoes() {
        return Collections.unmodifiableList(estacoes);
    }
    
    public void adicionarAluguer(Aluguer aluguer) {
        alugueres.add(aluguer);
    }
}
