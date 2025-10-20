/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.context.filter;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.Collection;

public abstract class FilterDescriptor
implements IFilterDescriptor {
    private final String dataSetName;
    private final String fieldName;
    private final DSField field;

    public FilterDescriptor(String dataSetName, DSField field) {
        if (StringUtils.isEmpty((String)dataSetName)) {
            throw new IllegalArgumentException("\u8fc7\u6ee4\u6761\u4ef6\u5fc5\u987b\u6307\u5b9a\u9650\u5b9a\u7684\u6570\u636e\u96c6\u3002");
        }
        this.dataSetName = dataSetName.toUpperCase();
        this.field = field;
        this.fieldName = field == null ? null : field.getName().toUpperCase();
    }

    protected FilterDescriptor() {
        this.dataSetName = null;
        this.field = null;
        this.fieldName = null;
    }

    @Override
    public String getDataSetName() {
        return this.dataSetName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public DSField getField() {
        return this.field;
    }

    @Override
    public void getRefFields(Collection<String> fields) {
        if (!StringUtils.isEmpty((String)this.fieldName)) {
            fields.add(this.fieldName);
        }
    }

    protected DSFieldNode createFieldNode(IContext context, String dsName, DSField field) throws ReportContextException {
        DSModel model = ((ReportContext)context).openDataSetModel(dsName);
        return new DSFieldNode(null, model, field, true);
    }

    public int hashCode() {
        int h = this.dataSetName.hashCode();
        h = h * 31 + (this.fieldName == null ? 0 : this.fieldName.hashCode());
        return h;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof FilterDescriptor) {
            return this.compareTo((FilterDescriptor)obj) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(IFilterDescriptor o) {
        int c = StringUtils.compare((String)this.dataSetName, (String)o.getDataSetName());
        if (c != 0) {
            return c;
        }
        c = StringUtils.compare((String)this.fieldName, (String)o.getFieldName());
        if (c != 0) {
            return c;
        }
        c = this.getClass().getName().compareTo(o.getClass().getName());
        if (c != 0) {
            return c;
        }
        return 0;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.dataSetName);
        if (!StringUtils.isEmpty((String)this.fieldName)) {
            buffer.append('.').append(this.fieldName);
        }
        buffer.append(':').append(this.toFilterString());
        return buffer.toString();
    }

    protected abstract String toFilterString();

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }
}

