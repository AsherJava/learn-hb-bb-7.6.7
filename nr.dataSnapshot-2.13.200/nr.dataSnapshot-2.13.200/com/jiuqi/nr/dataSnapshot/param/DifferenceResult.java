/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.dataSnapshot.param.DataSnapshotDifference;
import com.jiuqi.nr.dataSnapshot.param.FieldObject;
import java.util.List;
import java.util.Map;

public class DifferenceResult {
    private List<DataSnapshotDifference> dataSnapshotDifference;
    private Map<String, String> fieldMap;
    private List<FieldObject> selectedField;
    private List<FieldObject> selectableFixedField;

    public List<FieldObject> getSelectableFixedField() {
        return this.selectableFixedField;
    }

    public void setSelectableFixedField(List<FieldObject> selectableFixedField) {
        this.selectableFixedField = selectableFixedField;
    }

    public List<DataSnapshotDifference> getDataSnapshotDifference() {
        return this.dataSnapshotDifference;
    }

    public void setDataSnapshotDifference(List<DataSnapshotDifference> dataSnapshotDifference) {
        this.dataSnapshotDifference = dataSnapshotDifference;
    }

    public Map<String, String> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public List<FieldObject> getSelectedField() {
        return this.selectedField;
    }

    public void setSelectedField(List<FieldObject> selectedField) {
        this.selectedField = selectedField;
    }
}

