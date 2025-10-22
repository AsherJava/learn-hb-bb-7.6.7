/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.dao.TemplateConfigDao;
import com.jiuqi.nr.dataentry.exception.DataEntryException;
import com.jiuqi.nr.dataentry.gather.ExtendTemplateImpl;
import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import com.jiuqi.nr.dataentry.gather.TemplateItem;
import com.jiuqi.nr.dataentry.model.AllViewConfig;
import com.jiuqi.nr.dataentry.model.FuncExecuteConfig;
import com.jiuqi.nr.dataentry.model.TreeNodeItem;
import com.jiuqi.nr.dataentry.service.IFunctionAuthorService;
import com.jiuqi.nr.dataentry.service.IPlugInService;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.dataentry.util.ExceptionConsts;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

@Service
@DependsOn(value={"i18nHelperSupport"})
@Transactional(rollbackFor={NpRollbackException.class})
public class TemplateConfigServiceImpl
implements ITemplateConfigService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateConfigServiceImpl.class);
    @Autowired
    private TemplateConfigDao templateConfigDao;
    @Autowired
    private IPlugInService plugInService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private IFunctionAuthorService functionAuthorService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    private final NedisCache templateConfigCache;

    public TemplateConfigServiceImpl(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManger = cacheProvider.getCacheManager();
        this.templateConfigCache = cacheManger.getCache("nr:dataentry:templateConfig");
    }

    @Override
    public boolean updateTemplateConfig(TemplateConfigImpl templateConfig) {
        boolean success = this.templateConfigDao.updateTemplateConfig(templateConfig);
        return success;
    }

    @Override
    public boolean updateTemplateConfigByCode(TemplateConfigImpl templateConfig) {
        boolean success = this.templateConfigDao.updateTemplateConfigByCode(templateConfig);
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FTemplateConfig getTemplateConfigById(String templateId) {
        FTemplateConfig templateConfig = this.templateConfigDao.getTemplateConfigById(templateId);
        if ("dataentry_defaultFuncode".equals(templateConfig.getCode())) {
            InputStream input = null;
            try {
                ClassPathResource templateResource = new ClassPathResource("template/templateConfig_default.json");
                input = templateResource.getInputStream();
                String content = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
                ((TemplateConfigImpl)templateConfig).setTemplateConfig(content);
            }
            catch (Exception e) {
                Log.error((Exception)e);
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    }
                    catch (IOException e) {
                        Log.error((Exception)e);
                    }
                }
            }
        }
        return templateConfig;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FTemplateConfig getTemplateConfigByCode(String code) {
        if ("dataentry_defaultFuncode".equals(code)) {
            return this.getDefaultTemplateConfig();
        }
        FTemplateConfig templateConfigByCode = null;
        try {
            templateConfigByCode = this.templateConfigDao.getTemplateConfigByCode(code);
        }
        catch (Exception e) {
            Log.error((Exception)e);
            return this.getDefaultTemplateConfig();
        }
        if (StringUtils.isEmpty((String)templateConfigByCode.getTemplateConfig())) {
            return this.getDefaultTemplateConfig();
        }
        return templateConfigByCode;
    }

    private TemplateConfigImpl getDefaultTemplateConfig() {
        TemplateConfigImpl templateConfig = new TemplateConfigImpl();
        String content = this.plugInService.getDefaultTemplate();
        String template = this.plugInService.getDefaultTemplateCode();
        templateConfig.setTemplate(template);
        templateConfig.setTemplateConfig(content);
        return templateConfig;
    }

    @Override
    public boolean addTemplate_old(TemplateConfigImpl templateConfig) {
        boolean success = this.templateConfigDao.addTemplate_old(templateConfig);
        return success;
    }

    @Override
    public boolean addTemplate(TemplateConfigImpl templateConfig) {
        boolean success = this.templateConfigDao.addTemplate(templateConfig);
        return success;
    }

    @Override
    public boolean verifyCode(TemplateConfigImpl templateConfig) {
        boolean noRepeat = this.templateConfigDao.verifyCode(templateConfig);
        return noRepeat;
    }

    @Override
    public boolean updateTemplateCode(TemplateConfigImpl templateConfig) {
        return this.templateConfigDao.updateCode(templateConfig);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<TemplateConfigImpl> getAllTemplateConfig() {
        return this.templateConfigDao.getAllTemplateConfig();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FuncExecuteConfig getTemplateConfig(FTemplateConfig templateConfigImpl) {
        String templateConfig = templateConfigImpl.getTemplateConfig();
        String templateCode = templateConfigImpl.getTemplate() == null ? "standardTemplate" : templateConfigImpl.getTemplate();
        TemplateItem template = this.plugInService.getTemplateByCode(templateCode);
        String templateConfigCacheKey = "templateConfig";
        Cache.ValueWrapper valueWrapper = this.templateConfigCache.hGet(templateConfigCacheKey, (Object)templateCode);
        String templateStr = null;
        if (valueWrapper == null) {
            templateStr = template.getContent();
            this.templateConfigCache.hSet(templateConfigCacheKey, (Object)templateCode, (Object)templateStr);
        } else {
            templateStr = (String)valueWrapper.get();
        }
        JSONObject templateJson = new JSONObject(templateStr);
        ObjectMapper objectMapper = new ObjectMapper();
        AllViewConfig allViewConfig = null;
        try {
            allViewConfig = (AllViewConfig)objectMapper.readValue(templateConfig, AllViewConfig.class);
            allViewConfig = this.i18nAndAvailably(allViewConfig);
        }
        catch (IOException e) {
            Log.error((Exception)e);
            throw new DataEntryException(ExceptionConsts.EXCEPTION_TEMPLATE_ERROR);
        }
        FuncExecuteConfig funcExecuteConfig = new FuncExecuteConfig();
        funcExecuteConfig.setTemplateType(templateConfigImpl.getTemplate());
        funcExecuteConfig.setTemplate(templateJson.toString());
        funcExecuteConfig.setConfig(allViewConfig);
        return funcExecuteConfig;
    }

    private AllViewConfig i18nAndAvailably(AllViewConfig config) throws JsonProcessingException {
        List<TreeNodeItem> chooseButtons = config.getToolbarViewConfig().getChooseButtons();
        ArrayList<TreeNodeItem> newChooseButtons = new ArrayList<TreeNodeItem>();
        for (TreeNodeItem treeNodeItem : chooseButtons) {
            int findAuthorizeConfig = this.functionAuthorService.queryAuthorByModule(treeNodeItem.getCode());
            if (null != treeNodeItem.getChildren() && treeNodeItem.getChildren().size() > 0) {
                ArrayList<TreeNodeItem> childrenButtons = new ArrayList<TreeNodeItem>();
                for (TreeNodeItem childrenNode : treeNodeItem.getChildren()) {
                    int childrenConfig = this.functionAuthorService.queryAuthorByModule(childrenNode.getCode());
                    if (0 > childrenConfig) continue;
                    if (StringUtils.isNotEmpty((String)childrenNode.getAlias())) {
                        childrenNode.setTitle(childrenNode.getAlias());
                    } else if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage(childrenNode.getCode()))) {
                        childrenNode.setTitle(this.i18nHelper.getMessage(childrenNode.getCode()));
                    }
                    childrenButtons.add(childrenNode);
                }
                if (childrenButtons.size() > 0) {
                    treeNodeItem.setChildren(childrenButtons);
                }
            }
            if (0 > findAuthorizeConfig) continue;
            if (StringUtils.isNotEmpty((String)treeNodeItem.getAlias())) {
                treeNodeItem.setTitle(treeNodeItem.getAlias());
            } else if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage(treeNodeItem.getCode()))) {
                treeNodeItem.setTitle(this.i18nHelper.getMessage(treeNodeItem.getCode()));
            }
            newChooseButtons.add(treeNodeItem);
        }
        config.getToolbarViewConfig().setChooseButtons(newChooseButtons);
        List<TreeNodeItem> menus = config.getGridViewConfig().getMenus();
        for (TreeNodeItem treeNodeItem : menus) {
            if (!StringUtils.isNotEmpty((String)this.i18nHelper.getMessage(treeNodeItem.getCode()))) continue;
            treeNodeItem.setTitle(this.i18nHelper.getMessage(treeNodeItem.getCode()));
        }
        List<InfoViewItem> list = config.getInfoViewConfig().getChooseViews();
        for (InfoViewItem infoViewItem : list) {
            if (!StringUtils.isNotEmpty((String)this.i18nHelper.getMessage(infoViewItem.getCode()))) continue;
            infoViewItem.setTitle(this.i18nHelper.getMessage(infoViewItem.getCode()));
        }
        return config;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FuncExecuteConfig getFuncExecuteConfigByCode(String code) {
        FTemplateConfig templateConfigByCode = this.getTemplateConfigByCode(code);
        FuncExecuteConfig templateConfig = this.getTemplateConfig(templateConfigByCode);
        return templateConfig;
    }

    @Override
    public void deleteTemplateConfig(String templateId) {
        this.templateConfigDao.deleteTemplateConfig(templateId);
    }

    @Override
    public List<TemplateConfigImpl> getMiniTemplateConfig() {
        return this.templateConfigDao.getMiniTemplateConfig();
    }

    @Override
    public List<ExtendTemplateImpl> getExtendTemplateImpls() {
        return this.templateConfigDao.getExtendTemplateImpls();
    }

    @Override
    public ReturnInfo uploadExtendTemplate(ExtendTemplateImpl extendTemplate) {
        ReturnInfo returnInfo;
        block7: {
            boolean paramCorrect;
            returnInfo = new ReturnInfo();
            boolean bl = paramCorrect = StringUtils.isEmpty((String)extendTemplate.getCode()) || StringUtils.isEmpty((String)extendTemplate.getTitle());
            if (paramCorrect) {
                returnInfo.setMessage("error");
                returnInfo.setCommitError("\u4f60\u6709\u672a\u586b\u5199\u7684\u53c2\u6570\uff0c\u8bf7\u586b\u5199\uff01");
                return returnInfo;
            }
            ExtendTemplateImpl existTemplateImpl = this.templateConfigDao.getExtendTemplateImpl(extendTemplate.getCode());
            if (existTemplateImpl != null) {
                returnInfo.setMessage("error");
                returnInfo.setCommitError("\u6a21\u677f\u6807\u8bc6\u91cd\u590d\uff0c\u8bf7\u91cd\u65b0\u586b\u5199\uff01");
                return returnInfo;
            }
            try {
                byte[] fileBytes = this.fileUploadOssService.downloadFileByteFormTemp(extendTemplate.getFileKey());
                ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileBytes);
                String jsonStr = StreamUtils.copyToString(fileInputStream, Charset.forName("UTF-8"));
                JSONObject jsonObject = new JSONObject(jsonStr);
                boolean existLayouts = jsonObject.has("layouts");
                boolean existComponent = jsonObject.has("components");
                if (existLayouts && existComponent) {
                    String content = jsonObject.toString();
                    extendTemplate.setContent(content);
                    boolean addSuccess = this.templateConfigDao.addExtendTemplate(extendTemplate);
                    if (!addSuccess) {
                        returnInfo.setMessage("error");
                        returnInfo.setCommitError("\u6269\u5c55\u6a21\u677f\u4e0a\u4f20\u51fa\u9519\uff01");
                    }
                    break block7;
                }
                returnInfo.setMessage("error");
                returnInfo.setCommitError("\u4f60\u7684\u6a21\u677f\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570\uff0c\u8bf7\u68c0\u67e5\uff01");
                return returnInfo;
            }
            catch (JSONException e) {
                logger.error("\u6570\u636e\u5f55\u5165\u6269\u5c55\u6a21\u677f\u6587\u4ef6\u5185\u5bb9\u4e0d\u7b26\u5408json\u683c\u5f0f\u51fa\u9519\uff1a" + e.getMessage(), e);
                returnInfo.setMessage("error");
                returnInfo.setCommitError("\u4f60\u7684\u6a21\u677f\u4e0d\u7b26\u5408json\u683c\u5f0f\uff0c\u8bf7\u68c0\u67e5\uff01");
                return returnInfo;
            }
            catch (IOException e) {
                logger.error("\u6570\u636e\u5f55\u5165\u6269\u5c55\u6a21\u677f\u83b7\u53d6\u51fa\u9519\uff1a" + e.getMessage(), e);
                returnInfo.setMessage("error");
                returnInfo.setCommitError("\u6269\u5c55\u6a21\u677f\u4e0a\u4f20\u51fa\u9519\uff01");
                return returnInfo;
            }
        }
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public boolean deleteExtendTemplate(String code) {
        boolean success = this.templateConfigDao.deleteExtendTemplate(code);
        if (success) {
            this.templateConfigDao.deleteTemplateByKind(code);
        }
        return success;
    }

    @Override
    public ExtendTemplateImpl getExtendTemplateImpl(String code) {
        return this.templateConfigDao.getExtendTemplateImpl(code);
    }

    @Override
    public boolean modifyExtendTemplate(ExtendTemplateImpl extendTemplate) {
        return this.templateConfigDao.updateExtendTemplate(extendTemplate);
    }
}

