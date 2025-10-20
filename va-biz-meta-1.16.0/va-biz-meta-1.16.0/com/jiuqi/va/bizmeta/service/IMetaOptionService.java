/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metaoption.MetaOptionDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.List;

public interface IMetaOptionService {
    public List<OptionItemVO> list(OptionItemDTO var1);

    public R update(MetaOptionDO var1);
}

