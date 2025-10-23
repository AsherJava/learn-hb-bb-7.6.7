/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.fmdm.validator.impl;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.internal.check.CheckParam;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import com.jiuqi.nr.fmdm.internal.formula.IFMDMFormulaCheckService;
import com.jiuqi.nr.fmdm.validator.DataAddValidator;
import com.jiuqi.nr.fmdm.validator.DataUpdateValidator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaValidator
implements DataUpdateValidator,
DataAddValidator {
    @Autowired
    private IFMDMFormulaCheckService formulaCheckService;
    @Autowired
    private IRunTimeViewController runTimeController;
    private static final int FORCE_CHECK_TYPE = -1;

    @Override
    public List<FMDMCheckFailNodeInfo> check(FMDMDataDTO fmdmDataDTO) {
        FormDefine formDefine = this.getFormDefine(fmdmDataDTO);
        if (!fmdmDataDTO.isIgnoreCheck() && formDefine != null) {
            FMDMCheckResult check = this.formulaCheckService.check(this.buildCheckParam(fmdmDataDTO, formDefine.getKey()));
            if (check == null) {
                return null;
            }
            return check.getResults();
        }
        return null;
    }

    private CheckParam buildCheckParam(FMDMDataDTO fmdmDataDTO, String formKey) {
        CheckParam checkParam = new CheckParam();
        checkParam.setFormKey(formKey);
        checkParam.setFormulaSchemeKey(fmdmDataDTO.getFormulaSchemeKey());
        Map<String, Object> modifyValueMap = fmdmDataDTO.getModifyValueMap();
        checkParam.setData(modifyValueMap);
        checkParam.setMasterKeys(fmdmDataDTO.getDimensionCombination().toDimensionValueSet());
        checkParam.setModifyType(fmdmDataDTO.getModifyType());
        return checkParam;
    }

    private FormDefine getFormDefine(FMDMDataDTO fmdmDataDTO) {
        List formDefines = this.runTimeController.queryAllFormDefinesByFormScheme(fmdmDataDTO.getFormSchemeKey());
        Optional<FormDefine> findForm = formDefines.stream().filter(e -> FormType.FORM_TYPE_NEWFMDM.equals((Object)e.getFormType())).findFirst();
        return findForm.orElse(null);
    }
}

