package aluguer.viatura;

import pds.util.Validator;

public class ModeloViatura {
    private final String id;
    private final String modelo;
    private final Categoria categoria;
    private final int lotacao;
    private final int bagagem;
    private final long preco;
    
    public ModeloViatura(String id, String modelo, Categoria categoria, int lotacao, int bagagem, long preco) {
        this.id = Validator.requireNonBlankTrimmed(id);
        this.modelo = Validator.requireNonBlankTrimmed(modelo);
        this.categoria = categoria;
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
