/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO
 */
package com.jiuqi.common.plantask.extend.service;

import com.jiuqi.common.plantask.extend.vo.SettingPageTemplateVO;
import com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO;

public interface PlanTaskSettingPageService {
    public SettingPageTemplateVO getTemplateByCode(String var1);

    public JobLogDetailVO queryPlanTaskLogDetailById(String var1);
}

