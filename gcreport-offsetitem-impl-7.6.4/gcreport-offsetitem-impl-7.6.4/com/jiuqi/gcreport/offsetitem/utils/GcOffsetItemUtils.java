/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;

public class GcOffsetItemUtils {
    public static void logOffsetEntryFilterCondition(QueryParamsVO queryParamsDto, String tabTitle) {
        if (!queryParamsDto.getFilterCondition().isEmpty()) {
            String taskTitle = GcOffsetItemUtils.getTaskTitle(queryParamsDto.getTaskId());
            String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(new PeriodWrapper(queryParamsDto.getPeriodStr()));
            String logTitle = String.format("\u7b5b\u9009-%1$s-\u4efb\u52a1%2$s-\u65f6\u671f%3$s", tabTitle, taskTitle, periodTitle);
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)logTitle, (String)JsonUtils.writeValueAsString((Object)queryParamsDto.getFilterCondition()));
        }
    }

    public static String getTaskTitle(String taskId) {
        try {
            TaskDefine taskDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).queryTaskDefine(taskId);
            if (taskDefine != null) {
                return taskDefine.getTitle();
            }
        }
        catch (Exception e) {
            return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.getTaskTitleError", (Object[])new Object[]{e.getMessage()});
        }
        return "";
    }
}

