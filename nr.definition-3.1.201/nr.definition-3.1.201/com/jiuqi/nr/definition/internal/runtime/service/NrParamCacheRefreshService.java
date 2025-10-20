/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCache
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.definition.common.DiskDataUtils;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLinkDTO;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormParamCacheService;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaCacheService;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaGraphService;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamGraphService;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class NrParamCacheRefreshService {
    private static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;
    @Autowired
    private RuntimeViewController runTimeViewController;
    @Autowired
    private NrFormParamCacheService formParamCacheService;
    @Autowired
    private NrFormulaCacheService formFormulaCacheService;
    @Autowired
    private NrParamGraphService paramGraphService;
    @Autowired
    private NrFormulaGraphService formulaGraphService;

    protected void init() {
        this.formParamCacheService.getCache();
        this.formFormulaCacheService.getCache();
    }

    public void clean() {
        this.formParamCacheService.refreshFormSchemeCache();
        this.formParamCacheService.getCache().clear();
        this.formFormulaCacheService.refreshFormulaSchemeCache();
        this.formFormulaCacheService.getCache().clear();
        this.cleanDiskCache(Collections.emptyList());
        NrFormParamCacheService.POINT_POOL.clear();
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u6e05\u7406\u5168\u90e8\u7f13\u5b58");
    }

    public void resetDataLink(Collection<String> formSchemeKeys) {
        for (String formSchemeKey : formSchemeKeys) {
            if (!this.formParamCacheService.getCache().exists(formSchemeKey)) continue;
            IGraph graph = this.formParamCacheService.getCache().get(formSchemeKey);
            graph.forEachNode(NrParamGraphService.DATALINK, node -> {
                DataLinkDefine data = (DataLinkDefine)node.getData(DataLinkDefine.class);
                if (data instanceof DataLinkDTO) {
                    ((DataLinkDTO)data).resetForDataField();
                }
            });
        }
    }

    private void cleanDiskCache(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            Path path = DiskDataUtils.tempPath("NR_FORMULA_JS");
            DiskDataUtils.delete(path);
        } else {
            for (String key : keys) {
                Path path = DiskDataUtils.tempPath("NR_FORMULA_JS", key);
                DiskDataUtils.delete(path);
            }
        }
    }

    public void clean(Collection<String> formSchemes, Collection<String> formulaSchemes) {
        if (!CollectionUtils.isEmpty(formSchemes)) {
            this.formParamCacheService.refreshFormSchemeCache();
            this.formParamCacheService.getCache().remove(formSchemes);
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u79fb\u9664\u62a5\u8868{}\u7684\u7f13\u5b58", (Object)formSchemes);
        }
        if (!CollectionUtils.isEmpty(formulaSchemes)) {
            this.formFormulaCacheService.refreshFormulaSchemeCache();
            this.formFormulaCacheService.getCache().remove(formulaSchemes);
            if (this.formulaGraphService.enableJsDiskCache()) {
                this.cleanDiskCache(formulaSchemes);
            }
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u79fb\u9664\u516c\u5f0f{}\u7684\u7f13\u5b58", (Object)formulaSchemes);
        }
    }

    public void refresh(Collection<String> formSchemes, Collection<String> formulaSchemes) {
        if (!CollectionUtils.isEmpty(formSchemes)) {
            this.formParamCacheService.refreshFormSchemeCache();
        }
        if (!CollectionUtils.isEmpty(formulaSchemes)) {
            this.formFormulaCacheService.refreshFormulaSchemeCache();
        }
        Map<String, IGraph> formSchemeGraphs = this.loadFormSchemeGraphs(formSchemes);
        Map<String, IGraph> formulaSchemeGraphs = this.loadFormulaSchemeGraphs(formulaSchemes, formSchemeGraphs);
        Map<String, Map<String, String>> formulaJs = this.getFormulaJs(formulaSchemeGraphs);
        this.refresh(formSchemeGraphs, formulaSchemeGraphs, formulaJs);
    }

    private Map<String, Map<String, String>> getFormulaJs(Map<String, IGraph> formulaSchemeGraphs) {
        if (!this.formulaGraphService.enableJsDiskCache()) {
            return Collections.emptyMap();
        }
        HashMap<String, Map<String, String>> formulaJs = new HashMap<String, Map<String, String>>();
        if (!CollectionUtils.isEmpty(formulaSchemeGraphs)) {
            for (Map.Entry<String, IGraph> entry : formulaSchemeGraphs.entrySet()) {
                formulaJs.put(entry.getKey(), this.formulaGraphService.getFormulaScript(entry.getKey(), entry.getValue()));
            }
        }
        return formulaJs;
    }

    private Map<String, IGraph> loadFormulaSchemeGraphs(Collection<String> formulaSchemes, Map<String, IGraph> formSchemeGraphs) {
        RuntimeViewController cloneController;
        if (!formSchemeGraphs.isEmpty()) {
            NrFormParamCacheService cloneService = this.formParamCacheService.getCacheService();
            IGraphCache formSchemeCache = cloneService.getCache();
            formSchemeCache.put(formSchemeGraphs);
            cloneController = this.runTimeViewController.getRuntimeViewController(cloneService);
        } else {
            cloneController = this.runTimeViewController;
        }
        HashMap<String, IGraph> formulaSchemeGraphs = new HashMap<String, IGraph>();
        if (!CollectionUtils.isEmpty(formulaSchemes)) {
            for (String formulaSchemeKey : formulaSchemes) {
                if (!this.formFormulaCacheService.getCache().exists(formulaSchemeKey)) continue;
                IGraph graph = this.formulaGraphService.getFormulaGraph(cloneController, formulaSchemeKey);
                formulaSchemeGraphs.put(formulaSchemeKey, graph);
            }
        }
        return formulaSchemeGraphs;
    }

    private Map<String, IGraph> loadFormSchemeGraphs(Collection<String> formSchemes) {
        HashMap<String, IGraph> formSchemeGraphs = new HashMap<String, IGraph>();
        if (!CollectionUtils.isEmpty(formSchemes)) {
            for (String formSchemeKey : formSchemes) {
                if (!this.formParamCacheService.getCache().exists(formSchemeKey)) continue;
                IGraph graph = this.paramGraphService.getFormGraph(formSchemeKey);
                formSchemeGraphs.put(formSchemeKey, graph);
            }
        }
        return formSchemeGraphs;
    }

    private void refresh(Map<String, IGraph> formSchemeGraphs, Map<String, IGraph> formulaSchemeGraphs, Map<String, Map<String, String>> formulaJs) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u5237\u65b0\u62a5\u8868{}\u7684\u7f13\u5b58", (Object)formSchemeGraphs.keySet());
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u5237\u65b0\u516c\u5f0f{}\u7684\u7f13\u5b58", (Object)formulaSchemeGraphs.keySet());
        if (!formSchemeGraphs.isEmpty()) {
            this.formParamCacheService.getCache().put(formSchemeGraphs);
        }
        if (!formulaSchemeGraphs.isEmpty()) {
            this.formFormulaCacheService.getCache().put(formulaSchemeGraphs);
            for (String string : formSchemeGraphs.keySet()) {
                List<FormulaSchemeDefine> formulaSchemes = this.formFormulaCacheService.getFormulaSchemesByFormScheme(string);
                for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemes) {
                    if (formulaSchemeGraphs.containsKey(formulaSchemeDefine.getFormSchemeKey()) || !this.formFormulaCacheService.getCache().exists(formulaSchemeDefine.getFormSchemeKey())) continue;
                    this.formFormulaCacheService.getCache().remove(formulaSchemeDefine.getFormSchemeKey());
                }
            }
            for (Map.Entry entry : formulaJs.entrySet()) {
                Collection<Path> children = DiskDataUtils.children(DiskDataUtils.tempPath("NR_FORMULA_JS", (String)entry.getKey()));
                for (Map.Entry entry2 : ((Map)entry.getValue()).entrySet()) {
                    Path path = DiskDataUtils.tempPath("NR_FORMULA_JS", (String)entry.getKey(), (String)entry2.getKey());
                    children.remove(path);
                    DiskDataUtils.write(path, (String)entry2.getValue());
                }
                for (Path path : children) {
                    DiskDataUtils.write(path, null);
                }
            }
        }
    }
}

