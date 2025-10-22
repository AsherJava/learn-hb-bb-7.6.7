/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrConfirmStatusEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.callback;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbr.dao.ClbrBillCheckDao;
import com.jiuqi.gcreport.clbr.entity.ClbrBillCheckEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrConfirmStatusEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClbrBillCheckAddCheckUserInit
implements ModuleInitiator {
    private static Logger LOGGER = LoggerFactory.getLogger(ClbrBillCheckAddCheckUserInit.class);
    @Autowired
    private ClbrBillCheckDao clbrBillCheckDao;

    public void init(ServletContext context) throws Exception {
    }

    @Transactional
    public void initWhenStarted(ServletContext context) {
        List clbrBillCheckEOS = this.clbrBillCheckDao.selectList((BaseEntity)new ClbrBillCheckEO());
        if (CollectionUtils.isEmpty((Collection)clbrBillCheckEOS)) {
            return;
        }
        List filterChecks = clbrBillCheckEOS.stream().filter(clbrBillCheckEO -> !StringUtils.isEmpty((String)clbrBillCheckEO.getInitiateConfirmUsername())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filterChecks)) {
            return;
        }
        LOGGER.info("\u534f\u540c\u786e\u8ba4\u5355\u589e\u52a0\u786e\u8ba4\u4eba\u811a\u672c\u5f00\u59cb\u6267\u884c\uff0c\u5f85\u4fee\u590d\u6761\u6570\uff1a{}", (Object)clbrBillCheckEOS.size());
        ArrayList<ClbrBillCheckEO> updateChecks = new ArrayList<ClbrBillCheckEO>();
        ClbrBillCheckEO autoBillCheckEO = new ClbrBillCheckEO();
        autoBillCheckEO.setClbrBillType(ClbrBillTypeEnum.RECEIVER.getCode());
        autoBillCheckEO.setConfirmType(ClbrBillConfirmTypeEnum.AUTO.getCode());
        List autoCheckEOs = this.clbrBillCheckDao.selectList((BaseEntity)autoBillCheckEO);
        Map<String, String> autoMap = autoCheckEOs.stream().collect(Collectors.toMap(ClbrBillCheckEO::getGroupId, ClbrBillCheckEO::getUserName, (k1, k2) -> k2));
        List<ClbrBillCheckEO> billCheckEOS = this.clbrBillCheckDao.listByGroupIds(new ArrayList<String>(autoMap.keySet()));
        billCheckEOS.forEach(clbrBillCheckEO -> {
            clbrBillCheckEO.setInitiateConfirmUsername((String)autoMap.get(clbrBillCheckEO.getGroupId()));
            clbrBillCheckEO.setReceiveConfirmUsername((String)autoMap.get(clbrBillCheckEO.getGroupId()));
        });
        updateChecks.addAll(billCheckEOS);
        ClbrBillCheckEO bothBillCheckEO = new ClbrBillCheckEO();
        bothBillCheckEO.setClbrBillType(ClbrBillTypeEnum.RECEIVER.getCode());
        bothBillCheckEO.setConfirmType(ClbrBillConfirmTypeEnum.MANUAL.getCode());
        bothBillCheckEO.setConfirmStatus(ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode());
        List bothBillCheckEOs = this.clbrBillCheckDao.selectList((BaseEntity)bothBillCheckEO);
        Map<String, String> bothMap = bothBillCheckEOs.stream().collect(Collectors.toMap(ClbrBillCheckEO::getGroupId, ClbrBillCheckEO::getUserName, (k1, k2) -> k2));
        bothBillCheckEO.setClbrBillType(ClbrBillTypeEnum.INITIATOR.getCode());
        List bothInitBillCheckEOs = this.clbrBillCheckDao.selectList((BaseEntity)bothBillCheckEO);
        Map<String, String> bothInitMap = bothInitBillCheckEOs.stream().collect(Collectors.toMap(ClbrBillCheckEO::getGroupId, ClbrBillCheckEO::getUserName, (k1, k2) -> k2));
        List<ClbrBillCheckEO> bothBillCheckEOS = this.clbrBillCheckDao.listByGroupIds(new ArrayList<String>(bothMap.keySet()));
        bothBillCheckEOS.forEach(clbrBillCheckEO -> {
            clbrBillCheckEO.setInitiateConfirmUsername((String)bothInitMap.get(clbrBillCheckEO.getGroupId()));
            clbrBillCheckEO.setReceiveConfirmUsername((String)bothMap.get(clbrBillCheckEO.getGroupId()));
        });
        updateChecks.addAll(bothBillCheckEOS);
        ClbrBillCheckEO singleBillCheckEO = new ClbrBillCheckEO();
        singleBillCheckEO.setClbrBillType(ClbrBillTypeEnum.INITIATOR.getCode());
        singleBillCheckEO.setConfirmType(ClbrBillConfirmTypeEnum.MANUAL.getCode());
        singleBillCheckEO.setConfirmStatus(ClbrConfirmStatusEnum.SINGLE_CONFIRMED.getCode());
        List singleBillCheckEOs = this.clbrBillCheckDao.selectList((BaseEntity)singleBillCheckEO);
        Map<String, String> singleMap = singleBillCheckEOs.stream().collect(Collectors.toMap(ClbrBillCheckEO::getGroupId, ClbrBillCheckEO::getUserName, (k1, k2) -> k2));
        List<ClbrBillCheckEO> singleBillCheckEOS = this.clbrBillCheckDao.listByGroupIds(new ArrayList<String>(singleMap.keySet()));
        singleBillCheckEOS.forEach(clbrBillCheckEO -> clbrBillCheckEO.setInitiateConfirmUsername((String)singleMap.get(clbrBillCheckEO.getGroupId())));
        updateChecks.addAll(singleBillCheckEOS);
        this.clbrBillCheckDao.updateBatch(updateChecks);
        LOGGER.info("\u534f\u540c\u786e\u8ba4\u5355\u589e\u52a0\u786e\u8ba4\u4eba\u811a\u672c\u6267\u884c\u7ed3\u675f\uff0c\u4fee\u590d\u6761\u6570\uff1a{}", (Object)updateChecks.size());
    }
}

