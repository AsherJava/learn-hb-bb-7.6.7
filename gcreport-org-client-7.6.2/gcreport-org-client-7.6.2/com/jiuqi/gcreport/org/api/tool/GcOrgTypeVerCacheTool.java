/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.tool;

import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.Date;
import java.util.List;

public interface GcOrgTypeVerCacheTool {
    public List<OrgTypeVO> listOrgType();

    public OrgTypeVO getOrgTypeByName(String var1);

    public List<OrgVersionVO> getOrgVersionByType(String var1);

    public OrgVersionVO getOrgVersionByType(String var1, Date var2);
}

