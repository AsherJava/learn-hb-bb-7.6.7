/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.formulamapping.IFormulaMappingController
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.copydes.web;

import com.jiuqi.nr.dataentry.copydes.web.vo.FormulaSchemeVO;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.formulamapping.IFormulaMappingController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/copy-des/"})
public class CopyDesWebController {
    @Autowired
    private IFormulaMappingController formulaMappingController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @PostMapping(value={"/get-src-formulaSchemes/{targetFormulaSchemeKey}"})
    public List<FormulaSchemeVO> getSrcFormulaSchemes(@PathVariable String targetFormulaSchemeKey) {
        List sourceFormulaSchemes = this.formulaMappingController.querySourceFormulaSchemes(targetFormulaSchemeKey);
        if (CollectionUtils.isEmpty(sourceFormulaSchemes)) {
            return Collections.emptyList();
        }
        String defaultFls = this.getDefaultFls(targetFormulaSchemeKey);
        ArrayList<FormulaSchemeVO> result = new ArrayList<FormulaSchemeVO>();
        for (String sourceFormulaScheme : sourceFormulaSchemes) {
            FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(sourceFormulaScheme);
            if (formulaSchemeDefine == null) continue;
            FormulaSchemeVO formulaSchemeVO = new FormulaSchemeVO();
            formulaSchemeVO.setKey(formulaSchemeDefine.getKey());
            formulaSchemeVO.setTitle(formulaSchemeDefine.getTitle());
            String formSchemeKey = formulaSchemeDefine.getFormSchemeKey();
            formulaSchemeVO.setFormSchemeKey(formSchemeKey);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            formulaSchemeVO.setFormSchemeTitle(formScheme.getTitle());
            formulaSchemeVO.setFormSchemeCode(formScheme.getFormSchemeCode());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            formulaSchemeVO.setTaskKey(taskDefine.getKey());
            formulaSchemeVO.setTaskTitle(taskDefine.getTitle());
            formulaSchemeVO.setTaskCode(taskDefine.getTaskCode());
            if (defaultFls.equals(formulaSchemeDefine.getKey())) {
                formulaSchemeVO.setDefaultFls(true);
            }
            result.add(formulaSchemeVO);
        }
        return result;
    }

    @NonNull
    private String getDefaultFls(String targetFormulaSchemeKey) {
        String defaultFls = this.formulaMappingController.querySourceFormulaSchemeKey(targetFormulaSchemeKey);
        if (defaultFls == null) {
            defaultFls = "";
        }
        return defaultFls;
    }
}

