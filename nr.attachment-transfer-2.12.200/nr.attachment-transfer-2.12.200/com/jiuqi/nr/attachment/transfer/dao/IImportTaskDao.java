/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dao;

import com.jiuqi.nr.attachment.transfer.domain.ImportTaskDO;

public interface IImportTaskDao {
    public ImportTaskDO query(String var1);

    public ImportTaskDO queryByServe(String var1);

    public int insert(ImportTaskDO var1);

    public int update(ImportTaskDO var1);

    public int updateStatus(String var1, int var2);

    public int deleteByServer(String var1);
}

