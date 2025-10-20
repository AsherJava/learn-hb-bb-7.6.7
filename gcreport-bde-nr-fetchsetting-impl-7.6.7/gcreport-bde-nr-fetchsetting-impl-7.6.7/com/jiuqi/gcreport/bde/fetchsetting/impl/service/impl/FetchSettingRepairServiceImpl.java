/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingRepairDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingRepairService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSettingRepairServiceImpl
implements FetchSettingRepairService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchSettingRepairServiceImpl.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchSettingDesService fetchSettingDesService;
    @Autowired
    private FetchFloatSettingService floatSettingService;
    @Autowired
    private FetchFloatSettingDesService floatSettingDesService;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private FetchFloatSettingDao fetchFloatSettingDao;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private FetchSettingDao fetchSettingDao;

    @Override
    @Async
    @Transactional(rollbackFor={Exception.class})
    public void doRepair(String username, DeployParams deployParams) {
        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5f00\u59cb\u6267\u884c\u53d6\u6570\u8bbe\u7f6e\u4fee\u590d\u903b\u8f91============================");
        try {
            Thread.sleep(15000L);
        }
        catch (InterruptedException e) {
            LOGGER.error("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u53d6\u6570\u8bbe\u7f6e\u7b49\u5f85\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u9519\u8bef============================");
            Thread.currentThread().interrupt();
            return;
        }
        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5f00\u59cb\u6a21\u62df\u767b\u5f55......");
        BdeCommonUtil.initNpUser((String)username);
        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5b8c\u6210\u6a21\u62df\u767b\u5f55......");
        if (!CollectionUtils.isEmpty((Collection)deployParams.getTaskDefine().getRunTimeKeys())) {
            Set taskKeys = deployParams.getTaskDefine().getRunTimeKeys();
            TaskDefine taskDefine = null;
            for (String taskKey : taskKeys) {
                List runtimeSchemes;
                try {
                    taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
                }
                catch (Exception e) {
                    LOGGER.error("\u6839\u636e\u4efb\u52a1\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u4efb\u52a1\u51fa\u73b0\u9519\u8bef", (Object)taskKey, (Object)e);
                    continue;
                }
                if (!taskDefine.getEfdcSwitch()) continue;
                try {
                    runtimeSchemes = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
                }
                catch (Exception e) {
                    LOGGER.error("\u6839\u636e\u4efb\u52a1\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u51fa\u73b0\u9519\u8bef", (Object)taskKey, (Object)e);
                    continue;
                }
                for (FormSchemeDefine formSchemeDefine : runtimeSchemes) {
                    List formDefines;
                    List<FetchSchemeVO> fetchSchemeList = this.fetchSchemeService.listFetchScheme(formSchemeDefine.getKey());
                    if (CollectionUtils.isEmpty(fetchSchemeList)) continue;
                    try {
                        formDefines = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
                    }
                    catch (Exception e) {
                        LOGGER.error("\u6839\u636e\u62a5\u8868\u65b9\u6848\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u51fa\u73b0\u9519\u8bef", (Object)formSchemeDefine.getKey(), (Object)e);
                        continue;
                    }
                    HashSet<String> fetchSchemeIdSet = new HashSet<String>();
                    for (FormDefine formDefine : formDefines) {
                        fetchSchemeIdSet.addAll(((FetchSettingRepairServiceImpl)ApplicationContextRegister.getBean(FetchSettingRepairServiceImpl.class)).doFetchSettingRepair(taskDefine, formSchemeDefine, formDefine));
                    }
                    List<String> formIdList = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    for (String fetchSchemeId : fetchSchemeIdSet) {
                        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5f00\u59cb\u91cd\u65b0\u6267\u884c\u7f13\u5b58\u6e05\u9664......");
                        this.fetchSettingService.fetchSettingCacheEvictInFetchScheme(fetchSchemeId, formIdList);
                        this.floatSettingService.fetchFloatSettingCacheEvit(fetchSchemeId);
                        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5b8c\u6210\u91cd\u65b0\u6267\u884c\u7f13\u5b58\u6e05\u9664......");
                    }
                }
            }
        } else if (!CollectionUtils.isEmpty((Collection)deployParams.getFormScheme().getDesignTimeKeys())) {
            FormSchemeDefine formSchemeDefine = null;
            TaskDefine taskDefine = null;
            for (String formSchemeDefineKey : deployParams.getFormScheme().getDesignTimeKeys()) {
                List formDefines;
                List<FetchSchemeVO> fetchSchemeList;
                try {
                    formSchemeDefine = this.runTimeAuthViewController.getFormScheme(formSchemeDefineKey);
                }
                catch (Exception e) {
                    LOGGER.error("\u6839\u636e\u62a5\u8868\u65b9\u6848\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u51fa\u73b0\u9519\u8bef", (Object)formSchemeDefineKey, (Object)e);
                    continue;
                }
                if (formSchemeDefine == null) {
                    LOGGER.error("\u6839\u636e\u62a5\u8868\u65b9\u6848\u6807\u8bc6{}\u6ca1\u6709\u83b7\u53d6\u5230\u62a5\u8868\u65b9\u6848", (Object)formSchemeDefineKey);
                    continue;
                }
                try {
                    taskDefine = this.runTimeAuthViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
                }
                catch (Exception e) {
                    LOGGER.error("\u6839\u636e\u4efb\u52a1\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u4efb\u52a1\u51fa\u73b0\u9519\u8bef", (Object)formSchemeDefine.getTaskKey(), (Object)e);
                    continue;
                }
                if (!taskDefine.getEfdcSwitch() || CollectionUtils.isEmpty(fetchSchemeList = this.fetchSchemeService.listFetchScheme(formSchemeDefine.getKey()))) continue;
                try {
                    formDefines = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
                }
                catch (Exception e) {
                    LOGGER.error("\u6839\u636e\u62a5\u8868\u65b9\u6848\u6807\u8bc6{}\u83b7\u53d6\u62a5\u8868\u51fa\u73b0\u9519\u8bef", (Object)formSchemeDefine.getKey(), (Object)e);
                    continue;
                }
                HashSet<String> fetchSchemeIdSet = new HashSet<String>();
                for (FormDefine formDefine : formDefines) {
                    fetchSchemeIdSet.addAll(((FetchSettingRepairServiceImpl)ApplicationContextRegister.getBean(FetchSettingRepairServiceImpl.class)).doFetchSettingRepair(taskDefine, formSchemeDefine, formDefine));
                }
                List<String> formIdList = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                for (String fetchSchemeId : fetchSchemeIdSet) {
                    LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5f00\u59cb\u91cd\u65b0\u6267\u884c\u7f13\u5b58\u6e05\u9664......");
                    this.fetchSettingService.fetchSettingCacheEvictInFetchScheme(fetchSchemeId, formIdList);
                    this.floatSettingService.fetchFloatSettingCacheEvit(fetchSchemeId);
                    LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u5b8c\u6210\u91cd\u65b0\u6267\u884c\u7f13\u5b58\u6e05\u9664......");
                }
            }
        }
        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1\u7ed3\u675f\u6267\u884c\u53d6\u6570\u8bbe\u7f6e\u4fee\u590d\u903b\u8f91============================");
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public Set<String> doFetchSettingRepair(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, FormDefine formDefine) {
        FetchSettingRepairDTO repairDto = new FetchSettingRepairDTO();
        String formSchemeKey = formSchemeDefine.getKey();
        Assert.isNotEmpty((String)formSchemeKey);
        String formKey = formDefine.getKey();
        Assert.isNotEmpty((String)formKey);
        List allLinksInForm = this.runTimeAuthViewController.getAllLinksInForm(formKey);
        HashMap fieldDefineMap = new HashMap();
        allLinksInForm.forEach(dataLink -> {
            if (!StringUtils.isEmpty((String)dataLink.getLinkExpression())) {
                fieldDefineMap.put(dataLink.getLinkExpression(), dataLink.getKey());
            }
        });
        Set regionSet = this.runTimeAuthViewController.getAllRegionsInForm(formKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        Set dataLinkSet = allLinksInForm.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        Set fieldDefineSet = this.runTimeAuthViewController.getFieldKeysInForm(formKey).stream().collect(Collectors.toSet());
        LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u83b7\u53d6\u5230\u7684\u533a\u57df{}\u3001\u94fe\u63a5{}\u3001\u6307\u6807{}\u4fe1\u606f......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle(), regionSet, dataLinkSet, fieldDefineSet);
        if (CollectionUtils.isEmpty(regionSet) || CollectionUtils.isEmpty(dataLinkSet) || CollectionUtils.isEmpty(fieldDefineSet)) {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u56e0\u4e3a\u83b7\u53d6\u5230\u7684\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u4fe1\u606f\u4e3a\u7a7a\uff0c\u81ea\u52a8\u8df3\u8fc7......");
            return CollectionUtils.newHashSet();
        }
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(formSchemeKey);
        condi.setFormId(formKey);
        List<FloatRegionConfigVO> floatSettingList = this.floatSettingDesService.listFloatSettingDesByCondi(condi);
        for (FloatRegionConfigVO fetchFloatSetting : floatSettingList) {
            if (!regionSet.contains(fetchFloatSetting.getRegionId())) {
                repairDto.addDeleteFloatSetting(fetchFloatSetting);
                continue;
            }
            if (fetchFloatSetting.getQueryConfigInfo() == null || CollectionUtils.isEmpty((Collection)fetchFloatSetting.getQueryConfigInfo().getZbMapping())) {
                repairDto.addDeleteFloatSetting(fetchFloatSetting);
                continue;
            }
            ArrayList<FloatZbMappingVO> zbMappingList = new ArrayList<FloatZbMappingVO>(fetchFloatSetting.getQueryConfigInfo().getZbMapping().size());
            boolean needUpdate = false;
            for (FloatZbMappingVO floatZbMapping : fetchFloatSetting.getQueryConfigInfo().getZbMapping()) {
                if (!fieldDefineSet.contains(floatZbMapping.getFieldDefineId())) {
                    needUpdate = true;
                    continue;
                }
                if (!dataLinkSet.contains(floatZbMapping.getDataLinkId())) {
                    needUpdate = true;
                    if (!fieldDefineMap.containsKey(floatZbMapping.getFieldDefineId())) continue;
                    floatZbMapping.setDataLinkId((String)fieldDefineMap.get(floatZbMapping.getFieldDefineId()));
                    continue;
                }
                zbMappingList.add(floatZbMapping);
            }
            if (needUpdate && zbMappingList.size() > 0) {
                fetchFloatSetting.getQueryConfigInfo().setZbMapping(zbMappingList);
                repairDto.addUpdateFloatSetting(fetchFloatSetting);
            }
            if (zbMappingList.size() != 0) continue;
            repairDto.addDeleteFloatSetting(fetchFloatSetting);
        }
        List<FetchSettingVO> fetchSettingList = this.fetchSettingDesService.listFetchSettingDesByCondi(condi);
        for (FetchSettingVO fetchSetting : fetchSettingList) {
            if (!regionSet.contains(fetchSetting.getRegionId())) {
                repairDto.addDeleteFetchSetting(fetchSetting);
                continue;
            }
            if (!fieldDefineSet.contains(fetchSetting.getFieldDefineId())) {
                repairDto.addDeleteFetchSetting(fetchSetting);
                continue;
            }
            if (dataLinkSet.contains(fetchSetting.getDataLinkId())) continue;
            if (fieldDefineMap.containsKey(fetchSetting.getFieldDefineId())) {
                fetchSetting.setDataLinkId((String)fieldDefineMap.get(fetchSetting.getFieldDefineId()));
                repairDto.AddUpdateFetchSetting(fetchSetting);
                continue;
            }
            repairDto.addDeleteFetchSetting(fetchSetting);
        }
        if (!CollectionUtils.isEmpty(repairDto.getDeleteFloatSettingIdList())) {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5f00\u59cb\u5220\u9664\u6d6e\u52a8\u8868\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u9519\u8bef\u6570\u636e{}......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle(), JsonUtils.writeValueAsString(repairDto.getDeleteFloatSettingList()));
            this.fetchFloatSettingDesDao.deleteFloatSettingDesData(repairDto.getDeleteFloatSettingIdList());
            this.fetchFloatSettingDao.deleteFloatSettingData(repairDto.getDeleteFloatSettingIdList());
            LogHelper.info((String)"\u5408\u5e76-BDE\u53d6\u6570\u8bbe\u7f6e", (String)String.format("\u5220\u9664-\u6d6e\u52a8\u8bbe\u7f6e-%1$s-%2$s-%3$s", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle()), (String)JsonUtils.writeValueAsString(repairDto.getDeleteFloatSettingList()));
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5b8c\u6210\u5220\u9664\u6d6e\u52a8\u8868\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u9519\u8bef\u6570\u636e......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle());
        }
        if (!CollectionUtils.isEmpty(repairDto.getUpdateFloatSettingList())) {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5f00\u59cb\u4fee\u6539\u6d6e\u52a8\u8868\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u9519\u8bef\u6570\u636e{}......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle(), JsonUtils.writeValueAsString(repairDto.getUpdateFloatSettingList()));
            this.fetchFloatSettingDesDao.updateFloatSettingDesData(repairDto.getUpdateFloatSettingList());
            this.fetchFloatSettingDao.updateFloatSettingData(repairDto.getUpdateFloatSettingList());
            LogHelper.info((String)"\u5408\u5e76-BDE\u53d6\u6570\u8bbe\u7f6e", (String)String.format("\u4fee\u6539-\u6d6e\u52a8\u8bbe\u7f6e-%1$s-%2$s-%3$s", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle()), (String)JsonUtils.writeValueAsString(repairDto.getUpdateFloatSettingList()));
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5b8c\u6210\u4fee\u6539\u6d6e\u52a8\u8868\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u9519\u8bef\u6570\u636e......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle());
        }
        if (!CollectionUtils.isEmpty(repairDto.getDeleteFetchSettingIdList())) {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5f00\u59cb\u6e05\u7406\u56fa\u5b9a\u8868\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u9519\u8bef\u6570\u636e{}......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle(), JsonUtils.writeValueAsString(repairDto.getDeleteFetchSettingList()));
            this.fetchSettingDesDao.deleteFetchSettingDesData(repairDto.getDeleteFetchSettingIdList());
            this.fetchSettingDao.deleteFetchSettingData(repairDto.getDeleteFetchSettingIdList());
            LogHelper.info((String)"\u5408\u5e76-BDE\u53d6\u6570\u8bbe\u7f6e", (String)String.format("\u5220\u9664-\u56fa\u5b9a\u8bbe\u7f6e-%1$s-%2$s-%3$s", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle()), (String)JsonUtils.writeValueAsString(repairDto.getDeleteFetchSettingList()));
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5b8c\u6210\u6e05\u7406\u56fa\u5b9a\u8868\u533a\u57df\u3001\u94fe\u63a5\u3001\u6307\u6807\u9519\u8bef\u6570\u636e......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle());
        }
        if (!CollectionUtils.isEmpty(repairDto.getUpdateFetchSettingList())) {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5f00\u59cb\u66f4\u65b0\u56fa\u5b9a\u8868\u533a\u57df\u9519\u8bef\u94fe\u63a5\u6570\u636e{}......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle(), JsonUtils.writeValueAsString(repairDto.getUpdateFetchSettingList()));
            this.fetchSettingDesDao.updateFetchSettingDesDk(repairDto.getUpdateFetchSettingList());
            this.fetchSettingDao.updateFetchSettingDk(repairDto.getUpdateFetchSettingList());
            LogHelper.info((String)"\u5408\u5e76-BDE\u53d6\u6570\u8bbe\u7f6e", (String)String.format("\u66f4\u65b0-\u56fa\u5b9a\u8bbe\u7f6e-%1$s-%2$s-%3$s", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle()), (String)JsonUtils.writeValueAsString(repairDto.getUpdateFetchSettingList()));
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5f02\u6b65\u4efb\u52a1{}-{}-{}\u5b8c\u6210\u66f4\u65b0\u56fa\u5b9a\u8868\u533a\u57df\u9519\u8bef\u94fe\u63a5\u6570\u636e......", taskDefine.getTitle(), formSchemeDefine.getTitle(), formDefine.getTitle());
        }
        return repairDto.getRepairFetchSchemeIdSet();
    }
}

