/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.efdc.internal.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.efdc.exception.EFDCException;
import com.jiuqi.nr.efdc.pojo.QueryEntity;
import com.jiuqi.nr.efdc.pojo.QueryEntityImpl;
import com.jiuqi.nr.efdc.pojo.QueryEntityInfo;
import java.util.List;
import java.util.stream.Collectors;

public class QueryEntityFormatter {
    private static final String ENTITYITEMCONNECTOR = "@";
    private static final String ENTITYITEMSEPERTOR = ";";

    public static String formatToString(QueryEntityInfo masterEntity) {
        StringBuilder builder = new StringBuilder();
        List sortedTableNames = masterEntity.getDimessionNames().stream().sorted((o1, o2) -> o1.compareTo((String)o2)).collect(Collectors.toList());
        for (String tableName : sortedTableNames) {
            builder.append(masterEntity.getQueryEntityKey(tableName)).append(ENTITYITEMCONNECTOR).append(tableName).append(ENTITYITEMSEPERTOR);
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static QueryEntity parsingFromString(String formatMasterEntity) {
        QueryEntityImpl masterEntity = new QueryEntityImpl();
        if (!StringUtils.isEmpty((String)formatMasterEntity)) {
            for (String entityText : formatMasterEntity.split(ENTITYITEMSEPERTOR)) {
                String entityKey;
                if (StringUtils.isEmpty((String)entityText)) continue;
                int sepIndex = entityText.indexOf(ENTITYITEMCONNECTOR);
                if (sepIndex < 0 || sepIndex == entityText.length() - 1) {
                    throw new EFDCException(String.format("QueryEntitySet format error. error key is %s.", formatMasterEntity));
                }
                String tableName = entityText.substring(sepIndex + 1);
                if (StringUtils.isEmpty((String)tableName)) {
                    throw new EFDCException(String.format("QueryEntitySet format error. error key is %s.", formatMasterEntity));
                }
                try {
                    entityKey = entityText.substring(0, sepIndex);
                }
                catch (Exception e) {
                    throw new EFDCException(String.format("QueryEntitySet format error. error key is %s.", formatMasterEntity));
                }
                masterEntity.setQueryEntityDimessionValue(tableName, entityKey);
            }
        }
        return masterEntity;
    }
}

