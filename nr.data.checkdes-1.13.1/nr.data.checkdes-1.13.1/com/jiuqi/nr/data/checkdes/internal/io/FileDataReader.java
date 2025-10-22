/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.io;

import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import java.io.IOException;
import java.util.function.Consumer;

public interface FileDataReader {
    public void read(ImpContext var1, Consumer<CKDExpEntity> var2) throws IOException;
}

