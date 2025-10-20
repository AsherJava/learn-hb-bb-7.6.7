/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.GcFinancialCheckDataEntryClient
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.impl.GcRelatedItemCommandServiceImpl
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.GcFinancialCheckDataEntryService;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.FinancialCheckDataCollectionTaskHandlerB;
import com.jiuqi.gcreport.financialcheckapi.dataentry.GcFinancialCheckDataEntryClient;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.gcreport.financialcheckcore.item.service.impl.GcRelatedItemCommandServiceImpl;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcFinancialCheckDataEntryController
implements GcFinancialCheckDataEntryClient {
    @Autowired
    private GcRelatedItemCommandServiceImpl relatedItemCommandService;
    @Autowired
    private GcFinancialCheckDataEntryService gcFinancialCheckDataEntryService;
    @Autowired
    FinancialCheckDataCollectionTaskHandlerB handlerB;
    @Autowired
    GcRelatedItemQueryService relatedItemQueryService;

    public BusinessResponseEntity<DataInputVO> query(DataInputConditionVO condition) {
        DataInputVO vo = this.gcFinancialCheckDataEntryService.query(condition);
        return BusinessResponseEntity.ok((Object)vo);
    }

    public BusinessResponseEntity<String> deleteRelatedItem(List<String> ids) {
        List needDeleteItems = this.relatedItemQueryService.queryByIds(ids);
        if (CollectionUtils.isEmpty((Collection)needDeleteItems)) {
            throw new BusinessRuntimeException("\u8981\u5220\u9664\u7684\u6570\u636e\u4e0d\u5b58\u5728\u6570\u636e\u4e0d\u5b58\u5728");
        }
        if (needDeleteItems.stream().anyMatch(item -> CheckStateEnum.CHECKED.name().equals(item.getChkState()))) {
            throw new BusinessRuntimeException("\u5b58\u5728\u5df2\u5bf9\u8d26\u7684\u6570\u636e,\u8bf7\u786e\u8ba4\u72b6\u6001\u540e\u91cd\u8bd5");
        }
        this.relatedItemCommandService.batchDelete(ids);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<String> saveRelatedItems(List<GcRelatedItemVO> voucherItems) {
        this.gcFinancialCheckDataEntryService.saveRelatedItems(voucherItems);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<List<GcRelatedItemVO>> listByIds(List<String> ids) {
        return BusinessResponseEntity.ok(this.gcFinancialCheckDataEntryService.listByIds(ids));
    }
}

