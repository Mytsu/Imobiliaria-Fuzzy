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

    double SCOPE_START = 0;
    double SCOPE_END = 0;

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