/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.proxy.EntityMetaServiceProxy
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.np.definition.impl.proxy;

import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.proxy.EntityMetaServiceProxy;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EntityMetaServiceProxyImpl
implements EntityMetaServiceProxy {
    @Autowired
    private IEntityMetaService entityMetaService;

    public String getExpression(IDimensionFilter dimensionFilter) {
        if (Objects.isNull(dimensionFilter)) {
            return null;
        }
        if (!StringUtils.hasLength(dimensionFilter.getValue())) {
            return null;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dimensionFilter.getEntityId());
        if (entityModel == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        if (dimensionFilter.getListType() == DimensionFilterListType.BLACK_LIST) {
            builder.append(" NOT(").append(bizKeyField.getCode()).append(" IN {");
        } else {
            builder.append(bizKeyField.getCode());
            builder.append(" IN {");
        }
        List values = dimensionFilter.getList();
        for (int i = 0; i < values.size(); ++i) {
            builder.append("'").append((String)values.get(i)).append("'");
            if (i >= values.size() - 1) continue;
            builder.append(",");
        }
        builder.append("}");
        if (dimensionFilter.getListType() == DimensionFilterListType.BLACK_LIST) {
            builder.append(")");
        }
        return builder.toString();
    }
}

