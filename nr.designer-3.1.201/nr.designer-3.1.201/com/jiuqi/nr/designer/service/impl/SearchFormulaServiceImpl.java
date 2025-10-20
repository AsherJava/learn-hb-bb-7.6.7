/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.service.SearchFormulaService;
import com.jiuqi.nr.designer.web.facade.FormulaSearchItem;
import com.jiuqi.nr.designer.web.facade.FormulaSearchNode;
import com.jiuqi.nr.designer.web.facade.FormulaSearchParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="searchFormulaService")
public class SearchFormulaServiceImpl
implements SearchFormulaService {
    private static final String BJ_GROUPID = "test";
    private static final String NUMBER_FORMULAS = "number_formulas";
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IFormulaDesignTimeController formulaContorller;

    @Override
    public FormulaSearchParam fuzzyQueryFormula(FormulaSearchParam param) throws Exception {
        if (StringUtils.isEmpty((String)param.getKeywords())) {
            return null;
        }
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaContorller.queryFormulaSchemeDefine(param.getFormulaScheme());
        if (formulaSchemeDefine == null) {
            return null;
        }
        List formulasInScheme = this.formulaContorller.getAllFormulasInScheme(param.getFormulaScheme());
        HashMap formulaSearchNodeCache = new HashMap();
        ArrayList<FormulaSearchItem> formulaSearchItems = new ArrayList<FormulaSearchItem>();
        formulasInScheme.stream().filter(f -> f.getExpression().toLowerCase().replace(" ", "").contains(param.getKeywords().toLowerCase().replace(" ", "")) || f.getCode().toLowerCase().replace(" ", "").contains(param.getKeywords().toLowerCase().replace(" ", ""))).skip((param.getPageNo() - 1) * param.getPageSize()).limit(param.getPageSize().intValue()).forEach(formula -> {
            ArrayList<FormulaSearchNode> tablePath = new ArrayList<FormulaSearchNode>();
            boolean isBJ = StringUtils.isEmpty((String)formula.getFormKey());
            String formId = isBJ ? NUMBER_FORMULAS : formula.getFormKey();
            FormulaSearchNode formNodeCache = this.addFormNode(formulaSchemeDefine, formulaSearchNodeCache, tablePath, isBJ, formId);
            this.addGroupNode(formulaSearchNodeCache, tablePath, formNodeCache);
            Collections.reverse(tablePath);
            formulaSearchItems.add(this.initSearchItem((DesignFormulaDefine)formula, (List<FormulaSearchNode>)tablePath, formId));
        });
        param.setList(formulaSearchItems);
        return param;
    }

    private FormulaSearchNode addFormNode(DesignFormulaSchemeDefine formulaSchemeDefine, Map<String, FormulaSearchNode> formulaSearchNodeCache, List<FormulaSearchNode> tablePath, boolean isBJ, String formId) {
        FormulaSearchNode formNodeCache = formulaSearchNodeCache.get(formId);
        if (formNodeCache == null) {
            if (isBJ) {
                formNodeCache = this.initBJNode();
            } else {
                formNodeCache = new FormulaSearchNode();
                DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(formId);
                formNodeCache.setKey(designFormDefine.getKey());
                formNodeCache.setTitle(designFormDefine.getTitle());
                formNodeCache.setParentId(this.getFormGroupIdByFormId(formId, formulaSchemeDefine.getFormSchemeKey()));
            }
            formulaSearchNodeCache.put(formNodeCache.getKey(), formNodeCache);
        }
        tablePath.add(formNodeCache);
        return formNodeCache;
    }

    private void addGroupNode(Map<String, FormulaSearchNode> formulaSearchNodeCache, List<FormulaSearchNode> tablePath, FormulaSearchNode formNodeCache) {
        String formGroupId = formNodeCache.getParentId();
        if (formGroupId != null && !formGroupId.equalsIgnoreCase(BJ_GROUPID)) {
            while (StringUtils.isNotEmpty((String)formGroupId)) {
                FormulaSearchNode groupNodeCache = formulaSearchNodeCache.get(formGroupId);
                if (groupNodeCache != null) {
                    tablePath.add(groupNodeCache);
                    formGroupId = groupNodeCache.getParentId();
                    continue;
                }
                DesignFormGroupDefine formGroupDefine = this.nrDesignTimeController.queryFormGroup(formGroupId);
                FormulaSearchNode groupPath = new FormulaSearchNode();
                groupPath.setKey(formGroupDefine.getKey());
                groupPath.setTitle(formGroupDefine.getTitle());
                groupPath.setParentId(formGroupDefine.getParentKey());
                tablePath.add(groupPath);
                formulaSearchNodeCache.put(groupPath.getKey(), groupPath);
                formGroupId = groupPath.getParentId();
            }
        }
    }

    private FormulaSearchItem initSearchItem(DesignFormulaDefine formula, List<FormulaSearchNode> tablePath, String formId) {
        FormulaSearchItem item = new FormulaSearchItem();
        item.setCode(formula.getCode());
        item.setFormId(formId);
        item.setTitle(formula.getExpression());
        item.setKey(formula.getKey());
        item.setGroupPath(tablePath);
        return item;
    }

    private FormulaSearchNode initBJNode() {
        FormulaSearchNode betweenTablePath = new FormulaSearchNode();
        betweenTablePath.setKey(NUMBER_FORMULAS);
        betweenTablePath.setTitle("\u8868\u95f4\u516c\u5f0f");
        betweenTablePath.setParentId(BJ_GROUPID);
        return betweenTablePath;
    }

    private String getFormGroupIdByFormId(String formId, String formSchemeKey) {
        List formGroupDefines = this.nrDesignTimeController.getFormGroupsByFormId(formId);
        Optional<DesignFormGroupDefine> optional = formGroupDefines.stream().filter(g -> g.getFormSchemeKey().equals(formSchemeKey)).findFirst();
        return optional.isPresent() ? optional.get().getKey() : null;
    }
}

