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
    public static final long _INFINITY =  9000000;

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

    public ArrayList<Float> equals (ArrayList<Float> l1, ArrayList<Float> l2) {
        ArrayList<Float> resp = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            if(l1.get(i) == l2.get(i)) {
                resp.add(l1.get(i));
            }
        }
        return resp;
    }

    public ArrayList<Float> complement (ArrayList<Float> l) {
        ArrayList<Float> resp = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            resp.add(1 - l.get(i));
        }
        return resp;
    }

    public boolean empty (ArrayList<Float> l) {
        if(l == null || l.size() == 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Float> union (ArrayList<Float> l1, ArrayList<Float> l2) {
        ArrayList<Float> resp = new ArrayList<>();
        for(int i = 0; i < l1.size(); i++) {
            resp.add( max(l1.get(i), l2.get(i)) );
        }
        return resp;
    }

    public ArrayList<Float> intersection (ArrayList<Float> l1, ArrayList<Float> l2) {
        ArrayList<Float> resp = new ArrayList<>();
        for(int i = 0; i < l1.size(); i++) {
            resp.add( min(l1.get(i), l2.get(i)) );
        }
        return resp;
    }

    public boolean subconjunct (ArrayList<Float> l1, ArrayList<Float> l2) {
        return l1.containsAll(l2);
    }

    public ArrayList<Float> trunc(ArrayList<Float> l) {
        ArrayList<Float> resp = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            if( l.get(i) >= 1 ) {
                resp.add((float)1);
            } else if ( l.get(i) <= 0 ) {
                resp.add((float)0);
            } else {
                resp.add(l.get(i));
            }
        }
        return resp;
    }

    public Float trunc(Float l) {
        if( l > 1 ) {
            return (float)1;
        } else if ( l < 1 ) {
            return (float)0;
        } else {
            return l;
        }
    }
}
