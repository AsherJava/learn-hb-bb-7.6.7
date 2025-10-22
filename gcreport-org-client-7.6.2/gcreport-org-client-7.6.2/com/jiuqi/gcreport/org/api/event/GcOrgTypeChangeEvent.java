/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.event;

import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.enums.GcOrgCacheConst;
import com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;

public class GcOrgTypeChangeEvent
extends GcOrgBaseEvent<OrgTypeVO> {
    private static final long serialVersionUID = 1L;

    public GcOrgTypeChangeEvent(EventChangeTypeEnum ect, OrgTypeVO source) {
        super(ect, source == null ? null : GcOrgCacheConst.getOrgTypeKey(source.getName()), source);
    }
}

