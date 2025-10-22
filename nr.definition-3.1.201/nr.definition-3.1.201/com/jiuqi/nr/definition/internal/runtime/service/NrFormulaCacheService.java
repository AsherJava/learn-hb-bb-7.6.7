/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
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

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.common.DiskDataUtils;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.formulatracking.facade.FormulaTrackDefine;
import com.jiuqi.nr.definition.internal.dao.EFDCPeriodSettingDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaTrackService;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaFormDTO;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaGraphService;
import com.jiuqi.nr.definition.util.FormulaTrackUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NrFormulaCacheService
implements IRuntimeFormulaSchemeService,
IRuntimeFormulaService,
IRuntimeExpressionService,
IRuntimeFormulaTrackService {
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;
    @Autowired
    private IRuntimeDataLinkService dataLinkService;
    @Autowired
    private RunTimeFormulaSchemeDefineDao formulaSchemeDao;
    @Autowired
    private EFDCPeriodSettingDao efdcPeriodSettingDao;
    @Autowired
    private RunTimeFormulaDefineDao formulaDao;
    @Autowired
    private NrFormulaGraphService nrFormulaGraphService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();
    private volatile IGraphCache cache;
    private static final Map<String, FormulaSchemeDefine> FORMULA_SCHEME_CACHE = new ConcurrentHashMap<String, FormulaSchemeDefine>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadFormulaSchemeCache() {
        Map<String, FormulaSchemeDefine> map = FORMULA_SCHEME_CACHE;
        synchronized (map) {
            if (FORMULA_SCHEME_CACHE.isEmpty()) {
                Map<String, EFDCPeriodSettingDefineImpl> edfcSettings = this.efdcPeriodSettingDao.list().stream().collect(Collectors.toMap(EFDCPeriodSettingDefineImpl::getFormulaSchemeKey, v -> v));
                List<FormulaSchemeDefine> list = this.formulaSchemeDao.list();
                for (FormulaSchemeDefine define : list) {
                    ((RunTimeFormulaSchemeDefineImpl)define).setEfdcPeriodSettingDefineImpl(edfcSettings.get(define.getKey()));
                    FORMULA_SCHEME_CACHE.put(define.getKey(), define);
                }
            }
        }
    }

    protected void refreshFormulaSchemeCache() {
        FORMULA_SCHEME_CACHE.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected IGraphCache getCache() {
        if (null == this.cache) {
            NrFormulaCacheService nrFormulaCacheService = this;
            synchronized (nrFormulaCacheService) {
                if (null == this.cache) {
                    this.cache = GraphHelper.createGraphCache((GraphCacheDefine)this.nrFormulaGraphService.getGraphCacheDefine());
                }
            }
        }
        return this.cache;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IGraph getGraph(String schemeKey) {
        if (!StringUtils.hasText(schemeKey)) {
            return GraphUtils.emptyGraph();
        }
        IGraph schemeGraph = this.getCache().get(schemeKey);
        if (null != schemeGraph) {
            return schemeGraph;
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(ParamResourceType.FORMULA.getId().concat(schemeKey));
        synchronized (mutex) {
            IGraph graph = this.getCache().get(schemeKey);
            if (null != graph) {
                return graph;
            }
            String lockName = this.nrDefinitionHelper.getLockName(ParamResourceType.FORMULA, schemeKey);
            return (IGraph)this.rwLockExecuterManager.getRWLockExecuter(lockName).tryRead(() -> {
                IGraph g = this.nrFormulaGraphService.getFormulaGraph(schemeKey);
                this.getCache().put(schemeKey, g);
                return g;
            });
        }
    }

    private IGraph getGraphByFormula(String formulaKey) {
        return this.getCache().getByIndex(NrFormulaGraphService.FORMULA_DEFINE, formulaKey, this::getGraph);
    }

    @Override
    public void refreshObjCache(String formulaSchemeKey, EFDCPeriodSettingDefineImpl efdcPeriodSettingDefine) {
        FormulaSchemeDefine formulaScheme = this.queryFormulaScheme(formulaSchemeKey);
        if (null != formulaScheme) {
            ((RunTimeFormulaSchemeDefineImpl)formulaScheme).setEfdcPeriodSettingDefineImpl(efdcPeriodSettingDefine);
        }
    }

    @Override
    public List<String> queryDataLinkKeyByFormulaTracks(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, int direction) {
        List<FormulaTrackDefine> tracks = this.queryFormulaTracks(formulaSchemeKey, formKey, formulaType, direction);
        List<String> dataLinkCodes = tracks.stream().map(FormulaTrackDefine::getDataLinkCode).collect(Collectors.toList());
        return this.dataLinkService.queryDataLinkDefineByUniquecodes(formKey, dataLinkCodes).stream().filter(Objects::nonNull).map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }

    @Override
    public FormulaSchemeDefine queryFormulaScheme(String formulaSchemeKey) {
        if (!StringUtils.hasText(formulaSchemeKey)) {
            return null;
        }
        if (FORMULA_SCHEME_CACHE.isEmpty()) {
            this.loadFormulaSchemeCache();
        }
        return FORMULA_SCHEME_CACHE.get(formulaSchemeKey);
    }

    @Override
    public FormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String formSchemeKey) {
        return this.getFormulaSchemesByFormScheme(formSchemeKey, FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT).stream().filter(FormulaSchemeDefine::isDefault).findFirst().orElse(null);
    }

    @Override
    public List<FormulaSchemeDefine> getFormulaSchemesByFormScheme(String formSchemeKey) {
        if (FORMULA_SCHEME_CACHE.isEmpty()) {
            this.loadFormulaSchemeCache();
        }
        return FORMULA_SCHEME_CACHE.values().stream().filter(d -> d.getFormSchemeKey().equals(formSchemeKey)).sorted((m1, m2) -> m1.getOrder().compareTo(m2.getOrder())).collect(Collectors.toList());
    }

    @Override
    public List<FormulaSchemeDefine> getFormulaSchemesByFormScheme(String formSchemeKey, FormulaSchemeType formulaSchemeType) {
        return this.getFormulaSchemesByFormScheme(formSchemeKey).stream().filter(f -> formulaSchemeType == f.getFormulaSchemeType()).collect(Collectors.toList());
    }

    private INode getFormNode(String formulaSchemeKey, String formKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return null;
        }
        return graph.getNode(NrFormulaGraphService.FORMULA_FORM, formKey);
    }

    @Override
    public List<FormulaTrackDefine> getFormulaTrackByForm(String formulaSchemeKey, String formKey) {
        INode formNode = this.getFormNode(formulaSchemeKey, formKey);
        if (null == formNode) {
            return Collections.emptyList();
        }
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        expressions.addAll(this.getParsedExpressionByForm(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE));
        expressions.addAll(this.getParsedExpressionByForm(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CHECK));
        return FormulaTrackUtil.buildFormulaTrack(formulaSchemeKey, expressions, columnId -> {
            DataField dataField = this.dataSchemeService.getDataFieldByColumnKey(columnId);
            return null == dataField ? null : dataField.getKey();
        });
    }

    @Override
    public List<FormulaTrackDefine> getFormulaTrackByScheme(String formulaSchemeKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        List<IParsedExpression> expressions = graph.getNodes(NrFormulaGraphService.FORMULA_EXP).stream().map(n -> ((FormulaParsedExp)n.getData(FormulaParsedExp.class)).getParsedExpression()).collect(Collectors.toList());
        return FormulaTrackUtil.buildFormulaTrack(formulaSchemeKey, expressions, columnId -> {
            DataField dataField = this.dataSchemeService.getDataFieldByColumnKey(columnId);
            return null == dataField ? null : dataField.getKey();
        });
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForm(String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType type) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        if (!StringUtils.hasText(formKey)) {
            return graph.getNodes(NrFormulaGraphService.FORMULA_EXP).stream().map(n -> ((FormulaParsedExp)n.getData(FormulaParsedExp.class)).getParsedExpression()).filter(e -> type == e.getFormulaType()).collect(Collectors.toList());
        }
        INode formNode = graph.getNode(NrFormulaGraphService.FORMULA_FORM, formKey);
        if (null == formNode) {
            return Collections.emptyList();
        }
        ArrayList exps = new ArrayList();
        FormulaFormDTO formulaForm = (FormulaFormDTO)formNode.getData(FormulaFormDTO.class);
        List<FormulaDTO> formulas = formulaForm.getFormulas();
        for (FormulaDTO formula : formulas) {
            exps.addAll(formula.getExpressions().stream().map(FormulaParsedExp::getParsedExpression).filter(f -> type == f.getFormulaType()).collect(Collectors.toList()));
        }
        return Collections.unmodifiableList(exps);
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByFormulas(String formulaSchemeKey, String ... formulaKeys) {
        if (null == formulaKeys || 0 == formulaKeys.length) {
            return Collections.emptyList();
        }
        IGraph graph = this.getGraph(formulaSchemeKey);
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        for (String formulaKey : formulaKeys) {
            INode node = graph.getNode(NrFormulaGraphService.FORMULA_DEFINE, formulaKey);
            if (null == node) continue;
            expressions.addAll(((FormulaDTO)node.getData(FormulaDTO.class)).getExpressions().stream().map(FormulaParsedExp::getParsedExpression).collect(Collectors.toList()));
        }
        return expressions;
    }

    @Override
    public boolean isParsedFormulaFieldException(String formulaSchemeKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return false;
        }
        return Boolean.TRUE.equals(graph.getProperty("FORMULA_FIELD_ERROR"));
    }

    @Override
    public FormulaField getFormulaField(String formulaSchemeKey, String fieldKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrFormulaGraphService.FORMULA_FIELD, fieldKey);
        return null == node ? null : (FormulaField)node.getData(FormulaField.class);
    }

    @Override
    public List<FormulaField> getFormulaFields(String formulaSchemeKey, List<String> fieldKeys) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(NrFormulaGraphService.FORMULA_FIELD, fieldKeys.toArray(new String[0])).stream().filter(Objects::nonNull).map(n -> (FormulaField)n.getData(FormulaField.class)).collect(Collectors.toList());
    }

    @Override
    public FormulaParsedExp getFormulaParsedExp(String formulaSchemeKey, String expKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrFormulaGraphService.FORMULA_EXP, expKey);
        return null == node ? null : (FormulaParsedExp)node.getData(FormulaParsedExp.class);
    }

    @Override
    public List<FormulaParsedExp> getFormulaParsedExps(String formulaSchemeKey, List<String> expKeys) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(NrFormulaGraphService.FORMULA_EXP, expKeys.toArray(new String[0])).stream().filter(Objects::nonNull).map(n -> (FormulaParsedExp)n.getData(FormulaParsedExp.class)).collect(Collectors.toList());
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByForms(String formulaSchemeKey, List<String> formKeys, DataEngineConsts.FormulaType type) {
        if (null == formKeys) {
            return this.getParsedExpressionByForm(formulaSchemeKey, null, type);
        }
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        for (String formKey : formKeys) {
            if (!StringUtils.hasText(formKey)) continue;
            expressions.addAll(this.getParsedExpressionByForm(formulaSchemeKey, formKey, type));
        }
        return expressions;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionBetweenTable(String formulaSchemeKey, DataEngineConsts.FormulaType type) {
        return this.getParsedExpressionByForm(formulaSchemeKey, "00000000-0000-0000-0000-000000000000", type);
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(String dataLinkCode, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType type) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode linkNode = graph.getNode(NrFormulaGraphService.FORMULA_DATALINK, dataLinkCode);
        if (null == linkNode) {
            return Collections.emptyList();
        }
        Set data = (Set)linkNode.getData(Set.class);
        return data.stream().map(n -> ((FormulaParsedExp)n).getParsedExpression()).filter(n -> type == n.getFormulaType()).collect(Collectors.toList());
    }

    @Override
    public IParsedExpression getParsedExpression(String formulaSchemeKey, String expressionKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(NrFormulaGraphService.FORMULA_EXP, expressionKey);
        return null == node ? null : ((FormulaParsedExp)node.getData(FormulaParsedExp.class)).getParsedExpression();
    }

    @Override
    public Collection<String> getCalcCellDataLinks(String formulaSchemeKey, String formKey) {
        INode node = this.getFormNode(formulaSchemeKey, formKey);
        if (null == node) {
            return Collections.emptyList();
        }
        FormulaFormDTO formulaForm = (FormulaFormDTO)node.getData(FormulaFormDTO.class);
        return formulaForm.getCalDatalinks();
    }

    @Override
    public String getCalculateJsFormulasInForm(String formulaSchemeKey, String formKey) {
        if (!StringUtils.hasText(formKey)) {
            return null;
        }
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return null;
        }
        if (this.nrFormulaGraphService.enableJsDiskCache()) {
            return DiskDataUtils.read(DiskDataUtils.tempPath("NR_FORMULA_JS", formulaSchemeKey, formKey));
        }
        INode node = graph.getNode(NrFormulaGraphService.FORMULA_FORM, formKey);
        if (null == node) {
            return null;
        }
        return ((FormulaFormDTO)node.getData(FormulaFormDTO.class)).getJs();
    }

    @Override
    public List<CalcItem> getDimensionCalcCells(String formulaSchemeKey, String formKey) {
        ArrayList<CalcItem> calcItems = new ArrayList<CalcItem>();
        List<IParsedExpression> expressions = this.getParsedExpressionByForm(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE);
        if (expressions.isEmpty()) {
            return calcItems;
        }
        HashMap<Integer, String> linkMap = new HashMap<Integer, String>();
        int index = 0;
        for (IParsedExpression iParsedExpression : expressions) {
            DynamicDataNode assignNode = iParsedExpression.getAssignNode();
            QueryField queryField = assignNode == null ? null : assignNode.getQueryField();
            if (queryField == null || queryField.getDimensionRestriction() == null) continue;
            CalcItem calcItem = new CalcItem();
            calcItem.setDimValues(new DimensionValueSet(queryField.getDimensionRestriction()));
            if (assignNode.getDataLink() != null) {
                linkMap.put(index, assignNode.getDataLink().getDataLinkCode());
            } else {
                calcItem.setLinkId(queryField.getUID());
            }
            ++index;
            calcItems.add(calcItem);
        }
        if (!calcItems.isEmpty()) {
            List<DataLinkDefine> dataLinkDefines = this.dataLinkService.getDataLinksInForm(formKey);
            Map<String, String> fieldLinkMap = dataLinkDefines.stream().collect(Collectors.toMap(t -> t.getLinkExpression(), t -> t.getKey(), (oldValue, newValue) -> oldValue));
            Map<String, String> linkCodeMap = dataLinkDefines.stream().collect(Collectors.toMap(t -> t.getUniqueCode(), t -> t.getKey(), (oldValue, newValue) -> oldValue));
            for (index = calcItems.size() - 1; index >= 0; --index) {
                CalcItem calcItem = (CalcItem)calcItems.get(index);
                if (linkMap.containsKey(index)) {
                    String linkCode = (String)linkMap.get(index);
                    if (linkCodeMap.containsKey(linkCode)) {
                        calcItem.setLinkId(linkCodeMap.get(linkCode));
                        continue;
                    }
                    calcItems.remove(index);
                    continue;
                }
                if (fieldLinkMap.containsKey(calcItem.getLinkId())) {
                    String linkId = calcItem.getLinkId();
                    calcItem.setLinkId(fieldLinkMap.get(linkId));
                    continue;
                }
                calcItems.remove(index);
            }
        }
        return calcItems;
    }

    @Override
    public List<IParsedExpression> getParsedExpressionByDataLink(List<String> linkCodes, String formulaSchemeKey, String formKey, DataEngineConsts.FormulaType formulaType, Integer direction) {
        if (formKey == null) {
            return Collections.emptyList();
        }
        List<FormulaTrackDefine> formulaTracks = this.getFormulaTrackByForm(formulaSchemeKey, formKey);
        List<String> expressionKeys = formulaTracks.stream().filter(o -> linkCodes.contains(o.getDataLinkCode()) && Objects.equals(o.getFormulaType(), formulaType.getValue()) && Objects.equals(o.getFormulaDataDirection(), direction)).map(FormulaTrackDefine::getExpressionKey).collect(Collectors.toList());
        List<IParsedExpression> expressions = this.getParsedExpressions(formulaSchemeKey, expressionKeys);
        return expressions.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<IParsedExpression> getParsedExpressions(String formulaSchemeKey, List<String> expressionKeys) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        List nodes = graph.getNodes(NrFormulaGraphService.FORMULA_EXP, expressionKeys.toArray(new String[0]));
        return nodes.stream().map(n -> ((FormulaParsedExp)n.getData(FormulaParsedExp.class)).getParsedExpression()).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getBalanceZBExpressionByForm(String formulaSchemeKey, String formKey) {
        INode node = this.getFormNode(formulaSchemeKey, formKey);
        if (null == node) {
            return Collections.emptyMap();
        }
        FormulaFormDTO formulaForm = (FormulaFormDTO)node.getData(FormulaFormDTO.class);
        return formulaForm.getBlanceExpressions();
    }

    @Override
    public Map<String, String> getEffectiveForms(String formulaSchemeKey, List<String> formulaKeys) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyMap();
        }
        HashMap<String, String> result = new HashMap<String, String>();
        List nodes = graph.getNodes(NrFormulaGraphService.FORMULA_DEFINE, formulaKeys.toArray(new String[0]));
        for (INode node : nodes) {
            FormulaDTO formula = (FormulaDTO)node.getData(FormulaDTO.class);
            result.put(formula.getFormulaKey(), formula.getEffectiveForm());
        }
        return result;
    }

    @Override
    public FormulaDefine queryFormula(String formulaKey) {
        IGraph graph = this.getGraphByFormula(formulaKey);
        INode node = graph.getNode(NrFormulaGraphService.FORMULA_DEFINE, formulaKey);
        return null == node ? null : ((FormulaDTO)node.getData(FormulaDTO.class)).getFormulaDefine();
    }

    @Override
    public FormulaDefine findFormula(String formulaDefineCode, String formulaSchemeKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        INode node = graph.getNode(NrFormulaGraphService.FORMULA_DEFINE_CODE, formulaDefineCode);
        return null == node ? null : ((FormulaDTO)node.getData(FormulaDTO.class)).getFormulaDefine();
    }

    @Override
    public List<FormulaDefine> getFormulasInScheme(String formulaSchemeKey) {
        IGraph graph = this.getGraph(formulaSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(NrFormulaGraphService.FORMULA_DEFINE).stream().map(n -> ((FormulaDTO)n.getData(FormulaDTO.class)).getFormulaDefine()).collect(Collectors.toList());
    }

    private boolean compareType(FormulaDefine formula, DataEngineConsts.FormulaType formulaType) {
        if (null == formula) {
            return false;
        }
        switch (formulaType) {
            case CALCULATE: {
                return formula.getUseCalculate();
            }
            case CHECK: {
                return formula.getUseCheck();
            }
            case BALANCE: {
                return formula.getUseBalance();
            }
        }
        return false;
    }

    @Override
    public List<FormulaDefine> getFormulasInScheme(String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        return this.getFormulasInScheme(formulaSchemeKey).stream().filter(f -> this.compareType((FormulaDefine)f, formulaType)).collect(Collectors.toList());
    }

    @Override
    public List<FormulaDefine> getFormulasInForm(String formulaScheme, String formkey) {
        INode node;
        IGraph graph = this.getGraph(formulaScheme);
        if (null == graph) {
            return Collections.emptyList();
        }
        if (!StringUtils.hasLength(formkey)) {
            formkey = "00000000-0000-0000-0000-000000000000";
        }
        if (null == (node = graph.getNode(NrFormulaGraphService.FORMULA_FORM, formkey))) {
            return Collections.emptyList();
        }
        return ((FormulaFormDTO)node.getData(FormulaFormDTO.class)).getFormulas().stream().map(FormulaDTO::getFormulaDefine).collect(Collectors.toList());
    }

    @Override
    public List<FormulaDefine> getFormulasInFormByType(String formulaScheme, String form, DataEngineConsts.FormulaType formulaType) {
        return this.getFormulasInForm(formulaScheme, form).stream().filter(f -> this.compareType((FormulaDefine)f, formulaType)).collect(Collectors.toList());
    }

    @Override
    public List<FormulaDefine> searchFormulaInScheme(String formulaCode, String formulaSchemeKey) {
        if (!StringUtils.hasText(formulaCode)) {
            throw new IllegalArgumentException("'formulaCode' must not be null.");
        }
        if (!StringUtils.hasText(formulaSchemeKey)) {
            throw new IllegalArgumentException("'formulaSchemeKey' must not be null.");
        }
        return this.formulaDao.searchFormula(formulaCode, formulaSchemeKey);
    }
}

