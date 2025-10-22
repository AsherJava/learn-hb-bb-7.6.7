/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dao;

import com.jiuqi.nr.attachment.transfer.domain.ImportRecordDO;
import java.util.List;
import java.util.Map;

public interface IImportRecordDao {
    public List<ImportRecordDO> list();

    public List<ImportRecordDO> listByStatus(int ... var1);

    public List<ImportRecordDO> listByFilter(int var1, int var2, int var3);

    public ImportRecordDO get(String var1);

    public void insert(List<ImportRecordDO> var1);

    public int update(ImportRecordDO var1);

    public void updateField(String var1, Map<String, Object> var2);

    public int updateStatus(String var1, int var2);

    public int updateFileInfo(ImportRecordDO var1);

    public void batchUpdateStatus(int var1, String ... var2);

    public void deleteAll();
}

