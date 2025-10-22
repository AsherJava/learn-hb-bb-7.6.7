/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.context.ITreeContext
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodemap.TreeBuilder
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.batch.summary.web.tree.selector;

import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodemap.TreeBuilder;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class TargetDimTreeDataSource
extends TreeBuilder {
    public static final String TYPE_NAME = "dimType";
    public static final String TASK_NAME = "taskKey";
    private ITreeContext context;
    private TaskDefine taskDefine;
    private IRunTimeViewController rtController;
    private IEntityMetaService entityMetaService;
    private ICalibreDefineService calibreDefineService;

    public TargetDimTreeDataSource(IEntityMetaService entityMetaService, ICalibreDefineService calibreDefineService, IRunTimeViewController rtController, ITreeContext context) {
        this.context = context;
        this.rtController = rtController;
        this.entityMetaService = entityMetaService;
        this.calibreDefineService = calibreDefineService;
        this.taskDefine = this.readTaskDefineFromParam(context.getCustomVariable());
        this.initTree();
    }

    public List<String> getNodePath(IBaseNodeData data) {
        return this.findTreeNode(data) != null ? Arrays.asList(this.findTreeNode(data).getPath()) : new ArrayList<String>();
    }

    public boolean isLeaf(IBaseNodeData data) {
        return !TargetDimType.BASE_DATA.key.equals(data.getKey()) && !TargetDimType.CALIBRE.key.equals(data.getKey());
    }

    public List<IBaseNodeData> getRoots() {
        ArrayList<IBaseNodeData> roots = new ArrayList<IBaseNodeData>();
        roots.add(this.buildCustomConditionNode());
        roots.add(this.buildCalibreGroup());
        roots.add(this.buildBaseDataGroup());
        return roots;
    }

    private IBaseNodeData buildCustomConditionNode() {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(TargetDimType.CONDITION.key);
        node.setCode(TargetDimType.CONDITION.code);
        node.setTitle(TargetDimType.CONDITION.name);
        node.put(TYPE_NAME, (Object)TargetDimType.CONDITION.value);
        return node;
    }

    private IBaseNodeData buildCalibreGroup() {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(TargetDimType.CALIBRE.key);
        node.setCode(TargetDimType.CALIBRE.code);
        node.setTitle(TargetDimType.CALIBRE.name);
        node.put(TYPE_NAME, (Object)TargetDimType.CALIBRE.value);
        return node;
    }

    private IBaseNodeData buildBaseDataGroup() {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(TargetDimType.BASE_DATA.key);
        node.setCode(TargetDimType.BASE_DATA.code);
        node.setTitle(TargetDimType.BASE_DATA.name);
        node.put(TYPE_NAME, (Object)TargetDimType.BASE_DATA.value);
        return node;
    }

    public List<IBaseNodeData> getChildren(IBaseNodeData parent) {
        ArrayList<IBaseNodeData> children = new ArrayList<IBaseNodeData>();
        if (TargetDimType.BASE_DATA.key.equals(parent.getKey())) {
            return this.getBaseDataNodeData(parent);
        }
        if (TargetDimType.CALIBRE.key.equals(parent.getKey())) {
            return this.getCalibreNodeData(parent);
        }
        return children;
    }

    private List<IBaseNodeData> getBaseDataNodeData(IBaseNodeData parent) {
        ArrayList<IBaseNodeData> rows = new ArrayList<IBaseNodeData>();
        if (this.taskDefine != null) {
            List entityRefer;
            String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
            if (StringUtils.isEmpty((String)contextEntityId)) {
                contextEntityId = this.taskDefine.getDw();
            }
            if ((entityRefer = this.entityMetaService.getEntityRefer(contextEntityId)) != null) {
                return entityRefer.stream().map(refer -> this.buildBaseDataNodeData(parent, (IEntityRefer)refer)).collect(Collectors.toList());
            }
        }
        return rows;
    }

    private List<IBaseNodeData> getCalibreNodeData(IBaseNodeData parent) {
        ArrayList<IBaseNodeData> rows = new ArrayList<IBaseNodeData>();
        if (this.taskDefine != null) {
            String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
            if (StringUtils.isEmpty((String)contextEntityId)) {
                contextEntityId = this.taskDefine.getDw();
            }
            CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
            calibreDefineDTO.setEntityId(contextEntityId);
            Result schemeResult = this.calibreDefineService.getByRefer(calibreDefineDTO);
            if (schemeResult.getData() != null) {
                return ((List)schemeResult.getData()).stream().map(calibreDefine -> this.buildCalibreNodeData(parent, (CalibreDefineDTO)calibreDefine)).collect(Collectors.toList());
            }
        }
        return rows;
    }

    private IBaseNodeData buildBaseDataNodeData(IBaseNodeData parent, IEntityRefer refer) {
        IEntityDefine referEntityDefine = this.entityMetaService.queryEntity(refer.getReferEntityId());
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(referEntityDefine.getId());
        node.setCode(referEntityDefine.getCode());
        node.setTitle(referEntityDefine.getTitle());
        node.put(TYPE_NAME, (Object)TargetDimType.BASE_DATA.value);
        return node;
    }

    private IBaseNodeData buildCalibreNodeData(IBaseNodeData parent, CalibreDefineDTO calibreDefine) {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(calibreDefine.getCode());
        node.setCode(calibreDefine.getCode());
        node.setTitle(calibreDefine.getName());
        node.put(TYPE_NAME, (Object)TargetDimType.CALIBRE.value);
        return node;
    }

    private TaskDefine readTaskDefineFromParam(JSONObject customParam) {
        if (customParam.has(TASK_NAME)) {
            String taskKey = customParam.getString(TASK_NAME);
            return this.rtController.queryTaskDefine(taskKey);
        }
        return null;
    }
}

