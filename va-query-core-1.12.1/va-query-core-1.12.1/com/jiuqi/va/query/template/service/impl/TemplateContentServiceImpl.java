/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  com.jiuqi.va.query.template.enumerate.AlignEnum
 *  com.jiuqi.va.query.template.enumerate.AutoWidthTypeEnum
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.QueryRelatePlugin
 *  com.jiuqi.va.query.template.vo.ConfigureExportVO
 *  com.jiuqi.va.query.template.vo.QueryConfigureImportVO
 *  com.jiuqi.va.query.template.vo.QueryConfigureImportVO$ImportResult
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.QueryTemplateDesignVO
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.query.template.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.cache.QueryCacheManage;
import com.jiuqi.va.query.common.QueryTemplateConst;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.sql.service.impl.SqlQueryServiceImpl;
import com.jiuqi.va.query.template.dao.QueryTemplateDataSourceSetDao;
import com.jiuqi.va.query.template.dao.QueryTemplateFieldSettingDao;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.dao.QueryTemplateParamsDao;
import com.jiuqi.va.query.template.dao.QueryTemplateRelateQueryDao;
import com.jiuqi.va.query.template.dao.QueryTemplateToolbarInfoDao;
import com.jiuqi.va.query.template.dto.ScopeDefaultDTO;
import com.jiuqi.va.query.template.enumerate.AlignEnum;
import com.jiuqi.va.query.template.enumerate.AutoWidthTypeEnum;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.ConfigureExportVO;
import com.jiuqi.va.query.template.vo.QueryConfigureImportVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.QueryTemplateDesignVO;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import com.jiuqi.va.query.util.QueryUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TemplateContentServiceImpl
implements TemplateContentService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateContentServiceImpl.class);
    @Autowired
    private QueryTemplateDataSourceSetDao queryTemplateDataSourceSetDao;
    @Autowired
    private QueryTemplateFieldSettingDao queryTemplateFieldSettingDao;
    @Autowired
    private QueryTemplateParamsDao queryTemplateParamsDao;
    @Autowired
    private QueryTemplateInfoDao queryTemplateInfoDao;
    @Autowired
    private QueryTemplateToolbarInfoDao queryTemplateToolDao;
    @Autowired
    private QueryTemplateRelateQueryDao queryTemplateRelateQueryDao;
    @Autowired
    private MenuTreeService menuTreeService;
    @Autowired
    private SqlQueryServiceImpl sqlQueryServiceImpl;
    @Autowired
    private TemplateDesignService templateDesignService;
    @Autowired
    private QueryCacheManage queryCacheManage;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String templateContentSave(TemplateContentVO templateContentVO) {
        List tools;
        List params;
        TemplateInfoVO template = templateContentVO.getTemplate();
        String id = template.getId();
        String templateId = UUID.randomUUID().toString();
        TemplateInfoVO templatesByCode = this.queryTemplateInfoDao.getTemplatesByCode(template.getCode());
        if (templatesByCode != null && !templatesByCode.getId().equals(id)) {
            throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728\uff0c\u4e0d\u5141\u8bb8\u4fdd\u5b58");
        }
        if (!DCQueryStringHandle.isEmpty(id)) {
            this.queryTemplateInfoDao.deleteById(id);
            this.queryTemplateDataSourceSetDao.deleteByTemplateId(id);
            this.queryTemplateFieldSettingDao.deleteByTemplateId(id);
            this.queryTemplateParamsDao.deleteByTemplateId(id);
            this.queryTemplateToolDao.deleteByTemplateId(id);
            this.queryTemplateRelateQueryDao.deleteByTemplateId(id);
        } else {
            template.setId(templateId);
        }
        if (templateContentVO.getTemplate().getSortOrder() == null) {
            List<TemplateInfoVO> templates = this.queryTemplateInfoDao.getTemplatesByGroupId(templateContentVO.getTemplate().getGroupId());
            if (templates == null || templates.isEmpty()) {
                template.setSortOrder(Integer.valueOf(1));
            } else {
                template.setSortOrder(Integer.valueOf(templates.get(templates.size() - 1).getSortOrder() + 1));
            }
        }
        this.queryTemplateInfoDao.save(template);
        if (templateContentVO.getDataSourceSet() != null && !DCQueryStringHandle.isEmpty(templateContentVO.getDataSourceSet().getDefineSql())) {
            templateContentVO.getDataSourceSet().setTemplateId(DCQueryStringHandle.isEmpty(id) ? templateId : id);
            this.queryTemplateDataSourceSetDao.save(templateContentVO.getDataSourceSet());
        }
        if (templateContentVO.getFields() != null && !templateContentVO.getFields().isEmpty()) {
            List fields = templateContentVO.getFields();
            fields.forEach(item -> item.setTemplateId(DCQueryStringHandle.isEmpty(id) ? templateId : id));
            this.queryTemplateFieldSettingDao.batchSave(fields);
        }
        if (templateContentVO.getParams() != null && !templateContentVO.getParams().isEmpty() && !DCQueryStringHandle.isEmpty(((TemplateParamsVO)(params = templateContentVO.getParams()).get(0)).getName())) {
            this.checkParams(params);
            params.forEach(item -> {
                item.setTemplateId(DCQueryStringHandle.isEmpty(id) ? templateId : id);
                item.setFoldFlag(Boolean.valueOf(Boolean.TRUE.equals(item.getFoldFlag()) && item.getFoldFlag() != false));
            });
            this.queryTemplateParamsDao.batchSave(params);
        }
        if (templateContentVO.getTools() != null && !templateContentVO.getTools().isEmpty() && !DCQueryStringHandle.isEmpty(((TemplateToolbarInfoVO)(tools = templateContentVO.getTools()).get(0)).getAction())) {
            tools.forEach(item -> item.setTemplateId(DCQueryStringHandle.isEmpty(id) ? templateId : id));
            this.queryTemplateToolDao.batchSave(tools);
        }
        if (templateContentVO.getRelateQuerys() != null && !templateContentVO.getRelateQuerys().isEmpty()) {
            List relateQuerys = templateContentVO.getRelateQuerys();
            for (int i = relateQuerys.size() - 1; i >= 0; --i) {
                TemplateRelateQueryVO item2 = (TemplateRelateQueryVO)relateQuerys.get(i);
                if (DCQueryStringHandle.isEmpty(item2.getProcessorName()) && DCQueryStringHandle.isEmpty(item2.getTriggerField())) {
                    relateQuerys.remove(i);
                    continue;
                }
                item2.setTemplateId(DCQueryStringHandle.isEmpty(id) ? templateId : id);
            }
            this.queryTemplateRelateQueryDao.batchSave(relateQuerys);
        }
        return DCQueryStringHandle.isEmpty(id) ? templateId : id;
    }

    private void checkParams(List<TemplateParamsVO> params) {
        for (TemplateParamsVO param : params) {
            String refTableName = param.getRefTableName();
            String mode = param.getMode();
            boolean mustInput = param.isMustInput();
            Boolean enableAuth = param.getEnableAuth();
            if (QueryModeEnum.mutileData.getModeSign().equals(mode) || !StringUtils.hasText(refTableName) || !StringUtils.hasText(param.getFilterCondition()) && Boolean.TRUE.equals(enableAuth == false) || mustInput) continue;
            throw new DefinedQueryRuntimeException("\u67e5\u8be2\u6761\u4ef6\u4e2d\u7684 " + param.getName() + " \u5b57\u6bb5\u914d\u7f6e\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u5728\u542f\u7528\u6743\u9650/\u8fc7\u6ee4\u6761\u4ef6\u6709\u503c/\u9ed8\u8ba4\u503c\u6709\u8fc7\u6ee4/\u65f6\uff0c\u8bf7\u914d\u7f6e\u201c\u5355\u503c+\u5fc5\u586b\u201c\u6216\u8005\u201c\u591a\u503c+\u975e\u5fc5\u586b/\u5fc5\u586b");
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void templateContentUpdate(TemplateContentVO templateContentVO) {
        String id = templateContentVO.getTemplate().getId();
        TemplateInfoVO templatesByCode = this.queryTemplateInfoDao.getTemplatesByCode(templateContentVO.getTemplate().getCode());
        if (templatesByCode != null && !templatesByCode.getId().equals(id)) {
            throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u4fee\u6539\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6");
        }
        if (id == null) {
            throw new DefinedQueryRuntimeException("templateId \u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (templateContentVO.getTemplate() != null) {
            this.queryTemplateInfoDao.update(templateContentVO.getTemplate());
        }
        if (templateContentVO.getDataSourceSet() != null && !DCQueryStringHandle.isEmpty(templateContentVO.getDataSourceSet().getDefineSql())) {
            templateContentVO.getDataSourceSet().setTemplateId(id);
            this.queryTemplateDataSourceSetDao.save(templateContentVO.getDataSourceSet());
        }
        this.templateDesignService.saveTemplate(QueryUtils.TemplateContentVO2QueryTemplate(templateContentVO), false);
    }

    @Override
    public List<TableHeaderVO> getTableHeader(String templateId) {
        LinkedList<TableHeaderVO> header = new LinkedList<TableHeaderVO>();
        QueryTemplate template = this.templateDesignService.getTemplate(templateId);
        List fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
        for (int i = 0; i < fields.size(); ++i) {
            TableHeaderVO column;
            TemplateFieldSettingVO field = (TemplateFieldSettingVO)fields.get(i);
            if (!field.isVisibleFlag()) continue;
            String title = field.getTitle();
            if (DCQueryStringHandle.isEmpty(title)) {
                return null;
            }
            if (title.contains("|")) {
                int indexOf = title.indexOf("|");
                String firstColumn = title.substring(0, indexOf);
                column = this.fieldToTableHeaderVO(field, firstColumn);
                i = this.setChildColumn(i, fields, firstColumn, column);
            } else {
                column = this.fieldToTableHeaderVO(field, title);
            }
            header.add(column);
        }
        return header;
    }

    @Override
    public List<TableHeaderVO> getPreviewTableHeader(List<TemplateFieldSettingVO> fields) {
        LinkedList<TableHeaderVO> header = new LinkedList<TableHeaderVO>();
        for (int i = 0; i < fields.size(); ++i) {
            TableHeaderVO column;
            TemplateFieldSettingVO field = fields.get(i);
            if (!field.isVisibleFlag()) continue;
            String title = field.getTitle();
            if (DCQueryStringHandle.isEmpty(title)) {
                return null;
            }
            if (title.contains("|")) {
                int indexOf = title.indexOf("|");
                String firstColumn = title.substring(0, indexOf);
                column = this.fieldToTableHeaderVO(field, firstColumn);
                i = this.setChildColumn(i, fields, firstColumn, column);
            } else {
                column = this.fieldToTableHeaderVO(field, title);
            }
            header.add(column);
        }
        return header;
    }

    @Override
    public TemplateContentVO getTemplateContent(String templateId) {
        QueryTemplate template = this.templateDesignService.getTemplate(templateId);
        if (template == null) {
            return null;
        }
        return QueryUtils.QueryTemplate2TemplateContentVO(template);
    }

    @Override
    public List<TemplateParamsVO> getTemplateParams(String templateId) {
        QueryTemplate template = this.templateDesignService.getTemplate(templateId);
        List paramsVOList = template.getDataSourceSet().getParams();
        if (paramsVOList == null || paramsVOList.isEmpty()) {
            return new ArrayList<TemplateParamsVO>();
        }
        for (TemplateParamsVO paramsVO : paramsVOList) {
            if (DCQueryStringHandle.isEmpty(paramsVO.getDefaultValue())) continue;
            if (!this.isJsonStr(paramsVO.getDefaultValue())) {
                Object formulaEvaluateData = FormulaExecuteHandlerUtil.getFormulaEvaluateData(paramsVO.getDefaultValue(), paramsVO.getParamType());
                paramsVO.setDefaultValue(formulaEvaluateData == null ? "" : formulaEvaluateData.toString());
                continue;
            }
            ScopeDefaultDTO scopeDefaultDTO = (ScopeDefaultDTO)JSONUtil.parseObject((String)paramsVO.getDefaultValue(), ScopeDefaultDTO.class);
            Object startValue = FormulaExecuteHandlerUtil.getFormulaEvaluateData(scopeDefaultDTO.getStart(), paramsVO.getParamType());
            Object endValue = FormulaExecuteHandlerUtil.getFormulaEvaluateData(scopeDefaultDTO.getEnd(), paramsVO.getParamType());
            scopeDefaultDTO.setStart(startValue == null ? "" : startValue.toString());
            scopeDefaultDTO.setEnd(endValue == null ? "" : endValue.toString());
            paramsVO.setDefaultValue(JSONUtil.toJSONString((Object)scopeDefaultDTO));
        }
        return paramsVOList;
    }

    private boolean isJsonStr(String defaultValue) {
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

    @Override
    public ConfigureExportVO configureExport(List<String> templateIds) {
        ConfigureExportVO configureExportVO = new ConfigureExportVO();
        configureExportVO.setMenuTree(this.menuTreeService.treeInit(templateIds));
        LinkedList<TemplateContentVO> templateContents = new LinkedList<TemplateContentVO>();
        for (String templateId : templateIds) {
            templateContents.add(this.getTemplateContent(templateId));
        }
        configureExportVO.setTemplateContents(templateContents);
        return configureExportVO;
    }

    @Override
    public ConfigureExportVO configureImportPreview(MultipartFile uploadFile) {
        String filename = uploadFile.getOriginalFilename();
        if (StringUtils.hasText(filename) && !filename.endsWith(".txt") && !filename.endsWith(".json")) {
            throw new DefinedQueryRuntimeException("\u5bfc\u5165\u6587\u4ef6\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5\u5bfc\u5165\u6587\u4ef6\uff01");
        }
        String configureExportJsonString = this.readFileToString(uploadFile);
        try {
            return (ConfigureExportVO)JSONUtil.parseObject((String)configureExportJsonString, ConfigureExportVO.class);
        }
        catch (Exception e) {
            throw new DefinedQueryRuntimeException("\u5bfc\u5165\u6587\u4ef6\u5185\u5bb9\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5\u5bfc\u5165\u6587\u4ef6\uff01");
        }
    }

    @Override
    public QueryConfigureImportVO configureImport(ConfigureExportVO configureExportVO) {
        QueryConfigureImportVO queryConfigureImportVO = new QueryConfigureImportVO();
        ArrayList<QueryConfigureImportVO.ImportResult> successList = new ArrayList<QueryConfigureImportVO.ImportResult>();
        ArrayList failureList = new ArrayList();
        List templateContents = configureExportVO.getTemplateContents();
        if (CollectionUtils.isEmpty(templateContents)) {
            return queryConfigureImportVO;
        }
        HashMap<String, TemplateInfoVO> templateCodeToVoMap = new HashMap<String, TemplateInfoVO>();
        String importStrategy = configureExportVO.getImportStrategy();
        ArrayList<String> skipImportCodeList = new ArrayList<String>();
        for (TemplateContentVO templateContentVO : templateContents) {
            QueryConfigureImportVO.ImportResult importResult = new QueryConfigureImportVO.ImportResult();
            String msgPrefix = null;
            try {
                TemplateInfoVO templateInfoParam = templateContentVO.getTemplate();
                String title = templateInfoParam.getTitle();
                String code = templateInfoParam.getCode();
                msgPrefix = "\u67e5\u8be2\u5b9a\u4e49\uff1a" + title + "(" + code + ") ";
                if (templateContentVO.getDataSourceSet() == null) {
                    this.dealImportResult(msgPrefix + "\u5bfc\u5165\u6587\u4ef6\u4e2d\u6570\u636e\u6e90\u4fe1\u606f\u4e0d\u5b58\u5728\u8df3\u8fc7\u5bfc\u5165", importResult, successList);
                    continue;
                }
                String templateCode = templateContentVO.getTemplate().getCode();
                String sql = templateContentVO.getDataSourceSet().getDefineSql();
                this.sqlQueryServiceImpl.verifySpecialSQL(sql);
                templateCodeToVoMap.put(templateCode, templateContentVO.getTemplate());
                QueryTemplate queryTemplate = QueryUtils.TemplateContentVO2QueryTemplate(templateContentVO);
                importResult = ObjectUtils.isEmpty(importStrategy) ? this.templateDesignService.importQueryTemplate(queryTemplate, templateInfoParam) : this.templateDesignService.importQueryTemplateUseStrategy(queryTemplate, importStrategy, templateInfoParam, skipImportCodeList);
                successList.add(importResult);
            }
            catch (Exception e) {
                this.dealImportResult(msgPrefix + "\u5bfc\u5165\u8fc7\u7a0b\u51fa\u9519\uff1a" + e.getMessage(), importResult, failureList);
                logger.error(e.getMessage(), e);
            }
        }
        try {
            this.menuTreeService.saveMenuTree(configureExportVO.getMenuTree(), templateCodeToVoMap, skipImportCodeList, new HashMap<String, String>());
            this.queryCacheManage.clearTreeCache();
        }
        catch (Exception e) {
            QueryConfigureImportVO.ImportResult importResult = new QueryConfigureImportVO.ImportResult();
            this.dealImportResult("\u5bfc\u5165\u8fc7\u7a0b\u4e2d\u7ef4\u62a4\u5b9a\u4e49\u5206\u7ec4\u4fe1\u606f\u51fa\u9519\uff0c\u8bf7\u68c0\u67e5\u5bfc\u5165\u6570\u636e\uff1a" + e.getMessage(), importResult, failureList);
            logger.error(e.getMessage(), e);
        }
        queryConfigureImportVO.setSuccessInfo(successList);
        queryConfigureImportVO.setFailureInfo(failureList);
        return queryConfigureImportVO;
    }

    private void dealImportResult(String msg, QueryConfigureImportVO.ImportResult importResult, List<? super QueryConfigureImportVO.ImportResult> list) {
        if (Objects.nonNull(importResult) && Objects.nonNull(list)) {
            importResult.setMsg(msg);
            list.add((QueryConfigureImportVO.ImportResult)importResult);
        }
    }

    private String readFileToString(MultipartFile uploadFile) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            int length;
            InputStream inputStream = uploadFile.getInputStream();
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
        catch (IOException e) {
            throw new DefinedQueryRuntimeException("\u8bfb\u53d6\u6587\u4ef6\u5931\u8d25!", (Throwable)e);
        }
    }

    private int setChildColumn(int i, List<TemplateFieldSettingVO> fields, String firstColumn, TableHeaderVO column) {
        LinkedList<TableHeaderVO> child = new LinkedList<TableHeaderVO>();
        LinkedList<TemplateFieldSettingVO> childColumns = new LinkedList<TemplateFieldSettingVO>();
        while (i < fields.size()) {
            TemplateFieldSettingVO field = fields.get(i);
            if (field.isVisibleFlag()) {
                String title = field.getTitle();
                if (!title.startsWith(firstColumn + "|")) break;
                field.setTitle(title.substring(firstColumn.length() + 1));
                childColumns.add(field);
            }
            ++i;
        }
        for (int j = 0; j < childColumns.size(); ++j) {
            TableHeaderVO childcolumn;
            TemplateFieldSettingVO childField = (TemplateFieldSettingVO)childColumns.get(j);
            String childtitle = childField.getTitle();
            if (childtitle.contains("|")) {
                int indexOf = childtitle.indexOf("|");
                String childFirst = childtitle.substring(0, indexOf);
                childcolumn = this.fieldToTableHeaderVO(childField, childFirst);
                j = this.setChildColumn(j, childColumns, childFirst, childcolumn);
            } else {
                childcolumn = this.fieldToTableHeaderVO(childField, childtitle);
            }
            child.add(childcolumn);
        }
        column.setChildren(child);
        return i - 1;
    }

    private TableHeaderVO fieldToTableHeaderVO(TemplateFieldSettingVO field, String columnName) {
        TableHeaderVO column = new TableHeaderVO();
        if (!DCQueryStringHandle.isEmpty(field.getAlign())) {
            column.setAlign(field.getAlign());
        } else if (ParamTypeEnum.NUMBER.getTypeName().equals(field.getDataType())) {
            column.setAlign(AlignEnum.RIGHT.getName());
        }
        String name = field.getName();
        if (field.getTitle().contains("|")) {
            column.setAlign(AlignEnum.CENTER.getName());
        } else {
            column.setKey(name);
        }
        column.setTitle(columnName);
        if (AutoWidthTypeEnum.REGULAR.getName().equals(field.getAutoWidth())) {
            column.setWidth(field.getWidth());
        } else {
            Set<String> fixedColumn = QueryTemplateConst.getFixedColumn();
            if (fixedColumn.contains(columnName.toUpperCase()) || fixedColumn.contains(name.toUpperCase())) {
                column.setWidth("200px");
            } else {
                ParamTypeEnum paramType = ParamTypeEnum.val((String)field.getDataType());
                switch (paramType) {
                    case STRING: 
                    case NUMBER: 
                    case INTEGER: 
                    case BOOL: {
                        column.setWidth("128px");
                        break;
                    }
                    case DATE: {
                        column.setWidth("122px");
                        break;
                    }
                }
            }
        }
        column.setFieldSetting(field);
        return column;
    }

    @Override
    public TemplateContentVO getTemplateContentByCode(String templateCode) {
        QueryTemplate template = this.templateDesignService.getTemplateByCode(templateCode);
        if (template == null) {
            return null;
        }
        return QueryUtils.QueryTemplate2TemplateContentVO(template);
    }

    @Override
    public List<FetchQueryFiledVO> getSimpleTemplateParams(String templateCode) {
        QueryTemplate template = this.templateDesignService.getTemplateByCode(templateCode);
        List params = template.getDataSourceSet().getParams();
        return params.stream().map(paramsVO -> {
            FetchQueryFiledVO queryFiledVO = new FetchQueryFiledVO();
            queryFiledVO.setName(paramsVO.getName());
            queryFiledVO.setTitle(paramsVO.getTitle());
            queryFiledVO.setNeedFlag(paramsVO.isMustInput());
            return queryFiledVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FetchQueryFiledVO> getSimpleTemplateFields(String templateCode) {
        QueryTemplate template = this.templateDesignService.getTemplateByCode(templateCode);
        QueryFieldsPlugin pluginByName = (QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class);
        return pluginByName.getFields().stream().map(paramsVO -> {
            FetchQueryFiledVO queryFiledVO = new FetchQueryFiledVO();
            queryFiledVO.setName(paramsVO.getName());
            queryFiledVO.setTitle(paramsVO.getTitle());
            queryFiledVO.setNeedFlag(paramsVO.isVisibleFlag());
            return queryFiledVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TemplateRelateQueryVO> getTemplateRelateQuery(String templateId) {
        QueryTemplate template = this.templateDesignService.getTemplate(templateId);
        QueryRelatePlugin pluginByName = (QueryRelatePlugin)template.getPluginByName(PluginEnum.queryRelate.name(), QueryRelatePlugin.class);
        return pluginByName.getRelateQuerys();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String templateCopy(String sourceTemplateId, String newGroupId, TemplateInfoVO newTemplateInfoVO) {
        TemplateInfoVO sourceTemplateInfo = this.queryTemplateInfoDao.getTemplatesById(sourceTemplateId);
        if (sourceTemplateInfo.getCode().equals(newTemplateInfoVO.getCode())) {
            throw new DefinedQueryRuntimeException("\u65b0\u6a21\u677f\u7684\u6807\u8bc6\u4e0d\u80fd\u4e0e\u590d\u5236\u524d\u6a21\u677f\u6807\u8bc6\u76f8\u540c");
        }
        if (sourceTemplateInfo.getTitle().equals(newTemplateInfoVO.getTitle())) {
            throw new DefinedQueryRuntimeException("\u65b0\u6a21\u677f\u7684\u6807\u8bc6\u4e0d\u80fd\u4e0e\u590d\u5236\u524d\u6a21\u677f\u6807\u9898\u76f8\u540c");
        }
        TemplateInfoVO templatesByCode = this.queryTemplateInfoDao.getTemplatesByCode(newTemplateInfoVO.getCode());
        if (templatesByCode != null) {
            throw new DefinedQueryRuntimeException("\u67e5\u8be2\u5b9a\u4e49\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728\uff0c\u4e0d\u5141\u8bb8\u4fdd\u5b58");
        }
        String newTemplateId = DCQueryUUIDUtil.getUUIDStr();
        this.copyTemplateInfo(newGroupId, newTemplateInfoVO, sourceTemplateInfo, newTemplateId);
        QueryTemplateDesignVO templateDesignData = this.templateDesignService.getTemplateDesignData(sourceTemplateId);
        templateDesignData.setId(newTemplateId);
        this.templateDesignService.saveTemplateDesign(templateDesignData);
        this.queryCacheManage.clearTreeCache();
        return newTemplateId;
    }

    private void copyTemplateInfo(String newGroupId, TemplateInfoVO newTemplateInfoVO, TemplateInfoVO sourceTemplateInfo, String newTemplateId) {
        newTemplateInfoVO.setId(newTemplateId);
        newTemplateInfoVO.setDatasourceCode(sourceTemplateInfo.getDatasourceCode());
        newTemplateInfoVO.setColumnLayout(sourceTemplateInfo.getColumnLayout());
        newTemplateInfoVO.setType(sourceTemplateInfo.getType());
        newTemplateInfoVO.setGroupId(newGroupId);
        newTemplateInfoVO.setSortOrder(Integer.valueOf(this.queryTemplateInfoDao.getMaxSortOrderByGroupId(newGroupId) + 1));
        this.queryTemplateInfoDao.save(newTemplateInfoVO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String templateMove(String sourceTemplateId, String newGroupId) {
        int sortOrder = this.queryTemplateInfoDao.getMaxSortOrderByGroupId(newGroupId) + 1;
        this.queryTemplateInfoDao.updateGroupIdAndSortOrderByTemplateId(sourceTemplateId, newGroupId, sortOrder);
        this.queryCacheManage.clearTreeCache();
        return sourceTemplateId;
    }

    @Override
    public List<TemplateInfoVO> getTemplatesByGroupId(String groupId) {
        return this.queryTemplateInfoDao.getTemplatesByGroupId(groupId);
    }

    @Override
    public TemplateInfoVO getTemplatesByCode(String code) {
        return this.queryTemplateInfoDao.getTemplatesByCode(code);
    }
}

