/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO
 */
package com.jiuqi.dc.adjustvchr.impl.dao;

import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO;
import java.util.List;

public interface AdjustVchrDao {
    public List<AdjustVoucherVO> listByGroupIdList(List<String> var1);

    public List<String> listGroupIdById(List<String> var1);

    public List<AdjustVoucherVO> listByGroupId(String var1);

    public void insertVoucher(AdjustVoucherEO var1);

    public void batchInsertItem(List<AdjustVchrItemEO> var1);

    public void updateVoucher(AdjustVoucherEO var1);

    public int delVoucherByVchrIdList(List<String> var1);

    public void delItemByVchrIdList(List<String> var1);
}

