/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.bill.intf.BillDataService
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.bizmeta.service.IMetaInfoService
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.financialcheckImpl.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BillUtils {
    public static Map<String, String> getMetaInfoByClbrCode(String clbrCode) {
        TenantDO param = new TenantDO();
        HashMap<String, String> extInfo = new HashMap<String, String>();
        param.setExtInfo(extInfo);
        extInfo.put("billCode", clbrCode);
        BillDataService billDataService = (BillDataService)SpringContextUtils.getBean(BillDataService.class);
        BillDefine billDefine = billDataService.getBillDefineByCode(param);
        IMetaInfoService metaInfoService = (IMetaInfoService)SpringContextUtils.getBean(IMetaInfoService.class);
        MetaInfoDTO metaData = metaInfoService.getMetaInfoByUniqueCode(billDefine.getName());
        if (Objects.isNull(metaData)) {
            throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5e73\u53f0\u672a\u627e\u5230\u5355\u636e\u5b9a\u4e49\uff1a" + billDefine.getName());
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("code", metaData.getUniqueCode());
        map.put("title", metaData.getTitle());
        return map;
    }

    public static String getBillVerifyCode(String billCode) {
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setBillCode(billCode);
        billVerifyDTO.setAuth(15);
        ArrayList<String> userIds = new ArrayList<String>();
        userIds.add("@OPENAPI");
        billVerifyDTO.setUserIds(userIds);
        BillVerifyClient billVerifyClient = (BillVerifyClient)SpringContextUtils.getBean(BillVerifyClient.class);
        R result = billVerifyClient.encodeBillCode(billVerifyDTO);
        return result.get((Object)"data").toString().replaceFirst("@OPENAPI=", "").replace("{", "").replace("}", "");
    }
}

