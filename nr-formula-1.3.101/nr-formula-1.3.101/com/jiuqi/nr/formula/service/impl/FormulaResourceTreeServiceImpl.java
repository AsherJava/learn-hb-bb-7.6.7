/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.task.api.tree.TreeConfig
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeBuilder
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.formula.dto.FormulaFormTreeDTO;
import com.jiuqi.nr.formula.exception.FormulaTreeException;
import com.jiuqi.nr.formula.service.IFormulaResourceTreeService;
import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeBuilder;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormulaResourceTreeServiceImpl
implements IFormulaResourceTreeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimeFormulaController formulaDesignTimeController;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;

    @Override
    public List<UITreeNode<FormulaFormTreeDTO>> initFormulaResourceTree(String formulaSchemeKey) {
        ArrayList<UITreeNode<FormulaFormTreeDTO>> nodeList = new ArrayList<UITreeNode<FormulaFormTreeDTO>>();
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        if (formulaScheme != null) {
            String locationKey = this.addFormTree(nodeList, formulaScheme.getFormSchemeKey());
            if (formulaScheme.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT)) {
                this.addBetweenFormulaTree(nodeList, formulaSchemeKey);
            }
            UITreeBuilder builder = new UITreeBuilder(new TreeConfig().selected(new String[]{locationKey}));
            builder.add(nodeList);
            return builder.build();
        }
        return Collections.emptyList();
    }

    private UITreeNode<FormulaFormTreeDTO> buildNode(IMetaGroup group) {
        FormulaFormTreeDTO dto = new FormulaFormTreeDTO(group.getCode(), group.getTitle());
        dto.setType(FormulaFormTreeDTO.NodeType.FORMGROUP);
        UITreeNode nrNode = new UITreeNode((TreeData)dto);
        nrNode.setKey(group.getKey());
        nrNode.setTitle(group.getTitle());
        nrNode.setParent(group.getParentKey());
        nrNode.setOrder(group.getOrder());
        return nrNode;
    }

    private UITreeNode<FormulaFormTreeDTO> buildNode(DesignFormDefine item, String parentKey) {
        FormulaFormTreeDTO dto = new FormulaFormTreeDTO(item.getFormCode(), item.getTitle());
        dto.setType(FormulaFormTreeDTO.NodeType.FORM);
        UITreeNode nrNode = new UITreeNode((TreeData)dto);
        nrNode.setKey(item.getKey());
        nrNode.setParent(parentKey);
        nrNode.setTitle(item.getTitle());
        nrNode.setOrder(item.getOrder());
        nrNode.setIcon(this.getFormIcon(item.getFormType()));
        return nrNode;
    }

    private String getFormIcon(FormType formType) {
        if (formType == FormType.FORM_TYPE_FLOAT) {
            return "icon-J_GJ_A_NR_fudongbiao";
        }
        if (formType == FormType.FORM_TYPE_SURVEY) {
            return "icon-J_GJ_A_NR_wenjuan";
        }
        if (formType == FormType.FORM_TYPE_INSERTANALYSIS) {
            return "icon-J_GJ_A_NR_fenxibiao";
        }
        if (formType == FormType.FORM_TYPE_ACCOUNT) {
            return "icon-J_GJ_A_NR_taizhang";
        }
        if (formType == FormType.FORM_TYPE_NEWFMDM) {
            return "icon-J_GJ_A_NR_fengmiandaima";
        }
        return "icon-J_GJ_A_NR_gudingbiao";
    }

    private void addBetweenFormulaTree(List<UITreeNode<FormulaFormTreeDTO>> nodeList, String parent) {
        UITreeNode nrNode = new UITreeNode();
        nrNode.setKey("BJ");
        nrNode.setParent(null);
        nrNode.setTitle("\u8868\u95f4\u516c\u5f0f");
        nrNode.setOrder("0");
        nrNode.setIcon("icon-folder");
        FormulaFormTreeDTO data = new FormulaFormTreeDTO("BJ", "\u8868\u95f4\u516c\u5f0f");
        data.setType(FormulaFormTreeDTO.NodeType.FORMGROUP);
        nrNode.setData((TreeData)data);
        nodeList.add((UITreeNode<FormulaFormTreeDTO>)nrNode);
    }

    private String addFormTree(List<UITreeNode<FormulaFormTreeDTO>> treeList, String formSchemeKey) {
        String locationKey = null;
        try {
            List formGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
            for (DesignFormGroupDefine formGroupDefine : formGroupDefines) {
                UITreeNode<FormulaFormTreeDTO> groupTreeNode = this.buildNode((IMetaGroup)formGroupDefine);
                groupTreeNode.setIcon("icon-folder");
                treeList.add(groupTreeNode);
                List formDefines = this.designTimeViewController.listFormByGroup(formGroupDefine.getKey());
                for (DesignFormDefine formDefine : formDefines) {
                    if (locationKey == null) {
                        locationKey = formDefine.getKey();
                    }
                    treeList.add(this.buildNode(formDefine, formGroupDefine.getKey()));
                }
            }
        }
        catch (Exception e) {
            throw new FormulaTreeException("\u6811\u5f62\u6784\u5efa\u5931\u8d25! ", e);
        }
        return locationKey;
    }

    @Override
    public List<UITreeNode<FormulaFormTreeDTO>> initUnitTree(String taskKey, String formulaSchemeKey) {
        String taskKey1 = this.getTaskKey(formulaSchemeKey);
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey1);
        String currentWD = task.getDw();
        if (null == currentWD) {
            return Collections.emptyList();
        }
        RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
        viewDefine.setEntityId(currentWD);
        try {
            if (this.systemIdentityService.isAdmin()) {
                return this.initAdminUnitTree(viewDefine);
            }
            return this.initUserUnitTree(viewDefine);
        }
        catch (Exception e) {
            throw new FormulaTreeException(e);
        }
    }

    private String getTaskKey(String formulaSchemeKey) {
        DesignFormSchemeDefine formScheme;
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        if (formulaScheme != null && (formScheme = this.designTimeViewController.getFormScheme(formulaScheme.getFormSchemeKey())) != null) {
            return formScheme.getTaskKey();
        }
        return null;
    }

    private String getFormSchemeKey(String formulaSchemeKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        if (formulaScheme != null) {
            return formulaScheme.getFormSchemeKey();
        }
        return null;
    }

    private List<UITreeNode<FormulaFormTreeDTO>> initUserUnitTree(RunTimeEntityViewDefineImpl viewDefine) throws Exception {
        final String code = NpContextHolder.getContext().getOrganization().getCode();
        if (null == code) {
            return Collections.emptyList();
        }
        ArrayList<UITreeNode<FormulaFormTreeDTO>> nodeList = new ArrayList<UITreeNode<FormulaFormTreeDTO>>();
        UITreeNode<FormulaFormTreeDTO> superNode = this.queryDirectSuper(viewDefine, code);
        if (null == superNode) {
            return Collections.emptyList();
        }
        nodeList.add(superNode);
        IEntityQuery iEntityQueryChildren = this.entityDataService.newEntityQuery();
        viewDefine.setFilterRowByAuthority(true);
        iEntityQueryChildren.setEntityView((EntityViewDefine)viewDefine);
        iEntityQueryChildren.sorted(true);
        iEntityQueryChildren.setAuthorityOperations(AuthorityType.Read);
        ExecutorContext contextChildren = new ExecutorContext(this.definitionRuntimeController);
        contextChildren.setPeriodView(viewDefine.getEntityId());
        TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
        treeRangeQuery.setParentKey((List)new ArrayList<String>(){
            {
                this.add(code);
            }
        });
        IEntityTable iEntityTableChildren = iEntityQueryChildren.executeRangeBuild((IContext)contextChildren, (RangeQuery)treeRangeQuery);
        IEntityRow byCode = iEntityTableChildren.findByEntityKey(code);
        List childRows = iEntityTableChildren.getChildRows(code);
        if (byCode != null) {
            this.addUnitTreeNode(nodeList, null, byCode, byCode.getCode(), childRows.size() == 0);
            if (childRows.size() != 0) {
                Collection grantedCode = this.orgIdentityService.getGrantedOrg(NpContextHolder.getContext().getUserId());
                Collection<String> grantedOrg = this.codeToOrgCode(iEntityTableChildren, grantedCode);
                if (grantedOrg.size() != 0) {
                    this.recursionIEntityRows(nodeList, childRows, byCode.getCode(), iEntityTableChildren, grantedOrg);
                } else {
                    this.recursionIEntityRows(nodeList, childRows, byCode.getCode(), iEntityTableChildren);
                }
            }
        }
        UITreeBuilder builder = new UITreeBuilder();
        builder.add(nodeList);
        return builder.build();
    }

    private UITreeNode<FormulaFormTreeDTO> queryDirectSuper(RunTimeEntityViewDefineImpl viewDefine, String code) throws Exception {
        HashSet<String> set;
        Map byEntityKeys;
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
        iEntityQuery.sorted(true);
        ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
        context.setPeriodView(viewDefine.getEntityId());
        IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)context);
        IEntityRow row = iEntityTable.findByEntityKey(code);
        if (row == null) {
            return null;
        }
        String[] parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath();
        if (null != parentsEntityKeyDataPath && parentsEntityKeyDataPath.length != 0 && null != (byEntityKeys = iEntityTable.findByEntityKeys(set = new HashSet<String>(Arrays.asList(parentsEntityKeyDataPath)))) && byEntityKeys.size() != 0) {
            UITreeNode nrNode = new UITreeNode();
            nrNode.setKey("\u4e0a\u7ea7\u516c\u5f0f");
            nrNode.setParent(null);
            nrNode.setTitle("\u4e0a\u7ea7\u516c\u5f0f");
            return nrNode;
        }
        return null;
    }

    private void recursionIEntityRows(List<UITreeNode<FormulaFormTreeDTO>> nodeList, List<IEntityRow> childRows, String parent, IEntityTable iEntityTableChildren, Collection<String> grantedOrg) {
        for (IEntityRow childRow : childRows) {
            if (grantedOrg.contains(childRow.getCode())) continue;
            List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
            if (childRows1.size() != 0) {
                this.addUnitTreeNode(nodeList, parent, childRow, "", false);
                this.recursionIEntityRows(nodeList, childRows1, childRow.getCode(), iEntityTableChildren, grantedOrg);
                continue;
            }
            this.addUnitTreeNode(nodeList, parent, childRow, "", true);
        }
    }

    private Collection<String> codeToOrgCode(IEntityTable iEntityTable, Collection<String> grantedOrg) {
        ArrayList<String> collection = new ArrayList<String>();
        for (String s : grantedOrg) {
            IEntityRow byEntityKey = iEntityTable.findByEntityKey(s);
            if (null == byEntityKey) continue;
            collection.add(byEntityKey.getCode());
        }
        return collection;
    }

    private List<UITreeNode<FormulaFormTreeDTO>> initAdminUnitTree(RunTimeEntityViewDefineImpl viewDefine) throws Exception {
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
        ExecutorContext contextChildren = new ExecutorContext(this.definitionRuntimeController);
        contextChildren.setPeriodView(viewDefine.getEntityId());
        iEntityQuery.sorted(true);
        IEntityTable iEntityTable = iEntityQuery.executeFullBuild((IContext)contextChildren);
        UITreeBuilder builder = new UITreeBuilder();
        builder.add(this.getNodeFromTable(iEntityTable));
        return builder.build();
    }

    private List<UITreeNode<FormulaFormTreeDTO>> getNodeFromTable(IEntityTable iEntityTable) {
        List allRows = iEntityTable.getAllRows();
        ArrayList<UITreeNode<FormulaFormTreeDTO>> treeNodes = new ArrayList<UITreeNode<FormulaFormTreeDTO>>();
        for (IEntityRow row : allRows) {
            UITreeNode node = new UITreeNode();
            this.parseNode(row, (UITreeNode<FormulaFormTreeDTO>)node);
            treeNodes.add((UITreeNode<FormulaFormTreeDTO>)node);
        }
        return treeNodes;
    }

    private void parseNode(IEntityRow row, UITreeNode<FormulaFormTreeDTO> node) {
        node.setParent(row.getParentEntityKey());
        node.setKey(row.getEntityKeyData());
        node.setTitle(row.getTitle());
    }

    private void recursionIEntityRows(List<UITreeNode<FormulaFormTreeDTO>> nodeList, List<IEntityRow> childRows, String parent, IEntityTable iEntityTableChildren) {
        for (IEntityRow childRow : childRows) {
            List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
            if (childRows1.size() != 0) {
                this.addUnitTreeNode(nodeList, parent, childRow, "", false);
                this.recursionIEntityRows(nodeList, childRows1, childRow.getCode(), iEntityTableChildren);
                continue;
            }
            this.addUnitTreeNode(nodeList, parent, childRow, "", true);
        }
    }

    private void addUnitTreeNode(List<UITreeNode<FormulaFormTreeDTO>> nodeList, String parent, IEntityRow row, String currCode, boolean isLeaf) {
        if (StringUtils.hasText(parent)) {
            for (UITreeNode<FormulaFormTreeDTO> node : nodeList) {
                if (node.getKey().equals(parent)) {
                    nodeList.add(this.transTreeObj(row, parent, currCode, isLeaf));
                    continue;
                }
                this.addUnitTreeNode(nodeList, parent, row, currCode, isLeaf);
            }
        } else {
            nodeList.add(this.transTreeObj(row, null, currCode, isLeaf));
        }
    }

    private UITreeNode<FormulaFormTreeDTO> transTreeObj(IEntityRow row, String parent, String currCode, boolean isLeaf) {
        UITreeNode node = new UITreeNode();
        node.setKey(row.getCode());
        node.setParent(parent);
        node.setTitle(row.getTitle());
        if (row.getCode().equals(currCode)) {
            node.setExpand(true);
        }
        return node;
    }

    @Override
    public List<UITreeNode<FormulaFormTreeDTO>> initTreeAndLocate(String formulaSchemeKey, String formKey) {
        ArrayList<UITreeNode<FormulaFormTreeDTO>> nodeList = new ArrayList<UITreeNode<FormulaFormTreeDTO>>();
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        if (formulaScheme != null) {
            this.addFormTree(nodeList, formulaScheme.getFormSchemeKey());
            if (formulaScheme.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT)) {
                this.addBetweenFormulaTree(nodeList, formulaSchemeKey);
            }
            UITreeBuilder builder = new UITreeBuilder(new TreeConfig().selected(new String[]{formKey}));
            builder.add(nodeList);
            return builder.build();
        }
        return Collections.emptyList();
    }
}

