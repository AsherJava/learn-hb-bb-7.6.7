/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 *  com.jiuqi.gcreport.journalsingle.condition.JournalPostRuleCondition
 */
package com.jiuqi.gcreport.journalsingle.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalPostRuleCondition;
import com.jiuqi.gcreport.journalsingle.dao.IJournalPostRuleDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalPostRuleEO;
import com.jiuqi.gcreport.journalsingle.service.IJournalPostRuleService;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalPostRuleServiceImpl
implements IJournalPostRuleService {
    @Autowired
    IJournalPostRuleDao dao;

    @Override
    public List<String> getSubjectCodesByZbId(JournalDetailCondition condi) {
        JournalPostRuleCondition postRuleCondition = new JournalPostRuleCondition();
        postRuleCondition.taskId = condi.taskId;
        postRuleCondition.schemeId = condi.schemeId;
        postRuleCondition.formId = condi.formId;
        postRuleCondition.zbId = condi.zbId;
        List<JournalPostRuleEO> ruleEOs = this.dao.queryListByCondition(postRuleCondition);
        if (ruleEOs == null || ruleEOs.isEmpty()) {
            return null;
        }
        JournalPostRuleEO journalPostRuleEO = ruleEOs.get(0);
        String formula = journalPostRuleEO.getFormula();
        if (StringUtils.isEmpty((String)formula)) {
            return null;
        }
        return this.getSubjectCodesByFormula(formula);
    }

    private List<String> getSubjectCodesByFormula(String formula) {
        LinkedList<String> subjectCodes = new LinkedList<String>();
        String subjectFormulaRegular = "l\\$\\(\\[SUBJECTCODE\\],\\d+\\)=\"\\d+\"";
        String numberRegular = "\\d+";
        Pattern formulaPattern = Pattern.compile(subjectFormulaRegular);
        Pattern numberPattern = Pattern.compile(numberRegular);
        Matcher m = formulaPattern.matcher(formula);
        LinkedList<String> subjectFormulalist = new LinkedList<String>();
        while (m.find()) {
            subjectFormulalist.add(m.group());
        }
        if (subjectFormulalist.isEmpty()) {
            return null;
        }
        for (String subjectFormula : subjectFormulalist) {
            LinkedList<String> numberList = new LinkedList<String>();
            Matcher subjectMat = numberPattern.matcher(subjectFormula);
            while (subjectMat.find()) {
                numberList.add(subjectMat.group());
            }
            int length = Integer.parseInt((String)numberList.get(0));
            subjectCodes.add(((String)numberList.get(1)).substring(0, length));
        }
        return subjectCodes;
    }
}

