package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import pds.tempo.IntervaloTempo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que representa o sistema
 */
public class BESTAuto {
    private final List<Estacao> estacoes;
    private final List<Aluguer> alugueres;
    private final List<ModeloViatura> modelos;

    public BESTAuto() {
        estacoes = new ArrayList<>();
        alugueres = new ArrayList<>();
        modelos = new ArrayList<>();
    }
    
    public void adicionarEstacao(Estacao estacao) {
        estacoes.add(estacao);
    }

    public Estacao getEstacao(String id) {
        return estacoes.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Estacao> getEstacoes() {
        return Collections.unmodifiableList(estacoes);
    }

    public ModeloViatura getModelo(String id) {
        return modelos.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<ModeloViatura> getModelos() {
        return Collections.unmodifiableList(modelos);
    }
    
    public void adicionarModelo(ModeloViatura modelo) {
        modelos.add(modelo);
    }
    
    public void adicionarAluguer(Aluguer aluguer) {
        alugueres.add(aluguer);
    }
    
    public List<Viatura> pesquisarViaturas(Categoria categoria, String estacao) {
        return getEstacao(estacao)
                .getViaturas()
                .stream()
                .filter(v -> v.getModelo().getCategoria().equals(categoria))
                .toList();
    }
    
    public long calcularCustoTotal(String estacao, String modelo, IntervaloTempo intervalo) {
        int dias = (int)Math.ceil(intervalo.duracao().toHours() / 24.0);
        long custoTotal = getModelo(modelo).getPreco() * dias;
    }
}
