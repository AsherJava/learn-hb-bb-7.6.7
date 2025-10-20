/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.dao.BillChangeRecDAO;
import com.jiuqi.va.bill.dao.BillChangeRecDataDAO;
import com.jiuqi.va.bill.domain.BillChangeRecDO;
import com.jiuqi.va.bill.domain.BillChangeRecDataDO;
import com.jiuqi.va.bill.service.BillChangeRecordService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="vaBillChangeRecordServiceImpl")
public class BillChangeRecordServiceImpl
implements BillChangeRecordService {
    @Autowired
    private BillChangeRecDAO billChangeRecDAO;
    @Autowired
    private BillChangeRecDataDAO billChangeRecDataDAO;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void insertRecord(String reson, String billcode, Map<String, Map<String, Object>> changeData) {
        UUID id = UUID.randomUUID();
        BillChangeRecDO billChangeRecDO = new BillChangeRecDO();
        billChangeRecDO.setId(id);
        billChangeRecDO.setBillcode(billcode);
        billChangeRecDO.setOptuser(ShiroUtil.getUser().getId());
        billChangeRecDO.setOpttype(0);
        billChangeRecDO.setOpttime(new Date(System.currentTimeMillis()));
        billChangeRecDO.setReason(reson);
        this.billChangeRecDAO.insert((Object)billChangeRecDO);
        BillChangeRecDataDO billChangeRecDataDO = new BillChangeRecDataDO();
        billChangeRecDataDO.setId(id);
        billChangeRecDataDO.setOptdata(JSONUtil.toJSONString(changeData));
        this.billChangeRecDataDAO.insert((Object)billChangeRecDataDO);
    }
}

