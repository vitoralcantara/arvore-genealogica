package game.data;

public class JogoData {

	// Consoles
	public final static String PS3 = "PS3";
	public final static String XBOX360 = "XBOX360";
	public final static String WII = "WII";
	public final static String PC = "PC";

	// Estilos
	public final static String FPS = "Tiro em primeira pessoa";
	public final static String AVENTURA = "Aventura";

	private final String ID;
	private final String nomeDoJogo;
	private final String produtora;
	private final Requisitos requisitos;
	private final String[] imagemURLs;
	private final String[] videoURLs;
	private final String estilo;
	private final int diaLancamento;
	private final int mesLancamento;
	private final int anoLancamento;
	private final String[] plataformas;
	private final String resumo;
	private final String iconeURL;

	public JogoData(String ID, String nomeDoJogo, String produtora,
			Requisitos requisitos, String[] imagemURLs, String[] videoURLs,
			String estilo, int diaLancamento, int mesLancamento,
			int anoLancamento, String[] plataformas, String resumo,
			String iconeURL) {
		super();
		this.ID = ID;
		this.nomeDoJogo = nomeDoJogo;
		this.produtora = produtora;
		this.requisitos = requisitos;
		this.imagemURLs = imagemURLs;
		this.videoURLs = videoURLs;
		this.estilo = estilo;
		this.diaLancamento = diaLancamento;
		this.mesLancamento = mesLancamento;
		this.anoLancamento = anoLancamento;
		this.plataformas = plataformas;
		this.resumo = resumo;
		this.iconeURL = iconeURL;
	}

	public String[] getPlataformas() {
		return plataformas;
	}

	public String getIconeURL() {
		return iconeURL;
	}

	public String getResumo() {
		return resumo;
	}

	public static String getPs3() {
		return PS3;
	}

	public static String getXbox360() {
		return XBOX360;
	}

	public static String getWii() {
		return WII;
	}

	public static String getPc() {
		return PC;
	}

	public static String getFps() {
		return FPS;
	}

	public static String getAventura() {
		return AVENTURA;
	}

	public String getNomeDoJogo() {
		return nomeDoJogo;
	}

	public String getProdutora() {
		return produtora;
	}

	public Requisitos getRequisitos() {
		return requisitos;
	}

	public String[] getImagemURLs() {
		return imagemURLs;
	}

	public String[] getVideoURLs() {
		return videoURLs;
	}

	public String getEstilo() {
		return estilo;
	}

	public Integer getDiaLancamento() {
		return diaLancamento;
	}

	public Integer getMesLancamento() {
		return mesLancamento;
	}

	public Integer getAnoLancamento() {
		return anoLancamento;
	}

	public String getPlataformasToString() {
		String result = "";
		if (plataformas.length > 0) {
			result += plataformas[0];
		}
		for (int i = 1; i < plataformas.length; i++) {
			result += "," + plataformas[i];
		}
		return result;
	}

	public String getID() {
		return ID;
	}

}
