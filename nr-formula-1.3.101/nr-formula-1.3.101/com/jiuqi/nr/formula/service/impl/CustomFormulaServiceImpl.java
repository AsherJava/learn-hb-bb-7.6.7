/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.definition.api.IDesignTimeExtFormulaController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.task.api.resource.state.ResourceState
 *  com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.definition.api.IDesignTimeExtFormulaController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import com.jiuqi.nr.formula.exception.FormulaException;
import com.jiuqi.nr.formula.service.ICustomFormulaService;
import com.jiuqi.nr.formula.service.impl.AbstractFormulaServiceImpl;
import com.jiuqi.nr.formula.utils.convert.FormulaConvert;
import com.jiuqi.nr.formula.web.param.FormulaExtPM;
import com.jiuqi.nr.formula.web.param.FormulaListPM;
import com.jiuqi.nr.formula.web.param.FormulaSavePM;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.task.api.resource.state.ResourceState;
import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CustomFormulaServiceImpl
extends AbstractFormulaServiceImpl
implements ICustomFormulaService {
    private static final String PARENT_FORMULA = "ROOT_FORMULA";
    private static final String DEFAULT_UNIT = "-";
    @Autowired
    private IDesignTimeExtFormulaController extFormulaDesignTimeController;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private OrgIdentityService orgIdentityService;
    private static final String CUSTOM_REGEX = "^[a-zA-Z][\\w-]*$";
    private static final String CUSTOM_ERR = "\u516c\u5f0f\u7f16\u53f7\u4e0d\u7b26\u5408\u5b57\u6bcd\u5f00\u5934,\u5b57\u6bcd\u52a0\u6570\u5b57\u4e0b\u5212\u7ebf\u7684\u8981\u6c42!";
    private static final String CUSTOM_ERR_OUT_OFF_LENGTH = "\u516c\u5f0f\u7f16\u53f7\u957f\u5ea6\u4e0d\u7b26\u5408\u7f16\u53f7\u957f\u5ea6\u52a0\u5355\u4f4d\u7f16\u53f7\u5c0f\u4e8e50\u7684\u8981\u6c42!";

    @Override
    public Boolean existCustomFormula() {
        return this.extFormulaDesignTimeController.getExistPrivateFormula();
    }

    @Override
    public List<UITreeNode<EntityDataDTO>> initUnitTree(String formulaSchemeKey, String formKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        String formSchemeKey = formulaScheme.getFormSchemeKey();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        String entityId = task.getDw();
        if (this.systemIdentityService.isAdmin()) {
            return this.buildAdminTree(entityId);
        }
        return this.buildUserTree(entityId, formulaSchemeKey, formKey);
    }

    @Override
    public List<UITreeNode<EntityDataDTO>> loadChildren(String taskKey, String entityKey) {
        IEntityTable iEntityTable;
        ArrayList<UITreeNode<EntityDataDTO>> tree = new ArrayList<UITreeNode<EntityDataDTO>>();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        String entityId = task.getDw();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        entityQuery.sorted(true);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        try {
            iEntityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        List childRows = iEntityTable.getChildRows(entityKey);
        Collection grantedCode = this.orgIdentityService.getGrantedOrg(NpContextHolder.getContext().getUserId());
        for (IEntityRow childRow : childRows) {
            if (grantedCode.contains(childRow.getEntityKeyData())) continue;
            boolean hasChildren = this.adjustChildrenStatus(iEntityTable, childRow.getCode());
            UITreeNode<EntityDataDTO> rootTreeNode = this.buildNode(childRow, hasChildren);
            tree.add(rootTreeNode);
        }
        return tree;
    }

    private List<UITreeNode<EntityDataDTO>> buildAdminTree(String entityId) {
        IEntityTable iEntityTable;
        ArrayList<UITreeNode<EntityDataDTO>> tree = new ArrayList<UITreeNode<EntityDataDTO>>();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView(entityViewDefine);
        ExecutorContext contextChildren = new ExecutorContext(this.dataDefinitionRuntimeController);
        iEntityQuery.sorted(true);
        iEntityQuery.lazyQuery();
        iEntityQuery.markLeaf();
        try {
            iEntityTable = iEntityQuery.executeReader((IContext)contextChildren);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        List rootRows = iEntityTable.getRootRows();
        if (!CollectionUtils.isEmpty(rootRows)) {
            for (IEntityRow rootRow : rootRows) {
                UITreeNode<EntityDataDTO> rootTreeNode = this.buildNode(rootRow, rootRow.hasChildren());
                tree.add(rootTreeNode);
            }
        }
        return tree;
    }

    private List<UITreeNode<EntityDataDTO>> buildUserTree(String entityId, String formulaSchemeKey, String formKey) {
        ArrayList<UITreeNode<EntityDataDTO>> tree = new ArrayList<UITreeNode<EntityDataDTO>>();
        ContextOrganization organization = NpContextHolder.getContext().getOrganization();
        if (organization == null || organization.getCode() == null) {
            return tree;
        }
        String code = organization.getCode();
        IEntityTable iEntityTable = this.getEntityTable(entityId, false);
        IEntityRow currentRow = iEntityTable.findByEntityKey(code);
        if (currentRow == null) {
            return null;
        }
        String[] parentsEntityKeyDataPath = currentRow.getParentsEntityKeyDataPath();
        if (null != parentsEntityKeyDataPath && parentsEntityKeyDataPath.length != 0) {
            Set set = Arrays.stream(parentsEntityKeyDataPath).collect(Collectors.toSet());
            IEntityTable parentTable = this.getEntityTable(entityId, false);
            Map byEntityKeys = parentTable.findByEntityKeys(set);
            if (null != byEntityKeys && !byEntityKeys.isEmpty()) {
                boolean existFormula = false;
                for (String entityKey : set) {
                    IEntityRow entityRow = (IEntityRow)byEntityKeys.get(entityKey);
                    List formulaDefines = this.extFormulaDesignTimeController.listFormulaBySchemeAndFormAndEntity(formulaSchemeKey, formKey, entityRow.getCode());
                    if (CollectionUtils.isEmpty(formulaDefines)) continue;
                    existFormula = true;
                    break;
                }
                if (existFormula) {
                    UITreeNode<EntityDataDTO> rootTreeNode = this.buildNode(PARENT_FORMULA, "\u4e0a\u7ea7\u516c\u5f0f");
                    tree.add(rootTreeNode);
                }
            }
        }
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId, null, true);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.sorted(true);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        ExecutorContext contextChildren = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            iEntityTable = entityQuery.executeReader((IContext)contextChildren);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        IEntityRow ownerEntity = iEntityTable.findByEntityKey(code);
        boolean hasChildren = this.adjustChildrenStatus(iEntityTable, code);
        UITreeNode<EntityDataDTO> rootTreeNode = this.buildNode(ownerEntity, hasChildren);
        tree.add(rootTreeNode);
        return tree;
    }

    private boolean adjustChildrenStatus(IEntityTable iEntityTable, String code) {
        boolean hasChildren = false;
        List allChildRows = iEntityTable.getChildRows(code);
        if (!CollectionUtils.isEmpty(allChildRows)) {
            Collection grantedCode = this.orgIdentityService.getGrantedOrg(NpContextHolder.getContext().getUserId());
            Set childrenSet = allChildRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
            childrenSet.removeAll(grantedCode);
            hasChildren = !childrenSet.isEmpty();
        }
        return hasChildren;
    }

    private IEntityTable getEntityTable(String entityId, boolean auth) {
        IEntityTable iEntityTable;
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setAuthorityOperations(auth ? AuthorityType.Read : AuthorityType.None);
        try {
            iEntityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return iEntityTable;
    }

    private IEntityTable getEntityTableWithoutAuth(String entityId) {
        IEntityTable iEntityTable;
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        entityQuery.setEntityView(entityViewDefine);
        try {
            iEntityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return iEntityTable;
    }

    private UITreeNode<EntityDataDTO> buildNode(IEntityRow row, boolean haChildren) {
        EntityDataDTO node = new EntityDataDTO();
        node.setKey(row.getEntityKeyData());
        node.setCode(row.getCode());
        node.setTitle(row.getTitle());
        node.setParent(row.getParentEntityKey());
        UITreeNode treeNode = new UITreeNode((TreeData)node);
        treeNode.setLeaf(!haChildren);
        return treeNode;
    }

    private UITreeNode<EntityDataDTO> buildNode(String code, String title) {
        EntityDataDTO node = new EntityDataDTO();
        node.setKey(code);
        node.setCode(code);
        node.setTitle(title);
        UITreeNode treeNode = new UITreeNode((TreeData)node);
        treeNode.setLeaf(true);
        return treeNode;
    }

    @Override
    public List<FormulaDTO> listFormulaByForm(FormulaListPM param) {
        String formKey = param.getFormKey();
        if ("BJ".equals(formKey)) {
            formKey = null;
        }
        List<Object> formulaDefines = new ArrayList<DesignFormulaDefine>();
        if (PARENT_FORMULA.equals(param.getUnit())) {
            if (!this.systemIdentityService.isAdmin()) {
                DesignTaskDefine designTaskDefine;
                ContextOrganization organization = NpContextHolder.getContext().getOrganization();
                if (organization == null) {
                    return null;
                }
                String orgId = organization.getCode();
                String dw = null;
                DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaDesignTimeController.getFormulaScheme(param.getFormulaSchemeKey());
                DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(designFormulaSchemeDefine.getFormSchemeKey());
                if (null != formSchemeDefine && null != (designTaskDefine = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey()))) {
                    dw = designTaskDefine.getDw();
                }
                if (StringUtils.hasText(dw)) {
                    String[] parentsEntityKeyDataPath;
                    IEntityTable iEntityTable;
                    RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
                    viewDefine.setEntityId(dw);
                    IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                    iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
                    ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
                    context.setPeriodView(dw);
                    try {
                        iEntityTable = iEntityQuery.executeReader((IContext)context);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    IEntityRow row = iEntityTable.findByEntityKey(orgId);
                    if (row != null && (parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath()) != null && parentsEntityKeyDataPath.length != 0) {
                        Set codeSet = Arrays.stream(parentsEntityKeyDataPath).collect(Collectors.toSet());
                        Map parents = iEntityTable.findByEntityKeys(codeSet);
                        for (String key : parents.keySet()) {
                            IEntityRow entityRow = (IEntityRow)parents.get(key);
                            List parentFormulas = this.extFormulaDesignTimeController.listFormulaBySchemeAndFormAndEntity(param.getFormulaSchemeKey(), formKey, entityRow.getCode());
                            if (CollectionUtils.isEmpty(parentFormulas)) continue;
                            formulaDefines.addAll(parentFormulas);
                        }
                    }
                }
            }
        } else {
            formulaDefines = this.extFormulaDesignTimeController.listFormulaBySchemeAndFormAndEntity(param.getFormulaSchemeKey(), formKey, param.getUnit());
        }
        List<FormulaDTO> list = FormulaConvert.defineToDTOList(formulaDefines);
        if (!PARENT_FORMULA.equals(param.getUnit())) {
            CustomFormulaServiceImpl.replaceCode(list);
        }
        return list;
    }

    @Override
    protected String buildCode(String value, DesignFormDefine form, int row, String unit) {
        String code = super.buildCode(value, form, row, unit);
        if (!StringUtils.hasText(unit)) {
            return code;
        }
        return this.buildUnitCode(code, unit);
    }

    @Override
    protected boolean validateCheckType(String type) {
        return type.equals("\u5ba1\u6838\u516c\u5f0f");
    }

    private static void replaceCode(List<FormulaDTO> list) {
        for (FormulaDTO formula : list) {
            String unit = formula.getUnit();
            if (DEFAULT_UNIT.equals(unit) || !formula.getPrivate() || !formula.getCode().contains(unit)) continue;
            String realCode = formula.getCode().substring(unit.length() + 1);
            formula.setCode(realCode);
        }
    }

    @Override
    public void saveFormulaData(FormulaSavePM pm) {
        String unit = pm.getUnit();
        List<FormulaDTO> itemList = pm.getItemList();
        for (FormulaDTO formula : itemList) {
            formula.setCode(this.buildUnitCode(formula.getCode(), unit));
            formula.setPrivate(true);
            formula.setUnit(unit);
        }
        super.saveFormulaData(pm);
    }

    @Override
    public void updateFormulas(List<FormulaDTO> formulas) {
        DesignFormulaDefine[] defines = FormulaConvert.dtoToDefineList(formulas, ResourceState.DIRTY, this.formulaDesignTimeController);
        this.extFormulaDesignTimeController.updateExtFormula(defines);
    }

    @Override
    public void insertFormulas(List<FormulaDTO> formulas) {
        DesignFormulaDefine[] list;
        for (DesignFormulaDefine define : list = FormulaConvert.dtoToDefineList(formulas, this.formulaDesignTimeController)) {
            if (define.getOrder() != null) continue;
            define.setOrder(OrderGenerator.newOrder());
        }
        this.extFormulaDesignTimeController.insertExtFormula(list);
    }

    @Override
    public void updateFormulaDefine(List<DesignFormulaDefine> formulaDefines) {
        this.extFormulaDesignTimeController.updateExtFormula(formulaDefines.toArray(new DesignFormulaDefine[0]));
    }

    @Override
    public FormulaCheckResult formulaCheck(FormulaExtPM pm) {
        List<FormulaExtDTO> itemList = pm.getItemList();
        String unit = pm.getUnit();
        for (FormulaExtDTO formula : itemList) {
            formula.setUnit(unit);
            formula.setCode(this.buildUnitCode(formula.getCode(), unit));
        }
        return super.formulaCheck(pm.getFormulaSchemeKey(), pm.getFormKey(), false, itemList, pm.getDeleted());
    }

    @Override
    protected <T extends FormulaExtDTO> void formulaCodeRegCheck(Map<String, T> checkResult, T item) {
        String unit;
        String code = item.getCode();
        String realCode = code.substring((unit = item.getUnit()).length() + 1);
        if (!Pattern.matches(CUSTOM_REGEX, realCode)) {
            CustomFormulaServiceImpl.addCheckError(checkResult, item, CUSTOM_ERR);
        } else if (code.length() >= 50) {
            CustomFormulaServiceImpl.addCheckError(checkResult, item, CUSTOM_ERR_OUT_OFF_LENGTH);
        }
    }

    @Override
    protected void codeCheck(String code, String unit) {
        String realCode = code;
        if (unit != null) {
            realCode = code.substring(unit.length() + 1);
        }
        if (!Pattern.matches(CUSTOM_REGEX, realCode)) {
            throw new FormulaException(CUSTOM_ERR);
        }
        if (code.length() >= 50) {
            throw new FormulaException(CUSTOM_ERR_OUT_OFF_LENGTH);
        }
    }

    @Override
    public List<FormulaDTO> listFormulaByScheme(String formulaSchemeKey) {
        List formulaDefines = this.extFormulaDesignTimeController.listFormulaByScheme(formulaSchemeKey);
        List<FormulaDTO> list = super.convertDto(formulaDefines);
        CustomFormulaServiceImpl.replaceCode(list);
        return list;
    }

    @Override
    protected List<DesignFormulaDefine> queryFormulaByScheme(String formulaScheme) {
        return this.extFormulaDesignTimeController.listFormulaByScheme(formulaScheme);
    }

    @Override
    protected List<DesignFormulaDefine> queryFormulaBySchemeAndForm(String formulaScheme, String formKey, String unit) {
        if (StringUtils.hasText(unit)) {
            return this.extFormulaDesignTimeController.listFormulaBySchemeAndFormAndEntity(formulaScheme, formKey, unit);
        }
        return this.extFormulaDesignTimeController.listFormulaBySchemeAndForm(formulaScheme, formKey);
    }

    @Override
    protected boolean formulaCodeCheck(String code, String id, String formulaScheme, List<String> deleted) {
        List queryItem = this.extFormulaDesignTimeController.getFormulaByCodeAndSchemeAndForm(code, null, formulaScheme);
        return !CollectionUtils.isEmpty(queryItem) && queryItem.stream().noneMatch(e -> e.getKey().equals(id));
    }

    private String buildUnitCode(String formulaCode, String unit) {
        return unit + DEFAULT_UNIT + formulaCode;
    }
}

