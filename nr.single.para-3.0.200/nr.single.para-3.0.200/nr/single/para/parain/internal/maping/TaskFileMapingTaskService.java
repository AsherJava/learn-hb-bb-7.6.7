/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileTaskInfo
 */
package nr.single.para.parain.internal.maping;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.single.core.para.ParaInfo;
import java.util.Date;
import java.util.List;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping.ITaskFileMapingTaskService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileMapingTaskService
implements ITaskFileMapingTaskService {
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private IParaImportCommonService paraCommonService;

    @Override
    public void UpdateContextCache(TaskImportContext importContext) {
        DesignTaskDefine task = null;
        if (null != importContext.getTaskKey()) {
            task = this.viewController.queryTaskDefine(importContext.getTaskKey());
            importContext.setTaskDefine(task);
        }
        DesignFormSchemeDefine formScheme = null;
        if (null != importContext.getFormSchemeKey()) {
            formScheme = this.viewController.queryFormSchemeDefine(importContext.getFormSchemeKey());
            importContext.getSchemeInfoCache().setFormScheme(formScheme);
        }
    }

    @Override
    public void UpdateMapSchemeDefineByTask(TaskImportContext importContext) {
        ParaInfo para;
        DesignTaskDefine task;
        SingleFileTaskInfo mapTask = importContext.getMapScheme().getTaskInfo();
        mapTask.setUpdateTime(new Date());
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        if (null != formScheme) {
            mapTask.setNetFormSchemeFlag(formScheme.getTaskPrefix());
            mapTask.setNetFormSchemeKey(formScheme.getKey());
            mapTask.setNetFormSchemeTitle(formScheme.getTitle());
        }
        if (null != (task = importContext.getTaskDefine())) {
            mapTask.setNetTaskFlag(task.getTaskCode());
            mapTask.setNetTaskKey(task.getKey());
            mapTask.setNetTaskTitle(task.getTitle());
        }
        if (null != (para = importContext.getParaInfo())) {
            mapTask.setSingleFileFlag(para.getFileFlag());
            mapTask.setSingleTaskFlag(para.getPrefix());
            mapTask.setSingleTaskTitle(para.getTaskName());
            mapTask.setSingleTaskYear(para.getTaskYear());
            mapTask.setSingleTaskPeriod(para.getTaskType());
            mapTask.setSingleTaskTime(para.getTaskTime());
            mapTask.setParaVersion(para.getTaskVerion());
            mapTask.setSingleFloatOrderField(para.getFloatOrderField());
        }
        SingleFileFmdmInfo fmdmInfo = importContext.getMapScheme().getFmdmInfo();
        fmdmInfo.getZdmFieldCodes().clear();
        if (null != para) {
            List zdms = para.getFmRepInfo().getZdmFields();
            List zbNameList = para.getFmRepInfo().getDefs().getZbNameList();
            for (String zdm : zdms) {
                fmdmInfo.getZdmFieldCodes().add(zdm);
            }
            fmdmInfo.setZdmLength(para.getFmRepInfo().getZDMLength());
            if (para.getFmRepInfo().getBBLXFieldId() > 0) {
                fmdmInfo.setBBLXField((String)zbNameList.get(para.getFmRepInfo().getBBLXFieldId()));
            }
            if (para.getFmRepInfo().getDWDMFieldId() > 0) {
                fmdmInfo.setDWDMField((String)zbNameList.get(para.getFmRepInfo().getDWDMFieldId()));
            }
            if (para.getFmRepInfo().getDWMCFieldId() > 0) {
                fmdmInfo.setDWMCField((String)zbNameList.get(para.getFmRepInfo().getDWMCFieldId()));
            }
            if (para.getFmRepInfo().getSJDMFieldId() > 0) {
                fmdmInfo.setSJDMField((String)zbNameList.get(para.getFmRepInfo().getSJDMFieldId()));
            }
            if (para.getFmRepInfo().getZBDMFieldId() > 0) {
                fmdmInfo.setZBDMField((String)zbNameList.get(para.getFmRepInfo().getZBDMFieldId()));
            }
            if (para.getFmRepInfo().getSNDMFieldId() > 0) {
                fmdmInfo.setSNDMField((String)zbNameList.get(para.getFmRepInfo().getSNDMFieldId()));
            }
            if (para.getFmRepInfo().getXBYSFieldId() > 0) {
                fmdmInfo.setXBYSField((String)zbNameList.get(para.getFmRepInfo().getXBYSFieldId()));
            }
            if (para.getFmRepInfo().getSQFieldId() > 0) {
                fmdmInfo.setPeriodField((String)zbNameList.get(para.getFmRepInfo().getSQFieldId()));
            }
        }
    }
}

