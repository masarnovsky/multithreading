package by.masarnovsky.multithreading.atomic;

import java.util.UUID;

public class Test {
    private AtomicVersionedReference<ComplexObject> state;

    public void setState(ComplexObject object) {
        state = new AtomicVersionedReference<>(object, getVersion());
    }

    public void updateState(ComplexObject newState) {
        ComplexObject reference;
        String version;
        do {
            reference = state.getReference();
            version = state.getVersion();
        } while(state.compareAndSet(reference, newState, version, getVersion()));
    }

    public String getVersion() {
        return UUID.randomUUID().toString();
    }
}
