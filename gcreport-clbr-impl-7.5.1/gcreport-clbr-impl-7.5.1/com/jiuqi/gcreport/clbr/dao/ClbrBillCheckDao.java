/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.clbr.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillCheckEO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import java.util.List;
import java.util.Set;

public interface ClbrBillCheckDao
extends IBaseSqlGenericDAO<ClbrBillCheckEO> {
    public List<ClbrBillCheckEO> queryGroupByBillIds(Set<String> var1);

    public PageInfo<ClbrBillCheckEO> listConfirmPageByCondition(ClbrProcessCondition var1);

    public List<ClbrBillCheckEO> listByGroupIds(List<String> var1);

    public void deleteByGroupIds(List<String> var1);

    public void updateConfirmStatusByGroupIds(List<String> var1, String var2);

    public PageInfo<ClbrBillCheckEO> listInitiatorConfirmByUser(ClbrBillSsoConditionDTO var1);
}

