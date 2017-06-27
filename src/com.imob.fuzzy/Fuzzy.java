/**
 * File: Fuzzy.java
 * This file implements a fuzzy logic for a specialist real state seller
 * Created by 
 *      Jonathan Arantes
 *      Rubia Marques
 *      Ana Paula
 * v0.1
 */

package com.imob.fuzzy;

import java.util.ArrayList;

public class Fuzzy {
    public final long _INFINITY =  9000000;

    public ArrayList<Float> min (ArrayList<Float> l1, ArrayList<Float> l2) {
        ArrayList<Float> result = new ArrayList<Float>();
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i) <= l2.get(i)) {
                result.add(l1.get(i));
            } else {
                result.add(l2.get(i));
            }
        }
        return result;
    }

    public float min (float l1, float l2) {
        if(l1 < l2) {
            return l1;
        } else {
            return l2;
        }
    }

    public float min (ArrayList<Float> l) {
        float m = _INFINITY;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) < m) {
                m = l.get(i);
            }
        }
        return m;
    }

    public ArrayList<Float> max (ArrayList<Float> l1, ArrayList<Float> l2) {
        ArrayList<Float> result = new ArrayList<Float>();
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i) > l2.get(i)) {
                result.add(l1.get(i));
            } else {
                result.add(l2.get(i));
            }
        }
        return result;
    }

    public float max (float l1, float l2) {
        if(l1 > l2) {
            return l1;
        } else {
            return l2;
        }
    }

    public float max (ArrayList<Float> l) {
        float m = _INFINITY;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) > m) {
                m = l.get(i);
            }
        }
        return m;
    }
}
