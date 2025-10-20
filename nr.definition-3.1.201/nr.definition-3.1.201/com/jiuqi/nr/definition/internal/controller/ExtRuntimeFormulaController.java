/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.definition.controller.IExtFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExtFormulaService;
import com.jiuqi.nr.definition.internal.runtime.service.ExtRuntimeExpressionServiceV2;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Primary
class ExtRuntimeFormulaController
implements IExtFormulaRunTimeController {
    private static final Logger logger = LoggerFactory.getLogger(ExtRuntimeFormulaController.class);
    @Autowired
    private IRuntimeExtFormulaService extFormulaService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private ExtRuntimeExpressionServiceV2 expressionService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private SystemIdentityService systemIdentityService;

    ExtRuntimeFormulaController() {
    }

    @Override
    public List<FormulaUnitGroup> get(List<String> unitCodes, String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        ArrayList<FormulaUnitGroup> formulaUnitGroupList = new ArrayList<FormulaUnitGroup>();
        if (!StringUtils.hasLength(formulaSchemeKey)) {
            return formulaUnitGroupList;
        }
        FormulaUnitGroup unitGroup = new FormulaUnitGroup();
        FormulaSchemeDefine formulaSchemeDefine = this.iFormulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (null == formulaSchemeDefine) {
            return formulaUnitGroupList;
        }
        if (DataEngineConsts.FormulaType.CALCULATE.equals((Object)formulaType)) {
            unitGroup.setUnit(null);
            ArrayList<IParsedExpression> iParsedExpressionList = new ArrayList<IParsedExpression>();
            List<IParsedExpression> parsedExpressionbj = this.iFormulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CALCULATE);
            iParsedExpressionList.addAll(parsedExpressionbj);
            unitGroup.setFormulaList(iParsedExpressionList);
            formulaUnitGroupList.add(unitGroup);
        } else if (DataEngineConsts.FormulaType.BALANCE.equals((Object)formulaType)) {
            unitGroup.setUnit(null);
            ArrayList<IParsedExpression> iParsedExpressionList = new ArrayList<IParsedExpression>();
            List<IParsedExpression> parsedExpressionbj = this.iFormulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.BALANCE);
            iParsedExpressionList.addAll(parsedExpressionbj);
            unitGroup.setFormulaList(iParsedExpressionList);
            formulaUnitGroupList.add(unitGroup);
        } else {
            unitGroup.setUnit(null);
            ArrayList<IParsedExpression> iParsedExpressionList = new ArrayList<IParsedExpression>();
            List<IParsedExpression> parsedExpressionbj = this.iFormulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
            iParsedExpressionList.addAll(parsedExpressionbj);
            unitGroup.setFormulaList(iParsedExpressionList);
            formulaUnitGroupList.add(unitGroup);
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formulaSchemeDefine.getFormSchemeKey());
            String userName = NpContextHolder.getContext().getUserName();
            if (null == formScheme || !this.existPrivateFormula() || this.systemIdentityService.isAdmin()) {
                return formulaUnitGroupList;
            }
            if (null == NpContextHolder.getContext().getOrganization()) {
                return formulaUnitGroupList;
            }
            List<String> unitList = unitCodes;
            String code = NpContextHolder.getContext().getOrganization().getCode();
            IEntityTable iEntityTable = this.getIEntityTable(formScheme.getDw());
            if (null == iEntityTable) {
                logger.error("\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u5668\u521d\u59cb\u5316\u5931\u8d25");
                return formulaUnitGroupList;
            }
            List<String> parentPath = this.getParentPath(iEntityTable, formScheme.getDw(), code);
            if (null != unitList && unitList.size() != 0) {
                String OrgCode;
                HashSet<String> unitNodes;
                Set noRepeatUnits = unitList.stream().filter(t -> StringUtils.hasLength(t)).collect(Collectors.toSet());
                HashMap<String, Object> parentUnitMap = new HashMap<String, Object>();
                if (parentPath.size() != 0) {
                    for (String unit : noRepeatUnits) {
                        if (parentPath.contains(unit)) {
                            List<String> parentPath1 = this.getParentPath(iEntityTable, formScheme.getDw(), unit);
                            HashSet unitNodes2 = new HashSet();
                            for (String s : parentPath1) {
                                String orgCodeP = this.codeToOrgCode(iEntityTable, s);
                                if (null == orgCodeP) continue;
                                unitNodes2.add(orgCodeP);
                            }
                            String string = this.codeToOrgCode(iEntityTable, unit);
                            if (null != string) {
                                unitNodes2.add(string);
                            }
                            parentUnitMap.put(unit, unitNodes2);
                            continue;
                        }
                        unitNodes = new HashSet();
                        for (String string : parentPath) {
                            String orgCodeP = this.codeToOrgCode(iEntityTable, string);
                            if (null == orgCodeP) continue;
                            unitNodes.add(orgCodeP);
                        }
                        OrgCode = this.codeToOrgCode(iEntityTable, code);
                        if (null != OrgCode) {
                            unitNodes.add(OrgCode);
                        }
                        parentUnitMap.put(unit, unitNodes);
                    }
                } else {
                    for (String unit : noRepeatUnits) {
                        unitNodes = new HashSet<String>();
                        OrgCode = this.codeToOrgCode(iEntityTable, code);
                        if (null != OrgCode) {
                            unitNodes.add(OrgCode);
                        }
                        parentUnitMap.put(unit, unitNodes);
                    }
                }
                for (String noRepeatUnit : noRepeatUnits) {
                    Set units = (Set)parentUnitMap.get(noRepeatUnit);
                    ArrayList<IParsedExpression> currAndParents = new ArrayList<IParsedExpression>();
                    if (null == units || units.size() == 0) continue;
                    for (String unit : units) {
                        List<IParsedExpression> parses = this.expressionService.getParsedExpressionByUnit(formulaSchemeKey, unit);
                        if (null == parses || parses.size() == 0) continue;
                        currAndParents.addAll(parses);
                    }
                    if (currAndParents.size() == 0) continue;
                    FormulaUnitGroup formulaUnitGroup = new FormulaUnitGroup();
                    formulaUnitGroup.setUnit(noRepeatUnit);
                    formulaUnitGroup.setFormulaList(currAndParents);
                    formulaUnitGroupList.add(formulaUnitGroup);
                }
            }
        }
        return formulaUnitGroupList;
    }

    private String codeToOrgCode(IEntityTable iEntityTable, String code) {
        IEntityRow byEntityKey = iEntityTable.findByEntityKey(code);
        if (null != byEntityKey) {
            return byEntityKey.getCode();
        }
        return null;
    }

    private IEntityTable getIEntityTable(String dw) {
        IEntityTable iEntityTable = null;
        try {
            RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
            viewDefine.setEntityId(dw);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
            ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
            context.setPeriodView(dw);
            iEntityTable = iEntityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53\u6846\u67b6\u521d\u59cb\u5316\u5931\u8d25\u3002", e);
        }
        return iEntityTable;
    }

    private List<String> getParentPath(IEntityTable iEntityTable, String dw, String orgid) {
        ArrayList<String> parentPath = new ArrayList<String>();
        try {
            IEntityRow row = iEntityTable.findByEntityKey(orgid);
            if (row != null) {
                String[] parentsEntityKeyDataPath;
                for (String s : parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath()) {
                    if (!StringUtils.hasLength(s)) continue;
                    parentPath.add(s);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u79c1\u6709\u516c\u5f0f\u4e0a\u7ea7\u5355\u4f4d\u6784\u5efa\u5931\u8d25\u3002", e);
        }
        return parentPath;
    }

    @Override
    public boolean existPrivateFormula() {
        boolean isOpen = false;
        String enable = this.systemOptionService.get("PRIVATE_FORMULA", "PRIVATE_FORMULA_VALUE");
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)enable) && "1".equals(enable)) {
            isOpen = true;
        }
        return isOpen;
    }

    @Override
    public FormulaDefine queryFormulaDefine(String formulaKey) {
        if (this.existPrivateFormula()) {
            return this.extFormulaService.queryFormula(formulaKey);
        }
        return null;
    }

    @Override
    public FormulaDefine findFormulaDefine(String formulaDefineCode, String formulaSchemeKey) {
        return this.extFormulaService.findFormula(formulaDefineCode, formulaSchemeKey);
    }

    @Override
    public List<FormulaDefine> getAllFormulasInScheme(String formulaSchemeKey) {
        return this.extFormulaService.getFormulasInScheme(formulaSchemeKey);
    }

    @Override
    public List<FormulaDefine> getCheckFormulasInScheme(String formulaSchemeKey) {
        return this.getAllFormulasInScheme(formulaSchemeKey);
    }

    @Override
    public List<FormulaDefine> getAllFormulasInForm(String formulaSchemekey, String formkey) {
        return this.extFormulaService.getFormulasInForm(formulaSchemekey, formkey);
    }

    @Override
    public List<FormulaDefine> getCheckFormulasInForm(String formulaSchemekey, String formkey) {
        return this.extFormulaService.getFormulasInFormByType(formulaSchemekey, formkey);
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForm(String formulaSchemeKey, String formKey) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByForm(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CHECK);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    private List<IParsedExpression> filterExpressions(List<IParsedExpression> expressionByPublicForm, List<IParsedExpression> expressionByForm) {
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        HashSet formulaKeys = new HashSet();
        if (expressionByPublicForm != null && expressionByPublicForm.size() > 0) {
            result.addAll(expressionByPublicForm);
            formulaKeys.addAll(expressionByPublicForm.stream().map(t -> t.getSource().getId()).collect(Collectors.toList()));
        }
        if (expressionByForm != null) {
            if (formulaKeys.size() > 0) {
                for (IParsedExpression expression : expressionByForm) {
                    if (formulaKeys.contains(expression.getSource().getId())) continue;
                    result.add(expression);
                }
            } else {
                result.addAll(expressionByForm);
            }
        }
        return result;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForms(String formulaSchemeKey, List<String> formKeys) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByForms(formulaSchemeKey, formKeys, DataEngineConsts.FormulaType.CHECK);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionBetweenTable(String formulaSchemeKey) {
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        List<IParsedExpression> parsedExpressionBetweenTable = this.expressionService.getParsedExpressionBetweenTable(formulaSchemeKey, DataEngineConsts.FormulaType.CHECK);
        result.addAll(parsedExpressionBetweenTable);
        return result;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(String dataLinkCode, String formulaSchemeKey, String formKey) {
        ArrayList<IParsedExpression> expressionByPublicForm = new ArrayList<IParsedExpression>();
        List<IParsedExpression> expressionByForm = this.expressionService.getParsedExpressionByDataLink(dataLinkCode, formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CHECK);
        List<IParsedExpression> result = this.filterExpressions(expressionByPublicForm, expressionByForm);
        return result;
    }

    @Override
    public IParsedExpression getParsedExpression(String formulaSchemeKey, String expressionKey) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaSchemeKey, expressionKey);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public IParsedExpression getParsedExpression(String formulaSchemeKey, String formKey, String expressionKey) {
        IParsedExpression parsedExpression2 = this.expressionService.getParsedExpression(formulaSchemeKey, formKey, expressionKey);
        if (parsedExpression2 != null) {
            return parsedExpression2;
        }
        return null;
    }

    @Override
    public Collection<String> getCalcCellDataLinks(String formulaSchemeKey, String formKey) {
        Collection<String> links = this.expressionService.getCalcCellDataLinks(formulaSchemeKey, formKey);
        ArrayList<String> result = new ArrayList<String>();
        if (links != null) {
            result.addAll(links);
        }
        return result;
    }

    @Override
    public List<FormulaDefine> searchFormulaInScheme(String formulaCode, String formulaSchemeKey) {
        return this.extFormulaService.searchFormulaInScheme(formulaCode, formulaSchemeKey);
    }

    @Override
    public List<FormulaDefine> getFormulaByUnit(String formulaScheme, String unit) {
        if (!StringUtils.hasLength(formulaScheme) || !StringUtils.hasLength(unit)) {
            return new ArrayList<FormulaDefine>();
        }
        HashSet<String> set = new HashSet<String>();
        set.add(unit);
        return this.getFormulaByUnits(formulaScheme, set);
    }

    @Override
    public List<FormulaDefine> getFormulaByUnits(String formulaScheme, Set<String> units) {
        return this.extFormulaService.getFormulaByUnits(formulaScheme, units);
    }
}

