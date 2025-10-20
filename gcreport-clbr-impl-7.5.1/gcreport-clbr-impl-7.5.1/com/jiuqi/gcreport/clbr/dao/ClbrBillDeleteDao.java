/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.clbr.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.entity.ClbrBillDeleteEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import java.util.Set;

public interface ClbrBillDeleteDao
extends IBaseSqlGenericDAO<ClbrBillDeleteEO> {
    public Integer getRejectClbrBillCountByRelationCodeAndBillType(Set<String> var1, Integer var2);

    public PageInfo<ClbrBillDeleteEO> listClbrBillDetailsByRelationCodeAndBillType(ClbrDataQueryConditon var1, ClbrBillStateEnum var2);
}

