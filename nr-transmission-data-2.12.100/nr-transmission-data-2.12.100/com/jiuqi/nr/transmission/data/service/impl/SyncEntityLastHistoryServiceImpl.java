/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.nr.transmission.data.dao.ISyncEntityLastHistoryDao;
import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import com.jiuqi.nr.transmission.data.dto.SyncEntityLastHistoryDTO;
import com.jiuqi.nr.transmission.data.service.ISyncEntityLastHistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncEntityLastHistoryServiceImpl
implements ISyncEntityLastHistoryService {
    @Autowired
    private ISyncEntityLastHistoryDao syncEntityLastHistoryDao;

    @Override
    public boolean add(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        int add = this.syncEntityLastHistoryDao.add(syncEntityLastHistoryDO);
        return add == 1;
    }

    @Override
    public int[] betchAdd(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        int[] ints = this.syncEntityLastHistoryDao.betchAdd(syncEntityLastHistoryDOS);
        return ints;
    }

    @Override
    public int delete(SyncEntityLastHistoryDO syncEntityLastHistoryDOS) {
        int delete = this.syncEntityLastHistoryDao.delete(syncEntityLastHistoryDOS.getKey());
        return delete;
    }

    @Override
    public int[] betchdeletes(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        int[] ints = this.syncEntityLastHistoryDao.betchdeletes(syncEntityLastHistoryDOS);
        return ints;
    }

    @Override
    public SyncEntityLastHistoryDTO get(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        SyncEntityLastHistoryDO syncEntityLastHistoryDO1 = this.syncEntityLastHistoryDao.get(syncEntityLastHistoryDO);
        return SyncEntityLastHistoryDTO.getInstance(syncEntityLastHistoryDO1);
    }

    @Override
    public List<SyncEntityLastHistoryDTO> list(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        List<SyncEntityLastHistoryDO> list = this.syncEntityLastHistoryDao.list(syncEntityLastHistoryDO);
        return SyncEntityLastHistoryDTO.toListInstance(list);
    }

    @Override
    public List<SyncEntityLastHistoryDTO> lists(SyncEntityLastHistoryDO syncEntityLastHistoryDO, List<String> entitys) {
        List<SyncEntityLastHistoryDO> list = this.syncEntityLastHistoryDao.lists(syncEntityLastHistoryDO, entitys);
        return SyncEntityLastHistoryDTO.toListInstance(list);
    }

    @Override
    public int[] batchUpdateWithForm(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        int[] ints = this.syncEntityLastHistoryDao.batchUpdateWithForm(syncEntityLastHistoryDOS);
        return ints;
    }

    @Override
    public int[] batchUpdateWithEntity(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        int[] ints = this.syncEntityLastHistoryDao.batchUpdateWithEntity(syncEntityLastHistoryDOS);
        return ints;
    }
}

