/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.CalcExecutor
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datacrud.impl.calc;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class GroupRowCalcExecutor {
    private List<IParsedExpression> expressions;
    private DefinitionsCache definitionsCache;
    private QueryContext queryContext;
    private CalcExecutor calcExecutor;
    protected ExecutorContext context;
    private List<QueryFields> writeQueryFields;
    private QueryParam queryParam;
    private DataModelService dataModelService;
    private Map<String, QueryField> queryFieldCache;

    public void setDataModelService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    public void setExpressions(List<IParsedExpression> expressions) {
        this.expressions = expressions;
    }

    public void setContext(ExecutorContext context) {
        this.context = context;
    }

    public void initialize() throws ParseException {
        this.definitionsCache = new DefinitionsCache(this.context);
        this.queryContext = new QueryContext(this.context, this.queryParam, (IMonitor)new AbstractMonitor());
        this.calcExecutor = new CalcExecutor(this.queryContext);
        this.writeQueryFields = new ArrayList<QueryFields>();
        for (IParsedExpression expression : this.expressions) {
            if (!(expression instanceof CalcExpression)) continue;
            this.writeQueryFields.add(expression.getWriteQueryFields());
            this.calcExecutor.add((CalcExpression)expression);
        }
        this.calcExecutor.sort();
        this.queryFieldCache = new HashMap<String, QueryField>();
    }

    public void calcRow(IRowData rowData) throws Exception {
        this.queryContext.getRowValuesMap().clear();
        HashMap<QueryField, IDataValue> oldValueMap = new HashMap<QueryField, IDataValue>();
        for (IDataValue iDataValue : rowData.getLinkDataValues()) {
            Object first;
            IMetaData metaData = iDataValue.getMetaData();
            DataField dataField = metaData.getDataField();
            if (dataField == null) continue;
            QueryField queryField = this.queryFieldCache.get(dataField.getKey());
            if (queryField == null && ((Optional)(first = metaData.getDeployInfos().stream().findFirst())).isPresent()) {
                String columnModelKey = ((DataFieldDeployInfo)((Optional)first).get()).getColumnModelKey();
                ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByID(columnModelKey);
                queryField = this.definitionsCache.extractQueryField(this.context, columnModelDefine, null, null);
            }
            if (queryField == null) continue;
            this.queryFieldCache.put(dataField.getKey(), queryField);
            first = this.writeQueryFields.iterator();
            while (first.hasNext()) {
                QueryFields writeQueryField = (QueryFields)first.next();
                if (!writeQueryField.hasField(queryField)) continue;
                oldValueMap.put(queryField, iDataValue);
                break;
            }
            AbstractData abstractData = iDataValue.getAbstractData();
            this.queryContext.writeData(queryField, abstractData.getAsObject());
        }
        this.calcExecutor.calc();
        for (Map.Entry entry : oldValueMap.entrySet()) {
            Object newValue = this.queryContext.readData((QueryField)entry.getKey());
            AbstractData oldValue = ((IDataValue)entry.getValue()).getAbstractData();
            if (Objects.equals(oldValue.getAsObject(), newValue)) continue;
            IMetaData metaData = ((IDataValue)entry.getValue()).getMetaData();
            ((IDataValue)entry.getValue()).setAbstractData(AbstractData.valueOf((Object)newValue, (int)metaData.getDataType()));
        }
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }
}

