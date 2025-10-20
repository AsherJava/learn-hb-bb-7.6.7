/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.rewritesetting.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSubjectSettingEO;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface RewriteSubjectSettingDao
extends IDbSqlGenericDAO<RewriteSubjectSettingEO, String> {
    public void deleteRewriteSubjectSetting(@RequestBody List<String> var1);

    public List<RewriteSubjectSettingEO> queryRewriteSubjectSettings(String var1);

    public void deleteRewriteSubjectSettingBySchemeId(String var1);
}

