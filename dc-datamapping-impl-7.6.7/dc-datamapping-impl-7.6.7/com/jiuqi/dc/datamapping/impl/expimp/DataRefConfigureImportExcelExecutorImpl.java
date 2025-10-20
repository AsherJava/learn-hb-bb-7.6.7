/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.dc.datamapping.impl.expimp;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefConfigureExpImpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DataRefConfigureImportExcelExecutorImpl
extends AbstractImportExcelExecutor {
    @Autowired
    private DataRefConfigureExpImpService expImpService;

    public String getName() {
        return "DataRefConfigureImportExcelExecutor";
    }

    public Object dataImport(MultipartFile importFile, ImportContext context) throws Exception {
        DataRefListDTO dto = (DataRefListDTO)JsonUtils.readValue((String)context.getParam(), DataRefListDTO.class);
        return this.expImpService.importExcel(importFile, dto);
    }
}

