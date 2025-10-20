/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.dao.BillLockDao;
import com.jiuqi.va.bill.domain.BillLockDO;
import com.jiuqi.va.bill.service.BillLockService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BillLockServiceImpl
implements BillLockService {
    @Autowired
    private BillLockDao billLockDao;

    @Override
    public void delete(BillLockDO billLockDO) {
        if (!StringUtils.hasText(billLockDO.getBillcode())) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.billlockservice.lackparam"));
        }
        this.billLockDao.delete((Object)billLockDO);
    }

    @Override
    public BillLockDO select(BillLockDO billLockDO) {
        if (!StringUtils.hasText(billLockDO.getBillcode())) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.billlockservice.lackparam"));
        }
        return (BillLockDO)((Object)this.billLockDao.selectOne((Object)billLockDO));
    }

    @Override
    public void add(BillLockDO billLockDO) {
        if (!StringUtils.hasText(billLockDO.getBillcode())) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.billlockservice.lackparam"));
        }
        this.billLockDao.insert((Object)billLockDO);
    }
}

