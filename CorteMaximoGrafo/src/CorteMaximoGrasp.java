import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esta classe � respons�vel por ler um arquivo, criar um grafo, e encontrar o
 * conjunto de corte m�ximo deste grafo. Para mais detalhes, acesse
 * <code>Trabalho Corte M�ximo GRASP.odt</code>.
 * 
 * @author Vitor Alc�ntara de Almeida
 * 
 */
public class CorteMaximoGrasp {
	/**
	 * Este enumerador � usado para identificar o conjunto ao qual cada v�rtice
	 * pertence. Se um v�rtice estiver marcado como <code>PRIMEIRO</code>, ent�o
	 * o v�rtice pertence ao primeiro conjunto. Se ele estiver marcado como
	 * <code>SEGUNDO</code>, ent�o o v�rtice pertence ao segundo conjunto. Se
	 * ele estiver marcado como <code>NENHUM</code>, ent�o o v�rtice pertence a
	 * nenhum conjunto. Os conjuntos <code>PRIMEIRO</code> e
	 * <code>SEGUNDO</code> s�o disjuntos e a uni�o de ambos forma o conjunto de
	 * v�rtices do grafo.
	 * 
	 * @author Vitor
	 * 
	 */
	public enum IDConjunto {
		PRIMEIRO, SEGUNDO, NENHUM
	}

	/**
	 * Esta vari�vel cont�m o valor alfa que � usado no c�lculo da lista de
	 * candidatos restritos. Veja o documento para mais detalhes.
	 */
	final double alfa;

	/**
	 * Esta lista armazena o conjunto de v�rtices do grafo. A estrutura de dados
	 * para armazenar o elemento � um vetor.
	 */
	final List<Vertice> conjuntoDeVertices;

	/**
	 * Esta lista armazena o conjunto de arestas do grafo. A estrutura de dados
	 * para armazenar o elemento � um vetor.
	 */
	final List<Aresta> conjuntoDeArestas;

	/**
	 * Esta lista armazena o wmax conjunto de arestas encontrado no grafo. A
	 * estrutura de dados para armazenar o elemento � um vetor.
	 */
	List<Aresta> corteMaximo = new ArrayList<Aresta>();

	/**
	 * Esta classe representa um v�rtice. Ela possui um identificador, uma lista
	 * de arestas que se ligam a este v�rtice, um identificador de conjunto e um
	 * m�todo que retorna uma string representando o v�rtice.
	 */
	static class Vertice implements Comparable<Vertice> {
		int id;
		List<Aresta> arestas = new ArrayList<Aresta>();
		IDConjunto idConjunto;
		int custo = 0;

		@Override
		public String toString() {
			return "Vertice [id=" + id + ", arestas=" + arestas + "]";
		}

