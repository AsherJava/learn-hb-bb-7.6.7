/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.exception.BpmException;
import java.util.List;
import java.util.stream.Collectors;

public class MasterEntityFormatter {
    private static final String ENTITYITEMCONNECTOR = "@";
    private static final String ENTITYITEMSEPERTOR = ";";
    private static final String EMPTY = "";

    public static String formatToString(MasterEntityInfo masterEntity) {
        if (masterEntity.dimessionSize() == 0) {
            return EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        List sortedTableNames = masterEntity.getDimessionNames().stream().sorted((o1, o2) -> o1.compareTo((String)o2)).collect(Collectors.toList());
        for (String tableName : sortedTableNames) {
            builder.append(masterEntity.getMasterEntityKey(tableName)).append(ENTITYITEMCONNECTOR).append(tableName).append(ENTITYITEMSEPERTOR);
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static MasterEntity parsingFromString(String formatMasterEntity) {
        MasterEntityImpl masterEntity = new MasterEntityImpl();
        if (!StringUtils.isEmpty((String)formatMasterEntity) && !EMPTY.equals(formatMasterEntity)) {
            for (String entityText : formatMasterEntity.split(ENTITYITEMSEPERTOR)) {
                String entityKey;
                if (StringUtils.isEmpty((String)entityText)) continue;
                int sepIndex = entityText.indexOf(ENTITYITEMCONNECTOR);
                if (sepIndex < 0 || sepIndex == entityText.length() - 1) {
                    throw new BpmException(String.format("MasterEntitySet format error. error key is %s.", formatMasterEntity));
                }
                String tableName = entityText.substring(sepIndex + 1);
                if (StringUtils.isEmpty((String)tableName)) {
                    throw new BpmException(String.format("MasterEntitySet format error. error key is %s.", formatMasterEntity));
                }
                try {
                    entityKey = entityText.substring(0, sepIndex);
                }
                catch (Exception e) {
                    throw new BpmException(String.format("MasterEntitySet format error. error key is %s.", formatMasterEntity));
                }
                masterEntity.setMasterEntityDimessionValue(tableName, entityKey);
            }
        }
        return masterEntity;
    }
}

