package game.data;

public class Requisitos {

	private final String processador;
	private final String mem�ria;
	private final String discoRigido;
	private final String directX;
	private final String placaVideo;

	public Requisitos(String processador, //
			String mem�ria, //
			String discoRigido, //
			String directX, //
			String placaVideo) {
		super();
		this.processador = processador;
		this.mem�ria = mem�ria;
		this.discoRigido = discoRigido;
		this.directX = directX;
		this.placaVideo = placaVideo;
	}

	public String getProcessador() {
		return processador;
	}

	public String getMem�ria() {
		return mem�ria;
	}

	public String getDiscoRigido() {
		return discoRigido;
	}

	public String getDirectX() {
		return directX;
	}

	public String getPlacaVideo() {
		return placaVideo;
	}

}
