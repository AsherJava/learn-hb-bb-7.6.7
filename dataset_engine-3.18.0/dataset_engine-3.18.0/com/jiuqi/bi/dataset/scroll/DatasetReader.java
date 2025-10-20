/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.scroll;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.scroll.IDataProcessor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public abstract class DatasetReader {
    protected DSModel model;

    public DatasetReader(DSModel model) {
        this.model = model;
    }

    public Metadata<BIDataSetFieldInfo> getMetadata() {
        Metadata metadata = new Metadata();
        List<DSField> fields = this.model.getFields();
        List columns = metadata.getColumns();
        for (int i = 0; i < fields.size(); ++i) {
            DSField field = fields.get(i);
            Column column = new Column(field.getName(), field.getValType());
            column.setTitle(field.getTitle());
            BIDataSetFieldInfo info = DSUtils.transform(field);
            if (field.getFieldType() == FieldType.MEASURE) {
                if (field.getApplyType() == null) {
                    info.setApplyType(ApplyType.PERIOD);
                } else {
                    info.setApplyType(field.getApplyType());
                }
            }
            column.setInfo((Object)info);
            columns.add(column);
        }
        List<DSHierarchy> hierarchies = this.model.getHiers();
        ArrayList<DSHierarchy> cloned = new ArrayList<DSHierarchy>();
        for (DSHierarchy hierarchy : hierarchies) {
            cloned.add(hierarchy.clone());
        }
        metadata.getProperties().put("HIERARCHY", cloned);
        if (this.model.getMinFiscalMonth() >= 0 || this.model.getMaxFiscalMonth() >= 0) {
            JSONObject j = new JSONObject();
            j.put("min", this.model.getMinFiscalMonth());
            j.put("max", this.model.getMaxFiscalMonth());
            metadata.getProperties().put("FiscalMonth", j.toString());
        }
        return metadata;
    }

    public abstract void read(IDSContext var1, IDataProcessor var2) throws BIDataSetException;
}

