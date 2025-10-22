/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeTemplateDao
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationFormImpl
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.util.OrderGenerator
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationSchemeTemplateDao;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationFormImpl;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeTemplateService
implements IEstimationSchemeTemplateService {
    @Resource
    private IEstimationSchemeTemplateDao templateDao;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public IEstimationSchemeTemplate findSchemeTemplateByKey(String estimationSchemeKey) {
        EstimationSchemeBase template = this.templateDao.findEstimationScheme(estimationSchemeKey);
        if (template != null) {
            return this.getImpl(template);
        }
        return null;
    }

    @Override
    public IEstimationSchemeTemplate findSchemeTemplateByFormScheme(String formSchemeId) {
        EstimationSchemeBase template = this.templateDao.findTemplate("manager_created_scheme", formSchemeId);
        if (template != null) {
            return this.getImpl(template);
        }
        return null;
    }

    public IEstimationSchemeTemplateImpl getImpl(EstimationSchemeBase template) {
        IEstimationSchemeTemplateImpl impl = new IEstimationSchemeTemplateImpl();
        impl.setKey(template.getKey());
        impl.setCode(template.getCode());
        impl.setTitle(template.getTitle());
        impl.setTaskDefine(this.runTimeViewController.queryTaskDefine(template.getTaskId()));
        impl.setFormSchemeDefine(this.runTimeViewController.getFormScheme(template.getFormSchemeId()));
        impl.setEstimationForms(this.getEstimationForms(template.getFormDefines()));
        impl.setAccessFormulaSchemes(this.getFormulaSchemes(template.getAccessFormulaSchemes()));
        impl.setCalcFormulaSchemes(this.getFormulaSchemes(template.getCalcFormulaSchemes()));
        return impl;
    }

    @Override
    public List<IEstimationSchemeTemplate> findAllSchemeTemplate() {
        List allTemplate = this.templateDao.findAllTemplate("manager_created_scheme");
        return allTemplate.stream().map(this::getImpl).collect(Collectors.toList());
    }

    private List<IEstimationForm> getEstimationForms(List<EstimationForm> forms) {
        HashMap formTypeMap = new HashMap();
        forms.forEach(e -> formTypeMap.put(e.getFormId(), EstimationFormType.toType((String)e.getFormType())));
        List formDefines = this.runTimeViewController.queryFormsById(forms.stream().map(EstimationForm::getFormId).collect(Collectors.toList()));
        return formDefines.stream().map(formDefine -> {
            IEstimationFormImpl impl = new IEstimationFormImpl();
            impl.setFormDefine(formDefine);
            impl.setFormType((EstimationFormType)formTypeMap.get(formDefine.getKey()));
            return impl;
        }).collect(Collectors.toList());
    }

    private List<FormulaSchemeDefine> getFormulaSchemes(List<String> formulaSchemeIds) {
        if (formulaSchemeIds != null && !formulaSchemeIds.isEmpty()) {
            return formulaSchemeIds.stream().map(formulaSchemeKey -> this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey)).collect(Collectors.toList());
        }
        return new ArrayList<FormulaSchemeDefine>();
    }

    @Override
    public String saveEstimationSchemeTemplate(IEstimationSchemeTemplate scheme) {
        EstimationSchemeBase template = this.templateDao.findTemplate("manager_created_scheme", scheme.getFormSchemeDefine().getKey());
        if (template == null) {
            template = new EstimationSchemeBase();
            template.setKey(UUID.randomUUID().toString());
            template.setTaskId(scheme.getTaskDefine().getKey());
            template.setFormSchemeId(scheme.getFormSchemeDefine().getKey());
            template.setCreator("manager_created_scheme");
            template.setOrder(OrderGenerator.newOrder());
            this.setCommonAttr(scheme, template);
            return this.templateDao.insertEstimationScheme(template) + "";
        }
        this.setCommonAttr(scheme, template);
        return this.templateDao.updateEstimationScheme(template) + "";
    }

    @Override
    public String updateEstimationSchemeForms(String estimationSchemeKey, List<String> formIds) {
        EstimationSchemeBase userTemplate = this.templateDao.findEstimationScheme(estimationSchemeKey);
        if (userTemplate != null) {
            EstimationSchemeBase template = this.templateDao.findTemplate("manager_created_scheme", userTemplate.getFormSchemeId());
            List newFormList = template.getFormDefines().stream().filter(form -> formIds.contains(form.getFormId())).collect(Collectors.toList());
            userTemplate.setFormDefines(newFormList);
            return this.templateDao.updateEstimationScheme(userTemplate) + "";
        }
        return null;
    }

    @Override
    public boolean hasSchemeCode(String formSchemeId, String schemeCode) {
        return false;
    }

    private void setCommonAttr(IEstimationSchemeTemplate scheme, EstimationSchemeBase template) {
        template.setCode(scheme.getCode());
        template.setTitle(scheme.getTitle());
        template.setFormDefines(this.getFormDefines(scheme.getEstimationForms()));
        template.setAccessFormulaSchemes(scheme.getAccessFormulaSchemes().stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        template.setCalcFormulaSchemes(scheme.getCalcFormulaSchemes().stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        template.setUpdateTime(new Date());
    }

    private List<EstimationForm> getFormDefines(List<IEstimationForm> eForms) {
        return eForms.stream().map(eForm -> {
            EstimationForm impl = new EstimationForm();
            impl.setFormId(eForm.getFormDefine().getKey());
            impl.setFormType(eForm.getFormType().value);
            return impl;
        }).collect(Collectors.toList());
    }
}

