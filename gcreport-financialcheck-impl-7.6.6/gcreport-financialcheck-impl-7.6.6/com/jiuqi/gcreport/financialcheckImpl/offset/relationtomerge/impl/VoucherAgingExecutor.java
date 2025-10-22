/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl;

import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.MdAgeLoader;
import com.jiuqi.gcreport.financialcheckImpl.util.AgeCalendar;
import com.jiuqi.gcreport.financialcheckImpl.util.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO;
import com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class VoucherAgingExecutor {
    public void calcAging(List<GcFcRuleUnOffsetDataDTO> relatedItemList, String periodStr) {
        AgeCalendar ageCalendar = new AgeCalendar(periodStr);
        Map<String, MdSubjectAgingDTO> code2SubjectAgingMap = MdAgeLoader.getCode2SubjectAgingMap(ageCalendar.getPeriodTypeCode());
        Map<String, String> acctSubjectCode2ParentCodeMap = BaseDataUtils.getCode2ParentCodeMap("MD_ACCTSUBJECT");
        relatedItemList.forEach(item -> {
            MdSubjectAgingDTO subjectAging = this.getSubjectAging(item.getSubjectCode(), code2SubjectAgingMap, acctSubjectCode2ParentCodeMap);
            String ageBaseDataCode = this.matchAgeBaseDataCode(ageCalendar, item.getCreateDate(), subjectAging);
            HashMap<String, Double> ageMap = new HashMap<String, Double>(4);
            ageMap.put(ageBaseDataCode, item.getAmt());
            item.addFieldValue("AGINGRANGE", ageMap);
        });
    }

    private MdSubjectAgingDTO getSubjectAging(String subjectCode, Map<String, MdSubjectAgingDTO> code2SubjectAgingMap, Map<String, String> acctSubjectCode2ParentCodeMap) {
        MdSubjectAgingDTO subjectAging = code2SubjectAgingMap.get(subjectCode);
        if (null != subjectAging) {
            return subjectAging;
        }
        String parentSubjectCode = acctSubjectCode2ParentCodeMap.get(subjectCode);
        if (StringUtil.isEmpty((String)parentSubjectCode) || "-".equals(parentSubjectCode)) {
            return null;
        }
        subjectAging = this.getSubjectAging(parentSubjectCode, code2SubjectAgingMap, acctSubjectCode2ParentCodeMap);
        code2SubjectAgingMap.put(subjectCode, subjectAging);
        return subjectAging;
    }

    private String matchAgeBaseDataCode(AgeCalendar ageCalendar, Date createDate, MdSubjectAgingDTO subjectAgingDTO) {
        if (null == subjectAgingDTO) {
            return null;
        }
        for (MdAgingDTO mdAgingDTO : subjectAgingDTO.getAgingList()) {
            Integer endPeriod;
            int periodNum = ageCalendar.compareTo(createDate, mdAgingDTO.getPeriodType());
            Integer beginPeriod = mdAgingDTO.getBeginPeriod();
            boolean match = AgeCalendar.match(beginPeriod, periodNum, endPeriod = mdAgingDTO.getEndPeriod());
            if (!match) continue;
            return mdAgingDTO.getCode();
        }
        return subjectAgingDTO.getDefaultAgingCode();
    }
}

