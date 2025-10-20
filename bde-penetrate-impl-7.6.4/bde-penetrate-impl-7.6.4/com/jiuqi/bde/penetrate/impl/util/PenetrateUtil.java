/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 */
package com.jiuqi.bde.penetrate.impl.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.utils.CommonUtil;

public class PenetrateUtil {
    public static BalanceCondition convert2Condi(PenetrateBaseDTO condi, String computationModel) {
        BalanceCondition fetchCondi = (BalanceCondition)BeanConvertUtil.convert((Object)condi, BalanceCondition.class, (String[])new String[0]);
        fetchCondi.setComputationModel(computationModel);
        fetchCondi.setEnableDirectFilter(true);
        return fetchCondi;
    }

    public static String replaceContext(String srcSql, PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)srcSql);
        Assert.isNotNull((Object)condi);
        String period = String.valueOf(condi.getEndPeriod());
        String acctYear = String.valueOf(condi.getAcctYear().toString());
        String sql = srcSql.replaceAll("(?i)#unitCode#", condi.getOrgMapping().getAcctOrgCode()).replaceAll("(?i)#orgid#", condi.getOrgMapping().getAcctOrgCode()).replaceAll("(?i)#\\*", condi.getOrgMapping().getAcctOrgCode()).replaceAll("(?i)#yearperiod#", ModelExecuteUtil.buildPeriod((String)acctYear, (String)period)).replaceAll("(?i)#year#", acctYear).replaceAll("(?i)#period#", period).replaceAll("(?i)#fullperiod#", CommonUtil.lpad((String)period, (String)"0", (int)2)).replaceAll("(?i)#bookid#", condi.getOrgMapping().getAcctBookCode()).replaceAll("(?i)#includeuncharged#", condi.getIncludeUncharged() != false ? "0" : "1");
        return sql;
    }
}

