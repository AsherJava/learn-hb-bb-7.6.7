/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.init.carryover.entity.GcCarryOverSubjectMappingExcelModel;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GcCarryOverSubjectMappingImportExecutorImpl
extends AbstractImportExcelModelExecutor<GcCarryOverSubjectMappingExcelModel> {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;

    protected GcCarryOverSubjectMappingImportExecutorImpl() {
        super(GcCarryOverSubjectMappingExcelModel.class);
    }

    public String getName() {
        return "GcCarryOverSubjectMappingImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<GcCarryOverSubjectMappingExcelModel> rowDatas) {
        Map param = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String systemId = (String)param.get("systemId");
        String destSystemId = (String)param.get("destSystemId");
        Assert.isNotEmpty((String)systemId, (String)"\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)destSystemId, (String)"\u53c2\u8003\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        if (CollectionUtils.isEmpty(rowDatas)) {
            return null;
        }
        Map<String, String> srcCode2SubjectTitleMap = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId).stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getTitle));
        Map<String, String> destCode2SubjectTitleMap = this.consolidatedSubjectService.listAllSubjectsBySystemId(destSystemId).stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getTitle));
        Set<String> currentSubjectCodeSet = srcCode2SubjectTitleMap.keySet();
        Set<String> destSubjectCodeSet = destCode2SubjectTitleMap.keySet();
        StringBuffer errorInfo = new StringBuffer();
        ArrayList<CarryOverOffsetSubjectMappingVO> subjectMappingVOList = new ArrayList<CarryOverOffsetSubjectMappingVO>(rowDatas.size());
        for (int i = 0; i < rowDatas.size(); ++i) {
            boolean isError = false;
            StringBuffer errItem = new StringBuffer("\u7b2c" + (i + 1) + "\u884c");
            GcCarryOverSubjectMappingExcelModel model = rowDatas.get(i);
            if (!currentSubjectCodeSet.contains(model.getSrcSubjectCode())) {
                errItem.append("\u79d1\u76ee\u4ee3\u7801[" + model.getSrcSubjectCode() + "]\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728 ");
                isError = true;
            }
            if (!destSubjectCodeSet.contains(model.getDestSubjectCode())) {
                errItem.append("\u6620\u5c04\u79d1\u76ee\u4ee3\u7801[" + model.getDestSubjectCode() + "]\u5728\u76ee\u6807\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728");
                isError = true;
            }
            if (isError) {
                errorInfo.append(errItem.append("\n"));
                continue;
            }
            CarryOverOffsetSubjectMappingVO mappingVO = this.initCarryOverOffsetSubjectMappingVO(model.getSrcSubjectCode(), model.getDestSubjectCode(), srcCode2SubjectTitleMap, destCode2SubjectTitleMap);
            subjectMappingVOList.add(mappingVO);
        }
        if (errorInfo.length() > 0) {
            return errorInfo;
        }
        return subjectMappingVOList;
    }

    private CarryOverOffsetSubjectMappingVO initCarryOverOffsetSubjectMappingVO(String srcCode, String destCode, Map<String, String> srcCode2SubjectTitleMap, Map<String, String> destCode2SubjectTitleMap) {
        CarryOverOffsetSubjectMappingVO vo = new CarryOverOffsetSubjectMappingVO();
        vo.setSrcSubjectCode(srcCode);
        vo.setDestSubjectCode(destCode);
        vo.setSrcSubjectTitle(srcCode2SubjectTitleMap.get(srcCode));
        CarryOverOffsetSubjectVO subjectVO = new CarryOverOffsetSubjectVO();
        subjectVO.setCode(destCode);
        subjectVO.setTitle(destCode2SubjectTitleMap.get(destCode));
        vo.setDestSubjectTitle(subjectVO);
        return vo;
    }
}

