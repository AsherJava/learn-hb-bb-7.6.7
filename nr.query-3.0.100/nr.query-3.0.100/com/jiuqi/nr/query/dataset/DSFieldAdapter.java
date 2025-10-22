/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeHelper
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.dataset.QueryDSField;
import com.jiuqi.nr.query.dataset.QueryDefineDataSetUtil;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSContext;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNodeFinder;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DSFieldAdapter {
    @Autowired
    private IEntityMetaService entityMetaService;

    public QueryDSField getDSField(NrQueryDSContext context, ColumnModelDefine columnModel, String title) throws ParseException, Exception {
        QueryDSField dsField = new QueryDSField(columnModel);
        FieldDefine field = context.getExecutorContext().getCache().getDataModelDefinitionsCache().getColumnModelFinder().findFieldDefine(columnModel);
        String dsFieldName = this.getDSFieldName(context, field);
        dsField.setName(dsFieldName);
        dsField.setValType(this.fieldTypeToDataType(field.getType()));
        dsField.setFieldType(this.getDSFieldType(field));
        dsField.setAggregation(this.adaptAggregation(field));
        dsField.setApplyType(ApplyType.PERIOD);
        String dsFieldTitle = field.getTitle();
        if (title != null) {
            dsFieldTitle = title;
        }
        dsField.setTitle(dsFieldTitle);
        dsField.setMessageAlias(dsFieldName);
        dsField.setKeyField(dsFieldName);
        dsField.setNameField(dsFieldName);
        return dsField;
    }

    public QueryDSField getDsFieldByExpression(NrQueryDSContext context, TableModelRunInfo tableRunInfo, DataModelDefinitionsCache dataDefinitionsCache, ReportFormulaParser formulaParser, QueryContext qContext, String dsFieldTitle, QueryDSField dsField, String customExpression) throws ParseException, Exception {
        DataSetDimensionNode dimNode = null;
        IExpression exp = null;
        String entityTableName = null;
        String entityFieldName = null;
        try {
            exp = formulaParser.parseEval(customExpression, (IContext)qContext);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (exp != null) {
            Object dataNode;
            IASTNode root = exp.getChild(0);
            if (root instanceof DynamicDataNode) {
                dataNode = (DynamicDataNode)root;
                entityTableName = dataNode.getQueryField().getTableName();
                entityFieldName = dataNode.getQueryField().getFieldName();
            } else if (root instanceof DataSetDimensionNode) {
                dataNode = (DataSetDimensionNode)root;
                String oldEntityTableName = ((DataSetDimensionNode)((Object)dataNode)).getEntityTableName();
                entityTableName = context.getOldTableNameMap().get(oldEntityTableName);
                if (entityTableName == null) {
                    String sql = "select td_key from sys_tabledefine where td_code='?'";
                    String tableDefineKey = (String)context.getJdbcTemplate().queryForObject(sql, String.class, new Object[]{oldEntityTableName});
                    sql = "select ET_NAME from NR_ENTITY_UPGRADE_TABLE where  ET_TD_KEY='?'";
                    entityTableName = (String)context.getJdbcTemplate().queryForObject(sql, String.class, new Object[]{tableDefineKey});
                    context.getOldTableNameMap().put(oldEntityTableName, entityTableName);
                }
                entityFieldName = ((DataSetDimensionNode)((Object)dataNode)).getEntityFieldName();
            }
            Map<String, TableModelRunInfo> allTableInfos = context.getAllTableInfos();
            if (entityTableName != null) {
                String unitdim;
                for (ColumnModelDefine columnModel : tableRunInfo.getAllFields()) {
                    IEntityDefine entityDefine;
                    FieldDefine field = dataDefinitionsCache.getFieldDefine(columnModel);
                    if (field.getEntityKey() == null || field.getEntityKey().indexOf("@") <= 0 || !(entityDefine = this.entityMetaService.queryEntity(field.getEntityKey())).getCode().equals(entityTableName) && !entityDefine.getDimensionName().equals(entityTableName)) continue;
                    IEntityModel entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
                    dimNode = DataSetDimensionNodeFinder.createNode(entityTableName, entityModel, entityFieldName, tableRunInfo.getTableModelDefine().getCode(), columnModel.getCode());
                    if (allTableInfos.size() <= 1) break;
                    ArrayList<String> fieldExpList = new ArrayList<String>();
                    for (TableModelRunInfo tableInfo : allTableInfos.values()) {
                        String fieldExp = tableInfo.getTableModelDefine().getCode() + "[" + field.getCode() + "]";
                        fieldExpList.add(fieldExp);
                    }
                    dimNode.setFieldExpList(fieldExpList);
                    break;
                }
                if (dimNode == null && (unitdim = qContext.getExeContext().getEnv().getUnitDimesion(qContext.getExeContext())) != null) {
                    ColumnModelDefine unitField = tableRunInfo.getDimensionField(unitdim);
                    FieldDefine unitFieldDefine = dataDefinitionsCache.getFieldDefine(unitField);
                    IEntityDefine unitEntityDefine = this.entityMetaService.queryEntity(unitFieldDefine.getEntityKey());
                    IEntityModel unitEntiyModel = this.entityMetaService.getEntityModel(unitEntityDefine.getId());
                    List entityRefers = this.entityMetaService.getEntityRefer(unitEntityDefine.getId());
                    for (IEntityRefer refer : entityRefers) {
                        IEntityDefine unitRefEntityDefine = this.entityMetaService.queryEntity(refer.getReferEntityId());
                        if (!unitRefEntityDefine.getCode().equals(entityTableName)) continue;
                        IEntityModel unitRefEntiyModel = this.entityMetaService.getEntityModel(unitRefEntityDefine.getId());
                        DataSetDimensionNode fmdmNode = DataSetDimensionNodeFinder.createNode(unitEntityDefine.getCode(), unitEntiyModel, "CODE", tableRunInfo.getTableModelDefine().getCode(), unitField.getCode());
                        if (allTableInfos.size() > 1) {
                            ArrayList<String> fieldExpList = new ArrayList<String>();
                            for (TableModelRunInfo tableInfo : allTableInfos.values()) {
                                if (tableInfo.getTableModelDefine().getCode().equals(unitEntityDefine.getCode())) continue;
                                String fieldExp = tableInfo.getTableModelDefine().getCode() + "[" + unitField.getCode() + "]";
                                fieldExpList.add(fieldExp);
                            }
                            if (fieldExpList.size() > 1) {
                                fmdmNode.setFieldExpList(fieldExpList);
                            }
                        }
                        dimNode = DataSetDimensionNodeFinder.createEnumNode(unitRefEntityDefine.getCode(), unitRefEntiyModel, entityFieldName, unitEntityDefine.getCode(), refer.getOwnField(), fmdmNode);
                        break;
                    }
                }
            }
        }
        if (dimNode != null) {
            dsField = new QueryDSField();
            dsField.setExpresion(dimNode.toEvalFormula());
            dsField.setEntityTableName(entityTableName);
            dsField.setValType(6);
            dsField.setFieldType(FieldType.GENERAL_DIM);
            dsField.setName(dimNode.toString());
            dsField.setAggregation(AggregationType.MIN);
            dsField.setApplyType(ApplyType.PERIOD);
            dsField.setTitle(dsFieldTitle);
            dsField.setMessageAlias(dsField.getName());
            dsField.setKeyField(dsField.getKeyFieldName());
            dsField.setNameField(dsField.getNameFieldName());
        } else {
            int type;
            dsField = new QueryDSField(exp == null ? "0" : customExpression);
            int n = type = exp == null ? 3 : exp.getType((IContext)qContext);
            if (type == 10) {
                type = 3;
            }
            dsField.setValType(type);
            FieldType fieldType = FieldType.MEASURE;
            if (type != 10 && type != 3 && type != 1) {
                fieldType = FieldType.DESCRIPTION;
            }
            dsField.setFieldType(fieldType);
            dsField.setName(customExpression);
            dsField.setAggregation(AggregationType.MIN);
            dsField.setApplyType(ApplyType.PERIOD);
            dsField.setTitle(dsFieldTitle);
            dsField.setMessageAlias(dsField.getName());
            dsField.setKeyField(dsField.getName());
            dsField.setNameField(dsField.getName());
        }
        return dsField;
    }

    protected String getDSFieldName(NrQueryDSContext context, FieldDefine field) {
        String oldTableName;
        String oldFieldName = context.getFieldNameMap().get(field.getCode());
        if (oldFieldName == null) {
            String sql = "select FD_CODE from NR_PARAM_UPGRADE_FIELD where DF_KEY='" + field.getKey() + "'";
            oldFieldName = (String)context.getJdbcTemplate().queryForObject(sql, String.class);
            context.getFieldNameMap().put(field.getCode(), oldFieldName);
        }
        if ((oldTableName = context.getTableNameMap().get(field.getOwnerTableKey())) == null) {
            String sql = "select TD_CODE from NR_PARAM_UPGRADE_TABLE where DT_KEY='" + field.getOwnerTableKey() + "'";
            oldTableName = (String)context.getJdbcTemplate().queryForObject(sql, String.class);
            context.getTableNameMap().put(field.getOwnerTableKey(), oldTableName);
        }
        return QueryDefineDataSetUtil.getDSFieldName(oldTableName, oldFieldName);
    }

    public void adaptTimeDim(DSField dsField, TimeGranularity timeGranularity, boolean isTimeKey) throws TimeCalcException {
        dsField.setFieldType(FieldType.TIME_DIM);
        dsField.setTimekey(isTimeKey);
        dsField.setTimegranularity(timeGranularity);
        dsField.setDataPattern(TimeHelper.getDefaultDataPattern((int)timeGranularity.value(), (boolean)isTimeKey));
        dsField.setShowPattern(TimeHelper.getDefaultShowPattern((int)timeGranularity.value(), (boolean)isTimeKey));
    }

    public void adaptKeyAndName(DSField dsField, FieldDefine field, String entityTableName, boolean isNameField) {
    }

    private AggregationType adaptAggregation(FieldDefine field) {
        if (field.getGatherType() == FieldGatherType.FIELD_GATHER_SUM) {
            return AggregationType.SUM;
        }
        if (field.getGatherType() == FieldGatherType.FIELD_GATHER_MAX) {
            return AggregationType.MAX;
        }
        if (field.getGatherType() == FieldGatherType.FIELD_GATHER_MIN) {
            return AggregationType.MIN;
        }
        if (field.getGatherType() == FieldGatherType.FIELD_GATHER_AVG) {
            return AggregationType.AVG;
        }
        return AggregationType.SUM;
    }

    private FieldType getDSFieldType(FieldDefine field) {
        switch (field.getType()) {
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_LOGIC: 
            case FIELD_TYPE_DECIMAL: {
                return FieldType.MEASURE;
            }
            case FIELD_TYPE_STRING: {
                return FieldType.GENERAL_DIM;
            }
            case FIELD_TYPE_GENERAL: 
            case FIELD_TYPE_UUID: 
            case FIELD_TYPE_DATE: 
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_TIME: {
                return FieldType.GENERAL_DIM;
            }
        }
        return FieldType.DESCRIPTION;
    }

    private int fieldTypeToDataType(com.jiuqi.np.definition.common.FieldType fieldType) {
        switch (fieldType) {
            case FIELD_TYPE_GENERAL: {
                return 6;
            }
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_DECIMAL: {
                return 3;
            }
            case FIELD_TYPE_STRING: {
                return 6;
            }
            case FIELD_TYPE_INTEGER: {
                return 5;
            }
            case FIELD_TYPE_LOGIC: {
                return 1;
            }
            case FIELD_TYPE_DATE: {
                return 2;
            }
            case FIELD_TYPE_DATE_TIME: {
                return 2;
            }
            case FIELD_TYPE_TIME: {
                return 2;
            }
            case FIELD_TYPE_BINARY: {
                return 9;
            }
            case FIELD_TYPE_FILE: {
                return 9;
            }
            case FIELD_TYPE_PICTURE: {
                return 9;
            }
            case FIELD_TYPE_TEXT: {
                return 9;
            }
            case FIELD_TYPE_UUID: {
                return 6;
            }
        }
        return 0;
    }
}

