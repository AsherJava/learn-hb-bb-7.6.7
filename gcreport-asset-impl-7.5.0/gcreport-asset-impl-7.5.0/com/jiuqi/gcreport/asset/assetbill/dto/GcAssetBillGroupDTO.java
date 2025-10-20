/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.asset.assetbill.dto;

import com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.List;

public class GcAssetBillGroupDTO
extends GcBillGroupDTO {
    public GcAssetBillGroupDTO(DefaultTableEntity master, List<DefaultTableEntity> items) {
        super(master, items);
    }
}

