/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.client.dao;

import com.jiuqi.dc.adjustvchr.client.domain.AdjustVchrIdListDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import java.util.List;

public interface AdjustVchrClientDao {
    public String getMaxVchrNum(String var1, int var2);

    public int countByCondi(AdjustVoucherQueryDTO var1);

    public List<AdjustVoucherVO> listByCondi(AdjustVoucherQueryDTO var1);

    public List<AdjustVchrItemEO> listByVchrId(AdjustVchrIdListDTO var1);

    public List<String> listAccountSubject();

    public List<String> listReclassfySubject();
}

