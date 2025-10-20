/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.dimension.domain.DimensionDO
 *  com.jiuqi.budget.param.measurement.domain.MeasurementDO
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.dimension.internal.cache.converter;

import com.jiuqi.budget.param.dimension.domain.DimensionDO;
import com.jiuqi.budget.param.measurement.domain.MeasurementDO;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.time.LocalDateTime;
import java.util.Date;

public abstract class BaseDimensionConverter {
    protected <T> DimensionEO createBaseDimensionEO(T source, TableModelDefine tableModelDefine) {
        DimensionEO dimensionEO = new DimensionEO();
        dimensionEO.setPublishedFlag(1);
        dimensionEO.setRecver(0L);
        dimensionEO.setConvertByOpposite(0);
        if (tableModelDefine != null && !StringUtils.isEmpty((String)tableModelDefine.getCode())) {
            dimensionEO.setReferTable(StringUtils.toViewString((Object)tableModelDefine.getID()));
            dimensionEO.setReferField(tableModelDefine.getCode());
        }
        if (source instanceof DimensionDO) {
            this.setBasicProperties(dimensionEO, (DimensionDO)source);
        } else if (source instanceof MeasurementDO) {
            this.setBasicProperties(dimensionEO, (MeasurementDO)source);
        }
        return dimensionEO;
    }

    private void setBasicProperties(DimensionEO dimensionEO, DimensionDO source) {
        dimensionEO.setTitle(source.getName());
        dimensionEO.setCode(source.getCode());
        dimensionEO.setId(source.getId());
        dimensionEO.setCreator(source.getCreator());
        dimensionEO.setDescription(source.getRemark());
        dimensionEO.setSortOrder(source.getOrderNum().intValue());
        this.setTimeProperties(dimensionEO, source.getCreateTime(), source.getModifyTime());
    }

    private void setBasicProperties(DimensionEO dimensionEO, MeasurementDO source) {
        dimensionEO.setTitle(source.getName());
        dimensionEO.setCode(source.getCode());
        dimensionEO.setId(source.getId());
        dimensionEO.setCreator(source.getCreator());
        dimensionEO.setDescription(source.getRemark());
        dimensionEO.setSortOrder(source.getOrderNum().intValue());
        this.setTimeProperties(dimensionEO, source.getCreateTime(), source.getModifyTime());
    }

    private void setTimeProperties(DimensionEO dimensionEO, LocalDateTime createTime, LocalDateTime modifyTime) {
        dimensionEO.setCreateTime(DateUtils.convertLDTToDate((LocalDateTime)createTime));
        dimensionEO.setUpdateTime(modifyTime == null ? new Date() : DateUtils.convertLDTToDate((LocalDateTime)createTime));
    }
}

