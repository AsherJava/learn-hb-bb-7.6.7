/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nr.workflow2.service.execute.runtime.ExecuteTimeSetting
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IExecuteTimeSetting
 *  com.jiuqi.nr.workflow2.service.helper.IProcessExecuteTimeHelper
 */
package com.jiuqi.nr.workflow2.converter.ext.time.setting;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nr.workflow2.service.execute.runtime.ExecuteTimeSetting;
import com.jiuqi.nr.workflow2.service.execute.runtime.IExecuteTimeSetting;
import com.jiuqi.nr.workflow2.service.helper.IProcessExecuteTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessExecuteTimeHelper
implements IProcessExecuteTimeHelper {
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;

    public IExecuteTimeSetting getExecuteTimeSetting(FormSchemeDefine formSchemeDefine, DimensionCombination combination) {
        MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(formSchemeDefine.getKey(), combination.toDimensionValueSet());
        ExecuteTimeSetting timeSetting = new ExecuteTimeSetting();
        timeSetting.setInTimeRange(!compareSetTime.isDisabled());
        timeSetting.setTipMessage(compareSetTime.getMsg());
        return timeSetting;
    }
}

