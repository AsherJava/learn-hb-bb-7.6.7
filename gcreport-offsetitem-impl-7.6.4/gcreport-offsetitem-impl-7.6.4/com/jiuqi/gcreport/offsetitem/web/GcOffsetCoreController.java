/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcOffsetCoreController {
    private static final String GC_API_BASE_PATH = "/api/gcreport/v1/adjustingEntry";
    @Resource
    GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/executeOffsetActions"})
    public BusinessResponseEntity<Object> executeOffsetActions(@RequestBody GcOffsetExecutorVO gcOffsetExecutorVO) {
        GcOffSetItemAction action = this.gcOffSetItemAdjustExecutor.getActionForCode(gcOffsetExecutorVO);
        return BusinessResponseEntity.ok((Object)action.execute(gcOffsetExecutorVO));
    }
}

