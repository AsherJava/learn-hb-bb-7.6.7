/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import java.util.List;

public interface EnumDataService {
    public EnumDataDO get(EnumDataDTO var1);

    public List<EnumDataDO> list(EnumDataDTO var1);

    public int count(EnumDataDTO var1);

    public R add(EnumDataDO var1);

    public R update(EnumDataDO var1);

    public int remove(List<EnumDataDTO> var1);

    public List<EnumDataDO> listBiztype(EnumDataDTO var1);
}

