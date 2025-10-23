/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.dataresource.util.SceneUtilService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  io.swagger.annotations.ApiOperation
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.dataresource.util.SceneUtilService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.ZBFieldEx;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.DisplayContent;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.LinkField;
import com.jiuqi.nr.zbquery.model.LinkResourceNode;
import com.jiuqi.nr.zbquery.model.NullRowDisplayMode;
import com.jiuqi.nr.zbquery.model.OpenType;
import com.jiuqi.nr.zbquery.model.OrderField;
import com.jiuqi.nr.zbquery.model.OrderMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryLayout;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.QueryOption;
import com.jiuqi.nr.zbquery.model.ZBField;
import com.jiuqi.nr.zbquery.model.ZBFieldType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.vo.DataEntryVO;
import com.jiuqi.nr.zbquery.rest.vo.DimensionVO;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.service.impl.ZBQueryResourceTreeServiceImpl;
import com.jiuqi.nr.zbquery.util.DataChangeUtils;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.SerializeUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringHelper;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/manage"})
public class DataEntryController {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryController.class);
    private NedisCacheManager cacheManager;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeTaskService runtimeTaskService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private SceneUtilService sceneUtilService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEFDCConfigService EFDCConfigService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private ZBQueryResourceTreeServiceImpl zbQueryResourceTreeService;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("ZBQueryModel");
    }

    @ApiOperation(value="\u83b7\u53d6queryModel\u5bf9\u8c61")
    @PostMapping(value={"/get_querymodelobject"})
    public ZBQueryModel getQueryModelObject(@RequestBody DataEntryVO dataEntryVO) throws Exception {
        return this.buildQueryModel(dataEntryVO);
    }

    @ApiOperation(value="\u83b7\u53d6queryModel\u7f13\u5b58id")
    @PostMapping(value={"/get_querymodelcache_id"})
    public String getQueryModelCacheId(@RequestBody DataEntryVO dataEntryVO) throws Exception {
        String uuid = UUID.randomUUID().toString();
        NedisCache cache = this.cacheManager.getCache("ZBQueryModel");
        if (cache != null) {
            cache.put(uuid, (Object)SerializeUtils.jsonSerializeToByte(this.buildQueryModel(dataEntryVO)));
        }
        return uuid;
    }

    @ApiOperation(value="\u4ece\u7f13\u5b58\u4e2d\u83b7\u53d6queryModel\u5bf9\u8c61")
    @PostMapping(value={"/get_querymodelobjectbycache"})
    public ZBQueryModel getQueryModelObjectByCache(@RequestBody String id) throws Exception {
        Cache.ValueWrapper valueWrapper;
        ZBQueryModel zBQueryModel = null;
        NedisCache cache = this.cacheManager.getCache("ZBQueryModel");
        if (cache != null && (valueWrapper = cache.get(id)) != null) {
            zBQueryModel = SerializeUtils.jsonDeserialize((byte[])valueWrapper.get());
        }
        return zBQueryModel;
    }

    public ZBQueryModel buildQueryModel(DataEntryVO dataEntryVO) throws Exception {
        this.buildEntityId(dataEntryVO);
        this.buildFMDMSeleted(dataEntryVO);
        this.addSchemeName(dataEntryVO);
        ZBQueryModel zbQueryModel = this.zbQueryInfoService.createQueryModel();
        HashMap<String, String> linkFullNameMap = new HashMap<String, String>();
        List<QueryObject> queryObjects = this.buildQueryObjects(dataEntryVO, linkFullNameMap);
        zbQueryModel.setQueryObjects(queryObjects);
        zbQueryModel.setExtendedDatas(DataEntryController.buildPierceParam(linkFullNameMap));
        List<QueryDimension> queryDimensions = this.buildDimensions(dataEntryVO);
        zbQueryModel.setDimensions(queryDimensions);
        zbQueryModel.setConditions(this.buildConditions(dataEntryVO));
        zbQueryModel.setLayout(this.buildLayout(dataEntryVO, queryObjects));
        Map<String, String> paramMap = dataEntryVO.getParamMap();
        if (paramMap != null && paramMap.containsKey("EFDC") && this.EFDCConfigService.existSolution(dataEntryVO.getTaskKey()).booleanValue()) {
            zbQueryModel.setHyperlinks(this.buildLinkFields(queryObjects, paramMap));
        }
        zbQueryModel.setOption(this.buildOption(dataEntryVO));
        this.dealAdjust(dataEntryVO, zbQueryModel);
        this.dealCurrency(dataEntryVO, zbQueryModel);
        this.dealCustom(dataEntryVO, zbQueryModel);
        return zbQueryModel;
    }

    public void buildEntityId(DataEntryVO dataEntryVO) {
        IPeriodEntity period;
        if (!this.keyNotNull(dataEntryVO, "FORMSCHEME") || CollectionUtils.isEmpty(dataEntryVO.getDimensionVOList()) || !StringUtils.hasText(dataEntryVO.getDimensionVOList().get(0).getDimensionName())) {
            return;
        }
        HashMap<String, Integer> dimNameIndex = new HashMap<String, Integer>();
        for (int i = 0; i < dataEntryVO.getDimensionVOList().size(); ++i) {
            DimensionVO dimensionVO = dataEntryVO.getDimensionVOList().get(i);
            if (dimensionVO.getDimensionName().equals("ADJUST")) {
                if (dataEntryVO.getParamMap() != null) {
                    dataEntryVO.getParamMap().put("ADJUST", dimensionVO.getValue());
                } else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ADJUST", dimensionVO.getValue());
                    dataEntryVO.setParamMap(map);
                }
            }
            dimNameIndex.put(dimensionVO.getDimensionName(), i);
        }
        ArrayList<DimensionVO> dimensionVOList = new ArrayList<DimensionVO>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dataEntryVO.getParamMap().get("FORMSCHEME"));
        IEntityDefine dw = this.entityMetaService.queryEntity(formScheme.getDw());
        if (dw != null && dimNameIndex.containsKey(dw.getDimensionName())) {
            DimensionVO dimensionVO = dataEntryVO.getDimensionVOList().get((Integer)dimNameIndex.get(dw.getDimensionName()));
            dimensionVO.setMessageAlias(dw.getDimensionName());
            dimensionVO.setEntityId(dw.getId());
            dimensionVO.setMasterEntity(true);
            if (!StringUtils.hasText(dimensionVO.getFullName())) {
                dimensionVO.setFullName(dw.getCode());
            }
            dimensionVOList.add(dimensionVO);
        }
        if ((period = this.periodEngineService.getPeriodAdapter().getPeriodEntity(formScheme.getDateTime())) != null && dimNameIndex.containsKey(period.getDimensionName())) {
            DimensionVO dimensionVO = dataEntryVO.getDimensionVOList().get((Integer)dimNameIndex.get(period.getDimensionName()));
            dimensionVO.setMessageAlias(period.getDimensionName());
            dimensionVO.setEntityId(period.getKey());
            if (!StringUtils.hasText(dimensionVO.getFullName())) {
                dimensionVO.setFullName(NrPeriodConst.PREFIX_CODE + period.getKey());
            }
            dimensionVOList.add(dimensionVO);
        }
        if (StringUtils.hasText(formScheme.getDims())) {
            for (String entity : formScheme.getDims().split(";")) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entity);
                if (entityDefine == null || !dimNameIndex.containsKey(entityDefine.getDimensionName())) continue;
                DimensionVO dimensionVO = dataEntryVO.getDimensionVOList().get((Integer)dimNameIndex.get(entityDefine.getDimensionName()));
                dimensionVO.setMessageAlias(entityDefine.getDimensionName());
                dimensionVO.setEntityId(entityDefine.getId());
                if (!StringUtils.hasText(dimensionVO.getFullName())) {
                    dimensionVO.setFullName(entityDefine.getCode());
                }
                dimensionVOList.add(dimensionVO);
            }
        }
        dataEntryVO.setDimensionVOList(dimensionVOList);
    }

    private static Map<String, String> buildPierceParam(Map<String, String> linkFullNameMap) {
        JSONObject propertyJSON = new JSONObject();
        for (String link : linkFullNameMap.keySet()) {
            propertyJSON.put(linkFullNameMap.get(link), (Object)link);
        }
        HashMap<String, String> pierceParam = new HashMap<String, String>();
        pierceParam.put("pierceParam", propertyJSON.toString());
        return pierceParam;
    }

    private boolean hasDetailZB(DataEntryVO dataEntryVO) {
        boolean detailZB = false;
        for (String linkId : dataEntryVO.getLinkIdList()) {
            ZBFieldEx zBFieldEx;
            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(linkId);
            DataField dataField = this.runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression());
            if (dataField == null || ZBFieldType.DETAIL != (zBFieldEx = DataChangeUtils.change2ZBField(dataField, null)).getZbType()) continue;
            detailZB = true;
            break;
        }
        return detailZB;
    }

    private void buildFMDMSeleted(DataEntryVO dataEntryVO) {
        HashSet<Integer> dataLinkTypes = new HashSet<Integer>();
        for (String linkId : dataEntryVO.getLinkIdList()) {
            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(linkId);
            dataLinkTypes.add(dataLinkDefine.getType().getValue());
        }
        if (dataLinkTypes.size() == 1 && dataLinkTypes.contains(DataLinkType.DATA_LINK_TYPE_FMDM.getValue())) {
            dataEntryVO.setFMDMSelected("org");
        }
        if (dataLinkTypes.size() == 1 && dataLinkTypes.contains(DataLinkType.DATA_LINK_TYPE_INFO.getValue())) {
            dataEntryVO.setFMDMSelected("info");
        }
        if (dataLinkTypes.size() == 2 && dataLinkTypes.contains(DataLinkType.DATA_LINK_TYPE_FMDM.getValue()) && dataLinkTypes.contains(DataLinkType.DATA_LINK_TYPE_INFO.getValue())) {
            dataEntryVO.setFMDMSelected("orgInfo");
        }
    }

    private void addSchemeName(DataEntryVO dataEntryVO) throws Exception {
        TaskDefine taskDefine = this.runtimeTaskService.queryTaskDefine(dataEntryVO.getTaskKey());
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        if (StringHelper.notNull((String)dataEntryVO.getTaskKey())) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String period = dataEntryVO.getDimensionVOList().stream().filter(v -> v.getFullName().contains(NrPeriodConst.PREFIX_CODE)).findFirst().get().getValue();
            for (int i = 0; i < dataEntryVO.getDimensionVOList().size(); ++i) {
                DimensionVO dimensionVO = dataEntryVO.getDimensionVOList().get(i);
                dimensionVO.setSchemeName(dataScheme.getCode());
                if (dimensionVO.isMasterEntity() || entityDefine.getId().equals(dimensionVO.getEntityId())) {
                    dimensionVO.setQueryDimensionType(QueryDimensionType.MASTER);
                }
                String value = dimensionVO.getValue();
                if (QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType() && this.isPierceQuery(dataEntryVO) && !value.contains(";")) {
                    IEntityTable entityTable = this.getEntityTable(taskDefine, period, dimensionVO.getEntityId(), dataEntryVO);
                    List list = this.showUnitAllChildren(dataEntryVO) ? entityTable.getAllChildRows(value) : entityTable.getChildRows(value);
                    for (IEntityRow entityRow : list) {
                        value = value + ";" + entityRow.getEntityKeyData();
                    }
                    dimensionVO.setValue(value);
                }
                this.dealPeriod(dataEntryVO, taskDefine, dimensionVO, value);
                if (QueryDimensionType.MASTER != dimensionVO.getQueryDimensionType() || !this.keyNotNull(dataEntryVO, "orgRange") || dataEntryVO.getParamMap().get("orgRange").indexOf(";") >= 0) continue;
                this.buildOrgValue(taskDefine, period, dataEntryVO, dimensionVO);
            }
        }
    }

    private void dealPeriod(DataEntryVO dataEntryVO, TaskDefine taskDefine, DimensionVO dimensionVO, String value) {
        if (!dimensionVO.getFullName().contains(NrPeriodConst.PREFIX_CODE)) {
            return;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dimensionVO.getEntityId());
        String fromPeriod = StringUtils.hasText(taskDefine.getFromPeriod()) ? taskDefine.getFromPeriod() : periodProvider.getPeriodCodeRegion()[0];
        String toPeriod = StringUtils.hasText(taskDefine.getToPeriod()) ? taskDefine.getToPeriod() : periodProvider.getPeriodCodeRegion()[periodProvider.getPeriodCodeRegion().length - 1];
        int periodType = periodProvider.getPeriodType(value);
        if (this.isHistquery(dataEntryVO)) {
            String calculatePeriod;
            String priorPeriod = "";
            int queryPeriod = this.getHistoryQueryPeriod(dataEntryVO);
            if (queryPeriod != -1) {
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(Integer.valueOf("-" + queryPeriod) + 1);
                priorPeriod = periodProvider.modify(value, periodModifier);
            } else if (PeriodType.CUSTOM.type() == periodType || PeriodType.YEAR.type() == periodType) {
                priorPeriod = periodProvider.priorPeriod(value);
            } else {
                IPeriodEntity entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimensionVO.getEntityId());
                int num = Integer.parseInt(value.substring(value.length() - 4));
                if (PeriodUtils.isPeriod13((String)entity.getCode(), (PeriodType)entity.getPeriodType()) && entity.getMinFiscalMonth() == 0) {
                    ++num;
                }
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(-num + 1);
                priorPeriod = periodProvider.modify(value, periodModifier);
            }
            String string = calculatePeriod = periodProvider.comparePeriod(priorPeriod, fromPeriod) > 0 ? priorPeriod : fromPeriod;
            value = queryPeriod != -1 && PeriodType.CUSTOM.type() == periodType ? this.getAllPeriod(priorPeriod, value, periodProvider) : calculatePeriod + ";" + value;
        } else {
            value = value + ";" + value;
        }
        dimensionVO.setValue(value);
        dimensionVO.setMinValue(fromPeriod);
        dimensionVO.setMaxValue(toPeriod);
    }

    private String getAllPeriod(String from, String to, IPeriodProvider periodProvider) {
        if (from != to) {
            String tempV = from + ";";
            String next = periodProvider.nextPeriod(from);
            while (!to.equals(next)) {
                tempV = tempV + next + ";";
                next = periodProvider.nextPeriod(next);
            }
            return tempV + to;
        }
        return from;
    }

    private boolean keyNotNull(DataEntryVO dataEntryVO, String key) {
        return dataEntryVO.getParamMap() != null && StringHelper.notNull((String)dataEntryVO.getParamMap().get(key));
    }

    private IEntityTable getEntityTable(TaskDefine taskDefine, String period, String entityId, DataEntryVO dataEntryVO) throws Exception {
        EntityViewDefine entityView;
        if (!entityId.equals(taskDefine.getDw())) {
            String contextFilterExpression = "";
            if (this.keyNotNull(dataEntryVO, "CONTEXTFILTEREXPRESSION")) {
                contextFilterExpression = dataEntryVO.getParamMap().get("CONTEXTFILTEREXPRESSION");
            }
            entityView = this.entityViewRunTimeController.buildEntityView(entityId, contextFilterExpression);
        } else {
            entityView = this.runTimeViewController.getViewByTaskDefineKey(taskDefine.getKey());
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        return query.executeReader((IContext)context);
    }

    private void buildOrgValue(TaskDefine taskDefine, String period, DataEntryVO dataEntryVO, DimensionVO dimensionVO) throws Exception {
        String orgRange = dataEntryVO.getParamMap().get("orgRange");
        if ("own".equals(orgRange)) {
            return;
        }
        String value = dimensionVO.getValue();
        IEntityTable entityTable = this.getEntityTable(taskDefine, period, dimensionVO.getEntityId(), dataEntryVO);
        if ("brother".equals(orgRange)) {
            IEntityRow entityRow = entityTable.findByEntityKey(value);
            String parentEntityKey = entityRow.getParentEntityKey();
            List list = entityTable.getChildRows(parentEntityKey);
            value = list.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.joining(";"));
        } else if ("own-child".equals(orgRange)) {
            List list = entityTable.getChildRows(value);
            for (IEntityRow entityRow : list) {
                value = value + ";" + entityRow.getEntityKeyData();
            }
        } else if ("own-all-child".equals(orgRange)) {
            List list = entityTable.getAllChildRows(value);
            for (IEntityRow entityRow : list) {
                value = value + ";" + entityRow.getEntityKeyData();
            }
        } else if ("child".equals(orgRange)) {
            List list = entityTable.getChildRows(value);
            value = list.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.joining(";"));
        } else if ("all-child".equals(orgRange)) {
            List list = entityTable.getAllChildRows(value);
            value = list.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.joining(";"));
        }
        dimensionVO.setValue(value);
    }

    private List<QueryObject> buildQueryObjects(DataEntryVO dataEntryVO, Map<String, String> linkFullNameMap) throws Exception {
        HashMap<String, QueryDimensionType> relatedDimensionMap = new HashMap<String, QueryDimensionType>();
        ArrayList<QueryObject> queryObjects = new ArrayList<QueryObject>();
        LinkedHashMap<String, String> fmdmZbs = new LinkedHashMap<String, String>();
        String schemeName = "";
        TaskDefine taskDefine = this.runtimeTaskService.queryTaskDefine(dataEntryVO.getTaskKey());
        if (StringHelper.notNull((String)dataEntryVO.getTaskKey())) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            schemeName = dataScheme.getCode();
        }
        ArrayList<DataField> dataFieldList = new ArrayList<DataField>();
        HashMap<String, String> linkFormatMap = new HashMap<String, String>();
        HashMap<String, String> linkFieldMap = new HashMap<String, String>();
        HashMap<Integer, Boolean> hideRow = new HashMap<Integer, Boolean>();
        HashMap<Integer, Boolean> hideCol = new HashMap<Integer, Boolean>();
        this.buildHideRowCol(dataEntryVO, hideRow, hideCol);
        dataEntryVO.setLinkIdList(new ArrayList<String>(new LinkedHashSet<String>(dataEntryVO.getLinkIdList())));
        List<DataLinkDefine> linkList = this.sortLinkList(dataEntryVO);
        for (DataLinkDefine dataLinkDefine : linkList) {
            if (hideRow.containsKey(dataLinkDefine.getPosY()) && ((Boolean)hideRow.get(dataLinkDefine.getPosY())).booleanValue() || hideCol.containsKey(dataLinkDefine.getPosX()) && ((Boolean)hideCol.get(dataLinkDefine.getPosX())).booleanValue()) continue;
            if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                fmdmZbs.put(dataLinkDefine.getLinkExpression(), dataLinkDefine.getCaptionFieldsString());
                continue;
            }
            DataField dataField = this.runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression());
            if (dataField == null) continue;
            dataFieldList.add(dataField);
            linkFieldMap.put(dataField.getKey(), dataLinkDefine.getKey());
            if (dataLinkDefine.getFormatProperties() == null) continue;
            linkFormatMap.put(dataField.getKey(), dataLinkDefine.getFormatProperties().getPattern());
        }
        for (DataField dataField : dataFieldList) {
            ZBFieldEx zBFieldEx = DataChangeUtils.change2ZBField(dataField, null);
            relatedDimensionMap.putAll(zBFieldEx.getRelatedDimensionMap());
            if (DataFieldKind.TABLE_FIELD_DIM.equals((Object)dataField.getDataFieldKind()) && DataChangeUtils.isChangeTableDim(dataField)) continue;
            linkFullNameMap.put((String)linkFieldMap.get(dataField.getKey()), zBFieldEx.getFullName());
            ZBField zBField = new ZBField();
            BeanUtils.copyProperties(zBFieldEx, zBField);
            if (linkFormatMap.containsKey(dataField.getKey())) {
                zBField.setShowFormat((String)linkFormatMap.get(dataField.getKey()));
            }
            if (this.showZBCodeTitle(dataEntryVO) && StringHelper.notNull((String)dataField.getRefDataEntityKey())) {
                zBField.setDisplayContent(DisplayContent.CODE_TITLE);
            }
            if (this.needSetMagnitude(dataEntryVO, dataField, "measure", false)) {
                zBField.setQueryMagnitude(DataChangeUtils.toMagnitudeValue(dataEntryVO.getParamMap().get("measure")));
            }
            if (this.needSetMagnitude(dataEntryVO, dataField, "decimal", true)) {
                this.setDecimal(dataEntryVO, zBField);
            }
            queryObjects.add(zBField);
        }
        Optional<DimensionVO> master = dataEntryVO.getDimensionVOList().stream().filter(e -> e.getQueryDimensionType() == QueryDimensionType.MASTER).findFirst();
        for (Map.Entry entry : relatedDimensionMap.entrySet()) {
            if (QueryDimensionType.INNER != entry.getValue()) continue;
            String dimKey = (String)entry.getKey();
            String dw = master.map(DimensionVO::getEntityId).orElse(null);
            ITree<IResourceTreeNode> treeNode = this.zbQueryResourceTreeService.queryDefaultDim(dimKey, QueryDimensionType.INNER, schemeName, dw);
            QueryDimension queryDimension = (QueryDimension)((IResourceTreeNode)treeNode.getData()).getQueryObject();
            DimensionVO dimensionVO = new DimensionVO();
            dimensionVO.setSchemeName(schemeName);
            dimensionVO.setId(queryDimension.getId());
            dimensionVO.setFullName(queryDimension.getFullName());
            dimensionVO.setTitle(queryDimension.getTitle());
            dimensionVO.setEntityId(queryDimension.getEntityId());
            dimensionVO.setQueryDimensionType(QueryDimensionType.INNER);
            dimensionVO.setOrgDimension(queryDimension.isOrgDimension());
            dimensionVO.setMessageAlias(queryDimension.getMessageAlias());
            dimensionVO.setVirtualDimension(queryDimension.isVirtualDimension());
            if (treeNode.hasChildren()) {
                ArrayList<QueryObject> children = new ArrayList<QueryObject>();
                for (ITree child : treeNode.getChildren()) {
                    if (!"NAME".equals(child.getCode())) continue;
                    children.add(((IResourceTreeNode)child.getData()).getQueryObject());
                    if (!this.showDimCodeTitle(dataEntryVO) || !StringHelper.notNull((String)dimensionVO.getEntityId()) || !"CODE".equals(child.getCode())) continue;
                    children.add(((IResourceTreeNode)child.getData()).getQueryObject());
                }
                dimensionVO.setChildren(children);
            }
            dataEntryVO.getDimensionVOList().add(dimensionVO);
        }
        this.setQueryDimensionType(dataEntryVO);
        if ("org".equals(dataEntryVO.getFMDMSelected())) {
            dataEntryVO.setDimensionVOList(dataEntryVO.getDimensionVOList().stream().filter(e -> QueryDimensionType.MASTER == e.getQueryDimensionType() || QueryDimensionType.PERIOD == e.getQueryDimensionType()).collect(Collectors.toList()));
        }
        boolean bl = "1".equals(this.nvwaSystemOptionService.get("NrZbQuerySystemOption", "DISPLAY_TIERED"));
        for (DimensionVO dimensionVO : dataEntryVO.getDimensionVOList()) {
            IPeriodEntity entity;
            if (dimensionVO.getQueryDimensionType() == QueryDimensionType.MDINFO) continue;
            String fullName = dimensionVO.getFullName();
            FieldGroup fieldGroup = new FieldGroup();
            fieldGroup.setSchemeName(dimensionVO.getSchemeName());
            fieldGroup.setId(StringUtils.hasText(dimensionVO.getId()) ? dimensionVO.getId() : fullName);
            fieldGroup.setName(fullName);
            fieldGroup.setFullName(fullName);
            fieldGroup.setTitle(StringUtils.hasText(dimensionVO.getTitle()) ? dimensionVO.getTitle() : "");
            fieldGroup.setAlias("");
            fieldGroup.setMessageAlias(StringUtils.hasText(dimensionVO.getMessageAlias()) ? dimensionVO.getMessageAlias() : "");
            fieldGroup.setVisible(true);
            fieldGroup.setParent(null);
            fieldGroup.setType(QueryObjectType.GROUP);
            fieldGroup.setGroupType(FieldGroupType.DIMENSION);
            fieldGroup.setDisplayTiered(bl);
            String entityCode = "";
            String entityTitle = "";
            if (dimensionVO.getFullName().contains(NrPeriodConst.PREFIX_CODE)) {
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimensionVO.getEntityId());
                if (entity != null) {
                    entityCode = entity.getCode();
                    entityTitle = entity.getTitle();
                    dimensionVO.setPeriodType(entity.getType());
                    fieldGroup.setMessageAlias(entity.getDimensionName());
                    dimensionVO.setMessageAlias(entity.getDimensionName());
                    dimensionVO.setMinFiscalMonth(entity.getMinFiscalMonth());
                    dimensionVO.setMaxFiscalMonth(entity.getMaxFiscalMonth());
                } else {
                    fieldGroup.setMessageAlias("NR_PERIOD");
                }
            } else if (StringHelper.notNull((String)dimensionVO.getEntityId()) && QueryDimensionType.INNER != dimensionVO.getQueryDimensionType() && (entity = this.entityMetaService.queryEntity(dimensionVO.getEntityId())) != null) {
                entityCode = entity.getCode();
                entityTitle = entity.getTitle();
                fieldGroup.setMessageAlias(entity.getDimensionName());
                dimensionVO.setMessageAlias(entity.getDimensionName());
            }
            if (StringHelper.notNull((String)entityCode) && QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType()) {
                fieldGroup.setEnablePierce(true);
                if (dimensionVO.getEntityId().contains("@ORG")) {
                    fieldGroup.setDisplayIndent(true);
                    OrgCategoryDO param = new OrgCategoryDO();
                    param.setName(entityCode);
                    PageVO orgcates = this.orgCategoryClient.list(param);
                    if (!orgcates.getRows().isEmpty()) {
                        Integer versionFlag = ((OrgCategoryDO)orgcates.getRows().get(0)).getVersionflag();
                        dimensionVO.setEnableVersion(versionFlag != null && versionFlag == 1);
                    }
                    if (!StringUtils.hasText(fieldGroup.getMessageAlias())) {
                        fieldGroup.setMessageAlias("MD_ORG");
                    }
                } else {
                    BaseDataDefineDTO filterCond = new BaseDataDefineDTO();
                    filterCond.setName(entityCode);
                    BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(filterCond);
                    if (Objects.nonNull(baseDataDefineDO)) {
                        dimensionVO.setEnableVersion(baseDataDefineDO.getVersionflag() == 1);
                    }
                }
            }
            if (StringHelper.notNull((String)entityTitle)) {
                dimensionVO.setTitle(entityTitle);
                fieldGroup.setTitle(entityTitle);
            }
            if (QueryDimensionType.INNER == dimensionVO.getQueryDimensionType() && !CollectionUtils.isEmpty(dimensionVO.getChildren())) {
                fieldGroup.setChildren(dimensionVO.getChildren());
            } else {
                fieldGroup.setChildren(this.buildDimAttribute(dimensionVO, dataEntryVO, fmdmZbs));
            }
            queryObjects.add(fieldGroup);
        }
        this.filterScene(dataEntryVO, queryObjects);
        return queryObjects;
    }

    private void buildHideRowCol(DataEntryVO dataEntryVO, Map<Integer, Boolean> hideRow, Map<Integer, Boolean> hideCol) {
        int i;
        if (!dataEntryVO.getParamMap().containsKey("formKey")) {
            return;
        }
        String formKey = dataEntryVO.getParamMap().get("formKey");
        BigDataDefine formDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        if (null == formDefine) {
            return;
        }
        if (formDefine.getData() == null) {
            return;
        }
        Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
        int rowCount = bytesToGrid.getRowCount();
        for (i = 0; i < rowCount; ++i) {
            hideRow.put(i, bytesToGrid.isRowHidden(i));
        }
        int colCount = bytesToGrid.getColumnCount();
        for (i = 0; i < colCount; ++i) {
            hideCol.put(i, bytesToGrid.isColumnHidden(i));
        }
    }

    private List<DataLinkDefine> sortLinkList(DataEntryVO vo) {
        LinkedHashSet<DataLinkDefine> sortSet = new LinkedHashSet<DataLinkDefine>();
        if (vo.getParamMap().containsKey("LinkArea")) {
            String[] regions;
            String linkArea = vo.getParamMap().get("LinkArea");
            for (String region : regions = linkArea.split(";")) {
                String[] links = region.split(",");
                List<DataLinkDefine> sortLinkDefines = this.sortRegionLinkList(vo, links);
                sortSet.addAll(sortLinkDefines);
            }
        } else {
            sortSet.addAll(this.sortRegionLinkList(vo, vo.getLinkIdList().toArray(new String[0])));
        }
        return new ArrayList<DataLinkDefine>(sortSet);
    }

    private List<DataLinkDefine> sortRegionLinkList(DataEntryVO vo, String[] links) {
        HashSet<String> regionKeySet = new HashSet<String>();
        ArrayList<DataRegionDefine> regionList = new ArrayList<DataRegionDefine>();
        ArrayList<DataLinkDefine> defines = new ArrayList<DataLinkDefine>();
        List<String> allLinks = vo.getLinkIdList();
        for (String linkId : links) {
            DataLinkDefine dataLinkDefine;
            if (!allLinks.contains(linkId) || (dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(linkId)) == null) continue;
            defines.add(dataLinkDefine);
            if (!regionKeySet.add(dataLinkDefine.getRegionKey())) continue;
            DataRegionDefine region = this.runTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            regionList.add(region);
        }
        ArrayList<DataLinkDefine> sortList = new ArrayList<DataLinkDefine>();
        Map<String, List<DataLinkDefine>> linkMap = defines.stream().collect(Collectors.groupingBy(DataLinkDefine::getRegionKey));
        for (DataRegionDefine region : regionList) {
            if (DataRegionKind.DATA_REGION_SIMPLE == region.getRegionKind()) {
                if (this.isFixColRow(vo)) {
                    this.ColRow(sortList, linkMap, region);
                    continue;
                }
                this.RowCol(sortList, linkMap, region);
                continue;
            }
            this.RowCol(sortList, linkMap, region);
        }
        return sortList;
    }

    private void ColRow(List<DataLinkDefine> sortList, Map<String, List<DataLinkDefine>> linkMap, DataRegionDefine region) {
        List list = linkMap.get(region.getKey()).stream().sorted((o1, o2) -> {
            if (o1.getPosX() > o2.getPosX()) {
                return 1;
            }
            if (o1.getPosX() == o2.getPosX()) {
                if (o1.getPosY() > o2.getPosY()) {
                    return 1;
                }
                if (o1.getPosY() == o2.getPosY()) {
                    return 0;
                }
            }
            return -1;
        }).collect(Collectors.toList());
        sortList.addAll(list);
    }

    private void RowCol(List<DataLinkDefine> sortList, Map<String, List<DataLinkDefine>> linkMap, DataRegionDefine region) {
        List list = linkMap.get(region.getKey()).stream().sorted((o1, o2) -> {
            if (o1.getPosY() > o2.getPosY()) {
                return 1;
            }
            if (o1.getPosY() == o2.getPosY()) {
                if (o1.getPosX() > o2.getPosX()) {
                    return 1;
                }
                if (o1.getPosX() == o2.getPosX()) {
                    return 0;
                }
            }
            return -1;
        }).collect(Collectors.toList());
        sortList.addAll(list);
    }

    private boolean dealInfo(List<QueryObject> queryObjects, String schemeName, DataLinkDefine dataLinkDefine, DataField dataField, DataEntryVO dataEntryVO) {
        String refDataEntityKey = dataField.getRefDataEntityKey();
        if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_INFO || !StringUtils.hasText(refDataEntityKey) || !BaseDataAdapterUtil.isBaseData((String)refDataEntityKey)) {
            return false;
        }
        IEntityDefine entity = this.entityMetaService.queryEntity(refDataEntityKey);
        if (entity == null) {
            return false;
        }
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
        DimensionVO dimensionVO = new DimensionVO();
        dimensionVO.setFullName(dataTable.getCode() + "." + dataField.getCode() + "." + entity.getCode());
        dimensionVO.setQueryDimensionType(QueryDimensionType.MDINFO);
        dataEntryVO.getDimensionVOList().add(dimensionVO);
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.setFullName(dataTable.getCode() + "." + dataField.getCode() + "." + entity.getCode());
        fieldGroup.setGroupType(FieldGroupType.CHILDDIMENSION);
        fieldGroup.setId(fieldGroup.getFullName());
        fieldGroup.setMessageAlias(dataTable.getCode() + "." + dataField.getCode());
        fieldGroup.setName(entity.getCode());
        fieldGroup.setParent(dataTable.getCode());
        fieldGroup.setSchemeName(schemeName);
        fieldGroup.setTitle(dataField.getTitle());
        fieldGroup.setType(QueryObjectType.GROUP);
        DimensionAttributeField dimensionAttributeField = new DimensionAttributeField();
        dimensionAttributeField.setFullName(dataTable.getCode() + "." + dataField.getCode());
        dimensionAttributeField.setId(dimensionAttributeField.getFullName());
        dimensionAttributeField.setName(dataField.getCode());
        dimensionAttributeField.setParent(dataTable.getCode());
        dimensionAttributeField.setRelatedDimension(entity.getCode());
        dimensionAttributeField.setSchemeName(schemeName);
        dimensionAttributeField.setTitle(entity.getTitle());
        dimensionAttributeField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
        fieldGroup.setDimAttribute(dimensionAttributeField);
        ArrayList<QueryObject> children = new ArrayList<QueryObject>();
        DimensionAttributeField codeField = new DimensionAttributeField();
        codeField.setDataType(dataField.getDataFieldType().getValue());
        codeField.setFullName(dataTable.getCode() + "." + dataField.getCode() + "." + entity.getCode() + "." + "CODE");
        codeField.setId(codeField.getFullName());
        codeField.setMessageAlias(codeField.getFullName());
        codeField.setName("CODE");
        codeField.setParent(dataTable.getCode() + "." + dataField.getCode() + "." + entity.getCode());
        codeField.setSchemeName(schemeName);
        codeField.setTitle(dataField.getTitle() + "\u4ee3\u7801");
        codeField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
        DimensionAttributeField nameField = new DimensionAttributeField();
        BeanUtils.copyProperties(codeField, nameField);
        nameField.setFullName(dataTable.getCode() + "." + dataField.getCode() + "." + entity.getCode() + "." + "NAME");
        nameField.setId(nameField.getFullName());
        nameField.setMessageAlias(nameField.getFullName());
        nameField.setName("NAME");
        nameField.setTitle(dataField.getTitle() + "\u540d\u79f0");
        children.add(codeField);
        children.add(nameField);
        fieldGroup.setChildren(children);
        queryObjects.add(fieldGroup);
        if (!dataEntryVO.getQueryDimensions().stream().map(QueryObject::getFullName).collect(Collectors.toList()).contains(dataTable.getCode())) {
            ArrayList queryDimensions = new ArrayList();
            QueryDimension mdInfoD = new QueryDimension();
            mdInfoD.setDimensionType(QueryDimensionType.MDINFO);
            mdInfoD.setFullName(dataTable.getCode());
            mdInfoD.setId(mdInfoD.getFullName());
            mdInfoD.setMessageAlias(mdInfoD.getFullName());
            mdInfoD.setName(mdInfoD.getFullName());
            mdInfoD.setSchemeName(schemeName);
            mdInfoD.setType(QueryObjectType.DIMENSION);
            dataEntryVO.addQueryDimensions(mdInfoD);
        }
        QueryDimension mdInfoFD = new QueryDimension();
        mdInfoFD.setDimensionType(QueryDimensionType.CHILD);
        mdInfoFD.setEntityId(refDataEntityKey);
        mdInfoFD.setFullName(dataTable.getCode() + "." + dataField.getCode() + "." + entity.getCode());
        mdInfoFD.setId(entity.getCode());
        mdInfoFD.setMessageAlias(dataTable.getCode() + "." + dataField.getCode());
        mdInfoFD.setName(entity.getCode());
        mdInfoFD.setParent(dataTable.getCode());
        mdInfoFD.setSchemeName(schemeName);
        mdInfoFD.setTitle(dataField.getTitle());
        mdInfoFD.setType(QueryObjectType.DIMENSION);
        dataEntryVO.addQueryDimensions(mdInfoFD);
        return true;
    }

    private boolean needSetMagnitude(DataEntryVO dataEntryVO, DataField dataField, String key, boolean setDecimal) {
        String measureUnit;
        if (setDecimal && dataField.getDataFieldType() == DataFieldType.INTEGER) {
            return false;
        }
        return this.keyNotNull(dataEntryVO, key) && (dataField.getDataFieldType() == DataFieldType.INTEGER || dataField.getDataFieldType() == DataFieldType.BIGDECIMAL) && StringUtils.hasLength(measureUnit = dataField.getMeasureUnit()) && measureUnit.startsWith("9493b4eb-6516-48a8-a878-25a63a23e63a;") && !measureUnit.endsWith("NotDimession");
    }

    private void setDecimal(DataEntryVO dataEntryVO, ZBField zBField) {
        int decimal = Integer.parseInt(dataEntryVO.getParamMap().get("decimal"));
        StringBuilder decimalStr = new StringBuilder();
        for (int i = 0; i < decimal; ++i) {
            decimalStr.append("0");
        }
        String formatStr = zBField.getShowFormat();
        if (StringHelper.notNull((String)formatStr)) {
            int index = formatStr.indexOf(".");
            if (index > 0) {
                String[] split = formatStr.split("\\.");
                zBField.setShowFormat(split[0] + "." + decimalStr + split[1].replace("0", ""));
            } else {
                zBField.setShowFormat(formatStr + "." + decimalStr);
            }
        }
    }

    private void setQueryDimensionType(DataEntryVO dataEntryVO) {
        TaskDefine taskDefine = this.runtimeTaskService.queryTaskDefine(dataEntryVO.getTaskKey());
        if (taskDefine == null) {
            return;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme == null) {
            return;
        }
        List allDataDims = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        block4: for (DimensionVO dimensionVO : dataEntryVO.getDimensionVOList()) {
            Optional<DataDimension> dataDimension = allDataDims.stream().filter(e -> e.getDimKey().equals(dimensionVO.getEntityId())).findFirst();
            if (!dataDimension.isPresent()) continue;
            DimensionType dimensionType = dataDimension.get().getDimensionType();
            switch (dimensionType) {
                case DIMENSION: {
                    dimensionVO.setQueryDimensionType(QueryDimensionType.SCENE);
                    continue block4;
                }
                case PERIOD: {
                    dimensionVO.setQueryDimensionType(QueryDimensionType.PERIOD);
                    continue block4;
                }
            }
            dimensionVO.setQueryDimensionType(QueryDimensionType.MASTER);
        }
    }

    private List<QueryObject> buildDimAttribute(DimensionVO dimensionVO, DataEntryVO dataEntryVO, Map<String, String> fmdmZbs) {
        ArrayList<QueryObject> children = new ArrayList<QueryObject>();
        String fullName = dimensionVO.getFullName();
        DimensionAttributeField dimensionAttributeField = new DimensionAttributeField();
        dimensionAttributeField.setSchemeName(dimensionVO.getSchemeName());
        dimensionAttributeField.setId(fullName + "_" + UUID.randomUUID().toString());
        dimensionAttributeField.setAlias("");
        dimensionAttributeField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
        dimensionAttributeField.setVisible(true);
        dimensionAttributeField.setDataType(6);
        dimensionAttributeField.setApplyType(DataFieldApplyType.NONE);
        dimensionAttributeField.setShowFormat("");
        dimensionAttributeField.setDisplaySum(false);
        dimensionAttributeField.setRelatedDimension("");
        dimensionAttributeField.setDisplayContent(DisplayContent.TITLE);
        if (QueryDimensionType.PERIOD == dimensionVO.getQueryDimensionType()) {
            if (PeriodType.CUSTOM == dimensionVO.getPeriodType() || PeriodType.WEEK == dimensionVO.getPeriodType()) {
                dimensionAttributeField.setFullName(fullName + "." + "P_TITLE");
                dimensionAttributeField.setName("P_TITLE");
            } else {
                dimensionAttributeField.setFullName(fullName + "." + "P_TIMEKEY");
                dimensionAttributeField.setName("P_TIMEKEY");
            }
            dimensionAttributeField.setTitle("\u65f6\u671f");
            dimensionAttributeField.setParent(fullName);
            dimensionAttributeField.setTimekey(true);
            dimensionAttributeField.setPeriodType(dimensionVO.getPeriodType());
        } else {
            if (fullName.indexOf(".") == fullName.lastIndexOf(".")) {
                dimensionAttributeField.setFullName(fullName + "." + "NAME");
                dimensionAttributeField.setName("NAME");
            } else {
                dimensionAttributeField.setFullName(fullName.substring(fullName.indexOf(".") + 1));
                dimensionAttributeField.setName(fullName.substring(fullName.lastIndexOf(".") + 1));
            }
            dimensionAttributeField.setTitle(dimensionVO.getTitle());
            dimensionAttributeField.setParent(fullName);
        }
        children.add(dimensionAttributeField);
        if (QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType() && this.showOrgCode(dataEntryVO) && !BaseDataAdapterUtil.isBaseData((String)dimensionVO.getEntityId())) {
            DimensionAttributeField orgCodeDimensionAttributeField = new DimensionAttributeField();
            BeanUtils.copyProperties(dimensionAttributeField, orgCodeDimensionAttributeField);
            children.add(this.buildOrgCodeDimensionAttributeField(orgCodeDimensionAttributeField, fullName));
        }
        if (QueryDimensionType.INNER == dimensionVO.getQueryDimensionType() && this.showDimCodeTitle(dataEntryVO) && StringHelper.notNull((String)dimensionVO.getEntityId())) {
            DimensionAttributeField dimCodeDimensionAttributeField = new DimensionAttributeField();
            BeanUtils.copyProperties(dimensionAttributeField, dimCodeDimensionAttributeField);
            dimCodeDimensionAttributeField.setName("CODE");
            dimCodeDimensionAttributeField.setFullName(fullName + "." + "CODE");
            dimCodeDimensionAttributeField.setTitle(dimensionAttributeField.getTitle() + "\u7f16\u7801");
            children.add(dimCodeDimensionAttributeField);
        }
        if (QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType()) {
            this.buildMasterDimAttr(dimensionVO, dataEntryVO, fmdmZbs, children, fullName, dimensionAttributeField);
        }
        return children;
    }

    private void buildMasterDimAttr(DimensionVO dimensionVO, DataEntryVO dataEntryVO, Map<String, String> fmdmZbs, List<QueryObject> children, String fullName, DimensionAttributeField dimensionAttributeField) {
        if (!StringUtils.hasText(dimensionVO.getEntityId())) {
            return;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(dimensionVO.getEntityId());
        List entityRefer = this.entityMetaService.getEntityRefer(dimensionVO.getEntityId());
        for (Map.Entry<String, String> entry : fmdmZbs.entrySet()) {
            String mdmZb = entry.getKey();
            DimensionAttributeField dimCodeDimensionAttributeField = new DimensionAttributeField();
            BeanUtils.copyProperties(dimensionAttributeField, dimCodeDimensionAttributeField);
            IEntityAttribute attribute = entityModel.getAttribute(mdmZb);
            if (attribute == null) continue;
            dimCodeDimensionAttributeField.setName(attribute.getName());
            dimCodeDimensionAttributeField.setTitle(attribute.getTitle());
            Optional<IEntityRefer> refer = entityRefer.stream().filter(e -> e.getOwnField().equals(mdmZb)).findFirst();
            if (refer.isPresent()) {
                dimCodeDimensionAttributeField.setFullName(fullName + "." + attribute.getName() + "." + refer.get().getReferEntityId().split("@")[0]);
            } else {
                dimCodeDimensionAttributeField.setFullName(fullName + "." + attribute.getName());
            }
            dimCodeDimensionAttributeField.setId(dimCodeDimensionAttributeField.getFullName());
            String referTableID = attribute.getReferTableID();
            this.dealReferTableID(entry, dimCodeDimensionAttributeField, referTableID);
            children.add(dimCodeDimensionAttributeField);
        }
        if ("org".equals(dataEntryVO.getFMDMSelected()) && !fmdmZbs.isEmpty() && fmdmZbs.containsKey("NAME")) {
            children.remove(0);
        }
    }

    private void dealReferTableID(Map.Entry<String, String> entry, DimensionAttributeField dimCodeDimensionAttributeField, String referTableID) {
        if (!StringUtils.hasLength(referTableID)) {
            return;
        }
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(referTableID);
        if (Objects.nonNull(tableModelDefine)) {
            dimCodeDimensionAttributeField.setRelatedDimension(tableModelDefine.getCode());
            if (entry.getValue().indexOf("CODE") > -1 && entry.getValue().indexOf("NAME") > -1) {
                dimCodeDimensionAttributeField.setDisplayContent(DisplayContent.CODE_TITLE);
            } else if (entry.getValue().indexOf("CODE") > -1) {
                dimCodeDimensionAttributeField.setDisplayContent(DisplayContent.CODE);
            }
        }
    }

    private DimensionAttributeField buildOrgCodeDimensionAttributeField(DimensionAttributeField dimensionAttributeField, String fullName) {
        dimensionAttributeField.setName("ORGCODE");
        dimensionAttributeField.setFullName(fullName + "." + "ORGCODE");
        dimensionAttributeField.setTitle(dimensionAttributeField.getTitle() + "\u7f16\u7801");
        return dimensionAttributeField;
    }

    private List<QueryDimension> buildDimensions(DataEntryVO dataEntryVO) {
        List<DimensionVO> dimensionVOList = dataEntryVO.getDimensionVOList();
        ArrayList<QueryDimension> dimensions = new ArrayList<QueryDimension>();
        for (DimensionVO dimensionVO : dimensionVOList) {
            String buildMsgAlias;
            String queryMsgAlias;
            if (dimensionVO.getQueryDimensionType() == QueryDimensionType.MDINFO) continue;
            String fullName = dimensionVO.getFullName();
            QueryDimension queryDimension = new QueryDimension();
            queryDimension.setSchemeName(dimensionVO.getSchemeName());
            queryDimension.setId(fullName);
            queryDimension.setName(fullName);
            queryDimension.setFullName(fullName);
            queryDimension.setAlias("");
            queryDimension.setType(QueryObjectType.DIMENSION);
            queryDimension.setVisible(true);
            queryDimension.setParent(null);
            queryDimension.setVirtualDimension(false);
            queryDimension.setEnableVersion(dimensionVO.isEnableVersion());
            if (QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType() || QueryDimensionType.SCENE == dimensionVO.getQueryDimensionType()) {
                if (dimensionVO.getEntityId().contains("@ORG")) {
                    queryDimension.setOrgDimension(true);
                }
                this.setBizKey(dimensionVO, queryDimension);
                queryDimension.setTitle(dimensionVO.getTitle());
            } else if (QueryDimensionType.PERIOD == dimensionVO.getQueryDimensionType()) {
                queryDimension.setPeriodType(dimensionVO.getPeriodType());
                queryDimension.setMinFiscalMonth(dimensionVO.getMinFiscalMonth());
                queryDimension.setMaxFiscalMonth(dimensionVO.getMaxFiscalMonth());
                if (StringHelper.notNull((String)dimensionVO.getTitle())) {
                    queryDimension.setTitle(dimensionVO.getTitle());
                } else {
                    queryDimension.setTitle(dimensionVO.getPeriodType().title());
                }
            } else {
                this.setBizKey(dimensionVO, queryDimension);
                queryDimension.setTitle(dimensionVO.getTitle());
                queryDimension.setVirtualDimension(dimensionVO.isVirtualDimension());
                if (StringHelper.notNull((String)dimensionVO.getEntityId())) {
                    queryDimension.setEntityId(dimensionVO.getEntityId());
                }
            }
            queryDimension.setDimensionType(dimensionVO.getQueryDimensionType());
            if (StringUtils.hasText(dimensionVO.getMessageAlias())) {
                queryDimension.setMessageAlias(dimensionVO.getMessageAlias());
            }
            if (StringUtils.hasText(queryMsgAlias = FullNameWrapper.getMessageAlias(queryDimension)) && (!StringUtils.hasText(buildMsgAlias = queryDimension.getMessageAlias()) || queryMsgAlias.contains(buildMsgAlias) || "P_TIMEKEY".equals(queryMsgAlias))) {
                queryDimension.setMessageAlias(queryMsgAlias);
            }
            if (dimensionVO.isOrgDimension()) {
                queryDimension.setOrgDimension(true);
            }
            dimensions.add(queryDimension);
        }
        if (!dataEntryVO.getQueryDimensions().isEmpty()) {
            dimensions.addAll(dataEntryVO.getQueryDimensions());
        }
        return dimensions;
    }

    private void setBizKey(DimensionVO dimensionVO, QueryDimension queryDimension) {
        try {
            if (StringHelper.notNull((String)dimensionVO.getEntityId())) {
                IEntityDefine entity;
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dimensionVO.getEntityId());
                IEntityAttribute bizKeyField = entityModel.getBizKeyField();
                if (bizKeyField != null) {
                    queryDimension.setBizKey(bizKeyField.getName());
                }
                if ((entity = this.entityMetaService.queryEntity(dimensionVO.getEntityId())) != null) {
                    queryDimension.setTreeStructure(entity.isTree());
                    queryDimension.setIsolation(entity.getIsolation());
                    if (queryDimension.getDimensionType() == QueryDimensionType.INNER) {
                        queryDimension.setMessageAlias(entity.getCode());
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private List<ConditionField> buildConditions(DataEntryVO dataEntryVO) {
        ArrayList<ConditionField> conditions = new ArrayList<ConditionField>();
        ArrayList<ConditionField> innerConditions = new ArrayList<ConditionField>();
        for (DimensionVO dimensionVO : dataEntryVO.getDimensionVOList()) {
            IEntityDefine entity;
            ConditionField conditionField = new ConditionField();
            conditionField.setFullName(dimensionVO.getFullName());
            conditionField.setObjectType(QueryObjectType.DIMENSION);
            conditionField.setQuickCondition(true);
            conditionField.setVisible(true);
            if (QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType()) {
                conditionField.setConditionType(ConditionType.MULTIPLE);
                conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                conditionField.setDefaultValues(dimensionVO.getValue().split(";"));
                if (conditions.size() > 0) {
                    conditions.add(1, conditionField);
                } else {
                    conditions.add(0, conditionField);
                }
                JSONObject propertyJSON = new JSONObject();
                if (this.keyNotNull(dataEntryVO, "NRContext")) {
                    propertyJSON.put("NRContext", (Object)dataEntryVO.getParamMap().get("NRContext"));
                }
                propertyJSON.put("taskKey", (Object)dataEntryVO.getTaskKey());
                conditionField.setProperties(propertyJSON.toString());
                continue;
            }
            if (QueryDimensionType.PERIOD == dimensionVO.getQueryDimensionType()) {
                if (PeriodType.CUSTOM == dimensionVO.getPeriodType()) {
                    conditionField.setConditionType(this.isPierceQuery(dataEntryVO) ? ConditionType.SINGLE : ConditionType.MULTIPLE);
                    conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                    if (this.isHistquery(dataEntryVO)) {
                        conditionField.setDefaultValues(dimensionVO.getValue().split(";"));
                    } else {
                        conditionField.setDefaultValues(new String[]{this.getMaxValue(dimensionVO.getValue())});
                    }
                    conditionField.setDefaultMaxValueMode(DefaultValueMode.APPOINT);
                    conditionField.setDefaultMaxValue(this.getMaxValue(dimensionVO.getValue()));
                } else if (this.isPierceQuery(dataEntryVO)) {
                    conditionField.setConditionType(ConditionType.SINGLE);
                    conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                    conditionField.setDefaultValues(dimensionVO.getValue().split(";"));
                } else {
                    conditionField.setConditionType(ConditionType.RANGE);
                    conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                    conditionField.setDefaultValues(dimensionVO.getValue().split(";"));
                    conditionField.setDefaultMaxValueMode(DefaultValueMode.APPOINT);
                    conditionField.setDefaultMaxValue(this.getMaxValue(dimensionVO.getValue()));
                }
                conditionField.setMinValue(dimensionVO.getMinValue());
                conditionField.setMaxValue(dimensionVO.getMaxValue());
                conditions.add(0, conditionField);
                continue;
            }
            if (QueryDimensionType.SCENE == dimensionVO.getQueryDimensionType()) {
                conditionField.setConditionType(ConditionType.SINGLE);
                conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                conditionField.setDefaultValues(new String[]{dimensionVO.getValue()});
                conditions.add(conditionField);
                continue;
            }
            if (QueryDimensionType.INNER != dimensionVO.getQueryDimensionType() || !StringHelper.notNull((String)dimensionVO.getEntityId()) || (entity = this.entityMetaService.queryEntity(dimensionVO.getEntityId())) != null && BaseDataAdapterUtil.isBaseData((String)dimensionVO.getEntityId()) && entity.getIsolation() != 0) continue;
            conditionField.setConditionType(ConditionType.MULTIPLE);
            conditionField.setDefaultValueMode(DefaultValueMode.NONE);
            innerConditions.add(conditionField);
        }
        innerConditions.forEach(field -> conditions.add((ConditionField)field));
        return conditions;
    }

    private String getMaxValue(String value) {
        String[] splits = value.split(";");
        if (splits.length > 0) {
            return splits[splits.length - 1];
        }
        return value;
    }

    private QueryLayout buildLayout(DataEntryVO dataEntryVO, List<QueryObject> queryObjectList) {
        LayoutField layoutField;
        List<DimensionVO> dimensionVOList = dataEntryVO.getDimensionVOList();
        boolean changLayout = !this.hasDetailZB(dataEntryVO) && this.isHistquery(dataEntryVO);
        QueryLayout layout = new QueryLayout();
        ArrayList<LayoutField> rows = new ArrayList<LayoutField>();
        ArrayList<LayoutField> cols = new ArrayList<LayoutField>();
        ArrayList<LayoutField> afterList = new ArrayList<LayoutField>();
        for (DimensionVO dimensionVO : dimensionVOList) {
            if (dimensionVO.getQueryDimensionType() == QueryDimensionType.MDINFO) continue;
            layoutField = new LayoutField();
            layoutField.setFullName(dimensionVO.getFullName());
            layoutField.setType(QueryObjectType.DIMENSION);
            if (QueryDimensionType.PERIOD == dimensionVO.getQueryDimensionType()) {
                if (this.isPierceQuery(dataEntryVO) || "org".equals(dataEntryVO.getFMDMSelected())) continue;
                if (changLayout) {
                    cols.add(layoutField);
                    continue;
                }
                rows.add(0, layoutField);
                continue;
            }
            if (QueryDimensionType.MASTER == dimensionVO.getQueryDimensionType()) {
                if (changLayout) {
                    rows.add(layoutField);
                    continue;
                }
                if (rows.size() > 0) {
                    rows.add(1, layoutField);
                    continue;
                }
                rows.add(0, layoutField);
                continue;
            }
            if (QueryDimensionType.SCENE == dimensionVO.getQueryDimensionType()) continue;
            afterList.add(layoutField);
        }
        for (DimensionVO dimensionVO : dimensionVOList) {
            if (dimensionVO.getQueryDimensionType() != QueryDimensionType.MDINFO) continue;
            layoutField = new LayoutField();
            layoutField.setFullName(dimensionVO.getFullName());
            layoutField.setType(QueryObjectType.DIMENSION);
            rows.add(layoutField);
        }
        for (QueryObject queryObject : queryObjectList) {
            if (QueryObjectType.ZB != queryObject.getType()) continue;
            layoutField = new LayoutField();
            layoutField.setFullName(queryObject.getFullName());
            layoutField.setType(QueryObjectType.ZB);
            if (ZBFieldType.FIXED == ((ZBField)queryObject).getZbType()) {
                if (changLayout) {
                    cols.add(layoutField);
                    continue;
                }
                rows.add(layoutField);
                continue;
            }
            afterList.add(layoutField);
        }
        if (changLayout) {
            afterList.forEach(field -> cols.add((LayoutField)field));
        } else {
            afterList.forEach(field -> rows.add((LayoutField)field));
        }
        layout.setRows(rows);
        layout.setCols(cols);
        if (changLayout) {
            layout.setZbVertical(true);
        }
        return layout;
    }

    private List<LinkField> buildLinkFields(List<QueryObject> queryObjects, Map<String, String> paramMap) {
        LinkResourceNode node = new LinkResourceNode();
        node.setType(paramMap.get("EFDC"));
        node.setTitle("efdc\u7a7f\u900f");
        node.setId(UUID.randomUUID().toString());
        ArrayList<LinkField> linkFields = new ArrayList<LinkField>();
        for (QueryObject queryObject : queryObjects) {
            if (QueryObjectType.ZB != queryObject.getType()) continue;
            LinkField linkField = new LinkField();
            linkField.setFullName(queryObject.getFullName());
            linkField.setResourceNode(node);
            linkField.setOpenType(OpenType.OPENBLANK);
            linkFields.add(linkField);
        }
        return linkFields;
    }

    private QueryOption buildOption(DataEntryVO dataEntryVO) {
        QueryOption queryOption = new QueryOption();
        queryOption.setLockRowHead(!StringUtils.hasText(dataEntryVO.getFMDMSelected()));
        queryOption.setNullRowDisplayMode(NullRowDisplayMode.valueOf(this.getNullRowDisplayMode(dataEntryVO)));
        if (this.keyNotNull(dataEntryVO, "emptyRowDisplayMode")) {
            queryOption.setNullRowDisplayMode(NullRowDisplayMode.valueOf(dataEntryVO.getParamMap().get("emptyRowDisplayMode")));
        }
        if (this.keyNotNull(dataEntryVO, "FORMSCHEME")) {
            queryOption.setReportScheme(dataEntryVO.getParamMap().get("FORMSCHEME"));
        }
        queryOption.setPageSize(this.getPageSize(dataEntryVO));
        queryOption.setQueryDetailRecord(this.hasDetailZB(dataEntryVO));
        return queryOption;
    }

    private boolean isPierceQuery(DataEntryVO dataEntryVO) {
        return "query".equals(dataEntryVO.getQueryType()) || "pierce".equals(dataEntryVO.getQueryType());
    }

    private boolean isHistquery(DataEntryVO dataEntryVO) {
        return "historyQuery".equals(dataEntryVO.getQueryType());
    }

    private int getHistoryQueryPeriod(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-defaultQueryPeriods", -1);
    }

    private boolean showUnitAllChildren(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-unitAllChildren");
    }

    private boolean showOrgCode(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-showOrgCode");
    }

    private boolean showZBCodeTitle(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-showZBItemCode");
    }

    private boolean showDimCodeTitle(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-showDimItemCode");
    }

    private boolean isFixColRow(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-fixColRow", true);
    }

    private int getPageSize(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-pageSize", 100);
    }

    private int getNullRowDisplayMode(DataEntryVO dataEntryVO) {
        return this.getParams(dataEntryVO, "-nullRowDisplayMode", NullRowDisplayMode.DISPLAY_ALLNULL.value());
    }

    private boolean getParams(DataEntryVO dataEntryVO, String paramName) {
        return this.getParams(dataEntryVO, paramName, false);
    }

    private boolean getParams(DataEntryVO dataEntryVO, String paramName, boolean flag) {
        String params = dataEntryVO.getParams();
        if (StringHelper.isNull((String)params)) {
            return flag;
        }
        String[] param = params.split(";");
        block0: for (int i = 0; i < param.length; ++i) {
            String[] item = param[i].split(":");
            for (int j = 0; j < item.length; ++j) {
                if (!paramName.equalsIgnoreCase(item[j])) continue;
                flag = item.length > 1 && "true".equalsIgnoreCase(item[1]);
                continue block0;
            }
        }
        return flag;
    }

    private int getParams(DataEntryVO dataEntryVO, String paramName, int defaultVal) {
        int num = defaultVal;
        String params = dataEntryVO.getParams();
        if (StringHelper.isNull((String)params)) {
            return num;
        }
        String[] param = params.split(";");
        block0: for (int i = 0; i < param.length; ++i) {
            String[] item = param[i].split(":");
            for (int j = 0; j < item.length; ++j) {
                if (!paramName.equalsIgnoreCase(item[j]) || item.length <= 1) continue;
                num = Integer.parseInt(item[1]);
                if (num >= 0) continue block0;
                num = defaultVal;
                continue block0;
            }
        }
        return num;
    }

    public void dealAdjust(DataEntryVO dataEntryVO, ZBQueryModel zbQueryModel) {
        if (!this.hasAdjust(dataEntryVO)) {
            return;
        }
        zbQueryModel.getQueryObjects().add(this.buildAdjustQueryObject());
        zbQueryModel.getDimensions().add(this.buildAdjustQueryDimension());
        Optional<QueryDimension> periodDimension = zbQueryModel.getDimensions().stream().filter(e -> e.getDimensionType() == QueryDimensionType.PERIOD).findFirst();
        if (periodDimension.isPresent()) {
            String periodFullName = periodDimension.get().getFullName();
            periodDimension.get().setEnableAdjust(true);
            Optional<ConditionField> conditionField = zbQueryModel.getConditions().stream().filter(e -> periodFullName.equals(e.getFullName())).findFirst();
            if (conditionField.isPresent()) {
                conditionField.get().setDefaultBinding(dataEntryVO.getParamMap().get("ADJUST"));
            }
            if ("info".equals(dataEntryVO.getFMDMSelected()) || "orgInfo".equals(dataEntryVO.getFMDMSelected())) {
                return;
            }
            LayoutField layoutField = new LayoutField();
            layoutField.setFullName("ADJUST");
            layoutField.setType(QueryObjectType.DIMENSION);
            List<LayoutField> rows = zbQueryModel.getLayout().getRows();
            for (int i = 0; i < rows.size(); ++i) {
                if (!periodFullName.equals(rows.get(i).getFullName())) continue;
                rows.add(i + 1, layoutField);
                return;
            }
            List<LayoutField> cols = zbQueryModel.getLayout().getCols();
            for (int i = 0; i < cols.size(); ++i) {
                if (!periodFullName.equals(cols.get(i).getFullName())) continue;
                cols.add(i + 1, layoutField);
                return;
            }
        }
    }

    private boolean hasAdjust(DataEntryVO dataEntryVO) {
        return dataEntryVO.getParamMap() != null && dataEntryVO.getParamMap().containsKey("ADJUST");
    }

    private QueryObject buildAdjustQueryObject() {
        FieldGroup queryObject = new FieldGroup();
        queryObject.setId("nrfs_dim_ADJUST");
        queryObject.setName("ADJUST");
        queryObject.setFullName("ADJUST");
        queryObject.setTitle("\u8c03\u6574\u671f");
        queryObject.setType(QueryObjectType.GROUP);
        queryObject.setMessageAlias("ADJUST.CODE");
        queryObject.setGroupType(FieldGroupType.DIMENSION);
        DimensionAttributeField attributeField = new DimensionAttributeField();
        attributeField.setId("nrfs_dim_ADJUST_dimattr_TITLE");
        attributeField.setName("TITLE");
        attributeField.setFullName("ADJUST.TITLE");
        attributeField.setTitle("\u8c03\u6574\u671f");
        attributeField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
        attributeField.setParent("ADJUST");
        attributeField.setMessageAlias("TITLE");
        attributeField.setDataType(6);
        attributeField.setTimekey(true);
        ArrayList<QueryObject> list = new ArrayList<QueryObject>();
        list.add(attributeField);
        queryObject.setChildren(list);
        return queryObject;
    }

    private QueryDimension buildAdjustQueryDimension() {
        QueryDimension queryDimension = new QueryDimension();
        queryDimension.setId("nrfs_dim_ADJUST");
        queryDimension.setName("ADJUST".toUpperCase());
        queryDimension.setFullName("ADJUST".toUpperCase());
        queryDimension.setTitle("\u8c03\u6574\u671f");
        queryDimension.setType(QueryObjectType.DIMENSION);
        queryDimension.setMessageAlias("ADJUST.CODE");
        queryDimension.setDimensionType(QueryDimensionType.SCENE);
        return queryDimension;
    }

    private void filterScene(DataEntryVO dataEntryVO, List<QueryObject> queryObjects) {
        DimensionVO dimension;
        List<DimensionVO> dataEntryDims = dataEntryVO.getDimensionVOList();
        String schemeName = "";
        Iterator<DimensionVO> iterator = dataEntryDims.iterator();
        while (iterator.hasNext() && !StringUtils.hasText(schemeName = (dimension = iterator.next()).getSchemeName())) {
        }
        Optional<DimensionVO> master = dataEntryVO.getDimensionVOList().stream().filter(e -> e.getQueryDimensionType() == QueryDimensionType.MASTER).findFirst();
        if (!master.isPresent()) {
            return;
        }
        if (!StringUtils.hasText(schemeName)) {
            return;
        }
        DataScheme scheme = this.runtimeDataSchemeService.getDataSchemeByCode(schemeName);
        if (scheme == null) {
            return;
        }
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(scheme.getKey(), DimensionType.DIMENSION);
        block1: for (DataDimension dimension2 : dimensions) {
            if (this.sceneUtilService.isAddScene(master.get().getEntityId(), dimension2)) continue;
            for (int i = 0; i < dataEntryDims.size(); ++i) {
                DimensionVO dataEntryDim = dataEntryDims.get(i);
                if (!dimension2.getDimKey().equals(dataEntryDim.getEntityId())) continue;
                dataEntryDims.remove(i);
                for (int j = 0; j < queryObjects.size(); ++j) {
                    if (!queryObjects.get(j).getFullName().equals(dataEntryDim.getFullName())) continue;
                    queryObjects.remove(j);
                    continue block1;
                }
                continue block1;
            }
        }
    }

    private void dealCurrency(DataEntryVO dataEntryVO, ZBQueryModel zbQueryModel) {
        if ("org".equals(dataEntryVO.getFMDMSelected())) {
            return;
        }
        boolean hasCurrency = false;
        String currencyFullName = "";
        List<DimensionVO> dimensionVOList = dataEntryVO.getDimensionVOList();
        List<QueryDimension> dimensions = zbQueryModel.getDimensions();
        Optional<DimensionVO> master = dimensionVOList.stream().filter(e -> e.getQueryDimensionType() == QueryDimensionType.MASTER).findFirst();
        if (!master.isPresent()) {
            return;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(master.get().getEntityId());
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            if (!"CURRENCYID".equals(attribute.getCode())) continue;
            Optional<QueryDimension> dimension = dimensions.stream().filter(e -> e.getDimensionType() == QueryDimensionType.SCENE && "MD_CURRENCY".equals(e.getFullName())).findFirst();
            if (!dimension.isPresent()) break;
            dimension.get().setEnableStandardCurrency(true);
            hasCurrency = true;
            currencyFullName = dimension.get().getFullName();
            break;
        }
        if (!hasCurrency) {
            return;
        }
        if ("info".equals(dataEntryVO.getFMDMSelected()) || "orgInfo".equals(dataEntryVO.getFMDMSelected())) {
            return;
        }
        List<LayoutField> rows = zbQueryModel.getLayout().getRows();
        for (int i = 0; i < rows.size(); ++i) {
            if (!master.get().getFullName().equals(rows.get(i).getFullName())) continue;
            LayoutField layoutField = new LayoutField();
            layoutField.setFullName(currencyFullName);
            layoutField.setType(QueryObjectType.DIMENSION);
            rows.add(i + 1, layoutField);
            return;
        }
    }

    private void dealCustom(DataEntryVO dataEntryVO, ZBQueryModel zbQueryModel) {
        Optional<DimensionVO> period;
        if (this.isHistquery(dataEntryVO) && this.getParams(dataEntryVO, "-historyPeriodDesc") && (period = dataEntryVO.getDimensionVOList().stream().filter(e -> e.getQueryDimensionType() == QueryDimensionType.PERIOD).findFirst()).isPresent()) {
            OrderField orderField = new OrderField();
            String fullName = period.get().getFullName() + ".";
            fullName = PeriodType.CUSTOM == period.get().getPeriodType() || PeriodType.WEEK == period.get().getPeriodType() ? fullName + "P_TITLE" : fullName + "P_TIMEKEY";
            orderField.setFullName(fullName);
            orderField.setMode(OrderMode.DESC);
            ArrayList<OrderField> list = new ArrayList<OrderField>();
            list.add(orderField);
            zbQueryModel.setOrders(list);
        }
    }
}

