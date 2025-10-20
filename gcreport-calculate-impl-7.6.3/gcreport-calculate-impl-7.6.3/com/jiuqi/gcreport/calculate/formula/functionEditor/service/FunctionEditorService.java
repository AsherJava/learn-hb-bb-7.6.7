/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.TaskSchemeVO
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData
 */
package com.jiuqi.gcreport.calculate.formula.functionEditor.service;

import com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.TaskSchemeVO;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import java.util.List;

public interface FunctionEditorService {
    public List<TreeNode> getAllTableDefinesGroups();

    public List<TreeNode> getTabDefineByTableKind(TableKind var1);

    public List<TreeNode> getFieldsInTableForFunctionTree(String var1);

    public void addFunction(FunctionEditorVO var1);

    public void deleteFunction(String var1);

    public List<GcFunctionEditorEO> getFunctionsByUserId(Integer var1, Integer var2);

    public int queryAllCountByUserId();

    public List<FunctionTreeVO> getInheritFunctionsTree(Boolean var1);

    public List<TaskSchemeVO> getBondTaskAndSchemeVOS();

    public List<TaskSchemeVO> getBondTaskAndSchemeVOSByTaskKeyList(String[] var1);

    public ReturnObject checkFunction(String var1, List<String> var2, boolean var3);

    public FormulaDesignFormData getFormData(String var1);

    public List<TaskSchemeVO> getTaskSchemeByFormScheme(String var1);

    public List<TaskSchemeVO> getBondTaskAndSchemeByDataScheme(String var1);
}

