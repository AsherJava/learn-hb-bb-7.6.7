/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.bill.BillDataDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.domain.bill.BillDataDTO;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/batch"})
public class BillBatchController {
    @Autowired
    private BillDataService billDataService;

    @PostMapping(value={"/bills/delete"})
    public R<?> batchDelete(@RequestBody Map<String, List<String>> billCodeMap) {
        BillContextImpl billContext = new BillContextImpl();
        HashMap<String, R> result = new HashMap<String, R>(16);
        for (Map.Entry<String, List<String>> entry : billCodeMap.entrySet()) {
            String billDefine = entry.getKey();
            List<String> billCodeList = entry.getValue();
            for (String billCode : billCodeList) {
                try {
                    this.billDataService.delete(billContext, billDefine, billCode);
                    result.put(billCode, R.ok((Object)BillCoreI18nUtil.getMessage("va.billcore.billdataservice.deletebillsuccess", new Object[]{billCode})));
                    LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)billDefine, (String)billCode, null);
                }
                catch (Exception e) {
                    result.put(billCode, R.error((String)BillCoreI18nUtil.getMessage("va.billcore.billbatchcontroller.deletefailed", new Object[]{billCode, e.getMessage()}), (Throwable)e));
                    LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)billDefine, (String)billCode, (String)String.format("\u5220\u9664\u5355\u636e%s\u5931\u8d25:%s", billCode, e.getMessage()));
                }
            }
        }
        return R.ok(result);
    }

    @PostMapping(value={"/bills/delete/bybilllist"})
    public R batchDelete(@RequestBody List<BillDataDTO> billDataDTOList) {
        if (CollectionUtils.isEmpty(billDataDTOList)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.bill.core.please.check.param"));
        }
        BillContextImpl billContext = new BillContextImpl();
        HashMap<String, R> result = new HashMap<String, R>(16);
        for (BillDataDTO billDataDTO : billDataDTOList) {
            String billCode = billDataDTO.getBillCode();
            String defineCode = billDataDTO.getDefineCode();
            String verifyCode = billDataDTO.getVerifyCode();
            billContext.setVerifyCode(verifyCode);
            try {
                this.billDataService.delete(billContext, defineCode, billCode);
                result.put(billCode, R.ok((Object)BillCoreI18nUtil.getMessage("va.billcore.billdataservice.deletebillsuccess", new Object[]{billCode})));
                LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)defineCode, (String)billCode, null);
            }
            catch (Exception e) {
                String message = e.getMessage();
                if (Objects.nonNull(message) && message.endsWith(";")) {
                    message = message.substring(0, message.length() - 1);
                }
                result.put(billCode, R.error((String)BillCoreI18nUtil.getMessage("va.billcore.billbatchcontroller.deletefailed", new Object[]{billCode, message}), (Throwable)e));
                LogUtil.add((String)"\u5355\u636e", (String)"\u5220\u9664", (String)defineCode, (String)billCode, (String)String.format("\u5220\u9664\u5355\u636e%s\u5931\u8d25:%s", billCode, message));
            }
        }
        return R.ok(result);
    }
}

