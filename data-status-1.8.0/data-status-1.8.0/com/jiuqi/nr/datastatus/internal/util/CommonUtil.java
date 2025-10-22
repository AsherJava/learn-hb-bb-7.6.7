/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nvwa.dataengine.util.DataValueUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datastatus.internal.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.datastatus.facade.obj.DimensionInfo;
import com.jiuqi.nvwa.dataengine.util.DataValueUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static Map<String, ITempTable> getTempTable(ITempTableManager tempTableManager, int maxInSize, DimensionValueSet dimensionValueSet, List<DimensionInfo> dimensionInfos) throws Exception {
        HashMap<String, ITempTable> tempAssistantTables = new HashMap<String, ITempTable>();
        for (DimensionInfo dimensionInfo : dimensionInfos) {
            List dimValueList;
            Object value = dimensionValueSet.getValue(dimensionInfo.getDimensionName());
            if (!(value instanceof List) || (dimValueList = (List)value).size() < maxInSize) continue;
            ITempTable oneKeyTempTable = tempTableManager.getOneKeyTempTable();
            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
            for (Object filterValue : dimValueList) {
                Object resultValue = DataValueUtils.formatData((int)ColumnModelType.STRING.getValue(), filterValue);
                Object[] batchArray = new Object[]{resultValue};
                batchValues.add(batchArray);
            }
            oneKeyTempTable.insertRecords(batchValues);
            tempAssistantTables.put(dimensionInfo.getColName(), oneKeyTempTable);
        }
        return tempAssistantTables;
    }

    public static String getExistsSelectSql(ITempTable tempTable, String relationCol) {
        return "(Select 1 From " + tempTable.getTableName() + " where " + "TEMP_KEY" + "=" + relationCol + ")";
    }

    public static DimensionValueSet getMergeDimensionValueSet(DimensionCollection dimensionCollection) {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            return new DimensionValueSet();
        }
        if (allDimension.size() == 1) {
            return allDimension.get(0);
        }
        return CommonUtil.merge(allDimension);
    }

    public static DimensionValueSet merge(Collection<DimensionValueSet> allDimension) {
        LinkedHashMap dimensionMap = new LinkedHashMap();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (dimensionMap.containsKey(name)) {
                    ((Set)dimensionMap.get(name)).add(value);
                    continue;
                }
                HashSet<Object> valueSet = new HashSet<Object>();
                valueSet.add(value);
                dimensionMap.put(name, valueSet);
            }
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            String dimensionName = (String)entry.getKey();
            Set valueSet = (Set)entry.getValue();
            if (valueSet.size() == 1) {
                for (Object o : valueSet) {
                    dimensionValueSet.setValue(dimensionName, o);
                }
                continue;
            }
            dimensionValueSet.setValue(dimensionName, new ArrayList(valueSet));
        }
        return dimensionValueSet;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        ConcurrentHashMap seen = new ConcurrentHashMap();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

