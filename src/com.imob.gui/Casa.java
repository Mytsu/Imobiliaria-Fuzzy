
public class Casa {
	
	private float valor;
	private int vagas;
	private float AreaLivre;
	private float tamConstrucao;
	private short localidade;
	
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	public float getAreaLivre() {
		return AreaLivre;
	}
	public void setAreaLivre(float areaLivre) {
		AreaLivre = areaLivre;
	}
	public float getTamConstrucao() {
		return tamConstrucao;
	}
	public void setTamConstrucao(float tamConstrucao) {
		this.tamConstrucao = tamConstrucao;
	}
	public short getLocalidade() {
		return localidade;
	}
	public void setLocalidade(short localidade) {
		this.localidade = localidade;
	}
	@Override
	public String toString() {
		return "Casa [valor=" + valor + ", vagas=" + vagas + ", AreaLivre=" + AreaLivre + ", tamConstrucao="
				+ tamConstrucao + ", localidade=" + localidade + "]";
	}
	
	
}
