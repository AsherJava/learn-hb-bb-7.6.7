/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.option.CarryOverSubjectMappingExcelModel
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.CarryOverSubjectMappingExcelModel;
import com.jiuqi.gcreport.offsetitem.init.carryover.entity.GcCarryOverSubjectMappingExcelModel;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GcCarryOverSubjectMappingExportExecutorImpl
extends AbstractExportExcelModelExecutor<GcCarryOverSubjectMappingExcelModel> {
    @Autowired
    private GcCarryOverConfigService configService;

    protected GcCarryOverSubjectMappingExportExecutorImpl() {
        super(GcCarryOverSubjectMappingExcelModel.class);
    }

    public String getName() {
        return "GcCarryOverSubjectMappingExportExecutor";
    }

    protected List exportExcelModels(ExportContext context) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String systemId = ConverterUtils.getAsString(param.get("systemId"));
        String destSystemId = ConverterUtils.getAsString(param.get("destSystemId"));
        String carryOverSchemeId = ConverterUtils.getAsString(param.get("carryOverSchemeId"));
        Assert.isNotEmpty((String)systemId, (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)destSystemId, (String)"\u53c2\u8003\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)carryOverSchemeId, (String)"\u5e74\u7ed3\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        String configOptionDataStr = this.configService.getConfigOptionById(carryOverSchemeId);
        CarryOverOffsetConfigVO optionData = (CarryOverOffsetConfigVO)JsonUtils.readValue((String)configOptionDataStr, CarryOverOffsetConfigVO.class);
        if (optionData == null) {
            return Collections.emptyList();
        }
        Map subjectMappingSetByDestSystemId = optionData.getSubjectMappingSetByDestSystemId(destSystemId);
        if (CollectionUtils.isEmpty(subjectMappingSetByDestSystemId)) {
            return Collections.emptyList();
        }
        ArrayList exportModelList = new ArrayList();
        subjectMappingSetByDestSystemId.forEach((srcSubjectCode, destSubjectCode) -> {
            if (StringUtils.isEmpty((String)srcSubjectCode) || StringUtils.isEmpty((String)destSubjectCode)) {
                return;
            }
            exportModelList.add(new CarryOverSubjectMappingExcelModel(srcSubjectCode, destSubjectCode));
        });
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return exportModelList;
    }
}

