/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.I18nPluginManager
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.model.PluginManager
 *  com.jiuqi.va.biz.intf.model.PluginType
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.springframework.dao.DuplicateKeyException
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.model.PluginType;
import com.jiuqi.va.bizmeta.dao.IMetaDataHelperDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.domain.helper.BizViewTemplateDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.service.MetaHelperService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.bizmeta.util.MetaI18nUtils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class MetaHelperServiceImpl
implements MetaHelperService {
    private static final Logger logger = LoggerFactory.getLogger(MetaHelperServiceImpl.class);
    private static final String SYMBOL_ONE = "&";
    private static final String KEY_WORKFLOW_DEFINE = "workFlowDefine";
    @Autowired
    private IMetaDataHelperDao iMetaDataHelperDao;
    private ModelManager modelManager;
    @Autowired
    private I18nPluginManager i18nPluginManager;
    @Autowired
    private IMetaDataInfoVersionDao metaDataInfoVersionDao;
    @Autowired
    private VaI18nClient vaI18nClient;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    private PluginManager pluginManager;

    private PluginManager getPluginManager() {
        if (this.pluginManager == null) {
            this.pluginManager = (PluginManager)ApplicationContextRegister.getBean(PluginManager.class);
        }
        return this.pluginManager;
    }

    private ModelManager getModelManager() {
        if (this.modelManager == null) {
            this.modelManager = (ModelManager)ApplicationContextRegister.getBean(ModelManager.class);
        }
        return this.modelManager;
    }

    @Override
    public R saveBillTemplate(BizViewTemplateDO bizViewTemplateDO) {
        String message = "\u4fdd\u5b58\u6210\u529f\uff01";
        try {
            String id = bizViewTemplateDO.getId();
            bizViewTemplateDO.setCreateTime(new Date());
            if (StringUtils.hasText(id)) {
                message = "\u8986\u76d6\u6210\u529f\uff01";
                this.iMetaDataHelperDao.updateByName(bizViewTemplateDO);
                return R.ok((String)message);
            }
            bizViewTemplateDO.setId(UUID.randomUUID().toString());
            this.iMetaDataHelperDao.insert((Object)bizViewTemplateDO);
        }
        catch (DuplicateKeyException exception) {
            return R.error((int)2, (String)"\u6a21\u677f\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728\uff0c\u662f\u5426\u8986\u76d6\u5df2\u6709\u6a21\u677f\uff1f");
        }
        catch (Exception exception) {
            logger.error("error occur: ", exception);
            return R.error((int)500, (String)("\u540e\u7aef\u51fa\u9519\uff1a" + exception.getMessage()));
        }
        return R.ok((String)message);
    }

    @Override
    public R listBillTemplate(String searchKey, String bizType) {
        BizViewTemplateDO bizViewTemplateDO = new BizViewTemplateDO();
        bizViewTemplateDO.setName(searchKey);
        if (bizType != null) {
            bizViewTemplateDO.setBizType(bizType);
        }
        List<BizViewTemplateDO> list = this.iMetaDataHelperDao.list(bizViewTemplateDO);
        R r = new R();
        r.put("code", (Object)200);
        r.put("data", list);
        return r;
    }

    @Override
    public R deleteBillTemplate(BizViewTemplateDO bizViewTemplateDO) {
        try {
            bizViewTemplateDO.setTemplate(null);
            bizViewTemplateDO.setTitle(null);
            this.iMetaDataHelperDao.delete((Object)bizViewTemplateDO);
        }
        catch (Exception e) {
            logger.error("error occur: ", e);
            return R.error((int)500, (String)("\u5220\u9664\u5931\u8d25\uff1a" + e.getMessage()));
        }
        return R.ok((String)"\u5220\u9664\u6210\u529f\uff01");
    }

    @Override
    public List<VaI18nResourceItem> listWorkFlowVersionItemResourceList(TenantDO tenant) {
        List plugins;
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        Object workFlowVersion = tenant.getExtInfo(KEY_WORKFLOW_DEFINE);
        if (ObjectUtils.isEmpty(workFlowVersion)) {
            return resourceList;
        }
        MetaInfoDTO metaInfo = this.metaSyncCacheService.getMetaInfoByCache(String.valueOf(workFlowVersion), -1L);
        ModelType modelType = (ModelType)this.getModelManager().find(metaInfo.getModelName());
        if (modelType != null && (plugins = this.getPluginManager().getPluginList(modelType.getModelClass())) != null && plugins.size() > 0) {
            for (PluginType plugin : plugins) {
                I18nPlugin i18nPlugin = (I18nPlugin)this.i18nPluginManager.find(plugin.getName());
                if (i18nPlugin == null) continue;
                VaI18nResourceItem pluginResourceItem = new VaI18nResourceItem();
                pluginResourceItem.setName(i18nPlugin.getName() + "&plugin");
                pluginResourceItem.setTitle(i18nPlugin.getTitle() + "\uff08\u63d2\u4ef6\uff09");
                resourceList.add(pluginResourceItem);
            }
        }
        return resourceList;
    }

    @Override
    public List<VaI18nResourceItem> listWorkFlowVersionResourceList(TenantDO tempTenantDO) {
        ArrayList<VaI18nResourceItem> list = new ArrayList<VaI18nResourceItem>();
        Object uniqueCode = tempTenantDO.getExtInfo("uniqueCode");
        if (ObjectUtils.isEmpty(uniqueCode)) {
            return list;
        }
        MetaInfoHistoryDO metaInfoHistory = new MetaInfoHistoryDO();
        metaInfoHistory.setUniqueCode(String.valueOf(uniqueCode));
        List metaInfoHistoryDOList = Optional.ofNullable(this.metaDataInfoVersionDao.getMetaInfoAllByUniqueCode(metaInfoHistory)).orElse(Collections.emptyList());
        String title = "\u7248\u672c";
        int i = metaInfoHistoryDOList.size();
        for (MetaInfoHistoryDO metaInfoHistoryDO : metaInfoHistoryDOList) {
            Long versionNo = metaInfoHistoryDO.getVersionNO();
            VaI18nResourceItem vaI18nResourceItem = new VaI18nResourceItem();
            vaI18nResourceItem.setTitle(title + i);
            vaI18nResourceItem.setName(versionNo + SYMBOL_ONE + "workflowversion");
            vaI18nResourceItem.setCategoryFlag(true);
            vaI18nResourceItem.setGroupFlag(true);
            list.add(vaI18nResourceItem);
            --i;
        }
        return list;
    }

    @Override
    public <T> void convertMetaInfoI18nLanguage(T infoDO, Function<T, String> keyGetter, BiConsumer<T, String> titleHandle) {
        String prefixFlag;
        String titleFromLanguage;
        if (!Objects.isNull(infoDO) && VaI18nParamUtil.getTranslationEnabled().booleanValue() && StringUtils.hasText(titleFromLanguage = this.findTitleFromLanguage(prefixFlag = keyGetter.apply(infoDO)))) {
            titleHandle.accept(infoDO, titleFromLanguage);
        }
    }

    @Override
    public <T> void convertMetaInfoI18nLanguage(List<T> infoList, Function<T, String> keyGetter, BiConsumer<T, String> titleHandle) {
        if (CollectionUtils.isEmpty(infoList) || !VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
            return;
        }
        ArrayList<String> prefixFlagList = new ArrayList<String>();
        for (T infoDO : infoList) {
            String prefixFlag = keyGetter.apply(infoDO);
            prefixFlagList.add(prefixFlag);
        }
        VaI18nResourceDTO vaI18nDTO = new VaI18nResourceDTO();
        vaI18nDTO.setKey(prefixFlagList);
        List list = this.vaI18nClient.queryList(vaI18nDTO);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int size = infoList.size();
        for (int i = 0; i < size; ++i) {
            String languageSource = (String)list.get(i);
            T infoDO = infoList.get(i);
            if (!StringUtils.hasText(languageSource)) continue;
            titleHandle.accept(infoDO, languageSource);
        }
    }

    @Override
    public R findZhEnI18nLanguage(MetaInfoDTO infoDTO) {
        R r;
        if (Objects.isNull(infoDTO)) {
            return R.error();
        }
        String prefixFlag = MetaI18nUtils.generateBillDefineI18nKey(infoDTO.getModuleName(), infoDTO.getUniqueCode());
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add(prefixFlag);
        VaI18nResourceDTO vaI18nResourceDTO = new VaI18nResourceDTO();
        vaI18nResourceDTO.setKey(tempList);
        vaI18nResourceDTO.setLocale(new Locale("zh"));
        List zhList = this.vaI18nClient.queryList(vaI18nResourceDTO);
        vaI18nResourceDTO.setLocale(new Locale("en"));
        List enList = this.vaI18nClient.queryList(vaI18nResourceDTO);
        if (!CollectionUtils.isEmpty(zhList) && !CollectionUtils.isEmpty(enList)) {
            r = R.ok();
            r.put("zh", zhList.get(0));
            r.put("en", enList.get(0));
        } else {
            r = R.error();
        }
        return r;
    }

    private String findTitleFromLanguage(String nodeLanguageFlag) {
        if (ObjectUtils.isEmpty(nodeLanguageFlag)) {
            return "";
        }
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add(nodeLanguageFlag);
        VaI18nResourceDTO vaI18nDTO = new VaI18nResourceDTO();
        vaI18nDTO.setKey(tempList);
        List list = this.vaI18nClient.queryList(vaI18nDTO);
        if (!ObjectUtils.isEmpty(list)) {
            return (String)list.get(0);
        }
        return "";
    }
}

