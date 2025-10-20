/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.domain.BillActionAuthDTO;
import com.jiuqi.va.bill.domain.BillActionAuthFindDTO;
import com.jiuqi.va.bill.domain.BillActionAuthVO;
import com.jiuqi.va.bill.domain.option.BillActionAuthUpdateDO;
import com.jiuqi.va.bill.service.BillActionAuthService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/action/auth"})
public class BillActionAuthController {
    @Autowired
    private BillActionAuthService billActionAuthService;

    @PostMapping(value={"/detail/list"})
    @RequiresPermissions(value={"vaAuth:auth:mgr"})
    public PageVO<BillActionAuthVO> listDetail(@RequestBody BillActionAuthDTO param) {
        List<BillActionAuthVO> actionAuthVOS = this.billActionAuthService.listDetail(param);
        PageVO page = new PageVO();
        page.setRs(R.ok());
        page.setRows(actionAuthVOS);
        page.setTotal(actionAuthVOS.size());
        return page;
    }

    @PostMapping(value={"/detail/update"})
    @RequiresPermissions(value={"vaAuth:auth:mgr"})
    public R updateDetail(@RequestBody BillActionAuthUpdateDO param) {
        if (param.getData() == null || param.getData().size() == 0) {
            return R.error((String)"\u4f20\u5165\u6570\u636e\u4e3a\u7a7a");
        }
        return this.billActionAuthService.updateDetail(param);
    }

    @PostMapping(value={"/check"})
    public Set<String> checkAuth(@RequestBody BillActionAuthFindDTO param) {
        return this.billActionAuthService.getUserAuth(param);
    }
}

