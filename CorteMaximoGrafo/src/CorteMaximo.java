import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esta classe � respons�vel por ler um arquivo, criar um grafo, e encontrar o
 * conjunto de corte m�ximo deste grafo. Para mais detalhes, acesse
 * <code>Trabalho Corte M�ximo.odt</code>.
 * 
 * @author Vitor Alc�ntara de Almeida
 * 
 */
public class CorteMaximo {

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
	 * Esta vari�vel � usada para contar a quantidade de c�lculos de corte
	 * m�ximo que s�o feitos. N�o influenciam no algoritmo, apenas prov� uma
	 * informa��o a mais em rela��o ao processamento da informa��o.
	 */
	public int totalCount = 0;

	/**
	 * esta vari�vel armazena a quantidade de arestas que n�o est� no conjunto
	 * de corte definido em {@link #corteMaximo}.
	 */
	int totalArestasNaoCorte;

	/**
	 * Este m�todo � o construtor da classe {@link CorteMaximo}. Ela s� �
	 * inicializada com um grafo dado como entrada, no qual ela armazena este
	 * grafo em {@link #conjuntoDeArestas} e {@link #conjuntoDeVertices}. Al�m
	 * disso, salva em {@link #totalArestasNaoCorte} a quantidade de arestas do
	 * conjunto de arestas, pois o conjunto de corte m�ximo est� inicialmente
	 * vazia.
	 * 
	 * @param g
	 *            O grafo que ser� processado
	 */
	public CorteMaximo(Grafo g) {
		this.conjuntoDeVertices = g.nos;
		this.conjuntoDeArestas = g.arestas;
		totalArestasNaoCorte = conjuntoDeArestas.size();
	}

	/**
	 * 
	 * Esta classe representa um grafo. Ela possui uma lista de arestas e uma
	 * lista de arestas.
	 */
	static class Grafo {
		List<Vertice> nos = new ArrayList<Vertice>();
		List<Aresta> arestas = new ArrayList<Aresta>();
	}

	/**
	 * Esta classe representa um v�rtice. Ela possui um identificador, uma lista
	 * de arestas que se ligam a este v�rtice e um m�todo que retorna uma string
	 * representando o v�rtice.
	 */
	static class Vertice {
		int id;
		int pos;
		List<Aresta> arestas = new ArrayList<Aresta>();
		boolean primeiroConjunto = false;

