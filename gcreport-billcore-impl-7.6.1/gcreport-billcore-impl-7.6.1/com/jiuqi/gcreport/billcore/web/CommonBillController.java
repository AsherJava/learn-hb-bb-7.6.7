/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.api.CommonBillClient
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.billcore.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.api.CommonBillClient;
import com.jiuqi.gcreport.billcore.service.CommonBillService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonBillController
implements CommonBillClient {
    @Autowired
    private CommonBillService commonBillService;

    public BusinessResponseEntity<List<Map<String, Object>>> listColumns(String tableName, String defineCode) {
        if (StringUtils.isEmpty((String)tableName)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u5217\u9009\u5b57\u6bb5\uff1a\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)defineCode)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u5217\u9009\u5b57\u6bb5\uff1a\u5355\u636e\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return BusinessResponseEntity.ok(this.commonBillService.listColumns(tableName, defineCode));
    }

    public BusinessResponseEntity<String> getOrgType(String defineCode) {
        return BusinessResponseEntity.ok((Object)this.commonBillService.getOrgType(defineCode));
    }

    public BusinessResponseEntity<String> getOrgTypeByTableName(String tableName) {
        return BusinessResponseEntity.ok((Object)this.commonBillService.getOrgTypeByTableName(tableName));
    }

    public List<Map<String, String>> listAcctYearRange() {
        Calendar date = Calendar.getInstance();
        int year = date.get(1);
        int fromAcctYear = year - 5;
        int toAcctYear = year + 5;
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (int i = fromAcctYear; i <= toAcctYear; ++i) {
            String tempYearStr = Integer.toString(i);
            HashMap<String, String> yearMap = new HashMap<String, String>();
            yearMap.put("key", tempYearStr);
            yearMap.put("title", tempYearStr);
            result.add(yearMap);
        }
        return result;
    }
}

