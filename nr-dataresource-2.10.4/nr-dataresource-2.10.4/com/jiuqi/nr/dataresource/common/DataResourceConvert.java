/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.dataresource.common;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceLink;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dto.DimAttributeDTO;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.DataResourceLinkDO;
import com.jiuqi.nr.dataresource.entity.DimAttributeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class DataResourceConvert {
    public static ResourceTreeGroup iDg2Do(DataResourceDefineGroup defineGroup) {
        ResourceTreeGroup group = new ResourceTreeGroup();
        BeanUtils.copyProperties(defineGroup, group);
        return group;
    }

    public static ResourceTreeDO iDd2Do(DataResourceDefine entity) {
        ResourceTreeDO treeDO = new ResourceTreeDO();
        BeanUtils.copyProperties(entity, treeDO);
        return treeDO;
    }

    public static DataResourceDO iDr2Do(DataResource entity) {
        DataResourceDO resourceDO = new DataResourceDO();
        BeanUtils.copyProperties(entity, resourceDO);
        return resourceDO;
    }

    public static DataResourceLinkDO iDl2do(DataResourceLink r) {
        DataResourceLinkDO resourceDO = new DataResourceLinkDO();
        BeanUtils.copyProperties(r, resourceDO);
        return resourceDO;
    }

    public static DimAttributeDO iDm2Do(DimAttribute dimAttribute) {
        DimAttributeDO resourceDO = new DimAttributeDO();
        BeanUtils.copyProperties(dimAttribute, resourceDO);
        return resourceDO;
    }

    public static <COL extends ColumnModelDefine> List<DimAttribute> build(List<COL> showAttributes, List<DimAttributeDO> attrs, List<DataField> fields, String dimKey, String defineKey) {
        DimAttributeDTO dimAttribute;
        DimAttributeDO dimAttributeDO;
        ArrayList<DimAttribute> dimAttributes = new ArrayList<DimAttribute>();
        HashMap<String, DimAttributeDO> map = new HashMap<String, DimAttributeDO>(showAttributes.size());
        if (attrs != null) {
            for (DimAttributeDO attr : attrs) {
                map.put(attr.getKey(), attr);
            }
        }
        if (!CollectionUtils.isEmpty(showAttributes)) {
            showAttributes.sort((o1, o2) -> {
                if (o1.getOrder() == o2.getOrder()) {
                    return 0;
                }
                return o1.getOrder() < o2.getOrder() ? -1 : 1;
            });
            for (ColumnModelDefine attribute : showAttributes) {
                dimAttributeDO = (DimAttributeDO)map.get(attribute.getID());
                dimAttribute = new DimAttributeDTO();
                dimAttribute.setKey(attribute.getID());
                dimAttribute.setCode(attribute.getCode());
                dimAttribute.setTitle(attribute.getTitle());
                dimAttribute.setDimKey(dimKey);
                dimAttribute.setResourceDefineKey(defineKey);
                if (dimAttributeDO != null) {
                    dimAttribute.setHidden(dimAttributeDO.isHidden());
                } else {
                    dimAttribute.setHidden(false);
                }
                dimAttributes.add(dimAttribute);
            }
        }
        if (fields != null) {
            for (DataField field : fields) {
                if (field.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
                dimAttributeDO = (DimAttributeDO)map.get(field.getKey());
                dimAttribute = new DimAttributeDTO();
                dimAttribute.setKey(field.getKey());
                dimAttribute.setCode(field.getCode());
                dimAttribute.setTitle(field.getTitle());
                dimAttribute.setDimKey(dimKey);
                dimAttribute.setResourceDefineKey(defineKey);
                if (dimAttributeDO != null) {
                    dimAttribute.setHidden(dimAttributeDO.isHidden());
                } else {
                    dimAttribute.setHidden(false);
                }
                dimAttributes.add(dimAttribute);
            }
        }
        return dimAttributes;
    }

    public static String parseDataResourceSource(String source) {
        Assert.notNull((Object)source, "source must not be null.");
        int splitPosition = source.lastIndexOf("_");
        if (splitPosition < 0) {
            throw new UnsupportedOperationException("unrecognized src id: " + source);
        }
        if (splitPosition + 1 >= source.length()) {
            throw new UnsupportedOperationException("unrecognized src id: " + source);
        }
        return source.substring(splitPosition + 1);
    }

    public static NodeType resourceKind2NodeType(DataResourceKind kind) {
        if (kind == null) {
            return null;
        }
        switch (kind) {
            case RESOURCE_GROUP: {
                return NodeType.RESOURCE_GROUP;
            }
            case DIM_GROUP: {
                return NodeType.DIM_GROUP;
            }
            case DIM_FMDM_GROUP: {
                return NodeType.DIM_FMDM_GROUP;
            }
            case TABLE_DIM_GROUP: {
                return NodeType.TABLE_DIM_GROUP;
            }
            case MD_INFO: {
                return NodeType.MD_INFO;
            }
        }
        throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
    }
}

