/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.MetaComparator
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.MetaComparator;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.web.rest.resultBean.QuoteForms;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.nr.designer.web.rest.vo.TaskSchemeGroupTreeNode;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteFormByOtherTaskServiceImpl {
    @Autowired
    IDesignRestService iDesignRestService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private TaskDesignerService taskDesignerService;

    public void updateFormGroupLink(String formKey, String ownGroupKeys) throws Exception {
        if (StringUtils.isEmpty((String)formKey)) {
            return;
        }
        DesignFormDefine form = this.nrDesignTimeController.queryFormById(formKey);
        if (form == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_005);
        }
        this.taskDesignerService.doFormGroup(formKey, ownGroupKeys);
    }

    public List<ITree<TaskSchemeGroupTreeNode>> getSchemeTree(String schemeKey) throws JQException {
        ArrayList<ITree<TaskSchemeGroupTreeNode>> tree_Task = new ArrayList<ITree<TaskSchemeGroupTreeNode>>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            listDesignTask.forEach(task -> tree_Task.add(this.getGroupTreeNode((DesignTaskDefine)task)));
        }
        this.taskSchemeTree(tree_Task, schemeKey);
        return tree_Task;
    }

    public List<ITree<TaskSchemeGroupTreeNode>> getFormGroupTree(String formSchemeKey) throws JQException {
        ArrayList<ITree<TaskSchemeGroupTreeNode>> tree_FormGroup = new ArrayList<ITree<TaskSchemeGroupTreeNode>>();
        List formGroups = this.nrDesignTimeController.queryAllGroupsByFormScheme(formSchemeKey);
        if (formGroups != null) {
            formGroups.forEach(group -> tree_FormGroup.add(this.getGroupTreeNode((DesignFormGroupDefine)group)));
        }
        this.taskFormTree(tree_FormGroup);
        return tree_FormGroup;
    }

    private String addFormGroup(List<DesignFormGroupDefine> queryRootGroupsByFormScheme, String groupKey) {
        DesignFormGroupDefine queryFormGroup = this.nrDesignTimeController.queryFormGroup(groupKey);
        List list = queryRootGroupsByFormScheme.stream().filter(item -> item.getTitle().equals(queryFormGroup.getTitle())).collect(Collectors.toList());
        if (list.isEmpty()) {
            DesignFormGroupDefine createFormGroup = this.nrDesignTimeController.createFormGroup();
            createFormGroup.setTitle(queryFormGroup.getTitle());
            createFormGroup.setCondition(queryFormGroup.getCondition());
            createFormGroup.setMeasureUnit(queryFormGroup.getMeasureUnit());
            createFormGroup.setCode(queryFormGroup.getCode());
            createFormGroup.setParentKey(queryFormGroup.getParentKey());
            createFormGroup.setOwnerLevelAndId(queryFormGroup.getOwnerLevelAndId());
            createFormGroup.setOrder(OrderGenerator.newOrder());
            this.nrDesignTimeController.addNewFormGroupToScheme(createFormGroup, queryRootGroupsByFormScheme.get(0).getFormSchemeKey());
            return createFormGroup.getKey();
        }
        return ((DesignFormGroupDefine)list.get(0)).getKey();
    }

    private void addFormToGroup(String formKey, String targetgroupId) throws Exception {
        List designFormGroupLinks = this.nrDesignTimeController.getFormGroupLinksByFormId(formKey);
        List formGroupKeys = designFormGroupLinks.stream().map(e -> e.getGroupKey()).collect(Collectors.toList());
        if (!formGroupKeys.contains(targetgroupId)) {
            this.nrDesignTimeController.addFormToGroup(formKey, targetgroupId);
        }
    }

    private void copyFormulaToNewFormulaScheme(QuoteForms quoteForms) throws JQException {
        List targetFormulaSchemeDefineList = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(quoteForms.getTargetFormSchemeKey());
        List<String> formulaSchemekeys = quoteForms.getFormulaSchemekeys();
        ArrayList newFormulaDefineByForm = new ArrayList();
        Map<String, DesignFormulaSchemeDefine> targetFormulaSchemeMap = targetFormulaSchemeDefineList.stream().collect(Collectors.toMap(t -> t.getTitle(), t -> t, (oldValue, newValue) -> newValue));
        if (formulaSchemekeys != null && formulaSchemekeys.size() > 0) {
            for (String sourceFormulaSchemeKey : formulaSchemekeys) {
                DesignFormulaSchemeDefine sourceFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(sourceFormulaSchemeKey);
                String sourceTitle = sourceFormulaSchemeDefine.getTitle();
                DesignFormulaSchemeDefine targetFormulaSchemeDefine = targetFormulaSchemeMap.get(sourceTitle);
                if (targetFormulaSchemeDefine != null || !quoteForms.isCreateNewFormulaScheme()) continue;
                DesignFormulaSchemeDefine newFormulaSchemeDefine = this.nrDesignTimeController.createFormulaSchemeDefine();
                newFormulaSchemeDefine.setTitle(sourceFormulaSchemeDefine.getTitle());
                newFormulaSchemeDefine.setDefault(false);
                newFormulaSchemeDefine.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
                newFormulaSchemeDefine.setFormSchemeKey(quoteForms.getTargetFormSchemeKey());
                this.nrDesignTimeController.insertFormulaSchemeDefine(newFormulaSchemeDefine);
            }
        }
        if (newFormulaDefineByForm.size() > 0) {
            this.nrDesignTimeController.insertFormulaDefines(newFormulaDefineByForm.toArray(new DesignFormulaDefine[0]));
        }
    }

    private void copyFinancialToNewFinancialScheme(QuoteForms quoteForms) throws JQException {
        List targetFormulaSchemeDefineList = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(quoteForms.getTargetFormSchemeKey());
        List<String> financialSchemekeys = quoteForms.getFinancialSchemekeys();
        ArrayList newFormulaDefineByForm = new ArrayList();
        Map<String, DesignFormulaSchemeDefine> targetFormulaSchemeMap = targetFormulaSchemeDefineList.stream().collect(Collectors.toMap(t -> t.getTitle(), t -> t, (oldValue, newValue) -> newValue));
        if (financialSchemekeys != null && financialSchemekeys.size() > 0) {
            for (String sourceFormulaSchemeKey : financialSchemekeys) {
                DesignFormulaSchemeDefine sourceFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(sourceFormulaSchemeKey);
                String sourceTitle = sourceFormulaSchemeDefine.getTitle();
                DesignFormulaSchemeDefine targetFormulaSchemeDefine = targetFormulaSchemeMap.get(sourceTitle);
                if (targetFormulaSchemeDefine != null || !quoteForms.isCreateNewFinancialScheme()) continue;
                DesignFormulaSchemeDefine newFormulaSchemeDefine = this.nrDesignTimeController.createFormulaSchemeDefine();
                newFormulaSchemeDefine.setTitle(sourceFormulaSchemeDefine.getTitle());
                newFormulaSchemeDefine.setDefault(false);
                newFormulaSchemeDefine.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
                newFormulaSchemeDefine.setFormSchemeKey(quoteForms.getTargetFormSchemeKey());
                this.nrDesignTimeController.insertFormulaSchemeDefine(newFormulaSchemeDefine);
            }
        }
        if (newFormulaDefineByForm.size() > 0) {
            this.nrDesignTimeController.insertFormulaDefines(newFormulaDefineByForm.toArray(new DesignFormulaDefine[0]));
        }
    }

    private void copyPrintTemplateAndScheme(QuoteForms quoteForms, DesignFormSchemeDefine sourceFormSchemeDefine, String formKey) throws Exception {
        List targetPrintTemplateSchemeList = this.nrDesignTimeController.getAllPrintSchemeByFormScheme(quoteForms.getTargetFormSchemeKey());
        List<String> printSchemekeys = quoteForms.getPrintSchemekeys();
        Map<String, DesignPrintTemplateSchemeDefine> targetPrintTemplateSchemeMap = targetPrintTemplateSchemeList.stream().collect(Collectors.toMap(t -> t.getTitle(), t -> t, (oldValue, newValue) -> newValue));
        ArrayList<DesignPrintTemplateDefine> newPrintTemplateDefineList = new ArrayList<DesignPrintTemplateDefine>();
        if (printSchemekeys != null && printSchemekeys.size() > 0) {
            for (String printSchemekey : printSchemekeys) {
                DesignPrintTemplateSchemeDefine sourcePrintTemplateScheme = this.nrDesignTimeController.queryPrintTemplateSchemeDefine(printSchemekey);
                String sourceTitle = sourcePrintTemplateScheme.getTitle();
                DesignPrintTemplateSchemeDefine targetPrintTemplateScheme = targetPrintTemplateSchemeMap.get(sourceTitle);
                if (targetPrintTemplateScheme != null) {
                    DesignPrintTemplateDefine sourcePrintTemplateDefine = this.nrDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(sourcePrintTemplateScheme.getKey(), formKey);
                    if (sourcePrintTemplateDefine == null) continue;
                    sourcePrintTemplateDefine.setKey(UUID.randomUUID().toString());
                    sourcePrintTemplateDefine.setPrintSchemeKey(targetPrintTemplateScheme.getKey());
                    newPrintTemplateDefineList.add(sourcePrintTemplateDefine);
                    continue;
                }
                if (!quoteForms.isCreateNewPrintScheme()) continue;
                DesignPrintTemplateSchemeDefine printSchemeDefine = this.nrDesignTimeController.createPrintTemplateSchemeDefine();
                printSchemeDefine.setTitle(sourceTitle);
                printSchemeDefine.setTaskKey(((DesignPrintTemplateSchemeDefine)targetPrintTemplateSchemeList.get(0)).getTaskKey());
                printSchemeDefine.setFormSchemeKey(quoteForms.getTargetFormSchemeKey());
                printSchemeDefine.setOrder(OrderGenerator.newOrder());
                this.nrDesignTimeController.setPrintSchemeAttribute(printSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
                this.nrDesignTimeController.insertPrintTemplateSchemeDefine(printSchemeDefine);
                DesignPrintTemplateDefine sourcePrintTemplateDefine = this.nrDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(sourcePrintTemplateScheme.getKey(), formKey);
                if (sourcePrintTemplateDefine == null) continue;
                sourcePrintTemplateDefine.setKey(UUID.randomUUID().toString());
                sourcePrintTemplateDefine.setPrintSchemeKey(printSchemeDefine.getKey());
                newPrintTemplateDefineList.add(sourcePrintTemplateDefine);
            }
        }
        if (newPrintTemplateDefineList.size() > 0) {
            this.nrDesignTimeController.insertTemplates(newPrintTemplateDefineList.toArray(new DesignPrintTemplateDefine[0]));
        }
    }

    private void taskFormTree(List<ITree<TaskSchemeGroupTreeNode>> tree_FormGroup) throws JQException {
        for (ITree<TaskSchemeGroupTreeNode> tTree : tree_FormGroup) {
            ArrayList tree_form = new ArrayList();
            List listForms = this.nrDesignTimeController.getAllFormsInGroupWithoutBinaryData(tTree.getKey());
            listForms.sort(new MetaComparator());
            if (listForms != null) {
                listForms.forEach(form -> tree_form.add(this.getGroupTreeNode((DesignFormDefine)form, tTree.getKey())));
            }
            tTree.setChildren(tree_form);
        }
    }

    private void taskSchemeTree(List<ITree<TaskSchemeGroupTreeNode>> tree_Task, String schemeKey) throws JQException {
        for (ITree<TaskSchemeGroupTreeNode> taskTree : tree_Task) {
            ArrayList tree_Scheme = new ArrayList();
            List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(((TaskSchemeGroupTreeNode)taskTree.getData()).getKey());
            if (listFormSchemes != null) {
                listFormSchemes.forEach(scheme -> {
                    ITree<TaskSchemeGroupTreeNode> node = this.getGroupTreeNode((DesignFormSchemeDefine)scheme);
                    if (schemeKey.equals(node.getKey())) {
                        node.setSelected(true);
                    }
                    tree_Scheme.add(node);
                });
            }
            taskTree.setChildren(tree_Scheme);
        }
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignTaskDefine task) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(task));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignFormSchemeDefine scheme) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(scheme));
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignFormGroupDefine group) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(group));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignFormDefine form, String formGroupKey) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(form, formGroupKey));
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }
}

