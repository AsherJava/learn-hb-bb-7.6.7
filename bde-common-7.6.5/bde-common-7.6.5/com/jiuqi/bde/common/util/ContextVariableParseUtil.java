/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 */
package com.jiuqi.bde.common.util;

import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.GcOrgUtils;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContextVariableParseUtil {
    private static final String OPEN_TOKEN = "#";
    private static final String CLOSE_TOKEN = "#";
    private static String TMPL_VARIABLE = "#%1$s#";

    public static String parse(String text, FetchTaskContext fetchTaskContext) {
        return ContextVariableParseUtil.parse(text, ContextVariableParseUtil.initContextVariableMap(fetchTaskContext));
    }

    public static String parse(String text, Map<String, String> variableMap) {
        if (variableMap == null || variableMap.isEmpty()) {
            return text;
        }
        return ContextVariableParseUtil.parse(text, variableMap, "#", "#");
    }

    private static String parse(String text, Map<String, String> variableMap, String openToken, String closeToken) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        int start = (text = text.replace("'#'", "'\\#'").replace("'#%'", "'\\#%'")).indexOf(openToken);
        if (start == -1) {
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do {
            if (start > 0 && src[start - 1] == '\\') {
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
                continue;
            }
            if (expression == null) {
                expression = new StringBuilder();
            } else {
                expression.setLength(0);
            }
            builder.append(src, offset, start - offset);
            offset = start + openToken.length();
            int end = text.indexOf(closeToken, offset);
            while (end > -1) {
                if (end > offset && src[end - 1] == '\\') {
                    expression.append(src, offset, end - offset - 1).append(closeToken);
                    offset = end + closeToken.length();
                    end = text.indexOf(closeToken, offset);
                    continue;
                }
                expression.append(openToken).append(src, offset, end - offset).append(closeToken);
                break;
            }
            if (end == -1) {
                builder.append(src, start, src.length - start);
                offset = src.length;
                continue;
            }
            builder.append(ContextVariableParseUtil.replaceVariable(expression.toString(), variableMap));
            offset = end + closeToken.length();
        } while ((start = text.indexOf(openToken, offset)) > -1);
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    private static String replaceVariable(String expression, Map<String, String> variableMap) {
        for (Map.Entry<String, String> entry : variableMap.entrySet()) {
            if (entry.getKey() == null || !entry.getKey().equalsIgnoreCase(expression)) continue;
            return entry.getValue() == null ? "" : entry.getValue();
        }
        return expression;
    }

    public static Map<String, String> initContextVariableMap(FetchTaskContext fetchTaskContext) {
        String year = "";
        String period = "";
        if (!StringUtils.isEmpty((String)fetchTaskContext.getEndDateStr())) {
            Date endDate = DateUtils.parse((String)fetchTaskContext.getEndDateStr());
            year = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)1));
            period = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)2));
        }
        LinkedHashMap<String, String> precastParamMap = new LinkedHashMap<String, String>(16);
        List<OrgMappingItem> orgMappingItems = fetchTaskContext.getOrgMapping().getOrgMappingItems();
        if (CollectionUtils.isEmpty(orgMappingItems)) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.UNITCODE.getCode()), fetchTaskContext.getOrgMapping().getAcctOrgCode());
        } else {
            HashSet<String> orgCodeSet = new HashSet<String>();
            for (OrgMappingItem orgMappingItem : orgMappingItems) {
                if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
                orgCodeSet.add(orgMappingItem.getAcctOrgCode());
            }
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.UNITCODE.getCode()), String.join((CharSequence)"','", orgCodeSet));
        }
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.RPUNITCODE.getCode()), fetchTaskContext.getOrgMapping().getReportOrgCode());
        if (CollectionUtils.isEmpty(orgMappingItems) && StringUtils.isEmpty((String)fetchTaskContext.getOrgMapping().getAssistCode())) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ASSISTCODE.getCode()), "");
        } else if (CollectionUtils.isEmpty(orgMappingItems)) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ASSISTCODE.getCode()), fetchTaskContext.getOrgMapping().getAssistCode());
        } else {
            HashSet<String> assistCodeSet = new HashSet<String>();
            for (OrgMappingItem orgMappingItem : orgMappingItems) {
                if (StringUtils.isEmpty((String)orgMappingItem.getAssistCode())) continue;
                assistCodeSet.add(orgMappingItem.getAssistCode());
            }
            if (!CollectionUtils.isEmpty(assistCodeSet)) {
                precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ASSISTCODE.getCode()), String.join((CharSequence)"','", assistCodeSet));
            } else {
                precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ASSISTCODE.getCode()), "");
            }
        }
        if (CollectionUtils.isEmpty(orgMappingItems) && StringUtils.isEmpty((String)fetchTaskContext.getOrgMapping().getAcctBookCode())) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.BOOKCODE.getCode()), "");
        } else if (CollectionUtils.isEmpty(orgMappingItems)) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.BOOKCODE.getCode()), fetchTaskContext.getOrgMapping().getAcctBookCode());
        } else {
            HashSet<String> assistBookSet = new HashSet<String>();
            for (OrgMappingItem orgMappingItem : orgMappingItems) {
                if (StringUtils.isEmpty((String)orgMappingItem.getAcctBookCode())) continue;
                assistBookSet.add(orgMappingItem.getAcctBookCode());
            }
            if (!CollectionUtils.isEmpty(assistBookSet)) {
                precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.BOOKCODE.getCode()), String.join((CharSequence)"','", assistBookSet));
            } else {
                precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.BOOKCODE.getCode()), "");
            }
        }
        if (!StringUtils.isEmpty((String)fetchTaskContext.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)fetchTaskContext.getEndAdjustPeriod())) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ADJUST_START_PERIOD.getCode()), fetchTaskContext.getStartAdjustPeriod());
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ADJUST_END_PERIOD.getCode()), fetchTaskContext.getEndAdjustPeriod());
        }
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.YEARPERIOD.getCode()), String.format("%1$s-%2$s", year, CommonUtil.lpad((String)period, (String)"0", (int)2)));
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.PERIODSCHEME.getCode()), fetchTaskContext.getPeriodScheme());
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.YEAR.getCode()), year);
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.PERIOD.getCode()), period);
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.FULLPERIOD.getCode()), CommonUtil.lpad((String)period, (String)"0", (int)2));
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.STARTDATE.getCode()), BdeCommonUtil.formatDateNoDash(fetchTaskContext.getStartDateStr()));
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.ENDDATE.getCode()), BdeCommonUtil.formatDateNoDash(fetchTaskContext.getEndDateStr()));
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.TASKID.getCode()), fetchTaskContext.getTaskId());
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.INCLUDEUNCHARGED.getCode()), Boolean.TRUE.equals(fetchTaskContext.getIncludeUncharged()) ? "1" : "0");
        precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.SELFANDCHILDUNIT.getCode()), GcOrgUtils.unitToStringCommaSeparated(fetchTaskContext));
        if (fetchTaskContext.getOtherEntity() != null) {
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.MD_GCADJTYPE.getCode()), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_GCADJTYPE.getCode()));
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.MD_CURRENCY.getCode()), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_CURRENCY.getCode()));
            precastParamMap.put(ContextVariableParseUtil.getVariable(ArgumentValueEnum.MD_GCORGTYPE.getCode()), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_GCORGTYPE.getCode()));
        }
        if (fetchTaskContext.getExtParam() != null && !fetchTaskContext.getExtParam().isEmpty()) {
            for (Map.Entry<String, String> extParamEntry : fetchTaskContext.getExtParam().entrySet()) {
                precastParamMap.put(ContextVariableParseUtil.getVariable(extParamEntry.getKey()), extParamEntry.getValue());
            }
        }
        return precastParamMap;
    }

    public static String getVariable(String variable) {
        return String.format(TMPL_VARIABLE, variable);
    }
}

