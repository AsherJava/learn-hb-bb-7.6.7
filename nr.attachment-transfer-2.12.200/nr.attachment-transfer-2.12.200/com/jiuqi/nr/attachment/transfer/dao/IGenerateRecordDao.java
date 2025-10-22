/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dao;

import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import java.util.List;
import java.util.Map;

public interface IGenerateRecordDao {
    public List<AttachmentRecordDO> list();

    public List<AttachmentRecordDO> list(List<String> var1);

    public AttachmentRecordDO get(String var1);

    public List<AttachmentRecordDO> listByFilter(int var1, int var2, int var3, int var4, int var5);

    public List<AttachmentRecordDO> listByStatus(int ... var1);

    public void insert(List<AttachmentRecordDO> var1);

    public int update(AttachmentRecordDO var1);

    public void deleteAll();

    public int updateFileInfo(AttachmentRecordDO var1);

    public void batchUpdateStatus(int var1, boolean var2, String ... var3);

    public void updateStatus(String var1, int var2);

    public void updateField(String var1, Map<String, Object> var2);

    public void updateDownloadInfo(String var1, int var2);
}

