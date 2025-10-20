/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.client.BillVerifyClient
 */
package com.jiuqi.va.bill.bd.core.service.impl;

import com.jiuqi.va.bill.bd.bill.service.impl.WriteBackServiceImpl;
import com.jiuqi.va.bill.bd.core.dao.BillChangeRecordDao;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordVO;
import com.jiuqi.va.bill.bd.core.service.BillChangeRecordService;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.client.BillVerifyClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BillChangeRecordServiceImpl
implements BillChangeRecordService {
    private static final Logger logger = LoggerFactory.getLogger(WriteBackServiceImpl.class);
    @Autowired
    BillChangeRecordDao billChangeRecordDao;
    @Autowired
    private BillVerifyClient billVerifyClient;

    @Override
    public List<BillChangeRecordDO> list(BillChangeRecordDO recordDO) {
        return this.billChangeRecordDao.select((Object)recordDO);
    }

    @Override
    public R add(BillChangeRecordDO recordDO) {
        recordDO.setId(UUID.randomUUID().toString());
        recordDO.setCreatetime(new Date());
        recordDO.setVer(new BigDecimal(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue()));
        this.billChangeRecordDao.insert((Object)recordDO);
        return R.ok();
    }

    @Override
    public R batchAdd(List<BillChangeRecordDO> records) {
        try {
            List finalRecords = records.stream().filter(o -> {
                if (o.getChangeafter() == null && o.getChangebefore() == null) {
                    return false;
                }
                if (o.getChangeafter() == null && o.getChangebefore() != null) {
                    return true;
                }
                if (o.getChangeafter() != null && o.getChangebefore() == null) {
                    return true;
                }
                return !o.getChangebefore().equals(o.getChangeafter());
            }).collect(Collectors.toList());
            Date date = new Date();
            BigDecimal ver = new BigDecimal(OrderNumUtil.getOrderNumByCurrentTimeMillis().doubleValue());
            for (BillChangeRecordDO recordDO : finalRecords) {
                recordDO.setId(UUID.randomUUID().toString());
                recordDO.setCreatetime(date);
                recordDO.setVer(ver);
                this.billChangeRecordDao.insert((Object)recordDO);
            }
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u53d8\u66f4\u8bb0\u5f55\u5931\u8d25\uff0c\u7ec8\u6b62\u53d8\u66f4", e);
            return R.error((String)"\u63d2\u5165\u53d8\u66f4\u8bb0\u5f55\u5931\u8d25\uff0c\u7ec8\u6b62\u53d8\u66f4");
        }
        return R.ok();
    }

    @Override
    public List<BillChangeRecordVO> listByBillCode(BillChangeRecordDO recordDO) {
        List<BillChangeRecordDO> list = this.billChangeRecordDao.listByBillCode(recordDO);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<BillChangeRecordVO>();
        }
        try {
            Map<String, Map<Date, List<BillChangeRecordDO>>> filterMap = list.stream().collect(Collectors.groupingBy(o -> o.getSrcbillcode(), Collectors.groupingBy(o -> o.getCreatetime())));
            ArrayList<BillChangeRecordVO> result = new ArrayList<BillChangeRecordVO>();
            BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
            ArrayList<String> userIds = new ArrayList<String>();
            userIds.add(ShiroUtil.getUser().getId());
            billVerifyDTO.setUserIds(userIds);
            billVerifyDTO.setAuth(1);
            for (String s : filterMap.keySet()) {
                Map<Date, List<BillChangeRecordDO>> dateListMap = filterMap.get(s);
                billVerifyDTO.setBillCode(s);
                R verify = this.billVerifyClient.encodeBillCode(billVerifyDTO);
                Map verifyMap = (Map)verify.get((Object)"data");
                String verifyCode = (String)verifyMap.get(ShiroUtil.getUser().getId());
                for (Date date : dateListMap.keySet()) {
                    BillChangeRecordVO billChangeRecordVO = new BillChangeRecordVO();
                    billChangeRecordVO.setBillcode(s);
                    billChangeRecordVO.setUniquecode(dateListMap.get(date).get(0).getSrcbilldefine());
                    billChangeRecordVO.setChangedate(date);
                    billChangeRecordVO.setVerify(verifyCode);
                    List<BillChangeRecordDO> billChangeRecordDOS = dateListMap.get(date);
                    billChangeRecordVO.setRecords(billChangeRecordDOS);
                    result.add(billChangeRecordVO);
                }
            }
            return result.stream().sorted(Comparator.comparing(BillChangeRecordVO::getChangedate).reversed()).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error("\u53d8\u66f4\u8bb0\u5f55\u6570\u636e\u5904\u7406\u5f02\u5e38" + e.getMessage(), e);
            return new ArrayList<BillChangeRecordVO>();
        }
    }
}

