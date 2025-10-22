/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataValidateException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.setting.IDataValidateProvider
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.validate;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.query.GcDataQueryImpl;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.setting.IDataValidateProvider;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class GcDataValidateProvider
implements IDataValidateProvider {
    private InputDataService inputDataService;
    private GcDataQueryImpl gcDataQuery;
    private IEntityMetaService entityMetaService;

    public GcDataValidateProvider(GcDataQueryImpl gcDataQuery) {
        this.gcDataQuery = gcDataQuery;
        this.inputDataService = (InputDataService)SpringContextUtils.getBean(InputDataService.class);
        this.entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
    }

    public boolean checkRow(IDataRow dataRow) throws DataValidateException {
        if (!this.gcDataQuery.getGcContext().isGcQuery()) {
            return true;
        }
        try {
            Map<String, Object> fieldValues = this.getFieldValuesByDataRow(dataRow);
            this.inputDataService.beforeSave(this.gcDataQuery.getGcContext(), fieldValues);
        }
        catch (Exception e) {
            throw new DataValidateException(e.getMessage());
        }
        return true;
    }

    private Map<String, Object> getFieldValuesByDataRow(IDataRow dataRow) {
        HashMap<String, Object> fieldValueMap = new HashMap<String, Object>();
        DimensionValueSet dimensionValueSet = dataRow.getMasterKeys();
        this.putValue(dimensionValueSet, "MD_ORG", "MDCODE", fieldValueMap);
        this.putValue(dimensionValueSet, "DATATIME", "DATATIME", fieldValueMap);
        Stream.of(this.gcDataQuery.getGcContext().getFormScheme().getDims().split(";")).forEach(f -> {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(f);
            if (dimensionValueSet.hasValue(entityDefine.getDimensionName())) {
                this.putValue(dimensionValueSet, entityDefine.getDimensionName(), entityDefine.getCode(), fieldValueMap);
            }
        });
        IFieldsInfo rowFieldsInfo = dataRow.getFieldsInfo();
        int fieldCount = rowFieldsInfo.getFieldCount();
        for (int index = 0; index < fieldCount; ++index) {
            FieldDefine field = rowFieldsInfo.getFieldDefine(index);
            if (field == null) continue;
            String fieldCode = field.getCode().toUpperCase();
            Object asObject = null;
            try {
                AbstractData value = dataRow.getValue(field);
                if (!value.isNull) {
                    asObject = value.getAsObject();
                }
                if ("BIZKEYORDER".equals(fieldCode)) {
                    asObject = ((DataRowImpl)dataRow).getRecordKey();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            fieldValueMap.put(fieldCode, asObject);
        }
        return fieldValueMap;
    }

    private void putValue(DimensionValueSet dimensionValueSet, String dimensionCode, String fieldName, Map<String, Object> fieldValueMap) {
        Object value = dimensionValueSet.getValue(dimensionCode);
        if (value instanceof String) {
            fieldValueMap.put(fieldName, String.valueOf(value));
        } else if (value instanceof List) {
            fieldValueMap.put(fieldName, String.valueOf(((List)value).get(0)));
        }
    }
}

