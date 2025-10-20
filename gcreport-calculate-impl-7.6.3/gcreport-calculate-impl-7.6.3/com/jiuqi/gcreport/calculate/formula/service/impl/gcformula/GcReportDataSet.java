/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.gcformula;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportInputDataColmn;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GcReportDataSet
extends DataSet<FieldDefine>
implements Cloneable {
    List<DataRow> dataRows = new ArrayList<DataRow>();

    public GcReportDataSet(String ... tableNames) {
        List<Column<FieldDefine>> columns = new GcReportInputDataColmn(tableNames).getColumns();
        for (int i = 0; i < columns.size(); ++i) {
            this.getMetadata().addColumn(columns.get(i));
        }
    }

    public GcReportDataRow add() {
        GcReportDataRow dataRow = new GcReportDataRow((Metadata<FieldDefine>)this.getMetadata());
        this.dataRows.add(dataRow);
        return dataRow;
    }

    public void clear() {
        this.dataRows.clear();
    }

    public Iterator<DataRow> iterator() {
        return this.dataRows.iterator();
    }
}

