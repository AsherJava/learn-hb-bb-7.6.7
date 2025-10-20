/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.EnumDataTransUtil;
import com.jiuqi.va.basedata.dao.VaEnumDataDao;
import com.jiuqi.va.basedata.domain.EnumDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.EnumDataService;
import com.jiuqi.va.basedata.service.impl.help.EnumDataCacheService;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaEnumDataServiceImpl")
public class EnumDataServiceImpl
implements EnumDataService {
    @Autowired
    private VaEnumDataDao enumDataDao;
    @Autowired
    private EnumDataCacheService dataCacheService;
    private EnumDataTransUtil transUtil;

    @Override
    public EnumDataDO get(EnumDataDTO param) {
        List<EnumDataDO> list;
        if (param.getBiztype() != null && param.getVal() != null && (list = this.list(param)).size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<EnumDataDO> list(EnumDataDTO param) {
        List<EnumDataDO> list;
        boolean languageTransFlag;
        Object languageTrans = param.getExtInfo("languageTransFlag");
        boolean bl = languageTransFlag = VaI18nParamUtils.getTranslationEnabled() != false && languageTrans != null && (Boolean)languageTrans != false;
        if (languageTransFlag) {
            param.setDeepClone(Boolean.valueOf(true));
        }
        if ((list = this.dataCacheService.list(param)).isEmpty()) {
            return list;
        }
        String searchKey = param.getSearchKey();
        Iterator<EnumDataDO> iterator = list.iterator();
        while (iterator.hasNext()) {
            EnumDataDO curr = iterator.next();
            if (StringUtils.hasText(searchKey) && !curr.getTitle().contains(searchKey) && !curr.getVal().contains(searchKey)) {
                iterator.remove();
                continue;
            }
            if (param.getStatus() == null || param.getStatus().equals(curr.getStatus())) continue;
            iterator.remove();
        }
        if (list.size() > 1) {
            Collections.sort(list, (o1, o2) -> {
                if (o1.getOrdernum() == null) {
                    return -1;
                }
                if (o2.getOrdernum() == null) {
                    return 1;
                }
                return o1.getOrdernum().compareTo(o2.getOrdernum());
            });
        }
        if (languageTransFlag) {
            if (this.transUtil == null) {
                this.transUtil = (EnumDataTransUtil)ApplicationContextRegister.getBean(EnumDataTransUtil.class);
            }
            this.transUtil.transDatss(list);
        }
        return list;
    }

    @Override
    public int count(EnumDataDTO param) {
        return this.list(param).size();
    }

    @Override
    public R add(EnumDataDO enumDataDO) {
        EnumDataDTO param = new EnumDataDTO();
        param.setVal(enumDataDO.getVal());
        param.setBiztype(enumDataDO.getBiztype());
        EnumDataDO old = this.get(param);
        if (old != null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("enum.error.value.exist", new Object[0]));
        }
        enumDataDO.setId(UUID.randomUUID());
        if (enumDataDO.getOrdernum() == null) {
            enumDataDO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        enumDataDO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        int cnt = this.enumDataDao.insert(enumDataDO);
        if (cnt > 0) {
            this.updateCache(enumDataDO, false);
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        return R.error();
    }

    @Override
    public R update(EnumDataDO enumDataDO) {
        EnumDataDTO param = new EnumDataDTO();
        param.setVal(enumDataDO.getVal());
        param.setBiztype(enumDataDO.getBiztype());
        EnumDataDO old = this.get(param);
        if (old == null && enumDataDO.getId() == null) {
            return this.add(enumDataDO);
        }
        if (old != null && !old.getId().equals(enumDataDO.getId())) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("enum.error.value.exists", new Object[0]));
        }
        if (old == null) {
            param.setVal(null);
            param.setId(enumDataDO.getId());
            old = (EnumDataDO)this.enumDataDao.selectOne(param);
        }
        if (old == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.not.exist", new Object[0]));
        }
        if (enumDataDO.getVal() != null && !old.getVal().equals(enumDataDO.getVal())) {
            this.updateCache(old, true);
        }
        enumDataDO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        int cnt = this.enumDataDao.updateByPrimaryKeySelective(enumDataDO);
        if (cnt > 0) {
            this.updateCache(enumDataDO, false);
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    private void updateCache(EnumDataDO enumDataDO, boolean isRemove) {
        if (enumDataDO.getBiztype() == null || enumDataDO.getVal() == null) {
            enumDataDO.setVer(null);
            enumDataDO = (EnumDataDO)this.enumDataDao.selectOne(enumDataDO);
        }
        EnumDataSyncCacheDTO edsc = new EnumDataSyncCacheDTO();
        edsc.setTenantName(enumDataDO.getTenantName());
        edsc.setEnumDataDO(enumDataDO);
        edsc.setRemove(isRemove);
        this.dataCacheService.pushSyncMsg(edsc);
    }

    @Override
    public int remove(List<EnumDataDTO> objs) {
        if (objs == null || objs.isEmpty()) {
            return 1;
        }
        for (EnumDataDTO data : objs) {
            if (!StringUtils.hasText(data.getBiztype()) || !StringUtils.hasText(data.getVal()) || this.enumDataDao.delete(data) <= 0) continue;
            this.updateCache((EnumDataDO)data, true);
        }
        return 1;
    }

    @Override
    public List<EnumDataDO> listBiztype(EnumDataDTO param) {
        List<EnumDataDO> typeList = this.enumDataDao.listBiztype(param);
        if (typeList == null || typeList.isEmpty()) {
            return typeList;
        }
        String searchKey = param.getSearchKey();
        if (StringUtils.hasText(searchKey)) {
            Iterator<EnumDataDO> iterator = typeList.iterator();
            while (iterator.hasNext()) {
                EnumDataDO curr = iterator.next();
                if (curr.getBiztype().contains(searchKey) || curr.getDescription().contains(searchKey)) continue;
                iterator.remove();
            }
        }
        return typeList;
    }
}

