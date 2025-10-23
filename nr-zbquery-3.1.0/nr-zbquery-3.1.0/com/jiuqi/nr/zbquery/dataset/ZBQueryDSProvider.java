/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSDefine;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModel;
import com.jiuqi.nr.zbquery.engine.executor.QueryExecutor;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.util.ObjectUtils;

public class ZBQueryDSProvider
implements IDataSetProvider {
    private ZBQueryDSModel dsModel;

    public ZBQueryDSProvider(ZBQueryDSModel dsModel) {
        this.dsModel = dsModel;
    }

    public void open(MemoryDataSet<BIDataSetFieldInfo> dataSet, IDSContext context) throws BIDataSetException {
        ZBQueryDSDefine zbQueryDSDefine = this.dsModel.getZbQueryDSDefine();
        if (!ObjectUtils.isEmpty(zbQueryDSDefine)) {
            try {
                QueryExecutor queryExecutor = new QueryExecutor(UUID.randomUUID().toString(), zbQueryDSDefine.getZbQueryModel());
                MemoryDataSet<ColumnInfo> memoryDataSet = queryExecutor.query(ZBQueryDSProvider.generateConditionValues(context), null);
                if (dataSet.getMetadata().getColumnCount() == memoryDataSet.getMetadata().getColumnCount()) {
                    for (int i = 0; i < memoryDataSet.size(); ++i) {
                        dataSet.add(memoryDataSet.getBuffer(i));
                    }
                } else {
                    for (int i = 0; i < memoryDataSet.size(); ++i) {
                        DataRow row = dataSet.add();
                        int j = 0;
                        for (Object obj : memoryDataSet.getBuffer(i)) {
                            row.setValue(j, obj);
                            ++j;
                        }
                    }
                }
                ZBQueryDSProvider.setFixRefDims(dataSet, this.dsModel, queryExecutor);
            }
            catch (Exception e) {
                throw new BIDataSetException(e.getMessage(), (Throwable)e);
            }
        }
    }

    protected static ConditionValues generateConditionValues(IDSContext context) throws Exception {
        ConditionValues conditionValues = new ConditionValues();
        for (ParameterModel parameterModel : context.getEnhancedParameterEnv().getParameterModels()) {
            ParameterResultset value = context.getEnhancedParameterEnv().getValue(parameterModel.getName());
            if (value == null) continue;
            IParameterValueFormat format = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)parameterModel.getDatasource());
            conditionValues.putValue(parameterModel.getName(), value.getValueAsString(format).toArray(new String[0]));
        }
        return conditionValues;
    }

    protected static void setFixRefDims(MemoryDataSet<BIDataSetFieldInfo> dataSet, ZBQueryDSModel dsModel, QueryExecutor queryExecutor) throws Exception {
        Set<String> detailZBMeasureAliases = queryExecutor.getModelBuilder().getDetailZBMeasureAliases();
        if (detailZBMeasureAliases.isEmpty()) {
            return;
        }
        ArrayList<String> resDimCols = new ArrayList<String>();
        List<QueryDimension> queryDims = queryExecutor.getModelBuilder().getModelFinder().getLayoutDimensions();
        for (QueryDimension queryDim : queryDims) {
            QueryDimension parentQueryDim;
            if (queryDim.getDimensionType() == QueryDimensionType.INNER || queryDim.getDimensionType() == QueryDimensionType.CHILD && (parentQueryDim = queryExecutor.getModelBuilder().getModelFinder().getQueryDimension(queryDim.getParent())) != null && parentQueryDim.getDimensionType() == QueryDimensionType.INNER) continue;
            String keyFullName = FullNameWrapper.getKeyFullName(queryDim);
            String keyAlias = queryExecutor.getModelBuilder().getFullNameAliasMapper().get(keyFullName);
            if (!StringUtils.isNotEmpty((String)keyAlias)) continue;
            resDimCols.add(keyAlias);
        }
        for (Column col : dataSet.getMetadata().getColumns()) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)col.getInfo();
            if (info.getFieldType() != FieldType.MEASURE || detailZBMeasureAliases.contains(col.getName())) continue;
            info.getRefDimCols().clear();
            info.getRefDimCols().addAll(resDimCols);
        }
    }
}

