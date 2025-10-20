/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.datamapping.impl.service;

import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import javax.validation.constraints.NotNull;

public interface DataRefListConfigureService {
    public DataRefListVO list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefListDTO var1);
}

