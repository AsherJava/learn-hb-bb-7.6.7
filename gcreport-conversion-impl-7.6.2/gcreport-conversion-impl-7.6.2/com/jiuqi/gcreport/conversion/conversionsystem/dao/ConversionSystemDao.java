/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import java.util.List;

public interface ConversionSystemDao
extends IDbSqlGenericDAO<ConversionSystemEO, String> {
    public List<ConversionSystemEO> getAllSystemList();

    public List<ConversionSystemEO> getAllSystemListByTaskId(String var1);

    public List<ConversionSystemTaskSchemeVO> getSystemTaskSchemes();

    public ConversionSystemEO querySystemByName(String var1);

    public ConversionSystemEO querySystemById(String var1);
}