		@Override
		public String toString() {
			return "Vertice [id=" + id + ", arestas=" + arestas
					+ ", primeiroConjunto=" + primeiroConjunto + "]";
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
	 * Este m�todo � executado em um n� folha da �rvore e calcula o conjunto de
	 * corte dada uma configura��o atual (os v�rtices j� est�o definidos em seus
	 * respectivos conjuntos).
	 * <p>
	 * O que este m�todo faz � percorrer o conjunto de arestas e verificar, para
	 * cada aresta, se os v�rtices que ele liga est�o em conjuntos distintos. Se
	 * sim, adiciona-se esta aresta em um conjunto de arestas inicialmente vazia
	 * (<code>corteAtual</code>).
	 * <p>
	 * Ao final, Se <code>corteAtual</code> for wmax que o wmax conjunto de
	 * corte encontrado at� o momento (armazenado em {@link #corteMaximo}),
	 * ent�o este torna-se o novo wmax conjunto de corte, e define-se em outra
	 * vari�vel ( {@link #totalArestasNaoCorte}) a quantidade de arestas que n�o
	 * fazem parte do conjunto de corte.
	 */
	private void calcularCorte() {
		List<Aresta> corteAtual = new ArrayList<Aresta>(
				conjuntoDeArestas.size());
		for (Aresta aresta : conjuntoDeArestas) {
			if (aresta.v1.primeiroConjunto == !aresta.v2.primeiroConjunto) {
				corteAtual.add(aresta);
			}
		}
		if (corteAtual.size() > corteMaximo.size()) {
			corteMaximo = corteAtual;
			totalArestasNaoCorte = conjuntoDeArestas.size()
					- corteMaximo.size();
			return;
		}
		return;
	}

	/**
	 * Este m�todo � executado no n� da �rvore, e � dividido em duas partes.
	 * Nesta primeira parte, o v�rtice correspondente ao n� � marcado como sendo
	 * do primeiro conjunto se o o n� atual for o n� 1, e 2 caso contr�rio.
	 * <p>
	 * Depois disso, verifica-se se a quantidade de arestas que n�o fazem parte
	 * do conjunto de corte no caminho atual da arvore � wmax do que a
	 * quantidade de arestas que n�o fazem parte do wmax conjunto de corte
	 * encontrado at� o momento. Se o primeiro for wmax que o segundo, ent�o,
	 * os filhos s�o cortados e a execu��o volta para o n� pai. Caso contr�rio,
	 * continua-se a execu��o na segunda parte do m�todo.
	 * 
	 * 
	 * @param no1
	 *            booleano indicando se o n� tem flag "1", caso o valor seja
	 *            true, ou "0", caso o valor seja "0".
	 * @param profundidade
	 *            profundidade atual do n�.
	 * @param somaArestasNaoCorte
	 *            este par�metro armazena o total de arestas que n�o fazem parte
	 *            do conjunto de corte no caminho atual da �rvore.
	 */
	private void percorrerNo(boolean no1, int profundidade,
			int somaArestasNaoCorte) {
		Vertice vertice = conjuntoDeVertices.get(profundidade);
		if (no1) {
			vertice.primeiroConjunto = true;
		} else {
			vertice.primeiroConjunto = false;
		}

		int arestasNAOCorte = 0;
		for (Aresta a : vertice.arestas) {
			Vertice temp;
			if (a.v1 == vertice) {
				temp = a.v2;
			} else {
				temp = a.v1;
			}
			if (temp.pos < profundidade) {
				if (temp.primeiroConjunto == vertice.primeiroConjunto) {
					++arestasNAOCorte;
				}
			}
			if (somaArestasNaoCorte + arestasNAOCorte > totalArestasNaoCorte) {
				return;
			}
		}
		percorrerNo(no1, profundidade, somaArestasNaoCorte, arestasNAOCorte);
	}

	/**
	 * Esta � a segunda parte do m�todo de percorrer o n�. Nesta parte,
	 * verifica-se:
	 * 
	 * 
	 * <ol>
	 * <li>Se a profundidade for m�xima (tamanho do conjunto de v�rtices do
	 * grafo - 1), ent�o calcular o conjunto de corte.</li>
	 * <li>Caso contr�rio, se o n� atual tiver flag "1", percorrer o filho
	 * direito e depois o filho esquerdo, sen�o, percorrer o filho esquerdo e
	 * depois o filho direito</li>
	 * </ol>
	 * 
	 * @param no1
	 *            booleano indicando se o n� tem flag "1", caso o valor seja
	 *            true, ou "0", caso o valor seja "0".
	 * @param profundidade
	 *            profundidade atual do n�.
	 * @param somaArestasNaoCorte
	 *            este par�metro armazena o total de arestas que n�o fazem parte
	 *            do conjunto de corte no caminho atual da �rvore.
	 * @param arestasNAOCorte
	 *            este par�metro armazena as arestas que n�o fazem parte do
	 *            conjunto de corte calculados para o v�rtice deste n�.
	 */
	private void percorrerNo(boolean no1, int profundidade,
			int somaArestasNaoCorte, int arestasNAOCorte) {
		if (profundidade >= conjuntoDeVertices.size() - 1) {
			calcularCorte();
			++totalCount;
		} else {
			percorrerNo(!no1, profundidade + 1, somaArestasNaoCorte
					+ arestasNAOCorte);
			percorrerNo(no1, profundidade + 1, somaArestasNaoCorte
					+ arestasNAOCorte);
		}
	}

	/**
	 * Este m�todo inicia a execu��o da �rvore de chamadas para calcular o corte
	 * m�ximo. Define-se o primeiro n� filho como n� esquerdo (flag "1"), define
	 * a profundidade = 0, e o total de arestas que n�o fazem parte do conjunto
	 * de corte = 0.
	 * 
	 */
	public void calcularMaxCut() {
		definirId();
		percorrerNo(true, 0, 0);
	}

	/**
	 * Este m�todo define, para cada v�rtice v, v.id = posicao de v no vetor de
	 * v�rtices.
	 */
	private void definirId() {
		for (int i = 0; i < conjuntoDeVertices.size(); i++) {
			conjuntoDeVertices.get(i).pos = i;
		}
	}

	/**
	 * Este m�todo cria o grafo a partir do arquivo especificado. Ele armazena
	 * os v�rtices e arestas em dois vetores distintos, armazena em cada aresta
	 * a refefer�ncia aos dois v�rtices que ela liga e em cada v�rtice as
	 * arestas que se ligam a ele.
	 * <p>
	 * O arquivo que ser� lido deve ter o seguinte formato:
	 * 
	 * <numero de vertices> <numero de arestas> <id de vertice> <id de vertice>
	 * <id de vertice> <id de vertice> ...
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
	 * O programa come�a sua execu��o atrav�s deste m�todo. O primeiro par�metro
	 * � a lista de argumentos que o usu�rio passa linha de comando quando se
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
	 * i - Caso este par�metro seja adicionado, mostra-se a quantidade de
	 * c�lculos de corte que foram feitos (c�lculos de corte ao chegar na raiz
	 * da �rvore). Por padr�o, esta informa��o n�o � mostrada.
	 * <p>
	 * Um exemplo de execu��o do algoritmo �:
	 * <p>
	 * 3 par�metros:
	 * <p>
	 * <code>java CorteMaximo adt "C:\Users\g70.n"</code>
	 * <p>
	 * Nenhum par�metro:
	 * <p>
	 * <code>java CorteMaximo "C:\Users\g70.n"</code>
	 * <p>
	 * O segundo par�metro � o caminho do arquivo a ser a processado.
	 * 
	 * @param args
	 *            Os argumentos que s�o passados pela linha de comando
	 */
	public static void main(String[] args) {
		boolean calcularTempoTotal = false;
		boolean calcularTempoAlgoritmo = false;
		boolean mostrarVerticesLigados = false;
		boolean mostrarQuantidadeIteracoes = false;
		int i = 0;
		if (args.length == 0) {
			System.out
					.println("Este programa deve ser iniciado com o caminho do arquivo que especifica o grafo no primeiro argumento. Peco desculpas pelo incomodo.");
			return;
		}
		if (args.length == 2) {
			String options = args[0];
			if (options.contains("t")) {
				calcularTempoTotal = true;
			}
			if (options.contains("a")) {
				calcularTempoAlgoritmo = true;
			}
			if (options.contains("d")) {
				mostrarVerticesLigados = true;
			}
			if (options.contains("i")) {
				mostrarQuantidadeIteracoes = true;
			}
			i = 1;
		}

		long tempoInicialTotal = System.currentTimeMillis();
		Grafo g;
		try {
			g = extrairGrafoDeArquivo(args[i]);
			CorteMaximo c = new CorteMaximo(g);
			long tempoInicialAlgoritmo = System.currentTimeMillis();
			c.calcularMaxCut();
			long tempoFinalAlgoritmo = System.currentTimeMillis();
			if (mostrarQuantidadeIteracoes) {
				System.out.println("Foram calculados " + c.totalCount
						+ " conjuntos de corte.");
			}
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
				long tempoDecorrido = tempoFinalTotal - tempoInicialTotal;
				System.out.println("Tempo decorrido do programa: "
						+ tempoDecorrido + " milisegundos.");
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("Houve um erro ao ler o arquivo. Por favor, verifique o caminho especificado do mesmo. Obrigado.");
		}
	}
}
