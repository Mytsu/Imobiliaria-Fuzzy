import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Imobiliaria {

	private ArrayList<Casa> vetorCasas = new ArrayList<Casa>();

	public void leituraArquivo()
	{
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("IA_Trabalho.txt"));
				
			String linha;			
			String lixo;
			
			//linha de cabecalho.
			linha = br.readLine();
			
			while(br.ready()){
				
			linha = br.readLine();	
			
			Casa casa = new Casa();
				
			lixo=linha.substring(0,linha.indexOf("-"));
			casa.setValor(Float.parseFloat(lixo.replace("-","")));
			linha=linha.substring(linha.indexOf("-")+1,linha.length());
			
			lixo=linha.substring(0,linha.indexOf("-"));
			casa.setVagas(Short.parseShort(lixo.replace("-","")));
			linha=linha.substring(linha.indexOf("-")+1,linha.length());
			
			lixo=linha.substring(0,linha.indexOf("-"));
			casa.setAreaLivre(Float.parseFloat(lixo.replace("-","")));
			linha=linha.substring(linha.indexOf("-")+1,linha.length());
			
			lixo=linha.substring(0,linha.indexOf("-"));
			casa.setTamConstrucao(Float.parseFloat(lixo.replace("-","")));
			linha=linha.substring(linha.indexOf("-")+1,linha.length());

			casa.setLocalidade(Short.parseShort(linha.substring(0,linha.length())));					
			
			vetorCasas.add(casa);
									
			}
			br.close();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
	
	public void mostraCasa()
	{
		for (Iterator iterator = vetorCasas.iterator(); iterator.hasNext();) {
			Casa casa = (Casa) iterator.next();
			
			System.out.println(casa.toString());		
		}
		
	}

		
		
}
