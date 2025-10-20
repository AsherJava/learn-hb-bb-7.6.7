/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.subject.impl.subject.expimp;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpMode;
import com.jiuqi.common.subject.impl.subject.service.SubjectExpImpService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SubjectImportExcelExecutorImpl
extends AbstractImportExcelExecutor {
    @Autowired
    private SubjectExpImpService expImpService;
    private static final String IMP_MODE = "impMode";

    public String getName() {
        return "AcctSubjectImportExcelExecutor";
    }

    public Object dataImport(MultipartFile importFile, ImportContext context) throws Exception {
        JsonNode param = JsonUtils.readTree((String)context.getParam());
        SubjectImpMode impMode = SubjectImpMode.fromCode(Optional.ofNullable(param.get(IMP_MODE)).map(JsonNode::textValue).orElse(null));
        return this.expImpService.importExcel(importFile, impMode);
    }
}

