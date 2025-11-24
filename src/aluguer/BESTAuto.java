package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import aluguer.viatura.ViaturaIndisponivel;
import pds.tempo.IntervaloTempo;
import pds.util.GeradorCodigos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public void adicionarAluguer(Aluguer aluguer, boolean daCentral) {
        alugueres.add(aluguer);
        Estacao estacao = aluguer.getEstacao();
        ViaturaIndisponivel viaturaIndisponivel = new ViaturaIndisponivel(aluguer);

        if (!daCentral) {
            estacao.adicionarViaturaIndisponivel(viaturaIndisponivel);
            return;
        }
        
        Estacao central = estacao.getCentral();
        IntervaloTempo periodoRecolha = new IntervaloTempo(
                LocalDateTime.of(aluguer.getPeriodoAluguer().getInicio().minusDays(1).toLocalDate(), LocalTime.of(17, 0)),
                aluguer.getPeriodoAluguer().getInicio());
        ViaturaIndisponivel indisponibilidadeRecolha = new ViaturaIndisponivel(
                aluguer.getViatura(),
                periodoRecolha,
                "Movendo a viatura de " + central.getNome() + " para " + estacao.getNome()
        );

        IntervaloTempo periodoEntrega = new IntervaloTempo(
                aluguer.getPeriodoAluguer().getFim(),
                LocalDateTime.of(aluguer.getPeriodoAluguer().getFim().plusDays(1).toLocalDate(), LocalTime.of(9, 30))
        );
        ViaturaIndisponivel indisponibilidadeEntrega = new ViaturaIndisponivel(
                aluguer.getViatura(),
                periodoEntrega,
                "Movendo a viatura de " + estacao.getNome() + " para " + central.getNome()
        );

        central.adicionarViaturaIndisponivel(viaturaIndisponivel);
        central.adicionarViaturaIndisponivel(indisponibilidadeRecolha);
        central.adicionarViaturaIndisponivel(indisponibilidadeEntrega);
    }

    public List<Viatura> pesquisarViaturas(Categoria categoria, String estacao, IntervaloTempo intervalo) {
        return new ArrayList<Viatura>(
                getEstacao(estacao)
                        .getViaturasDisponiveis(intervalo)
                        .stream()
                        .filter(v -> v.getModelo().getCategoria().equals(categoria))
                        .toList()
        );
    }
    
    public List<ViaturaIndisponivel> pesquisarIndisponibilidades(Estacao estacao, String matricula) {
        return estacao.getViaturasIndisponiveis().stream()
                .filter(v -> v.getViatura().getMatricula().equals(matricula))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    private long calcularCustoDiario(ModeloViatura modelo, IntervaloTempo intervalo) {
        int dias = (int) Math.ceil(intervalo.duracao().toHours() / 24.0);
        return modelo.getPreco() * dias;
    }
    
    private long calcularTaxaCentral(ModeloViatura modelo) {
        return modelo.getPreco() * 2;
    }
    
    private long calcularCustoExtensao(Estacao estacao, ModeloViatura modelo, IntervaloTempo intervalo) {
        long custoExtensao = 0;
        if (estacao.estaAbertaEmExtensao(intervalo.getInicio())) {
            custoExtensao += estacao.getCustoExtensao(modelo.getPreco());
        }

        if (estacao.estaAbertaEmExtensao(intervalo.getFim())) {
            custoExtensao += estacao.getCustoExtensao(modelo.getPreco());
        }
        
        return custoExtensao;
    }

    public long calcularCustoTotal(String estacao, String modelo, IntervaloTempo intervalo, boolean daCentral) {
        Estacao est = getEstacao(estacao);
        ModeloViatura mod = getModelo(modelo);
        
        return daCentral ? calcularTaxaCentral(mod) + calcularCustoExtensao(est, mod, intervalo) + calcularCustoDiario(mod, intervalo) : 
                calcularCustoDiario(mod, intervalo)  + calcularCustoExtensao(est, mod, intervalo);
    }

    public String gerarCodigoAluguer() {
        String codigo = GeradorCodigos.gerarCodigo(8);
        if (alugueres.stream().anyMatch(a -> a.getId().equals(codigo)))
            return gerarCodigoAluguer();

        return codigo;
    }
    
    public boolean eDaCentral(String estacao, String matricula) {
        Estacao est = getEstacao(estacao);
        return est.getCentral().getViaturas()
                .stream()
                .anyMatch(v -> v.getMatricula().equals(matricula));
    }
}
