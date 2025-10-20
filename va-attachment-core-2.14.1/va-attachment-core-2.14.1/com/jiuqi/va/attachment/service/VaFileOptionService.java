/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.entity.FileOptionDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.List;

public interface VaFileOptionService {
    public List<OptionItemVO> list(OptionItemDTO var1);

    public R update(FileOptionDO var1);
}

