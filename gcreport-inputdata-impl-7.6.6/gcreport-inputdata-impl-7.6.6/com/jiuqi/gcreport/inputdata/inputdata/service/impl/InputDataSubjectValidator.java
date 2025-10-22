/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator$ValidatorResult
 */
package com.jiuqi.gcreport.inputdata.inputdata.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputDataSubjectValidator
implements ConsolidatedSubjectValidator {
    @Autowired
    private InputDataDao inputDataDao;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;

    public ConsolidatedSubjectValidator.ValidatorResult deleteValidator(List<ConsolidatedSubjectEO> allDelSubject) {
        Map subjectCode2EoMap;
        String systemId = allDelSubject.get(0).getSystemId();
        ConsolidatedSystemEO consolidatedSystemEO = this.consolidatedSystemService.getConsolidatedSystemEO(systemId);
        String dataSchemeKey = consolidatedSystemEO.getDataSchemeKey();
        List<String> subjectCodeList = this.inputDataDao.listSubjectCodeBySystemIdAndSubjectCode(dataSchemeKey, systemId, (subjectCode2EoMap = allDelSubject.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, Function.identity(), (o1, o2) -> o1))).keySet());
        if (CollectionUtils.isEmpty(subjectCodeList)) {
            return ConsolidatedSubjectValidator.ValidatorResult.success();
        }
        StringJoiner subjectInfo = new StringJoiner(",", "\u3010", "\u3011");
        subjectCodeList.forEach(item -> subjectInfo.add(item + "|" + ((ConsolidatedSubjectEO)subjectCode2EoMap.get(item)).getTitle()));
        return ConsolidatedSubjectValidator.ValidatorResult.error((String)(subjectInfo + "\u5df2\u7ecf\u5b58\u5728\u5185\u90e8\u8868\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u5220\u9664"));
    }
}

