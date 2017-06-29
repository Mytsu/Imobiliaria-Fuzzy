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
    private Trapezio areaInternaPequena = new Trapezio(50, 450, 0, 0, 120, 150);
    private Trapezio areaInternaMediana = new Trapezio(50, 450, 120, 150, 300, 350);
    private Trapezio areaInternaGrande = new Trapezio(50, 450, 300, 400, Fuzzy._INFINITY, Fuzzy._INFINITY);

    // Valor
    private Trapezio valorBarata = new Trapezio(120, 750, 120, 120, 250, 350);
    private Trapezio valorMediana = new Trapezio(120, 750, 300, 350, 450, 500);
    private Trapezio valorCara = new Trapezio(120, 750, 450, 750, Fuzzy._INFINITY, Fuzzy._INFINITY);

    // Vagas
    private Linha vagaPouca = new Linha(0, 4, 0, 2);
    private Linha vagaMediana = new Linha(0, 4, 1, (float)2.5);
    private Linha vagaMuita = new Linha(0, 4, 2, 4);

    // Resposta
    private Trapezio respRuim = new Trapezio(0, 10, - Fuzzy._INFINITY, - Fuzzy._INFINITY, (float)2.5, 3);
    private Trapezio respMedia = new Trapezio(0, 10, (float)2.5, 3, (float)5.25, 6);
    private Trapezio respBoa = new Trapezio(0, 10, (float)5.25, 6, Fuzzy._INFINITY, Fuzzy._INFINITY);

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

    public float doFuzzy(Casa casa) {
        return centroid(regras(casa.getValor(),(float)casa.getVagas(),casa.getTamConstrucao(),casa.getAreaLivre(),(float)casa.getLocalidade()));
    }

    public void mostraCasa() {
        for (Iterator iterator = vetorCasas.iterator(); iterator.hasNext(); ) {
            Casa casa = (Casa) iterator.next();
            System.out.println(casa.toString() + "\tQualidade (%): " + doFuzzy(casa));
        }

    }

    public ArrayList<Float> regras(Float valor, Float vagas, Float areaInterna, Float areaExterna, Float localidade) {
        ArrayList<Float> qualidade = new ArrayList<>();

        // Regras (De acordo com Especialista)

        ArrayList<Float> valores = new ArrayList<Float>();
        // se valor Barato e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Pouca e área construída Mediana e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Pouca e área construída Pequena e área extra Mediana e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Mediana
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Pequena e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Mediana e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Mediana e área extra Mediana e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Mediana e área extra Mediana e localidade Mediana
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Pouca e área construída Grande e área extra Pequena e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Pouca e área construída Pequena e área extra Grande e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Boa
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Grande e área extra Pequena e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Grande e área extra Grande e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Mediana e área construída Grande e área extra Grande e localidade Boa
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Barato e vagas garagem Muita e área construída Grande e área extra Grande e localidade Boa
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorBarata.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Mediana e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Pequena e área extra Mediana e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Mediana
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Pequena e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Mediana e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Mediana e área extra Mediana e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Mediana e área extra Mediana e localidade Mediana
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Grande e área extra Pequena e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Pequena e área extra Grande e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Boa
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Grande e área extra Pequena e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Grande e área extra Grande e localidade Ruim
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Mediana e área construída Grande e área extra Grande e localidade Boa
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Mediano e vagas garagem Muita e área construída Grande e área extra Grande e localidade Boa
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorMediana.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Mediana e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Pequena e área extra Mediana e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Mediana
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Pequena e área extra Pequena e localidade Ruim
        // então Qualidade Ruim
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_RUIM, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Mediana e área extra Pequena e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Mediana e área extra Mediana e localidade Ruim
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Mediana e área extra Mediana e localidade Mediana
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaMediana.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaMediana.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localMediana.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Grande e área extra Pequena e localidade Ruim
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Pequena e área extra Grande e localidade Ruim
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Pouca e área construída Pequena e área extra Pequena e localidade Boa
        // então Qualidade Mediana
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaPouca.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaPequena.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_MEDIA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Grande e área extra Pequena e localidade Ruim
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaPequena.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Grande e área extra Grande e localidade Ruim
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localRuim.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Mediana e área construída Grande e área extra Grande e localidade Boa
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMediana.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        valores.clear();
        // se valor Caro e vagas garagem Muita e área construída Grande e área extra Grande e localidade Boa
        // então Qualidade Boa
        valores.add(fuzzy.trunc(valorCara.fuzzyfy(valor)));
        valores.add(fuzzy.trunc(vagaMuita.fuzzyfy(vagas)));
        valores.add(fuzzy.trunc(areaInternaGrande.fuzzyfy(areaInterna)));
        valores.add(fuzzy.trunc(areaExternaGrande.fuzzyfy(areaExterna)));
        valores.add(fuzzy.trunc(localBoa.fuzzyfy(localidade)));
        qualidade.add(_QUALIDADE_BOA, fuzzy.min(valores));

        return qualidade;
    }

    public float centroid(ArrayList<Float> l) {
        float mult = 0, soma = 0, aux, maior;
        ArrayList<Float> valores;
        // valor de pertinencia deve ser menor ou igual que o valor definido na saida das regras
        for (int i = 0; i < 100; i++) {
            maior = 0;
            aux = -1;
            if(respRuim.fuzzyfy(i) <= l.get(_QUALIDADE_RUIM)) {
                aux = respRuim.fuzzyfy(i);
            }
            maior = fuzzy.max(maior, aux);
            if(respMedia.fuzzyfy(i) <= l.get(_QUALIDADE_MEDIA)) {
                aux = respMedia.fuzzyfy(i);
            }
            maior = fuzzy.max(maior,aux);
            if(respBoa.fuzzyfy(i) <= l.get(_QUALIDADE_BOA)) {
                aux = respBoa.fuzzyfy(i);
            }
            maior = fuzzy.max(maior,aux);
            mult += (maior * i);
            soma += maior;
        }

        if(soma == 0)
            return 0;

        return mult / soma;
    }
}
