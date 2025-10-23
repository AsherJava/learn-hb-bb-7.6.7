/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.internal.dto.PropInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;

public interface IZbSchemeValidator {
    public void checkZbScheme(ZbSchemeDTO var1);

    public void checkZbSchemeGroup(ZbSchemeGroup var1);

    public void checkPropInfo(PropInfo var1);

    public void checkZbGroup(ZbGroup var1);

    public void checkSchemeVersionBeforeDelete(String var1);

    public void checkZbPropBeforeUpdate(PropInfoDTO var1);

    public void checkSchemeVersionBeforeUpdate(ZbSchemeVersion var1);

    public void checkZbGroupBeforeDelete(ZbGroupDO var1);

    public void checkZbGroupBeforeUpdate(ZbGroup var1);

    public void checkZbInfoBeforeUpdate(ZbInfo var1);

    public void checkZbSchemeVersion(String var1);

    public void checkZbPropBeforeDelete(PropInfoDTO var1);

    public void checkZbSchemeGroupBeforeDelete(String var1);

    public void checkZbSchemeBeforeDelete(String var1);

    public void checkZbInfo(ZbInfo var1);

    public void checkZbInfoBeforeDelete(ZbInfoDO var1);

    public void checkZbSchemeVersion(ZbSchemeVersion var1);
}

