/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.task.form.formio;

import com.jiuqi.nr.task.form.dto.FormImportDTO;
import com.jiuqi.nr.task.form.formio.service.IFormImportCacheService;
import org.springframework.web.multipart.MultipartFile;

public interface IFormImportService
extends IFormImportCacheService {
    public String excelUpload(MultipartFile var1);

    public void formImport(FormImportDTO var1);

    public void saveImportData(FormImportDTO var1);
}

