/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.measurement.domain.MeasurementDO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.dimension.internal.cache.converter;

import com.jiuqi.budget.param.measurement.domain.MeasurementDO;
import com.jiuqi.gcreport.dimension.internal.cache.converter.BaseDimensionConverter;
import com.jiuqi.gcreport.dimension.internal.cache.converter.DimensionConverter;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class MeasurementDOConverter
extends BaseDimensionConverter
implements DimensionConverter<MeasurementDO> {
    @Override
    public DimensionEO convert(MeasurementDO source, TableModelDefine tableModelDefine) {
        DimensionEO dimensionEO = this.createBaseDimensionEO(source, tableModelDefine);
        dimensionEO.setDimensionType("meas");
        dimensionEO.setFieldSize(source.getDataLength());
        dimensionEO.setFieldDecimal(source.getDataPrecision());
        if (source.getDataType() != null) {
            switch (source.getDataType()) {
                case NUM: 
                case MONEY: {
                    dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_DECIMAL.getNrValue());
                    dimensionEO.setGroupDimFlag(0);
                    break;
                }
                case STR: {
                    dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue());
                    dimensionEO.setGroupDimFlag(1);
                    break;
                }
                case DATETIME: {
                    dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue());
                    dimensionEO.setGroupDimFlag(1);
                    break;
                }
                case DATE: {
                    dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue());
                    dimensionEO.setGroupDimFlag(1);
                    break;
                }
                case BOOLEAN: {
                    dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue());
                    dimensionEO.setGroupDimFlag(1);
                    break;
                }
                default: {
                    dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue());
                    dimensionEO.setGroupDimFlag(0);
                    break;
                }
            }
        } else {
            dimensionEO.setFieldType(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue());
            dimensionEO.setGroupDimFlag(0);
        }
        return dimensionEO;
    }
}

