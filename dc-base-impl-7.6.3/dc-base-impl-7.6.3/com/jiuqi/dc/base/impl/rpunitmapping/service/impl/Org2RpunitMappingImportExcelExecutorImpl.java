/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO
 */
package com.jiuqi.dc.base.impl.rpunitmapping.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Org2RpunitMappingImportExcelExecutorImpl
extends AbstractImportExcelModelExecutor<Org2RpunitMappingVO> {
    private static final String ACCT_YEAR = "acctYear";
    @Autowired
    private Org2RpunitMappingService org2RpunitMappingService;

    protected Org2RpunitMappingImportExcelExecutorImpl() {
        super(Org2RpunitMappingVO.class);
    }

    public String getName() {
        return "Org2RpunitMappingImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<Org2RpunitMappingVO> rowDatas) {
        JsonNode param = JsonUtils.readTree((String)context.getParam());
        Integer acctYear = Optional.ofNullable(param.get(ACCT_YEAR)).map(JsonNode::intValue).orElse(null);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        return this.org2RpunitMappingService.importExcel(acctYear, rowDatas);
    }
}

