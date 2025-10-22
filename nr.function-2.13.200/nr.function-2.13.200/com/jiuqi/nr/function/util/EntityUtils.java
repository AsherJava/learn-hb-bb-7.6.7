/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 *  com.jiuqi.np.dataengine.intf.IEntityTable
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 */
package com.jiuqi.nr.function.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EntityUtils
implements IEntityUpgrader {
    private static final Logger logger = LogFactory.getLogger(EntityUtils.class);

    public static IEntityRow queryEntityRow(String entityKey, IEntityTable entityTable) {
        IEntityRow row = null;
        if (entityKey.length() != 32 && entityKey.length() != 36) {
            row = entityTable.findByCode(entityKey);
        }
        if (row == null) {
            row = entityTable.findByEntityKey(entityKey);
        }
        if (row == null) {
            row = entityTable.findByRecKey(entityKey);
        }
        return row;
    }

    private static List<Map<String, String>> queryEntitysByFormula(String expression) {
        ArrayList<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanProvider.getBean(IEntityViewRunTimeController.class);
            IExpression iexpression = EntityUtils.parseFormula(expression);
            Iterator exp = iexpression.iterator();
            ArrayList<String> tableKey = new ArrayList<String>();
            while (exp.hasNext()) {
                IASTNode n = (IASTNode)exp.next();
                try {
                    QueryFields fields = ExpressionUtils.getQueryFields((IASTNode)n);
                    int fieldsCount = fields.getCount();
                    for (int i = 0; i < fieldsCount; ++i) {
                        String[] bizKeyFieldsID;
                        QueryField fieldItem = fields.getItem(i);
                        FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(fieldItem.getUID());
                        TableDefine tableDefine = dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                        if (tableDefine.getKind().getValue() == TableKind.TABLE_KIND_ENTITY.getValue()) {
                            if (tableKey.contains(tableDefine.getKey())) continue;
                            HashMap<String, String> result = new HashMap<String, String>();
                            result.put("viewKey", tableDefine.getKey());
                            result.put("kind", tableDefine.getKind().toString());
                            result.put("field", fieldDefine.getKey());
                            results.add(result);
                            tableKey.add(tableDefine.getKey());
                            continue;
                        }
                        for (String str : bizKeyFieldsID = tableDefine.getBizKeyFieldsID()) {
                            FieldDefine fieldDefine2 = dataDefinitionRuntimeController.queryFieldDefine(str);
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), (Throwable)e);
                }
            }
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return results;
    }

    private static IExpression parseFormula(String expression) throws ParseException {
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        IDataAssist dataAssist = dataAccessProvider.newDataAssist(executorContext);
        QueryContext qContext = new QueryContext(executorContext, null);
        ReportFormulaParser parser = dataAssist.createFormulaParser(false);
        return parser.parseAssign(expression, (IContext)qContext);
    }

    public static String queryIEntityTableNameByCode(String zbExp, String unitCode) {
        try {
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanProvider.getBean(IEntityViewRunTimeController.class);
            List<Map<String, String>> unitMapList = EntityUtils.queryEntitysByFormula(zbExp);
            for (Map<String, String> unitMap : unitMapList) {
                if ("6".equals(unitMap.get("kind")) || "TABLE_KIND_ENTITY_PERIOD".equals(unitMap.get("kind"))) continue;
                FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(unitMap.get("field"));
                IEntityTable entityTable = null;
                IEntityQuery entityQuery = dataAccessProvider.newEntityQuery();
                entityQuery.addColumn(fieldDefine);
                entityQuery.setIgnoreViewFilter(true);
                ExecutorContext context2 = new ExecutorContext(dataDefinitionRuntimeController);
                entityTable = entityQuery.executeReader(context2);
                IEntityRow entityRow = entityTable.findByCode(unitCode);
                return entityRow.getTitle();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return unitCode;
    }

    public static String queryIEntityTableCodeByKey(String zbExp, String key) {
        try {
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanProvider.getBean(IEntityViewRunTimeController.class);
            List<Map<String, String>> unitMapList = EntityUtils.queryEntitysByFormula(zbExp);
            for (Map<String, String> unitMap : unitMapList) {
                if ("6".equals(unitMap.get("kind")) || "TABLE_KIND_ENTITY_PERIOD".equals(unitMap.get("kind"))) continue;
                FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(unitMap.get("field"));
                IEntityTable entityTable = null;
                IEntityQuery entityQuery = dataAccessProvider.newEntityQuery();
                entityQuery.addColumn(fieldDefine);
                entityQuery.setIgnoreViewFilter(true);
                ExecutorContext context2 = new ExecutorContext(dataDefinitionRuntimeController);
                entityTable = entityQuery.executeReader(context2);
                IEntityRow entityRow = entityTable.findByEntityKey(key);
                return entityRow.getCode();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return key;
    }
}

