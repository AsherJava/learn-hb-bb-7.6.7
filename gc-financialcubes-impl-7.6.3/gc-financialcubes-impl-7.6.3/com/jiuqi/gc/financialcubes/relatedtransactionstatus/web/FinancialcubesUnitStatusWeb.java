/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialCubesPeriodTypeVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.web;

import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialCubesPeriodTypeVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinancialcubesUnitStatusWeb {
    private final String FINANCIAL_STATUS_API_BASE_PATH = "/api/gcreport/v1/financialStatus";
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @GetMapping(value={"/api/gcreport/v1/financialStatus/periodTypes"})
    public List<FinancialCubesPeriodTypeVO> listPeriodTypes() {
        return Arrays.stream(FinancialCubesPeriodTypeEnum.values()).map(item -> new FinancialCubesPeriodTypeVO(item.getCode(), item.getName())).collect(Collectors.toList());
    }

    @GetMapping(value={"/api/gcreport/v1/financialStatus/orgTypes"})
    public List<OrgCategoryDO> listOrgTypes() {
        return this.orgCategoryClient.list(new OrgCategoryDO()).getRows();
    }
}

