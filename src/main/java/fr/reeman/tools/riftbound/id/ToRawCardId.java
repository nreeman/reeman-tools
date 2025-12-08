package fr.reeman.tools.riftbound.id;

import fr.reeman.tools.riftbound.RawCardId;

@FunctionalInterface
public interface ToRawCardId<T> {

    RawCardId to(T t);

}
