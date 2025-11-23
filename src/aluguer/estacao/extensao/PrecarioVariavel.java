package aluguer.estacao.extensao;

public class PrecarioVariavel implements PrecarioExtensao{
    @Override
    public long calcularCusto(long precoVeiculo) {
        return precoVeiculo / 2;
    }
}
