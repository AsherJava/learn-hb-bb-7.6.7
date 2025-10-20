/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.datamapping.impl.utils.BaseDataDataRefChecker;
import com.jiuqi.dc.datamapping.impl.utils.IDataRefChcker;
import com.jiuqi.dc.datamapping.impl.utils.IDataRefChckerGather;
import com.jiuqi.dc.datamapping.impl.utils.OrgDataRefChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataRefChckerGather
implements IDataRefChckerGather {
    @Autowired
    private OrgDataRefChecker orgDataRefChecker;
    @Autowired
    private BaseDataDataRefChecker baseDataDataRefChecker;

    @Override
    public IDataRefChcker getChecker(String tableName) {
        Assert.isNotEmpty((String)tableName);
        if ("MD_ORG".equals(tableName)) {
            return this.orgDataRefChecker;
        }
        return this.baseDataDataRefChecker;
    }
}

