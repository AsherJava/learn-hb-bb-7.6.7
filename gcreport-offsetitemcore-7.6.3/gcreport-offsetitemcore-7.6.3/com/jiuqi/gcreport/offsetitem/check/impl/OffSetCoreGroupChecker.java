/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.offsetitem.check.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache;
import com.jiuqi.gcreport.offsetitem.check.IOffsetGroupChecker;
import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class OffSetCoreGroupChecker
implements IOffsetGroupChecker {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private SubjectCache subjectCache;

    @Override
    public String validatorName() {
        return "\u62b5\u9500\u5206\u5f55\u57fa\u7840\u6821\u9a8c\u5668";
    }

    @Override
    public OffsetItemCheckResult saveCheck(GcOffSetVchrDTO itemDTO) {
        if (null == itemDTO.getMrecid()) {
            itemDTO.setMrecid(UUIDOrderSnowUtils.newUUIDStr());
        }
        double sumOffsetValue = 0.0;
        HashSet<String> units = new HashSet<String>();
        boolean isLossGain = false;
        HashSet<OffSetSrcTypeEnum> lossGainSrcTypeSet = new HashSet<OffSetSrcTypeEnum>(Arrays.asList(OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN, OffSetSrcTypeEnum.DEFERRED_INCOME_TAX, OffSetSrcTypeEnum.DEFERRED_INCOME_TAX_RULE, OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY));
        List<GcOffSetVchrItemDTO> items = itemDTO.getItems();
        for (GcOffSetVchrItemDTO offSetItemDTO : items) {
            if (!CollectionUtils.isEmpty(offSetItemDTO.getUnSysFields())) {
                for (String unSysFieldKey : offSetItemDTO.getUnSysFields().keySet()) {
                    if (offSetItemDTO.getFields().containsKey(unSysFieldKey)) continue;
                    offSetItemDTO.addFieldValue(unSysFieldKey, offSetItemDTO.getUnSysFieldValue(unSysFieldKey));
                }
            }
            this.checkItemDTO(offSetItemDTO);
            units.add(offSetItemDTO.getUnitId());
            units.add(offSetItemDTO.getOppUnitId());
            sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)offSetItemDTO.getOffSetDebit(), (Double)offSetItemDTO.getOffSetCredit()));
            isLossGain = isLossGain || lossGainSrcTypeSet.contains((Object)offSetItemDTO.getOffSetSrcType());
        }
        Assert.isTrue((boolean)NumberUtils.isZreo((Double)sumOffsetValue), (String)"\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        Assert.isTrue((itemDTO.isAllowMoreUnit() || isLossGain || units.size() == 2 ? 1 : 0) != 0, (String)("\u540c\u4e00\u7ec4\u62b5\u9500\u5206\u5f55\u6d89\u53ca\u7684\u5355\u4f4d\u53ea\u80fd\u4e3a\u4e24\u5bb6,\u76ee\u524d\u4e3a" + units.size() + "\u5bb6"), (Object[])new Object[0]);
        if (itemDTO.isAllowMoreUnit()) {
            this.checkMergeUnit(itemDTO.getItems(), itemDTO.getCurrentOrgType(), itemDTO.getItems().get(0).getDefaultPeriod());
        }
        this.repairAmtPrecision(itemDTO);
        if (!StringUtils.hasLength(itemDTO.getVchrCode())) {
            OffsetVchrCodeDTO vchrCodeDTO = new OffsetVchrCodeDTO();
            vchrCodeDTO.setPeriodStr(items.get(0).getDefaultPeriod());
            itemDTO.setVchrCode(OffsetVchrCodeUtil.createVchrCode(vchrCodeDTO));
        }
        return OffsetItemCheckResult.success();
    }

    public void repairAmtPrecision(GcOffSetVchrDTO itemDTO) {
        ArrayList<GcOffSetVchrItemDTO> repairItemList = new ArrayList<GcOffSetVchrItemDTO>(itemDTO.getItems().size());
        double sumOffsetValue = 0.0;
        double sumValue = 0.0;
        for (GcOffSetVchrItemDTO offSetItemDTO : itemDTO.getItems()) {
            GcOffSetVchrItemDTO offSetItemCopy = new GcOffSetVchrItemDTO();
            BeanUtils.copyProperties((Object)offSetItemDTO, (Object)offSetItemCopy);
            offSetItemCopy.resetFields(new HashMap(offSetItemDTO.getFields()));
            if (offSetItemCopy.getOffSetDebit() != null) {
                offSetItemCopy.setOffSetDebit(OffsetVchrItemNumberUtils.round((Double)offSetItemCopy.getOffSetDebit()));
            }
            if (offSetItemCopy.getOffSetCredit() != null) {
                offSetItemCopy.setOffSetCredit(OffsetVchrItemNumberUtils.round((Double)offSetItemCopy.getOffSetCredit()));
            }
            if (offSetItemCopy.getDebit() != null) {
                offSetItemCopy.setDebit(OffsetVchrItemNumberUtils.round((Double)offSetItemCopy.getDebit()));
            }
            if (offSetItemCopy.getCredit() != null) {
                offSetItemCopy.setCredit(OffsetVchrItemNumberUtils.round((Double)offSetItemCopy.getCredit()));
            }
            repairItemList.add(offSetItemCopy);
            sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)offSetItemCopy.getOffSetDebit(), (Double)offSetItemCopy.getOffSetCredit()));
            sumValue = NumberUtils.sum((double)sumValue, (double)NumberUtils.sub((Double)offSetItemCopy.getDebit(), (Double)offSetItemCopy.getCredit()));
        }
        if (!NumberUtils.isZreo((Double)sumOffsetValue) || !NumberUtils.isZreo((Double)sumValue)) {
            return;
        }
        itemDTO.setItems(repairItemList);
    }

    private void checkMergeUnit(List<GcOffSetVchrItemDTO> gcOffSetVchrItemDTOList, String currentOrgType, String defaultPeriod) {
        if (gcOffSetVchrItemDTOList.size() == 1) {
            return;
        }
        Assert.isTrue((!StringUtils.isEmpty(currentOrgType) ? 1 : 0) != 0, (String)"\u591a\u5bb6\u5355\u4f4d\u62b5\u9500\u65f6\uff0c\u5f53\u524d\u673a\u6784\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)currentOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, defaultPeriod));
        String commonUnitCode = null;
        int i = 1;
        for (GcOffSetVchrItemDTO gcOffSetVchrItemDTO : gcOffSetVchrItemDTOList) {
            GcOrgCacheVO unitGcOrg = tool.getOrgByCode(gcOffSetVchrItemDTO.getUnitId());
            GcOrgCacheVO oppUnitGcOrg = tool.getOrgByCode(gcOffSetVchrItemDTO.getOppUnitId());
            GcOrgCacheVO commonUnit = tool.getCommonUnit(unitGcOrg, oppUnitGcOrg);
            if (commonUnitCode == null) {
                if (commonUnit == null || commonUnit.getCode() == null) {
                    String errorInfo = "\u7b2c" + i + "\u884c, " + unitGcOrg.getCode() + "|" + unitGcOrg.getTitle() + "\u548c" + oppUnitGcOrg.getCode() + "|" + oppUnitGcOrg.getTitle() + "\u5171\u540c\u4e0a\u7ea7\u4e3a\u7a7a";
                    throw new BusinessRuntimeException(errorInfo);
                }
                commonUnitCode = commonUnit.getCode();
                ++i;
                continue;
            }
            if (!commonUnitCode.equals(commonUnit.getCode())) {
                String errorInfo = "\u7b2c" + i + "\u884c, " + unitGcOrg.getCode() + "|" + unitGcOrg.getTitle() + "\u548c" + oppUnitGcOrg.getCode() + "|" + oppUnitGcOrg.getTitle() + "\u5171\u540c\u4e0a\u7ea7\u4e0d\u5c5e\u4e8e\u5f53\u524d\u5408\u5e76\u5c42\u7ea7";
                throw new BusinessRuntimeException(errorInfo);
            }
            ++i;
        }
    }

    private void checkItemDTO(GcOffSetVchrItemDTO item) {
        Double amt;
        String reportSystemId;
        Assert.isNotNull((Object)item.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((null != item.getAcctYear() && null != item.getAcctPeriod() && !StringUtils.isEmpty(item.getDefaultPeriod()) ? 1 : 0) != 0, (String)"\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)item.getOffSetCurr(), (String)"\u5e01\u522b\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((item.getOffSetCurr().length() < 10 ? 1 : 0) != 0, (String)"\u5e01\u522b\u975e\u6cd5", (Object[])new Object[0]);
        Assert.isNotNull((Object)item.getUnitId(), (String)"\u672c\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)item.getOppUnitId(), (String)"\u5bf9\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)item.getSubjectCode(), (String)"\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        String subjectTitle = "";
        if (!StringUtils.hasText(item.getSystemId()) && null != (reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdByTaskId(item.getTaskId(), item.getDefaultPeriod()))) {
            item.setSystemId(reportSystemId);
        }
        Assert.isNotNull((Object)item.getSystemId(), (String)"\u4f53\u7cfb\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        boolean isLeaf = this.consolidatedSubjectService.isLeafByCode(item.getSystemId(), item.getSubjectCode());
        Assert.isTrue((boolean)isLeaf, (String)("\u62b5\u9500\u5206\u5f55\u4e0d\u80fd\u4fdd\u5b58\u5e26\u6709\u975e\u672b\u7ea7\u79d1\u76ee\u7684\u6570\u636e\uff0c\u79d1\u76ee\uff1a" + item.getSubjectCode()), (Object[])new Object[0]);
        if (item.getSubjectOrient() == null) {
            ConsolidatedSubjectEO subjectEO = this.subjectCache.getSubjectByCode(item.getSystemId(), item.getSubjectCode());
            Assert.isNotNull((Object)subjectEO, (String)("\u672a\u627e\u89c1\u79d1\u76ee:" + item.getSubjectCode()), (Object[])new Object[0]);
            item.setSubjectOrient(OrientEnum.valueOf((Integer)subjectEO.getOrient()));
            subjectTitle = subjectEO.getTitle();
            Assert.isNotNull((Object)item.getSubjectOrient(), (String)"\u79d1\u76ee\u501f\u8d37\u65b9\u5411\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        }
        Assert.isNotNull((Object)item.getOrient(), (String)"\u6570\u636e\u501f\u8d37\u65b9\u5411\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Double d = amt = item.getOrient() == OrientEnum.D ? item.getOffSetDebit() : item.getOffSetCredit();
        if (null == item.getCreateTime()) {
            item.setCreateTime(new Date());
        }
        switch (item.getOffSetSrcType()) {
            case BROUGHT_FORWARD_LOSS_GAIN: 
            case DEFERRED_INCOME_TAX: {
                break;
            }
            default: {
                String optValue = GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE");
                if ("1".equals(optValue)) break;
                OffSetUnSysDimensionCache.load();
                List<DimensionVO> notNullDimValues = OffSetUnSysDimensionCache.notNullDimValues(item.getSystemId());
                if (CollectionUtils.isEmpty(notNullDimValues)) break;
                String finalSubjectTitle = subjectTitle;
                notNullDimValues.forEach(notNullDimValue -> {
                    Object value = item.getFieldValue(notNullDimValue.getCode());
                    if (StringUtils.isEmpty(value)) {
                        if (null == item.getSortOrder()) {
                            throw new BusinessRuntimeException(notNullDimValue.getTitle() + "\u4e0d\u5141\u8bb8\u4e3a\u7a7a \u672c\u65b9\u5355\u4f4d\uff1a" + item.getUnitId() + " \u79d1\u76ee\uff1a" + finalSubjectTitle + " \u91d1\u989d\uff1a" + amt);
                        }
                        throw new BusinessRuntimeException(this.getTitle(item.getUnitId(), item.getDefaultPeriod()) + notNullDimValue.getTitle() + "\u4e0d\u5141\u8bb8\u4e3a\u7a7a \u672c\u65b9\u5355\u4f4d\uff1a" + item.getUnitId() + " \u79d1\u76ee\uff1a" + finalSubjectTitle + " \u91d1\u989d\uff1a" + amt);
                    }
                });
            }
        }
        if (item.getOrgType() == null) {
            item.setOrgType("NONE");
        }
    }

    private String getTitle(String orgId, String defaultPeriod) {
        YearPeriodObject yp = new YearPeriodObject(null, defaultPeriod);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgId);
        if (org != null) {
            return org.getTitle() + "(" + orgId + ")";
        }
        tool = GcOrgPublicTool.getInstance((String)"MD_ORG_MANAGEMENT", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        org = tool.getOrgByCode(orgId);
        if (org != null) {
            return org.getTitle() + "(" + orgId + ")";
        }
        org = GcOrgPublicTool.getInstance().getBaseOrgByCode(orgId);
        Assert.isNotNull((Object)org, (String)("\u627e\u4e0d\u5230\u6307\u5b9a\u5355\u4f4d\uff0c\u8bf7\u68c0\u67e5\u5355\u4f4d\u6570\u636e:" + orgId), (Object[])new Object[0]);
        return org.getTitle() + "(" + orgId + ")";
    }
}

