/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.designer.web.facade.EntityObj;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FormCopyObj;
import com.jiuqi.nr.designer.web.facade.SaveEntityVO;
import com.jiuqi.nr.designer.web.facade.simple.SimpleFormGroupObj;
import com.jiuqi.nr.designer.web.rest.vo.CheckTaskTitleAvailable;
import com.jiuqi.nr.designer.web.rest.vo.FormTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.ParamToDesigner;
import com.jiuqi.nr.designer.web.rest.vo.TaskSchemeGroupTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.TaskTreeNode;
import java.util.List;
import java.util.Map;

public interface IDesignRestService {
    public String getFormData(ParamToDesigner var1) throws Exception;

    public String queryNewTaskTitle();

    public boolean isExistTaskByTitle(CheckTaskTitleAvailable var1) throws Exception;

    public List<EntityTables> getFormSchemeEntity(String var1, boolean var2) throws JQException;

    public List<ITree<TaskTreeNode>> getTaskFormSchemes() throws JQException;

    public List<ITree<TaskTreeNode>> getAllDefaultTaskSchemeItree() throws Exception;

    public void getValidationResult(String var1, String var2, Map<String, Object> var3);

    public List<TaskTreeNode> getSearchSchemeResult(String var1) throws JQException;

    public List<ITree<TaskTreeNode>> reloadTaskAndSchemes(TaskTreeNode var1) throws JQException;

    public List<ITree<FormTreeNode>> getFormTree(String var1);

    public EntityObj initEnum() throws JQException;

    public void saveEnum(SaveEntityVO var1) throws Exception;

    public EntityObj getEnum(String var1) throws Exception;

    public void deleteEnum(String var1) throws Exception;

    public List<ITree<TaskSchemeGroupTreeNode>> getGroupTree() throws JQException;

    public List<TaskSchemeGroupTreeNode> getSearchGroupResult(String var1) throws JQException;

    public List<ITree<TaskSchemeGroupTreeNode>> reloadGroupTree(TaskSchemeGroupTreeNode var1) throws JQException;

    public List<SimpleFormGroupObj> simpleFormGroupTree(String var1) throws JQException;

    public boolean taskParamCheck(ParamToDesigner var1) throws Exception;

    public List<TreeObj> getFormCopyTree(String var1) throws JQException;

    public FormCopyObj copyForm(FormCopyObj var1) throws Exception;

    public List<ITree<TaskTreeNode>> getTaskGroupAndTaskTree();
}

