/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.parse.PreProcessingFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.function.testpre;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.parse.PreProcessingFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class TestPreProcessing
extends PreProcessingFunction {
    private static final Logger logger = LogFactory.getLogger(TestPreProcessing.class);
    private static final long serialVersionUID = 3308001410540142785L;

    public TestPreProcessing() {
        this.parameters().add(new Parameter("ZB", 0, "\u53d6\u6570\u6307\u6807"));
    }

    public String name() {
        return "TestPreProcessing";
    }

    public String title() {
        return "\u6d4b\u8bd5\u652f\u6301\u9884\u5904\u7406\u7684\u51fd\u6570,\u53d6\u6307\u5b9a\u6307\u6807\u7684\u5408\u8ba1\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            try {
                IASTNode p0 = parameters.get(0);
                ColumnModelDefine fieldDefine = qContext.getExeContext().getCache().extractFieldDefine(p0);
                if (fieldDefine != null) {
                    TableModelDefine tableModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableModel(fieldDefine);
                    String tableName = tableModel.getName();
                    String fieldName = fieldDefine.getName();
                    String cacheKey = this.name() + "_" + tableName + "[" + fieldName + "]";
                    Object value = qContext.getCache().get(cacheKey);
                    if (value != null) {
                        logger.info("TestPreProcessing read Value from cache");
                        return value;
                    }
                    logger.info("TestPreProcessing query start");
                    DimensionValueSet masterKeys = new DimensionValueSet(qContext.getCurrentMasterKey());
                    IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
                    IGroupingQuery request = accessProvider.newGroupingQuery();
                    request.setWantDetail(false);
                    request.setQueryParam(qContext.getQueryParam());
                    request.setMasterKeys(masterKeys);
                    String fieldExp = tableName + "[" + fieldName + "]";
                    request.addExpressionColumn(fieldExp);
                    request.setGatherType(0, FieldGatherType.FIELD_GATHER_SUM);
                    IGroupingTable executeReader = request.executeReader(qContext.getExeContext());
                    IDataRow row = executeReader.getItem(0);
                    AbstractData result = row.getValue(0);
                    logger.info("TestPreProcessing query end");
                    return result.getAsFloat();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }
}

