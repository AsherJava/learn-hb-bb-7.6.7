/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.cache.service;

import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgQueryService;
import java.util.Date;

public interface FGcOrgEditService
extends FGcOrgQueryService<OrgToJsonVO> {
    public boolean add(GcOrgParam var1);

    public boolean relAdd(GcOrgParam var1);

    public boolean update(GcOrgParam var1);

    public boolean changeState(GcOrgParam var1);

    public boolean remove(GcOrgParam var1);

    public boolean recovery(GcOrgParam var1);

    public boolean upOrDown(GcOrgParam var1, boolean var2);

    public boolean move(GcOrgParam var1);

    @Override
    public OrgTypeVO getOrgType(String var1);

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO var1, Date var2);
}

