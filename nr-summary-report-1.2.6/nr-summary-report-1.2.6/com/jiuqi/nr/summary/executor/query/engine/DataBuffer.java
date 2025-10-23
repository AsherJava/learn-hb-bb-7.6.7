/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.summary.executor.query.engine;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

class DataBuffer {
    private Map<String, String[]> buffer = new HashMap<String, String[]>();
    private IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    private IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
    private IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
    private String dimName;
    private String filterDimName;
    private Object dataTime;
    private String[] fieldNames;
    private int codeField = -1;
    private IEntityQuery iEntityQuery = null;

    public DataBuffer(String dimName, String[] filedNames, Object dataTime) throws Exception {
        IEntityDefine entityDefine;
        this.dimName = dimName;
        this.dataTime = dataTime;
        this.fieldNames = filedNames;
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
                this.iEntityQuery.setAuthorityOperations(AuthorityType.Read);
                this.iEntityQuery.setEntityView(entityViewDefine);
                this.iEntityQuery.setMasterKeys(new DimensionValueSet());
            }
        }
        if (this.iEntityQuery == null) {
            throw new Exception("\u7ef4\u5ea6\u3010" + dimName + "\u3011\u4e0d\u5b58\u5728");
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
        IEntityTable entityTable;
        List rows;
        this.iEntityQuery.getMasterKeys().clearAll();
        this.iEntityQuery.getMasterKeys().setValue(this.filterDimName, (Object)key);
        if (!ObjectUtils.isEmpty(this.dataTime)) {
            this.iEntityQuery.getMasterKeys().setValue("DATATIME", this.dataTime);
        }
        if ((rows = (entityTable = this.iEntityQuery.executeReader(null)).getAllRows()) != null && rows.size() > 0) {
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

