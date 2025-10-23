/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.calibre2.internal.service.impl;

import com.jiuqi.nr.calibre2.internal.service.IFormDataQueryService;
import com.jiuqi.nr.calibre2.vo.CommonReportFormVO;
import com.jiuqi.nr.calibre2.vo.ReportFormVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormDataQueryService
implements IFormDataQueryService {
    private static final Logger log = LoggerFactory.getLogger(FormDataQueryService.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public List<CommonReportFormVO> getTaskList(String entityId) {
        ArrayList<CommonReportFormVO> taskList = new ArrayList<CommonReportFormVO>();
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : allTaskDefines) {
            String dw = taskDefine.getDw();
            if (!StringUtils.hasText(dw) || !dw.equals(entityId)) continue;
            taskList.add(CommonReportFormVO.newTaskDefine(taskDefine));
        }
        return taskList;
    }

    @Override
    public List<CommonReportFormVO> getFormSchemeList(String taskId, String entityId) {
        ArrayList<CommonReportFormVO> schemeDefineList = new ArrayList<CommonReportFormVO>();
        List schemeDefines = null;
        try {
            schemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskId);
        }
        catch (Exception e) {
            log.error("\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u9519\u8bef", e);
        }
        for (FormSchemeDefine schemeDefine : schemeDefines) {
            String dw = null;
            if (StringUtils.hasText(schemeDefine.getDw())) {
                dw = schemeDefine.getDw();
            } else {
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(schemeDefine.getTaskKey());
                dw = taskDefine.getDw();
            }
            if (!entityId.equals(dw)) continue;
            schemeDefineList.add(CommonReportFormVO.newFormSchemeDefine(schemeDefine));
        }
        return schemeDefineList;
    }

    @Override
    public List<CommonReportFormVO> getFormList(String entityId) {
        ArrayList<CommonReportFormVO> formList = new ArrayList<CommonReportFormVO>();
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        ArrayList allFormSchemes = new ArrayList();
        for (TaskDefine taskDefine : allTaskDefines) {
            try {
                List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                allFormSchemes.addAll(formSchemeDefines);
            }
            catch (Exception e) {}
        }
        for (FormSchemeDefine formSchemeDefine : allFormSchemes) {
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
            for (FormDefine formDefine : formDefines) {
                formList.add(ReportFormVO.newReportForm(formDefine));
            }
        }
        return formList;
    }
}

