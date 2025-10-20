/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrCopyDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO
 *  com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrDeleteVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.base.common.utils.BeanValidUtils
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.adjustvchr.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrCopyDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO;
import com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrDeleteVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.service.AdjustVoucherService;
import com.jiuqi.dc.base.common.utils.BeanValidUtils;
import java.util.Collection;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdjustVchrController {
    public static final String ADJUST_API_BASE_PATH = "/api/datacenter/v1/dm/adjustVchr";
    @Autowired
    private AdjustVoucherService service;
    @Autowired
    private AdjustVoucherClientService clientService;

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/maxVchrNum"})
    public BusinessResponseEntity<String> getMaxVchrNum(@RequestBody AdjustVoucherVO param) {
        try {
            Assert.isNotEmpty((String)param.getUnitCode(), (String)"\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            Assert.isNotNull((Object)param.getAcctYear(), (String)"\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            return BusinessResponseEntity.ok((Object)this.clientService.getMaxVchrNum(param.getUnitCode(), param.getAcctYear()));
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((String)"\u83b7\u53d6\u8c03\u6574\u51ed\u8bc1\u7f16\u53f7\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
    }

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/delete/batch"})
    public BusinessResponseEntity<Boolean> batchDelete(@RequestBody AdjustVchrDeleteVO adjustVchrDeleteVO) {
        return BusinessResponseEntity.ok((Object)this.service.batchDelete(adjustVchrDeleteVO));
    }

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/save"})
    public BusinessResponseEntity<Boolean> save(@RequestBody AdjustVoucherSaveDTO param) {
        StringBuffer errorMsg = new StringBuffer();
        for (AdjustVoucherVO voucher : param.getVouchers()) {
            HashMap errorMap = new HashMap();
            errorMap.putAll(BeanValidUtils.validate((Object)voucher, (Class[])new Class[0]));
            errorMap.putAll(BeanValidUtils.validateList((Collection)voucher.getItems()));
            if (errorMap.isEmpty()) continue;
            errorMsg.append("\u51ed\u8bc1\u3010").append(voucher.getVchrNum()).append("\u3011\u5b58\u5728\u4ee5\u4e0b\u95ee\u9898\uff1a<br/>");
            for (String errorField : errorMap.keySet()) {
                errorMsg.append((String)errorMap.get(errorField)).append("<br/>");
            }
        }
        if (!StringUtils.isEmpty((String)errorMsg.toString())) {
            return BusinessResponseEntity.error((String)errorMsg.toString());
        }
        try {
            String errorInfo = this.service.save(param);
            if (StringUtils.isEmpty((String)errorInfo)) {
                return BusinessResponseEntity.ok((Object)true);
            }
            return BusinessResponseEntity.error((String)errorInfo);
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/copy"})
    public BusinessResponseEntity<Boolean> copy(@RequestBody AdjustVchrCopyDTO copyData) {
        StringBuffer errorMsg = new StringBuffer();
        HashMap errorMap = new HashMap();
        errorMap.putAll(BeanValidUtils.validate((Object)copyData, (Class[])new Class[0]));
        if (!errorMap.isEmpty()) {
            for (String errorField : errorMap.keySet()) {
                errorMsg.append((String)errorMap.get(errorField)).append("<br/>");
            }
        }
        if (!StringUtils.isEmpty((String)errorMsg.toString())) {
            return BusinessResponseEntity.error((String)errorMsg.toString());
        }
        try {
            if (copyData.getSrcVouchers() == null || copyData.getSrcVouchers().isEmpty()) {
                return BusinessResponseEntity.error((String)"\u8bf7\u9009\u62e9\u9700\u8981\u590d\u5236\u7684\u6570\u636e");
            }
            this.service.copy(copyData);
            return BusinessResponseEntity.ok((Object)true);
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((Throwable)e);
        }
    }
}

