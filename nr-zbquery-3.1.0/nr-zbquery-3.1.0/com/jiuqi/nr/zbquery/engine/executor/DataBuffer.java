/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.zbquery.engine.executor;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DataBuffer {
    private Map<String, String[]> buffer = new HashMap<String, String[]>();
    private IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    private IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
    private IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
    private IDataDefinitionRuntimeController dataDefinitionController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
    private String dimName;
    private String filterDimName;
    private String[] fieldNames;
    private int codeField = -1;
    private IEntityQuery iEntityQuery = null;
    private String period = null;
    private ExecutorContext executorContext = null;

    public DataBuffer(String dimName, String[] filedNames, String period, String periodView) throws Exception {
        IEntityDefine entityDefine;
        this.dimName = dimName;
        this.fieldNames = filedNames;
        this.period = period;
        for (int i = 0; i < filedNames.length; ++i) {
            if (!"CODE".contentEquals(filedNames[i])) continue;
            this.codeField = i;
            break;
        }
        if ((entityDefine = this.entityMetaService.queryEntityByCode(dimName)) != null) {
            this.filterDimName = entityDefine.getDimensionName();
            EntityViewDefine entityViewDefine = this.entityViewController.buildEntityView(entityDefine.getId());
            if (entityViewDefine != null) {
                this.iEntityQuery = this.entityDataService.newEntityQuery();
                this.iEntityQuery.setAuthorityOperations(AuthorityType.None);
                this.iEntityQuery.setEntityView(entityViewDefine);
                this.iEntityQuery.setMasterKeys(new DimensionValueSet());
                this.iEntityQuery.queryStopModel(0);
                if (StringUtils.isNotEmpty((String)period) && StringUtils.isNotEmpty((String)periodView)) {
                    this.executorContext = new ExecutorContext(this.dataDefinitionController);
                    this.executorContext.setPeriodView(periodView);
                }
            }
        }
        if (this.iEntityQuery == null) {
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.dimNotExist", dimName));
        }
    }

    public String[] getValues(String key) throws Exception {
        String[] values;
        if (StringUtils.isEmpty((String)key)) {
            return null;
        }
        if (this.buffer.containsKey(key)) {
            values = this.buffer.get(key);
        } else {
            values = this.query(key);
            this.buffer.put(key, values);
        }
        return values;
    }

    private String[] query(String key) throws Exception {
        this.iEntityQuery.getMasterKeys().clearAll();
        if (StringUtils.isNotEmpty((String)this.period)) {
            this.iEntityQuery.getMasterKeys().setValue("DATATIME", (Object)this.period);
        }
        this.iEntityQuery.getMasterKeys().setValue(this.filterDimName, (Object)key);
        IEntityTable entityTable = this.iEntityQuery.executeReader((IContext)this.executorContext);
        List rows = entityTable.getAllRows();
        if (rows != null && rows.size() > 0) {
            String[] values = new String[this.fieldNames.length];
            for (int k = 0; k < rows.size(); ++k) {
                IEntityRow row = (IEntityRow)rows.get(k);
                for (int i = 0; i < values.length; ++i) {
                    String value = null;
                    value = i == this.codeField ? row.getCode() : row.getAsString(this.fieldNames[i]);
                    values[i] = k == 0 ? value : StringUtils.clean((String)values[i]) + ";" + StringUtils.clean((String)value);
                }
            }
            return values;
        }
        return null;
    }
}

