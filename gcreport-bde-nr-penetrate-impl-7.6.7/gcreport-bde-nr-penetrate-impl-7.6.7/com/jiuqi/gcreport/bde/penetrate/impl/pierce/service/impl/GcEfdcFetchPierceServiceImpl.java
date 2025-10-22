/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO
 *  com.jiuqi.nr.efdc.param.EfdcNewRequestInfo
 *  com.jiuqi.nr.efdc.param.EfdcResponseInfo
 *  com.jiuqi.nr.efdc.service.impl.EfdcPierceServiceImpl
 */
package com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcEfdcFetchPierceService;
import com.jiuqi.nr.efdc.param.EfdcNewRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcResponseInfo;
import com.jiuqi.nr.efdc.service.impl.EfdcPierceServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcEfdcFetchPierceServiceImpl
implements GcEfdcFetchPierceService {
    private static final Logger log = LoggerFactory.getLogger(GcEfdcFetchPierceService.class);
    private static final String EFDC_FORMULA_NOTES = "//";
    @Autowired
    EfdcPierceServiceImpl efdcPierceService;

    private EfdcResponseInfo queryEfdcPierceInfo(GcFetchPierceDTO efdcRequestInfo) {
        try {
            EfdcNewRequestInfo efdcNewRequestInfo = new EfdcNewRequestInfo();
            efdcNewRequestInfo.setLinkKey((String)efdcRequestInfo.getLinkKeys().get(0));
            efdcNewRequestInfo.setFormSchemeKey(efdcRequestInfo.getFormSchemeKey());
            efdcNewRequestInfo.setDimensionSet(efdcRequestInfo.getDimensionSet());
            return this.efdcPierceService.getResponseInfoNew(efdcNewRequestInfo);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new EfdcResponseInfo();
        }
    }

    @Override
    public EfdcFormulaResultVO efdcFormulaAnalysis(GcFetchPierceDTO efdcNewRequestInfo) {
        EfdcResponseInfo efdcResponseInfo = this.queryEfdcPierceInfo(efdcNewRequestInfo);
        String efdcFormula = efdcResponseInfo.getEfdcFormula();
        return this.buildEfdcFormula(efdcFormula);
    }

    @Override
    public Boolean judgeEfdcFormulaIsNotEmpty(GcFetchPierceDTO efdcNewRequestInfo) {
        EfdcResponseInfo efdcResponseInfo = this.queryEfdcPierceInfo(efdcNewRequestInfo);
        String efdcFormula = efdcResponseInfo.getEfdcFormula();
        if (StringUtils.isEmpty((String)efdcFormula)) {
            return false;
        }
        return true;
    }

    private EfdcFormulaResultVO buildEfdcFormula(String fetchFormula) {
        String regex;
        Pattern pattern;
        String[] matcher;
        EfdcFormulaResultVO efdcFormulaResult = new EfdcFormulaResultVO();
        if (StringUtils.isEmpty((String)fetchFormula) || !fetchFormula.toUpperCase().contains("ZFZDX")) {
            return efdcFormulaResult;
        }
        String efdcFormula = fetchFormula;
        if (efdcFormula.contains(EFDC_FORMULA_NOTES)) {
            efdcFormula = this.removeNotesOfFormula(fetchFormula);
        }
        efdcFormulaResult.setFormula(efdcFormula);
        ArrayList<String> subjectList = new ArrayList<String>();
        HashMap assistMap = new HashMap();
        ArrayList<String> formulaList = new ArrayList<String>();
        ArrayList<String> efdcIfElseThenFormulaList = new ArrayList<String>();
        if (efdcFormula.contains("if") && (matcher = (pattern = Pattern.compile(regex = "if\\s+(.*?)\\s+then\\s+(.*?)\\s+else\\s+(.*)", 32)).matcher(efdcFormula)).find()) {
            String condition = matcher.group(1).trim();
            String thenPart = matcher.group(2).trim();
            String elsePart = matcher.group(3).trim();
            efdcIfElseThenFormulaList.add(condition);
            efdcIfElseThenFormulaList.add(thenPart);
            efdcIfElseThenFormulaList.add(elsePart);
        }
        if (efdcFormula.contains("if")) {
            for (String partFormulaStr : efdcIfElseThenFormulaList) {
                for (String formulaStr : partFormulaStr.split("\\+")) {
                    formulaList.addAll(Arrays.asList(formulaStr.split("-")));
                }
            }
        } else {
            for (String formulaStr : efdcFormula.split("\\+")) {
                formulaList.addAll(Arrays.asList(formulaStr.split("-")));
            }
        }
        for (String formula : formulaList) {
            String assistFormula;
            if (!formula.toUpperCase().contains("ZFZDX")) continue;
            int leftBracketIndex = formula.indexOf("(");
            int rightBracketIndex = formula.indexOf(")");
            if (leftBracketIndex < 0 || rightBracketIndex < 0) continue;
            String bareBracketFormula = formula.substring(leftBracketIndex + 1, rightBracketIndex);
            String subjectAndAssistFormula = bareBracketFormula.substring(bareBracketFormula.indexOf(",") + 1);
            if ('\"' == subjectAndAssistFormula.charAt(0)) {
                int firstQuoteIndex;
                int secondQuoteIndex;
                String assistStr;
                String bareQuoteSubject = this.splitDoubleQuote(subjectAndAssistFormula);
                if (!StringUtils.isEmpty((String)bareQuoteSubject)) {
                    if (bareQuoteSubject.contains(",")) {
                        String[] split = bareQuoteSubject.split(",");
                        subjectList.add(split[0]);
                        subjectList.add(split[1]);
                    } else if (bareQuoteSubject.contains(":")) {
                        String[] split = bareQuoteSubject.split(":");
                        subjectList.add(split[0]);
                    }
                }
                if ((assistStr = subjectAndAssistFormula.substring((secondQuoteIndex = subjectAndAssistFormula.indexOf("\"", (firstQuoteIndex = subjectAndAssistFormula.indexOf("\"")) + 1)) + 1)).contains("<")) {
                    String[] splitStr = subjectAndAssistFormula.split("\"?<");
                    assistFormula = "\"<" + splitStr[1];
                } else {
                    assistFormula = null;
                }
            } else if (subjectAndAssistFormula.contains("<")) {
                String[] splitStr = subjectAndAssistFormula.split("\"?<");
                String subjectCode = splitStr[0].trim().replace(",", "");
                assistFormula = "\"<" + splitStr[1];
                subjectList.add(subjectCode);
            } else {
                assistFormula = null;
                subjectList.add(subjectAndAssistFormula);
            }
            if (StringUtils.isEmpty(assistFormula)) continue;
            String bareQuoteAssistFormula = this.splitDoubleQuote(assistFormula);
            Assert.isNotNull((Object)bareQuoteAssistFormula, (String)("EFDC\u516c\u5f0f\u4e0d\u89c4\u8303\uff0c\u622a\u53d6\u8f85\u52a9\u9879\u4e3a\u7a7a\uff1a" + formula), (Object[])new Object[0]);
            String[] assistAndValueArray = bareQuoteAssistFormula.split("<");
            for (int i = 1; i < assistAndValueArray.length; ++i) {
                String[] split = assistAndValueArray[i].split(">");
                if (assistMap.containsKey(split[0])) {
                    String assistValue = split[1];
                    if (!assistValue.contains(",")) {
                        ((List)assistMap.get(split[0])).add(split[1]);
                        continue;
                    }
                    for (String singleAssistValue : assistValue.split(",")) {
                        ((List)assistMap.get(split[0])).add(singleAssistValue);
                    }
                    continue;
                }
                LinkedList<String> assistList = new LinkedList<String>();
                String assistValue = split[1];
                if (!assistValue.contains(",")) {
                    assistList.add(split[1]);
                } else {
                    Collections.addAll(assistList, assistValue.split(","));
                }
                assistMap.put(split[0], assistList);
            }
        }
        efdcFormulaResult.setSubjectCodes(subjectList);
        efdcFormulaResult.setAssistMap(assistMap);
        return efdcFormulaResult;
    }

    private String removeNotesOfFormula(String efdcFormula) {
        int index = efdcFormula.indexOf(EFDC_FORMULA_NOTES);
        if (index == -1) {
            return efdcFormula;
        }
        return efdcFormula.substring(0, index);
    }

    private String splitDoubleQuote(String strWithDoubleQuote) {
        if (StringUtils.isEmpty((String)strWithDoubleQuote)) {
            return null;
        }
        int firstQuoteIndex = strWithDoubleQuote.indexOf("\"");
        int secondQuoteIndex = strWithDoubleQuote.indexOf("\"", firstQuoteIndex + 1);
        if (firstQuoteIndex != -1 && secondQuoteIndex != -1) {
            return strWithDoubleQuote.substring(firstQuoteIndex + 1, secondQuoteIndex);
        }
        return null;
    }
}

