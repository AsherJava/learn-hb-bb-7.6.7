/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.bde.penetrate.impl.core.model;

import com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateVoucher;
import com.jiuqi.bde.penetrate.impl.core.model.AbstractBdePenetrateModel;
import com.jiuqi.common.base.util.JsonUtils;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BdeVoucherPenetrateModel
extends AbstractBdePenetrateModel<VoucherPenetrateDTO, PenetrateVoucher> {
    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.VOUCHER;
    }

    @Override
    public VoucherPenetrateDTO getCondi(Map<String, Object> param) {
        return (VoucherPenetrateDTO)JsonUtils.readValue((String)JsonUtils.writeValueAsString(param), VoucherPenetrateDTO.class);
    }
}

