/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.debug.BillFormulaDebugWhiteListDO;
import com.jiuqi.va.bill.service.BillFormulaDebugWhiteListService;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/formula"})
public class BillFormulaDebugWhiteListController {
    @Autowired
    private BillFormulaDebugWhiteListService billFormulaDebugWhiteListService;

    @PostMapping(value={"/white/save"})
    R whiteSave(@RequestBody BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO) {
        if (!StringUtils.hasText(billFormulaDebugWhiteListDO.getFormulaname())) {
            return R.error((String)"\u516c\u5f0f\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        int flag = this.billFormulaDebugWhiteListService.saveWhiteList(billFormulaDebugWhiteListDO);
        return flag == 0 ? R.error((String)"\u4fdd\u5b58\u5931\u8d25") : R.ok();
    }

    @PostMapping(value={"/white/delete"})
    R whiteDelete(@RequestBody BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO) {
        if (billFormulaDebugWhiteListDO.getId() == null && billFormulaDebugWhiteListDO.getFormulaname() == null) {
            return R.error((String)"\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        int flag = this.billFormulaDebugWhiteListService.deleteWhiteList(billFormulaDebugWhiteListDO);
        return flag == 0 ? R.error((String)"\u5220\u9664\u5931\u8d25") : R.ok();
    }

    @GetMapping(value={"/white/list"})
    R whiteList() {
        List<BillFormulaDebugWhiteListDO> billFormulaDebugWhiteListDOS = this.billFormulaDebugWhiteListService.whiteList();
        return R.ok().put("data", billFormulaDebugWhiteListDOS);
    }
}

