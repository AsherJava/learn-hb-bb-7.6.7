/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.BatchFMDMDTO
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.IFMDMUpdateResult
 *  com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.common.UnitNatureGetter
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.datatable.DataColumn
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.xlib.utils.StringUtil
 *  javax.annotation.Resource
 *  nr.single.map.configurations.bean.AutoAppendCode
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.MappingConfig
 *  nr.single.map.configurations.bean.RuleKind
 *  nr.single.map.configurations.bean.RuleMap
 *  nr.single.map.configurations.bean.SkipUnit
 *  nr.single.map.configurations.bean.UnitCustomMapping
 *  nr.single.map.configurations.bean.UpdateWay
 *  nr.single.map.data.DataEntityCache
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.bean.RepeatEntityNode
 *  nr.single.map.data.bean.RepeatFormNode
 *  nr.single.map.data.bean.RepeatImportParam
 *  nr.single.map.data.bean.SingleDataRow
 *  nr.single.map.data.exception.SingleDataCorpException
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.internal.util.SyntaxExcuteMapEntityImpl
 *  nr.single.map.data.service.SingleDataTransService
 *  nr.single.map.data.service.SingleDimissionServcie
 *  nr.single.map.data.service.SingleMappingService
 *  nr.single.map.data.service.TaskDataService
 *  nr.single.map.data.util.SyntaxExcuteMapEntity
 */
