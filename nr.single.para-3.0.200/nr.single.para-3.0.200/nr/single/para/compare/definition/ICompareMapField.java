/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.io.Serializable;
import java.time.Instant;

public interface ICompareMapField
extends Serializable {
    public String getKey();

    public String getFieldKey();

    public String getDataSchemeKey();

    public String getMatchTitle();

    public Instant getUpdateTime();
}

