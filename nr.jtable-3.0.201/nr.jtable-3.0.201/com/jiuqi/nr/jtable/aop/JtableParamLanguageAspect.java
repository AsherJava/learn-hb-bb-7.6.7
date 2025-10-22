/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.nr.jtable.aop;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component(value="JtableParamLanguageAspect")
public class JtableParamLanguageAspect {
    private static final String SPLIT = "_";
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    @Pointcut(value="execution(public * com.jiuqi.nr.jtable.service.impl.JtableParamServiceImpl.getLink(String))")
    public void getLink() {
    }

    @Pointcut(value="execution(public * com.jiuqi.nr.jtable.service.impl.JtableParamServiceImpl.getLinks(String))")
    public void getLinks() {
    }

    @Around(value="getLink()")
    public Object aroundGetLink(ProceedingJoinPoint point) throws Throwable {
        Object resultValue = point.proceed();
        Locale locale = NpContextHolder.getContext().getLocale();
        if (locale == null || locale.getLanguage().equals(Locale.CHINESE.getLanguage())) {
            return resultValue;
        }
        if (resultValue instanceof EnumLinkData) {
            EnumLinkData enumLinkData = (EnumLinkData)resultValue;
            this.changeEnumFields(enumLinkData, locale);
            resultValue = enumLinkData;
        }
        return resultValue;
    }

    @Around(value="getLinks()")
    public Object aroundGetLinks(ProceedingJoinPoint point) throws Throwable {
        Object resultValue = point.proceed();
        Locale locale = NpContextHolder.getContext().getLocale();
        if (locale == null || locale.getLanguage().equals(Locale.CHINESE.getLanguage())) {
            return resultValue;
        }
        if (resultValue instanceof List) {
            List listValues = (List)resultValue;
            for (Object currentValue : listValues) {
                if (!(currentValue instanceof EnumLinkData)) continue;
                this.changeEnumFields((EnumLinkData)currentValue, locale);
            }
            resultValue = listValues;
        }
        return resultValue;
    }

    private void changeEnumFields(EnumLinkData enumLinkData, Locale locale) throws Exception {
        List<String> captionFields = enumLinkData.getCapnames();
        String prefix = locale.toString();
        if (captionFields != null && captionFields.size() > 0) {
            for (int index = 0; index < captionFields.size(); ++index) {
                FieldDefine langField;
                String fieldKey = captionFields.get(index);
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                boolean isLanguageField = this.isLanguageField(fieldDefine);
                if (!isLanguageField || (langField = this.getLanguageField(fieldDefine, prefix)) == null) continue;
                captionFields.set(index, langField.getKey());
            }
        }
        enumLinkData.setCapnames(captionFields);
        List<String> dropFields = enumLinkData.getDropnames();
        if (dropFields != null && dropFields.size() > 0) {
            for (int index = 0; index < dropFields.size(); ++index) {
                FieldDefine langField;
                String fieldKey = dropFields.get(index);
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                boolean isLanguageField = this.isLanguageField(fieldDefine);
                if (!isLanguageField || (langField = this.getLanguageField(fieldDefine, prefix)) == null) continue;
                dropFields.set(index, langField.getKey());
            }
        }
        enumLinkData.setDropnames(dropFields);
        Map<String, String> enumPosMap = enumLinkData.getEnumFieldPosMap();
        if (enumPosMap != null && enumPosMap.size() > 0) {
            ArrayList<String> removeKeys = new ArrayList<String>();
            HashMap<String, String> addValues = new HashMap<String, String>();
            for (Map.Entry<String, String> enumPos : enumPosMap.entrySet()) {
                FieldDefine langField;
                String fieldKey = enumPos.getKey();
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                boolean isLanguageField = this.isLanguageField(fieldDefine);
                if (!isLanguageField || (langField = this.getLanguageField(fieldDefine, prefix)) == null) continue;
                removeKeys.add(fieldKey);
                addValues.put(langField.getKey(), enumPos.getValue());
            }
            if (removeKeys.size() > 0) {
                for (String removeKey : removeKeys) {
                    enumPosMap.remove(removeKey);
                }
                enumPosMap.putAll(addValues);
            }
        }
        enumLinkData.setEnumFieldPosMap(enumPosMap);
    }

    private FieldDefine getLanguageField(FieldDefine fieldDefine, String language) throws Exception {
        String fieldCode = fieldDefine.getCode().concat(SPLIT).concat(language);
        return this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode.toUpperCase(), fieldDefine.getOwnerTableKey());
    }

    private boolean isLanguageField(FieldDefine fieldDefine) {
        return fieldDefine != null && (fieldDefine.getValueType() == FieldValueType.FIELD_VALUE_UNIT_NAME || fieldDefine.getValueType() == FieldValueType.FIELD_VALUE_DICTIONARY_TITLE);
    }
}

