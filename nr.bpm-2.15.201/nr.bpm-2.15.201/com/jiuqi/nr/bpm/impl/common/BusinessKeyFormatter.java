/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.exception.BpmException;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessKeyFormatter {
    private static final String PERIODBEGIN = "P{";
    private static final String FORMSCHEMEBEGIN = "FS{";
    private static final String ENTITYBEGIN = "E{";
    private static final String FORMBEGIN = "F{";
    private static final String END = "}";
    private static final String ENTITYITEMCONNECTOR = "@";
    private static final String ENTITYITEMSEPERTOR = ";";

    public static String formatToString(BusinessKeyInfo businessKey) {
        StringBuilder builder = new StringBuilder();
        MasterEntityInfo masterEntity = businessKey.getMasterEntityInfo();
        builder.append(ENTITYBEGIN);
        if (masterEntity.dimessionSize() > 0) {
            List sortedTableNames = masterEntity.getDimessionNames().stream().sorted((o1, o2) -> o1.compareTo((String)o2)).collect(Collectors.toList());
            for (String tableName : sortedTableNames) {
                builder.append(masterEntity.getMasterEntityKey(tableName)).append(ENTITYITEMCONNECTOR).append(tableName).append(ENTITYITEMSEPERTOR);
            }
            builder.setLength(builder.length() - 1);
        }
        builder.append(END);
        builder.append(FORMBEGIN).append(businessKey.getFormKey() == null ? "" : businessKey.getFormKey()).append(END);
        builder.append(PERIODBEGIN).append(businessKey.getPeriod() == null ? "" : businessKey.getPeriod()).append(END);
        builder.append(FORMSCHEMEBEGIN).append(businessKey.getFormSchemeKey() == null ? "" : businessKey.getFormSchemeKey()).append(END);
        return builder.toString();
    }

    public static BusinessKey parsingFromString(String formatBusinessKey) {
        String formIdText;
        String periodText;
        BusinessKeyImpl businessKey = new BusinessKeyImpl();
        StringBuilder text = new StringBuilder(formatBusinessKey);
        String formSchemeText = BusinessKeyFormatter.fatchFromTail(text, FORMSCHEMEBEGIN, END);
        if (!StringUtils.isEmpty((String)formSchemeText)) {
            String formSchemeId = formSchemeText;
            businessKey.setFormSchemeKey(formSchemeId);
        }
        if (!StringUtils.isEmpty((String)(periodText = BusinessKeyFormatter.fatchFromTail(text, PERIODBEGIN, END)))) {
            businessKey.setPeriod(periodText);
        }
        if (!StringUtils.isEmpty((String)(formIdText = BusinessKeyFormatter.fatchFromTail(text, FORMBEGIN, END)))) {
            String formKey = formIdText;
            businessKey.setFormKey(formKey);
        }
        MasterEntityImpl masterEntity = new MasterEntityImpl();
        String entitiesText = BusinessKeyFormatter.fatchFromTail(text, ENTITYBEGIN, END);
        if (!StringUtils.isEmpty((String)entitiesText)) {
            for (String entityText : entitiesText.split(ENTITYITEMSEPERTOR)) {
                if (StringUtils.isEmpty((String)entityText)) continue;
                int sepIndex = entityText.indexOf(ENTITYITEMCONNECTOR);
                if (sepIndex < 0 || sepIndex == entityText.length() - 1) {
                    throw new BpmException(String.format("ProcessInstanceBusinessKey format error. error key is %s.", formatBusinessKey));
                }
                String tableName = entityText.substring(sepIndex + 1);
                if (StringUtils.isEmpty((String)tableName)) {
                    throw new BpmException(String.format("ProcessInstanceBusinessKey format error. error key is %s.", formatBusinessKey));
                }
                String entityKey = entityText.substring(0, sepIndex);
                if (StringUtils.isEmpty((String)entityKey)) {
                    throw new BpmException(String.format("ProcessInstanceBusinessKey format error. error key is %s.", formatBusinessKey));
                }
                masterEntity.setMasterEntityDimessionValue(tableName, entityKey);
            }
        }
        businessKey.setMasterEntity(masterEntity);
        return businessKey;
    }

    private static String fatchFromTail(StringBuilder text, String beginWith, String endWith) {
        if (text.length() == 0) {
            return "";
        }
        int beginIndexOfEndWith = text.lastIndexOf(endWith);
        if (beginIndexOfEndWith < 0) {
            throw new BpmException(String.format("ProcessInstanceBusinessKey format error. error key is %s.", text));
        }
        int beginIndex = text.lastIndexOf(beginWith);
        if (beginIndex < 0 || beginIndex >= beginIndexOfEndWith) {
            throw new BpmException(String.format("ProcessInstanceBusinessKey format error. error key is %s.", text));
        }
        String matchText = text.substring(beginIndex + beginWith.length(), beginIndexOfEndWith);
        text.setLength(beginIndex);
        return matchText;
    }
}

