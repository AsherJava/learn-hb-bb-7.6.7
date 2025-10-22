/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataAuthRuleEnableService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 */
package com.jiuqi.nr.definition.auth.basedata;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataAuthRuleEnableService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseDataAuthRuleEnableServiceImpl
implements IBaseDataAuthRuleEnableService {
    private static final String CACHE_NAME = "BASE_DATA_AUTH_RULE";
    @Autowired
    protected IRuntimeDataSchemeService dataSchemeService;

    public boolean isEnable(BaseDataDTO param) {
        ContextExtension extension = NpContextHolder.getContext().getExtension(CACHE_NAME);
        Object value = extension.get(param.getTableName());
        if (value == null) {
            value = this.isDim(param.getTableName());
            extension.put(param.getTableName(), (Serializable)((Integer)value));
        }
        return (Integer)value == 1;
    }

    private Integer isDim(String tableName) {
        List allDataScheme = this.dataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.UNIT);
            for (DataDimension dataDimension : dataSchemeDimension) {
                if (!dataDimension.getDimKey().equals(this.getEntityId(tableName))) continue;
                return 1;
            }
        }
        return 0;
    }

    private String getEntityId(String tableName) {
        return BaseDataAdapterUtil.getEntityIdByBaseDataCode((String)tableName);
    }
}

