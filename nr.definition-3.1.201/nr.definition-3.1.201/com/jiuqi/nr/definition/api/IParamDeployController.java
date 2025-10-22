/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.common.ProgressConsumer;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;

public interface IParamDeployController {
    public ParamDeployStatus getDeployStatus(String var1);

    default public Date getDeployTime(String schemeKey) {
        return this.getDeployTime(ParamResourceType.FORM, schemeKey);
    }

    public Date getDeployTime(ParamResourceType var1, String var2);

    public void updateDeployWarning(String var1, String var2);

    public void updateParamStatus(String var1, ParamDeployEnum.ParamStatus var2);

    public void deploy(String var1, boolean var2, ProgressConsumer var3);

    default public void deploy(String formSchemeKey, List<String> formKeys) {
        this.deploy(formSchemeKey, formKeys, (ParamResourceType type, IMetaItem item) -> true);
    }

    default public void deploy(String formSchemeKey, List<String> formKeys, BiPredicate<ParamResourceType, IMetaItem> filter) {
        this.deploy(formSchemeKey, formKeys, false, filter);
    }

    public void deploy(String var1, List<String> var2, boolean var3, BiPredicate<ParamResourceType, IMetaItem> var4);

    public void deploy(ParamResourceType var1, String var2, List<String> var3);
}

