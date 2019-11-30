package CS4125.Model.Utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Taken from stackoverflow (https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java)
 * Random collection object where each item in the collection has an associated weight which influences how often it is picked from the collection
 * @param <E> Tye of item in collection
 */
public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
