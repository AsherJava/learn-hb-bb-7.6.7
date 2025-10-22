/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.nrextracteditctrl.client.NrExtractEditCtrlClient
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.common.param.ReadOnlyContext
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormulaDefineImpl
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.definition.option.internal.EfdcGetValueModify
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.efdc.service.impl.EFDCExtractFormulaService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.ExtractCellInfo
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.zbquery.util.PeriodUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.IGetReportFormDataHook;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.nrextracteditctrl.client.NrExtractEditCtrlClient;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.common.param.ReadOnlyContext;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.definition.option.internal.EfdcGetValueModify;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.efdc.service.impl.EFDCExtractFormulaService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BDEExtractFormulaService
extends EFDCExtractFormulaService {
    public static final String DIMENSIONSET_MD_ORG = "MD_ORG";
    public static final int BDE_FORMULA_SCHEME_KEY_LENGTH = 16;
    public static final int EFDC_FORMULA_SCHEME_KEY_LENGTH = 36;
    private static final Logger log = LoggerFactory.getLogger(BDEExtractFormulaService.class);
    @Autowired
    private FetchSchemeNrService fetchSchemeService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private IRuntimeDataLinkService iRuntimeDataLinkService;
    @Autowired
    private IRuntimeDataRegionService iRuntimeDataRegionService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired(required=false)
    private NrExtractEditCtrlClient nrExtractEditCtrlClient;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired(required=false)
    private IGetReportFormDataHook getReportFormDataHook;
    private final String FORMULA = "=";
    @Autowired
    private ITaskOptionController iTaskOptionController;
    private static final String DIM_MAP_DATATIME = "DATATIME";
    private static final String DIM_MAP_ORG_CODE = "MD_ORG";
    @Autowired
    private IEFDCConfigService efdcConfigService;

    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext jtableContext, String formulaSchemaKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        if (taskDefine == null || !taskDefine.getEfdcSwitch()) {
            return Collections.emptyList();
        }
        FormulaSchemeDefine soluctionByDimensions = super.getSoluctionByDimensions(jtableContext);
        if (soluctionByDimensions == null || StringUtils.isEmpty((String)soluctionByDimensions.getKey())) {
            return Collections.emptyList();
        }
        boolean isDefaulDatatLinkModifyOption = this.efdcGetValueDataLinKModifyisDefault(jtableContext.getTaskKey());
        if (soluctionByDimensions.getKey().length() == 36) {
            return this.efdcExtractDataLinkList(jtableContext, formulaSchemaKey, isDefaulDatatLinkModifyOption);
        }
        if (soluctionByDimensions.getKey().length() == 16) {
            return this.bdeExtractDataLinkList(jtableContext, soluctionByDimensions.getKey(), isDefaulDatatLinkModifyOption);
        }
        return super.getExtractDataLinkList(jtableContext, formulaSchemaKey);
    }

    public List<String> getExtractDataLinkList(JtableContext jtableContext) {
        if (this.getReportFormDataHook != null && this.getReportFormDataHook.enable(jtableContext)) {
            return this.getReportFormDataHook.execute(jtableContext);
        }
        FormulaSchemeDefine soluctionByDimensions = super.getSoluctionByDimensions(jtableContext);
        if (soluctionByDimensions == null || StringUtils.isEmpty((String)soluctionByDimensions.getKey())) {
            return Collections.emptyList();
        }
        if (soluctionByDimensions.getKey().length() == 16) {
            Set<String> bdeDataLinkSet = this.buildBdeDataLinkSet(jtableContext, soluctionByDimensions.getKey());
            return new ArrayList<String>(bdeDataLinkSet);
        }
        return super.getExtractDataLinkList(jtableContext);
    }

    private boolean efdcGetValueDataLinKModifyisDefault(String taskKey) {
        return new EfdcGetValueModify().getDefaultValue().equals(this.taskOptionController.getValue(taskKey, new EfdcGetValueModify().getKey()));
    }

    private List<ExtractCellInfo> efdcExtractDataLinkList(JtableContext jtableContext, String formulaSchemaKey, boolean isDefaulDatatLinkModifyOption) {
        List efdcExtractDataLinkList = super.getExtractDataLinkList(jtableContext, formulaSchemaKey);
        if (isDefaulDatatLinkModifyOption && Objects.nonNull(this.nrExtractEditCtrlClient)) {
            Map dimensionSet = jtableContext.getDimensionSet();
            String orgCode = ((DimensionValue)dimensionSet.get("MD_ORG")).getValue();
            List<String> efdcLinks = this.getLinkKeysByExtractLinkList(efdcExtractDataLinkList);
            NrExtractEditCtrlCondi condi = new NrExtractEditCtrlCondi(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), orgCode, jtableContext.getFormKey());
            List canEditLinkKeys = (List)this.nrExtractEditCtrlClient.queryEditableLinkIdListInForm(condi, efdcLinks).getData();
            for (ExtractCellInfo extractCellInfo : efdcExtractDataLinkList) {
                if (!canEditLinkKeys.contains(extractCellInfo.getLinkKey())) continue;
                extractCellInfo.setReadOnly(false);
            }
        }
        return efdcExtractDataLinkList;
    }

    private List<String> getLinkKeysByExtractLinkList(List<ExtractCellInfo> efdcExtractDataLinkList) {
        ArrayList<String> efdcDataLinks = new ArrayList<String>();
        for (ExtractCellInfo extractCellInfo : efdcExtractDataLinkList) {
            efdcDataLinks.add(extractCellInfo.getLinkKey());
        }
        return efdcDataLinks;
    }

    private Set<String> judgingAdaptCondi(JtableContext jtableContext, String bdeFetchSchmeKey, TaskDefine taskDefine) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond(bdeFetchSchmeKey, jtableContext.getFormSchemeKey(), jtableContext.getFormKey());
        List<FixedFieldDefineSettingDTO> fixedFieldDefineSettingDTOS = this.fetchSettingService.listDataLinkFixedSettingRowRecords(fetchSettingCond);
        HashSet<String> bdeDataLinkSet = new HashSet<String>();
        if (!CollectionUtils.isEmpty(fixedFieldDefineSettingDTOS)) {
            Map dimensionSet = jtableContext.getDimensionSet();
            String datatime = ((DimensionValue)dimensionSet.get(DIM_MAP_DATATIME)).getValue();
            String orgCode = ((DimensionValue)dimensionSet.get("MD_ORG")).getValue();
            String orgType = FetchTaskUtil.getOrgTypeByTaskAndCtx(jtableContext.getTaskKey());
            block0: for (FixedFieldDefineSettingDTO fieldDefineSetting : fixedFieldDefineSettingDTOS) {
                for (FixedAdaptSettingDTO adaptSetting : fieldDefineSetting.getFixedSettingData()) {
                    String adaptFormula = adaptSetting.getAdaptFormula();
                    if (StringUtils.isEmpty((String)adaptFormula)) {
                        bdeDataLinkSet.add(fieldDefineSetting.getDataLinkId());
                        continue block0;
                    }
                    FormulaExeParam formulaExeParam = new FormulaExeParam();
                    String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)adaptFormula, (String)orgType);
                    formulaExeParam.setFormulaExpress(adaptExpression);
                    HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                    adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), orgCode);
                    adaptContext.put(AdaptContextEnum.DATATIME.getKey(), datatime);
                    adaptContext.put(orgType, orgCode);
                    adaptContext.put("formSchemeKey", jtableContext.getFormSchemeKey());
                    formulaExeParam.setDimValMap(adaptContext);
                    if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                    bdeDataLinkSet.add(fieldDefineSetting.getDataLinkId());
                    continue block0;
                }
            }
        }
        return bdeDataLinkSet;
    }

    private List<ExtractCellInfo> bdeExtractDataLinkList(JtableContext jtableContext, String formulaSchemaKey, boolean isDefaulDatatLinkModifyOption) {
        if (formulaSchemaKey.length() != 16) {
            return Collections.emptyList();
        }
        LinkedList<ExtractCellInfo> bdeExtractDataLinkList = new LinkedList<ExtractCellInfo>();
        Set<String> bdeDataLinkSet = this.buildBdeDataLinkSet(jtableContext, formulaSchemaKey);
        List<String> editableLinkIds = this.queryEditableLinkIdList(jtableContext, new ArrayList<String>(bdeDataLinkSet), isDefaulDatatLinkModifyOption);
        for (String link : bdeDataLinkSet) {
            ExtractCellInfo extractCellInfo = editableLinkIds.contains(link) ? new ExtractCellInfo(link, false) : new ExtractCellInfo(link, true);
            bdeExtractDataLinkList.add(extractCellInfo);
        }
        return bdeExtractDataLinkList;
    }

    private List<String> queryEditableLinkIdList(JtableContext jtableContext, List<String> linkIds, boolean isDefaulDatatLinkModifyOption) {
        if (!isDefaulDatatLinkModifyOption) {
            return linkIds;
        }
        if (isDefaulDatatLinkModifyOption && Objects.nonNull(this.nrExtractEditCtrlClient)) {
            Map dimensionSet = jtableContext.getDimensionSet();
            String orgCode = ((DimensionValue)dimensionSet.get("MD_ORG")).getValue();
            NrExtractEditCtrlCondi condi = new NrExtractEditCtrlCondi(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), orgCode, jtableContext.getFormKey());
            List editableLinkIds = (List)this.nrExtractEditCtrlClient.queryEditableLinkIdListInForm(condi, linkIds).getData();
            return editableLinkIds;
        }
        return Collections.emptyList();
    }

    private Set<String> buildBdeDataLinkSet(JtableContext jtableContext, String formulaSchemaKey) {
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(formulaSchemaKey);
        if (fetchScheme == null) {
            return Collections.emptySet();
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        String bdeFetchSchemeId = fetchScheme.getId();
        HashSet<String> bdeDataLinkSet = new HashSet<String>();
        FetchSettingCond fetchSettingCond = new FetchSettingCond(bdeFetchSchemeId, jtableContext.getFormSchemeKey(), jtableContext.getFormKey());
        bdeDataLinkSet.addAll(this.judgingAdaptCondi(jtableContext, bdeFetchSchemeId, taskDefine));
        List<FloatRegionConfigVO> fetchFloatSettingVOS = this.fetchFloatSettingService.listFetchFloatSettingByFormId(fetchSettingCond);
        if (!CollectionUtils.isEmpty(fetchFloatSettingVOS)) {
            fetchFloatSettingVOS.forEach(item -> {
                QueryConfigInfo queryConfigInfo = item.getQueryConfigInfo();
                if (queryConfigInfo == null || CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
                    return;
                }
                queryConfigInfo.getZbMapping().forEach(zbMappingVO -> bdeDataLinkSet.add(zbMappingVO.getDataLinkId()));
            });
        }
        return bdeDataLinkSet;
    }

    public List<FormulaDefine> getEFDCFormulaInfo(JtableContext jtableContext, String dataLinkKey) {
        String formulaSchemeId = this.getFormulaSchemeId(jtableContext);
        if (StringUtils.isEmpty((String)formulaSchemeId)) {
            return super.getEFDCFormulaInfo(jtableContext, dataLinkKey);
        }
        DataLinkDefine dataLinkDefine = this.iRuntimeDataLinkService.queryDataLink(dataLinkKey);
        if (dataLinkDefine == null) {
            return super.getEFDCFormulaInfo(jtableContext, dataLinkKey);
        }
        String regionKey = dataLinkDefine.getRegionKey();
        DataRegionDefine dataRegionDefine = this.iRuntimeDataRegionService.queryDataRegion(regionKey);
        if (dataRegionDefine == null) {
            return super.getEFDCFormulaInfo(jtableContext, dataLinkKey);
        }
        if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind())) {
            return this.getFixedFormulaDefine(jtableContext, dataLinkKey, formulaSchemeId);
        }
        List<FormulaDefine> fixedFormulaDefineList = this.getFixedFormulaDefine(jtableContext, dataLinkKey, formulaSchemeId);
        return CollectionUtils.isEmpty(fixedFormulaDefineList) ? this.getFloatFormulaDefine(jtableContext, dataRegionDefine, dataLinkKey, formulaSchemeId) : fixedFormulaDefineList;
    }

    private List<FormulaDefine> getFixedFormulaDefine(JtableContext jtableContext, String dataLinkKey, String formulaSchemeId) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond(formulaSchemeId, jtableContext.getFormSchemeKey(), jtableContext.getFormKey(), null, dataLinkKey);
        List<FetchSettingEO> fetchSettingList = this.fetchSettingService.getFetchSettingListByCond(fetchSettingCond);
        if (CollectionUtils.isEmpty(fetchSettingList)) {
            return Collections.emptyList();
        }
        RunTimeFormulaDefineImpl runTimeFormulaDefine = this.convertBdeSettingToFormulaDefine(fetchSettingList.get(0));
        StringBuffer memo = new StringBuffer();
        StringBuffer description = new StringBuffer();
        FormulaExeParam formulaExeParam = null;
        block0: for (FetchSettingEO item : fetchSettingList) {
            String fixedSettingDataStr = item.getFixedSettingData();
            List<FixedAdaptSettingVO> adaptSettingVOS = this.fetchSettingService.convertFixedSettingDataStr(fixedSettingDataStr);
            for (FixedAdaptSettingVO adaptSetting : adaptSettingVOS) {
                if (StringUtils.isEmpty((String)adaptSetting.getAdaptFormula())) {
                    memo.append(adaptSetting.getMemo()).append("\n");
                    if (StringUtils.isEmpty((String)adaptSetting.getDescription())) continue;
                    description.append(adaptSetting.getDescription()).append("\n");
                    continue;
                }
                formulaExeParam = new FormulaExeParam();
                Map dimensionSet = jtableContext.getDimensionSet();
                String gcOrgType = FetchTaskUtil.getOrgTypeByTaskAndCtx(jtableContext.getTaskKey());
                String orgCode = ((DimensionValue)dimensionSet.get(AdaptContextEnum.MD_ORG.getKey())).getValue();
                String datatime = ((DimensionValue)dimensionSet.get(AdaptContextEnum.DATATIME.getKey())).getValue();
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)adaptSetting.getAdaptFormula(), (String)gcOrgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), orgCode);
                adaptContext.put(AdaptContextEnum.DATATIME.getKey(), datatime);
                adaptContext.put(gcOrgType, orgCode);
                adaptContext.put("formSchemeKey", jtableContext.getFormSchemeKey());
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                memo.append(this.removeAdaptMemo(adaptSetting));
                if (StringUtils.isEmpty((String)adaptSetting.getDescription())) continue block0;
                description.append(adaptSetting.getDescription()).append("\n");
                continue block0;
            }
        }
        runTimeFormulaDefine.setExpression(memo.toString());
        String descriptionStr = description.toString();
        if (!StringUtils.isNull((String)descriptionStr)) {
            runTimeFormulaDefine.setDescription(description.toString());
        }
        return Collections.singletonList(runTimeFormulaDefine);
    }

    private String removeAdaptMemo(FixedAdaptSettingVO adaptSetting) {
        if (Objects.isNull(adaptSetting)) {
            return "";
        }
        String adaptFormula = adaptSetting.getAdaptFormula();
        String logicFormula = adaptSetting.getLogicFormula();
        String memo = adaptSetting.getMemo();
        if (StringUtils.isEmpty((String)adaptFormula)) {
            return memo;
        }
        if (!StringUtils.isEmpty((String)logicFormula)) {
            try {
                return (String)JsonUtils.readValue((String)memo, String.class);
            }
            catch (Exception e) {
                log.error("\u9002\u5e94\u6761\u4ef6\u52a0\u903b\u8f91\u8868\u8fbe\u5f0f\u60ac\u6d6e\u4fe1\u606f\u89e3\u6790\u5931\u8d25\uff1a{}", (Object)memo);
                return memo;
            }
        }
        int startIndex = memo.indexOf("[") + 2;
        int endIndex = memo.lastIndexOf("]") - 1;
        String substring = memo.substring(startIndex, endIndex);
        String[] split = substring.split("\\)\",\"");
        StringBuilder newMemo = new StringBuilder();
        for (String s : split) {
            newMemo.append(s.replace("\\", "")).append(");");
        }
        newMemo.deleteCharAt(newMemo.length() - 1);
        newMemo.deleteCharAt(newMemo.length() - 2);
        return newMemo.toString();
    }

    private List<FormulaDefine> getFloatFormulaDefine(JtableContext jtableContext, DataRegionDefine dataRegionDefine, String dataLinkKey, String formulaSchemeId) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond(formulaSchemeId, jtableContext.getFormSchemeKey(), jtableContext.getFormKey(), dataRegionDefine.getKey(), null);
        FloatRegionConfigVO fetchFloatSetting = this.fetchFloatSettingService.getFetchFloatSetting(fetchSettingCond);
        Map<String, String> dataLinkIdQyertFieldMap = this.getDataLinkIdQyertFieldMap(fetchFloatSetting);
        RunTimeFormulaDefineImpl runTimeFormulaDefine = new RunTimeFormulaDefineImpl();
        runTimeFormulaDefine.setKey(dataLinkKey);
        runTimeFormulaDefine.setCode("");
        runTimeFormulaDefine.setFormKey(dataRegionDefine.getFormKey());
        runTimeFormulaDefine.setCheckType(0);
        runTimeFormulaDefine.setFormulaSchemeKey(formulaSchemeId);
        runTimeFormulaDefine.setOrder("1");
        runTimeFormulaDefine.setExpression(dataLinkIdQyertFieldMap.get(dataLinkKey));
        runTimeFormulaDefine.setDescription("");
        return Arrays.asList(runTimeFormulaDefine);
    }

    private Map<String, String> getDataLinkIdQyertFieldMap(FloatRegionConfigVO fetchFloatSetting) {
        QueryConfigInfo queryConfigInfo;
        if (null != fetchFloatSetting && null != fetchFloatSetting.getQueryConfigInfo() && null != (queryConfigInfo = fetchFloatSetting.getQueryConfigInfo()) && !CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return this.getFloatSettingDataLinkIdQueryFieldTitleMapping(queryConfigInfo);
        }
        return new HashMap<String, String>();
    }

    private Map<String, String> getFloatSettingDataLinkIdQueryFieldTitleMapping(QueryConfigInfo queryConfigInfo) {
        if (CollectionUtils.isEmpty((Collection)queryConfigInfo.getZbMapping())) {
            return new HashMap<String, String>();
        }
        Map<String, String> nameToTitleMap = queryConfigInfo.getQueryFields().stream().collect(Collectors.toMap(FloatQueryFieldVO::getName, FloatQueryFieldVO::getTitle, (o1, o2) -> o1));
        return queryConfigInfo.getZbMapping().stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, zbMappingVO -> {
            if ("=".equals(zbMappingVO.getQueryName())) {
                return "=";
            }
            String patternStr = "(?<=\\$\\{)[^}]*(?=})";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(zbMappingVO.getQueryName());
            if (matcher.find()) {
                String code = matcher.group(0);
                return StringUtils.isEmpty((String)((String)nameToTitleMap.get(code))) ? code : (String)nameToTitleMap.get(code);
            }
            return zbMappingVO.getQueryName();
        }, (last, next) -> next));
    }

    private RunTimeFormulaDefineImpl convertBdeSettingToFormulaDefine(FetchSettingEO item) {
        RunTimeFormulaDefineImpl runTimeFormulaDefine = new RunTimeFormulaDefineImpl();
        runTimeFormulaDefine.setKey(item.getId());
        runTimeFormulaDefine.setCode("");
        runTimeFormulaDefine.setFormKey(item.getFormId());
        runTimeFormulaDefine.setCheckType(0);
        runTimeFormulaDefine.setFormulaSchemeKey(item.getFetchSchemeId());
        runTimeFormulaDefine.setOrder("" + item.getSortOrder());
        runTimeFormulaDefine.setDescription("");
        return runTimeFormulaDefine;
    }

    private String getFormulaSchemeId(JtableContext jtableContext) {
        FormulaSchemeDefine soluctionByDimensions = super.getSoluctionByDimensions(jtableContext);
        if (soluctionByDimensions == null || StringUtils.isEmpty((String)soluctionByDimensions.getKey())) {
            return null;
        }
        FetchSchemeVO fetchScheme = this.fetchSchemeService.getFetchScheme(soluctionByDimensions.getKey());
        if (fetchScheme == null || StringUtils.isEmpty((String)fetchScheme.getId())) {
            return null;
        }
        return fetchScheme.getId();
    }

    public Set<String> getReadOnlyDataLinks(ReadOnlyContext readOnlyContext) {
        String value = this.iTaskOptionController.getValue(readOnlyContext.getTaskKey(), "EFDC_GET_VALUE_MODIFY_TASK");
        if ("1".equals(value)) {
            return new HashSet<String>();
        }
        String formSchemeKey = readOnlyContext.getFormSchemeKey();
        DimensionValueSet dimensionValueSet = readOnlyContext.getDimensionValueSet();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        HashMap dimMap = new HashMap();
        for (Map.Entry dimensionValueEntry : dimensionSet.entrySet()) {
            String dimValue = Objects.nonNull(dimensionValueEntry.getValue()) ? ((DimensionValue)dimensionValueEntry.getValue()).getValue() : "";
            dimMap.put(dimensionValueEntry.getKey(), dimValue);
        }
        String orgCode = (String)dimMap.get("MD_ORG");
        String datatime = (String)dimMap.get(DIM_MAP_DATATIME);
        String periodStr = PeriodUtil.toNrPeriod((String)datatime, (PeriodType)formSchemeDefine.getPeriodType());
        dimMap.put(DIM_MAP_DATATIME, periodStr);
        QueryObjectImpl queryObject = new QueryObjectImpl();
        queryObject.setTaskKey(formSchemeDefine.getTaskKey());
        queryObject.setFormSchemeKey(formSchemeKey);
        queryObject.setMainDim(orgCode);
        FormulaSchemeDefine formulaSchemeDefine = this.efdcConfigService.getSoluctionByDimensions(queryObject, dimMap, formSchemeDefine.getDw());
        if (Objects.isNull(formulaSchemeDefine)) {
            return super.getReadOnlyDataLinks(readOnlyContext);
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(readOnlyContext.getTaskKey());
        jtableContext.setFormSchemeKey(readOnlyContext.getFormSchemeKey());
        jtableContext.setFormKey(readOnlyContext.getFormKey());
        if (dimensionValueSet != null) {
            jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        }
        List<ExtractCellInfo> extractDataLinkList = this.getExtractDataLinkList(jtableContext, formulaSchemeDefine.getKey());
        return extractDataLinkList.stream().filter(ExtractCellInfo::isReadOnly).map(ExtractCellInfo::getLinkKey).collect(Collectors.toSet());
    }

    public boolean isAllOrgShare(ReadOnlyContext readOnlyContext) {
        return false;
    }
}

