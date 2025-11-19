package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.Viatura;
import pds.util.Validator;

import java.time.LocalDateTime;
import java.util.Objects;

public class Aluguer {
    private final  Viatura viatura;
    private final Estacao estacao;
    private final long custoTotal;
    private final LocalDateTime inicio;
    
    public Aluguer(Viatura viatura, Estacao estacao, long custoTotal, LocalDateTime inicio) {
        this.viatura = Objects.requireNonNull(viatura);
        this.estacao = Objects.requireNonNull(estacao);
        this.custoTotal = Validator.requirePositiveOrZero(custoTotal);
        this.inicio = Objects.requireNonNull(inicio);
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
    
    public LocalDateTime getInicio() {
        return inicio;
    }
}
