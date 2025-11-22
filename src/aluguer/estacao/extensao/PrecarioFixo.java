package aluguer.estacao.extensao;

import pds.util.Validator;

public class PrecarioFixo implements PrecarioExtensao{
    private final long taxaFixa;

    public PrecarioFixo(long taxaFixa) {
        this.taxaFixa = Validator.requirePositiveOrZero(taxaFixa);
    }

    @Override
    public long calcularCusto(long precoVeiculo) {
        return taxaFixa + precoVeiculo;
    }
}
