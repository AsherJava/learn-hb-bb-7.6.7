/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class ClbrFilterColumnHandlerUtils {
    private static final Logger log = LoggerFactory.getLogger(ClbrFilterColumnHandlerUtils.class);

    public static void handlerColumns(Map<String, Object> otherColumnsCondition, StringBuffer whereSql, String alias, List<Object> paramList) {
        for (Map.Entry<String, Object> entry : otherColumnsCondition.entrySet()) {
            Double item;
            SimpleDateFormat simpleDateFormat;
            String columnKey = entry.getKey();
            Object columnValue = entry.getValue();
            if (ObjectUtils.isEmpty(columnValue)) continue;
            if ("OPPCLBRTYPE".equals(columnKey)) {
                Map columnValueMap = (Map)entry.getValue();
                whereSql.append(" ").append(alias).append(".OPPCLBRTYPE like '%").append(columnValueMap.get("code")).append("%' ").append(" AND ");
                continue;
            }
            if (columnKey.contains("_smaller")) {
                if (columnKey.contains("_DATETIME")) {
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date begin = simpleDateFormat.parse(entry.getValue().toString());
                        columnValue = begin;
                    }
                    catch (ParseException e) {
                        log.error("\u65e5\u671f\u683c\u5f0f\u5316\u5f02\u5e38\uff1a", e);
                    }
                } else {
                    item = Double.parseDouble(columnValue.toString());
                    columnValue = item;
                }
                int endIndex = columnKey.indexOf("_smaller");
                columnKey = columnKey.substring(0, endIndex);
                whereSql.append(" ").append(alias).append(".").append(columnKey).append(" >= ? ").append(" AND ");
                paramList.add(columnValue);
                continue;
            }
            if (columnKey.contains("_bigger")) {
                if (columnKey.contains("_DATETIME")) {
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        String dateStr = entry.getValue().toString().split("T")[0];
                        Date begin = simpleDateFormat.parse(dateStr);
                        columnValue = begin;
                    }
                    catch (ParseException e) {
                        log.error("\u65e5\u671f\u683c\u5f0f\u5316\u5f02\u5e38\uff1a", e);
                    }
                } else {
                    item = Double.parseDouble(columnValue.toString());
                    columnValue = item;
                }
                int endIndex = columnKey.indexOf("_bigger");
                columnKey = columnKey.substring(0, endIndex);
                whereSql.append(" ").append(alias).append(".").append(columnKey).append(" <= ? ").append(" AND ");
                paramList.add(columnValue);
                continue;
            }
            whereSql.append(" ").append(alias).append(".").append(columnKey).append(" = ? ").append(" AND ");
            paramList.add(entry.getValue());
        }
    }
}

