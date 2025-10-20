/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.persistence.EntityNotFoundException
 */
package com.jiuqi.gcreport.dimension.internal.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityNotFoundException;
import org.springframework.util.ObjectUtils;

public class DimensionManagerUtil {
    public static final String VARCHAR_DEFAULT_VALUE = "'#'";
    public static final String NUMERIC_DEFAULT_VALUE = "0.00";
    public static final String DATE_DEFAULT_VALUE = "1970-01-01";

    public static List<DefinitionFieldV> getExtendColumn(String effectTable) {
        PropertyDescriptor[] fields;
        if (StringUtils.isEmpty((String)effectTable)) {
            return Collections.emptyList();
        }
        ArrayList fieldVList = CollectionUtils.newArrayList();
        List<DimensionVO> dimensionVOS = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsVOByTableName(effectTable);
        if (CollectionUtils.isEmpty(dimensionVOS)) {
            return Collections.emptyList();
        }
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity entity = entityTableCollector.getEntityByName(effectTable);
        DBTable dbTable = entityTableCollector.getDbTableByType(entity.getClass());
        try {
            fields = Introspector.getBeanInfo(entity.getClass(), dbTable.stopSuper()).getPropertyDescriptors();
        }
        catch (IntrospectionException e) {
            throw new EntityNotFoundException("\u8868" + entity.getTableName() + "\u5bf9\u5e94\u7684\u5b9e\u4f53\u7c7b\u201c" + entity.getClass().getName() + "\u201d\u5c5e\u6027\u4fe1\u606f\u52a0\u8f7d\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        Integer orderIndex = fields.length + 1;
        for (DimensionVO dimension : dimensionVOS) {
            if (dimension.getPublishedFlag() != 1) continue;
            DefinitionFieldV fieldV = new DefinitionFieldV();
            fieldV.setKey(UUIDUtils.newUUIDStr());
            fieldV.setNullable(true);
            fieldV.setCode(dimension.getCode());
            fieldV.setTitle(dimension.getTitle());
            fieldV.setEntityFieldName(dimension.getCode());
            fieldV.setFieldName(dimension.getCode());
            fieldV.setFieldValueType(DBColumn.DBType.NVarchar.getType());
            fieldV.setDbType(DBColumn.DBType.NVarchar);
            fieldV.setSize(ObjectUtils.isEmpty(dimension.getFieldSize()) ? 60 : dimension.getFieldSize());
            fieldV.setDescription(dimension.getDescription());
            Integer n = orderIndex;
            Integer n2 = orderIndex = Integer.valueOf(orderIndex + 1);
            fieldV.setOrder((double)n.intValue());
            if (dbTable.extendFieldDefaultVal()) {
                if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue() == dimension.getFieldType().intValue()) {
                    fieldV.setDefaultValue(DATE_DEFAULT_VALUE);
                } else if (dimension.getFieldType().intValue() == FieldTypeUtils.FieldType.FIELD_TYPE_DECIMAL.getNrValue()) {
                    fieldV.setDefaultValue(NUMERIC_DEFAULT_VALUE);
                    if (Objects.nonNull(dimension.getFieldDecimal())) {
                        fieldV.setFractionDigits(dimension.getFieldDecimal().intValue());
                    }
                } else {
                    fieldV.setDefaultValue(VARCHAR_DEFAULT_VALUE);
                }
            }
            if (!StringUtils.isEmpty((String)dimension.getReferField())) {
                DataModelService dataModelService = (DataModelService)ApplicationContextRegister.getBean(DataModelService.class);
                TableModelDefine tableModel = dataModelService.getTableModelDefineByCode(dimension.getReferField());
                fieldV.setReferField(tableModel.getBizKeys());
                fieldV.setReferTable(tableModel.getID());
            }
            fieldVList.add(fieldV);
        }
        return fieldVList;
    }
}

