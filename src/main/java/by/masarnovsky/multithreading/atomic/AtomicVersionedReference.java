package by.masarnovsky.multithreading.atomic;


import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Objects;

public class AtomicVersionedReference<V> {
    private static class Pair<T> {
        final T reference;
        final String version;

        private Pair(T reference, String version) {
            this.reference = reference;
            this.version = version;
        }

        static <T> Pair<T> of(T reference, String version) {
            return new Pair<>(reference, version);
        }
    }

    private volatile Pair<V> pair;

    public AtomicVersionedReference(V initialRef, String initialVersion) {
        pair = Pair.of(initialRef, initialVersion);
    }

    public V getReference() {
        return pair.reference;
    }

    public String getVersion() {
        return pair.version;
    }

    public boolean compareAndSet(V expectedReference, V newReference, String expectedVersion, String newVersion) {
        Pair<V> current = pair;

        return expectedReference == current.reference && Objects.equals(expectedVersion, current.version) &&
                ((newReference == current.reference && Objects.equals(newVersion, current.version)) ||
                        casPair(current, Pair.of(newReference, newVersion)));
    }


    private static final VarHandle PAIR;

    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            PAIR = l.findVarHandle(AtomicVersionedReference.class, "pair",
                    AtomicVersionedReference.Pair.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }


    private boolean casPair(Pair<V> cmp, Pair<V> val) {
        return PAIR.compareAndSet(this, cmp, val);
    }
}
