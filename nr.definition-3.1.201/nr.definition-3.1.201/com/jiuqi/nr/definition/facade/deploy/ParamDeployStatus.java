/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.deploy;

import com.jiuqi.nr.definition.common.ParamDeployEnum;
import java.util.Date;

public interface ParamDeployStatus {
    public String getSchemeKey();

    public void setSchemeKey(String var1);

    public ParamDeployEnum.ParamStatus getParamStatus();

    public void setParamStatus(ParamDeployEnum.ParamStatus var1);

    public ParamDeployEnum.DeployStatus getDeployStatus();

    public void setDeployStatus(ParamDeployEnum.DeployStatus var1);

    public Date getDeployTime();

    public void setDeployTime(Date var1);

    public Date getLastDeployTime();

    public void setLastDeployTime(Date var1);

    public String getUserName();

    public void setUserName(String var1);

    public String getUserKey();

    public void setUserKey(String var1);

    public String getDeployDetail();

    public void setDeployDetail(String var1);

    public int getDdlStatus();

    public void setDdlStatus(int var1);
}

