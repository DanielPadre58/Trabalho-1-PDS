package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import pds.tempo.IntervaloTempo;
import pds.util.GeradorCodigos;

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
        return new ArrayList<Viatura>(
                getEstacao(estacao)
                .getViaturas()
                .stream()
                .filter(v -> v.getModelo().getCategoria().equals(categoria))
                .toList()
        );
    }

    public long calcularCustoTotal(String estacao, String modelo,  IntervaloTempo intervalo, boolean daCentral) {
        long precoDiario = getModelo(modelo).getPreco();
        int dias = (int)Math.ceil(intervalo.duracao().toHours() / 24.0);
        if (daCentral)
            dias += 2;
        long custoTotal = precoDiario * dias;
        
        Estacao est = getEstacao(estacao);
        if (est.estaAbertaEmExtensao(intervalo.getInicio())) {
            custoTotal += est.getCustoExtensao(precoDiario);
        }
        
        if (est.estaAbertaEmExtensao(intervalo.getFim())) {
            custoTotal += est.getCustoExtensao(precoDiario);
        }

        return custoTotal;
    }
    
    public String gerarCodigoAluguer() {
        String codigo = GeradorCodigos.gerarCodigo(8);
        if(alugueres.stream().anyMatch(a -> a.getId().equals(codigo)))
            return gerarCodigoAluguer();
        
        return codigo;
    }
}
