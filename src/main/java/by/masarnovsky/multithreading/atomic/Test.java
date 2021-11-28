package by.masarnovsky.multithreading.atomic;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class Test {
    private AtomicVersionedReference<ComplexObject> state;

    public void setState(ComplexObject object) {
        state = new AtomicVersionedReference<>(object, getVersion());
    }

    // версия считается независимо
    public void updateState(ComplexObject newState) {
        ComplexObject reference;
        String version;
        do {
            reference = state.getReference();
            version = state.getVersion();
        } while(state.compareAndSet(reference, newState, version, getVersion()));
    }

    // версия считается независимо
    public String getVersion() {
        return UUID.randomUUID().toString();
    }

    // версия рассчитывается на основе предыдущей
    public void updateState1(ComplexObject newState) {
        ComplexObject reference;
        String version;
        String newVersion;

        do {
            reference = state.getReference();
            version = state.getVersion();
            newVersion = getVersion1(version);
        } while (state.compareAndSet(reference, newState, version, newVersion));
    }

    // версия рассчитывается на основе предыдущей
    public String getVersion1(String version) {
        byte[] decoded = Base64.getDecoder().decode(version);
        Integer decodedInt = ByteBuffer.wrap(decoded).getInt();
        Integer nextVersion = decodedInt + 1;
        byte[] encodeBase = ByteBuffer.allocate(4).putInt(nextVersion).array();
        return Base64.getEncoder().encodeToString(encodeBase);
    }
}
