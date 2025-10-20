/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO
 *  org.apache.shiro.util.CollectionUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.service.ClbrBillReceBillCodeService;
import java.util.List;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

@Service
public class ClbrBillReceBillCodeServiceImpl
implements ClbrBillReceBillCodeService {
    private final Logger logger = LoggerFactory.getLogger(ClbrBillReceBillCodeServiceImpl.class);
    @Autowired
    private ClbrBillDao clbrBillDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> updateOrDeleteClbrReceBillCode(ClbrBillReceBillCodeDTO clbrBillReceBillCodeDTO) {
        int[] ints;
        String sysCode = clbrBillReceBillCodeDTO.getSysCode();
        GcBaseData clbrSystemBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRSYSTEM", sysCode);
        Assert.isTrue(!ObjectUtils.isEmpty(clbrSystemBdo), "\u672a\u652f\u6301\u7684\u6765\u6e90\u7cfb\u7edf[" + sysCode + "]");
        List<ClbrBillEO> clbrBillEOS = this.clbrBillDao.queryBillMessage(clbrBillReceBillCodeDTO);
        String sendBillIdJoin = String.join((CharSequence)",", clbrBillReceBillCodeDTO.getSendBillIdList());
        if (CollectionUtils.isEmpty(clbrBillEOS)) {
            this.logger.error("\u534f\u540c\u4e1a\u52a1\u5355\u4e2d\u4e0d\u5b58\u5728\u6e90\u5355ID\u4e3a" + sendBillIdJoin + "\u53d1\u8d77\u65b9\u5355\u636e");
            return BusinessResponseEntity.error((String)"\u534f\u540c\u4e1a\u52a1\u5355\u4e2d\u4e0d\u5b58\u5728\u53d1\u8d77\u65b9\u5355\u636e\uff0c\u8bf7\u68c0\u67e5");
        }
        if (clbrBillReceBillCodeDTO.isDeleteFlag()) {
            for (ClbrBillEO clbrBillEO : clbrBillEOS) {
                clbrBillEO.setUnClbrReceBillCode("");
            }
        } else {
            for (ClbrBillEO clbrBillEO : clbrBillEOS) {
                clbrBillEO.setUnClbrReceBillCode(clbrBillReceBillCodeDTO.getReceBillCode());
            }
        }
        if ((ints = this.clbrBillDao.updateBatch(clbrBillEOS)).length != clbrBillEOS.size()) {
            throw new BusinessRuntimeException("\u4fee\u6539\u6216\u6e05\u9664\u5f85\u534f\u540c\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7\u5931\u8d25");
        }
        return BusinessResponseEntity.ok();
    }
}

