package aluguer;

import aluguer.estacao.EstacaoGrande;
import aluguer.viatura.Viatura;
import pds.util.Validator;

import java.time.LocalDateTime;
import java.util.Objects;

public class Aluguer {
    private final  Viatura viatura;
    private final EstacaoGrande estacao;
    private final long custoTotal;
    private final LocalDateTime inicio;
    
    public Aluguer(Viatura viatura, EstacaoGrande estacao, long custoTotal, LocalDateTime inicio) {
        this.viatura = Objects.requireNonNull(viatura);
        this.estacao = Objects.requireNonNull(estacao);
        this.custoTotal = Validator.requirePositiveOrZero(custoTotal);
        this.inicio = Objects.requireNonNull(inicio);
    }
    
    public Viatura getViatura() {
        return viatura;
    }
    
    public EstacaoGrande getEstacao() {
        return estacao;
    }
    
    public long getCustoTotal() {
        return custoTotal;
    }
    
    public LocalDateTime getInicio() {
        return inicio;
    }
}
