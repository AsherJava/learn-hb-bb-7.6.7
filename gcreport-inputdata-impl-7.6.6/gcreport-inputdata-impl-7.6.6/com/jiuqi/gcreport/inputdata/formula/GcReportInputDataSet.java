/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportInputDataColmn
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.gcreport.inputdata.formula;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportInputDataColmn;
import com.jiuqi.gcreport.inputdata.formula.GcReportInputDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GcReportInputDataSet
extends GcReportDataSet {
    List<DataRow> dataRows = new ArrayList<DataRow>();

    public GcReportInputDataSet(String ... tableNames) {
        super(new String[0]);
        List columns = new GcReportInputDataColmn(tableNames).getColumns();
        for (int i = 0; i < columns.size(); ++i) {
            this.getMetadata().addColumn((Column)columns.get(i));
        }
    }

    public GcReportInputDataRow add() {
        GcReportInputDataRow dataRow = new GcReportInputDataRow((Metadata<FieldDefine>)this.getMetadata());
        this.dataRows.add((DataRow)dataRow);
        return dataRow;
    }

    public void clear() {
        this.dataRows.clear();
    }

    public Iterator<DataRow> iterator() {
        return this.dataRows.iterator();
    }
}

