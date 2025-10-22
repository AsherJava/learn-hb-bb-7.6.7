/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 */
package com.jiuqi.gcreport.org.impl.fieldManager.service;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import java.util.List;

public interface GcOrgGenerateService {
    public void generateOrgTree(GcOrgTypeCopyApiParam var1);

    public List<OrgToJsonVO> generateOrgTreePreview(GcOrgTypeCopyApiParam var1);
}

