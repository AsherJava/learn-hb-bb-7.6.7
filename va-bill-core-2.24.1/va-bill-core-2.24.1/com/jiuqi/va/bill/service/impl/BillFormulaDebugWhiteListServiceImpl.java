/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DuplicateKeyException
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.dao.BillFormulaDebugWhiteListDao;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugWhiteListDO;
import com.jiuqi.va.bill.service.BillFormulaDebugWhiteListService;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BillFormulaDebugWhiteListServiceImpl
implements BillFormulaDebugWhiteListService {
    private static final Logger logger = LoggerFactory.getLogger(BillFormulaDebugWhiteListServiceImpl.class);
    @Autowired
    private BillFormulaDebugWhiteListDao billFormulaDebugWhiteListDao;

    @Override
    public int saveWhiteList(BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO) {
        int count;
        BillFormulaDebugWhiteListDO save = new BillFormulaDebugWhiteListDO();
        save.setFormulaname(billFormulaDebugWhiteListDO.getFormulaname());
        save.setId(UUID.randomUUID().toString());
        save.setVer(System.currentTimeMillis());
        try {
            count = this.billFormulaDebugWhiteListDao.insert((Object)save);
        }
        catch (DuplicateKeyException e) {
            logger.warn("\u91cd\u590d\u63d2\u5165" + e.getMessage());
            return 1;
        }
        return count;
    }

    @Override
    public int deleteWhiteList(BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO) {
        BillFormulaDebugWhiteListDO delete = new BillFormulaDebugWhiteListDO();
        if (billFormulaDebugWhiteListDO.getId() != null) {
            delete.setId(billFormulaDebugWhiteListDO.getId());
        } else {
            delete.setFormulaname(billFormulaDebugWhiteListDO.getFormulaname());
        }
        return this.billFormulaDebugWhiteListDao.delete((Object)delete);
    }

    @Override
    public List<BillFormulaDebugWhiteListDO> whiteList() {
        return this.billFormulaDebugWhiteListDao.select((Object)new BillFormulaDebugWhiteListDO());
    }

    @Override
    public boolean checkExist(BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO) {
        String formulaname = billFormulaDebugWhiteListDO.getFormulaname();
        if (StringUtils.hasText(formulaname)) {
            BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO1 = new BillFormulaDebugWhiteListDO();
            billFormulaDebugWhiteListDO1.setFormulaname(formulaname);
            List billFormulaDebugWhiteListDOList = this.billFormulaDebugWhiteListDao.select((Object)billFormulaDebugWhiteListDO1);
            return !CollectionUtils.isEmpty(billFormulaDebugWhiteListDOList);
        }
        return false;
    }
}

