/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.xlib.utils.StringUtil
 *  javax.annotation.Resource
 */
package nr.single.map.data.internal.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UpdateWay;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.util.SyntaxExcuteMapEntityImpl;
import nr.single.map.data.util.SingleMapEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SingleMapEntityUtilImplFMDM
implements SingleMapEntityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SingleMapEntityUtilImplFMDM.class);
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private SystemIdentityService systemdentityService;

    @Override
    public List<DataEntityInfo> queryEntityDataRows(String entityID) {
        List rows = null;
        ArrayList<DataEntityInfo> datas = new ArrayList<DataEntityInfo>();
        try {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityID);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            entityQuery.setEntityView(entityViewDefine);
            entityQuery.setMasterKeys(new DimensionValueSet());
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
            entityQuery.setAuthorityOperations(AuthorityType.None);
            ExecutorContext queryContext = new ExecutorContext(this.dataRuntimeController);
            IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
            rows = entityTable.getAllRows();
            if (rows != null && rows.size() > 0) {
                for (IEntityRow row : rows) {
                    DataEntityInfo data = new DataEntityInfo();
                    data.setEntityKey(row.getEntityKeyData());
                    data.setEntityTitle(row.getTitle());
                    data.setEntityParentKey(row.getParentEntityKey());
                    datas.add(data);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return datas;
    }

    @Override
    public List<String> queryEntityDataKeys(String entityID, String netPeriodCode, String formSchemeKey) {
        ArrayList<String> entiyDatas = new ArrayList<String>();
        try {
            PeriodWrapper periodW;
            IEntityTable entityTable;
            DimensionValueSet valueSet = new DimensionValueSet();
            if (StringUtils.isNotEmpty((String)netPeriodCode)) {
                DimensionValue periodDim = new DimensionValue();
                periodDim.setName("DATATIME");
                periodDim.setType(6);
                periodDim.setValue(netPeriodCode);
                valueSet.setValue("DATATIME", (Object)periodDim);
            }
            if ((entityTable = this.entityQuerySet(entityID, periodW = new PeriodWrapper(netPeriodCode), valueSet, AuthorityType.Read, formSchemeKey)) != null) {
                List allRows = entityTable.getAllRows();
                for (IEntityRow row : allRows) {
                    entiyDatas.add(row.getEntityKeyData());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entiyDatas;
    }

    private IEntityTable entityQuerySet(String entityID, PeriodWrapper period, DimensionValueSet valueSet, AuthorityType authType, String formSchemeKey) {
        IEntityQuery query = this.buildQuery(entityID, period, valueSet, authType);
        ExecutorContext context = this.executorContext(formSchemeKey);
        return this.entityQuerySet(query, context);
    }

    private IEntityQuery buildQuery(String entityID, PeriodWrapper period, DimensionValueSet valueSet, AuthorityType authType) {
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityID);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        if (valueSet != null && valueSet.size() > 0) {
            entityQuery.setMasterKeys(valueSet);
        }
        if (authType != null) {
            entityQuery.setAuthorityOperations(authType);
        }
        return entityQuery;
    }

    private ExecutorContext executorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataRuntimeController);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            // empty if block
        }
        return context;
    }

    private IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rsSet;
    }

    @Override
    public List<DataEntityInfo> queryEntityDataRows(String entityID, List<String> fieldList) {
        return null;
    }

    @Override
    public List<DataEntityInfo> queryEntityDataRowsByKeys(String entityID, String entityDimName, List<String> entityKeys) {
        DimensionValueSet masterSet = new DimensionValueSet();
        masterSet.setValue(entityDimName, entityKeys);
        return this.queryEntityDataRowsByDims(entityID, masterSet);
    }

    @Override
    public List<DataEntityInfo> queryEntityDataRowsByDims(String entityID, DimensionValueSet masterSet) {
        List rows = null;
        ArrayList<DataEntityInfo> datas = new ArrayList<DataEntityInfo>();
        try {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityID);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            entityQuery.setEntityView(entityViewDefine);
            entityQuery.setMasterKeys(masterSet);
            if (!masterSet.hasValue("DATATIME")) {
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
            }
            entityQuery.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext queryContext = new ExecutorContext(this.dataRuntimeController);
            IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
            rows = entityTable.getAllRows();
            if (rows != null && rows.size() > 0) {
                for (IEntityRow row : rows) {
                    DataEntityInfo data = new DataEntityInfo();
                    data.setEntityKey(row.getEntityKeyData());
                    data.setEntityCode(row.getEntityKeyData());
                    data.setEntityTitle(row.getTitle());
                    AbstractData orgCodeData = row.getValue("ORGCODE");
                    if (orgCodeData != null) {
                        data.setEntityOrgCode(orgCodeData.getAsString());
                    }
                    data.setEntityParentKey(row.getParentEntityKey());
                    datas.add(data);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return datas;
    }

    @Override
    public void MapSingleEnityData(TaskDataContext context) throws Exception {
        SingleFileTableInfo table = null;
        if (context.getMapingCache().isMapConfig()) {
            table = context.getMapingCache().getFmdmInfo();
        }
        this.MapEntityDatas(context, context.getDwEntityId(), null, table);
    }

    private void MapEntityDatas(TaskDataContext context, String entityID, List<FieldDefine> fieldDefines, SingleFileTableInfo singeleTable) throws Exception {
        context.info(logger, "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u52a0\u8f7d\u53c2\u6570");
        HashMap<String, IFMDMAttribute> fieldMap = new HashMap<String, IFMDMAttribute>();
        ArrayList<Object> queryFieldDefines = new ArrayList<Object>();
        HashMap singleFieldMap = new HashMap();
        HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, String> fieldValueMap = new HashMap<String, String>();
        LinkedHashMap<String, IFMDMAttribute> zdmFieldMap = new LinkedHashMap<String, IFMDMAttribute>();
        Map<String, String> entityKeyZdmMap = context.getEntityKeyZdmMap();
        Map<String, String> entityZdmAndKeyMap = context.getEntityZdmKeyMap();
        Map<String, String> entityCodeKeyMap = context.getEntityCodeKeyMap();
        Map<String, String> entityKeyCodeMap = context.getEntityKeyCodeMap();
        Map<String, String> entityKeyParentMap = context.getEntityKeyParentMap();
        LinkedHashMap SingleZdmCodeAndFieldIdMap = new LinkedHashMap();
        LinkedHashMap<String, String> SingleZdmCodeAndFieldCodeMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> netRlueCodeAndSingleFieldCodeMap = new LinkedHashMap<String, String>();
        String autoCodeField = null;
        ArrayList<IFMDMAttribute> zdmFieldList = new ArrayList<IFMDMAttribute>();
        ArrayList<SingleFileFieldInfo> singeZdmFieldList = new ArrayList<SingleFileFieldInfo>();
        ArrayList<SingleFileFieldInfo> singeOtherFieldList = new ArrayList<SingleFileFieldInfo>();
        HashMap<String, UnitCustomMapping> unitMap = new HashMap<String, UnitCustomMapping>();
        HashMap<String, UnitCustomMapping> unitMapByCodes = new HashMap<String, UnitCustomMapping>();
        HashMap<String, Map<String, String>> unitFieldMap = new HashMap<String, Map<String, String>>();
        HashMap<String, RuleMap> unitRuleMap = new HashMap<String, RuleMap>();
        context.getMapingCache().setUnitMap(unitMap);
        context.getMapingCache().setUnitFieldMap(unitFieldMap);
        entityKeyZdmMap.clear();
        entityZdmAndKeyMap.clear();
        entityCodeKeyMap.clear();
        entityKeyCodeMap.clear();
        entityKeyParentMap.clear();
        context.getEntityCache().ClearEntitys();
        int zmdLength = 0;
        int periodOutLen = 0;
        int qydmIndex = 0;
        int bblxIdex = 0;
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setEntityId(entityID);
        fmdmAttributeDTO.setFormSchemeKey(context.getFormSchemeKey());
        List fieldList = this.fmdmAttributeService.list(fmdmAttributeDTO);
        for (IFMDMAttribute attr : fieldList) {
            fieldMap.put(attr.getCode(), attr);
        }
        boolean isZdmMapExpression = false;
        boolean corpMapUseExp = false;
        String netMapExpresion = "";
        String netMapExpValue = "";
        ArrayList<IFMDMAttribute> fmdmAttributeList = new ArrayList<IFMDMAttribute>();
        ArrayList fmdmRuleAttrs = new ArrayList();
        if (null != context.getMapingCache().getMapConfig()) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(context.getDwEntityId());
            ISingleMappingConfig mapConfig = context.getMapingCache().getMapConfig();
            List<RuleMap> rules = null;
            rules = context.isNeedDownLoad() ? mapConfig.getMapRule(RuleKind.UNIT_MAP_EXPORT) : mapConfig.getMapRule(RuleKind.UNIT_MAP_IMPORT);
            for (RuleMap rule : rules) {
                String string = rule.getNetCode();
                String singleExp = rule.getSingleCode();
                if (!StringUtils.isNotEmpty((String)string)) continue;
                unitRuleMap.put(rule.getSingleCode(), rule);
                if (fieldMap.containsKey(string)) {
                    queryFieldDefines.add(fieldMap.get(string));
                }
                isZdmMapExpression = true;
                if (string.contains("+") || string.contains("-") || string.contains("(") || string.contains(")") || string.contains("*") || string.contains("$")) {
                    corpMapUseExp = true;
                } else if (fieldMap.containsKey(string)) {
                    fmdmAttributeList.add((IFMDMAttribute)fieldMap.get(string));
                    fmdmRuleAttrs.add(fieldMap.get(string));
                }
                if (!corpMapUseExp && StringUtils.isNotEmpty((String)singleExp) && (singleExp.contains("+") || singleExp.contains("-") || singleExp.contains("(") || singleExp.contains(")") || singleExp.contains("*") || singleExp.contains("$"))) {
                    corpMapUseExp = true;
                }
                SingleZdmCodeAndFieldCodeMap.put(rule.getSingleCode(), rule.getNetCode());
                netRlueCodeAndSingleFieldCodeMap.put(rule.getNetCode(), rule.getSingleCode());
                if (StringUtils.isNotEmpty((String)netMapExpresion)) {
                    netMapExpresion = netMapExpresion + " + (" + rule.getNetCode() + ")";
                    continue;
                }
                netMapExpresion = "(" + rule.getNetCode() + ")";
            }
            if (!corpMapUseExp) {
                netMapExpresion = "";
            }
            AutoAppendCode autoCodeCoinfig = null;
            if (null != context.getMapingCache().getMapConfig().getConfig() && null != context.getMapingCache().getMapConfig().getConfig().getAutoAppendCode() && (autoCodeCoinfig = context.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()).isAutoAppendCode() && StringUtils.isNotEmpty((String)autoCodeCoinfig.getAppendCodeZb())) {
                String netExp2;
                autoCodeField = netExp2 = autoCodeCoinfig.getAppendCodeZb();
                if (fieldMap.containsKey(netExp2)) {
                    queryFieldDefines.add(fieldMap.get(netExp2));
                }
                if (netExp2.indexOf(43) < 0 && netExp2.indexOf(45) < 0 && netExp2.indexOf(42) < 0 && netExp2.indexOf(40) < 0 && netExp2.indexOf(91) < 0) {
                    netExp2 = entityDefine.getCode() + "[" + netExp2 + "]";
                }
            }
            List<UnitCustomMapping> unitList = context.getMapingCache().getMapConfig().getMapping().getUnitInfos();
            Iterator iterator = unitList.iterator();
            while (iterator.hasNext()) {
                UnitCustomMapping unit = (UnitCustomMapping)iterator.next();
                if (StringUtils.isNotEmpty((String)unit.getNetUnitKey())) {
                    unitMap.put(unit.getNetUnitKey(), unit);
                }
                if (!StringUtils.isNotEmpty((String)unit.getNetUnitCode())) continue;
                unitMapByCodes.put(unit.getNetUnitCode(), unit);
            }
        }
        IFMDMAttribute fjdField = null;
        SingleFileFmdmInfo fMTable = null;
        String periodCode = "";
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
            fjdField = (IFMDMAttribute)fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey);
            queryFieldDefines.add(fjdField);
        } else if (fieldMap.containsKey("PARENTID")) {
            fjdField = (IFMDMAttribute)fieldMap.get("PARENTID");
            queryFieldDefines.add(fjdField);
        }
        if (null != singeleTable) {
            Map<String, SingleFileFieldInfo> fMDMSingleMaps = context.getMapingCache().getSingleFieldMap().get("FMDM");
            for (SingleFileFieldInfo singleFileFieldInfo : singeleTable.getRegion().getFields()) {
                SingleFileFieldInfo netMapField;
                mapNetFieldList.put(singleFileFieldInfo.getNetFieldCode(), singleFileFieldInfo);
                mapSingleFieldList.put(singleFileFieldInfo.getFieldCode(), singleFileFieldInfo);
                boolean isHasNetField = false;
                if (StringUtils.isNotEmpty((String)singleFileFieldInfo.getNetFieldCode()) && fieldMap.containsKey(singleFileFieldInfo.getNetFieldCode())) {
                    singleFieldMap.put(singleFileFieldInfo.getFieldCode(), fieldMap.get(singleFileFieldInfo.getNetFieldCode()));
                    isHasNetField = true;
                }
                if (isHasNetField || !StringUtils.isNotEmpty((String)singleFileFieldInfo.getFieldCode()) || fMDMSingleMaps == null || (netMapField = fMDMSingleMaps.get("FMDM." + singleFileFieldInfo.getFieldCode())) == null || !StringUtils.isNotEmpty((String)netMapField.getNetFieldCode()) || !fieldMap.containsKey(netMapField.getNetFieldCode())) continue;
                singleFieldMap.put(singleFileFieldInfo.getFieldCode(), fieldMap.get(netMapField.getNetFieldCode()));
            }
            fMTable = (SingleFileFmdmInfo)singeleTable;
            for (String string : fMTable.getZdmFieldCodes()) {
                String netCode1;
                if (!mapSingleFieldList.containsKey(string)) continue;
                SingleFileFieldInfo singleField = (SingleFileFieldInfo)mapSingleFieldList.get(string);
                singeZdmFieldList.add(singleField);
                zmdLength += singleField.getFieldSize();
                String netCode = singleField.getNetFieldCode();
                if (!StringUtils.isEmpty((String)netCode) && fieldMap.containsKey(netCode)) {
                    queryFieldDefines.add(fieldMap.get(netCode));
                }
                if (fieldMap.containsKey(string)) {
                    queryFieldDefines.add(fieldMap.get(string));
                }
                if (SingleZdmCodeAndFieldCodeMap.containsKey(string) && StringUtils.isNotEmpty((String)(netCode1 = (String)SingleZdmCodeAndFieldCodeMap.get(string))) && fieldMap.containsKey(netCode1)) {
                    netCode = netCode1;
                }
                IFMDMAttribute netField = null;
                if (StringUtils.isNotEmpty((String)netCode) && fieldMap.containsKey(netCode)) {
                    netField = (IFMDMAttribute)fieldMap.get(netCode);
                    zdmFieldMap.put(string, netField);
                    zdmFieldList.add(netField);
                    queryFieldDefines.add(netField);
                    fmdmAttributeList.add(netField);
                } else if (unitRuleMap.containsKey(string) && fieldMap.containsKey(((RuleMap)unitRuleMap.get(string)).getNetCode())) {
                    netField = (IFMDMAttribute)fieldMap.get(((RuleMap)unitRuleMap.get(string)).getNetCode());
                    zdmFieldMap.put(string, netField);
                    zdmFieldList.add(netField);
                    queryFieldDefines.add(netField);
                    fmdmAttributeList.add(netField);
                } else {
                    zdmFieldMap.put(string, null);
                }
                if (string.equalsIgnoreCase(fMTable.getPeriodField())) {
                    periodCode = this.getSinglePeriodCode(context, context.getNetPeriodCode(), singleField.getFieldSize());
                    fieldValueMap.put(singleField.getFieldCode(), periodCode);
                    context.setMapCurrentPeriod(periodCode);
                    context.getEntityCache().setSinglePeriodLen(singleField.getFieldSize());
                    context.getEntityCache().setSinglePeriodIndex(periodOutLen);
                } else {
                    periodOutLen += singleField.getFieldSize();
                    singeOtherFieldList.add(singleField);
                    context.getEntityCache().getSingleOtherZdmFieldList().add(singleField);
                }
                if (string.equalsIgnoreCase(fMTable.getDWDMField())) {
                    context.getEntityCache().setSingleQYDMIndex(qydmIndex);
                    context.getEntityCache().setSingleQYDMLen(singleField.getFieldSize());
                } else {
                    qydmIndex += singleField.getFieldSize();
                }
                if (string.equalsIgnoreCase(fMTable.getBBLXField())) {
                    context.getEntityCache().setSingleBBLXIndex(bblxIdex);
                    context.getEntityCache().setSingleBBLXLen(singleField.getFieldSize());
                } else {
                    bblxIdex += singleField.getFieldSize();
                }
                context.getEntityCache().getSingleZdmFieldList().add(singleField);
            }
            context.getEntityCache().setSingleZdmLen(zmdLength);
            context.getEntityCache().setSingleZdmOutPeriodLen(periodOutLen);
        }
        if (StringUtils.isEmpty((String)context.getCurrentPeriod()) && StringUtils.isNotEmpty((String)context.getFirstZDM())) {
            String zdm = context.getFirstZDM();
            String singlePeriod = context.getEntityCache().getSinglePeriodByZdm(zdm);
            context.setCurrentPeriod(singlePeriod);
            String string = context.getSingleTaskYear() + "@" + (String)singlePeriod;
            String mapNetPeriod = this.getNetPeriodCode(context, string);
            context.setMapNetPeriodCode(mapNetPeriod);
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)) {
            queryFieldDefines.add(fieldMap.get(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey));
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWDMField())) {
            queryFieldDefines.add(singleFieldMap.get(fMTable.getDWDMField()));
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
            queryFieldDefines.add(fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey));
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWMCField())) {
            queryFieldDefines.add(singleFieldMap.get(fMTable.getDWMCField()));
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
            queryFieldDefines.add(fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey));
        } else if (fieldMap.containsKey("PARENTID")) {
            queryFieldDefines.add(fieldMap.get("PARENTID"));
        }
        HashMap<String, IFMDMAttribute> queryFieldDic = new HashMap<String, IFMDMAttribute>();
        for (IFMDMAttribute iFMDMAttribute : queryFieldDefines) {
            if (iFMDMAttribute == null || queryFieldDic.containsKey(iFMDMAttribute.getID())) continue;
            queryFieldDic.put(iFMDMAttribute.getID(), iFMDMAttribute);
        }
        SyntaxExcuteMapEntityImpl expGeter = new SyntaxExcuteMapEntityImpl();
        if (corpMapUseExp && StringUtils.isNotEmpty((String)netMapExpresion)) {
            if (!fmdmAttributeList.isEmpty()) {
                expGeter.buildExpression(netMapExpresion, fmdmAttributeList);
            } else {
                expGeter.buildExpression(netMapExpresion, fieldList);
            }
        }
        context.info(logger, "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u52a0\u8f7d\u5355\u4f4d\u6570\u636e");
        FMDMDataDTO fMDMDataDTO = new FMDMDataDTO();
        fMDMDataDTO.setFormSchemeKey(context.getFormSchemeKey());
        if (!fmdmAttributeList.isEmpty()) {
            fMDMDataDTO.setQueryPartZb(true);
            fMDMDataDTO.setFmdmAttributeList(fmdmAttributeList);
        }
        fMDMDataDTO.setSortedByQuery(false);
        HashSet<String> downLoadParentKeys = new HashSet<String>();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String period = context.getNetPeriodCode();
        if (StringUtils.isNotEmpty((String)context.getMapNetPeriodCode())) {
            period = context.getMapNetPeriodCode();
        }
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (context.isNeedDownLoad()) {
            if (context.getDownloadEntityKeys() != null && context.getDownloadEntityKeys().size() > 0) {
                ArrayList<String> aList2 = new ArrayList<String>();
                aList2.addAll(context.getDownloadEntityKeys());
                DimensionValueSet queryMasterSet = new DimensionValueSet();
                queryMasterSet.setValue(context.getEntityCompanyType(), aList2);
                queryMasterSet.setValue(context.getEntityDateType(), (Object)period);
                List<DataEntityInfo> queryEntitys = this.queryEntityDataRowsByDims(entityID, queryMasterSet);
                ArrayList aList = new ArrayList();
                aList.addAll(context.getDownloadEntityKeys());
                HashSet<String> checkList = new HashSet<String>(context.getDownloadEntityKeys());
                if (queryEntitys != null) {
                    for (DataEntityInfo info : queryEntitys) {
                        if (!StringUtils.isNotEmpty((String)info.getEntityParentKey()) || "-".equalsIgnoreCase(info.getEntityParentKey()) || checkList.contains(info.getEntityParentKey())) continue;
                        aList.add(info.getEntityParentKey());
                        checkList.add(info.getEntityParentKey());
                        downLoadParentKeys.add(info.getEntityParentKey());
                    }
                }
                dimensionValueSet.setValue(context.getEntityCompanyType(), (Object)aList);
            } else {
                dimensionValueSet.clearValue(context.getEntityCompanyType());
            }
        } else {
            Object way;
            dimensionValueSet.clearValue(context.getEntityCompanyType());
            if (!this.systemdentityService.isAdmin() && context.getCanReadNetEntityKeys().size() > 0 && context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getConfig() != null && !((UpdateWay)(way = context.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay())).isIncrement()) {
                ArrayList<String> canReadunits = new ArrayList<String>();
                canReadunits.addAll(context.getCanReadNetEntityKeys());
                dimensionValueSet.setValue(context.getEntityCompanyType(), canReadunits);
            }
        }
        if (context.getOtherDims().size() > 0) {
            for (DimensionValue dim : context.getOtherDims().values()) {
                dimensionValueSet.setValue(dim.getName(), (Object)dim.getValue());
            }
        }
        if (context.getDimEntityCache().getEntitySingleDims().size() > 0) {
            for (String dimName : context.getDimEntityCache().getEntitySingleDims()) {
                dimensionValueSet.setValue(dimName, null);
            }
        }
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)context.getFormSchemeKey());
        List tranDims = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)dimensionCollection);
        HashMap<String, DimensionValueSet> tranDimDic = new HashMap<String, DimensionValueSet>();
        for (DimensionValueSet tranDim : tranDims) {
            String orgCode = (String)tranDim.getValue(context.getEntityCompanyType());
            tranDimDic.put(orgCode, tranDim);
        }
        List queryRes = this.fmdmDataService.list(fMDMDataDTO, dimensionCollection);
        if (queryRes != null && this.systemdentityService.isAdmin()) {
            context.setNetCorpCount(queryRes.size());
        }
        context.info(logger, "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u6784\u9020\u6620\u5c04\u5173\u7cfb\uff0c\u5f53\u524d\u5355\u4f4d\u6570\uff1a" + String.valueOf(queryRes.size()));
        for (int i = 0; i < queryRes.size(); ++i) {
            boolean isNeedSingleDim;
            String curInfo;
            SingleDataError errorItem;
            SingleFileFieldInfo singleField;
            String fieldValue;
            IFMDMAttribute field;
            IFMDMData entityRow = (IFMDMData)queryRes.get(i);
            String zdmKey = entityRow.getValue("CODE").getAsString();
            String zdm = "";
            String zdmWithOutPeriod = "";
            HashMap<Object, String> unitFieldValueMap = new HashMap<Object, String>();
            for (Object singleFieldCode : zdmFieldMap.keySet()) {
                int pos;
                field = (IFMDMAttribute)zdmFieldMap.get(singleFieldCode);
                fieldValue = "";
                if (!SingleZdmCodeAndFieldIdMap.containsKey(singleFieldCode) && null != field) {
                    fieldValue = entityRow.getValue(field.getCode()).getAsString();
                }
                if (StringUtils.isNotEmpty((String)fieldValue) && (pos = fieldValue.indexOf("|")) > 0) {
                    fieldValue = fieldValue.substring(0, pos);
                }
                String fieldValue2 = fieldValue;
                if (((String)singleFieldCode).equalsIgnoreCase(fMTable.getPeriodField())) {
                    fieldValue2 = fieldValue = periodCode;
                } else if (StringUtils.isNotEmpty((String)fieldValue)) {
                    if (mapSingleFieldList.containsKey(singleFieldCode) && (singleField = (SingleFileFieldInfo)mapSingleFieldList.get(singleFieldCode)) != null && singleField.getFieldSize() > fieldValue.length()) {
                        byte[] dataByte = fieldValue.getBytes("gbk");
                        for (int len = singleField.getFieldSize() - dataByte.length; len > 0; --len) {
                            fieldValue2 = fieldValue2 + " ";
                        }
                    }
                    zdmWithOutPeriod = zdmWithOutPeriod + fieldValue2;
                }
                if (StringUtils.isNotEmpty((String)fieldValue)) {
                    zdm = zdm + fieldValue2;
                }
                unitFieldValueMap.put(singleFieldCode, fieldValue);
            }
            String netMapRlueCode = "";
            if (!fmdmRuleAttrs.isEmpty()) {
                Object singleFieldCode;
                singleFieldCode = fmdmRuleAttrs.iterator();
                while (singleFieldCode.hasNext()) {
                    String singleFieldCode2;
                    int pos;
                    field = (IFMDMAttribute)singleFieldCode.next();
                    fieldValue = entityRow.getValue(field.getCode()).getAsString();
                    if (StringUtils.isNotEmpty((String)fieldValue) && (pos = fieldValue.indexOf("|")) > 0) {
                        fieldValue = fieldValue.substring(0, pos);
                    }
                    if (netRlueCodeAndSingleFieldCodeMap.containsKey(field.getCode()) && StringUtils.isNotEmpty((String)fieldValue) && mapSingleFieldList.containsKey(singleFieldCode2 = (String)netRlueCodeAndSingleFieldCodeMap.get(field.getCode())) && (singleField = (SingleFileFieldInfo)mapSingleFieldList.get(singleFieldCode2)) != null && singleField.getFieldSize() > fieldValue.length()) {
                        String fieldValue2 = fieldValue;
                        for (int len = singleField.getFieldSize() - fieldValue.length(); len > 0; --len) {
                            fieldValue2 = fieldValue2 + " ";
                        }
                        fieldValue = fieldValue2;
                    }
                    netMapRlueCode = netMapRlueCode + fieldValue;
                }
            }
            netMapExpValue = "";
            if (corpMapUseExp && StringUtils.isNotEmpty((String)netMapExpresion)) {
                netMapExpValue = expGeter.getExpValue(entityRow);
            }
            String autoCodeFieldValue = "";
            if (StringUtils.isNotEmpty((String)autoCodeField)) {
                autoCodeFieldValue = entityRow.getValue(autoCodeField).getAsString();
            }
            String entityCode = entityRow.getValue("CODE").getAsString();
            String entityOrgCode = entityRow.getValue("ORGCODE").getAsString();
            if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)) {
                entityCode = entityRow.getValue(((IFMDMAttribute)fieldMap.get(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)).getCode()).getAsString();
            } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWDMField())) {
                IFMDMAttribute netMapField = (IFMDMAttribute)singleFieldMap.get(fMTable.getDWDMField());
                entityCode = entityRow.getValue(netMapField.getCode()).getAsString();
            }
            String entityTitle = "";
            if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
                entityTitle = entityRow.getValue(((IFMDMAttribute)fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)).getCode()).getAsString();
            } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWMCField())) {
                IFMDMAttribute netMapField = (IFMDMAttribute)singleFieldMap.get(fMTable.getDWMCField());
                entityTitle = entityRow.getValue(netMapField.getCode()).getAsString();
            }
            UnitCustomMapping sUnitMap = null;
            if (unitMap.containsKey(zdmKey)) {
                sUnitMap = (UnitCustomMapping)unitMap.get(zdmKey);
            } else if (unitMap.containsKey(entityCode)) {
                sUnitMap = (UnitCustomMapping)unitMap.get(entityCode);
            } else if (unitMapByCodes.containsKey(zdmKey)) {
                sUnitMap = (UnitCustomMapping)unitMapByCodes.get(zdmKey);
            } else if (unitMapByCodes.containsKey(entityCode)) {
                sUnitMap = (UnitCustomMapping)unitMapByCodes.get(entityCode);
            }
            boolean isUnitMap = false;
            String expEntityExCode = null;
            String expSingleZdm = null;
            if (sUnitMap != null) {
                isUnitMap = true;
                expEntityExCode = zdmWithOutPeriod;
                expSingleZdm = zdm;
                if (StringUtil.isNotEmpty((String)sUnitMap.getSingleUnitCode())) {
                    zdmWithOutPeriod = sUnitMap.getSingleUnitCode();
                    zdm = context.getEntityCache().getNewZdmFromOutPeriod(zdmWithOutPeriod, periodCode);
                    Map<String, String> mapFieldvalues = context.getEntityCache().getFieldValueByZdmOutPeriod(zdmWithOutPeriod);
                    for (String mapfieldCode : mapFieldvalues.keySet()) {
                        String mapFieldValue = mapFieldvalues.get(mapfieldCode);
                        if (!StringUtils.isNotEmpty((String)mapFieldValue)) continue;
                        unitFieldValueMap.put(mapfieldCode, mapFieldValue);
                    }
                }
            } else if (!isZdmMapExpression) continue;
            if (StringUtils.isEmpty((String)zdmWithOutPeriod)) {
                errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "MAPFMDM", "\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u4e3a\u7a7a", "empty", zdmKey, entityTitle, entityCode);
                context.recordLog("MAPFMDM", errorItem);
                curInfo = "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u7f51\u62a5\u3010" + entityCode + "\u3011" + entityTitle + ",\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5" + zdmWithOutPeriod;
                context.info(logger, curInfo);
            } else if (zdmWithOutPeriod.length() < context.getEntityCache().getSingleZdmOutPeriodLen()) {
                errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "MAPFMDM", "\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u957f\u5ea6\u4e0d\u8db3", "smallLength", zdmKey, entityTitle, entityCode, zdmWithOutPeriod);
                context.recordLog("MAPFMDM", errorItem);
                curInfo = "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u7f51\u62a5\u3010" + entityCode + "\u3011" + entityTitle + ",\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u957f\u5ea6\u4e0d\u8db3,\u503c\u4e3a" + zdmWithOutPeriod;
                context.info(logger, curInfo);
            } else if (zdmWithOutPeriod.length() > context.getEntityCache().getSingleZdmOutPeriodLen()) {
                errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "MAPFMDM", "\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u957f\u5ea6\u8d85\u957f", "bigLength", zdmKey, entityTitle, entityCode, zdmWithOutPeriod);
                context.recordLog("MAPFMDM", errorItem);
                curInfo = "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u7f51\u62a5\u3010" + entityCode + "\u3011" + entityTitle + ",\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u8d85\u957f,\u503c\u4e3a" + zdmWithOutPeriod;
                context.info(logger, curInfo);
            } else if (context.getEntityCache().getEntityExCodeFinder().containsKey(zdmWithOutPeriod)) {
                errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "MAPFMDM", "\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801\u91cd\u590d", "codeRepeat", zdmKey, entityTitle, entityCode, zdmWithOutPeriod);
                context.recordLog("MAPFMDM", errorItem);
                curInfo = "\u8bfb\u53d6\u5355\u4f4d\u6620\u5c04\u5173\u7cfb\uff1a\u7f51\u62a5\u3010" + entityCode + "\u3011" + entityTitle + ",\u6620\u5c04\u7684\u5355\u673a\u7248\u4ee3\u7801,\u91cd\u590d\u503c\u4e3a" + zdmWithOutPeriod;
                context.info(logger, curInfo);
            }
            unitFieldMap.put(zdmKey, unitFieldValueMap);
            entityZdmAndKeyMap.put(zdm, zdmKey);
            if (!entityKeyZdmMap.containsKey(zdmKey)) {
                entityKeyZdmMap.put(zdmKey, zdm);
            }
            if (!entityCodeKeyMap.containsKey(zdmWithOutPeriod)) {
                entityCodeKeyMap.put(zdmWithOutPeriod, zdmKey);
                entityKeyCodeMap.put(zdmKey, zdmWithOutPeriod);
            }
            String fjd = entityRow.getValue("PARENTCODE").getAsString();
            if (null != fjdField) {
                fjd = entityRow.getValue(fjdField.getCode()).getAsString();
            }
            if (StringUtils.isNotEmpty((String)fjd)) {
                entityKeyParentMap.put(zdmKey, fjd);
            }
            String entityRowCaption = "";
            String entityParentKey = "";
            if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
                entityParentKey = entityRow.getValue(((IFMDMAttribute)fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)).getCode()).getAsString();
            } else if (fieldMap.containsKey("PARENTID")) {
                entityParentKey = entityRow.getValue(((IFMDMAttribute)fieldMap.get("PARENTID")).getCode()).getAsString();
            }
            DataEntityInfo entityInfo = new DataEntityInfo();
            entityInfo.setEntityKey(zdmKey);
            entityInfo.setEntityTitle(entityTitle);
            entityInfo.setEntityCode(entityCode);
            entityInfo.setEntityOrgCode(entityOrgCode);
            if (StringUtils.isNotEmpty((String)netMapRlueCode)) {
                entityInfo.setEntityExCode(netMapRlueCode);
            } else {
                entityInfo.setEntityExCode(zdmWithOutPeriod);
            }
            entityInfo.setEntityExpCode(netMapExpValue);
            entityInfo.setEntityParentKey(entityParentKey);
            entityInfo.setSingleZdm(zdm);
            entityInfo.setSingleZdmWithOutPeriod(zdmWithOutPeriod);
            entityInfo.setEntityRowCaption(entityRowCaption);
            entityInfo.setIsUnitMap(isUnitMap);
            entityInfo.setExpEntityExCode(expEntityExCode);
            entityInfo.setExpSingleZdm(expSingleZdm);
            entityInfo.setEntityAppendCode(autoCodeFieldValue);
            boolean bl = isNeedSingleDim = !context.getDimEntityCache().getEntitySingleDimAndFields().isEmpty();
            if (context.isNeedDownLoad() && isNeedSingleDim && !downLoadParentKeys.isEmpty()) {
                boolean bl2 = isNeedSingleDim = !downLoadParentKeys.contains(zdmKey);
            }
            if (isNeedSingleDim) {
                for (Map.Entry<String, DimensionValue> entry : context.getDimEntityCache().getSingleDimValueSet().entrySet()) {
                    String dimName = entry.getKey();
                    String fieldName = context.getDimEntityCache().getEntitySingleDimAndFields().get(dimName);
                    String dimValue = entityRow.getValue(fieldName).getAsString();
                    entityInfo.getFieldValues().put(fieldName, dimValue);
                    DimensionValue oldDim = entry.getValue();
                    DimensionValue newDim = new DimensionValue(oldDim);
                    newDim.setValue(dimValue);
                    entityInfo.getSingleDimValueSet().put(dimName, newDim);
                    Map<Object, Object> entitySingleDimValue = null;
                    if (context.getDimEntityCache().getEntitySingleDimValues().containsKey(dimName)) {
                        entitySingleDimValue = context.getDimEntityCache().getEntitySingleDimValues().get(dimName);
                        if (entitySingleDimValue.containsKey(newDim.getValue())) continue;
                        entitySingleDimValue.put(newDim.getValue(), newDim);
                        continue;
                    }
                    entitySingleDimValue = new HashMap<String, DimensionValue>();
                    entitySingleDimValue.put(newDim.getValue(), newDim);
                    context.getDimEntityCache().getEntitySingleDimValues().put(dimName, entitySingleDimValue);
                }
                context.getDimEntityCache().getEntitySingleDimList().put(entityCode, entityInfo.getSingleDimValueSet());
            }
            context.getEntityCache().addEntity(entityInfo);
        }
        context.getEntityCache().buildNormalTree();
    }

    @Override
    public String getNetPeriodCode(TaskDataContext context, String singlePeriodCode) {
        String result = null;
        int idYear = singlePeriodCode.indexOf("@");
        if (idYear > 0) {
            String YearCode = singlePeriodCode.substring(0, idYear);
            String periodCode = singlePeriodCode.substring(idYear + 1, singlePeriodCode.length());
            ISingleMappingConfig mapConfig = context.getMapingCache().getMapConfig();
            if (null != mapConfig && null != mapConfig.getMapping() && null != mapConfig.getMapping().getPeriodMapping()) {
                String newPeriod = periodCode;
                if (StringUtils.isEmpty((String)newPeriod)) {
                    newPeriod = YearCode;
                }
                if (mapConfig.getMapping().getPeriodMapping().containsKey(newPeriod)) {
                    result = mapConfig.getMapping().getPeriodMapping().get(newPeriod);
                } else if (StringUtils.isNotEmpty((String)YearCode) && StringUtils.isEmpty((String)periodCode)) {
                    if (mapConfig.getMapping().getPeriodMapping().containsKey(YearCode)) {
                        result = mapConfig.getMapping().getPeriodMapping().get(YearCode);
                    } else if (mapConfig.getMapping().getPeriodMapping().containsKey("0001")) {
                        result = mapConfig.getMapping().getPeriodMapping().get("0001");
                    }
                }
                if (StringUtils.isEmpty((String)result)) {
                    if (StringUtils.isNotEmpty((String)periodCode)) {
                        if (periodCode.length() > 4) {
                            periodCode = periodCode.substring(periodCode.length() - 4, periodCode.length());
                        } else if (periodCode.length() < 4) {
                            periodCode = "0000000".substring(0, 4 - periodCode.length()) + periodCode;
                        }
                        result = YearCode + mapConfig.getTaskInfo().getSingleTaskPeriod() + periodCode;
                    } else {
                        result = YearCode + mapConfig.getTaskInfo().getSingleTaskPeriod() + "0001";
                    }
                }
            }
        } else {
            result = singlePeriodCode;
        }
        return result;
    }

    @Override
    public String getSinglePeriodCode(TaskDataContext context, String netPeriodCode, int singlePeriodLen) {
        String periodCode = netPeriodCode;
        if (StringUtils.isNotEmpty((String)netPeriodCode)) {
            int idYear = netPeriodCode.indexOf("@");
            if (idYear > 0) {
                periodCode = netPeriodCode.substring(idYear + 1, netPeriodCode.length());
            } else {
                String newPeriodCode = "";
                if (null != context.getMapingCache().getMapConfig() && null != context.getMapingCache().getMapConfig().getMapping() && null != context.getMapingCache().getMapConfig().getMapping().getPeriodMapping()) {
                    for (Map.Entry<String, String> item : context.getMapingCache().getMapConfig().getMapping().getPeriodMapping().entrySet()) {
                        if (!StringUtils.isNotEmpty((String)item.getValue()) || !item.getValue().equalsIgnoreCase(netPeriodCode)) continue;
                        newPeriodCode = item.getKey();
                        break;
                    }
                }
                if (StringUtils.isEmpty((String)newPeriodCode) && StringUtils.isNotEmpty((String)periodCode)) {
                    if (singlePeriodLen > 0 && singlePeriodLen <= 4) {
                        periodCode = periodCode.substring(periodCode.length() - singlePeriodLen, periodCode.length());
                    } else if (singlePeriodLen > 4) {
                        periodCode = periodCode.substring(periodCode.length() - 4, periodCode.length());
                        periodCode = "00000000".substring(0, singlePeriodLen - 4) + periodCode;
                    }
                } else {
                    periodCode = newPeriodCode;
                }
            }
        }
        return periodCode;
    }
}

