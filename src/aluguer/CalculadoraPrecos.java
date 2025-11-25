package aluguer;

import aluguer.estacao.Estacao;
import aluguer.viatura.ModeloViatura;
import pds.tempo.IntervaloTempo;

public class CalculadoraPrecos {
    private static long calcularCustoDiario(ModeloViatura modelo, IntervaloTempo intervalo) {
        int dias = (int) Math.ceil(intervalo.duracao().toHours() / 24.0);
        return modelo.getPreco() * dias;
    }

    private static long calcularTaxaCentral(ModeloViatura modelo) {
        return modelo.getPreco() * 2;
    }

    private static long calcularCustoExtensao(Estacao estacao, ModeloViatura modelo, IntervaloTempo intervalo) {
        long custoExtensao = 0;
        if (estacao.estaAbertaEmExtensao(intervalo.getInicio())) {
            custoExtensao += estacao.getCustoExtensao(modelo.getPreco());
        }

        if (estacao.estaAbertaEmExtensao(intervalo.getFim())) {
            custoExtensao += estacao.getCustoExtensao(modelo.getPreco());
        }

        return custoExtensao;
    }

    public static long calcularCustoTotal(Estacao estacao, ModeloViatura modelo, IntervaloTempo intervalo, boolean daCentral) {
        return daCentral ? calcularTaxaCentral(modelo) + calcularCustoExtensao(estacao, modelo, intervalo) + calcularCustoDiario(modelo, intervalo) :
                calcularCustoDiario(modelo, intervalo) + calcularCustoExtensao(estacao, modelo, intervalo);
    }
}
