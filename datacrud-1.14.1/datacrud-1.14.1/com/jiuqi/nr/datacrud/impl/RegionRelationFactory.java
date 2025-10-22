/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RegionRelationFactory {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private MeasureService measureService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewController entityViewController;
    @Autowired
    private IFMDMAttributeService fmAttributeService;
    @Autowired
    private IRuntimeExpressionService expressionService;
    @Autowired
    private IRunTimeFormulaController runTimeFormulaController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;

    public RegionRelation getRegionRelation(String regionKey) {
        Assert.hasLength(regionKey, "\u533a\u57dfkey \u4e0d\u80fd\u4e3a\u7a7a");
        RegionRelation regionRelation = new RegionRelation(this.runTimeViewController, this.runtimeDataSchemeService, this.measureService, this.dataDefinitionController, this.fmAttributeService, this.entityViewController, this.expressionService, this.runTimeFormulaController, this.entityViewRunTimeController, regionKey);
        regionRelation.setEntityMetaService(this.entityMetaService);
        return regionRelation;
    }

    public RegionRelation getRegionRelationByLinkKey(String linkKey) {
        Assert.hasLength(linkKey, "\u94fe\u63a5key \u4e0d\u80fd\u4e3a\u7a7a");
        String regionKey = this.getRegionKeyByLinkKey(linkKey);
        if (regionKey == null) {
            return null;
        }
        RegionRelation regionRelation = new RegionRelation(this.runTimeViewController, this.runtimeDataSchemeService, this.measureService, this.dataDefinitionController, this.fmAttributeService, this.entityViewController, this.expressionService, this.runTimeFormulaController, this.entityViewRunTimeController, regionKey);
        regionRelation.setEntityMetaService(this.entityMetaService);
        return regionRelation;
    }

    private String getRegionKeyByLinkKey(String linkKey) {
        Assert.hasLength(linkKey, "\u94fe\u63a5key \u4e0d\u80fd\u4e3a\u7a7a");
        DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(linkKey);
        if (dataLinkDefine == null) {
            return null;
        }
        return dataLinkDefine.getRegionKey();
    }
}

