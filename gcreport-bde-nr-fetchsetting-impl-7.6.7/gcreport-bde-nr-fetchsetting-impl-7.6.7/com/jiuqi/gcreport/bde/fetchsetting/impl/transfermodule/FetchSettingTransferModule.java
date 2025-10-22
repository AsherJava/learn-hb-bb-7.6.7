/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.task.service.IFormSchemeService
 *  com.jiuqi.nr.task.web.vo.FormSchemeItemVO
 *  com.jiuqi.nr.task.web.vo.FormSchemeVO
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.support.TransactionTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.transfermodule;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.task.service.IFormSchemeService;
import com.jiuqi.nr.task.web.vo.FormSchemeItemVO;
import com.jiuqi.nr.task.web.vo.FormSchemeVO;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class FetchSettingTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchSettingTransferModule.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private FetchFloatSettingDesDao fetchFloatSettingDesDao;
    @Autowired
    private TransactionTemplate transactionTemplate;
    private static final String MODULE_NAME_BDE_FETCHSETTING = "MODULE_BDE_FETCHSETTING";
    private static final String MODULE_ID_BDE_FETCHSETTING = "MODULE_ID_BDE_FETCHSETTING";
    private static final String MODULE_TITLE_BDE_FETCHSETTING = "BDE\u53d6\u6570\u8bbe\u7f6e";
    private static final String CATEGORY_NAME_BDE_FETCHSETTING = "CATEGORY_BDE_FETCHSETTING";
    private static final String CATEGORY_TITLE_BDE_FETCHSETTING = "\u4efb\u52a1\u5217\u8868";
    private static final String NODE_ID_PLACEHOLDER = "#";
    private static final String NODE_ID_SPLIT_CHAR = "_";
    private static final String NODE_ID_SUFFIX = "_t";
    public static final String REPORT_NODE_ID_SPLIT_CHAR = ":";
    private static final String NODE_ID_TEMPLATE = "%s_%s_t";

    private static Level getLevel(String nodeId) {
        int levelCode;
        if (FetchSettingTransferModule.isInvalidNodeId(nodeId)) {
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
        if (FetchSettingTransferModule.isInvalidNodeId(nodeId)) {
            throw new IllegalArgumentException("\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + nodeId);
        }
        String[] split = nodeId.split(NODE_ID_SPLIT_CHAR);
        return split[1];
    }

    private static String buildNodeId(Level level) {
        return FetchSettingTransferModule.buildNodeId(level, null);
    }

    public static String buildNodeId(Level level, String id) {
        if (level == null) {
            throw new IllegalArgumentException("level \u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((CharSequence)id)) {
            return String.format(NODE_ID_TEMPLATE, level.code, NODE_ID_PLACEHOLDER);
        }
        return String.format(NODE_ID_TEMPLATE, level.code, id);
    }

    private String getIdFromReportNodeId(Level level, String reportNodeId) {
        String ids = FetchSettingTransferModule.getResourceId(reportNodeId);
        Assert.isTrue((boolean)ids.contains(REPORT_NODE_ID_SPLIT_CHAR), (String)"\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        String[] idsArr = ids.split(REPORT_NODE_ID_SPLIT_CHAR);
        Assert.isTrue((idsArr.length == 2 ? 1 : 0) != 0, (String)"\u8282\u70b9 id \u683c\u5f0f\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        switch (level) {
            case FETCH_SCHEME: {
                return idsArr[0];
            }
            case REPORT: {
                return idsArr[1];
            }
        }
        throw new IllegalArgumentException("\u8282\u70b9\u7c7b\u578b\u4e0d\u6b63\u786e\uff01");
    }

    public String getModuleId() {
        return MODULE_ID_BDE_FETCHSETTING;
    }

    public String getName() {
        return MODULE_NAME_BDE_FETCHSETTING;
    }

    public String getTitle() {
        return MODULE_TITLE_BDE_FETCHSETTING;
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categories = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(CATEGORY_NAME_BDE_FETCHSETTING);
        category.setTitle(CATEGORY_TITLE_BDE_FETCHSETTING);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categories.add(category);
        return categories;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String nodeId) {
        if (CATEGORY_NAME_BDE_FETCHSETTING.equals(category) && StringUtils.isEmpty((CharSequence)nodeId)) {
            ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
            List taskDefines = this.designTimeViewController.listAllTask();
            for (DesignTaskDefine taskDefine : taskDefines) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                String id = FetchSettingTransferModule.buildNodeId(Level.TASK, taskDefine.getKey());
                node.setId(id);
                node.setTitle(taskDefine.getTitle());
                nodes.add(node);
            }
            return nodes;
        }
        if (!StringUtils.isEmpty((CharSequence)nodeId) && FetchSettingTransferModule.getLevel(nodeId) == Level.TASK) {
            String taskId = FetchSettingTransferModule.getResourceId(nodeId);
            ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
            List formSchemeItems = this.formSchemeService.queryByTask(taskId);
            for (FormSchemeItemVO formSchemeItem : formSchemeItems) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                String id = FetchSettingTransferModule.buildNodeId(Level.FORM_SCHEME, formSchemeItem.getKey());
                node.setId(id);
                node.setParentId(nodeId);
                node.setTitle(formSchemeItem.getTitle());
                nodes.add(node);
            }
            return nodes;
        }
        if (!StringUtils.isEmpty((CharSequence)nodeId) && FetchSettingTransferModule.getLevel(nodeId) == Level.FORM_SCHEME) {
            String formSchemeId = FetchSettingTransferModule.getResourceId(nodeId);
            ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
            List<FetchSchemeVO> fetchSchemes = this.fetchSchemeService.listFetchScheme(formSchemeId);
            for (FetchSchemeVO fetchScheme : fetchSchemes) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                String id = FetchSettingTransferModule.buildNodeId(Level.FETCH_SCHEME, fetchScheme.getId());
                node.setId(id);
                node.setParentId(nodeId);
                node.setTitle(fetchScheme.getName());
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
        Level level = FetchSettingTransferModule.getLevel(nodeId);
        String resourceId = FetchSettingTransferModule.getResourceId(nodeId);
        switch (level) {
            case FETCH_SCHEME: {
                FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(resourceId);
                if (fetchScheme == null) {
                    return null;
                }
                node.setId(FetchSettingTransferModule.buildNodeId(Level.FETCH_SCHEME, resourceId));
                node.setTitle(fetchScheme.getName());
                node.setParentId(FetchSettingTransferModule.buildNodeId(Level.FORM_SCHEME, fetchScheme.getFormSchemeId()));
                return node;
            }
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
                node.setId(FetchSettingTransferModule.buildNodeId(Level.FORM_SCHEME, resourceId));
                node.setTitle(formScheme.getTitle());
                node.setParentId(FetchSettingTransferModule.buildNodeId(Level.TASK, formScheme.getTaskKey()));
                return node;
            }
            case TASK: {
                DesignTaskDefine taskDefine = this.designTimeViewController.getTask(resourceId);
                if (taskDefine == null) {
                    return null;
                }
                node.setId(FetchSettingTransferModule.buildNodeId(Level.TASK, resourceId));
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
        if (!StringUtils.isEmpty((CharSequence)nodeId) && FetchSettingTransferModule.getLevel(nodeId) == Level.FETCH_SCHEME) {
            String fetchSchemeId = FetchSettingTransferModule.getResourceId(nodeId);
            FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
            String formSchemeId = fetchScheme.getFormSchemeId();
            ArrayList<VaParamTransferBusinessNode> nodes = new ArrayList<VaParamTransferBusinessNode>();
            ArrayList formDefines = new ArrayList();
            try {
                List allFormGroupsInFormScheme = this.designTimeViewController.listFormGroupByFormScheme(formSchemeId);
                for (DesignFormGroupDefine formGroupDefine : allFormGroupsInFormScheme) {
                    List formDefinesInGroup = this.designTimeViewController.listFormByGroup(formGroupDefine.getKey());
                    formDefines.addAll(formDefinesInGroup);
                }
            }
            catch (Exception e) {
                LOGGER.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5173\u8054\u7684\u6240\u6709\u8868\u5355\u5b9a\u4e49\u5217\u8868\u5931\u8d25\uff01", e);
                throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5173\u8054\u7684\u6240\u6709\u8868\u5355\u5b9a\u4e49\u5217\u8868\u5931\u8d25\uff01", e);
            }
            for (FormDefine formDefine : formDefines) {
                VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
                String id = FetchSettingTransferModule.buildNodeId(Level.REPORT, fetchSchemeId + REPORT_NODE_ID_SPLIT_CHAR + formDefine.getKey());
                node.setId(id);
                node.setTitle(formDefine.getTitle());
                nodes.add(node);
            }
            return nodes;
        }
        return Collections.emptyList();
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.isEmpty((CharSequence)nodeId) && FetchSettingTransferModule.getLevel(nodeId) == Level.REPORT) {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            String fetchSchemeId = this.getIdFromReportNodeId(Level.FETCH_SCHEME, nodeId);
            String formId = this.getIdFromReportNodeId(Level.REPORT, nodeId);
            node.setTypeTitle(MODULE_TITLE_BDE_FETCHSETTING);
            DesignFormDefine formDefine = this.designTimeViewController.getForm(formId);
            if (formDefine == null) {
                return null;
            }
            node.setName(formDefine.getFormCode());
            node.setTitle(formDefine.getTitle());
            node.setType(Level.REPORT.name());
            node.setId(nodeId);
            node.setFolderId(FetchSettingTransferModule.buildNodeId(Level.FETCH_SCHEME, fetchSchemeId));
            return node;
        }
        return null;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        Level level = FetchSettingTransferModule.getLevel(nodeId);
        Assert.isTrue((level == Level.REPORT ? 1 : 0) != 0, (String)"\u8282\u70b9\u7c7b\u578b\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        String fetchSchemeId = this.getIdFromReportNodeId(Level.FETCH_SCHEME, nodeId);
        String formId = this.getIdFromReportNodeId(Level.REPORT, nodeId);
        DesignFormDefine formDefine = this.designTimeViewController.getForm(formId);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
        FormSchemeVO formScheme = this.formSchemeService.getFormScheme(fetchScheme.getFormSchemeId());
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(formScheme.getTaskKey());
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        ArrayList<VaParamTransferFolderNode> nodes = new ArrayList<VaParamTransferFolderNode>();
        String title = taskDefine.getTitle() + " / " + formScheme.getTitle() + " / " + fetchScheme.getName() + " / " + formDefine.getTitle();
        node.setTitle(title);
        nodes.add(node);
        return nodes;
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        return super.getRelatedBusiness(category, nodeId);
    }

    public String getExportModelInfo(String category, String nodeId) {
        Level level = FetchSettingTransferModule.getLevel(nodeId);
        Assert.isTrue((level == Level.REPORT ? 1 : 0) != 0, (String)"\u8282\u70b9\u7c7b\u578b\u4e0d\u6b63\u786e\uff01", (Object[])new Object[0]);
        String fetchSchemeId = this.getIdFromReportNodeId(Level.FETCH_SCHEME, nodeId);
        String formId = this.getIdFromReportNodeId(Level.REPORT, nodeId);
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(fetchSchemeId);
        String formSchemeId = fetchScheme.getFormSchemeId();
        FetchSettingCond cond = new FetchSettingCond(fetchSchemeId, formSchemeId, formId);
        List<FetchSettingEO> fixFetchSettings = this.fetchSettingService.getFetchSettingListByCond(cond);
        List<FetchFloatSettingEO> floatFetchSettings = this.fetchFloatSettingService.getFetchFloatSettingListByCond(cond);
        return JsonUtils.writeValueAsString((Object)new FetchSetting(fetchScheme, cond, new ArrayList<FetchSettingEO>(fixFetchSettings), new ArrayList<FetchFloatSettingEO>(floatFetchSettings)));
    }

    public String getExportDataInfo(String category, String nodeId) {
        return super.getExportDataInfo(category, nodeId);
    }

    public void importModelInfo(String category, String info) {
        FetchSetting fetchSetting = (FetchSetting)JsonUtils.readValue((String)info, FetchSetting.class);
        this.transactionTemplate.execute(status -> {
            try {
                this.deleteFetchSettingThenAdd(fetchSetting.getFetchScheme(), fetchSetting.getCond(), fetchSetting.getFixFetchSettings(), fetchSetting.getFloatFetchSettings());
            }
            catch (Exception e) {
                LOGGER.error("\u5bfc\u5165\u53d6\u6570\u8bbe\u7f6e\u5931\u8d25\uff01", e);
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    private void deleteFetchSettingThenAdd(FetchSchemeVO importedFetchScheme, FetchSettingCond fetchSettingCond, List<FetchSettingEO> fixedFetchSettings, List<FetchFloatSettingEO> floatFetchSettings) {
        String formSchemeId = fetchSettingCond.getFormSchemeId();
        String fetchSchemeId = fetchSettingCond.getFetchSchemeId();
        Assert.isNotNull((Object)fetchSettingCond);
        Assert.isNotNull(fixedFetchSettings);
        Assert.isNotEmpty((String)formSchemeId);
        Assert.isNotEmpty((String)fetchSchemeId);
        Assert.isNotEmpty((String)fetchSettingCond.getFormId());
        String newFetchSchemeId = fetchSchemeId;
        boolean existFetchScheme = false;
        List<FetchSchemeVO> fetchSchemes = this.fetchSchemeService.listFetchScheme(formSchemeId);
        for (FetchSchemeVO fetchScheme : fetchSchemes) {
            if (fetchScheme.getName() == null || !fetchScheme.getName().equals(importedFetchScheme.getName())) continue;
            newFetchSchemeId = fetchScheme.getId();
            existFetchScheme = true;
            break;
        }
        if (!existFetchScheme) {
            if (this.fetchSchemeService.getFetchScheme(fetchSchemeId) != null) {
                newFetchSchemeId = UUIDUtils.newHalfGUIDStr();
                importedFetchScheme.setId(newFetchSchemeId);
            }
            this.fetchSchemeService.saveFetchScheme(importedFetchScheme);
        }
        fetchSettingCond.setFetchSchemeId(newFetchSchemeId);
        List fetchSettingDesList = this.fetchSettingDesDao.listFetchSettingDesByFormId(fetchSettingCond);
        this.fetchSettingDesDao.deleteFetchSettingDesData(fetchSettingDesList.stream().map(FetchSettingDesEO::getId).collect(Collectors.toList()));
        List floatSettingDesList = this.fetchFloatSettingDesDao.listFetchFloatSettingDesByFormId(fetchSettingCond);
        this.fetchFloatSettingDesDao.deleteFloatSettingDesData(floatSettingDesList.stream().map(FetchFloatSettingDesEO::getId).collect(Collectors.toList()));
        ArrayList<FetchSettingDesEO> fixedFetchSettingDesData = new ArrayList<FetchSettingDesEO>();
        for (FetchSettingEO fixedFetchSetting : fixedFetchSettings) {
            FetchSettingDesEO fetchSettingDes = new FetchSettingDesEO();
            fixedFetchSetting.setFetchSchemeId(newFetchSchemeId);
            this.manualCopyFixedFetchSettingDes(fixedFetchSetting, fetchSettingDes);
            fixedFetchSettingDesData.add(fetchSettingDes);
        }
        this.fetchSettingDesDao.addBatch(fixedFetchSettingDesData);
        ArrayList<FetchFloatSettingDesEO> floatFetchSettingDesData = new ArrayList<FetchFloatSettingDesEO>();
        for (FetchFloatSettingEO floatFetchSetting : floatFetchSettings) {
            FetchFloatSettingDesEO floatFetchSettingDes = new FetchFloatSettingDesEO();
            floatFetchSetting.setFetchSchemeId(newFetchSchemeId);
            this.manualCopyFloatFetchSettingDes(floatFetchSetting, floatFetchSettingDes);
            floatFetchSettingDesData.add(floatFetchSettingDes);
        }
        this.fetchFloatSettingDesDao.addBatch(floatFetchSettingDesData);
    }

    private void manualCopyFixedFetchSettingDes(FetchSettingEO source, FetchSettingDesEO target) {
        target.setId(source.getId());
        target.setFixedSettingData(source.getFixedSettingData());
        target.setFormSchemeId(source.getFormSchemeId());
        target.setFetchSchemeId(source.getFetchSchemeId());
        target.setFormId(source.getFormId());
        target.setDataLinkId(source.getDataLinkId());
        target.setRegionId(source.getRegionId());
        target.setFieldDefineId(source.getFieldDefineId());
        target.setSortOrder(source.getSortOrder());
        target.setRegionType(source.getRegionType());
    }

    private void manualCopyFloatFetchSettingDes(FetchFloatSettingEO source, FetchFloatSettingDesEO target) {
        target.setId(source.getId());
        target.setFormSchemeId(source.getFormSchemeId());
        target.setFetchSchemeId(source.getFetchSchemeId());
        target.setFormId(source.getFormId());
        target.setRegionId(source.getRegionId());
        target.setQueryConfigInfo(source.getQueryConfigInfo());
        target.setQueryType(source.getQueryType());
    }

    public void importDataInfo(String category, String targetId, String info) {
        super.importDataInfo(category, targetId, info);
    }

    static class FetchSetting {
        public FetchSchemeVO fetchScheme;
        public FetchSettingCond cond;
        public List<FetchSettingEO> fixFetchSettings;
        public List<FetchFloatSettingEO> floatFetchSettings;

        public FetchSetting(FetchSchemeVO fetchScheme, FetchSettingCond cond, List<FetchSettingEO> fixFetchSettings, List<FetchFloatSettingEO> floatFetchSettings) {
            this.fetchScheme = fetchScheme;
            this.cond = cond;
            this.fixFetchSettings = fixFetchSettings;
            this.floatFetchSettings = floatFetchSettings;
        }

        public FetchSchemeVO getFetchScheme() {
            return this.fetchScheme;
        }

        public void setFetchScheme(FetchSchemeVO fetchScheme) {
            this.fetchScheme = fetchScheme;
        }

        public FetchSetting() {
        }

        public FetchSettingCond getCond() {
            return this.cond;
        }

        public void setCond(FetchSettingCond cond) {
            this.cond = cond;
        }

        public List<FetchSettingEO> getFixFetchSettings() {
            return this.fixFetchSettings;
        }

        public void setFixFetchSettings(List<FetchSettingEO> fixFetchSettings) {
            this.fixFetchSettings = fixFetchSettings;
        }

        public List<FetchFloatSettingEO> getFloatFetchSettings() {
            return this.floatFetchSettings;
        }

        public void setFloatFetchSettings(List<FetchFloatSettingEO> floatFetchSettings) {
            this.floatFetchSettings = floatFetchSettings;
        }
    }

    public static enum Level {
        TASK(62, "\u4efb\u52a1"),
        FORM_SCHEME(63, "\u62a5\u8868\u65b9\u6848"),
        FETCH_SCHEME(64, "\u53d6\u6570\u65b9\u6848"),
        REPORT(65, "\u62a5\u8868");

        final int code;
        final String name;

        private Level(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}

