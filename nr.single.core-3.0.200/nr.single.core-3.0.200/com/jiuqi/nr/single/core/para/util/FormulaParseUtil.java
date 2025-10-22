/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.util;

import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.ParaInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulaParseUtil {
    private static final String[] doSecondPara_funcName = new String[]{"inList", "getMeaning"};

    public static String ConvertFormula(String formulaCode, String expression, Map<String, LinkTaskBean> linkTaskMap, ParaInfo paraInfo, String rptCode) {
        expression = FormulaParseUtil.PeriodOffset(expression, paraInfo.getTaskType());
        expression = FormulaParseUtil.ConvertFmzb(formulaCode, expression, paraInfo);
        expression = FormulaParseUtil.ConvertRptZB(expression, paraInfo, rptCode);
        if (linkTaskMap != null && linkTaskMap.size() > 0) {
            expression = FormulaParseUtil.ConvertLinkTask(expression, linkTaskMap);
        }
        for (String funcName : doSecondPara_funcName) {
            expression = FormulaParseUtil.DoSecondPara(funcName, expression);
        }
        expression = FormulaParseUtil.ConvertExist(expression);
        expression = FormulaParseUtil.ConvertBD(expression, paraInfo);
        return expression;
    }

    private static String ConvertLinkTask(String expression, Map<String, LinkTaskBean> linkTaskMap) {
        String re1 = "(.*?)";
        String re2 = "(^|[^a-z0-9_])";
        String re3 = "([a-z0-9_]*)";
        String re4 = "(\\[[^\\]]*\\]\\s*){0,1}";
        String re5 = "(@)";
        String re6 = "([0-9]*)";
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private static String ConvertBD(String expression, ParaInfo paraInfo) {
        Map<String, String> basedataNameMap = paraInfo.getBasedataNameMap();
        String fmlExpression = expression;
        for (String key : basedataNameMap.keySet()) {
            fmlExpression = FormulaParseUtil.ConvertExpression(fmlExpression, key, basedataNameMap.get(key));
        }
        return fmlExpression;
    }

    private static String ConvertFmzb(String formulaCode, String expression, ParaInfo paraInfo) {
        List<String> list = paraInfo.getFmzbList();
        String prefix = "[" + paraInfo.getPrefix() + "FMDM";
        Map<String, String> basedataNameMap = paraInfo.getBasedataNameMap();
        String newExpression = "";
        for (String str : list) {
            newExpression = FormulaParseUtil.ConvertExpression(expression, str, prefix + str + "]");
            if (formulaCode != null && basedataNameMap.containsKey(str) && !expression.equals(newExpression)) {
                paraInfo.getExceptionList().add("\u516c\u5f0f\u3010" + formulaCode + "\u3011\u5b58\u5728\u5c01\u9762\u6307\u6807\u4e0e\u57fa\u7840\u6570\u636e\u540c\u540d\u60c5\u51b5:" + str);
            }
            expression = newExpression;
        }
        expression = expression.replace("\\[\\[", "\\[");
        expression = expression.replace("\\]\\]", "\\]");
        return expression;
    }

    private static String ConvertRptZB(String expression, ParaInfo paraInfo, String defaultRptCode) {
        return expression;
    }

    private static String ConvertExist(String expression) {
        String re1 = "((?:.*?))";
        String re2 = "(exist)";
        String re3 = "(\\s*)";
        String re4 = "(\\()";
        String re5 = "([a-zA-Z0-9_]*)";
        String re6 = "(\\s*)";
        String re7 = "(\\))";
        String re8 = "((?:.*?))";
        StringBuilder sb = new StringBuilder();
        return sb.toString().trim();
    }

    private static String ConvertExpression(String expression, String sourceStr, String targetStr) {
        String re1 = "((?:.*?))";
        String re2 = "((?:[^a-z0-9]))";
        String re3 = "((?:" + sourceStr + "\\s*))";
        String re4 = "((?:[^a-z0-9@\\s]))";
        String re5 = "((?:.*?))";
        StringBuilder sb = new StringBuilder();
        return sb.toString().trim();
    }

    private static String ConvertReportCode(String fmlExpression, Map<String, String> jiosrRpCodeMap, String linkTaskprefix) {
        for (String key : jiosrRpCodeMap.keySet()) {
            fmlExpression = FormulaParseUtil.ConvertExpression(fmlExpression, key, (linkTaskprefix == null ? "" : linkTaskprefix) + key);
        }
        return fmlExpression;
    }

    public static String[] CheckLink(String expression) {
        String linkStr = "";
        String re1 = "((?:.*?))";
        String re2 = "(@)";
        String re3 = "((?:\\d+))";
        StringBuilder sb = new StringBuilder();
        String[] ret = new String[]{sb.toString(), linkStr};
        return ret;
    }

    private static String ReplaceFirst(String str, String regex, String replacement) {
        StringBuilder sb = new StringBuilder();
        if (!str.contains(regex)) {
            return str;
        }
        int index = str.indexOf(regex);
        sb.append(str.substring(0, index - 0)).append(replacement).append(str.substring(index + regex.length()));
        return sb.toString();
    }

    private static String DoSecondPara(String type, String expression) {
        ProcessObject processObject = FormulaParseUtil.Process(type, expression);
        Map<String, String> map = processObject.getMap();
        expression = processObject.getExpression();
        for (String string : map.keySet()) {
            String valueRen = map.get(string);
            String before = valueRen.substring(0, valueRen.indexOf(40) + 1 - 0);
            String sonValue = valueRen.substring(valueRen.indexOf(40) + 1, valueRen.indexOf(40) + 1 + (valueRen.length() - 1) - (valueRen.indexOf(40) + 1));
            sonValue = FormulaParseUtil.DoSecondPara(type, sonValue);
            ProcessObject sonProcessObject = FormulaParseUtil.Process("", sonValue);
            Map sonMap = sonProcessObject.map;
            sonValue = sonProcessObject.expression;
            valueRen = before + sonValue + ")";
            valueRen = FormulaParseUtil.DoSecondPara(valueRen);
            for (String sonKey : sonMap.keySet()) {
                valueRen = FormulaParseUtil.ReplaceFirst(valueRen, sonKey, (String)sonMap.get(sonKey));
            }
            map.put(string, valueRen);
        }
        for (Map.Entry entry : map.entrySet()) {
            String keyCode = (String)entry.getKey();
            expression = FormulaParseUtil.ReplaceFirst(expression, keyCode, (String)entry.getValue());
        }
        return expression;
    }

    private static ProcessObject Process(String type, String expression) {
        return new ProcessObject();
    }

    private static String DoSecondPara(String expression) {
        String re1 = "([^(]*)";
        String re2 = "([(]*)";
        String re8 = "(\\[.*\\])?";
        String re3 = "([^,]*)";
        String re4 = "([,])";
        String re5 = "([\\s]*)";
        String re6 = "([\\w]*)";
        String re7 = "(.*?)";
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private static String PeriodOffset(String expression, String taskPeriodType) {
        String reg = "(.*?)(\\])(\\.)([-]{0,1})([0-9]*)([NYD]{0,1})";
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public static class ProcessObject {
        private Map<String, String> map = new HashMap<String, String>();
        private String expression = "";

        public Map<String, String> getMap() {
            return this.map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }

        public String getExpression() {
            return this.expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }
}

