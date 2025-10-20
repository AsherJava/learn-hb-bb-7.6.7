/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.service.impl.BizService
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.biz.service.impl.BizService;
import com.jiuqi.va.bizmeta.common.utils.VerifyUtils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz"})
public class MetaController {
    @Autowired
    private BizService bizService;
    @Value(value="${com.jiuqi.va.bill.permissions.check:true}")
    private boolean checkPermission;

    @RequiresPermissions(value={"inner"})
    @PostMapping(value={"/bill/encode"})
    public R encodeBill(@RequestHeader HttpHeaders headers, @RequestBody BillVerifyDTO dto) {
        if (this.checkPermission && !VerifyUtils.isFeignClient(headers)) {
            return R.error((String)"\u975e\u6cd5\u8c03\u7528");
        }
        R r = R.ok();
        if (dto.getUserIds() != null && dto.getUserIds().size() > 0) {
            r.put("data", VerifyUtils.genVerifyCodeForUsers(dto));
        } else if (dto.getBillCodes() != null && dto.getBillCodes().size() > 0) {
            r.put("data", VerifyUtils.genVerifyCode(dto));
        } else if (!StringUtils.isEmpty(dto.getBillCode())) {
            r.put("data", (Object)VerifyUtils.genVerifyCode(dto.getBillCode(), dto.getAuth()));
        }
        return r;
    }

    @RequiresPermissions(value={"inner"})
    @PostMapping(value={"/bill/Verify"})
    public R VerifyBill1(@RequestHeader HttpHeaders headers, @RequestBody BillVerifyDTO dto) {
        if (this.checkPermission && !VerifyUtils.isFeignClient(headers)) {
            return R.error((String)"\u975e\u6cd5\u8c03\u7528");
        }
        int result = VerifyUtils.doVerify(dto.getBillCode(), dto.getAuth(), dto.getVerifyCode());
        R r = R.ok();
        r.put("VerifyResult", (Object)result);
        return r;
    }

    @PostMapping(value={"/reftable/get"})
    public DataModelDO getRefTable(@RequestBody DataModelDTO param) {
        return this.bizService.getRefTable(param.getName());
    }
}

