/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.designer.web.rest.external;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskCacheLoadController {
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    IRunTimeViewController viewController;
    @Autowired
    IFormulaRunTimeController formulaController;
    @Autowired
    IEntityDataService entityDataService;
    @Autowired
    private SystemIdentityService systemIdentityService;

    @GetMapping(value={"/nr/param/cache/loadtaskcache/{taskKey}", "/nr/param/cache/loadtaskcache/{taskKey}/{period}"})
    public String loadCache(@PathVariable String taskKey, @PathVariable(required=false) String period) throws Exception {
        if (!this.systemIdentityService.isAdmin()) {
            return "No Authority.";
        }
        TaskDefine task = this.viewController.queryTaskDefine(taskKey);
        if (task == null) {
            return "task not found.";
        }
        StringBuilder errorInfo = new StringBuilder();
        HashSet<String> loadedTableKey = new HashSet<String>();
        List formschemes = this.viewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine fs : formschemes) {
            try {
                List formulaSchemes = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(fs.getKey());
                List fgs = this.viewController.getAllFormGroupsInFormScheme(fs.getKey());
                for (FormGroupDefine fg : fgs) {
                    List forms = this.viewController.getAllFormsInGroup(fg.getKey());
                    for (FormDefine form : forms) {
                        try {
                            List drs = this.viewController.getAllRegionsInForm(form.getKey());
                            for (DataRegionDefine dr : drs) {
                                List dls = this.viewController.getAllLinksInRegion(dr.getKey());
                                for (DataLinkDefine dl : dls) {
                                    FieldDefine field;
                                    if (dl.getLinkExpression() == null || !loadedTableKey.add((field = this.dataDefinitionController.queryFieldDefine(dl.getLinkExpression())).getOwnerTableKey())) continue;
                                    this.dataDefinitionController.queryTableDefine(field.getOwnerTableKey());
                                }
                            }
                            for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                                this.formulaController.getCheckFormulasInForm(formulaScheme.getKey(), form.getKey());
                                this.formulaController.getCalculateFormulasInForm(formulaScheme.getKey(), form.getKey());
                                this.formulaController.queryPublicFormulaDefineByScheme(formulaScheme.getKey(), form.getKey());
                                this.formulaController.getParsedExpressionByForm(formulaScheme.getKey(), form.getKey(), DataEngineConsts.FormulaType.CHECK);
                                this.formulaController.getParsedExpressionByForm(formulaScheme.getKey(), form.getKey(), DataEngineConsts.FormulaType.CALCULATE);
                                this.formulaController.getCalcCellDataLinks(formulaScheme.getKey(), form.getKey());
                            }
                        }
                        catch (Exception e) {
                            errorInfo.append("form load failed. " + e.getMessage() + "/n");
                            errorInfo.append("\tform key: " + form.getKey() + "/n");
                            errorInfo.append("\tform code: " + form.getFormCode() + "/n");
                            errorInfo.append("\tform title: " + form.getTitle() + "/n");
                        }
                    }
                }
                for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                    this.formulaController.getFormConditionsByFormScheme(formulaScheme.getKey());
                    this.formulaController.getParsedExpressionBetweenTable(formulaScheme.getKey(), DataEngineConsts.FormulaType.CHECK);
                    this.formulaController.getParsedExpressionBetweenTable(formulaScheme.getKey(), DataEngineConsts.FormulaType.CALCULATE);
                }
            }
            catch (Exception e) {
                errorInfo.append("form scheme load failed. " + e.getMessage() + "/n");
                errorInfo.append("\tscheme key: " + fs.getKey() + "/n");
                errorInfo.append("\tscheme code: " + fs.getFormSchemeCode() + "/n");
                errorInfo.append("\tscheme title: " + fs.getTitle() + "/n");
            }
        }
        try {
            IEntityQuery eq = this.entityDataService.newEntityQuery();
            eq.setEntityView(this.viewController.getViewByTaskDefineKey(taskKey));
            eq.setAuthorityOperations(AuthorityType.None);
            DimensionValueSet masterKeys = new DimensionValueSet();
            masterKeys.setValue("DATATIME", (Object)period);
            eq.setMasterKeys(masterKeys);
            IEntityTable table = eq.executeReader((IContext)new ExecutorContext(this.dataDefinitionController));
            table.getRootRows();
        }
        catch (Exception e) {
            e.printStackTrace();
            errorInfo.append("entity table load failed. " + e.getMessage() + "/n");
        }
        return errorInfo.length() > 0 ? errorInfo.toString() : "success";
    }
}

