/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.archive.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface ArchiveLogDao
extends IDbSqlGenericDAO<ArchiveLogEO, String> {
    public PageInfo<ArchiveLogEO> querybatchLogByConid(ArchiveQueryParam var1, String var2);

    public List<String> getIdListByConid(ArchiveQueryParam var1, String var2);

    public List<ArchiveLogEO> getInExecTaskLogByUnit(String var1, String var2, String var3, String var4);
}

