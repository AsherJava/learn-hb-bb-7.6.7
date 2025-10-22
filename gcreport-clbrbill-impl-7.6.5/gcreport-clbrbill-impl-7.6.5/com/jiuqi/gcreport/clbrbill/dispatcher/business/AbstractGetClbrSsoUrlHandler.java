/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.certification.bean.NvwaApp
 *  com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO
 *  com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage
 *  com.jiuqi.nvwa.certification.service.INvwaAppService
 *  com.jiuqi.va.bill.feign.util.domain.VaBillFeignUtil
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillVerifyClient
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrBillSsoUrlDTO;
import com.jiuqi.nvwa.certification.bean.NvwaApp;
import com.jiuqi.nvwa.certification.dto.NvwaSsoBuildDTO;
import com.jiuqi.nvwa.certification.manage.INvwaAppRequestManage;
import com.jiuqi.nvwa.certification.service.INvwaAppService;
import com.jiuqi.va.bill.feign.util.domain.VaBillFeignUtil;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BillVerifyClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGetClbrSsoUrlHandler
implements ClbrBusinessHandler<ClbrBillSsoUrlDTO, String> {
    @Autowired
    INvwaAppService nvwaAppService;
    @Autowired
    BillVerifyClient billVerifyClient;
    @Autowired
    INvwaAppRequestManage nvwaAppRequestManage;

    @Override
    public final String getBusinessCode() {
        return "GETCLBRSSOURL";
    }

    @Override
    public ClbrBillSsoUrlDTO beforeHandler(Object content) {
        Map map = (Map)content;
        String userName = ConverterUtils.getAsString(map.get("userName"));
        if (StringUtils.isEmpty((String)userName)) {
            throw new BusinessRuntimeException("\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String clbrCode = ConverterUtils.getAsString(map.get("clbrCode"));
        if (StringUtils.isEmpty((String)clbrCode)) {
            throw new BusinessRuntimeException("\u534f\u540c\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String clientId = ConverterUtils.getAsString(map.get("clientId"));
        if (StringUtils.isEmpty((String)clientId)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aefID\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String clientSecret = ConverterUtils.getAsString(map.get("clientSecret"));
        if (StringUtils.isEmpty((String)clientSecret)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aef\u5bc6\u94a5\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ClbrBillSsoUrlDTO dto = new ClbrBillSsoUrlDTO();
        dto.setClientId(clientId);
        dto.setClientSecret(clientSecret);
        dto.setClbrCode(clbrCode);
        dto.setUserName(userName);
        return dto;
    }

    @Override
    public final String handler(ClbrBillSsoUrlDTO clbrBillSsoUrlDTO) {
        NvwaApp nvwaApp = this.checkClientId(clbrBillSsoUrlDTO.getClientId(), clbrBillSsoUrlDTO.getClientSecret());
        String frontendURL = nvwaApp.getFrontendURL();
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCode(clbrBillSsoUrlDTO.getClbrCode());
        billVerifyDTO.setAuth(15);
        ArrayList<String> userIds = new ArrayList<String>();
        userIds.add("@OPENAPI");
        billVerifyDTO.setUserIds(userIds);
        R result = this.billVerifyClient.encodeBillCode(billVerifyDTO);
        String verify = result.get((Object)"data").toString().replaceFirst("@OPENAPI=", "").replace("{", "").replace("}", "");
        HashMap<String, Object> billInfo = new HashMap<String, Object>();
        billInfo.put("billCode", clbrBillSsoUrlDTO.getClbrCode());
        billInfo.put("dataState", "BROWSE");
        billInfo.put("defineCode", VaBillFeignUtil.getDefineByBillCode((String)clbrBillSsoUrlDTO.getClbrCode(), (String)"__default_tenant__"));
        billInfo.put("verifyCode", verify);
        billInfo.put("showToolBar", true);
        billInfo.put("toolBarShowStrategy", "byConfig");
        String appConfig = JSONUtil.toJSONString(billInfo);
        NvwaSsoBuildDTO nvwaSsoBuildDTO = new NvwaSsoBuildDTO();
        nvwaSsoBuildDTO.setScope("@va");
        nvwaSsoBuildDTO.setAppName("bill-app");
        nvwaSsoBuildDTO.setExpose("VaBill");
        nvwaSsoBuildDTO.setFrontAddress(frontendURL);
        nvwaSsoBuildDTO.setUserName(clbrBillSsoUrlDTO.getUserName());
        nvwaSsoBuildDTO.setAppConfig(appConfig);
        return this.nvwaAppRequestManage.buildCurSsoLocation(nvwaSsoBuildDTO);
    }

    @Override
    public String afterHandler(ClbrBillSsoUrlDTO content, String result) {
        return result;
    }

    private NvwaApp checkClientId(String clientId, String clientSecret) {
        NvwaApp nvwaApp = this.nvwaAppService.selectByClientid(clientId);
        if (Objects.isNull(nvwaApp)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aef\u8ba4\u8bc1\u5931\u8d25");
        }
        if (!nvwaApp.getClientsecret().equals(clientSecret)) {
            throw new BusinessRuntimeException("\u5ba2\u6237\u7aef\u8ba4\u8bc1\u5931\u8d25");
        }
        return nvwaApp;
    }
}

