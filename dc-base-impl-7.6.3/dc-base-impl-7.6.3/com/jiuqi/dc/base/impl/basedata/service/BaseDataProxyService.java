/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO
 *  com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.dc.base.impl.basedata.service;

import com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO;
import com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;

public interface BaseDataProxyService {
    public BaseDataDefineProxyVO getDefine(BaseDataDefineDTO var1);

    public PageVO<BaseDataDO> list(BaseDataProxyDTO var1);
}

