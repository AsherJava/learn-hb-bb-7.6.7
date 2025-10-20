/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DBSimpleQueryUtils
 *  com.jiuqi.nr.graph.GraphHelper
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCacheObserver
 *  com.jiuqi.nr.graph.IGraphEditor
 *  com.jiuqi.nr.graph.INode
 *  com.jiuqi.nr.graph.cache.GraphCacheDefine
 *  com.jiuqi.nr.graph.function.AttrValueGetter
 *  com.jiuqi.nr.graph.internal.GraphBuilder
 *  com.jiuqi.nr.graph.label.ILabel
 *  com.jiuqi.nr.graph.label.IndexLabel
 *  com.jiuqi.nr.graph.label.NodeLabel
 *  com.jiuqi.nr.graph.util.GraphUtils
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.datascheme.internal.dao.impl.DBSimpleQueryUtils;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataRegionDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDimensionFilterDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaVariableDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeRegionSettingDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RuntimeDataLinkMappingDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimeDimensionFilterImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.RunTimeRegionSettingDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLink4AttrDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLink4EnumDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLinkDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.DataRegionDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupLinkDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormSchemeDTO;
import com.jiuqi.nr.definition.internal.runtime.service.AbstractNrParamCacheExpireService;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.SerializeListImpl;
import com.jiuqi.nr.graph.GraphHelper;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCacheObserver;
import com.jiuqi.nr.graph.IGraphEditor;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.internal.GraphBuilder;
import com.jiuqi.nr.graph.label.ILabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NrParamGraphService {
    private static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;
    public static final String NR_FORM_CACHE_NAME = "NR_FORM_CACHE_NAME";
    private static final AttrValueGetter<Object, String> ITEM_KEY_GETTER = o -> null == o ? null : ((IMetaItem)o).getKey();
    private static final GraphBuilder FORM_GRAPH_BUILDER = GraphHelper.createGraphBuilder((String)"FORM_GRAPH_BUILDER");
    public static final NodeLabel FORM_SCHEME = FORM_GRAPH_BUILDER.registerNode("FORM_SCHEME", o -> null == o ? null : ((FormSchemeDTO)o).getFormSchemeDefine().getKey());
    public static final NodeLabel FORM_SCHEME_LINK = FORM_GRAPH_BUILDER.registerNode("FORM_SCHEME_LINK", ITEM_KEY_GETTER);
    public static final NodeLabel FORM_GROUP = FORM_GRAPH_BUILDER.registerNode("FORM_GROUP", o -> null == o ? null : ((FormGroupDTO)o).getFormGroupDefine().getKey());
    public static final NodeLabel FORM = FORM_GRAPH_BUILDER.registerNode("FORM", o -> null == o ? null : ((FormDTO)o).getFormDefine().getKey());
    public static final NodeLabel DATAREGION = FORM_GRAPH_BUILDER.registerNode("DATAREGION", o -> null == o ? null : ((DataRegionDTO)o).getDataRegionDefine().getKey());
    public static final NodeLabel DATALINK = FORM_GRAPH_BUILDER.registerNode("DATALINK", ITEM_KEY_GETTER);
    private static final AttrValueGetter<INode, String> FORM_CODE_GETTER = o -> null == o ? null : ((FormDTO)o.getData(FormDTO.class)).getFormDefine().getFormCode();
    private static final AttrValueGetter<INode, String> FORM_SCHEME_CODE_GETTER = o -> null == o ? null : ((FormSchemeDTO)o.getData(FormSchemeDTO.class)).getFormSchemeDefine().getFormSchemeCode();
    public static final IndexLabel FORM_SCHEME_CODE = FORM_GRAPH_BUILDER.registerIndex("FORM_SCHEME_CODE", FORM_SCHEME, FORM_SCHEME_CODE_GETTER);
    public static final IndexLabel FORM_CODE = FORM_GRAPH_BUILDER.registerIndex("FORM_CODE", FORM, FORM_CODE_GETTER);
    @Autowired
    private RunTimeTaskDefineDao taskDefineDao;
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDefineDao;
    @Autowired
    private RunTimeTaskLinkDefineDao taskLinkDefineDao;
    @Autowired
    private RunTimeFormGroupDefineDao formGroupDao;
    @Autowired
    private RunTimeFormGroupLinkDao formGroupLinkDao;
    @Autowired
    private RunTimeFormDefineDao formDao;
    @Autowired
    private RunTimeDataRegionDefineDao dataRegionDao;
    @Autowired
    private RunTimeRegionSettingDefineDao dataRegionSettingDao;
    @Autowired
    private RunTimeDataLinkDefineDao dataLinkDao;
    @Autowired
    private RuntimeDataLinkMappingDefineDao dataLinkMappingDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;
    @Autowired
    private RunTimeFormulaVariableDefineDao formulaVariableDao;
    @Autowired
    private RunTimeDimensionFilterDao runTimeDimensionFilterDao;
    @Autowired
    private DBSimpleQueryUtils queryUtils;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private AbstractNrParamCacheExpireService cacheObserverService;
    private static final String SQL_IDX_REGION = "SELECT F.FM_FORMSCHEME, R.DR_KEY FROM NR_PARAM_DATAREGION R LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY ";
    private static final String SQL_IDX_LINK = "SELECT F.FM_FORMSCHEME, L.DL_KEY FROM NR_PARAM_DATALINK L LEFT JOIN NR_PARAM_DATAREGION R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY ";
    private final Supplier<Map<ILabel, Map<String, String>>> globalIndexSupplier = () -> {
        HashMap<Object, Map> globalIndex = new HashMap<Object, Map>();
        globalIndex.put(FORM_SCHEME_LINK, this.queryUtils.queryForMap("NR_PARAM_TASKLINK", "TR_KEY", "TR_CURRENT_FORM_SCHEME_KEY"));
        globalIndex.put(FORM_GROUP, this.queryUtils.queryForMap("NR_PARAM_FORMGROUP", "FG_KEY", "FG_FORM_SCHEME_KEY"));
        globalIndex.put(FORM, this.queryUtils.queryForMap("NR_PARAM_FORM", "FM_KEY", "FM_FORMSCHEME"));
        globalIndex.put(DATAREGION, this.queryUtils.queryForMap(SQL_IDX_REGION, (Object[])new String[0], "DR_KEY", "FM_FORMSCHEME"));
        globalIndex.put(DATALINK, this.queryUtils.queryForMap(SQL_IDX_LINK, (Object[])new String[0], "DL_KEY", "FM_FORMSCHEME"));
        globalIndex.put(FORM_SCHEME_CODE, this.queryUtils.queryForMap("NR_PARAM_FORMSCHEME", "FC_CODE", "FC_KEY"));
        return globalIndex;
    };
    private final Function<String, Map<ILabel, Map<String, String>>> globalIndexRefresher = formSchemeKey -> {
        HashMap<Object, Map> globalIndex = new HashMap<Object, Map>();
        globalIndex.put(FORM_SCHEME_LINK, this.queryUtils.queryForMap("NR_PARAM_TASKLINK", "TR_KEY", "TR_CURRENT_FORM_SCHEME_KEY", formSchemeKey));
        globalIndex.put(FORM_GROUP, this.queryUtils.queryForMap("NR_PARAM_FORMGROUP", "FG_KEY", "FG_FORM_SCHEME_KEY", formSchemeKey));
        globalIndex.put(FORM, this.queryUtils.queryForMap("NR_PARAM_FORM", "FM_KEY", "FM_FORMSCHEME", formSchemeKey));
        globalIndex.put(DATAREGION, this.queryUtils.queryForMap("SELECT F.FM_FORMSCHEME, R.DR_KEY FROM NR_PARAM_DATAREGION R LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY  WHERE F.FM_FORMSCHEME = ? ", (Object[])new String[]{formSchemeKey}, "DR_KEY", "FM_FORMSCHEME"));
        globalIndex.put(DATALINK, this.queryUtils.queryForMap("SELECT F.FM_FORMSCHEME, L.DL_KEY FROM NR_PARAM_DATALINK L LEFT JOIN NR_PARAM_DATAREGION R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY  WHERE F.FM_FORMSCHEME = ? ", (Object[])new String[]{formSchemeKey}, "DL_KEY", "FM_FORMSCHEME"));
        globalIndex.put(FORM_SCHEME_CODE, this.queryUtils.queryForMap("NR_PARAM_FORMSCHEME", "FC_CODE", "FC_KEY", formSchemeKey));
        return globalIndex;
    };

    public GraphCacheDefine getFormGraphCacheDefine() {
        return this.getFormGraphCacheDefine(true);
    }

    public GraphCacheDefine getFormGraphCacheDefine(boolean enableObserver) {
        GraphCacheDefine define = new GraphCacheDefine(NR_FORM_CACHE_NAME, FORM_GRAPH_BUILDER.getGraphDefine());
        define.enableGlobalIndex(this.globalIndexSupplier, this.globalIndexRefresher);
        if (enableObserver) {
            define.addObserver((IGraphCacheObserver)this.cacheObserverService);
        }
        return define;
    }

    public IGraphEditor createFormGraph() {
        return FORM_GRAPH_BUILDER.createGraph();
    }

    public IGraph getFormGraph(String formSchemeKey) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u52a0\u8f7d\u62a5\u8868\u65b9\u6848\u5f00\u59cb\uff1a{}", (Object)formSchemeKey);
        long millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() : 0L;
        IGraph graph = this.getFGraph(formSchemeKey);
        millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() - millis : millis;
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u52a0\u8f7d\u62a5\u8868\u65b9\u6848\u7ed3\u675f\uff1a{}\uff0c\u8017\u65f6\uff1a{}", (Object)formSchemeKey, (Object)millis);
        return graph;
    }

    private IGraph getFGraph(String formSchemeKey) {
        FormSchemeDefine formScheme = this.formSchemeDefineDao.getDefineByKey(formSchemeKey);
        if (null == formScheme) {
            return GraphUtils.emptyGraph();
        }
        IGraphEditor graph = this.createFormGraph();
        this.loadFormScheme(graph, formScheme);
        this.loadTaskLink(graph, formSchemeKey);
        List<String> formKeys = this.loadForms(graph, formSchemeKey);
        this.loadDataRegions(graph, formSchemeKey);
        this.loadDataLinks(graph, formScheme);
        this.loadFormBigDatas(graph, formKeys);
        this.loadDataLinkMapping(graph, formSchemeKey);
        return graph.finish();
    }

    private List<String> loadForms(IGraphEditor graph, String formSchemeKey) {
        List<FormGroupDefine> groupDefines = this.formGroupDao.queryDefinesByFormScheme(formSchemeKey);
        for (FormGroupDefine groupDefine : groupDefines) {
            graph.addNode(FORM_GROUP, (Object)new FormGroupDTO(groupDefine));
        }
        ArrayList<String> formKeys = new ArrayList<String>();
        List<FormDefine> formDefines = this.formDao.queryDefinesByFormScheme(formSchemeKey);
        for (FormDefine form : formDefines) {
            graph.addNode(FORM, (Object)new FormDTO(new RunTimeFormDefineGetterImpl(form)));
            formKeys.add(form.getKey());
        }
        List<RunTimeFormGroupLink> formGroupLinks = this.formGroupLinkDao.getFormGroupLinksByFormScheme(formSchemeKey);
        for (RunTimeFormGroupLink groupLink : formGroupLinks) {
            INode groupNode = graph.getNode(FORM_GROUP, groupLink.getGroupKey());
            INode formNode = graph.getNode(FORM, groupLink.getFormKey());
            if (null == groupNode || null == formNode) continue;
            FormGroupDTO formGroupDTO = (FormGroupDTO)groupNode.getData(FormGroupDTO.class);
            FormDTO formDTO = (FormDTO)formNode.getData(FormDTO.class);
            FormGroupLinkDTO formGroupLinkDTO = new FormGroupLinkDTO(formGroupDTO, formDTO, groupLink.getFormOrder());
            formGroupDTO.getFormGroupLinks().add(formGroupLinkDTO);
            formDTO.getFormGroupLinks().add(formGroupLinkDTO);
        }
        return formKeys;
    }

    private void loadTaskLink(IGraphEditor graph, String formSchemeKey) {
        try {
            List<TaskLinkDefine> links = this.taskLinkDefineDao.queryDefinesByCurrentFormScheme(formSchemeKey);
            graph.addNodes(FORM_SCHEME_LINK, links);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDataRegions(IGraphEditor graph, String formSchemeKey) {
        List<DataRegionDefine> regionDefines = this.dataRegionDao.getAllRegionsInFormScheme(formSchemeKey);
        Map<String, RegionSettingDefine> regionSettings = this.getRegionSettings(formSchemeKey);
        for (DataRegionDefine region : regionDefines) {
            DataRegionDTO dataRegionDTO = new DataRegionDTO(region, regionSettings.get(region.getRegionSettingKey()));
            INode node = graph.getNode(FORM, region.getFormKey());
            if (null == node) continue;
            ((FormDTO)node.getData(FormDTO.class)).getDataRegions().add(dataRegionDTO);
            graph.addNode(DATAREGION, (Object)dataRegionDTO);
        }
    }

    private void loadDataLinks(IGraphEditor graph, FormSchemeDefine formScheme) {
        List<DataLinkDefine> linkDefines = this.dataLinkDao.getDefinesByFormScheme(formScheme.getKey());
        HashMap<String, List> regionLinkDefines = new HashMap<String, List>();
        for (DataLinkDefine link : linkDefines) {
            regionLinkDefines.computeIfAbsent(link.getRegionKey(), k -> new ArrayList()).add(link);
        }
        Map<String, RunTimeBigDataTable> dataLinkAttrs = this.bigDataDao.queryLinkAttr(formScheme.getKey()).stream().collect(Collectors.toMap(RunTimeBigDataTable::getKey, v -> v));
        TaskDefine taskDefine = this.taskDefineDao.getDefineByKey(formScheme.getTaskKey());
        String dataSchemeKey = null;
        if (null != taskDefine) {
            dataSchemeKey = taskDefine.getDataScheme();
        } else {
            for (DataLinkDefine linkDefine : linkDefines) {
                DataField dataField;
                if (DataLinkType.DATA_LINK_TYPE_FIELD != linkDefine.getType() && DataLinkType.DATA_LINK_TYPE_INFO != linkDefine.getType() || null == (dataField = this.runtimeDataSchemeService.getDataField(linkDefine.getLinkExpression()))) continue;
                dataSchemeKey = dataField.getDataSchemeKey();
                break;
            }
        }
        HashSet<String> refEntityFields = new HashSet<String>();
        HashSet<String> fileFields = new HashSet<String>();
        List allDataFields = this.runtimeDataSchemeService.getAllDataField(dataSchemeKey);
        for (DataField dataField : allDataFields) {
            if (DataFieldType.FILE == dataField.getDataFieldType()) {
                fileFields.add(dataField.getKey());
                continue;
            }
            if (DataFieldType.STRING != dataField.getDataFieldType() || !StringUtils.hasText(dataField.getRefDataEntityKey())) continue;
            refEntityFields.add(dataField.getKey());
        }
        HashMap<String, String> strPool = new HashMap<String, String>();
        for (Map.Entry entry : regionLinkDefines.entrySet()) {
            INode node = graph.getNode(DATAREGION, (String)entry.getKey());
            if (null == node) continue;
            DataRegionDTO dataRegionDTO = (DataRegionDTO)node.getData(DataRegionDTO.class);
            for (DataLinkDefine linkDefine : (List)entry.getValue()) {
                DataLinkDefine dataLinkDTO;
                if (DataLinkType.DATA_LINK_TYPE_FIELD == linkDefine.getType() || DataLinkType.DATA_LINK_TYPE_INFO == linkDefine.getType()) {
                    RunTimeBigDataTable attrData = dataLinkAttrs.get(linkDefine.getKey());
                    DataLinkDTO dto = fileFields.contains(linkDefine.getLinkExpression()) ? new DataLink4AttrDTO(linkDefine, attrData) : (refEntityFields.contains(linkDefine.getLinkExpression()) ? new DataLink4EnumDTO(linkDefine) : new DataLinkDTO(linkDefine));
                    dto.setRegionKey(dataRegionDTO.getDataRegionDefine().getKey());
                    dto.setMeasureUnit(strPool.computeIfAbsent(dto.getMeasureUnit(), k -> k));
                    dataLinkDTO = dto;
                } else {
                    dataLinkDTO = linkDefine;
                }
                dataRegionDTO.getDataLinks().add(dataLinkDTO);
                graph.addNode(DATALINK, (Object)dataLinkDTO);
            }
        }
    }

    private Map<String, RegionSettingDefine> getRegionSettings(String formSchemeKey) {
        List<RegionSettingDefine> regionSettingDefines = this.dataRegionSettingDao.getDefinesByFormScheme(formSchemeKey);
        ArrayList<String> keys = new ArrayList<String>();
        HashMap<String, RegionSettingDefine> map = new HashMap<String, RegionSettingDefine>();
        for (RegionSettingDefine regionSetting : regionSettingDefines) {
            keys.add(regionSetting.getKey());
            map.put(regionSetting.getKey(), regionSetting);
        }
        List<RunTimeBigDataTable> bigDatas = this.bigDataDao.queryBigDataDefine(keys);
        for (RunTimeBigDataTable bigData : bigDatas) {
            RunTimeRegionSettingDefineImpl regionSetting = (RunTimeRegionSettingDefineImpl)map.get(bigData.getKey());
            if (null == regionSetting) continue;
            switch (bigData.getCode()) {
                case "REGION_TAB": {
                    regionSetting.setRegionTabSetting(new ArrayList<RegionTabSettingDefine>(RegionTabSettingData.bytesToRegionTabSettingData(bigData.getData())));
                    break;
                }
                case "REGION_ORDER": {
                    SerializeListImpl<RowNumberSetting> serializeUtil = new SerializeListImpl<RowNumberSetting>(RowNumberSetting.class);
                    List<RowNumberSetting> rowNumberSettings = serializeUtil.deserialize(bigData.getData(), RowNumberSetting.class);
                    regionSetting.setRowNumberSetting(rowNumberSettings);
                    break;
                }
                case "BIG_REGION_CARD": {
                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bigData.getData());
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        RecordCard parseObject = (RecordCard)objectInputStream.readObject();
                        regionSetting.setCardRecord(parseObject);
                        break;
                    }
                    catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "REGION_LT_ROW_STYLES": {
                    try {
                        SerializeListImpl<RegionEdgeStyleDefine> styleSerializeUtil = new SerializeListImpl<RegionEdgeStyleDefine>(RegionEdgeStyleDefine.class);
                        List<RegionEdgeStyleDefine> lastRowStyles = styleSerializeUtil.deserialize(bigData.getData(), RegionEdgeStyleDefine.class);
                        regionSetting.setLastRowStyle(lastRowStyles);
                        break;
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "BIG_REGION_SURVEY": {
                    regionSetting.setRegionSurvey(DesignFormDefineBigDataUtil.bytesToString(bigData.getData()));
                    break;
                }
            }
        }
        return map;
    }

    private void loadFormBigDatas(IGraphEditor graph, List<String> formKeys) {
        List<RunTimeBigDataTable> formBigDatas = this.bigDataDao.queryBigDataDefine(formKeys);
        for (RunTimeBigDataTable formBigData : formBigDatas) {
            INode node = graph.getNode(FORM, formBigData.getKey());
            if (null == node) continue;
            FormDTO formDTO = (FormDTO)node.getData(FormDTO.class);
            switch (formBigData.getCode()) {
                case "FORM_DATA": {
                    formDTO.setFormData(formBigData);
                    break;
                }
                case "FILLING_GUIDE": {
                    formDTO.setFormGuide(formBigData);
                    break;
                }
                case "BIG_SURVEY_DATA": {
                    formDTO.setFormSurvey(formBigData);
                    break;
                }
                case "BIG_SCRIPT_EDITOR": {
                    formDTO.setFormScript(formBigData);
                    break;
                }
            }
        }
    }

    private void loadFormScheme(IGraphEditor graph, FormSchemeDefine formScheme) {
        String dims;
        FormSchemeDTO formSchemeDTO = new FormSchemeDTO(formScheme);
        List<FormulaVariDefine> formulaVariables = this.formulaVariableDao.queryAllFormulaVariable(formScheme.getKey());
        formSchemeDTO.getFormulaVariables().addAll(formulaVariables);
        List<IDimensionFilter> dimensionFilters = this.runTimeDimensionFilterDao.getByFormSchemeKey(formScheme.getKey());
        if (dimensionFilters == null) {
            dimensionFilters = new ArrayList<IDimensionFilter>(10);
        }
        if (StringUtils.hasLength(dims = formScheme.getDims())) {
            String[] splitKey;
            for (String s : splitKey = dims.split(";")) {
                RunTimeDimensionFilterImpl dimensionFilter = new RunTimeDimensionFilterImpl(formScheme.getKey(), s);
                if (dimensionFilters.contains(dimensionFilter)) continue;
                dimensionFilters.add(dimensionFilter);
            }
        }
        formSchemeDTO.getDimensionFilters().addAll(dimensionFilters);
        graph.addNode(FORM_SCHEME, (Object)formSchemeDTO);
    }

    private void loadDataLinkMapping(IGraphEditor graph, String formSchemeKey) {
        List<DataLinkMappingDefine> mapping = this.dataLinkMappingDao.getByFormSchemeKey(formSchemeKey);
        Map<String, List<DataLinkMappingDefine>> dataLinkMapping = mapping.stream().collect(Collectors.groupingBy(DataLinkMappingDefine::getFormKey));
        for (Map.Entry<String, List<DataLinkMappingDefine>> entry : dataLinkMapping.entrySet()) {
            INode node = graph.getNode(FORM, entry.getKey());
            if (null == node) continue;
            FormDTO form = (FormDTO)node.getData(FormDTO.class);
            form.getDataLinkMapping().addAll((Collection<DataLinkMappingDefine>)entry.getValue());
        }
    }
}

