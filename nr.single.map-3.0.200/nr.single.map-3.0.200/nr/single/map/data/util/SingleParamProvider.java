/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.exception.NotFoundTaskFlowException
 */
package nr.single.map.data.util;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.exception.NotFoundTaskFlowException;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.bean.FlowData;
import nr.single.map.data.bean.FormSchemeData;
import nr.single.map.data.bean.PrintSchemeData;
import nr.single.map.data.bean.TaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleParamProvider {
    private RunTimeAuthViewController runTimeAuthViewController = (RunTimeAuthViewController)BeanUtil.getBean(RunTimeAuthViewController.class);
    private IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
    private IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
    private IPrintRunTimeController printRunTimeController = (IPrintRunTimeController)BeanUtil.getBean(IPrintRunTimeController.class);
    private static Logger log = LoggerFactory.getLogger(SingleParamProvider.class);

    public List<TaskData> getRuntimeTaskList() {
        ArrayList<TaskData> taskList = new ArrayList<TaskData>();
        List allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            TaskData taskData = new TaskData();
            taskData.init(taskDefine);
            taskList.add(taskData);
        }
        return taskList;
    }

    public TaskData getRuntimeTaskByKey(String taskKey) {
        TaskDefine taskDefine = null;
        try {
            this.runTimeAuthViewController.initTask(taskKey);
            taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        if (taskDefine != null) {
            TaskData task = new TaskData();
            task.init(taskDefine);
            return task;
        }
        return null;
    }

    public List<FormSchemeData> getRuntimeFormSchemeList(String taskKey) {
        ArrayList<FormSchemeData> list = new ArrayList<FormSchemeData>();
        List queryAllFormSchemeDefines = null;
        TaskDefine taskDefine = null;
        if (taskKey != null) {
            try {
                this.runTimeAuthViewController.initTask(taskKey);
                taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
                queryAllFormSchemeDefines = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            queryAllFormSchemeDefines = new ArrayList();
        }
        if (queryAllFormSchemeDefines == null || queryAllFormSchemeDefines.isEmpty()) {
            throw new NotFoundFormSchemeException(null);
        }
        for (FormSchemeDefine formSchemeDefine : queryAllFormSchemeDefines) {
            FormSchemeData formSchemeData = new FormSchemeData();
            formSchemeData.init(this.formulaRunTimeController, this.dataAccessProvider, formSchemeDefine, taskDefine);
            List printSchemes = null;
            try {
                printSchemes = this.printRunTimeController.getAllPrintSchemeByFormScheme(formSchemeData.getKey());
                for (PrintTemplateSchemeDefine designPrintTemplateSchemeDefine : printSchemes) {
                    PrintSchemeData printSchemeData = new PrintSchemeData();
                    printSchemeData.setKey(designPrintTemplateSchemeDefine.getKey());
                    printSchemeData.setTitle(designPrintTemplateSchemeDefine.getTitle());
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            FlowData schemeFlowData = new FlowData();
            TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
            if (flowsSetting != null) {
                schemeFlowData.init(flowsSetting);
            }
            if (schemeFlowData.getEntitys().size() == 0) {
                schemeFlowData = this.getTaskFlow(taskKey);
            }
            list.add(formSchemeData);
        }
        return list;
    }

    private FlowData getTaskFlow(String taskKey) {
        FlowData taskFlowData = new FlowData();
        if (taskKey != null) {
            try {
                TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
                TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
                if (flowsSetting != null) {
                    taskFlowData.init(flowsSetting);
                    return taskFlowData;
                }
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            throw new NotFoundTaskFlowException(null);
        }
        return taskFlowData;
    }
}

