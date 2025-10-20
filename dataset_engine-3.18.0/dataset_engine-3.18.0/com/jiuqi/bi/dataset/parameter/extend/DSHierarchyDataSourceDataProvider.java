/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDataSourceDataProvider;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDataSourceModel;
import com.jiuqi.bi.dataset.parameter.extend.DSHierarchyDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.List;

public class DSHierarchyDataSourceDataProvider
implements IParameterDataSourceProvider {
    private DSFieldDataSourceDataProvider fieldProvider = new DSFieldDataSourceDataProvider();

    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        ParameterDataSourceContext cxt = this.rebuildDataSourceContext(context);
        return this.fieldProvider.getDefaultValue(cxt);
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        ParameterDataSourceContext cxt = this.rebuildDataSourceContext(context);
        return this.fieldProvider.getCandidateValue(cxt, hierarchyFilter);
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        ParameterDataSourceContext cxt = this.rebuildDataSourceContext(context);
        return this.fieldProvider.compute(cxt, value);
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        ParameterDataSourceContext cxt = this.rebuildDataSourceContext(context);
        return this.fieldProvider.search(cxt, searchValues);
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        DSFieldDataSourceModel model = this.convertDataSourceModel((DSHierarchyDataSourceModel)datasourceModel);
        return this.fieldProvider.getDataSourceCandidateFields(model);
    }

    private DSFieldDataSourceModel convertDataSourceModel(DSHierarchyDataSourceModel datasource) throws ParameterException {
        DSHierarchy dsHier;
        DSFieldDataSourceModel converted = new DSFieldDataSourceModel();
        converted.setBusinessType(datasource.getBusinessType());
        converted.setDataType(datasource.getDataType());
        try {
            dsHier = DataSetManagerFactory.create().findHierarchy(datasource.getDsName(), datasource.getDsType(), datasource.getDsHierarchyName());
        }
        catch (BIDataSetException e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
        if (dsHier == null) {
            throw new ParameterException("\u53c2\u6570\u5173\u8054\u7684\u6570\u636e\u96c6\u5c42\u7ea7\u4e0d\u5b58\u5728\uff1a" + datasource.getDsHierarchyName());
        }
        converted.setDsFieldName(dsHier.getLevels().get(0));
        converted.setDsName(datasource.getDsName());
        converted.setDsType(datasource.getDsType());
        converted.setHierarchyType(datasource.getHierarchyType());
        converted.setRefObject(datasource.getRefObject());
        converted.setTimegranularity(datasource.getTimegranularity());
        converted.setTimekey(datasource.isTimekey());
        return converted;
    }

    private ParameterDataSourceContext rebuildDataSourceContext(ParameterDataSourceContext context) throws ParameterException {
        ParameterModel model = context.getModel().clone();
        DSHierarchyDataSourceModel datasource = (DSHierarchyDataSourceModel)model.getDatasource();
        DSFieldDataSourceModel converted = this.convertDataSourceModel(datasource);
        model.setDatasource((AbstractParameterDataSourceModel)converted);
        ParameterDataSourceContext cxt = new ParameterDataSourceContext(model, context.getCalculator());
        cxt.setPageInfo(context.getPageInfo());
        cxt.setShowPathInSearchResult(context.isShowPathInSearchResult());
        return cxt;
    }
}

