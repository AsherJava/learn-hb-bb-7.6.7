/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillVerifyClient
 */
package com.jiuqi.va.biz.exchange;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.utils.VerifyUtils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BillVerifyClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class BillVerifyClientImpl
implements BillVerifyClient {
    public R encodeBillCode(BillVerifyDTO dto) {
        R r = R.ok();
        if (dto.getUserIds() != null && dto.getUserIds().size() > 0) {
            r.put("data", VerifyUtils.genVerifyCodeForUsers(dto));
        } else if (dto.getBillCodes() != null && dto.getBillCodes().size() > 0) {
            r.put("data", VerifyUtils.genVerifyCode(dto));
        } else if (!Utils.isEmpty(dto.getBillCode())) {
            r.put("data", (Object)VerifyUtils.genVerifyCode(dto.getBillCode(), dto.getAuth()));
        }
        return r;
    }

    public R VerifyBill(BillVerifyDTO dto) {
        int result = VerifyUtils.doVerify(dto.getBillCode(), dto.getAuth(), dto.getVerifyCode());
        R r = R.ok();
        r.put("VerifyResult", (Object)result);
        return r;
    }
}

