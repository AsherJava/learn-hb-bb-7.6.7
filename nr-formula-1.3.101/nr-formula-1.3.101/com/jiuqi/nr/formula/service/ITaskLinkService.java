/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.TaskDataDTO;
import com.jiuqi.nr.formula.web.vo.PeriodTypeVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkEntityQueryVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkSettingVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkVO;
import java.util.List;

public interface ITaskLinkService {
    public TaskLinkSettingVO getTaskLinkSetting(String var1);

    public List<TaskLinkVO> listTaskLinks(String var1);

    public List<TaskDataDTO> getTaskList();

    public TaskLinkEntityQueryVO queryRelatedTaskEntities(String var1);

    public void saveTaskLinks(List<TaskLinkVO> var1);

    public PeriodTypeVO getPeriodType(String var1);
}

