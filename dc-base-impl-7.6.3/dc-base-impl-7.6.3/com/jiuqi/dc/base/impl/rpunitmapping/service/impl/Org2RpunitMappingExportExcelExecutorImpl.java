/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO
 */
package com.jiuqi.dc.base.impl.rpunitmapping.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Org2RpunitMappingExportExcelExecutorImpl
extends AbstractExportExcelModelExecutor<Org2RpunitMappingVO> {
    @Autowired
    private Org2RpunitMappingService org2RpunitMappingService;

    protected Org2RpunitMappingExportExcelExecutorImpl() {
        super(Org2RpunitMappingVO.class);
    }

    public String getName() {
        return "Org2RpunitMappingExportExecutor";
    }

    protected List<Org2RpunitMappingVO> exportExcelModels(ExportContext context) {
        JsonNode param = JsonUtils.readTree((String)context.getParam());
        context.getProgressData().setProgressValueAndRefresh(0.1);
        Org2RpunitMappingQueryVO oppUnitMappingQueryVO = (Org2RpunitMappingQueryVO)JsonUtils.readValue((String)context.getParam(), Org2RpunitMappingQueryVO.class);
        oppUnitMappingQueryVO.setPageNum(Integer.valueOf(-1));
        List results = this.org2RpunitMappingService.query(oppUnitMappingQueryVO).getDataList();
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return results;
    }
}

