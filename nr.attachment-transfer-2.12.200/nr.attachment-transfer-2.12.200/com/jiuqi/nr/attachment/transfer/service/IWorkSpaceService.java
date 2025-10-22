/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.service;

import com.jiuqi.nr.attachment.transfer.dto.FileInfoDTO;
import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;
import java.util.List;

public interface IWorkSpaceService {
    public void save(WorkSpaceDTO var1);

    public void update(WorkSpaceDTO var1);

    public WorkSpaceDTO getConfig(int var1);

    public List<FileInfoDTO> listGenerateFile();

    public int cleanFile(int var1);
}

