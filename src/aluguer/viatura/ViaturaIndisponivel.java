package aluguer.viatura;

import aluguer.Aluguer;
import pds.tempo.IntervaloTempo;
import pds.util.Validator;

import java.util.Objects;

public class ViaturaIndisponivel {
    private final Viatura viatura;
    private final IntervaloTempo periodoIndisponibilidade;
    private final String motivo;
    
    public ViaturaIndisponivel(Viatura viatura, IntervaloTempo periodoIndisponibilidade, String motivo) {
        this.viatura = Objects.requireNonNull(viatura);
        this.periodoIndisponibilidade = Objects.requireNonNull(periodoIndisponibilidade);
        this.motivo = Validator.requireNonBlankTrimmed(motivo);
    }
    
    public ViaturaIndisponivel(Aluguer aluguer){
        this(aluguer.getViatura(), aluguer.getPeriodoAluguer(), "Aluguer " + aluguer.getId());
    }
    
    public Viatura getViatura() {
        return viatura;
    }
    
    public IntervaloTempo getPeriodoIndisponibilidade() {
        return periodoIndisponibilidade;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public boolean estaIndisponivel(IntervaloTempo periodo) {
        return periodoIndisponibilidade.engloba( periodo);
    }
}
