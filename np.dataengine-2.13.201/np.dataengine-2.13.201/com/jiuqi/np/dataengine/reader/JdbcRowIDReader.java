/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.reader.JdbcCheckEventInfoReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcRowIDReader
extends JdbcCheckEventInfoReader {
    private List<Integer> keyFields = new ArrayList<Integer>();
    private DimensionSet rowDimesions;

    public JdbcRowIDReader(ResultSet rs, DimensionSet rowDimesions) {
        super(rs, -1, -1);
        this.rowDimesions = rowDimesions;
    }

    @Override
    public void readToEvent(FormulaCheckEventImpl event) throws Exception {
        DimensionValueSet rowkey = new DimensionValueSet();
        for (int i = 0; i < this.rowDimesions.size(); ++i) {
            String dimesion = this.rowDimesions.get(i);
            int fieldIndex = this.keyFields.get(i);
            rowkey.setValue(dimesion, this.rs.getObject(fieldIndex + 1));
        }
        event.setRowkey(rowkey);
    }

    public List<Integer> getKeyFields() {
        return this.keyFields;
    }
}

