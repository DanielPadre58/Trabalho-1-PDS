package aluguer.viatura;

import aluguer.estacao.EstacaoGrande;
import pds.util.Validator;

import java.util.Objects;

public class Viatura {
    private final String matricula;
    private final ModeloViatura modelo;
    private final EstacaoGrande estacao;
    
    
    public Viatura(String matricula, ModeloViatura modelo, EstacaoGrande estacao, String marca, int lote) {
        this.matricula = Validator.requireNonBlankTrimmed(matricula);
        this.modelo = Objects.requireNonNull(modelo);
        this.estacao = Objects.requireNonNull(estacao);
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public ModeloViatura getModelo() {
        return modelo;
    }
    
    public EstacaoGrande getEstacao() {
        return estacao;
    }
}
