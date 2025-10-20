/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.billcode.service.join;

import com.jiuqi.va.billcode.controller.BillCodeController;
import com.jiuqi.va.billcode.controller.BillCodeRuleController;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class VaBillCodeClientImpl
implements BillCodeClient {
    @Autowired
    private BillCodeController billCodeFlowService;
    @Autowired
    private BillCodeRuleController billCodeRuleService;

    public R createBillCode(BillCodeDTO billCodeDTO) {
        return this.billCodeFlowService.createBillCode(billCodeDTO);
    }

    public R getUniqueCodeByBillCode(TenantDO param) {
        return this.billCodeRuleService.getUniqueCodeByBillCode(param);
    }

    public R getDimFormulaByUniqueCode(BillCodeRuleDTO param) {
        return this.billCodeRuleService.getDimFormulaByUniqueCode(param);
    }

    public List<BillCodeRuleVO> getBillCodeRuleList(BillCodeRuleDTO param) {
        return this.billCodeRuleService.getBillCodeRuleList(param);
    }

    public R addBillCodeRule(BillCodeRuleDTO param) {
        return this.billCodeRuleService.addBillCodeRule(param);
    }

    public R updateBillCodeRule(BillCodeRuleDTO param) {
        return this.billCodeRuleService.updateBillCodeRule(param);
    }

    public PageVO<BillCodeRuleVO> getBillCodeRule(BillCodeRuleDTO param) {
        return this.billCodeRuleService.getBillCodeRule(param);
    }
}

