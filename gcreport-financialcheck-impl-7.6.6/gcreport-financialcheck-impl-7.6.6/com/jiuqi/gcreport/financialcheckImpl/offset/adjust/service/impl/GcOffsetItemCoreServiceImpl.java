/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffsetItemCoreService
 *  com.jiuqi.gcreport.org.impl.period.PeriodTypeEnum
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.impl;

import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.FinancialCheckOffsetService;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.MdAgeLoader;
import com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO;
import com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffsetItemCoreService;
import com.jiuqi.gcreport.org.impl.period.PeriodTypeEnum;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOffsetItemCoreServiceImpl
implements GcOffsetItemCoreService {
    @Autowired
    private FinancialCheckOffsetService financialCheckOffsetService;
    @Autowired
    private IRuntimeTaskService taskService;

    public void save(GcOffSetVchrDTO gcOffSetVchrDTO) {
        this.financialCheckOffsetService.batchSaveOffsetData(gcOffSetVchrDTO);
    }

    public List<String> getAgingBySubjectCode(String subjectCode, String taskId) {
        TaskDefine taskDefine = this.taskService.queryTaskDefine(taskId);
        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.findById((int)taskDefine.getPeriodType().type());
        Map<String, MdSubjectAgingDTO> subjectCode2SubjectAgingMap = MdAgeLoader.getCode2SubjectAgingMap(String.valueOf(periodTypeEnum.getCode()));
        return subjectCode2SubjectAgingMap.get(subjectCode) == null ? new ArrayList<String>() : subjectCode2SubjectAgingMap.get(subjectCode).getAgingList().stream().map(MdAgingDTO::getCode).collect(Collectors.toList());
    }
}

