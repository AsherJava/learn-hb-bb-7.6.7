/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface ISyncHistoryService {
    public boolean insert(SyncHistoryDTO var1);

    public boolean delete(String var1);

    public boolean deletes(String var1);

    public List<SyncHistoryDTO> getByScheme(String var1);

    public List<SyncHistoryDTO> getImport();

    public List<SyncHistoryDTO> getBySchemes(List<String> var1);

    public List<SyncHistoryDTO> getUnComplete();

    public SyncHistoryDTO get(String var1);

    public int update(SyncHistoryDO var1);

    public int updateSyncSchemeParam(SyncHistoryDO var1);

    public int updateResult(SyncHistoryDO var1);

    public int updateDetail(String var1, String var2);

    public int updateField(String var1, String var2, String var3);

    public void getExportData(String var1);

    public void exportDetail(String var1, HttpServletResponse var2) throws Exception;
}

