/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.GcFormulaDiffRewriteWayEnum
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.GcFormulaDiffRewriteWayEnum;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.IGcDiffUnitReWriteSubTask;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.bde.GcBdeExecuteTaskImpl;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDiffUnitBdeReWriteSubTaskImpl
implements IGcDiffUnitReWriteSubTask {
    @Autowired
    RuntimeViewController runtimeViewController;
    @Autowired
    private GcBdeExecuteTaskImpl bdeExecuteTask;

    @Override
    public String getName() {
        return GcFormulaDiffRewriteWayEnum.BDE.getCode();
    }

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog) {
        ReturnObject returnObject = new ReturnObject();
        DimensionParamsVO param = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, param);
        for (String diffUnitId : diffUnitIds) {
            String formDefines;
            OneKeyMergeUtils.checkStopOrNot(paramsVO);
            List<String> filterForms = OneKeyMergeUtils.getFilterLockedAndHiddenForm(paramsVO.getSchemeId(), diffUnitId, paramsVO);
            List<String> lockedForm = OneKeyMergeUtils.getLockedForm(paramsVO.getSchemeId(), diffUnitId, paramsVO);
            String lockTitle = lockedForm.stream().map(s -> {
                FormDefine formDefine = this.runtimeViewController.queryFormById(s);
                return formDefine.getTitle();
            }).collect(Collectors.joining("\uff0c"));
            if (!StringUtils.isEmpty((String)lockTitle)) {
                taskLog.writeWarnLog("\u5dee\u989d\u5355\u4f4d\u4ee5\u4e0b\u8868\u4e3a\u5df2\u9501\u5b9a\u6216\u4e0a\u62a5\u72b6\u6001\uff0c\u8df3\u8fc7\u56de\u5199\uff1a" + lockTitle, Float.valueOf(taskLog.getProcessPercent()), Integer.valueOf(100));
            }
            if (!(returnObject = this.bdeExecuteTask.doTask(paramsVO, formDefines = filterForms.stream().collect(Collectors.joining(";")), diffUnitId)).isSuccess()) break;
            if (returnObject.getData() == null) continue;
            taskLog.writeInfoLog(returnObject.getData().toString(), Float.valueOf(36.0f));
        }
        return returnObject;
    }
}

