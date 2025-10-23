/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.fmdm.validator.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.option.OrgCodeEditOption;
import com.jiuqi.nr.fmdm.validator.DataUpdateValidator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrgCodeValidator
implements DataUpdateValidator {
    @Autowired
    private OrgCodeEditOption orgCodeEditOption;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    @Override
    public List<FMDMCheckFailNodeInfo> check(FMDMDataDTO fmdmDataDTO) {
        IEntityRow iEntityRow;
        String code;
        if (this.orgCodeEditOption.isAllow()) {
            return null;
        }
        FormSchemeDefine formSchemeDefine = this.runTimeController.getFormScheme(fmdmDataDTO.getFormSchemeKey());
        String entityId = Utils.getEntityId(formSchemeDefine);
        if (!StringUtils.hasText(entityId)) {
            entityId = formSchemeDefine.getDw();
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute codeField = entityModel.getCodeField();
        Object modifyValue = fmdmDataDTO.getEntityModify().get(codeField.getCode());
        if (modifyValue == null) {
            return null;
        }
        DimensionValueSet dimensionValueSet = fmdmDataDTO.getDimensionCombination().toDimensionValueSet();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setMasterKeys(dimensionValueSet);
        IEntityTable iEntityTable = null;
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setPeriodView(formSchemeDefine.getDateTime());
        try {
            iEntityTable = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        Object entityKey = dimensionValueSet.getValue(dimensionName);
        if (entityKey instanceof String && iEntityTable != null && !(code = (iEntityRow = iEntityTable.quickFindByEntityKey((String)entityKey)).getCode()).equals(modifyValue.toString())) {
            ArrayList<FMDMCheckFailNodeInfo> fails = new ArrayList<FMDMCheckFailNodeInfo>();
            FMDMCheckFailNodeInfo failNodeInfo = new FMDMCheckFailNodeInfo();
            failNodeInfo.setFieldCode(codeField.getCode());
            failNodeInfo.setFieldTitle(codeField.getTitle());
            CheckNodeInfo checkNode = new CheckNodeInfo(-1, "\u7981\u6b62\u4fee\u6539\u673a\u6784\u7f16\u7801");
            failNodeInfo.addNode(checkNode);
            fails.add(failNodeInfo);
            return fails;
        }
        return null;
    }
}

