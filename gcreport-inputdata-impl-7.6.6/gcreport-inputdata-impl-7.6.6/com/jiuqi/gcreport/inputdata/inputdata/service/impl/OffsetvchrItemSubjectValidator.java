/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator$ValidatorResult
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 */
package com.jiuqi.gcreport.inputdata.inputdata.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OffsetvchrItemSubjectValidator
implements ConsolidatedSubjectValidator {
    @Autowired
    private GcOffSetItemAdjustCoreService coreService;

    public ConsolidatedSubjectValidator.ValidatorResult deleteValidator(List<ConsolidatedSubjectEO> allDelSubject) {
        Map subjectCode2EoMap;
        String systemId = allDelSubject.get(0).getSystemId();
        List subjectCodeList = this.coreService.listExistsSubjectCodes(systemId, (subjectCode2EoMap = allDelSubject.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, Function.identity(), (o1, o2) -> o1))).keySet());
        if (CollectionUtils.isEmpty((Collection)subjectCodeList)) {
            return ConsolidatedSubjectValidator.ValidatorResult.success();
        }
        StringJoiner subjectInfo = new StringJoiner(",", "\u3010", "\u3011");
        subjectCodeList.forEach(item -> subjectInfo.add(item + "|" + ((ConsolidatedSubjectEO)subjectCode2EoMap.get(item)).getTitle()));
        return ConsolidatedSubjectValidator.ValidatorResult.error((String)(subjectInfo + "\u5df2\u7ecf\u5b58\u5728\u62b5\u9500\u5206\u5f55\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5220\u9664"));
    }
}

