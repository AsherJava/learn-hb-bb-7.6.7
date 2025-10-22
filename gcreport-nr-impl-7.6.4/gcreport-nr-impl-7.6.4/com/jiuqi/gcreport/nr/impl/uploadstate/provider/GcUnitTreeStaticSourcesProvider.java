/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.field.NodeIconVO
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.impl.extend.StaticSourceProvider
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.provider;

import com.jiuqi.gcreport.nr.impl.uploadstate.GcOrgQueryWithStateTool;
import com.jiuqi.gcreport.org.api.vo.field.NodeIconVO;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.impl.extend.StaticSourceProvider;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class GcUnitTreeStaticSourcesProvider
implements StaticSourceProvider {
    public Map<String, NodeIconVO> getBase64IconMap(OrgDataParam orgDataParam) {
        return GcOrgQueryWithStateTool.getOrgTreeIconMap(orgDataParam.getFormSchemeKey(), orgDataParam.getOrgType());
    }
}

