import java.util.ArrayList;

/**
 * File: Fuzzy.java
 * This file implements a fuzzy logic for a specialist real state seller
 * Created by 
 *      Jonathan Arantes
 *      Rubia Marques
 *      Ana Paula
 * v0.1
 */
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
}
