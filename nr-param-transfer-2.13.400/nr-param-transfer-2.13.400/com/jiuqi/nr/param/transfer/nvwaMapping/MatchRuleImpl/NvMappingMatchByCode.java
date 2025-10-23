/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nr.mapping2.util.NvMappingMatchRule
 *  com.jiuqi.nr.mapping2.util.NvdataParamType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.param.transfer.nvwaMapping.MatchRuleImpl;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.mapping2.util.NvMappingMatchRule;
import com.jiuqi.nr.mapping2.util.NvdataParamType;
import com.jiuqi.nr.param.transfer.datascheme.DataSchemeModelTransfer;
import com.jiuqi.nr.param.transfer.datascheme.TransferSchemeDTO;
import com.jiuqi.nr.param.transfer.datascheme.TransferTableDTO;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskDTO;
import com.jiuqi.nr.param.transfer.nvwaMapping.NvdataMappingMatchContext;
import com.jiuqi.nr.param.transfer.nvwaMapping.NvdataMappingMatchRuleService;
import com.jiuqi.nr.param.transfer.nvwaMapping.NvwaMappingUtils;
import com.jiuqi.nr.param.transfer.period.PeriodModelTransfer;
import com.jiuqi.nr.param.transfer.period.PeriodRowDTO;
import com.jiuqi.nr.param.transfer.period.PeriodTransferDTO;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class NvMappingMatchByCode
implements NvdataMappingMatchRuleService {
    private static final Logger logger = LoggerFactory.getLogger(NvMappingMatchByCode.class);
    public static String INTER_TABLE_FORMULA_KEY = "0000";
    public static String INTER_TABLE_FORMULA_CODE = "FORMULA-MAPPING-BETWEEN";
    public static String BASE_DATA_DEFINE_TYPE = "BaseDataDefine";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FormulaMappingService formulaMappingService;
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    private PeriodMappingService periodMappingService;
    @Autowired
    IBaseDataMappingService baseDataMappingService;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    BaseDataDefineClient baseDataDefineClient;
    @Autowired
    BaseDataClient baseDataClient;

    @Override
    public NvMappingMatchRule getNvMappingMatchRule() {
        return NvMappingMatchRule.MATCH_BY_CODE;
    }

    @Override
    public void saveMappingMatchRule(Map<String, List<TransferResourceDataBean>> param, NvdataMappingMatchContext nvdataMappingMatchContext) throws Exception {
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(nvdataMappingMatchContext.getFormSchemeKey());
        Map<String, String> formCodeToKeyMap = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, a -> a.getKey(), (k1, k2) -> k1));
        Map<String, String> formKeyToCodeMap = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a.getFormCode(), (k1, k2) -> k1));
        HashMap<String, String> srcToDesFormKeyMap = new HashMap<String, String>();
        HashSet<String> srcExcludeFormKeySet = new HashSet<String>();
        Map<String, String> srcKeyMap = this.doCheckSchemeAndForm(param, srcToDesFormKeyMap, srcExcludeFormKeySet, formCodeToKeyMap, nvdataMappingMatchContext.getTaskTitle(), nvdataMappingMatchContext.getFormSchemeTitle());
        String srcFormSchemeSchemeKey = srcKeyMap.get("srcFormSchemeSchemeKey");
        String srcDataSchemeKey = srcKeyMap.get("srcDataSchemeKey");
        for (Map.Entry<String, List<TransferResourceDataBean>> entry : param.entrySet()) {
            String factoryId = entry.getKey();
            List<TransferResourceDataBean> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) continue;
            if (NvdataParamType.ORG_CATEGORY.getFactoryId().equals(factoryId)) {
                logger.info("nvdata\u4e2d\u4e0d\u5305\u542b\u7ec4\u7ec7\u673a\u6784\u6570\u636e\uff0c\u6682\u65f6\u65e0\u6cd5\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u6620\u5c04");
                continue;
            }
            if (NvdataParamType.BASE_DATA_DEFINE.getFactoryId().equals(factoryId)) {
                logger.info("\u5f00\u59cb\u751f\u6210\u57fa\u7840\u6570\u636e\u6620\u5c04");
                this.doBaseDataImport(nvdataMappingMatchContext, value);
                logger.info("\u57fa\u7840\u6570\u636e\u6620\u5c04\u751f\u6210\u5b8c\u6bd5");
                continue;
            }
            if (NvdataParamType.PERIOD.getFactoryId().equals(factoryId)) {
                logger.info("\u5f00\u59cb\u751f\u6210\u65f6\u671f\u6620\u5c04");
                this.doPeriodImport(nvdataMappingMatchContext, value);
                logger.info("\u65f6\u671f\u6620\u5c04\u751f\u6210\u5b8c\u6bd5");
                continue;
            }
            if (NvdataParamType.FORMULA_FORM.getFactoryId().equals(factoryId)) {
                logger.info("\u5f00\u59cb\u751f\u6210\u516c\u5f0f\u6620\u5c04");
                this.doFormulaMappingImport(nvdataMappingMatchContext, value, srcFormSchemeSchemeKey, srcToDesFormKeyMap, formKeyToCodeMap, srcExcludeFormKeySet);
                logger.info("\u516c\u5f0f\u6620\u5c04\u751f\u6210\u5b8c\u6bd5");
                continue;
            }
            if (!NvdataParamType.FORM_FIELD.getFactoryId().equals(factoryId)) continue;
            logger.info("\u5f00\u59cb\u751f\u6210\u62a5\u8868\u6307\u6807\u6620\u5c04");
            this.doFormZBImport(nvdataMappingMatchContext, value, formCodeToKeyMap, srcDataSchemeKey);
            logger.info("\u62a5\u8868\u6307\u6807\u6620\u5c04\u751f\u6210\u5b8c\u6bd5");
        }
    }

    private Map<String, String> doCheckSchemeAndForm(@NotNull Map<String, List<TransferResourceDataBean>> param, Map<String, String> srcToDesFormKeyMap, Set<String> srcExcludeFormKeySet, Map<String, String> formCodeToKeyMap, String taskTitle, String formSchemeSchemeTitle) throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        String srcFormSchemeSchemeKey = "";
        String srcTaskKey = "";
        String srcDataSchemeKey = "";
        if (!CollectionUtils.isEmpty((Collection)param.get(NvdataParamType.FORMULA_FORM.getFactoryId())) || !CollectionUtils.isEmpty((Collection)param.get(NvdataParamType.FORM_FIELD.getFactoryId()))) {
            Throwable throwable;
            InputStream businessModelStream;
            String[] split;
            ArrayList<TransferResourceDataBean> formParamTransferResourceDataBeans = new ArrayList<TransferResourceDataBean>();
            List<TransferResourceDataBean> thisFormSchemeResourceDataBeans = param.get(NvdataParamType.FORM.getFactoryId());
            for (TransferResourceDataBean transferResourceDataBean : thisFormSchemeResourceDataBeans) {
                if (!transferResourceDataBean.getBusinessNode().getType().equals(TransferNodeType.TASK.name()) || !transferResourceDataBean.getBusinessNode().getTitle().equals(taskTitle)) continue;
                split = transferResourceDataBean.getBusinessNode().getGuid().split("_");
                srcTaskKey = split[1];
                businessModelStream = transferResourceDataBean.getBusinessModelStream();
                throwable = null;
                try {
                    TaskDTO taskDTO = (TaskDTO)NvwaMappingUtils.inputStreamToData(businessModelStream, DefinitionModelTransfer.objectMapper, TaskDTO.class);
                    srcDataSchemeKey = taskDTO.getTaskInfo().getDataScheme();
                }
                catch (Throwable taskDTO) {
                    throwable = taskDTO;
                    throw taskDTO;
                }
                finally {
                    if (businessModelStream == null) continue;
                    if (throwable != null) {
                        try {
                            businessModelStream.close();
                        }
                        catch (Throwable taskDTO) {
                            throwable.addSuppressed(taskDTO);
                        }
                        continue;
                    }
                    businessModelStream.close();
                }
            }
            if (!StringUtils.hasLength(srcTaskKey)) {
                throw new Exception("\u5f53\u524d\u6620\u5c04\u65b9\u6848\u6240\u5c5e\u4efb\u52a1\u5728\u53c2\u6570\u5305\u4e2d\u4e0d\u5339\u914d\u4e0d\u5230\u540c\u540d\u7684\u4efb\u52a1\uff0c\u751f\u6210\u6620\u5c04\u65b9\u6848\u7ed3\u675f");
            }
            for (TransferResourceDataBean transferResourceDataBean : thisFormSchemeResourceDataBeans) {
                String type = transferResourceDataBean.getBusinessNode().getType();
                if (type.equals(TransferNodeType.FORM.name())) {
                    formParamTransferResourceDataBeans.add(transferResourceDataBean);
                    continue;
                }
                if (!type.equals(TransferNodeType.FORM_SCHEME.name()) || !transferResourceDataBean.getBusinessNode().getTitle().equals(formSchemeSchemeTitle)) continue;
                businessModelStream = transferResourceDataBean.getBusinessModelStream();
                throwable = null;
                try {
                    FormSchemeDTO formSchemeDTO = (FormSchemeDTO)NvwaMappingUtils.inputStreamToData(businessModelStream, DefinitionModelTransfer.objectMapper, FormSchemeDTO.class);
                    if (!srcTaskKey.equals(formSchemeDTO.getFormSchemeInfo().getTaskKey())) continue;
                    String[] split2 = transferResourceDataBean.getBusinessNode().getGuid().split("_");
                    srcFormSchemeSchemeKey = split2[1];
                }
                catch (Throwable formSchemeDTO) {
                    throwable = formSchemeDTO;
                    throw formSchemeDTO;
                }
                finally {
                    if (businessModelStream == null) continue;
                    if (throwable != null) {
                        try {
                            businessModelStream.close();
                        }
                        catch (Throwable formSchemeDTO) {
                            throwable.addSuppressed(formSchemeDTO);
                        }
                        continue;
                    }
                    businessModelStream.close();
                }
            }
            if (!StringUtils.hasLength(srcFormSchemeSchemeKey)) {
                throw new Exception("\u5f53\u524d\u6620\u5c04\u65b9\u6848\u6240\u5c5e\u62a5\u8868\u65b9\u6848\u5728\u53c2\u6570\u5305\u4e2d\u5339\u914d\u4e0d\u5230\u540c\u4efb\u52a1\u4e0b\u7684\u540c\u540d\u76ee\u6807\u65b9\u6848\uff0c\u751f\u6210\u6620\u5c04\u65b9\u6848\u7ed3\u675f");
            }
            for (TransferResourceDataBean formResourceDataBean : formParamTransferResourceDataBeans) {
                split = formResourceDataBean.getBusinessNode().getGuid().split("_");
                if (StringUtils.hasLength(srcToDesFormKeyMap.get(split[1]))) continue;
                BusinessNode businessNode = formResourceDataBean.getBusinessNode();
                String srcFormCode = businessNode.getName();
                String thenFormKey = formCodeToKeyMap.get(srcFormCode);
                if (StringUtils.hasLength(thenFormKey)) {
                    srcToDesFormKeyMap.put(split[1], thenFormKey);
                    continue;
                }
                srcExcludeFormKeySet.add(split[1]);
            }
        }
        logger.info("\u5339\u914d\u5230\u7684\u6765\u6e90\u4efb\u52a1key\u662f\uff1a{}", (Object)srcTaskKey);
        logger.info("\u5339\u914d\u5230\u7684\u6765\u6e90\u62a5\u8868\u65b9\u6848key\u662f\uff1a{}", (Object)srcFormSchemeSchemeKey);
        logger.info("\u5339\u914d\u5230\u7684\u6765\u6e90\u6570\u636e\u65b9\u6848key\u662f\uff1a{}", (Object)srcDataSchemeKey);
        result.put("srcTaskKey", srcTaskKey);
        result.put("srcFormSchemeSchemeKey", srcFormSchemeSchemeKey);
        result.put("srcDataSchemeKey", srcDataSchemeKey);
        return result;
    }

    private void doBaseDataImport(NvdataMappingMatchContext nvdataMappingMatchContext, List<TransferResourceDataBean> value) throws Exception {
        String mappingSchemeKey = nvdataMappingMatchContext.getMappingSchemeKey();
        List existBaseDataMapping = this.baseDataMappingService.getBaseDataMapping(mappingSchemeKey);
        Map<String, BaseDataMapping> existBaseDataMaps = existBaseDataMapping.stream().collect(Collectors.toMap(BaseDataMapping::getBaseDataCode, a -> a, (k1, k2) -> k1));
        for (TransferResourceDataBean transferResourceDataBean : value) {
            BaseDataMapping thisBaseDataMapping;
            if (transferResourceDataBean.getBusinessModelStream() == null || transferResourceDataBean.getBusinessDataStream() == null || !transferResourceDataBean.getBusinessNode().getType().equals(BASE_DATA_DEFINE_TYPE)) continue;
            BaseDataDefineDTO thisBaseDataDefine = new BaseDataDefineDTO();
            String baseDataDefineCode = transferResourceDataBean.getBusinessNode().getName();
            thisBaseDataDefine.setName(baseDataDefineCode);
            BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(thisBaseDataDefine);
            if (baseDataDefineDO == null || (thisBaseDataMapping = existBaseDataMaps.get(baseDataDefineCode)) != null && StringUtils.hasText(thisBaseDataMapping.getMappingCode()) && !thisBaseDataMapping.getBaseDataCode().equals(thisBaseDataMapping.getMappingCode())) continue;
            List srcBaseDataDOs = new ArrayList();
            try (InputStream businessDataStream = transferResourceDataBean.getBusinessDataStream();){
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = businessDataStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                srcBaseDataDOs = JSONUtil.parseArray((String)new String(bos.toByteArray(), StandardCharsets.UTF_8), BaseDataDO.class);
            }
            if (CollectionUtils.isEmpty(srcBaseDataDOs)) continue;
            ArrayList<BaseDataItemMapping> baseDataItemMappingList = new ArrayList<BaseDataItemMapping>();
            Map<String, BaseDataDO> srcBaseDataDOMap = srcBaseDataDOs.stream().collect(Collectors.toMap(BaseDataDO::getCode, a -> a, (k1, k2) -> k1));
            BaseDataDTO srcBaseDataDTO = new BaseDataDTO();
            srcBaseDataDTO.setTableName(baseDataDefineCode);
            PageVO baseDataPage = this.baseDataClient.list(srcBaseDataDTO);
            List rows = baseDataPage.getRows();
            for (BaseDataDO row : rows) {
                if (!srcBaseDataDOMap.containsKey(row.getCode())) continue;
                BaseDataItemMapping baseDataItemMapping = new BaseDataItemMapping();
                baseDataItemMapping.setBaseDataItemCode(row.getCode());
                BaseDataDO srcBaseDataDO = srcBaseDataDOMap.get(row.getCode());
                baseDataItemMapping.setMappingCode(srcBaseDataDO.getCode());
                baseDataItemMapping.setMappingTitle(srcBaseDataDO.getName());
                baseDataItemMappingList.add(baseDataItemMapping);
            }
            if (baseDataItemMappingList.size() <= 0) continue;
            if (thisBaseDataMapping == null) {
                BaseDataMapping baseDataMapping = new BaseDataMapping();
                baseDataMapping.setBaseDataCode(baseDataDefineCode);
                baseDataMapping.setMappingCode(baseDataDefineCode);
                baseDataMapping.setMappingTitle(baseDataDefineDO.getTitle());
                List<BaseDataMapping> insertList = Arrays.asList(baseDataMapping);
                this.baseDataMappingService.addBaseDataMapping(mappingSchemeKey, insertList);
            }
            List existBaseDataItem = this.baseDataMappingService.getBaseDataItem(mappingSchemeKey, baseDataDefineCode);
            Set existBaseDataItemCodeSets = existBaseDataItem.stream().map(BaseDataItemMapping::getBaseDataItemCode).collect(Collectors.toSet());
            List addBaseDataItemMappingList = baseDataItemMappingList.stream().filter(a -> !existBaseDataItemCodeSets.contains(a.getBaseDataCode())).collect(Collectors.toList());
            if (addBaseDataItemMappingList.size() <= 0) continue;
            existBaseDataItem.addAll(addBaseDataItemMappingList);
            this.baseDataMappingService.saveBaseDataItemMappings(mappingSchemeKey, baseDataDefineCode, existBaseDataItem);
        }
    }

    private void doPeriodImport(NvdataMappingMatchContext nvdataMappingMatchContext, List<TransferResourceDataBean> value) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(nvdataMappingMatchContext.getTaskKey());
        String dateTime = taskDefine.getDateTime();
        List collect = value.stream().filter(a -> dateTime.equals(a.getBusinessNode().getGuid())).collect(Collectors.toList());
        ArrayList<PeriodMapping> matchPeriodMappings = new ArrayList<PeriodMapping>();
        if (!CollectionUtils.isEmpty(collect)) {
            PeriodTransferDTO periodTransferDTO = null;
            try (InputStream businessModelStream2 = ((TransferResourceDataBean)collect.get(0)).getBusinessModelStream();){
                periodTransferDTO = (PeriodTransferDTO)NvwaMappingUtils.inputStreamToData(businessModelStream2, PeriodModelTransfer.MAPPER, PeriodTransferDTO.class);
            }
            catch (Exception businessModelStream2) {
                // empty catch block
            }
            Set srcPeriodCodes = periodTransferDTO.getPeriodRows().stream().map(PeriodRowDTO::getCode).collect(Collectors.toSet());
            List periodKeys = this.runTimeViewController.querySchemePeriodLinkByScheme(nvdataMappingMatchContext.getFormSchemeKey()).stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toList());
            for (String periodKey : periodKeys) {
                if (!srcPeriodCodes.contains(periodKey)) continue;
                PeriodMapping periodMapping = new PeriodMapping(UUID.randomUUID().toString(), nvdataMappingMatchContext.getMappingSchemeKey(), periodKey, periodKey);
                matchPeriodMappings.add(periodMapping);
            }
        }
        if (matchPeriodMappings.size() > 0) {
            List existPeriodMapping = this.periodMappingService.findByMS(nvdataMappingMatchContext.getMappingSchemeKey());
            Set existPeriodSets = existPeriodMapping.stream().map(PeriodMapping::getPeriod).collect(Collectors.toSet());
            List addPeriodMappings = matchPeriodMappings.stream().filter(a -> !existPeriodSets.contains(a.getPeriod())).collect(Collectors.toList());
            if (addPeriodMappings.size() > 0) {
                this.periodMappingService.batchAdd(addPeriodMappings);
            }
        }
    }

    private void doFormZBImport(NvdataMappingMatchContext nvdataMappingMatchContext, List<TransferResourceDataBean> value, Map<String, String> formCodeToKeyMap, String srcDataSchemeKey) throws Exception {
        String thisDataSchemeKey = nvdataMappingMatchContext.getDataSchemeKey();
        ArrayList<TransferResourceDataBean> dataTableParam = new ArrayList<TransferResourceDataBean>();
        ArrayList<TransferResourceDataBean> dataSchemeParam = new ArrayList<TransferResourceDataBean>();
        for (TransferResourceDataBean transferResourceDataBean : value) {
            String type = transferResourceDataBean.getBusinessNode().getType();
            if (type.equals(NodeType.SCHEME.name())) {
                dataSchemeParam.add(transferResourceDataBean);
                continue;
            }
            if (type.equals(NodeType.TABLE.name())) {
                dataTableParam.add(transferResourceDataBean);
                continue;
            }
            if (type.equals(NodeType.MD_INFO.name())) {
                dataTableParam.add(transferResourceDataBean);
                continue;
            }
            if (type.equals(NodeType.ACCOUNT_TABLE.name())) {
                dataTableParam.add(transferResourceDataBean);
                continue;
            }
            if (type.equals(NodeType.DETAIL_TABLE.name())) {
                dataTableParam.add(transferResourceDataBean);
                continue;
            }
            if (!type.equals(NodeType.MUL_DIM_TABLE.name())) continue;
            dataTableParam.add(transferResourceDataBean);
        }
        String srcDataSchemePrefix = "";
        String desDataSchemePrefix = "";
        for (TransferResourceDataBean transferResourceDataBean : dataSchemeParam) {
            String[] split = transferResourceDataBean.getBusinessNode().getGuid().split("_");
            String thisSrcDataSchemeKey = split[1];
            if (!thisSrcDataSchemeKey.equals(srcDataSchemeKey)) continue;
            InputStream businessModelStream = transferResourceDataBean.getBusinessModelStream();
            Throwable throwable = null;
            try {
                TransferSchemeDTO dataSchemeDTO = (TransferSchemeDTO)NvwaMappingUtils.inputStreamToData(businessModelStream, DataSchemeModelTransfer.objectMapper, TransferSchemeDTO.class);
                srcDataSchemePrefix = dataSchemeDTO.getDataScheme().getPrefix();
            }
            catch (Throwable dataSchemeDTO) {
                throwable = dataSchemeDTO;
                throw dataSchemeDTO;
            }
            finally {
                if (businessModelStream == null) continue;
                if (throwable != null) {
                    try {
                        businessModelStream.close();
                    }
                    catch (Throwable dataSchemeDTO) {
                        throwable.addSuppressed(dataSchemeDTO);
                    }
                    continue;
                }
                businessModelStream.close();
            }
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(thisDataSchemeKey);
        desDataSchemePrefix = dataScheme.getPrefix();
        boolean subDesDataSchemePrefix = !srcDataSchemePrefix.equals(desDataSchemePrefix) && StringUtils.hasLength(desDataSchemePrefix);
        int desDataSchemePrefixLength = desDataSchemePrefix.length();
        boolean subSrcDataSchemePrefix = !srcDataSchemePrefix.equals(desDataSchemePrefix) && StringUtils.hasLength(srcDataSchemePrefix);
        int srcDataSchemePrefixLength = srcDataSchemePrefix.length();
        HashMap<String, String> fieldCodeToTableCodeMap = new HashMap<String, String>();
        HashMap<String, Set> srcTableKeyToFieldCodeMap = new HashMap<String, Set>();
        HashMap<Object, String> desToSrcDataTableKey = new HashMap<Object, String>();
        List allDataTable = this.runtimeDataSchemeService.getAllDataTable(thisDataSchemeKey);
        Map<String, String> dataTableCodeToKey = allDataTable.stream().collect(Collectors.toMap(a -> subDesDataSchemePrefix ? a.getCode().substring(desDataSchemePrefixLength) : a.getCode(), a -> a.getKey(), (k1, k2) -> k1));
        Map<String, String> dataTableKeyToCode = allDataTable.stream().collect(Collectors.toMap(Basic::getKey, a -> a.getCode(), (k1, k2) -> k1));
        for (TransferResourceDataBean transferResourceDataBean : dataTableParam) {
            TransferTableDTO transferTableDTO = null;
            try (InputStream businessModelStream = transferResourceDataBean.getBusinessModelStream();){
                transferTableDTO = (TransferTableDTO)NvwaMappingUtils.inputStreamToData(businessModelStream, DataSchemeModelTransfer.objectMapper, TransferTableDTO.class);
            }
            DataTable srcDataTable = transferTableDTO.getDataTable();
            if (!srcDataSchemeKey.equals(srcDataTable.getDataSchemeKey())) continue;
            List<DesignDataField> fields = transferTableDTO.getFields();
            for (DesignDataField field : fields) {
                if (field.getDataFieldKind() != DataFieldKind.FIELD_ZB) continue;
                fieldCodeToTableCodeMap.put(field.getCode(), srcDataTable.getCode());
            }
            String srcDataTableCode = subSrcDataSchemePrefix ? srcDataTable.getCode().substring(srcDataSchemePrefixLength) : srcDataTable.getCode();
            String desTableKey = dataTableCodeToKey.get(srcDataTableCode);
            if (!StringUtils.hasLength(desTableKey)) continue;
            desToSrcDataTableKey.put(desTableKey, srcDataTable.getKey());
            Set srcDataFieldCodeForThisTable = fields.stream().map(Basic::getCode).collect(Collectors.toSet());
            srcTableKeyToFieldCodeMap.computeIfAbsent(srcDataTable.getKey(), key -> new HashSet()).addAll(srcDataFieldCodeForThisTable);
        }
        for (Map.Entry entry : formCodeToKeyMap.entrySet()) {
            String formCode = (String)entry.getKey();
            String formKey = (String)entry.getValue();
            List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formKey);
            ArrayList<ZBMapping> formZBMapping = new ArrayList<ZBMapping>();
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                List fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                List dataFields = this.runtimeDataSchemeService.getDataFields(fieldKeysInRegion);
                for (DataField dataField : dataFields) {
                    Set fieldCodes;
                    if (dataField == null) continue;
                    String dataTableKey = dataField.getDataTableKey();
                    if (dataField.getDataFieldKind() == DataFieldKind.FIELD_ZB) {
                        String srcDataTableCodeForField = (String)fieldCodeToTableCodeMap.get(dataField.getCode());
                        if (!StringUtils.hasLength(srcDataTableCodeForField)) continue;
                        String desDataTableCodeForField = dataTableKeyToCode.get(dataTableKey);
                        String mappingCode = dataField.getCode();
                        if (!desDataTableCodeForField.equals(srcDataTableCodeForField)) {
                            mappingCode = srcDataTableCodeForField + "[" + mappingCode + "]";
                        }
                        ZBMapping zBMapping = new ZBMapping("", "", formCode, desDataTableCodeForField, dataRegionDefine.getCode(), dataField.getCode(), mappingCode);
                        formZBMapping.add(zBMapping);
                        continue;
                    }
                    if (dataField.getDataFieldKind() != DataFieldKind.FIELD || !StringUtils.hasLength((String)desToSrcDataTableKey.get(dataTableKey)) || !(fieldCodes = (Set)srcTableKeyToFieldCodeMap.get(desToSrcDataTableKey.get(dataTableKey))).contains(dataField.getCode())) continue;
                    ZBMapping zBMapping = new ZBMapping("", "", formCode, dataTableKeyToCode.get(dataTableKey), dataRegionDefine.getCode(), dataField.getCode(), dataField.getCode());
                    formZBMapping.add(zBMapping);
                }
            }
            if (formZBMapping.size() <= 0) continue;
            List existZBMapping = this.zbMappingService.findByMSAndForm(nvdataMappingMatchContext.getMappingSchemeKey(), formCode);
            Set existFormulaCodeSet = existZBMapping.stream().map(a -> a.getTable() + a.getZbCode() + a.getRegionCode()).collect(Collectors.toSet());
            List adFormZBMapping = formZBMapping.stream().filter(a -> !existFormulaCodeSet.contains(a.getTable() + a.getZbCode() + a.getRegionCode())).collect(Collectors.toList());
            if (adFormZBMapping.size() <= 0) continue;
            existZBMapping.addAll(adFormZBMapping);
            this.zbMappingService.save(nvdataMappingMatchContext.getMappingSchemeKey(), formCode, existZBMapping);
        }
    }

    private void doFormulaMappingImport(NvdataMappingMatchContext nvdataMappingMatchContext, List<TransferResourceDataBean> value, String srcFormSchemeSchemeKey, Map<String, String> srcToDesFormKeyMap, Map<String, String> formKeyToCodeMap, Set<String> srcExcludeFormKeySet) throws Exception {
        ArrayList<TransferResourceDataBean> formulaSchemeParam = new ArrayList<TransferResourceDataBean>();
        ArrayList<TransferResourceDataBean> formulaFormParam = new ArrayList<TransferResourceDataBean>();
        for (TransferResourceDataBean transferResourceDataBean : value) {
            String type = transferResourceDataBean.getBusinessNode().getType();
            if (type.equals(TransferNodeType.FORMULA_SCHEME.name())) {
                formulaSchemeParam.add(transferResourceDataBean);
                continue;
            }
            if (!type.equals(TransferNodeType.FORMULA_FORM.name())) continue;
            formulaFormParam.add(transferResourceDataBean);
        }
        HashMap<String, String> srcToDesFormulaSchemeMap = new HashMap<String, String>();
        ArrayList<String> srcFormulaSchemeKeys = new ArrayList<String>();
        List formulaSchemeDefines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(nvdataMappingMatchContext.getFormSchemeKey());
        Map<String, String> desFormulaSchemeTitleToKey = formulaSchemeDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, a -> a.getKey(), (k1, k2) -> k1));
        Map<String, String> desFormulaSchemeKeyToTitle = formulaSchemeDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a.getTitle(), (k1, k2) -> k1));
        for (TransferResourceDataBean transferResourceDataBean : formulaSchemeParam) {
            InputStream businessModelStream = transferResourceDataBean.getBusinessModelStream();
            Throwable throwable = null;
            try {
                FormulaSchemeDTO formulaSchemeDTO = (FormulaSchemeDTO)NvwaMappingUtils.inputStreamToData(businessModelStream, DefinitionModelTransfer.objectMapper, FormulaSchemeDTO.class);
                String formSchemeKey = formulaSchemeDTO.getFormulaSchemeInfo().getFormSchemeKey();
                if (!formSchemeKey.equals(srcFormSchemeSchemeKey)) {
                    continue;
                }
            }
            catch (Throwable formulaSchemeDTO) {
                throwable = formulaSchemeDTO;
                throw formulaSchemeDTO;
            }
            finally {
                if (businessModelStream == null) continue;
                if (throwable != null) {
                    try {
                        businessModelStream.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    continue;
                }
                businessModelStream.close();
                continue;
            }
            String formulaSchemeKey = desFormulaSchemeTitleToKey.get(transferResourceDataBean.getBusinessNode().getTitle());
            if (!StringUtils.hasLength(formulaSchemeKey)) continue;
            String[] split = transferResourceDataBean.getBusinessNode().getGuid().split("_");
            srcToDesFormulaSchemeMap.put(split[1], formulaSchemeKey);
            srcFormulaSchemeKeys.add(split[1]);
        }
        HashMap formulaSchemeToFormulaMapping = new HashMap();
        HashMap<String, Map<String, List<FormulaDefine>>> formulaSchemeToFormulaMap = new HashMap<String, Map<String, List<FormulaDefine>>>();
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            List allFormulasInScheme = this.formulaRunTimeController.getAllFormulasInScheme(formulaSchemeDefine.getKey());
            Map<String, List<FormulaDefine>> formToFormulaForFormulaScheme = null;
            if (!CollectionUtils.isEmpty(allFormulasInScheme)) {
                ArrayList noFormFormula = new ArrayList();
                formToFormulaForFormulaScheme = allFormulasInScheme.stream().filter(a -> {
                    if (!StringUtils.hasLength(a.getFormKey())) {
                        noFormFormula.add(a);
                        return false;
                    }
                    return true;
                }).collect(Collectors.groupingBy(FormulaDefine::getFormKey));
                if (noFormFormula.size() > 0) {
                    formToFormulaForFormulaScheme.put(INTER_TABLE_FORMULA_KEY, noFormFormula);
                }
            }
            formulaSchemeToFormulaMap.put(formulaSchemeDefine.getKey(), formToFormulaForFormulaScheme);
            HashMap thisFormulaSchemeFormulaMapping = new HashMap();
            formulaSchemeToFormulaMapping.put(formulaSchemeDefine.getKey(), thisFormulaSchemeFormulaMapping);
        }
        for (TransferResourceDataBean transferResourceDataBean : formulaFormParam) {
            List desFormulaDefines;
            Map formulaSchemeFormulaDefines;
            String srcFormulaSchemeKey = "";
            String srcFormKey = "";
            try {
                String[] args = FormulaGuidParse.parseKey(transferResourceDataBean.getBusinessNode().getGuid().split("_")[1]);
                srcFormulaSchemeKey = args[0];
                srcFormKey = args[1];
            }
            catch (Exception e) {
                throw new RuntimeException("nvdata\u751f\u6210\u516c\u5f0f\u6620\u5c04\u65f6\u51fa\u9519\uff1a" + e.getMessage());
            }
            if (!StringUtils.hasLength((String)srcToDesFormulaSchemeMap.get(srcFormulaSchemeKey)) || srcExcludeFormKeySet.contains(srcFormKey)) continue;
            String desFormulaSchemeKey = (String)srcToDesFormulaSchemeMap.get(srcFormulaSchemeKey);
            String desFormulaSchemeTitle = desFormulaSchemeKeyToTitle.get(desFormulaSchemeKey);
            String desFormKey = INTER_TABLE_FORMULA_KEY;
            if (!INTER_TABLE_FORMULA_KEY.equals(srcFormKey)) {
                desFormKey = srcToDesFormKeyMap.get(srcFormKey);
            }
            if (CollectionUtils.isEmpty(formulaSchemeFormulaDefines = (Map)formulaSchemeToFormulaMap.get(desFormulaSchemeKey)) || CollectionUtils.isEmpty(desFormulaDefines = (List)formulaSchemeFormulaDefines.get(desFormKey))) continue;
            Set formulaCodeSet = desFormulaDefines.stream().map(FormulaDefine::getCode).collect(Collectors.toSet());
            ArrayList<FormulaMapping> formFormulaMappings = new ArrayList<FormulaMapping>();
            try (InputStream businessModelStream = transferResourceDataBean.getBusinessModelStream();){
                FormulaSchemeDTO formulaSchemeDTO = (FormulaSchemeDTO)NvwaMappingUtils.inputStreamToData(businessModelStream, DefinitionModelTransfer.objectMapper, FormulaSchemeDTO.class);
                List<FormulaDTO> srcFormulas = formulaSchemeDTO.getFormulas();
                for (FormulaDTO srcFormula : srcFormulas) {
                    if (!formulaCodeSet.contains(srcFormula.getCode())) continue;
                    FormulaMapping formulaMapping = new FormulaMapping(null, null, null, null, srcFormula.getCode(), desFormulaSchemeTitle, srcFormula.getCode());
                    formFormulaMappings.add(formulaMapping);
                }
            }
            catch (Exception e) {
                throw new RuntimeException("nvdata\u751f\u6210\u516c\u5f0f\u6620\u5c04\u65f6\u51fa\u9519\uff1a" + e.getMessage());
            }
            Map formulaMappingForFormScheme = (Map)formulaSchemeToFormulaMapping.get(desFormulaSchemeKey);
            formulaMappingForFormScheme.computeIfAbsent(desFormKey, key -> new ArrayList()).addAll(formFormulaMappings);
            String formCode = INTER_TABLE_FORMULA_CODE;
            if (!desFormKey.equals(INTER_TABLE_FORMULA_KEY)) {
                formCode = formKeyToCodeMap.get(desFormKey);
            }
            List existFormulaForm = this.formulaMappingService.findByMSFormulaForm(nvdataMappingMatchContext.getMappingSchemeKey(), desFormulaSchemeKey, formCode);
            Set existFormulaCodeSet = existFormulaForm.stream().map(FormulaMapping::getFormulaCode).collect(Collectors.toSet());
            List addFormFormulaMappings = formFormulaMappings.stream().filter(a -> !existFormulaCodeSet.contains(a.getFormulaCode())).collect(Collectors.toList());
            if (addFormFormulaMappings.size() <= 0) continue;
            existFormulaForm.addAll(addFormFormulaMappings);
            this.formulaMappingService.save(nvdataMappingMatchContext.getMappingSchemeKey(), desFormulaSchemeKey, formCode, existFormulaForm);
        }
    }
}

