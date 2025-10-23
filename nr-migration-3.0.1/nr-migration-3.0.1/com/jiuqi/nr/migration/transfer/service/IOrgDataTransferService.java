/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transfer.service;

import com.jiuqi.nr.migration.transfer.vo.OrgDataFile;
import org.springframework.web.multipart.MultipartFile;

public interface IOrgDataTransferService {
    public OrgDataFile uploadFile(MultipartFile var1) throws Exception;

    public void importOrgData(String var1) throws Exception;
}

