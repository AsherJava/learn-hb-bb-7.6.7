/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.CrossTaskLocateInfo;
import com.jiuqi.nr.jtable.params.output.FormAccessResult;
import com.jiuqi.nr.jtable.service.IFormAuthorityServive;
import com.jiuqi.nr.jtable.service.IJtableLocateService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableLocateService
implements IJtableLocateService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private List<IFormAuthorityServive> iFormAuthorityServives;

    @Override
    public CrossTaskLocateInfo getCrossTaskLocateInfo(JtableContext context) throws Exception {
        CrossTaskLocateInfo crossTaskLocateInfo = new CrossTaskLocateInfo();
        IFormAuthorityServive formAuthorityServive = this.getFormAuthorityServive(context.getAccessCode());
        List schemePeriods = this.runTimeViewController.querySchemePeriodLinkByTask(context.getTaskKey());
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        DimensionValue periodDimensionValue = dimensionSet.get("DATATIME");
        String period = periodDimensionValue.getValue();
        String schemeKey = "";
        for (SchemePeriodLinkDefine schemePeriod : schemePeriods) {
            if (!period.equals(schemePeriod.getPeriodKey())) continue;
            schemeKey = schemePeriod.getSchemeKey();
            break;
        }
        if (StringUtils.isEmpty((String)schemeKey)) {
            crossTaskLocateInfo.setHaveAccess(false);
            crossTaskLocateInfo.setMessage("\u672a\u627e\u5230\u5173\u8054\u4efb\u52a1\u7684\u62a5\u8868\u65b9\u6848\uff0c\u65e0\u6cd5\u5b9a\u4f4d\uff01");
            return crossTaskLocateInfo;
        }
        context.setFormSchemeKey(schemeKey);
        FormAccessResult caRead = new FormAccessResult(false, "\u60a8\u6ca1\u6709\u6743\u9650\u8bbf\u95ee\u5173\u8054\u4efb\u52a1\u7684\u62a5\u8868\uff0c\u65e0\u6cd5\u5b9a\u4f4d\uff01");
        if (formAuthorityServive != null) {
            caRead = formAuthorityServive.caRead(context);
        }
        if (caRead.isHaveAccess()) {
            FormDefine formDefine;
            crossTaskLocateInfo.setFormSchemeKey(schemeKey);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTaskKey());
            if (taskDefine != null) {
                crossTaskLocateInfo.setTaskTitle(taskDefine.getTitle());
            }
            if ((formDefine = this.runTimeViewController.queryFormById(context.getFormKey())) != null) {
                crossTaskLocateInfo.setFormTitle(formDefine.getTitle());
            }
        } else {
            crossTaskLocateInfo.setMessage("\u60a8\u6ca1\u6709\u6743\u9650\u8bbf\u95ee\u5173\u8054\u4efb\u52a1\u7684\u62a5\u8868\uff0c\u65e0\u6cd5\u5b9a\u4f4d\uff01");
        }
        crossTaskLocateInfo.setHaveAccess(caRead.isHaveAccess());
        return crossTaskLocateInfo;
    }

    private IFormAuthorityServive getFormAuthorityServive(String accessCode) {
        if (StringUtils.isEmpty((String)accessCode)) {
            accessCode = "dataentry";
        }
        for (IFormAuthorityServive iFormAuthorityServive : this.iFormAuthorityServives) {
            String accessCode2 = iFormAuthorityServive.getAccessCode();
            if (!accessCode.equals(accessCode2)) continue;
            return iFormAuthorityServive;
        }
        return null;
    }
}

