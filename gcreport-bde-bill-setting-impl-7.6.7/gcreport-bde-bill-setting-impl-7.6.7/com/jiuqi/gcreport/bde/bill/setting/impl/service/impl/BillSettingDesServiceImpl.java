/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingDesService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingDesService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillSettingDesServiceImpl
implements BillSettingDesService {
    private static final Logger log = LoggerFactory.getLogger(BillSettingDesServiceImpl.class);
    @Autowired
    private BillFloatSettingDesService floatSettingDesService;
    @Autowired
    private BillFixedSettingDesService fixedSettingDesService;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;

    @Override
    public BillFloatRegionConfigDTO getBillFloatSetting(BillSettingCondiDTO billSettingCondi) {
        return this.floatSettingDesService.getBillFloatSetting(billSettingCondi);
    }

    @Override
    public Map<String, BillFloatRegionConfigDTO> getBillFloatSettingByScheme(BillSettingCondiDTO billSettingCondi) {
        return this.floatSettingDesService.getBillFloatSettingByBillCodeAndScheme(billSettingCondi);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cleanFloatSetting(BillSettingCondiDTO fetchSettingCond) {
        Assert.isNotEmpty((String)fetchSettingCond.getSchemeId(), (String)"\u53d6\u6570\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getBillType(), (String)"\u5355\u636e\u552f\u4e00\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getBillTable(), (String)"\u5355\u636e\u5b50\u8868\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        deleteWhereValues.add(Arrays.asList(fetchSettingCond.getBillType(), fetchSettingCond.getSchemeId(), fetchSettingCond.getBillTable(), fetchSettingCond.getBillTable()));
        this.fetchFloatSettingDesDao.deleteBatchFetchFloatSettingDesData(deleteWhereValues);
        this.fetchSettingDesDao.deleteFloatFetchSettingDesData(deleteWhereValues);
    }

    @Override
    public BillFixedSettingDTO getBillFiexedSetting(BillSettingCondiDTO billSettingCondi) {
        return this.fixedSettingDesService.getBillFiexedSetting(billSettingCondi);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String save(String schemeId, BillExtractSettingDTO setting) {
        Assert.isNotEmpty((String)schemeId);
        Assert.isNotNull((Object)setting);
        Assert.isNotEmpty((String)setting.getBillType());
        Assert.isNotEmpty((String)setting.getBillTable());
        this.fixedSettingDesService.save(schemeId, setting);
        this.floatSettingDesService.save(schemeId, setting);
        return "\u4fdd\u5b58\u6210\u529f";
    }
}

