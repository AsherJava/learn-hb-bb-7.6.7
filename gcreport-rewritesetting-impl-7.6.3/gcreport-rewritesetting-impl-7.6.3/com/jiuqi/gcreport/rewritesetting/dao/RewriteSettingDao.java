/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.rewritesetting.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface RewriteSettingDao
extends IDbSqlGenericDAO<RewriteSettingEO, String> {
    public void deleteRewriteSetting(@RequestBody List<String> var1);

    public void deleteRewriteSettingByGroupId(String var1);

    public List<RewriteSettingEO> queryRewriteSettings(String var1);

    public RewriteSettingEO queryRewriteSettingsById(String var1);
}

