/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveItemVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.task.web.TaskConditionBoxController
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.dataentry.tree.FormTreeItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.archive.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveItemVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.common.ArchiveResAppEnum;
import com.jiuqi.gcreport.archive.common.ArchiveStatusEnum;
import com.jiuqi.gcreport.archive.common.ArchiveTaskStatusEnum;
import com.jiuqi.gcreport.archive.dao.ArchiveConfigDao;
import com.jiuqi.gcreport.archive.dao.ArchiveInfoDao;
import com.jiuqi.gcreport.archive.dao.ArchiveLogDao;
import com.jiuqi.gcreport.archive.dao.ArchivePluginDao;
import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.archive.entity.ArchiveInfoEO;
import com.jiuqi.gcreport.archive.entity.ArchiveLogEO;
import com.jiuqi.gcreport.archive.plugin.ArchivePlugin;
import com.jiuqi.gcreport.archive.plugin.ArchivePluginProvider;
import com.jiuqi.gcreport.archive.service.GcArchiveService;
import com.jiuqi.gcreport.archive.util.ArchiveLogUtil;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.web.TaskConditionBoxController;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.nr.impl.service.GCFormTabSelectService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcArchiveServiceImpl
implements GcArchiveService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ArchiveInfoDao archiveInfoDao;
    @Autowired
    private ArchivePluginDao archivePluginDao;
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private ArchiveLogDao archiveLogDao;
    @Autowired
    private ArchiveConfigDao archiveConfigDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private ArchivePluginProvider archivePluginProvider;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public FormTree queryFormTree(String schemeId, String dataTime) {
        try {
            FormTree formTree = this.getFormTree(schemeId, dataTime);
            return formTree;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u51fa\u9519", (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ArchiveLogVO> batchLogQuery(ArchiveQueryParam param) {
        Set<String> orgCodeSet;
        NpContext context = NpContextHolder.getContext();
        if (!this.systemIdentityService.isSystemByUserId(context.getUser().getId())) {
            if (param.getQueryConditions() == null) {
                param.setQueryConditions(new ArchiveContext());
            }
            param.getQueryConditions().setUserName(context.getUserName());
        }
        if ((orgCodeSet = this.initUnitCodeToTempTable(param)).isEmpty()) {
            return PageInfo.empty();
        }
        String tempGroupId = UUIDUtils.newHalfGUIDStr();
        try {
            IdTemporaryTableUtils.insertTempStr((String)tempGroupId, orgCodeSet);
            PageInfo<ArchiveLogEO> eoPageInfo = this.archiveLogDao.querybatchLogByConid(param, tempGroupId);
            if (eoPageInfo.getSize() == 0 || CollectionUtils.isEmpty((Collection)eoPageInfo.getList())) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            PageInfo pageInfo = PageInfo.of(this.convertArchiveLogEO2VO(eoPageInfo.getList()), (int)eoPageInfo.getSize());
            return pageInfo;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    private Set<String> initUnitCodeToTempTable(ArchiveQueryParam param) {
        HashSet<String> orgCodeSet = new HashSet<String>(1024);
        ArchiveContext queryConditions = param.getQueryConditions();
        if (queryConditions == null || StringUtils.isEmpty((String)queryConditions.getTaskId()) || StringUtils.isEmpty((String)queryConditions.getSchemeId())) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u4efb\u52a1\u548c\u65b9\u6848\uff01");
        }
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(queryConditions.getSchemeId());
        TaskConditionBoxController taskConditionBoxController = (TaskConditionBoxController)SpringContextUtils.getBean(TaskConditionBoxController.class);
        Scheme scheme = taskConditionBoxController.convertSchemeDefinToScheme(formScheme);
        String orgType = param.getQueryConditions().getOrgType();
        String startPeriodStr = null;
        String endPeriodStr = null;
        if (param.getQueryConditions() != null) {
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getStartPeriodString())) {
                startPeriodStr = param.getQueryConditions().getStartPeriodString();
            }
            if (!StringUtils.isEmpty((String)param.getQueryConditions().getEndPeriodString())) {
                endPeriodStr = param.getQueryConditions().getEndPeriodString();
            }
        }
        if (startPeriodStr == null || endPeriodStr == null) {
            YearPeriodObject yp = new YearPeriodObject(null, "");
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            List orgTree = tool.getOrgTree();
            this.treeToOrgCodeSet(orgTree, orgCodeSet);
            return orgCodeSet;
        }
        PeriodWrapper startPeriodWrapper = new PeriodWrapper(startPeriodStr);
        PeriodWrapper endPeriodWrapper = new PeriodWrapper(endPeriodStr);
        if (startPeriodWrapper.getType() != endPeriodWrapper.getType()) {
            throw new BusinessRuntimeException("\u5f52\u6863\u8d77\u6b62\u65f6\u95f4\u7c7b\u578b\u4e0d\u76f8\u540c");
        }
        if (startPeriodStr.compareTo(endPeriodStr) > 0) {
            throw new BusinessRuntimeException("\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
        }
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        do {
            YearPeriodObject yp = new YearPeriodObject(null, startPeriodWrapper.toString());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            List orgTree = tool.getOrgTree();
            this.treeToOrgCodeSet(orgTree, orgCodeSet);
        } while (defaultPeriodAdapter.nextPeriod(startPeriodWrapper) && startPeriodWrapper.compareTo((Object)endPeriodWrapper) <= 0);
        return orgCodeSet;
    }

    private void treeToOrgCodeSet(List<GcOrgCacheVO> orgTree, Set<String> orgCodeSet) {
        if (CollectionUtils.isEmpty(orgTree)) {
            return;
        }
        for (GcOrgCacheVO vo : orgTree) {
            orgCodeSet.add(vo.getCode());
            this.treeToOrgCodeSet(vo.getChildren(), orgCodeSet);
        }
    }

    @Override
    public PageInfo<ArchiveInfoVO> detailsLogQuery(ArchiveQueryParam param) {
        Set<String> orgCodeSet = this.initUnitCodeToTempTable(param);
        if (orgCodeSet.isEmpty()) {
            return PageInfo.empty();
        }
        PageInfo<ArchiveInfoEO> eoPageInfo = this.archiveInfoDao.queryArchiveInfoByConid(param);
        if (eoPageInfo.getSize() == 0 || CollectionUtils.isEmpty((Collection)eoPageInfo.getList())) {
            return PageInfo.empty();
        }
        return PageInfo.of(this.convertArchiveInfoEO2VO(eoPageInfo.getList()), (int)eoPageInfo.getSize());
    }

    @Override
    public List<ArchiveInfoVO> detailsAllLogQuery(ArchiveQueryParam param) {
        Set<String> orgCodeSet = this.initUnitCodeToTempTable(param);
        if (orgCodeSet.isEmpty()) {
            return Collections.emptyList();
        }
        List<ArchiveInfoEO> eoList = this.archiveInfoDao.queryAllArchiveInfoByConid(param);
        if (CollectionUtils.isEmpty(eoList)) {
            return Collections.emptyList();
        }
        return this.convertArchiveInfoEO2VO(eoList);
    }

    private List<ArchiveInfoVO> convertArchiveInfoEO2VO(List<ArchiveInfoEO> list) {
        String orgType = ArchiveResAppEnum.MANAGEMENT_RES_APP.getResAppCode().equals(list.get(0).getResApp()) ? "MD_ORG_MANAGEMENT" : "MD_ORG_CORPORATE";
        ArrayList<ArchiveInfoVO> result = new ArrayList<ArchiveInfoVO>();
        for (ArchiveInfoEO eo : list) {
            YearPeriodObject yp = new YearPeriodObject(null, eo.getDefaultPeriod());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            ArchiveInfoVO vo = new ArchiveInfoVO();
            BeanUtils.copyProperties((Object)eo, vo);
            GcOrgCacheVO cacheVO = tool.getOrgByCode(eo.getUnitId());
            if (Objects.isNull(cacheVO)) continue;
            vo.setUnitName(cacheVO.getTitle());
            result.add(vo);
        }
        return result;
    }

    private List<ArchiveLogVO> convertArchiveLogEO2VO(List<ArchiveLogEO> list) {
        ArrayList<ArchiveLogVO> result = new ArrayList<ArchiveLogVO>();
        TableModelDefine tableModel = this.entityMetaService.getTableModel(list.get(0).getOrgType());
        String orgTypeTitle = tableModel.getTitle();
        for (ArchiveLogEO eo : list) {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(eo.getTaskId());
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(eo.getSchemeId());
            ArchiveLogVO vo = new ArchiveLogVO();
            BeanUtils.copyProperties((Object)eo, vo);
            vo.setTaskName(taskDefine.getTitle());
            vo.setSchemeName(formScheme.getTitle());
            vo.setOrgTypeTitle(orgTypeTitle);
            String orgCodeListStr = eo.getOrgCodeList();
            vo.setOrgCodeList(Arrays.asList(orgCodeListStr.split(",")));
            result.add(vo);
        }
        return result;
    }

    @Override
    public ArchiveInfoEO getArchiveByUnitAndPeriod(JtableContext param) {
        String defaultPeriod;
        String unit = ((DimensionValue)param.getDimensionSet().get("MD_ORG")).getValue();
        List<ArchiveInfoEO> oldList = this.archiveInfoDao.queryByUnitAndPeriod(param, unit, defaultPeriod = ((DimensionValue)param.getDimensionSet().get("DATATIME")).getValue());
        if (!CollectionUtils.isEmpty(oldList)) {
            return oldList.get(0);
        }
        return null;
    }

    @Override
    public List<ArchiveInfoEO> getNeedUploadArchive() {
        return this.archiveInfoDao.getNeedUploadArchive(this.archiveProperties.getRetryCount());
    }

    @Override
    public List<ArchiveInfoEO> getNeedSendArchive() {
        return this.archiveInfoDao.getNeedSendArchive(this.archiveProperties.getRetryCount());
    }

    @Override
    public List<ArchiveConfigVO> getArchiveConfig(String taskId) {
        List formSchemeDefines = null;
        try {
            formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskId);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u672a\u627e\u89c1\u4efb\u52a1:" + taskId, (Throwable)e);
        }
        if (CollectionUtils.isEmpty((Collection)formSchemeDefines)) {
            throw new BusinessRuntimeException("\u672a\u627e\u89c1\u4efb\u52a1:" + taskId);
        }
        ArrayList<ArchiveConfigVO> configVOs = new ArrayList<ArchiveConfigVO>();
        String pluginName = this.archivePluginDao.getPluginCode();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            List attachmentFormInfos;
            String attachmentFormsInfoJsonStr;
            List pdfFormInfos;
            String pdfFormsInfoJsonStr;
            List excelFormInfos;
            ArchiveConfigVO configVO = new ArchiveConfigVO();
            configVO.setSchemeId(formSchemeDefine.getKey());
            configVO.setSchemeTitle(formSchemeDefine.getTitle());
            configVO.setPluginName(pluginName);
            FormTree formTree = null;
            try {
                formTree = this.getFormTree(formSchemeDefine.getKey(), null);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u5f62\u5931\u8d25", (Throwable)e);
            }
            configVO.setCanSelectedFormsTree(formTree);
            try {
                FormTree formTreeWithAttachment = this.getFormTree(formSchemeDefine.getKey(), null);
                formTreeWithAttachment.setTree(this.pruneFormTreeNodes((Tree<FormTreeItem>)formTreeWithAttachment.getTree()));
                configVO.setCanSelectedFormsTreeWithAttachment(formTreeWithAttachment);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u5f62\u5931\u8d25", (Throwable)e);
            }
            List<ArchiveConfigEO> archiveConfigEOs = this.archiveConfigDao.queryBySchemeId(formSchemeDefine.getKey());
            if (CollectionUtils.isEmpty(archiveConfigEOs)) {
                configVOs.add(configVO);
                continue;
            }
            String excelFormsInfoJsonStr = archiveConfigEOs.get(0).getExcelFormInfos();
            if (excelFormsInfoJsonStr != null && !CollectionUtils.isEmpty((Collection)(excelFormInfos = (List)JsonUtils.readValue((String)excelFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = excelFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setExcelFormInfo(excelFormInfos);
                configVO.setExcelFormKeys(formKeys);
            }
            if ((pdfFormsInfoJsonStr = archiveConfigEOs.get(0).getPdfFormInfos()) != null && !CollectionUtils.isEmpty((Collection)(pdfFormInfos = (List)JsonUtils.readValue((String)pdfFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = pdfFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setPdfFormInfo(pdfFormInfos);
                configVO.setPdfFormKeys(formKeys);
            }
            if ((attachmentFormsInfoJsonStr = archiveConfigEOs.get(0).getAttachmentFormInfos()) != null && !CollectionUtils.isEmpty((Collection)(attachmentFormInfos = (List)JsonUtils.readValue((String)attachmentFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = attachmentFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setAttachmentFormInfo(attachmentFormInfos);
                configVO.setAttachmentFormKeys(formKeys);
            }
            configVO.setOrgType(archiveConfigEOs.get(0).getOrgType());
            configVOs.add(configVO);
        }
        return configVOs;
    }

    private boolean hasAttachmentField(String formKey) throws Exception {
        List fieldKeys = this.iRunTimeViewController.getFieldKeysInForm(formKey);
        List fieldDefines = this.iDataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeys);
        for (FieldDefine field : fieldDefines) {
            if (!FieldType.FIELD_TYPE_FILE.equals((Object)field.getType())) continue;
            return true;
        }
        return false;
    }

    private Tree<FormTreeItem> pruneFormTreeNodes(Tree<FormTreeItem> node) throws Exception {
        if (node == null || node.getChildren() == null) {
            return node;
        }
        Iterator it = node.getChildren().iterator();
        while (it.hasNext()) {
            Tree child = (Tree)it.next();
            FormTreeItem item = (FormTreeItem)child.getData();
            if ("group".equals(item.getType())) {
                this.pruneFormTreeNodes((Tree<FormTreeItem>)child);
                if (child.getChildren() != null && !child.getChildren().isEmpty()) continue;
                it.remove();
                continue;
            }
            if (!"form".equals(item.getType()) || this.hasAttachmentField(item.getKey())) continue;
            it.remove();
        }
        return node;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveArchiveConfig(String taskId, List<ArchiveConfigVO> archiveConfigVOs) {
        if (CollectionUtils.isEmpty(archiveConfigVOs)) {
            return;
        }
        ArrayList<ArchiveConfigEO> archiveConfigEOS = new ArrayList<ArchiveConfigEO>();
        for (ArchiveConfigVO efdcCheckConfigVO : archiveConfigVOs) {
            ArchiveConfigEO archiveConfigEO = new ArchiveConfigEO();
            archiveConfigEO.setTaskId(taskId);
            archiveConfigEO.setOrgType(efdcCheckConfigVO.getOrgType());
            archiveConfigEO.setSchemeId(efdcCheckConfigVO.getSchemeId());
            archiveConfigEO.setExcelFormInfos(JsonUtils.writeValueAsString(this.getArchiveConfigFormInfos(efdcCheckConfigVO.getExcelFormKeys())));
            archiveConfigEO.setPdfFormInfos(JsonUtils.writeValueAsString(this.getArchiveConfigFormInfos(efdcCheckConfigVO.getPdfFormKeys())));
            archiveConfigEO.setOfdFormInfos(JsonUtils.writeValueAsString(this.getArchiveConfigFormInfos(efdcCheckConfigVO.getOfdFormKeys())));
            archiveConfigEO.setAttachmentFormInfos(JsonUtils.writeValueAsString(this.getArchiveConfigFormInfos(efdcCheckConfigVO.getAttachmentFormKeys())));
            archiveConfigEOS.add(archiveConfigEO);
        }
        this.archiveConfigDao.save(taskId, archiveConfigEOS);
        this.archivePluginDao.save(archiveConfigVOs.get(0).getPluginName());
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public String batchDoActionSave(ArchiveContext context) {
        List<ArchiveConfigEO> archiveConfigEOS = this.archiveConfigDao.queryBySchemeId(context.getSchemeId());
        if (CollectionUtils.isEmpty(archiveConfigEOS)) {
            throw new BusinessRuntimeException("\u8be5\u62a5\u8868\u65b9\u6848\u5c1a\u672a\u914d\u7f6e\u5f52\u6863\u62a5\u8868\uff01");
        }
        ContextUser user = NpContextHolder.getContext().getUser();
        String username = user == null ? "" : user.getName();
        ArchiveLogEO logEO = new ArchiveLogEO();
        logEO.setOrgType(context.getOrgType());
        logEO.setId(UUIDUtils.newUUIDStr());
        logEO.setTaskId(context.getTaskId());
        logEO.setSchemeId(context.getSchemeId());
        logEO.setStartPeriod(context.getStartPeriodString());
        logEO.setStartAdjustCode(StringUtils.isEmpty((String)context.getStartAdjustCode()) ? "0" : context.getStartAdjustCode());
        logEO.setEndAdjustCode(StringUtils.isEmpty((String)context.getEndAdjustCode()) ? "0" : context.getEndAdjustCode());
        logEO.setEndPeriod(context.getEndPeriodString());
        StringBuffer orgCodeStr = new StringBuffer();
        for (String orgCode : context.getOrgCodeList()) {
            orgCodeStr.append(orgCode).append(",");
        }
        orgCodeStr.deleteCharAt(orgCodeStr.length() - 1);
        logEO.setOrgCodeList(orgCodeStr.toString());
        logEO.setStatus(ArchiveTaskStatusEnum.TASK_START.getStatus());
        logEO.setCreateDate(new Date());
        logEO.setCreateUser(username);
        logEO.setExcelFormInfo(context.getExcelFormInfos());
        this.archiveLogDao.save(logEO);
        return logEO.getId();
    }

    @Override
    public void batchDoActionStart(String id) {
        ArchiveLogEO logEO = (ArchiveLogEO)this.archiveLogDao.get((Serializable)((Object)id));
        if (logEO == null) {
            logEO = new ArchiveLogEO();
            this.updateArchiveLogEO(logEO, "\u6ca1\u6709\u627e\u5230\u8981\u6267\u884c\u7684\u4efb\u52a1");
            throw new BusinessRuntimeException("\u6ca1\u6709\u627e\u5230\u8981\u6267\u884c\u7684\u4efb\u52a1");
        }
        String orgCodeStr = logEO.getOrgCodeList();
        if (StringUtils.isEmpty((String)orgCodeStr)) {
            this.updateArchiveLogEO(logEO, "\u6ca1\u6709\u9009\u62e9\u9700\u8981\u5904\u7406\u7684\u5355\u4f4d");
            throw new BusinessRuntimeException("\u6ca1\u6709\u9009\u62e9\u9700\u8981\u5904\u7406\u7684\u5355\u4f4d");
        }
        String[] orgCodeList = orgCodeStr.split(",");
        if (orgCodeList == null || orgCodeList.length == 0) {
            this.updateArchiveLogEO(logEO, "\u6ca1\u6709\u9009\u62e9\u9700\u8981\u5904\u7406\u7684\u5355\u4f4d");
            throw new BusinessRuntimeException("\u6ca1\u6709\u9009\u62e9\u9700\u8981\u5904\u7406\u7684\u5355\u4f4d");
        }
        String startPeriodString = logEO.getStartPeriod();
        String endPeriodString = logEO.getEndPeriod();
        if (StringUtils.isEmpty((String)startPeriodString) || StringUtils.isEmpty((String)endPeriodString)) {
            this.updateArchiveLogEO(logEO, "\u5f52\u6863\u65f6\u95f4\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a");
            throw new BusinessRuntimeException("\u5f52\u6863\u65f6\u95f4\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)logEO.getOrgType())) {
            this.updateArchiveLogEO(logEO, "\u5f52\u6863\u53e3\u5f84\u4e0d\u80fd\u4e3a\u7a7a");
            throw new BusinessRuntimeException("\u5f52\u6863\u53e3\u5f84\u4e0d\u80fd\u4e3a\u7a7a");
        }
        PeriodWrapper startPeriodWrapper = new PeriodWrapper(startPeriodString);
        PeriodWrapper endPeriodWrapper = new PeriodWrapper(endPeriodString);
        if (startPeriodWrapper.getType() != endPeriodWrapper.getType()) {
            this.updateArchiveLogEO(logEO, "\u5f52\u6863\u8d77\u6b62\u65f6\u95f4\u7c7b\u578b\u4e0d\u76f8\u540c");
            throw new BusinessRuntimeException("\u5f52\u6863\u8d77\u6b62\u65f6\u95f4\u7c7b\u578b\u4e0d\u76f8\u540c");
        }
        if (startPeriodString.compareTo(endPeriodString) > 0) {
            this.updateArchiveLogEO(logEO, "\u5f52\u6863\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
            throw new BusinessRuntimeException("\u5f52\u6863\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
        }
        List<ArchiveConfigEO> archiveConfigEOS = this.archiveConfigDao.queryBySchemeId(logEO.getSchemeId());
        String orgType = logEO.getOrgType();
        archiveConfigEOS = archiveConfigEOS.stream().filter(archiveConfigEO -> orgType.equals(archiveConfigEO.getOrgType())).collect(Collectors.toList());
        ArchivePlugin archivePlugin = this.archivePluginProvider.getArchivePluginByCode(this.archivePluginDao.getPluginCode());
        archivePlugin.beforeArchive(startPeriodWrapper);
        StringBuffer loginfo = new StringBuffer();
        for (String orgCode : orgCodeList) {
            try {
                loginfo.append(archivePlugin.doAction(this.initDoActionParam(logEO, orgCode), logEO, archiveConfigEOS)).append("\n");
            }
            catch (Exception e) {
                loginfo.append(ArchiveLogUtil.getExceptionStackStr(e));
            }
        }
        archivePlugin.afterArchiveProcessComplete(logEO, loginfo);
        this.updateArchiveLogEO(logEO, loginfo.toString());
    }

    @Override
    public void detailsLogDelete(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u8981\u5220\u9664\u7684\u6761\u76ee\uff01");
        }
        this.archiveInfoDao.deleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<EFSResponseData> cancelArchive(SendArchiveVO sendArchiveVO) {
        List data = sendArchiveVO.getDATA();
        if (CollectionUtils.isEmpty((Collection)data)) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<EFSResponseData> result = new ArrayList<EFSResponseData>(data.size());
        List<String> ids = data.stream().map(SendArchiveItemVO::getF_UUID).collect(Collectors.toList());
        List<ArchiveInfoEO> archiveInfoEOList = this.archiveInfoDao.queryArchiveInfoByIds(ids);
        ArrayList<ArchiveInfoEO> cancelArchiveList = new ArrayList<ArchiveInfoEO>(data.size());
        Map archiveInfoIdMap = archiveInfoEOList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity(), (key1, key2) -> key2));
        for (String id : ids) {
            if (!archiveInfoIdMap.containsKey(id)) {
                result.add(new EFSResponseData("0", id, "\u5f52\u6863\u4fe1\u606f\u4e0d\u5b58\u5728"));
                continue;
            }
            ArchiveInfoEO archiveInfoEO = (ArchiveInfoEO)((Object)archiveInfoIdMap.get(id));
            ArchiveStatusEnum statusEnum = ArchiveStatusEnum.getEnum(archiveInfoEO.getStatus());
            if (statusEnum == null) {
                result.add(new EFSResponseData("1", id, "\u5c1a\u672a\u5b8c\u6210\u5f52\u6863"));
                continue;
            }
            switch (statusEnum) {
                case UPLOAD_FAILED: 
                case UPLOAD_SUCCESS: 
                case SEND_FAILED: {
                    result.add(new EFSResponseData("1", id, "\u5c1a\u672a\u5b8c\u6210\u5f52\u6863"));
                    break;
                }
                case SEND_SUCCESS: {
                    archiveInfoEO.setStatus(ArchiveStatusEnum.CANCEL_ARCHIVE.getStatus());
                    cancelArchiveList.add(archiveInfoEO);
                    result.add(new EFSResponseData("0", id, "\u53d6\u6d88\u6210\u529f"));
                    break;
                }
                case CANCEL_ARCHIVE: {
                    result.add(new EFSResponseData("0", id, "\u8be5\u8bb0\u5f55\u4e4b\u524d\u5df2\u7ecf\u53d1\u9001\u8fc7\u53d6\u6d88\u5f52\u6863\u6d88\u606f"));
                }
            }
        }
        if (!CollectionUtils.isEmpty(cancelArchiveList)) {
            this.archiveInfoDao.updateAll(cancelArchiveList);
        }
        return result;
    }

    @Override
    public List<ArchiveConfigVO> getArchiveConfigWithOrgType(String taskId, String orgType) {
        List formSchemeDefines = null;
        try {
            formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskId);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u672a\u627e\u89c1\u4efb\u52a1:" + taskId, (Throwable)e);
        }
        if (CollectionUtils.isEmpty((Collection)formSchemeDefines)) {
            throw new BusinessRuntimeException("\u672a\u627e\u89c1\u4efb\u52a1:" + taskId);
        }
        if (StringUtils.isEmpty((String)orgType)) {
            throw new BusinessRuntimeException("\u672a\u627e\u89c1\u53e3\u5f84:" + taskId);
        }
        ArrayList<ArchiveConfigVO> configVOs = new ArrayList<ArchiveConfigVO>();
        String pluginName = this.archivePluginDao.getPluginCode();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            List attachmentFormInfos;
            String attachmentFormsInfoJsonStr;
            List ofdFormInfos;
            String ofdFormsInfoJsonStr;
            List pdfFormInfos;
            String pdfFormsInfoJsonStr;
            List excelFormInfos;
            ArchiveConfigVO configVO = new ArchiveConfigVO();
            configVO.setSchemeId(formSchemeDefine.getKey());
            configVO.setSchemeTitle(formSchemeDefine.getTitle());
            configVO.setPluginName(pluginName);
            FormTree formTree = null;
            try {
                formTree = this.getFormTree(formSchemeDefine.getKey(), null);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u5f62\u5931\u8d25", (Throwable)e);
            }
            configVO.setCanSelectedFormsTree(formTree);
            try {
                FormTree formTreeWithAttachment = this.getFormTree(formSchemeDefine.getKey(), null);
                formTreeWithAttachment.setTree(this.pruneFormTreeNodes((Tree<FormTreeItem>)formTreeWithAttachment.getTree()));
                configVO.setCanSelectedFormsTreeWithAttachment(formTreeWithAttachment);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u5f62\u5931\u8d25", (Throwable)e);
            }
            List<ArchiveConfigEO> archiveConfigEOs = this.archiveConfigDao.queryBySchemeIdAndOrgType(formSchemeDefine.getKey(), orgType);
            if (CollectionUtils.isEmpty(archiveConfigEOs)) {
                configVOs.add(configVO);
                continue;
            }
            String excelFormsInfoJsonStr = archiveConfigEOs.get(0).getExcelFormInfos();
            if (excelFormsInfoJsonStr != null && !CollectionUtils.isEmpty((Collection)(excelFormInfos = (List)JsonUtils.readValue((String)excelFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = excelFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setExcelFormInfo(excelFormInfos);
                configVO.setExcelFormKeys(formKeys);
            }
            if ((pdfFormsInfoJsonStr = archiveConfigEOs.get(0).getPdfFormInfos()) != null && !CollectionUtils.isEmpty((Collection)(pdfFormInfos = (List)JsonUtils.readValue((String)pdfFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = pdfFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setPdfFormInfo(pdfFormInfos);
                configVO.setPdfFormKeys(formKeys);
            }
            if ((ofdFormsInfoJsonStr = archiveConfigEOs.get(0).getOfdFormInfos()) != null && !CollectionUtils.isEmpty((Collection)(ofdFormInfos = (List)JsonUtils.readValue((String)ofdFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = ofdFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setOfdFormInfo(ofdFormInfos);
                configVO.setOfdFormKeys(formKeys);
            }
            if ((attachmentFormsInfoJsonStr = archiveConfigEOs.get(0).getAttachmentFormInfos()) != null && !CollectionUtils.isEmpty((Collection)(attachmentFormInfos = (List)JsonUtils.readValue((String)attachmentFormsInfoJsonStr, (TypeReference)new TypeReference<List<ArchiveConfigFormInfo>>(){})))) {
                List formKeys = attachmentFormInfos.stream().map(ArchiveConfigFormInfo::getFormKey).collect(Collectors.toList());
                configVO.setAttachmentFormInfo(attachmentFormInfos);
                configVO.setAttachmentFormKeys(formKeys);
            }
            configVO.setOrgType(archiveConfigEOs.get(0).getOrgType());
            configVOs.add(configVO);
        }
        return configVOs;
    }

    public ArchiveContext initDoActionParam(ArchiveLogEO logEO, String orgCode) {
        ArchiveContext resultContext = new ArchiveContext();
        resultContext.setTaskId(logEO.getTaskId());
        resultContext.setOrgCode(orgCode);
        resultContext.setTaskKey(logEO.getTaskId());
        resultContext.setFormSchemeKey(logEO.getSchemeId());
        resultContext.setStartAdjustCode(logEO.getStartAdjustCode());
        resultContext.setStartPeriodString(logEO.getStartPeriod());
        resultContext.setEndAdjustCode(logEO.getEndAdjustCode());
        resultContext.setEndPeriodString(logEO.getEndPeriod());
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(logEO.getStartPeriod());
        dimensionSetMap.put("DATATIME", dimensionValue);
        DimensionValue dimensionValue2 = new DimensionValue();
        dimensionValue2.setName("MD_ORG");
        dimensionValue2.setValue(orgCode);
        dimensionSetMap.put("MD_ORG", dimensionValue2);
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(logEO.getSchemeId());
        TaskConditionBoxController taskConditionBoxController = (TaskConditionBoxController)SpringContextUtils.getBean(TaskConditionBoxController.class);
        Scheme scheme = taskConditionBoxController.convertSchemeDefinToScheme(formScheme);
        DimensionValue dimensionValue3 = new DimensionValue();
        dimensionValue3.setName("MD_GCORGTYPE");
        YearPeriodObject yp = new YearPeriodObject(formScheme.getKey(), logEO.getStartPeriod());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)logEO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrgCacheVO = instance.getOrgByCode(orgCode);
        GcOrgTypeUtils.setContextEntityId((String)logEO.getOrgType());
        if (gcOrgCacheVO != null) {
            dimensionValue3.setValue(gcOrgCacheVO.getOrgTypeId());
        }
        dimensionSetMap.put("MD_GCORGTYPE", dimensionValue3);
        if (DimensionUtils.isExistAdjust((String)logEO.getTaskId())) {
            DimensionValue adjustDim = new DimensionValue();
            adjustDim.setName("ADJUST");
            adjustDim.setValue("0");
            dimensionSetMap.put("ADJUST", adjustDim);
        }
        DimensionUtils.dimensionMapSetAdjType((String)logEO.getTaskId(), dimensionSetMap);
        DimensionValue dimensionValue5 = new DimensionValue();
        dimensionValue5.setName("MD_CURRENCY");
        dimensionValue5.setValue("CNY");
        dimensionSetMap.put("MD_CURRENCY", dimensionValue5);
        resultContext.setDimensionSet(dimensionSetMap);
        return resultContext;
    }

    private List<ArchiveConfigFormInfo> getArchiveConfigFormInfos(List<String> formkeys) {
        ArrayList<ArchiveConfigFormInfo> forms = new ArrayList<ArchiveConfigFormInfo>();
        if (CollectionUtils.isEmpty(formkeys)) {
            return forms;
        }
        for (String formKey : formkeys) {
            ArchiveConfigFormInfo formInfo = new ArchiveConfigFormInfo();
            formInfo.setFormKey(formKey);
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (null != formDefine) {
                formInfo.setFormType("form");
            } else {
                FormGroupDefine formGroupDefine = this.runTimeViewController.queryFormGroup(formKey);
                if (null != formGroupDefine) {
                    formInfo.setFormType("group");
                }
            }
            forms.add(formInfo);
        }
        return forms;
    }

    public FormTree getFormTree(String formSchemeKey, String dataTime) throws Exception {
        List rootFormGroups = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        rootItem.setKey(UUIDUtils.emptyUUIDStr());
        rootItem.setTitle("\u62a5\u8868\u8ddf\u8282\u70b9");
        Tree tree = new Tree((Object)rootItem);
        if (StringUtils.isEmpty((String)dataTime)) {
            this.addChildren((Tree<FormTreeItem>)tree, rootFormGroups);
        } else {
            this.addChildren((Tree<FormTreeItem>)tree, rootFormGroups, formSchemeKey, dataTime);
        }
        this.removeNoChildrenGroup(tree.getChildren());
        FormTree formTree = new FormTree();
        formTree.setTree(tree);
        return formTree;
    }

    private Tree<FormTreeItem> addChildren(Tree<FormTreeItem> node, List<FormGroupDefine> formGroupList) throws Exception {
        for (FormGroupDefine formGroup : formGroupList) {
            List formGroupDefines;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroup.getKey());
            groupItem.setCode(formGroup.getCode());
            groupItem.setTitle(formGroup.getCode() + '|' + formGroup.getTitle());
            groupItem.setType("group");
            Tree child = node.addChild((Object)groupItem);
            List formDefines = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            if (null != formDefines) {
                for (FormDefine form : formDefines) {
                    FormTreeItem reportItem = new FormTreeItem();
                    reportItem.setKey(form.getKey());
                    reportItem.setCode(form.getFormCode());
                    reportItem.setTitle(form.getFormCode() + '|' + form.getTitle());
                    reportItem.setSerialNumber(form.getSerialNumber());
                    reportItem.setType("form");
                    reportItem.setGroupKey(formGroup.getKey());
                    child.addChild((Object)reportItem);
                }
            }
            if (CollectionUtils.isEmpty((Collection)(formGroupDefines = this.runTimeViewController.getChildFormGroups(formGroup.getKey())))) continue;
            this.addChildren((Tree<FormTreeItem>)child, formGroupDefines);
        }
        return node;
    }

    private void removeNoChildrenGroup(List<Tree<FormTreeItem>> childrens) {
        for (int i = childrens.size() - 1; i >= 0; --i) {
            Tree<FormTreeItem> item = childrens.get(i);
            if (item == null || !"group".equals(((FormTreeItem)item.getData()).getType())) continue;
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                this.removeNoChildrenGroup(item.getChildren());
            }
            if (item.getChildren() != null && item.getChildren().size() != 0) continue;
            childrens.remove(i);
        }
    }

    private Tree<FormTreeItem> addChildren(Tree<FormTreeItem> node, List<FormGroupDefine> formGroupList, String formSchemeKey, String dataTime) throws Exception {
        GCFormTabSelectService formTabSelectService = (GCFormTabSelectService)SpringContextUtils.getBean(GCFormTabSelectService.class);
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(dataTime);
        dimensionSetMap.put("DATATIME", dimensionValue);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)dataTime);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setDimensionSet(dimensionSetMap);
        formGroupList = formGroupList.stream().filter(define -> formTabSelectService.isFormCondition(jtableContext, define.getCondition(), dimensionValueSet)).collect(Collectors.toList());
        for (FormGroupDefine formGroupDefine : formGroupList) {
            List formDefines = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
            if ((formDefines = formDefines.stream().filter(define -> formTabSelectService.isFormCondition(jtableContext, define.getFormCondition(), dimensionValueSet)).collect(Collectors.toList())).size() == 0) continue;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroupDefine.getKey());
            groupItem.setCode(formGroupDefine.getCode());
            groupItem.setTitle(formGroupDefine.getCode() + '|' + formGroupDefine.getTitle());
            groupItem.setType("group");
            Tree child = node.addChild((Object)groupItem);
            for (FormDefine form : formDefines) {
                FormTreeItem reportItem = new FormTreeItem();
                reportItem.setKey(form.getKey());
                reportItem.setCode(form.getFormCode());
                reportItem.setTitle(form.getFormCode() + '|' + form.getTitle());
                reportItem.setSerialNumber(form.getSerialNumber());
                reportItem.setType("form");
                reportItem.setGroupKey(formGroupDefine.getKey());
                child.addChild((Object)reportItem);
            }
            List formGroupDefines = this.runTimeViewController.getChildFormGroups(formGroupDefine.getKey());
            if (CollectionUtils.isEmpty((Collection)formGroupDefines)) continue;
            this.addChildren((Tree<FormTreeItem>)child, formGroupDefines, formSchemeKey, dataTime);
        }
        return node;
    }

    private void updateArchiveLogEO(ArchiveLogEO logEO, String messageInfo) {
        logEO.setLogInfo(messageInfo);
        logEO.setEndDate(new Date());
        logEO.setStatus(ArchiveTaskStatusEnum.TASK_END.getStatus());
        this.archiveLogDao.update((BaseEntity)logEO);
    }
}

