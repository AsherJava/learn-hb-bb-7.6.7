/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dao.ClbrBillCheckDao;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDeleteDao;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.service.ClbrBillExtendService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

@Service
public class ClbrBillExtendServiceImpl
implements ClbrBillExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClbrBillExtendServiceImpl.class);
    private final ClbrBillDao clbrBillDao;
    private final ClbrBillDeleteDao clbrBillDeleteDao;
    private final ClbrBillCheckDao clbrBillCheckDao;

    public ClbrBillExtendServiceImpl(ClbrBillDao clbrBillDao, ClbrBillDeleteDao clbrBillDeleteDao, ClbrBillCheckDao clbrBillCheckDao) {
        this.clbrBillCheckDao = clbrBillCheckDao;
        this.clbrBillDeleteDao = clbrBillDeleteDao;
        this.clbrBillDao = clbrBillDao;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void partCancelClbrBills(ClbrBillCancelMsgDTO billCancelMsgDTO) {
        Assert.hasText(billCancelMsgDTO.getSysCode(), "\u6765\u6e90\u7cfb\u7edf\u4e0d\u80fd\u4e3a\u7a7a");
        this.sourceSystemCheck(billCancelMsgDTO.getSysCode());
        String receBillSrcId = billCancelMsgDTO.getSrcId();
        String sendBillSrcId = billCancelMsgDTO.getOppSrcId();
        String clbrCode = billCancelMsgDTO.getClbrCode();
        Assert.hasText(sendBillSrcId, "\u53d1\u8d77\u65b9srcId\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.hasText(receBillSrcId, "\u63a5\u6536\u65b9srcId\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.hasText(clbrCode, "\u534f\u540c\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        this.CancelClbrBillDataCheck(sendBillSrcId, receBillSrcId);
        this.CancelClbrBillCheckDelete(sendBillSrcId, receBillSrcId, clbrCode);
        List<ClbrBillEO> deleteClbrBillEOs = this.getToDeleteClbrBills(sendBillSrcId, receBillSrcId);
        Assert.isTrue(deleteClbrBillEOs != null && deleteClbrBillEOs.size() == 2, "\u4e1a\u52a1\u534f\u540c\u5355\u636e\u67e5\u8be2\u6570\u91cf\u6709\u8bef");
        this.deleteClbrBills(deleteClbrBillEOs);
    }

    private void deleteClbrBills(List<ClbrBillEO> deleteClbrBillEOs) {
        List addClbrBillDeleteEOs = deleteClbrBillEOs.stream().map(clbrBillEO -> {
            clbrBillEO.setBillState(ClbrBillStateEnum.CANCEL.getCode());
            return ClbrBillConverter.convertBillEO2DeleteEO(clbrBillEO);
        }).collect(Collectors.toList());
        int[] addResult = this.clbrBillDeleteDao.addBatch(addClbrBillDeleteEOs);
        Assert.isTrue(Arrays.stream(addResult).allMatch(i -> i == 1), "\u534f\u540c\u5355\u636e\u4f5c\u5e9f\u5931\u8d25");
        int[] deleteResult = this.clbrBillDao.deleteBatch(deleteClbrBillEOs);
        Assert.isTrue(Arrays.stream(deleteResult).allMatch(i -> i == 1), "\u534f\u540c\u5355\u636e\u5220\u9664\u5931\u8d25");
    }

    private void CancelClbrBillCheckDelete(String sendBillSrcId, String receBillSrcId, String clbrCode) {
        int result = this.deleteClbrBills(sendBillSrcId, receBillSrcId, clbrCode);
        if (result != 2) {
            throw new IllegalArgumentException("\u5df2\u534f\u540c\u5173\u7cfb\u6570\u636e\u5220\u9664\u5931\u8d25");
        }
    }

    private void CancelClbrBillDataCheck(String sendBillSrcId, String receBillSrcId) {
        int cnt = this.countClbrBill(sendBillSrcId, receBillSrcId);
        if (cnt != 1) {
            throw new IllegalArgumentException("\u5df2\u534f\u540c\u53d1\u8d77\u65b9\u534f\u540c\u5355\u636e\u6570\u636e\u6709\u8bef");
        }
    }

    private void sourceSystemCheck(String sysCode) {
        GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRSYSTEM", sysCode);
        if (ObjectUtils.isEmpty(baseData)) {
            throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u6765\u6e90\u7cfb\u7edf[" + sysCode + "]");
        }
    }

    private List<ClbrBillEO> getToDeleteClbrBills(String sendBillSrcId, String receBillSrcId) {
        String sql = "SELECT * FROM GC_CLBR_BILL WHERE SRCID IN (?, ?)";
        return this.clbrBillDao.selectEntity(sql, new Object[]{sendBillSrcId, receBillSrcId});
    }

    private int countClbrBill(String sendBillSrcId, String receBillSrcId) {
        String sql = "SELECT * FROM GC_CLBR_BILL WHERE SRCID = ? AND OPPSRCID = ?";
        return this.clbrBillDao.count(sql, new Object[]{receBillSrcId, sendBillSrcId});
    }

    private int deleteClbrBills(String sendBillSrcId, String receBillSrcId, String clbrCode) {
        String sql = "DELETE FROM GC_CLBR_BILLCHECK WHERE CLBRCODE = ? AND SRCID IN (?, ?)";
        return this.clbrBillCheckDao.execute(sql, new Object[]{clbrCode, sendBillSrcId, receBillSrcId});
    }
}

