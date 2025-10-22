/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.form.analysis.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.form.analysis.common.FormAnalysisErrorEnum;
import com.jiuqi.nr.form.analysis.common.FormAnalysisParamEnum;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormAnalysisEntityHelper {
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    private static final Logger log = LoggerFactory.getLogger(FormAnalysisEntityHelper.class);

    public ExecutorContext createExecutorContext() {
        return this.createExecutorContext(null);
    }

    public ExecutorContext createExecutorContext(String formSchemeKey) {
        ExecutorContext newExecutorContext = new ExecutorContext(this.runtimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeKey);
        newExecutorContext.setEnv((IFmlExecEnvironment)env);
        return newExecutorContext;
    }

    public IEntityTable getIEntityTable(String entityId, DimensionValueSet masterKeys, boolean fullBuild) throws JQException {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (null != masterKeys) {
            entityQuery.setMasterKeys(masterKeys);
        }
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityId);
        entityQuery.setEntityView(entityView);
        ExecutorContext context = this.createExecutorContext();
        try {
            if (fullBuild) {
                return entityQuery.executeFullBuild((IContext)context);
            }
            return entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_701, (Throwable)e);
        }
    }

    public List<String> getEntityDatasByCondition(String srcFormSchemeKey, DimensionValueSet periodValueSet, String destEntityId, String destRowKey, String srcEntityId, String condition) {
        ArrayList<String> entityData = new ArrayList<String>();
        if (StringUtils.hasText(condition)) {
            try {
                IEntityTable srcEntityTable = this.getIEntityTable(srcEntityId, periodValueSet, false);
                IEntityTable destEntityTable = this.getIEntityTable(destEntityId, periodValueSet, false);
                List allEntityRows = srcEntityTable.getAllRows();
                ExecutorContext executorContext = this.createExecutorContext(srcFormSchemeKey);
                IEntityRow destEntityRow = destEntityTable.findByEntityKey(destRowKey);
                IFieldsInfo fieldsInfo = destEntityTable.getFieldsInfo();
                for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
                    IEntityAttribute field = fieldsInfo.getFieldByIndex(i);
                    if (field == null) continue;
                    AbstractData value = destEntityRow.getValue(field.getCode());
                    int dataType = value.dataType;
                    executorContext.getVariableManager().add(new Variable("SYS_SRC_" + field.getCode(), field.getTitle(), dataType, value.getAsObject()));
                }
                Metadata metaData = new Metadata();
                IFieldsInfo srcFieldsInfo = srcEntityTable.getFieldsInfo();
                for (int i = 0; i < srcFieldsInfo.getFieldCount(); ++i) {
                    IEntityAttribute entityAttribute = srcFieldsInfo.getFieldByIndex(i);
                    metaData.addColumn(new Column(entityAttribute.getCode(), entityAttribute.getColumnType().getValue()));
                }
                MemoryDataSet dataSet = new MemoryDataSet(null, metaData);
                IDataSetExprEvaluator judger = this.dataAccessProvider.newDataSetExprEvaluator((DataSet)dataSet);
                judger.prepare(executorContext, new DimensionValueSet(), condition);
                for (IEntityRow entityRow : allEntityRows) {
                    DataRow row = dataSet.add();
                    for (int i = 0; i < srcFieldsInfo.getFieldCount(); ++i) {
                        row.setValue(i, (Object)entityRow.getValue(srcFieldsInfo.getFieldByIndex(i).getCode()));
                    }
                    if (judger.judge(row)) {
                        entityData.add(entityRow.getEntityKeyData());
                    }
                    row.commit();
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return entityData;
    }

    public List<String> getPeriodDatas(String periodEntityId, String startData, String endData) {
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(periodEntityId);
        List periodItems = periodProvider.getPeriodItems();
        ArrayList<String> result = new ArrayList<String>();
        int state = 0;
        for (IPeriodRow periodItem : periodItems) {
            if (startData.equals(periodItem.getCode()) || endData.equals(periodItem.getCode())) {
                ++state;
            }
            if (1 == state) {
                result.add(periodItem.getCode());
            }
            if (2 != state) continue;
            break;
        }
        return result;
    }

    public List<String> getPeriodDatas(String periodEntityId, String periodRange) {
        List<String> periodDatas = null;
        String[] split = periodRange.split("-");
        periodDatas = !StringUtils.hasText(split[1]) || "null".equals(split[1]) ? Collections.singletonList(split[0]) : this.getPeriodDatas(periodEntityId, split[0], split[1]);
        return periodDatas;
    }

    public String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityId);
            return periodEntity.getDimensionName();
        }
        return this.entityMetaService.getDimensionName(entityId);
    }

    public SrcDimDataQueryer createSrcDimDataQueryer(String srcFormSchemeKey) {
        return new SrcDimDataQueryer(srcFormSchemeKey);
    }

    public class SrcDimDataQueryer {
        private String srcFormSchemeKey;
        private DimensionValueSet periodValueSet;
        private Map<String, String> dimNameMap = new HashMap<String, String>();
        private Map<String, List<String>> entityDataMap = new HashMap<String, List<String>>();
        private Map<String, List<String>> periodDataMap = new HashMap<String, List<String>>();

        protected SrcDimDataQueryer(String srcFormSchemeKey) {
            this.srcFormSchemeKey = srcFormSchemeKey;
        }

        public void set(DimensionValueSet periodValueSet) {
            this.periodValueSet = periodValueSet;
        }

        public String getDimName(String entityId) {
            if (this.dimNameMap.containsKey(entityId)) {
                return this.dimNameMap.get(entityId);
            }
            String dimName = FormAnalysisEntityHelper.this.getDimensionName(entityId);
            this.dimNameMap.put(entityId, dimName);
            return dimName;
        }

        public List<String> getEntityDatas(String entityId, String rowData, FormAnalysisParamEnum.DimDataRangeType type) throws JQException {
            String concatKey = this.getConcatKey(entityId, rowData, type.name());
            if (this.entityDataMap.containsKey(concatKey)) {
                return this.entityDataMap.get(concatKey);
            }
            IEntityTable iEntityTable = FormAnalysisEntityHelper.this.getIEntityTable(entityId, this.periodValueSet, false);
            List rows = null;
            switch (type) {
                case DEST_CHILDREN: {
                    rows = iEntityTable.getChildRows(rowData);
                    break;
                }
                case DEST_ALL_CHILDREN: {
                    rows = iEntityTable.getAllChildRows(rowData);
                    break;
                }
                case DEST_BROTHERS: {
                    IEntityRow entityRow = iEntityTable.findByEntityKey(rowData);
                    if (StringUtils.hasText(entityRow.getParentEntityKey())) {
                        rows = iEntityTable.getRootRows();
                        break;
                    }
                    rows = iEntityTable.getChildRows(entityRow.getParentEntityKey());
                    break;
                }
            }
            if (null != rows) {
                return rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            return null;
        }

        public List<String> getEntityDatas(String destOrgEntityId, String destOrgRowData, String srcEntityId, String condition) {
            String concatKey = this.getConcatKey(srcEntityId, destOrgRowData, condition);
            if (this.entityDataMap.containsKey(concatKey)) {
                return this.entityDataMap.get(concatKey);
            }
            List<String> datas = FormAnalysisEntityHelper.this.getEntityDatasByCondition(this.srcFormSchemeKey, this.periodValueSet, destOrgEntityId, destOrgRowData, srcEntityId, condition);
            this.entityDataMap.put(concatKey, datas);
            return datas;
        }

        public List<String> getPeriodDatas(String periodEntityId, String periodRange) {
            String concatKey = this.getConcatKey(periodEntityId, periodRange);
            if (this.periodDataMap.containsKey(concatKey)) {
                return this.periodDataMap.get(concatKey);
            }
            List<String> periodDatas = FormAnalysisEntityHelper.this.getPeriodDatas(periodEntityId, periodRange);
            this.periodDataMap.put(concatKey, periodDatas);
            return periodDatas;
        }

        private String getConcatKey(String ... keys) {
            String concatKey = "";
            for (String key : keys) {
                concatKey.concat(key);
            }
            return concatKey;
        }
    }
}

