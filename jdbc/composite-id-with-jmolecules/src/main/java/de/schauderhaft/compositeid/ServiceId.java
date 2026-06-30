package de.schauderhaft.compositeid;

import java.util.UUID;

public record ServiceId(UUID tenantId, UUID id) {
}
