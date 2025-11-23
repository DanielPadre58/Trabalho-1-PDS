package aluguer.estacao;

import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import aluguer.viatura.ViaturaIndisponivel;
import pds.tempo.HorarioSemanal;

import java.time.LocalDateTime;
import java.util.List;

public interface Estacao {
    String getId();

    String getNome();
    
    public HorarioSemanal getHorario();
    
    public boolean estaAberta(LocalDateTime hora);
    
    public boolean estaAbertaEmExtensao(LocalDateTime hora);

    public boolean estaAbertaComExtensao(LocalDateTime hora);
    
    public long getCustoExtensao(long precoVeiculo);

    Estacao getCentral();

    void setCentral(Estacao central);

    void adicionarViatura(Viatura viatura);

    List<Viatura> getViaturas();
    
    List<Categoria> getCategorias();
    
    List<ModeloViatura> getModelos();
    
    List<ViaturaIndisponivel> getViaturasIndisponiveis();
    
    void adicionarViaturaIndisponivel(ViaturaIndisponivel viatura);

    void removerViaturaIndisponivel(ViaturaIndisponivel viatura);
}
