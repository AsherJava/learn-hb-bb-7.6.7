/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.adjustvchr.client.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrQueryResultDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrBaseDataVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdjustVchrClientController {
    public static final String ADJUST_API_BASE_PATH = "/api/datacenter/v1/dm/adjustVchr";
    @Autowired
    private AdjustVoucherClientService service;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/list"})
    public BusinessResponseEntity<AdjustVchrQueryResultDTO> list(@RequestBody AdjustVoucherQueryDTO param) {
        try {
            Assert.isNotNull((Object)param.getPage(), (String)"\u9875\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            Assert.isNotNull((Object)param.getPageSize(), (String)"\u6bcf\u9875\u663e\u793a\u6570\u91cf\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            return BusinessResponseEntity.ok((Object)this.service.list(param));
        }
        catch (Exception e) {
            BusinessResponseEntity res = BusinessResponseEntity.error((Throwable)e).errorMessage("\u83b7\u53d6\u8c03\u6574\u51ed\u8bc1\u5217\u8868\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            return res;
        }
    }

    @GetMapping(value={"/api/datacenter/v1/dm/adjustVchr/dimList"})
    public BusinessResponseEntity<List<DimensionVO>> listAdjustDim() {
        return BusinessResponseEntity.ok(this.service.listAdjustDim());
    }

    @GetMapping(value={"/api/datacenter/v1/dm/adjustVchr/getAccountAndReclassifly"})
    public BusinessResponseEntity<Map<String, List<String>>> getAccountAndReclassifly() {
        return BusinessResponseEntity.ok(this.service.getAccountAndReclassifly());
    }

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/sysOption"})
    public BusinessResponseEntity<AdjustVchrSysOptionVO> getAdjustVchrSysOption() {
        return BusinessResponseEntity.ok((Object)this.service.getAdjustVchrSysOptions());
    }

    @PostMapping(value={"/api/datacenter/v1/dm/adjustVchr/repcurrency"})
    public BusinessResponseEntity<List<AdjustVchrBaseDataVO>> getRepCurrCodeByUnit(@RequestBody AdjustVoucherVO param) {
        return BusinessResponseEntity.ok(this.service.getRepCurrCodeByUnit(param.getUnitCode()));
    }

    @GetMapping(value={"/api/datacenter/v1/dm/adjustVchr/org/category/list"})
    public List<OrgCategoryDO> getOrgVersionList() {
        return this.orgCategoryClient.list(new OrgCategoryDO()).getRows();
    }
}

