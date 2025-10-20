/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlExtractManageServiceImpl
extends SameCtrlOffSetItemServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(SameCtrlExtractManageServiceImpl.class);
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    public void extractLastMonthSameCtrlOffSet(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        this.deleteSameCtrlOffset(sameCtrlOffsetCond, sameCtrlExtractManageCond.getSameCtrlLastDateSrcTypeEnum());
        SameCtrlChgOrgEO sameCtrlChgOrgEO = sameCtrlExtractManageCond.getSameCtrlChgOrgEO();
        GcOrgCacheVO virtualCodeVO = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(sameCtrlChgOrgEO.getVirtualCode());
        String result = String.format("\u5f00\u59cb\u6267\u884c\u865a\u62df\u5355\u4f4d\uff1a%1s | %2s \u671f\u672b\u62b5\u9500\u5206\u5f55\u63d0\u53d6", sameCtrlChgOrgEO.getVirtualCode(), virtualCodeVO != null ? virtualCodeVO.getTitle() : sameCtrlChgOrgEO.getVirtualCode());
        sameCtrlChgEnvContext.addResultItem(result);
        logger.info(result);
        int mrecidNum = this.extractSameCtrlOffSet(sameCtrlChgEnvContext);
        String resultEnd = String.format("\u865a\u62df\u5355\u4f4d\uff1a%1s | %2s \u5171\u63d0\u53d6%3s\u7ec4\u62b5\u9500\u5206\u5f55", sameCtrlChgOrgEO.getVirtualCode(), virtualCodeVO != null ? virtualCodeVO.getTitle() : sameCtrlChgOrgEO.getVirtualCode(), mrecidNum);
        sameCtrlChgEnvContext.addResultItem(resultEnd);
        logger.info(resultEnd);
    }

    private int extractSameCtrlOffSet(SameCtrlChgEnvContext sameCtrlChgEnvContext) {
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        SameCtrlExtractManageCond sameCtrlExtractManageCond = sameCtrlChgEnvContext.getSameCtrlExtractManageCond();
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemEOS = new ArrayList<SameCtrlOffSetItemEO>();
        String periodStr = sameCtrlOffsetCond.getPeriodStr();
        sameCtrlOffsetCond.setPeriodStr(this.getLastPeriodStr(sameCtrlOffsetCond.getPeriodStr()));
        List<SameCtrlOffSetItemEO> sameCtrlOffSetItems = this.sameCtrlOffSetItemDao.listOffsetsByParams(sameCtrlOffsetCond, sameCtrlExtractManageCond.getSameCtrlSrcTypeEnumList().stream().map(SameCtrlSrcTypeEnum::getCode).collect(Collectors.toList()));
        Map<String, List<SameCtrlOffSetItemEO>> mecid2SameCtrlOffsetItemMap = sameCtrlOffSetItems.stream().collect(Collectors.groupingBy(SameCtrlOffSetItemEO::getmRecid));
        for (Map.Entry<String, List<SameCtrlOffSetItemEO>> entry : mecid2SameCtrlOffsetItemMap.entrySet()) {
            List<SameCtrlOffSetItemEO> sameCtrlItemsOfMecid = entry.getValue();
            String mRecid = UUIDOrderSnowUtils.newUUIDStr();
            for (SameCtrlOffSetItemEO item : sameCtrlItemsOfMecid) {
                SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
                BeanUtils.copyProperties((Object)item, (Object)sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEO.setId(UUIDUtils.newUUIDStr());
                sameCtrlOffSetItemEO.setmRecid(mRecid);
                sameCtrlOffSetItemEO.setDefaultPeriod(periodStr);
                sameCtrlOffSetItemEO.setSameCtrlSrcType(sameCtrlExtractManageCond.getSameCtrlLastDateSrcTypeEnum().getCode());
                sameCtrlOffSetItemEO.setSrcId(item.getId());
                sameCtrlOffSetItemEO.setSameCtrlChgId(sameCtrlExtractManageCond.getSameCtrlChgOrgEO().getId());
                this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
                sameCtrlOffSetItemEOS.add(sameCtrlOffSetItemEO);
            }
        }
        this.sameCtrlOffSetItemDao.addBatch(sameCtrlOffSetItemEOS);
        return mecid2SameCtrlOffsetItemMap.size();
    }

    private String getLastPeriodStr(String periodStr) {
        int month = Integer.valueOf(periodStr.substring(periodStr.length() - 2)) - 1;
        String monthStr = month > 9 ? String.valueOf(month) : "0" + month;
        return periodStr.substring(0, periodStr.length() - 2) + monthStr;
    }

    public void initSameCtrlExtractManageCond(SameCtrlExtractDataVO sameCtrlExtractDataVO, SameCtrlChgEnvContextImpl sameCtrlChgEnvContext, SameCtrlSrcTypeEnum sameCtrlEnum) {
        SameCtrlExtractManageCond sameCtrlExtractManageCond = new SameCtrlExtractManageCond();
        sameCtrlExtractManageCond.setGcOrgCenterService(this.getGcOrgTool(sameCtrlChgEnvContext.getSameCtrlOffsetCond()));
        SameCtrlChgOrgEO sameCtrlChgOrgEO = (SameCtrlChgOrgEO)this.sameCtrlChgOrgDao.get((Serializable)((Object)sameCtrlExtractDataVO.getSameCtrlChgOrg().getId()));
        sameCtrlExtractManageCond.setSameCtrlChgOrgEO(sameCtrlChgOrgEO);
        sameCtrlExtractManageCond.setSameCtrlChgOrgEOList(this.sameCtrlChgOrgDao.listSameCtrlChgOrgByMRecid(sameCtrlChgOrgEO.getmRecid()));
        sameCtrlExtractManageCond.setSystemId(this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(sameCtrlChgEnvContext.getSameCtrlOffsetCond().getTaskId(), sameCtrlChgEnvContext.getSameCtrlOffsetCond().getPeriodStr()));
        sameCtrlExtractManageCond.setSameCtrlSrcTypeEnum(sameCtrlEnum);
        sameCtrlChgEnvContext.setSameCtrlExtractManageCond(sameCtrlExtractManageCond);
        SameCtrlOffsetCond sameCtrlOffsetCond = sameCtrlChgEnvContext.getSameCtrlOffsetCond();
        GcOrgCacheVO changedCodeVO = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(sameCtrlChgOrgEO.getChangedCode());
        GcOrgCacheVO virtualCodeVO = sameCtrlExtractManageCond.getGcOrgCenterService().getOrgByCode(sameCtrlChgOrgEO.getVirtualCode());
        sameCtrlOffsetCond.setSameParentCode(sameCtrlExtractManageCond.getGcOrgCenterService().getCommonUnit(changedCodeVO, virtualCodeVO).getCode());
        sameCtrlOffsetCond.setSelectAdjustCode(sameCtrlExtractDataVO.getSelectAdjustCode());
    }

    public List<SameCtrlOffSetItemEO> listOffsetSameCtrlManageByParams(SameCtrlOffsetCond sameCtrlOffsetCond, List<String> sameCtrlSrcTypeList) {
        return this.sameCtrlOffSetItemDao.listOffsetsByParams(sameCtrlOffsetCond, sameCtrlSrcTypeList);
    }

    public int[] addBatch(List<SameCtrlOffSetItemEO> saveSameCtrlOffSetItemEOList) {
        return this.sameCtrlOffSetItemDao.addBatch(saveSameCtrlOffSetItemEOList);
    }
}

