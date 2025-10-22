/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel
 *  com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.dataset.QueryDSField;
import com.jiuqi.nr.query.dataset.QueryDSModel;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DSParameterAdapter {
    private static final Logger log = LoggerFactory.getLogger(DSParameterAdapter.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private QueryEntityUtil queryEntityUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ParameterModel> getParameterModel(ExecutorContext executorContext, QueryDSModel dsModel, QueryBlockDefine block, String modelType) {
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        for (QueryDimensionDefine dim : dims) {
            String dimName;
            int colIndex = -1;
            EntityViewDefine entityView = null;
            if (dim.getDimensionType() == QueryDimensionType.QDT_ENTITY) {
                entityView = this.entityViewRunTimeController.buildEntityView(dim.getViewId().toString());
            } else if (dim.getDimensionType() == QueryDimensionType.QDT_DICTIONARY) {
                for (int i = 0; i < dsModel.getCommonFields().size(); ++i) {
                    QueryDSField qdsField = (QueryDSField)((Object)dsModel.getCommonFields().get(i));
                    if (qdsField.getColumnModel() == null) continue;
                    try {
                        ColumnModelDefine columnModel = qdsField.getColumnModel();
                        DataModelDefinitionsCache dataModelDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
                        String dimName2 = dataModelDefinitionsCache.getDimensionName(columnModel);
                        if (!dimName2.equals(dim.getDimensionName())) continue;
                        colIndex = i;
                        if (columnModel.getReferTableID() == null) continue;
                        TableModelDefine refTable = dataModelDefinitionsCache.findTable(columnModel.getReferTableID());
                        String refEntityId = dataModelDefinitionsCache.getDimensionProvider().getEntityIdByEntityTableCode(executorContext, refTable.getCode());
                        entityView = this.entityViewRunTimeController.buildEntityView(refEntityId);
                        continue;
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
            if (entityView == null) continue;
            String tableTitle = null;
            boolean isPeriodUnit = false;
            String newDimName = dimName = dataAssist.getDimensionName(entityView);
            try {
                ParameterModel parameterModel = new ParameterModel();
                ParameterValueConfig valueConfig = new ParameterValueConfig();
                isPeriodUnit = this.queryEntityUtil.isPeriodUnit(entityView.getEntityId());
                if (isPeriodUnit) {
                    tableTitle = "\u65f6\u671f";
                    IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                    IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(entityView.getEntityId());
                    parameterModel.setGuid(periodEntity.getKey());
                    parameterModel.setName("MD_PERIOD");
                    parameterModel.setTitle(tableTitle);
                    valueConfig.setDefaultValueMode("expr");
                    ExpressionParameterValue expValue = new ExpressionParameterValue("-0N");
                    valueConfig.setDefaultValue((AbstractParameterValue)expValue);
                    parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
                    NrPeriodDataSourceModel dataSourceModel = new NrPeriodDataSourceModel();
                    dataSourceModel.setEntityViewId(periodEntity.getKey());
                    dataSourceModel.setDataType(6);
                    dataSourceModel.setPeriodType(periodEntity.getPeriodType().type());
                    parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
                } else {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityView.getEntityId());
                    tableTitle = entityDefine.getTitle();
                    String sql = "select ET_TD_KEY from NR_ENTITY_UPGRADE_TABLE where  ET_ENTITY_ID='?'";
                    String tableDefineKey = (String)this.jdbcTemplate.queryForObject(sql, String.class, new Object[]{entityView.getEntityId()});
                    sql = "select TD_BIZKEY from SYS_TABLEDEFINE where  TD_KEY='?'";
                    String keyFieldId = (String)this.jdbcTemplate.queryForObject(sql, String.class, new Object[]{tableDefineKey});
                    sql = "select fd_code from sys_fielddefine where fd_key='?'";
                    String oldKeyFieldCode = (String)this.jdbcTemplate.queryForObject(sql, String.class, new Object[]{keyFieldId});
                    dimName = dimName + "_" + oldKeyFieldCode;
                    parameterModel.setGuid(entityDefine.getId());
                    parameterModel.setName(dimName);
                    parameterModel.setMessageAlias(dimName);
                    parameterModel.setTitle(tableTitle);
                    valueConfig.setDefaultValueMode("first");
                    parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
                    NrEntityDataSourceModel dataSourceModel = new NrEntityDataSourceModel();
                    dataSourceModel.setEntityViewId(entityDefine.getId());
                    dataSourceModel.setDataType(6);
                    parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
                    if (colIndex >= 0) {
                        dsModel.getParaColumnIndexMap().put(dimName, colIndex);
                    }
                }
                dsModel.getParaNamesMap().put(dimName, newDimName);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return parameterModels;
    }
}

