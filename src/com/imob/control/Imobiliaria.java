/**
 * File: Imobiliaria.java
 *
 * Created by
 * Jonathan Arantes
 * Rubia Marques
 * Ana Paula
 * v0.1
 */

package com.imob.control;

import com.imob.fuzzy.Fuzzy;
import com.imob.fuzzy.Linha;
import com.imob.fuzzy.Trapezio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Imobiliaria {

    private Fuzzy fuzzy = new Fuzzy();
    private final int _QUALIDADE_RUIM = 0;
    private final int _QUALIDADE_MEDIA = 1;
    private final int _QUALIDADE_BOA = 2;

    // Localidade
    private Trapezio localRuim = new Trapezio(0,8,0,0,3,4);
    private Trapezio localMediana = new Trapezio(0,8,3, (float)4.5, 6,7);
    private Trapezio localBoa = new Trapezio(0,8, 6,(float)8.5, Fuzzy._INFINITY, Fuzzy._INFINITY);

    // Area Externa
    private Trapezio areaExternaPequena = new Trapezio(0, 700, 0, 0, 300, 350);
    private Trapezio areaExternaMediana = new Trapezio(0, 700, 300, 350, 550, 600);
    private Trapezio areaExternaGrande = new Trapezio(0, 700, 550, 700, Fuzzy._INFINITY, Fuzzy._INFINITY);

    // Area Interna
    private Trapezio areaTotalPequena = new Trapezio(50, 450, 0, 0, 120, 150);
    private Trapezio areaTotalMediana = new Trapezio(50, 450, 120, 150, 300, 350);
    private Trapezio areaTotalGrande = new Trapezio(50, 450, 300, 400, Fuzzy._INFINITY, Fuzzy._INFINITY);

    // Valor
    private Trapezio valorBarata = new Trapezio(120, 750, 120, 120, 250, 350);
    private Trapezio valorMediana = new Trapezio(120, 750, 300, 350, 450, 500);
    private Trapezio valorCara = new Trapezio(120, 750, 450, 750, Fuzzy._INFINITY, Fuzzy._INFINITY);

    // Vagas
    private Linha vagaPouca = new Linha(0, 4, 0, 2);
    private Linha vagaMediana = new Linha(0, 4, 1, (float)2.5);
    private Linha vagaMuita = new Linha(0, 4, 2, 4);

    private ArrayList<Casa> vetorCasas = new ArrayList<Casa>();

    public void leituraArquivo(String filename) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));

            String linha;
            String lixo;

            //linha de cabecalho.
            linha = br.readLine();

            while (br.ready()) {

                linha = br.readLine();

                Casa casa = new Casa();

                lixo = linha.substring(0, linha.indexOf("-"));
                casa.setValor(Float.parseFloat(lixo.replace("-", "")));
                linha = linha.substring(linha.indexOf("-") + 1, linha.length());

                lixo = linha.substring(0, linha.indexOf("-"));
                casa.setVagas(Short.parseShort(lixo.replace("-", "")));
                linha = linha.substring(linha.indexOf("-") + 1, linha.length());

                lixo = linha.substring(0, linha.indexOf("-"));
                casa.setAreaLivre(Float.parseFloat(lixo.replace("-", "")));
                linha = linha.substring(linha.indexOf("-") + 1, linha.length());

                lixo = linha.substring(0, linha.indexOf("-"));
                casa.setTamConstrucao(Float.parseFloat(lixo.replace("-", "")));
                linha = linha.substring(linha.indexOf("-") + 1, linha.length());

                casa.setLocalidade(Short.parseShort(linha.substring(0, linha.length())));

                vetorCasas.add(casa);

            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mostraCasa() {
        for (Iterator iterator = vetorCasas.iterator(); iterator.hasNext(); ) {
            Casa casa = (Casa) iterator.next();
            System.out.println(casa.toString());
        }

    }

    public ArrayList<Float> regras(Float valor, Float vagas, Float areaLivre, Float areaInterna, Float localidade) {
        ArrayList<Float> qualidade = new ArrayList<>();

        // Regras (De acordo com Especialista)

        ArrayList<Float> valores = new ArrayList<Float>();

        // IF valor == Barato AND vagas == Pouca AND areaExterna == Pequena AND localidade == Ruim THEN qualidade = Ruim
            valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Barato AND vagas == Mediana AND areaExterna == Pequena AND localidade == Ruim THEN qualidade = Ruim
            valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Barato AND vagas == Mediana AND areaExterna == Mediana AND localidade == Ruim THEN qualidade = Mediana
            valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Barato AND vagas == Mediana AND areaExterna == Mediana AND localidade == Media THEN qualidade = Mediana
            valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Mediana AND vagas == Pequena AND areaExterna == Pequena AND localidade == Ruim THEN qualidade = Ruim
            valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Pequena AND areaExterna == Pequena AND localidade == Ruim THEN qualidade = Ruim
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Mediana AND vagas == Mediana AND areaExterna == Pequena AND localidade == Mediana THEN qualidade = Mediana
            valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Mediana AND vagas == Pequena AND areaExterna == Mediana AND localidade == Mediana THEN qualidade = Mediana
            valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Mediana AND areaExterna == Grande AND localidade == Ruim THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Grande AND areaExterna == Grande AND localidade == Ruim THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Media AND areaExterna == Grande AND localidade == Ruim THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Media AND areaExterna == Media AND localidade == Ruim THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Grande AND areaExterna == Grande AND localidade == Media THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Caro AND vagas == Grande AND areaExterna == Grande AND localidade == Ruim THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Media AND vagas == Grande AND areaExterna == Grande AND localidade == Boa THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Media AND vagas == Grande AND areaExterna == Grande AND localidade == Media THEN qualidade = Boa
            valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Media AND vagas == Grande AND areaExterna == Grande AND localidade == Ruim THEN qualidade = Media
            valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
            valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
            valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
            valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Media AND (vagas == Pequena || Media)  AND areaExterna == Media AND localidade == Boa THEN qualidade = Media
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        if (fuzzy.trunc(vagaPouca.fuzzyfy(vagas)) < fuzzy.trunc(vagaMediana.fuzzyfy(vagas))) {
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        } else {
            valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        }
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        valores.clear();

        // IF valor == Media AND (vagas == Pequena || Media)  AND areaExterna == Pequena AND localidade == Ruim THEN qualidade = Media
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        if (fuzzy.trunc(vagaPouca.fuzzyfy(vagas)) < fuzzy.trunc(vagaMediana.fuzzyfy(vagas))) {
            valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        } else {
            valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        }
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min( valores ));

        return qualidade;
    }

    public float centroid(ArrayList<Float> l) {
        float mult = 0, soma = 0, aux;
        ArrayList<Float> valores;
        // valor de pertinencia deve ser menor ou igual que o valor definido na saida das regras
        for (int i = 0; i < 100; i++) {

        }
        return mult / soma;
    }
}