package nr.single.data.datain.internal.service.fmdm;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import nr.single.data.bean.CheckNodeList;
import nr.single.data.bean.CheckResultNode;
import nr.single.data.datain.service.ITaskFileImportEntityService;
import nr.single.data.datain.service.ITaskFileMergeEntityService;
import nr.single.data.system.SingleDataOptionsService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.data.util.bean.SingleAttachmentFailFile;
import nr.single.data.util.bean.SingleAttachmentResult;
import nr.single.data.util.service.ISingleAttachmentService;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SkipUnit;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UpdateWay;
import nr.single.map.data.DataEntityCache;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.bean.RepeatEntityNode;
import nr.single.map.data.bean.RepeatFormNode;
import nr.single.map.data.bean.RepeatImportParam;
import nr.single.map.data.bean.SingleDataRow;
import nr.single.map.data.exception.SingleDataCorpException;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.util.SyntaxExcuteMapEntityImpl;
import nr.single.map.data.service.SingleDataTransService;
import nr.single.map.data.service.SingleDimissionServcie;
import nr.single.map.data.service.SingleMappingService;
import nr.single.map.data.service.TaskDataService;
import nr.single.map.data.util.SyntaxExcuteMapEntity;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="TaskFileImportEntityFMDMServiceImpl")
public class TaskFileImportEntityFMDMServiceImpl
implements ITaskFileImportEntityService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileImportEntityFMDMServiceImpl.class);
    @Autowired
    private TaskDataService taskDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private SystemIdentityService systemdentityService;
    @Autowired(required=false)
    private ITaskFileMergeEntityService entityMergeServie;
    @Autowired
    private IFormTypeApplyService formTypeApplyService;
    @Autowired
    private SingleMappingService mappingConfigService;
    @Autowired
    private SingleDimissionServcie singleDimService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private SingleDataTransService singleTransService;
    @Autowired
    private ISingleAttachmentService singleAttachService;
    @Autowired
    private SingleDataOptionsService singleOptionService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    private IProviderStore providerStore;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void importSingleEnityData(TaskDataContext importContext, String path, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        try {
            String dataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            String dbfFileName = dataPath + importContext.getSingleFileFlag() + "FMDM.DBF";
            try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
                SingleFileTableInfo table = null;
                if (null == table && importContext.getMapingCache().isMapConfig()) {
                    table = importContext.getMapingCache().getFmdmInfo();
                }
                this.ImportEntityDatasToNet(importContext, importContext.getDwEntityId(), dbf, table, asyncTaskMonitor);
            }
        }
        catch (Exception e) {
            importContext.error(logger, e.getMessage(), (Throwable)e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void ImportEntityDatasToNet(TaskDataContext importContext, String entityId, IDbfTable dbf, SingleFileTableInfo singeleTable, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        if (dbf.getRecordCount() > 0) {
            DataRow dbfRow = dbf.getRecordByIndex(0);
            String firstZDM = dbfRow.getValueString(0);
            importContext.setFirstZDM(firstZDM);
        }
        RepeatImportParam jioRepeatParam = importContext.getJioRepeatParam();
        if ("dt".equalsIgnoreCase(importContext.getProductName()) && jioRepeatParam != null && !jioRepeatParam.isRepeatByFile() && !jioRepeatParam.getEntityNodes().isEmpty()) {
            this.buidSingleEnityDataFromParam(importContext);
        } else {
            this.taskDataService.MapSingleEnityData(importContext);
        }
        importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u52a0\u8f7d\u53c2\u6570");
        if (StringUtils.isEmpty((String)importContext.getCurrentPeriod()) && StringUtils.isNotEmpty((String)importContext.getFirstZDM()) && StringUtils.isEmpty((String)importContext.getMapNetPeriodCode())) {
            String zdm = importContext.getFirstZDM();
            String singlePeriod = importContext.getEntityCache().getSinglePeriodByZdm(zdm);
            importContext.setCurrentPeriod(singlePeriod);
            String singlePeriod1 = importContext.getSingleTaskYear() + "@" + singlePeriod;
            String mapNetPeriod = this.taskDataService.getNetPeriodCode(importContext, singlePeriod1);
            importContext.setMapNetPeriodCode(mapNetPeriod);
        }
        HashMap<String, IFMDMAttribute> fieldMap = new HashMap<String, IFMDMAttribute>();
        HashMap<String, IFMDMAttribute> singleFieldMap = new HashMap<String, IFMDMAttribute>();
        HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, String> fieldValueMap = new HashMap<String, String>();
        HashMap<String, DataField> zdmFieldMap = new HashMap<String, DataField>();
        ArrayList<String> singleZdmWithoutPeriodCodes = new ArrayList<String>();
        LinkedHashMap SingleZdmCodeAndFieldCodeMap = new LinkedHashMap();
        ArrayList<DataField> zdmFieldList = new ArrayList<DataField>();
        ArrayList<SingleFileFieldInfo> singeZdmFieldList = new ArrayList<SingleFileFieldInfo>();
        ArrayList<SingleFileFieldInfo> singeOtherFieldList = new ArrayList<SingleFileFieldInfo>();
        int periodIndex = 0;
        int peirodLength = 0;
        int zmdLength = 0;
        SingleFileFmdmInfo fMTable = null;
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setEntityId(entityId);
        fmdmAttributeDTO.setFormSchemeKey(importContext.getFormSchemeKey());
        List fieldList = this.fmdmAttributeService.list(fmdmAttributeDTO);
        importContext.setDwEntityId(entityId);
        for (IFMDMAttribute attr : fieldList) {
            fieldMap.put(attr.getCode(), attr);
        }
        Map fMDMSingleMaps = null;
        fMDMSingleMaps = importContext.getMapingCache().getSingleFieldMap() != null && importContext.getMapingCache().getSingleFieldMap().containsKey("FMDM") ? (Map)importContext.getMapingCache().getSingleFieldMap().get("FMDM") : new HashMap();
        String periodCode = "";
        if (null != singeleTable) {
            DataField netField;
            for (SingleFileFieldInfo field : singeleTable.getRegion().getFields()) {
                mapNetFieldList.put(field.getNetFieldCode(), field);
                mapSingleFieldList.put(field.getFieldCode(), field);
                String netCode = field.getNetFieldCode();
                String singleFieldCode = field.getFieldCode();
                if (StringUtils.isNotEmpty((String)field.getFieldCode()) && fMDMSingleMaps != null && fMDMSingleMaps.containsKey("FMDM." + field.getFieldCode())) {
                    SingleFileFieldInfo netMapField = (SingleFileFieldInfo)fMDMSingleMaps.get("FMDM." + field.getFieldCode());
                    if (netMapField != null && StringUtils.isNotEmpty((String)netMapField.getNetFieldCode())) {
                        netCode = netMapField.getNetFieldCode();
                    }
                } else if (StringUtils.isNotEmpty((String)netCode) && (netField = this.dataSchemeSevice.getZbKindDataFieldBySchemeKeyAndCode(importContext.getDataSchemeKey(), netCode)) != null && StringUtils.isNotEmpty((String)netField.getAlias())) {
                    singleFieldCode = netField.getAlias();
                }
                if (StringUtils.isNotEmpty((String)netCode) && fieldMap.containsKey(netCode)) {
                    singleFieldMap.put(field.getFieldCode(), (IFMDMAttribute)fieldMap.get(netCode));
                    continue;
                }
                if (fieldMap.containsKey(singleFieldCode)) {
                    singleFieldMap.put(field.getFieldCode(), (IFMDMAttribute)fieldMap.get(singleFieldCode));
                    continue;
                }
                if (!fieldMap.containsKey(field.getFieldCode())) continue;
                singleFieldMap.put(field.getFieldCode(), (IFMDMAttribute)fieldMap.get(field.getFieldCode()));
            }
            fMTable = (SingleFileFmdmInfo)singeleTable;
            for (String code : fMTable.getZdmFieldCodes()) {
                String netCode1;
                if (!code.equalsIgnoreCase(fMTable.getPeriodField())) {
                    singleZdmWithoutPeriodCodes.add(code);
                }
                if (!mapSingleFieldList.containsKey(code)) continue;
                SingleFileFieldInfo singleField = (SingleFileFieldInfo)mapSingleFieldList.get(code);
                singeZdmFieldList.add(singleField);
                zmdLength += singleField.getFieldSize();
                if (!code.equals(fMTable.getPeriodField())) {
                    singeOtherFieldList.add(singleField);
                    periodIndex += singleField.getFieldSize();
                } else {
                    peirodLength = singleField.getFieldSize();
                }
                String netCode = singleField.getNetFieldCode();
                if (SingleZdmCodeAndFieldCodeMap.containsKey(code) && fieldMap.containsKey(netCode1 = (String)SingleZdmCodeAndFieldCodeMap.get(code))) {
                    netCode = netCode1;
                }
                netField = null;
                if (fieldMap.containsKey(netCode)) {
                    netField = (IFMDMAttribute)fieldMap.get(netCode);
                    zdmFieldMap.put(code, netField);
                    zdmFieldList.add(netField);
                } else {
                    zdmFieldMap.put(code, null);
                }
                if (!code.equalsIgnoreCase(fMTable.getPeriodField())) continue;
                periodCode = this.taskDataService.getSinglePeriodCode(importContext, importContext.getNetPeriodCode(), singleField.getFieldSize());
                fieldValueMap.put(singleField.getFieldCode(), periodCode);
                importContext.setMapCurrentPeriod(periodCode);
            }
        }
        this.upateEntityData(importContext, entityId, dbf, null, fMTable, fieldMap, singleFieldMap, singleZdmWithoutPeriodCodes, peirodLength, periodIndex, zmdLength, mapSingleFieldList, asyncTaskMonitor);
    }

    private void upateEntityData(TaskDataContext importContext, String entityId, IDbfTable dbf, IEntityQuery dataQuery, SingleFileFmdmInfo fMTable, Map<String, IFMDMAttribute> fieldMap, Map<String, IFMDMAttribute> singleFieldMap, List<String> singleZdmWithoutPeriodCodes, int peirodLength, int periodIndex, int zmdLength, Map<String, SingleFileFieldInfo> mapSingleFieldList, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        MappingConfig mConfig;
        DataScheme dataScheme;
        UpdateWay way;
        String netPeriodCode = importContext.getNetPeriodCode();
        if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
            netPeriodCode = importContext.getMapNetPeriodCode();
        }
        importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u67e5\u8be2\u6570\u636e");
        String singleMapExpresion = "";
        boolean corpMapUseExp = false;
        boolean corpMapUseRule = false;
        LinkedHashMap<String, RuleMap> singleMapRuleFields = new LinkedHashMap<String, RuleMap>();
        LinkedHashMap<String, RuleMap> netMapRuleFields = new LinkedHashMap<String, RuleMap>();
        if (null != importContext.getMapingCache().getMapConfig()) {
            ISingleMappingConfig mapConfig = importContext.getMapingCache().getMapConfig();
            List rules = mapConfig.getMapRule(RuleKind.UNIT_MAP_IMPORT);
            for (RuleMap rule : rules) {
                if (StringUtils.isNotEmpty((String)rule.getSingleCode())) {
                    singleMapRuleFields.put(rule.getSingleCode(), rule);
                    if (rule.getSingleCode().contains("+") || rule.getSingleCode().contains("(") || rule.getSingleCode().contains(")") || rule.getSingleCode().contains("*") || rule.getSingleCode().contains("$")) {
                        corpMapUseExp = true;
                    }
                    singleMapExpresion = StringUtils.isNotEmpty((String)singleMapExpresion) ? singleMapExpresion + " + (" + rule.getSingleCode() + ")" : "(" + rule.getSingleCode() + ")";
                }
                if (!StringUtils.isNotEmpty((String)rule.getNetCode())) continue;
                netMapRuleFields.put(rule.getNetCode(), rule);
                if (!rule.getNetCode().contains("+") && !rule.getNetCode().contains("(") && !rule.getNetCode().contains(")") && !rule.getNetCode().contains("*") && !rule.getNetCode().contains("$")) continue;
                corpMapUseExp = true;
            }
            if (!corpMapUseExp) {
                singleMapExpresion = "";
            }
            if (singleMapRuleFields.size() > 0 && netMapRuleFields.size() == singleMapRuleFields.size()) {
                corpMapUseRule = true;
                importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u4f7f\u7528\u5bfc\u5165\u89c4\u5219");
            } else {
                corpMapUseRule = false;
                importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u4e0d\u4f7f\u7528\u5bfc\u5165\u89c4\u5219");
            }
        }
        FMDMDataDTO queryParam = new FMDMDataDTO();
        queryParam.setFormSchemeKey(importContext.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)netPeriodCode);
        dimensionValueSet.clearValue(importContext.getEntityCompanyType());
        ArrayList canReadNetEntityKey = null;
        if (!this.systemdentityService.isAdmin() && importContext.getCanReadNetEntityKeys().size() > 0 && importContext.getMapingCache().getMapConfig() != null && importContext.getMapingCache().getMapConfig().getConfig() != null && !(way = importContext.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay()).isIncrement()) {
            canReadNetEntityKey = new ArrayList();
            canReadNetEntityKey.addAll(importContext.getCanReadNetEntityKeys());
            dimensionValueSet.setValue(importContext.getEntityCompanyType(), canReadNetEntityKey);
        }
        this.setOtherDimValue(importContext, dimensionValueSet);
        this.setSingleDimValue(importContext, dimensionValueSet, null);
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)importContext.getFormSchemeKey());
        List tranDims = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)dimensionCollection);
        HashMap<String, DimensionValueSet> tranDimDic = new HashMap<String, DimensionValueSet>();
        for (DimensionValueSet tranDim : tranDims) {
            String orgCode = (String)tranDim.getValue(importContext.getEntityCompanyType());
            tranDimDic.put(orgCode, tranDim);
        }
        importContext.getDimEntityCache().setEntityTranDims(tranDimDic);
        String dataSchemeInfo = importContext.getDataSchemeKey();
        if (StringUtils.isNotEmpty((String)importContext.getDataSchemeKey()) && (dataScheme = this.dataSchemeSevice.getDataScheme(importContext.getDataSchemeKey())) != null) {
            dataSchemeInfo = dataScheme.getCode() + "," + dataScheme.getTitle();
        }
        importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u6570\u636e\u65b9\u6848:" + dataSchemeInfo + ",\u4e3b\u4f53:" + entityId + ",\u7f51\u62a5\u65f6\u671f:" + netPeriodCode);
        HashMap<String, FMDMDataDTO> repeatDatas = new HashMap<String, FMDMDataDTO>();
        BatchFMDMDTO addBatchOptDTO = new BatchFMDMDTO();
        BatchFMDMDTO updateBatchOptDTO = new BatchFMDMDTO();
        BatchFMDMDTO deleteBatchOptDTO = new BatchFMDMDTO();
        addBatchOptDTO.setIgnoreCheckErrorData(true);
        addBatchOptDTO.setFormSchemeKey(importContext.getFormSchemeKey());
        addBatchOptDTO.setFmdmList(new ArrayList());
        addBatchOptDTO.setDimensionValueSet(dimensionValueSet);
        updateBatchOptDTO.setFormSchemeKey(importContext.getFormSchemeKey());
        updateBatchOptDTO.setFmdmList(new ArrayList());
        updateBatchOptDTO.setDimensionValueSet(dimensionValueSet);
        updateBatchOptDTO.setProviderStore(this.providerStore);
        updateBatchOptDTO.getIgnorePermissions().add("formCondition");
        if (null != importContext.getMapingCache().getMapConfig() && (mConfig = importContext.getMapingCache().getMapConfig().getConfig()) != null && mConfig.getDataImportRule() != null && mConfig.getDataImportRule().isEnable()) {
            updateBatchOptDTO.getIgnorePermissions().add("upload");
        }
        deleteBatchOptDTO.setFormSchemeKey(importContext.getFormSchemeKey());
        deleteBatchOptDTO.setFmdmList(new ArrayList());
        deleteBatchOptDTO.setDimensionValueSet(dimensionValueSet);
        HashMap<String, FMDMDataDTO> entityAutoExCodeAndRowMapNew = new HashMap<String, FMDMDataDTO>();
        HashMap<String, FMDMDataDTO> entityAutoExCodeAndRowMapUpdate = new HashMap<String, FMDMDataDTO>();
        HashMap<String, DataEntityInfo> entityAutoExCodeAndEntiyInfos = new HashMap<String, DataEntityInfo>();
        HashMap<String, DataEntityInfo> entityExpCodeAndEntiyInfoMap = new HashMap<String, DataEntityInfo>();
        HashMap<String, DataEntityInfo> matchNetCorps = new HashMap<String, DataEntityInfo>();
        ArrayList<DataEntityInfo> netCorpList = new ArrayList<DataEntityInfo>();
        ArrayList<String> errorTreeZdms = new ArrayList<String>();
        ArrayList<String> errorZdms = new ArrayList<String>();
        importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u6784\u9020\u4e3b\u4ee3\u7801\u4e0e\u6570\u636e\u884c\u5173\u7cfb,\u884c\u6578:" + importContext.getEntityCache().getEntityList().size() + ",\u5355\u673a\u7248\u65f6\u671f:" + importContext.getCurrentPeriod() + ",\u7f51\u62a5\u65f6\u671f:" + netPeriodCode);
        DataEntityInfo entityInfo = null;
        for (int i = 0; i < importContext.getEntityCache().getEntityList().size(); ++i) {
            entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityList().get(i);
            if (entityInfo == null || canReadNetEntityKey != null && !canReadNetEntityKey.isEmpty() && !canReadNetEntityKey.contains(entityInfo.getEntityKey())) continue;
            if (entityAutoExCodeAndEntiyInfos.containsKey(entityInfo.getEntityAutoExCode())) {
                DataEntityInfo oldEntityInfo = (DataEntityInfo)entityAutoExCodeAndEntiyInfos.get(entityInfo.getEntityAutoExCode());
                if (oldEntityInfo != null && !oldEntityInfo.getIsUnitMap()) {
                    entityAutoExCodeAndEntiyInfos.put(entityInfo.getEntityAutoExCode(), entityInfo);
                }
            } else {
                entityAutoExCodeAndEntiyInfos.put(entityInfo.getEntityAutoExCode(), entityInfo);
            }
            entityExpCodeAndEntiyInfoMap.put(entityInfo.getEntityExpCode(), entityInfo);
            netCorpList.add(entityInfo);
        }
        IFMDMAttribute titleField = null;
        IFMDMAttribute autoAppendField = null;
        boolean isIncrement = true;
        boolean isUpdate = true;
        CaseInsensitiveMap<String, String> zdmWithoutPeriodTempMap = new CaseInsensitiveMap<String, String>();
        CaseInsensitiveMap<String, String> zdmTempMap = new CaseInsensitiveMap<String, String>();
        CaseInsensitiveMap<String, List<FMDMDataDTO>> tempFJDRows = new CaseInsensitiveMap<String, List<FMDMDataDTO>>();
        CaseInsensitiveMap<String, String> tempFJDZdms = new CaseInsensitiveMap<String, String>();
        Map entityCodeKeyMap = importContext.getEntityCodeKeyMap();
        Map entityZdmAndKeyMap = importContext.getEntityZdmKeyMap();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        Map entityKeyZdmMap = importContext.getEntityKeyZdmMap();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map insertEntityZdmKeyMap = importContext.getInsertEntityZdmKeyMap();
        CaseInsensitiveMap<String, DataRow> dbfZdmRowMap = new CaseInsensitiveMap<String, DataRow>();
        CaseInsensitiveMap<String, String> dbfZdmFjdMap = new CaseInsensitiveMap<String, String>();
        CaseInsensitiveMap<String, SingleDataRow> dbfZdmAndRowMap = new CaseInsensitiveMap<String, SingleDataRow>();
        uploadEntityZdmKeyMap.clear();
        insertEntityZdmKeyMap.clear();
        AutoAppendCode autoCodeCoinfig = null;
        CaseInsensitiveMap<String, UnitCustomMapping> singleMapingUnitList = new CaseInsensitiveMap<String, UnitCustomMapping>();
        ArrayList skipUnitKeys = new ArrayList();
        HashSet<String> ignoreUpdateFields = new HashSet<String>();
        if (null != importContext.getMapingCache().getMapConfig()) {
            if (null != importContext.getMapingCache().getMapConfig().getConfig()) {
                UpdateWay way2 = importContext.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay();
                isIncrement = way2.isIncrement();
                isUpdate = way2.isUpdate();
                importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u5141\u8bb8\u65b0\u589e\uff1a" + isIncrement + ",\u5141\u8bb8\u66f4\u65b0\uff1a" + isUpdate);
                List ignoreAttribute = way2.getIgnoreAttribute();
                SkipUnit skipUnit = importContext.getMapingCache().getMapConfig().getConfig().getSkipUnit();
                if (null != skipUnit) {
                    List skipUnitKeys3;
                    String skipFormula;
                    List skipUnitKeys2 = skipUnit.getUnitKey();
                    if (null != skipUnitKeys2) {
                        skipUnitKeys.addAll(skipUnitKeys2);
                    }
                    if (StringUtils.isNotEmpty((String)(skipFormula = skipUnit.getFormula())) && null != (skipUnitKeys3 = this.taskDataService.queryFilterUnits(importContext, skipFormula))) {
                        skipUnitKeys.addAll(skipUnitKeys3);
                    }
                }
                if (ignoreAttribute != null && ignoreAttribute.size() > 0) {
                    for (String field : ignoreAttribute) {
                        if (!StringUtils.isNotEmpty((String)field)) continue;
                        ignoreUpdateFields.add(field.toUpperCase());
                    }
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getMapping()) {
                List list = importContext.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                CaseInsensitiveMap<String, UnitCustomMapping> netMapingUnitList = new CaseInsensitiveMap<String, UnitCustomMapping>();
                CaseInsensitiveMap<String, UnitCustomMapping> netMapingUnitOrgCodeList = new CaseInsensitiveMap<String, UnitCustomMapping>();
                for (UnitCustomMapping unitMap : list) {
                    if (StringUtils.isNotEmpty((String)unitMap.getNetUnitKey()) && StringUtils.isEmpty((String)unitMap.getSingleUnitCode())) {
                        skipUnitKeys.add(unitMap.getNetUnitKey());
                    } else if (StringUtils.isNotEmpty((String)unitMap.getSingleUnitCode())) {
                        if (singleMapingUnitList.containsKey(unitMap.getSingleUnitCode())) {
                            importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u4e2a\u522b\u5355\u4f4d\u6620\u5c04\uff0c\u5355\u4f4d\u4ee3\u7801\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\uff0c" + unitMap.getSingleUnitCode());
                            continue;
                        }
                        singleMapingUnitList.put(unitMap.getSingleUnitCode(), unitMap);
                    }
                    if (StringUtils.isNotEmpty((String)unitMap.getNetUnitCode())) {
                        if (netMapingUnitOrgCodeList.containsKey(unitMap.getNetUnitCode())) {
                            importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u4e2a\u522b\u5355\u4f4d\u6620\u5c04\uff0c\u5355\u4f4d\u4ee3\u7801\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\uff0c" + unitMap.getNetUnitCode());
                            continue;
                        }
                        netMapingUnitOrgCodeList.put(unitMap.getNetUnitCode(), unitMap);
                    }
                    if (!StringUtils.isNotEmpty((String)unitMap.getNetUnitKey())) continue;
                    if (netMapingUnitList.containsKey(unitMap.getNetUnitKey())) {
                        importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u4e2a\u522b\u5355\u4f4d\u6620\u5c04\uff0c\u5355\u4f4d\u4ee3\u7801\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\uff0c" + unitMap.getNetUnitKey());
                        continue;
                    }
                    netMapingUnitList.put(unitMap.getNetUnitKey(), unitMap);
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getConfig() && null != importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()) {
                autoCodeCoinfig = importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode();
            }
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
            titleField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey);
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWMCField())) {
            titleField = singleFieldMap.get(fMTable.getDWMCField());
        }
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isNotEmpty((String)autoCodeCoinfig.getAppendCodeZb()) && fieldMap.containsKey(autoCodeCoinfig.getAppendCodeZb())) {
            autoAppendField = fieldMap.get(autoCodeCoinfig.getAppendCodeZb());
        }
        RepeatImportParam jioRepeatParam = importContext.getJioRepeatParam();
        RepeatImportParam jioRepeatResult = importContext.getJioRepeatResult();
        if (jioRepeatResult == null) {
            jioRepeatResult = new RepeatImportParam();
            jioRepeatResult.setNetCorpCount(importContext.getNetCorpCount());
        }
        Map repeatNodes = importContext.getRepeatEntityNodes();
        RepeatImportParam jioSelectParam = importContext.getJioSelectImportParam();
        RepeatImportParam jioSelectResult = importContext.getJioSelectImportResult();
        if (jioSelectResult == null) {
            jioSelectResult = new RepeatImportParam();
        }
        Map jioSelectNodes = importContext.getSelectEntityNodes();
        int selectImportType = 0;
        if (jioSelectNodes.size() > 0 && importContext.isNeedSelectImport()) {
            boolean findImport = false;
            boolean findNotImport = false;
            for (RepeatEntityNode selectNode : jioSelectNodes.values()) {
                if (selectNode.getRepeatMode() == 0) {
                    findNotImport = true;
                    continue;
                }
                if (selectNode.getRepeatMode() == 1) {
                    findImport = true;
                    continue;
                }
                if (selectNode.getRepeatMode() != 2) continue;
                findImport = true;
            }
            if (findImport && findNotImport) {
                selectImportType = 3;
            } else if (findNotImport) {
                selectImportType = 2;
            } else if (findImport) {
                selectImportType = 1;
            }
        }
        SyntaxExcuteMapEntityImpl expGeter = new SyntaxExcuteMapEntityImpl();
        if (corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpresion)) {
            importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u542f\u7528\u8868\u8fbe\u5f0f\u6a21\u5f0f\uff1a" + singleMapExpresion);
            if (!expGeter.buildExpression(singleMapExpresion, dbf.getTable().getColumns())) {
                corpMapUseExp = false;
                importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a" + singleMapExpresion + ",\u5b58\u5728\u8bed\u6cd5\u9519\u8bef\uff0c\u5173\u95ed\u542f\u8868\u8fbe\u5f0f\u6a21\u5f0f");
            }
        }
        importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1aDBF\u8bb0\u5f55\u6570\u4e3a" + dbf.getRecordCount());
        ArrayList<DataRow> importDataRows = new ArrayList<DataRow>();
        ArrayList<SingleDataRow> importSingleRows = new ArrayList<SingleDataRow>();
        HashSet<String> insertOrgCodesByMap = new HashSet<String>();
        DataEntityCache dbfEntityCache = new DataEntityCache();
        this.loadSingleRowFromDBF(importContext, dbf, dbfEntityCache, fMTable, singleZdmWithoutPeriodCodes, corpMapUseExp, corpMapUseRule, singleMapExpresion, (SyntaxExcuteMapEntity)expGeter, singleMapRuleFields, dbfZdmFjdMap, mapSingleFieldList, dbfZdmRowMap, importDataRows, dbfZdmAndRowMap, importSingleRows, errorZdms);
        List<CheckResultNode> errorLinkChainList = this.checkLinkChain(importContext, importSingleRows, fMTable != null && StringUtils.isNotEmpty((String)fMTable.getSJDMField()));
        if (!errorLinkChainList.isEmpty() && (isIncrement || isUpdate)) {
            for (CheckResultNode node : errorLinkChainList) {
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u73af\u94fe", "checkLinkChainError", node.getUnitCode(), node.getUnitTitle(), node.getUnitZdm());
                importContext.recordLog("FMDM", errorItem);
            }
        }
        Set<String> mergeZdms = this.getMergeZdms(importContext, dbfZdmFjdMap, netPeriodCode);
        boolean enableNrFormType = this.formTypeApplyService.enableNrFormTypeMgr();
        UnitNatureGetter unitNatureGetter = this.getUnitNatureGetter(importContext, enableNrFormType);
        boolean needSort = this.reTreeDbf(importContext, dbfEntityCache, unitNatureGetter, importSingleRows);
        SingleDataRow rootRow = null;
        if (importContext.isNeedSelectImport() && jioSelectParam == null) {
            dbfEntityCache.clear();
        } else if (importContext.getMapingCache().getMapConfig().getConfig().isImpByUnitAllCover()) {
            if (!this.checkRootRow(importContext, dbfEntityCache, netPeriodCode, entityId, importSingleRows, tranDimDic, deleteBatchOptDTO, corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList)) {
                uploadEntityZdmKeyMap.clear();
                dbfEntityCache.clear();
                importContext.setSingleCorpCount(importSingleRows.size());
                return;
            }
            if (dbfEntityCache.getRootEntitys().size() == 1) {
                DataEntityInfo rootEntity = (DataEntityInfo)dbfEntityCache.getRootEntitys().get(0);
                rootRow = (SingleDataRow)rootEntity.getEntintyRow();
            }
            dbfEntityCache.clear();
        } else {
            dbfEntityCache.clear();
        }
        Map fMDMSingleMaps = null;
        fMDMSingleMaps = importContext.getMapingCache().getSingleFieldMap() != null && importContext.getMapingCache().getSingleFieldMap().containsKey("FMDM") ? (Map)importContext.getMapingCache().getSingleFieldMap().get("FMDM") : new HashMap();
        boolean showConsole = importDataRows.size() <= 10;
        double addProgress = 0.0;
        if (importDataRows.size() > 0) {
            addProgress = importContext.getNextProgressLen() / (double)importDataRows.size();
        }
        int singlePeriodUnitCount = 0;
        Set singleCorps = importContext.getSingleCorpList();
        ArrayList<String> singleCorps2 = new ArrayList<String>();
        for (int i = 0; i < importSingleRows.size(); ++i) {
            SingleDataError errorItem;
            String zdmCode;
            String zdmTitle;
            SingleDataError errorItem2;
            String zdmTitle2;
            SingleDataRow singleRow = (SingleDataRow)importSingleRows.get(i);
            DataRow dbfRow = singleRow.getDataRow();
            String dbfPeriod = "";
            if (StringUtils.isNotEmpty((String)fMTable.getPeriodField()) && StringUtils.isNotEmpty((String)(dbfPeriod = singleRow.getPeriod())) && !dbfPeriod.equalsIgnoreCase(importContext.getCurrentPeriod())) continue;
            FMDMDataDTO entityRow1 = new FMDMDataDTO();
            String zdm = singleRow.getZdm();
            if (StringUtils.isEmpty((String)zdm)) continue;
            ++singlePeriodUnitCount;
            String zdmWithOutPeriod2 = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(zdm);
            if (!singleCorps.contains(zdmWithOutPeriod2)) {
                singleCorps.add(zdmWithOutPeriod2);
            }
            singleCorps2.add(zdmWithOutPeriod2);
            if (!this.judgeNeedImportBySelect(importContext, zdmWithOutPeriod2, selectImportType, jioSelectNodes)) continue;
            String tempQYDM = null;
            if (repeatNodes.containsKey(zdm)) {
                RepeatEntityNode repeatNode = (RepeatEntityNode)repeatNodes.get(zdm);
                if (repeatNode.getRepeatMode() == 0) {
                    if (!jioRepeatParam.isRepeatByFile()) continue;
                    String zdmCode2 = zdm;
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        zdmCode2 = singleRow.getDwdm();
                        if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                            zdmCode2 = zdmCode2 + singleRow.getBblx();
                        }
                    }
                    String zdmTitle3 = "";
                    if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                        zdmTitle3 = singleRow.getDwmc();
                    }
                    SingleDataError errorItem3 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u6587\u4ef6\u95f4\u91cd\u7801\u4e0d\u5bfc\u5165", "fileRepeatNotUpdate", null, zdmTitle3, zdmCode2);
                    importContext.recordLog("FMDM", errorItem3);
                    importContext.info(logger, "\u6587\u4ef6\u95f4\u91cd\u7801\u4e0d\u5bfc\u5165:" + zdm + "");
                    continue;
                }
                if (repeatNode.getRepeatMode() != 1) {
                    if (repeatNode.getRepeatMode() != 2) continue;
                    tempQYDM = repeatNode.getTempQYDM();
                    if (StringUtils.isEmpty((String)tempQYDM)) {
                        importContext.info(logger, "\u91cd\u7801\u5355\u4f4d\u7684\u4e34\u65f6\u7801\u9519\u8bef:" + zdm + ",\u4e34\u65f6\u7801\uff1a" + tempQYDM);
                        continue;
                    }
                }
            }
            if (null != asyncTaskMonitor) {
                importContext.addProgress(addProgress);
                if (i % 10 == 0) {
                    asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\uff0c\u91cd\u7801\u68c0\u67e5");
                }
            }
            String zdmWithOutPeriod = "";
            String zdmWithoutBBLXPeriod = "";
            String zdmRuleCode = "";
            if (StringUtils.isNotEmpty((String)singleRow.getMapFieldValue())) {
                zdmRuleCode = singleRow.getMapFieldValue();
                if (StringUtils.isNotEmpty(tempQYDM) && corpMapUseRule) {
                    String singleMapFieldValue = "";
                    for (String code : singleMapRuleFields.keySet()) {
                        if (code.equalsIgnoreCase(fMTable.getDWDMField())) {
                            singleMapFieldValue = singleMapFieldValue + tempQYDM;
                            continue;
                        }
                        singleMapFieldValue = singleMapFieldValue + (String)singleRow.getFieldValues().get(code);
                    }
                    zdmRuleCode = singleMapFieldValue;
                }
            }
            String singlePeriodInZdm = "";
            if (singleZdmWithoutPeriodCodes.size() > 0) {
                for (String code : singleZdmWithoutPeriodCodes) {
                    if (StringUtils.isNotEmpty((String)tempQYDM) && StringUtils.isNotEmpty((String)code) && code.equalsIgnoreCase(fMTable.getDWDMField())) {
                        zdmWithOutPeriod = zdmWithOutPeriod + tempQYDM;
                        zdmWithoutBBLXPeriod = zdmWithoutBBLXPeriod + tempQYDM;
                        continue;
                    }
                    String dbfFieldValue = (String)singleRow.getFieldValues().get(code);
                    if (mapSingleFieldList.containsKey(code)) {
                        SingleFileFieldInfo singleField = mapSingleFieldList.get(code);
                        for (int len = singleField.getFieldSize() - dbfFieldValue.length(); len > 0; --len) {
                            dbfFieldValue = dbfFieldValue + " ";
                        }
                    }
                    if (StringUtils.isNotEmpty((String)code) && code.equalsIgnoreCase(fMTable.getBBLXField())) {
                        zdmWithOutPeriod = zdmWithOutPeriod + dbfFieldValue;
                        continue;
                    }
                    zdmWithOutPeriod = zdmWithOutPeriod + dbfFieldValue;
                    zdmWithoutBBLXPeriod = zdmWithoutBBLXPeriod + dbfFieldValue;
                }
            }
            if (StringUtils.isEmpty((String)zdmWithOutPeriod)) {
                String fieldCode;
                if (StringUtils.isNotEmpty((String)tempQYDM) && importContext.getEntityCache().getSingleQYDMIndex() >= 0) {
                    zdm = importContext.getEntityCache().getNewZdmByQydm(zdm, tempQYDM);
                }
                zdmWithOutPeriod = zdm;
                if (peirodLength > 0 && StringUtils.isNotEmpty((String)zdm) && zdm.length() > periodIndex) {
                    singlePeriodInZdm = zdm.substring(periodIndex, periodIndex + peirodLength);
                    zdmWithOutPeriod = zdm.substring(0, periodIndex - 1) + zdm.substring(periodIndex + peirodLength - 1, zdm.length());
                }
                if (singleZdmWithoutPeriodCodes.size() > 0 && StringUtils.isNotEmpty((String)(fieldCode = singleZdmWithoutPeriodCodes.get(singleZdmWithoutPeriodCodes.size() - 1))) && fieldCode.equalsIgnoreCase(fMTable.getBBLXField()) && StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                    zdmWithoutBBLXPeriod = zdmWithOutPeriod.substring(0, zdmWithOutPeriod.length() - 1);
                }
            }
            if (StringUtils.isEmpty((String)singlePeriodInZdm)) {
                singlePeriodInZdm = dbfPeriod;
            }
            if (importContext.isNeedSelectImport() && jioSelectParam == null) {
                RepeatEntityNode selectNode = new RepeatEntityNode();
                selectNode.setSingleZdm(zdm);
                selectNode.setSingleCode(zdmWithOutPeriod);
                selectNode.setSinglePeriod(singlePeriodInZdm);
                selectNode.setNetPeriod(netPeriodCode);
                if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    selectNode.setSingleQYDM(singleRow.getDwdm());
                }
                if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                    selectNode.setSingleBBLX(singleRow.getBblx());
                }
                if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                    selectNode.setSingleTitle(singleRow.getDwmc());
                }
                selectNode.setSingleParent(singleRow.getFjd());
                jioSelectResult.getEntityNodes().add(selectNode);
                if (jioSelectResult.getFormNodes().size() != 0) continue;
                jioSelectResult.getFormNodes().addAll(this.getReaptFormNodes(importContext));
                continue;
            }
            String entityJcm = "";
            if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
                entityJcm = this.getJcmbyZdm(importContext, zdm, zdmWithOutPeriod, dbfZdmFjdMap, autoCodeCoinfig);
                if (StringUtils.isEmpty((String)entityJcm)) {
                    String zdmCode3 = zdm;
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        zdmCode3 = singleRow.getDwdm();
                        if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                            zdmCode3 = zdmCode3 + singleRow.getBblx();
                        }
                    }
                    SingleDataError errorItem4 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u52a0\u957f\u7801\u914d\u7f6e\u4e0d\u5bfc\u5165", "notJcm", "", zdmWithOutPeriod, zdmCode3);
                    importContext.recordLog("FMDM", errorItem4);
                    importContext.info(logger, "\u65e0\u52a0\u957f\u7801\u914d\u7f6e\u4e0d\u5bfc\u5165 " + zdmWithOutPeriod);
                    continue;
                }
                entityJcm = this.singleTransService.toUpper(importContext, entityJcm);
            }
            String singleMapExpValue = "";
            if (corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpresion) && dbfZdmAndRowMap.containsKey(zdm)) {
                singleMapExpValue = ((SingleDataRow)dbfZdmAndRowMap.get(zdm)).getMapExpValue();
            }
            entityInfo = this.findEntity(importContext, zdmWithOutPeriod, zdmRuleCode, singleMapExpValue, entityJcm, singleRow.getZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
            String entityAutoExCode = this.singleTransService.toUpper(importContext, zdmRuleCode) + entityJcm;
            String singleMapCode = "";
            if (entityInfo != null && entityInfo.getFieldValues().containsKey("singleMapCode")) {
                singleMapCode = (String)entityInfo.getFieldValues().get("singleMapCode");
            }
            boolean isNewEntity = false;
            if (entityInfo == null && StringUtils.isNotEmpty((String)entityAutoExCode)) {
                if (entityAutoExCodeAndRowMapNew.containsKey(entityAutoExCode)) {
                    zdmTitle2 = "";
                    if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                        zdmTitle2 = singleRow.getDwmc();
                    }
                    errorItem2 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u91cd\u7801\u5355\u4f4d", "repeatCode", zdmWithOutPeriod, zdmTitle2, zdm);
                    importContext.recordLog("FMDM", errorItem2);
                    importContext.info(logger, "\u91cd\u590d\u5355\u4f4d\uff1a" + entityAutoExCode + ",\u4e3b\u4ee3\u7801\uff1a" + zdm + " " + zdmTitle2);
                    continue;
                }
            } else if (entityInfo != null && StringUtils.isNotEmpty((String)entityAutoExCode) && entityAutoExCodeAndRowMapUpdate.containsKey(entityAutoExCode)) {
                zdmTitle2 = "";
                if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                    zdmTitle2 = singleRow.getDwmc();
                }
                errorItem2 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u91cd\u7801\u5355\u4f4d", "repeatCode", zdmWithOutPeriod, zdmTitle2, zdm);
                importContext.recordLog("FMDM", errorItem2);
                importContext.info(logger, "\u91cd\u590d\u5355\u4f4d\uff1a" + entityAutoExCode + ",\u4e3b\u4ee3\u7801\uff1a" + zdm + " " + zdmTitle2);
                continue;
            }
            String entityTitle = "";
            if (entityInfo != null) {
                SingleDataError errorItem5;
                String zdmKey = entityInfo.getEntityKey();
                zdmTitle = "";
                zdmCode = "";
                DimensionValueSet rowDimensionValueSet = new DimensionValueSet();
                rowDimensionValueSet.setValue("DATATIME", (Object)netPeriodCode);
                rowDimensionValueSet.setValue(importContext.getEntityCompanyType(), (Object)zdmKey);
                this.setOtherDimValue(importContext, rowDimensionValueSet);
                this.setSingleDimValue(importContext, rowDimensionValueSet, "");
                if (tranDimDic.containsKey(zdmKey)) {
                    if (rowDimensionValueSet.hasValue("MD_CURRENCY")) {
                        String currency = (String)((DimensionValueSet)tranDimDic.get(zdmKey)).getValue("MD_CURRENCY");
                        rowDimensionValueSet.setValue("MD_CURRENCY", (Object)currency);
                    }
                    if (importContext.getDimEntityCache().getEntitySingleDims().size() > 0) {
                        for (String dimName : importContext.getDimEntityCache().getEntitySingleDims()) {
                            String dimValue = (String)((DimensionValueSet)tranDimDic.get(zdmKey)).getValue(dimName);
                            rowDimensionValueSet.setValue(dimName, (Object)dimValue);
                        }
                    }
                }
                entityRow1.setDimensionValueSet(rowDimensionValueSet);
                entityRow1.setValue("CODE", (Object)zdmKey);
                entityRow1.setFormSchemeKey(importContext.getFormSchemeKey());
                String entityParentKey = "";
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                if (entityInfo != null && StringUtils.isNotEmpty((String)entityInfo.getEntityTitle())) {
                    zdmTitle = entityInfo.getEntityTitle();
                    zdmCode = entityInfo.getEntityCode();
                    entityParentKey = entityInfo.getEntityParentKey();
                }
                if (importContext.isNeedCheckRepeat() && (jioRepeatParam == null || jioRepeatParam.isRepeatByFile())) {
                    RepeatEntityNode repeatNode = new RepeatEntityNode();
                    repeatNode.setNetBBLX("");
                    repeatNode.setNetCode(zdmKey);
                    repeatNode.setNetTitle(zdmTitle);
                    repeatNode.setNetQYDM("");
                    repeatNode.setNetParent(entityParentKey);
                    repeatNode.setSingleZdm(zdm);
                    repeatNode.setSingleCode(zdmWithOutPeriod);
                    repeatNode.setSinglePeriod(singlePeriodInZdm);
                    repeatNode.setNetPeriod(netPeriodCode);
                    repeatNode.setSingleMapCode(singleMapCode);
                    repeatNode.setNetMapCode(singleMapCode);
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        repeatNode.setSingleQYDM(singleRow.getDwdm());
                    }
                    if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                        repeatNode.setSingleBBLX(singleRow.getBblx());
                    }
                    if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                        repeatNode.setSingleTitle(singleRow.getDwmc());
                    }
                    repeatNode.setSingleParent(singleRow.getFjd());
                    jioRepeatResult.getEntityNodes().add(repeatNode);
                    if (jioRepeatResult.getFormNodes().size() == 0) {
                        jioRepeatResult.getFormNodes().addAll(this.getReaptFormNodes(importContext));
                    }
                    if (importContext.getRepeatImportType() == 2 || importContext.getRepeatImportType() == 0) {
                        if (importContext.getRepeatImportType() != 0) continue;
                        SingleDataError errorItem6 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u91cd\u7801\u4e0d\u5bfc\u5165", "repeatNotUpdate", zdmKey, zdmTitle, zdmCode);
                        importContext.recordLog("FMDM", errorItem6);
                        importContext.info(logger, "\u91cd\u7801\u4e0d\u5bfc\u5165  " + zdmKey + " " + zdmTitle);
                        continue;
                    }
                }
                if (skipUnitKeys.size() > 0 && skipUnitKeys.contains(zdmKey)) {
                    errorItem5 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u5bfc\u5165", "notMap", zdmKey, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem5);
                    importContext.info(logger, "\u4e0d\u5141\u8bb8\u5bfc\u5165  " + zdmKey + " " + zdmTitle);
                    continue;
                }
                if (this.entityMergeServie != null && mergeZdms.size() > 0 && mergeZdms.contains(zdm)) {
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e\uff0c\u88ab\u5408\u5e76\u5355\u4f4d\uff0c\u4e0d\u9700\u8981\u66f4\u65b0\uff0c" + zdm + " " + zdmTitle);
                    continue;
                }
                matchNetCorps.put(zdmKey, entityInfo);
                if (importContext.isNeedCheckCorpTree() && null != fMTable && StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    String fjdZdm = singleRow.getFjd();
                    String fjdCode = this.getParentCoddeByFJD(importContext, fjdZdm, repeatNodes, peirodLength, periodIndex, zmdLength, dbfZdmFjdMap, autoCodeCoinfig, entityCodeKeyMap, entityZdmAndKeyMap, zdmWithoutPeriodTempMap, tempFJDRows, zdmTempMap, dbfZdmAndRowMap, entityRow1, corpMapUseExp, singleMapingUnitList);
                    boolean isSameFJD = false;
                    if (StringUtils.isEmpty((String)entityInfo.getEntityParentKey()) && StringUtils.isEmpty((String)fjdCode)) {
                        isSameFJD = true;
                    } else if ("-".equalsIgnoreCase(entityInfo.getEntityParentKey()) && StringUtils.isEmpty((String)fjdCode)) {
                        isSameFJD = true;
                    } else if (StringUtils.isNotEmpty((String)entityInfo.getEntityParentKey()) && entityInfo.getEntityParentKey().equalsIgnoreCase(fjdCode)) {
                        isSameFJD = true;
                    }
                    if (!isSameFJD) {
                        errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u7236\u8282\u70b9\u4e0e\u7cfb\u7edf\u7236\u8282\u70b9\u4e0d\u4e00\u81f4\u7684\u5355\u4f4d", "checkTreeNotFJD", zdmWithOutPeriod, zdmTitle, zdmCode);
                        importContext.recordLog("FMDM", errorItem);
                        importContext.info(logger, zdmWithOutPeriod + " " + zdmTitle + "\uff0c JIO\u6587\u4ef6\u4e2d\u7236\u8282\u70b9\u4e0e\u7cfb\u7edf\u7236\u8282\u70b9\u4e0d\u4e00\u81f4");
                        errorTreeZdms.add(zdmCode);
                        continue;
                    }
                }
                uploadEntityZdmKeyMap.put(zdm, zdmKey);
                entityAutoExCodeAndRowMapUpdate.put(entityAutoExCode, entityRow1);
                if (!isUpdate) {
                    errorItem5 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u4fee\u6539\u5355\u4f4d\u4fe1\u606f", "notUpdate", zdmKey, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem5);
                    importContext.info(logger, zdmWithOutPeriod + " " + zdmTitle + "\uff0c \u4e0d\u5141\u8bb8\u4fee\u6539\u5355\u4f4d\u4fe1\u606f");
                }
                if (null != titleField) {
                    String dwmcField = null;
                    if (null != fMTable) {
                        dwmcField = fMTable.getDWMCField();
                    }
                    entityTitle = StringUtils.isNotEmpty(dwmcField) ? singleRow.getDwmc() : zdmTitle;
                    entityInfo.setEntityTitle(entityTitle);
                    if (!ignoreUpdateFields.contains("NAME")) {
                        entityRow1.setValue("NAME", (Object)entityTitle);
                    }
                }
                if (!ignoreUpdateFields.contains("NAME")) {
                    entityRow1.setValue("NAME", (Object)zdmTitle);
                }
                if (importContext.getMapingCache().getMapConfig().getConfig().isImpByUnitAllCover() && needSort && rootRow != singleRow) {
                    entityRow1.setValue("ORDINAL", (Object)new BigDecimal(OrderGenerator.newOrderID()));
                }
                updateBatchOptDTO.getFmdmList().add(entityRow1);
                if (showConsole) {
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u66f4\u65b0" + zdmKey + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                }
            } else {
                String rowCurrency;
                isNewEntity = true;
                if (this.zdmIsMergedAndDoMerge(importContext, dbf, singlePeriodInZdm, dbfRow, netPeriodCode, mergeZdms)) continue;
                String entityCode = "";
                zdmTitle = zdm;
                zdmCode = zdm;
                if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    zdmCode = singleRow.getDwdm();
                    if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                        zdmCode = zdmCode + singleRow.getBblx();
                    }
                }
                if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                    zdmTitle = singleRow.getDwmc();
                }
                if (importContext.isNeedCheckCorpTree()) {
                    SingleDataError errorItem7 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u7cfb\u7edf\u4e0d\u5b58\u5728\u7684\u5355\u4f4d", "checkTreeNotAdd", zdmWithOutPeriod, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem7);
                    importContext.info(logger, zdmWithOutPeriod + " " + zdmTitle + "\uff0c \u7cfb\u7edf\u4e0d\u5b58\u5728");
                    errorTreeZdms.add(zdmCode);
                    continue;
                }
                if (!isIncrement) {
                    SingleDataError errorItem8 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d", "notAdd", zdmWithOutPeriod, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem8);
                    importContext.info(logger, zdmWithOutPeriod + " " + zdmTitle + "\uff0c \u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d");
                    continue;
                }
                if (!corpMapUseRule) {
                    boolean canImport = true;
                    if (singleMapingUnitList.containsKey(zdmWithOutPeriod)) {
                        UnitCustomMapping unitMapping = (UnitCustomMapping)singleMapingUnitList.get(zdmWithOutPeriod);
                        if (StringUtil.isEmpty((String)unitMapping.getNetUnitCode())) {
                            canImport = false;
                        }
                    } else {
                        canImport = false;
                    }
                    if (!canImport) {
                        SingleDataError errorItem9 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d", "notAdd", zdmWithOutPeriod, zdmTitle, zdmCode);
                        importContext.recordLog("FMDM", errorItem9);
                        importContext.info(logger, zdmWithOutPeriod + " " + zdmTitle + "\uff0c \u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d");
                        continue;
                    }
                }
                String newZdmKeyID = null;
                if (zdmWithoutPeriodTempMap.containsKey(zdmWithOutPeriod)) {
                    newZdmKeyID = (String)zdmWithoutPeriodTempMap.get(zdmWithOutPeriod);
                    tempFJDZdms.put(newZdmKeyID, zdm);
                } else if (zdmTempMap.containsKey(zdm)) {
                    newZdmKeyID = (String)zdmTempMap.get(zdm);
                    tempFJDZdms.put(newZdmKeyID, zdm);
                } else {
                    newZdmKeyID = this.singleTransService.toUpper(importContext, UUID.randomUUID().toString());
                }
                if (showConsole) {
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u65b0\u589e" + newZdmKeyID.toString() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                }
                String newZdmKey = zdmWithOutPeriod;
                DimensionValueSet rowDimensionValueSet = new DimensionValueSet();
                rowDimensionValueSet.setValue("DATATIME", (Object)netPeriodCode);
                this.setOtherDimValue(importContext, rowDimensionValueSet);
                this.setSingleDimValue(importContext, rowDimensionValueSet, "");
                if (rowDimensionValueSet.hasValue("MD_CURRENCY") && ("PROVIDER_BASECURRENCY".equalsIgnoreCase(rowCurrency = (String)rowDimensionValueSet.getValue("MD_CURRENCY")) || "PROVIDER_PBASECURRENCY".equalsIgnoreCase(rowCurrency))) {
                    rowDimensionValueSet.setValue("MD_CURRENCY", null);
                }
                entityRow1.setDimensionValueSet(rowDimensionValueSet);
                entityRow1.setFormSchemeKey(importContext.getFormSchemeKey());
                entityTitle = fMTable != null && StringUtils.isNotEmpty((String)fMTable.getDWMCField()) ? singleRow.getDwmc() : OrderGenerator.newOrder();
                String dwdmField = null;
                if (null != fMTable) {
                    dwdmField = fMTable.getDWDMField();
                }
                if (corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpValue)) {
                    entityCode = singleMapExpValue;
                    newZdmKey = singleMapExpValue;
                } else if (corpMapUseRule) {
                    entityCode = zdmRuleCode;
                    newZdmKey = zdmRuleCode;
                } else {
                    entityCode = StringUtils.isNotEmpty((String)zdmWithOutPeriod) ? zdmWithOutPeriod : (StringUtils.isNotEmpty((String)dwdmField) ? singleRow.getDwdm() : this.singleTransService.toUpper(importContext, OrderGenerator.newOrder()));
                }
                newZdmKey = this.singleTransService.toUpper(importContext, newZdmKey);
                String entityOrgCode = entityCode = this.singleTransService.toUpper(importContext, entityCode);
                if (enableNrFormType) {
                    String bblxCode = null;
                    if (null != fMTable && StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                        bblxCode = singleRow.getBblx();
                    }
                    if (StringUtils.isNotEmpty(bblxCode) && null != fMTable && StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        String fjdZdm = singleRow.getFjd();
                        String fjdCode = this.getParentCoddeByFJD(importContext, fjdZdm, repeatNodes, peirodLength, periodIndex, zmdLength, dbfZdmFjdMap, autoCodeCoinfig, entityCodeKeyMap, entityZdmAndKeyMap, zdmWithoutPeriodTempMap, tempFJDRows, zdmTempMap, dbfZdmAndRowMap, entityRow1, corpMapUseExp, singleMapingUnitList);
                        String qydm = singleRow.getDwdm();
                        if (StringUtils.isNotEmpty((String)zdmWithoutBBLXPeriod)) {
                            entityOrgCode = this.singleTransService.toUpper(importContext, zdmWithoutBBLXPeriod);
                        } else if (StringUtils.isNotEmpty((String)qydm)) {
                            entityOrgCode = qydm.toString();
                        }
                        String entityCode2 = this.getBJCEEntityCode(entityCode, qydm, bblxCode, zdmWithoutBBLXPeriod, fjdZdm, fjdCode, dbfRow, fMTable, dbf, dbfZdmRowMap, unitNatureGetter);
                        if (StringUtils.isNotEmpty((String)entityCode2)) {
                            entityCode = entityCode2.toString();
                        }
                    }
                }
                if (singleMapingUnitList.containsKey(entityCode)) {
                    UnitCustomMapping unitMapping = (UnitCustomMapping)singleMapingUnitList.get(entityCode);
                    entityOrgCode = entityCode = this.singleTransService.toUpper(importContext, unitMapping.getNetUnitCode());
                    insertOrgCodesByMap.add(entityCode);
                    if (StringUtils.isNotEmpty((String)unitMapping.getNetUnitCode())) {
                        newZdmKey = this.singleTransService.toUpper(importContext, unitMapping.getNetUnitCode());
                    } else if (StringUtils.isNotEmpty((String)unitMapping.getNetUnitKey())) {
                        newZdmKey = this.singleTransService.toUpper(importContext, unitMapping.getNetUnitKey());
                    }
                }
                entityCodeKeyMap.put(zdmWithOutPeriod, newZdmKey);
                entityKeyCodeMap.put(newZdmKey, zdmWithOutPeriod);
                entityKeyZdmMap.put(newZdmKey, zdm);
                entityZdmAndKeyMap.put(zdm, newZdmKey);
                insertEntityZdmKeyMap.put(zdm, newZdmKey);
                uploadEntityZdmKeyMap.put(zdm, newZdmKey);
                entityAutoExCodeAndRowMapNew.put(entityAutoExCode, entityRow1);
                entityRow1.setValue("CODE", (Object)entityCode);
                entityRow1.setValue("ORGCODE", (Object)entityOrgCode);
                if (null != titleField) {
                    entityRow1.setValue(titleField.getCode().toUpperCase(), (Object)entityTitle);
                    entityRow1.setValue("NAME", (Object)entityTitle);
                }
                if (repeatDatas.containsKey(entityCode)) {
                    errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u5355\u4f4d\u91cd\u7801\u4e0d\u5141\u8bb8\u65b0\u589e", "repeatCode", zdmWithOutPeriod, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    importContext.info(logger, "\u91cd\u590d\u5355\u4f4d\uff1a" + entityCode + ",\u4e3b\u4ee3\u7801\uff1a" + zdm + "");
                    continue;
                }
                repeatDatas.put(entityCode, entityRow1);
                addBatchOptDTO.getFmdmList().add(entityRow1);
                PeriodWrapper periodWrapper = new PeriodWrapper(netPeriodCode);
                Date beginDate = null;
                String[] periodList = PeriodUtil.getTimesArr((PeriodWrapper)periodWrapper);
                if (periodList != null && periodList.length == 2) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    beginDate = simpleDateFormat.parse(periodList[0]);
                }
                entityRow1.setValue("ORDINAL", (Object)new BigDecimal(OrderGenerator.newOrderID()));
                if (null != beginDate) {
                    entityRow1.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey).getCode().toUpperCase(), (Object)beginDate);
                } else {
                    entityRow1.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey).getCode().toUpperCase(), (Object)Consts.DATE_VERSION_MIN_VALUE);
                }
                entityRow1.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_INVALIDTIME.fieldKey).getCode().toUpperCase(), (Object)Consts.DATE_VERSION_MAX_VALUE);
                if (autoAppendField != null) {
                    entityRow1.setValue(autoAppendField.getCode().toUpperCase(), (Object)entityJcm);
                }
                entityInfo = new DataEntityInfo();
                entityInfo.setEntityTitle(entityTitle);
                entityInfo.setEntityCode(entityCode);
                entityInfo.setEntityKey(newZdmKey);
                entityInfo.setEntityExCode(zdmWithOutPeriod);
                entityInfo.setEntityExpCode(singleMapExpValue);
                entityInfo.setSingleZdm(zdm);
                entityInfo.setIsUnitMap(false);
                entityInfo.setExpSingleZdm(zdm);
                entityInfo.setEntityAppendCode(entityJcm);
                importContext.getEntityCache().addEntity(entityInfo);
            }
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            this.setFieldValueFromDBf(importContext, dbf, dbfRow, singleFieldMap, fMDMSingleMaps, entityRow1, isNewEntity, tempQYDM, ignoreUpdateFields, fMTable);
            String fjdZdm = dbfRow.getValueString("SYS_FJD");
            if (StringUtils.isNotEmpty((String)fjdZdm) && (isNewEntity || !ignoreUpdateFields.contains("PARENTCODE"))) {
                String fjdCode = this.getParentCoddeByFJD(importContext, fjdZdm, repeatNodes, peirodLength, periodIndex, zmdLength, dbfZdmFjdMap, autoCodeCoinfig, entityCodeKeyMap, entityZdmAndKeyMap, zdmWithoutPeriodTempMap, tempFJDRows, zdmTempMap, dbfZdmAndRowMap, entityRow1, corpMapUseExp, singleMapingUnitList);
                entityRow1.setValue("PARENTCODE", (Object)fjdCode);
                entityInfo.setEntityParentKey(fjdCode);
                if (importContext.getMapingCache().getMapConfig().getConfig().isImpByUnitAllCover() && singleRow.getFieldValues().containsKey("IMPORT_NET_PARENTKEY")) {
                    String fjdCode2 = (String)singleRow.getFieldValues().get("IMPORT_NET_PARENTKEY");
                    entityRow1.setValue("PARENTCODE", (Object)fjdCode2);
                    entityInfo.setEntityParentKey(fjdCode2);
                }
            }
            if (dbf.isHasLoadAllRec()) continue;
            dbf.clearDataRow(dbfRow);
        }
        importContext.setSingleCorpCount(singleCorps2.size());
        if (importContext.getSingleMergeZdms() != null && importContext.getSingleMergeZdms().size() > 0) {
            importContext.setSingleCorpCount(singleCorps2.size() - importContext.getSingleMergeZdms().size());
        }
        for (Map.Entry entry : tempFJDRows.entrySet()) {
            String zdmKey = (String)entry.getKey();
            if (!StringUtils.isNotEmpty((String)zdmKey) || tempFJDZdms.containsKey(zdmKey)) continue;
            List list = (List)entry.getValue();
            for (FMDMDataDTO subEntity : list) {
                AbstractData subParentData = subEntity.getValue("PARENTCODE");
                if (subParentData == null || !zdmKey.equalsIgnoreCase(subParentData.getAsString())) continue;
                subEntity.setValue("PARENTCODE", (Object)"");
                AbstractData subEntityData = subEntity.getValue("CODE");
                if (subEntityData == null || !importContext.getEntityCache().getEntityKeyFinder().containsKey(subEntityData.getAsString())) continue;
                DataEntityInfo entityInfo2 = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(subEntityData.getAsString());
                entityInfo2.setEntityParentKey("");
            }
        }
        if (jioSelectResult.getEntityNodes().size() > 0) {
            importContext.setJioSelectImportResult(jioSelectResult);
        } else if (jioRepeatResult.getEntityNodes().size() > 0 && importContext.getRepeatImportType() == 2) {
            importContext.setJioRepeatResult(jioRepeatResult);
        } else if (importContext.getDataOption().isUploadEntityData() && (jioRepeatResult.getEntityNodes().size() == 0 || importContext.getRepeatImportType() == 1)) {
            if (!errorZdms.isEmpty()) {
                uploadEntityZdmKeyMap.clear();
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u4e3b\u4ee3\u7801\u4e0e\u6784\u6210\u5b57\u6bb5\u4e0d\u4e00\u81f4\uff0c\u4e0d\u5bfc\u5165\u6570\u636e\uff0c\u5355\u4f4d\u6570\uff1a" + errorZdms.size());
                return;
            }
            if (!errorLinkChainList.isEmpty() && (isIncrement || isUpdate)) {
                uploadEntityZdmKeyMap.clear();
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u73af\u94fe\uff0c\u4e0d\u5bfc\u5165\u6570\u636e\uff0c\u5355\u4f4d\u6570\uff1a" + errorLinkChainList.size());
                return;
            }
            if (importContext.isNeedCheckCorpTree()) {
                for (DataEntityInfo entityInfo3 : netCorpList) {
                    if (matchNetCorps.containsKey(entityInfo3.getEntityKey())) continue;
                    SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u4e0d\u5b58\u5728\u7684\u5355\u4f4d", "checkTreeNotExist", entityInfo3.getEntityKey(), entityInfo3.getEntityTitle(), entityInfo3.getEntityKey());
                    importContext.recordLog("FMDM", errorItem);
                    importContext.info(logger, entityInfo3.getEntityKey() + " " + entityInfo3.getEntityTitle() + "\uff0c JIO\u6587\u4ef6\u4e2d\u4e0d\u5b58\u5728\u7684\u5355\u4f4d");
                    errorTreeZdms.add(entityInfo3.getEntityKey());
                }
                if (!errorTreeZdms.isEmpty()) {
                    uploadEntityZdmKeyMap.clear();
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6811\u5f62\u6bd4\u5bf9\u5f02\u5e38\uff0c\u4e0d\u5bfc\u5165\u6570\u636e\uff0c\u5355\u4f4d\u6570\uff1a" + errorTreeZdms.size());
                    return;
                }
            }
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\uff0c\u6570\u636e\u66f4\u65b0");
            }
            this.filterDatas(importContext, updateBatchOptDTO, netPeriodCode, entityKeyCodeMap, uploadEntityZdmKeyMap);
            this.filterAddDatas(importContext, addBatchOptDTO, updateBatchOptDTO, netPeriodCode, entityKeyCodeMap, uploadEntityZdmKeyMap);
            this.commitDatas(importContext, addBatchOptDTO, updateBatchOptDTO, deleteBatchOptDTO, isIncrement, isUpdate, mergeZdms, insertOrgCodesByMap);
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\uff0c\u6570\u636e\u66f4\u65b0");
            }
        }
    }

    private DataEntityInfo findEntity(TaskDataContext importContext, String zdmWithOutPeriod, String zdmRuleCode, String singleMapExpValue, String entityJcm, String zdm, boolean corpMapUseExp, Map<String, DataEntityInfo> entityExpCodeAndEntiyInfoMap, Map<String, DataEntityInfo> entityAutoExCodeAndEntiyInfos, Map<String, UnitCustomMapping> singleMapingUnitList) {
        String entityAutoExCode = this.singleTransService.toUpper(importContext, zdmRuleCode) + entityJcm;
        String singleMapCode = "";
        DataEntityInfo entityInfo = null;
        if (!singleMapingUnitList.isEmpty()) {
            String zdmWithOutPeriodU = this.singleTransService.toUpper(importContext, singleMapExpValue);
            UnitCustomMapping unitMapping = null;
            if (StringUtils.isNotEmpty((String)zdmWithOutPeriod) && singleMapingUnitList.containsKey(zdmWithOutPeriod)) {
                unitMapping = singleMapingUnitList.get(zdmWithOutPeriod);
            } else if (corpMapUseExp && StringUtils.isNotEmpty((String)zdmWithOutPeriodU) && singleMapingUnitList.containsKey(zdmWithOutPeriodU)) {
                unitMapping = singleMapingUnitList.get(zdmWithOutPeriodU);
            }
            if (unitMapping != null) {
                if (StringUtil.isNotEmpty((String)unitMapping.getNetUnitKey())) {
                    entityInfo = importContext.getEntityCache().findEntityByCode(unitMapping.getNetUnitKey());
                } else if (StringUtil.isNotEmpty((String)unitMapping.getNetUnitCode())) {
                    entityInfo = importContext.getEntityCache().findEntityByOrgCode(unitMapping.getNetUnitCode());
                }
            }
        }
        if (entityInfo == null) {
            if (StringUtils.isEmpty((String)entityJcm) && corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpValue)) {
                String singleMapExpValue2 = this.singleTransService.toUpper(importContext, singleMapExpValue);
                if (entityExpCodeAndEntiyInfoMap.containsKey(singleMapExpValue2)) {
                    entityInfo = entityExpCodeAndEntiyInfoMap.get(singleMapExpValue2);
                    singleMapCode = singleMapExpValue2;
                } else if (entityExpCodeAndEntiyInfoMap.containsKey(singleMapExpValue)) {
                    entityInfo = entityExpCodeAndEntiyInfoMap.get(singleMapExpValue);
                    singleMapCode = singleMapExpValue;
                }
                entityAutoExCode = singleMapExpValue + entityJcm;
            } else if (StringUtils.isNotEmpty((String)zdmRuleCode) && StringUtils.isNotEmpty((String)entityAutoExCode)) {
                String entityAutoExCode2 = entityAutoExCode.trim();
                String entityAutoExCode3 = this.singleTransService.toUpper(importContext, entityAutoExCode);
                String entityAutoExCode4 = this.singleTransService.toUpper(importContext, entityAutoExCode2);
                if (StringUtils.isNotEmpty((String)entityAutoExCode3) && entityAutoExCodeAndEntiyInfos.containsKey(entityAutoExCode3)) {
                    entityInfo = entityAutoExCodeAndEntiyInfos.get(entityAutoExCode3);
                    singleMapCode = entityAutoExCode3;
                } else if (entityAutoExCodeAndEntiyInfos.containsKey(entityAutoExCode)) {
                    entityInfo = entityAutoExCodeAndEntiyInfos.get(entityAutoExCode);
                    singleMapCode = entityAutoExCode;
                } else if (StringUtils.isNotEmpty((String)entityAutoExCode4) && entityAutoExCodeAndEntiyInfos.containsKey(entityAutoExCode4)) {
                    entityInfo = entityAutoExCodeAndEntiyInfos.get(entityAutoExCode4);
                    singleMapCode = entityAutoExCode4;
                } else if (StringUtils.isNotEmpty((String)entityAutoExCode2) && entityAutoExCodeAndEntiyInfos.containsKey(entityAutoExCode2)) {
                    entityInfo = entityAutoExCodeAndEntiyInfos.get(entityAutoExCode2);
                    singleMapCode = entityAutoExCode2;
                }
            }
        }
        if (entityInfo != null && StringUtils.isNotEmpty((String)singleMapCode)) {
            entityInfo.getFieldValues().put("singleMapCode", singleMapCode);
        }
        return entityInfo;
    }

    private IEntityRow findEntityInOrg(TaskDataContext importContext, String zdmWithOutPeriod, String zdmRuleCode, String singleMapExpValue, String entityJcm, String zdm, boolean corpMapUseExp, Map<String, UnitCustomMapping> singleMapingUnitList, Map<String, IEntityRow> allNetRowsMap) {
        String entityAutoExCode = this.singleTransService.toUpper(importContext, zdmRuleCode) + entityJcm;
        String singleMapCode = "";
        IEntityRow entityInfo = null;
        boolean isNewEntity = false;
        if (!singleMapingUnitList.isEmpty()) {
            String zdmWithOutPeriodU = this.singleTransService.toUpper(importContext, singleMapExpValue);
            UnitCustomMapping unitMapping = null;
            if (StringUtils.isNotEmpty((String)zdmWithOutPeriod) && singleMapingUnitList.containsKey(zdmWithOutPeriod)) {
                unitMapping = singleMapingUnitList.get(zdmWithOutPeriod);
            } else if (corpMapUseExp && StringUtils.isNotEmpty((String)zdmWithOutPeriodU) && singleMapingUnitList.containsKey(zdmWithOutPeriodU)) {
                unitMapping = singleMapingUnitList.get(zdmWithOutPeriodU);
            }
            if (unitMapping != null && StringUtil.isNotEmpty((String)unitMapping.getNetUnitKey()) && allNetRowsMap.containsKey(unitMapping.getNetUnitKey())) {
                entityInfo = allNetRowsMap.get(unitMapping.getNetUnitKey());
            }
        }
        if (entityInfo == null) {
            if (StringUtils.isEmpty((String)entityJcm) && corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpValue)) {
                String singleMapExpValue2 = this.singleTransService.toUpper(importContext, singleMapExpValue);
                if (allNetRowsMap.containsKey(singleMapExpValue2)) {
                    entityInfo = allNetRowsMap.get(singleMapExpValue2);
                    singleMapCode = singleMapExpValue2;
                } else if (allNetRowsMap.containsKey(singleMapExpValue)) {
                    entityInfo = allNetRowsMap.get(singleMapExpValue);
                    singleMapCode = singleMapExpValue;
                }
                entityAutoExCode = singleMapExpValue + entityJcm;
            } else if (StringUtils.isNotEmpty((String)zdmRuleCode) && StringUtils.isNotEmpty((String)entityAutoExCode)) {
                String entityAutoExCode2 = entityAutoExCode.trim();
                String entityAutoExCode3 = this.singleTransService.toUpper(importContext, entityAutoExCode);
                String entityAutoExCode4 = this.singleTransService.toUpper(importContext, entityAutoExCode2);
                if (StringUtils.isNotEmpty((String)entityAutoExCode3) && allNetRowsMap.containsKey(entityAutoExCode3)) {
                    entityInfo = allNetRowsMap.get(entityAutoExCode3);
                    singleMapCode = entityAutoExCode3;
                } else if (allNetRowsMap.containsKey(entityAutoExCode)) {
                    entityInfo = allNetRowsMap.get(entityAutoExCode);
                    singleMapCode = entityAutoExCode;
                } else if (StringUtils.isNotEmpty((String)entityAutoExCode4) && allNetRowsMap.containsKey(entityAutoExCode4)) {
                    entityInfo = allNetRowsMap.get(entityAutoExCode4);
                    singleMapCode = entityAutoExCode4;
                } else if (StringUtils.isNotEmpty((String)entityAutoExCode2) && allNetRowsMap.containsKey(entityAutoExCode2)) {
                    entityInfo = allNetRowsMap.get(entityAutoExCode2);
                    singleMapCode = entityAutoExCode2;
                }
            }
        }
        return entityInfo;
    }

    private String getNewEntityKey(TaskDataContext importContext, String zdmWithOutPeriod, String zdmRuleCode, String singleMapExpValue, String entityJcm, String zdm, boolean corpMapUseExp, Map<String, UnitCustomMapping> singleMapingUnitList) {
        String entityAutoExCode = this.singleTransService.toUpper(importContext, zdmRuleCode) + entityJcm;
        String newNetEntityKey = "";
        if (!singleMapingUnitList.isEmpty()) {
            String zdmWithOutPeriodU = this.singleTransService.toUpper(importContext, singleMapExpValue);
            UnitCustomMapping unitMapping = null;
            if (StringUtils.isNotEmpty((String)zdmWithOutPeriod) && singleMapingUnitList.containsKey(zdmWithOutPeriod)) {
                unitMapping = singleMapingUnitList.get(zdmWithOutPeriod);
            } else if (corpMapUseExp && StringUtils.isNotEmpty((String)zdmWithOutPeriodU) && singleMapingUnitList.containsKey(zdmWithOutPeriodU)) {
                unitMapping = singleMapingUnitList.get(zdmWithOutPeriodU);
            }
            if (unitMapping != null) {
                newNetEntityKey = unitMapping.getNetUnitKey();
            }
        }
        if (StringUtils.isEmpty((String)newNetEntityKey)) {
            if (StringUtils.isEmpty((String)entityJcm) && corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpValue)) {
                String singleMapExpValue2 = this.singleTransService.toUpper(importContext, singleMapExpValue);
                newNetEntityKey = singleMapExpValue2 + entityJcm;
            } else if (StringUtils.isNotEmpty((String)zdmRuleCode) && StringUtils.isNotEmpty((String)entityAutoExCode)) {
                String entityAutoExCode3;
                newNetEntityKey = entityAutoExCode3 = this.singleTransService.toUpper(importContext, entityAutoExCode);
            }
        }
        return newNetEntityKey;
    }

    private void setFieldValueFromDBf(TaskDataContext importContext, IDbfTable dbf, DataRow dbfRow, Map<String, IFMDMAttribute> singleFieldMap, Map<String, SingleFileFieldInfo> fMDMSingleMaps, FMDMDataDTO entityRow1, boolean isNewEntity, String tempQYDM, Set<String> ignoreUpdateFields, SingleFileFmdmInfo fMTable) {
        for (int j = 1; j < dbf.geDbfFields().length; ++j) {
            DataColumn column;
            String dbfFieldName = dbf.geDbfFields()[j].getFieldName();
            if (dbf.getTable() != null && dbf.getTable().getColumns() != null && dbf.getTable().getColumns().isHasMapColumName() && (column = dbf.getTable().getColumns().get(dbfFieldName)) != null) {
                dbfFieldName = column.getMapColumnName();
            }
            if (dbfFieldName.equalsIgnoreCase("orgcode")) continue;
            String dbFieldValue = dbfRow.getValueString(j);
            if (!singleFieldMap.containsKey(dbfFieldName)) continue;
            IFMDMAttribute field = singleFieldMap.get(dbfFieldName);
            SingleFileFieldInfo mapField = null;
            if (fMDMSingleMaps.containsKey("FMDM." + dbfFieldName)) {
                mapField = fMDMSingleMaps.get("FMDM." + dbfFieldName);
                String enumFlag = mapField.getEnumCode();
                String newFieldValue = importContext.getMapingCache().getEnumNetItemCodeFromItem(enumFlag, dbFieldValue);
                if (StringUtils.isNotEmpty((String)newFieldValue)) {
                    dbFieldValue = newFieldValue;
                }
            }
            dbFieldValue = this.getFMFMDFieldValue(importContext, dbfFieldName, dbFieldValue, tempQYDM, field, fMTable, mapField, dbfRow, entityRow1);
            if (!isNewEntity && ignoreUpdateFields.contains(field.getCode().toUpperCase())) continue;
            entityRow1.setValue(field.getCode().toUpperCase(), (Object)dbFieldValue);
        }
    }

    private void loadSingleRowFromDBF(TaskDataContext importContext, IDbfTable dbf, DataEntityCache dbfEntityCache, SingleFileFmdmInfo fMTable, List<String> singleZdmWithoutPeriodCodes, boolean corpMapUseExp, boolean corpMapUseRule, String singleMapExpresion, SyntaxExcuteMapEntity expGeter, Map<String, RuleMap> singleMapRuleFields, Map<String, String> dbfZdmFjdMap, Map<String, SingleFileFieldInfo> mapSingleFieldList, Map<String, DataRow> dbfZdmRowMap, List<DataRow> importDataRows, Map<String, SingleDataRow> dbfZdmAndRowMap, List<SingleDataRow> importSingleRows, List<String> errorZdms) {
        ArrayList<String> loadDbfFieldNames = new ArrayList<String>();
        loadDbfFieldNames.add("SYS_FJD");
        if (fMTable != null) {
            if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                loadDbfFieldNames.add(fMTable.getDWDMField());
            }
            if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                loadDbfFieldNames.add(fMTable.getDWMCField());
            }
            if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                loadDbfFieldNames.add(fMTable.getBBLXField());
            }
            if (StringUtils.isNotEmpty((String)fMTable.getPeriodField())) {
                loadDbfFieldNames.add(fMTable.getPeriodField());
            }
            for (String code : singleZdmWithoutPeriodCodes) {
                if (loadDbfFieldNames.contains(code)) continue;
                loadDbfFieldNames.add(code);
            }
        }
        List loadDbfFields = dbf.getFieldIndexs(loadDbfFieldNames);
        loadDbfFields.add(0, 0);
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            DataRow dbfRow = dbf.getRecordByIndex(i);
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm)) continue;
            if (!dbf.isHasLoadAllRec()) {
                if (corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpresion)) {
                    dbf.loadDataRow(dbfRow);
                } else {
                    dbf.loadDataRowByIndexs(dbfRow, loadDbfFields);
                }
            }
            String dbfPeriod = "";
            boolean checkDBFPeriod = true;
            if (importContext.getMapingCache().isMapConfig() && "N".equalsIgnoreCase(importContext.getMapingCache().getMapConfig().getTaskInfo().getSingleTaskPeriod())) {
                checkDBFPeriod = false;
            }
            if (StringUtils.isNotEmpty((String)fMTable.getPeriodField()) && checkDBFPeriod && StringUtils.isNotEmpty((String)(dbfPeriod = dbfRow.getValueString(fMTable.getPeriodField()))) && !dbfPeriod.equalsIgnoreCase(importContext.getCurrentPeriod())) continue;
            dbfZdmRowMap.put(zdm, dbfRow);
            String fjd = dbfRow.getValueString("SYS_FJD");
            dbfZdmFjdMap.put(zdm, fjd);
            SingleDataRow singleRow = new SingleDataRow();
            singleRow.setZdm(zdm);
            singleRow.setFjd(fjd);
            singleRow.setDataRow(dbfRow);
            singleRow.setPeriod(dbfPeriod);
            String singleMapExpValue = null;
            if (corpMapUseExp && StringUtils.isNotEmpty((String)singleMapExpresion)) {
                singleMapExpValue = expGeter.getExpValue(dbfRow);
                singleRow.setMapExpValue(singleMapExpValue);
            }
            if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                singleRow.setDwdm(dbfRow.getValueString(fMTable.getDWDMField()));
            }
            if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                singleRow.setDwmc(dbfRow.getValueString(fMTable.getDWMCField()));
            }
            if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                singleRow.setBblx(dbfRow.getValueString(fMTable.getBBLXField()));
            }
            String zdmWithOutPeriod = "";
            for (String string : singleZdmWithoutPeriodCodes) {
                String dbfFieldValue = dbfRow.getValueString(string);
                if (mapSingleFieldList.containsKey(string)) {
                    SingleFileFieldInfo singleField = mapSingleFieldList.get(string);
                    for (int len = singleField.getFieldSize() - dbfFieldValue.length(); len > 0; --len) {
                        dbfFieldValue = dbfFieldValue + " ";
                    }
                }
                singleRow.getFieldValues().put(string, dbfFieldValue);
                zdmWithOutPeriod = zdmWithOutPeriod + dbfFieldValue;
            }
            singleRow.setZdmWithOutPeriod(zdmWithOutPeriod);
            String singleMapFieldValue = "";
            if (corpMapUseRule && !corpMapUseExp) {
                for (String code2 : singleMapRuleFields.keySet()) {
                    Object dbfFieldValue = dbfRow.getValueString(code2);
                    if (mapSingleFieldList.containsKey(code2)) {
                        SingleFileFieldInfo singleField = mapSingleFieldList.get(code2);
                        for (int len = singleField.getFieldSize() - ((String)dbfFieldValue).length(); len > 0; --len) {
                            dbfFieldValue = (String)dbfFieldValue + " ";
                        }
                    }
                    singleRow.getFieldValues().put(code2, dbfFieldValue);
                    singleMapFieldValue = singleMapFieldValue + (String)dbfFieldValue;
                }
                singleRow.setMapFieldValue(singleMapFieldValue);
            }
            dbfZdmAndRowMap.put(zdm, singleRow);
            importDataRows.add(dbfRow);
            importSingleRows.add(singleRow);
            DataEntityInfo dataEntityInfo = new DataEntityInfo();
            dataEntityInfo.setEntityKey(zdm);
            dataEntityInfo.setEntityCode(zdm);
            dataEntityInfo.setEntityParentKey(fjd);
            dataEntityInfo.setEntityExCode(zdmWithOutPeriod);
            dataEntityInfo.setEntityRuleCode(singleMapFieldValue);
            dataEntityInfo.setEntityExpCode(singleMapExpValue);
            if (fMTable != null) {
                if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    dataEntityInfo.setSingleDwdm(dbfRow.getValueString(fMTable.getDWDMField()));
                }
                if (StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                    dataEntityInfo.setSingleBblx(dbfRow.getValueString(fMTable.getBBLXField()));
                }
                if (StringUtils.isNotEmpty((String)fMTable.getDWMCField())) {
                    dataEntityInfo.setEntityTitle(dbfRow.getValueString(fMTable.getDWMCField()));
                }
            }
            dataEntityInfo.setEntintyRow((Object)singleRow);
            dataEntityInfo.setParentZdm(fjd);
            dataEntityInfo.setSingleZdm(zdm);
            dataEntityInfo.setSingleZdmWithOutPeriod(zdmWithOutPeriod);
            dbfEntityCache.addEntity(dataEntityInfo);
            String zdmNew = "";
            for (String code : fMTable.getZdmFieldCodes()) {
                zdmNew = zdmNew + dbfRow.getValueString(code);
            }
            if (!zdm.equalsIgnoreCase(zdmNew)) {
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u4e3b\u4ee3\u7801\u4e0e\u6784\u6210\u5b57\u6bb5\u4e0d\u4e00\u81f4\u7684\u5355\u4f4d", "checkZdmError", zdmWithOutPeriod, singleRow.getDwmc(), zdm);
                if (errorZdms.size() < 30) {
                    importContext.info(logger, "JIO\u6587\u4ef6\u4e2d\u4e3b\u4ee3\u7801\u4e0e\u6784\u6210\u5b57\u6bb5\u4e0d\u4e00\u81f4\u7684\u5355\u4f4d,zdm=" + zdm + ",\u6784\u6210\u5b57\u6bb5=" + zdmNew + ",\u5b57\u6bb5\u6807\u8bc6\uff1a" + fMTable.getZdmFieldCodes().toString() + ",\u8bf7\u68c0\u67e5");
                }
                if (this.singleOptionService.isUploadCheckZdm()) {
                    importContext.recordLog("FMDM", errorItem);
                    errorZdms.add(zdm);
                }
            }
            if (dbf.isHasLoadAllRec()) continue;
            dbf.clearDataRow(dbfRow);
        }
    }

    private Set<String> getMergeZdms(TaskDataContext importContext, Map<String, String> dbfZdmFjdMap, String netPeriodCode) throws Exception {
        HashSet<String> mergeZdms = new HashSet<String>();
        if (this.entityMergeServie != null) {
            String singleTaskFlag = null;
            String singleFileFlag = null;
            if (importContext.getMapingCache().getMapConfig() != null && importContext.getMapingCache().getMapConfig().getTaskInfo() != null) {
                singleTaskFlag = importContext.getMapingCache().getMapConfig().getTaskInfo().getSingleTaskFlag();
                singleFileFlag = importContext.getMapingCache().getMapConfig().getTaskInfo().getSingleTaskFlag();
            }
            Map<String, List<String>> entityMergeList = this.entityMergeServie.getMergeEntityList(singleTaskFlag, singleFileFlag, importContext.getCurrentPeriod(), dbfZdmFjdMap);
            Map<String, String> entityMergeBenJi = this.entityMergeServie.getMergeEntityBenJi(singleTaskFlag, singleFileFlag, importContext.getCurrentPeriod(), dbfZdmFjdMap);
            if (entityMergeBenJi != null && entityMergeBenJi.size() > 0) {
                importContext.getEntityMergeBenJiMap().clear();
                importContext.getEntityMergeBenJiMap().putAll(entityMergeBenJi);
            }
            if (entityMergeList != null && entityMergeList.size() > 0) {
                importContext.getEntityMergeZdmMap().clear();
                importContext.getEntityMergeZdmMap().putAll(entityMergeList);
                this.entityMergeServie.beginImportFMDM(singleTaskFlag, singleFileFlag, importContext.getCurrentPeriod(), netPeriodCode);
                for (List<String> zdms : entityMergeList.values()) {
                    mergeZdms.addAll(zdms);
                    importContext.getSingleMergeZdms().addAll(zdms);
                }
            }
        }
        return mergeZdms;
    }

    private boolean zdmIsMergedAndDoMerge(TaskDataContext importContext, IDbfTable dbf, String zdm, DataRow dbfRow, String netPeriodCode, Set<String> mergeZdms) {
        boolean isMerged = false;
        if (this.entityMergeServie != null && mergeZdms.size() > 0 && mergeZdms.contains(zdm)) {
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            LinkedHashMap<String, String> fieldDataList = new LinkedHashMap<String, String>();
            for (int j = 1; j < dbf.geDbfFields().length; ++j) {
                DataColumn column;
                String dbfFieldName = dbf.geDbfFields()[j].getFieldName();
                if (dbf.getTable() != null && dbf.getTable().getColumns() != null && dbf.getTable().getColumns().isHasMapColumName() && (column = dbf.getTable().getColumns().get(dbfFieldName)) != null) {
                    dbfFieldName = column.getMapColumnName();
                }
                if (dbfFieldName.equalsIgnoreCase("orgcode")) continue;
                String dbFieldValue = dbfRow.getValueString(j);
                fieldDataList.put(dbfFieldName, dbFieldValue);
            }
            this.entityMergeServie.updateMergeFMDMData(zdm, importContext.getCurrentPeriod(), netPeriodCode, fieldDataList);
            if (!dbf.isHasLoadAllRec()) {
                dbf.clearDataRow(dbfRow);
            }
            isMerged = true;
        }
        return isMerged;
    }

    private UnitNatureGetter getUnitNatureGetter(TaskDataContext importContext, boolean enableNrFormType) {
        UnitNatureGetter unitNatureGetter = null;
        if (StringUtils.isNotEmpty((String)importContext.getDataSchemeKey())) {
            DsContext dsContext = DsContextHolder.getDsContext();
            String queryEntityId = null;
            if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
                queryEntityId = dsContext.getContextEntityId();
            } else {
                TaskDefine taskDefine = this.runtimeView.queryTaskDefine(importContext.getTaskKey());
                queryEntityId = taskDefine.getDw();
            }
            String bblxCode = this.formTypeApplyService.getEntityFormTypeCode(queryEntityId);
            if (StringUtils.isNotEmpty((String)bblxCode)) {
                unitNatureGetter = this.formTypeApplyService.getUnitNatureGetter(bblxCode);
            }
            if (unitNatureGetter == null) {
                String fileFlag = "";
                DataScheme dataScheme = this.dataSchemeSevice.getDataScheme(importContext.getDataSchemeKey());
                if (dataScheme != null && StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
                    fileFlag = "_" + dataScheme.getPrefix();
                }
                if ((unitNatureGetter = this.formTypeApplyService.getUnitNatureGetter("MD_BBLX" + fileFlag)) == null) {
                    unitNatureGetter = this.formTypeApplyService.getUnitNatureGetter("MD_BBLX");
                }
                if (enableNrFormType) {
                    importContext.info(logger, "\u56fd\u8d44\u59d4\u6a21\u5f0f\u4e0b\uff1a");
                    if (unitNatureGetter == null) {
                        importContext.info(logger, "\u56fd\u8d44\u59d4\u6a21\u5f0f\u4e0b\uff0c\u627e\u4e0d\u5230\u62a5\u8868\u7c7b\u578bMD_BBLX" + fileFlag);
                    }
                }
            }
        }
        return unitNatureGetter;
    }

    private boolean reTreeDbf(TaskDataContext importContext, DataEntityCache dbfEntityCache, UnitNatureGetter unitNatureGetter, List<SingleDataRow> importSingleRows) {
        List treeNodes;
        boolean needSort = false;
        dbfEntityCache.buildNormalTree();
        if (unitNatureGetter != null) {
            for (Map.Entry entry : dbfEntityCache.getChildrenEntitys().entrySet()) {
                String parentZdm = (String)entry.getKey();
                List childList = (List)entry.getValue();
                DataEntityInfo parentEntity = (DataEntityInfo)dbfEntityCache.getEntityKeyFinder().get(parentZdm);
                if (parentEntity == null || childList == null || childList.isEmpty()) continue;
                ArrayList<DataEntityInfo> newChildList = new ArrayList<DataEntityInfo>();
                DataEntityInfo bjChild = null;
                ArrayList<DataEntityInfo> bjChilds = new ArrayList<DataEntityInfo>();
                DataEntityInfo ceChild = null;
                ArrayList<DataEntityInfo> ceChilds = new ArrayList<DataEntityInfo>();
                for (DataEntityInfo childEntity : childList) {
                    int bblxType = 2;
                    if (StringUtils.isNotEmpty((String)childEntity.getSingleBblx())) {
                        UnitNature unitNature = unitNatureGetter.getUnitNature(childEntity.getSingleBblx());
                        if (unitNature == UnitNature.JCFHB && StringUtils.isNotEmpty((String)childEntity.getSingleDwdm()) && childEntity.getSingleDwdm().equalsIgnoreCase(parentEntity.getSingleDwdm())) {
                            bblxType = 0;
                            bjChild = childEntity;
                            bjChilds.add(childEntity);
                        } else if (unitNature == UnitNature.JTCEB) {
                            bblxType = 1;
                            ceChild = childEntity;
                            ceChilds.add(childEntity);
                        }
                    }
                    if (bblxType != 2) continue;
                    newChildList.add(childEntity);
                }
                if (!bjChilds.isEmpty()) {
                    for (DataEntityInfo child : bjChilds) {
                        newChildList.add(0, child);
                    }
                }
                if (!ceChilds.isEmpty()) {
                    for (DataEntityInfo child : ceChilds) {
                        newChildList.add(0, child);
                    }
                }
                if (childList.size() != newChildList.size()) {
                    logger.info("\u8282\u70b9\u4e22\u5931\uff1a" + parentZdm);
                }
                dbfEntityCache.getChildrenEntitys().put(parentZdm, newChildList);
            }
        }
        if (!(treeNodes = dbfEntityCache.getNodesByTree()).isEmpty() && treeNodes.size() == importSingleRows.size()) {
            importSingleRows.clear();
            for (DataEntityInfo treeEntity : treeNodes) {
                importSingleRows.add((SingleDataRow)treeEntity.getEntintyRow());
            }
            needSort = true;
        } else {
            logger.info("FMDM.DBF\u6784\u5efa\u6811\u5f62\u540e\uff0c\u8282\u70b9\u6570\u4e0d\u4e00\u81f4\uff0c\u4e0d\u6309\u7167\u6811\u5f62\u987a\u5e8f\u5bfc\u5165,DBF\u8bb0\u5f55\u6570\uff1a" + importSingleRows.size() + ",\u65b0\u5efa\u6811\u5f62\u8282\u70b9\u6570\uff1a" + treeNodes.size());
            HashMap<String, DataEntityInfo> hashMap = new HashMap<String, DataEntityInfo>();
            for (DataEntityInfo treeEntity : treeNodes) {
                hashMap.put(treeEntity.getSingleZdm(), treeEntity);
            }
            for (SingleDataRow dataRow : importSingleRows) {
                if (hashMap.containsKey(dataRow.getZdm())) continue;
                logger.info("\u6811\u5f62\u4e4b\u5916\u8282\u70b9\uff1a" + dataRow.getZdm());
            }
        }
        return needSort;
    }

    private boolean checkRootRow(TaskDataContext importContext, DataEntityCache dbfEntityCache, String netPeriodCode, String entityId, List<SingleDataRow> importSingleRows, Map<String, DimensionValueSet> tranDimDic, BatchFMDMDTO deleteBatchOptDTO, boolean corpMapUseExp, Map<String, DataEntityInfo> entityExpCodeAndEntiyInfoMap, Map<String, DataEntityInfo> entityAutoExCodeAndEntiyInfos, Map<String, UnitCustomMapping> singleMapingUnitList) throws Exception {
        SingleDataError errorItem;
        boolean result = false;
        boolean isAdmin = this.systemdentityService.isAdmin();
        boolean isBusinessManager = this.systemdentityService.isBusinessManager();
        if (dbfEntityCache.getRootEntitys().size() != 1) {
            for (DataEntityInfo node : dbfEntityCache.getRootEntitys()) {
                SingleDataError errorItem2 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u591a\u4e2a\u6839\u8282\u70b9", "checkRootEntityCount", node.getEntityExCode(), node.getEntityTitle(), node.getEntityCode());
                importContext.recordLog("FMDM", errorItem2);
            }
            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6811\u5f62\u4e0d\u662f\u552f\u4e00\u6839\u8282\u70b9\uff0c\u4e0d\u5bfc\u5165\u6570\u636e\uff0c\u5355\u4f4d\u6570\uff1a" + dbfEntityCache.getNodesByTree().size() + ",\u6839\u8282\u70b9\u6570\uff1a" + dbfEntityCache.getRootEntitys().size());
            return result;
        }
        DataEntityInfo rootEntity = (DataEntityInfo)dbfEntityCache.getRootEntitys().get(0);
        SingleDataRow rootRow = (SingleDataRow)rootEntity.getEntintyRow();
        String zdmWithOutPeriod = rootEntity.getEntityExCode();
        String singleMapExpValue = rootEntity.getEntityExpCode();
        String singleRuleCode = rootEntity.getEntityRuleCode();
        String zdmWithOutPeriodParent = null;
        String zdmRuleCodeParent = null;
        if (StringUtils.isNotEmpty((String)rootEntity.getParentZdm()) && (zdmRuleCodeParent = (zdmWithOutPeriodParent = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(rootEntity.getParentZdm()))).length() < importContext.getEntityCache().getSingleZdmOutPeriodLen()) {
            String bblx = zdmRuleCodeParent.substring(zdmRuleCodeParent.length() - 1);
            String qydm = zdmRuleCodeParent.substring(0, zdmRuleCodeParent.length() - 1);
            for (int len = importContext.getEntityCache().getSingleZdmOutPeriodLen() - zdmRuleCodeParent.length(); len > 0; --len) {
                qydm = qydm + " ";
            }
            zdmRuleCodeParent = qydm + bblx;
        }
        String singleMapExpValueParent = null;
        String singleMapExpValueParent2 = null;
        if (corpMapUseExp) {
            singleMapExpValueParent = zdmWithOutPeriodParent;
            singleMapExpValueParent = zdmRuleCodeParent;
            if (importContext.getMapingCache().getMapConfig() != null && importContext.getMapingCache().getMapConfig().getMapRule() != null && !importContext.getMapingCache().getMapConfig().getMapRule().isEmpty()) {
                String singleExpFormula = null;
                for (RuleMap rule : importContext.getMapingCache().getMapConfig().getMapRule()) {
                    if (rule.getRule() != RuleKind.UNIT_MAP_IMPORT || !StringUtils.isNotEmpty((String)rule.getSingleCode())) continue;
                    singleExpFormula = rule.getSingleCode();
                    break;
                }
                if (StringUtils.isNotEmpty(singleExpFormula)) {
                    String defaultValue;
                    String next;
                    if (singleExpFormula.startsWith("\"")) {
                        next = singleExpFormula.substring(1);
                        int nextId = next.indexOf("\"");
                        defaultValue = next.substring(0, nextId);
                        singleMapExpValueParent = defaultValue + zdmWithOutPeriodParent;
                        singleMapExpValueParent2 = defaultValue + zdmRuleCodeParent;
                    } else if (singleExpFormula.endsWith("\"")) {
                        next = singleExpFormula;
                        int nextId = next.indexOf("\"");
                        defaultValue = next.substring(nextId + 1, singleExpFormula.length() - 1);
                        singleMapExpValueParent = zdmWithOutPeriodParent + defaultValue;
                        singleMapExpValueParent2 = zdmRuleCodeParent + defaultValue;
                    } else if (singleExpFormula.startsWith("'")) {
                        next = singleExpFormula.substring(1);
                        int nextId = next.indexOf("'");
                        defaultValue = next.substring(0, nextId);
                        singleMapExpValueParent = defaultValue + zdmWithOutPeriodParent;
                        singleMapExpValueParent2 = defaultValue + zdmRuleCodeParent;
                    } else if (singleExpFormula.endsWith("'")) {
                        next = singleExpFormula;
                        int nextId = next.indexOf("'");
                        defaultValue = next.substring(nextId + 1, singleExpFormula.length() - 1);
                        singleMapExpValueParent = zdmWithOutPeriodParent + defaultValue;
                        singleMapExpValueParent2 = zdmRuleCodeParent + defaultValue;
                    }
                }
            }
        }
        AutoAppendCode autoCodeCoinfig = null;
        if (null != importContext.getMapingCache().getMapConfig().getConfig() && null != importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()) {
            autoCodeCoinfig = importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode();
        }
        String entityJcm = "";
        String parentEntityJcm = "";
        String defaultEntityJcm = "";
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
            if (autoCodeCoinfig.getCodeMapping().containsKey("\u9ed8\u8ba4\u503c")) {
                defaultEntityJcm = (String)autoCodeCoinfig.getCodeMapping().get("\u9ed8\u8ba4\u503c");
            }
            parentEntityJcm = StringUtils.isNotEmpty((String)zdmWithOutPeriodParent) && autoCodeCoinfig.getCodeMapping().containsKey(zdmWithOutPeriodParent) ? (String)autoCodeCoinfig.getCodeMapping().get(zdmWithOutPeriodParent) : defaultEntityJcm;
            entityJcm = autoCodeCoinfig.getCodeMapping().containsKey(zdmWithOutPeriod) ? (String)autoCodeCoinfig.getCodeMapping().get(zdmWithOutPeriod) : (StringUtils.isNotEmpty((String)parentEntityJcm) ? parentEntityJcm : defaultEntityJcm);
        }
        DataEntityInfo rootEntityInfo = this.findEntity(importContext, zdmWithOutPeriod, singleRuleCode, singleMapExpValue, entityJcm, rootEntity.getSingleZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
        DataEntityInfo parentEntityInfo = null;
        IEntityTable entityTable = this.getEntityTable(importContext, null, netPeriodCode, AuthorityType.None);
        HashMap<String, IEntityRow> allNetRowsMap = null;
        Set<Object> allNetUnitList = null;
        if (entityTable != null) {
            allNetRowsMap = entityTable.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity()));
            allNetUnitList = entityTable.getAllRows().stream().map(IEntityItem::getCode).collect(Collectors.toSet());
        } else {
            allNetRowsMap = new HashMap();
            allNetUnitList = new HashSet();
        }
        Map<Object, Object> modifiyNetRowsMap = null;
        HashMap<String, Object> editNetRowsMap = null;
        Map<Object, Object> readNetRowsMap = null;
        if (isAdmin) {
            modifiyNetRowsMap = new HashMap<String, IEntityRow>();
            modifiyNetRowsMap.putAll(allNetRowsMap);
            readNetRowsMap = new HashMap<String, IEntityRow>();
            readNetRowsMap.putAll(allNetRowsMap);
            editNetRowsMap = new HashMap<String, Object>();
            editNetRowsMap.putAll(allNetRowsMap);
        } else {
            IEntityTable modifyEntityTable = this.getEntityTable(importContext, null, netPeriodCode, AuthorityType.Modify);
            modifiyNetRowsMap = modifyEntityTable != null ? modifyEntityTable.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity())) : new HashMap();
            IEntityTable readEntityTable = this.getEntityTable(importContext, null, netPeriodCode, AuthorityType.Read);
            readNetRowsMap = readEntityTable != null ? readEntityTable.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity())) : new HashMap();
            editNetRowsMap = new HashMap();
            if (!allNetUnitList.isEmpty()) {
                Map<String, Boolean> editMap = this.getBatchEidtEable(importContext.getFormSchemeKey(), allNetUnitList, netPeriodCode);
                for (Map.Entry entry : allNetRowsMap.entrySet()) {
                    Boolean isEdit;
                    if (!editMap.containsKey(entry.getKey()) || (isEdit = editMap.get(entry.getKey())) == null || !isEdit.booleanValue()) continue;
                    editNetRowsMap.put((String)entry.getKey(), entry.getValue());
                }
            }
        }
        if (rootEntityInfo != null) {
            if (!isAdmin && !modifiyNetRowsMap.containsKey(rootEntityInfo.getEntityKey())) {
                SingleDataError errorItem3 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u6743\u9650\u4fee\u6539", "noPower_modify", rootEntityInfo.getEntityExCode(), rootEntityInfo.getEntityTitle(), rootEntityInfo.getEntityCode());
                importContext.recordLog("FMDM", errorItem3);
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6839\u8282\u70b9\u65e0\u6743\u9650\u4fee\u6539" + rootEntityInfo.getEntityKey());
                return result;
            }
            boolean canModifyParent = true;
            String newParentKey = "";
            if (StringUtils.isNotEmpty((String)rootEntity.getParentZdm())) {
                parentEntityInfo = this.findEntity(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
                if (corpMapUseExp && parentEntityInfo == null && StringUtils.isNotEmpty((String)singleMapExpValueParent) && !singleMapExpValueParent.equalsIgnoreCase(singleMapExpValueParent2)) {
                    parentEntityInfo = this.findEntity(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent2, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
                }
                if (parentEntityInfo == null) {
                    canModifyParent = false;
                    newParentKey = zdmWithOutPeriodParent;
                    IEntityRow parentEnittyRow = this.findEntityInOrg(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, singleMapingUnitList, allNetRowsMap);
                    if (corpMapUseExp && parentEnittyRow == null && StringUtils.isNotEmpty((String)singleMapExpValueParent) && !singleMapExpValueParent.equalsIgnoreCase(singleMapExpValueParent2)) {
                        parentEnittyRow = this.findEntityInOrg(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent2, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, singleMapingUnitList, allNetRowsMap);
                    }
                    if (parentEnittyRow != null && StringUtils.isNotEmpty((String)(newParentKey = parentEnittyRow.getEntityKeyData())) && newParentKey.equalsIgnoreCase(rootEntityInfo.getEntityParentKey())) {
                        canModifyParent = true;
                        rootRow.getFieldValues().put("IMPORT_NET_PARENTKEY", newParentKey);
                    }
                } else {
                    newParentKey = parentEntityInfo.getEntityKey();
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6839\u8282" + zdmWithOutPeriod + ",\u539f\u7236\u8282\u70b9" + rootEntityInfo.getEntityParentKey() + ",\u53d8\u66f4\u5230\u65b0\u7236\u8282\u70b9" + newParentKey);
                    rootRow.getFieldValues().put("IMPORT_NET_PARENTKEY", newParentKey);
                    canModifyParent = true;
                }
            } else if (StringUtils.isNotEmpty((String)rootEntity.getEntityParentKey()) && !"-".equalsIgnoreCase(rootEntity.getEntityParentKey())) {
                canModifyParent = false;
                newParentKey = "-";
            }
            if (!canModifyParent) {
                errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u6743\u9650\u4fee\u6539\u7236\u8282\u70b9", "noPower_modifyParent", rootEntity.getEntityExCode(), rootEntity.getEntityTitle(), rootEntity.getEntityCode());
                importContext.recordLog("FMDM", errorItem);
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6839\u8282\u70b9\u65e0\u6743\u9650\u4fee\u6539\u7236\u8282\u70b9" + zdmWithOutPeriod + ",\u65b0\u7236\u8282\u70b9" + newParentKey);
                return result;
            }
            HashMap<String, SingleDataRow> allSinlgeRowsMap2 = new HashMap<String, SingleDataRow>();
            ArrayList<SingleDataRow> exitInOtherNodes = new ArrayList<SingleDataRow>();
            List subNetRows = entityTable.getAllChildRows(rootEntityInfo.getEntityKey());
            Map subNetRowsMap = subNetRows.stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity()));
            for (int i = 0; i < importSingleRows.size(); ++i) {
                SingleDataRow singleRow = importSingleRows.get(i);
                if (singleRow == rootRow) continue;
                String subZdmWithOutPeriod = singleRow.getZdmWithOutPeriod();
                Iterator subSingleMapExpValue = singleRow.getMapExpValue();
                String subSingleRuleCode = singleRow.getMapFieldValue();
                String subEntityJcm = "";
                if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
                    if (autoCodeCoinfig.getCodeMapping().containsKey(subZdmWithOutPeriod)) {
                        subEntityJcm = (String)autoCodeCoinfig.getCodeMapping().get(subZdmWithOutPeriod);
                    } else if (StringUtils.isNotEmpty((String)entityJcm)) {
                        subEntityJcm = entityJcm;
                    }
                }
                DataEntityInfo subEntityInfo = this.findEntity(importContext, subZdmWithOutPeriod, subSingleRuleCode, (String)((Object)subSingleMapExpValue), subEntityJcm, singleRow.getZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
                boolean hasInOtherNode = false;
                if (subEntityInfo != null) {
                    if (!subNetRowsMap.containsKey(subEntityInfo.getEntityKey())) {
                        importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d" + subEntityInfo.getEntityKey());
                        hasInOtherNode = true;
                    }
                    allSinlgeRowsMap2.put(subEntityInfo.getEntityKey(), singleRow);
                } else {
                    IEntityRow subEnittyRow = this.findEntityInOrg(importContext, subZdmWithOutPeriod, subSingleRuleCode, (String)((Object)subSingleMapExpValue), subEntityJcm, singleRow.getZdm(), corpMapUseExp, singleMapingUnitList, (Map<String, IEntityRow>)allNetRowsMap);
                    if (subEnittyRow != null && allNetRowsMap.containsKey(subEnittyRow.getEntityKeyData())) {
                        importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d" + subZdmWithOutPeriod);
                        hasInOtherNode = true;
                    }
                    if (subEnittyRow != null) {
                        allSinlgeRowsMap2.put(subEnittyRow.getEntityKeyData(), singleRow);
                    } else {
                        String netEntityKey = this.getNewEntityKey(importContext, subZdmWithOutPeriod, subSingleRuleCode, (String)((Object)subSingleMapExpValue), subEntityJcm, singleRow.getZdm(), corpMapUseExp, singleMapingUnitList);
                        allSinlgeRowsMap2.put(netEntityKey, singleRow);
                    }
                }
                if (!hasInOtherNode) continue;
                SingleDataError errorItem4 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d", "exist_otherNode", singleRow.getZdmWithOutPeriod(), singleRow.getDwmc(), singleRow.getZdmWithOutPeriod());
                importContext.recordLog("FMDM", errorItem4);
                exitInOtherNodes.add(singleRow);
            }
            if (!exitInOtherNodes.isEmpty()) {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e\uff0c\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d,\u5355\u4f4d\u6570" + exitInOtherNodes.size());
                return result;
            }
            ArrayList<SingleDataError> deleteUnitList = new ArrayList<SingleDataError>();
            ArrayList<IEntityRow> noPowerDeleteNodes = new ArrayList<IEntityRow>();
            Map<String, DataEntityInfo> allSinlgeRowsMap = dbfEntityCache.getEntityList().stream().collect(Collectors.toMap(DataEntityInfo::getEntityExCode, a -> a));
            for (IEntityRow entityRow : subNetRows) {
                if (allSinlgeRowsMap2.containsKey(entityRow.getEntityKeyData())) continue;
                boolean canDelete = true;
                if (isAdmin) {
                    canDelete = true;
                } else if (isBusinessManager) {
                    canDelete = true;
                } else if (!editNetRowsMap.containsKey(entityRow.getEntityKeyData())) {
                    canDelete = false;
                }
                if (!canDelete) {
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u65e0\u6743\u9650\u5220\u9664" + entityRow.getEntityKeyData());
                    SingleDataError errorItem5 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u6743\u9650\u5220\u9664", "noPower_delete", entityRow.getEntityKeyData(), entityRow.getTitle(), entityRow.getEntityKeyData());
                    importContext.recordLog("FMDM", errorItem5);
                    noPowerDeleteNodes.add(entityRow);
                    continue;
                }
                FMDMDataDTO entityRow1 = new FMDMDataDTO();
                String zdmKey = entityRow.getEntityKeyData();
                DimensionValueSet rowDimensionValueSet = new DimensionValueSet();
                rowDimensionValueSet.setValue("DATATIME", (Object)netPeriodCode);
                rowDimensionValueSet.setValue(importContext.getEntityCompanyType(), (Object)zdmKey);
                this.setOtherDimValue(importContext, rowDimensionValueSet);
                this.setSingleDimValue(importContext, rowDimensionValueSet, "");
                if (tranDimDic.containsKey(zdmKey)) {
                    if (rowDimensionValueSet.hasValue("MD_CURRENCY")) {
                        String currency = (String)tranDimDic.get(zdmKey).getValue("MD_CURRENCY");
                        rowDimensionValueSet.setValue("MD_CURRENCY", (Object)currency);
                    }
                    if (importContext.getDimEntityCache().getEntitySingleDims().size() > 0) {
                        for (String dimName : importContext.getDimEntityCache().getEntitySingleDims()) {
                            String dimValue = (String)tranDimDic.get(zdmKey).getValue(dimName);
                            rowDimensionValueSet.setValue(dimName, (Object)dimValue);
                        }
                    }
                }
                entityRow1.setDimensionValueSet(rowDimensionValueSet);
                entityRow1.setValue("CODE", (Object)zdmKey);
                entityRow1.setFormSchemeKey(importContext.getFormSchemeKey());
                deleteBatchOptDTO.getFmdmList().add(entityRow1);
                SingleDataError deleteItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u5220\u9664\u5355\u4f4d", "delete_unit", entityRow.getEntityKeyData(), entityRow.getTitle(), entityRow.getEntityKeyData());
                deleteUnitList.add(deleteItem);
            }
            if (!noPowerDeleteNodes.isEmpty()) {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u5b58\u5728\u65e0\u6743\u9650\u5220\u9664\u5355\u4f4d\uff0c\u5355\u4f4d\u6570" + exitInOtherNodes.size());
                return result;
            }
            if (!deleteBatchOptDTO.getFmdmList().isEmpty()) {
                importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u5220\u9664\uff1a\u4e2a\u6570" + deleteBatchOptDTO.getFmdmList().size());
                for (SingleDataError deleteItem : deleteUnitList) {
                    importContext.recordLog("FMDM", deleteItem);
                }
            }
        } else {
            IEntityRow rootEnittyRow = this.findEntityInOrg(importContext, zdmWithOutPeriod, singleRuleCode, singleMapExpValue, entityJcm, rootEntity.getSingleZdm(), corpMapUseExp, singleMapingUnitList, allNetRowsMap);
            if (rootEnittyRow != null) {
                SingleDataError errorItem6 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u6743\u9650\u4fee\u6539", "noPower_modify", rootEntity.getEntityExCode(), rootEntity.getEntityTitle(), rootEntity.getEntityCode());
                importContext.recordLog("FMDM", errorItem6);
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6839\u8282\u70b9\u65e0\u6743\u9650\u4fee\u6539" + rootEntity.getEntityExCode());
                return result;
            }
            boolean canAddRoot = true;
            if (StringUtils.isNotEmpty((String)rootEntity.getParentZdm())) {
                parentEntityInfo = this.findEntity(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
                if (corpMapUseExp && parentEntityInfo == null && StringUtils.isNotEmpty((String)singleMapExpValueParent) && !singleMapExpValueParent.equalsIgnoreCase(singleMapExpValueParent2)) {
                    parentEntityInfo = this.findEntity(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent2, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
                }
                if (parentEntityInfo == null) {
                    String newParentKey = zdmWithOutPeriodParent;
                    IEntityRow parentEnittyRow = this.findEntityInOrg(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, singleMapingUnitList, allNetRowsMap);
                    if (corpMapUseExp && parentEnittyRow == null && StringUtils.isNotEmpty((String)singleMapExpValueParent) && !singleMapExpValueParent.equalsIgnoreCase(singleMapExpValueParent2)) {
                        parentEnittyRow = this.findEntityInOrg(importContext, zdmWithOutPeriodParent, zdmRuleCodeParent, singleMapExpValueParent2, parentEntityJcm, rootEntity.getParentZdm(), corpMapUseExp, singleMapingUnitList, allNetRowsMap);
                    }
                    if (parentEnittyRow != null) {
                        newParentKey = parentEnittyRow.getEntityKeyData();
                        if (!isAdmin && !readNetRowsMap.containsKey(newParentKey)) {
                            canAddRoot = false;
                        }
                        rootRow.getFieldValues().put("IMPORT_NET_PARENTKEY", newParentKey);
                    } else if (!isAdmin) {
                        canAddRoot = false;
                    }
                } else {
                    if (!isAdmin && !readNetRowsMap.containsKey(parentEntityInfo.getEntityKey())) {
                        canAddRoot = false;
                    }
                    rootRow.getFieldValues().put("IMPORT_NET_PARENTKEY", parentEntityInfo.getEntityKey());
                }
            }
            if (!canAddRoot) {
                errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u6743\u9650\u65b0\u589e", "noPower_add", rootEntity.getEntityExCode(), rootEntity.getEntityTitle(), rootEntity.getEntityCode());
                importContext.recordLog("FMDM", errorItem);
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u6839\u8282\u70b9\u65e0\u6743\u9650\u65b0\u589e" + zdmWithOutPeriod);
                return result;
            }
            ArrayList<SingleDataRow> exitInOtherNodes = new ArrayList<SingleDataRow>();
            for (int i = 0; i < importSingleRows.size(); ++i) {
                SingleDataRow singleRow = importSingleRows.get(i);
                if (singleRow == rootRow) continue;
                String subZdmWithOutPeriod = singleRow.getZdmWithOutPeriod();
                String subSingleMapExpValue = singleRow.getMapExpValue();
                String subSingleRuleCode = singleRow.getMapFieldValue();
                String subEntityJcm = null;
                if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
                    if (autoCodeCoinfig.getCodeMapping().containsKey(subZdmWithOutPeriod)) {
                        subEntityJcm = (String)autoCodeCoinfig.getCodeMapping().get(subZdmWithOutPeriod);
                    } else if (StringUtils.isNotEmpty((String)entityJcm)) {
                        subEntityJcm = entityJcm;
                    }
                }
                DataEntityInfo subEntityInfo = this.findEntity(importContext, subZdmWithOutPeriod, subSingleRuleCode, subSingleMapExpValue, subEntityJcm, singleRow.getZdm(), corpMapUseExp, entityExpCodeAndEntiyInfoMap, entityAutoExCodeAndEntiyInfos, singleMapingUnitList);
                boolean hasInOtherNode = false;
                if (subEntityInfo != null) {
                    importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d" + subZdmWithOutPeriod);
                    hasInOtherNode = true;
                } else {
                    IEntityRow parentEnittyRow = this.findEntityInOrg(importContext, subZdmWithOutPeriod, subSingleRuleCode, subSingleMapExpValue, subEntityJcm, singleRow.getZdm(), corpMapUseExp, singleMapingUnitList, allNetRowsMap);
                    if (parentEnittyRow != null && allNetRowsMap.containsKey(parentEnittyRow.getEntityKeyData())) {
                        importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d" + subZdmWithOutPeriod);
                        hasInOtherNode = true;
                        return result;
                    }
                }
                if (!hasInOtherNode) continue;
                SingleDataError errorItem7 = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d", "exist_otherNode", singleRow.getZdmWithOutPeriod(), singleRow.getDwmc(), singleRow.getZdmWithOutPeriod());
                importContext.recordLog("FMDM", errorItem7);
                exitInOtherNodes.add(singleRow);
            }
            if (!exitInOtherNodes.isEmpty()) {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u4e0e\u5f53\u524d\u6811\u5f62\u91cd\u7801\u5355\u4f4d,\u5355\u4f4d\u6570" + exitInOtherNodes.size());
                return result;
            }
        }
        result = true;
        return result;
    }

    private boolean judgeNeedImportBySelect(TaskDataContext importContext, String zdm, int selectImportType, Map<String, RepeatEntityNode> jioSelectNodes) {
        boolean needImport = true;
        if (importContext.isNeedSelectImport() && jioSelectNodes.size() > 0) {
            if (selectImportType == 1) {
                if (!jioSelectNodes.containsKey(zdm)) {
                    importContext.info(logger, "\u9009\u62e9\u4e0d\u88c5\u5165:" + zdm);
                    needImport = false;
                }
            } else if (selectImportType == 2) {
                if (jioSelectNodes.containsKey(zdm)) {
                    importContext.info(logger, "\u9009\u62e9\u4e0d\u88c5\u5165:" + zdm);
                    needImport = false;
                }
            } else if (jioSelectNodes.containsKey(zdm)) {
                RepeatEntityNode selectNode = jioSelectNodes.get(zdm);
                if (selectNode.getRepeatMode() == 0) {
                    needImport = false;
                } else if (selectNode.getRepeatMode() == 1) {
                    // empty if block
                }
            }
        }
        return needImport;
    }

    private void filterDatas(TaskDataContext importContext, BatchFMDMDTO updateBatchOptDTO, String netPeriodCode, Map<String, String> entityKeyCodeMap, Map<String, String> uploadEntityZdmKeyMap) throws Exception {
        FormSchemeDefine formScheme;
        TaskDefine taskDefine;
        if (updateBatchOptDTO.getFmdmList().size() > 0 && (StringUtils.isNotEmpty((String)(taskDefine = this.runTimeAuthViewController.queryTaskDefine((formScheme = this.runTimeAuthViewController.getFormScheme(importContext.getFormSchemeKey())).getTaskKey())).getFilterExpression()) || StringUtils.isNotEmpty((String)taskDefine.getFilterTemplate()))) {
            EntityViewDefine entityView = this.runtimeView.getViewByTaskDefineKey(taskDefine.getKey());
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityView);
            DimensionValueSet masterKeys = new DimensionValueSet();
            masterKeys.setValue("DATATIME", (Object)netPeriodCode);
            masterKeys.clearValue(importContext.getEntityCompanyType());
            ArrayList<String> canReadunits = new ArrayList<String>();
            for (FMDMDataDTO fmmdDto : updateBatchOptDTO.getFmdmList()) {
                canReadunits.add(fmmdDto.getValue("CODE").getAsString());
            }
            masterKeys.setValue(importContext.getEntityCompanyType(), canReadunits);
            entityQuery.setMasterKeys(masterKeys);
            entityQuery.setAuthorityOperations(AuthorityType.None);
            try {
                IEntityTable entityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
                Map collect = entityTable.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity()));
                if (canReadunits.size() != collect.size()) {
                    HashMap<String, String> tempZdmAndKeyMap = new HashMap<String, String>();
                    for (Map.Entry<String, String> zdmEntry : uploadEntityZdmKeyMap.entrySet()) {
                        String zdm = zdmEntry.getKey();
                        String zdmKey = zdmEntry.getValue();
                        tempZdmAndKeyMap.put(zdmKey, zdm);
                    }
                    ArrayList<FMDMDataDTO> newUpdateList = new ArrayList<FMDMDataDTO>();
                    for (FMDMDataDTO fmmdDto : updateBatchOptDTO.getFmdmList()) {
                        String zdmWithOutPeriod;
                        String orgCode = fmmdDto.getValue("CODE").getAsString();
                        String orgTitle = fmmdDto.getValue("TITLE").getAsString();
                        if (collect.containsKey(orgCode)) {
                            newUpdateList.add(fmmdDto);
                            continue;
                        }
                        String zdm = (String)tempZdmAndKeyMap.get(orgCode);
                        if (StringUtils.isNotEmpty((String)zdm)) {
                            uploadEntityZdmKeyMap.remove(zdm);
                        }
                        if (StringUtils.isEmpty((String)(zdmWithOutPeriod = entityKeyCodeMap.get(orgCode)))) {
                            zdmWithOutPeriod = orgCode;
                        }
                        SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u5bfc\u5165", "filterNotUpdate", zdmWithOutPeriod, orgTitle, orgCode);
                        importContext.recordLog("FMDM", errorItem);
                        importContext.info(logger, "\u56e0\u4efb\u52a1\u4e3b\u4f53\u8fc7\u6ee4\u4e0d\u66f4\u65b0\uff1a" + orgCode);
                    }
                    updateBatchOptDTO.setFmdmList(newUpdateList);
                }
            }
            catch (Exception e) {
                importContext.error(logger, e.getMessage(), (Throwable)e);
                throw new RuntimeException(e);
            }
        }
    }

    private IEntityTable getEntityTable(TaskDataContext importContext, List<String> readunits, String netPeriodCode, AuthorityType authType) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(importContext.getTaskKey());
        if (taskDefine != null) {
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(taskDefine.getDw());
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityView);
            DimensionValueSet masterKeys = new DimensionValueSet();
            masterKeys.setValue("DATATIME", (Object)netPeriodCode);
            if (readunits != null && !readunits.isEmpty()) {
                masterKeys.clearValue(importContext.getEntityCompanyType());
                masterKeys.setValue(importContext.getEntityCompanyType(), readunits);
            }
            entityQuery.setMasterKeys(masterKeys);
            entityQuery.setAuthorityOperations(authType);
            return entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private void filterAddDatas(TaskDataContext importContext, BatchFMDMDTO addBatchOptDTO, BatchFMDMDTO updateBatchFMDMDTO, String netPeriodCode, Map<String, String> entityKeyCodeMap, Map<String, String> uploadEntityZdmKeyMap) throws Exception {
        block41: {
            FormSchemeDefine formScheme;
            TaskDefine taskDefine;
            boolean isAdmin = this.systemdentityService.isAdmin();
            if (addBatchOptDTO.getFmdmList().size() > 0 && !isAdmin && (taskDefine = this.runTimeAuthViewController.queryTaskDefine((formScheme = this.runTimeAuthViewController.getFormScheme(importContext.getFormSchemeKey())).getTaskKey())) != null) {
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(taskDefine.getDw());
                IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                entityQuery.setEntityView(entityView);
                DimensionValueSet masterKeys = new DimensionValueSet();
                masterKeys.setValue("DATATIME", (Object)netPeriodCode);
                masterKeys.clearValue(importContext.getEntityCompanyType());
                ArrayList<String> canReadunits = new ArrayList<String>();
                ArrayList<String> canWriteunits = new ArrayList<String>();
                HashSet<Object> unitDic = new HashSet<Object>();
                HashMap<String, FMDMDataDTO> addFMDMCodes = new HashMap<String, FMDMDataDTO>();
                HashMap addFMDMParentCodeMaps = new HashMap();
                HashMap addFMDMParentCodes = new HashMap();
                for (FMDMDataDTO fmmdDto : addBatchOptDTO.getFmdmList()) {
                    void var23_24;
                    Map map;
                    String code = fmmdDto.getValue("CODE").getAsString();
                    Object parentCode = fmmdDto.getValue("PARENTCODE").getAsString();
                    if (!unitDic.contains(code)) {
                        canReadunits.add(code);
                        unitDic.add(code);
                    } else {
                        importContext.info(logger, "\u65b0\u589e\u5355\u4f4d\u5b58\u5728\u91cd\u7801\uff1a" + code);
                    }
                    addFMDMCodes.put(code, fmmdDto);
                    if (StringUtils.isEmpty((String)parentCode)) {
                        parentCode = "-";
                    }
                    if (!"-".equalsIgnoreCase((String)parentCode) && !unitDic.contains(parentCode)) {
                        canReadunits.add((String)parentCode);
                        unitDic.add(parentCode);
                    }
                    if ((map = (Map)addFMDMParentCodeMaps.get(parentCode)) == null) {
                        HashMap hashMap = new HashMap();
                        addFMDMParentCodeMaps.put(parentCode, hashMap);
                    }
                    var23_24.put(code, fmmdDto);
                }
                for (String parentCode : addFMDMParentCodeMaps.keySet()) {
                    if ("-".equalsIgnoreCase(parentCode) || !addFMDMCodes.containsKey(parentCode)) continue;
                    addFMDMParentCodes.put(parentCode, addFMDMCodes.get(parentCode));
                }
                masterKeys.setValue(importContext.getEntityCompanyType(), canReadunits);
                entityQuery.setMasterKeys(masterKeys);
                entityQuery.setAuthorityOperations(AuthorityType.None);
                try {
                    IEntityTable entityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
                    Map existCollect = entityTable.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity()));
                    unitDic.clear();
                    Map<Object, Object> writeAuthCollect = null;
                    if (!existCollect.isEmpty()) {
                        for (String string : existCollect.keySet()) {
                            if (unitDic.contains(string)) continue;
                            canWriteunits.add(string);
                            unitDic.add(string);
                        }
                        masterKeys.setValue(importContext.getEntityCompanyType(), canWriteunits);
                        entityQuery.setAuthorityOperations(AuthorityType.Modify);
                        IEntityTable entityTable2 = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
                        writeAuthCollect = entityTable2.getAllRows().stream().collect(Collectors.toMap(IEntityItem::getCode, Function.identity()));
                    } else {
                        writeAuthCollect = new HashMap();
                    }
                    if (canReadunits.size() <= 0) break block41;
                    HashMap<String, String> tempZdmAndKeyMap = new HashMap<String, String>();
                    for (Map.Entry<String, String> entry : uploadEntityZdmKeyMap.entrySet()) {
                        String zdm = entry.getKey();
                        String zdmKey = entry.getValue();
                        tempZdmAndKeyMap.put(zdmKey, zdm);
                    }
                    ArrayList<FMDMDataDTO> arrayList = new ArrayList<FMDMDataDTO>();
                    for (FMDMDataDTO fmmdDto : addBatchOptDTO.getFmdmList()) {
                        String zdmWithOutPeriod;
                        String orgCode = fmmdDto.getValue("CODE").getAsString();
                        String orgTitle = fmmdDto.getValue("NAME").getAsString();
                        String parentCode = fmmdDto.getValue("PARENTCODE").getAsString();
                        if (StringUtils.isEmpty((String)parentCode)) {
                            parentCode = "-";
                        }
                        boolean orgIsError = false;
                        if (existCollect.containsKey(orgCode)) {
                            if (writeAuthCollect.containsKey(orgCode)) {
                                updateBatchFMDMDTO.addFmdmUpdateDTO(fmmdDto);
                            } else {
                                orgIsError = true;
                            }
                        } else if ("-".equalsIgnoreCase(parentCode)) {
                            arrayList.add(fmmdDto);
                        } else if (!"-".equalsIgnoreCase(parentCode)) {
                            if (existCollect.containsKey(parentCode)) {
                                if (writeAuthCollect.containsKey(parentCode)) {
                                    arrayList.add(fmmdDto);
                                } else {
                                    orgIsError = true;
                                }
                            } else if (addFMDMParentCodes.containsKey(parentCode)) {
                                String nParentCode = parentCode;
                                HashSet<String> parentFinds = new HashSet<String>();
                                while (addFMDMParentCodes.containsKey(nParentCode)) {
                                    FMDMDataDTO pFmmdDto = (FMDMDataDTO)addFMDMParentCodes.get(nParentCode);
                                    if (StringUtils.isEmpty((String)(nParentCode = pFmmdDto.getValue("PARENTCODE").getAsString()))) {
                                        nParentCode = "-";
                                        arrayList.add(fmmdDto);
                                    } else if ("-".equalsIgnoreCase(nParentCode)) {
                                        arrayList.add(fmmdDto);
                                    } else if (!"-".equalsIgnoreCase(nParentCode) && existCollect.containsKey(nParentCode)) {
                                        if (writeAuthCollect.containsKey(nParentCode)) {
                                            arrayList.add(fmmdDto);
                                        } else {
                                            orgIsError = true;
                                        }
                                    } else if (!parentFinds.contains(nParentCode)) {
                                        parentFinds.add(nParentCode);
                                        continue;
                                    }
                                    break;
                                }
                            } else {
                                orgIsError = true;
                            }
                        }
                        if (!orgIsError) continue;
                        String zdm = (String)tempZdmAndKeyMap.get(orgCode);
                        if (StringUtils.isNotEmpty((String)zdm)) {
                            uploadEntityZdmKeyMap.remove(zdm);
                        }
                        if (StringUtils.isEmpty((String)(zdmWithOutPeriod = entityKeyCodeMap.get(orgCode)))) {
                            zdmWithOutPeriod = orgCode;
                        }
                        SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u6743\u9650\u65b0\u589e", "noPower_add", zdmWithOutPeriod, orgTitle, orgCode);
                        importContext.recordLog("FMDM", errorItem);
                        importContext.info(logger, "\u65e0\u6743\u9650\u65b0\u589e\uff1a" + orgCode);
                    }
                    addBatchOptDTO.setFmdmList(arrayList);
                }
                catch (Exception e) {
                    importContext.error(logger, e.getMessage(), (Throwable)e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void commitDatas(TaskDataContext importContext, BatchFMDMDTO addBatchOptDTO, BatchFMDMDTO updateBatchOptDTO, BatchFMDMDTO deleteBatchOptDTO, boolean isIncrement, boolean isUpdate, Set<String> mergeZdms, Set<String> insertOrgCodesByMap) throws Exception {
        SingleDataError errorItem;
        Object entityTitle;
        CheckNodeInfo error;
        FMDMCheckFailNodeInfo nodeerror;
        StringBuilder errorMsgs;
        ArrayList resulstDimValueSet = new ArrayList();
        if (!isIncrement && !isUpdate) {
            importContext.info(logger, "\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u65e0\u66f4\u65b0\u6216\u65b0\u589e");
        }
        boolean hasIncrement = false;
        boolean hasUpdate = false;
        if (addBatchOptDTO.getFmdmList().size() > 0 && isIncrement) {
            try {
                importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u63d0\u4ea4\u4e2a\u6570\uff1a" + String.valueOf(addBatchOptDTO.getFmdmList().size()));
                IFMDMUpdateResult addR = this.fmdmDataService.batchAddFMDM(addBatchOptDTO);
                hasIncrement = true;
                HashSet<String> successCodes = new HashSet<String>();
                if (addR.getUpdateKeys() != null) {
                    importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u6210\u529f\u4e2a\u6570\uff1a" + String.valueOf(addR.getUpdateKeys().size()));
                    List insertCodes = addR.getUpdateCodes();
                    if (insertCodes.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (Object entityCode : insertCodes) {
                            successCodes.add((String)entityCode);
                            if (successCodes.size() > 30) continue;
                            sb.append((String)entityCode).append(",");
                        }
                        if (successCodes.size() > 30) {
                            sb.append("...,\u4e2a\u6570" + successCodes.size());
                        }
                        importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u6210\u529f\u5355\u4f4d\u5217\u8868\uff1a" + sb.toString());
                    }
                    if (insertCodes.size() > 0 && insertOrgCodesByMap != null && insertOrgCodesByMap.size() > 0) {
                        List list = importContext.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                        HashMap<String, Object> netMapingUnitList = new HashMap<String, Object>();
                        for (Object unitMapping : list) {
                            if (!StringUtils.isNotEmpty((String)unitMapping.getNetUnitCode()) || netMapingUnitList.containsKey(unitMapping.getNetUnitCode())) continue;
                            netMapingUnitList.put(unitMapping.getNetUnitCode(), unitMapping);
                        }
                        boolean needSaveMap = false;
                        for (String entityCode : insertCodes) {
                            if (insertOrgCodesByMap == null || !insertOrgCodesByMap.contains(entityCode)) continue;
                            String entityKey = addR.getSaveKey(entityCode);
                            if (!netMapingUnitList.containsKey(entityCode)) continue;
                            UnitCustomMapping unitMapping = (UnitCustomMapping)netMapingUnitList.get(entityCode);
                            unitMapping.setNetUnitKey(entityKey);
                            needSaveMap = true;
                        }
                        if (needSaveMap) {
                            this.mappingConfigService.saveEntityUnitCustomMapping(importContext.getConfigKey(), list);
                        }
                    }
                }
                if (addR.getUpdateDimensions() != null && addR.getUpdateDimensions().size() > 0) {
                    resulstDimValueSet.addAll(addR.getUpdateDimensions());
                }
                if (addR != null && addR.getFMDMCheckResult() != null && addR.getFMDMCheckResult().getResults() != null) {
                    importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u9519\u8bef\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(addR.getFMDMCheckResult().getResults().size()));
                    errorMsgs = new StringBuilder();
                    errorMsgs.append("\u65b0\u589e\u5931\u8d25");
                    for (int i = 0; i < addR.getFMDMCheckResult().getResults().size(); ++i) {
                        nodeerror = (FMDMCheckFailNodeInfo)addR.getFMDMCheckResult().getResults().get(i);
                        importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u5931\u8d25\u5b57\u6bb5\u4fe1\u606f\uff1a" + nodeerror.getFieldCode() + "," + nodeerror.getFieldTitle());
                        for (int j = 0; j < nodeerror.getNodes().size(); ++j) {
                            error = (CheckNodeInfo)nodeerror.getNodes().get(j);
                            importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u5931\u8d25\u8be6\u7ec6\u4fe1\u606f\uff1a" + error.getType() + "," + error.getContent());
                            if (j > 100) break;
                            if (j != 0) continue;
                            errorMsgs.append(",").append(error.getContent());
                        }
                        if (i > 40) break;
                    }
                    if (addR.getFMDMCheckResult().getResults().size() > 0) {
                        for (FMDMDataDTO fmdmDTO : addBatchOptDTO.getFmdmList()) {
                            String entityCode = fmdmDTO.getValue("CODE").getAsString();
                            entityTitle = fmdmDTO.getValue("TITLE").getAsString();
                            if (successCodes.contains(entityCode)) continue;
                            errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", errorMsgs.toString(), "addEntityError", entityCode, (String)entityTitle, entityCode);
                            importContext.recordLog("FMDM", errorItem);
                            importContext.info(logger, entityCode + " " + (String)entityTitle + " " + errorMsgs.toString());
                            if (!importContext.getEntityKeyZdmMap().containsKey(entityCode)) continue;
                            String zdm = (String)importContext.getEntityKeyZdmMap().get(entityCode);
                            importContext.getUploadEntityZdmKeyMap().remove(zdm);
                        }
                    }
                }
                importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u65b0\u589e,\u5355\u4f4d\u603b\u6570" + String.valueOf(addBatchOptDTO.getFmdmList().size()));
            }
            catch (Exception ex) {
                importContext.error(logger, ex.getMessage(), (Throwable)ex);
                throw ex;
            }
        }
        if (updateBatchOptDTO.getFmdmList().size() > 0 && isUpdate) {
            try {
                importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u63d0\u4ea4\u4e2a\u6570\uff1a" + String.valueOf(updateBatchOptDTO.getFmdmList().size()));
                IFMDMUpdateResult updateR = this.fmdmDataService.batchUpdateFMDM(updateBatchOptDTO);
                hasUpdate = true;
                List updateKeys = updateR.getUpdateKeys();
                if (updateKeys != null) {
                    importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u6210\u529f\u4e2a\u6570\uff1a" + String.valueOf(updateKeys.size()));
                }
                if (updateR.getUpdateDimensions() != null && updateR.getUpdateDimensions().size() > 0) {
                    resulstDimValueSet.addAll(updateR.getUpdateDimensions());
                }
                if (updateR != null && updateR.getFMDMCheckResult() != null && updateR.getFMDMCheckResult().getResults() != null) {
                    importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u9519\u8bef\u4e2a\u6570\uff1a" + String.valueOf(updateR.getFMDMCheckResult().getResults().size()));
                    errorMsgs = new StringBuilder();
                    errorMsgs.append("\u66f4\u65b0\u5931\u8d25");
                    for (int i = 0; i < updateR.getFMDMCheckResult().getResults().size(); ++i) {
                        nodeerror = (FMDMCheckFailNodeInfo)updateR.getFMDMCheckResult().getResults().get(i);
                        importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u5931\u8d25\u5b57\u6bb5\u4fe1\u606f\uff1a" + nodeerror.getFieldCode() + "," + nodeerror.getFieldTitle());
                        errorMsgs.append(",\u5b57\u6bb5").append(nodeerror.getFieldCode());
                        for (int j = 0; j < nodeerror.getNodes().size(); ++j) {
                            error = (CheckNodeInfo)nodeerror.getNodes().get(j);
                            importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u5931\u8d25\u8be6\u7ec6\u4fe1\u606f\uff1a" + error.getType() + "," + error.getContent());
                            if (j > 100) break;
                            if (j != 0) continue;
                            errorMsgs.append(",").append(error.getContent());
                        }
                        if (i > 40) break;
                    }
                    if (updateR.getFMDMCheckResult().getResults().size() > 0) {
                        for (FMDMDataDTO fmdmDTO : updateBatchOptDTO.getFmdmList()) {
                            String entityCode = fmdmDTO.getValue("CODE").getAsString();
                            entityTitle = fmdmDTO.getValue("TITLE").getAsString();
                            errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", errorMsgs.toString(), "updateEntityError", entityCode, (String)entityTitle, entityCode);
                            importContext.recordLog("FMDM", errorItem);
                            importContext.info(logger, entityCode + " " + (String)entityTitle + " " + errorMsgs.toString());
                        }
                    }
                }
                importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u66f4\u65b0,\u5355\u4f4d\u603b\u6570" + String.valueOf(updateBatchOptDTO.getFmdmList().size()));
            }
            catch (Exception ex) {
                importContext.error(logger, ex.getMessage(), (Throwable)ex);
                throw ex;
            }
        }
        if (deleteBatchOptDTO.getFmdmList().size() > 0) {
            try {
                for (FMDMDataDTO fmdmData : deleteBatchOptDTO.getFmdmList()) {
                    this.fmdmDataService.delete(fmdmData);
                }
                importContext.info(logger, "\u5b9e\u4f53\u6570\u636e\u5220\u9664\uff1a," + String.valueOf(deleteBatchOptDTO.getFmdmList().size()));
            }
            catch (Exception ex) {
                importContext.error(logger, ex.getMessage(), (Throwable)ex);
                throw ex;
            }
        }
        if (resulstDimValueSet.size() > 0 && importContext.getDimEntityCache().getEntitySingleDims().size() > 0) {
            List sourceDimList = this.singleDimService.getDimensionMaspFromSet(resulstDimValueSet);
            ArrayList mapNoUnitList = new ArrayList();
            ArrayList downLoadList = new ArrayList();
            ArrayList entityKeys2 = new ArrayList();
            HashMap unitDic = new HashMap();
            this.singleDimService.doAnalSingleUnitDims(importContext, sourceDimList, true, mapNoUnitList, downLoadList, entityKeys2, unitDic);
            HashMap<String, DimensionValueSet> tranDimDic = new HashMap<String, DimensionValueSet>();
            for (DimensionValueSet tranDim : resulstDimValueSet) {
                String orgCode = (String)tranDim.getValue(importContext.getEntityCompanyType());
                tranDimDic.put(orgCode, tranDim);
            }
            Map oldTranDimDic = importContext.getDimEntityCache().getEntityTranDims();
            if (oldTranDimDic != null && !oldTranDimDic.isEmpty() && !isUpdate) {
                for (DimensionValueSet tranDim : oldTranDimDic.values()) {
                    String orgCode = (String)tranDim.getValue(importContext.getEntityCompanyType());
                    if (tranDimDic.containsKey(orgCode)) continue;
                    tranDimDic.put(orgCode, tranDim);
                }
            }
            importContext.getDimEntityCache().setEntityTranDims(tranDimDic);
        }
        if (hasUpdate || hasIncrement) {
            importContext.updatImportedFormNumAsyn(1);
        }
        if (this.entityMergeServie != null && mergeZdms.size() > 0) {
            this.entityMergeServie.commitImoprtFMDM();
        }
    }

    private String getFMFMDFieldValue(TaskDataContext importContext, String dbfFieldName, String dbFieldValue, String tempQYDM, IFMDMAttribute field, SingleFileFmdmInfo fMTable, SingleFileFieldInfo mapField, DataRow dbfRow, FMDMDataDTO entityRow) {
        block58: {
            if (dbfFieldName.equalsIgnoreCase(fMTable.getDWDMField())) {
                if (StringUtils.isNotEmpty((String)tempQYDM)) {
                    dbFieldValue = this.singleTransService.toUpper(importContext, tempQYDM);
                } else if (StringUtils.isNotEmpty((String)dbFieldValue)) {
                    dbFieldValue = this.singleTransService.toUpper(importContext, dbFieldValue);
                }
            } else if (dbfFieldName.equalsIgnoreCase(fMTable.getBBLXField()) && StringUtils.isNotEmpty((String)dbFieldValue)) {
                dbFieldValue = this.singleTransService.toUpper(importContext, dbFieldValue);
            }
            if (StringUtils.isNotEmpty((String)dbFieldValue)) {
                if (field.getColumnType() == ColumnModelType.BIGDECIMAL || field.getColumnType() == ColumnModelType.DOUBLE) {
                    if (dbFieldValue.contains(".")) {
                        if (dbFieldValue.length() > field.getPrecision()) {
                            String numCode1 = dbFieldValue.substring(0, dbFieldValue.indexOf("."));
                            String numCode2 = dbFieldValue.substring(dbFieldValue.indexOf(".") + 1, dbFieldValue.length());
                            if (StringUtils.isNotEmpty((String)numCode1) && numCode1.length() > field.getPrecision() - field.getDecimal()) {
                                numCode1 = numCode1.substring(0, field.getPrecision() - field.getDecimal());
                            }
                            if (StringUtils.isNotEmpty((String)numCode2) && numCode2.length() > field.getDecimal()) {
                                numCode2 = numCode2.substring(0, field.getDecimal());
                            }
                            dbFieldValue = numCode1 + "." + numCode2;
                        }
                    } else if (dbFieldValue.length() > field.getPrecision() - field.getDecimal() && field.getPrecision() - field.getDecimal() > 0) {
                        dbFieldValue = dbFieldValue.substring(0, field.getPrecision() - field.getDecimal());
                    }
                } else if (field.getColumnType() == ColumnModelType.INTEGER && dbFieldValue.length() > field.getPrecision()) {
                    dbFieldValue = dbFieldValue.substring(0, field.getPrecision());
                } else if (mapField != null && mapField.getFieldType() == FieldType.FIELD_TYPE_FILE) {
                    String fieldValue = dbFieldValue;
                    try {
                        FormDefine form;
                        if (!StringUtils.isNotEmpty((String)fieldValue)) break block58;
                        String zdm = dbfRow.getValueString(0);
                        SingleFieldFileInfo fieldFileInfo = new SingleFieldFileInfo();
                        fieldFileInfo.setDataSchemeKey(importContext.getDataSchemeKey());
                        if (field != null) {
                            fieldFileInfo.setFieldKey(field.getZBKey());
                        }
                        if (StringUtils.isNotEmpty((String)importContext.getFmdmFormKey()) && (form = this.runtimeView.queryFormById(importContext.getFmdmFormKey())) != null) {
                            fieldFileInfo.setFormKey(form.getKey());
                            fieldFileInfo.setFormCode(form.getFormCode());
                        }
                        fieldFileInfo.setFormSchemeKey(importContext.getFormSchemeKey());
                        if (entityRow.getDimensionValueSet() != null) {
                            HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                            for (int i = 0; i < entityRow.getDimensionValueSet().size(); ++i) {
                                DimensionValue dim = new DimensionValue();
                                dim.setName(entityRow.getDimensionValueSet().getName(i));
                                dim.setValue((String)entityRow.getDimensionValueSet().getValue(i));
                                dim.setType(0);
                                dimensionSet.put(dim.getName(), dim);
                            }
                            if (!dimensionSet.containsKey(importContext.getEntityCompanyType())) {
                                DimensionValue dim = new DimensionValue();
                                dim.setName(importContext.getEntityCompanyType());
                                if (StringUtils.isNotEmpty((String)entityRow.getEntityKey())) {
                                    dim.setValue(entityRow.getEntityKey());
                                } else {
                                    AbstractData codeValue = entityRow.getValue("CODE");
                                    String entityCode = null;
                                    if (codeValue != null) {
                                        entityCode = codeValue.getAsString();
                                    }
                                    if (StringUtils.isEmpty(entityCode)) {
                                        entityCode = (String)importContext.getUploadEntityZdmKeyMap().get(zdm);
                                    }
                                    dim.setValue(entityCode);
                                }
                                dim.setType(0);
                                dimensionSet.put(dim.getName(), dim);
                            }
                            if (!dimensionSet.containsKey(importContext.getEntityDateType())) {
                                DimensionValue dim = new DimensionValue();
                                dim.setName(importContext.getEntityDateType());
                                dim.setValue(importContext.getMapNetPeriodCode());
                                dim.setType(0);
                                dimensionSet.put(dim.getName(), dim);
                            }
                            fieldFileInfo.setDimensionSet(dimensionSet);
                        }
                        fieldFileInfo.setTaskKey(importContext.getTaskKey());
                        fieldFileInfo.setZdm(zdm);
                        String groupTypeCode = null;
                        if (fieldValue.contains(";")) {
                            String[] newValues = fieldValue.split(";");
                            if (newValues.length > 0) {
                                fieldValue = newValues[0];
                            }
                            if (newValues.length > 2) {
                                groupTypeCode = newValues[2];
                            }
                        }
                        try {
                            if (StringUtils.isNotEmpty(groupTypeCode) && "T=1".equalsIgnoreCase(groupTypeCode)) {
                                dbFieldValue = fieldValue;
                            } else if (StringUtils.isNotEmpty(groupTypeCode) && "T=2".equalsIgnoreCase(groupTypeCode)) {
                                fieldFileInfo.setGroupType(2);
                                fieldFileInfo.setGroupKey(fieldValue);
                                dbFieldValue = this.getFJFieldValueByFileNoExt(importContext, fieldFileInfo, zdm, fieldValue, importContext.getTaskDataPath());
                            } else {
                                fieldFileInfo.setGroupType(0);
                                dbFieldValue = this.getFJFieldValueByFileNoExt(importContext, fieldFileInfo, zdm, fieldValue, importContext.getTaskDataPath());
                            }
                        }
                        catch (SingleDataException ex) {
                            importContext.error(logger, "\u5bfc\u5165\u9644\u4ef6\u6307\u6807" + dbfFieldName + ",\u5355\u4f4d\uff1a" + zdm + ",\u5f02\u5e38\uff1a" + ex.getMessage(), (Throwable)ex);
                            SingleDataCorpException re = new SingleDataCorpException(ex.getMessage(), (Throwable)ex);
                            re.setFieldInfo(zdm, fieldValue, dbfFieldName);
                            throw re;
                        }
                    }
                    catch (SingleDataCorpException e) {
                        importContext.error(logger, e.getMessage(), (Throwable)e);
                    }
                } else if (mapField != null && mapField.getFieldType() == FieldType.FIELD_TYPE_PICTURE) {
                    String fieldValue = dbFieldValue;
                    try {
                        if (StringUtils.isNotEmpty((String)fieldValue)) {
                            String picFilePath = TaskFileDataOperateUtil.getSingleFieldImgByZdm(importContext, importContext.getTaskDataPath(), fieldValue, null);
                            if (PathUtil.getFileExists((String)picFilePath)) {
                                FormDefine form;
                                SingleFieldFileInfo fieldFileInfo = new SingleFieldFileInfo();
                                fieldFileInfo.setDataSchemeKey(importContext.getDataSchemeKey());
                                if (field != null) {
                                    fieldFileInfo.setFieldKey(field.getZBKey());
                                }
                                if (StringUtils.isNotEmpty((String)importContext.getFmdmFormKey()) && (form = this.runtimeView.queryFormById(importContext.getFmdmFormKey())) != null) {
                                    fieldFileInfo.setFormKey(form.getKey());
                                    fieldFileInfo.setFormCode(form.getFormCode());
                                }
                                fieldFileInfo.setFormSchemeKey(importContext.getFormSchemeKey());
                                if (entityRow.getDimensionValueSet() != null) {
                                    HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                                    for (int i = 0; i < entityRow.getDimensionValueSet().size(); ++i) {
                                        DimensionValue dim = new DimensionValue();
                                        dim.setName(entityRow.getDimensionValueSet().getName(i));
                                        dim.setValue((String)entityRow.getDimensionValueSet().getValue(i));
                                        dim.setType(0);
                                        dimensionSet.put(dim.getName(), dim);
                                    }
                                    fieldFileInfo.setDimensionSet(dimensionSet);
                                }
                                fieldFileInfo.setTaskKey(importContext.getTaskKey());
                                fieldFileInfo.setZdm(dbfRow.getValueString(0));
                                ArrayList<String> singleFiles = new ArrayList<String>();
                                singleFiles.add(picFilePath);
                                String groupFileKey = UUID.randomUUID().toString();
                                fieldFileInfo.setGroupKey(groupFileKey);
                                dbFieldValue = groupFileKey = this.singleAttachService.uploadSingleFiles(singleFiles, fieldFileInfo);
                            } else {
                                dbFieldValue = null;
                            }
                            break block58;
                        }
                        dbFieldValue = null;
                    }
                    catch (SingleFileException ex) {
                        importContext.error(logger, ex.getMessage(), (Throwable)ex);
                    }
                    catch (SingleDataException e) {
                        importContext.error(logger, e.getMessage(), (Throwable)e);
                    }
                    catch (IOException e) {
                        importContext.error(logger, e.getMessage(), (Throwable)e);
                    }
                } else if (mapField != null && mapField.getFieldType() == FieldType.FIELD_TYPE_TEXT) {
                    try {
                        String fieldValue = dbFieldValue;
                        if (StringUtils.isNotEmpty((String)fieldValue)) {
                            dbFieldValue = TaskFileDataOperateUtil.getSingleFieldTextByZdm(importContext, importContext.getTaskDataPath(), fieldValue, null);
                        }
                    }
                    catch (SingleFileException ex) {
                        importContext.error(logger, ex.getMessage(), (Throwable)ex);
                    }
                    catch (IOException e) {
                        importContext.error(logger, e.getMessage(), (Throwable)e);
                    }
                }
            }
        }
        return dbFieldValue;
    }

    private String getFJFieldValueByFileNoExt(TaskDataContext context, SingleFieldFileInfo fieldFileInfo, String zdm, String fileNoExt, String taskDataPath) throws SingleDataException {
        String dbFieldValue = null;
        if (context.getFjUploadMode() == 0) {
            List<SingleFieldFileInfo> singleFiles = TaskFileDataOperateUtil.getSingleFileInfoFormFileByZdm(context, taskDataPath, fileNoExt, zdm);
            if (null != singleFiles && !singleFiles.isEmpty()) {
                ArrayList<SingleFieldFileInfo> singleFiles2 = new ArrayList<SingleFieldFileInfo>();
                String groupFileKey = UUID.randomUUID().toString();
                if (fieldFileInfo.getGroupType() == 2 && StringUtils.isNotEmpty((String)fieldFileInfo.getGroupKey())) {
                    groupFileKey = fieldFileInfo.getGroupKey();
                }
                singleFiles2.addAll(singleFiles);
                fieldFileInfo.setGroupKey(groupFileKey);
                SingleAttachmentResult uploadResult = this.singleAttachService.uploadSingleFileInfosR(singleFiles2, fieldFileInfo);
                if (!uploadResult.isSuccess()) {
                    String entityKey = fieldFileInfo.getEntityKey();
                    if (StringUtils.isEmpty((String)entityKey)) {
                        if (fieldFileInfo.getDimensionSet() != null && StringUtils.isNotEmpty((String)context.getEntityCompanyType())) {
                            DimensionValue dim = (DimensionValue)fieldFileInfo.getDimensionSet().get(context.getEntityCompanyType());
                            if (dim != null) {
                                entityKey = dim.getValue();
                            }
                        } else if (StringUtils.isNotEmpty((String)fieldFileInfo.getZdm()) && context.getUploadEntityZdmKeyMap().containsKey(fieldFileInfo.getZdm())) {
                            entityKey = (String)context.getUploadEntityZdmKeyMap().get(fieldFileInfo.getZdm());
                        }
                    }
                    String errorMsg = this.getFailFileMessage(uploadResult);
                    SingleDataError errorItem = new SingleDataError("", fieldFileInfo.getFormCode(), errorMsg, "importFileFail", entityKey, null, entityKey);
                    errorItem.setFormKey(fieldFileInfo.getFormKey());
                    errorItem.setSingleCode(fieldFileInfo.getZdm());
                    context.recordLog(fieldFileInfo.getFormCode(), errorItem);
                    context.info(logger, fieldFileInfo.getZdm() + " \u8868\u5355\uff1a" + fieldFileInfo.getFormCode() + " \u5b57\u6bb5\uff1a" + fieldFileInfo.getFieldKey() + " \u4e0a\u4f20\u9644\u4ef6\u5931\u8d25\uff1a" + errorMsg);
                } else {
                    context.updateAttachFileNumAsyn(singleFiles2.size());
                }
                dbFieldValue = groupFileKey;
            } else {
                dbFieldValue = null;
            }
        } else if (context.getFjUploadMode() == 1) {
            String groupFileKey = UUID.randomUUID().toString();
            if (fieldFileInfo.getGroupType() == 2 && StringUtils.isNotEmpty((String)fieldFileInfo.getGroupKey())) {
                groupFileKey = fieldFileInfo.getGroupKey();
            }
            fieldFileInfo.setGroupKey(groupFileKey);
            fieldFileInfo.setFjPath("DATA/SYS_DOC");
            fieldFileInfo.setFileName(fileNoExt + ".ZIP");
            context.addFieldFileInfo(fieldFileInfo);
            dbFieldValue = groupFileKey;
        }
        return dbFieldValue;
    }

    private String getFailFileMessage(SingleAttachmentResult uploadResult) {
        StringBuilder errorMsgs = new StringBuilder();
        errorMsgs.append("\u9644\u4ef6\u5bfc\u5165\u5931\u8d25");
        if (StringUtils.isNotEmpty((String)uploadResult.getMessage())) {
            errorMsgs.append(",").append(uploadResult.getMessage());
        }
        if (!uploadResult.getFailedFileList().isEmpty()) {
            int aNum = 0;
            logger.info("\u9644\u4ef6\u5bfc\u5165\u5931\u8d25\uff0c\u6587\u4ef6\u6570\uff1a" + uploadResult.getFailedFileList().size());
            for (SingleAttachmentFailFile failFile : uploadResult.getFailedFileList()) {
                errorMsgs.append(",").append(failFile.getFileName());
                if (++aNum <= 10) continue;
                errorMsgs.append(",").append("...");
                break;
            }
        }
        return errorMsgs.toString();
    }

    private String getBJCEEntityCode(String entityCode, String qydm, String bblxCode, String zdmWithoutBBLXPeriod, String fjdZdm, String fjdCode, DataRow dbfRow, SingleFileFmdmInfo fMTable, IDbfTable dbf, Map<String, DataRow> dbfZdmRowMap, UnitNatureGetter unitNatureGetter) {
        UnitNature unitNature;
        if (StringUtils.isNotEmpty((String)bblxCode) && unitNatureGetter != null && ((unitNature = unitNatureGetter.getUnitNature(bblxCode)) == UnitNature.JCFHB || unitNature == UnitNature.JTCEB)) {
            if (StringUtils.isNotEmpty((String)fjdZdm) && dbfZdmRowMap.containsKey(fjdZdm)) {
                DataRow fjdDbfRow = dbfZdmRowMap.get(fjdZdm);
                ArrayList<String> loadFieldNames = new ArrayList<String>();
                loadFieldNames.add(fMTable.getDWDMField());
                loadFieldNames.add(fMTable.getBBLXField());
                List loadFieldIndexs = dbf.getFieldIndexs(loadFieldNames);
                loadFieldIndexs.add(0, 0);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRowByIndexs(fjdDbfRow, loadFieldIndexs);
                }
                String fjdQydm = fjdDbfRow.getValueString(fMTable.getDWDMField());
                String fjdBblx = fjdDbfRow.getValueString(fMTable.getBBLXField());
                UnitNature fjdUnitNature = unitNatureGetter.getUnitNature(fjdBblx);
                if (fjdUnitNature != null && fjdUnitNature == UnitNature.JTHZB && StringUtils.isNotEmpty((String)fjdQydm) && fjdQydm.equalsIgnoreCase(qydm)) {
                    entityCode = this.formTypeApplyService.getAutoGenUnitCode(fjdCode, unitNature);
                }
                if (!dbf.isHasLoadAllRec()) {
                    dbf.clearDataRow(fjdDbfRow);
                }
            } else if (StringUtils.isNotEmpty((String)fjdZdm) && fjdZdm.contains(qydm) && StringUtils.isNotEmpty((String)fjdZdm) && StringUtils.isNotEmpty((String)qydm) && StringUtils.isNotEmpty((String)fMTable.getBBLXField())) {
                if (fjdZdm.length() == qydm.length() + 1) {
                    String fjdBblx = fjdZdm.substring(fjdZdm.length() - 1, fjdZdm.length());
                    UnitNature fjdUnitNature = unitNatureGetter.getUnitNature(fjdBblx);
                    if (fjdUnitNature != null && fjdUnitNature == UnitNature.JTHZB) {
                        entityCode = this.formTypeApplyService.getAutoGenUnitCode(fjdCode, unitNature);
                    }
                } else {
                    entityCode = this.formTypeApplyService.getAutoGenUnitCode(fjdCode, unitNature);
                }
            }
        }
        return entityCode;
    }

    private String getParentCoddeByFJD(TaskDataContext importContext, String fjdZdm, Map<String, RepeatEntityNode> repeatNodes, int peirodLength, int periodIndex, int zmdLength, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig, Map<String, String> entityCodeKeyMap, Map<String, String> entityZdmAndKeyMap, Map<String, String> zdmWithoutPeriodTempMap, Map<String, List<FMDMDataDTO>> tempFJDRows, Map<String, String> zdmTempMap, Map<String, SingleDataRow> dbfZdmAndRowMap, FMDMDataDTO entityRow1, boolean corpMapUseExp, Map<String, UnitCustomMapping> singleMapingUnitList) {
        String tempFjdQYDM;
        RepeatEntityNode repeatFjdNode;
        if (repeatNodes.containsKey(fjdZdm = fjdZdm.toUpperCase()) && (repeatFjdNode = repeatNodes.get(fjdZdm)).getRepeatMode() == 2 && StringUtils.isNotEmpty((String)(tempFjdQYDM = repeatFjdNode.getTempQYDM())) && importContext.getEntityCache().getSingleQYDMIndex() >= 0) {
            fjdZdm = importContext.getEntityCache().getNewZdmByQydm(fjdZdm, tempFjdQYDM);
        }
        String fjdZdmWithoutPeriod = fjdZdm;
        if (peirodLength > 0 && fjdZdm.length() == zmdLength) {
            fjdZdmWithoutPeriod = fjdZdm.substring(0, periodIndex - 1) + fjdZdm.substring(periodIndex + peirodLength - 1, fjdZdm.length());
        } else if (StringUtils.isNotEmpty((String)fjdZdm) && fjdZdm.length() < zmdLength && zmdLength > 0) {
            try {
                byte[] dataByte = fjdZdm.getBytes("gbk");
                if (dataByte.length == zmdLength && importContext.getEntityCache().getSingleQYDMIndex() >= 0 && importContext.getEntityCache().getSingleQYDMLen() > 0 && importContext.getEntityCache().getSingleBBLXIndex() == zmdLength - 1) {
                    String bblx = fjdZdm.substring(fjdZdm.length() - 1, fjdZdm.length());
                    String qydm = fjdZdm.substring(0, fjdZdm.length() - 1);
                    if (peirodLength > 0) {
                        qydm = qydm.substring(0, qydm.length() - peirodLength);
                    }
                    for (int len = zmdLength - fjdZdm.length(); len > 0; --len) {
                        qydm = qydm + " ";
                    }
                    fjdZdmWithoutPeriod = qydm + bblx;
                }
            }
            catch (UnsupportedEncodingException e) {
                importContext.info(logger, "\u8bfb\u53d6\u5b57\u7b26gbk\u6570\u7ec4\u51fa\u9519\uff1a" + e.getMessage() + "," + fjdZdm);
            }
        }
        String jfdEntityJcm = this.getJcmbyZdm(importContext, fjdZdm, fjdZdmWithoutPeriod, dbfZdmFjdMap, autoCodeCoinfig);
        String fjdKey = null;
        String fjdCode = fjdZdmWithoutPeriod;
        String fjdEntityAutoExCode = fjdZdmWithoutPeriod + jfdEntityJcm;
        DataEntityInfo parentEntityInfo = null;
        if (corpMapUseExp) {
            if (dbfZdmAndRowMap.containsKey(fjdZdm)) {
                String singleMapValue = dbfZdmAndRowMap.get(fjdZdm).getMapExpValue();
                parentEntityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityExpCodeFinder().get(singleMapValue);
            }
        } else {
            parentEntityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(fjdEntityAutoExCode);
        }
        if (StringUtils.isNotEmpty((String)fjdEntityAutoExCode) && parentEntityInfo != null) {
            fjdCode = fjdKey = parentEntityInfo.getEntityKey();
        } else if (singleMapingUnitList.containsKey(fjdZdmWithoutPeriod)) {
            UnitCustomMapping unitMapping = singleMapingUnitList.get(fjdZdmWithoutPeriod);
            fjdCode = fjdKey = unitMapping.getNetUnitKey();
        } else if (entityCodeKeyMap.containsKey(fjdZdmWithoutPeriod)) {
            fjdCode = fjdKey = entityCodeKeyMap.get(fjdZdmWithoutPeriod);
        } else if (entityZdmAndKeyMap.containsKey(fjdZdm)) {
            fjdCode = fjdKey = entityZdmAndKeyMap.get(fjdZdm);
        } else if (zdmWithoutPeriodTempMap.containsKey(fjdZdmWithoutPeriod)) {
            fjdKey = zdmWithoutPeriodTempMap.get(fjdZdmWithoutPeriod);
            this.addTempFjdToList(fjdKey, entityRow1, tempFJDRows);
        } else if (zdmTempMap.containsKey(fjdZdm)) {
            fjdKey = zdmTempMap.get(fjdZdm);
            this.addTempFjdToList(fjdKey, entityRow1, tempFJDRows);
        }
        if (StringUtils.isEmpty((String)fjdKey) && dbfZdmAndRowMap.containsKey(fjdZdm)) {
            fjdKey = UUID.randomUUID().toString().toUpperCase();
            zdmWithoutPeriodTempMap.put(fjdZdmWithoutPeriod, fjdKey);
            zdmTempMap.put(fjdZdm, fjdKey);
            this.addTempFjdToList(fjdKey, entityRow1, tempFJDRows);
        }
        return fjdCode;
    }

    private void addTempFjdToList(String fjdKey, FMDMDataDTO entityRow1, Map<String, List<FMDMDataDTO>> tempFJDRows) {
        List<Object> list = null;
        if (tempFJDRows.containsKey(fjdKey)) {
            list = tempFJDRows.get(fjdKey);
        } else {
            list = new ArrayList();
            tempFJDRows.put(fjdKey, list);
        }
        list.add(entityRow1);
    }

    private List<RepeatFormNode> getReaptFormNodes(TaskDataContext importContext) throws Exception {
        ArrayList<RepeatFormNode> repeatForms = new ArrayList<RepeatFormNode>();
        List forms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(importContext.getFormSchemeKey());
        block0: for (FormDefine form : forms) {
            List fieldKeys;
            RepeatFormNode repeatForm = new RepeatFormNode();
            repeatForm.setFormCode(form.getFormCode());
            repeatForm.setFormKey(form.getKey());
            repeatForm.setFormTitle(form.getTitle());
            repeatForm.setRepeatMode(1);
            repeatForm.setFormType(0);
            List dataRegions = this.runTimeAuthViewController.getAllRegionsInForm(form.getKey());
            if (dataRegions.size() > 1) {
                repeatForm.setFormType(1);
            }
            repeatForms.add(repeatForm);
            if (form.getFormType() == FormType.FORM_TYPE_NEWFMDM || (fieldKeys = this.runTimeAuthViewController.getFieldKeysInForm(form.getKey())) == null) continue;
            for (String fieldKey : fieldKeys) {
                DataField field = this.dataSchemeSevice.getDataField(fieldKey);
                if (field == null || field.getDataFieldType() != DataFieldType.FILE) continue;
                repeatForm.setFormType(2);
                continue block0;
            }
        }
        return repeatForms;
    }

    private List<FMDMDataDTO> getSortNodes(List<FMDMDataDTO> fmdmList) {
        ArrayList<FMDMDataDTO> tempSorts = new ArrayList<FMDMDataDTO>();
        HashMap<String, List<FMDMDataDTO>> tempParentDic = new HashMap<String, List<FMDMDataDTO>>();
        HashMap<String, FMDMDataDTO> tempCodeDic = new HashMap<String, FMDMDataDTO>();
        for (FMDMDataDTO fmdmData : fmdmList) {
            String parentCode = fmdmData.getValue("PARENTCODE").getAsString();
            List<FMDMDataDTO> tempParents = null;
            if (tempParentDic.containsKey(parentCode)) {
                tempParents = (List)tempParentDic.get(parentCode);
            } else {
                tempParents = new ArrayList();
                tempParentDic.put(parentCode, tempParents);
            }
            tempParents.add(fmdmData);
            tempCodeDic.put(fmdmData.getValue("CODE").getAsString(), fmdmData);
        }
        ArrayList tempRoots = new ArrayList();
        for (String code : tempParentDic.keySet()) {
            if (tempCodeDic.containsKey(code)) continue;
            tempRoots.addAll((Collection)tempParentDic.get(code));
        }
        for (FMDMDataDTO fmdmData : tempRoots) {
            tempSorts.add(fmdmData);
            this.sortNodes(tempParentDic, fmdmData, tempSorts);
        }
        return tempSorts;
    }

    private void sortNodes(Map<String, List<FMDMDataDTO>> tempParentDic, FMDMDataDTO fmdmData, List<FMDMDataDTO> tempSorts) {
        String code = fmdmData.getValue("CODE").getAsString();
        if (tempParentDic.containsKey(code)) {
            List<FMDMDataDTO> tempChilds = tempParentDic.get(code);
            for (FMDMDataDTO aData : tempChilds) {
                tempSorts.add(aData);
                this.sortNodes(tempParentDic, aData, tempSorts);
            }
        }
    }

    private String getJcmbyZdm(TaskDataContext importContext, String zdm, String zdmWithOutPeriod, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig) {
        String entityJcm = "";
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
            if (autoCodeCoinfig.getCodeMapping().containsKey(zdmWithOutPeriod)) {
                entityJcm = (String)autoCodeCoinfig.getCodeMapping().get(zdmWithOutPeriod);
            } else {
                String fjdZdm = dbfZdmFjdMap.get(zdm);
                if (StringUtils.isNotEmpty((String)fjdZdm)) {
                    entityJcm = this.getJcm(importContext, zdm, fjdZdm, dbfZdmFjdMap, autoCodeCoinfig);
                }
                if (StringUtils.isEmpty((String)entityJcm) && autoCodeCoinfig.getCodeMapping().containsKey("\u9ed8\u8ba4\u503c")) {
                    entityJcm = (String)autoCodeCoinfig.getCodeMapping().get("\u9ed8\u8ba4\u503c");
                }
            }
        }
        return entityJcm;
    }

    private String getJcm(TaskDataContext importContext, String firstzdm, String zdm, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig) {
        String jcm = "";
        if (!firstzdm.equalsIgnoreCase(zdm)) {
            String zmdOutPeriodCode = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(zdm);
            if (autoCodeCoinfig.getCodeMapping().containsKey(zmdOutPeriodCode)) {
                jcm = (String)autoCodeCoinfig.getCodeMapping().get(zmdOutPeriodCode);
            } else {
                String fjdZdm = dbfZdmFjdMap.get(zdm);
                if (StringUtils.isNotEmpty((String)fjdZdm)) {
                    String fjdOutPeriodCode = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(fjdZdm);
                    jcm = autoCodeCoinfig.getCodeMapping().containsKey(fjdOutPeriodCode) ? (String)autoCodeCoinfig.getCodeMapping().get(fjdOutPeriodCode) : this.getJcm(importContext, firstzdm, fjdZdm, dbfZdmFjdMap, autoCodeCoinfig);
                }
            }
        }
        return jcm;
    }

    @Override
    public String getType() {
        return "FMDM";
    }

    private void setOtherDimValue(TaskDataContext importContext, DimensionValueSet dimensionValueSet) {
        if (importContext.getOtherDims().size() > 0) {
            for (DimensionValue dim : importContext.getOtherDims().values()) {
                dimensionValueSet.setValue(dim.getName(), (Object)dim.getValue());
            }
        }
    }

    private void setSingleDimValue(TaskDataContext importContext, DimensionValueSet dimensionValueSet, Object dimValue) {
        if (importContext.getDimEntityCache().getEntitySingleDims().size() > 0) {
            for (String dimName : importContext.getDimEntityCache().getEntitySingleDims()) {
                dimensionValueSet.setValue(dimName, dimValue);
            }
        }
    }

    private void buidSingleEnityDataFromParam(TaskDataContext importContext) throws Exception {
        RepeatImportParam jioRepeatParam;
        SingleFileTableInfo singeleTable = null;
        if (importContext.getMapingCache().isMapConfig()) {
            singeleTable = importContext.getMapingCache().getFmdmInfo();
            SingleFileFmdmInfo fMTable = (SingleFileFmdmInfo)singeleTable;
            HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
            HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
            for (SingleFileFieldInfo field : singeleTable.getRegion().getFields()) {
                mapNetFieldList.put(field.getNetFieldCode(), field);
                mapSingleFieldList.put(field.getFieldCode(), field);
            }
            int zmdLength = 0;
            int periodOutLen = 0;
            int qydmIndex = 0;
            int bblxIdex = 0;
            String periodCode = "";
            HashMap<String, String> fieldValueMap = new HashMap<String, String>();
            ArrayList<SingleFileFieldInfo> singeOtherFieldList = new ArrayList<SingleFileFieldInfo>();
            for (String code : fMTable.getZdmFieldCodes()) {
                if (!mapSingleFieldList.containsKey(code)) continue;
                SingleFileFieldInfo singleField = (SingleFileFieldInfo)mapSingleFieldList.get(code);
                zmdLength += singleField.getFieldSize();
                if (code.equalsIgnoreCase(fMTable.getPeriodField())) {
                    periodCode = this.taskDataService.getSinglePeriodCode(importContext, importContext.getNetPeriodCode(), singleField.getFieldSize());
                    fieldValueMap.put(singleField.getFieldCode(), periodCode);
                    importContext.setMapCurrentPeriod(periodCode);
                    importContext.getEntityCache().setSinglePeriodLen(singleField.getFieldSize());
                    importContext.getEntityCache().setSinglePeriodIndex(periodOutLen);
                } else {
                    periodOutLen += singleField.getFieldSize();
                    singeOtherFieldList.add(singleField);
                    importContext.getEntityCache().getSingleOtherZdmFieldList().add(singleField);
                }
                if (code.equalsIgnoreCase(fMTable.getDWDMField())) {
                    importContext.getEntityCache().setSingleQYDMIndex(qydmIndex);
                    importContext.getEntityCache().setSingleQYDMLen(singleField.getFieldSize());
                } else {
                    qydmIndex += singleField.getFieldSize();
                }
                if (code.equalsIgnoreCase(fMTable.getBBLXField())) {
                    importContext.getEntityCache().setSingleBBLXIndex(bblxIdex);
                    importContext.getEntityCache().setSingleBBLXLen(singleField.getFieldSize());
                } else {
                    bblxIdex += singleField.getFieldSize();
                }
                importContext.getEntityCache().getSingleZdmFieldList().add(singleField);
            }
        }
        if (StringUtils.isEmpty((String)importContext.getCurrentPeriod()) && StringUtils.isNotEmpty((String)importContext.getFirstZDM())) {
            String zdm = importContext.getFirstZDM();
            String singlePeriod = importContext.getEntityCache().getSinglePeriodByZdm(zdm);
            importContext.setCurrentPeriod(singlePeriod);
            String singlePeriod1 = importContext.getSingleTaskYear() + "@" + singlePeriod;
            String mapNetPeriod = this.taskDataService.getNetPeriodCode(importContext, singlePeriod1);
            importContext.setMapNetPeriodCode(mapNetPeriod);
        }
        if ((jioRepeatParam = importContext.getJioRepeatParam()).getNetCorpCount() > 0) {
            importContext.setNetCorpCount(jioRepeatParam.getNetCorpCount());
        } else {
            importContext.setNetCorpCount(jioRepeatParam.getEntityNodes().size());
        }
        for (RepeatEntityNode node : jioRepeatParam.getEntityNodes()) {
            DataEntityInfo entityInfo = new DataEntityInfo();
            entityInfo.setEntityKey(node.getNetCode());
            entityInfo.setEntityTitle(node.getNetTitle());
            entityInfo.setEntityCode(node.getNetCode());
            entityInfo.setEntityExCode(node.getNetMapCode());
            entityInfo.setEntityExpCode(node.getNetMapCode());
            entityInfo.setEntityParentKey(node.getNetParent());
            entityInfo.setSingleZdm(node.getSingleZdm());
            entityInfo.setEntityRowCaption(node.getNetTitle());
            entityInfo.setExpEntityExCode(node.getSingleMapCode());
            entityInfo.setExpSingleZdm(node.getSingleZdm());
            entityInfo.setEntityAppendCode("");
            importContext.getEntityCache().addEntity(entityInfo);
        }
        importContext.getEntityCache().buildNormalTree();
    }

    private List<CheckResultNode> checkLinkChain(TaskDataContext importContext, List<SingleDataRow> importSingleRows, boolean hasSjdm) {
        SingleDataRow entityRow;
        int i;
        HashMap<String, CheckResultNode> entityNodesMap = new HashMap<String, CheckResultNode>();
        HashMap<String, SingleDataRow> entityCodesMap = new HashMap<String, SingleDataRow>();
        CheckNodeList entityNodes = new CheckNodeList();
        Map<String, String> fjdMap = entityNodes.getFjdMap();
        HashMap<String, String> fjdFjdMap = new HashMap<String, String>();
        ArrayList<String> fjdList = new ArrayList<String>();
        for (i = 0; i < importSingleRows.size(); ++i) {
            entityRow = importSingleRows.get(i);
            entityCodesMap.put(entityRow.getZdm(), entityRow);
        }
        for (i = 0; i < importSingleRows.size(); ++i) {
            SingleDataRow parentEntityRow;
            entityRow = importSingleRows.get(i);
            CheckResultNode node = this.getNodeInfo(importContext, entityRow, entityCodesMap);
            node.setSjdmField(hasSjdm);
            entityNodes.add(node);
            entityNodesMap.put(node.getUnitCode(), node);
            if (!StringUtils.isNotEmpty((String)entityRow.getFjd())) continue;
            fjdMap.put(entityRow.getZdm(), entityRow.getFjd());
            if (fjdFjdMap.containsKey(entityRow.getFjd()) || (parentEntityRow = (SingleDataRow)entityCodesMap.get(entityRow.getFjd())) == null) continue;
            fjdFjdMap.put(entityRow.getFjd(), parentEntityRow.getFjd());
            fjdList.add(entityRow.getFjd());
        }
        ArrayList<CheckResultNode> errorList = new ArrayList<CheckResultNode>();
        Iterator iterator = fjdList.iterator();
        while (iterator.hasNext()) {
            String nodeCode;
            String sTmp = nodeCode = (String)iterator.next();
            if (!StringUtils.isNotEmpty((String)(sTmp = this.checkExistsRing(sTmp, entityNodes, fjdFjdMap)))) continue;
            fjdFjdMap.put(nodeCode, "");
            CheckResultNode errorNode = new CheckResultNode();
            if (entityNodes.getEntityCodesMap().containsKey(nodeCode)) {
                errorNode.copyFrom(entityNodes.getEntityCodesMap().get(nodeCode));
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s   \u5f62\u6210\u73af\u94fe", sTmp));
                errorList.add(errorNode);
                fjdMap.put(nodeCode, "");
                importContext.info(logger, errorNode.getErrorMsg());
                continue;
            }
            errorNode.setErrorType(1);
            errorNode.setErrorMsg(String.format("%s   \u5f62\u6210\u73af\u94fe", sTmp));
            errorList.add(errorNode);
            importContext.info(logger, "\u6570\u636e\u5f02\u5e38\u8bf7\u68c0\u67e5," + errorNode.getErrorMsg());
        }
        if (!errorList.isEmpty()) {
            importContext.info(logger, "\u5b58\u5728\u73af\u94fe");
        }
        return errorList;
    }

    private CheckResultNode getNodeInfo(TaskDataContext checkContext, SingleDataRow entityRow, Map<String, SingleDataRow> entityCodesMap) {
        CheckResultNode node = new CheckResultNode();
        this.writeNodeInfo(checkContext, node, entityRow);
        if (StringUtils.isNotEmpty((String)node.getParentCode()) && entityCodesMap.containsKey(node.getParentCode())) {
            this.writeNodeParent(checkContext, node, entityCodesMap.get(node.getParentCode()));
        }
        return node;
    }

    private void writeNodeInfo(TaskDataContext checkContext, CheckResultNode node, SingleDataRow entityRow) {
        node.setUnitKey(entityRow.getZdm());
        node.setUnitCode(entityRow.getZdm());
        node.setOrgCode(entityRow.getZdm());
        node.setUnitTitle(entityRow.getDwmc());
        node.setUnitZdm(entityRow.getZdm());
        node.setBblxCode(entityRow.getBblx());
        node.setQydmCode(entityRow.getDwdm());
        node.setParentCode(entityRow.getFjd());
        node.setParentKey(entityRow.getFjd());
    }

    private void writeNodeParent(TaskDataContext checkContext, CheckResultNode node, SingleDataRow parnetEntityRow) {
        node.setParentKey(parnetEntityRow.getZdm());
        node.setParentCode(parnetEntityRow.getZdm());
        node.setParentOrgCode(parnetEntityRow.getZdm());
        node.setParentTitle(parnetEntityRow.getDwmc());
        node.setParentZdm(parnetEntityRow.getZdm());
        node.setParentBblx(parnetEntityRow.getBblx());
        node.setParentQydm(parnetEntityRow.getDwdm());
        node.setParentOrgCode(parnetEntityRow.getZdm());
        if (StringUtils.isEmpty((String)node.getParentOrgCode())) {
            node.setParentOrgCode(node.getParentCode());
        }
    }

    private String checkExistsRing(String sNode, CheckNodeList entityNodes, Map<String, String> fjdFjdMap) {
        String result = "";
        while (StringUtils.isNotEmpty((String)sNode)) {
            result = result + sNode + " , ";
            if (!StringUtils.isNotEmpty((String)(sNode = fjdFjdMap.containsKey(sNode) ? fjdFjdMap.get(sNode) : (entityNodes.getEntityCodesMap().containsKey(sNode) ? entityNodes.getEntityCodesMap().get(sNode).getParentCode() : ""))) || (" , " + result).indexOf(" , " + sNode + " , ") < 0) continue;
            result = result + sNode;
            return result;
        }
        result = "";
        return result;
    }

    private boolean readeableByEntity(String entityId, String unitCode, String netPeriodCode) {
        if (this.systemdentityService.isAdmin()) {
            return true;
        }
        String periodValue = netPeriodCode;
        Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        String unitKey = unitCode;
        try {
            return this.entityAuthorityService.canReadEntity(entityId, unitKey, startDate, endDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            return true;
        }
    }

    private boolean weditEableByEntity(String entityId, String unitCode, String netPeriodCode) {
        if (this.systemdentityService.isAdmin()) {
            return true;
        }
        String periodValue = netPeriodCode;
        Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        String unitKey = unitCode;
        try {
            return this.entityAuthorityService.canEditEntity(entityId, unitKey, startDate, endDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            return true;
        }
    }

    private boolean editEable(String formSchemeKey, String unitCode, String netPeriodCode) {
        if (this.systemdentityService.isAdmin()) {
            return true;
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String periodValue = netPeriodCode;
        Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        String unitKey = unitCode;
        try {
            return this.entityAuthorityService.canEditEntity(formScheme.getDw(), unitKey, startDate, endDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            return true;
        }
    }

    private boolean editEable(String formSchemeKey, DimensionCombination collectionKey) {
        if (this.systemdentityService.isAdmin()) {
            return true;
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        DimensionValueSet masterKey = collectionKey.toDimensionValueSet();
        String unitDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        String periodDimName = this.periodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
        String periodValue = String.valueOf(masterKey.getValue(periodDimName));
        Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        String unitKey = String.valueOf(masterKey.getValue(unitDimName));
        try {
            return this.entityAuthorityService.canEditEntity(formScheme.getDw(), unitKey, startDate, endDate);
        }
        catch (UnauthorizedEntityException e) {
            logger.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            return true;
        }
    }

    private Map<String, Boolean> getBatchEidtEable(String formSchemeKey, Set<String> unitKeys, String periodValue) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        Map<String, Boolean> batchWriteable = unitKeys.stream().collect(Collectors.toMap(e -> e, e -> true));
        if (this.systemdentityService.isAdmin()) {
            return batchWriteable;
        }
        try {
            return this.entityAuthorityService.canEditEntity(formScheme.getDw(), unitKeys, endDate);
        }
        catch (UnauthorizedEntityException e2) {
            logger.error("\u6279\u91cf\u5199\u6743\u9650\u5224\u65ad\u51fa\u9519\uff01", e2);
            return batchWriteable;
        }
    }
}

