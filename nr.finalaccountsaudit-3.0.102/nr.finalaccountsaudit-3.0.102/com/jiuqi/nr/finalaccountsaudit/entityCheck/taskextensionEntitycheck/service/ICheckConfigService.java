/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.service;

import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigItemStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigRequsetParam;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.MatchTypeStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.SelectStructure;
import java.util.List;

public interface ICheckConfigService {
    public List<SelectStructure> getTasks();

    public List<SelectStructure> getFormSchemes(ConfigRequsetParam var1);

    public int getPeriodType(ConfigRequsetParam var1);

    public List<MatchTypeStructure> getMatching();

    public List<ConfigItemStructure> getConfigItemsByType(ConfigRequsetParam var1);

    public List<EnumStructure> getBBLXEnumData(ConfigRequsetParam var1);

    public ExtensionBasicModel<?> queryConfigData(ConfigRequsetParam var1);

    public List<SelectStructure> removeSavedTasks(ConfigRequsetParam var1);

    public List<SelectStructure> removeSavedFormSchemesByTask(ConfigRequsetParam var1);
}

