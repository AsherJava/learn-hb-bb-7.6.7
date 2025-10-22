/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.task.service.IFormSchemeService
 *  com.jiuqi.nr.task.web.vo.FormSchemeVO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.transaction.support.TransactionTemplate
 */
package com.jiuqi.gcreport.formulaschemeconfig.transfermodule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.service.IFormSchemeService;
import com.jiuqi.nr.task.web.vo.FormSchemeVO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class FormulaSchemeConfigTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaSchemeConfigTransferModule.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private TransactionTemplate transactionTemplate;
    private static final String MODULE_NAME_FORMULASCHEMECONFIG = "MODULE_FORMULASCHEMECONFIG";
    private static final String MODULE_ID_FORMULASCHEMECONFIG = "MODULE_ID_FORMULASCHEMECONFIG";
    private static final String MODULE_TITLE_FORMULASCHEMECONFIG = "\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848";
    private static final String CATEGORY_NAME_FORMULASCHEMECONFIG = "CATEGORY_FORMULASCHEMECONFIG";
    private static final String CATEGORY_TITLE_FORMULASCHEMECONFIG = "\u4efb\u52a1\u5217\u8868";
    private static final String NODE_ID_PLACEHOLDER = "#";
    private static final String NODE_ID_SPLIT_CHAR = "/";
    private static final String NODE_ID_SUFFIX = "/t";
    public static final String ENTITY_NODE_ID_SPLIT_CHAR = ":";
    private static final String NODE_ID_TEMPLATE = "%s/%s/t";

    private static Level getLevel(String nodeId) {
        int levelCode;
        if (FormulaSchemeConfigTransferModule.isInvalidNodeId(nodeId)) {
            throw new IllegalArgumentException("\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + nodeId);
        }
        String[] split = nodeId.split(NODE_ID_SPLIT_CHAR);
        String levelCodeStr = split[0];
        try {
            levelCode = Integer.parseInt(levelCodeStr);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u65e0\u6cd5\u89e3\u6790 levelCode\uff1a" + nodeId);
        }
        for (Level level : Level.values()) {
            if (level.code != levelCode) continue;
            return level;
        }
        throw new IllegalArgumentException("\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e" + nodeId);
    }

    private static boolean isInvalidNodeId(String nodeId) {
        if (!nodeId.endsWith(NODE_ID_SUFFIX)) {
            return true;
        }
        int splitIndex = nodeId.indexOf(NODE_ID_SPLIT_CHAR);
        if (splitIndex == -1 || splitIndex == 0) {
            return true;
        }
        String suffix = nodeId.substring(splitIndex + 1, nodeId.length() - NODE_ID_SUFFIX.length());
        return suffix.isEmpty();
    }

    private static String getResourceId(String nodeId) {
        if (FormulaSchemeConfigTransferModule.isInvalidNodeId(nodeId)) {
            throw new IllegalArgumentException("\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + nodeId);
        }
        String[] split = nodeId.split(NODE_ID_SPLIT_CHAR);
        return split[1];
    }

    private static String buildNodeId(Level level) {
        return FormulaSchemeConfigTransferModule.buildNodeId(level, null);
    }

    public static String buildNodeId(Level level, String id) {
        if (level == null) {
            throw new IllegalArgumentException("level \u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)id)) {
            return String.format(NODE_ID_TEMPLATE, level.code, NODE_ID_PLACEHOLDER);
        }
        return String.format(NODE_ID_TEMPLATE, level.code, id);
    }

    private String getIdEnitiyNodeId(Level level, String reportNodeId) {
        String ids = FormulaSchemeConfigTransferModule.getResourceId(reportNodeId);
        Assert.isTrue((boolean)ids.contains(ENTITY_NODE_ID_SPLIT_CHAR), (String)"\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        String[] idsArr = ids.split(ENTITY_NODE_ID_SPLIT_CHAR);
        Assert.isTrue((idsArr.length == 2 ? 1 : 0) != 0, (String)"\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        switch (level) {
            case FORM_SCHEME: {
                return idsArr[0];
            }
            case ENTITY: {
                return idsArr[1];
            }
        }
        throw new IllegalArgumentException("\u8282\u70b9\u7c7b\u578b\u4e0d\u6b63\u786e\uff01");
    }

    public String getModuleId() {
        return MODULE_ID_FORMULASCHEMECONFIG;
    }

    public String getName() {
        return MODULE_NAME_FORMULASCHEMECONFIG;
    }

    public String getTitle() {
        return MODULE_TITLE_FORMULASCHEMECONFIG;
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categories = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(CATEGORY_NAME_FORMULASCHEMECONFIG);
        category.setTitle(CATEGORY_TITLE_FORMULASCHEMECONFIG);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categories.add(category);
        return categories;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String nodeId) {
        if (CATEGORY_NAME_FORMULASCHEMECONFIG.equals(category) && StringUtils.isEmpty((String)nodeId)) {
            ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
            List allTaskDefines = this.runTimeAuthViewController.getAllReportTaskDefines();
            List filtedTaskDefines = allTaskDefines.stream().filter(Objects::nonNull).filter(taskDefine -> this.authorityProvider.canReadTask(taskDefine.getKey())).collect(Collectors.toList());
            for (TaskDefine taskDefine2 : filtedTaskDefines) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                String id = FormulaSchemeConfigTransferModule.buildNodeId(Level.TASK, taskDefine2.getKey());
                node.setId(id);
                node.setTitle(taskDefine2.getTitle());
                nodes.add(node);
            }
            return nodes;
        }
        if (!StringUtils.isEmpty((String)nodeId) && FormulaSchemeConfigTransferModule.getLevel(nodeId) == Level.TASK) {
            String taskId = FormulaSchemeConfigTransferModule.getResourceId(nodeId);
            ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
            List formSchemeDefines = this.iRunTimeViewController.listFormSchemeByTask(taskId);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                String id = FormulaSchemeConfigTransferModule.buildNodeId(Level.FORM_SCHEME, formSchemeDefine.getKey());
                node.setId(id);
                node.setParentId(nodeId);
                node.setTitle(formSchemeDefine.getTitle());
                nodes.add(node);
            }
            return nodes;
        }
        return Collections.emptyList();
    }

    public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        if (nodeId == null) {
            return null;
        }
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        Level level = FormulaSchemeConfigTransferModule.getLevel(nodeId);
        String resourceId = FormulaSchemeConfigTransferModule.getResourceId(nodeId);
        switch (level) {
            case FORM_SCHEME: {
                FormSchemeVO formScheme;
                try {
                    formScheme = this.formSchemeService.getFormScheme(resourceId);
                }
                catch (NullPointerException e) {
                    LOGGER.error(String.format("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01\u5f53\u524d\u62a5\u8868\u65b9\u6848\u3010%s\u3011\u4e0d\u5b58\u5728\uff01", resourceId), e);
                    throw new RuntimeException(String.format("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01\u5f53\u524d\u62a5\u8868\u65b9\u6848\u3010%s\u3011\u4e0d\u5b58\u5728\uff01", resourceId), e);
                }
                if (formScheme == null) {
                    return null;
                }
                node.setId(FormulaSchemeConfigTransferModule.buildNodeId(Level.FORM_SCHEME, resourceId));
                node.setTitle(formScheme.getTitle());
                node.setParentId(FormulaSchemeConfigTransferModule.buildNodeId(Level.TASK, formScheme.getTaskKey()));
                return node;
            }
            case TASK: {
                DesignTaskDefine taskDefine = this.designTimeViewController.getTask(resourceId);
                if (taskDefine == null) {
                    return null;
                }
                node.setId(FormulaSchemeConfigTransferModule.buildNodeId(Level.TASK, resourceId));
                node.setTitle(taskDefine.getTitle());
                return node;
            }
        }
        return null;
    }

    public VaParamTransferFolderNode addFolderNode(String category, VaParamTransferFolderNode node) {
        return super.addFolderNode(category, node);
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String nodeId) {
        if (!StringUtils.isEmpty((String)nodeId) && FormulaSchemeConfigTransferModule.getLevel(nodeId) == Level.FORM_SCHEME) {
            ArrayList<VaParamTransferBusinessNode> nodes = new ArrayList<VaParamTransferBusinessNode>();
            String formSchemeId = FormulaSchemeConfigTransferModule.getResourceId(nodeId);
            FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeId);
            TaskOrgLinkListStream taskOrgLinkListStream = this.iRunTimeViewController.listTaskOrgLinkStreamByTask(formSchemeDefine.getTaskKey());
            List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
            IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkList) {
                VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
                String entity = taskOrgLinkDefine.getEntity();
                if (StringUtils.isEmpty((String)entity)) continue;
                String id = FormulaSchemeConfigTransferModule.buildNodeId(Level.ENTITY, formSchemeId + ENTITY_NODE_ID_SPLIT_CHAR + entity);
                node.setId(id);
                if (!StringUtils.isEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                    node.setTitle(taskOrgLinkDefine.getEntityAlias());
                } else {
                    node.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
                }
                nodes.add(node);
            }
            return nodes;
        }
        return Collections.emptyList();
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.isEmpty((String)nodeId) && FormulaSchemeConfigTransferModule.getLevel(nodeId) == Level.ENTITY) {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            String formSchemeId = this.getIdEnitiyNodeId(Level.FORM_SCHEME, nodeId);
            String entityId = this.getIdEnitiyNodeId(Level.ENTITY, nodeId);
            node.setTypeTitle(MODULE_TITLE_FORMULASCHEMECONFIG);
            IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            IEntityDefine iEntityDefine = iEntityMetaService.queryEntity(entityId);
            if (iEntityDefine == null) {
                return null;
            }
            node.setName(iEntityDefine.getCode());
            node.setTitle(iEntityDefine.getTitle());
            node.setType(Level.ENTITY.name());
            node.setId(nodeId);
            node.setFolderId(FormulaSchemeConfigTransferModule.buildNodeId(Level.FORM_SCHEME, formSchemeId));
            return node;
        }
        return null;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
        Level level = FormulaSchemeConfigTransferModule.getLevel(nodeId);
        Assert.isTrue((level == Level.ENTITY ? 1 : 0) != 0, (String)"\u8282\u70b9\u7c7b\u578b\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        String formSchemeId = this.getIdEnitiyNodeId(Level.FORM_SCHEME, nodeId);
        String entityId = this.getIdEnitiyNodeId(Level.ENTITY, nodeId);
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        IEntityDefine iEntityDefine = iEntityMetaService.queryEntity(entityId);
        FormSchemeVO formScheme = this.formSchemeService.getFormScheme(formSchemeId);
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(formScheme.getTaskKey());
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        String title = taskDefine.getTitle() + " / " + formScheme.getTitle() + " / " + iEntityDefine.getTitle();
        node.setTitle(title);
        nodes.add(node);
        return nodes;
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        return super.getRelatedBusiness(category, nodeId);
    }

    public String getExportModelInfo(String category, String nodeId) {
        Level level = FormulaSchemeConfigTransferModule.getLevel(nodeId);
        Assert.isTrue((level == Level.ENTITY ? 1 : 0) != 0, (String)"\u8282\u70b9\u7c7b\u578b\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        String formSchemeId = this.getIdEnitiyNodeId(Level.FORM_SCHEME, nodeId);
        String entityId = this.getIdEnitiyNodeId(Level.ENTITY, nodeId);
        Map<String, List<NrFormulaSchemeConfigTableVO>> formulaSchemeConfigTableMap = this.formulaSchemeConfigService.queryTabSelectOrgIds(formSchemeId, entityId, null, true);
        return JSONUtil.toJSONString(formulaSchemeConfigTableMap);
    }

    public String getExportDataInfo(String category, String nodeId) {
        return super.getExportDataInfo(category, nodeId);
    }

    public void importModelInfo(String category, String info) {
        Map<String, List<NrFormulaSchemeConfigTableVO>> formulaSchemeConfigTableMap = FormulaSchemeConfigTransferModule.parseJson(info);
        this.transactionTemplate.execute(status -> {
            try {
                this.formulaSchemeConfigService.importFormulaSchemeConfig("batchStrategy", (List)formulaSchemeConfigTableMap.get("batchStrategy"));
                this.formulaSchemeConfigService.importFormulaSchemeConfig("batchUnit", (List)formulaSchemeConfigTableMap.get("batchUnit"));
            }
            catch (Exception e) {
                LOGGER.error("\u5bfc\u5165\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u5931\u8d25\uff01", e);
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    public void importDataInfo(String category, String targetId, String info) {
        super.importDataInfo(category, targetId, info);
    }

    public static Map<String, List<NrFormulaSchemeConfigTableVO>> parseJson(String jsonString) {
        try {
            return (Map)objectMapper.readValue(jsonString, (TypeReference)new TypeReference<Map<String, List<NrFormulaSchemeConfigTableVO>>>(){});
        }
        catch (IOException e) {
            throw new RuntimeException("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u5bfc\u5165\u6570\u636e\u89e3\u6790\u5931\u8d25", e);
        }
    }

    public static enum Level {
        TASK(63, "\u4efb\u52a1"),
        FORM_SCHEME(64, "\u62a5\u8868\u65b9\u6848"),
        ENTITY(65, "\u53e3\u5f84");

        final int code;
        final String name;

        private Level(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}

