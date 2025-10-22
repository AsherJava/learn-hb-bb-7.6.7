/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariableDefine
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionContext
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.single.core.para.FormulaInfo
 *  com.jiuqi.nr.single.core.para.FormulaVariableInfo
 *  com.jiuqi.nr.single.core.para.consts.ZBDataType
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileFormulaInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.internal.SingleFileFormulaItemImpl
 *  nr.single.map.data.internal.SingleFileTableFormulaInfoImpl
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.FormulaVariableDefine;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionContext;
import com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.single.core.para.FormulaInfo;
import com.jiuqi.nr.single.core.para.FormulaVariableInfo;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import nr.single.map.data.internal.SingleFileTableFormulaInfoImpl;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaSchemeDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormulaFormService;
import nr.single.para.compare.definition.ISingleCompareDataFormulaScemeService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.internal.service3.function.SingleGetmeaning;
import nr.single.para.parain.internal.service3.function.SingleInlist;
import nr.single.para.parain.service.IFormulaDefineImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaDefineImportServiceImpl
implements IFormulaDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(FormulaDefineImportServiceImpl.class);
    @Autowired
    private com.jiuqi.nr.definition.controller.IDesignTimeViewController viewController;
    @Autowired
    private IFormulaDesignTimeController formulaController;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController2;
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IBaseDataMappingService baseDataMappingService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ISingleCompareDataFormulaScemeService formulaSchemeCompareService;
    @Autowired
    private ISingleCompareDataFormulaFormService formulaFormService;

    @Override
    public void importFormulaDefines(TaskImportContext importContext, String formSchemeKey) throws JQException {
        List formulaSchemes = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        HashMap<String, DesignFormulaSchemeDefine> formulaSchemeDic = new HashMap<String, DesignFormulaSchemeDefine>();
        for (DesignFormulaSchemeDefine scheme : formulaSchemes) {
            formulaSchemeDic.put(scheme.getTitle(), scheme);
        }
        boolean isAnalTask = importContext.getIsAnalTask();
        Map<String, ConversionFormInfo> conversionFormInfoMap = this.getConversionFormInfoMap(importContext);
        Map<String, String> enumMap = this.getEnumMap(importContext);
        ExecutorContext transContext = null;
        try {
            transContext = this.initContext(conversionFormInfoMap, formSchemeKey, enumMap);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        ParaImportInfoResult formulaSchemesLog = null;
        if (importContext.getImportResult() != null) {
            formulaSchemesLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_FORMULA, "formulaSchemes", "\u516c\u5f0f\u65b9\u6848");
        }
        HashMap<String, CompareDataFormulaSchemeDTO> oldFormulaSchemeCompareDic = new HashMap<String, CompareDataFormulaSchemeDTO>();
        if (importContext.getCompareInfo() != null) {
            CompareDataFormulaSchemeDTO formulaQueryParam = new CompareDataFormulaSchemeDTO();
            formulaQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            formulaQueryParam.setDataType(CompareDataType.DATA_FORMULA_SCHEME);
            List<CompareDataFormulaSchemeDTO> oldSchemeCompareList = this.formulaSchemeCompareService.list(formulaQueryParam);
            for (CompareDataFormulaSchemeDTO oldData : oldSchemeCompareList) {
                oldFormulaSchemeCompareDic.put(oldData.getSingleTitle(), oldData);
            }
        }
        LinkedHashMap<String, DesignFormDefine> formCache = new LinkedHashMap<String, DesignFormDefine>();
        List rootGroups = this.viewController.queryRootGroupsByFormScheme(formSchemeKey);
        for (Object formGroup : rootGroups) {
            List groupForms = this.viewController.queryAllSoftFormDefinesInGroup(formGroup.getKey());
            for (DesignFormDefine form : groupForms) {
                formCache.put(form.getFormCode(), form);
            }
        }
        if (formCache.isEmpty()) {
            List oldFormList = this.viewController.queryAllSoftFormDefinesByFormScheme(formSchemeKey);
            for (DesignFormDefine form : oldFormList) {
                formCache.put(form.getFormCode(), form);
            }
        }
        ArrayList<DesignFormulaSchemeDefine> formulaSchemeList = new ArrayList<DesignFormulaSchemeDefine>();
        Map fmlMgr = importContext.getParaInfo().getFmlMgr();
        HashSet<String> singleFormulaSchemes = new HashSet<String>();
        double progressLen = 0.1 / (double)fmlMgr.size();
        for (Map.Entry entry : fmlMgr.entrySet()) {
            String fmlGroupName = (String)entry.getKey();
            int id1 = fmlGroupName.indexOf("\uff3b");
            int id2 = fmlGroupName.indexOf("\uff3d");
            singleFormulaSchemes.add(fmlGroupName);
            boolean isNeedUpdateScheme = true;
            CompareDataFormulaSchemeDTO oldSchemeCompare = null;
            if (oldFormulaSchemeCompareDic.containsKey(fmlGroupName) && (oldSchemeCompare = (CompareDataFormulaSchemeDTO)oldFormulaSchemeCompareDic.get(fmlGroupName)) != null && (oldSchemeCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE || oldSchemeCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) {
                isNeedUpdateScheme = false;
            }
            DesignFormulaSchemeDefine scheme = null;
            boolean isNewScheme = true;
            if (formulaSchemeDic.containsKey(fmlGroupName)) {
                scheme = (DesignFormulaSchemeDefine)formulaSchemeDic.get(fmlGroupName);
                if (isAnalTask && "\u5206\u6790\u53d6\u6570\u516c\u5f0f".equalsIgnoreCase(fmlGroupName)) {
                    scheme.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM);
                    scheme.setDefault(true);
                } else {
                    scheme.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
                }
                scheme.setDisplayMode(id1 >= 0 && id2 > 0 ? FormulaSchemeDisplayMode.FORMULA_SCHEME_DISPLAYMODE_ALONEBUTTON : FormulaSchemeDisplayMode.FORMULA_SCHEME_DISPLAYMODE_NOBUTTON);
                scheme.setOrder(OrderGenerator.newOrder());
                if (isNeedUpdateScheme) {
                    this.formulaController.updateFormulaSchemeDefine(scheme);
                }
                isNewScheme = false;
            } else {
                if (!isNeedUpdateScheme) continue;
                scheme = this.formulaController.createFormulaSchemeDefine();
                scheme.setTitle(fmlGroupName);
                scheme.setFormSchemeKey(formSchemeKey);
                if (isAnalTask && "\u5206\u6790\u53d6\u6570\u516c\u5f0f".equalsIgnoreCase(fmlGroupName)) {
                    scheme.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM);
                    scheme.setDefault(true);
                } else {
                    scheme.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
                }
                scheme.setDisplayMode(id1 >= 0 && id2 > 0 ? FormulaSchemeDisplayMode.FORMULA_SCHEME_DISPLAYMODE_ALONEBUTTON : FormulaSchemeDisplayMode.FORMULA_SCHEME_DISPLAYMODE_NOBUTTON);
                scheme.setOrder(OrderGenerator.newOrder());
                if (fmlGroupName.equals("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848")) {
                    scheme.setDefault(true);
                }
                scheme.setOwnerLevelAndId(importContext.getCurServerCode());
                this.formulaController.insertFormulaSchemeDefine(scheme);
            }
            ParaImportInfoResult schemeLog = null;
            if (oldSchemeCompare != null) {
                schemeLog = new ParaImportInfoResult();
                schemeLog.copyForm(oldSchemeCompare);
                formulaSchemesLog.addItem(schemeLog);
            } else {
                schemeLog = this.setFormulaSchemeLog(formulaSchemesLog, fmlGroupName, scheme);
            }
            this.uploadFormulaScheme(importContext, formSchemeKey, fmlGroupName, isNewScheme, scheme, progressLen, (Map)entry.getValue(), formCache, transContext, schemeLog, oldSchemeCompare);
            formulaSchemeList.add(scheme);
        }
        this.updateFormulaVars(importContext, formSchemeKey);
        if (importContext.getImportOption().isOverWriteAll()) {
            for (DesignFormulaSchemeDefine scheme : formulaSchemes) {
                if (singleFormulaSchemes.contains(scheme.getTitle())) continue;
                this.formulaController.deleteFormulaSchemeDefine(scheme.getKey());
            }
        }
    }

    private void updateFormulaVars(TaskImportContext importContext, String formSchemeKey) {
        String newField;
        List zmdFields;
        String defaultValue;
        FMRepInfo fmInfo;
        DesignFormulaVariableDefineImpl netVar;
        List netVarList = this.formulaController.queryAllFormulaVariable(formSchemeKey);
        Map<String, FormulaVariDefine> netVarMap = netVarList.stream().collect(Collectors.toMap(FormulaVariableDefine::getCode, e -> e));
        for (FormulaVariableInfo varinfo : importContext.getParaInfo().getFormulaVariables()) {
            if (netVarMap.containsKey(varinfo.getVarCode())) continue;
            DesignFormulaVariableDefineImpl netVar2 = new DesignFormulaVariableDefineImpl();
            try {
                netVar2.setKey(UUID.randomUUID().toString());
                netVar2.setFormSchemeKey(formSchemeKey);
                this.setFromulaVarItem(importContext, varinfo, (FormulaVariDefine)netVar2);
                this.formulaController.addFormulaVariable((FormulaVariDefine)netVar2);
            }
            catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
        }
        if (!netVarMap.containsKey("SYS_ZDM")) {
            netVar = new DesignFormulaVariableDefineImpl();
            try {
                netVar.setKey(UUID.randomUUID().toString());
                netVar.setFormSchemeKey(formSchemeKey);
                netVar.setCode("SYS_ZDM");
                netVar.setTitle("SYS_ZDM");
                netVar.setType(1);
                netVar.setInitType(0);
                fmInfo = importContext.getParaInfo().getFmRepInfo();
                defaultValue = "";
                if (fmInfo != null) {
                    zmdFields = fmInfo.getZdmFields();
                    for (String field : zmdFields) {
                        if (!StringUtils.isNotEmpty((String)field)) continue;
                        newField = field;
                        if (field.equalsIgnoreCase(fmInfo.getPeriodField())) {
                            newField = "SQ";
                        }
                        if (StringUtils.isEmpty((String)defaultValue)) {
                            defaultValue = newField;
                            continue;
                        }
                        defaultValue = defaultValue + "+" + newField;
                    }
                    netVar.setLength(fmInfo.getZDMLength());
                } else {
                    netVar.setLength(19);
                }
                netVar.setValueType(1);
                if (StringUtils.isNotEmpty((String)defaultValue)) {
                    netVar.setInitValue(defaultValue);
                }
                netVar.setOrder(OrderGenerator.newOrder());
                netVar.setOwnerLevelAndId(importContext.getCurServerCode());
                this.formulaController.addFormulaVariable((FormulaVariDefine)netVar);
            }
            catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
        }
        if (!netVarMap.containsKey("SYS_FJD")) {
            netVar = new DesignFormulaVariableDefineImpl();
            try {
                netVar.setKey(UUID.randomUUID().toString());
                netVar.setFormSchemeKey(formSchemeKey);
                netVar.setCode("SYS_FJD");
                netVar.setTitle("SYS_FJD");
                netVar.setType(1);
                netVar.setInitType(0);
                fmInfo = importContext.getParaInfo().getFmRepInfo();
                defaultValue = "";
                if (fmInfo != null) {
                    zmdFields = fmInfo.getZdmFields();
                    Iterator iterator = zmdFields.iterator();
                    while (iterator.hasNext()) {
                        String field;
                        newField = field = (String)iterator.next();
                        newField = field.equalsIgnoreCase(fmInfo.getPeriodField()) ? "SQ" : "PARENT_ATTR_" + field;
                        if (!StringUtils.isNotEmpty((String)newField)) continue;
                        if (StringUtils.isEmpty((String)defaultValue)) {
                            defaultValue = newField;
                            continue;
                        }
                        defaultValue = defaultValue + "+" + newField;
                    }
                    netVar.setLength(fmInfo.getZDMLength());
                } else {
                    netVar.setLength(19);
                }
                netVar.setValueType(1);
                if (StringUtils.isNotEmpty((String)defaultValue)) {
                    netVar.setInitValue(defaultValue);
                }
                netVar.setOrder(OrderGenerator.newOrder());
                netVar.setOwnerLevelAndId(importContext.getCurServerCode());
                this.formulaController.addFormulaVariable((FormulaVariDefine)netVar);
            }
            catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
        }
    }

    private void setFromulaVarItem(TaskImportContext importContext, FormulaVariableInfo varinfo, FormulaVariDefine netVar) {
        EnumsItemModel enumModel;
        netVar.setCode(varinfo.getVarCode());
        netVar.setTitle(varinfo.getVarTitle());
        netVar.setType(0);
        if (varinfo.getDftType() == 2) {
            netVar.setInitType(1);
        } else {
            netVar.setInitType(0);
        }
        String defaultValue = varinfo.getDefaultValue();
        if (varinfo.getDataType() == ZBDataType.INTEGER || varinfo.getDataType() == ZBDataType.NUMERIC) {
            netVar.setValueType(0);
            netVar.setLength(varinfo.getDecimal());
        } else if (varinfo.getDataType() == ZBDataType.STRING) {
            netVar.setValueType(1);
            netVar.setLength(varinfo.getDataSize());
        } else if (varinfo.getDataType() == ZBDataType.DATE) {
            netVar.setValueType(2);
            if (StringUtils.isNotEmpty((String)defaultValue)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = formatter.parse(defaultValue);
                    defaultValue = PeriodUtils.getPeriodFromDate((int)6, (Date)date);
                }
                catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        if (StringUtils.isNotEmpty((String)varinfo.getEnumDict()) && (enumModel = (EnumsItemModel)importContext.getParaInfo().getEnunList().get(varinfo.getEnumDict())) != null) {
            String code = "";
            for (DataInfo enumItem : enumModel.getItemDataList()) {
                if (StringUtils.isEmpty((String)code)) {
                    code = enumItem.getCode();
                    continue;
                }
                code = ',' + enumItem.getCode();
            }
            defaultValue = code;
            netVar.setType(2);
        }
        if (StringUtils.isNotEmpty((String)defaultValue)) {
            netVar.setInitValue(defaultValue);
        }
        netVar.setOrder(OrderGenerator.newOrder());
        netVar.setOwnerLevelAndId(importContext.getCurServerCode());
    }

    private void uploadFormulaScheme(TaskImportContext importContext, String formSchemeKey, String fmlGroupName, boolean isNewScheme, DesignFormulaSchemeDefine scheme, double progressLen, Map<String, List<FormulaInfo>> schemeFormMap, Map<String, DesignFormDefine> formCache, ExecutorContext transContext, ParaImportInfoResult schemeLog, CompareDataFormulaSchemeDTO oldSchemeCompare) throws JQException {
        String formCode;
        double startPos = importContext.getCurProgress();
        SingleFileFormulaInfo singleFormulaInfo = importContext.getMapScheme().getNewFormulaInfo();
        singleFormulaInfo.setSingleSchemeName(fmlGroupName);
        if (!isNewScheme) {
            singleFormulaInfo.setNetSchemeName(fmlGroupName);
            singleFormulaInfo.setNetSchemeKey(scheme.getKey());
        } else {
            singleFormulaInfo.setNetSchemeName(fmlGroupName);
            singleFormulaInfo.setNetSchemeKey(scheme.getKey());
        }
        importContext.getMapScheme().getFormulaInfos().add(singleFormulaInfo);
        HashMap<String, CompareDataFormulaFormDTO> oldFormulaFormCompares = new HashMap<String, CompareDataFormulaFormDTO>();
        if (oldSchemeCompare != null && importContext.getCompareInfo() != null) {
            CompareDataFormulaFormDTO queryParam = new CompareDataFormulaFormDTO();
            queryParam.setInfoKey(importContext.getCompareInfo().getKey());
            queryParam.setFmlSchemeCompareKey(oldSchemeCompare.getKey());
            Iterator<Map.Entry<String, List<Object>>> formFormulas = this.formulaFormService.list(queryParam);
            if (formFormulas != null) {
                Iterator iterator = formFormulas.iterator();
                while (iterator.hasNext()) {
                    CompareDataFormulaFormDTO formCompare = (CompareDataFormulaFormDTO)iterator.next();
                    oldFormulaFormCompares.put(formCompare.getSingleCode(), formCompare);
                }
            }
        }
        ArrayList<String> importSingleFormCodes = new ArrayList<String>();
        importSingleFormCodes.add("SYS_ALLTABLE");
        if (!formCache.isEmpty()) {
            for (Map.Entry<String, DesignFormDefine> entry : formCache.entrySet()) {
                formCode = entry.getKey();
                importSingleFormCodes.add(formCode);
            }
        } else if (!importContext.getSortRepList().isEmpty()) {
            for (RepInfo repInfo : importContext.getSortRepList()) {
                importSingleFormCodes.add(repInfo.getCode());
            }
        } else if (!importContext.getParaInfo().getRepInfos().isEmpty()) {
            for (RepInfo repInfo : importContext.getParaInfo().getRepInfos()) {
                importSingleFormCodes.add(repInfo.getCode());
            }
        } else {
            for (Map.Entry<String, List<Object>> entry : schemeFormMap.entrySet()) {
                formCode = entry.getKey();
                importSingleFormCodes.add(formCode);
            }
        }
        ArrayList<DesignFormulaDefine> insertFormulas = new ArrayList<DesignFormulaDefine>();
        ArrayList<DesignFormulaDefine> arrayList = new ArrayList<DesignFormulaDefine>();
        ArrayList<String> deleteFormulas = new ArrayList<String>();
        double progressLen2 = progressLen / (double)schemeFormMap.size();
        for (String formCode2 : importSingleFormCodes) {
            List<FormulaInfo> singleFormulas = schemeFormMap.get(formCode2);
            if (singleFormulas == null || singleFormulas.isEmpty()) continue;
            importContext.onProgress(startPos += progressLen2, "\u5bfc\u5165\u8868\u5355" + formCode2 + "\u7684\u516c\u5f0f");
            log.info("\u5bfc\u5165\u516c\u5f0f\u65b9\u6848:{}", (Object)(fmlGroupName + "," + scheme.getKey().toString() + ",\u8868\u5355\uff1a" + formCode2 + ",\u65f6\u95f4:" + new Date().toString()));
            DesignFormDefine form = null;
            if (formCache.containsKey(formCode2)) {
                form = formCache.get(formCode2);
            } else if (StringUtils.isNotEmpty((String)formCode2) && !formCode2.equalsIgnoreCase("SYS_ALLTABLE")) {
                form = this.viewController.querySoftFormDefineByCodeInFormScheme(formSchemeKey, formCode2);
                if (form == null) {
                    log.info("\u5bfc\u5165\u516c\u5f0f\uff0c\u8868\u5355\u4e0d\u5b58\u5728\uff1a{}", (Object)formCode2);
                    continue;
                }
                formCache.put(formCode2, form);
            }
            CompareDataFormulaFormDTO formulaFormCompare = null;
            if (!oldFormulaFormCompares.isEmpty() && oldFormulaFormCompares.containsKey(formCode2) && ((formulaFormCompare = (CompareDataFormulaFormDTO)oldFormulaFormCompares.get(formCode2)).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || formulaFormCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) continue;
            ParaImportInfoResult formFormulaLog = null;
            if (schemeLog != null) {
                formFormulaLog = new ParaImportInfoResult();
                if (formulaFormCompare != null) {
                    formFormulaLog.copyForm(formulaFormCompare);
                } else {
                    formFormulaLog.setKey(UUID.randomUUID().toString());
                }
                formFormulaLog.setSingleCode(formCode2);
                if (form != null) {
                    formFormulaLog.setSingleTitle(form.getTitle());
                    formFormulaLog.setNetCode(form.getFormCode());
                    formFormulaLog.setNetTitle(form.getTitle());
                }
                formFormulaLog.setDataType(CompareDataType.DATA_FORM);
            }
            Map<String, DesignFormulaDefine> oldFormulaDic = this.getFormulasFromSchemeAndForm(scheme.getKey(), form);
            SingleFileTableFormulaInfoImpl singleTableFormulas = new SingleFileTableFormulaInfoImpl();
            singleTableFormulas.setSingleTableCode(formCode2);
            String netFormCode = null;
            if (null != form) {
                singleTableFormulas.setNetFormCode(formCode2);
                singleTableFormulas.setNetFormKey(form.getKey());
                netFormCode = form.getFormCode();
            }
            singleFormulaInfo.getTableFormulaInfos().put(formCode2, singleTableFormulas);
            ArrayList<DesignFormulaDefine> curFormFormulas = new ArrayList<DesignFormulaDefine>();
            HashSet<String> singleFormulaCodes = new HashSet<String>();
            for (FormulaInfo singleFormula : singleFormulas) {
                DesignFormulaDefine formula = null;
                boolean isFormulaNew = isNewScheme;
                String aCode = singleFormula.getUserLevel() + singleFormula.getCode();
                if (StringUtils.isEmpty((String)aCode)) {
                    log.info("\u516c\u5f0f\u5f02\u5e38\uff0c\u7f16\u53f7\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\uff1a" + singleFormula.getExpression());
                    continue;
                }
                singleFormulaCodes.add(aCode);
                if (!isNewScheme) {
                    if (oldFormulaDic.containsKey(aCode)) {
                        formula = oldFormulaDic.get(aCode);
                    }
                    boolean bl = isFormulaNew = null == formula;
                    if (isFormulaNew) {
                        formula = this.formulaController.createFormulaDefine();
                    }
                } else {
                    formula = this.formulaController.createFormulaDefine();
                    isFormulaNew = true;
                }
                formula.setFormulaSchemeKey(scheme.getKey());
                if (null != form) {
                    formula.setFormKey(form.getKey());
                }
                this.setFormulaAttr(singleFormula, formula, aCode);
                if (isFormulaNew) {
                    formula.setOwnerLevelAndId(importContext.getCurServerCode());
                    insertFormulas.add(formula);
                } else {
                    arrayList.add(formula);
                }
                curFormFormulas.add(formula);
                if (StringUtils.isNotEmpty((String)formula.getExpression()) && formula.getExpression().length() >= 2000) {
                    log.info("\u516c\u5f0f\u5f02\u5e38\uff0c\u8868\u8fbe\u5f0f\u8fc7\u957f\uff0c\u8bf7\u68c0\u67e5\uff1a" + aCode + "," + formula.getExpression());
                    formula.setExpression(formula.getExpression().substring(0, 2000));
                }
                if (insertFormulas.size() > 2000 || arrayList.size() > 2000) {
                    if (transContext != null && curFormFormulas.size() > 0) {
                        this.transFormulaDefines(transContext, scheme.getKey(), netFormCode, curFormFormulas, formFormulaLog);
                        curFormFormulas.clear();
                    }
                    this.updateFormulaCacheToServer(importContext, insertFormulas, arrayList, false);
                }
                SingleFileFormulaItemImpl singleFormulaMapItem = new SingleFileFormulaItemImpl();
                singleFormulaMapItem.setSingleFormulaCode(aCode);
                singleFormulaMapItem.setSingleFormulaExp(singleFormula.getExpression());
                if (null != formula) {
                    singleFormulaMapItem.setNetFormulaCode(formula.getCode());
                    singleFormulaMapItem.setNetFormulaExp(formula.getExpression());
                    singleFormulaMapItem.setNetFormulaKey(formula.getKey());
                }
                singleFormulaMapItem.setSingleSchemeName(fmlGroupName);
                singleFormulaMapItem.setSingleTableCode(formCode2);
                if (null != scheme) {
                    singleFormulaMapItem.setNetSchemeName(fmlGroupName);
                    singleFormulaMapItem.setNetSchemeKey(scheme.getKey());
                }
                if (null != form) {
                    singleFormulaMapItem.setNetFormCode(form.getFormCode());
                    singleFormulaMapItem.setNetFormKey(form.getKey());
                }
                singleTableFormulas.getFormulaItems().put(singleFormulaMapItem.getSingleFormulaCode(), singleFormulaMapItem);
            }
            if (transContext != null && curFormFormulas.size() > 0) {
                this.transFormulaDefines(transContext, scheme.getKey(), netFormCode, curFormFormulas, formFormulaLog);
                curFormFormulas.clear();
            }
            if (schemeLog != null && formFormulaLog != null && !formFormulaLog.getItems().isEmpty()) {
                if ("SYS_ALLTABLE".equalsIgnoreCase(formCode2)) {
                    schemeLog.insert(formFormulaLog, 0);
                } else {
                    schemeLog.addItem(formFormulaLog);
                }
            }
            for (DesignFormulaDefine netFormula : oldFormulaDic.values()) {
                if (singleFormulaCodes.contains(netFormula.getCode()) || !StringUtils.isEmpty((String)netFormula.getOwnerLevelAndId()) && !"0".equalsIgnoreCase(netFormula.getOwnerLevelAndId()) && (!StringUtils.isNotEmpty((String)netFormula.getOwnerLevelAndId()) || !netFormula.getOwnerLevelAndId().equalsIgnoreCase(importContext.getCurServerCode()))) continue;
                deleteFormulas.add(netFormula.getKey());
            }
            this.updateFormulaCacheToServer(importContext, insertFormulas, arrayList, false);
        }
        this.updateFormulaCacheToServer(importContext, insertFormulas, arrayList, true);
        if (importContext.getImportOption().isOverWriteAll() && deleteFormulas.size() > 0) {
            this.formulaController.deleteFormulaDefines(deleteFormulas.toArray(new String[deleteFormulas.size()]));
        }
    }

    private void setFormulaAttr(FormulaInfo singleFormula, DesignFormulaDefine formula, String aCode) {
        try {
            formula.setCode(aCode);
            formula.setOrder(OrderGenerator.newOrder());
            formula.setDescription(singleFormula.getDescription());
            formula.setExpression(singleFormula.getExpression());
            formula.setUseCalculate(singleFormula.isCaclFormula());
            formula.setIsAutoExecute(singleFormula.isAutoCaclFormula());
            formula.setUseCheck(singleFormula.isCheckFormula());
            formula.setCheckType(this.getFormulaCheckType(singleFormula.getType(), singleFormula.isCheckFormula()));
            formula.setDataItems(singleFormula.getErrorData());
            formula.setUseBalance(singleFormula.isCheckBalance());
            if (formula.getUseBalance()) {
                formula.setBalanceZBExp(singleFormula.getAdjustCells());
            } else {
                formula.setBalanceZBExp(null);
            }
        }
        catch (Exception ex) {
            log.info("\u516c\u5f0f\u5b58\u5728\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\uff1a" + aCode + "," + singleFormula.getExpression() + "");
            throw ex;
        }
    }

    private Map<String, DesignFormulaDefine> getFormulasFromSchemeAndForm(String formulaSchemeKey, DesignFormDefine form) throws JQException {
        HashMap<String, DesignFormulaDefine> oldFormulaDic = new HashMap<String, DesignFormulaDefine>();
        List oldFormulas = null;
        oldFormulas = null == form ? this.formulaController.getAllSoftFormulasInForm(formulaSchemeKey, null) : this.formulaController.getAllSoftFormulasInForm(formulaSchemeKey, form.getKey());
        if (null != oldFormulas) {
            for (DesignFormulaDefine formula : oldFormulas) {
                oldFormulaDic.put(formula.getCode(), formula);
            }
        }
        return oldFormulaDic;
    }

    private int getFormulaCheckType(String atype, boolean isCheck) {
        int result = 0;
        if (isCheck && !StringUtils.isEmpty((String)atype)) {
            if (atype.equals("1")) {
                result = FormulaCheckType.FORMULA_CHECK_ERROR.getValue();
            } else if (atype.equals("0")) {
                result = FormulaCheckType.FORMULA_CHECK_WARNING.getValue();
            } else if (atype.equals("2")) {
                result = FormulaCheckType.FORMULA_CHECK_HINT.getValue();
            }
        }
        return result;
    }

    private void updateFormulaCacheToServer(TaskImportContext importContext, List<DesignFormulaDefine> insertFormulas, List<DesignFormulaDefine> updateFormulas, boolean isMustPost) throws JQException {
        if (isMustPost && insertFormulas.size() > 0 || insertFormulas.size() > 2000) {
            this.formulaController.insertFormulasNotAnalysis(insertFormulas.toArray(new DesignFormulaDefine[insertFormulas.size()]));
            insertFormulas.clear();
        }
        if (isMustPost && updateFormulas.size() > 0 || updateFormulas.size() > 2000) {
            this.formulaController.updateFormulasNotAnalysis(updateFormulas.toArray(new DesignFormulaDefine[updateFormulas.size()]));
            updateFormulas.clear();
        }
    }

    @Override
    public ExecutorContext initContext(TaskImportContext importContext) throws Exception {
        Map<String, ConversionFormInfo> conversionFormInfoMap = this.getConversionFormInfoMap(importContext);
        Map<String, String> enumMap = this.getEnumMap(importContext);
        return this.initContext(conversionFormInfoMap, importContext.getFormSchemeKey(), enumMap);
    }

    private ExecutorContext initContext(Map<String, ConversionFormInfo> conversionFormInfoMap, String formSchemeKey, Map<String, String> enumMap) throws Exception {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController2);
        context.setDesignTimeData(true, this.npDesignController);
        context.setJQReportModel(true);
        FormulaConversionContext context1 = new FormulaConversionContext();
        context1.setConversionFormInfoMap(conversionFormInfoMap);
        context1.setEntityMap(enumMap);
        FormulaConversionFmlExecEnvironment env = new FormulaConversionFmlExecEnvironment(this.nrDesignController, this.npDesignController, formSchemeKey, null, context1);
        env.setUseCache();
        context.setEnv((IFmlExecEnvironment)env);
        FunctionProvider singleProvider = new FunctionProvider();
        singleProvider.add((IFunction)new SingleGetmeaning());
        singleProvider.add((IFunction)new SingleInlist());
        context.getCache().getFormulaParser(true).registerFunctionProvider((IFunctionProvider)singleProvider);
        return context;
    }

    private Map<String, ConversionFormInfo> getConversionFormInfoMap(TaskImportContext importContext) {
        HashMap<String, ConversionFormInfo> conversionFormInfoMap;
        block56: {
            conversionFormInfoMap = new HashMap<String, ConversionFormInfo>();
            if (importContext.getMapScheme() == null) break block56;
            if (!importContext.getImportOption().isUploadForm()) {
                if (StringUtils.isNotEmpty((String)importContext.getMapSchemeKey())) {
                    HashMap<String, DesignFormDefine> formCodeMap = new HashMap<String, DesignFormDefine>();
                    HashMap<String, DesignDataTable> tableCodeMap = new HashMap<String, DesignDataTable>();
                    HashMap tableFieldsMap = new HashMap();
                    HashMap tableColumnsMap = new HashMap();
                    List forms = this.viewController.queryAllSoftFormDefinesByFormScheme(importContext.getFormSchemeKey());
                    for (Object form : forms) {
                        formCodeMap.put(form.getFormCode(), (DesignFormDefine)form);
                    }
                    List list = this.dataSchemeSevice.getDataTableByScheme(importContext.getDataSchemeKey());
                    for (Object table : list) {
                        tableCodeMap.put(table.getCode(), (DesignDataTable)table);
                    }
                    List zbMappings = this.zbMappingService.findByMS(importContext.getMapSchemeKey());
                    for (ZBMapping zbMapping : zbMappings) {
                        DesignDataField field;
                        DesignFormDefine form;
                        String formKey = null;
                        boolean isFMDM = false;
                        if (formCodeMap.containsKey(zbMapping.getForm()) && (form = (DesignFormDefine)formCodeMap.get(zbMapping.getForm())) != null) {
                            formKey = form.getKey();
                            boolean bl = isFMDM = form.getFormType() == FormType.FORM_TYPE_NEWFMDM;
                        }
                        if (StringUtils.isEmpty(formKey)) continue;
                        ConversionFormInfo formInfo = null;
                        HashMap<String, ConversionFieldInfo> fieldInfoMap = null;
                        if (conversionFormInfoMap.containsKey(formKey)) {
                            formInfo = (ConversionFormInfo)conversionFormInfoMap.get(formKey);
                            fieldInfoMap = formInfo.getFieldInfoMap();
                        } else {
                            formInfo = new ConversionFormInfo();
                            fieldInfoMap = new HashMap<String, ConversionFieldInfo>();
                            formInfo.setFieldInfoMap(fieldInfoMap);
                            conversionFormInfoMap.put(formKey, formInfo);
                        }
                        String netFieldKey = null;
                        HashMap<String, DesignDataField> tableFields = null;
                        HashMap<String, ColumnModelDefine> tableColumns = null;
                        if (tableFieldsMap.containsKey(zbMapping.getTable())) {
                            tableFields = (HashMap<String, DesignDataField>)tableFieldsMap.get(zbMapping.getTable());
                        } else {
                            List fields = null;
                            if (tableCodeMap.containsKey(zbMapping.getTable())) {
                                DesignDataTable table = (DesignDataTable)tableCodeMap.get(zbMapping.getTable());
                                fields = this.dataSchemeSevice.getDataFieldByTable(table.getKey());
                            } else {
                                fields = this.dataSchemeSevice.getDataFieldByTableCode(zbMapping.getTable());
                            }
                            if (isFMDM && fields.isEmpty()) {
                                if (tableColumnsMap.containsKey(zbMapping.getTable())) {
                                    tableColumns = (Map)tableColumnsMap.get(zbMapping.getTable());
                                } else {
                                    TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(zbMapping.getTable());
                                    if (tableModel != null) {
                                        tableColumns = new HashMap<String, ColumnModelDefine>();
                                        List columModel = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                                        for (ColumnModelDefine field2 : columModel) {
                                            tableColumns.put(field2.getCode(), field2);
                                        }
                                        tableColumnsMap.put(zbMapping.getTable(), tableColumns);
                                    }
                                }
                            } else {
                                tableFields = new HashMap<String, DesignDataField>();
                                for (DesignDataField field3 : fields) {
                                    tableFields.put(field3.getCode(), field3);
                                }
                                tableFieldsMap.put(zbMapping.getTable(), tableFields);
                            }
                        }
                        if (tableFields != null && tableFields.containsKey(zbMapping.getZbCode())) {
                            field = (DesignDataField)tableFields.get(zbMapping.getZbCode());
                            if (field != null) {
                                netFieldKey = field.getKey();
                            }
                        } else if (tableColumns != null && tableColumns.containsKey(zbMapping.getZbCode())) {
                            field = (ColumnModelDefine)tableColumns.get(zbMapping.getZbCode());
                            if (field != null) {
                                netFieldKey = field.getID();
                            }
                        } else if (!isFMDM) continue;
                        String fieldCode = zbMapping.getMapping();
                        int id1 = fieldCode.indexOf("[");
                        int id2 = fieldCode.indexOf("]");
                        if (id1 > 0 && id2 > 0) {
                            fieldCode = fieldCode.substring(id1 + 1, id2);
                        }
                        ConversionFieldInfo fieldInfo = new ConversionFieldInfo();
                        fieldInfo.setOldCode(fieldCode);
                        fieldInfo.setNewKey(netFieldKey);
                        fieldInfo.setNewCode(zbMapping.getZbCode());
                        fieldInfoMap.put(fieldInfo.getOldCode(), fieldInfo);
                    }
                }
            } else {
                List tableInfos = importContext.getMapScheme().getTableInfos();
                ArrayList<SingleFileFmdmInfo> tableInfos2 = new ArrayList<SingleFileFmdmInfo>();
                tableInfos2.add(importContext.getMapScheme().getFmdmInfo());
                tableInfos2.addAll(tableInfos);
                HashMap tableFieldsMap = new HashMap();
                HashMap tableColumnsMap = new HashMap();
                for (SingleFileTableInfo singleFileTableInfo : tableInfos2) {
                    CompareDataFormDTO formCapare;
                    ConversionFormInfo formInfo = new ConversionFormInfo();
                    HashMap<String, ConversionFieldInfo> fieldInfoMap = new HashMap<String, ConversionFieldInfo>();
                    if (importContext.getCompareFormDic().containsKey(singleFileTableInfo.getSingleTableCode()) && StringUtils.isNotEmpty((String)importContext.getMapSchemeKey()) && ((formCapare = importContext.getCompareFormDic().get(singleFileTableInfo.getSingleTableCode())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || formCapare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) {
                        DesignFormDefine form = this.viewController.querySoftFormDefineByCodeInFormScheme(importContext.getFormSchemeKey(), formCapare.getNetCode());
                        boolean isFMDM = false;
                        if (form != null) {
                            isFMDM = form.getFormType() == FormType.FORM_TYPE_NEWFMDM;
                        }
                        List zbMappings = this.zbMappingService.findByMSAndForm(importContext.getMapSchemeKey(), formCapare.getNetCode());
                        for (ZBMapping zbMapping : zbMappings) {
                            DesignDataField field;
                            String netFieldKey = null;
                            HashMap<String, DesignDataField> tableFields = null;
                            HashMap<String, ColumnModelDefine> tableColumns = null;
                            if (tableFieldsMap.containsKey(zbMapping.getTable())) {
                                tableFields = (HashMap<String, DesignDataField>)tableFieldsMap.get(zbMapping.getTable());
                            } else {
                                List fields = this.dataSchemeSevice.getDataFieldByTableCode(zbMapping.getTable());
                                if (isFMDM && fields.isEmpty()) {
                                    if (tableColumnsMap.containsKey(zbMapping.getTable())) {
                                        tableColumns = (Map)tableColumnsMap.get(zbMapping.getTable());
                                    } else {
                                        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(zbMapping.getTable());
                                        if (tableModel != null) {
                                            tableColumns = new HashMap<String, ColumnModelDefine>();
                                            List columModel = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                                            for (ColumnModelDefine field4 : columModel) {
                                                tableColumns.put(field4.getCode(), field4);
                                            }
                                            tableColumnsMap.put(zbMapping.getTable(), tableColumns);
                                        }
                                    }
                                } else {
                                    tableFields = new HashMap<String, DesignDataField>();
                                    for (DesignDataField field5 : fields) {
                                        tableFields.put(field5.getCode(), field5);
                                    }
                                    tableFieldsMap.put(zbMapping.getTable(), tableFields);
                                }
                            }
                            if (tableFields != null && tableFields.containsKey(zbMapping.getZbCode())) {
                                field = (DesignDataField)tableFields.get(zbMapping.getZbCode());
                                if (field != null) {
                                    netFieldKey = field.getKey();
                                }
                            } else if (tableColumns != null && tableColumns.containsKey(zbMapping.getZbCode())) {
                                field = (ColumnModelDefine)tableColumns.get(zbMapping.getZbCode());
                                if (field != null) {
                                    netFieldKey = field.getID();
                                }
                            } else if (!isFMDM) continue;
                            String fieldCode = zbMapping.getMapping();
                            int id1 = fieldCode.indexOf("[");
                            int id2 = fieldCode.indexOf("]");
                            if (id1 > 0 && id2 > 0) {
                                fieldCode = fieldCode.substring(id1 + 1, id2);
                            }
                            ConversionFieldInfo fieldInfo = new ConversionFieldInfo();
                            fieldInfo.setOldCode(fieldCode);
                            fieldInfo.setNewKey(netFieldKey);
                            fieldInfo.setNewCode(zbMapping.getZbCode());
                            fieldInfoMap.put(fieldInfo.getOldCode(), fieldInfo);
                        }
                        formInfo.setFieldInfoMap(fieldInfoMap);
                        conversionFormInfoMap.put(formCapare.getNetKey(), formInfo);
                        continue;
                    }
                    boolean isFMDM = "FMDM".equalsIgnoreCase(singleFileTableInfo.getSingleTableCode());
                    for (SingleFileFieldInfo field : singleFileTableInfo.getRegion().getFields()) {
                        String netKey = field.getNetFieldKey();
                        if (isFMDM && StringUtils.isEmpty((String)netKey) && StringUtils.isNotEmpty((String)field.getNetTableCode())) {
                            HashMap<String, ColumnModelDefine> tableColumns = null;
                            if (tableColumnsMap.containsKey(field.getNetTableCode())) {
                                tableColumns = (HashMap<String, ColumnModelDefine>)tableColumnsMap.get(field.getNetTableCode());
                            } else {
                                TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(field.getNetTableCode());
                                if (tableModel != null) {
                                    tableColumns = new HashMap<String, ColumnModelDefine>();
                                    List columModel = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                                    for (ColumnModelDefine field2 : columModel) {
                                        tableColumns.put(field2.getCode(), field2);
                                    }
                                    tableColumnsMap.put(field.getNetTableCode(), tableColumns);
                                }
                            }
                            if (tableColumns.containsKey(field.getNetFieldCode())) {
                                netKey = ((ColumnModelDefine)tableColumns.get(field.getNetFieldCode())).getID();
                            }
                        }
                        ConversionFieldInfo fieldInfo = new ConversionFieldInfo();
                        fieldInfo.setOldCode(field.getFieldCode());
                        fieldInfo.setNewKey(netKey);
                        fieldInfo.setNewCode(field.getNetFieldCode());
                        fieldInfoMap.put(fieldInfo.getOldCode(), fieldInfo);
                    }
                    for (SingleFileRegionInfo subRegion : singleFileTableInfo.getRegion().getSubRegions()) {
                        for (SingleFileFieldInfo field : subRegion.getFields()) {
                            ConversionFieldInfo fieldInfo = new ConversionFieldInfo();
                            fieldInfo.setOldCode(field.getFieldCode());
                            fieldInfo.setNewKey(field.getNetFieldKey());
                            fieldInfo.setNewCode(field.getNetFieldCode());
                            fieldInfoMap.put(fieldInfo.getOldCode(), fieldInfo);
                        }
                    }
                    formInfo.setFieldInfoMap(fieldInfoMap);
                    conversionFormInfoMap.put(singleFileTableInfo.getNetFormKey(), formInfo);
                }
            }
        }
        return conversionFormInfoMap;
    }

    private Map<String, String> getEnumMap(TaskImportContext importContext) {
        HashMap<String, String> enumMap;
        block8: {
            block7: {
                List baseDataMappings;
                enumMap = new HashMap<String, String>();
                if (importContext.getImportOption().isUploadEnum()) break block7;
                if (!StringUtils.isNotEmpty((String)importContext.getMapSchemeKey()) || (baseDataMappings = this.baseDataMappingService.getBaseDataMapping(importContext.getMapSchemeKey())) == null || baseDataMappings.isEmpty()) break block8;
                for (BaseDataMapping aEnum : baseDataMappings) {
                    enumMap.put(aEnum.getMappingCode(), aEnum.getBaseDataCode());
                }
                break block8;
            }
            if (importContext.getMapScheme() != null) {
                HashMap<String, BaseDataMapping> baseDataMappingsMap = null;
                List enums = importContext.getMapScheme().getEnumInfos();
                if (enums != null && !enums.isEmpty()) {
                    for (SingleFileEnumInfo aEnum : enums) {
                        CompareDataEnumDTO enumCompare;
                        if (importContext.getCompareEnumDic().containsKey(aEnum.getEnumCode()) && ((enumCompare = importContext.getCompareEnumDic().get(aEnum.getEnumCode())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || enumCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) {
                            if (baseDataMappingsMap == null) {
                                baseDataMappingsMap = new HashMap<String, BaseDataMapping>();
                                List baseDataMappings = this.baseDataMappingService.getBaseDataMapping(importContext.getMapSchemeKey());
                                baseDataMappings = this.baseDataMappingService.getBaseDataMapping(importContext.getMapSchemeKey());
                                for (BaseDataMapping enumMapping : baseDataMappings) {
                                    baseDataMappingsMap.put(enumMapping.getMappingCode(), enumMapping);
                                }
                            }
                            if (!baseDataMappingsMap.containsKey(aEnum.getEnumCode())) continue;
                            BaseDataMapping enumMapping = (BaseDataMapping)baseDataMappingsMap.get(aEnum.getEnumCode());
                            enumMap.put(enumMapping.getMappingCode(), enumMapping.getBaseDataCode());
                            continue;
                        }
                        enumMap.put(aEnum.getEnumCode(), aEnum.getNetTableCode());
                    }
                }
            }
        }
        return enumMap;
    }

    private List<String> transFormulaDefines(ExecutorContext context, String formSchemeKey, String formCode, List<DesignFormulaDefine> formulaDefines, ParaImportInfoResult formFormulaLog) throws JQException {
        List<String> ids = null;
        if (context == null) {
            return ids;
        }
        String expression = "";
        try {
            for (DesignFormulaDefine designFormulaDefine : formulaDefines) {
                try {
                    expression = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)context, (String)designFormulaDefine.getExpression(), (String)formCode, (DataEngineConsts.FormulaShowType)DataEngineConsts.FormulaShowType.JQ);
                    designFormulaDefine.setExpression(expression);
                }
                catch (Exception e) {
                    log.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38\uff0c" + formCode + "," + designFormulaDefine.getCode() + "," + designFormulaDefine.getExpression(), e);
                    if (formFormulaLog == null) continue;
                    ParaImportInfoResult formulaLog = new ParaImportInfoResult();
                    formulaLog.setKey(UUID.randomUUID().toString());
                    formulaLog.setCompareDataKey(null);
                    formulaLog.setData(null);
                    formulaLog.setCompareInfoKey(null);
                    formulaLog.setDataType(CompareDataType.DATA_FORMULA);
                    formulaLog.setChangeType(CompareChangeType.CHANGE_FLAGSAME);
                    formulaLog.setNetCode(designFormulaDefine.getCode());
                    formulaLog.setNetKey(designFormulaDefine.getKey());
                    formulaLog.setNetTitle(designFormulaDefine.getExpression());
                    formulaLog.setSingleCode(designFormulaDefine.getCode());
                    formulaLog.setSingleTitle(designFormulaDefine.getExpression());
                    formulaLog.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    formulaLog.setUpdateTime(Instant.now());
                    formulaLog.setCode(designFormulaDefine.getCode());
                    formulaLog.setSuccess(false);
                    formulaLog.setMessage(e.getMessage());
                    formFormulaLog.addItem(formulaLog);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
        return ids;
    }

    private ParaImportInfoResult setFormulaSchemeLog(ParaImportInfoResult formulaSchemesLog, String formulaSchemeName, DesignFormulaSchemeDefine scheme) {
        ParaImportInfoResult schemeLog = null;
        if (formulaSchemesLog != null) {
            schemeLog = new ParaImportInfoResult();
            schemeLog.setKey(formulaSchemeName);
            schemeLog.setCompareDataKey(null);
            schemeLog.setData(null);
            schemeLog.setCompareInfoKey(formulaSchemesLog.getCompareInfoKey());
            schemeLog.setDataType(CompareDataType.DATA_FORMULA_SCHEME);
            schemeLog.setChangeType(CompareChangeType.CHANGE_DEFAULT);
            schemeLog.setNetCode(null);
            schemeLog.setNetKey(scheme.getKey());
            schemeLog.setNetTitle(scheme.getTitle());
            schemeLog.setSingleCode(null);
            schemeLog.setSingleTitle(formulaSchemeName);
            schemeLog.setUpdateType(CompareUpdateType.UPDATE_OVER);
            schemeLog.setUpdateTime(Instant.now());
            schemeLog.setCode(formulaSchemeName);
            schemeLog.setSuccess(true);
            formulaSchemesLog.addItem(schemeLog);
        }
        return schemeLog;
    }
}

