/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.financialcheckImpl.util.BaseDataUtils;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCheckRuleConverter {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    private static final String AGEING_PRIFIX = "AGE_";

    public FinancialCheckRuleDTO convert2ConSubject(FinancialCheckRuleDTO rule) {
        FinancialCheckRuleDTO ruleClone = rule.clone();
        Map<String, String> accountSubjectCode2ParentCodeMap = BaseDataUtils.getCode2ParentCodeMap("MD_ACCTSUBJECT");
        Set<String> conSubjectSet = this.consolidatedSubjectService.listAllSubjectsBySystemId(rule.getReportSystem()).stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        rule.setDebitItemList(this.getConsSubjectCode(rule.getDebitItemList(), accountSubjectCode2ParentCodeMap, conSubjectSet));
        rule.setCreditItemList(this.getConsSubjectCode(rule.getCreditItemList(), accountSubjectCode2ParentCodeMap, conSubjectSet));
        return ruleClone;
    }

    private List<String> getConsSubjectCode(List<String> accountSubjectCodes, Map<String, String> accountSubjectCode2ParentCodeMap, Set<String> conSubjectSet) {
        ArrayList<String> result = new ArrayList<String>();
        for (String subjectCode : accountSubjectCodes) {
            String consSubjectCode = this.getConsSubjectCode(subjectCode, accountSubjectCode2ParentCodeMap, conSubjectSet);
            Assert.isNotEmpty((String)consSubjectCode, (String)("\u89c4\u5219\u6570\u636e\u6e90\u4e2d\u7684\u79d1\u76ee\u8f6c\u6362\u5931\u8d25\uff0c\u539f\u59cb\u79d1\u76ee\u4ee3\u7801\uff1a" + subjectCode), (Object[])new Object[0]);
            result.add(consSubjectCode);
        }
        return result;
    }

    public Set<String> getAllBoundSubjects(FinancialCheckRuleDTO rule) {
        List creditSubjectCodeList = rule.getSrcCreditSubjectCodeList();
        List debitSubjectCodeList = rule.getSrcDebitSubjectCodeList();
        HashSet boundSubjects = new HashSet();
        boundSubjects.addAll(creditSubjectCodeList);
        boundSubjects.addAll(debitSubjectCodeList);
        if (CollectionUtils.isEmpty(boundSubjects)) {
            throw new BusinessRuntimeException("\u89c4\u5219: " + rule.getTitle() + "\u7684\u6570\u636e\u6e90\u4e3a\u7a7a");
        }
        HashSet<String> allBoundSubjects = new HashSet<String>();
        allBoundSubjects.addAll(boundSubjects);
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        boundSubjects.forEach(boundSubject -> allBoundSubjects.addAll(tool.queryAllBasedataItemsByParentid("MD_ACCTSUBJECT", boundSubject).stream().map(GcBaseData::getCode).collect(Collectors.toSet())));
        return allBoundSubjects;
    }

    public String getConsSubjectCode(String accountSubjectCode, Map<String, String> accountSubjectCode2ParentCodeMap, Set<String> conSubjectSet) {
        if (conSubjectSet.contains(accountSubjectCode)) {
            return accountSubjectCode;
        }
        String parentCode = accountSubjectCode2ParentCodeMap.get(accountSubjectCode);
        if (null == parentCode) {
            return null;
        }
        return this.getConsSubjectCode(parentCode, accountSubjectCode2ParentCodeMap, conSubjectSet);
    }
}

