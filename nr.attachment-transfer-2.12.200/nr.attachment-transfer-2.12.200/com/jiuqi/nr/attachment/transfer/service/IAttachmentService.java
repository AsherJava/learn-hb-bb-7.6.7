/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.service;

import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import java.util.List;

public interface IAttachmentService {
    public boolean generateAttachment(GenerateParamDTO var1);

    public boolean pausedGenerate();

    public boolean rebuildFailed();

    public boolean rebuild(String var1);

    public boolean restartGenerate();

    public boolean cleanAndNext();

    public boolean cleanRecords(List<String> var1);

    public boolean resetGenerate();

    public boolean importAttachment(ImportParamDTO var1);

    public boolean pausedImport();

    public boolean restartImport();

    public boolean reloadFailed();

    public boolean resetImport();

    public boolean reload(String var1);
}

