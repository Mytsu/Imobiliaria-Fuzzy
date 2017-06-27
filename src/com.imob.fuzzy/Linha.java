/**
 * File: Linha.java
 *
 * Created by
 *      Jonathan Arantes
 *      Rubia Marques
 *      Ana Paula
 * v0.1
 */

package com.imob.fuzzy;

public class Linha extends Graph {

    private float a,b;

    public Linha(double ini, double fim, float a, float b) {
        this.SCOPE_START = ini;
        this.SCOPE_END = fim;
        this.a = a;
        this.b = b;
    }

    public float fuzzyfy(float x) {
        return (this.a * x) + this.b;
    }
}