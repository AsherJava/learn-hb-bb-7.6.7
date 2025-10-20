/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.event;

import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.enums.GcOrgCacheConst;
import com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.List;

public class GcOrgVersionChangeEvent
extends GcOrgBaseEvent<List<OrgVersionVO>> {
    private static final long serialVersionUID = 1L;

    public GcOrgVersionChangeEvent(EventChangeTypeEnum ect, OrgTypeVO type, List<OrgVersionVO> values) {
        super(ect, type == null ? null : GcOrgCacheConst.getOrgVersionKey(type), values);
    }
}

