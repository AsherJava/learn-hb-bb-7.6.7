/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionImpl;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionLinkImpl;
import com.jiuqi.nr.definition.internal.service.DesignFormulaDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaFunctionDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService;
import com.jiuqi.nr.definition.internal.service.formula.DesignFormulaConditionLinkService;
import com.jiuqi.nr.definition.internal.service.formula.DesignFormulaConditionService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class DesignTimeFormulaController
implements IDesignTimeFormulaController {
    private static final Logger logger = LoggerFactory.getLogger(DesignTimeFormulaController.class);
    @Autowired
    private DesignFormulaDefineService designFormulaDefineService;
    @Autowired
    private DesignFormulaSchemeDefineService designFormulaSchemeDefineService;
    @Autowired
    private DesignFormulaVariableDefineService designFormulaVariableDefineService;
    @Autowired
    private DesignFormulaFunctionDefineService designFormulaFunctionDefineService;
    @Autowired
    private IDesignParamCheckService iDesignParamCheckService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private DesignFormulaConditionService designFormulaConditionService;
    @Autowired
    private DesignFormulaConditionLinkService designFormulaConditionLinkService;

    @Override
    public DesignFormulaSchemeDefine initFormulaScheme() {
        DesignFormulaSchemeDefineImpl designFormulaSchemeDefine = new DesignFormulaSchemeDefineImpl();
        designFormulaSchemeDefine.setKey(UUIDUtils.getKey());
        designFormulaSchemeDefine.setOrder(OrderGenerator.newOrder());
        designFormulaSchemeDefine.setUpdateTime(new Date());
        return designFormulaSchemeDefine;
    }

    @Override
    public void insertFormulaScheme(DesignFormulaSchemeDefine designFormulaSchemeDefine) {
        try {
            this.iDesignParamCheckService.checkFormulaSchemeNameByType(designFormulaSchemeDefine);
            this.designFormulaSchemeDefineService.insertFormulaSchemeDefine(designFormulaSchemeDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateFormulaScheme(DesignFormulaSchemeDefine designFormulaSchemeDefine) {
        try {
            this.iDesignParamCheckService.checkFormulaSchemeNameByType(designFormulaSchemeDefine);
            boolean defaultScheme = designFormulaSchemeDefine.isDefault();
            if (defaultScheme && !designFormulaSchemeDefine.isShow()) {
                designFormulaSchemeDefine.setShow(true);
            }
            this.designFormulaSchemeDefineService.updateFormulaSchemeDefine(designFormulaSchemeDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deleteFormulaScheme(String[] keys) {
        try {
            for (String formulaScheme : keys) {
                this.deleteFormulaByScheme(formulaScheme);
            }
            this.designFormulaSchemeDefineService.delete(keys);
            this.designFormulaConditionLinkService.deleteByFormula(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignFormulaSchemeDefine getFormulaScheme(String formulaSchemeKey) {
        try {
            return this.designFormulaSchemeDefineService.queryFormulaSchemeDefine(formulaSchemeKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaSchemeDefine> listFormulaSchemeByFormScheme(String formScheme) {
        try {
            return this.designFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaSchemeDefine> listFormulaSchemeByFormSchemeAndType(String formScheme, FormulaSchemeType formulaSchemeType) {
        try {
            List<DesignFormulaSchemeDefine> defines = this.designFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formScheme);
            return defines.stream().filter(e -> e.getFormulaSchemeType() == formulaSchemeType).collect(Collectors.toList());
        }
        catch (Exception e2) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY.getMessage(), e2);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY, (Throwable)e2);
        }
    }

    @Override
    public DesignFormulaSchemeDefine getDefaultFormulaSchemeByFormScheme(String formScheme) {
        try {
            DesignFormulaSchemeDefine defaultScheme = null;
            List<DesignFormulaSchemeDefine> formulaSchemeDefines = this.designFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formScheme);
            for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                if (!FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT.equals((Object)formulaSchemeDefine.getFormulaSchemeType()) || !formulaSchemeDefine.isDefault()) continue;
                defaultScheme = formulaSchemeDefine;
            }
            return defaultScheme;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaSchemeDefine> listAllFormulaScheme() {
        try {
            return this.designFormulaSchemeDefineService.listGhostScheme();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormulaSchemeDefine getFormulaSchemeByTitle(DesignFormulaSchemeDefine formulaSchemeDefine) {
        try {
            DesignFormulaSchemeDefine titleDefine = null;
            List<DesignFormulaSchemeDefine> formulaSchemeDefines = this.designFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formulaSchemeDefine.getFormSchemeKey());
            for (DesignFormulaSchemeDefine schemeDefine : formulaSchemeDefines) {
                if (!schemeDefine.getFormulaSchemeType().equals((Object)formulaSchemeDefine.getFormulaSchemeType()) || !schemeDefine.getTitle().equals(formulaSchemeDefine.getTitle())) continue;
                titleDefine = schemeDefine;
            }
            return titleDefine;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormulaDefine initFormula() {
        DesignFormulaDefineImpl designFormulaDefine = new DesignFormulaDefineImpl();
        designFormulaDefine.setKey(UUIDUtils.getKey());
        designFormulaDefine.setIsPrivate(false);
        designFormulaDefine.setOrder(OrderGenerator.newOrder());
        designFormulaDefine.setUpdateTime(new Date());
        designFormulaDefine.setSyntax(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue());
        return designFormulaDefine;
    }

    @Override
    public void insertFormula(DesignFormulaDefine[] designFormulaDefines) {
        try {
            this.iDesignParamCheckService.checkFormulaCode(designFormulaDefines);
            this.addLog(designFormulaDefines, "add", null);
            this.designFormulaDefineService.insertFormulaDefines(designFormulaDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateFormula(DesignFormulaDefine[] designFormulaDefines) {
        try {
            this.iDesignParamCheckService.checkFormulaCode(designFormulaDefines);
            this.addLog(designFormulaDefines, "update", null);
            this.designFormulaDefineService.updateFormulaDefines(designFormulaDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteFormula(String[] keys) {
        try {
            this.addLog(null, "delete", keys);
            this.designFormulaDefineService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaByForm(String formKey) {
        try {
            this.designFormulaDefineService.deleteByForm(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaBySchemeAndForm(String formulaScheme, String formKey) {
        try {
            this.designFormulaDefineService.deleteBySchemeAndForm(formulaScheme, formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaByScheme(String formulaScheme) {
        try {
            this.designFormulaDefineService.deleteByScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignFormulaDefine getFormula(String key) {
        try {
            return this.designFormulaDefineService.queryFormulaDefine(key);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignFormulaDefine getFormulaByCodeAndScheme(String formulaCode, String formulaScheme) {
        try {
            return this.designFormulaDefineService.queryFormulaDefineBySchemeAndCode(formulaCode, formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> getFormulaByCodeAndSchemeAndForm(String formulaCode, String formulaScheme, String formkey) {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        try {
            List<DesignFormulaDefine> formulaDefines = this.designFormulaDefineService.queryFormulaDefineBySchemeAndCodes(formulaCode, formulaScheme);
            if (null != formulaDefines && formulaDefines.size() > 0) {
                for (DesignFormulaDefine formulaDefine : formulaDefines) {
                    if (formulaDefine.getFormKey() == null) {
                        if (formulaDefine.getFormKey() == formkey) continue;
                        defines.add(formulaDefine);
                        continue;
                    }
                    if (formulaDefine.getFormKey().equals(formkey)) continue;
                    defines.add(formulaDefine);
                }
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> listFormulaByScheme(String formulaScheme) {
        try {
            return this.designFormulaDefineService.listFormulaDefineByScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listAllFormula() {
        try {
            return this.designFormulaDefineService.listGhostFormula();
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listCalculateFormulaByScheme(String formulaScheme) {
        try {
            return this.designFormulaDefineService.listCalcFormulaDefineByScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listCheckFormulaByScheme(String formulaScheme) {
        try {
            return this.designFormulaDefineService.listCheckFormulaDefineByScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listBalanceFormulaByScheme(String formulaScheme) {
        try {
            return this.designFormulaDefineService.listBalanceFormulaDefineByScheme(formulaScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listFormulaBySchemeAndForm(String formulaScheme, String formKey) {
        try {
            return this.designFormulaDefineService.listFormulaDefineBySchemeAndForm(formulaScheme, formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listCalculateFormulaBySchemeAndForm(String formulaScheme, String formKey) {
        try {
            return this.designFormulaDefineService.listCalcFormulaDefineBySchemeAndForm(formulaScheme, formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listCheckFormulaBySchemeAndForm(String formulaScheme, String formKey) {
        try {
            return this.designFormulaDefineService.listCheckFormulaDefineBySchemeAndForm(formulaScheme, formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> listBalanceFormulaBySchemeAndForm(String formulaScheme, String formKey) {
        try {
            return this.designFormulaDefineService.listBalanceFormulaDefineBySchemeAndForm(formulaScheme, formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertFormulaVariable(FormulaVariDefine formulaVariDefine) {
        try {
            this.iDesignParamCheckService.checkFormulaVariable(formulaVariDefine);
            this.designFormulaVariableDefineService.addFormulaVariable(formulaVariDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_VARI_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORMULA_VARI_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updateFormulaVariable(FormulaVariDefine formulaVariDefine) {
        try {
            this.iDesignParamCheckService.checkFormulaVariable(formulaVariDefine);
            this.designFormulaVariableDefineService.updateFormulaVariable(formulaVariDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_VARI_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException(NrDefinitionErrorEnum2.FORMULA_VARI_UPDATE);
        }
    }

    @Override
    public void deleteFormulaVariable(String key) {
        try {
            this.designFormulaVariableDefineService.deleteFormulaVariable(key);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE);
        }
    }

    @Override
    public void deleteFormulaVariableByFormScheme(String formScheme) {
        try {
            this.designFormulaVariableDefineService.deleteFormulaVariByFormScheme(formScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE);
        }
    }

    @Override
    public List<FormulaVariDefine> listFormulaVariByFormScheme(String formScheme) {
        try {
            return this.designFormulaVariableDefineService.queryAllFormulaVariable(formScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE);
        }
    }

    @Override
    public FormulaVariDefine getFormulaVariByCodeAndFormScheme(String code, String formScheme) {
        try {
            List<FormulaVariDefine> formulaVariDefines = this.designFormulaVariableDefineService.queryFormulaVariableByCode(formScheme, code);
            if (null != formulaVariDefines && formulaVariDefines.size() == 1) {
                return formulaVariDefines.get(0);
            }
            return null;
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException(NrDefinitionErrorEnum2.FORMULA_VARI_DELETE);
        }
    }

    @Override
    public List<FormulaFunctionDefine> listAllFormulaFunction() {
        return this.designFormulaFunctionDefineService.queryAllFormulaFunction();
    }

    @Override
    public List<FormulaFunctionParamterDefine> listAllFormulaFunctionParamter() {
        return this.designFormulaFunctionDefineService.queryAllFormulaFunctionParamter();
    }

    private DesignFormulaSchemeDefine getFormulaSchemeDefine(Map<String, DesignFormulaSchemeDefine> formulaSchemeDefineMap, String key) throws Exception {
        DesignFormulaSchemeDefine formulaSchemeDefine = null;
        if (null == formulaSchemeDefineMap.get(key)) {
            formulaSchemeDefineMap.put(key, this.designFormulaSchemeDefineService.queryFormulaSchemeDefine(key));
            formulaSchemeDefine = formulaSchemeDefineMap.get(key);
        } else {
            formulaSchemeDefine = formulaSchemeDefineMap.get(key);
        }
        return formulaSchemeDefine;
    }

    private DesignFormSchemeDefine getFormSchemeDefine(Map<String, DesignFormSchemeDefine> formSchemeDefineMap, String key) throws Exception {
        DesignFormSchemeDefine formSchemeDefine = null;
        if (null == formSchemeDefineMap.get(key)) {
            formSchemeDefineMap.put(key, this.iDesignTimeViewController.getFormScheme(key));
            formSchemeDefine = formSchemeDefineMap.get(key);
        } else {
            formSchemeDefine = formSchemeDefineMap.get(key);
        }
        return formSchemeDefine;
    }

    private DesignTaskDefine getTaskDefine(Map<String, DesignTaskDefine> taskDefineMap, String key) throws Exception {
        DesignTaskDefine taskDefine = null;
        if (null == taskDefineMap.get(key)) {
            taskDefineMap.put(key, this.iDesignTimeViewController.getTask(key));
            taskDefine = taskDefineMap.get(key);
        } else {
            taskDefine = taskDefineMap.get(key);
        }
        return taskDefine;
    }

    private DesignFormDefine getFormDefine(Map<String, DesignFormDefine> formDefineMap, String key) throws Exception {
        DesignFormDefine formDefine = null;
        if (null == key) {
            return formDefine;
        }
        if (null == formDefineMap.get(key)) {
            formDefineMap.put(key, this.iDesignTimeViewController.getForm(key));
            formDefine = formDefineMap.get(key);
        } else {
            formDefine = formDefineMap.get(key);
        }
        return formDefine;
    }

    private String getFormulaType(DesignFormulaDefine formulaDefine) {
        ArrayList<String> formulaTypes = new ArrayList<String>();
        if (formulaDefine.getUseCalculate()) {
            formulaTypes.add("\u8fd0\u7b97\u516c\u5f0f");
        }
        if (formulaDefine.getUseCheck()) {
            formulaTypes.add("\u5ba1\u6838\u516c\u5f0f");
        }
        if (formulaDefine.getUseBalance()) {
            formulaTypes.add("\u5e73\u8861\u516c\u5f0f");
        }
        return formulaTypes.stream().collect(Collectors.joining(";"));
    }

    private Map<String, List<DesignFormulaDefine>> formulaByGroup(DesignFormulaDefine[] designFormulaDefine) {
        HashMap<String, List<DesignFormulaDefine>> formulaGroup = new HashMap<String, List<DesignFormulaDefine>>();
        for (DesignFormulaDefine formulaDefine : designFormulaDefine) {
            if (null == formulaDefine) continue;
            if (formulaGroup.get(formulaDefine.getFormulaSchemeKey()) == null) {
                formulaGroup.put(formulaDefine.getFormulaSchemeKey(), new ArrayList());
                ((List)formulaGroup.get(formulaDefine.getFormulaSchemeKey())).add(formulaDefine);
                continue;
            }
            ((List)formulaGroup.get(formulaDefine.getFormulaSchemeKey())).add(formulaDefine);
        }
        return formulaGroup;
    }

    public void addLog(DesignFormulaDefine[] designFormulaDefine, String type, String[] formulaKey) {
        try {
            HashMap<String, DesignFormulaSchemeDefine> formulaSchemeDefineMap = new HashMap<String, DesignFormulaSchemeDefine>();
            HashMap<String, DesignFormSchemeDefine> formSchemeDefineMap = new HashMap<String, DesignFormSchemeDefine>();
            HashMap<String, DesignTaskDefine> taskDefineMap = new HashMap<String, DesignTaskDefine>();
            HashMap<String, DesignFormDefine> formDefineMap = new HashMap<String, DesignFormDefine>();
            switch (type) {
                case "add": {
                    StringBuffer sbf = new StringBuffer();
                    Map<String, List<DesignFormulaDefine>> formulaGroup = this.formulaByGroup(designFormulaDefine);
                    for (String formulaSchemeKey : formulaGroup.keySet()) {
                        DesignFormulaSchemeDefine formulaSchemeDefine = this.getFormulaSchemeDefine(formulaSchemeDefineMap, formulaSchemeKey);
                        DesignFormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(formSchemeDefineMap, formulaSchemeDefine.getFormSchemeKey());
                        DesignTaskDefine taskDefine = this.getTaskDefine(taskDefineMap, formSchemeDefine.getTaskKey());
                        sbf.append(String.format("\u4efb\u52a1\u3010%s\u3011", taskDefine.getTitle()));
                        sbf.append(String.format("\u62a5\u8868\u65b9\u6848\u3010%s\u3011", formSchemeDefine.getTitle()));
                        sbf.append(String.format("\u516c\u5f0f\u65b9\u6848\u3010%s\u3011", formulaSchemeDefine.getTitle()));
                        for (int i = 0; i < formulaGroup.get(formulaSchemeKey).size(); ++i) {
                            DesignFormulaDefine formulaDefine = formulaGroup.get(formulaSchemeKey).get(i);
                            if (null != formulaDefine.getFormKey()) {
                                DesignFormDefine formDefine = this.getFormDefine(formDefineMap, formulaDefine.getFormKey());
                                sbf.append(String.format("\u62a5\u8868\u3010%s|%s\u3011", formDefine.getFormCode(), formDefine.getTitle()));
                            }
                            sbf.append(String.format("\u516c\u5f0f\u4e3b\u952e\u3010%s\u3011", formulaDefine.getKey()));
                            sbf.append(String.format("\u516c\u5f0f\u6807\u8bc6\u3010%s\u3011", formulaDefine.getCode()));
                            sbf.append(String.format("\u516c\u5f0f\u5185\u5bb9\u3010%s\u3011", formulaDefine.getExpression()));
                            sbf.append(String.format("\u516c\u5f0f\u7c7b\u578b\u3010%s\u3011", this.getFormulaType(formulaDefine)));
                            if (i == formulaGroup.get(formulaSchemeKey).size() - 1) continue;
                            sbf.append(";");
                        }
                    }
                    sbf.append("\u3002");
                    LogHelper.info((String)"\u516c\u5f0f\u7ba1\u7406", (String)"\u65b0\u589e\u516c\u5f0f", (String)sbf.toString());
                    break;
                }
                case "update": {
                    StringBuffer sbf = new StringBuffer();
                    Map<String, List<DesignFormulaDefine>> formulaGroup = this.formulaByGroup(designFormulaDefine);
                    String[] keys = (String[])Arrays.stream(designFormulaDefine).map(IBaseMetaItem::getKey).distinct().toArray(String[]::new);
                    Map<String, DesignFormulaDefine> oldFormulaDefines = this.designFormulaDefineService.queryFormulaDefines(keys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v, (v1, v2) -> v1));
                    for (String formulaSchemeKey : formulaGroup.keySet()) {
                        DesignFormulaSchemeDefine formulaSchemeDefine = this.getFormulaSchemeDefine(formulaSchemeDefineMap, formulaSchemeKey);
                        DesignFormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(formSchemeDefineMap, formulaSchemeDefine.getFormSchemeKey());
                        DesignTaskDefine taskDefine = this.getTaskDefine(taskDefineMap, formSchemeDefine.getTaskKey());
                        sbf.append(String.format("\u4efb\u52a1\u3010%s\u3011", taskDefine.getTitle()));
                        sbf.append(String.format("\u62a5\u8868\u65b9\u6848\u3010%s\u3011", formSchemeDefine.getTitle()));
                        sbf.append(String.format("\u516c\u5f0f\u65b9\u6848\u3010%s\u3011", formulaSchemeDefine.getTitle()));
                        for (int i = 0; i < formulaGroup.get(formulaSchemeKey).size(); ++i) {
                            DesignFormulaDefine formulaDefine = formulaGroup.get(formulaSchemeKey).get(i);
                            if (null != formulaDefine.getFormKey()) {
                                DesignFormDefine formDefine = this.getFormDefine(formDefineMap, formulaDefine.getFormKey());
                                sbf.append(String.format("\u62a5\u8868\u3010%s|%s\u3011", formDefine.getFormCode(), formDefine.getTitle()));
                            }
                            DesignFormulaDefine oldFormulaDefine = oldFormulaDefines.get(formulaDefine.getKey());
                            sbf.append(String.format("\u516c\u5f0f\u4e3b\u952e\u3010%s\u3011", formulaDefine.getKey()));
                            sbf.append(String.format("\u516c\u5f0f\u6807\u8bc6\u3010%s\u3011", oldFormulaDefine.getCode()));
                            if (!oldFormulaDefine.getCode().equals(formulaDefine.getCode())) {
                                sbf.append(String.format("\u4fee\u6539\u4e3a\u3010%s\u3011", formulaDefine.getCode()));
                            }
                            sbf.append(String.format("\u516c\u5f0f\u5185\u5bb9\u3010%s\u3011", oldFormulaDefine.getExpression()));
                            if (!oldFormulaDefine.getExpression().equals(formulaDefine.getExpression())) {
                                sbf.append(String.format("\u4fee\u6539\u4e3a\u3010%s\u3011", formulaDefine.getExpression()));
                            }
                            sbf.append(String.format("\u516c\u5f0f\u7c7b\u578b\u3010%s\u3011", this.getFormulaType(oldFormulaDefine)));
                            if (!this.getFormulaType(oldFormulaDefine).equals(this.getFormulaType(formulaDefine))) {
                                sbf.append(String.format("\u4fee\u6539\u4e3a\u3010%s\u3011", this.getFormulaType(formulaDefine)));
                            }
                            if (i == formulaGroup.get(formulaSchemeKey).size() - 1) continue;
                            sbf.append(";");
                        }
                    }
                    sbf.append("\u3002");
                    LogHelper.info((String)"\u516c\u5f0f\u7ba1\u7406", (String)"\u66f4\u65b0\u516c\u5f0f", (String)sbf.toString());
                    break;
                }
                case "delete": {
                    StringBuffer sbf = new StringBuffer();
                    List<DesignFormulaDefine> formulaDefines = this.designFormulaDefineService.queryFormulaDefines(formulaKey);
                    Map<String, List<DesignFormulaDefine>> formulaGroup = this.formulaByGroup((DesignFormulaDefine[])formulaDefines.stream().toArray(DesignFormulaDefine[]::new));
                    for (String formulaSchemeKey : formulaGroup.keySet()) {
                        DesignFormulaSchemeDefine formulaSchemeDefine = this.getFormulaSchemeDefine(formulaSchemeDefineMap, formulaSchemeKey);
                        DesignFormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(formSchemeDefineMap, formulaSchemeDefine.getFormSchemeKey());
                        DesignTaskDefine taskDefine = this.getTaskDefine(taskDefineMap, formSchemeDefine.getTaskKey());
                        sbf.append(String.format("\u4efb\u52a1\u3010%s\u3011", taskDefine.getTitle()));
                        sbf.append(String.format("\u62a5\u8868\u65b9\u6848\u3010%s\u3011", formSchemeDefine.getTitle()));
                        sbf.append(String.format("\u516c\u5f0f\u65b9\u6848\u3010%s\u3011", formulaSchemeDefine.getTitle()));
                        for (int i = 0; i < formulaGroup.get(formulaSchemeKey).size(); ++i) {
                            DesignFormulaDefine formulaDefine = formulaGroup.get(formulaSchemeKey).get(i);
                            if (null != formulaDefine.getFormKey()) {
                                DesignFormDefine formDefine = this.getFormDefine(formDefineMap, formulaDefine.getFormKey());
                                sbf.append(String.format("\u62a5\u8868\u3010%s\u3011", formDefine.getTitle()));
                            }
                            sbf.append(String.format("\u516c\u5f0f\u4e3b\u952e\u3010%s\u3011", formulaDefine.getKey()));
                            sbf.append(String.format("\u516c\u5f0f\u6807\u8bc6\u3010%s\u3011", formulaDefine.getCode()));
                            sbf.append(String.format("\u516c\u5f0f\u5185\u5bb9\u3010%s\u3011", formulaDefine.getExpression()));
                            sbf.append(String.format("\u516c\u5f0f\u7c7b\u578b\u3010%s\u3011", this.getFormulaType(formulaDefine)));
                            if (i == formulaGroup.get(formulaSchemeKey).size() - 1) continue;
                            sbf.append(";");
                        }
                    }
                    sbf.append("\u3002");
                    LogHelper.info((String)"\u516c\u5f0f\u7ba1\u7406", (String)"\u5220\u9664\u516c\u5f0f", (String)sbf.toString());
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error("\u516c\u5f0f\u4fee\u6539\u6dfb\u52a0\u65e5\u5fd7\u5931\u8d25", e);
        }
    }

    @Override
    public DesignFormulaCondition initFormulaCondition() {
        DesignFormulaConditionImpl condition = new DesignFormulaConditionImpl();
        condition.setKey(UUIDUtils.getKey());
        condition.setUpdateTime(new Date());
        condition.setOrder(OrderGenerator.newOrder());
        return condition;
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByTask(String task) {
        return this.designFormulaConditionService.listFormulaConditionByTask(task);
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByTaskAndCode(String taskKey, String ... codes) {
        if (codes == null || codes.length == 0) {
            return this.listFormulaConditionByTask(taskKey);
        }
        HashSet<String> codeSet = new HashSet<String>(Arrays.asList(codes));
        return this.listFormulaConditionByTask(taskKey).stream().filter(c -> codeSet.contains(c.getCode())).collect(Collectors.toList());
    }

    @Override
    public void insertFormulaConditions(List<DesignFormulaCondition> conditions) {
        this.designFormulaConditionService.insertFormulaConditions(conditions);
    }

    @Override
    public void insertFormulaCondition(DesignFormulaCondition designFormulaCondition) {
        this.designFormulaConditionService.insertFormulaCondition(designFormulaCondition);
    }

    @Override
    public void updateFormulaCondition(DesignFormulaCondition condition) {
        this.designFormulaConditionService.updateFormulaCondition(condition);
    }

    @Override
    public void updateFormulaConditions(List<DesignFormulaCondition> collect) {
        if (CollectionUtils.isEmpty(collect)) {
            return;
        }
        this.designFormulaConditionService.updateFormulaConditions(collect);
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByKey(List<String> keys) {
        return this.designFormulaConditionService.listFormulaConditionByKey(keys);
    }

    @Override
    public void deleteFormulaCondition(String key) {
        this.designFormulaConditionService.deleteFormulaCondition(key);
        this.deleteFormulaConditionLinkByCondition(key);
    }

    @Override
    public void deleteFormulaConditions(List<String> keys) {
        this.designFormulaConditionService.deleteFormulaConditions(keys);
        this.deleteFormulaConditionLinkByCondition(keys.toArray(new String[0]));
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByScheme(String formulaScheme) {
        List<DesignFormulaConditionLink> designFormulaConditionLinks = this.designFormulaConditionLinkService.listConditionLinkByScheme(formulaScheme);
        List<String> keys = designFormulaConditionLinks.stream().filter(l -> l.getFormulaSchemeKey().equals(formulaScheme)).map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList());
        return this.designFormulaConditionService.listFormulaConditionByKey(keys);
    }

    @Override
    public DesignFormulaConditionLink initFormulaConditionLink() {
        return new DesignFormulaConditionLinkImpl();
    }

    @Override
    public List<DesignFormulaConditionLink> listFormulaConditionLinkByScheme(String formulaScheme) {
        return this.designFormulaConditionLinkService.listConditionLinkByScheme(formulaScheme);
    }

    @Override
    public void insertFormulaConditionLinks(List<DesignFormulaConditionLink> designFormulaConditionLinks) {
        if (CollectionUtils.isEmpty(designFormulaConditionLinks)) {
            return;
        }
        DesignFormulaConditionLink link = designFormulaConditionLinks.get(0);
        HashSet<DesignFormulaConditionLink> conditionLinks = new HashSet<DesignFormulaConditionLink>(this.designFormulaConditionLinkService.listConditionLinkByScheme(link.getFormulaSchemeKey()));
        this.designFormulaConditionLinkService.insert(designFormulaConditionLinks.stream().filter(l -> !conditionLinks.contains(l)).collect(Collectors.toList()));
    }

    @Override
    public void deleteFormulaConditionLinkByCondition(String ... condition) {
        this.designFormulaConditionLinkService.deleteByConditionKey(condition);
    }

    @Override
    public void deleteFormulaConditionLinks(List<DesignFormulaConditionLink> designFormulaConditionLinks) {
        if (!CollectionUtils.isEmpty(designFormulaConditionLinks)) {
            this.designFormulaConditionLinkService.delete(designFormulaConditionLinks);
        }
    }

    @Override
    public List<DesignFormulaConditionLink> listConditionLinksByCondition(List<String> conditionKey) {
        return this.designFormulaConditionLinkService.listConditionLinkByCondition(conditionKey);
    }

    @Override
    public void deleteFormulaConditionLinkByScheme(String formulaScheme) {
        this.designFormulaConditionLinkService.deleteByScheme(formulaScheme);
    }

    @Override
    public void deleteFormulaConditionLinkByFormula(String ... formulaKey) {
        this.designFormulaConditionLinkService.deleteByFormula(formulaKey);
    }
}

