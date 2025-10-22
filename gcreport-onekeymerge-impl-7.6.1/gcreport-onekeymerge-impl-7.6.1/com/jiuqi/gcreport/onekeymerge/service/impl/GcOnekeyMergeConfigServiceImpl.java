/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.onekeymerge.dao.OnekeyMergeConfigDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeConfigEO;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeConfigService;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcOnekeyMergeConfigServiceImpl
implements GcOnekeyMergeConfigService {
    @Autowired
    private OnekeyMergeConfigDao configDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveConfig(GcConfigVO configVO) {
        List gcOnekeyMergeConfigEOS = this.configDao.loadAll();
        List collect = gcOnekeyMergeConfigEOS.stream().filter(gcOnekeyMergeConfigEO -> gcOnekeyMergeConfigEO.getConfigName().equalsIgnoreCase(configVO.getConfigName())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            throw new BusinessRuntimeException("\u5b58\u5728\u76f8\u540c\u7684\u540d\u79f0");
        }
        GcOnekeyMergeConfigEO eo = new GcOnekeyMergeConfigEO();
        BeanUtils.copyProperties(configVO, (Object)eo);
        eo.setUserId(OneKeyMergeUtils.getUser().getId());
        eo.setId(UUIDUtils.newUUIDStr());
        eo.setRuleIds(JsonUtils.writeValueAsString((Object)configVO.getRuleIds()));
        eo.setOrgIds(JsonUtils.writeValueAsString((Object)configVO.getOrgIds()));
        MergeTypeEnum mergeTypeEnum = MergeTypeEnum.valueOf((String)configVO.getMergeType());
        Assert.isNotNull((Object)mergeTypeEnum, (String)"\u5408\u5e76\u65b9\u5f0f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        eo.setMergeType(MergeTypeEnum.valueOf((String)configVO.getMergeType()).getCode());
        this.configDao.save(eo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteConfig(String id) {
        GcOnekeyMergeConfigEO eo = new GcOnekeyMergeConfigEO();
        eo.setId(id);
        this.configDao.delete((BaseEntity)eo);
    }

    @Override
    public void updateConfig(GcConfigVO configVO) {
        GcOnekeyMergeConfigEO eo = new GcOnekeyMergeConfigEO();
        BeanUtils.copyProperties(configVO, (Object)eo);
        eo.setRuleIds(JsonUtils.writeValueAsString((Object)configVO.getRuleIds()));
        eo.setOrgIds(JsonUtils.writeValueAsString((Object)configVO.getOrgIds()));
        eo.setUserId(OneKeyMergeUtils.getUser().getId());
        this.configDao.update((BaseEntity)eo);
    }

    @Override
    public List<GcConfigVO> getConfigByUser() {
        String userID = OneKeyMergeUtils.getUser().getId();
        List configEOS = this.configDao.loadAll();
        return configEOS.stream().filter(configEO -> configEO.getUserId().equals(userID)).map(configEO -> {
            GcConfigVO vo = new GcConfigVO();
            BeanUtils.copyProperties(configEO, vo);
            vo.setRuleIds((List)JsonUtils.readValue((String)configEO.getRuleIds(), (TypeReference)new TypeReference<List<String>>(){}));
            vo.setOrgIds((List)JsonUtils.readValue((String)configEO.getOrgIds(), (TypeReference)new TypeReference<List<String>>(){}));
            return vo;
        }).collect(Collectors.toList());
    }
}

