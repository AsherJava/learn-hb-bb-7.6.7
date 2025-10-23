/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.dto.SyncNode;
import java.util.List;

public interface IZbSchemeSyncProvider {
    public String getType();

    public String getTitle();

    public List<SyncNode> listSyncNode(String var1);

    public List<SyncNode> listSyncNode(String var1, String var2);

    public List<String> listSyncZbCode(String var1);

    public List<String> listSyncZbCode(String var1, String var2);

    public boolean checkZbRefer(String var1, String var2);
}

