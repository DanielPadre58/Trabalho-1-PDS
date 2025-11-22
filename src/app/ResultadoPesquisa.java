package app;

import aluguer.viatura.Viatura;
import pds.tempo.IntervaloTempo;
import pds.util.Validator;

import java.util.Objects;

public class ResultadoPesquisa {
    private final Viatura viatura;
    private final long custoTotal;
    private final IntervaloTempo intervalo;
    
    public ResultadoPesquisa(Viatura viatura, long custoTotal, IntervaloTempo intervalo) {
        this.viatura = Objects.requireNonNull(viatura);
        this.custoTotal = Validator.requirePositiveOrZero(custoTotal);
        this.intervalo = Objects.requireNonNull(intervalo);
    }
    
    public Viatura getViatura() {
        return viatura;
    }
    
    public long getCustoTotal() {
        return custoTotal;
    }
    
    public IntervaloTempo getIntervalo() {
        return intervalo;
    }
}
