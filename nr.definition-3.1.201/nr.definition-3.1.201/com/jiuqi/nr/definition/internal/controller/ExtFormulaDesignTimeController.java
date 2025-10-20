/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IExtFormulaDesignTimeController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignExtFormulaDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtFormulaDesignTimeController
implements IExtFormulaDesignTimeController {
    @Autowired
    private DesignExtFormulaDefineService formulaService;
    @Autowired
    private DesignFormulaSchemeDefineService formulaSchemeService;
    @Autowired
    private DesignFormSchemeDefineService formSchemeService;
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    private final Logger logger = LogFactory.getLogger(this.getClass());

    @Override
    public boolean existPrivateFormula() {
        boolean isOpen = false;
        String enable = this.systemOptionService.get("PRIVATE_FORMULA", "PRIVATE_FORMULA_VALUE");
        if (StringUtils.isNotEmpty((String)enable) && "1".equals(enable)) {
            isOpen = true;
        }
        return isOpen;
    }

    @Override
    public DesignFormulaDefine createFormulaDefine() {
        DesignFormulaDefineImpl define = new DesignFormulaDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertFormulaDefine(DesignFormulaDefine formulaDefine) throws JQException {
        String id = null;
        try {
            id = this.formulaService.insertFormulaDefine(formulaDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return id;
    }

    @Override
    public void updateFormulaDefine(DesignFormulaDefine formulaDefine) throws JQException {
        try {
            this.formulaService.updateFormulaDefine(formulaDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaDefine(String formulaKey) {
        try {
            this.formulaService.delete(formulaKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void exchangeFormulaOrder(String orinFormulaKey, String targetFormulaKey) {
        try {
            DesignFormulaDefine orin = this.formulaService.queryFormulaDefine(orinFormulaKey);
            DesignFormulaDefine target = this.formulaService.queryFormulaDefine(targetFormulaKey);
            String oldOrder = orin.getOrder();
            orin.setOrder(target.getOrder());
            target.setOrder(oldOrder);
            this.formulaService.updateFormulaDefine(orin);
            this.formulaService.updateFormulaDefine(target);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public DesignFormulaDefine queryFormulaDefine(String formulaKey) throws JQException {
        DesignFormulaDefine define = null;
        try {
            define = this.formulaService.queryFormulaDefine(formulaKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return define;
    }

    @Override
    public List<DesignFormulaDefine> findRepeatFormulaDefineFormOutSchemes(String formulaDefineCode, String formKey, String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> objs = null;
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        try {
            objs = this.formulaService.queryFormulaDefineBySchemeAndCodes(formulaDefineCode, formulaSchemeKey);
            if (objs.size() > 0) {
                for (DesignFormulaDefine formula : objs) {
                    if (formula.getFormKey() == null) {
                        if (formula.getFormKey() == formKey) continue;
                        defines.add(formula);
                        continue;
                    }
                    if (formula.getFormKey().equals(formKey)) continue;
                    defines.add(formula);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<DesignFormulaDefine> getAllFormulasInScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            defines = this.getAllSoftFormulasInScheme(formulaSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    public List<DesignFormulaDefine> getAllSoftFormulasInScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            List<DesignFormDefine> forms = this.nrDesignController.queryAllSoftFormDefinesByFormScheme(formulaSchemeKey);
            defines = this.formulaService.queryFormulaDefineByScheme(formulaSchemeKey, forms);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getAllFormulas() throws JQException {
        try {
            return this.formulaService.queryAllFormulaDefine();
        }
        catch (Exception exception) {
            return new ArrayList<DesignFormulaDefine>();
        }
    }

    @Override
    public List<DesignFormulaDefine> getFormulaByUnit(String formulaScheme, String formId, String unit) throws JQException {
        try {
            return this.formulaService.getFormulaByUnit(formulaScheme, formId, unit);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0100, (Throwable)e);
        }
    }

    @Override
    public void updateFormulaDefines(DesignFormulaDefine[] formulaDefines) throws JQException {
        try {
            this.formulaService.updateFormulaDefines(formulaDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0101, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaDefines(String[] formulaKey) throws JQException {
        try {
            this.formulaService.delete(formulaKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0101, (Throwable)e);
        }
    }

    @Override
    public Map<String, Integer> getFormulaCodeCountByScheme(String formulaSchemeKey) throws JQException {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        if (formulaSchemeKey == null) {
            return result;
        }
        try {
            List<DesignFormulaDefine> formulaDefines = this.getAllFormulasInScheme(formulaSchemeKey);
            formulaDefines.forEach(e -> {
                Integer count = (Integer)result.get(e.getCode());
                if (count == null) {
                    count = 0;
                }
                count = count + 1;
                result.put(e.getCode(), count);
            });
        }
        catch (JQException e2) {
            throw e2;
        }
        catch (Exception e3) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_029, (Throwable)e3);
        }
        return result;
    }

    @Override
    public List<String> insertFormulaDefines(DesignFormulaDefine[] formulaDefines) throws JQException {
        List<String> ids = null;
        try {
            ids = this.formulaService.insertFormulaDefines(formulaDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return ids;
    }

    @Override
    public List<DesignFormulaDefine> searchFormulaIgnorePrivate(String formulaSchemeKey) throws JQException {
        ArrayList<DesignFormulaDefine> formulaDefines = new ArrayList();
        try {
            formulaDefines = this.formulaService.searchFormulaIgnorePrivate(formulaSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return formulaDefines;
    }

    @Override
    public List<DesignFormulaDefine> getFormulaBySchemeAndForm(String formulaScheme, String formId) throws Exception {
        return this.formulaService.queryCheckFormulaDefineBySchemeAndForm(formulaScheme, formId, null);
    }
}

