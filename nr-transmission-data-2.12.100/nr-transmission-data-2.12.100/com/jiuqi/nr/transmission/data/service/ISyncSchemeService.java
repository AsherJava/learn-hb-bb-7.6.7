/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import java.util.List;

public interface ISyncSchemeService {
    public List<SyncSchemeDTO> list(SyncSchemeDTO var1);

    public List<SyncSchemeDTO> listWithOutParam();

    public SyncSchemeDTO get(String var1);

    public SyncSchemeDTO getWithOutParam(String var1);

    public boolean insert(SyncSchemeDTO var1);

    public boolean delete(String var1);

    public boolean deletes(String var1, String var2);

    public boolean update(SyncSchemeDTO var1);

    public boolean updates(String var1, String var2, String var3);
}

