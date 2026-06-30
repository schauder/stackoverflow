package de.schauderhaft.compositeid;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record ServiceId(UUID tenantId, UUID id) implements Identifier {
}
