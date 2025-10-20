/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.va.bill.bd.core.controller;

import com.jiuqi.va.bill.bd.bill.service.impl.WriteBackServiceImpl;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordVO;
import com.jiuqi.va.bill.bd.core.service.BillChangeRecordService;
import com.jiuqi.va.bill.bd.utils.BillBdI18nUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/bill/generate/record"})
public class BillChangeRecordController {
    private static final Logger logger = LoggerFactory.getLogger(WriteBackServiceImpl.class);
    @Autowired
    BillChangeRecordService recordService;

    @RequestMapping(value={"/list"})
    @ResponseBody
    public PageVO<BillChangeRecordDO> getAlertList(@RequestBody BillChangeRecordDO requrstDTO) {
        int total = 0;
        List<Object> resultList = new ArrayList();
        try {
            resultList = this.recordService.list(requrstDTO);
            if (resultList != null) {
                total = resultList.size();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PageVO(resultList, total, R.error((String)"\u67e5\u8be2\u5931\u8d25"));
        }
        return new PageVO(resultList, total, R.ok());
    }

    @RequestMapping(value={"/listByBillCode"})
    @ResponseBody
    public R listByBillCode(@RequestBody BillChangeRecordDO recordDO) {
        if (!StringUtils.hasText(recordDO.getBillcode())) {
            return R.error((String)BillBdI18nUtil.getMessage("va.billbd.register.billcode.not.empty"));
        }
        List<BillChangeRecordVO> list = this.recordService.listByBillCode(recordDO);
        return R.ok().put("result", list);
    }
}

