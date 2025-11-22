package app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import aluguer.BESTAuto;
import aluguer.estacao.Estacao;
import aluguer.estacao.EstacaoGrande;
import aluguer.estacao.EstacaoMedia;
import aluguer.estacao.EstacaoPequena;
import aluguer.estacao.extensao.*;
import aluguer.viatura.Categoria;
import aluguer.viatura.ModeloViatura;
import aluguer.viatura.Viatura;
import app.LeitorFicheiros.Bloco;
import pds.tempo.HorarioDiario;
import pds.tempo.HorarioSemanal;

/**
 * Clase que arranca com o sistema
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BESTAuto albi = new BESTAuto();
		// ler os dados
		readEstacoes(albi, "dados/estacoes.txt");
		readModelos(albi, "dados/modelos.txt");
		readViaturas(albi, "dados/carros.txt");

		// ver o tamanho do écran onde criar as janelas
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// criar as janelas
		JanelaEstacoes je = new JanelaEstacoes(albi);
		JanelaAluguer ja = new JanelaAluguer(albi);

		// posicioná-las ao centro
		int posx1 = (screenSize.width - je.getWidth() - ja.getWidth()) / 2;
		int posx2 = posx1 + je.getWidth() + 10;
		je.setLocation(posx1, 50);
		ja.setLocation(posx2, 50);

		// torná-las visiveis
		je.setVisible(true);
		ja.setVisible(true);
	}

	/**
	 * método para ler o ficheiro com a informação das estações
	 * 
	 * @param best         a companhia
	 * @param estacoesFile o nome do ficheiro com a informação
	 */
	private static void readEstacoes(BESTAuto best, String estacoesFile) {
		try {
			List<LeitorFicheiros.Bloco> blocos = LeitorFicheiros.lerFicheiro(estacoesFile);

			for (LeitorFicheiros.Bloco b : blocos) {
				String id = b.getValor("id");
				String nome = b.getValor("nome");
				HorarioSemanal horario = processarHorario(b);
				Estacao central = processarCentral(best, b);
				Extensao extensao = processarExtensao(b);
				PrecarioExtensao precarioExtensao = processarPagamentoExtensao(b);
				Estacao estacao;
				String tipoHorario = b.getValor("horario");
				if (tipoHorario.equals("total")) {
					estacao = new EstacaoGrande(id, nome);
				} else if (tipoHorario.equals("alargado")) {
					estacao = new EstacaoMedia(id, nome, horario, extensao, precarioExtensao);
				} else {
					estacao = new EstacaoPequena(id, nome, horario, extensao, precarioExtensao);
				}

				if(central != null)
					estacao.setCentral(central);
				
				best.adicionarEstacao(estacao);
			}
			
		} catch (IOException e) {
			System.out.println("Erro na leitura do ficheiro " + estacoesFile);
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Processa as informações sobre como calcular o extra por haver extensão de
	 * horário
	 * 
	 * @param b O bloco com a informação a processar
	 */
	private static PrecarioExtensao processarPagamentoExtensao(Bloco b) {
		String tipoPrecario = b.getValor("preco_extensao");

		// se não tem esta chave é porque não tem extensão
		if (tipoPrecario == null) {
			return null;
		} else if (tipoPrecario.equals("taxa")) {
			return new PrecarioFixo(Long.parseLong(b.getOpcoes("preco_extensao")[0]));
		} else if (tipoPrecario.equals("variavel")) {
			return new PrecarioVariavel();
		}
		// nunca devia chegar aqui, mas caso alguma coisa corra mal...
		throw new UnsupportedOperationException("Campo desconhecido: " + tipoPrecario);
	}

	/**
	 * Processa as informações sobre como proceder à extensão de horário
	 * 
	 * @param b o bloco com a informação a processar
	 */
	private static Extensao processarExtensao(Bloco b) {
		String tipoExtensao = b.getValor("extensao");
		// se não tem esta chave é porque não tem extensão
		if (tipoExtensao == null) {
			return null;
		} else if (tipoExtensao.equals("horas")) {
			return new ExtensaoPorHora(Integer.parseInt(b.getOpcoes("extensao")[0]));
		} else if (tipoExtensao.equals("total")) {
			return new ExtensaoTotal();
		}
		// nunca devia chegar aqui
		throw new UnsupportedOperationException("Campo desconhecido: " + tipoExtensao);
	}

	/**
	 * Processa as informações sobre se tem central ou não
	 * 
	 * @param b o bloco com a informação a processar
	 */
	private static Estacao processarCentral(BESTAuto best, Bloco b) {
		if (b.getValor("central") != null) {
			String central = b.getValor("central");
			return best.getEstacao(central);
		} else {
			// não tem central
			return null;
		}
	}

	/**
	 * Processa as informações sobre o horário
	 * 
	 * @param b o bloco com a informação a processar
	 * @return o horário semanal
	 */
	private static HorarioSemanal processarHorario(LeitorFicheiros.Bloco info) {
		String tipoHorario = info.getValor("horario");
		if (tipoHorario.equals("total"))
			return HorarioSemanal.sempreAberto();
		String dias[] = info.getOpcoes("horario");
		HorarioDiario hds[] = new HorarioDiario[dias.length];
		for (int i = 0; i < dias.length; i++) {
			String horas[] = dias[i].split("-");
			LocalTime ini = LocalTime.parse(horas[0]);
			LocalTime fim = LocalTime.parse(horas[1]);
			hds[i] = HorarioDiario.deAte(ini, fim);
		}
		if (tipoHorario.equals("alargado"))
			return HorarioSemanal.of(hds);
		if (tipoHorario.equals("semanal"))
			return HorarioSemanal.semanaUtil(hds);

		// nunca devia chegar aqui
		throw new UnsupportedOperationException("Campo desconhecido: " + tipoHorario);
	}

	/**
	 * Lê o ficheiro com as informações sobre os modelos
	 * 
	 * @param best a companhia
	 * @param file o nome do ficheiro
	 */
	private static void readModelos(BESTAuto best, String file) {
		try {
			List<LeitorFicheiros.Bloco> blocos = LeitorFicheiros.lerFicheiro(file);
			for (LeitorFicheiros.Bloco b : blocos) {
				String id = b.getValor("id");
				String modelo = b.getValor("modelo");
				Categoria categoria = Categoria.valueOf(b.getValor("categoria"));
				String marca = b.getValor("marca");
				int lotacao = Integer.parseInt(b.getValor("lotacao"));
				int bagagem = Integer.parseInt(b.getValor("bagagem"));
				long preco = Integer.parseInt(b.getValor("preco"));
				ModeloViatura modeloViatura = new ModeloViatura(id, modelo, categoria, marca, lotacao, bagagem, preco);
				best.adicionarModelo(modeloViatura);
			}
		} catch (IOException e) {
			System.out.println("Erro na leitura do ficheiro " + file);
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Lê o ficheiro com as informações sobre as viaturas
	 * 
	 * @param best a companhia
	 * @param file o nome do ficheiro
	 */
	private static void readViaturas(BESTAuto best, String file) {
		try {
			List<LeitorFicheiros.Bloco> blocos = LeitorFicheiros.lerFicheiro(file);
			for (LeitorFicheiros.Bloco b : blocos) {
				String matricula = b.getValor("matricula");
				String modelo = b.getValor("modelo");
				String estacao = b.getValor("estacao");
				ModeloViatura modeloViatura = best.getModelo(modelo);
				Estacao estacaoViatura = best.getEstacao(estacao);
				Viatura viatura = new Viatura(matricula, modeloViatura, estacaoViatura);
				// TODO completar o método

			}
		} catch (IOException e) {
			System.out.println("Erro na leitura do ficheiro " + file);
			e.printStackTrace();
			System.exit(0);
		}
	}
}
