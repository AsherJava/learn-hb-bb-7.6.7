/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 */
package com.jiuqi.nr.formulaeditor.internal.param;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.ParamObj;
import com.jiuqi.nr.formulaeditor.internal.param.core.DefaultFormulaEditor;
import com.jiuqi.nr.formulaeditor.vo.SchemeData;
import com.jiuqi.nr.formulaeditor.vo.TaskData;
import com.jiuqi.nr.formulaeditor.vo.TaskParam;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AllFormulaEditor
extends DefaultFormulaEditor {
    @Override
    public String getId() {
        return "ALL_FORMULA_EDITOR_ID";
    }

    @Override
    public TaskParam queryTaskData(EditObject editObject) {
        if (StringUtils.hasLength(editObject.getSchemeId())) {
            TaskParam taskParam = new TaskParam();
            taskParam.setTasks(new ArrayList<TaskData>());
            DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.getFormScheme(editObject.getSchemeId());
            DesignTaskDefine task = this.iDesignTimeViewController.getTask(formScheme.getTaskKey());
            TaskData currTask = new TaskData();
            currTask.setKey(task.getKey());
            currTask.setTitle("\u5f53\u524d\u4efb\u52a1");
            taskParam.getTasks().add(currTask);
            List designTaskLinkDefines = this.iDesignTimeViewController.listTaskLinkByFormScheme(formScheme.getKey());
            for (DesignTaskLinkDefine designTaskLinkDefine : designTaskLinkDefines) {
                DesignFormSchemeDefine taskLinkScheme = this.iDesignTimeViewController.getFormScheme(designTaskLinkDefine.getRelatedFormSchemeKey());
                if (null == taskLinkScheme) continue;
                DesignTaskDefine taskLinkTask = this.iDesignTimeViewController.getTask(taskLinkScheme.getTaskKey());
                TaskData taskLink = new TaskData();
                taskLink.setKey(taskLinkScheme.getKey().concat("@" + designTaskLinkDefine.getLinkAlias()));
                taskLink.setTitle(taskLinkTask.getTitle() + "@" + designTaskLinkDefine.getLinkAlias());
                taskLink.setAlias(designTaskLinkDefine.getLinkAlias());
                taskParam.getTasks().add(taskLink);
            }
            return taskParam;
        }
        return super.queryTaskData(editObject);
    }

    @Override
    public List<SchemeData> querySchemeData(EditObject editObject, ParamObj paramObj) {
        if (StringUtils.hasLength(paramObj.getTaskKey())) {
            ArrayList<SchemeData> data = new ArrayList<SchemeData>();
            DesignFormSchemeDefine formScheme = null;
            if (editObject.getTaskId().equals(paramObj.getTaskKey())) {
                formScheme = this.iDesignTimeViewController.getFormScheme(editObject.getSchemeId());
            } else {
                String substring = paramObj.getTaskKey().substring(0, paramObj.getTaskKey().indexOf("@"));
                formScheme = this.iDesignTimeViewController.getFormScheme(substring);
            }
            if (null == formScheme) {
                return super.querySchemeData(editObject, paramObj);
            }
            SchemeData schemeData = new SchemeData();
            schemeData.setKey(formScheme.getKey());
            schemeData.setTitle(formScheme.getTitle());
            data.add(schemeData);
            return data;
        }
        return super.querySchemeData(editObject, paramObj);
    }
}

