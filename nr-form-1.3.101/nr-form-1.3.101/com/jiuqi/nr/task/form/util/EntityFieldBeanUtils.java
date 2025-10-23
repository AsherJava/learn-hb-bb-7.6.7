/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.task.form.dto.EntityFieldDTO;

public class EntityFieldBeanUtils {
    public static EntityFieldDTO toDTO(IEntityAttribute attribute) {
        EntityFieldDTO entityFieldDTO = new EntityFieldDTO();
        entityFieldDTO.setKey(attribute.getID());
        entityFieldDTO.setCode(attribute.getCode());
        entityFieldDTO.setTitle(attribute.getTitle());
        entityFieldDTO.setCategory(attribute.getCatagory());
        entityFieldDTO.setDataFieldType(attribute.getColumnType().getValue());
        entityFieldDTO.setPrecision(attribute.getPrecision());
        entityFieldDTO.setDecimal(attribute.getDecimal());
        entityFieldDTO.setRefDataEntityKey(attribute.getReferTableID());
        entityFieldDTO.setNullable(attribute.isNullAble());
        return entityFieldDTO;
    }
}

