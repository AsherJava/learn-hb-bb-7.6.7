/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.internal.model.impl.EntityReferImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class EntityDataQueryAssistImpl
implements IEntityDataQueryAssist {
    private final IRuntimeDataSchemeService dataSchemeService;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final IEntityMetaService entityMetaService;

    public EntityDataQueryAssistImpl(IRuntimeDataSchemeService dataSchemeService, IEntityViewRunTimeController entityViewRunTimeController, IEntityMetaService entityMetaService) {
        this.dataSchemeService = dataSchemeService;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.entityMetaService = entityMetaService;
    }

    public ReferRelation buildReferRelation(String dataSchemeKey, String dimKey) {
        if (this.isLegal(dimKey)) {
            return null;
        }
        List dimensions = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        if (CollectionUtils.isEmpty(dimensions)) {
            return null;
        }
        DataDimension unit = this.getUnit(dimensions);
        DataDimension dimension = this.getDimension(dimensions, dimKey);
        if (Objects.isNull(unit) || Objects.isNull(dimension)) {
            return null;
        }
        String dimAttribute = dimension.getDimAttribute();
        if (StringUtils.hasLength(dimAttribute)) {
            return this.createReferRelation(unit, dimension);
        }
        List entityRefer = this.entityMetaService.getEntityRefer(unit.getDimKey());
        Optional<IEntityRefer> findRefer = entityRefer.stream().filter(e -> e.getReferEntityId().equals(dimKey)).findFirst();
        if (findRefer.isPresent()) {
            IEntityRefer refer = findRefer.get();
            ReferRelation referRelation = new ReferRelation();
            referRelation.setRefer(refer);
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(unit.getDimKey());
            referRelation.setViewDefine(entityViewDefine);
            return referRelation;
        }
        return null;
    }

    private DataDimension getDimension(List<DataDimension> dimensions, String dimKey) {
        return dimensions.stream().filter(dataDimension -> DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType())).filter(x -> dimKey.equals(x.getDimKey())).findFirst().orElse(null);
    }

    private DataDimension getUnit(List<DataDimension> dimensions) {
        return dimensions.stream().filter(dataDimension -> DimensionType.UNIT.equals((Object)dataDimension.getDimensionType())).findFirst().orElse(null);
    }

    private ReferRelation createReferRelation(DataDimension unit, DataDimension dimension) {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dimension.getDimKey());
        if (Objects.nonNull(entityModel)) {
            ReferRelation referRelation = new ReferRelation();
            IEntityAttribute bizKeyField = entityModel.getBizKeyField();
            EntityReferImpl entityRefer = new EntityReferImpl();
            entityRefer.setOwnField(dimension.getDimAttribute());
            entityRefer.setOwnEntityId(unit.getDimKey());
            entityRefer.setReferEntityId(dimension.getDimKey());
            entityRefer.setReferEntityField(bizKeyField.getCode());
            referRelation.setRefer((IEntityRefer)entityRefer);
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(unit.getDimKey());
            referRelation.setViewDefine(entityViewDefine);
            return referRelation;
        }
        return null;
    }

    private boolean isLegal(String dimKey) {
        if (Objects.isNull(dimKey)) {
            return false;
        }
        return "ADJUST".equals(dimKey);
    }
}

