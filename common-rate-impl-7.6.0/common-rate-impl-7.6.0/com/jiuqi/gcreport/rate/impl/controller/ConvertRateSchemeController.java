/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.rate.client.ConvertRateSchemeClient
 *  com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO
 *  com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.rate.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.rate.client.ConvertRateSchemeClient;
import com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO;
import com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO;
import com.jiuqi.gcreport.rate.impl.exception.RateSchemeSaveException;
import com.jiuqi.gcreport.rate.impl.service.ConvertRateSchemeService;
import com.jiuqi.gcreport.rate.impl.utils.LogUtil;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvertRateSchemeController
implements ConvertRateSchemeClient {
    @Autowired
    private ConvertRateSchemeService schemeService;

    public BusinessResponseEntity<PageVO<ConvertRateSchemeVO>> query(@RequestBody ConvertRateSchemeDTO param) {
        try {
            return BusinessResponseEntity.ok(this.schemeService.query(param));
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((String)"500", (String)"\u67e5\u8be2\u6298\u7b97\u6c47\u7387\u53d6\u503c\u65b9\u6848\u51fa\u73b0\u5f02\u5e38", (String)LogUtil.getExceptionStackStr(e));
        }
    }

    public BusinessResponseEntity<PageVO<ConvertRateSchemeVO>> save(@RequestBody ConvertRateSchemeDTO param) {
        try {
            return BusinessResponseEntity.ok(this.schemeService.save(param));
        }
        catch (RateSchemeSaveException e) {
            return BusinessResponseEntity.error((String)"500", (String)"\u4fdd\u5b58\u6298\u7b97\u6c47\u7387\u53d6\u503c\u65b9\u6848\u51fa\u73b0\u5f02\u5e38", (String)e.getMessage());
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((String)"500", (String)"\u4fdd\u5b58\u6298\u7b97\u6c47\u7387\u53d6\u503c\u65b9\u6848\u51fa\u73b0\u5f02\u5e38", (String)LogUtil.getExceptionStackStr(e));
        }
    }

    public BusinessResponseEntity<Map<String, ConvertRateSchemeVO>> getBySubjCodes(@RequestBody List<String> subjectCodes) {
        try {
            return BusinessResponseEntity.ok(this.schemeService.getRateSchemeBySubjectCodes(subjectCodes));
        }
        catch (RateSchemeSaveException e) {
            return BusinessResponseEntity.error((String)"500", (String)"\u83b7\u53d6\u6298\u7b97\u6c47\u7387\u53d6\u503c\u65b9\u6848\u51fa\u73b0\u5f02\u5e38", (String)e.getMessage());
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((String)"500", (String)"\u83b7\u53d6\u6298\u7b97\u6c47\u7387\u53d6\u503c\u65b9\u6848\u51fa\u73b0\u5f02\u5e38", (String)LogUtil.getExceptionStackStr(e));
        }
    }
}

