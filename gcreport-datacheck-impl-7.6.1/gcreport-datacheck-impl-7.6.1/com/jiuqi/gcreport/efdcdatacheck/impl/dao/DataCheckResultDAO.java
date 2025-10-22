/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckResultEO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCheckResultDAO
extends IBaseSqlGenericDAO<EfdcCheckResultEO> {
    public void deleteBeforeCreateTime(Date var1);

    public List<EfdcCheckResultUnitVO> queryUnitsByAsynTaskId(String var1);

    public void deleteByAsynTaskId(String var1);

    public List<EfdcCheckResultEO> queryResultByAsynTaskId(GcBatchEfdcQueryParam var1);

    public Map<String, Integer> queryCountGroupByOrgId(String var1);

    public Map<String, Integer> queryCountGroupByOrgIdAndCurrency(String var1);
}

