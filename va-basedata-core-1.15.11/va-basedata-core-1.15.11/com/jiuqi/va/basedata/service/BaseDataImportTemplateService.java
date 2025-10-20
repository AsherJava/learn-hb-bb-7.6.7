/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDO;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDTO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BaseDataImportTemplateService {
    public int add(BaseDataImportTemplateDTO var1);

    public int update(BaseDataImportTemplateDTO var1);

    public int delete(BaseDataImportTemplateDTO var1);

    public List<BaseDataImportTemplateDO> list(BaseDataImportTemplateDTO var1);

    public void exportTemplate(BaseDataImportTemplateDTO var1);

    public void exportData(BaseDataImportTemplateDTO var1);

    public void importCheck(BaseDataImportTemplateDTO var1, MultipartFile var2);

    public void importSave(BaseDataImportTemplateDTO var1);

    public boolean exist(BaseDataImportTemplateDTO var1);

    public R getImportResult(BaseDataImportTemplateDTO var1);

    public R exportResultInfo(BaseDataImportTemplateDTO var1);
}

