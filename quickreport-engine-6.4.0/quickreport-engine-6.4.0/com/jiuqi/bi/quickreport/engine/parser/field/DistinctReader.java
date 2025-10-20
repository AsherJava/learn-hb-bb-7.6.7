/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.IDSFilter
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.quickreport.engine.parser.field;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.parser.field.DistinctFilter;
import com.jiuqi.bi.quickreport.engine.parser.field.IFieldReader;
import com.jiuqi.bi.syntax.SyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistinctReader
implements IFieldReader {
    private final IFieldReader reader;
    private final DSField field;
    private Optional<List<Integer>> distinctCols;

    public DistinctReader(IFieldReader reader, DSField field) {
        this.reader = reader;
        this.field = field;
    }

    @Override
    public Object read(BIDataSet ds) throws SyntaxException {
        BIDataSet dataset = this.distinct(ds);
        return this.reader.read(dataset);
    }

    private BIDataSet distinct(BIDataSet ds) throws SyntaxException {
        if (this.field.getFieldType() != FieldType.MEASURE || ds.getRecordCount() <= 1) {
            return ds;
        }
        if (this.distinctCols == null) {
            this.distinctCols = this.createDistinctCols(ds);
        }
        if (!this.distinctCols.isPresent()) {
            return ds;
        }
        try {
            return ds.filter((IDSFilter)new DistinctFilter(this.distinctCols.get()));
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    private Optional<List<Integer>> createDistinctCols(BIDataSet ds) {
        Column measure = ds.getMetadata().find(this.field.getName());
        if (((BIDataSetFieldInfo)measure.getInfo()).getRefDimCols().isEmpty()) {
            return Optional.empty();
        }
        Map<String, Column<BIDataSetFieldInfo>> refColumns = this.findCols(ds, ((BIDataSetFieldInfo)measure.getInfo()).getRefDimCols());
        if (refColumns.isEmpty()) {
            return Optional.empty();
        }
        Optional<Column<BIDataSetFieldInfo>> distinctable = this.isDistinctable(ds, refColumns);
        if (distinctable == null) {
            return Optional.empty();
        }
        return this.getDistinctBy(refColumns, (Column<BIDataSetFieldInfo>)((Column)distinctable.orElse(null)));
    }

    private Map<String, Column<BIDataSetFieldInfo>> findCols(BIDataSet ds, List<String> colNames) {
        HashMap<String, Column<BIDataSetFieldInfo>> colMap = new HashMap<String, Column<BIDataSetFieldInfo>>();
        for (String colName : colNames) {
            Column column = ds.getMetadata().find(colName);
            if (column == null) continue;
            colMap.put(column.getName(), (Column<BIDataSetFieldInfo>)column);
        }
        return colMap;
    }

    private Optional<Column<BIDataSetFieldInfo>> isDistinctable(BIDataSet ds, Map<String, Column<BIDataSetFieldInfo>> refColumns) {
        Column timeKey = null;
        boolean missTime = false;
        boolean missDim = false;
        for (Column column : ds.getMetadata()) {
            if ("SYS_ROWNUM".equals(column.getName()) || "SYS_TIMEKEY".equals(column.getName())) continue;
            if (((BIDataSetFieldInfo)column.getInfo()).isTimekey()) {
                if (!refColumns.containsKey(column.getName())) continue;
                timeKey = column;
                continue;
            }
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.TIME_DIM) {
                if (refColumns.containsKey(column.getName())) continue;
                missTime = true;
                continue;
            }
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() != FieldType.GENERAL_DIM || !((BIDataSetFieldInfo)column.getInfo()).isKeyField() || refColumns.containsKey(column.getName())) continue;
            missDim = true;
        }
        if (!(timeKey == null && missTime || missDim)) {
            return null;
        }
        return Optional.ofNullable(timeKey);
    }

    private Optional<List<Integer>> getDistinctBy(Map<String, Column<BIDataSetFieldInfo>> refColumns, Column<BIDataSetFieldInfo> timeKey) {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        if (timeKey == null) {
            for (Column<BIDataSetFieldInfo> column : refColumns.values()) {
                if (!((BIDataSetFieldInfo)column.getInfo()).isKeyField() && refColumns.containsKey(((BIDataSetFieldInfo)column.getInfo()).getKeyField())) continue;
                cols.add(column.getIndex());
            }
        } else {
            cols.add(timeKey.getIndex());
            for (Column<BIDataSetFieldInfo> column : refColumns.values()) {
                if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() != FieldType.GENERAL_DIM || !((BIDataSetFieldInfo)column.getInfo()).isKeyField() && refColumns.containsKey(((BIDataSetFieldInfo)column.getInfo()).getKeyField())) continue;
                cols.add(column.getIndex());
            }
        }
        return Optional.of(cols);
    }
}

