/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage;

import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSchemeRowDefine;
import java.util.List;

public interface ShareSchemeRowDao {
    public int[] insertRows(List<ShareSchemeRowDefine> var1);

    public int[] updateRows(List<ShareSchemeRowDefine> var1);

    public int removeRows(List<String> var1);
}

