/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.controller.BizController
 *  com.jiuqi.va.biz.domain.FormulasCheckDTO
 *  com.jiuqi.va.biz.domain.GrammarTreeVO
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.impl.action.ActionManagerImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.utils.R
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.biz.controller.BizController;
import com.jiuqi.va.biz.domain.FormulasCheckDTO;
import com.jiuqi.va.biz.domain.GrammarTreeVO;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.impl.action.ActionManagerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.utils.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class RulerPluginCheck
implements PluginCheck {
    @Autowired
    private BizController bizController;

    public String getName() {
        return "ruler";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return RulerDefineImpl.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName(this.getName());
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        ArrayList checkResults = new ArrayList();
        HashSet nameSet = new HashSet();
        HashSet titleSet = new HashSet();
        FormulasCheckDTO formulasCheckDTO = new FormulasCheckDTO();
        formulasCheckDTO.setModelDefine(modelDefine);
        rulerDefine.getFormulas().stream().filter(FormulaImpl::isUsed).forEach(formula -> {
            PluginCheckResultDTO checkResultDTO;
            Action action = null;
            String objectType = formula.getObjectType();
            if (objectType != null && objectType.equals("action")) {
                action = ActionManagerImpl.findAction((UUID)formula.getId());
            }
            String actioName = action == null ? null : action.getName();
            String formulaName = formula.getName();
            String formulaTitle = formula.getTitle();
            if (!StringUtils.hasText(formulaName)) {
                PluginCheckResultDTO checkResultDTO2 = this.getPluginCheckResultDTO(PluginCheckType.ERROR, formulaTitle + "\uff1a\u516c\u5f0f\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", objectType + "/" + actioName);
                checkResults.add(checkResultDTO2);
                return;
            }
            if (!nameSet.add(formulaName)) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, formulaTitle + "\uff1a\u516c\u5f0f\u6807\u8bc6\u91cd\u590d\uff1a" + formulaName, objectType + "/" + actioName);
                checkResults.add(checkResultDTO);
            }
            if (!StringUtils.hasText(formulaTitle)) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u516c\u5f0f\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff1a" + formulaName, objectType + "/" + actioName);
                checkResults.add(checkResultDTO);
            } else if (!titleSet.add(formulaTitle)) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, formulaTitle + "\uff1a\u516c\u5f0f\u540d\u79f0\u91cd\u590d\uff1a" + formulaTitle, objectType + "/" + actioName);
                checkResults.add(checkResultDTO);
            }
            ArrayList<FormulaImpl> formulas = new ArrayList<FormulaImpl>();
            formulas.add((FormulaImpl)formula);
            formulasCheckDTO.setFormulas(formulas);
            R checkFormula = this.bizController.checkFormula(formulasCheckDTO);
            formulas.clear();
            if (StringUtils.hasText(checkFormula.getMsg())) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, formulaTitle + "\uff1a" + checkFormula.getMsg() + objectType, formula.getExpression());
                checkResults.add(checkResultDTO);
            } else {
                if (CollectionUtils.isEmpty((Collection)checkFormula.getData())) {
                    return;
                }
                GrammarTreeVO grammarTreeVO = (GrammarTreeVO)((List)checkFormula.getData()).get(0);
                if (StringUtils.hasText(grammarTreeVO.getWarnMsg())) {
                    checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, formulaTitle + "\uff1a" + grammarTreeVO.getWarnMsg(), formula.getExpression());
                    checkResults.add(checkResultDTO);
                }
            }
        });
        pluginCheckResultVO.setCheckResults(checkResults);
        return pluginCheckResultVO;
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }
}

