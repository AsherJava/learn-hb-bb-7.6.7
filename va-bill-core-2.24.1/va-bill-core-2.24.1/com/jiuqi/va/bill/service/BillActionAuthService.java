/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.BillActionAuthDTO;
import com.jiuqi.va.bill.domain.BillActionAuthFindDTO;
import com.jiuqi.va.bill.domain.BillActionAuthVO;
import com.jiuqi.va.bill.domain.option.BillActionAuthUpdateDO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.Set;

public interface BillActionAuthService {
    public List<BillActionAuthVO> listDetail(BillActionAuthDTO var1);

    public R updateDetail(BillActionAuthUpdateDO var1);

    public Set<String> getUserAuth(BillActionAuthFindDTO var1);
}

