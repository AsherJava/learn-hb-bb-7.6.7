/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.domain.EntityDataDO
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.query.ICommonEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.domain.EntityDataDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FMDMNodeTitleQuery {
    private final IUnitTreeContext context;
    private final DimensionValueSet dimValueSet;
    private final List<IFMDMAttribute> attributes;
    private final Map<String, IFMDMData> cacheDataMap = new HashMap<String, IFMDMData>();
    private final Map<String, IEntityTable> refEntityDataMap = new HashMap<String, IEntityTable>();
    private final Map<String, String> titleMap = new HashMap<String, String>();

    public FMDMNodeTitleQuery(IUnitTreeContext context) {
        this.context = context;
        this.attributes = this.getCationFields(context);
        this.dimValueSet = this.buildDimensionValueSet(context);
    }

    public void batchBuildCacheRowTitle(List<IEntityRow> rows) {
        if (rows != null && !rows.isEmpty()) {
            List<IFMDMData> list = rows.stream().map(EntityDataDO::new).collect(Collectors.toList());
            list.forEach(fd -> this.cacheDataMap.put(fd.getFMDMKey(), (IFMDMData)fd));
        }
    }

    public void batchQueryCacheRowTitle(List<String> rowKeys) {
        IFMDMDataService dataServiceOfFMDM = (IFMDMDataService)SpringBeanUtils.getBean(IFMDMDataService.class);
        this.dimValueSet.setValue(this.context.getEntityDefine().getDimensionName(), rowKeys);
        List list = this.context.getFormScheme() != null ? dataServiceOfFMDM.list(this.createFMDMDataDTO(this.dimValueSet)) : this.listEntityData(rowKeys);
        if (list != null && !list.isEmpty()) {
            list.forEach(fd -> this.cacheDataMap.put(fd.getFMDMKey(), (IFMDMData)fd));
        }
    }

    public String getAttributesTitle(String rowKey) {
        List titles = this.attributes.stream().map(attribute -> this.getAttributeTitle((IFMDMAttribute)attribute, this.readOneFMDMData(rowKey))).collect(Collectors.toList());
        return String.join((CharSequence)" | ", titles);
    }

    private List<IFMDMData> listEntityData(List<String> rowKeys) {
        UnitTreeEntityDataQuery dataQuery = (UnitTreeEntityDataQuery)SpringBeanUtils.getBean(UnitTreeEntityDataQuery.class);
        IEntityTable entityTable = dataQuery.makeIEntityTable(this.context, rowKeys);
        List allRows = entityTable.getAllRows();
        return allRows.stream().map(EntityDataDO::new).collect(Collectors.toList());
    }

    private String getAttributeTitle(IFMDMAttribute attribute, IFMDMData ifmdmData) {
        if (ifmdmData != null) {
            AbstractData value = ifmdmData.getValue(attribute.getCode());
            String fmdmValue = value != null && !value.isNull ? value.getAsString() : "";
            String referEntityId = attribute.getReferEntityId();
            if (StringUtils.isNotEmpty((String)fmdmValue) && StringUtils.isNotEmpty((String)referEntityId)) {
                String titleKey = referEntityId + "_" + fmdmValue;
                if (!this.titleMap.containsKey(titleKey)) {
                    IEntityRow row = this.getFMDMEntityRow(referEntityId, fmdmValue);
                    String rowTitle = row != null ? row.getTitle() : fmdmValue;
                    this.titleMap.put(titleKey, rowTitle);
                }
                return this.titleMap.get(titleKey);
            }
            return fmdmValue;
        }
        return "";
    }

    private IEntityRow getFMDMEntityRow(String referEntityId, String fmdmValue) {
        IEntityTable dataTable = this.refEntityDataMap.get(referEntityId);
        if (dataTable == null) {
            ICommonEntityDataQuery entityRowQuery = (ICommonEntityDataQuery)SpringBeanUtils.getBean(ICommonEntityDataQuery.class);
            dataTable = entityRowQuery.makeIEntityTable(referEntityId);
            this.refEntityDataMap.put(referEntityId, dataTable);
        }
        return dataTable.findByEntityKey(fmdmValue);
    }

    private IFMDMData readOneFMDMData(String rowKey) {
        IFMDMData fmdmData = this.cacheDataMap.get(rowKey);
        if (fmdmData == null) {
            IFMDMDataService dataServiceOfFMDM = (IFMDMDataService)SpringBeanUtils.getBean(IFMDMDataService.class);
            this.dimValueSet.setValue(this.context.getEntityDefine().getDimensionName(), (Object)rowKey);
            List list = dataServiceOfFMDM.list(this.createFMDMDataDTO(this.dimValueSet));
            return list != null && !list.isEmpty() ? (IFMDMData)list.get(0) : null;
        }
        return fmdmData;
    }

    private List<IFMDMAttribute> getCationFields(IUnitTreeContext context) {
        IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
        return contextWrapper.getCationFields(context.getFormScheme(), context.getEntityDefine(), context.getEntityQueryPloy());
    }

    private FMDMDataDTO createFMDMDataDTO(DimensionValueSet dimValueSet) {
        IDataDefinitionRuntimeController tbRtCtl = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        FormSchemeDefine formScheme = this.context.getFormScheme();
        ExecutorContext executorContext = new ExecutorContext(tbRtCtl);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        ReportFmlExecEnvironment iFmlExecEnvironment = new ReportFmlExecEnvironment(runTimeViewController, runtimeController, entityViewRunTimeController, formScheme.getKey());
        executorContext.setEnv((IFmlExecEnvironment)iFmlExecEnvironment);
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setContext((IContext)executorContext);
        fmdmDataDTO.setFormSchemeKey(formScheme.getKey());
        fmdmDataDTO.setDimensionValueSet(dimValueSet);
        return fmdmDataDTO;
    }

    private DimensionValueSet buildDimensionValueSet(IUnitTreeContext context) {
        DimensionValueSet dimValueSet = new DimensionValueSet();
        if (context.getPeriodEntity() != null) {
            dimValueSet.setValue(context.getPeriodEntity().getDimensionName(), (Object)context.getPeriod());
        }
        return dimValueSet;
    }
}

