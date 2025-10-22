/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.np.dataengine.log.LogRow;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogRowCollector {
    List<LogRow> logRows = new ArrayList<LogRow>();

    public List<LogRow> getLogRows() {
        return this.logRows;
    }

    public LinkedHashMap<String, Integer> getTableSizeTopN(int count) {
        Map<String, Integer> tableSizeMap = this.logRows.stream().filter(r -> r.isQuerySql()).collect(Collectors.groupingBy(LogRow::getTableName, Collectors.summingInt(LogRow::getRowCount)));
        long maxSize = tableSizeMap.size() > count ? (long)count : (long)tableSizeMap.size();
        return tableSizeMap.entrySet().stream().sorted(Map.Entry.comparingByValue().reversed()).limit(maxSize).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }
}

