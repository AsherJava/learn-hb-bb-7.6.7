/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.option;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.CarryOverSubjectMappingExcelModel;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CarryOverSubjectMappingExportExecutorImpl
extends AbstractExportExcelModelExecutor<CarryOverSubjectMappingExcelModel> {
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;

    protected CarryOverSubjectMappingExportExecutorImpl() {
        super(CarryOverSubjectMappingExcelModel.class);
    }

    public String getName() {
        return "CarryOverSubjectMappingExportExecutor";
    }

    protected List exportExcelModels(ExportContext context) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String systemId = (String)param.get("systemId");
        String destSystemId = (String)param.get("destSystemId");
        Assert.isNotEmpty((String)systemId, (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)destSystemId, (String)"\u53c2\u8003\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        ConsolidatedOptionVO optionData = this.consolidatedOptionService.getOptionData(systemId);
        if (optionData == null) {
            return Collections.emptyList();
        }
        Map carryOverSubjectCodeMapping = optionData.getCarryOverSubjectCodeMapping();
        if (CollectionUtils.isEmpty(carryOverSubjectCodeMapping)) {
            return Collections.emptyList();
        }
        ArrayList exportModelList = new ArrayList();
        carryOverSubjectCodeMapping.forEach((currSubjectCode, desSystemId2SubjectCodeMap) -> {
            if (CollectionUtils.isEmpty(desSystemId2SubjectCodeMap) || !desSystemId2SubjectCodeMap.containsKey(destSystemId)) {
                return;
            }
            exportModelList.add(new CarryOverSubjectMappingExcelModel((String)currSubjectCode, (String)desSystemId2SubjectCodeMap.get(destSystemId)));
        });
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return exportModelList;
    }
}

