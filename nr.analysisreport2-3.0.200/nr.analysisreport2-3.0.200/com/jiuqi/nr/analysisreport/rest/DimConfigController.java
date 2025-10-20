/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.analysisreport.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.internal.service.IEntityUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"/analysis/api/"})
@Api(tags={"\u81ea\u5b9a\u4e49\u5206\u6790\u6a21\u677f\u7ef4\u5ea6\u8bbe\u7f6e"})
public class DimConfigController {
    @Autowired
    private IAnalysisReportEntityService analysisReportEntityService;
    @Autowired
    private IEntityUtils entityUtils;
    private static final int DIMENSION_FLAG_BASEDATA = 0;
    private static final int DIMENSION_FLAG_ORGANIZATION = 1;

    private ITree<DimensionObj> toITree(DimensionObj node, List<String> nodeKeys, boolean leaf) {
        ITree itree = new ITree((INode)node);
        itree.setLeaf(leaf);
        if (!nodeKeys.isEmpty()) {
            boolean checked = nodeKeys.contains(node.getKey());
            if (checked) {
                nodeKeys.remove(node.getKey());
            }
            itree.setChecked(checked);
        }
        itree.setIcons(null);
        return itree;
    }

    @ApiOperation(value="\u7ef4\u5ea6\u8bbe\u7f6e\u6811\u5f62\u521d\u59cb\u5316")
    @RequestMapping(value={"dimconfig/init/{type}"}, method={RequestMethod.POST})
    public List<ITree<DimensionObj>> init(@PathVariable DimensionType type, @RequestBody List<String> nodeKeys) throws JQException {
        switch (type) {
            case DIMENSION_UNIT: {
                List<IEntityDefine> entities = this.entityUtils.getAllEntityDefinesByDimensionFlag(1);
                ArrayList<ITree<DimensionObj>> tree = new ArrayList<ITree<DimensionObj>>();
                if (null != entities && !entities.isEmpty()) {
                    entities.forEach(item -> tree.add(this.toITree(DimensionObj.toTreeNodeObj(item), nodeKeys, true)));
                }
                return tree;
            }
            case DIMENSION_NOMAL: {
                List<IEntityDefine> dictionaries = this.entityUtils.getAllEntityDefinesByDimensionFlag(0);
                if (null != dictionaries && !dictionaries.isEmpty()) {
                    return dictionaries.stream().map(item -> this.toITree(DimensionObj.toTreeNodeObj(item), nodeKeys, true)).collect(Collectors.toList());
                }
                return Collections.emptyList();
            }
            case DIMENSION_PERIOD: {
                List<IPeriodEntity> periodEntities = this.entityUtils.getAllPeriodEntitys();
                ArrayList<ITree<DimensionObj>> tree = new ArrayList<ITree<DimensionObj>>();
                if (null != periodEntities && !periodEntities.isEmpty()) {
                    periodEntities.forEach(item -> tree.add(this.toITree(DimensionObj.toTreeNodeObj(item), nodeKeys, true)));
                }
                return tree;
            }
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u7ef4\u5ea6\u8bbe\u7f6e\u6811\u5f62\u52a0\u8f7d\u5b50\u8282\u70b9")
    @RequestMapping(value={"dimconfig/load/{type}/{parentKey}"}, method={RequestMethod.POST})
    public List<ITree<DimensionObj>> load(@PathVariable DimensionType type, @PathVariable String parentKey, @RequestBody List<String> nodeKeys) throws JQException {
        return Collections.emptyList();
    }

    @ApiOperation(value="\u7ef4\u5ea6\u8bbe\u7f6e\u6811\u5f62\u641c\u7d22\u8282\u70b9")
    @RequestMapping(value={"dimconfig/search/{type}"}, method={RequestMethod.GET})
    public List<DimensionObj> search(@PathVariable DimensionType type, @RequestParam(value="keyword") String keyword) {
        List<Object> entities = new ArrayList();
        switch (type) {
            case DIMENSION_UNIT: {
                entities = this.entityUtils.fuzzySearchEntity(keyword, 1);
                break;
            }
            case DIMENSION_NOMAL: {
                entities = this.entityUtils.fuzzySearchEntity(keyword, 0);
                break;
            }
        }
        if (null != entities && !entities.isEmpty()) {
            return entities.stream().map(item -> DimensionObj.toTreeNodeObj(item)).collect(Collectors.toList());
        }
        return new ArrayList<DimensionObj>();
    }

    @ApiOperation(value="\u7ef4\u5ea6\u8bbe\u7f6e\u6811\u5f62\u5b9a\u4f4d\u8282\u70b9")
    @RequestMapping(value={"dimconfig/reload/{type}/{nodeKey}"}, method={RequestMethod.POST})
    public List<ITree<DimensionObj>> reload(@PathVariable DimensionType type, @PathVariable String nodeKey, @RequestBody List<String> nodeKeys) {
        return null;
    }

    @ApiOperation(value="\u67e5\u8be2\u7ef4\u5ea6\u4fe1\u606f")
    @RequestMapping(value={"dimconfig/querynodes/{type}"}, method={RequestMethod.POST})
    public List<DimensionObj> queryNodes(@PathVariable DimensionType type, @RequestBody List<String> keys) throws Exception {
        switch (type) {
            case DIMENSION_UNIT: 
            case DIMENSION_NOMAL: {
                return this.queryNodes(keys);
            }
            case DIMENSION_PERIOD: {
                return this.queryPeriods(keys);
            }
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u67e5\u8be2\u7ef4\u5ea6\u4fe1\u606f")
    @RequestMapping(value={"dimconfig/querynodes"}, method={RequestMethod.POST})
    public List<DimensionObj> queryNodes(@RequestBody List<String> keys) throws Exception {
        ArrayList<DimensionObj> dimNodes = new ArrayList<DimensionObj>();
        List<IEntityDefine> entityDefines = this.entityUtils.getEntityDefinesInRanges(keys);
        if (null != entityDefines && !entityDefines.isEmpty()) {
            for (IEntityDefine entityDefine : entityDefines) {
                DimensionObj treeNodeObj = DimensionObj.toTreeNodeObj(entityDefine);
                EntityViewDefine defaultView = this.entityUtils.getDefaultEntityViewByEntityId(treeNodeObj.getKey());
                treeNodeObj.setViewKey(defaultView.getEntityId());
                if (DimensionType.DIMENSION_UNIT.equals((Object)treeNodeObj.getType()) || DimensionType.DIMENSION_NOMAL.equals((Object)treeNodeObj.getType())) {
                    treeNodeObj.getConfig().setLinkEntityKey(treeNodeObj.getKey());
                }
                dimNodes.add(treeNodeObj);
            }
        }
        return dimNodes;
    }

    @ApiOperation(value="\u67e5\u8be2\u7ef4\u5ea6\u4fe1\u606f")
    @RequestMapping(value={"dimconfig/queryperiods"}, method={RequestMethod.POST})
    public List<DimensionObj> queryPeriods(@RequestBody List<String> keys) throws Exception {
        ArrayList<DimensionObj> dimNodes = new ArrayList<DimensionObj>();
        List<IPeriodEntity> periodEntitys = this.entityUtils.getPeriodEntitysInRanges(keys);
        if (null != periodEntitys && !periodEntitys.isEmpty()) {
            for (IPeriodEntity periodEntity : periodEntitys) {
                DimensionObj treeNodeObj = DimensionObj.toTreeNodeObj(periodEntity);
                EntityViewDefine defaultView = this.entityUtils.getPeriodViewByEntityKey(treeNodeObj.getKey());
                treeNodeObj.setViewKey(defaultView.getEntityId());
                if (DimensionType.DIMENSION_PERIOD.equals((Object)treeNodeObj.getType())) {
                    this.entityUtils.setPeriodEntityType(treeNodeObj);
                }
                dimNodes.add(treeNodeObj);
            }
        }
        return dimNodes;
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u4efb\u52a1")
    @RequestMapping(value={"dimconfig/query/task"}, method={RequestMethod.GET})
    public List<TaskDefine> queryTasks() {
        return this.analysisReportEntityService.getAllTaskDefines();
    }

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848")
    @RequestMapping(value={"dimconfig/query/scheme/{taskKey}"}, method={RequestMethod.GET})
    public List<FormSchemeDefine> queryScheme(@PathVariable String taskKey) throws Exception {
        return this.analysisReportEntityService.queryFormSchemeByTask(taskKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u4e3b\u4f53\u9ed8\u8ba4\u89c6\u56fe")
    @RequestMapping(value={"dimconfig/query/defaultview/{tableKey}"}, method={RequestMethod.GET})
    public String queryDefaultView(@PathVariable String tableKey) {
        return this.analysisReportEntityService.queryTaskDefine(tableKey).getDw();
    }

    @ApiOperation(value="\u67e5\u8be2\u4e3b\u4f53\u5168\u90e8\u89c6\u56fe")
    @RequestMapping(value={"dimconfig/query/allviews/{tableKey}"}, method={RequestMethod.GET})
    public List<EntityViewDefine> queryAllViews(@PathVariable String tableKey) {
        ArrayList<EntityViewDefine> res = new ArrayList<EntityViewDefine>();
        if (this.analysisReportEntityService.isPeriodEntity(tableKey)) {
            res.add(this.entityUtils.getPeriodViewByEntityKey(tableKey));
        } else {
            res.add(this.analysisReportEntityService.buildEntityViewDefine(tableKey));
        }
        return res;
    }

    @ApiOperation(value="\u67e5\u8be2\u81ea\u5b9a\u4e49\u65f6\u671f\u4e3b\u4f53\u6240\u6709\u671f\u6570,\u53c2\u6570\u662f\u65f6\u671f\u4e3b\u4f53key\u6216\u8005\u89c6\u56fekey")
    @RequestMapping(value={"dimconfig/query/allperiods/{key}"}, method={RequestMethod.GET})
    public List<Map<String, String>> queryCusPeriods(@PathVariable String key) {
        IPeriodProvider periodProvider = this.entityUtils.getPeriodProvider(key);
        List periodRows = periodProvider.getPeriodItems();
        ArrayList<Map<String, String>> res = new ArrayList<Map<String, String>>();
        if (periodRows != null && !periodRows.isEmpty()) {
            for (IPeriodRow periodRow : periodRows) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("code", periodRow.getCode());
                map.put("title", periodRow.getTitle());
                res.add(map);
            }
        }
        return res;
    }
}

