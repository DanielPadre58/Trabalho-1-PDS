package aluguer.viatura;

import pds.util.Validator;

public class ModeloViatura {
    private final String id;
    private final String modelo;
    private final Categoria categoria;
    private final String marca;
    private final int lotacao;
    private final int bagagem;
    private final long preco;
    
    public ModeloViatura(String id, String modelo, Categoria categoria, String marca, int lotacao, int bagagem, long preco) {
        this.id = Validator.requireNonBlankTrimmed(id);
        this.modelo = Validator.requireNonBlankTrimmed(modelo);
        this.categoria = categoria;
        this.marca = Validator.requireNonBlankTrimmed(marca);
        this.lotacao = Validator.requirePositive(lotacao);
        this.bagagem = Validator.requirePositive(bagagem);
        this.preco = Validator.requirePositive(preco);
    }
    
    public String getId() {
        return id;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public int getLotacao() {
        return lotacao;
    }
    
    public int getBagagem() {
        return bagagem;
    }
    
    public long getPreco() {
        return preco;
    }
}
