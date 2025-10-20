/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.extend.controller;

import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.extend.intf.BillDetailCopyBizExtendIntf;
import com.jiuqi.va.extend.service.VaBillExtendService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/extend"})
public class VaBillExtendController {
    @Autowired
    private VaBillExtendService vaBillExtendService;
    @Autowired(required=false)
    private List<BillDetailCopyBizExtendIntf> billDetailCopyBizExtendIntfList;

    @PostMapping(value={"/filterFields"})
    public R getBillFields(@RequestBody BillDefine billDefine) {
        return R.ok().put("data", this.vaBillExtendService.filterFields(billDefine));
    }

    @GetMapping(value={"/copyProgress/{key}"})
    public R getBillFields(@PathVariable String key) {
        return R.ok().put("data", (Object)this.vaBillExtendService.getCopyProgress(key));
    }

    @PostMapping(value={"/detail/copy/biz"})
    public R copyBillDetail() {
        if (CollectionUtils.isEmpty(this.billDetailCopyBizExtendIntfList)) {
            return R.ok();
        }
        ArrayList result = new ArrayList();
        for (BillDetailCopyBizExtendIntf billDetailCopyBizExtendIntf : this.billDetailCopyBizExtendIntfList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", billDetailCopyBizExtendIntf.getName());
            map.put("title", billDetailCopyBizExtendIntf.getTitle());
            result.add(map);
        }
        return R.ok().put("data", result);
    }
}

