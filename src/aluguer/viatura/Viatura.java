package aluguer.viatura;

import aluguer.estacao.Estacao;
import pds.util.Validator;

import java.util.Objects;

public class Viatura {
    private final String matricula;
    private final ModeloViatura modelo;
    private final Estacao estacao;
    
    public Viatura(String matricula, ModeloViatura modelo, Estacao estacao) {
        this.matricula = Validator.requireNonBlankTrimmed(matricula);
        this.modelo = Objects.requireNonNull(modelo);
        this.estacao = Objects.requireNonNull(estacao);
        estacao.adicionarViatura(this);
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public ModeloViatura getModelo() {
        return modelo;
    }
    
    public Estacao getEstacao() {
        return estacao;
    }
}
