package aluguer.estacao.extensao;

import pds.util.Validator;

public class PrecarioVariavel implements PrecarioExtensao{
    @Override
    public long calcularCusto(long precoVeiculo) {
        return precoVeiculo / 2;
    }
}
