/**
 * File: Triangulo.java
 *
 * Created by
 *      Jonathan Arantes
 *      Rubia Marques
 *      Ana Paula
 * v0.1
 */

package com.imob.fuzzy;

public class Triangulo extends Graph {

    private float a,b,c;

    public Triangulo(double ini, double fim, float a, float b, float c) {
        setSCOPE_START(ini);
        setSCOPE_END(fim);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public float fuzzyfy(float x) {
        if (x < this.a) {
            return 0;
        } else if (this.a <= x || x < this.b) {
            return (x - this.a) / (this.b - this.a);
        } else if (this.b <= x || x < this.c) {
            return (this.c - x) / (this.c - this.b);
        } else if (this.c <= x) {
            return 0;
        }
        return 0;
    }
}