		@Override
		public int compareTo(Vertice o) {
			if (this.custo > o.custo) {
				return 1;
			} else if (this.custo == o.custo) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Esta classe representa uma aresta. Ela possui um identificador, os dois
	 * v�rtices que s�o ligadas pela aresta, e um m�todo que retorna uma string
	 * representando a aresta.
	 */
	static class Aresta {
		int id;
		Vertice v1;
		Vertice v2;

		@Override
		public String toString() {
			return "Aresta [id=" + id + ", v1=" + v1 + ", v2=" + v2 + "]";
		}
	}

	/**
	 * 
	 * Esta classe representa um grafo. Ela possui uma lista de arestas e uma
	 * lista de v�rtices.
	 */
	static class Grafo {
		List<Vertice> nos = new ArrayList<Vertice>();
		List<Aresta> arestas = new ArrayList<Aresta>();
	}

	/**
	 * Construtor da classe. Esta classe � respons�vel por calcular o corte
	 * m�ximo de um grafo usando a meta-heur�stica GRASP.
	 * 
	 * @param g
	 *            O grafo que ser� processado
	 */
	public CorteMaximoGrasp(Grafo g, double alfa) {
		this.conjuntoDeArestas = g.arestas;
		this.conjuntoDeVertices = g.nos;
		this.alfa = alfa;
	}

	/**
	 * Procedimento GRASP inicial. Este m�todo � composto de duas etapas: Uma
	 * etapa de constru��o, no qual utiliza-se um algoritmo guloso rand�mico, e
	 * uma etapa de busca local. A fase de constru��o adiciona um elemento por
	 * vez em um conjunto que finaliza com a representa��o de uma solu��o
	 * poss�vel. A cada itera��o, um elemento � selecionado aleatoriamente de
	 * lista de candidatos restritos, no qual seus elementos s�o bem
	 * selecionados de acordo com alguma fun��o gulosa. Ap�s encontrar uma
	 * solu��o plaus�vel, o procedimento de busca local tenta melhor�-lo
	 * produzindo uma solu��o �tima local em rela��o a alguma estrutura de
	 * vizinhan�a. As fases de constru��o e de busca local s�o aplicadas
	 * repetidamente. A melhor solu��o encontrada � retornada como uma
	 * aproxima��o para a solu��o �tima.
	 * 
	 * @param maximoIteracoes
	 */
	public void procedimentoGRASP(int maximoIteracoes) {
		for (int i = 0; i < maximoIteracoes; i++) {
			zerarIDConjuntoDosVertices();
			construirSolucaoRandomicaGulosaAdaptativa();
			buscaLocal();
			List<Aresta> corte = calcularArestasdoCorte();
			if (i == 1) {
				corteMaximo = corte;
			} else if (corte.size() > corteMaximo.size()) {
				corteMaximo = corte;
			}
		}
	}

	/**
	 * Este m�todo zera a configura��o do grafo, isto �, todos os v�rtices s�o
	 * marcados que pertencem a nenhum conjunto.
	 */
	private void zerarIDConjuntoDosVertices() {
		for (Vertice v : conjuntoDeVertices) {
			v.idConjunto = IDConjunto.NENHUM;
		}
	}

	/**
	 * Este m�todo calcula o corte dada a configura��o atual (J� est�o definidos
	 * os dois conjuntos complementares de v�rtices do grafo). Ele percorre o
	 * vetor de arestas e adiciona em um cont�iner as arestas que ligam v�rtices
	 * de conjuntos diferentes.
	 * 
	 * @return O conjunto de corte encontrado.
	 */
	private List<Aresta> calcularArestasdoCorte() {
		List<Aresta> corte = new ArrayList<Aresta>();
		for (Aresta a : conjuntoDeArestas) {
			if (a.v1.idConjunto == IDConjunto.PRIMEIRO
					&& a.v2.idConjunto == IDConjunto.SEGUNDO) {
				corte.add(a);
			} else if (a.v1.idConjunto == IDConjunto.SEGUNDO
					&& a.v2.idConjunto == IDConjunto.PRIMEIRO) {
				corte.add(a);
			}
		}
		return corte;
	}

	/**
	 * Etapa de busca local. A fase de busca local � baseada na seguinte
	 * estrutura de vizinhan�a. Sejam X e Y os conjuntos encontrados com a
	 * fun��o gulosa (X,Y). Para cada v�rtice v pertencente a V associa-se tanto
	 * o vizinho (X \ {v}, Y uni�o {v}) se v pertence a X, ou o vizinho (X uniao
	 * {v}, Y \ {v}) caso contr�rio. S�o ent�o investigadas todas as mudan�as
	 * poss�veis. Se uma mudan�a resulta em um corte wmax que o atual, este novo
	 * corte torna-se a nova solu��o tempor�ria. A procura � finalizada ap�s
	 * todos os movimentos serem avaliados e nenhum vizinho melhor for
	 * encontrado.
	 * */
	private void buscaLocal() {
		boolean change = true;
		while (change) {
			change = false;
			for (Vertice v : conjuntoDeVertices) {
				if (v.idConjunto == IDConjunto.PRIMEIRO
						&& ro(IDConjunto.PRIMEIRO, v)
								- ro(IDConjunto.SEGUNDO, v) > 0) {
					v.idConjunto = IDConjunto.SEGUNDO;
					change = true;
				} else if (v.idConjunto == IDConjunto.SEGUNDO
						&& ro(IDConjunto.SEGUNDO, v)
								- ro(IDConjunto.PRIMEIRO, v) > 0) {
					v.idConjunto = IDConjunto.PRIMEIRO;
					change = true;
				}
				if (change)
					break;
			}
		}
	}

	/**
	 * Esta classe tem a fun��o de armazenar os valores wmax e wmin encontrados
	 * na busca gulosa para calcular o valor de corte usado na defini��o da
	 * lista de candidatos restritos. Estas duas vari�veis, wmax e wmin, est�o
	 * detalhados no documento.
	 */
	private class Pair {
		final int wmin;
		final int wmax;

		public Pair(int wmin, int wmax) {
			this.wmin = wmin;
			this.wmax = wmax;
		}
	}

	/**
	 * Etapa gulosa aleat�ria adaptativa do GRASP. Este m�todo primeiramente
	 * ordena aleatoriamente o conjunto de v�rtices, depois define que o
	 * primeiro v�rtice do conjunto de v�rtices � do primeiro conjunto, e, ent�o
	 * percorre-se a lista de v�rtices verificando se cada v�rtice est� ligado
	 * com mais v�rtices do primeiro conjunto ou de seu complemento (conjunto
	 * segundo). Se o v�rtice possuir mais arestas ligados ao primeiro conjunto,
	 * ent�o este v�rtice � adicionado ao segundo conjunto, caso contr�rio, ele
	 * � definido como do primeiro conjunto. Veja o documento para mais
	 * detalhes.
	 * */
	private void construirSolucaoRandomicaGulosaAdaptativa() {
		while (!ehSolucaoFinal()) {
			Pair limites = calcularCustoDosElementos();
			List<Integer> lcr = construirLCR(limites);
			adicionarElementoASolucao(lcr);
		}
	}

	/**
	 * Este m�todo seleciona aleatoriamente um dos elementos da lista de
	 * candidatos restritos e o adiciona ao conjunto solu��o
	 * 
	 * @param listaDeCandidatosRestritos
	 *            a lista de candidatos restritos
	 */
	private void adicionarElementoASolucao(
			List<Integer> listaDeCandidatosRestritos) {
		int randomPos = (int) (Math.random() * (listaDeCandidatosRestritos
				.size() - 1));
		int posicao = listaDeCandidatosRestritos.get(randomPos);
		Vertice v = conjuntoDeVertices.get(posicao);
		if (ro(IDConjunto.PRIMEIRO, v) > ro(IDConjunto.SEGUNDO, v)) {
			v.idConjunto = IDConjunto.SEGUNDO;
		} else {
			v.idConjunto = IDConjunto.PRIMEIRO;
		}
	}

	/**
	 * Este m�todo calcula o custo de cada v�rtice do grafo. Al�m disso, este
	 * m�todo calcula e retorna os valores <code>wmin</code> e <code>wmax</code>
	 * . Veja a documenta��o para mais detalhes.
	 * 
	 * @return Os valores <code>wmin</code> e <code>wmax</code>
	 */
	private Pair calcularCustoDosElementos() {
		int wmin = 0;
		int wmax = 0;
		for (Vertice v : conjuntoDeVertices) {
			if (v.idConjunto == IDConjunto.NENHUM) {
				v.custo = Math.max(ro(IDConjunto.PRIMEIRO, v),
						ro(IDConjunto.SEGUNDO, v));
				if (v.custo < wmin)
					wmin = v.custo;
				if (v.custo > wmax)
					wmax = v.custo;
			}
		}
		return new Pair(wmin, wmax);
	}

	/**
	 * Este m�todo constr�i a lista de candidatos restritos. Veja a documenta��o
	 * para mais detalhes.
	 * 
	 * @param limites
	 *            Os valores <code>wmin</code> e <code>wmax</code>
	 * @return A lista de candidatos restritos.
	 */
	private List<Integer> construirLCR(Pair limites) {
		List<Integer> listaDeCandidatosRestritos = new ArrayList<Integer>();
		int valorDeCorte = (int) (limites.wmin + alfa
				* (limites.wmax - limites.wmax));
		for (int i = 0; i < conjuntoDeVertices.size(); i++) {
			Vertice v = conjuntoDeVertices.get(i);
			if (v.custo >= valorDeCorte) {
				if (v.idConjunto == IDConjunto.NENHUM) {
					listaDeCandidatosRestritos.add(i);
				}
			}
		}
		return listaDeCandidatosRestritos;
	}

	/**
	 * Verifica se a configura��o atual constitui uma solu��o, isto �, todos os
	 * v�rtices est�o definidos como pertencentes ao conjunto PRIMEIRO ou
	 * SEGUNDO.
	 * 
	 * @return true se a configura��o constitui uma solu��o v�lida, falso caso
	 *         contr�rio.
	 */
	private boolean ehSolucaoFinal() {
		for (Vertice v : conjuntoDeVertices) {
			if (v.idConjunto == IDConjunto.NENHUM) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Este m�todo calcula quantas arestas ligam v a v�rtices do conjunto
	 * <code>idConjunto</code>.
	 * 
	 * @param idConjunto
	 *            o identificador do conjunto.
	 * @param v
	 *            o v�rtice avaliado.
	 * @return a quantidade de arestas que liga v a v�rtices do conjunto
	 *         <code>idConjunto</code>
	 */
	private int ro(IDConjunto idConjunto, Vertice v) {
		int count = 0;
		for (Aresta a : v.arestas) {
			Vertice outroVertice = null;
			if (a.v1 == v) {
				outroVertice = a.v2;
			} else if (a.v2 == v) {
				outroVertice = a.v1;
			} else {
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (outroVertice.idConjunto == idConjunto) {
				++count;
			}
		}
		return count;
	}

	/**
	 * Este m�todo cria o grafo a partir do arquivo especificado. Ele armazena
	 * os v�rtices e arestas em dois vetores distintos, armazena em cada aresta
	 * a refefer�ncia aos dois v�rtices que ela liga e em cada v�rtice as
	 * arestas que se ligam a ele.
	 * <p>
	 * Na primeira linha do arquivo deve-se conter o n�mero de v�rtices e o
	 * n�mero de arestas, e, deve-se ter, na pr�xima linha e nas suas
	 * subsequentes, o id de 2 v�rtices que est�o diretamente ligados por uma
	 * aresta.
	 * <p>
	 * Por exemplo:
	 * <p>
	 * 2 3 <br>
	 * 1 2 <br>
	 * 2 3 <br>
	 * 1 3
	 * 
	 * 
	 * @param arquivo
	 *            O arquivo onde se encontra o grafo a ser processado
	 * @return A estrutura {@link Grafo} que cont�m o vetor de v�rtices e de
	 *         arestas
	 * @throws FileNotFoundException
	 *             Se o arquivo n�o foi encontrado no sistema
	 */
	public static Grafo extrairGrafoDeArquivo(String arquivo)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(new File(arquivo)));
		int numeroDeVertices = scanner.nextInt();
		int numeroDeArestas = scanner.nextInt();
		List<Vertice> nos = new ArrayList<Vertice>(numeroDeVertices);
		List<Aresta> arestas = new ArrayList<Aresta>(numeroDeArestas);
		int i = 1;
		while (scanner.hasNextInt()) {
			int v1 = scanner.nextInt();
			int v2 = scanner.nextInt();
			if (scanner.hasNext())
				scanner.nextLine();
			Vertice no1 = null;
			Vertice no2 = null;
			for (Vertice no : nos) {
				if (no.id == v1) {
					no1 = no;
				} else if (no.id == v2) {
					no2 = no;
				}
			}
			if (no1 == null) {
				no1 = new Vertice();
				no1.id = v1;
				nos.add(no1);
			}
			if (no2 == null) {
				no2 = new Vertice();
				no2.id = v2;
				nos.add(no2);
			}
			Aresta a = new Aresta();
			a.id = i;
			a.v1 = no1;
			a.v2 = no2;
			no1.arestas.add(a);
			no2.arestas.add(a);
			arestas.add(a);
			++i;
		}
		Grafo g = new Grafo();
		g.arestas = arestas;
		g.nos = nos;
		return g;
	}

	/**
	 * O programa come�a sua execu��o atrav�s deste m�todo. O primeiro argumento
	 * � a lista de par�metros que o usu�rio passa linha de comando quando se
	 * come�a a execu��o.
	 * <p>
	 * O programa pode ser ajustado com 4 par�metros:
	 * <p>
	 * t - Caso este par�metro seja adicionado, mostra-se ao final do programa o
	 * tempo gasto na execu��o do mesmo. Por padr�o, n�o mostra-se esta
	 * informa��o.
	 * <p>
	 * a - Caso este par�metro seja adicionado, mostra-se ao final do programa o
	 * tempo gasto na execu��o apenas do algoritmo de corte. Por padr�o, n�o
	 * mostra-se esta informa��o.
	 * <p>
	 * d - Caso este par�metro seja adicionado, mostra-se, al�m do identificador
	 * das arestas do conjunto de corte, o identificador dos v�rtices que s�o
	 * ligados por cada aresta. Por padr�o, mostra-se apenas o identificador das
	 * arestas do conjunto de corte.
	 * <p>
	 * i -Este par�metro define a quantidade de itera��es m�ximas que o GRASP
	 * deve fazer (olhar fun��o GRASP inicial para mais detalhes). Este
	 * par�metro deve ser seguido de dois pontos e um n�mero inteiro positivo
	 * (i:35, por exemplo). Caso este par�metro n�o seja definido, o algoritmo �
	 * executado com 10 itera��es. Importante: Se este par�metro for adicionado,
	 * ele deve ser o �ltimo da lista de par�metros.
	 * <p>
	 * Um exemplo de execu��o do algoritmo �:
	 * <p>
	 * 4 par�metros:
	 * <p>
	 * <code>java CorteMaximo adti:18 "C:\Users\g70.n"</code>
	 * <p>
	 * Nenhum par�metro:
	 * <p>
	 * <code>java CorteMaximo "C:\Users\g70.n"</code>
	 * <p>
	 * O segundo argumento � o caminho do arquivo a ser a processado.
	 * 
	 * @param args
	 *            Os argumentos que s�o passados pela linha de comando
	 * 
	 */
	public static void main(String[] args) {
		boolean calcularTempoTotal = false;
		boolean calcularTempoAlgoritmo = false;
		boolean mostrarVerticesLigados = false;
		int numberOfIterations = 10;
		int i;
		double alfa = 0.5;
		if (args.length == 0) {
			System.out
					.println("Este programa deve ser iniciado com o caminho do arquivo que especifica o grafo no primeiro argumento. Peco desculpas pelo incomodo.");
			return;
		}
		for (i = 0; i < args.length - 1; i++) {
			if (args[i].equals("t")) {
				calcularTempoTotal = true;
			}
			if (args[i].equals("a")) {
				calcularTempoAlgoritmo = true;
			}
			if (args[i].equals("d")) {
				mostrarVerticesLigados = true;
			}
			if (args[i].startsWith("i:")) {
				int startIndex = args[i].indexOf("i:") + 2;
				String intString = args[i].substring(startIndex);
				numberOfIterations = Math.abs(Integer.parseInt(intString));
			}
			if (args[i].startsWith("alfa:")) {
				int startIndex = args[i].indexOf("alfa:") + 5;
				String doubleString = args[i].substring(startIndex);
				alfa = Double.parseDouble(doubleString);
				if (alfa > 1)
					alfa = 1;
				if (alfa < 0)
					alfa = 0;
			}
		}
		i = args.length - 1;
		long tempoInicialTotal = System.currentTimeMillis();
		Grafo g;
		try {
			g = extrairGrafoDeArquivo(args[i]);
			CorteMaximoGrasp c = new CorteMaximoGrasp(g, alfa);
			long tempoInicialAlgoritmo = System.currentTimeMillis();
			c.procedimentoGRASP(numberOfIterations);
			long tempoFinalAlgoritmo = System.currentTimeMillis();

			System.out.println("Tamanho do corte maximo: "
					+ c.corteMaximo.size()
					+ ". ID das arestas do corte m�ximo:");
			if (mostrarVerticesLigados) {
				for (Aresta aresta : c.corteMaximo) {
					System.out.println("Aresta " + aresta.id
							+ ": liga vertice " + aresta.v1.id + " ao vertice "
							+ aresta.v2.id);
				}
			} else {
				for (Aresta aresta : c.corteMaximo) {
					System.out.print(aresta.id + " ");
				}
			}
			long tempoFinalTotal = System.currentTimeMillis();
			System.out.println();
			if (calcularTempoAlgoritmo) {
				long tempoDecorrido = tempoFinalAlgoritmo
						- tempoInicialAlgoritmo;
				System.out.println("Tempo decorrido do algoritmo: "
						+ tempoDecorrido + " milisegundos.");
			}
			if (calcularTempoTotal) {
				long tempoDecorrido = (tempoFinalTotal - tempoInicialTotal);
				System.out.println("Tempo decorrido do programa: "
						+ tempoDecorrido + " milisegundos.");
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("Houve um erro ao ler o arquivo. Por favor, verifique o caminho especificado do mesmo. Obrigado.");
		}
	}

}
