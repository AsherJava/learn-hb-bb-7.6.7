/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition;

import nr.midstore.core.definition.IMidstoreData;

public interface IMidstoreField
extends IMidstoreData {
    public String getCode();

    public String getTitle();

    public String getSchemeKey();

    public String getSrcTableKey();

    public String getSrcFieldKey();

    public String getRemark();

    public boolean isEncrypted();
}

