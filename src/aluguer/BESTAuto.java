package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import aluguer.viatura.ViaturaIndisponivel;
import app.ResultadoPesquisa;
import pds.tempo.IntervaloTempo;
import pds.util.GeradorCodigos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
                .filter(estacao -> estacao.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada: " + id));
    }

    public List<Estacao> getEstacoes() {
        return Collections.unmodifiableList(estacoes);
    }

    public ModeloViatura getModelo(String id) {
        return modelos.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado: " + id));
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

    public List<ResultadoPesquisa> pesquisarViaturasComCentral(Categoria categoria, String estacao, IntervaloTempo intervalo) {
        Estacao est = getEstacao(estacao);
        
        Set<String> modelosEncontrados = new HashSet<>();
        List<Viatura> resultados = est.getViaturasDisponiveis(intervalo)
                .stream()
                .filter(viatura -> viatura.getModelo().getCategoria().equals(categoria))
                .filter(viatura -> !modelosEncontrados.contains(viatura.getModelo().getId()))
                .peek(viatura -> modelosEncontrados.add(viatura.getModelo().getId()))
                .collect(Collectors.toList());
        

        if (est.getCentral() != null) 
            est.getCentral().getViaturasDisponiveis(intervalo)
                    .stream()
                    .filter(viatura -> viatura.getModelo().getCategoria().equals(categoria))
                    .filter(viatura -> !modelosEncontrados.contains(viatura.getModelo().getId()))
                    .forEach(resultados::add);

        return resultados
                .stream()
                .map(viatura -> {
                    boolean eDaCentral = eDaCentral(est.getId(), viatura.getMatricula());
                    long custoTotal = CalculadoraPrecos.calcularCustoTotal(est, viatura.getModelo(), intervalo, eDaCentral);
                    return new ResultadoPesquisa(
                            viatura,
                            custoTotal,
                            intervalo, 
                            eDaCentral
                    );
                })
                .toList();
    }

    public List<ViaturaIndisponivel> pesquisarIndisponibilidades(Estacao estacao, String matricula) {
        return estacao.getViaturasIndisponiveis().stream()
                .filter(viatura -> viatura.getViatura().getMatricula().equals(matricula))
                .collect(Collectors.toCollection(ArrayList::new));
    }

 

    public String gerarCodigoAluguer() {
        String codigo = GeradorCodigos.gerarCodigo(8);
        if (alugueres.stream().anyMatch(aluguer -> aluguer.getId().equals(codigo)))
            return gerarCodigoAluguer();

        return codigo;
    }

    public boolean eDaCentral(String estacao, String matricula) {
        Estacao est = getEstacao(estacao);

        if (est.getCentral() == null)
            return false;

        return est.getCentral().getViaturas()
                .stream()
                .anyMatch(viatura -> viatura.getMatricula().equals(matricula));
    }
}
