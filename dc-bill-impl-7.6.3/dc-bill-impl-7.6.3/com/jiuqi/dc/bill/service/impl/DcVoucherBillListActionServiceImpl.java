/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.dc.bill.service.impl;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.bill.service.DcVoucherBillListActionService;
import com.jiuqi.dc.bill.util.DcBillParseTool;
import com.jiuqi.dc.bill.vo.BillInfoVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DcVoucherBillListActionServiceImpl
implements DcVoucherBillListActionService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String unlock(String billDefineCode, List<String> idList) {
        BillInfoVo billInfoVo = DcBillParseTool.parseBillInfo(billDefineCode);
        String unlockSql = " UPDATE %s SET LOCKSTATE = 0 WHERE " + SqlBuildUtil.getStrInCondi((String)"id", idList);
        this.jdbcTemplate.update(String.format(unlockSql, billInfoVo.getMasterTableName()));
        return "\u6267\u884c\u6210\u529f";
    }
}

