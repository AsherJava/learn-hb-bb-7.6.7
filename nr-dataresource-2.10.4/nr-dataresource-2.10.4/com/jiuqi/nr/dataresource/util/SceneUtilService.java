/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.dataresource.util;

import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SceneUtilService {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;

    public boolean isAddSceneByScheme(String schemeKey, String dimKey) {
        if (AdjustUtils.isAdjust((String)dimKey).booleanValue()) {
            return false;
        }
        List dims = this.runtimeDataSchemeService.getDataSchemeDimension(schemeKey);
        DataDimension sence = null;
        String unit = null;
        ArrayList<String> scopes = new ArrayList<String>();
        block6: for (DataDimension dim : dims) {
            if (AdjustUtils.isAdjust((String)dim.getDimKey()).booleanValue()) continue;
            switch (dim.getDimensionType()) {
                case UNIT: {
                    if (dimKey.equals(dim.getDimKey())) {
                        return true;
                    }
                    unit = dim.getDimKey();
                    continue block6;
                }
                case UNIT_SCOPE: {
                    if (dimKey.equals(dim.getDimKey())) {
                        return true;
                    }
                    scopes.add(dim.getDimKey());
                    continue block6;
                }
                case PERIOD: {
                    if (!dimKey.equals(dim.getDimKey())) continue block6;
                    return true;
                }
                case DIMENSION: {
                    if (!dimKey.equals(dim.getDimKey())) continue block6;
                    sence = dim;
                    continue block6;
                }
            }
            throw new RuntimeException(dim.getDimensionType().getTitle() + "\uff1a\u7ef4\u5ea6\u7c7b\u578b\u5b9a\u4f4d\u5931\u8d25");
        }
        if (sence == null) {
            return true;
        }
        List<String> units = this.getUnits(unit, scopes);
        for (String u : units) {
            if (this.isAddScene(u, sence)) continue;
            return false;
        }
        return true;
    }

    private List<String> getUnits(String unit, List<String> scopes) {
        ArrayList<String> units = new ArrayList<String>();
        if (CollectionUtils.isEmpty(scopes)) {
            units.add(unit);
        } else {
            units.addAll(scopes);
        }
        return units;
    }

    public boolean isAddScene(String masterDim, DataDimension scene) {
        if (!StringUtils.hasLength(masterDim) || scene == null || !StringUtils.hasLength(scene.getDimAttribute())) {
            return true;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterDim);
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            if (!scene.getDimAttribute().equals(attribute.getCode()) || attribute.isMultival()) continue;
            return false;
        }
        return true;
    }
}

