/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.web.GcRelatedItemQueryClient;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcRelatedItemQueryController
implements GcRelatedItemQueryClient {
    @Autowired
    private GcRelatedItemQueryService itemQueryService;

    @Override
    public BusinessResponseEntity<List<GcRelatedItemEO>> queryByOffsetCondition(@RequestBody BalanceCondition queryCondition) {
        return BusinessResponseEntity.ok((Object)this.itemQueryService.queryByOffsetCondition(queryCondition));
    }

    @Override
    public BusinessResponseEntity<List<GcRelatedItemEO>> queryByIds(@RequestBody Set<String> ids) {
        return BusinessResponseEntity.ok((Object)this.itemQueryService.queryByIds(ids));
    }
}

