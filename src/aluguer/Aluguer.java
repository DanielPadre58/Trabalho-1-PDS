package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.Viatura;
import pds.tempo.IntervaloTempo;
import pds.util.Validator;

import java.util.Objects;

public class Aluguer {
    private final String id;
    private final  Viatura viatura;
    private final Estacao estacao;
    private final long custoTotal;
    private final IntervaloTempo periodoAluguer;
    
    public Aluguer(String id, Viatura viatura, Estacao estacao, long custoTotal, IntervaloTempo periodoAluguer) {
        this.id = Validator.requireNonBlankTrimmed(id);
        this.viatura = Objects.requireNonNull(viatura);
        this.estacao = Objects.requireNonNull(estacao);
        this.custoTotal = Validator.requirePositiveOrZero(custoTotal);
        this.periodoAluguer = Objects.requireNonNull(periodoAluguer);
    }
    
    public String getId() {
        return id;
    }
    
    public Viatura getViatura() {
        return viatura;
    }
    
    public Estacao getEstacao() {
        return estacao;
    }
    
    public long getCustoTotal() {
        return custoTotal;
    }
    
    public IntervaloTempo getPeriodoAluguer() {
        return periodoAluguer;
    }
}
