/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.formulamapping.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.Data;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import com.jiuqi.nr.formulamapping.service.InitTreeSevice;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitTreeServiceImpl
implements InitTreeSevice {
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IFormulaRunTimeController runTimeController;
    private static final String NUMBER_FORMULAS = "number_formulas";
    private static final String NUMBER_FORMULAS_TITLE = "\u8868\u95f4\u516c\u5f0f";

    @Override
    public List<TreeObj> initFormTree(String formulaSchemeKey, String nodeKey) throws JQException {
        ArrayList<TreeObj> initTree = new ArrayList<TreeObj>();
        FormulaSchemeDefine queryFormulaSchemeDefine = this.runTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (queryFormulaSchemeDefine == null) {
            return initTree;
        }
        String formSchemeKey = queryFormulaSchemeDefine.getFormSchemeKey();
        List groupsByFormScheme = this.runtimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        TreeObj treeObj = new TreeObj();
        treeObj.setId(queryFormulaSchemeDefine.getKey());
        treeObj.setTitle(queryFormulaSchemeDefine.getTitle());
        if (StringUtils.isNotEmpty((String)nodeKey)) {
            treeObj.setExpended(Boolean.valueOf(true));
        }
        Data data = new Data();
        data.setKey(treeObj.getId());
        data.setTitle(treeObj.getTitle());
        treeObj.setData(data);
        treeObj.setChildren(this.setChildrenNodeByGroup(groupsByFormScheme, queryFormulaSchemeDefine.getKey(), nodeKey));
        initTree.add(treeObj);
        return initTree;
    }

    private List<TreeObj> setChildrenNodeByGroup(List<FormGroupDefine> groupsByFormScheme, String formSchemeKey, String nodeKey) throws JQException {
        ArrayList<TreeObj> initTree = new ArrayList<TreeObj>();
        TreeObj initTestForm = this.initTestForm(nodeKey, formSchemeKey);
        initTree.add(initTestForm);
        if (groupsByFormScheme != null && !groupsByFormScheme.isEmpty()) {
            for (FormGroupDefine item : groupsByFormScheme) {
                TreeObj treeObj = new TreeObj();
                treeObj.setId(item.getKey());
                treeObj.setTitle(item.getTitle());
                treeObj.setCode(item.getCode());
                treeObj.setData(this.setDataNodeFormGroup(item, formSchemeKey));
                if (StringUtils.isNotEmpty((String)nodeKey) && nodeKey.equals(item.getKey())) {
                    this.setSelectNode(treeObj);
                    treeObj.setSelected(true);
                }
                try {
                    List allFormsInGroup = this.runtimeViewController.getAllFormsInGroupWithoutOrder(item.getKey());
                    treeObj.setChildren(this.setChildrenNodeByForm(allFormsInGroup, item.getKey(), treeObj, nodeKey));
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_108, (Throwable)e);
                }
                initTree.add(treeObj);
            }
        }
        return initTree;
    }

    private TreeObj initTestForm(String nodeKey, String formSchemeKey) {
        TreeObj treeObj = new TreeObj();
        treeObj.setId(NUMBER_FORMULAS);
        treeObj.setTitle(NUMBER_FORMULAS_TITLE);
        treeObj.setIsLeaf(Boolean.valueOf(true));
        Data data = new Data();
        data.setCode(NUMBER_FORMULAS);
        data.setKey(NUMBER_FORMULAS);
        data.setParentKey(formSchemeKey);
        data.setIsLeaf(true);
        treeObj.setData(data);
        if (NUMBER_FORMULAS.equals(nodeKey)) {
            this.setSelectNode(treeObj);
            treeObj.setSelected(true);
        }
        return treeObj;
    }

    private Data setDataNodeFormGroup(FormGroupDefine formGroupDefine, String formSchemeKey) {
        Data data = new Data();
        data.setCode(formGroupDefine.getCode());
        data.setKey(formGroupDefine.getKey());
        data.setParentKey(formSchemeKey);
        return data;
    }

    private List<TreeObj> setChildrenNodeByForm(List<FormDefine> allFormsInGroup, String formGroupKey, TreeObj treeObjByGroup, String nodeKey) {
        ArrayList<TreeObj> initTree = new ArrayList<TreeObj>();
        if (allFormsInGroup != null && !allFormsInGroup.isEmpty()) {
            allFormsInGroup.stream().forEach(item -> {
                TreeObj treeObj = new TreeObj();
                treeObj.setId(item.getKey());
                treeObj.setTitle(item.getFormCode() + " | " + item.getTitle());
                treeObj.setCode(item.getFormCode());
                treeObj.setIsLeaf(Boolean.valueOf(true));
                treeObj.setData(this.setDataNodeForm((FormDefine)item, formGroupKey));
                if (StringUtils.isNotEmpty((String)nodeKey) && nodeKey.equals(item.getKey())) {
                    this.setSelectNode(treeObj);
                    this.setSelectNode(treeObjByGroup);
                    treeObj.setSelected(true);
                }
                initTree.add(treeObj);
            });
        }
        return initTree;
    }

    private Data setDataNodeForm(FormDefine formDefine, String formGroupKey) {
        Data data = new Data();
        data.setCode(formDefine.getFormCode());
        data.setKey(StringUtils.isNotEmpty((String)formDefine.getKey()) ? formDefine.getKey() : NUMBER_FORMULAS);
        data.setParentKey(formGroupKey);
        data.setIsLeaf(true);
        return data;
    }

    private void setSelectNode(TreeObj treeObj) {
        treeObj.setExpended(Boolean.valueOf(true));
        treeObj.getData().setExpended(true);
        treeObj.getData().setSelected(true);
    }
}

