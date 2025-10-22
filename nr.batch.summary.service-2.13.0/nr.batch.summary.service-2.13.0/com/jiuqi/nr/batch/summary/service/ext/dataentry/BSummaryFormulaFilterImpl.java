/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.logic.facade.extend.IFormulaFilter
 *  com.jiuqi.nr.data.logic.facade.param.input.FormulaFilterEnv
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.dataentry;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.logic.facade.extend.IFormulaFilter;
import com.jiuqi.nr.data.logic.facade.param.input.FormulaFilterEnv;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BSummaryFormulaFilterImpl
implements IFormulaFilter {
    public static List<String> ALLOW_FORM_TYPES = new ArrayList<String>(Arrays.asList("FORM_TYPE_FIX", "FORM_TYPE_FLOAT", "FORM_TYPE_TEXT_INFO", "FORM_TYPE_ATTACHED", "FORM_TYPE_QUERY", "FORM_TYPE_SURVEY", "FORM_TYPE_INTERMEDIATE"));
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private IFMDMAttributeService fmdmService;
    @Resource
    private DataAccesslUtil dataAccesslUtil;

    public List<IParsedExpression> filterParsedExpression(FormulaFilterEnv formulaFilterEnv, List<IParsedExpression> parsedExpressionList) {
        Map variableMap = formulaFilterEnv.getVariableMap();
        if (!variableMap.containsKey("batchGatherSchemeCode")) {
            return parsedExpressionList;
        }
        if ("00000000-0000-0000-0000-000000000000".equals(formulaFilterEnv.getFormKey())) {
            return parsedExpressionList;
        }
        if (parsedExpressionList == null || parsedExpressionList.isEmpty()) {
            return new ArrayList<IParsedExpression>();
        }
        FormDefine currentForm = this.runTimeViewController.queryFormById(formulaFilterEnv.getFormKey());
        if (!ALLOW_FORM_TYPES.contains(currentForm.getFormType().toString())) {
            return new ArrayList<IParsedExpression>();
        }
        SummaryScheme scheme = this.schemeService.findScheme(variableMap.get("batchGatherSchemeCode").toString());
        List formList = scheme.getRangeForm().getFormList();
        if (scheme.getRangeForm().getRangeFormType() == RangeFormType.CUSTOM && !formList.contains(currentForm.getKey())) {
            return new ArrayList<IParsedExpression>();
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formulaFilterEnv.getFormSchemeKey());
        Set<String> fmdmAttrCodes = this.getFMDMAttrCode(formScheme);
        parsedExpressionList.removeIf(next -> this.canRemove((IParsedExpression)next, fmdmAttrCodes));
        return parsedExpressionList;
    }

    private boolean canRemove(IParsedExpression expression, Set<String> fmdmAttrCodes) {
        return this.isRefTaskFormula(expression) || this.isFMDMFormula(expression, fmdmAttrCodes);
    }

    private boolean isRefTaskFormula(IParsedExpression expression) {
        return expression.getSource().getFormula().contains("@");
    }

    private boolean isFMDMFormula(IParsedExpression expression, Set<String> fmdmAttrCodes) {
        return fmdmAttrCodes.stream().anyMatch(attrCodes -> expression.getSource().getFormula().toUpperCase().contains((CharSequence)attrCodes));
    }

    private Set<String> getFMDMAttrCode(FormSchemeDefine formScheme) {
        HashSet<String> attrCodes = new HashSet<String>();
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formScheme.getKey());
        if (!(formDefines = formDefines.stream().filter(form -> form.getFormType().equals((Object)FormType.FORM_TYPE_FMDM) || form.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)).collect(Collectors.toList())).isEmpty()) {
            attrCodes.add(((FormDefine)formDefines.get(0)).getFormCode());
            List<IFMDMAttribute> fmdmAttribute = this.getFMDMAttribute(formScheme);
            if (fmdmAttribute != null) {
                attrCodes.addAll(fmdmAttribute.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet()));
            }
        } else {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(this.dataAccesslUtil.contextEntityId(formScheme.getDw()));
            List showFields = entityModel.getShowFields();
            if (showFields != null) {
                attrCodes.addAll(showFields.stream().map(IModelDefineItem::getCode).collect(Collectors.toSet()));
            }
        }
        return attrCodes;
    }

    private List<IFMDMAttribute> getFMDMAttribute(FormSchemeDefine formScheme) {
        FMDMAttributeDTO dto = new FMDMAttributeDTO();
        dto.setEntityId(this.dataAccesslUtil.contextEntityId(formScheme.getDw()));
        dto.setFormSchemeKey(formScheme.getKey());
        return this.fmdmService.listShowAttribute(dto);
    }
}

