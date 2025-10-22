/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldRelationFactory {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private DataModelService dataModelService;

    @Deprecated
    public FieldRelation getFieldRelation() {
        FieldRelation fieldRelation = new FieldRelation();
        fieldRelation.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
        fieldRelation.setDataAccessProvider(this.dataAccessProvider);
        fieldRelation.setRuntimeController(this.runtimeController);
        fieldRelation.setEntityViewRunTimeController(this.entityViewRunTimeController);
        fieldRelation.setDataDefinitionController(this.dataDefinitionController);
        fieldRelation.setRunTimeViewController(this.runTimeViewController);
        fieldRelation.setDataModelService(this.dataModelService);
        return fieldRelation;
    }

    public FieldRelation getFieldRelation(Iterator<String> fieldKeys) {
        ArrayList<String> fieldKeyList = new ArrayList<String>();
        while (fieldKeys.hasNext()) {
            String fieldKey = fieldKeys.next();
            fieldKeyList.add(fieldKey);
        }
        return this.getFieldRelation(fieldKeyList);
    }

    public FieldRelation getFieldRelation(List<String> fieldKeys) {
        FieldRelation fieldRelation = new FieldRelation();
        fieldRelation.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
        fieldRelation.setDataAccessProvider(this.dataAccessProvider);
        fieldRelation.setRuntimeController(this.runtimeController);
        fieldRelation.setEntityViewRunTimeController(this.entityViewRunTimeController);
        fieldRelation.setDataDefinitionController(this.dataDefinitionController);
        fieldRelation.setRunTimeViewController(this.runTimeViewController);
        fieldRelation.setDataModelService(this.dataModelService);
        fieldRelation.initFieldRelation(fieldKeys);
        return fieldRelation;
    }
}

