/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.api;

import com.jiuqi.nr.calibre2.api.IReferenceCalibre;
import java.util.List;

public interface ICalibreCheckReference {
    public List<IReferenceCalibre> getReferences();

    public boolean enableDelete(String var1);
}

