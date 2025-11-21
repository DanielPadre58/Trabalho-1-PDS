package aluguer.viatura;

import pds.tempo.IntervaloTempo;

import java.time.LocalDateTime;
import java.util.Objects;

public class ViaturaIndisponivel {
    private Viatura viatura;
    private IntervaloTempo periodoIndisponibilidade;
    
    public ViaturaIndisponivel(Viatura viatura, IntervaloTempo periodoIndisponibilidade) {
        this.viatura = Objects.requireNonNull(viatura);
        
        if (!periodoIndisponibilidade.contem(LocalDateTime.now()))
            return;
        this.periodoIndisponibilidade = periodoIndisponibilidade;
    }
}
