/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.access.event;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;

public class FormLockHistoryExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        List dataSchemes = dataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : dataSchemes) {
            String tableCode = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK_HIS", dataScheme.getBizCode());
            DesignTableModelDefine designTableDefine = designDataModelService.getTableModelDefineByCode(tableCode);
            if (designTableDefine != null) continue;
            this.deployFormLockHisTable(dataSchemeService, designDataModelService, dataScheme, tableCode);
        }
    }

    private void deployFormLockHisTable(IRuntimeDataSchemeService dataSchemeService, DesignDataModelService designDataModelService, DataScheme dataScheme, String tableCode) throws Exception {
        String tableTitle = TableConsts.getSysTableTitle("\u9501\u5b9a\u5386\u53f2\u8868", dataScheme.getBizCode());
        DesignTableModelDefine tableModelDefine = designDataModelService.createTableModelDefine();
        tableModelDefine.setCode(tableCode);
        tableModelDefine.setName(tableCode);
        tableModelDefine.setTitle(tableTitle);
        tableModelDefine.setDesc(tableTitle);
        tableModelDefine.setOwner("NR");
        ArrayList<DesignColumnModelDefine> list = new ArrayList<DesignColumnModelDefine>();
        list.add(this.initKeyField(tableModelDefine, designDataModelService, "", "FLH_ID", 50, ""));
        list.add(this.initKeyField(tableModelDefine, designDataModelService, "", "FLH_FORMSCHEME", 50, ""));
        list.add(this.initKeyField(tableModelDefine, designDataModelService, "", "FLH_FORM", 50, ""));
        this.initPrimaryColumns(dataSchemeService, designDataModelService, list, tableModelDefine, dataScheme);
        list.add(this.initField(designDataModelService, tableModelDefine.getID(), "", "FLH_USER", ColumnModelType.STRING, 50, ""));
        list.add(this.initField(designDataModelService, tableModelDefine.getID(), "", "FLH_OPER", ColumnModelType.INTEGER, 2, ""));
        list.add(this.initField(designDataModelService, tableModelDefine.getID(), "", "FLH_OPERTIME", ColumnModelType.DATETIME, 0, ""));
        designDataModelService.insertTableModelDefine(tableModelDefine);
        designDataModelService.insertColumnModelDefines(list.toArray(new DesignColumnModelDefine[0]));
        DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
        dataModelDeployService.deployTable(tableModelDefine.getID());
    }

    public void initPrimaryColumns(IRuntimeDataSchemeService dataSchemeService, DesignDataModelService designDataModelService, List<DesignColumnModelDefine> list, DesignTableModelDefine tableModelDefine, DataScheme dataScheme) {
        List dimension = dataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        List filterDims = dimension.stream().filter(e -> e.getDimensionType() != DimensionType.UNIT_SCOPE).collect(Collectors.toList());
        IEntityMetaService iEntityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        for (DataDimension dim : filterDims) {
            DimensionType dimType = dim.getDimensionType();
            String entityKey = dim.getDimKey();
            String defaultVal = dim.getDefaultValue();
            if (dimType.equals((Object)DimensionType.UNIT)) {
                list.add(this.initKeyField(tableModelDefine, designDataModelService, this.queryEntityTableBizKeyByEntityID(iEntityMetaService, entityKey), "MDCODE", 50, ""));
                continue;
            }
            if (dimType.equals((Object)DimensionType.PERIOD)) {
                list.add(this.initKeyField(tableModelDefine, designDataModelService, this.queryPeriodBizKey(entityKey), "PERIOD", 9, ""));
                continue;
            }
            if ("ADJUST".equals(entityKey)) {
                list.add(this.initKeyField(tableModelDefine, designDataModelService, "", "ADJUST", 50, defaultVal));
                continue;
            }
            IEntityDefine entity = iEntityMetaService.queryEntity(entityKey);
            list.add(this.initKeyField(tableModelDefine, designDataModelService, this.queryEntityBizKeyByEntityId(iEntityMetaService, entity.getId()), entity.getDimensionName(), 50, defaultVal));
        }
    }

    private String queryEntityTableBizKeyByEntityID(IEntityMetaService iEntityMetaService, String entityID) {
        IEntityDefine entity;
        if (StringUtils.isNotEmpty((String)entityID) && (entity = iEntityMetaService.queryEntity(entityID)) != null) {
            return this.queryEntityBizKeyByEntityId(iEntityMetaService, entity.getId());
        }
        return null;
    }

    private String queryEntityBizKeyByEntityId(IEntityMetaService iEntityMetaService, String entityId) {
        IEntityModel entityModel = iEntityMetaService.getEntityModel(entityId);
        IEntityAttribute referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId.getID();
    }

    private String queryPeriodBizKey(String viewKey) {
        PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewKey);
        return periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
    }

    private DesignColumnModelDefine initField(DesignDataModelService designDataModelService, String tableKey, String referField, String code, ColumnModelType type, int size, String defaultVale) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setCode(code);
        columnModelDefine.setName(code);
        columnModelDefine.setColumnType(type);
        columnModelDefine.setTableID(tableKey);
        columnModelDefine.setPrecision(size);
        columnModelDefine.setReferColumnID(referField);
        columnModelDefine.setDefaultValue(defaultVale);
        return columnModelDefine;
    }

    private DesignColumnModelDefine initKeyField(DesignTableModelDefine tableModelDefine, DesignDataModelService designDataModelService, String referField, String code, int size, String defaultVale) {
        DesignColumnModelDefine columnModelDefine = this.initField(designDataModelService, tableModelDefine.getID(), referField, code, ColumnModelType.STRING, size, defaultVale);
        columnModelDefine.setNullAble(false);
        tableModelDefine.setBizKeys(StringUtils.isEmpty((String)tableModelDefine.getBizKeys()) ? columnModelDefine.getID() : tableModelDefine.getBizKeys() + ";" + columnModelDefine.getID());
        return columnModelDefine;
    }
}

