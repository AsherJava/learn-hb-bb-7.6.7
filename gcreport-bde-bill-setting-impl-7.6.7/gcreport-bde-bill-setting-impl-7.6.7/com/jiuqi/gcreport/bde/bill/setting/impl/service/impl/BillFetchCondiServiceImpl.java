/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.enums.BillFetchCondiFixCodeEnums
 *  com.jiuqi.gcreport.bde.bill.setting.client.enums.BillFetchCondiType
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  com.jiuqi.va.bizmeta.service.IMetaDataService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.enums.BillFetchCondiFixCodeEnums;
import com.jiuqi.gcreport.bde.bill.setting.client.enums.BillFetchCondiType;
import com.jiuqi.gcreport.bde.bill.setting.impl.dao.BillFetchCondiDao;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillFetchCondiEO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFetchCondiService;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillFetchCondiServiceImpl
implements BillFetchCondiService {
    @Autowired
    private BillFetchCondiDao fetchCondiDao;
    @Autowired
    private BillDefineService billDefineServiceImpl;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private BillExtractSchemeService extractSchemeService;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private MetaInfoService metaInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BillFetchCondiServiceImpl.class);

    @Override
    public void initBillFetchCondi(String fetchSchemeId) {
        BillFetchCondiDTO fetchCondiDTO = new BillFetchCondiDTO(fetchSchemeId, "DJZB[UNITCODE]", "DJZB[BILLDATE]", "DJZB[BILLDATE]");
        List<BillFetchCondiEO> fetchCondiEOS = this.convertFetchCondiDTO2EO(fetchCondiDTO);
        this.fetchCondiDao.saveBillFetchCondiEOs(fetchCondiEOS);
    }

    @Override
    public void saveBillFetchCondi(BillFetchCondiDTO fetchCondiDTO) {
        List<BillFetchCondiEO> fetchCondiEOS = this.convertFetchCondiDTO2EO(fetchCondiDTO);
        this.fetchCondiDao.saveBillFetchCondiEOs(fetchCondiEOS);
    }

    @Override
    public BillFetchCondiDTO queryBillFetchCondiDTOByFetchSchemeId(String fetchSchemeId) {
        List<BillFetchCondiEO> fetchCondiEOS = this.fetchCondiDao.queryBillFetchCondiEOByFetchSchemeId(fetchSchemeId);
        return this.convertFetchCondiEO2DTO(fetchCondiEOS);
    }

    @Override
    public boolean checkBillFetchCondi(BillFetchCondiDTO fetchCondiDTO) {
        if (StringUtils.isEmpty((String)fetchCondiDTO.getFetchSchemeId())) {
            return false;
        }
        BillFetchSchemeDTO fetchSchemeDTO = this.extractSchemeService.findById(fetchCondiDTO.getFetchSchemeId());
        if (Objects.isNull(fetchSchemeDTO)) {
            return false;
        }
        String billType = fetchSchemeDTO.getBillType();
        if (StringUtils.isEmpty((String)fetchCondiDTO.getUnitCode()) || StringUtils.isEmpty((String)fetchCondiDTO.getStartDate()) || StringUtils.isEmpty((String)fetchCondiDTO.getEndDate())) {
            return false;
        }
        boolean unitChecked = this.checkRequiredFormula(billType, fetchCondiDTO.getUnitCode());
        boolean startDateChecked = this.checkRequiredFormula(billType, fetchCondiDTO.getStartDate());
        boolean endDateChecked = this.checkRequiredFormula(billType, fetchCondiDTO.getEndDate());
        boolean gcUnitTypeChecked = this.checkNoRequiredFormula(billType, fetchCondiDTO.getGcUnitType());
        boolean currencyChecked = this.checkNoRequiredFormula(billType, fetchCondiDTO.getCurrency());
        boolean reportPeriodChecked = this.checkNoRequiredFormula(billType, fetchCondiDTO.getReportPeriod());
        return unitChecked && startDateChecked && endDateChecked && gcUnitTypeChecked && currencyChecked && reportPeriodChecked;
    }

    private boolean checkRequiredFormula(String billType, String formula) {
        if (StringUtils.isEmpty((String)formula)) {
            return false;
        }
        return this.checkSingleBillFormula(billType, formula);
    }

    private boolean checkNoRequiredFormula(String billType, String formula) {
        if (StringUtils.isEmpty((String)formula)) {
            return true;
        }
        return this.checkSingleBillFormula(billType, formula);
    }

    private boolean checkSingleBillFormula(String billType, String formula) {
        return !StringUtils.isEmpty((String)billType);
    }

    @Override
    public void copyBillFetchCondiDTOByFetchSchemeId(String fetchSchemeId, String newFetchSchemeId) {
        List<BillFetchCondiEO> fetchCondiEOS = this.fetchCondiDao.queryBillFetchCondiEOByFetchSchemeId(fetchSchemeId);
        for (BillFetchCondiEO fetchCondiEO : fetchCondiEOS) {
            fetchCondiEO.setId(UUIDUtils.newHalfGUIDStr());
            fetchCondiEO.setFetchSchemeId(newFetchSchemeId);
        }
        this.fetchCondiDao.saveBillFetchCondiEOs(fetchCondiEOS);
    }

    @Override
    public void deleteBillFetchCondiByFetchSchemeId(String fetchSchemeId) {
        this.fetchCondiDao.deleteBillFetchCondiEOByFetchSchemeId(fetchSchemeId);
    }

    private List<BillFetchCondiEO> convertFetchCondiDTO2EO(BillFetchCondiDTO fetchCondiDTO) {
        ArrayList<BillFetchCondiEO> fetchCondiEOS = new ArrayList<BillFetchCondiEO>();
        Assert.isNotEmpty((String)fetchCondiDTO.getUnitCode(), (String)"\u5355\u636e\u53d6\u6570\u6761\u4ef6\u5fc5\u586b\u6761\u4ef6\u672a\u586b-\u7ec4\u7ec7\u673a\u6784", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchCondiDTO.getStartDate(), (String)"\u5355\u636e\u53d6\u6570\u6761\u4ef6\u5fc5\u586b\u6761\u4ef6\u672a\u586b-\u5f00\u59cb\u671f\u95f4", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchCondiDTO.getEndDate(), (String)"\u5355\u636e\u53d6\u6570\u6761\u4ef6\u5fc5\u586b\u6761\u4ef6\u672a\u586b-\u7ed3\u675f\u671f\u95f4", (Object[])new Object[0]);
        BillFetchCondiEO unitFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.FIX.getCode(), BillFetchCondiFixCodeEnums.UNIT_CODE.getCode(), fetchCondiDTO.getUnitCode());
        BillFetchCondiEO startDateFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.FIX.getCode(), BillFetchCondiFixCodeEnums.START_DATE.getCode(), fetchCondiDTO.getStartDate());
        BillFetchCondiEO endDateFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.FIX.getCode(), BillFetchCondiFixCodeEnums.END_DATE.getCode(), fetchCondiDTO.getEndDate());
        fetchCondiEOS.add(unitFetchCondiEO);
        fetchCondiEOS.add(startDateFetchCondiEO);
        fetchCondiEOS.add(endDateFetchCondiEO);
        if (!Strings.isEmpty(fetchCondiDTO.getCurrency())) {
            BillFetchCondiEO currencyFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.FIX.getCode(), BillFetchCondiFixCodeEnums.CURRENCY.getCode(), fetchCondiDTO.getCurrency());
            fetchCondiEOS.add(currencyFetchCondiEO);
        }
        if (!Strings.isEmpty(fetchCondiDTO.getReportPeriod())) {
            BillFetchCondiEO reportPeriodFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.FIX.getCode(), BillFetchCondiFixCodeEnums.REPORT_PERIOD.getCode(), fetchCondiDTO.getReportPeriod());
            fetchCondiEOS.add(reportPeriodFetchCondiEO);
        }
        if (!Strings.isEmpty(fetchCondiDTO.getGcUnitType())) {
            BillFetchCondiEO gcUnitTypeFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.FIX.getCode(), BillFetchCondiFixCodeEnums.GC_UNIT_TYPE.getCode(), fetchCondiDTO.getGcUnitType());
            fetchCondiEOS.add(gcUnitTypeFetchCondiEO);
        }
        if (!CollectionUtils.isEmpty((Collection)fetchCondiDTO.getCustomFetchCondi())) {
            for (String customField : fetchCondiDTO.getCustomFetchCondi()) {
                BillFetchCondiEO customFetchCondiEO = new BillFetchCondiEO(UUIDUtils.newHalfGUIDStr(), fetchCondiDTO.getFetchSchemeId(), BillFetchCondiType.CUSTOM.getCode(), customField, "");
                fetchCondiEOS.add(customFetchCondiEO);
            }
        }
        return fetchCondiEOS;
    }

    private BillFetchCondiDTO convertFetchCondiEO2DTO(List<BillFetchCondiEO> fetchCondiEOs) {
        BillFetchCondiDTO fetchCondiDTO = new BillFetchCondiDTO();
        block16: for (BillFetchCondiEO fetchCondiEO : fetchCondiEOs) {
            switch (fetchCondiEO.getFetchCondiCode()) {
                case "UNIT_CODE": {
                    fetchCondiDTO.setUnitCode(fetchCondiEO.getFetchCondiValue());
                    continue block16;
                }
                case "START_DATE": {
                    fetchCondiDTO.setStartDate(fetchCondiEO.getFetchCondiValue());
                    continue block16;
                }
                case "END_DATE": {
                    fetchCondiDTO.setEndDate(fetchCondiEO.getFetchCondiValue());
                    continue block16;
                }
                case "CURRENCY": {
                    fetchCondiDTO.setCurrency(fetchCondiEO.getFetchCondiValue());
                    continue block16;
                }
                case "REPORT_PERIOD": {
                    fetchCondiDTO.setReportPeriod(fetchCondiEO.getFetchCondiValue());
                    continue block16;
                }
                case "GC_UNIT_TYPE": {
                    fetchCondiDTO.setGcUnitType(fetchCondiEO.getFetchCondiValue());
                    continue block16;
                }
            }
            if (!BillFetchCondiType.CUSTOM.getCode().equals(fetchCondiEO.getFetchCondiType())) continue;
            if (CollectionUtils.isEmpty((Collection)fetchCondiDTO.getCustomFetchCondi())) {
                ArrayList<String> customList = new ArrayList<String>();
                customList.add(fetchCondiEO.getFetchCondiCode());
                fetchCondiDTO.setCustomFetchCondi(customList);
                continue;
            }
            fetchCondiDTO.getCustomFetchCondi().add(fetchCondiEO.getFetchCondiCode());
        }
        return fetchCondiDTO;
    }

    @Override
    public MetaDataDTO getBillDefineByBillId(String billUniqueCode) {
        MetaInfoDTO billDTO = this.metaInfoService.getMetaInfoByUniqueCode(billUniqueCode);
        if (Objects.isNull(billDTO)) {
            LOGGER.error("\u6839\u636e\u5355\u636e\u552f\u4e00\u4ee3\u7801{}\u672a\u67e5\u8be2\u5230\u5355\u636e", (Object)billUniqueCode);
            throw new BusinessRuntimeException("\u6839\u636e\u5355\u636e\u552f\u4e00\u4ee3\u7801" + billUniqueCode + "\u672a\u67e5\u8be2\u5230\u5355\u636e");
        }
        UUID billId = billDTO.getId();
        return this.metaDataService.getMetaDataById(billId);
    }
}

