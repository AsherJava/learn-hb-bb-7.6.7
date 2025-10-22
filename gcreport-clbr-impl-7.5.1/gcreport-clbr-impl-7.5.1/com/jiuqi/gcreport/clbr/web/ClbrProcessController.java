/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.api.ClbrProcessClient
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrProcessListTypeEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.api.ClbrProcessClient;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrProcessListTypeEnum;
import com.jiuqi.gcreport.clbr.service.ClbrProcessService;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClbrProcessController
implements ClbrProcessClient {
    @Autowired
    private ClbrProcessService clbrProcessService;

    public BusinessResponseEntity<PageInfo<ClbrNotConfirmBillVO>> queryInitiatorNotConfirm(ClbrProcessCondition clbrProcessCondition) {
        return BusinessResponseEntity.ok(this.clbrProcessService.queryNotConfirm(clbrProcessCondition, ClbrProcessListTypeEnum.INITIATE_PART));
    }

    public BusinessResponseEntity<PageInfo<ClbrNotConfirmBillVO>> queryReceiverNotConfirm(ClbrProcessCondition clbrProcessCondition) {
        return BusinessResponseEntity.ok(this.clbrProcessService.queryNotConfirm(clbrProcessCondition, ClbrProcessListTypeEnum.RECEIVE_PART));
    }

    public BusinessResponseEntity<PageInfo<ClbrBillCheckVO>> queryConfirm(ClbrProcessCondition clbrProcessCondition, String tabFlag) {
        return BusinessResponseEntity.ok(this.clbrProcessService.queryConfirm(clbrProcessCondition, tabFlag));
    }

    public BusinessResponseEntity<Object> cancalByAmountCheckIds(Set<String> clbrAmountCheckIds) {
        this.clbrProcessService.cancalByAmountCheckIds(clbrAmountCheckIds);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<ClbrBillVO> getConfirmByClbrBillCode(String clbrBillCode) {
        return BusinessResponseEntity.ok((Object)this.clbrProcessService.getConfirmByClbrBillCode(clbrBillCode));
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryInitiatorClbrBillByClbrCode(ClbrProcessCondition clbrProcessCondition) {
        return BusinessResponseEntity.ok(this.clbrProcessService.queryByClbrCode(clbrProcessCondition, ClbrBillTypeEnum.INITIATOR));
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryReceiverClbrBillByClbrCode(ClbrProcessCondition clbrProcessCondition) {
        return BusinessResponseEntity.ok(this.clbrProcessService.queryByClbrCode(clbrProcessCondition, ClbrBillTypeEnum.RECEIVER));
    }

    public BusinessResponseEntity<List<TransferColumnVo>> queryAllShowFields(String tabFlag) {
        return BusinessResponseEntity.ok(this.clbrProcessService.queryAllShowFields(tabFlag));
    }
}

