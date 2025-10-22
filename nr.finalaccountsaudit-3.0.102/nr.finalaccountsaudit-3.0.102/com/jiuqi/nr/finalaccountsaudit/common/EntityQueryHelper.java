/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.FormFieldWrapper;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityQueryHelper {
    private static final String PERIOD_DIM = "DATATIME";
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IDataDefinitionRuntimeController dataCtrl;
    @Resource
    private IRunTimeViewController viewCtrl;
    @Resource
    private IEntityViewRunTimeController entityViewCtrl;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period, String filterEntityList, String rowFilter, AuthorityType filterAuth, boolean ignoreViewCondition) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(viewDefine);
        DimensionValueSet valueSet = new DimensionValueSet();
        boolean filterSetted = false;
        if (StringUtils.isNotEmpty((String)period)) {
            valueSet.setValue(PERIOD_DIM, (Object)period);
            filterSetted = true;
        }
        if (StringUtils.isNotEmpty((String)filterEntityList)) {
            IEntityDefine entityDefine = this.metaService.queryEntity(viewDefine.getEntityId());
            valueSet.setValue(entityDefine.getDimensionName(), (Object)filterEntityList);
            filterSetted = true;
        }
        if (filterSetted) {
            query.setMasterKeys(valueSet);
        }
        if (StringUtils.isNotEmpty((String)rowFilter)) {
            query.setRowFilter(rowFilter);
        }
        query.setAuthorityOperations(filterAuth);
        query.setIgnoreViewFilter(ignoreViewCondition);
        return query;
    }

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period, String filterEntityList, String rowFilter, AuthorityType filterAuth) {
        return this.getEntityQuery(viewDefine, period, filterEntityList, rowFilter, filterAuth, false);
    }

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period, String filterEntityList, AuthorityType filterAuth) {
        return this.getEntityQuery(viewDefine, period, filterEntityList, null, filterAuth, false);
    }

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period, AuthorityType filterAuth) {
        return this.getEntityQuery(viewDefine, period, null, null, filterAuth, false);
    }

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period) {
        return this.getEntityQuery(viewDefine, period, null, null, AuthorityType.Read, false);
    }

    public IEntityQuery getEntityQuery(EntityViewDefine entityView) {
        return this.getEntityQuery(entityView, null, null, null, AuthorityType.Read, false);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period, String filterEntityList, String rowFilter, AuthorityType filterAuth, boolean ignoreViewCondition) {
        return this.getEntityQuery(this.getDwEntityView(formSchemaKey), period, filterEntityList, rowFilter, filterAuth, ignoreViewCondition);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period, String filterEntityList, String rowFilter, AuthorityType filterAuth) {
        return this.getEntityQuery(formSchemaKey, period, filterEntityList, rowFilter, filterAuth, false);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period, String filterEntityList, AuthorityType filterAuth) {
        return this.getEntityQuery(formSchemaKey, period, filterEntityList, null, filterAuth, false);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period, AuthorityType filterAuth) {
        return this.getEntityQuery(formSchemaKey, period, null, null, filterAuth, false);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period) {
        return this.getEntityQuery(formSchemaKey, period, null, null, AuthorityType.Read, false);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey) {
        return this.getEntityQuery(formSchemaKey, null, null, null, AuthorityType.Read, false);
    }

    public IEntityTable buildEntityTable(IEntityQuery entityQuery, String formSchemeKey, boolean lessUse) throws Exception {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataCtrl);
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewCtrl, this.dataCtrl, this.entityViewCtrl, formSchemeKey);
            context.setEnv((IFmlExecEnvironment)environment);
        }
        context.setVarDimensionValueSet(entityQuery.getMasterKeys());
        if (lessUse) {
            return entityQuery.executeReader((IContext)context);
        }
        return entityQuery.executeFullBuild((IContext)context);
    }

    public IEntityTable buildEntityTable(EntityViewDefine entityView, String period, String formSchemeKey, boolean lessUse) throws Exception {
        return this.buildEntityTable(this.getEntityQuery(entityView, period), formSchemeKey, lessUse);
    }

    public IEntityTable buildEntityTable(EntityViewDefine entityView, String period, String formSchemeKey, String filterEntityList, boolean lessUse) throws Exception {
        return this.buildEntityTable(this.getEntityQuery(entityView, period, filterEntityList, AuthorityType.Read), formSchemeKey, lessUse);
    }

    public FormFieldWrapper getFormField(String dataSchemeId, String entityId, FormDefine fm, DataLinkDefine dl) throws Exception {
        FormFieldWrapper result = null;
        if (dl == null || StringUtils.isEmpty((String)dl.getLinkExpression())) {
            return null;
        }
        DataField dataFd = null;
        IFMDMAttribute fmAttr = null;
        if (fm.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            fmdmAttributeDTO.setEntityId(entityId);
            fmdmAttributeDTO.setFormSchemeKey(fm.getFormScheme());
            fmdmAttributeDTO.setCode(dl.getLinkExpression());
            fmAttr = this.fmdmAttributeService.queryByCode(fmdmAttributeDTO);
        } else if (dl.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
            dataFd = this.runtimeDataSchemeService.getDataField(dl.getLinkExpression());
        }
        if (dataFd != null) {
            if (StringUtils.isNotEmpty((String)dataFd.getRefDataEntityKey())) {
                result = new FormFieldWrapper(dl, dataFd);
                List fds = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFd.getKey()});
                if (fds != null && fds.size() > 0) {
                    DataFieldDeployInfo dfDeployInfo = (DataFieldDeployInfo)fds.get(0);
                    result.setTableName(dfDeployInfo.getTableName());
                    result.setRefEntity(this.metaService.queryEntity(dataFd.getRefDataEntityKey()));
                    result.setRefTableName(result.getRefEntity().getCode());
                    result.setIsEntityTable(false);
                }
            }
        } else if (fmAttr != null && StringUtils.isNotEmpty((String)fmAttr.getReferTableID())) {
            result = new FormFieldWrapper(dl, fmAttr);
            result.setTableName(this.entityMetaService.queryEntity(fmAttr.getEntityId()).getCode());
            IEntityDefine refEntity = this.entityMetaService.queryEntity(fmAttr.getReferEntityId());
            result.setRefTableName(refEntity.getCode());
            result.setRefEntity(refEntity);
            result.setIsEntityTable(true);
        }
        return result;
    }

    public List<FormFieldWrapper> getAllEnumOfForm(String dataSchemeId, String entityId, String formKey) throws Exception {
        ArrayList<FormFieldWrapper> result = new ArrayList<FormFieldWrapper>();
        FormDefine fm = this.viewCtrl.queryFormById(formKey);
        for (DataLinkDefine dl : this.viewCtrl.getAllLinksInForm(formKey)) {
            FormFieldWrapper fw;
            if (dl.getPosX() < 1 || dl.getPosY() < 1) continue;
            DataRegionDefine dataRegionDefine = this.viewCtrl.queryDataRegionDefine(dl.getRegionKey());
            if (dl.getPosY() > dataRegionDefine.getRegionBottom() || dl.getPosX() > dataRegionDefine.getRegionRight() || (fw = this.getFormField(dataSchemeId, entityId, fm, dl)) == null) continue;
            result.add(fw);
        }
        return result;
    }

    public HashMap<String, Integer> entityOrderByKey(IEntityQuery entityQuery, String formSchemeKey) throws Exception {
        IEntityTable entityTable = this.buildEntityTable(entityQuery, formSchemeKey, false);
        return this.entityOrderByKey(entityTable);
    }

    public HashMap<String, Integer> entityOrderByKey(IEntityTable entityTable) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        ArrayList<IEntityRow> rootRows = new ArrayList<IEntityRow>();
        rootRows.addAll(entityTable.getRootRows());
        Collections.sort(rootRows, new Comparator<IEntityRow>(){

            @Override
            public int compare(IEntityRow o1, IEntityRow o2) {
                if (o1 == null || o2 == null || o1.getEntityOrder() == null || o2.getEntityOrder() == null) {
                    return -1;
                }
                String l1 = o1.getEntityOrder().toString();
                String l2 = o2.getEntityOrder().toString();
                return l1.compareTo(l2);
            }
        });
        this.recursiveEntity(result, entityTable, rootRows);
        return result;
    }

    private void recursiveEntity(HashMap<String, Integer> result, IEntityTable entityTable, List<IEntityRow> rootRows) {
        for (IEntityRow row : rootRows) {
            result.put(row.getEntityKeyData(), result.size());
            this.recursiveEntity(result, entityTable, entityTable.getChildRows(row.getEntityKeyData()));
        }
    }

    public HashMap<String, Integer> formOrderByKey(String formSchemaKey) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        int idx = 0;
        for (FormDefine form : this.viewCtrl.queryAllFormDefinesByFormScheme(formSchemaKey)) {
            result.put(form.getKey(), idx++);
        }
        return result;
    }

    public EntityViewDefine getDwEntityView(String formSchemeKey) {
        return this.viewCtrl.getViewByFormSchemeKey(formSchemeKey);
    }

    public EntityViewDefine getDimEntityView(String formSchemeKey, String entityId) {
        return this.entityViewCtrl.buildEntityView(entityId);
    }

    public EntityViewDefine getDateEnitityView(String formSchemeKey) {
        FormSchemeDefine schemeDefine = this.viewCtrl.getFormScheme(formSchemeKey);
        return this.entityViewCtrl.buildEntityView(schemeDefine.getDateTime());
    }

    public EntityViewDefine getEnumViewByEntityId(String entityId) {
        return this.entityViewCtrl.buildEntityView(entityId);
    }

    public EntityViewDefine getEnumViewByTableName(String jsyy) {
        IEntityDefine entityDefine = this.metaService.queryEntityByCode(jsyy);
        return this.entityViewCtrl.buildEntityView(entityDefine.getId());
    }

    public String getMainDimName(String formSchemeKey) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataCtrl);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((ExecutorContext)context);
        return dataAssist.getDimensionName(this.getDwEntityView(formSchemeKey));
    }

    private String getDimName(String formSchemeKey, String entityId) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataCtrl);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((ExecutorContext)context);
        return dataAssist.getDimensionName(this.entityViewCtrl.buildEntityView(entityId));
    }

    public EntityViewDefine getEntityView(String formSchemeKey, String viewKey) {
        EntityViewDefine dwEntityView = this.getDwEntityView(formSchemeKey);
        if (viewKey.equals(dwEntityView.getEntityId())) {
            return dwEntityView;
        }
        return this.entityViewCtrl.buildEntityView(viewKey);
    }

    public String getDataScheme(String formSchemaKey) {
        String tskKey = this.viewCtrl.getFormScheme(formSchemaKey).getTaskKey();
        return this.viewCtrl.queryTaskDefine(tskKey).getDataScheme();
    }

    public String getTableNameByTableCode(String tableCode) {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey()).get(0);
        return deployInfo.getTableName();
    }

    public String getTableNameByFieldKey(String fieldKey) {
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldKey}).get(0);
        return deployInfo.getTableName();
    }

    public String getFieldNameByFieldKey(String fieldKey) {
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldKey}).get(0);
        return deployInfo.getFieldName();
    }

    public DataField getDataFieldByFieldKey(String fieldKey) {
        return this.runtimeDataSchemeService.getDataField(fieldKey);
    }
}

