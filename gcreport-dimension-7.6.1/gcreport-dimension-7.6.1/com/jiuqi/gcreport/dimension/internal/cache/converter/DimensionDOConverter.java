/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.dimension.domain.DimensionDO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.dimension.internal.cache.converter;

import com.jiuqi.budget.param.dimension.domain.DimensionDO;
import com.jiuqi.gcreport.dimension.internal.cache.converter.BaseDimensionConverter;
import com.jiuqi.gcreport.dimension.internal.cache.converter.DimensionConverter;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class DimensionDOConverter
extends BaseDimensionConverter
implements DimensionConverter<DimensionDO> {
    @Override
    public DimensionEO convert(DimensionDO source, TableModelDefine tableModelDefineByCode) {
        DimensionEO dimensionEO = this.createBaseDimensionEO(source, tableModelDefineByCode);
        dimensionEO.setDimensionType("dims");
        dimensionEO.setFieldSize(60);
        dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue());
        dimensionEO.setGroupDimFlag(1);
        return dimensionEO;
    }
}

