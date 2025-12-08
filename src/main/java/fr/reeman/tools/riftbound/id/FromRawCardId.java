package fr.reeman.tools.riftbound.id;

import fr.reeman.tools.riftbound.RawCardId;

@FunctionalInterface
public interface FromRawCardId<T> {

    T from(RawCardId id);
}
