/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 */
package com.jiuqi.bde.penetrate.impl.core.model;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.model.AbstractBdePenetrateModel;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BdeBalancePenetrateModel
extends AbstractBdePenetrateModel<PenetrateBaseDTO, List<PenetrateBalance>> {
    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    public PenetrateBaseDTO getCondi(Map<String, Object> param) {
        return this.cloneObj(param, PenetrateBaseDTO.class);
    }
}

