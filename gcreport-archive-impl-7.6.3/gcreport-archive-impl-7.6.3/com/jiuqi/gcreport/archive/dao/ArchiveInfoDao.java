/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.archive.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveInfoDao
extends IDbSqlGenericDAO<ArchiveInfoEO, String> {
    public List<ArchiveInfoEO> queryByUnitAndPeriod(JtableContext var1, String var2, String var3);

    public List<ArchiveInfoEO> queryByUnitPeriodAndOrgType(JtableContext var1, String var2, String var3, String var4);

    public List<ArchiveInfoEO> getNeedUploadArchive(int var1);

    public List<ArchiveInfoEO> getNeedSendArchive(int var1);

    public PageInfo<ArchiveInfoEO> queryArchiveInfoByConid(ArchiveQueryParam var1);

    public List<ArchiveInfoEO> queryAllArchiveInfoByConid(ArchiveQueryParam var1);

    public void deleteByIds(List<String> var1);

    public List<ArchiveInfoEO> queryArchiveInfoByIds(List<String> var1);

    public List<ArchiveInfoEO> listInfoByLogIdAndStatus(String var1, ArchiveStatusEnum var2);
}

