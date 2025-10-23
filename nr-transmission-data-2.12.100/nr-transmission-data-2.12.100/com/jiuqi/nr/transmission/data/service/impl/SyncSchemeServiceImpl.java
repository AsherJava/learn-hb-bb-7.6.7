/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.dao.ISyncSchemeDao;
import com.jiuqi.nr.transmission.data.dao.ISyncSchemeParamDao;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeDO;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeGroupServiceException;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SyncSchemeServiceImpl
implements ISyncSchemeService {
    @Autowired
    private ISyncSchemeDao syncSchemeDao;
    @Autowired
    private ISyncSchemeParamDao syncSchemeParamDao;

    @Override
    public List<SyncSchemeDTO> list(SyncSchemeDTO schemeDTO) {
        List<SyncSchemeDTO> syncSchemeDTOs;
        Map<Object, Object> map = new HashMap();
        if (StringUtils.hasText(schemeDTO.getGroup()) && !"00000000-0000-0000-0000-000000000000".equals(schemeDTO.getGroup())) {
            List<SyncSchemeDO> syncSchemeDOs = this.syncSchemeDao.listByGroup(schemeDTO.getGroup());
            syncSchemeDTOs = SyncSchemeDTO.toListInstance(syncSchemeDOs);
            List<String> collect = syncSchemeDOs.stream().map(SyncSchemeDO::getKey).collect(Collectors.toList());
            List<String> schemeKeys = collect;
            if (!CollectionUtils.isEmpty(schemeKeys)) {
                List<SyncSchemeParamDO> syncSchemeParamDOs = this.syncSchemeParamDao.listByGroup(schemeKeys);
                map = syncSchemeParamDOs.stream().collect(Collectors.toMap(SyncSchemeParamDO::getSchemeKey, a -> a, (k1, k2) -> k1));
                for (SyncSchemeDTO syncSchemeDTO : syncSchemeDTOs) {
                    syncSchemeDTO.setSchemeParam((SyncSchemeParamDO)map.get(syncSchemeDTO.getKey()));
                }
            }
        } else {
            List<SyncSchemeDO> syncSchemeDOs = this.syncSchemeDao.list();
            syncSchemeDTOs = SyncSchemeDTO.toListInstance(syncSchemeDOs);
            List collect = syncSchemeDOs.stream().map(SyncSchemeDO::getKey).collect(Collectors.toList());
            List schemeKeys = collect;
            if (!CollectionUtils.isEmpty(schemeKeys)) {
                List<SyncSchemeParamDO> syncSchemeParamDOs = this.syncSchemeParamDao.list();
                map = syncSchemeParamDOs.stream().collect(Collectors.toMap(SyncSchemeParamDO::getSchemeKey, a -> a, (k1, k2) -> k1));
                for (SyncSchemeDTO syncSchemeDTO : syncSchemeDTOs) {
                    syncSchemeDTO.setSchemeParam((SyncSchemeParamDO)map.get(syncSchemeDTO.getKey()));
                }
            }
        }
        Collections.sort(syncSchemeDTOs, (g1, g2) -> Utils.getChineseComparator().compare(g1.getTitle(), g2.getTitle()));
        return syncSchemeDTOs;
    }

    @Override
    public List<SyncSchemeDTO> listWithOutParam() {
        List<SyncSchemeDO> syncSchemeDOs = this.syncSchemeDao.list();
        List<SyncSchemeDTO> syncSchemeDTOS = SyncSchemeDTO.toListInstance(syncSchemeDOs);
        Collections.sort(syncSchemeDTOS, (g1, g2) -> Utils.getChineseComparator().compare(g1.getTitle(), g2.getTitle()));
        return syncSchemeDTOS;
    }

    @Override
    public SyncSchemeDTO get(String schemeKey) {
        SyncSchemeDO syncSchemeDO = this.syncSchemeDao.get(schemeKey);
        SyncSchemeDTO syncSchemeDTO = SyncSchemeDTO.getInstance(syncSchemeDO);
        SyncSchemeParamDO syncSchemeParamDOS = this.syncSchemeParamDao.get(schemeKey);
        syncSchemeDTO.setSchemeParam(syncSchemeParamDOS);
        return syncSchemeDTO;
    }

    @Override
    public SyncSchemeDTO getWithOutParam(String schemeKey) {
        SyncSchemeDO syncSchemeDO = this.syncSchemeDao.get(schemeKey);
        SyncSchemeDTO syncSchemeDTO = SyncSchemeDTO.getInstance(syncSchemeDO);
        return syncSchemeDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean insert(SyncSchemeDTO schemeDTO) {
        schemeDTO.setKey(UUIDUtils.getKey());
        if (!StringUtils.hasText(schemeDTO.getTitle())) {
            throw new SchemeGroupServiceException("\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        schemeDTO.setTitle(schemeDTO.getTitle().trim());
        schemeDTO.setUpdataTime(new Date());
        schemeDTO.setOrder(OrderGenerator.newOrder());
        SyncSchemeParamDO schemeParam = schemeDTO.getSchemeParam();
        schemeParam.setKey(UUIDUtils.getKey());
        schemeParam.setSchemeKey(schemeDTO.getKey());
        int result = this.syncSchemeDao.add(schemeDTO);
        if (schemeParam.getIsUpload() == 0) {
            schemeParam.setAllowForceUpload(0);
        }
        int ints = this.syncSchemeParamDao.add(schemeParam);
        return result == 1;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean delete(String schemeKey) {
        Assert.notNull((Object)schemeKey, "\u5220\u9664\u5206\u7ec4\u65f6\u5019\u5206\u7ec4\u5173\u952e\u5b57\u4e3anull");
        int result = this.syncSchemeDao.delete(schemeKey);
        int delete = this.syncSchemeParamDao.delete(schemeKey);
        return result == 1;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean deletes(String id, String field) {
        return false;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean update(SyncSchemeDTO schemeDTO) {
        if (!StringUtils.hasText(schemeDTO.getCode())) {
            throw new SchemeGroupServiceException("\u65b9\u6848\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        schemeDTO.setTitle(schemeDTO.getTitle().trim());
        schemeDTO.setUpdataTime(new Date());
        int result = this.syncSchemeDao.update(schemeDTO);
        SyncSchemeParamDO schemeParam = schemeDTO.getSchemeParam();
        if (schemeParam.getIsUpload() == 0) {
            schemeParam.setAllowForceUpload(0);
        }
        if (StringUtils.hasText(schemeParam.getKey())) {
            this.syncSchemeParamDao.update(schemeParam);
        } else {
            schemeParam.setKey(UUIDUtils.getKey());
            schemeParam.setSchemeKey(schemeDTO.getKey());
            this.syncSchemeParamDao.add(schemeParam);
        }
        return result == 1;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean updates(String oldStr, String newStr, String field) {
        int result = this.syncSchemeDao.updates(oldStr, newStr, field);
        return result == 1;
    }
}

