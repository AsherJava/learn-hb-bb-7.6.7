/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormulaSchemeData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.dataentry.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
    private static final String[] FILTER_STR = new String[]{"\u5bfc\u5165\u6570\u636e", "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", "\u6267\u884c\u4e0a\u62a5", "\u6267\u884c\u6279\u91cf\u4e0a\u62a5", "\u6dfb\u52a0\u6279\u6ce8", "\u4fee\u6539\u6279\u6ce8", "\u5bfc\u51fa\u6587\u4ef6"};

    public static StringBuilder buildMessing(JtableContext jtableContext, LogParam logParam, String title, IRunTimeViewController runtimeView, IJtableParamService jtableParamService, PeriodEngineService periodEngineService, IJtableEntityService jtableEntityService) {
        StringBuilder valueBuilder = new StringBuilder();
        if (null != jtableContext) {
            jtableContext.toString();
            String taskKey = jtableContext.getTaskKey();
            if (!StringUtils.isEmpty((String)taskKey)) {
                String[] splits = taskKey.split(";");
                String taskTitle = "";
                for (String oneTaskKey : splits) {
                    TaskDefine queryTaskDefine = null;
                    try {
                        queryTaskDefine = runtimeView.queryTaskDefine(oneTaskKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == queryTaskDefine) continue;
                    taskTitle = "".equals(taskTitle) ? queryTaskDefine.getTitle() : taskTitle + ";" + queryTaskDefine.getTitle();
                }
                taskKey = taskTitle;
            } else {
                taskKey = "\u6240\u6709\u4efb\u52a1";
            }
            valueBuilder.append("\u4efb\u52a1\u540d\u79f0:").append(taskKey).append(", ");
            String formSchemeKey = jtableContext.getFormSchemeKey();
            if (!StringUtils.isEmpty((String)formSchemeKey)) {
                String[] splits = formSchemeKey.split(";");
                String formSchemeKeyTitle = "";
                for (String oneFormSchemeKey : splits) {
                    FormSchemeDefine formSchemeDefine = null;
                    try {
                        formSchemeDefine = runtimeView.getFormScheme(oneFormSchemeKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == formSchemeDefine) continue;
                    formSchemeKeyTitle = "".equals(formSchemeKeyTitle) ? formSchemeDefine.getTitle() : formSchemeKeyTitle + ";" + formSchemeDefine.getTitle();
                }
                formSchemeKey = formSchemeKeyTitle;
            } else {
                formSchemeKey = "\u6240\u6709\u62a5\u8868\u65b9\u6848";
            }
            valueBuilder.append("\u62a5\u8868\u65b9\u6848\u540d\u79f0:").append(formSchemeKey).append(", ");
            List<String> filterStr = Arrays.asList(FILTER_STR);
            if (!filterStr.contains(title)) {
                String formulaSchemeKey = jtableContext.getFormulaSchemeKey();
                if (!StringUtils.isEmpty((String)formulaSchemeKey)) {
                    String[] splits = formulaSchemeKey.split(";");
                    String formulaSchemeTitle = "";
                    for (String oneFormSchemeKey : splits) {
                        FormulaSchemeData formulaSchemeData = null;
                        try {
                            formulaSchemeData = jtableParamService.getFormulaScheme(oneFormSchemeKey);
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (null == formulaSchemeData) continue;
                        formulaSchemeTitle = "".equals(formulaSchemeTitle) ? formulaSchemeData.getTitle() : formulaSchemeTitle + ";" + formulaSchemeData.getTitle();
                    }
                    formulaSchemeKey = formulaSchemeTitle;
                } else {
                    formulaSchemeKey = "\u6240\u6709\u516c\u5f0f\u65b9\u6848";
                }
                valueBuilder.append("\u516c\u5f0f\u65b9\u6848\u540d\u79f0:").append(formulaSchemeKey).append(", ");
            }
            String formKey = jtableContext.getFormKey();
            EntityViewData targetEntityInfo = jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            EntityViewData periodEntityInfo = jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
            String unilType = targetEntityInfo.getDimensionName();
            String dateType = periodEntityInfo.getDimensionName();
            HashMap<String, String> entityViewsMap = new HashMap<String, String>();
            for (EntityViewData entityViewData : jtableParamService.getEntityList(jtableContext.getFormSchemeKey())) {
                entityViewsMap.put(entityViewData.getDimensionName(), entityViewData.getKey());
            }
            Map dimensionSet = jtableContext.getDimensionSet();
            if (dimensionSet == null) {
                return valueBuilder;
            }
            FormSchemeDefine formScheme = runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
            IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                String value;
                if (((String)entry.getKey()).equals(dateType)) {
                    String periodTitle = periodProvider.getPeriodTitle(((DimensionValue)entry.getValue()).getValue());
                    valueBuilder.append("\u65f6\u671f:").append((String)entry.getKey()).append("|").append(periodTitle).append(", ");
                    continue;
                }
                if (((String)entry.getKey()).equals(unilType)) {
                    value = ((DimensionValue)entry.getValue()).getValue();
                    String unitTitle = null;
                    unitTitle = logParam != null && logParam.getKeyInfo() != null ? "\u76ee\u6807\u5355\u4f4d:" : "\u5355\u4f4d:";
                    if (StringUtils.isEmpty((String)value)) {
                        valueBuilder.append(unitTitle).append(entry.getKey()).append("|").append("\u6240\u6709\u5355\u4f4d").append(", ");
                        continue;
                    }
                    LoggerUtil.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, unitTitle, jtableEntityService);
                    continue;
                }
                value = ((DimensionValue)entry.getValue()).getValue();
                if (StringUtils.isEmpty((String)value)) {
                    valueBuilder.append("\u7ef4\u5ea6\u540d:").append((String)entry.getKey()).append("|").append("\u6240\u6709\u503c").append(",");
                    continue;
                }
                LoggerUtil.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, "\u7ef4\u5ea6", jtableEntityService);
            }
            if (title.equals("\u5bfc\u5165\u6570\u636e") || title.equals("\u6267\u884c\u4e0a\u62a5") || title.equals("\u6267\u884c\u6279\u91cf\u4e0a\u62a5")) {
                return new StringBuilder(valueBuilder.substring(0, valueBuilder.lastIndexOf(",")));
            }
            StringBuilder rang = new StringBuilder("\u62a5\u8868:");
            if (logParam != null && "\u6c47\u603b".equals(logParam.getKeyInfo())) {
                rang.setLength(0);
                rang.append("\u6c47\u603b\u8303\u56f4:");
                rang.append(logParam.getOrherMsg().get("recursive")).append(",\u62a5\u8868:");
            }
            if (logParam != null && "\u9009\u62e9\u6c47\u603b".equals(logParam.getKeyInfo())) {
                rang.setLength(0);
                rang.append("\u6c47\u603b\u8303\u56f4:");
                String string = (String)logParam.getOrherMsg().get("sourceKeys");
                LoggerUtil.appendEntity(jtableContext, rang, entityViewsMap, unilType, string, jtableEntityService);
                rang.append(",\u62a5\u8868:");
            }
            if (logParam != null && logParam.getOrherMsg().get("formKeys") != null) {
                formKey = (String)logParam.getOrherMsg().get("formKeys");
            }
            valueBuilder.append((CharSequence)rang);
            if (!(StringUtils.isEmpty((String)formKey) || "\u6267\u884c\u5168\u5ba1".equals(title) || formKey.equals("all"))) {
                String[] stringArray = formKey.split(";");
                boolean addFlag = false;
                for (String oneFormKey : stringArray) {
                    FormDefine formDefine = null;
                    try {
                        formDefine = runtimeView.queryFormById(oneFormKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == formDefine) continue;
                    if (addFlag) {
                        valueBuilder.append(",");
                    }
                    valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                    addFlag = true;
                }
            } else {
                formKey = "\u6240\u6709\u62a5\u8868";
                valueBuilder.append(formKey);
            }
            if (logParam != null && logParam.getKeyInfo() != null && logParam.getKeyInfo().contains("\u8282\u70b9\u68c0\u67e5")) {
                valueBuilder.append(", \u8bef\u5dee\u8303\u56f4:").append(logParam.getOrherMsg().get("errorRange")).append("");
            }
        } else {
            valueBuilder.append("\u672a\u8bb0\u5f55\u73af\u5883\u4fe1\u606f").append("");
        }
        return valueBuilder;
    }

    private static void appendEntity(JtableContext jtableContext, StringBuilder stringBuilder, Map<String, String> entityViewsMap, String unilType, String units, IJtableEntityService jtableEntityService) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(unilType);
        entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
        String[] split = units.split(";");
        entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
        entityQueryByKeysInfo.setContext(jtableContext);
        EntityByKeysReturnInfo entityByKeyReturnInfo = jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
        Map entitys = entityByKeyReturnInfo.getEntitys();
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    stringBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    stringBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        }
    }

    private static void appendEntity(JtableContext jtableContext, StringBuilder valueBuilder, Map<String, String> entityViewsMap, Map.Entry<String, DimensionValue> entry, String title, IJtableEntityService jtableEntityService) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(entry.getKey());
        Map entitys = null;
        if (null != entityViewKey && !"".equals(entityViewKey)) {
            entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
            String[] split = entry.getValue().getValue().split(";");
            entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
            entityQueryByKeysInfo.setContext(jtableContext);
            EntityByKeysReturnInfo entityByKeyReturnInfo = jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
            entitys = entityByKeyReturnInfo.getEntitys();
        }
        valueBuilder.append(title);
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    valueBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    valueBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        } else {
            valueBuilder.append(title).append("\u503c:").append(entry.getValue().getValue());
        }
        valueBuilder.append(", ");
    }
}

