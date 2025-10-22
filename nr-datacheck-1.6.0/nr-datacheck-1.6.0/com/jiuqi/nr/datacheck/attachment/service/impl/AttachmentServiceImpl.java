/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFieldResultItem
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckParam
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckResultItem
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckReturnInfo
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldInfo
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldStruct
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentFormStruct
 *  com.jiuqi.nr.attachmentcheck.bean.MapWrapper
 *  com.jiuqi.nr.attachmentcheck.service.IAttachmentCheckService
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.service.IMCSchemeService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.datacheck.attachment.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFieldResultItem;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckParam;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckResultItem;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckReturnInfo;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldInfo;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldStruct;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFormStruct;
import com.jiuqi.nr.attachmentcheck.bean.MapWrapper;
import com.jiuqi.nr.attachmentcheck.service.IAttachmentCheckService;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.datacheck.attachment.AttachmentAuditScope;
import com.jiuqi.nr.datacheck.attachment.AttachmentConfig;
import com.jiuqi.nr.datacheck.attachment.service.IAttachmentService;
import com.jiuqi.nr.datacheck.attachment.vo.AttachmentQueryPM;
import com.jiuqi.nr.datacheck.attachment.vo.AttachmentResVO;
import com.jiuqi.nr.datacheck.attachment.vo.QueryResParam;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.util.StringUtils;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AttachmentServiceImpl
implements IAttachmentService {
    private static final String DESCRIBE = "\u9009\u62e9\u6307\u6807 | \u6240\u6709\u6307\u6807";
    private static final String DESCRIBE_S = "\u9009\u62e9\u6307\u6807 | <span class=\"mtc-item-number-cls\">%d</span>\u4e2a";
    private static final String[] TITLES = new String[]{"\u5e8f\u53f7", "\u4f01\u4e1a\u4ee3\u7801", "\u4f01\u4e1a\u540d\u79f0"};
    private static final String FILE_STR = "%d(%d)\u4e2a,%.2fMb";
    private static final Predicate<FormType> IN_FORM = f -> f == FormType.FORM_TYPE_FIX || f == FormType.FORM_TYPE_FLOAT || f == FormType.FORM_TYPE_ATTACHED || f == FormType.FORM_TYPE_INTERMEDIATE;
    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    @Autowired
    private IAttachmentCheckService attachmentCheckService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private MultCheckResDao multCheckResDao;
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Resource
    private IEntityViewRunTimeController entityViewCtrl;
    @Resource
    private IEntityDataService entityDataService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;

    @Override
    public CheckItemResult runCheck(CheckItemParam param) {
        AttachmentCheckParam checkParam = this.buildParam(param);
        CheckItemResult result = this.initRes();
        if (checkParam != null) {
            try {
                AttachmentCheckReturnInfo returnInfo = this.attachmentCheckService.attachmentCheck(checkParam, param.getAsyncTaskMonitor());
                if (param.getAsyncTaskMonitor().isCancel()) {
                    return result;
                }
                this.log(param, returnInfo);
                this.buildRes(result, returnInfo, param);
            }
            catch (Exception e) {
                logger.error("\u9644\u4ef6\u6587\u6863\u5ba1\u6838\u5f02\u5e38\uff1a", e);
                result.setResult(CheckRestultState.FAIL);
            }
        }
        return result;
    }

    private void log(CheckItemParam param, AttachmentCheckReturnInfo returnInfo) {
        try {
            this.multCheckResDao.insert(UUIDMerger.merge((String)param.getRunId(), (String)param.getCheckItem().getKey()), SerializeUtil.serializeToJson(returnInfo));
        }
        catch (Exception e) {
            logger.error("\u9644\u4ef6\u6587\u6863\u5ba1\u6838\u7ed3\u679c\u5e8f\u5217\u5316\u5931\u8d25\uff1a", e);
        }
    }

    private void buildRes(CheckItemResult result, AttachmentCheckReturnInfo returnInfo, CheckItemParam param) {
        Map failedOrgs = result.getFailedOrgs();
        List errItems = returnInfo.getErrItems();
        if (!CollectionUtils.isEmpty(errItems) && returnInfo.getErrUnitCount() > 0) {
            for (AttachmentCheckResultItem errItem : errItems) {
                failedOrgs.put(errItem.getUnitCode(), this.buildFailedOrgInfo(errItem));
            }
        }
        for (String s : param.getContext().getOrgList()) {
            if (failedOrgs.containsKey(s)) continue;
            result.getSuccessOrgs().add(s);
        }
        result.setResult(failedOrgs.isEmpty() ? CheckRestultState.SUCCESS : CheckRestultState.FAIL);
    }

    private FailedOrgInfo buildFailedOrgInfo(AttachmentCheckResultItem errItem) {
        FailedOrgInfo orgInfo = new FailedOrgInfo();
        ArrayList<FailedOrgDimInfo> fieldInfos = new ArrayList<FailedOrgDimInfo>();
        orgInfo.setDimInfo(fieldInfos);
        orgInfo.setDesc(String.format("%s\u5355\u4f4d\u9644\u4ef6\u6587\u6863\u5ba1\u6838\u9519\u8bef", errItem.getUnitTitle()));
        List fieldItems = errItem.getFieldItems();
        if (fieldItems != null) {
            for (AttachmentCheckFieldResultItem fieldItem : fieldItems) {
                FailedOrgDimInfo fieldInfo = new FailedOrgDimInfo();
                fieldInfo.setDesc(String.format("%s\u9644\u4ef6\u6307\u6807\u4e0d\u5408\u89c4\u8303", fieldItem.getFieldTitle()));
                fieldInfos.add(fieldInfo);
            }
        }
        return orgInfo;
    }

    private AttachmentCheckParam buildParam(CheckItemParam param) {
        AttachmentCheckParam attachmentCheckParam = new AttachmentCheckParam();
        String config = param.getCheckItem().getConfig();
        if (config == null) {
            return null;
        }
        try {
            AttachmentConfig attachmentConfig = SerializeUtil.deserializeFromJson(config, AttachmentConfig.class);
            attachmentCheckParam.setSelBlobItem(this.buildBlobItem(param, attachmentConfig));
            attachmentCheckParam.setTaskKey(param.getContext().getTaskKey());
            attachmentCheckParam.setDims(param.getContext().getDims());
            attachmentCheckParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
            return attachmentCheckParam;
        }
        catch (Exception e) {
            logger.error("\u9644\u4ef6\u5ba1\u6838\u8fc7\u7a0b\u6784\u5efa\u6570\u636e\u5f02\u5e38", e);
            return null;
        }
    }

    private JtableContext buildContext(CheckItemParam param) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(param.getContext().getTaskKey());
        jtableContext.setFormSchemeKey(param.getContext().getFormSchemeKey());
        jtableContext.setFormKey("");
        jtableContext.setFormGroupKey("");
        jtableContext.setFormulaSchemeKey("");
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue periodDim = new DimensionValue();
        periodDim.setName("DATATIME");
        periodDim.setType(DimensionType.DIMENSION_PERIOD.getValue());
        periodDim.setValue(param.getContext().getPeriod());
        dimensionSet.put(periodDim.getName(), periodDim);
        DimensionValue orgDim = new DimensionValue();
        orgDim.setName("MD_ORG");
        orgDim.setType(DimensionType.DIMENSION_PERIOD.getValue());
        orgDim.setValue("");
        dimensionSet.put(orgDim.getName(), orgDim);
        jtableContext.setDimensionSet(dimensionSet);
        jtableContext.setVariableMap(new HashMap());
        jtableContext.setMeasureMap(new HashMap());
        return jtableContext;
    }

    private List<AttachmentFormStruct> buildBlobItem(CheckItemParam param, AttachmentConfig attachmentConfig) throws Exception {
        ArrayList<AttachmentFormStruct> blobFormStructs = new ArrayList<AttachmentFormStruct>();
        HashMap<String, AttachmentFormStruct> formMaps = new HashMap<String, AttachmentFormStruct>();
        List<AttachmentAuditScope> scopes = attachmentConfig == null || CollectionUtils.isEmpty(attachmentConfig.getAuditScope()) ? this.getAllField(param.getContext().getFormSchemeKey()) : attachmentConfig.getAuditScope();
        if (scopes != null) {
            for (AttachmentAuditScope scope : scopes) {
                AttachmentFormStruct formStruct;
                FormDefine formDefine = this.runTimeViewController.queryFormById(scope.getFormKey());
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(scope.getFieldKey());
                if (formDefine == null || fieldDefine == null) continue;
                if (formMaps.containsKey(scope.getFormKey())) {
                    formStruct = (AttachmentFormStruct)formMaps.get(scope.getFormKey());
                } else {
                    formStruct = new AttachmentFormStruct();
                    formStruct.setFlag(formDefine.getFormCode());
                    formStruct.setKey(formDefine.getKey());
                    formStruct.setTitle(formDefine.getTitle());
                    formMaps.put(scope.getFormKey(), formStruct);
                    blobFormStructs.add(formStruct);
                }
                AttachmentFieldInfo fieldStruct = new AttachmentFieldInfo();
                fieldStruct.setDataLinkKey(scope.getDataLinkKey());
                fieldStruct.setKey(fieldDefine.getKey());
                fieldStruct.setFlag(fieldDefine.getCode());
                fieldStruct.setFormCode(formDefine.getFormCode());
                fieldStruct.setFormKey(formDefine.getKey());
                fieldStruct.setFormTitle(formDefine.getTitle());
                fieldStruct.setTitle(fieldDefine.getTitle());
                formStruct.getChildren().add(fieldStruct);
            }
        }
        return blobFormStructs;
    }

    private List<AttachmentAuditScope> getAllField(String formSchemeKey) throws Exception {
        ArrayList<AttachmentAuditScope> scopes = new ArrayList<AttachmentAuditScope>();
        List<AttachmentFormStruct> blobTablesAndFields = this.getBlobTablesAndFields(formSchemeKey);
        for (AttachmentFormStruct blobTablesAndField : blobTablesAndFields) {
            if (CollectionUtils.isEmpty(blobTablesAndField.getChildren())) continue;
            for (AttachmentFieldStruct child : blobTablesAndField.getChildren()) {
                AttachmentAuditScope scope = new AttachmentAuditScope();
                scope.setFieldKey(child.getKey());
                scope.setFormKey(child.getFormKey());
                scope.setDataLinkKey(child.getDataLinkKey());
                scopes.add(scope);
            }
        }
        return scopes;
    }

    public List<AttachmentFormStruct> getBlobTablesAndFields(String formSchemeKey) throws Exception {
        ArrayList<AttachmentFormStruct> result = new ArrayList<AttachmentFormStruct>();
        List<FormDefine> forms = this.getFormsAllList(formSchemeKey);
        for (FormDefine form : forms) {
            if (!IN_FORM.test(form.getFormType())) continue;
            List linkDefines = this.runTimeViewController.getAllLinksInForm(form.getKey());
            AttachmentFormStruct blobFormStruct = this.buildBlobItem(form);
            result.add(blobFormStruct);
            for (DataLinkDefine linkDefine : linkDefines) {
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression());
                if (field == null || field.getType() != FieldType.FIELD_TYPE_FILE) continue;
                AttachmentFieldInfo childItem = new AttachmentFieldInfo();
                childItem.setFlag(field.getCode());
                childItem.setTitle(field.getTitle());
                childItem.setKey(field.getKey());
                childItem.setFormKey(form.getKey());
                childItem.setFormCode(form.getFormCode());
                childItem.setFormTitle(form.getTitle());
                childItem.setDataLinkKey(linkDefine.getKey());
                blobFormStruct.getChildren().add(childItem);
            }
        }
        return result;
    }

    private AttachmentFormStruct buildBlobItem(FormDefine form) {
        AttachmentFormStruct parentItem = new AttachmentFormStruct();
        parentItem.setFlag(form.getFormCode());
        parentItem.setTitle(form.getTitle());
        parentItem.setKey(form.getKey());
        parentItem.setGroupKey(form.getKey());
        ArrayList childItems = new ArrayList();
        parentItem.setChildren(childItems);
        return parentItem;
    }

    public List<FormDefine> getFormsAllList(String formSchemeKey) throws Exception {
        ArrayList<FormDefine> result = new ArrayList<FormDefine>();
        HashSet<String> formDic = new HashSet<String>();
        List allFormGroups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        for (FormGroupDefine formGroup : allFormGroups) {
            List allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            for (FormDefine fd : allFormsInGroup) {
                if (!IN_FORM.test(fd.getFormType()) || formDic.contains(fd.getKey())) continue;
                result.add(fd);
                formDic.add(fd.getKey());
            }
        }
        return result;
    }

    private CheckItemResult initRes() {
        CheckItemResult result = new CheckItemResult();
        result.setResult(CheckRestultState.SUCCESS);
        ArrayList successOrgs = new ArrayList();
        HashMap failedOrgs = new HashMap();
        result.setSuccessOrgs(successOrgs);
        result.setFailedOrgs(failedOrgs);
        return result;
    }

    @Override
    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        String config = item.getConfig();
        if (config == null) {
            return DESCRIBE;
        }
        try {
            AttachmentConfig attachmentConfig = SerializeUtil.deserializeFromJson(config, AttachmentConfig.class);
            List<AttachmentAuditScope> auditScope = attachmentConfig.getAuditScope();
            if (CollectionUtils.isEmpty(auditScope)) {
                return DESCRIBE;
            }
            return String.format(DESCRIBE_S, auditScope.size());
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.getItemDescribe(formSchemeKey, item);
    }

    public IEntityTable buildEntityTable(String viewKey, String period, String formSchemeKey) throws Exception {
        EntityViewDefine entityViewDefine = this.entityViewCtrl.buildEntityView(viewKey);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet valueSet = new DimensionValueSet();
        boolean filterSetted = false;
        if (StringUtils.isNotEmpty((String)period)) {
            valueSet.setValue("DATATIME", (Object)period);
            filterSetted = true;
        }
        if (filterSetted) {
            entityQuery.setMasterKeys(valueSet);
        }
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        entityQuery.setIgnoreViewFilter(false);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewCtrl, formSchemeKey);
            context.setEnv((IFmlExecEnvironment)environment);
        }
        context.setVarDimensionValueSet(entityQuery.getMasterKeys());
        return entityQuery.executeReader((IContext)context);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public AttachmentResVO queryResult(QueryResParam param) {
        AttachmentResVO res = new AttachmentResVO();
        MultCheckRes byId = this.multCheckResDao.findById(UUIDMerger.merge((String)param.getRunId(), (String)param.getCheckId()));
        try {
            String itemConfig;
            if (org.springframework.util.StringUtils.hasLength(byId.getData())) {
                String dimTitle;
                IEntityRow entityRow;
                String[] errItems;
                TaskDefine taskDefine;
                AttachmentCheckReturnInfo blobFileSizeCheckReturnInfo = SerializeUtil.deserializeFromJson(byId.getData(), AttachmentCheckReturnInfo.class);
                Map<String, String> selectDimNameValues = param.getSelectDims();
                if (null != selectDimNameValues && !selectDimNameValues.isEmpty()) {
                    taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
                    String masterDimName = this.entityMetaService.queryEntity(taskDefine.getDw()).getDimensionName();
                    HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                    DimensionValue dwDimensionValue = new DimensionValue();
                    dwDimensionValue.setName(masterDimName);
                    dwDimensionValue.setValue(String.join((CharSequence)";", param.getOrgCode()));
                    dimensionSet.put(masterDimName, dwDimensionValue);
                    DimensionValue dataTimeDim = new DimensionValue();
                    dataTimeDim.setName("DATATIME");
                    dataTimeDim.setValue(param.getPeriod());
                    dimensionSet.put("DATATIME", dataTimeDim);
                    Map<String, String> dims = param.getSelectDims();
                    for (Map.Entry dimNameValue : dims.entrySet()) {
                        DimensionValue dimensionValue = new DimensionValue();
                        dimensionValue.setName((String)dimNameValue.getKey());
                        dimensionValue.setValue((String)dimNameValue.getValue());
                        dimensionSet.put((String)dimNameValue.getKey(), dimensionValue);
                    }
                    DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, param.getFormSchemeKey());
                    ArrayList<MapWrapper> selectMapWrappers = new ArrayList<MapWrapper>();
                    List dimensionCombinations = dimensionCollection.getDimensionCombinations();
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
                        for (String dimName : dimensionCombination.getNames()) {
                            dimNameValueMap.put(dimName, (String)dimensionCombination.getValue(dimName));
                        }
                        selectMapWrappers.add(new MapWrapper(dimNameValueMap));
                    }
                    List<String> orgCode = param.getOrgCode();
                    ArrayList<AttachmentCheckResultItem> filterAllItems = new ArrayList<AttachmentCheckResultItem>();
                    List allItems = blobFileSizeCheckReturnInfo.getAllItems();
                    for (AttachmentCheckResultItem allItem : allItems) {
                        if (!orgCode.contains(allItem.getUnitCode())) continue;
                        boolean canAdd = false;
                        Map dimNameValue = allItem.getDimNameValue();
                        for (MapWrapper selectMapWrapper : selectMapWrappers) {
                            if (!selectMapWrapper.equals((Object)new MapWrapper(dimNameValue))) continue;
                            canAdd = true;
                            break;
                        }
                        if (!canAdd) continue;
                        filterAllItems.add(allItem);
                    }
                    blobFileSizeCheckReturnInfo.setAllItems(filterAllItems);
                    ArrayList<AttachmentCheckResultItem> filterErrItems = new ArrayList<AttachmentCheckResultItem>();
                    List errItems2 = blobFileSizeCheckReturnInfo.getErrItems();
                    for (AttachmentCheckResultItem errItem : errItems2) {
                        if (!orgCode.contains(errItem.getUnitCode())) continue;
                        boolean canAdd = false;
                        Map dimNameValue = errItem.getDimNameValue();
                        for (MapWrapper selectMapWrapper : selectMapWrappers) {
                            if (!selectMapWrapper.equals((Object)new MapWrapper(dimNameValue))) continue;
                            canAdd = true;
                            break;
                        }
                        if (!canAdd) continue;
                        filterErrItems.add(errItem);
                    }
                    blobFileSizeCheckReturnInfo.setErrItems(filterErrItems);
                } else {
                    List<String> orgCode = param.getOrgCode();
                    ArrayList<AttachmentCheckResultItem> filterAllItems = new ArrayList<AttachmentCheckResultItem>();
                    List allItems = blobFileSizeCheckReturnInfo.getAllItems();
                    for (AttachmentCheckResultItem allItem : allItems) {
                        if (!orgCode.contains(allItem.getUnitCode())) continue;
                        filterAllItems.add(allItem);
                    }
                    blobFileSizeCheckReturnInfo.setAllItems(filterAllItems);
                    ArrayList<AttachmentCheckResultItem> filterErrItems = new ArrayList<AttachmentCheckResultItem>();
                    errItems = blobFileSizeCheckReturnInfo.getErrItems();
                    for (AttachmentCheckResultItem attachmentCheckResultItem : errItems) {
                        if (!orgCode.contains(attachmentCheckResultItem.getUnitCode())) continue;
                        filterErrItems.add(attachmentCheckResultItem);
                    }
                    blobFileSizeCheckReturnInfo.setErrItems(filterErrItems);
                }
                taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
                HashMap<String, IEntityTable> dimNameTableMap = new HashMap<String, IEntityTable>();
                String dimsStr = taskDefine.getDims();
                if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dimsStr)) {
                    void var12_21;
                    String[] dimArrays;
                    errItems = dimArrays = dimsStr.split(";");
                    int dims = errItems.length;
                    boolean bl = false;
                    while (var12_21 < dims) {
                        String dimArra = errItems[var12_21];
                        IEntityDefine dimEntityDefine = this.entityMetaService.queryEntity(dimArra);
                        if (null != dimEntityDefine) {
                            IEntityQuery query = this.entityQueryHelper.getDimEntityQuery(dimArra, param.getPeriod());
                            IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, param.getFormSchemeKey(), true);
                            dimNameTableMap.put(dimEntityDefine.getDimensionName(), entityTable);
                        }
                        ++var12_21;
                    }
                }
                for (AttachmentCheckResultItem errItem : blobFileSizeCheckReturnInfo.getErrItems()) {
                    if (null == errItem.getDimNameValue() || errItem.getDimNameValue().isEmpty()) continue;
                    HashMap dimNameTitle = new HashMap();
                    for (Map.Entry entry : dimNameTableMap.entrySet()) {
                        entityRow = ((IEntityTable)entry.getValue()).findByEntityKey((String)errItem.getDimNameValue().get(entry.getKey()));
                        dimTitle = null != entityRow ? entityRow.getTitle() : (String)entry.getKey();
                        dimNameTitle.put(entry.getKey(), dimTitle);
                    }
                    errItem.setDimNameTitle(dimNameTitle);
                }
                for (AttachmentCheckResultItem allItem : blobFileSizeCheckReturnInfo.getAllItems()) {
                    if (null == allItem.getDimNameValue() || allItem.getDimNameValue().isEmpty()) continue;
                    HashMap dimNameTitle = new HashMap();
                    for (Map.Entry entry : dimNameTableMap.entrySet()) {
                        entityRow = ((IEntityTable)entry.getValue()).findByEntityKey((String)allItem.getDimNameValue().get(entry.getKey()));
                        dimTitle = null != entityRow ? entityRow.getTitle() : (String)entry.getKey();
                        dimNameTitle.put(entry.getKey(), dimTitle);
                    }
                    allItem.setDimNameTitle(dimNameTitle);
                }
                res.setInfo(blobFileSizeCheckReturnInfo);
                this.buildTable(res, blobFileSizeCheckReturnInfo);
            }
            if (org.springframework.util.StringUtils.hasLength(itemConfig = this.schemeService.getItemConfig(param.getCheckId()))) {
                res.setConfig(SerializeUtil.deserializeFromJson(this.schemeService.getItemConfig(param.getCheckId()), AttachmentConfig.class));
            } else {
                res.setConfig(new AttachmentConfig());
            }
            return res;
        }
        catch (Exception e) {
            logger.error("\u9644\u4ef6\u5ba1\u6838\u7ed3\u679c\u53cd\u5e8f\u5217\u5316\u6570\u636e\u5f02\u5e38", e);
            return null;
        }
    }

    private void buildTable(AttachmentResVO res, AttachmentCheckReturnInfo blobFileSizeCheckReturnInfo) {
        ArrayList<List<String>> table = new ArrayList<List<String>>();
        res.setTable(table);
        ArrayList<String> head = new ArrayList<String>();
        table.add(head);
        head.addAll(Arrays.asList(TITLES));
        if (!CollectionUtils.isEmpty(blobFileSizeCheckReturnInfo.getAllItems())) {
            this.addHead(head, blobFileSizeCheckReturnInfo);
            this.addBody(table, blobFileSizeCheckReturnInfo);
        }
    }

    private void addBody(List<List<String>> table, AttachmentCheckReturnInfo blobFileSizeCheckReturnInfo) {
        int r = 1;
        for (AttachmentCheckResultItem allItem : blobFileSizeCheckReturnInfo.getAllItems()) {
            ArrayList<String> list = new ArrayList<String>();
            table.add(list);
            list.addAll(Arrays.asList(String.valueOf(r++), allItem.getUnitCode(), allItem.getUnitTitle()));
            List fieldItems = allItem.getFieldItems();
            if (CollectionUtils.isEmpty(fieldItems)) continue;
            for (AttachmentCheckFieldResultItem fieldItem : fieldItems) {
                list.add(String.format(FILE_STR, fieldItem.getFilesInField().size(), fieldItem.getErrFilesInField().size(), fieldItem.getTotleFilesSize()));
            }
        }
    }

    private void addHead(List<String> head, AttachmentCheckReturnInfo blobFileSizeCheckReturnInfo) {
        AttachmentCheckResultItem resultItem = (AttachmentCheckResultItem)blobFileSizeCheckReturnInfo.getAllItems().get(0);
        List fieldItems = resultItem.getFieldItems();
        if (!CollectionUtils.isEmpty(fieldItems)) {
            for (AttachmentCheckFieldResultItem item : fieldItems) {
                head.add(item.getFieldTitle());
            }
        }
    }

    @Override
    public void exportResult(HttpServletResponse response, AttachmentQueryPM exportPM) {
        try (ServletOutputStream outputStream = response.getOutputStream();){
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("file.xlsx", "UTF-8"));
            MultCheckRes byId = this.multCheckResDao.findById(UUIDMerger.merge((String)exportPM.getRunId(), (String)exportPM.getItemKey()));
            if (byId != null) {
                AttachmentCheckReturnInfo info = SerializeUtil.deserializeFromJson(byId.getData(), AttachmentCheckReturnInfo.class);
                if (Boolean.TRUE.equals(exportPM.getExportAll())) {
                    this.doExport((OutputStream)outputStream, info.getAllItems());
                } else {
                    this.doExport((OutputStream)outputStream, info.getAllItems().stream().filter(x -> exportPM.getOrgCode().contains(x.getUnitCode())).collect(Collectors.toList()));
                }
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u6570\u636e\u5931\u8d25", e);
        }
    }

    private void doExport(OutputStream outputStream, List<AttachmentCheckResultItem> allItems) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u5ba1\u6838\u7ed3\u679c");
        this.createHead(workbook, sheet, allItems);
        this.createBody(sheet, allItems);
        workbook.write(outputStream);
        workbook.close();
    }

    private void createBody(Sheet sheet, List<AttachmentCheckResultItem> allItems) {
        int r = 1;
        for (AttachmentCheckResultItem allItem : allItems) {
            Row row = sheet.createRow(r);
            int c = 0;
            Cell cell = row.createCell(c++);
            cell.setCellValue(r);
            cell = row.createCell(c++);
            cell.setCellValue(allItem.getUnitCode());
            cell = row.createCell(c++);
            cell.setCellValue(allItem.getUnitTitle());
            List fieldItems = allItem.getFieldItems();
            if (!CollectionUtils.isEmpty(fieldItems)) {
                for (AttachmentCheckFieldResultItem fieldItem : fieldItems) {
                    cell = row.createCell(c++);
                    cell.setCellValue(String.format(FILE_STR, fieldItem.getFilesInField().size(), fieldItem.getErrFilesInField().size(), fieldItem.getTotleFilesSize()));
                }
            }
            ++r;
        }
    }

    private void createHead(Workbook workbook, Sheet sheet, List<AttachmentCheckResultItem> allItems) {
        Row head = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u9ed1\u4f53");
        int c = 0;
        int defaultWidth = 6000;
        for (String title : TITLES) {
            Cell cell = head.createCell(c);
            cell.setCellValue(title);
            sheet.setColumnWidth(c, defaultWidth);
            ++c;
        }
        for (AttachmentCheckResultItem allItem : allItems) {
            Cell cell = head.createCell(c);
            cell.setCellValue(allItem.getFieldTitle());
            sheet.setColumnWidth(c, defaultWidth);
            ++c;
        }
    }
}

