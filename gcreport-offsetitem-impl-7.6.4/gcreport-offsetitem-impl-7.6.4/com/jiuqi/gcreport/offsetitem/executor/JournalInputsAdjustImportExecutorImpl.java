/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.offsetitem.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor;
import com.jiuqi.gcreport.offsetitem.utils.GCOffSetItemAdjustImportUtil;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class JournalInputsAdjustImportExecutorImpl
extends AbstractImportExcelExecutor {
    public String getName() {
        return "JournalInputsAdjustImportExecutor";
    }

    public Object dataImport(MultipartFile importFile, ImportContext context) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)context.getParam(), QueryParamsVO.class);
        return GCOffSetItemAdjustImportUtil.parse(importFile, queryParamsVO);
    }
}

