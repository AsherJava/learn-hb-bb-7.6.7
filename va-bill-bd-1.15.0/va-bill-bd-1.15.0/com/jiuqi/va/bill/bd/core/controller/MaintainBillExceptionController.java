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

import com.jiuqi.va.bill.bd.bill.domain.CreateBillExceptionDTO;
import com.jiuqi.va.bill.bd.bill.service.impl.WriteBackServiceImpl;
import com.jiuqi.va.bill.bd.core.service.MaintainBillExceptionService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/bill/applyReg/exception"})
public class MaintainBillExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(WriteBackServiceImpl.class);
    @Autowired
    public MaintainBillExceptionService exHanderService;

    @RequestMapping(value={"/list"})
    @ResponseBody
    public PageVO<CreateBillExceptionDTO> queryExecptionBill(@RequestBody CreateBillExceptionDTO requrstDTO) {
        try {
            List<CreateBillExceptionDTO> responseDTOs = this.exHanderService.queryExceptionData(requrstDTO);
            return new PageVO(responseDTOs, responseDTOs.size(), R.ok());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PageVO(null, 0, R.error((String)e.getMessage()));
        }
    }

    @RequestMapping(value={"/republish"})
    @ResponseBody
    public R resetExecptionBill(@RequestBody CreateBillExceptionDTO requrstDTO) {
        return this.exHanderService.republish(this.exHanderService.queryExceptionData(requrstDTO));
    }
}

