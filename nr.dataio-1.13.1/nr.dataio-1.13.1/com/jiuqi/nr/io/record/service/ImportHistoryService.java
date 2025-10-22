/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 */
package com.jiuqi.nr.io.record.service;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportHistoryVO;
import com.jiuqi.nr.io.record.bean.ImportLog;
import java.util.List;

public interface ImportHistoryService {
    public void createImportHistory(ImportHistory var1);

    public Page<ImportHistoryVO> queryByCreator(String var1, int var2, int var3);

    public ImportHistory queryByRecKey(String var1);

    public ImportHistoryVO queryVOByRecKey(String var1);

    public void updateImportHistory(ImportHistory var1);

    public void addImportLog(ImportLog var1);

    public void updateImportLogDesc(ImportLog var1);

    public List<ImportLog> getImportLogs(String var1);

    public List<ImportLog> getImportLogsByFactory(String var1, String var2);

    public void deleteTimeOutImportHistory(int var1);
}

