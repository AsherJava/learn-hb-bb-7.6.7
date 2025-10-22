/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateGroupVO
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateNodeVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.conversion.conversionrate.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.conversion.conversionrate.dao.ConversionRateNodeDao;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateNodeEO;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateCurrencyCacheService;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateGroupService;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateNodeService;
import com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateGroupVO;
import com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateNodeVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversionRateNodeServiceImpl
implements ConversionRateNodeService {
    @Autowired
    private ConversionRateNodeDao dao;
    @Autowired
    private ConversionRateGroupService groupService;
    @Autowired
    private ConversionRateCurrencyCacheService currencyCacheService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateNodeVO save(ConversionRateNodeVO vo) {
        if (vo.getGroupId() == null && vo.getPeriodId() == null) {
            throw new BusinessRuntimeException("\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (vo.getGroupName() != null) {
            ConversionRateGroupVO groupVO = this.groupService.get(vo.getPeriodId(), vo.getGroupName(), vo.getSystemId());
            if (groupVO == null) {
                groupVO = this.groupService.save(vo.getPeriodId(), vo.getGroupName(), vo.getSystemId());
            }
            vo.setGroupId(groupVO.getId());
        }
        ConversionRateNodeEO eo = this.convertVO2EO(vo);
        return this.convertEO2VO(this.doSave(eo));
    }

    @Override
    public ConversionRateNodeVO update(ConversionRateNodeVO vo) {
        ConversionRateNodeEO eo = this.convertVO2EO(vo);
        return this.convertEO2VO(this.doUpdate(eo));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateNodeVO delete(String id) {
        ConversionRateNodeEO eo = (ConversionRateNodeEO)this.dao.get((Serializable)((Object)id));
        this.beforeDelete(eo);
        this.dao.delete((BaseEntity)eo);
        return this.convertEO2VO(eo);
    }

    @Override
    public ConversionRateNodeVO get(String id) {
        ConversionRateNodeEO eo = (ConversionRateNodeEO)this.dao.get((Serializable)((Object)id));
        return this.convertEO2VO(eo);
    }

    @Override
    public List<ConversionRateNodeVO> queryAll() {
        return this.convertEO2VO(this.dao.loadAll());
    }

    @Override
    public void beforeSave(ConversionRateNodeEO eo) {
        this.checkEO(eo);
        if (eo.getId() == null) {
            eo.setId(UUIDUtils.newUUIDStr());
        }
        eo.setCreateTime(new Date());
    }

    private void checkEO(ConversionRateNodeEO eo) {
        if (eo.getRateGroupId() == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.rate.group.notnull.error"));
        }
        if (eo.getSourceCurrencyCode() == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.rate.srcCurrency.notnull.error"));
        }
        if (eo.getTargetCurrencyCode() == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.rate.targetCurrency.notnull.error"));
        }
        if (eo.getSourceCurrencyCode().equals(eo.getTargetCurrencyCode())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.rate.srcEqualsTargetCurrency.error"));
        }
        ConversionRateNodeEO oldEO = this.dao.get(eo.getRateGroupId(), eo.getSourceCurrencyCode(), eo.getTargetCurrencyCode());
        if (oldEO != null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.rate.group.repeat.error"));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateNodeEO doSave(ConversionRateNodeEO eo) {
        this.beforeSave(eo);
        this.dao.save(eo);
        return eo;
    }

    @Override
    public void beforeUpdate(ConversionRateNodeEO eo) {
        eo.setUpdateTime(new Date());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionRateNodeEO doUpdate(ConversionRateNodeEO eo) {
        this.beforeUpdate(eo);
        this.dao.update((BaseEntity)eo);
        return eo;
    }

    @Override
    public void beforeDelete(ConversionRateNodeEO eo) {
    }

    @Override
    public ConversionRateNodeEO convertVO2EO(ConversionRateNodeVO vo) {
        ConversionRateNodeEO eo = new ConversionRateNodeEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        eo.setRateGroupId(vo.getGroupId());
        return eo;
    }

    @Override
    public ConversionRateNodeVO convertEO2VO(ConversionRateNodeEO eo) {
        ConversionRateNodeVO vo = new ConversionRateNodeVO();
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setSourceCurrencyTitle(this.currencyCacheService.getCurrencyTitle(vo.getSourceCurrencyCode()));
        vo.setTargetCurrencyTitle(this.currencyCacheService.getCurrencyTitle(vo.getTargetCurrencyCode()));
        vo.setGroupId(eo.getRateGroupId());
        return vo;
    }

    @Override
    private List<ConversionRateNodeVO> convertEO2VO(List<ConversionRateNodeEO> eoList) {
        ArrayList<ConversionRateNodeVO> resultList = null;
        if (eoList != null && eoList.size() > 0) {
            resultList = new ArrayList<ConversionRateNodeVO>();
            for (ConversionRateNodeEO item : eoList) {
                resultList.add(this.convertEO2VO(item));
            }
        }
        return resultList;
    }

    @Override
    public List<ConversionRateNodeVO> queryByGroupid(String groupID) {
        return this.convertEO2VO(this.dao.queryByGroupid(groupID));
    }

    @Override
    public ConversionRateNodeVO query(String groupId, String sourceCurrencyCode, String targetCurrencyCode) {
        ConversionRateNodeEO eo = this.dao.get(groupId, sourceCurrencyCode, targetCurrencyCode);
        if (eo != null) {
            return this.convertEO2VO(eo);
        }
        return null;
    }

    @Override
    public ConversionRateNodeVO save(String groupId, String sourceCurrencyCode, String targetCurrencyCode) {
        ConversionRateNodeVO vo = new ConversionRateNodeVO();
        vo.setGroupId(groupId);
        vo.setSourceCurrencyCode(sourceCurrencyCode);
        vo.setTargetCurrencyCode(targetCurrencyCode);
        return this.save(vo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteByGroupId(String groupId) {
        this.dao.deleteByGroupId(groupId);
    }

    @Override
    public List<ConversionRateNodeVO> queryByIds(List<String> idList) {
        return this.convertEO2VO(this.dao.queryByIds(idList));
    }
}

