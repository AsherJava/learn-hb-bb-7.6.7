/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transferdata.service;

import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.transferdata.bean.ImportJQRDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IDataTransImportService {
    public ReturnObject upload(MultipartFile var1) throws Exception;

    public ReturnObject importFile(ImportJQRDTO var1) throws Exception;
}

