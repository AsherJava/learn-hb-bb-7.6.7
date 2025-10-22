/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.snapshot.message.DataInfo;

public interface DataSource {
    public String getTitle();

    public DataInfo getData(String var1);
}

