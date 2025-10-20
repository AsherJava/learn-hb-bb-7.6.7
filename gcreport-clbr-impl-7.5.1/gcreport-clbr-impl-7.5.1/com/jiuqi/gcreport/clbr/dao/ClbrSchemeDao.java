/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.clbr.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeEO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import java.util.List;

public interface ClbrSchemeDao
extends IBaseSqlGenericDAO<ClbrSchemeEO> {
    public void deleteByIds(List<String> var1);

    public PageInfo<ClbrSchemeEO> selectForPages(ClbrSchemeCondition var1);

    public List<ClbrSchemeEO> selectDirectChildSchemes(String var1);

    public List<ClbrSchemeEO> selectByIds(List<String> var1);
}

