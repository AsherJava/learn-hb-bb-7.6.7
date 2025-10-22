/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition;

import java.io.Serializable;
import java.time.Instant;

public interface IMidstoreData
extends Serializable {
    public String getKey();

    public String getOrder();

    public Instant getUpdateTime();
}

