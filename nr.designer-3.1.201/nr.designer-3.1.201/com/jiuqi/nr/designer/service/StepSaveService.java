/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.designer.sync.IAction;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.designer.web.treebean.TaskObject;
import com.jiuqi.nr.designer.web.treebean.TaskOrderObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StepSaveService {
    public TaskObject saveTask(TaskObject var1) throws Exception;

    public void stepSaveScheme(FormSchemeObj var1) throws Exception;

    public void stepSaveForm(FormObj var1) throws Exception;

    public void syncGridData(String var1, int var2, List<IAction> var3);

    public void stepSaveFormulaScheme(FormulaSchemeObject var1) throws Exception;

    public void stepChangeFormOrder(String var1, String var2, String var3) throws Exception;

    public void saveFormGroup(FormGroupObject var1) throws Exception;

    public void saveChangeFormGroupOrder(String var1, String var2) throws Exception;

    public void delFormWhenDelFormGroup(String var1, Boolean var2) throws Exception;

    public void delForm(String var1, boolean var2) throws Exception;

    public void saveTaskLinkage(TaskLinkObject[] var1) throws JQException;

    public ReturnObject saveFormula(Map<String, FormulaObj> var1) throws Exception;

    public ArrayList<TaskLinkObject> getTaskLinkObjBySchemeKey(String var1);

    public String getFormPropWhenSave(String var1) throws Exception;

    public FormObj setFormObjProperty(String var1, int var2) throws Exception;

    public void editTaskOrder(TaskOrderObject var1) throws Exception;
}

