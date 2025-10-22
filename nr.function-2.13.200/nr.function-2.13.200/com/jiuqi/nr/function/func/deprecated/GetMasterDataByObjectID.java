/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.nr.function.func.GetMasterData;

public class GetMasterDataByObjectID
extends GetMasterData
implements IReportFunction {
    private static final long serialVersionUID = 6179857263464801281L;

    @Override
    public String name() {
        return "GetMasterDataByObjectID";
    }

    public boolean isDeprecated() {
        return true;
    }
}

