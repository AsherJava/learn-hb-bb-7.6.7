/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthorityState
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.query.datacheck.DataCheckManage
 *  com.jiuqi.va.query.datacheck.InterceptorEnum
 *  com.jiuqi.va.query.domain.DataCheckParam
 *  com.jiuqi.va.query.domain.DataCheckResult
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.sql.formula.QueryFormulaContext
 *  com.jiuqi.va.query.sql.formula.QueryFormulaHandler
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.BaseInfoPlugin
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.plugin.FormulaPlugin
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.QueryFormulaImpl
 *  com.jiuqi.va.query.template.plugin.QueryPlugin
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.plugin.ViewDesignPlugin
 *  com.jiuqi.va.query.template.vo.QueryConfigureImportVO$ImportResult
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.QueryTemplateCacheVO
 *  com.jiuqi.va.query.template.vo.QueryTemplateDesignVO
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  org.apache.shiro.util.Assert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.query.template.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.query.cache.QueryCacheManage;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.config.VaQueryI18nParam;
import com.jiuqi.va.query.datacheck.DataCheckManage;
import com.jiuqi.va.query.datacheck.InterceptorEnum;
import com.jiuqi.va.query.domain.DataCheckParam;
import com.jiuqi.va.query.domain.DataCheckResult;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import com.jiuqi.va.query.sql.formula.QueryFormulaHandler;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.dao.TemplateDesignDao;
import com.jiuqi.va.query.template.dao.TemplateInfoDao;
import com.jiuqi.va.query.template.dto.ScopeDefaultDTO;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.plugin.FormulaPlugin;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryFormulaImpl;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.plugin.ViewDesignPlugin;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryConfigureImportVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.QueryTemplateCacheVO;
import com.jiuqi.va.query.template.vo.QueryTemplateDesignVO;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import com.jiuqi.va.query.util.QueryUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class TemplateDesignServiceImpl
implements TemplateDesignService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateDesignServiceImpl.class);
    public static final String VA_VA_QUERY = "VA#VaQuery#";
    public static final String BASE_INFO = "baseInfo";
    @Autowired
    private TemplateDesignDao templateDesignDao;
    @Autowired
    private List<QueryPlugin> plugins;
    @Autowired
    private QueryCacheManage queryCacheManage;
    @Autowired
    private TemplateInfoDao templateInfoDao;
    @Autowired
    private VaI18nClient vaI18nClient;
    @Autowired
    private MenuTreeService menuTreeService;
    @Autowired
    private DataCheckManage dataCheckManage;
    @Autowired
    private QueryTemplateInfoDao queryTemplateInfoDao;
    @Autowired
    private QueryFormulaHandler queryFormulaHandler;

    @Override
    public QueryTemplateDesignVO getTemplateDesignData(String templateId) {
        return this.templateDesignDao.getTemplateDesignByTemplateId(templateId);
    }

    @Override
    public String getTemplateCodeByTemplateId(String templateId) {
        return this.queryCacheManage.getTemplate(templateId, pId -> {
            TemplateInfoVO templateInfo = this.templateInfoDao.getTemplateInfo(templateId);
            QueryTemplateDesignVO designVO = this.templateDesignDao.getTemplateDesignByTemplateId(pId);
            if (templateInfo == null) {
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5b9a\u4e49\u4e0d\u5b58\u5728\u6216\u8005\u5df2\u5220\u9664");
            }
            QueryTemplateCacheVO cacheVO = new QueryTemplateCacheVO();
            cacheVO.setTemplateCode(templateInfo.getCode());
            cacheVO.setTemplateId(templateInfo.getId());
            String designData = null;
            if (designVO != null) {
                designData = designVO.getDesignData();
            }
            cacheVO.setTemplateDesign(designData);
            cacheVO.setTemplateInfo(templateInfo);
            return cacheVO;
        }).getTemplateCode();
    }

    @Override
    public QueryTemplate getTemplate(String templateId) {
        QueryTemplateCacheVO templateCacheVO = this.queryCacheManage.getTemplate(templateId, pId -> {
            TemplateInfoVO templateInfo = this.templateInfoDao.getTemplateInfo(templateId);
            QueryTemplateDesignVO designVO = this.templateDesignDao.getTemplateDesignByTemplateId(pId);
            if (templateInfo == null) {
                throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5b9a\u4e49\u4e0d\u5b58\u5728\u6216\u8005\u5df2\u5220\u9664");
            }
            QueryTemplateCacheVO cacheVO = new QueryTemplateCacheVO();
            cacheVO.setTemplateCode(templateInfo.getCode());
            cacheVO.setTemplateId(templateInfo.getId());
            String designData = null;
            if (designVO != null) {
                designData = designVO.getDesignData();
            }
            cacheVO.setTemplateDesign(designData);
            cacheVO.setTemplateInfo(templateInfo);
            return cacheVO;
        });
        String templateString = templateCacheVO.getTemplateDesign();
        TemplateInfoVO templateInfo = templateCacheVO.getTemplateInfo();
        QueryTemplate template = new QueryTemplate();
        if (StringUtils.hasText(templateString)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
                template = (QueryTemplate)mapper.readValue(templateString, QueryTemplate.class);
                BaseInfoPlugin baseInfoPlugin = new BaseInfoPlugin();
                baseInfoPlugin.setBaseInfo(templateInfo);
                template.getPlugins().add(baseInfoPlugin);
                template.setCode(templateInfo.getCode());
                template.setId(templateInfo.getId());
                List existPlugins = template.getPlugins();
                this.plugins.forEach(plugin -> {
                    Optional<QueryPlugin> any = existPlugins.stream().filter(queryPlugin -> queryPlugin.getName().equals(plugin.getName())).findAny();
                    if (!any.isPresent()) {
                        existPlugins.add(plugin);
                    }
                });
                template.setPlugins(existPlugins.stream().sorted(Comparator.comparingInt(QueryPlugin::getSortNum)).collect(Collectors.toList()));
            }
            catch (JsonProcessingException e) {
                logger.error("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5b9a\u4e49\u8f6c\u6362\u5931\u8d25", e);
                throw new DefinedQueryRuntimeException("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5b9a\u4e49\u8f6c\u6362\u5931\u8d25");
            }
        } else {
            ArrayList<QueryPlugin> allPlugins = new ArrayList<QueryPlugin>(this.plugins);
            allPlugins.removeIf(queryPlugin -> queryPlugin.getName().equalsIgnoreCase(BASE_INFO));
            BaseInfoPlugin baseInfoPlugin = new BaseInfoPlugin();
            baseInfoPlugin.setBaseInfo(templateInfo);
            allPlugins.add((QueryPlugin)baseInfoPlugin);
            template.setCode(templateInfo.getCode());
            template.setId(templateInfo.getId());
            allPlugins.forEach(QueryPlugin::initPlugin);
            template.setPlugins(allPlugins.stream().sorted(Comparator.comparingInt(QueryPlugin::getSortNum)).collect(Collectors.toList()));
        }
        return template;
    }

    @Override
    public QueryTemplate getBizTemplate(String templateId) {
        QueryTemplate template = this.getTemplate(templateId);
        ToolBarPlugin pluginByClass = (ToolBarPlugin)template.getPluginByClass(ToolBarPlugin.class);
        DefaultAuthQueryService defaultAuthQueryService = DCQuerySpringContextUtils.getBean(DefaultAuthQueryService.class);
        ArrayList<TemplateToolbarInfoVO> filterAuthTools = new ArrayList<TemplateToolbarInfoVO>();
        for (TemplateToolbarInfoVO tool : pluginByClass.getTools()) {
            if (Boolean.TRUE.equals(tool.getEnableAuth())) {
                AuthorityConst.AuthorityState authority = defaultAuthQueryService.getAuthority("VAQueryAction_d215baaa-17b8-4f67", NpContextHolder.getContext().getIdentityId(), (Object)tool.getId());
                if (!authority.equals((Object)AuthorityConst.AuthorityState.ALLOW)) continue;
                filterAuthTools.add(tool);
                continue;
            }
            filterAuthTools.add(tool);
        }
        pluginByClass.setTools(filterAuthTools);
        if (Boolean.TRUE.equals(VaQueryI18nParam.getTranslationEnabled())) {
            this.translateI18nLanguage(template);
        }
        return QueryUtils.removeSqlFromQueryTemplate(this.initFieldExpression(this.initLabelNode(this.initDefaultValue(template))));
    }

    private QueryTemplate initFieldExpression(QueryTemplate queryTemplate) {
        DataSourcePlugin pluginByClass = (DataSourcePlugin)queryTemplate.getPluginByClass(DataSourcePlugin.class);
        FormulaPlugin formulaPlugin = (FormulaPlugin)queryTemplate.getPluginByClass(FormulaPlugin.class);
        if (CollectionUtils.isEmpty(pluginByClass.getParams())) {
            return queryTemplate;
        }
        for (TemplateParamsVO param : pluginByClass.getParams()) {
            Map configParamMap = param.getConfigParamMap();
            if (!configParamMap.containsKey("versionDate")) continue;
            String versionDate = String.valueOf(configParamMap.get("versionDate"));
            QueryFormulaContext context = new QueryFormulaContext();
            QueryFormulaImpl formula = formulaPlugin.getFormula(versionDate);
            if (StringUtils.hasText(formula.getExpression())) {
                this.queryFormulaHandler.getFieldInExpression(formula.getExpression(), context);
            }
            HashSet triggerFields = context.getTriggerFields();
            param.setTriggerFields(triggerFields);
            if (!CollectionUtils.isEmpty(triggerFields) || !StringUtils.hasText(formula.getExpression())) continue;
            Object value = this.queryFormulaHandler.evaluateData(formula.getExpression(), ParamTypeEnum.STRING.getTypeName());
            formulaPlugin.getFormulaValues().put(versionDate, value);
        }
        for (QueryFormulaImpl formula : formulaPlugin.getFormulas()) {
            Object value;
            if (!StringUtils.hasText(formula.getExpression()) || (value = this.queryFormulaHandler.evaluateData(formula.getExpression(), ParamTypeEnum.STRING.getTypeName())) == null) continue;
            formulaPlugin.getFormulaValues().put(formula.getId(), value);
        }
        return queryTemplate;
    }

    private QueryTemplate initLabelNode(QueryTemplate queryTemplate) {
        ViewDesignPlugin pluginByClass = (ViewDesignPlugin)queryTemplate.getPluginByClass(ViewDesignPlugin.class);
        if (pluginByClass == null) {
            return queryTemplate;
        }
        Map designSets = JSONUtil.parseMap((String)pluginByClass.getDesignSets());
        if (designSets != null && designSets.containsKey("nodes")) {
            List nodes = (List)designSets.get("nodes");
            for (Map node : nodes) {
                String expression = (String)node.get("text");
                if (!StringUtils.hasText(expression)) continue;
                try {
                    Object formulaValue = FormulaExecuteHandlerUtil.executeFormula(expression, ParamTypeEnum.STRING.getTypeName());
                    node.put("text", formulaValue == null ? "" : formulaValue.toString());
                }
                catch (Exception e) {
                    node.put("text", expression);
                    logger.error("default value parse failed", e);
                }
            }
        }
        pluginByClass.setDesignSets(JSONUtil.toJSONString((Object)designSets));
        return queryTemplate;
    }

    private void translateI18nLanguage(QueryTemplate template) {
        List fields;
        MenuTreeVO menuVO = this.menuTreeService.getMenuVO(template.getId());
        template.getBaseInfo().getBaseInfo().setTitle(QueryUtils.translateTemplateTitle(template));
        String groupCode = menuVO.getParents().replace("/", "#");
        List tools = ((ToolBarPlugin)template.getPluginByName(PluginEnum.toolBar.name(), ToolBarPlugin.class)).getTools();
        if (!ObjectUtils.isEmpty(tools)) {
            ArrayList<String> prefixFlagList = new ArrayList<String>();
            for (TemplateToolbarInfoVO tool : tools) {
                String prefixFlag = VA_VA_QUERY + groupCode + "#toolbars#" + tool.getId();
                prefixFlagList.add(prefixFlag);
            }
            VaI18nResourceDTO vaI18nDTO = new VaI18nResourceDTO();
            vaI18nDTO.setKey(prefixFlagList);
            List stringTools = this.vaI18nClient.queryList(vaI18nDTO);
            if (!ObjectUtils.isEmpty(stringTools)) {
                for (int i = 0; i < stringTools.size(); ++i) {
                    String languageSource = (String)stringTools.get(i);
                    TemplateToolbarInfoVO templateToolbarInfoVO = (TemplateToolbarInfoVO)tools.get(i);
                    if (ObjectUtils.isEmpty(languageSource)) continue;
                    templateToolbarInfoVO.setTitle(languageSource);
                }
            }
        }
        if (!ObjectUtils.isEmpty(fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields())) {
            ArrayList<String> prefixList = new ArrayList<String>();
            for (TemplateFieldSettingVO field : fields) {
                String prefixFlag = VA_VA_QUERY + groupCode + "#showcol#" + field.getId();
                prefixList.add(prefixFlag);
            }
            VaI18nResourceDTO vaI18nDTO = new VaI18nResourceDTO();
            vaI18nDTO.setKey(prefixList);
            List stringFields = this.vaI18nClient.queryList(vaI18nDTO);
            if (!ObjectUtils.isEmpty(stringFields)) {
                for (int i = 0; i < stringFields.size(); ++i) {
                    String languageSource = (String)stringFields.get(i);
                    TemplateFieldSettingVO templateFieldSettingVO = (TemplateFieldSettingVO)fields.get(i);
                    if (ObjectUtils.isEmpty(languageSource)) continue;
                    templateFieldSettingVO.setTitle(languageSource);
                }
            }
        }
        List params = template.getDataSourceSet().getParams();
        if (!ObjectUtils.isEmpty(fields)) {
            ArrayList<String> prefixList = new ArrayList<String>();
            for (TemplateParamsVO param : params) {
                String prefixFlag = VA_VA_QUERY + groupCode + "#param#" + param.getId();
                prefixList.add(prefixFlag);
            }
            VaI18nResourceDTO vaI18nDTO = new VaI18nResourceDTO();
            vaI18nDTO.setKey(prefixList);
            List paramList = this.vaI18nClient.queryList(vaI18nDTO);
            if (!ObjectUtils.isEmpty(paramList)) {
                for (int i = 0; i < paramList.size(); ++i) {
                    String languageSource = (String)paramList.get(i);
                    TemplateParamsVO templateParamsVO = (TemplateParamsVO)params.get(i);
                    if (ObjectUtils.isEmpty(languageSource)) continue;
                    templateParamsVO.setTitle(languageSource);
                }
            }
        }
    }

    private QueryTemplate initDefaultValue(QueryTemplate template) {
        List params = template.getDataSourceSet().getParams();
        if (params == null || params.isEmpty()) {
            return template;
        }
        for (TemplateParamsVO paramsVO : params) {
            if (DCQueryStringHandle.isEmpty(paramsVO.getDefaultValue())) continue;
            if (!this.isJsonStr(paramsVO.getDefaultValue())) {
                try {
                    Object formulaValue = FormulaExecuteHandlerUtil.executeFormula(paramsVO.getDefaultValue(), paramsVO.getParamType());
                    if (null == formulaValue) {
                        paramsVO.setDefaultValue("");
                        continue;
                    }
                    if (paramsVO.getParamType().equals(ParamTypeEnum.DATE_TIME.getTypeName())) {
                        formulaValue = QueryUtils.convertTimeWithTimeZone(String.valueOf(formulaValue));
                    }
                    paramsVO.setDefaultValue(formulaValue == null ? "" : formulaValue.toString());
                }
                catch (Exception e) {
                    paramsVO.setDefaultValue("");
                    logger.error("default value parser failed", e);
                }
                continue;
            }
            ScopeDefaultDTO scopeDefaultDTO = (ScopeDefaultDTO)JSONUtil.parseObject((String)paramsVO.getDefaultValue(), ScopeDefaultDTO.class);
            Object startValue = FormulaExecuteHandlerUtil.getFormulaEvaluateData(scopeDefaultDTO.getStart(), paramsVO.getParamType());
            Object endValue = FormulaExecuteHandlerUtil.getFormulaEvaluateData(scopeDefaultDTO.getEnd(), paramsVO.getParamType());
            scopeDefaultDTO.setStart(startValue == null ? "" : startValue.toString());
            scopeDefaultDTO.setEnd(endValue == null ? "" : endValue.toString());
            paramsVO.setDefaultValue(JSONUtil.toJSONString((Object)scopeDefaultDTO));
        }
        template.getDataSourceSet().setParams(params);
        return template;
    }

    @Override
    public QueryTemplate getBizTemplate(QueryTemplate queryTemplate) {
        ToolBarPlugin pluginByClass = (ToolBarPlugin)queryTemplate.getPluginByClass(ToolBarPlugin.class);
        DefaultAuthQueryService defaultAuthQueryService = DCQuerySpringContextUtils.getBean(DefaultAuthQueryService.class);
        ArrayList<TemplateToolbarInfoVO> filterAuthTools = new ArrayList<TemplateToolbarInfoVO>();
        for (TemplateToolbarInfoVO tool : pluginByClass.getTools()) {
            if (Boolean.TRUE.equals(tool.getEnableAuth())) {
                AuthorityConst.AuthorityState authority = defaultAuthQueryService.getAuthority("VAQueryAction_d215baaa-17b8-4f67", NpContextHolder.getContext().getIdentityId(), (Object)tool.getId());
                if (!authority.equals((Object)AuthorityConst.AuthorityState.ALLOW)) continue;
                filterAuthTools.add(tool);
                continue;
            }
            filterAuthTools.add(tool);
        }
        pluginByClass.setTools(filterAuthTools);
        QueryParamVO queryParamVO = new QueryParamVO();
        queryParamVO.setQueryTemplate(queryTemplate);
        QueryUtils.transformFrontQueryTemplate(queryParamVO, true);
        if (Boolean.TRUE.equals(VaQueryI18nParam.getTranslationEnabled())) {
            this.translateI18nLanguage(queryTemplate);
        }
        return this.initFieldExpression(this.initLabelNode(this.initDefaultValue(queryParamVO.getQueryTemplate())));
    }

    @Override
    public TemplateContentVO getTemplateContentVO(String templateId) {
        QueryTemplate template = this.getTemplate(templateId);
        return QueryUtils.QueryTemplate2TemplateContentVO(template);
    }

    @Override
    public QueryTemplate getTemplateByCode(String code) {
        TemplateInfoVO templateInfo = this.templateInfoDao.getTemplateInfoByCode(code);
        Assert.notNull((Object)templateInfo, (String)(code + "\uff1a\u5bf9\u5e94\u7684\u67e5\u8be2\u5b9a\u4e49\u4e0d\u5b58\u5728\u6216\u8005\u5df2\u5220\u9664"));
        return this.getTemplate(templateInfo.getId());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveTemplate(QueryTemplate queryTemplate, boolean useCheck) {
        Optional<QueryPlugin> first = queryTemplate.getPlugins().stream().filter(queryPlugin -> BASE_INFO.equalsIgnoreCase(queryPlugin.getName())).findFirst();
        boolean needClearTreeCache = false;
        if (first.isPresent()) {
            BaseInfoPlugin baseInfo = (BaseInfoPlugin)first.get();
            if (StringUtils.hasText(baseInfo.getBaseInfo().getId())) {
                TemplateInfoVO templateInfo = this.templateInfoDao.getTemplateInfo(baseInfo.getBaseInfo().getId());
                needClearTreeCache = this.needUpdateTreeCache(templateInfo, baseInfo.getBaseInfo());
                this.templateInfoDao.updateTemplateInfo(baseInfo.getBaseInfo());
            } else {
                baseInfo.getBaseInfo().setId(DCQueryUUIDUtil.getUUIDStr());
                this.templateInfoDao.saveTemplateInfo(baseInfo.getBaseInfo());
            }
        }
        queryTemplate.setPlugins(queryTemplate.getPlugins().stream().filter(queryPlugin -> !BASE_INFO.equalsIgnoreCase(queryPlugin.getName())).collect(Collectors.toList()));
        boolean exist = this.templateDesignDao.existByID(queryTemplate.getId());
        if (exist) {
            this.templateDesignDao.updateTemplateDesign(queryTemplate);
        } else {
            queryTemplate.setId(queryTemplate.getId());
            this.templateDesignDao.saveTemplateDesign(queryTemplate);
            this.queryCacheManage.clearTreeCache();
        }
        this.queryCacheManage.clearOneCache(queryTemplate.getId());
        if (needClearTreeCache) {
            this.queryCacheManage.clearTreeCache();
        }
    }

    @Override
    public void saveTemplateDesign(QueryTemplateDesignVO designVO) {
        this.templateDesignDao.saveTemplateDesignData(designVO);
        this.queryCacheManage.clearOneCache(designVO.getId());
    }

    @Override
    public QueryConfigureImportVO.ImportResult importQueryTemplate(QueryTemplate queryTemplate, TemplateInfoVO templateInfoParam) {
        List queryPluginList = queryTemplate.getPlugins().stream().filter(queryPlugin -> !BASE_INFO.equalsIgnoreCase(queryPlugin.getName())).collect(Collectors.toList());
        queryTemplate.setPlugins(queryPluginList);
        String title = templateInfoParam.getTitle();
        String code = templateInfoParam.getCode();
        String msgPrefix = "\u67e5\u8be2\u5b9a\u4e49\uff1a" + title + "(" + code + ") ";
        QueryConfigureImportVO.ImportResult importResult = new QueryConfigureImportVO.ImportResult();
        boolean exist = this.templateDesignDao.existByID(queryTemplate.getId());
        if (exist) {
            this.templateDesignDao.updateTemplateDesign(queryTemplate);
            importResult.setMsg(msgPrefix + "\u66f4\u65b0\u6210\u529f");
        } else {
            queryTemplate.setId(queryTemplate.getId());
            this.templateDesignDao.saveTemplateDesign(queryTemplate);
            this.queryCacheManage.clearTreeCache();
            importResult.setMsg(msgPrefix + "\u5bfc\u5165\u6210\u529f");
        }
        this.queryCacheManage.clearOneCache(queryTemplate.getId());
        return importResult;
    }

    @Override
    public QueryConfigureImportVO.ImportResult importQueryTemplateUseStrategy(QueryTemplate queryTemplate, String importStrategy, TemplateInfoVO templateInfoParam, List<String> skipImportCodeList) {
        List queryPluginList = queryTemplate.getPlugins().stream().filter(queryPlugin -> !BASE_INFO.equalsIgnoreCase(queryPlugin.getName())).collect(Collectors.toList());
        queryTemplate.setPlugins(queryPluginList);
        String code = queryTemplate.getCode();
        String title = templateInfoParam.getTitle();
        List allTemplates = Optional.ofNullable(this.queryTemplateInfoDao.getAllTemplates()).orElse(Collections.emptyList());
        TemplateInfoVO templateInfoVO = allTemplates.stream().filter(x -> x.getCode().equals(code)).findAny().orElse(null);
        String msgPrefix = "\u67e5\u8be2\u5b9a\u4e49\uff1a" + title + "(" + code + ") ";
        QueryConfigureImportVO.ImportResult importResult = new QueryConfigureImportVO.ImportResult();
        if (Objects.isNull(templateInfoVO)) {
            this.templateDesignDao.saveTemplateDesign(queryTemplate);
            this.queryCacheManage.clearTreeCache();
            importResult.setMsg(msgPrefix + "\u5bfc\u5165\u6210\u529f");
        } else {
            if ("skip".equals(importStrategy)) {
                importResult.setMsg(msgPrefix + "\u5df2\u5b58\u5728\u8df3\u8fc7\u5bfc\u5165");
                skipImportCodeList.add(code);
                return importResult;
            }
            if ("cover".equals(importStrategy)) {
                String id = templateInfoVO.getId();
                queryTemplate.setId(id);
                this.templateDesignDao.updateTemplateDesign(queryTemplate);
                importResult.setMsg(msgPrefix + "\u66f4\u65b0\u6210\u529f");
            }
        }
        this.queryCacheManage.clearOneCache(queryTemplate.getId());
        return importResult;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void removeTemplate(String templateId) {
        if (!StringUtils.hasText(templateId)) {
            throw new DefinedQueryRuntimeException("\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        TemplateInfoVO vo = this.templateInfoDao.getTemplateInfo(templateId);
        DataCheckParam dataCheckParam = new DataCheckParam();
        dataCheckParam.setTemplateInfoVOS(Collections.singletonList(vo));
        DataCheckResult dataCheckResult = this.dataCheckManage.preHandlerByType(dataCheckParam, InterceptorEnum.QueryTemplateDelete);
        if (!dataCheckResult.isPass()) {
            throw new DefinedQueryRuntimeException(dataCheckResult.getMessage());
        }
        this.templateDesignDao.deleteById(templateId);
        this.templateInfoDao.deleteById(templateId);
        this.queryCacheManage.clearOneCache(templateId);
        this.queryCacheManage.clearTreeCache();
    }

    private boolean isJsonStr(String defaultValue) {
        if (!StringUtils.hasText(defaultValue)) {
            return false;
        }
        if (!defaultValue.contains("{")) {
            return false;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(defaultValue);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean needUpdateTreeCache(TemplateInfoVO oldInfoVo, TemplateInfoVO newInfoVo) {
        return !Objects.equals(oldInfoVo.getTitle(), newInfoVo.getTitle()) || !Objects.equals(oldInfoVo.getGroupId(), newInfoVo.getGroupId()) || !Objects.equals(oldInfoVo.getSortOrder(), newInfoVo.getSortOrder());
    }
}

