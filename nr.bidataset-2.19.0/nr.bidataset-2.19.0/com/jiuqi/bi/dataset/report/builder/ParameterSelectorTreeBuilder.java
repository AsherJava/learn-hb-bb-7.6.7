/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.report.builder;

import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ParameterSelectorNodeVo;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.util.StringUtils;

public class ParameterSelectorTreeBuilder {
    private static final String NODE_TYPE_PERIOD = "period";
    private static final String NODE_TYPE_DW = "dw";
    private static final String NODE_TYPE_SCENE = "scene";
    private static final String NODE_TYPE_FORMSCHME = "formScheme";
    private static final String NODE_TYPE_FORM = "form";
    private static final String NODE_TYPE_INNER = "inner";
    private static final String NODE_TYPE_ENTITYFIELD = "entityField";
    private TaskDefine taskDefine;
    private IEntityMetaService entityMetaService;
    private IRunTimeViewController runTimeViewController;
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public ParameterSelectorTreeBuilder(TaskDefine taskDefine, IEntityMetaService entityMetaService, IRunTimeViewController runTimeViewController, IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.taskDefine = taskDefine;
        this.entityMetaService = entityMetaService;
        this.runTimeViewController = runTimeViewController;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public List<ParameterSelectorNodeVo> build() throws Exception {
        ArrayList<ParameterSelectorNodeVo> nodes = new ArrayList<ParameterSelectorNodeVo>();
        this.buildDefaultParameterNodes(nodes);
        return nodes;
    }

    private void buildDefaultParameterNodes(List<ParameterSelectorNodeVo> nodes) {
        String dataTimeKey = this.taskDefine.getDateTime();
        String dwDimKey = this.taskDefine.getDw();
        String dimKeys = this.taskDefine.getDims();
        this.buildDefaultPeriodNode(nodes, dataTimeKey);
        this.buildDwNode(nodes, dwDimKey);
        this.buildSceneNode(nodes, dimKeys);
    }

    private void buildFormSchemeNodes(List<ParameterSelectorNodeVo> nodes) throws Exception {
        ArrayList<String> dataTableCache = new ArrayList<String>();
        List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(this.taskDefine.getKey());
        for (FormSchemeDefine formScheme : formSchemeDefines) {
            ParameterSelectorNodeVo node = new ParameterSelectorNodeVo();
            node.setKey(formScheme.getKey());
            node.setName(formScheme.getFormSchemeCode());
            node.setTitle(formScheme.getTitle());
            node.setType(NODE_TYPE_FORMSCHME);
            nodes.add(node);
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formScheme.getKey());
            for (FormDefine formDefine : formDefines) {
                ParameterSelectorNodeVo formNode = new ParameterSelectorNodeVo();
                formNode.setKey(formDefine.getKey());
                formNode.setName(formDefine.getFormCode());
                formNode.setTitle(formDefine.getTitle());
                formNode.setType(NODE_TYPE_FORM);
                node.addChild(formNode);
                List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formDefine.getKey());
                for (String fieldKey : fieldKeys) {
                    FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
                    if (fieldDefine.getEntityKey() != null) {
                        ParameterSelectorNodeVo entityFieldNode = new ParameterSelectorNodeVo();
                        entityFieldNode.setKey(fieldDefine.getKey());
                        entityFieldNode.setName(fieldDefine.getCode());
                        entityFieldNode.setTitle(fieldDefine.getTitle());
                        entityFieldNode.setType(NODE_TYPE_ENTITYFIELD);
                        formNode.addChild(entityFieldNode);
                    }
                    if (dataTableCache.contains(fieldDefine.getOwnerTableKey())) continue;
                    dataTableCache.add(fieldDefine.getOwnerTableKey());
                    List innderDimFields = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(fieldDefine.getOwnerTableKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
                    for (DataField dataField : innderDimFields) {
                        ParameterSelectorNodeVo innerDimNode = new ParameterSelectorNodeVo();
                        innerDimNode.setKey(dataField.getKey());
                        innerDimNode.setName(dataField.getCode());
                        innerDimNode.setTitle(dataField.getTitle());
                        innerDimNode.setType(NODE_TYPE_INNER);
                        formNode.addChild(innerDimNode);
                    }
                }
            }
        }
    }

    private void buildDefaultPeriodNode(List<ParameterSelectorNodeVo> nodes, String dataTimeKey) {
        ParameterSelectorNodeVo node = new ParameterSelectorNodeVo();
        node.setKey(UUID.randomUUID().toString());
        node.setName(NrPeriodConst.PREFIX_CODE + dataTimeKey);
        node.setTitle("\u65f6\u671f");
        node.setType(NODE_TYPE_PERIOD);
        node.setDataType(6);
        node.setSelectMode(ParameterSelectMode.SINGLE);
        node.setDefaultValueMode(DefaultValueMode.CURRENT);
        node.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        node.setEntityId(dataTimeKey);
        nodes.add(node);
    }

    private void buildDwNode(List<ParameterSelectorNodeVo> nodes, String dwDimKey) {
        ParameterSelectorNodeVo node = new ParameterSelectorNodeVo();
        node.setKey(UUID.randomUUID().toString());
        node.setType(NODE_TYPE_DW);
        node.setDataType(6);
        node.setSelectMode(ParameterSelectMode.MUTIPLE);
        node.setDefaultValueMode(DefaultValueMode.NONE);
        node.setEntityId(dwDimKey);
        String[] dwSplit = dwDimKey.split("@");
        String dimKey = dwSplit[0];
        IEntityDefine entity = this.entityMetaService.queryEntity(dimKey);
        node.setTitle(entity.getTitle());
        if (dwSplit[1].equals("ORG")) {
            node.setName(dimKey);
            node.setMessageAlias(dimKey + ".ORGCODE");
        } else {
            node.setName(dimKey + "_CODE");
            node.setMessageAlias(dimKey + ".OBJECTCODE");
        }
        nodes.add(node);
    }

    private void buildSceneNode(List<ParameterSelectorNodeVo> nodes, String sceneDimKey) {
        if (StringUtils.hasLength(sceneDimKey)) {
            String[] sceneDimArr;
            for (String dimKey : sceneDimArr = sceneDimKey.split(";")) {
                String[] dimKeySplit = dimKey.split("@");
                String dimKeyPrefix = dimKeySplit[0];
                IEntityDefine entity = this.entityMetaService.queryEntity(dimKeyPrefix);
                ParameterSelectorNodeVo node = new ParameterSelectorNodeVo();
                node.setKey(UUID.randomUUID().toString());
                node.setName(dimKeyPrefix);
                node.setTitle(entity.getTitle());
                node.setType(NODE_TYPE_SCENE);
                node.setDataType(6);
                node.setMessageAlias(dimKeyPrefix + ".OBJECTCODE");
                node.setEntityId(dimKeyPrefix);
                node.setSelectMode(ParameterSelectMode.SINGLE);
                node.setDefaultValueMode(DefaultValueMode.FIRST);
                nodes.add(node);
            }
        }
    }
}

