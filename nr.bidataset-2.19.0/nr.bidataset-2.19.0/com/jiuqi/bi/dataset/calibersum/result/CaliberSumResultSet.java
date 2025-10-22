/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.dataset.calibersum.result;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSField;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSFieldType;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumDSRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaliberSumResultSet {
    private List<CaliberSumDSRow> rows = new ArrayList<CaliberSumDSRow>();
    private Map<String, CaliberSumDSRow> rowSeachByKey = new HashMap<String, CaliberSumDSRow>();
    private Map<String, CaliberSumDSRow> rowSeachByCode = new HashMap<String, CaliberSumDSRow>();
    private Map<String, CaliberSumDSRow> rowSeachByMaindim = new HashMap<String, CaliberSumDSRow>();
    private Map<String, List<CaliberSumDSRow>> soruceDestMap = new HashMap<String, List<CaliberSumDSRow>>();
    private CaliberSumDSModel dsModel;
    private int keyFieldIndex = -1;
    private int nameFieldIndex = -1;
    private int parentFieldIndex = -1;
    private int mdcodeFieldIndex = -1;
    private int checkHintFieldIndex = -1;
    private int checkWarnFieldIndex = -1;
    private int checkErrorFieldIndex = -1;
    private int unitStateFieldIndex = -1;

    public CaliberSumResultSet(CaliberSumDSModel dsModel) {
        this.dsModel = dsModel;
        this.doInit();
    }

    public void addRow(CaliberSumDSRow row) {
        this.rows.add(row);
        this.rowSeachByKey.put(row.getCalibreDataRegion().getKey(), row);
        this.rowSeachByCode.put(row.getCalibreDataRegion().getCalibreData().getCode(), row);
        row.setValue(this.keyFieldIndex, row.getCalibreDataRegion().getCalibreData().getCode());
        row.setValue(this.nameFieldIndex, row.getCalibreDataRegion().getCalibreData().getName());
        row.setValue(this.parentFieldIndex, row.getCalibreDataRegion().getCalibreData().getParent());
        String mainDimKey = row.getMainDimKey();
        if (mainDimKey != null) {
            this.rowSeachByMaindim.put(mainDimKey, row);
            row.setValue(this.mdcodeFieldIndex, mainDimKey);
        }
        for (String sourceKey : row.getCalibreDataRegion().getEntityKeys()) {
            List<CaliberSumDSRow> destRows = this.soruceDestMap.get(sourceKey);
            if (destRows == null) {
                destRows = new ArrayList<CaliberSumDSRow>();
                this.soruceDestMap.put(sourceKey, destRows);
            }
            destRows.add(row);
        }
    }

    public void buildParentRows() {
        for (CaliberSumDSRow row : this.rows) {
            String parent = row.getCalibreDataRegion().getCalibreData().getParent();
            if (parent == null) continue;
            this.addParent(row, row.getParentRows());
        }
    }

    private void addParent(CaliberSumDSRow row, List<CaliberSumDSRow> parents) {
        CaliberSumDSRow parentRow;
        String parent = row.getCalibreDataRegion().getCalibreData().getParent();
        if (parent != null && (parentRow = this.findRowByCode(parent)) != null) {
            parents.add(parentRow);
            this.addParent(parentRow, parents);
        }
    }

    public int size() {
        return this.rows.size();
    }

    public CaliberSumDSRow getRow(int index) {
        return this.rows.get(index);
    }

    public CaliberSumDSRow findRow(String rowKey) {
        return this.rowSeachByKey.get(rowKey);
    }

    public CaliberSumDSRow findRowByCode(String rowCode) {
        return this.rowSeachByCode.get(rowCode);
    }

    public CaliberSumDSRow findRowByMainDim(String mainDimKey) {
        return this.rowSeachByMaindim.get(mainDimKey);
    }

    public List<CaliberSumDSRow> getDestRows(String mainDimKey) {
        return this.soruceDestMap.get(mainDimKey);
    }

    public void toResult(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet) throws DataSetException {
        for (CaliberSumDSRow row : this.rows) {
            DataRow dataRow = memoryDataSet.add();
            for (int i = 0; i < row.getColumnCount(); ++i) {
                Object value = row.getValue(i);
                dataRow.setValue(i, value);
            }
            dataRow.commit();
        }
    }

    private void doInit() {
        List fields = this.dsModel.getFields();
        for (int dsIndex = 0; dsIndex < fields.size(); ++dsIndex) {
            CaliberSumDSField caliberSumDSField = (CaliberSumDSField)((Object)fields.get(dsIndex));
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.CALIBER_CODE) {
                this.keyFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.CALIBER_TITLE) {
                this.nameFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.CALIBER_PARENT) {
                this.parentFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.MDCODE) {
                this.mdcodeFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.CHECK_HINT) {
                this.checkHintFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.CHECK_WARNNING) {
                this.checkWarnFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() == CaliberSumDSFieldType.CHECK_ERROR) {
                this.checkErrorFieldIndex = dsIndex;
                continue;
            }
            if (caliberSumDSField.getType() != CaliberSumDSFieldType.UNITSTATE) continue;
            this.unitStateFieldIndex = dsIndex;
        }
    }

    public List<String> getAllDestUnits() {
        return new ArrayList<String>(this.rowSeachByMaindim.keySet());
    }

    public int getCheckHintFieldIndex() {
        return this.checkHintFieldIndex;
    }

    public int getCheckWarnFieldIndex() {
        return this.checkWarnFieldIndex;
    }

    public int getCheckErrorFieldIndex() {
        return this.checkErrorFieldIndex;
    }

    public int getUnitStatetFieldIndex() {
        return this.unitStateFieldIndex;
    }

    public CaliberSumDSModel getDsModel() {
        return this.dsModel;
    }
}

