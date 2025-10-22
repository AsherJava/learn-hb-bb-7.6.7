/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.DesignFormulaDTO;
import com.jiuqi.nr.definition.common.FormulaConditionDTO;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.MetaComparator;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.exception.DesignCheckException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionDefine;
import com.jiuqi.nr.definition.facade.FormulaFunctionParamterDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaDefineDao;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionImpl;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionLinkImpl;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FormulaDesignTimeController
implements IFormulaDesignTimeController {
    @Autowired
    private DesignFormulaDefineService formulaService;
    @Autowired
    private DesignFormulaSchemeDefineService formulaSchemeService;
    @Autowired
    private DesignFormSchemeDefineService formSchemeService;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private NRDesignTimeController designTimeController;
    @Autowired
    private DesignFormulaVariableDefineService designFormulaVariableDefineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController2;
    @Autowired
    private DesignFormulaDefineDao designFormulaDefineDao;
    @Autowired
    private DesignDataLinkDefineService dataLinkDefineService;
    @Autowired
    private DesignFormulaFunctionDefineService designFormulaFunctionDefineService;
    @Autowired
    private IDesignParamCheckService designParamCheckService;
    @Autowired
    private DesignFormulaConditionLinkService formulaConditionLinkService;
    @Autowired
    private DesignFormulaConditionService formulaConditionService;
    private final Logger logger = LogFactory.getLogger(this.getClass());
    private static final String FORM_FORMAT = "\u62a5\u8868\u3010%s|%s\u3011";

    @Override
    public DesignFormulaSchemeDefine createFormulaSchemeDefine() {
        DesignFormulaSchemeDefineImpl define = new DesignFormulaSchemeDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertFormulaSchemeDefine(DesignFormulaSchemeDefine formulaSchemeDefine) {
        String id = null;
        try {
            this.designParamCheckService.checkFormulaSchemeNameByType(formulaSchemeDefine);
            id = this.formulaSchemeService.insertFormulaSchemeDefine(formulaSchemeDefine);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public void updateFormulaSchemeDefine(DesignFormulaSchemeDefine formulaSchemeDefine) {
        try {
            this.designParamCheckService.checkFormulaSchemeNameByType(formulaSchemeDefine);
            this.formulaSchemeService.updateFormulaSchemeDefine(formulaSchemeDefine);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void checkFormulaTitle(DesignFormulaSchemeDefine formulaSchemeDefine) throws JQException {
        List<Object> list = Collections.emptyList();
        try {
            String formSchemeKey = formulaSchemeDefine.getFormSchemeKey();
            list = this.formulaSchemeService.queryFormulaSchemeDefineByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_062, (Throwable)e);
        }
        if (list.size() > 0) {
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : list) {
                if (formulaSchemeDefine.getFormulaSchemeType() != designFormulaSchemeDefine.getFormulaSchemeType() || !formulaSchemeDefine.getTitle().equals(designFormulaSchemeDefine.getTitle())) continue;
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_061);
            }
        }
    }

    @Override
    public void deleteFormulaSchemeDefine(String formulaSchemeID) {
        try {
            this.deleteFormulaDefinesByScheme(formulaSchemeID);
            this.formulaSchemeService.delete(formulaSchemeID);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void exchangeFormulaSchemeOrder(String orinFormulaSchemeKey, String targetFormulaSchemeKey) {
        try {
            DesignFormulaSchemeDefine orin = this.formulaSchemeService.queryFormulaSchemeDefine(orinFormulaSchemeKey);
            DesignFormulaSchemeDefine target = this.formulaSchemeService.queryFormulaSchemeDefine(targetFormulaSchemeKey);
            String oldOrder = orin.getOrder();
            orin.setOrder(target.getOrder());
            target.setOrder(oldOrder);
            this.formulaSchemeService.updateFormulaSchemeDefine(orin);
            this.formulaSchemeService.updateFormulaSchemeDefine(target);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public DesignFormulaSchemeDefine queryFormulaSchemeDefine(String formulaSchemeKey) {
        DesignFormulaSchemeDefine define = null;
        try {
            define = this.formulaSchemeService.queryFormulaSchemeDefine(formulaSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefines(String taskKey) {
        ArrayList<DesignFormulaSchemeDefine> list = new ArrayList<DesignFormulaSchemeDefine>();
        try {
            List<DesignFormSchemeDefine> formSchemes = this.formSchemeService.queryFormSchemeDefineByTaskKey(taskKey);
            for (DesignFormSchemeDefine scheme : formSchemes) {
                List<DesignFormulaSchemeDefine> defines = this.formulaSchemeService.queryFormulaSchemeDefineByFormScheme(scheme.getKey());
                list.addAll(defines);
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefinesByFormScheme(String fromSchemeKey) {
        List<Object> list = Collections.emptyList();
        try {
            list = this.formulaSchemeService.queryFormulaSchemeDefineByFormScheme(fromSchemeKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        list.sort(new MetaComparator());
        return list;
    }

    @Override
    public DesignFormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String fromSchemeKey) {
        DesignFormulaSchemeDefine define = null;
        try {
            List<DesignFormulaSchemeDefine> list = this.formulaSchemeService.queryFormulaSchemeDefineByFormScheme(fromSchemeKey);
            for (DesignFormulaSchemeDefine scheme : list) {
                if (!scheme.isDefault()) continue;
                define = scheme;
                break;
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
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
            this.addLog(new DesignFormulaDefine[]{formulaDefine}, "add", null);
            id = this.formulaService.insertFormulaDefine(formulaDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return id;
    }

    @Override
    public void updateFormulaDefine(DesignFormulaDefine formulaDefine) throws JQException {
        ArrayList formulaTrackDefines = new ArrayList();
        try {
            this.addLog(new DesignFormulaDefine[]{formulaDefine}, "update", null);
            this.formulaService.updateFormulaDefine(formulaDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaDefine(String formulaKey) {
        try {
            this.addLog(null, "delete", new String[]{formulaKey});
            this.formulaService.delete(formulaKey);
            this.deleteFormulaConditionLinkByFormula(formulaKey);
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
            define = this.querySoftFormulaDefine(formulaKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return define;
    }

    @Override
    public DesignFormulaDefine querySoftFormulaDefine(String formulaKey) throws JQException {
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
    public DesignFormulaDefine findFormulaDefineInFormulaScheme(String formulaDefineCode, String formulaSchemeKey) throws JQException {
        DesignFormulaDefine define = null;
        try {
            define = this.formulaService.queryFormulaDefineBySchemeAndCode(formulaDefineCode, formulaSchemeKey);
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

    @Override
    public List<DesignFormulaDefine> getAllSoftFormulasInScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            DesignFormulaSchemeDefine schemeDefine = this.queryFormulaSchemeDefine(formulaSchemeKey);
            List<DesignFormDefine> forms = this.nrDesignController.queryAllFormDefinesByFormScheme(schemeDefine.getFormSchemeKey());
            defines = this.formulaService.queryFormulaDefineByScheme(formulaSchemeKey, forms);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getAllFormulasInForm(String formulaSchemeKey, String formKey) throws JQException {
        List<DesignFormulaDefine> defines = Collections.emptyList();
        try {
            defines = this.getAllSoftFormulasInForm(formulaSchemeKey, formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getAllSoftFormulasInForm(String formulaSchemeKey, String formKey) throws JQException {
        List<DesignFormulaDefine> defines = Collections.emptyList();
        DesignFormDefine designFormDefine = null;
        try {
            if (StringUtils.isNotEmpty((String)formKey)) {
                designFormDefine = this.nrDesignController.queryFormByIdWithoutFormData(formKey);
            }
            defines = this.formulaService.queryFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey, designFormDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<String> insertFormulaDefines(DesignFormulaDefine[] formulaDefines) throws JQException {
        List<String> ids = null;
        try {
            this.addLog(formulaDefines, "add", null);
            ids = this.formulaService.insertFormulaDefines(formulaDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return ids;
    }

    @Override
    public List<String> insertFormulasNotAnalysis(DesignFormulaDefine[] formulaDefines) throws JQException {
        List<String> ids = null;
        try {
            this.addLog(formulaDefines, "add", null);
            ids = this.formulaService.insertFormulaDefines(formulaDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return ids;
    }

    @Override
    public void updateFormulaDefines(DesignFormulaDefine[] formulaDefines) throws JQException {
        try {
            this.addLog(formulaDefines, "update", null);
            this.formulaService.updateFormulaDefines(formulaDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
    }

    @Override
    public void updateFormulasNotAnalysis(DesignFormulaDefine[] formulaDefines) throws JQException {
        try {
            this.addLog(formulaDefines, "update", null);
            this.formulaService.updateFormulaDefines(formulaDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
    }

    @Override
    public void deleteFormulaDefines(String[] formulaKey) {
        try {
            this.addLog(null, "delete", formulaKey);
            this.formulaService.delete(formulaKey);
            this.deleteFormulaConditionLinkByFormula(formulaKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> createFormulaDefines(int count) {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        for (int i = 0; i < count; ++i) {
            DesignFormulaDefineImpl define = new DesignFormulaDefineImpl();
            define.setKey(UUIDUtils.getKey());
            defines.add(define);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> findFormulaDefinesInScheme(String[] formulaDefineCodes, String formulaSchemekey) throws JQException {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        try {
            for (int i = 0; i < formulaDefineCodes.length; ++i) {
                DesignFormulaDefine define = this.formulaService.queryFormulaDefineBySchemeAndCode(formulaDefineCodes[i], formulaSchemekey);
                defines.add(define);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getAllFormulasInForms(String formulaSchemeKey, String[] formKeys) {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        try {
            for (int i = 0; i < formKeys.length; ++i) {
                defines.addAll(this.getAllFormulasInForm(formulaSchemeKey, formKeys[i]));
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public void deleteFormulaDefinesByForm(String formKey) {
        try {
            List<DesignFormulaDefine> formulaDefines = this.formulaService.listFormulaDefineByForm(formKey);
            String[] keys = (String[])formulaDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
            this.deleteFormulaConditionLinkByFormula(keys);
            this.formulaService.deleteByForm(formKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteFormulaDefinesByFormInScheme(String formulaSchemeKey, String formKey) {
        try {
            this.formulaService.deleteBySchemeAndForm(formulaSchemeKey, formKey);
            List<DesignFormulaDefine> formulaDefines = this.formulaService.listFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey);
            this.deleteFormulaConditionLinkByFormula((String[])formulaDefines.stream().map(FormulaDefine::getCode).toArray(String[]::new));
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteFormulaDefinesByScheme(String formulaSchemeKey) {
        try {
            this.formulaService.deleteByScheme(formulaSchemeKey);
            this.deleteFormulaConditionLinkByScheme(formulaSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteFormulaDefinesByTask(String taskKey) {
        try {
            List<DesignFormSchemeDefine> formSchemes = this.formSchemeService.queryFormSchemeDefineByTaskKey(taskKey);
            for (DesignFormSchemeDefine scheme : formSchemes) {
                this.formulaService.deleteByScheme(scheme.getKey());
                this.deleteFormulaConditionLinkByScheme(scheme.getKey());
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignFormulaDefine> getCalculateFormulasInScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            DesignFormulaSchemeDefine schemeDefine = this.queryFormulaSchemeDefine(formulaSchemeKey);
            List<DesignFormDefine> forms = this.nrDesignController.queryAllFormDefinesByFormScheme(schemeDefine.getFormSchemeKey());
            defines = this.formulaService.queryCalcFormulaDefineByScheme(formulaSchemeKey, forms);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getCheckFormulasInScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            DesignFormulaSchemeDefine schemeDefine = this.queryFormulaSchemeDefine(formulaSchemeKey);
            List<DesignFormDefine> forms = this.nrDesignController.queryAllFormDefinesByFormScheme(schemeDefine.getFormSchemeKey());
            defines = this.formulaService.queryCheckFormulaDefineByScheme(formulaSchemeKey, forms);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getBalanceFormulasInScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            DesignFormulaSchemeDefine schemeDefine = this.queryFormulaSchemeDefine(formulaSchemeKey);
            List<DesignFormDefine> forms = this.nrDesignController.queryAllFormDefinesByFormScheme(schemeDefine.getFormSchemeKey());
            defines = this.formulaService.queryBalanceFormulaDefineByScheme(formulaSchemeKey, forms);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getCalculateFormulasInForm(String formulaSchemekey, String formkey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        DesignFormDefine designFormDefine = null;
        try {
            if (StringUtils.isNotEmpty((String)formkey)) {
                designFormDefine = this.nrDesignController.queryFormByIdWithoutFormData(formkey);
            }
            defines = this.formulaService.queryCalcFormulaDefineBySchemeAndForm(formulaSchemekey, formkey, designFormDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getCheckFormulasInForm(String formulaSchemekey, String formkey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        DesignFormDefine designFormDefine = null;
        try {
            if (StringUtils.isNotEmpty((String)formkey)) {
                designFormDefine = this.nrDesignController.queryFormByIdWithoutFormData(formkey);
            }
            defines = this.formulaService.queryCheckFormulaDefineBySchemeAndForm(formulaSchemekey, formkey, designFormDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormulaDefine> getBalanceFormulasInForm(String formulaSchemekey, String formkey) throws JQException {
        List<DesignFormulaDefine> defines = null;
        DesignFormDefine designFormDefine = null;
        try {
            if (StringUtils.isNotEmpty((String)formkey)) {
                designFormDefine = this.nrDesignController.queryFormByIdWithoutFormData(formkey);
            }
            defines = this.formulaService.queryBalanceFormulaDefineBySchemeAndForm(formulaSchemekey, formkey, designFormDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    private ExecutorContext initContext(String formulaSchemeKey) throws ParseException {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController2);
        context.setDesignTimeData(true, this.npDesignController);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormulaScheme(formulaSchemeKey);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        DesignFormulaSchemeDefine formulaSchemeDefine = this.queryFormulaSchemeDefine(formulaSchemeKey);
        List<FormulaVariDefine> formulaVariables = this.queryAllFormulaVariable(formulaSchemeDefine.getFormSchemeKey());
        DesignReportFmlExecEnvironment env = new DesignReportFmlExecEnvironment(this.nrDesignController, this.npDesignController, formulaSchemeDefine.getFormSchemeKey(), formulaVariables);
        env.setUseCache();
        context.setEnv((IFmlExecEnvironment)env);
        return context;
    }

    private ExecutorContext initContextByFomScheme(String formSchemeKey) throws ParseException {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController2);
        context.setDesignTimeData(true, this.npDesignController);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormScheme(formSchemeKey);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        List<FormulaVariDefine> formulaVariables = this.queryAllFormulaVariable(formSchemeKey);
        DesignReportFmlExecEnvironment env = new DesignReportFmlExecEnvironment(this.nrDesignController, this.npDesignController, formSchemeKey, formulaVariables);
        env.setUseCache();
        context.setEnv((IFmlExecEnvironment)env);
        return context;
    }

    private String transFromDataLinkExpression(ExecutorContext context, Map<String, String> formMap, DesignFormulaDTO designFormulaDTO, DataEngineConsts.FormulaShowType type, String formKey) {
        DesignFormulaDefine designFormulaDefine = designFormulaDTO.getDesignFormulaDefine();
        String formCode = null;
        if (formKey != null) {
            DesignFormDefine formDefine;
            if (formMap != null) {
                formCode = formMap.get(formKey);
                if (formCode == null && (formDefine = this.nrDesignController.queryFormById(formKey)) != null) {
                    formCode = formDefine.getFormCode();
                    formMap.put(formKey, formCode);
                }
            } else {
                formDefine = this.nrDesignController.queryFormById(formKey);
                if (formDefine != null) {
                    formCode = formDefine.getFormCode();
                }
            }
        }
        String expression = designFormulaDefine.getExpression();
        if (!StringUtils.isEmpty((String)designFormulaDTO.getDlExpression())) {
            try {
                expression = DataEngineFormulaParser.transFormulaStyleFromDataLink((ExecutorContext)context, (String)designFormulaDTO.getDlExpression(), (String)formCode, (DataEngineConsts.FormulaShowType)type);
            }
            catch (Exception e) {
                this.logger.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38" + e.getMessage(), (Throwable)e);
                designFormulaDTO.setSuccess(false);
                designFormulaDTO.setResultMes("\u516c\u5f0f\u89e3\u6790\u5931\u8d25");
            }
        }
        return expression;
    }

    private List<IParsedExpression> transToDataLinkExpression(ExecutorContext context, Map<String, String> formMap, DesignFormulaDefine designFormulaDefine) {
        String formKey = designFormulaDefine.getFormKey();
        String formCode = null;
        if (formKey != null) {
            DesignFormDefine formDefine;
            if (formMap != null) {
                formCode = formMap.get(formKey);
                if (formCode == null && (formDefine = this.nrDesignController.queryFormById(formKey)) != null) {
                    formCode = formDefine.getFormCode();
                    formMap.put(formKey, formCode);
                }
            } else {
                formDefine = this.nrDesignController.queryFormById(formKey);
                if (formDefine != null) {
                    formCode = formDefine.getFormCode();
                }
            }
        }
        ArrayList<IParsedExpression> iParsedExpressions = new ArrayList<IParsedExpression>();
        try {
            ArrayList<Formula> formulas = new ArrayList<Formula>();
            Formula formula = new Formula();
            formula.setId(designFormulaDefine.getKey());
            formula.setAutoCalc(false);
            formula.setChecktype(Integer.valueOf(designFormulaDefine.getCheckType()));
            formula.setCode(designFormulaDefine.getCode());
            formula.setFormKey(designFormulaDefine.getFormKey());
            formula.setFormula(designFormulaDefine.getExpression());
            formula.setMeanning(designFormulaDefine.getDescription());
            formula.setOrder(designFormulaDefine.getOrder());
            formula.setReportName(formCode);
            formulas.add(formula);
            if (designFormulaDefine.getUseCheck()) {
                List iParsedExpressions1 = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK);
                iParsedExpressions.addAll(iParsedExpressions1);
            }
            if (designFormulaDefine.getUseCalculate()) {
                iParsedExpressions.addAll(DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE));
            }
        }
        catch (Exception e) {
            this.logger.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38", (Throwable)e);
        }
        return iParsedExpressions;
    }

    private List<IParsedExpression> transToDataLinkFormulas(ExecutorContext context, Map<String, String> formMap, List<DesignFormulaDefine> designFormulaDefines) {
        ArrayList<IParsedExpression> iParsedExpressions = new ArrayList<IParsedExpression>();
        List<DesignFormulaDefine> checkFormulas = designFormulaDefines.stream().filter(item -> item.getUseCheck()).collect(Collectors.toList());
        List<DesignFormulaDefine> calculateFormulas = designFormulaDefines.stream().filter(item -> item.getUseCalculate()).collect(Collectors.toList());
        iParsedExpressions.addAll(this.parsedExpression(checkFormulas, formMap, true, context));
        iParsedExpressions.addAll(this.parsedExpression(calculateFormulas, formMap, false, context));
        return iParsedExpressions;
    }

    private List<IParsedExpression> parsedExpression(List<DesignFormulaDefine> designFormulaDefines, Map<String, String> formMap, boolean isCheckType, ExecutorContext context) {
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for (DesignFormulaDefine designFormulaDefine : designFormulaDefines) {
            String formKey = designFormulaDefine.getFormKey();
            String formCode = null;
            if (formKey != null) {
                DesignFormDefine formDefine;
                if (formMap != null) {
                    formCode = formMap.get(formKey);
                    if (formCode == null && (formDefine = this.nrDesignController.queryFormById(formKey)) != null) {
                        formCode = formDefine.getFormCode();
                        formMap.put(formKey, formCode);
                    }
                } else {
                    formDefine = this.nrDesignController.queryFormById(formKey);
                    if (formDefine != null) {
                        formCode = formDefine.getFormCode();
                    }
                }
            }
            Formula formula = new Formula();
            formula.setId(designFormulaDefine.getKey());
            formula.setAutoCalc(false);
            formula.setChecktype(Integer.valueOf(designFormulaDefine.getCheckType()));
            formula.setCode(designFormulaDefine.getCode());
            formula.setFormKey(designFormulaDefine.getFormKey());
            formula.setFormula(designFormulaDefine.getExpression());
            formula.setMeanning(designFormulaDefine.getDescription());
            formula.setOrder(designFormulaDefine.getOrder());
            formula.setReportName(formCode);
            formulas.add(formula);
        }
        List iParsedExpressions1 = null;
        try {
            iParsedExpressions1 = isCheckType ? DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK) : DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE);
        }
        catch (ParseException e) {
            this.logger.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38", (Throwable)e);
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return iParsedExpressions1;
    }

    public void fillExpression(String formulaSchemeKey, List<DesignFormulaDTO> designFormulaDTOS) throws ParseException {
        if (designFormulaDTOS == null || designFormulaDTOS.size() == 0) {
            return;
        }
        HashMap<String, String> formMap = new HashMap<String, String>();
        ExecutorContext context = this.initContext(formulaSchemeKey);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormulaScheme(formulaSchemeKey);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        for (DesignFormulaDTO designFormulaDTO : designFormulaDTOS) {
            String formKey = designFormulaDTO.getDesignFormulaDefine().getFormKey();
            if (!designFormulaDTO.isSuccess()) {
                designFormulaDTO.setNewExpression(designFormulaDTO.getOldExpression());
                continue;
            }
            String expression = this.transFromDataLinkExpression(context, formMap, designFormulaDTO, type, formKey);
            this.transFormulaConditionExpression(context, (String)formMap.get(formKey), designFormulaDTO.getConditions(), type);
            DesignFormulaDefine designFormulaDefine = designFormulaDTO.getDesignFormulaDefine();
            if (designFormulaDefine.getUseBalance() && StringUtils.isNotEmpty((String)designFormulaDefine.getBalanceZBExp())) {
                String otherFormKey = designFormulaDTO.getFormKey();
                String dataLinkUniqueCode = designFormulaDTO.getUniqueCode();
                String oldBalanceExp = designFormulaDTO.getOldBalanceExp();
                if (StringUtils.isNotEmpty((String)otherFormKey) && StringUtils.isNotEmpty((String)dataLinkUniqueCode) && StringUtils.isNotEmpty((String)oldBalanceExp)) {
                    String newDataLinkColAndRow = this.getNEWDataLinkColAndRow(otherFormKey, dataLinkUniqueCode, oldBalanceExp);
                    designFormulaDefine.setBalanceZBExp(newDataLinkColAndRow);
                    designFormulaDTO.setNewBalanceExp(newDataLinkColAndRow);
                }
            }
            if (!StringUtils.isEmpty((String)expression)) {
                designFormulaDefine.setExpression(expression);
            }
            designFormulaDTO.setNewExpression(designFormulaDefine.getExpression());
        }
    }

    private void transFormulaConditionExpression(ExecutorContext context, String formCode, List<FormulaConditionDTO> conditions, DataEngineConsts.FormulaShowType type) {
        for (FormulaConditionDTO conditionDTO : conditions) {
            String oldExpress = conditionDTO.getOldExpress();
            if (oldExpress == null) continue;
            try {
                conditionDTO.setNewExpress(DataEngineFormulaParser.transFormulaStyleFromDataLink((ExecutorContext)context, (String)oldExpress, (String)formCode, (DataEngineConsts.FormulaShowType)type));
            }
            catch (ParseException e) {
                this.logger.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38" + e.getMessage(), (Throwable)e);
                conditionDTO.setNewExpress(null);
            }
        }
    }

    public String getNEWDataLinkColAndRow(String formKey, String uniqueCode, String oldBalanceExp) {
        DesignDataLinkDefine designDataLinkDefine = this.dataLinkDefineService.queryDataLinkDefineByUniquecode(formKey, uniqueCode);
        int colIndex = designDataLinkDefine.getColNum();
        int rowIndex = designDataLinkDefine.getRowNum();
        String newBalanceExp = "[" + rowIndex + "," + colIndex + "]";
        String afterChange = oldBalanceExp.replaceAll("(\\[\\d+\\,\\d+\\])", newBalanceExp);
        return afterChange;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(String schemeKey) {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.queryFormulaSchemeDefine(schemeKey);
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignController.queryFormSchemeDefine(formulaSchemeDefine.getFormSchemeKey());
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormScheme(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignController.queryFormSchemeDefine(formSchemeKey);
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    @Override
    public Map<String, Integer> getFormulaCodeCountByScheme(String formulaSchemeKey) throws JQException {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        if (formulaSchemeKey == null) {
            return result;
        }
        try {
            List<DesignFormulaDefine> formulaDefines = this.querySimpleFormulaDefineByScheme(formulaSchemeKey);
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
    public List<DesignFormulaSchemeDefine> getAllFormulaSchemeDefines() {
        List<Object> list = Collections.emptyList();
        try {
            list = this.formulaSchemeService.queryAllFormulaSchemeDefine();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        list.sort(new MetaComparator());
        return list;
    }

    @Override
    public List<DesignFormulaDefine> getAllFormulas() throws JQException {
        List<DesignFormulaDefine> defines = null;
        try {
            defines = this.formulaService.queryAllFormulaDefine();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return defines;
    }

    @Override
    public List<FormulaVariDefine> queryAllFormulaVariable(String formSchemeKey) {
        return this.designFormulaVariableDefineService.queryAllFormulaVariable(formSchemeKey);
    }

    @Override
    public List<FormulaVariDefine> queryFormulaVariableByCodeOrTitle(String formSchemeKey, String searchContent) {
        List<FormulaVariDefine> queryFormulaVariableByCodeOrTitle = null;
        try {
            queryFormulaVariableByCodeOrTitle = this.designFormulaVariableDefineService.queryFormulaVariableByCodeOrTitle(formSchemeKey, searchContent);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return queryFormulaVariableByCodeOrTitle;
    }

    @Override
    public List<FormulaVariDefine> queryFormulaVariableByCode(String formSchemeKey, String code) {
        return this.designFormulaVariableDefineService.queryFormulaVariableByCode(formSchemeKey, code);
    }

    @Override
    public void addFormulaVariable(FormulaVariDefine designFormulaVariableDefine) throws JQException {
        List<FormulaVariDefine> queryFormulaVariableByCode = this.queryFormulaVariableByCode(designFormulaVariableDefine.getFormSchemeKey(), designFormulaVariableDefine.getCode());
        if (queryFormulaVariableByCode.size() > 0) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_057);
        }
        try {
            this.designFormulaVariableDefineService.addFormulaVariable(designFormulaVariableDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_054);
        }
    }

    @Override
    public void updateFormulaVariable(FormulaVariDefine designFormulaVariableDefine) throws JQException {
        List<FormulaVariDefine> queryFormulaVariableByCode = this.queryFormulaVariableByCode(designFormulaVariableDefine.getFormSchemeKey(), designFormulaVariableDefine.getCode());
        if (queryFormulaVariableByCode.size() != 0 && !queryFormulaVariableByCode.get(0).getKey().equals(designFormulaVariableDefine.getKey())) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_057);
        }
        try {
            this.designFormulaVariableDefineService.updateFormulaVariable(designFormulaVariableDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_055);
        }
    }

    @Override
    public void deleteFormulaVariable(String formulaVariableKey) throws JQException {
        try {
            this.designFormulaVariableDefineService.deleteFormulaVariable(formulaVariableKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_056);
        }
    }

    @Override
    public int getBJFormulaCountByFormulaSchemeKey(String formulaSchemeKey, String formKey) {
        return this.formulaService.getBJFormulaCountByFormulaSchemeKey(formulaSchemeKey, formKey);
    }

    @Override
    public List<FormulaFunctionDefine> queryAllFormulaFunction() {
        return this.designFormulaFunctionDefineService.queryAllFormulaFunction();
    }

    @Override
    public List<FormulaFunctionDefine> queryAllFormulaFunctionByType(int type) {
        return this.designFormulaFunctionDefineService.queryAllFormulaFunctionByType(type);
    }

    @Override
    public List<FormulaFunctionParamterDefine> queryAllFormulaFunctionParamter() {
        return this.designFormulaFunctionDefineService.queryAllFormulaFunctionParamter();
    }

    @Override
    public void insertFormulaFunction(FormulaFunctionDefine[] formulaFunctionDefine) throws JQException {
        try {
            this.designFormulaFunctionDefineService.insertFormulaFunction(formulaFunctionDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_057);
        }
    }

    @Override
    public void insertFormulaFunctionParamter(FormulaFunctionParamterDefine[] formulaFunctionParamterDefine) throws JQException {
        try {
            this.designFormulaFunctionDefineService.insertFormulaFunctionParamter(formulaFunctionParamterDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_058);
        }
    }

    @Override
    public List<DesignFormulaDTO> parseFormulaExpressionBeforeFormSave(String formKey, String formCode, String formulaSchemeKey) {
        ArrayList<DesignFormulaDTO> formulaDTOS = new ArrayList<DesignFormulaDTO>();
        try {
            List<DesignFormulaDefine> allFormulasInForm = this.designTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
            List<DesignFormulaDefine> allBJFormulas = this.designFormulaDefineDao.queryBJFormulaBySchemeAndForm(formulaSchemeKey, formKey, formCode);
            ArrayList<DesignFormulaDefine> allFormulas = new ArrayList<DesignFormulaDefine>(allFormulasInForm);
            allFormulas.addAll(allBJFormulas);
            DesignFormulaSchemeDefine scheme = this.queryFormulaSchemeDefine(formulaSchemeKey);
            Map<String, List<DesignFormulaCondition>> cMap = this.listFormulaConditionBySchemeAndFormula(formulaSchemeKey, (String[])allFormulas.stream().map(IBaseMetaItem::getKey).toArray(String[]::new));
            String title = scheme.getTitle();
            String order = scheme.getOrder();
            HashMap<String, String> formMap = new HashMap<String, String>();
            try {
                ExecutorContext context = this.initContext(formulaSchemeKey);
                for (DesignFormulaDefine designFormulaDefine : allFormulas) {
                    DesignFormulaDTO designFormulaDTO;
                    DesignFormDefine formDefine;
                    String formulaFormCode = null;
                    String formulaFormKey = designFormulaDefine.getFormKey();
                    if (StringUtils.isNotEmpty((String)formulaFormKey) && (formulaFormCode = (String)formMap.get(formulaFormKey)) == null && (formDefine = this.nrDesignController.querySoftFormDefine(formulaFormKey)) != null) {
                        formulaFormCode = formDefine.getFormCode();
                        formMap.put(formulaFormKey, formulaFormCode);
                    }
                    try {
                        DesignFormulaDTO formulaDTO;
                        String dlExpression = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)context, (String)designFormulaDefine.getExpression(), (String)formulaFormCode, (DataEngineConsts.FormulaShowType)DataEngineConsts.FormulaShowType.DATALINK);
                        designFormulaDTO = new DesignFormulaDTO(designFormulaDefine, dlExpression, "\u6b63\u5e38", true);
                        this.transFormulaConditionStyle(designFormulaDTO, cMap, context, formulaFormCode);
                        if (designFormulaDefine.getUseBalance() && StringUtils.isNotEmpty((String)designFormulaDefine.getBalanceZBExp()) && (formulaDTO = this.parseBalanceFormulaExpBeforeFormSave(formKey, designFormulaDefine)) != null) {
                            designFormulaDTO.setUniqueCode(formulaDTO.getUniqueCode());
                            designFormulaDTO.setOldBalanceExp(formulaDTO.getOldBalanceExp());
                            designFormulaDTO.setFormKey(formulaDTO.getFormKey());
                        }
                        designFormulaDTO.setOldExpression(designFormulaDefine.getExpression());
                        designFormulaDTO.setFormulaSchemeTitle(title);
                        designFormulaDTO.setFormulaSchemeOrder(order);
                        formulaDTOS.add(designFormulaDTO);
                    }
                    catch (ParseException e) {
                        designFormulaDTO = new DesignFormulaDTO(designFormulaDefine, null, "\u516c\u5f0f\u65e0\u6cd5\u66f4\u65b0\u3010\u6570\u636e\u94fe\u63a5\u88ab\u79fb\u9664\u7b49\u539f\u56e0\u3011\uff0c\u5c06\u4fdd\u6301\u539f\u5185\u5bb9\u4e0d\u53d8\u3002", false);
                        designFormulaDTO.setOldExpression(designFormulaDefine.getExpression());
                        designFormulaDTO.setFormulaSchemeTitle(title);
                        designFormulaDTO.setFormulaSchemeOrder(order);
                        formulaDTOS.add(designFormulaDTO);
                        this.logger.error("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38" + e.getMessage(), (Throwable)e);
                    }
                }
            }
            catch (ParseException e) {
                for (DesignFormulaDefine designFormulaDefine : allFormulas) {
                    DesignFormulaDTO designFormulaDTO = new DesignFormulaDTO(designFormulaDefine, null, "\u516c\u5f0f\u65e0\u6cd5\u66f4\u65b0\u3010\u6570\u636e\u94fe\u63a5\u88ab\u79fb\u9664\u7b49\u539f\u56e0\u3011\uff0c\u5c06\u4fdd\u6301\u539f\u5185\u5bb9\u4e0d\u53d8\u3002", false);
                    designFormulaDTO.setOldExpression(designFormulaDefine.getExpression());
                    designFormulaDTO.setFormulaSchemeTitle(title);
                    designFormulaDTO.setFormulaSchemeOrder(order);
                    formulaDTOS.add(designFormulaDTO);
                }
                this.logger.error("\u516c\u5f0f\u65b9\u6848\u521d\u59cb\u5316\u4e0a\u4e0b\u6587\u5f02\u5e38" + e.getMessage(), (Throwable)e);
            }
        }
        catch (JQException e) {
            this.logger.error("\u516c\u5f0f\u67e5\u8be2\u5f02\u5e38");
        }
        return formulaDTOS;
    }

    private void transFormulaConditionStyle(DesignFormulaDTO designFormulaDTO, Map<String, List<DesignFormulaCondition>> cMap, ExecutorContext context, String formulaFormCode) {
        String key = designFormulaDTO.getDesignFormulaDefine().getKey();
        List<DesignFormulaCondition> conditions = cMap.get(key);
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        for (DesignFormulaCondition condition : conditions) {
            FormulaConditionDTO formulaConditionDTO;
            try {
                String exp = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)context, (String)condition.getFormulaCondition(), (String)formulaFormCode, (DataEngineConsts.FormulaShowType)DataEngineConsts.FormulaShowType.DATALINK);
                formulaConditionDTO = new FormulaConditionDTO(key, condition, exp);
            }
            catch (ParseException e) {
                formulaConditionDTO = new FormulaConditionDTO(key, condition, null);
            }
            designFormulaDTO.getConditions().add(formulaConditionDTO);
        }
    }

    public DesignFormulaDTO parseBalanceFormulaExpBeforeFormSave(String formKey, DesignFormulaDefine formula) {
        DesignFormulaDTO formulaDTO;
        DesignFormDefine designFormDefine = this.designTimeController.queryFormById(formKey);
        String regex = "^([A-Z0-9]*)(\\[\\d+\\,\\d+\\])$";
        String balanceZBExp = formula.getBalanceZBExp();
        balanceZBExp.replace(" ", "");
        if (Pattern.matches(regex, balanceZBExp) && (formulaDTO = this.getDataLinkDefineByBalanceZBExp(designFormDefine.getFormScheme(), formKey, balanceZBExp, formula)) != null) {
            return formulaDTO;
        }
        return null;
    }

    public DesignFormulaDTO getDataLinkDefineByBalanceZBExp(String formSchemeKey, String formKey, String balanceZBExp, DesignFormulaDefine formula) {
        DesignDataLinkDefine designDataLinkDefine;
        DesignFormDefine designFormDefine = null;
        DesignFormulaDTO formulaDTO = null;
        int index1 = balanceZBExp.indexOf("[");
        int index2 = balanceZBExp.indexOf("]");
        int index3 = balanceZBExp.indexOf(",");
        String formcode = balanceZBExp.substring(0, index1);
        int rowIndex = Integer.parseInt(balanceZBExp.substring(index1 + 1, index3));
        int colIndex = Integer.parseInt(balanceZBExp.substring(index3 + 1, index2));
        if (StringUtils.isEmpty((String)formcode)) {
            designDataLinkDefine = this.dataLinkDefineService.queryDataLinkDefineByColRow(formKey, colIndex, rowIndex);
        } else {
            designFormDefine = this.designTimeController.queryFormByCodeInFormScheme(formSchemeKey, formcode);
            designDataLinkDefine = this.dataLinkDefineService.queryDataLinkDefineByColRow(designFormDefine.getKey(), colIndex, rowIndex);
        }
        if (designDataLinkDefine != null) {
            formulaDTO = new DesignFormulaDTO();
            formulaDTO.setUniqueCode(designDataLinkDefine.getUniqueCode());
            formulaDTO.setOldBalanceExp(balanceZBExp);
            if (designFormDefine == null) {
                formulaDTO.setFormKey(formKey);
            } else {
                formulaDTO.setFormKey(designFormDefine.getKey());
            }
        }
        return formulaDTO;
    }

    @Override
    public List<DesignFormulaDefine> querySimpleFormulaDefineByScheme(String formulaSchemeKey) throws JQException {
        List<DesignFormulaDefine> formulaDefines = new ArrayList<DesignFormulaDefine>();
        if (StringUtils.isEmpty((String)formulaSchemeKey)) {
            return formulaDefines;
        }
        try {
            formulaDefines = this.formulaService.querySimpleFormulaDefineByScheme(formulaSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_029, (Throwable)e);
        }
        return formulaDefines;
    }

    private DesignFormulaSchemeDefine getFormulaSchemeDefine(Map<String, DesignFormulaSchemeDefine> formulaSchemeDefineMap, String key) throws Exception {
        DesignFormulaSchemeDefine formulaSchemeDefine = null;
        if (null == formulaSchemeDefineMap.get(key)) {
            formulaSchemeDefineMap.put(key, this.formulaSchemeService.queryFormulaSchemeDefine(key));
            formulaSchemeDefine = formulaSchemeDefineMap.get(key);
        } else {
            formulaSchemeDefine = formulaSchemeDefineMap.get(key);
        }
        return formulaSchemeDefine;
    }

    private DesignFormSchemeDefine getFormSchemeDefine(Map<String, DesignFormSchemeDefine> formSchemeDefineMap, String key) throws Exception {
        DesignFormSchemeDefine formSchemeDefine = null;
        if (null == formSchemeDefineMap.get(key)) {
            formSchemeDefineMap.put(key, this.designTimeController.queryFormSchemeDefine(key));
            formSchemeDefine = formSchemeDefineMap.get(key);
        } else {
            formSchemeDefine = formSchemeDefineMap.get(key);
        }
        return formSchemeDefine;
    }

    private DesignTaskDefine getTaskDefine(Map<String, DesignTaskDefine> taskDefineMap, String key) throws Exception {
        DesignTaskDefine taskDefine = null;
        if (null == taskDefineMap.get(key)) {
            taskDefineMap.put(key, this.designTimeController.queryTaskDefine(key));
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
            formDefineMap.put(key, this.designTimeController.queryFormById(key));
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

    private void addLog(DesignFormulaDefine[] designFormulaDefine, String type, String[] formulaKey) {
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
                            DesignFormDefine formDefine;
                            DesignFormulaDefine formulaDefine = formulaGroup.get(formulaSchemeKey).get(i);
                            if (null != formulaDefine.getFormKey() && (formDefine = this.getFormDefine(formDefineMap, formulaDefine.getFormKey())) != null) {
                                sbf.append(String.format(FORM_FORMAT, formDefine.getFormCode(), formDefine.getTitle()));
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
                                sbf.append(String.format(FORM_FORMAT, formDefine.getFormCode(), formDefine.getTitle()));
                            }
                            DesignFormulaDefine oldFormulaDefine = this.formulaService.queryFormulaDefine(formulaDefine.getKey());
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
                    List<DesignFormulaDefine> formulaDefines = this.formulaService.queryFormulaDefines(formulaKey);
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
                                sbf.append(String.format(FORM_FORMAT, formDefine.getFormCode(), formDefine.getTitle()));
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
            this.logger.error("\u516c\u5f0f\u4fee\u6539\u6dfb\u52a0\u65e5\u5fd7\u5931\u8d25", (Throwable)e);
        }
    }

    @Override
    public List<DesignFormulaConditionLink> listConditionLinkByScheme(String formulaScheme) {
        return this.formulaConditionLinkService.listConditionLinkByScheme(formulaScheme);
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByTask(String task) {
        return this.formulaConditionService.listFormulaConditionByTask(task);
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByKey(List<String> keys) {
        return this.formulaConditionService.listFormulaConditionByKey(keys);
    }

    @Override
    public DesignFormulaCondition initFormulaCondition() {
        DesignFormulaConditionImpl designFormulaCondition = new DesignFormulaConditionImpl();
        designFormulaCondition.setKey(UUID.randomUUID().toString());
        designFormulaCondition.setOrder(OrderGenerator.newOrder());
        designFormulaCondition.setUpdateTime(new Date());
        return designFormulaCondition;
    }

    @Override
    public void updateFormulaCondition(DesignFormulaCondition condition) {
        this.formulaConditionService.updateFormulaCondition(condition);
    }

    @Override
    public void deleteFormulaCondition(String key) {
        this.formulaConditionService.deleteFormulaCondition(key);
        this.deleteFormulaConditionLinkByCondition(key);
    }

    @Override
    public void insertFormulaCondition(DesignFormulaCondition designFormulaCondition) {
        this.formulaConditionService.insertFormulaCondition(designFormulaCondition);
    }

    @Override
    public DesignFormulaConditionLink initDesignFormulaConditionLink() {
        return new DesignFormulaConditionLinkImpl();
    }

    @Override
    public void deleteFormulaConditionLinks(List<DesignFormulaConditionLink> designFormulaConditionLinks) {
        if (!CollectionUtils.isEmpty(designFormulaConditionLinks)) {
            this.formulaConditionLinkService.delete(designFormulaConditionLinks);
        }
    }

    @Override
    public void insertFormulaConditionLinks(List<DesignFormulaConditionLink> designFormulaConditionLinks) {
        if (CollectionUtils.isEmpty(designFormulaConditionLinks)) {
            return;
        }
        DesignFormulaConditionLink link = designFormulaConditionLinks.get(0);
        HashSet<DesignFormulaConditionLink> conditionLinks = new HashSet<DesignFormulaConditionLink>(this.formulaConditionLinkService.listConditionLinkByScheme(link.getFormulaSchemeKey()));
        this.formulaConditionLinkService.insert(designFormulaConditionLinks.stream().filter(l -> !conditionLinks.contains(l)).collect(Collectors.toList()));
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByTask(String task, Long start, Long num) {
        List<DesignFormulaCondition> designFormulaConditions = this.listFormulaConditionByTask(task);
        if (start == null || num == null) {
            return designFormulaConditions;
        }
        return designFormulaConditions.stream().skip((start - 1L) * num).limit(num).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
    }

    @Override
    public void updateFormulaConditions(List<DesignFormulaCondition> collect) {
        if (CollectionUtils.isEmpty(collect)) {
            return;
        }
        this.formulaConditionService.updateFormulaConditions(collect);
    }

    @Override
    public List<DesignFormulaConditionLink> listConditionLinksByCondition(List<String> conditionKeys) {
        return this.formulaConditionLinkService.listConditionLinkByCondition(conditionKeys);
    }

    @Override
    public void deleteFormulaConditionByTask(String task) {
        List<DesignFormulaCondition> conditions = this.formulaConditionService.listFormulaConditionByTask(task);
        String[] keys = (String[])conditions.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
        this.deleteFormulaConditionLinkByCondition(keys);
        this.formulaConditionService.deleteFormulaConditionByTask(task);
    }

    @Override
    public void insertFormulaConditions(List<DesignFormulaCondition> conditions) {
        this.formulaConditionService.insertFormulaConditions(conditions);
    }

    @Override
    public void deleteFormulaConditions(List<String> keys) {
        this.formulaConditionService.deleteFormulaConditions(keys);
        this.deleteFormulaConditionLinkByCondition(keys.toArray(new String[0]));
    }

    @Override
    public void deleteFormulaConditionLinkByScheme(String formulaScheme) {
        this.formulaConditionLinkService.deleteByScheme(formulaScheme);
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
    public void deleteFormulaConditionLinkByFormula(String ... formulaKey) {
        this.formulaConditionLinkService.deleteByFormula(formulaKey);
    }

    @Override
    public void deleteFormulaConditionLinkByCondition(String ... condition) {
        this.formulaConditionLinkService.deleteByConditionKey(condition);
    }

    @Override
    public List<DesignFormulaCondition> listFormulaConditionByScheme(String formulaScheme) {
        return this.listFormulaConditionByKey(this.listConditionLinkByScheme(formulaScheme).stream().map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList()));
    }

    @Override
    public Map<String, List<DesignFormulaCondition>> listFormulaConditionBySchemeAndFormula(String formulaScheme, String ... formula) {
        if (formula == null || formula.length == 0) {
            return Collections.emptyMap();
        }
        List<DesignFormulaConditionLink> conditionLinks = this.listConditionLinkByScheme(formulaScheme);
        Map fMap = conditionLinks.stream().collect(Collectors.groupingBy(FormulaConditionLink::getFormulaKey, Collectors.mapping(FormulaConditionLink::getConditionKey, Collectors.toList())));
        Map<String, DesignFormulaCondition> cMap = this.listFormulaConditionByKey(fMap.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList())).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        HashMap<String, List<DesignFormulaCondition>> res = new HashMap<String, List<DesignFormulaCondition>>();
        for (Map.Entry entry : fMap.entrySet()) {
            ArrayList<DesignFormulaCondition> temp = new ArrayList<DesignFormulaCondition>();
            for (String s : entry.getValue()) {
                if (!cMap.containsKey(s)) continue;
                temp.add(cMap.get(s));
            }
            res.put(entry.getKey(), temp);
        }
        return res;
    }
}

