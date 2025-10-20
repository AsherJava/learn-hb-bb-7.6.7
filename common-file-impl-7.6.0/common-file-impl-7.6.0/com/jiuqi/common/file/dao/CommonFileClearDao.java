/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.common.file.dao;

import com.jiuqi.common.file.entity.CommonFileClearEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import java.util.List;

public interface CommonFileClearDao
extends IBaseSqlGenericDAO<CommonFileClearEO> {
    public List<CommonFileClearEO> queryExpiredFiles();
}

