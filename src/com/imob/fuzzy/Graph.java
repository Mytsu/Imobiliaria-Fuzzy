/**
 * File: Graph.java
 *
 * Created by
 *      Jonathan Arantes
 *      Rubia Marques
 *      Ana Paula
 * v0.1
 */

package com.imob.fuzzy;

public abstract class Graph {

    private double SCOPE_START = 0;

    private double SCOPE_END = 0;

    public double getSCOPE_START() {
        return SCOPE_START;
    }

    public void setSCOPE_START(double SCOPE_START) {
        this.SCOPE_START = SCOPE_START;
    }

    public double getSCOPE_END() {
        return SCOPE_END;
    }

    public void setSCOPE_END(double SCOPE_END) {
        this.SCOPE_END = SCOPE_END;
    }

    public abstract float fuzzyfy(float x);

    public double centroid(float x, int cont) {

        double resp = 0;
        double div = 0;

        for(int i = (int) SCOPE_START; i < SCOPE_END; i += cont) {
            resp += i * this.fuzzyfy(i);
        }

        for(int i = (int) SCOPE_START; i < SCOPE_END; i += cont) {
            div += this.fuzzyfy(i);
        }
        return resp / div;
    }
}