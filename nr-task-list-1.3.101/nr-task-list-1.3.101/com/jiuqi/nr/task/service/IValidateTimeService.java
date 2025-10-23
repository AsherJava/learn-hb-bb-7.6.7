/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.service;

import com.jiuqi.nr.task.dto.ValidateTimeDTO;
import com.jiuqi.nr.task.web.vo.ValidateTimeCheckResultVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeMergeVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeSettingVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import java.util.List;

public interface IValidateTimeService {
    public void insertDefaultSchemePeriodLink(String var1, String var2, String var3, String var4);

    public List<ValidateTimeVO> queryByTask(String var1);

    public List<ValidateTimeVO> queryByFormScheme(String var1);

    public ValidateTimeSettingVO queryLimitedOptions(String var1);

    public List<ValidateTimeVO> merge(ValidateTimeMergeVO var1);

    public ValidateTimeCheckResultVO check(List<ValidateTimeVO> var1);

    public ValidateTimeCheckResultVO checkEmptyPeriod(List<ValidateTimeVO> var1);

    public ValidateTimeCheckResultVO checkAtTaskLimit(String var1, List<ValidateTimeVO> var2);

    public void save(List<ValidateTimeDTO> var1, int var2);

    public void doSave(List<ValidateTimeVO> var1, int var2);
}

