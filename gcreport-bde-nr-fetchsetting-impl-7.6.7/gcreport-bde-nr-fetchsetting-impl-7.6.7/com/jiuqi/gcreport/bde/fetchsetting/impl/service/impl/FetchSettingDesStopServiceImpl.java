/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesStopService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchSettingDesStopServiceImpl
implements FetchSettingDesStopService {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private FetchFloatSettingDao fetchFloatSettingDao;

    @Override
    public void disableFetchSetting(FetchSettingDesStopDTO stopDTO) {
        Assert.isNotEmpty((String)stopDTO.getFormSchemeId());
        Assert.isNotEmpty((String)stopDTO.getFetchSchemeId());
        Assert.isNotEmpty((String)stopDTO.getFormId());
        Assert.isNotEmpty((Map)stopDTO.getRegionLinkIds());
        Map regionLinkIds = stopDTO.getRegionLinkIds();
        for (Map.Entry regionEntity : regionLinkIds.entrySet()) {
            String regionKey = (String)regionEntity.getKey();
            FetchSettingCond condition = new FetchSettingCond(stopDTO.getFetchSchemeId(), stopDTO.getFormSchemeId(), stopDTO.getFormId(), regionKey);
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
            boolean isFixArea = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind());
            if (isFixArea) {
                this.updateFixRegionDesStopFlag(condition, (List)regionEntity.getValue(), true);
                continue;
            }
            this.updateFloatRegionDesStopFlag(condition, (List)regionEntity.getValue(), true);
        }
    }

    private void updateFixRegionDesStopFlag(FetchSettingCond condition, List<String> linkIds, boolean isDisAble) {
        Map linkIdEnable = this.fetchSettingDesDao.getFixSettingDesStopFlag(condition, linkIds);
        linkIds.removeIf(linkId -> !linkIdEnable.containsKey(linkId) || (isDisAble ? 0 : 1) != (Integer)linkIdEnable.get(linkId));
        this.updateFixSettingDesStopFlag(condition, linkIds, isDisAble);
    }

    private void updateFloatRegionDesStopFlag(FetchSettingCond condition, List<String> linkIds, boolean isDisAble) {
        FloatRegionConfigVO fetchFloatSettingDesWithStopState = this.getFloatRegionConfigVODesWithStopState(condition);
        if (Objects.isNull(fetchFloatSettingDesWithStopState)) {
            throw new BusinessRuntimeException((isDisAble ? "\u505c\u7528" : "\u542f\u7528") + "\u53d6\u6570\u8bbe\u7f6e\u5931\u8d25\uff0c\u672a\u67e5\u8be2\u5230\u8be5\u6d6e\u52a8\u533a\u57df\u7684\u53d6\u6570\u8bbe\u7f6e");
        }
        QueryConfigInfo queryConfigInfo = fetchFloatSettingDesWithStopState.getQueryConfigInfo();
        List zbMappings = queryConfigInfo.getZbMapping();
        ArrayList<String> fixLinkId = new ArrayList<String>();
        for (FloatZbMappingVO zbMappingVO : zbMappings) {
            boolean curDisAble;
            boolean bl = curDisAble = !Objects.isNull(zbMappingVO.getStopFlag()) && 1 == zbMappingVO.getStopFlag();
            if (!linkIds.contains(zbMappingVO.getDataLinkId()) || isDisAble == curDisAble) continue;
            zbMappingVO.setStopFlag(Integer.valueOf(isDisAble ? 1 : 0));
            if (!"=".equals(zbMappingVO.getQueryName())) continue;
            fixLinkId.add(zbMappingVO.getDataLinkId());
        }
        this.fetchFloatSettingDesDao.updateFloatRegionConfigVOStopFlag(JsonUtils.writeValueAsString((Object)queryConfigInfo), fetchFloatSettingDesWithStopState.getId());
        if (!CollectionUtils.isEmpty(fixLinkId)) {
            this.updateFixSettingDesStopFlag(condition, fixLinkId, isDisAble);
        }
    }

    private void updateFixSettingDesStopFlag(FetchSettingCond condition, List<String> linkIds, boolean isDisAble) {
        if (isDisAble) {
            this.fetchSettingDesDao.disableFixSetting(condition, linkIds);
        } else {
            this.fetchSettingDesDao.enableFixSetting(condition, linkIds);
        }
    }

    @Override
    public void enableFetchSetting(FetchSettingDesStopDTO enableDTO) {
        Assert.isNotEmpty((String)enableDTO.getFormSchemeId());
        Assert.isNotEmpty((String)enableDTO.getFetchSchemeId());
        Assert.isNotEmpty((String)enableDTO.getFormId());
        Assert.isNotEmpty((Map)enableDTO.getRegionLinkIds());
        Map regionLinkIds = enableDTO.getRegionLinkIds();
        for (Map.Entry regionEntity : regionLinkIds.entrySet()) {
            String regionKey = (String)regionEntity.getKey();
            FetchSettingCond condition = new FetchSettingCond(enableDTO.getFetchSchemeId(), enableDTO.getFormSchemeId(), enableDTO.getFormId(), regionKey);
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(regionKey);
            boolean isFixArea = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind());
            if (isFixArea) {
                this.updateFixRegionDesStopFlag(condition, (List)regionEntity.getValue(), false);
                continue;
            }
            this.updateFloatRegionDesStopFlag(condition, (List)regionEntity.getValue(), false);
        }
    }

    private FloatRegionConfigVO convertFloatSettingDesEoToVoWithState(FetchFloatSettingDesEO fetchFloatSettingDes) {
        if (fetchFloatSettingDes == null) {
            return null;
        }
        FloatRegionConfigVO fetchFloatSetting = new FloatRegionConfigVO();
        BeanUtils.copyProperties(fetchFloatSettingDes, fetchFloatSetting);
        if (!StringUtils.isEmpty((String)fetchFloatSettingDes.getQueryConfigInfo())) {
            fetchFloatSetting.setQueryConfigInfo((QueryConfigInfo)JSONUtil.parseObject((String)fetchFloatSettingDes.getQueryConfigInfo(), QueryConfigInfo.class));
        }
        return fetchFloatSetting;
    }

    private FloatRegionConfigVO convertFloatSettingEoToVoWithState(FetchFloatSettingEO fetchFloatSetting) {
        if (fetchFloatSetting == null) {
            return null;
        }
        FloatRegionConfigVO floatRegionConfigVO = new FloatRegionConfigVO();
        if (!StringUtils.isEmpty((String)fetchFloatSetting.getQueryConfigInfo())) {
            floatRegionConfigVO.setQueryConfigInfo((QueryConfigInfo)JSONUtil.parseObject((String)fetchFloatSetting.getQueryConfigInfo(), QueryConfigInfo.class));
        }
        return floatRegionConfigVO;
    }

    @Override
    public FloatRegionConfigVO getFloatRegionConfigVODesWithStopState(FetchSettingCond fetchSettingCond) {
        Assert.isNotEmpty((String)fetchSettingCond.getFormSchemeId(), (String)"\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getFetchSchemeId(), (String)"\u53d6\u6570\u65b9\u6848\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getFormId(), (String)"\u62a5\u8868\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchSettingCond.getRegionId(), (String)"\u6d6e\u52a8\u533a\u57df\u4e3a\u7a7a", (Object[])new Object[0]);
        FetchFloatSettingDesEO fetchFloatSettingDesEOS = this.fetchFloatSettingDesDao.listFloatSettingDesByRegionId(fetchSettingCond);
        if (Objects.isNull(fetchFloatSettingDesEOS)) {
            return null;
        }
        return this.convertFloatSettingDesEoToVoWithState(fetchFloatSettingDesEOS);
    }

    @Override
    public FloatRegionConfigVO getFloatRegionConfigVO(FetchSettingCond fetchSettingCond) {
        FetchFloatSettingEO fetchFloatSettingDes = this.fetchFloatSettingDao.getFetchFloatSetting(fetchSettingCond);
        return this.convertFloatSettingEoToVoWithState(fetchFloatSettingDes);
    }
}

