/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.aidocaudit.dao;

import com.jiuqi.gcreport.aidocaudit.dto.ResultDetailDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultItemAndRuleNameDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultitemEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface IAidocauditResultItemDao
extends IDbSqlGenericDAO<AidocauditResultitemEO, String> {
    public List<AidocauditResultitemEO> queryByResultIds(List<String> var1);

    public List<ResultItemAndRuleNameDTO> queryOrgQuestionStatus(List<String> var1);

    public List<ResultitemOrderDTO> queryDataByOrder(String var1);

    public List<ResultDetailDTO> queryRuleAuditResultDetail(List<String> var1, String var2, Boolean var3);
}

