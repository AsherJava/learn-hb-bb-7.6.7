/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.service.CommonBillService
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.investworkpaper.vo.Column
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo$SettingData
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo$SettingData$TzSetting
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.service.CommonBillService;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.invest.investworkpaper.dao.InvestWorkPaperSettingDao;
import com.jiuqi.gcreport.invest.investworkpaper.entity.InvestWorkPaperSettingEO;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperSettingService;
import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestWorkPaperSettingServiceImpl
implements InvestWorkPaperSettingService {
    @Autowired
    private InvestWorkPaperSettingDao investWorkPaperSettingDao;
    @Autowired
    private CommonBillService commonBillService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(InvestWorkPaperSettingVo investWorkPaperSettingVo) {
        InvestWorkPaperSettingEO investWorkPaperSettingEO = new InvestWorkPaperSettingEO();
        BeanUtils.copyProperties(investWorkPaperSettingVo, (Object)investWorkPaperSettingEO);
        investWorkPaperSettingEO.setSettingDataStr(JsonUtils.writeValueAsString((Object)investWorkPaperSettingVo.getSettingData()));
        String userId = NpContextHolder.getContext().getUser().getId();
        investWorkPaperSettingEO.setUserId(userId);
        if (StringUtils.isEmpty((String)investWorkPaperSettingVo.getId())) {
            investWorkPaperSettingEO.setId(UUIDOrderUtils.newUUIDStr());
            this.investWorkPaperSettingDao.save(investWorkPaperSettingEO);
        } else {
            this.investWorkPaperSettingDao.update((BaseEntity)investWorkPaperSettingEO);
        }
    }

    @Override
    public InvestWorkPaperSettingVo getSettingData(String taskId, String systemId, String orgType) {
        InvestWorkPaperSettingEO investWorkPaperSettingEO = this.investWorkPaperSettingDao.getInvestWorkPaperSetting(NpContextHolder.getContext().getUser().getId(), taskId, orgType, systemId);
        if (investWorkPaperSettingEO == null) {
            InvestWorkPaperSettingVo investWorkPaperSettingVo = new InvestWorkPaperSettingVo();
            ArrayList<InvestWorkPaperSettingVo.SettingData.TzSetting> tzSettingList = new ArrayList<InvestWorkPaperSettingVo.SettingData.TzSetting>();
            tzSettingList.add(new InvestWorkPaperSettingVo.SettingData.TzSetting("ENDGOODWILL", "\u5546\u8a89\u539f\u503c"));
            tzSettingList.add(new InvestWorkPaperSettingVo.SettingData.TzSetting("ENDGOODWILLDEVALUE", "\u671f\u672b\u5546\u8a89\u51cf\u503c\u51c6\u5907"));
            tzSettingList.add(new InvestWorkPaperSettingVo.SettingData.TzSetting("ENDINVESTMENTCOST", "\u671f\u672b\u6295\u8d44\u6210\u672c"));
            tzSettingList.add(new InvestWorkPaperSettingVo.SettingData.TzSetting("ENDINVSTDEVALUEPREP", "\u671f\u672b\u6295\u8d44\u51cf\u503c\u51c6\u5907"));
            investWorkPaperSettingVo.getSettingData().setTzSetting(tzSettingList);
            investWorkPaperSettingVo.setTaskId(taskId);
            investWorkPaperSettingVo.setOrgType(orgType);
            return investWorkPaperSettingVo;
        }
        InvestWorkPaperSettingVo investWorkPaperSettingVo = new InvestWorkPaperSettingVo();
        BeanUtils.copyProperties((Object)investWorkPaperSettingEO, investWorkPaperSettingVo);
        investWorkPaperSettingVo.setSettingData((InvestWorkPaperSettingVo.SettingData)JsonUtils.readValue((String)investWorkPaperSettingEO.getSettingDataStr(), InvestWorkPaperSettingVo.SettingData.class));
        return investWorkPaperSettingVo;
    }

    @Override
    public List<Column> listInvestAmtFields() {
        List dataModelColumns = this.commonBillService.listAmtFileds("GC_INVESTBILL");
        List<String> notContainColums = Arrays.asList("ENDEQUITYRATIO", "COMPREEQUITYRATIO");
        return dataModelColumns.stream().map(column -> new Column(column.getColumnName(), column.getColumnTitle())).filter(column -> !notContainColums.contains(column.getColumnKey())).collect(Collectors.toList());
    }
}

