/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil
 */
package com.jiuqi.gcreport.calculate.processor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class CalcManagementProcessor {
    private GcCalcEnvContextImpl env;
    private GcOffSetAppOffsetService offSetItemAdjustService;
    private double deleteHisProcess = 0.1;
    private double queryProcess = 0.1;
    private double copyProcess = 0.6;
    private int needCopyNum;
    private int count = 0;
    private Date createTime;
    private int copySteps = 10;
    private int stepCopyNum;
    private double copyStepProcess;
    private GroupMaping groupMaping = new GroupMaping();

    public CalcManagementProcessor(GcCalcEnvContextImpl env) {
        this.env = env;
        this.offSetItemAdjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
    }

    public GcCalcEnvContext executeCalc() {
        this.env.addProgressValueAndRefresh(this.deleteHisProcess);
        this.env.addResultItem("\u5220\u9664\u5386\u53f2\u5df2\u590d\u5236\u62b5\u9500\u5206\u5f55\u3002");
        List<GcOffSetVchrItemAdjustEO> srcOffsetVchrItems = Collections.emptyList();
        this.needCopyNum = srcOffsetVchrItems.size();
        this.env.addResultItem("\u5df2\u83b7\u53d6\u5230\u9700\u8981\u590d\u5236\u7684\u62b5\u9500\u5206\u5f55" + this.needCopyNum + "\u6761\u3002");
        this.env.addProgressValueAndRefresh(this.queryProcess);
        this.env.addResultItem("\u5f00\u59cb\u590d\u5236\u62b5\u9500\u5206\u5f55\u3002");
        List<GcOffSetVchrItemAdjustEO> offsetVchrItems = this.copyVchrItems(srcOffsetVchrItems);
        this.env.setProgressValueAndRefresh(this.deleteHisProcess + this.queryProcess + this.copyProcess);
        this.env.addResultItem("\u62b5\u9500\u5206\u5f55\u590d\u5236\u5b8c\u6210\u3002");
        this.env.addResultItem("\u5f00\u59cb\u5c06\u590d\u5236\u7684\u62b5\u9500\u5206\u5f55\u5165\u5e93\u3002");
        List offSetVchrs = offsetVchrItems.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid)).entrySet().stream().map(entry -> OffsetCoreConvertUtil.convertEO2DTO((String)((String)entry.getKey()), (List)((List)entry.getValue()))).collect(Collectors.toList());
        this.offSetItemAdjustService.batchSave(offSetVchrs);
        this.env.addResultItem("\u62b5\u9500\u5206\u5f55\u5165\u5e93\u5b8c\u6210\u3002");
        this.env.setSuccessFlag(true);
        this.env.setProgressValueAndRefresh(1.0);
        return this.env;
    }

    private List<GcOffSetVchrItemAdjustEO> copyVchrItems(List<GcOffSetVchrItemAdjustEO> srcOffsetVchrItems) {
        this.stepCopyNum = this.needCopyNum / this.copySteps;
        this.copyStepProcess = this.copyProcess / (double)this.copySteps;
        this.createTime = new Date();
        return srcOffsetVchrItems.stream().map(this::copyVchrItem).collect(Collectors.toList());
    }

    private GcOffSetVchrItemAdjustEO copyVchrItem(GcOffSetVchrItemAdjustEO srcOffsetVchrItem) {
        GcOffSetVchrItemAdjustEO offsetVchrItem = new GcOffSetVchrItemAdjustEO();
        BeanUtils.copyProperties(srcOffsetVchrItem, offsetVchrItem);
        offsetVchrItem.getFields().putAll(srcOffsetVchrItem.getFields());
        offsetVchrItem.setId(UUIDOrderUtils.newUUIDStr());
        offsetVchrItem.setSrcId(srcOffsetVchrItem.getId());
        offsetVchrItem.setmRecid(this.groupMaping.getMrecid(srcOffsetVchrItem.getmRecid()));
        offsetVchrItem.setSrcOffsetGroupId(this.groupMaping.getSrcOffsetGroupId(srcOffsetVchrItem.getSrcOffsetGroupId()));
        offsetVchrItem.setOrgType(GCOrgTypeEnum.MANAGEMENT.getId());
        offsetVchrItem.setCreateTime(this.createTime);
        offsetVchrItem.setMemo(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeName());
        offsetVchrItem.setOffSetSrcType(Integer.valueOf(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeValue()));
        ++this.count;
        if (this.stepCopyNum != 0 && this.count % this.stepCopyNum == 0) {
            this.env.addResultItem("\u5df2\u590d\u5236\u62b5\u9500\u5206\u5f55" + this.count + "\u6761\u3002");
            this.env.addProgressValue(this.copyStepProcess);
        }
        return offsetVchrItem;
    }

    private class GroupMaping {
        private Map<String, String> srcMrecidAndMrecidMapping = new HashMap<String, String>();
        private Map<String, String> srcGroupIdAndGroupIdMapping = new HashMap<String, String>();

        private GroupMaping() {
        }

        String getMrecid(String srcMrecid) {
            String mrecid = this.srcMrecidAndMrecidMapping.get(srcMrecid);
            if (mrecid == null) {
                mrecid = UUIDOrderUtils.newUUIDStr();
                this.srcMrecidAndMrecidMapping.put(srcMrecid, mrecid);
            }
            return mrecid;
        }

        String getSrcOffsetGroupId(String srcGroupId) {
            String groupId = this.srcGroupIdAndGroupIdMapping.get(srcGroupId);
            if (groupId == null) {
                groupId = UUIDOrderUtils.newUUIDStr();
                this.srcGroupIdAndGroupIdMapping.put(srcGroupId, groupId);
            }
            return groupId;
        }
    }
}

