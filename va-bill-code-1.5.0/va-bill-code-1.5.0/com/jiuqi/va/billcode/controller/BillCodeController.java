/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.dao.DataIntegrityViolationException
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.billcode.controller;

import com.jiuqi.va.billcode.common.BillCodeUtils;
import com.jiuqi.va.billcode.service.IBillCodeFlowService;
import com.jiuqi.va.billcode.service.IBillCodeRuleService;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/billcode"})
public class BillCodeController {
    @Autowired
    private IBillCodeRuleService billCodeRuleService;
    @Autowired
    private IBillCodeFlowService billCodeFlowService;

    @PostMapping(value={"/create"})
    public R createBillCode(@RequestBody BillCodeDTO billCodeDTO) {
        R r = null;
        if (!StringUtils.hasText(billCodeDTO.getUnitCode())) {
            r = R.ok();
            r.put("billcode", (Object)"");
            return r;
        }
        try {
            String billCode = "";
            BillCodeRuleDTO codeRuleDTO = this.billCodeRuleService.getRuleByUniqueCodeUnCheck(billCodeDTO.getDefineCode(), false);
            if (codeRuleDTO == null) {
                if (!((Boolean)billCodeDTO.getExtInfo("createDefaultFlow")).booleanValue()) {
                    return R.error((String)"\u672a\u914d\u7f6e\u5355\u636e\u7f16\u53f7\u89c4\u5219");
                }
                billCode = BillCodeUtils.createDefaultBillCode(billCodeDTO, this.billCodeFlowService);
            } else if (codeRuleDTO.getGenerateOpt() == billCodeDTO.getGenerateOpt()) {
                billCode = BillCodeUtils.createBillCode(codeRuleDTO, billCodeDTO, this.billCodeFlowService);
            }
            r = R.ok();
            r.put("billcode", (Object)billCode);
        }
        catch (Exception e) {
            String msg = "\u521b\u5efa\u5355\u636e\u7f16\u53f7\u5931\u8d25\uff1a";
            msg = e instanceof DataIntegrityViolationException ? msg + "\u5355\u636e\u7f16\u53f7\u6d41\u6c34\u7ef4\u5ea6\u503c\u8d85\u51fa\u6570\u636e\u5e93\u5b57\u6bb5\u957f\u5ea6" : e.getMessage();
            throw new RuntimeException(msg, e);
        }
        return r;
    }
}

