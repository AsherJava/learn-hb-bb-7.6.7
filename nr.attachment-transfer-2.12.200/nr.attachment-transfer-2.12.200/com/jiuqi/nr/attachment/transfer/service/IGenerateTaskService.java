/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.service;

import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateStatusDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import java.util.List;

public interface IGenerateTaskService {
    public List<AttachmentRecordDTO> initGenerateInfo(GenerateParamDTO var1);

    public boolean existRunningTask();

    public List<AttachmentRecordDTO> listRecords(RecordsQueryDTO var1);

    public List<AttachmentRecordDTO> listRecordsByStatus(int ... var1);

    public AttachmentRecordDTO queryRecord(String var1);

    public List<AttachmentRecordDTO> countProcess();

    public GenerateParamDTO getParam();

    public void reset();

    public GenerateStatusDTO queryRecords(RecordsQueryDTO var1);

    public List<AttachmentRecordDO> cleanRecords();

    public List<AttachmentRecordDO> cleanRecords(List<String> var1);

    public String queryError(String var1);
}

