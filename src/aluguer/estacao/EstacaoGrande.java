package aluguer.estacao;

import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import aluguer.viatura.ViaturaIndisponivel;
import pds.tempo.HorarioSemanal;
import pds.tempo.IntervaloTempo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EstacaoGrande implements Estacao {
    private final String id;
    private final String nome;
    private Estacao central;
    private final List<Viatura> viaturas;
    private final List<ViaturaIndisponivel> viaturasIndisponiveis;

    public EstacaoGrande(String id, String nome) {
        this.id = Objects.requireNonNull(id);
        this.nome = nome;
        this.viaturas = new ArrayList<>();
        this.viaturasIndisponiveis = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNome(){
        return nome;
    }

    @Override
    public HorarioSemanal getHorario() {
        return HorarioSemanal.sempreAberto();
    }

    @Override
    public boolean estaAberta(LocalDateTime hora) {
        return true;
    }
    
    @Override
    public boolean estaAbertaEmExtensao(LocalDateTime hora) {
        return false;
    }

    @Override
    public boolean estaAbertaComExtensao(LocalDateTime hora) {
        return true;
    }

    @Override
    public long getCustoExtensao(long precoVeiculo) {
        return 0;
    }
    
    @Override
    public Estacao getCentral() {
        return central;
    }

    @Override
    public void setCentral(Estacao central) {
        this.central = central;
    }

    @Override
    public void adicionarViatura(Viatura viatura) {
        viaturas.add(Objects.requireNonNull(viatura));
    }

    @Override
    public List<Viatura> getViaturas() {
        return Collections.unmodifiableList(viaturas);
    }

    @Override
    public List<Viatura> getViaturasDisponiveis(IntervaloTempo periodo) {
        List<Viatura> disponiveis = new ArrayList<>();
        for(Viatura viatura : viaturas) {
            if(viaturasIndisponiveis
                    .stream()
                    .noneMatch(viaturaIndisponivel -> viaturaIndisponivel.getViatura().equals(viatura) &&
                            viaturaIndisponivel.estaIndisponivel(periodo))) {
                disponiveis.add(viatura);
            }
        }
        
        return Collections.unmodifiableList(disponiveis);
    }

    @Override
    public List<Categoria> getCategorias() {
        return viaturas
                .stream()
                .map(viatura -> viatura.getModelo().getCategoria())
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<ModeloViatura> getModelos() {
        return viaturas
                .stream()
                .map(Viatura::getModelo)
                .distinct()
                .collect(Collectors.toCollection(ArrayList<ModeloViatura>::new));
    }

    @Override
    public List<ViaturaIndisponivel> getViaturasIndisponiveis() {
        return Collections.unmodifiableList(viaturasIndisponiveis);
    }
    
    @Override
    public void adicionarViaturaIndisponivel(ViaturaIndisponivel viatura) {
        if(!viaturas.contains(viatura.getViatura()))
            return;
        
        viaturasIndisponiveis.add(viatura);
    }
    
    @Override
    public void removerViaturaIndisponivel(ViaturaIndisponivel viatura) {
        viaturasIndisponiveis.remove(viatura);
    }
}
