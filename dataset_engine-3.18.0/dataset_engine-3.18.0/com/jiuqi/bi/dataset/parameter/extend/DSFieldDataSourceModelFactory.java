/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDependAnalyzer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDataSourceDataProvider;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDataSourceModel;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDependAnalyzer;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDependAnalyzer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;

public class DSFieldDataSourceModelFactory
extends AbstractParameterDataSourceFactory {
    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new DSFieldDataSourceDataProvider();
    }

    public String type() {
        return "com.jiuqi.bi.datasource.dsfield";
    }

    public String title() {
        return "\u6570\u636e\u96c6\u5b57\u6bb5";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new DSFieldDataSourceModel();
    }

    public IParameterDependAnalyzer createDependAnalyzer() throws ParameterException {
        return new DSFieldDependAnalyzer();
    }

    public IParameterValueFormat createValueFormat(AbstractParameterDataSourceModel model) {
        DSFieldDataSourceModel m = (DSFieldDataSourceModel)model;
        try {
            DSField field = DataSetManagerFactory.create().findField(m.getDsName(), m.getDsType(), m.getDsFieldName());
            if (field == null) {
                throw new RuntimeException("\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + m.getDsFieldName());
            }
            return new DSFieldParameterValueFormat(field);
        }
        catch (BIDataSetException e) {
            return super.createValueFormat(model);
        }
    }
}

