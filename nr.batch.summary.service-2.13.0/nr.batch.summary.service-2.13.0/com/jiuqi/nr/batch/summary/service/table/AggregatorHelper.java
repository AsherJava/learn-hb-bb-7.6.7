/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.aggregator.AverageAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.CountAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.DistinctCountAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregateType;
import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.MaxAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.MinAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.NoneAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.StringCountAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.StringDistinctCountAggregator;
import com.jiuqi.nr.batch.summary.service.table.aggregator.SumAggregator;
import com.jiuqi.nr.batch.summary.service.table.comparator.BigDecimalComparator;
import com.jiuqi.nr.batch.summary.service.table.comparator.DoubleComparator;
import com.jiuqi.nr.batch.summary.service.table.comparator.IntegerComparator;
import com.jiuqi.nr.batch.summary.service.table.comparator.StringComparator;
import com.jiuqi.nr.batch.summary.service.table.converter.BigDecimalConverter;
import com.jiuqi.nr.batch.summary.service.table.converter.DoubleConverter;
import com.jiuqi.nr.batch.summary.service.table.converter.IntegerConverter;
import com.jiuqi.nr.batch.summary.service.table.converter.StringConverter;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AggregatorHelper {
    protected Set<String> groupColumns;
    protected Map<String, IAggregator<?>> colAggregatorMap = new HashMap();
    protected Map<String, IPowerTableColumn> aggregateColumnMap = new LinkedHashMap<String, IPowerTableColumn>();

    public AggregatorHelper(Set<String> groupColumns, Set<IPowerTableColumn> aggregateColumns) {
        this.groupColumns = groupColumns;
        aggregateColumns.forEach(column -> this.aggregateColumnMap.put(column.getColumnCode(), (IPowerTableColumn)column));
    }

    public boolean containsColumn(String columnCode) {
        return this.aggregateColumnMap.containsKey(columnCode);
    }

    public String[] getAggregateColumns() {
        return this.aggregateColumnMap.keySet().toArray(new String[0]);
    }

    public Object aggregateColData(String columnCode, Object[] colData) {
        IAggregator<?> aggregator = this.colAggregatorMap.get(columnCode);
        if (aggregator == null) {
            IPowerTableColumn powerTableColumn = this.aggregateColumnMap.get(columnCode);
            aggregator = AggregatorHelper.createAggregator(powerTableColumn);
            this.colAggregatorMap.put(columnCode, aggregator);
        }
        return aggregator.aggregate(colData);
    }

    public static IAggregator<?> createAggregator(IPowerTableColumn powerTableColumn) {
        IAggregateType aggregateType = powerTableColumn.getAggregateType();
        if (IAggregateType.NO_AGGREGATION == aggregateType) {
            return new NoneAggregator<Object>(powerTableColumn.getAnyValue());
        }
        ColumnModelType columnModelType = powerTableColumn.getColumnType();
        switch (columnModelType) {
            case DOUBLE: {
                return AggregatorHelper.createDoubleAggregator(aggregateType);
            }
            case UUID: 
            case STRING: {
                return AggregatorHelper.createStringAggregator(aggregateType);
            }
            case INTEGER: {
                return AggregatorHelper.createIntegerAggregator(aggregateType);
            }
            case BIGDECIMAL: {
                return AggregatorHelper.createBigDecimalAggregator(aggregateType);
            }
        }
        throw new IllegalArgumentException("Unsupported data type: " + columnModelType);
    }

    private static IAggregator<Double> createDoubleAggregator(IAggregateType aggregateType) {
        DoubleConverter doubleConverter = new DoubleConverter();
        DoubleComparator doubleComparator = new DoubleComparator();
        switch (aggregateType) {
            case SUM: {
                return new SumAggregator<Double>(doubleConverter);
            }
            case AVERAGE: {
                return new AverageAggregator<Double>(doubleConverter);
            }
            case MAX: {
                return new MaxAggregator<Double>(doubleConverter, doubleComparator);
            }
            case MIN: {
                return new MinAggregator<Double>(doubleConverter, doubleComparator);
            }
            case COUNT: {
                return new CountAggregator<Double>();
            }
            case DISTINCT_COUNT: {
                return new DistinctCountAggregator<Double>(doubleConverter);
            }
        }
        throw new IllegalArgumentException("Unsupported aggregation type: " + (Object)((Object)aggregateType));
    }

    private static IAggregator<?> createStringAggregator(IAggregateType aggregateType) {
        StringConverter doubleConverter = new StringConverter();
        StringComparator doubleComparator = new StringComparator();
        switch (aggregateType) {
            case COUNT: {
                return new StringCountAggregator();
            }
            case DISTINCT_COUNT: {
                return new StringDistinctCountAggregator();
            }
            case MAX: {
                return new MaxAggregator<String>(doubleConverter, doubleComparator);
            }
            case MIN: {
                return new MinAggregator<String>(doubleConverter, doubleComparator);
            }
        }
        throw new IllegalArgumentException("Unsupported aggregation type for String: " + (Object)((Object)aggregateType));
    }

    private static IAggregator<Integer> createIntegerAggregator(IAggregateType aggregateType) {
        IntegerConverter doubleConverter = new IntegerConverter();
        IntegerComparator doubleComparator = new IntegerComparator();
        switch (aggregateType) {
            case SUM: {
                return new SumAggregator<Integer>(doubleConverter);
            }
            case AVERAGE: {
                return new AverageAggregator<Integer>(doubleConverter);
            }
            case MAX: {
                return new MaxAggregator<Integer>(doubleConverter, doubleComparator);
            }
            case MIN: {
                return new MinAggregator<Integer>(doubleConverter, doubleComparator);
            }
            case COUNT: {
                return new CountAggregator<Integer>();
            }
            case DISTINCT_COUNT: {
                return new DistinctCountAggregator<Integer>(doubleConverter);
            }
        }
        throw new IllegalArgumentException("Unsupported aggregation type: " + (Object)((Object)aggregateType));
    }

    private static IAggregator<BigDecimal> createBigDecimalAggregator(IAggregateType aggregateType) {
        BigDecimalConverter doubleConverter = new BigDecimalConverter();
        BigDecimalComparator doubleComparator = new BigDecimalComparator();
        switch (aggregateType) {
            case SUM: {
                return new SumAggregator<BigDecimal>(doubleConverter);
            }
            case AVERAGE: {
                return new AverageAggregator<BigDecimal>(doubleConverter);
            }
            case MAX: {
                return new MaxAggregator<BigDecimal>(doubleConverter, doubleComparator);
            }
            case MIN: {
                return new MinAggregator<BigDecimal>(doubleConverter, doubleComparator);
            }
            case COUNT: {
                return new CountAggregator<BigDecimal>();
            }
            case DISTINCT_COUNT: {
                return new DistinctCountAggregator<BigDecimal>(doubleConverter);
            }
        }
        throw new IllegalArgumentException("Unsupported aggregation type: " + (Object)((Object)aggregateType));
    }
}

