/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition;

import nr.midstore.core.definition.IMidstoreData;

public interface IMidstoreOrgData
extends IMidstoreData {
    public String getCode();

    public String getTitle();

    public String getSchemeKey();

    public String getOrgCode();

    public String getParentCode();
}

