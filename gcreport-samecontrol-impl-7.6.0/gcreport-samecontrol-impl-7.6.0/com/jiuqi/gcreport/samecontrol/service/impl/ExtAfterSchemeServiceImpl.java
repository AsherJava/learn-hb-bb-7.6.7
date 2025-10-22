/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtAfterSchemeVO
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.samecontrol.service.ExtAfterSchemeService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtAfterSchemeVO;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtAfterSchemeServiceImpl
implements ExtAfterSchemeService {
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    private static final Logger logger = LoggerFactory.getLogger(ExtAfterSchemeServiceImpl.class);

    @Override
    public List<String> getSameCtrlExtAfterScheme(SameCtrlExtAfterSchemeVO sameCtrlExtAfterScheme) {
        FormulaSchemeConfigDTO formulaSchemeConfig = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(sameCtrlExtAfterScheme.getSchemeId(), sameCtrlExtAfterScheme.getOrgId(), sameCtrlExtAfterScheme.getAssistDim());
        if (null != formulaSchemeConfig && !CollectionUtils.isEmpty((Collection)formulaSchemeConfig.getSameCtrlExtAfterSchemeId())) {
            return formulaSchemeConfig.getSameCtrlExtAfterSchemeId();
        }
        String defaultFormulaSchemeId = this.getDefaultFormulaSchemeId(sameCtrlExtAfterScheme.getSchemeId());
        return CollectionUtils.newArrayList((Object[])new String[]{defaultFormulaSchemeId});
    }

    private String getDefaultFormulaSchemeId(String schemeId) {
        List definesByFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(schemeId);
        if (CollectionUtils.isEmpty((Collection)definesByFormScheme)) {
            return null;
        }
        Optional<FormulaSchemeDefine> formulaSchemeDefineOptional = definesByFormScheme.stream().filter(formulaScheme -> {
            FormulaSchemeType schemeType = formulaScheme.getFormulaSchemeType();
            return FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT.equals((Object)schemeType) && formulaScheme.isDefault();
        }).findFirst();
        if (!formulaSchemeDefineOptional.isPresent()) {
            logger.error("\u672a\u627e\u5230\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848 schemeId:" + schemeId);
            return null;
        }
        return formulaSchemeDefineOptional.get().getKey();
    }
}

