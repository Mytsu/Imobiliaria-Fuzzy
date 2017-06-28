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

import com.imob.control.Imobiliaria;

public class Principal {

	public static void main(String[] args) {
		
		Imobiliaria teste=new Imobiliaria();
		
		teste.leituraArquivo("D:\\Projetos\\Java\\Trabalho-IA-Especialista-Imoveis-Fuzzy\\src\\IA_trabalho.txt");
		
		teste.mostraCasa();
		
		

	}

}
