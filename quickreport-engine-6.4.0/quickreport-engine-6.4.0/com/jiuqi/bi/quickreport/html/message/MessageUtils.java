/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.ParameterException
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.parameter.engine.ParameterEnvUtils
 *  com.jiuqi.bi.parameter.model.ParameterDimType
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  com.jiuqi.bi.parameter.model.datasource.DataSourceModel
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html.message;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.engine.ParameterEnvUtils;
import com.jiuqi.bi.parameter.model.ParameterDimType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.quickreport.QuickReportException;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.DimNameParameterFinder;
import com.jiuqi.bi.quickreport.html.message.MessageParser;
import com.jiuqi.bi.quickreport.html.message.PeriodFormatProcessor;
import com.jiuqi.bi.quickreport.sysoption.SysOptionManager;
import com.jiuqi.bi.util.StringUtils;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {
    private MessageUtils() {
    }

    public static void receiveMessage(String message, IParameterEnv env) throws QuickReportException {
        if (StringUtils.isEmpty((String)(message = MessageUtils.decodeMessage(MessageUtils.decodeMessage(message))))) {
            return;
        }
        HashMap<String, List<String>> pValuesMap = new HashMap<String, List<String>>();
        List<String> paramNames = MessageUtils.parse2ValuesMap(message, env, pValuesMap);
        MessageUtils.valid(pValuesMap);
        List sortedParamNames = ParameterEnvUtils.sortParaNameList((IParameterEnv)env, paramNames);
        try {
            MessageUtils.setParamDimTree(env, sortedParamNames, pValuesMap);
            MessageUtils.setParamValues(env, sortedParamNames, pValuesMap);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new QuickReportException(e);
        }
    }

    private static List<String> parse2ValuesMap(String message, IParameterEnv env, Map<String, List<String>> pValuesMap) {
        ArrayList<String> paramNames = new ArrayList<String>();
        MessageParser parser = new MessageParser(message);
        parser.getParamNames().forEach(msgName -> {
            String paramName = MessageUtils.msgName2paramName(msgName, env);
            if (StringUtils.isEmpty((String)paramName)) {
                return;
            }
            if (!paramNames.contains(paramName)) {
                paramNames.add(paramName);
                pValuesMap.put(paramName, parser.getParamValues((String)msgName));
            }
        });
        return paramNames;
    }

    public static String decodeMessage(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }
        message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            String tryISOMsg;
            message = SysOptionManager.getInstance().isMsgUTF8() ? (MessageUtils.isMessyCode(tryISOMsg = URLDecoder.decode(message.trim(), "UTF-8")) ? new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : tryISOMsg) : (MessageUtils.isMessyCode(tryISOMsg = new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)) ? URLDecoder.decode(message.trim(), "UTF-8") : tryISOMsg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    private static void setParamDimTree(IParameterEnv env, List<String> sortedParaNames, Map<String, List<String>> pValuesMap) throws ParameterException {
        DimNameParameterFinder finder = new DimNameParameterFinder();
        ArrayList<String> dimTreeParamNames = new ArrayList<String>();
        for (String paramName : sortedParaNames) {
            if (!paramName.toUpperCase().endsWith(".SYS_DIMTREE")) continue;
            dimTreeParamNames.add(paramName);
        }
        for (String paramName : dimTreeParamNames) {
            String dimName = paramName.split("\\.")[0];
            List<ParameterModel> parameters = finder.getParameters(env, dimName);
            for (ParameterModel parameter : parameters) {
                List<String> paramValues = pValuesMap.get(paramName);
                if (paramValues.isEmpty()) continue;
                env.setDimTree(parameter.getName(), paramValues.get(0));
            }
        }
        sortedParaNames.removeAll(dimTreeParamNames);
    }

    private static void setParamValues(IParameterEnv env, List<String> sortedParaNames, Map<String, List<String>> pValuesMap) throws ParameterException {
        for (String paramName : sortedParaNames) {
            List<String> values = pValuesMap.get(paramName);
            if (paramName.equals("GLOBALPERIOD_VALUE")) {
                MessageUtils.receiveGlobalPeriod_Value(values, env);
                continue;
            }
            ParameterModel p = env.getParameterModelByName(paramName);
            if (p == null) continue;
            if (p.isRangeParameter()) {
                if (p._isRangeCloned()) continue;
                String minValue = values.size() > 0 ? values.get(0) : null;
                String maxValue = values.size() > 1 ? values.get(1) : null;
                env.setKeyValueAsStringAndUpdateCascade(paramName + ".MIN", Collections.singletonList(minValue));
                env.setKeyValueAsStringAndUpdateCascade(paramName + ".MAX", Collections.singletonList(maxValue));
                continue;
            }
            env.setKeyValueAsStringAndUpdateCascade(paramName, values);
        }
    }

    private static String msgName2paramName(String msgName, IParameterEnv env) {
        if (msgName.startsWith(".")) {
            msgName = msgName.split("\\.")[1];
        } else if (msgName.startsWith("null.")) {
            msgName = msgName.split("null.")[1];
        } else if (msgName.startsWith("NULL.")) {
            msgName = msgName.split("NULL.")[1];
        }
        if (msgName.endsWith(".SYS_DIMTREE")) {
            return msgName;
        }
        boolean isMin = msgName.endsWith(".MIN");
        boolean isMax = msgName.endsWith(".MAX");
        if (isMin || isMax) {
            msgName = msgName.substring(0, msgName.length() - 4);
        }
        String paramName = null;
        if (env.containsParameterWithAlias(msgName)) {
            paramName = env.getParameterWithAlias(msgName).getName();
        } else if (env.containsParameter(msgName)) {
            paramName = env.getParameterModelByName(msgName).getName();
        }
        if (StringUtils.isEmpty((String)paramName)) {
            return null;
        }
        if (isMin || isMax) {
            paramName = paramName + (isMin ? ".MIN" : ".MAX");
        }
        return paramName;
    }

    private static void receiveGlobalPeriod_Value(List<String> parameterValues, IParameterEnv env) throws ParameterException {
        for (ParameterModel parameterModel : env.getParameterModels()) {
            DataSourceModel datasource = parameterModel.getDataSourceModel();
            if (datasource == null || datasource.getDimType() != ParameterDimType.TIME_DIM) continue;
            ArrayList<String> tempValues = new ArrayList<String>();
            for (String value : parameterValues) {
                tempValues.add(PeriodFormatProcessor.processGlobalPeriod(value, datasource.getTimegranularity(), datasource.getDataType(), datasource.getDataPattern()));
            }
            env.setKeyValueAsString(parameterModel.getName(), tempValues);
        }
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0.0f;
        for (char c : ch) {
            if (Character.isLetterOrDigit(c) || MessageUtils.isChinese(c)) continue;
            count += 1.0f;
        }
        float result = count / chLength;
        return (double)result > 0.4;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    private static void valid(Map<String, List<String>> pValuesMap) throws QuickReportException {
        if (!MessageUtils.isStrictlyVerifyIllegalCharacters()) {
            return;
        }
        Pattern pattern4Name = Pattern.compile("[a-zA-Z0-9_.#]*$");
        Pattern pattern4Value = Pattern.compile("[a-zA-Z0-9\u4e00-\u9fa5_.]*$");
        for (Map.Entry<String, List<String>> entry : pValuesMap.entrySet()) {
            String pName = entry.getKey();
            if (!pattern4Name.matcher(pName).matches()) {
                throw new QuickReportException("\u6d88\u606f\u53c2\u6570\u540d\u4e0d\u7b26\u5408\u89c4\u8303");
            }
            List<String> pValues = entry.getValue();
            for (String pValue : pValues) {
                if (pattern4Value.matcher(pValue).matches()) continue;
                throw new QuickReportException("\u6d88\u606f\u53c2\u6570\u503c\u542b\u6709\u975e\u6cd5\u5b57\u7b26");
            }
        }
    }

    private static boolean isStrictlyVerifyIllegalCharacters() {
        String bString = System.getProperty("StrictlyVerifyIllegalCharacters");
        return Boolean.parseBoolean(bString);
    }
}

