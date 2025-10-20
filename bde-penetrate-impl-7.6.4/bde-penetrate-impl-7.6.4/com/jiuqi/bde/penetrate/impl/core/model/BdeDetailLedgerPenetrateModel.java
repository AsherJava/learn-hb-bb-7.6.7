/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.bde.penetrate.impl.core.model;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.model.AbstractBdePenetrateModel;
import com.jiuqi.common.base.util.JsonUtils;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BdeDetailLedgerPenetrateModel
extends AbstractBdePenetrateModel<PenetrateBaseDTO, PenetrateDetailLedger> {
    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.DETAILLEDGER;
    }

    @Override
    public PenetrateBaseDTO getCondi(Map<String, Object> param) {
        return (PenetrateBaseDTO)JsonUtils.readValue((String)JsonUtils.writeValueAsString(param), PenetrateBaseDTO.class);
    }
}

