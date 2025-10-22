/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.facade.UniversalTableDefine;
import java.util.Date;

public interface DesignUniversalTableDefine
extends IMetaItem,
TableDefine,
UniversalTableDefine {
    public void setCode(String var1);

    public void setDescription(String var1);

    public void setTableName(String var1);

    public void setKind(TableKind var1);

    public void setOwnerGroupID(String var1);

    public void setBizKeyFields(String var1);

    public void setBizKeyFields(String[] var1);

    public void setNeedSynchronize(boolean var1);

    public void setUpdateTime(Date var1);

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setIsAuTo(boolean var1);
}

