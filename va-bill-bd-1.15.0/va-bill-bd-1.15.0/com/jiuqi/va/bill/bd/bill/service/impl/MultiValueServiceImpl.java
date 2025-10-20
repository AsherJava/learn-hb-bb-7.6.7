/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.bd.bill.service.impl;

import com.jiuqi.va.bill.bd.bill.dao.MultiValueDao;
import com.jiuqi.va.bill.bd.bill.domain.MultiValueDTO;
import com.jiuqi.va.bill.bd.bill.service.MultiValueService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiValueServiceImpl
implements MultiValueService {
    @Autowired
    private MultiValueDao multiValueDao;

    @Override
    public List<MultiValueDTO> getMultiValue(MultiValueDTO requrstDTO) {
        return this.multiValueDao.getMultiValue(requrstDTO);
    }
}

