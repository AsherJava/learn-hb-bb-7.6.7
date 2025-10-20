/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.billcode.service.impl;

import com.jiuqi.va.billcode.dao.IBillCodeFlowDao;
import com.jiuqi.va.billcode.domain.BillCodeFlowDO;
import com.jiuqi.va.billcode.domain.BillCodeFlowDTO;
import com.jiuqi.va.billcode.service.IBillCodeFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillCodeFlowService
implements IBillCodeFlowService {
    @Autowired
    private IBillCodeFlowDao billCodeFlowDao;

    @Override
    public BillCodeFlowDTO getFlowNumberByDimensions(String dimensions) {
        BillCodeFlowDO billCodeFlowDO = new BillCodeFlowDO();
        billCodeFlowDO.setDimensions(dimensions);
        billCodeFlowDO = (BillCodeFlowDO)((Object)this.billCodeFlowDao.selectOne((Object)billCodeFlowDO));
        if (billCodeFlowDO == null) {
            return null;
        }
        BillCodeFlowDTO codeFlowDTO = new BillCodeFlowDTO();
        codeFlowDTO.setDimensions(dimensions);
        codeFlowDTO.setFlowNumber(billCodeFlowDO.getFlowNumber());
        return codeFlowDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public long updateFlowNumberByDimensions(String dimensions, int n) {
        BillCodeFlowDTO billCodeFlowDTO = new BillCodeFlowDTO();
        billCodeFlowDTO.setDimensions(dimensions);
        billCodeFlowDTO.setFlowNumber(Long.valueOf(n));
        if (this.billCodeFlowDao.updateFlow(billCodeFlowDTO) > 0) {
            BillCodeFlowDTO flowDTO = this.getFlowNumberByDimensions(dimensions);
            return flowDTO.getFlowNumber();
        }
        BillCodeFlowDTO codeFlowDTO = new BillCodeFlowDTO();
        codeFlowDTO.setDimensions(dimensions);
        codeFlowDTO.setFlowNumber(Long.valueOf(n));
        this.addFlowNumber(codeFlowDTO);
        return n;
    }

    @Override
    public long reUpdateFlowNumberByDimensions(String dimensions, int n) {
        BillCodeFlowDTO billCodeFlowDTO = new BillCodeFlowDTO();
        billCodeFlowDTO.setDimensions(dimensions);
        billCodeFlowDTO.setFlowNumber(Long.valueOf(n));
        if (this.billCodeFlowDao.updateFlow(billCodeFlowDTO) == 1) {
            BillCodeFlowDTO flowDTO = this.getFlowNumberByDimensions(dimensions);
            return flowDTO.getFlowNumber();
        }
        throw new RuntimeException("\u5e76\u53d1\u66f4\u65b0\u5355\u636e\u7f16\u53f7\u5931\u8d25");
    }

    @Override
    public BillCodeFlowDTO addFlowNumber(BillCodeFlowDTO flowDTO) {
        this.billCodeFlowDao.insert((Object)flowDTO);
        return flowDTO;
    }
}

