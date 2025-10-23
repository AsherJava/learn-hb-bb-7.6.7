/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.dto.JIOContent;
import java.util.List;

public interface JIOConfigService {
    public void saveJIOFile(byte[] var1, String var2);

    public byte[] getJIOFileByMs(String var1);

    public void saveJIOConfig(byte[] var1, String var2);

    public byte[] getJIOConfigByMs(String var1);

    public void saveJIOContent(JIOContent var1, String var2);

    public JIOContent getJIOContentByMs(String var1);

    public List<JIOContent> batchGetJIOContentByMs(List<String> var1);

    public void saveJIO(String var1, byte[] var2, byte[] var3, JIOContent var4);

    public boolean isJIO(String var1);
}

