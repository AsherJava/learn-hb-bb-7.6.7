/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldParameterValueFormat;
import com.jiuqi.bi.dataset.parameter.extend.DSHierarchyDataSourceDataProvider;
import com.jiuqi.bi.dataset.parameter.extend.DSHierarchyDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.List;

public class DSHierarchyDataSourceModelFactory
extends AbstractParameterDataSourceFactory {
    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new DSHierarchyDataSourceDataProvider();
    }

    public String type() {
        return "com.jiuqi.bi.datasource.dshier";
    }

    public String title() {
        return "\u6570\u636e\u96c6\u5c42\u6b21";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new DSHierarchyDataSourceModel();
    }

    public IParameterValueFormat createValueFormat(AbstractParameterDataSourceModel model) {
        DSHierarchyDataSourceModel m = (DSHierarchyDataSourceModel)model;
        try {
            DSModel dsModel = DataSetManagerFactory.create().findModel(m.getDsName(), m.getDsType());
            List<DSHierarchy> hiers = dsModel.getHiers();
            for (DSHierarchy hier : hiers) {
                if (!hier.getName().equals(m.getDsHierarchyName())) continue;
                String fieldName = hier.getLevels().get(0);
                DSField field = dsModel.findField(fieldName);
                if (field == null) {
                    throw new RuntimeException("\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
                }
                return new DSFieldParameterValueFormat(field);
            }
            return super.createValueFormat(model);
        }
        catch (BIDataSetException e) {
            return super.createValueFormat(model);
        }
    }
}

