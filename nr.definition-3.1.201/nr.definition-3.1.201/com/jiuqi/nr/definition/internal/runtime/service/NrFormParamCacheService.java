/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.sql.exception.NotImplementedException
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 *  com.jiuqi.nr.graph.GraphHelper
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCache
 *  com.jiuqi.nr.graph.INode
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 *  com.jiuqi.nr.graph.cache.GraphCacheDefine
 *  com.jiuqi.nr.graph.util.GraphUtils
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.sql.exception.NotImplementedException;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.RegionPartitionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeBigDataService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkMappingService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionSettingService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskLinkService;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLink4AttrDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.DataRegionDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupLinkDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormSchemeDTO;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamGraphService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeBigDataService;
import com.jiuqi.nr.definition.util.NrDefinitionHelper;
import com.jiuqi.nr.graph.GraphHelper;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class NrFormParamCacheService
implements IRuntimeFormSchemeService,
IRuntimeTaskLinkService,
IRuntimeFormGroupService,
IRuntimeFormService,
IRuntimeDataRegionService,
IRuntimeDataRegionSettingService,
IRuntimeDataLinkService,
IRunTimeFormulaVariableService,
IRuntimeBigDataService,
IRuntimeDataLinkMappingService {
    public static final Map<Point, Point> POINT_POOL = new HashMap<Point, Point>();
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;
    @Autowired
    private NrParamGraphService paramGraphService;
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDao;
    @Autowired
    private RunTimeFormDefineDao formDao;
    @Autowired
    private RunTimeDataLinkDefineDao dataLinkDao;
    @Autowired
    private RuntimeBigDataService runtimeBigDataService;
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();
    private volatile IGraphCache cache;
    private static final Map<String, FormSchemeDefine> FORM_SCHEME_CACHE = new ConcurrentHashMap<String, FormSchemeDefine>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected IGraphCache getCache() {
        if (null == this.cache) {
            NrFormParamCacheService nrFormParamCacheService = this;
            synchronized (nrFormParamCacheService) {
                if (null == this.cache) {
                    this.cache = GraphHelper.createGraphCache((GraphCacheDefine)this.paramGraphService.getFormGraphCacheDefine());
                }
            }
        }
        return this.cache;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IGraph getGraph(String formSchemeKey) {
        if (!StringUtils.hasText(formSchemeKey)) {
            return GraphUtils.emptyGraph();
        }
        IGraph schemeGraph = this.getCache().get(formSchemeKey);
        if (null != schemeGraph) {
            return schemeGraph;
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(ParamResourceType.FORM.getId().concat(formSchemeKey));
        synchronized (mutex) {
            IGraph graph = this.getCache().get(formSchemeKey);
            if (null != graph) {
                return graph;
            }
            String lockName = this.nrDefinitionHelper.getLockName(ParamResourceType.FORM, formSchemeKey);
            return (IGraph)this.rwLockExecuterManager.getRWLockExecuter(lockName).tryRead(() -> {
                IGraph g = this.paramGraphService.getFormGraph(formSchemeKey);
                this.getCache().put(formSchemeKey, g);
                return g;
            });
        }
    }

    private IGraph getGraphByDataLink(String dataLinkKey) {
        return this.getCache().getByIndex(NrParamGraphService.DATALINK, dataLinkKey, this::getGraph);
    }

    private IGraph getGraphByDataRegion(String dataRegionKey) {
        return this.getCache().getByIndex(NrParamGraphService.DATAREGION, dataRegionKey, this::getGraph);
    }

    private IGraph getGraphByForm(String formKey) {
        return this.getCache().getByIndex(NrParamGraphService.FORM, formKey, this::getGraph);
    }

    private Map<String, IGraph> getGraphByForms(Collection<String> formKeys) {
        HashMap<String, IGraph> graphs = new HashMap<String, IGraph>();
        for (String formKey : formKeys) {
            graphs.put(formKey, this.getGraphByForm(formKey));
        }
        return graphs;
    }

    private IGraph getGraphByFormGroup(String formGroupKey) {
        return this.getCache().getByIndex(NrParamGraphService.FORM_GROUP, formGroupKey, this::getGraph);
    }

    private IGraph getGraphBySchemeCode(String schemeCode) {
        return this.getCache().getByIndex(NrParamGraphService.FORM_SCHEME_CODE, schemeCode, this::getGraph);
    }

    private IGraph getGraphByTaskLink(String key) {
        return this.getCache().getByIndex(NrParamGraphService.FORM_SCHEME_LINK, key, this::getGraph);
    }

    @Override
    public List<TaskLinkDefine> queryTaskLink(String currentFormSchemeKey) {
        IGraph graph = this.getGraph(currentFormSchemeKey);
        return graph.getNodes(NrParamGraphService.FORM_SCHEME_LINK).stream().map(n -> (TaskLinkDefine)n.getData(TaskLinkDefine.class)).collect(Collectors.toList());
    }

    @Override
    public TaskLinkDefine queryTaskLinkByCurrentFormSchemeAndNumber(String currentFormSchemeKey, String serialNumber) {
        return this.queryTaskLink(currentFormSchemeKey).stream().filter(l -> l.getLinkAlias().equals(serialNumber)).findFirst().orElse(null);
    }

    @Override
    public TaskLinkDefine queryTaskLinkByKey(String key) {
        IGraph graph = this.getGraphByTaskLink(key);
        INode taskLinkNode = graph.getNode(NrParamGraphService.FORM_SCHEME_LINK, key);
        if (null != taskLinkNode) {
            return (TaskLinkDefine)taskLinkNode.getData(TaskLinkDefine.class);
        }
        return null;
    }

    @Override
    public DataLinkDefine queryDataLink(String dataLinkKey) {
        IGraph graph = this.getGraphByDataLink(dataLinkKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATALINK, dataLinkKey);
        return null == node ? null : (DataLinkDefine)node.getData(DataLinkDefine.class);
    }

    @Override
    public List<DataLinkDefine> getDataLinksInForm(String formKey) {
        IGraph graph = this.getGraphByForm(formKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        ArrayList<DataLinkDefine> links = new ArrayList<DataLinkDefine>();
        for (DataRegionDTO dataRegion : ((FormDTO)node.getData(FormDTO.class)).getDataRegions()) {
            links.addAll(dataRegion.getDataLinks());
        }
        return links;
    }

    @Override
    public List<DataLinkDefine> getDataLinksInRegion(String dataRegionKey) {
        IGraph graph = this.getGraphByDataRegion(dataRegionKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.DATAREGION, dataRegionKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return new ArrayList<DataLinkDefine>(((DataRegionDTO)node.getData(DataRegionDTO.class)).getDataLinks());
    }

    @Override
    public List<String> getFieldKeysInRegion(String dataRegionKey) {
        List<DataLinkDefine> dataLinksInRegion = this.getDataLinksInRegion(dataRegionKey);
        return dataLinksInRegion.stream().filter(link -> DataLinkType.DATA_LINK_TYPE_FIELD == link.getType() || DataLinkType.DATA_LINK_TYPE_INFO == link.getType()).map(DataLinkDefine::getLinkExpression).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getFieldKeysInForm(String formKey) {
        return this.getDataLinksInForm(formKey).stream().filter(l -> (DataLinkType.DATA_LINK_TYPE_FIELD == l.getType() || DataLinkType.DATA_LINK_TYPE_INFO == l.getType()) && StringUtils.hasText(l.getLinkExpression())).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
    }

    @Override
    public DataLinkDefine queryDataLinkDefineByXY(String formKey, int posX, int posY) {
        FormDTO form = this.getFormDTO(formKey);
        return form.getDataLinkByPos(posX, posY);
    }

    @Override
    public DataLinkDefine queryDataLinkDefineByColRow(String formKey, int col, int row) {
        FormDTO form = this.getFormDTO(formKey);
        return form.getDataLinkByNum(col, row);
    }

    @Override
    public DataLinkDefine queryDataLinkDefineByUniquecode(String formKey, String uniqueCode) {
        FormDTO form = this.getFormDTO(formKey);
        if (null == form) {
            return null;
        }
        return form.getDataLinkByCode(uniqueCode);
    }

    @Override
    public List<DataLinkDefine> queryDataLinkDefineByUniquecodes(String formKey, Collection<String> uniqueCodes) {
        FormDTO form = this.getFormDTO(formKey);
        if (null == form) {
            return Collections.emptyList();
        }
        ArrayList<DataLinkDefine> results = new ArrayList<DataLinkDefine>();
        for (String uniqueCode : uniqueCodes) {
            DataLinkDefine link = form.getDataLinkByCode(uniqueCode);
            if (null == link) continue;
            results.add(link);
        }
        return results;
    }

    @Override
    public List<DataLinkDefine> getDataLinksInFormByField(String formKey, String fieldKey) {
        return this.getDataLinksInForm(formKey).stream().filter(l -> l.getLinkExpression().equals(fieldKey)).collect(Collectors.toList());
    }

    @Override
    public List<DataLinkDefine> getDataLinksInRegionByField(String regionKey, String fieldKey) {
        return this.getDataLinksInRegion(regionKey).stream().filter(l -> l.getLinkExpression().equals(fieldKey)).collect(Collectors.toList());
    }

    @Override
    public BigDataDefine getAttachmentDataFromLink(String linkKey) {
        IGraph graph = this.getGraphByDataLink(linkKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATALINK, linkKey);
        if (null == node) {
            return null;
        }
        DataLinkDefine dataLink = (DataLinkDefine)node.getData(DataLinkDefine.class);
        if (dataLink instanceof DataLink4AttrDTO) {
            return ((DataLink4AttrDTO)dataLink).getDataLinkAttr();
        }
        return null;
    }

    @Override
    public DataRegionDefine queryDataRegion(String dataRegionKey) {
        IGraph graph = this.getGraphByDataRegion(dataRegionKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATAREGION, dataRegionKey);
        return null == node ? null : ((DataRegionDTO)node.getData(DataRegionDTO.class)).getDataRegionDefine();
    }

    @Override
    public List<DataRegionDefine> getDataRegionsInForm(String formKey) {
        IGraph graph = this.getGraphByForm(formKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((FormDTO)node.getData(FormDTO.class)).getDataRegions().stream().map(DataRegionDTO::getDataRegionDefine).collect(Collectors.toList());
    }

    @Override
    public RegionSettingDefine getRegionSetting(String dataRegionKey) {
        IGraph graph = this.getGraphByDataRegion(dataRegionKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATAREGION, dataRegionKey);
        return null == node ? null : ((DataRegionDTO)node.getData(DataRegionDTO.class)).getRegionSettingDefine();
    }

    @Override
    public FormGroupDefine queryFormGroup(String formGroupKey) {
        IGraph graph = this.getGraphByFormGroup(formGroupKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM_GROUP, formGroupKey);
        return null == node ? null : ((FormGroupDTO)node.getData(FormGroupDTO.class)).getFormGroupDefine();
    }

    @Override
    public List<FormGroupDefine> getFormGroupsByForm(String formKey) {
        IGraph graph = this.getGraphByForm(formKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((FormDTO)node.getData(FormDTO.class)).getFormGroupLinks().stream().map(g -> g.getFormGroup().getFormGroupDefine()).collect(Collectors.toList());
    }

    @Override
    public List<FormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(NrParamGraphService.FORM_GROUP).stream().map(n -> ((FormGroupDTO)n.getData(FormGroupDTO.class)).getFormGroupDefine()).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
    }

    @Override
    public List<FormGroupDefine> getAllFormGroupsInFormScheme(String formSchemeKey) {
        return this.queryRootGroupsByFormScheme(formSchemeKey);
    }

    @Override
    public FormDefine queryForm(String formKey) {
        IGraph graph = this.getGraphByForm(formKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        return null == node ? null : ((FormDTO)node.getData(FormDTO.class)).getFormDefine();
    }

    @Override
    public List<FormDefine> queryForms(List<String> formKeys) {
        if (CollectionUtils.isEmpty(formKeys)) {
            return Collections.emptyList();
        }
        Map<String, IGraph> graphs = this.getGraphByForms(formKeys);
        ArrayList<FormDefine> forms = new ArrayList<FormDefine>();
        for (Map.Entry<String, IGraph> entry : graphs.entrySet()) {
            INode node = entry.getValue().getNode(NrParamGraphService.FORM, entry.getKey());
            if (node == null) continue;
            forms.add(((FormDTO)node.getData(FormDTO.class)).getFormDefine());
        }
        return forms;
    }

    @Override
    public FormDefine queryFormByCodeInScheme(String formSchemeKey, String formDefineCode) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM_CODE, formDefineCode);
        return null == node ? null : ((FormDTO)node.getData(FormDTO.class)).getFormDefine();
    }

    @Override
    public List<FormDefine> queryFormDefinesByFormScheme(String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (graph == null) {
            return Collections.emptyList();
        }
        ArrayList<FormDefine> forms = new ArrayList<FormDefine>();
        graph.forEachNode(NrParamGraphService.FORM, f -> forms.add(((FormDTO)f.getData(FormDTO.class)).getFormDefine()));
        return forms;
    }

    @Override
    public List<String> queryFormKeysByFormScheme(String formSchemeKey) {
        return this.queryFormDefinesByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }

    @Override
    public List<FormDefine> getFormsInGroupOrderByGroupLink(String formGroupKey, boolean isRecursion) {
        IGraph graph = this.getGraphByFormGroup(formGroupKey);
        return this.listFormDefineByGroup(graph, formGroupKey);
    }

    private List<FormDefine> listFormDefineByGroup(IGraph graph, String formGroupKey) {
        if (graph == null) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM_GROUP, formGroupKey);
        if (node == null) {
            return Collections.emptyList();
        }
        return ((FormGroupDTO)node.getData(FormGroupDTO.class)).getFormGroupLinks().stream().sorted(Comparator.comparing(FormGroupLinkDTO::getOrder)).map(f -> f.getForm().getFormDefine()).collect(Collectors.toList());
    }

    @Override
    public List<FormDefine> getFormsInGroup(String formGroupKey, boolean isRecursion) {
        return this.getFormsInGroupOrderByGroupLink(formGroupKey, isRecursion);
    }

    @Override
    public int getFormsCountInGroup(String formGroupKey, boolean isRecursion) {
        return this.getFormsInGroupOrderByGroupLink(formGroupKey, isRecursion).size();
    }

    @Override
    public BigDataDefine getReportDataFromForm(String formKey) {
        return this.getFormDTO(formKey).getFormData();
    }

    @Override
    public String getFillingGuideFromForm(String formKey) {
        RunTimeBigDataTable bigDataDefine = this.getFormDTO(formKey).getFormGuide();
        return bigDataDefine == null ? null : DesignFormDefineBigDataUtil.bytesToString(bigDataDefine.getData());
    }

    @Override
    public String getFrontScriptFromForm(String formKey) {
        RunTimeBigDataTable bigDataDefine = this.getFormDTO(formKey).getFormScript();
        return bigDataDefine == null ? null : DesignFormDefineBigDataUtil.bytesToString(bigDataDefine.getData());
    }

    @Override
    public String getSurveyDataFromForm(String formKey) {
        RunTimeBigDataTable bigDataDefine = this.getFormDTO(formKey).getFormSurvey();
        return bigDataDefine == null ? null : DesignFormDefineBigDataUtil.bytesToString(bigDataDefine.getData());
    }

    private FormDTO getFormDTO(String formKey) {
        IGraph graph = this.getGraphByForm(formKey);
        if (graph == null) {
            return FormDTO.EMPTY_FORM;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        return null == node ? FormDTO.EMPTY_FORM : (FormDTO)node.getData(FormDTO.class);
    }

    @Override
    public RunTimeFormGroupLink queryFormGroupLink(String formKey, String formGroupKey) throws Exception {
        return this.queryFormGroupLink(formGroupKey).stream().filter(g -> g.getFormKey().equals(formKey)).findFirst().orElse(null);
    }

    @Override
    public List<RunTimeFormGroupLink> queryFormGroupLink(String formGroupKey) {
        IGraph graph = this.getGraphByFormGroup(formGroupKey);
        if (graph == null) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM_GROUP, formGroupKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((FormGroupDTO)node.getData(FormGroupDTO.class)).getFormGroupLinks().stream().map(FormGroupLinkDTO::getRunTimeFormGroupLink).collect(Collectors.toList());
    }

    @Override
    public List<FormulaVariDefine> queryAllFormulaVariable(String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (graph == null) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM_SCHEME, formSchemeKey);
        if (node == null) {
            return Collections.emptyList();
        }
        return ((FormSchemeDTO)node.getData(FormSchemeDTO.class)).getFormulaVariables();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadFormSchemeCache() {
        Map<String, FormSchemeDefine> map = FORM_SCHEME_CACHE;
        synchronized (map) {
            if (FORM_SCHEME_CACHE.isEmpty()) {
                List<FormSchemeDefine> list = this.formSchemeDao.list();
                for (FormSchemeDefine define : list) {
                    FORM_SCHEME_CACHE.put(define.getKey(), define);
                }
            }
        }
    }

    protected void refreshFormSchemeCache() {
        FORM_SCHEME_CACHE.clear();
    }

    @Override
    public List<FormSchemeDefine> queryAllFormScheme() {
        if (FORM_SCHEME_CACHE.isEmpty()) {
            this.loadFormSchemeCache();
        }
        return FORM_SCHEME_CACHE.values().stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByTask(String taskKey) {
        return this.queryAllFormScheme().stream().filter(f -> f.getTaskKey().equals(taskKey)).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
    }

    @Override
    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        if (!StringUtils.hasText(formSchemeKey)) {
            return null;
        }
        if (FORM_SCHEME_CACHE.isEmpty()) {
            this.loadFormSchemeCache();
        }
        return FORM_SCHEME_CACHE.get(formSchemeKey);
    }

    @Override
    public FormSchemeDefine getFormschemeByCode(String code) {
        IGraph graph = this.getGraphBySchemeCode(code);
        INode schemeNode = graph.getNode(NrParamGraphService.FORM_SCHEME_CODE, code);
        if (null != schemeNode) {
            return ((FormSchemeDTO)schemeNode.getData(FormSchemeDTO.class)).getFormSchemeDefine();
        }
        return null;
    }

    @Override
    @Deprecated
    public List<DataLinkDefine> getDataLinksByField(String fieldKey) throws Exception {
        return this.dataLinkDao.getDefinesByFieldKey(fieldKey);
    }

    @Override
    @Deprecated
    public List<FormDefine> queryFormDefinesByTask(String taskKey) {
        return this.formDao.queryDefinesBytask(taskKey);
    }

    @Override
    @Deprecated
    public List<RegionPartitionDefine> getRegionPartitionDefines(String dataRegionKey) {
        throw new NotImplementedException();
    }

    @Override
    @Deprecated
    public List<FormGroupDefine> getChildFormGroups(String formGroupKey, boolean isRecursion) {
        return Collections.emptyList();
    }

    @Override
    public FormDefine queryForm(String formKey, String formScheme) {
        IGraph graph = this.getGraph(formScheme);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        return null == node ? null : ((FormDTO)node.getData(FormDTO.class)).getFormDefine();
    }

    @Override
    public FormGroupDefine queryFormGroup(String formGroupKey, String formScheme) {
        IGraph graph = this.getGraph(formScheme);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM_GROUP, formGroupKey);
        return null == node ? null : ((FormGroupDTO)node.getData(FormGroupDTO.class)).getFormGroupDefine();
    }

    @Override
    public List<FormGroupDefine> getFormGroupsByForm(String formKey, String formScheme) {
        IGraph graph = this.getGraph(formScheme);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((FormDTO)node.getData(FormDTO.class)).getFormGroupLinks().stream().map(l -> l.getFormGroup().getFormGroupDefine()).collect(Collectors.toList());
    }

    @Override
    public List<FormDefine> listFormByGroup(String formGroupKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        return this.listFormDefineByGroup(graph, formGroupKey);
    }

    @Override
    public BigDataDefine getFormStyle(String formKey, String formSchemeKey) {
        if (!StringUtils.hasText(formSchemeKey)) {
            return this.getReportDataFromForm(formKey);
        }
        IGraph graph = this.getGraph(formSchemeKey);
        if (graph == null) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        return null == node ? null : ((FormDTO)node.getData(FormDTO.class)).getFormData();
    }

    @Override
    public DataRegionDefine getDataRegion(String dataRegionKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATAREGION, dataRegionKey);
        return null == node ? null : ((DataRegionDTO)node.getData(DataRegionDTO.class)).getDataRegionDefine();
    }

    @Override
    public List<DataRegionDefine> listDataRegionByForm(String formKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((FormDTO)node.getData(FormDTO.class)).getDataRegions().stream().map(DataRegionDTO::getDataRegionDefine).collect(Collectors.toList());
    }

    @Override
    public DataRegionDefine getDataRegion(String regionCode, String formKey, String formSchemeKey) {
        return this.listDataRegionByForm(formKey, formSchemeKey).stream().filter(r -> r.getCode().equals(regionCode)).findAny().orElse(null);
    }

    @Override
    public RegionSettingDefine getRegionSettingByRegion(String dataRegionKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATAREGION, dataRegionKey);
        return null == node ? null : ((DataRegionDTO)node.getData(DataRegionDTO.class)).getRegionSettingDefine();
    }

    @Override
    public DataLinkDefine getDataLink(String dataLinkKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATALINK, dataLinkKey);
        return null == node ? null : (DataLinkDefine)node.getData(DataLinkDefine.class);
    }

    @Override
    public BigDataDefine getLinkFileSetting(String linkKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.DATALINK, linkKey);
        if (null == node) {
            return null;
        }
        DataLinkDefine dataLink = (DataLinkDefine)node.getData(DataLinkDefine.class);
        if (dataLink instanceof DataLink4AttrDTO) {
            return ((DataLink4AttrDTO)dataLink).getDataLinkAttr();
        }
        return null;
    }

    @Override
    public List<DataLinkDefine> listDataLinkByForm(String formKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        ArrayList<DataLinkDefine> links = new ArrayList<DataLinkDefine>();
        for (DataRegionDTO dataRegion : ((FormDTO)node.getData(FormDTO.class)).getDataRegions()) {
            links.addAll(dataRegion.getDataLinks());
        }
        return links;
    }

    @Override
    public List<DataLinkDefine> listDataLinkByDataRegion(String dataRegionKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.DATAREGION, dataRegionKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return new ArrayList<DataLinkDefine>(((DataRegionDTO)node.getData(DataRegionDTO.class)).getDataLinks());
    }

    @Override
    public DataLinkDefine getDataLinkByFormAndPos(String formKey, int posX, int posY, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return null;
        }
        return ((FormDTO)node.getData(FormDTO.class)).getDataLinkByCode(GraphUtils.concatIndex((Object[])new Object[]{posX, posY}));
    }

    @Override
    public DataLinkDefine getDataLinkByFormAndColRow(String formKey, int col, int row, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return null;
        }
        return ((FormDTO)node.getData(FormDTO.class)).getDataLinkByCode(GraphUtils.concatIndex((Object[])new Object[]{col, row}));
    }

    @Override
    public DataLinkDefine getDataLinkByFormAndUniquecode(String formKey, String uniqueCode, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return null;
        }
        return ((FormDTO)node.getData(FormDTO.class)).getDataLinkByCode(uniqueCode);
    }

    @Override
    public List<String> listFieldKeyByDataRegion(String dataRegionKey, String formSchemeKey) {
        return this.listDataLinkByDataRegion(dataRegionKey, formSchemeKey).stream().filter(l -> DataLinkType.DATA_LINK_TYPE_FIELD == l.getType() || DataLinkType.DATA_LINK_TYPE_INFO == l.getType()).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
    }

    @Override
    public List<String> listFieldKeyByForm(String formKey, String formSchemeKey) {
        return this.listDataLinkByForm(formKey, formSchemeKey).stream().filter(l -> (DataLinkType.DATA_LINK_TYPE_FIELD == l.getType() || DataLinkType.DATA_LINK_TYPE_INFO == l.getType()) && StringUtils.hasText(l.getLinkExpression())).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
    }

    @Override
    public BigDataDefine getBigDataDefineFromForm(String key, String type) {
        FormDTO formDTO = null;
        switch (type) {
            case "FORM_DATA": {
                formDTO = this.getFormDTO(key);
                return null == formDTO ? null : formDTO.getFormData();
            }
            case "FILLING_GUIDE": {
                formDTO = this.getFormDTO(key);
                return null == formDTO ? null : formDTO.getFormGuide();
            }
            case "BIG_SURVEY_DATA": {
                formDTO = this.getFormDTO(key);
                return null == formDTO ? null : formDTO.getFormSurvey();
            }
            case "BIG_SCRIPT_EDITOR": {
                formDTO = this.getFormDTO(key);
                return null == formDTO ? null : formDTO.getFormScript();
            }
        }
        return this.runtimeBigDataService.getBigDataDefineFromForm(key, type);
    }

    @Override
    public List<DataLinkMappingDefine> queryDataLinkMappingByFormKey(String formKey) {
        FormDTO formDTO = this.getFormDTO(formKey);
        return formDTO.getDataLinkMapping();
    }

    @Override
    public List<DataLinkMappingDefine> listDataLinkMappingByForm(String formKey, String formSchemeKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((FormDTO)node.getData(FormDTO.class)).getDataLinkMapping();
    }

    private FormDTO getFormDTO(String formSchemeKey, String formKey) {
        IGraph graph = this.getGraph(formSchemeKey);
        if (graph == null) {
            return FormDTO.EMPTY_FORM;
        }
        INode node = graph.getNode(NrParamGraphService.FORM, formKey);
        return null == node ? FormDTO.EMPTY_FORM : (FormDTO)node.getData(FormDTO.class);
    }

    @Override
    public List<DataLinkDefine> getDataLinkByFormAndLinkExp(String formSchemeKey, String formKey, String linkExpression) {
        return this.getFormDTO(formSchemeKey, formKey).getDataLinkByExp(linkExpression);
    }

    @Override
    public Set<String> listLinkExpressionByFormKey(String formSchemeKey, String formKey) {
        return this.getFormDTO(formSchemeKey, formKey).getDataLinkExp();
    }

    public NrFormParamCacheService getCacheService() {
        NrFormParamCacheService clone = new NrFormParamCacheService();
        clone.rwLockExecuterManager = this.rwLockExecuterManager;
        clone.nrDefinitionHelper = this.nrDefinitionHelper;
        clone.paramGraphService = this.paramGraphService;
        clone.formSchemeDao = this.formSchemeDao;
        clone.formDao = this.formDao;
        clone.dataLinkDao = this.dataLinkDao;
        clone.cache = GraphHelper.createGraphCache((GraphCacheDefine)this.paramGraphService.getFormGraphCacheDefine(false), (IGraphCache)this.cache);
        return clone;
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point)o;
            return this.x == point.x && this.y == point.y;
        }

        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }
    }
}

