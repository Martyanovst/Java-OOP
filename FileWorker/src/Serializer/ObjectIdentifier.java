package Serializer;

public class ObjectIdentifier implements Comparable {
    private Object key;
    private static int counter = 1;
    private int number;

    ObjectIdentifier(Object key) {
        this.key = key;
        number = counter++;
    }

    public Object getKey() {
        return key;
    }

    @Override
    public String toString() {
        if (key != null && key.getClass().isArray())
            return "a" + number;
        return "o" + number;
    }

    public boolean equals(Object obj) {
        return obj instanceof ObjectIdentifier && ((ObjectIdentifier) obj).key == key;
    }

    public int hashCode() {
        return System.identityHashCode(key);
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }
}
