package aluguer.estacao;

import aluguer.viatura.Viatura;
import aluguer.viatura.ViaturaIndisponivel;
import pds.tempo.HorarioSemanal;

import java.util.List;

public interface Estacao {
    String getId();

    String getNome();
    
    public HorarioSemanal getHorario();

    Estacao getCentral();

    void setCentral(Estacao central);

    void adicionarViatura(Viatura viatura);

    List<Viatura> getViaturas();
    
    List<ViaturaIndisponivel> getViaturasIndisponiveis();
    
    void adicionarViaturaIndisponivel(ViaturaIndisponivel viatura);
}
