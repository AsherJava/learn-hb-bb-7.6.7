/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.sql.enumerate.ModeOperatorEnum
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.QueryRelatePlugin
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.plugin.ViewSetPlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  org.apache.shiro.util.Assert
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.query.common.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.query.common.service.UpgradeService;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.enumerate.ModeOperatorEnum;
import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.template.dao.QueryTemplateDataSourceSetDao;
import com.jiuqi.va.query.template.dao.QueryTemplateFieldSettingDao;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.dao.QueryTemplateParamsDao;
import com.jiuqi.va.query.template.dao.QueryTemplateRelateQueryDao;
import com.jiuqi.va.query.template.dao.QueryTemplateToolbarInfoDao;
import com.jiuqi.va.query.template.dto.ScopeDefaultDTO;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.plugin.ViewSetPlugin;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UpgradeServiceImpl
implements UpgradeService {
    public static final Logger logger = LoggerFactory.getLogger(UpgradeServiceImpl.class);
    public static final String AND = " and ";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    QueryTemplateParamsDao queryTemplateParamsDao;
    @Autowired
    QueryTemplateInfoDao templateInfoDao;
    @Autowired
    QueryTemplateDataSourceSetDao queryTemplateDataSourceSetDao;
    @Autowired
    private QueryTemplateFieldSettingDao queryTemplateFieldSettingDao;
    @Autowired
    private QueryTemplateInfoDao queryTemplateInfoDao;
    @Autowired
    private QueryTemplateToolbarInfoDao queryTemplateToolDao;
    @Autowired
    private QueryTemplateRelateQueryDao queryTemplateRelateQueryDao;
    @Autowired
    private TemplateDesignService templateDesignService;

    private TransformResult transformScope(String defineSql, TemplateParamsVO paramsVO) {
        TransformResult ret = new TransformResult();
        String defaultValue = paramsVO.getDefaultValue();
        String refColumnName = paramsVO.getRefColumnName();
        String end = null;
        String start = null;
        if (this.isJsonStr(defaultValue)) {
            ScopeDefaultDTO scopeDefaultDTO = (ScopeDefaultDTO)JSONUtil.parseObject((String)paramsVO.getDefaultValue(), ScopeDefaultDTO.class);
            start = scopeDefaultDTO.getStart();
            end = scopeDefaultDTO.getEnd();
        }
        paramsVO.setRefColumnName("");
        paramsVO.setModeOperator("");
        TemplateParamsVO startParam = new TemplateParamsVO();
        BeanUtils.copyProperties(paramsVO, startParam);
        startParam.setName("start_" + paramsVO.getName());
        startParam.setTitle(paramsVO.getTitle() + "\u8d77\u59cb\u503c");
        startParam.setDefaultValue(start);
        startParam.setMode(QueryModeEnum.singleData.getModeSign());
        ArrayList<TemplateParamsVO> updates = new ArrayList<TemplateParamsVO>();
        updates.add(startParam);
        TemplateParamsVO endParam = new TemplateParamsVO();
        BeanUtils.copyProperties(paramsVO, endParam);
        endParam.setName("end_" + paramsVO.getName());
        endParam.setTitle(paramsVO.getTitle() + "\u7ed3\u675f\u503c");
        endParam.setDefaultValue(end);
        endParam.setId(DCQueryUUIDUtil.getUUIDStr());
        endParam.setMode(QueryModeEnum.singleData.getModeSign());
        StringBuilder replacement = new StringBuilder();
        String startVar = "@" + startParam.getName();
        String endVar = "@" + endParam.getName();
        replacement.append(AND).append(refColumnName).append(" >= ").append(startVar).append(AND).append(refColumnName).append(" <= ").append(endVar);
        updates.add(endParam);
        ret.setDefineSql(defineSql.replaceAll("@\\{" + paramsVO.getName() + "}", replacement.toString()));
        ret.setParamsVOS(updates);
        return ret;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void parserDefine(String templateId) {
        if (!StringUtils.hasText(templateId)) {
            return;
        }
        TemplateDataSourceSetVO templateDataSourceSetVO = this.queryTemplateDataSourceSetDao.getByTemplateId(templateId);
        if (templateDataSourceSetVO == null) {
            throw new DefinedQueryRuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u67e5\u8be2\u5b9a\u4e49\u6a21\u677f");
        }
        String defineSql = templateDataSourceSetVO.getDefineSql();
        List<TemplateParamsVO> paramsVOS = this.queryTemplateParamsDao.getByTemplateId(templateId);
        try {
            ArrayList<TemplateParamsVO> resultVOs = new ArrayList<TemplateParamsVO>();
            for (TemplateParamsVO paramsVO : paramsVOS) {
                if (!StringUtils.hasText(paramsVO.getMode())) {
                    throw new DefinedQueryRuntimeException("\u67e5\u8be2\u6a21\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
                }
                paramsVO.setName(paramsVO.getName().trim());
                QueryModeEnum queryModeEnum = QueryModeEnum.valueOf((String)paramsVO.getMode());
                if (queryModeEnum.equals((Object)QueryModeEnum.singleData)) {
                    defineSql = this.transformSingleMode(defineSql, paramsVO);
                    paramsVO.setRefColumnName("");
                    paramsVO.setModeOperator("");
                    resultVOs.add(paramsVO);
                    continue;
                }
                if (queryModeEnum.equals((Object)QueryModeEnum.mutileData)) {
                    defineSql = this.transformMulitMode(defineSql, paramsVO);
                    paramsVO.setRefColumnName("");
                    paramsVO.setModeOperator("");
                    resultVOs.add(paramsVO);
                    continue;
                }
                if (!queryModeEnum.equals((Object)QueryModeEnum.scope)) continue;
                TransformResult transformScope = this.transformScope(defineSql, paramsVO);
                defineSql = transformScope.getDefineSql();
                resultVOs.addAll(transformScope.getParamsVOS());
            }
            templateDataSourceSetVO.setDefineSql(defineSql);
            this.queryTemplateParamsDao.deleteByTemplateId(templateId);
            this.queryTemplateParamsDao.batchSave(resultVOs);
            this.queryTemplateDataSourceSetDao.update(templateDataSourceSetVO);
        }
        catch (Exception e) {
            TemplateInfoVO templateInfoVO = this.templateInfoDao.getTemplatesById(templateId);
            TemplateContentVO templateContentVO = new TemplateContentVO();
            templateContentVO.setTemplate(templateInfoVO);
            templateContentVO.setDataSourceSet(templateDataSourceSetVO);
            templateContentVO.setParams(paramsVOS);
            LogUtil.add((String)"\u67e5\u8be2\u5b9a\u4e49", (String)("\u5347\u7ea7\u5b9a\u4e49\u5931\u8d25" + e.getMessage()), (String)templateInfoVO.getId(), (String)templateInfoVO.getTitle(), (String)JSONUtil.toJSONString((Object)templateContentVO));
            throw new DefinedQueryRuntimeException("\u5347\u7ea7\u5b9a\u4e49\u5931\u8d25");
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void templateMigration(String templateId) {
        if (!StringUtils.hasText(templateId)) {
            return;
        }
        try {
            TemplateInfoVO templateInfoVO = this.queryTemplateInfoDao.getTemplatesById(templateId);
            TemplateDataSourceSetVO templateDataSourceSetVO = this.queryTemplateDataSourceSetDao.getByTemplateId(templateId);
            List<TemplateParamsVO> paramsVOS = this.queryTemplateParamsDao.getByTemplateId(templateId);
            List<TemplateFieldSettingVO> fieldSettingVOS = this.queryTemplateFieldSettingDao.getByTemplateId(templateId);
            List<TemplateToolbarInfoVO> toolbarInfoVOS = this.queryTemplateToolDao.getByTemplateId(templateId);
            List<TemplateRelateQueryVO> relateQueryVOS = this.queryTemplateRelateQueryDao.getRelateQueryByTemplateId(templateId);
            QueryTemplate queryTemplate = new QueryTemplate();
            queryTemplate.setCode(templateInfoVO.getCode());
            queryTemplate.setId(templateId);
            ArrayList<Object> plugins = new ArrayList<Object>();
            DataSourcePlugin dataSourcePlugin = new DataSourcePlugin();
            dataSourcePlugin.setDefineSql(templateDataSourceSetVO.getDefineSql());
            dataSourcePlugin.setParams(paramsVOS);
            plugins.add(dataSourcePlugin);
            QueryFieldsPlugin queryFieldsPlugin = new QueryFieldsPlugin();
            queryFieldsPlugin.setFields(fieldSettingVOS);
            plugins.add(queryFieldsPlugin);
            QueryRelatePlugin queryRelatePlugin = new QueryRelatePlugin();
            queryRelatePlugin.setRelateQuerys(relateQueryVOS);
            plugins.add(queryRelatePlugin);
            ToolBarPlugin toolBarPlugin = new ToolBarPlugin();
            toolBarPlugin.setTools(toolbarInfoVOS);
            plugins.add(toolBarPlugin);
            ViewSetPlugin viewSetPlugin = new ViewSetPlugin();
            viewSetPlugin.putViewSet("conditionDisplay", (Object)templateInfoVO.getConditionDisplay());
            viewSetPlugin.putViewSet("columnLayout", (Object)templateInfoVO.getColumnLayout());
            plugins.add(viewSetPlugin);
            queryTemplate.setPlugins(plugins);
            this.templateDesignService.saveTemplate(queryTemplate, false);
        }
        catch (Exception e) {
            logger.error("\u81ea\u5b9a\u4e49\u67e5\u8be2\u5347\u7ea7\u5931\u8d25, templateId:" + templateId, e);
            LogUtil.add((String)"\u81ea\u5b9a\u4e49\u67e5\u8be2", (String)"\u5347\u7ea7", (String)templateId, (String)"", (String)"");
        }
    }

    private String transformMulitMode(String defineSql, TemplateParamsVO paramsVO) {
        String name = paramsVO.getName();
        String refColumnName = paramsVO.getRefColumnName();
        Assert.hasText((String)paramsVO.getModeOperator(), (String)"\u64cd\u4f5c\u7b26\u4e0d\u80fd\u4e3a\u7a7a");
        ModeOperatorEnum modeOperatorEnum = ModeOperatorEnum.valueOf((String)paramsVO.getModeOperator());
        StringBuilder replacement = new StringBuilder(AND).append(refColumnName);
        String userVar = "@" + name;
        switch (modeOperatorEnum) {
            case in: {
                replacement.append(" in ").append(userVar);
                break;
            }
            case notIn: {
                replacement.append(" not in ").append(userVar);
                break;
            }
            case like: 
            case leftLike: 
            case notLike: 
            case notLeftLike: {
                replacement.append(" in ").append(userVar);
                break;
            }
            default: {
                throw new DefinedQueryRuntimeException("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c\u7b26" + modeOperatorEnum.getOperatorName());
            }
        }
        return defineSql.replaceAll("@\\{" + name + "}", replacement.toString());
    }

    private String transformSingleMode(String defineSql, TemplateParamsVO paramsVO) {
        String name = paramsVO.getName();
        String refColumnName = paramsVO.getRefColumnName();
        Assert.hasText((String)paramsVO.getModeOperator(), (String)"\u64cd\u4f5c\u7b26\u4e0d\u80fd\u4e3a\u7a7a");
        ModeOperatorEnum modeOperatorEnum = ModeOperatorEnum.valueOf((String)paramsVO.getModeOperator());
        StringBuilder replacement = new StringBuilder(AND).append(refColumnName);
        String userVar = "@" + name;
        switch (modeOperatorEnum) {
            case EQ: {
                replacement.append(" = ").append(userVar);
                break;
            }
            case NE: {
                replacement.append(" != ").append(userVar);
                break;
            }
            case LT: {
                replacement.append(" < ").append(userVar);
                break;
            }
            case LE: {
                replacement.append(" <= ").append(userVar);
                break;
            }
            case GT: {
                replacement.append(" > ").append(userVar);
                break;
            }
            case GE: {
                replacement.append(" >= ").append(userVar);
                break;
            }
            case like: {
                replacement.append(" like concat(concat('%', ").append(userVar).append("), '%') ");
                break;
            }
            case leftLike: {
                replacement.append(" like concat(").append(userVar).append(", '%') ");
                break;
            }
            case notLike: {
                replacement.append(" not like concat(concat('%', ").append(userVar).append("), '%') ");
                break;
            }
            case notLeftLike: {
                replacement.append(" not like concat(").append(userVar).append(", '%') ");
                break;
            }
            default: {
                throw new DefinedQueryRuntimeException("\u4e0d\u652f\u6301\u7684\u64cd\u4f5c\u7b26" + modeOperatorEnum.getOperatorName());
            }
        }
        return defineSql.replaceAll("@\\{" + name + "}", replacement.toString());
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

    static class TransformResult {
        private String defineSql;
        private List<TemplateParamsVO> paramsVOS;

        TransformResult() {
        }

        public String getDefineSql() {
            return this.defineSql;
        }

        public void setDefineSql(String defineSql) {
            this.defineSql = defineSql;
        }

        public List<TemplateParamsVO> getParamsVOS() {
            return this.paramsVOS;
        }

        public void setParamsVOS(List<TemplateParamsVO> paramsVOS) {
            this.paramsVOS = paramsVOS;
        }
    }
}

