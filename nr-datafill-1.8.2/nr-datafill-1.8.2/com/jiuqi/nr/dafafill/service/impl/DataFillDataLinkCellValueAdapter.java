/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataBase;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataAdapter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataFillDataLinkCellValueAdapter
implements IDataFillEntityDataAdapter {
    private static final Logger log = LoggerFactory.getLogger(DataFillDataLinkCellValueAdapter.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionQueryFieldParser;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public boolean accept(QueryField queryField) {
        return true;
    }

    @Override
    public List<DataFillEntityDataBase> query(DataFillEntityDataQueryInfo queryInfo) {
        String title;
        List resultRows;
        Map<FieldType, List<QueryField>> fieldTypeQueryFields;
        QueryField masterQueryField;
        String sqlDimensionName;
        Object value;
        ArrayList<DataFillEntityDataBase> ret = new ArrayList<DataFillEntityDataBase>();
        DataFillContext context = queryInfo.getContext();
        DimensionValueSet dimensionValueSet = this.dFDimensionQueryFieldParser.parserGetEntityDimensionValueSet(context);
        dimensionValueSet = this.dFDimensionQueryFieldParser.entityIdToSqlDimension(context, dimensionValueSet);
        QueryField curQueryField = this.dFDimensionQueryFieldParser.getQueryFieldsMap(queryInfo.getContext()).get(queryInfo.getFullCode());
        String fullCode = curQueryField.getFullCode();
        boolean removeOrg = false;
        if (fullCode.endsWith("@ORG.PARENTCODE") || fullCode.endsWith("@BASE.PARENTCODE")) {
            removeOrg = true;
        }
        StringBuilder filterExpression = new StringBuilder();
        if (StringUtils.hasLength(curQueryField.getFilterExpression())) {
            EntityViewDefine viewByLinkDefineKey = null;
            try {
                viewByLinkDefineKey = this.runTimeViewController.getViewByLinkDefineKey(curQueryField.getFilterExpression());
            }
            catch (IllegalArgumentException e) {
                log.error(e.getLocalizedMessage(), e);
            }
            if (viewByLinkDefineKey != null && StringUtils.hasLength(viewByLinkDefineKey.getRowFilterExpression())) {
                filterExpression.append("( ").append(viewByLinkDefineKey.getRowFilterExpression()).append(" )");
            }
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        IEntityDefine queryEntity = this.entityMetaService.queryEntity(curQueryField.getExpression());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (queryEntity.getIsolation() > 0 && null != (value = varDimensionValueSet.getValue(sqlDimensionName = this.entityMetaService.getDimensionName((masterQueryField = (fieldTypeQueryFields = this.dFDimensionQueryFieldParser.getFieldTypeQueryFields(queryInfo.getContext())).get((Object)FieldType.MASTER).get(0)).getSimplifyFullCode())))) {
            entityQuery.setIsolateCondition(value.toString());
        }
        if (removeOrg) {
            fieldTypeQueryFields = this.dFDimensionQueryFieldParser.getFieldTypeQueryFields(queryInfo.getContext());
            masterQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            sqlDimensionName = this.entityMetaService.getDimensionName(masterQueryField.getSimplifyFullCode());
            varDimensionValueSet.clearValue(sqlDimensionName);
        }
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        if (StringUtils.hasLength(queryInfo.getSearch())) {
            boolean have = false;
            if (filterExpression.length() > 0) {
                filterExpression.append(" AND ( ");
                have = true;
            }
            if (queryEntity.getIsolation() > 0) {
                filterExpression.append(String.format(" %s[OBJECTCODE] LIKE \"%%%s%%\"", curQueryField.getTableCode(), queryInfo.getSearch()));
            } else {
                filterExpression.append(String.format(" %s[CODE] LIKE \"%%%s%%\"", curQueryField.getTableCode(), queryInfo.getSearch()));
            }
            filterExpression.append(" OR ");
            filterExpression.append(String.format(" %s[NAME] LIKE \"%%%s%%\"", curQueryField.getTableCode(), queryInfo.getSearch()));
            if (have) {
                filterExpression.append(" ) ");
            }
        }
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(curQueryField.getExpression(), filterExpression.toString());
        entityQuery.setMasterKeys(varDimensionValueSet);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.sorted(true);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeReader((IContext)executorContext);
            resultRows = StringUtils.hasLength(queryInfo.getSearch()) ? iEntityTable.getAllRows() : (StringUtils.hasLength(queryInfo.getParentKey()) ? iEntityTable.getChildRows(queryInfo.getParentKey()) : iEntityTable.getRootRows());
        }
        catch (Exception e) {
            throw new DataFillRuntimeException("\u67e5\u8be2\u679a\u4e3e\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        HashMap<String, String> titelMap = new HashMap<String, String>();
        for (IEntityRow row : resultRows) {
            title = row.getTitle();
            titelMap.put(row.getEntityKeyData(), title);
        }
        for (IEntityRow row : resultRows) {
            title = (String)titelMap.get(row.getEntityKeyData());
            int directChildCount = iEntityTable.getDirectChildCount(row.getEntityKeyData());
            ret.add(this.buildDataFillEntityDataBase(title, row, directChildCount == 0));
        }
        return ret;
    }

    @Override
    public DataFillEntityDataBase queryByIdOrCode(DataFillEntityDataQueryInfo queryInfo) {
        IEntityTable iEntityTable;
        if (null == queryInfo.getCode() || queryInfo.getCode().length() == 0) {
            return null;
        }
        QueryField curQueryField = this.dFDimensionQueryFieldParser.getQueryFieldsMap(queryInfo.getContext()).get(queryInfo.getFullCode());
        String fullCode = curQueryField.getFullCode();
        boolean removeOrg = false;
        if (fullCode.endsWith("@ORG.PARENTCODE") || fullCode.endsWith("@BASE.PARENTCODE")) {
            removeOrg = true;
        }
        DataFillContext context = queryInfo.getContext();
        DimensionValueSet dimensionValueSet = this.dFDimensionQueryFieldParser.parserGetEntityDimensionValueSet(context);
        dimensionValueSet = this.dFDimensionQueryFieldParser.entityIdToSqlDimension(context, dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        if (removeOrg) {
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionQueryFieldParser.getFieldTypeQueryFields(queryInfo.getContext());
            QueryField masterQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            String sqlDimensionName = this.entityMetaService.getDimensionName(masterQueryField.getSimplifyFullCode());
            varDimensionValueSet.clearValue(sqlDimensionName);
        }
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(curQueryField.getExpression());
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(varDimensionValueSet);
        try {
            iEntityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new DataFillRuntimeException("\u67e5\u8be2\u679a\u4e3e\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        IEntityRow row = iEntityTable.findByEntityKey(queryInfo.getCode());
        if (null == row) {
            row = iEntityTable.findByCode(queryInfo.getCode());
        }
        if (null != row) {
            String title = row.getTitle();
            int directChildCount = iEntityTable.getDirectChildCount(row.getEntityKeyData());
            return this.buildDataFillEntityDataBase(title, row, directChildCount == 0);
        }
        return null;
    }

    private DataFillEntityDataBase buildDataFillEntityDataBase(String title, IEntityRow row, boolean isLeaf) {
        DataFillEntityDataBase dataFillEntityDataBase = new DataFillEntityDataBase();
        dataFillEntityDataBase.setId(row.getEntityKeyData());
        dataFillEntityDataBase.setLeaf(isLeaf);
        dataFillEntityDataBase.setTitle(title);
        dataFillEntityDataBase.setCode(row.getEntityKeyData());
        dataFillEntityDataBase.setPath(String.format("%s/%s", String.join((CharSequence)"/", row.getParentsEntityKeyDataPath()), row.getCode()));
        return dataFillEntityDataBase;
    }
}

