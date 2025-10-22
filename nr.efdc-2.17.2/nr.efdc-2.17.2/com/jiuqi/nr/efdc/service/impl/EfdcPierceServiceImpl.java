/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.input.RegionRestructureInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.RegionSingleDataSet
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.RegionDataFactory
 *  com.jiuqi.nr.zbquery.util.PeriodUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.efdc.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.param.EfdcBatchEnableInfo;
import com.jiuqi.nr.efdc.param.EfdcEnableInfo;
import com.jiuqi.nr.efdc.param.EfdcNewRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcResponseInfo;
import com.jiuqi.nr.efdc.service.EfdcExtendService;
import com.jiuqi.nr.efdc.service.EfdcPierceService;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.efdc.service.SoluctionQueryService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EfdcPierceServiceImpl
implements EfdcPierceService {
    @Autowired
    private RunTimeFormulaDefineDao runTimeFormulaDefineDao;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataLinkService iRuntimeDataLinkService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private SoluctionQueryService soluctionQueryService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    IEFDCConfigService efdcConfigServiceImpl;
    @Autowired
    IJtableEntityService jtableEntityService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired(required=false)
    private EfdcExtendService efdcExtendService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Resource
    private IEntityMetaService metaService;
    private static final Logger logger = LoggerFactory.getLogger(EfdcPierceServiceImpl.class);
    public static final String FIELDNAME_OPPUNIT = "OPPUNIT";
    private static final String REG_1 = "*1";
    private static final String REG_3 = "*3";
    private static final String KMDM_ZB = "KMDM";
    private static final String EJDM_ZB = "EJDM";
    private static final String ZFZEX = "ZFZEX";

    @Override
    public EfdcResponseInfo getResponseInfo(EfdcRequestInfo efdcRequestInfo) throws Exception {
        EfdcResponseInfo responseInfo = new EfdcResponseInfo();
        SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
        JtableContext jtableContext = efdcRequestInfo.getJtableContext();
        String formSchmeKey = jtableContext.getFormSchemeKey();
        String taskKey = jtableContext.getTaskKey();
        String formKey = jtableContext.getFormKey();
        DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(efdcRequestInfo.getLinkKey());
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchmeKey));
        Map dimValueSet = jtableContext.getDimensionSet();
        String perid = ((DimensionValue)dimValueSet.get("DATATIME")).getValue();
        PeriodWrapper periodWrapper = new PeriodWrapper(perid);
        Date[] dateRegion = executorContext.getPeriodAdapter().getPeriodDateRegion(periodWrapper);
        if (dateRegion != null && dateRegion.length == 2 && dateRegion[1] != null) {
            responseInfo.setStartTime(stf.format(dateRegion[0]));
            responseInfo.setEndTime(stf.format(dateRegion[1]));
        }
        HashMap<String, String> otherDimValueSet = new HashMap<String, String>();
        String dimValue = this.getDimValue(formSchmeKey, dimValueSet, otherDimValueSet);
        QueryObjectImpl queryObject = new QueryObjectImpl(taskKey, formSchmeKey, dimValue);
        String unitKey = efdcRequestInfo.getUnitCode();
        String unitBblx = "";
        IEntityAttribute bblxField = null;
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchmeKey);
        try {
            IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwEntity.getKey());
            bblxField = dwEntityModel.getBblxField();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (bblxField != null) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityKey(unitKey);
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryByKeyInfo.setContext(efdcRequestInfo.getJtableContext());
            entityQueryByKeyInfo.getCaptionFields().add(bblxField.getCode());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            int bblxIndex = queryEntityDataByKey.getCells().indexOf(bblxField.getCode());
            EntityData entity = queryEntityDataByKey.getEntity();
            if (entity != null && bblxIndex >= 0) {
                unitBblx = (String)entity.getData().get(bblxIndex);
            }
        }
        Boolean isFloat = efdcRequestInfo.isFloatType();
        this.setEfdcNormalFormula(jtableContext, formKey, efdcRequestInfo, dataLink, isFloat, responseInfo);
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String address = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDCPIERCEURL");
        if (org.springframework.util.StringUtils.isEmpty(address)) {
            address = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDCURL");
        }
        String messageType = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDC_PENETRAT_TYPE");
        String containAccounts = this.iNvwaSystemOptionService.get("fext-settings-group", "DEFAULT_CONTAINS_ACCOUNTING_VOUCHERS");
        responseInfo.setJsonType("1".equals(messageType));
        responseInfo.setFloat(isFloat);
        responseInfo.setInclude(containAccounts.equals("1") ? true : isFloat == false);
        responseInfo.setBblx(unitBblx);
        responseInfo.setEfdcUrl(address);
        responseInfo.setUserName(loginUser.getName());
        responseInfo.setFieldId(dataLink.getLinkExpression());
        responseInfo.setEntityList(this.jtableParamService.getEntityList(formSchmeKey));
        responseInfo.setUnitTitle(dwEntity.getTitle());
        responseInfo.setUnitCode(efdcRequestInfo.getUnitCode());
        return responseInfo;
    }

    @Override
    public EfdcResponseInfo getResponseInfoNew(EfdcNewRequestInfo efdcNewRequestInfo) throws Exception {
        EfdcRequestInfo efdcRequestInfo = new EfdcRequestInfo();
        JtableContext jtableContext = new JtableContext();
        String formSchemeKey = efdcNewRequestInfo.getFormSchemeKey();
        String linkKey = efdcNewRequestInfo.getLinkKey();
        Map<String, String> dimensionSet = efdcNewRequestInfo.getDimensionSet();
        DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(linkKey);
        efdcRequestInfo.setLinkKey(linkKey);
        efdcRequestInfo.setRegionId(dataLinkDefine.getRegionKey());
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        String formKey = dataRegionDefine.getFormKey();
        FieldDefine fieldDefine = this.iRunTimeViewController.queryFieldDefine(dataLinkDefine.getLinkExpression());
        efdcRequestInfo.setFieldCode(fieldDefine.getCode());
        efdcRequestInfo.setUnitCode(dimensionSet.get("MD_ORG"));
        DataField fieldData = (DataField)fieldDefine;
        if (fieldData.getDataFieldKind().equals((Object)DataFieldKind.FIELD)) {
            efdcRequestInfo.setFloatType(true);
        } else {
            efdcRequestInfo.setFloatType(false);
        }
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String period = PeriodUtil.toNrPeriod((String)dimensionSet.get("DATATIME"), (PeriodType)formSchemeDefine.getPeriodType());
        dimensionSet.put("DATATIME", period);
        FormGroupDefine formGroupDefine = (FormGroupDefine)this.iRunTimeViewController.getFormGroupsByFormKey(formKey).get(0);
        FormulaSchemeDefine formulaSchemeDefine = this.iFormulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
        jtableContext.setFormulaSchemeKey(formulaSchemeDefine.getKey());
        jtableContext.setFormGroupKey(formGroupDefine.getKey());
        jtableContext.setTaskKey(formSchemeDefine.getTaskKey());
        List entityList = this.jtableParamService.getEntityList(formSchemeKey);
        ArrayList<String> entityDimensionName = new ArrayList<String>();
        for (EntityViewData entityInfo : entityList) {
            entityDimensionName.add(entityInfo.getDimensionName());
            if (dimensionSet.get(entityInfo.getDimensionName()) != null) continue;
            dimensionSet.put(entityInfo.getDimensionName(), "");
        }
        HashMap<String, DimensionValue> newDimensionValue = new HashMap<String, DimensionValue>();
        StringBuilder rowId = new StringBuilder("");
        for (Map.Entry<String, String> dimensionValueEntry : dimensionSet.entrySet()) {
            DimensionValue dimensionValue;
            if (entityDimensionName.contains(dimensionValueEntry.getKey())) {
                dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionValueEntry.getKey());
                dimensionValue.setValue(dimensionValueEntry.getValue());
                newDimensionValue.put(dimensionValueEntry.getKey(), dimensionValue);
                continue;
            }
            if (dimensionValueEntry.getKey().equals("ADJUST")) {
                dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionValueEntry.getKey());
                dimensionValue.setValue(dimensionValueEntry.getValue());
                newDimensionValue.put(dimensionValueEntry.getKey(), dimensionValue);
                continue;
            }
            rowId.append(dimensionValueEntry.getValue() + "#^$");
        }
        if (rowId.length() > 3) {
            rowId.delete(rowId.length() - 3, rowId.length());
        }
        jtableContext.setDimensionSet(newDimensionValue);
        jtableContext.setFormKey(formKey);
        jtableContext.setFormSchemeKey(formSchemeKey);
        if (StringUtils.isEmpty((String)efdcRequestInfo.getRowId())) {
            efdcRequestInfo.setRowId(rowId.toString());
        }
        efdcRequestInfo.setJtableContext(jtableContext);
        return this.getResponseInfo(efdcRequestInfo);
    }

    @Override
    public Integer getBBLX(EfdcNewRequestInfo efdcRequestInfo) throws Exception {
        String formSchemeKey = efdcRequestInfo.getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formSchemeDefine.getTaskKey();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        IEntityAttribute bblxField = this.metaService.getEntityModel(taskDefine.getDw()).getBblxField();
        if (bblxField != null && bblxField.getCode().equals("1")) {
            return 1;
        }
        return -1;
    }

    @Override
    public EfdcEnableInfo getEFDCEnable(EfdcNewRequestInfo efdcRequestInfo) throws Exception {
        String period;
        EfdcEnableInfo efdcEnableInfo = new EfdcEnableInfo();
        JtableContext jtableContext = new JtableContext();
        String formSchemeKey = efdcRequestInfo.getFormSchemeKey();
        String linkKey = efdcRequestInfo.getLinkKey();
        Map<String, String> dimensionSet = efdcRequestInfo.getDimensionSet();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        try {
            period = PeriodUtil.toNrPeriod((String)dimensionSet.get("DATATIME"), (PeriodType)formSchemeDefine.getPeriodType());
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            period = dimensionSet.get("DATATIME");
        }
        dimensionSet.put("DATATIME", period);
        DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(linkKey);
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        String formKey = dataRegionDefine.getFormKey();
        List entityList = this.jtableParamService.getEntityList(formSchemeKey);
        ArrayList<String> entityDimensionName = new ArrayList<String>();
        for (EntityViewData entityInfo : entityList) {
            entityDimensionName.add(entityInfo.getDimensionName());
        }
        HashMap<String, DimensionValue> newDimensionValue = new HashMap<String, DimensionValue>();
        StringBuilder rowId = new StringBuilder("");
        for (Map.Entry<String, String> dimensionValueEntry : dimensionSet.entrySet()) {
            if (entityDimensionName.contains(dimensionValueEntry.getKey())) {
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionValueEntry.getKey());
                dimensionValue.setValue(dimensionValueEntry.getValue());
                newDimensionValue.put(dimensionValueEntry.getKey(), dimensionValue);
                continue;
            }
            rowId.append(dimensionValueEntry.getValue() + "#^$");
        }
        if (rowId.length() > 3) {
            rowId.delete(rowId.length() - 3, rowId.length());
        }
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("ROWID");
        dimensionValue.setValue(rowId.toString());
        newDimensionValue.put("ROWID", dimensionValue);
        FormGroupDefine formGroupDefine = (FormGroupDefine)this.iRunTimeViewController.getFormGroupsByFormKey(formKey).get(0);
        FormulaSchemeDefine formulaSchemeDefine = this.iFormulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
        jtableContext.setFormulaSchemeKey(formulaSchemeDefine.getKey());
        jtableContext.setFormGroupKey(formGroupDefine.getKey());
        jtableContext.setTaskKey(formSchemeDefine.getTaskKey());
        jtableContext.setDimensionSet(newDimensionValue);
        jtableContext.setFormKey(formKey);
        jtableContext.setFormSchemeKey(formSchemeKey);
        ArrayList<String> linkKeys = new ArrayList<String>();
        linkKeys.add(linkKey);
        if (this.efdcExtendService != null) {
            boolean[] result = this.efdcExtendService.queryBatchEfdcEnable(jtableContext, linkKeys);
            efdcEnableInfo.setEFDCEnable(result[0]);
        } else {
            List efdcDataLinks = this.jtableParamService.getExtractDataLinkList(jtableContext);
            if (efdcDataLinks.contains(linkKey)) {
                efdcEnableInfo.setEFDCEnable(true);
            } else {
                efdcEnableInfo.setEFDCEnable(false);
            }
        }
        efdcEnableInfo.setBBLX(this.getBBLX(efdcRequestInfo));
        efdcEnableInfo.setEFDCSwitch(this.iRunTimeViewController.queryTaskDefine(this.iRunTimeViewController.getFormScheme(formSchemeKey).getTaskKey()).getEfdcSwitch());
        return efdcEnableInfo;
    }

    public boolean[][] queryBatchEfdcEnable(EfdcBatchEnableInfo efdcBatchEnableInfo) {
        if (efdcBatchEnableInfo.getDimsList().size() == 0 || efdcBatchEnableInfo.getLinkKeys().size() == 0) {
            return null;
        }
        boolean[][] batchResult = new boolean[efdcBatchEnableInfo.getDimsList().size()][efdcBatchEnableInfo.getLinkKeys().size()];
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(efdcBatchEnableInfo.getFormSchemeKey());
        List<Map<String, String>> dimsList = efdcBatchEnableInfo.getDimsList();
        List<String> linkKeys = efdcBatchEnableInfo.getLinkKeys();
        DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(linkKeys.get(0));
        DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        String formKey = dataRegionDefine.getFormKey();
        List entityList = this.jtableParamService.getEntityList(efdcBatchEnableInfo.getFormSchemeKey());
        ArrayList<String> entityDimensionName = new ArrayList<String>();
        HashMap<String, boolean[]> mapCache = new HashMap<String, boolean[]>();
        for (EntityViewData entityInfo : entityList) {
            entityDimensionName.add(entityInfo.getDimensionName());
        }
        for (int i = 0; i < dimsList.size(); ++i) {
            if (dimsList.get(i) != null) {
                String period;
                String dataTime = dimsList.get(i).get("DATATIME");
                try {
                    period = PeriodUtil.toNrPeriod((String)dataTime, (PeriodType)formSchemeDefine.getPeriodType());
                }
                catch (Exception e) {
                    logger.info(e.getMessage());
                    period = dataTime;
                }
                Map<String, String> dims = dimsList.get(i);
                dims.put("DATATIME", period);
                JtableContext jtableContext = new JtableContext();
                HashMap<String, DimensionValue> newDimensionValue = new HashMap<String, DimensionValue>();
                StringBuilder rowId = new StringBuilder("");
                StringBuilder dimesionStr = new StringBuilder("");
                for (Map.Entry<String, String> dimensionValueEntry : dims.entrySet()) {
                    DimensionValue dimensionValue;
                    if (entityDimensionName.contains(dimensionValueEntry.getKey())) {
                        dimensionValue = new DimensionValue();
                        dimensionValue.setName(dimensionValueEntry.getKey());
                        dimensionValue.setValue(dimensionValueEntry.getValue());
                        dimesionStr.append(dimensionValueEntry.getValue() + "-");
                        newDimensionValue.put(dimensionValueEntry.getKey(), dimensionValue);
                        continue;
                    }
                    if (dimensionValueEntry.getKey().equals("ADJUST")) {
                        dimensionValue = new DimensionValue();
                        dimensionValue.setName(dimensionValueEntry.getKey());
                        dimensionValue.setValue(dimensionValueEntry.getValue());
                        dimesionStr.append(dimensionValueEntry.getValue() + "-");
                        newDimensionValue.put(dimensionValueEntry.getKey(), dimensionValue);
                        continue;
                    }
                    rowId.append(dimensionValueEntry.getValue() + "#^$");
                }
                if (rowId.length() > 3) {
                    rowId.delete(rowId.length() - 3, rowId.length());
                }
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName("ROWID");
                dimensionValue.setValue(rowId.toString());
                newDimensionValue.put("ROWID", dimensionValue);
                FormGroupDefine formGroupDefine = (FormGroupDefine)this.iRunTimeViewController.getFormGroupsByFormKey(formKey).get(0);
                FormulaSchemeDefine formulaSchemeDefine = this.iFormulaRunTimeController.getDefaultFormulaSchemeInFormScheme(efdcBatchEnableInfo.getFormSchemeKey());
                jtableContext.setFormulaSchemeKey(formulaSchemeDefine.getKey());
                jtableContext.setFormGroupKey(formGroupDefine.getKey());
                jtableContext.setTaskKey(formSchemeDefine.getTaskKey());
                jtableContext.setDimensionSet(newDimensionValue);
                jtableContext.setFormKey(formKey);
                jtableContext.setFormSchemeKey(efdcBatchEnableInfo.getFormSchemeKey());
                if (mapCache.containsKey(dimesionStr.toString())) {
                    batchResult[i] = (boolean[])mapCache.get(dimesionStr.toString());
                    continue;
                }
                if (this.efdcExtendService != null) {
                    batchResult[i] = this.efdcExtendService.queryBatchEfdcEnable(jtableContext, linkKeys);
                } else {
                    List efdcDataLinks = this.jtableParamService.getExtractDataLinkList(jtableContext);
                    for (int j = 0; j < linkKeys.size(); ++j) {
                        String linkKey = linkKeys.get(j);
                        batchResult[i][j] = efdcDataLinks.contains(linkKey);
                    }
                }
                mapCache.put(dimesionStr.toString(), batchResult[i]);
                continue;
            }
            for (int j = 0; j < linkKeys.size(); ++j) {
                batchResult[i][j] = false;
            }
        }
        return batchResult;
    }

    private void setEfdcNormalFormula(JtableContext jtableContext, String formKey, EfdcRequestInfo efdcRequestInfo, DataLinkDefine dataLink, boolean isFloat, EfdcResponseInfo responseInfo) throws Exception {
        FormulaSchemeDefine formulaSchemeDefine = this.getFormulaScheme(jtableContext);
        List formulas = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeDefine.getKey(), formKey);
        List queryPublicFormulaDefineByScheme = this.formulaRunTimeController.queryPublicFormulaDefineByScheme(formulaSchemeDefine.getKey(), formKey);
        formulas.addAll(queryPublicFormulaDefineByScheme);
        if (isFloat) {
            this.getEfdcFloatFormula(jtableContext, formKey, efdcRequestInfo, responseInfo, formulas);
        } else {
            List formulaExpress = formulas.stream().map(e -> e.getExpression()).collect(Collectors.toList());
            String cell = "[".concat(dataLink.getRowNum() + "").concat(",").concat(dataLink.getColNum() + "]");
            List effectiveFormulas = formulaExpress.stream().filter(e -> e.contains(cell) && !e.startsWith("//")).collect(Collectors.toList());
            if (effectiveFormulas.size() > 0) {
                String formula = (String)effectiveFormulas.get(effectiveFormulas.size() - 1);
                formula = formula.substring(formula.indexOf("=") + 1);
                responseInfo.setEfdcFormula(formula);
            }
        }
    }

    private void getEfdcFloatFormula(JtableContext jtableContext, String formKey, EfdcRequestInfo efdcRequestInfo, EfdcResponseInfo responseInfo, List<FormulaDefine> formulas) throws Exception {
        String fieldCode = efdcRequestInfo.getFieldCode();
        Map<String, Object> fieldValues = this.getSingleRecordValues(efdcRequestInfo.getRegionId(), efdcRequestInfo.getRowId(), jtableContext);
        String frangeFml = null;
        String formula = null;
        String firstNumberFieldFml = null;
        String firstNumberFieldCode = null;
        HashMap<String, Set<String>> filterVaues = new HashMap<String, Set<String>>();
        FormDefine form = this.iRunTimeViewController.queryFormById(formKey);
        StringBuffer filterFml = new StringBuffer(64);
        try {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            if (form != null) {
                executorContext.setDefaultGroupName(form.getFormCode());
            }
            executorContext.setDefaultGroupName(form.getFormCode());
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionRuntimeController, this.iEntityViewRunTimeController, efdcRequestInfo.getJtableContext().getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            QueryContext qContext = new QueryContext(executorContext, null);
            ReportFormulaParser parser = executorContext.getCache().getFormulaParser(true);
            efdcRequestInfo.getRegionId();
            List regionData = this.iRunTimeViewController.getAllRegionsInForm(formKey);
            String attr = null;
            String startAttr = null;
            if (regionData.size() > 1) {
                DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(efdcRequestInfo.getLinkKey());
                int rowNum = dataLink.getRowNum();
                attr = "[F" + rowNum + ",*]";
                startAttr = "[" + rowNum;
            }
            for (FormulaDefine formulaDefine : formulas) {
                String messageType;
                boolean isNumberField;
                String expression = formulaDefine.getExpression();
                if (expression.startsWith("//")) continue;
                int equalIndex = expression.indexOf("=");
                String assginExp = expression.substring(0, equalIndex);
                String efdcExpression = expression.substring(equalIndex + 1);
                if (assginExp.contains("*")) {
                    if (regionData.size() < 2) {
                        frangeFml = efdcExpression;
                        responseInfo.setFrangeFml(frangeFml);
                        continue;
                    }
                    if (!assginExp.equals(attr)) continue;
                    frangeFml = efdcExpression;
                    responseInfo.setFrangeFml(frangeFml);
                    continue;
                }
                IExpression exp = parser.parseEval(assginExp, (IContext)qContext);
                DynamicDataNode dataNode = null;
                for (IASTNode child : exp) {
                    if (!(child instanceof DynamicDataNode)) continue;
                    dataNode = (DynamicDataNode)child;
                    break;
                }
                String region = dataNode.getDataLink().getRegion();
                FieldDefine fieldDefine = dataNode.getDataLink().getField();
                this.filterFml(fieldValues, startAttr, expression, fieldDefine, efdcRequestInfo.getUnitCode(), filterVaues);
                if (formula != null || !(isNumberField = fieldDefine.getType() == FieldType.FIELD_TYPE_FLOAT || fieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL || fieldDefine.getType() == FieldType.FIELD_TYPE_INTEGER)) continue;
                if (!org.springframework.util.StringUtils.isEmpty(efdcExpression) && "1".equals(messageType = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDC_PENETRAT_TYPE"))) {
                    efdcExpression = this.replaceRegChar(efdcExpression, fieldValues);
                }
                if (region == null || !region.equals(efdcRequestInfo.getRegionId())) continue;
                if (fieldCode.equals(fieldDefine.getCode())) {
                    formula = efdcExpression;
                }
                if (null != firstNumberFieldCode) continue;
                firstNumberFieldCode = fieldDefine.getCode();
                firstNumberFieldFml = efdcExpression;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (formula == null) {
            responseInfo.setEfdcFormula(firstNumberFieldFml);
            responseInfo.setFieldCode(Objects.requireNonNull(form).getFormCode() + "_" + firstNumberFieldCode);
        } else {
            responseInfo.setEfdcFormula(formula);
            responseInfo.setFieldCode(Objects.requireNonNull(form).getFormCode() + "_" + fieldCode);
        }
        this.appendFilter(filterFml, filterVaues);
        responseInfo.setFilterFml(filterFml.toString());
        logger.info(String.valueOf(filterFml));
    }

    private void appendFilter(StringBuffer filterFml, Map<String, Set<String>> filterVaues) {
        filterFml.append(" filters='");
        boolean isAdd = false;
        for (Map.Entry<String, Set<String>> entry : filterVaues.entrySet()) {
            String exp = entry.getKey();
            Set<String> expValue = entry.getValue();
            if (isAdd) {
                filterFml.append(",");
            }
            filterFml.append(exp).append("=");
            expValue.forEach(e -> filterFml.append("\"").append((String)e).append(";\""));
            isAdd = true;
        }
        filterFml.append("'");
    }

    private String replaceRegChar(String efdcExpression, Map<String, Object> fieldValues) {
        String ejdmValue;
        String kmdmValue = fieldValues.get(KMDM_ZB) == null ? "" : fieldValues.get(KMDM_ZB).toString();
        String string = ejdmValue = fieldValues.get(EJDM_ZB) == null ? "" : fieldValues.get(EJDM_ZB).toString();
        if (!efdcExpression.startsWith(ZFZEX)) {
            return efdcExpression;
        }
        return efdcExpression.replace(REG_1, kmdmValue).replace(REG_3, ejdmValue);
    }

    private void filterFml(Map<String, Object> fieldValues, String startAttr, String expressionVal, FieldDefine fieldDefine, String unitCode, Map<String, Set<String>> filterVaues) {
        List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
        String regex1 = "getval\\(\"?\\*\\d+\"?\\)";
        String regex2 = "GETVAL\\(\"?\\*\\d+\"?\\)";
        String efdcExpression = expressionVal.substring(expressionVal.indexOf("=") + 1);
        if ((efdcExpression.matches(regex1) || efdcExpression.matches(regex2)) && expressionVal.startsWith(startAttr)) {
            String value = this.getUnitValue(fieldValues, fieldDefine.getCode());
            if (org.springframework.util.StringUtils.isEmpty(value)) {
                return;
            }
            int indexOf = value.indexOf("||");
            if (indexOf != -1) {
                value = value.substring(0, indexOf);
            }
            if (filterVaues.containsKey(efdcExpression)) {
                filterVaues.get(efdcExpression).add(value);
            } else {
                HashSet<String> filterVals = new HashSet<String>();
                filterVals.add(value);
                filterVaues.put(efdcExpression, filterVals);
            }
        } else if ("EFDCMOREFILTERFML".equalsIgnoreCase(((DataFieldDeployInfo)deployInfos.get(0)).getFieldName())) {
            String reg = "\\s*\\+\\s*\\\";\\\"\\s*\\+\\s*";
            String[] efdcExpressions = efdcExpression.split(reg);
            for (int i = 0; i < efdcExpressions.length; ++i) {
                String[] oneFieldSplitValues;
                String value;
                String expression = efdcExpressions[i];
                if (org.springframework.util.StringUtils.isEmpty(expression) || null == (value = (String)fieldValues.get(fieldDefine.getCode().toUpperCase())) || (oneFieldSplitValues = String.valueOf(value).split(";")).length < efdcExpressions.length) continue;
                if (filterVaues.containsKey(expression)) {
                    filterVaues.get(expression).add(oneFieldSplitValues[i]);
                    continue;
                }
                HashSet<String> filterVals = new HashSet<String>();
                filterVals.add(oneFieldSplitValues[i]);
                filterVaues.put(expression, filterVals);
            }
        }
    }

    private Map<String, Object> getSingleRecordValues(String regionKey, String rowId, JtableContext jtableContext) throws Exception {
        String[] rowids;
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        for (String id : rowids = rowId.split(";")) {
            this.getSelectedRecoredValues(regionKey, id, jtableContext, fieldValues);
        }
        return fieldValues;
    }

    /*
     * WARNING - void declaration
     */
    private void getSelectedRecoredValues(String regionKey, String rowId, JtableContext jtableContext, Map<String, Object> fieldValues) throws Exception {
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(jtableContext);
        RegionRestructureInfo regionRestructureInfo = new RegionRestructureInfo();
        regionRestructureInfo.setDataID(rowId);
        regionQueryInfo.setRestructureInfo(regionRestructureInfo);
        RegionData region = this.jtableParamService.getRegion(regionKey);
        RegionDataFactory factory = new RegionDataFactory();
        regionQueryInfo.setRegionKey(regionKey);
        RegionSingleDataSet regionSingleDataSet = factory.getRegionSingleDataSet(region, regionQueryInfo);
        ArrayList<String> fieldCodes = new ArrayList<String>();
        ArrayList values = new ArrayList();
        Map cells = regionSingleDataSet.getCells();
        for (Map.Entry entry : cells.entrySet()) {
            List dataLinkKeys = (List)entry.getValue();
            for (String key : dataLinkKeys) {
                DataLinkDefine dataLink = this.iRuntimeDataLinkService.queryDataLink(key);
                if (null != dataLink) {
                    FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(dataLink.getLinkExpression());
                    fieldCodes.add(fieldDefine.getCode().toUpperCase());
                    continue;
                }
                fieldCodes.add(key);
            }
        }
        List data = regionSingleDataSet.getData();
        for (List dataRow : data) {
            values.addAll(dataRow);
        }
        if (fieldCodes.size() == values.size()) {
            void var14_17;
            boolean bl = false;
            while (var14_17 < fieldCodes.size()) {
                Object object = fieldValues.get(fieldCodes.get((int)var14_17));
                if (object != null) {
                    fieldValues.put((String)fieldCodes.get((int)var14_17), object + ";" + values.get((int)var14_17));
                } else {
                    fieldValues.put((String)fieldCodes.get((int)var14_17), values.get((int)var14_17));
                }
                ++var14_17;
            }
        }
    }

    public String getUnitValue(Map<String, Object> fieldValues, String fieldCode) {
        String value;
        if (fieldValues.containsKey(fieldCode = fieldCode.toUpperCase()) && null != (value = (String)fieldValues.get(fieldCode))) {
            return value;
        }
        return "";
    }

    private String getDimValue(String formSchmeKey, Map<String, DimensionValue> dimValueSet, Map<String, String> otherDimSet) {
        String value = null;
        List entityList = this.jtableParamService.getEntityList(formSchmeKey);
        for (EntityViewData entityInfo : entityList) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimValueSet);
            if (entityInfo.isMasterEntity()) {
                value = dimensionValueSet.getValue(entityInfo.getDimensionName()).toString();
                continue;
            }
            otherDimSet.put(entityInfo.getTableName(), dimValueSet.get(entityInfo.getDimensionName()).getValue());
        }
        return value;
    }

    private FormulaSchemeDefine getFormulaScheme(JtableContext jtableContext) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        excutorJtableContext.setFormKey("");
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)excutorJtableContext);
        String unitKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        List dimEntityList = this.jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            DimensionValue dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get(entityInfo.getDimensionName());
            dimMap.put(entityInfo.getTableName(), dimensionValue.getValue());
        }
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), unitKey);
        dimMap.put("DATATIME", ((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
        return this.efdcConfigServiceImpl.getSoluctionByDimensions(queryObjectImpl, dimMap, dwEntity.getKey());
    }
}

