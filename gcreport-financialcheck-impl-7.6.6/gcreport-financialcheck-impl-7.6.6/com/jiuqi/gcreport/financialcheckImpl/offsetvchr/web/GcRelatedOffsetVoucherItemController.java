/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.offsetvchr.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.GcRelatedOffsetVoucherItemService;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.web.GcRelatedOffsetVoucherItemClient;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcRelatedOffsetVoucherItemController
implements GcRelatedOffsetVoucherItemClient {
    @Autowired
    GcRelatedOffsetVoucherItemService offsetVoucherItemService;

    @Override
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherInfoVO>> queryByCheckId(String checkId) {
        return BusinessResponseEntity.ok(this.offsetVoucherItemService.queryOffSetVoucherInfo(checkId));
    }

    @Override
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherInfoVO>> queryManualOffsetResult(ManualCheckParam param) {
        if (CollectionUtils.isEmpty((Collection)param.getItemIds())) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u6709\u6548\u6570\u636e");
        }
        return BusinessResponseEntity.ok(this.offsetVoucherItemService.queryManualOffsetResult(param));
    }

    @Override
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherInfoVO>> queryByClbrCode(@PathVariable(name="clbrCode") String clbrCode) {
        return BusinessResponseEntity.ok(this.offsetVoucherItemService.queryByClbrCode(clbrCode));
    }

    @Override
    public BusinessResponseEntity<String> saveRelatedOffsetVchrInfo(List<GcRelatedOffsetVoucherInfoVO> items) {
        this.offsetVoucherItemService.saveRelatedOffsetVchrInfo(items);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    @Override
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherItemEO>> queryByOffsetCondition(BalanceCondition queryCondition) {
        return BusinessResponseEntity.ok(this.offsetVoucherItemService.queryByOffsetCondition(queryCondition));
    }

    @Override
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherItemEO>> queryByIds(Set<String> ids) {
        return BusinessResponseEntity.ok(this.offsetVoucherItemService.queryByIds(ids));
    }
}

