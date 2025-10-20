/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.controller.BizController
 *  com.jiuqi.va.biz.domain.FormulasCheckDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.utils.R
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.biz.controller.BizController;
import com.jiuqi.va.biz.domain.FormulasCheckDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.utils.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ComputedPropPluginCheck
implements PluginCheck {
    @Autowired
    private BizController bizController;

    public String getName() {
        return "computedProp";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ComputedPropDefineImpl.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        ArrayList checkResults = new ArrayList();
        pluginCheckResultVO.setPluginName(this.getName());
        ComputedPropDefineImpl define = (ComputedPropDefineImpl)pluginDefine;
        List formulas = define.getFormulas();
        HashSet nameSet = new HashSet();
        FormulasCheckDTO formulasCheckDTO = new FormulasCheckDTO();
        formulasCheckDTO.setModelDefine(modelDefine);
        formulas.stream().forEach(o -> {
            PluginCheckResultDTO checkResultDTO;
            if (!nameSet.add(o.getName())) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u8ba1\u7b97\u5c5e\u6027\u6807\u8bc6\u91cd\u590d\uff1a" + o.getName(), o.getName());
                checkResults.add(checkResultDTO);
            }
            if (!StringUtils.hasText(o.getExpression())) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.WARN, String.format("\u8ba1\u7b97\u5c5e\u6027\u3010%s\u3011\uff1a\u516c\u5f0f\u5185\u5bb9\u4e3a\u7a7a", o.getName()), o.getName());
                checkResults.add(checkResultDTO);
                return;
            }
            ArrayList<FormulaImpl> checkFormulas = new ArrayList<FormulaImpl>();
            checkFormulas.add((FormulaImpl)o);
            formulasCheckDTO.setFormulas(checkFormulas);
            R checkFormula = this.bizController.checkFormula(formulasCheckDTO);
            checkFormulas.clear();
            if (StringUtils.hasText(checkFormula.getMsg())) {
                checkResultDTO = this.getPluginCheckResultDTO(PluginCheckType.ERROR, String.format("\u8ba1\u7b97\u5c5e\u6027\u3010%s\u3011\uff1a%s", o.getName(), checkFormula.getMsg()), o.getExpression());
                checkResults.add(checkResultDTO);
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

