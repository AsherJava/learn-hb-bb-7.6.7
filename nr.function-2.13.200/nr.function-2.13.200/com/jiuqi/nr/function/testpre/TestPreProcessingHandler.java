/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.IPreProcessingHandler
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.function.testpre;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.IPreProcessingHandler;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPreProcessingHandler
implements IPreProcessingHandler {
    private static final Logger logger = LoggerFactory.getLogger(TestPreProcessingHandler.class);

    public String funcName() {
        return "TestPreProcessing";
    }

    public void preProcessing(QueryContext qContext, List<FunctionNode> functions) {
        logger.info("TestPreProcessingHander preProcessing start");
        HashMap fieldsMap = new HashMap();
        try {
            for (FunctionNode funcNdoe : functions) {
                logger.info(funcNdoe.interpret((IContext)qContext, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA)));
                IASTNode p0 = (IASTNode)funcNdoe.getParameters().get(0);
                ColumnModelDefine fieldDefine = qContext.getExeContext().getCache().extractFieldDefine(p0);
                if (fieldDefine == null) continue;
                TableModelDefine tableModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableModel(fieldDefine);
                String tableName = tableModel.getName();
                HashSet<String> tableFields = (HashSet<String>)fieldsMap.get(tableName);
                if (tableFields == null) {
                    tableFields = new HashSet<String>();
                    fieldsMap.put(tableName, tableFields);
                }
                String fieldName = fieldDefine.getName();
                tableFields.add(fieldName);
            }
            for (String tableName : fieldsMap.keySet()) {
                Set tableFields = (Set)fieldsMap.get(tableName);
                DimensionValueSet masterKeys = new DimensionValueSet(qContext.getCurrentMasterKey());
                IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
                IGroupingQuery request = accessProvider.newGroupingQuery();
                request.setWantDetail(false);
                request.setQueryParam(qContext.getQueryParam());
                request.setMasterKeys(masterKeys);
                int index = 0;
                ArrayList<String> cacheKeys = new ArrayList<String>();
                for (String field : tableFields) {
                    String fieldExp = tableName + "[" + field + "]";
                    request.addExpressionColumn(fieldExp);
                    request.setGatherType(index, FieldGatherType.FIELD_GATHER_SUM);
                    cacheKeys.add(this.funcName() + "_" + fieldExp);
                    ++index;
                }
                IGroupingTable executeReader = request.executeReader(qContext.getExeContext());
                IDataRow row = executeReader.getItem(0);
                for (int i = 0; i < cacheKeys.size(); ++i) {
                    AbstractData value = row.getValue(i);
                    String cacheKey = (String)cacheKeys.get(i);
                    qContext.getCache().put(cacheKey, value.getAsFloat());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("TestPreProcessingHander preProcessing end");
    }

    private IGroupingQuery createGroupQuery(QueryContext qContext) throws Exception {
        DimensionValueSet masterKeys = new DimensionValueSet(qContext.getCurrentMasterKey());
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IGroupingQuery request = accessProvider.newGroupingQuery();
        request.setWantDetail(false);
        request.setQueryParam(qContext.getQueryParam());
        request.setMasterKeys(masterKeys);
        return request;
    }
}

