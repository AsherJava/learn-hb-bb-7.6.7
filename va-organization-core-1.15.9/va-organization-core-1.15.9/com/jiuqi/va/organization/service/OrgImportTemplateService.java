/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.organization.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface OrgImportTemplateService {
    public List<OrgImportTemplateDO> list(OrgImportTemplateDTO var1);

    public int add(OrgImportTemplateDTO var1);

    public int update(OrgImportTemplateDTO var1);

    public int delete(OrgImportTemplateDTO var1);

    public void exportTemplate(OrgImportTemplateDTO var1);

    public void exportData(OrgImportTemplateDTO var1);

    public void importCheck(OrgImportTemplateDTO var1, MultipartFile var2);

    public void importSave(OrgImportTemplateDTO var1);

    public R getImportResult(OrgImportTemplateDTO var1);

    public void exportResultInfo(OrgImportTemplateDTO var1);
}

