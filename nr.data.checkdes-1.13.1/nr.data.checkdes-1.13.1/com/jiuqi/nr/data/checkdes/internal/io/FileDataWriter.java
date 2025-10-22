/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.io;

import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import java.io.IOException;
import java.util.function.Consumer;

public interface FileDataWriter
extends Consumer<CKDExpEntity> {
    public void write() throws IOException;
}

