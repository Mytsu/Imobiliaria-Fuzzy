/**
 * File: Principal.java
 *
 * Created by
 *      Jonathan Arantes
 *      Rubia Marques
 *      Ana Paula
 * v0.1
 */

package com.imob.gui;

import com.imob.control.Casa;
import com.imob.control.Imobiliaria;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) {
		
		Imobiliaria teste = new Imobiliaria();

		if(args.length == 0) {
			System.out.println("Argumentos inválidos! Para utilizar o app utilize:");
			getHelp();
		} else {
			switch (args[0]) {
				case "-d":
					teste.leituraArquivo(args[1]);
					teste.mostraCasa();
					break;

				case "-h":
					getHelp();
					break;

				case "-help":
					getHelp();
					break;

				case "-c":
					Casa casa = new Casa();
					casa.setValor(Float.parseFloat(args[1]));
					casa.setVagas(Integer.parseInt(args[2]));
					casa.setAreaLivre(Float.parseFloat(args[3]));
					casa.setTamConstrucao(Float.parseFloat(args[4]));
					casa.setLocalidade(Short.parseShort(args[5]));
					System.out.println("Quality (%): " + teste.doFuzzy(casa));
					break;
			}
		}

	}

	public static void getHelp() {
		System.out.println(System.getProperty("program.name") + " -d DATASET.dat");
		System.out.println("Dataset format example: \n " +
				"\tValor-QtdVagasGaragem-AreaLivre-TamConstrucao-Localidade" +
				"\n\t200000-1-350-150-06\n");
		System.out.println(System.getProperty("program.name") + " -c " + "VALOR VAGAS AREALIVRE TAMANHO_CASA LOCALIDADE");
		System.out.println("Exemplo: " + System.getProperty("program.name") + " -c 200000-1-350-150-06");
	}

}
