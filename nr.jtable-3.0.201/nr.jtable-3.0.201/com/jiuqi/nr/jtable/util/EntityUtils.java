/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.impl.DataQueryImpl
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.impl.DataQueryImpl;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityUtils {
    private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);

    public static Map<String, Object> queryDataByFormula(QueryContext qContext, String zbExp, List<String> unitCodes, String periodCode) {
        LinkedHashMap<String, Object> numbers = new LinkedHashMap<String, Object>();
        IJtableParamService jtableParamService = (IJtableParamService)SpringBeanProvider.getBean(IJtableParamService.class);
        IJtableEntityService jtableEntityService = (IJtableEntityService)SpringBeanProvider.getBean(IJtableEntityService.class);
        try {
            List<Map<String, String>> unitMapList = EntityUtils.queryEntitysByFormula(zbExp);
            for (String unitCode : unitCodes) {
                ArrayList<EntityViewData> entitys = new ArrayList<EntityViewData>();
                ArrayList<Map<String, String>> units = new ArrayList<Map<String, String>>();
                for (Map<String, String> unitMap : unitMapList) {
                    EntityViewData entity = jtableParamService.getEntity(unitMap.get("viewKey"));
                    entitys.add(entity);
                    if ("6".equals(unitMap.get("kind")) || "TABLE_KIND_ENTITY_PERIOD".equals(unitMap.get("kind"))) continue;
                    unitMap.put("code", unitCode);
                    EntityQueryByKeyInfo queryEntityDataByKey = new EntityQueryByKeyInfo();
                    queryEntityDataByKey.setEntityViewKey(unitMap.get("viewKey"));
                    queryEntityDataByKey.setEntityKey(unitMap.get("code"));
                    queryEntityDataByKey.setContext(new JtableContext());
                    EntityByKeyReturnInfo entityByKeyReturnInfo = jtableEntityService.queryEntityDataByKey(queryEntityDataByKey);
                    EntityData entityData = entityByKeyReturnInfo.getEntity();
                    unitMap.put("key", entityData.getId());
                    unitMap.put("title", entityData.getTitle());
                    units.add(unitMap);
                }
                Map<String, DimensionValue> dimensionSet = EntityUtils.entityListAndUnitsCalcDimensionValue(entitys, units, periodCode);
                AbstractData result = null;
                DataQueryImpl dataQuery = new DataQueryImpl();
                dataQuery.setQueryParam(qContext.getQueryParam());
                dataQuery.addExpressionColumn(zbExp);
                dataQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionSet));
                dataQuery.setStatic(true);
                IDataTable dataTable = dataQuery.executeQuery(qContext.getExeContext());
                if (dataTable.getCount() == 1) {
                    result = dataTable.getItem(0).getValue(0);
                }
                if (result == null) continue;
                Object resultObj = result.getAsObject();
                numbers.put(unitCode, resultObj);
            }
        }
        catch (ExpressionException e) {
            logger.error(e.getMessage(), e);
        }
        catch (ExecuteException e) {
            logger.error(e.getMessage(), e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return numbers;
    }

    private static List<Map<String, String>> queryEntitysByFormula(String expression) {
        ArrayList<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
            IColumnModelFinder columnModelFinder = (IColumnModelFinder)SpringBeanProvider.getBean(IColumnModelFinder.class);
            IExpression iexpression = EntityUtils.parseFormula(expression);
            Iterator exp = iexpression.iterator();
            ArrayList<String> tableKey = new ArrayList<String>();
            while (exp.hasNext()) {
                IASTNode n = (IASTNode)exp.next();
                try {
                    QueryFields fields = ExpressionUtils.getQueryFields((IASTNode)n);
                    int fieldsCount = fields.getCount();
                    for (int i = 0; i < fieldsCount; ++i) {
                        QueryField fieldItem = fields.getItem(i);
                        FieldDefine fieldDefine = columnModelFinder.findFieldDefineByColumnId(fieldItem.getUID());
                        TableDefine tableDefine = dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                        if (!StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) continue;
                        HashMap<String, String> result = new HashMap<String, String>();
                        result.put("viewKey", fieldDefine.getEntityKey());
                        result.put("kind", "");
                        result.put("field", fieldDefine.getKey());
                        results.add(result);
                        tableKey.add(tableDefine.getKey());
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
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
            List<Map<String, String>> unitMapList = EntityUtils.queryEntitysByFormula(zbExp);
            IJtableEntityService jtableEntityService = (IJtableEntityService)SpringBeanProvider.getBean(IJtableEntityService.class);
            for (Map<String, String> unitMap : unitMapList) {
                if ("6".equals(unitMap.get("kind")) || "TABLE_KIND_ENTITY_PERIOD".equals(unitMap.get("kind"))) continue;
                EntityQueryByKeyInfo queryEntityDataByKey = new EntityQueryByKeyInfo();
                queryEntityDataByKey.setEntityViewKey(unitMap.get("viewKey"));
                queryEntityDataByKey.setEntityKey(unitMap.get("code"));
                queryEntityDataByKey.setContext(new JtableContext());
                EntityByKeyReturnInfo entityByKeyReturnInfo = jtableEntityService.queryEntityDataByKey(queryEntityDataByKey);
                EntityData entityData = entityByKeyReturnInfo.getEntity();
                return entityData.getTitle();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return unitCode;
    }

    public static Map<String, DimensionValue> entityListAndUnitsCalcDimensionValue(List<EntityViewData> entitys, List<Map<String, String>> unitMapList, final String periodCode) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (final EntityViewData entity : entitys) {
            for (Map<String, String> unitMap : unitMapList) {
                if (!entity.getKey().equals(unitMap.get("viewKey"))) continue;
                String unitsStr = "";
                unitsStr = dimensionSet.get(entity.getDimensionName()) != null ? ((DimensionValue)dimensionSet.get(entity.getDimensionName())).getValue() + ";" + unitMap.get("key") : unitMap.get("key");
                final String unitTemp = unitsStr;
                dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                    private static final long serialVersionUID = 4000102614123919711L;
                    {
                        this.setName(entity.getDimensionName());
                        this.setValue(unitTemp);
                    }
                });
            }
            dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                private static final long serialVersionUID = 1305611556436424581L;
                {
                    this.setName(entity.getDimensionName());
                    this.setValue(periodCode);
                }
            });
            if (dimensionSet.get(entity.getDimensionName()) != null) continue;
            dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                private static final long serialVersionUID = 4283911834961795176L;
                {
                    this.setName(entity.getDimensionName());
                    this.setValue(entity.getKey());
                }
            });
        }
        return dimensionSet;
    }
}

