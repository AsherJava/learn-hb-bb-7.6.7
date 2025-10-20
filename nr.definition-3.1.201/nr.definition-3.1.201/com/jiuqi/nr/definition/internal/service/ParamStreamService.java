/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.FormGroupListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormGroupStream;
import com.jiuqi.nr.definition.internal.stream.param.FormListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.FormStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.FormulaStream;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.PrintSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.RegionSettingStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskGroupListStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskGroupStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskListStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamStreamService {
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private IParamLanguageController languageController;

    public TaskStream getTaskStream(TaskDefine taskDefine) {
        return new TaskStream(taskDefine, this.authorityProvider, this.languageController);
    }

    public FormSchemeStream getFormSchemeStream(FormSchemeDefine formSchemeDefine) {
        return new FormSchemeStream(formSchemeDefine, this.authorityProvider, this.languageController);
    }

    public FormulaSchemeStream getFormulaSchemeStream(FormulaSchemeDefine formulaSchemeDefine) {
        return new FormulaSchemeStream(formulaSchemeDefine, this.authorityProvider, this.languageController);
    }

    public PrintSchemeStream getPrintSchemeStream(PrintTemplateSchemeDefine printTemplateSchemeDefine) {
        return new PrintSchemeStream(printTemplateSchemeDefine, this.authorityProvider, this.languageController);
    }

    public FormGroupStream getFormGroupStream(FormGroupDefine formGroupDefine) {
        return new FormGroupStream(formGroupDefine, this.authorityProvider, this.languageController);
    }

    public FormStream getFormStream(FormDefine formDefine) {
        return new FormStream(formDefine, this.authorityProvider, this.languageController);
    }

    public RegionSettingStream getRegionSettingStream(RegionSettingDefine regionSettingDefine) {
        return new RegionSettingStream(regionSettingDefine, this.authorityProvider, this.languageController);
    }

    public FormulaStream getFormulaStream(FormulaDefine formulaDefine) {
        return new FormulaStream(formulaDefine, this.authorityProvider, this.languageController);
    }

    public TaskGroupStream getTaskGroupStream(TaskGroupDefine taskGroupDefine) {
        return new TaskGroupStream(taskGroupDefine, this.authorityProvider, this.languageController);
    }

    public TaskOrgLinkStream getTaskOrgLinkStream(TaskOrgLinkDefine define) {
        return new TaskOrgLinkStream(define, this.authorityProvider, this.languageController);
    }

    public TaskListStream getTaskListStream(List<TaskDefine> taskListStream) {
        return new TaskListStream(taskListStream, this.authorityProvider, this.languageController);
    }

    public FormSchemeListStream getFormSchemeListStream(List<FormSchemeDefine> formSchemeDefine) {
        return new FormSchemeListStream(formSchemeDefine, this.authorityProvider, this.languageController);
    }

    public FormulaSchemeListStream getFormulaSchemeListStream(List<FormulaSchemeDefine> formulaSchemeDefine) {
        return new FormulaSchemeListStream(formulaSchemeDefine, this.authorityProvider, this.languageController);
    }

    public PrintSchemeListStream getPrintSchemeListStream(List<PrintTemplateSchemeDefine> printTemplateSchemeDefine) {
        return new PrintSchemeListStream(printTemplateSchemeDefine, this.authorityProvider, this.languageController);
    }

    public FormGroupListStream getFormGroupListStream(List<FormGroupDefine> formGroupDefine) {
        return new FormGroupListStream(formGroupDefine, this.authorityProvider, this.languageController);
    }

    public FormListStream getFormListStream(List<FormDefine> formDefine) {
        return new FormListStream(formDefine, this.authorityProvider, this.languageController);
    }

    public FormulaListStream getFormulaListStream(List<FormulaDefine> formulaDefine) {
        return new FormulaListStream(formulaDefine, this.authorityProvider, this.languageController);
    }

    public TaskGroupListStream getTaskGroupListStream(List<TaskGroupDefine> taskGroupDefine) {
        return new TaskGroupListStream(taskGroupDefine, this.authorityProvider, this.languageController);
    }

    public TaskOrgLinkListStream getTaskOrgLinkListStream(List<TaskOrgLinkDefine> defines) {
        return new TaskOrgLinkListStream(defines, this.authorityProvider, this.languageController);
    }
}

