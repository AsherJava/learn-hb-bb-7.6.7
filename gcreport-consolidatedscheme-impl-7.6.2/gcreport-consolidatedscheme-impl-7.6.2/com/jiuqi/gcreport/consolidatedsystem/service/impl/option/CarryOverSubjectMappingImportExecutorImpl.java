/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.option;

import com.google.common.collect.Maps;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.CarryOverSubjectMappingExcelModel;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CarryOverSubjectMappingImportExecutorImpl
extends AbstractImportExcelModelExecutor<CarryOverSubjectMappingExcelModel> {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;

    protected CarryOverSubjectMappingImportExecutorImpl() {
        super(CarryOverSubjectMappingExcelModel.class);
    }

    public String getName() {
        return "CarryOverSubjectMappingImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<CarryOverSubjectMappingExcelModel> rowDatas) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String systemId = (String)param.get("systemId");
        String destSystemId = (String)param.get("destSystemId");
        Assert.isNotEmpty((String)systemId, (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)destSystemId, (String)"\u53c2\u8003\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        if (CollectionUtils.isEmpty(rowDatas)) {
            return null;
        }
        Set<String> otherSubjectCodeSet = this.getOtherSystemSubjectCodeSet(systemId, destSystemId);
        Set currentSubjectCodeSet = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId).stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        Set destSubjectCodeSet = this.consolidatedSubjectService.listAllSubjectsBySystemId(destSystemId).stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        StringBuffer errorInfo = new StringBuffer();
        HashMap subjectToDestSubjectMap = Maps.newHashMapWithExpectedSize((int)rowDatas.size());
        for (int i = 0; i < rowDatas.size(); ++i) {
            boolean isError = false;
            StringBuffer errItem = new StringBuffer("\u7b2c" + (i + 1) + "\u884c");
            CarryOverSubjectMappingExcelModel model = rowDatas.get(i);
            if (otherSubjectCodeSet.contains(model.getDestSubjectCode())) {
                errItem.append("\u79d1\u76ee\u4ee3\u7801[" + model.getDestSubjectCode() + "]\u5df2\u5b58\u5728\u4e8e\u5176\u4ed6\u53c2\u8003\u4f53\u7cfb\uff0c\u4e0d\u53ef\u8bbe\u7f6e\uff01");
                isError = true;
            }
            if (!currentSubjectCodeSet.contains(model.getSrcSubjectCode())) {
                errItem.append("\u79d1\u76ee\u4ee3\u7801[" + model.getSrcSubjectCode() + "]\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728 ");
                isError = true;
            }
            if (!destSubjectCodeSet.contains(model.getDestSubjectCode())) {
                errItem.append("\u6620\u5c04\u79d1\u76ee\u4ee3\u7801[" + model.getDestSubjectCode() + "]\u5728\u53c2\u8003\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728");
                isError = true;
            }
            if (isError) {
                errorInfo.append(errItem.append("\n"));
                continue;
            }
            subjectToDestSubjectMap.put(model.getSrcSubjectCode(), model.getDestSubjectCode());
        }
        if (errorInfo.length() > 0) {
            return errorInfo;
        }
        return subjectToDestSubjectMap;
    }

    private Set<String> getOtherSystemSubjectCodeSet(String systemId, String destSystemId) {
        HashSet<String> result = new HashSet<String>();
        this.consolidatedOptionService.getOptionData(systemId).getCarryOverSubjectCodeMapping().forEach((currSubjectCode, desSystemId2SubjectCodeMap) -> {
            if (CollectionUtils.isEmpty(desSystemId2SubjectCodeMap) || !desSystemId2SubjectCodeMap.containsKey(destSystemId)) {
                return;
            }
            if (!StringUtils.isEmpty((String)((String)desSystemId2SubjectCodeMap.get(currSubjectCode)))) {
                result.add((String)currSubjectCode);
            }
        });
        return result;
    }
}

