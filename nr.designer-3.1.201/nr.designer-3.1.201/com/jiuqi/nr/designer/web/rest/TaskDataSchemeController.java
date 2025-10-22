/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.common.DefinitionTransUtils
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.TreeSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.internal.service.IRuntimeDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.NumAuthInfo
 *  com.jiuqi.nr.definition.common.ParamNum
 *  com.jiuqi.nr.definition.config.ParamMaxNumberConfig
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskLinkService
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.common.DefinitionTransUtils;
import com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.service.IRuntimeDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.NumAuthInfo;
import com.jiuqi.nr.definition.common.ParamNum;
import com.jiuqi.nr.definition.config.ParamMaxNumberConfig;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskLinkService;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.EntityLinkageObject;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.NoTimePeriod;
import com.jiuqi.nr.designer.web.facade.RunTaskSchemeNodeFilter;
import com.jiuqi.nr.designer.web.facade.TaskOrgVO;
import com.jiuqi.nr.designer.web.facade.TaskSchemeNodeFilter;
import com.jiuqi.nr.designer.web.facade.vo.RelatedTaskDimVO;
import com.jiuqi.nr.designer.web.rest.vo.DesignContextVO;
import com.jiuqi.nr.designer.web.rest.vo.DesignDataDimVO;
import com.jiuqi.nr.designer.web.rest.vo.ParamToDesigner;
import com.jiuqi.nr.designer.web.rest.vo.SearchNode;
import com.jiuqi.nr.designer.web.treebean.FieldObject;
import com.jiuqi.nr.designer.web.treebean.FieldTreeNode;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class TaskDataSchemeController {
    private static final Logger log = LoggerFactory.getLogger(TaskDataSchemeController.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    IRunTimeViewController IRunTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataSchemeTreeService<RuntimeDataSchemeNode> runTreeService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IRuntimeTaskLinkService taskLinkService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;
    @Autowired
    private ParamMaxNumberConfig paramMaxNumberConfig;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataSchemeTreeService<DataSchemeNode> treeService;
    @Autowired
    private IDesignDataSchemeTreeService iDesignDataSchemeTreeService;
    @Autowired
    private IRuntimeDataSchemeTreeService iRuntimeDataSchemeTreeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataDefinitionRuntimeController2 dataDefinitionRuntimeController2;
    @Autowired
    private DataDefinitionDesignTimeController2 dataDefinitionDesignTimeController2;

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u65b9\u6848")
    @RequestMapping(value={"datascheme/all"}, method={RequestMethod.GET})
    public List<DesignDataScheme> queryAllDataScheme() {
        return this.designDataSchemeService.getAllDataScheme();
    }

    @ApiOperation(value="\u6570\u636e\u65b9\u6848\u67e5\u8be2\u5355\u4f4d\u7ef4\u5ea6\u65f6\u671f")
    @RequestMapping(value={"queryEntitys/{datascheme}"}, method={RequestMethod.GET})
    public List<DesignDataDimension> queryEntitys(@PathVariable String datascheme) {
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(datascheme);
        ArrayList<DesignDataDimension> listUnit = new ArrayList<DesignDataDimension>();
        ArrayList<DesignDataDimension> listUnitScope = new ArrayList<DesignDataDimension>();
        dataSchemeDimension.forEach(i -> {
            if (i.getDimensionType().getValue() == DimensionType.UNIT.getValue()) {
                IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(i.getDimKey());
                DesignDataDimVO designDataDimVO = DesignDataDimVO.valueOf(i);
                designDataDimVO.setTitle(iEntityDefine.getTitle());
                listUnit.add(designDataDimVO);
            } else if (i.getDimensionType().getValue() == DimensionType.UNIT_SCOPE.getValue()) {
                IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(i.getDimKey());
                DesignDataDimVO designDataDimVO = DesignDataDimVO.valueOf(i);
                designDataDimVO.setTitle(iEntityDefine.getTitle());
                listUnitScope.add(designDataDimVO);
            }
        });
        if (listUnitScope.size() > 0) {
            return listUnitScope;
        }
        return listUnit;
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u6570\u636e\u65b9\u6848")
    @RequestMapping(value={"querydataschemebykey/{datascheme}"}, method={RequestMethod.GET})
    public DesignDataScheme querydataschemebykey(@PathVariable String datascheme) {
        return this.designDataSchemeService.getDataScheme(datascheme);
    }

    @ApiOperation(value="\u67e5\u8be2\u5173\u8054\u679a\u4e3e\u663e\u793a\u5217\u8868")
    @RequestMapping(value={"get-show-fields/{entityKey}"}, method={RequestMethod.GET})
    public List<IEntityAttribute> queryShowFields(@PathVariable String entityKey) throws JQException {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityKey);
        return entityModel.getShowFields();
    }

    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u542b\u4e49\u5bf9\u5e94\u7684id")
    @RequestMapping(value={"getMeaningColumn/{entityKey}"}, method={RequestMethod.GET})
    public IEntityAttribute queryMeaningColumn(@PathVariable String entityKey) throws JQException {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityKey);
        return entityModel.getNameField();
    }

    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u5173\u8054\u5bf9\u8c61")
    @RequestMapping(value={"getEnumEntity/{entityKey}"}, method={RequestMethod.GET})
    public IEntityDefine queryEntity(@PathVariable String entityKey) {
        return this.iEntityMetaService.queryEntity(entityKey);
    }

    @ApiOperation(value="\u901a\u8fc7\u6307\u6807\u5206\u7ec4key\uff0c\u62ff\u4e0b\u7ea7\u6307\u6807")
    @RequestMapping(value={"/getfieldsbygroupkey"}, method={RequestMethod.POST})
    public List<FieldObject> getAllFieldsByGroupKeys(@RequestBody SearchNode searchNode) throws JQException {
        try {
            List<DesignDataField> fields;
            List<Object> allDataField = new ArrayList();
            ArrayList<FieldObject> resFieldObjects = new ArrayList<FieldObject>();
            if (StringUtils.isEmpty((String)searchNode.getNodeKey())) {
                return resFieldObjects;
            }
            String[] ids = searchNode.getNodeKey().split(";");
            if ((NodeType.SCHEME.getValue() + "").equals(searchNode.getNodeType())) {
                allDataField = this.designDataSchemeService.getAllDataField(searchNode.getNodeKey());
                allDataField = allDataField.stream().filter(x -> x.getDataFieldKind().getValue() == DataFieldKind.FIELD.getValue() || x.getDataFieldKind().getValue() == DataFieldKind.FIELD_ZB.getValue() || x.getDataFieldKind().getValue() == DataFieldKind.TABLE_FIELD_DIM.getValue()).collect(Collectors.toList());
            } else if ((NodeType.GROUP.getValue() + "").equals(searchNode.getNodeType())) {
                for (String string : ids) {
                    if (!StringUtils.isNotEmpty((String)string)) continue;
                    this.allDataFieldByNode(string, allDataField);
                }
            } else if ((NodeType.TABLE.getValue() + "").equals(searchNode.getNodeType())) {
                for (String string : ids) {
                    if (!StringUtils.isNotEmpty((String)string) || (fields = this.getDataFieldByTableKeyAndKind(string)) == null || fields.size() == 0) continue;
                    allDataField.addAll(fields);
                }
            } else if ((NodeType.DETAIL_TABLE.getValue() + "").equals(searchNode.getNodeType())) {
                for (String string : ids) {
                    if (!StringUtils.isNotEmpty((String)string) || (fields = this.getDataFieldByTableKeyAndKind(string)) == null || fields.size() == 0) continue;
                    allDataField.addAll(fields);
                }
            } else if ((NodeType.ACCOUNT_TABLE.getValue() + "").equals(searchNode.getNodeType())) {
                for (String string : ids) {
                    if (!StringUtils.isNotEmpty((String)string) || (fields = this.getDataFieldByTableKeyAndKind(string)) == null || fields.size() == 0) continue;
                    allDataField.addAll(fields);
                }
            } else if ((NodeType.MD_INFO.getValue() + "").equals(searchNode.getNodeType())) {
                for (String string : ids) {
                    if (!StringUtils.isNotEmpty((String)string) || (fields = this.getDataFieldByTableKeyAndKind(string)) == null || fields.size() == 0) continue;
                    allDataField.addAll(fields);
                }
            }
            for (DesignDataField designDataField : allDataField) {
                resFieldObjects.add(this.initParamObjPropertyUtil.setFieldObjectProperty((FieldDefine)DefinitionTransUtils.toDesignFieldDefine((DataField)designDataField, null)));
            }
            return resFieldObjects;
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_135);
        }
    }

    private List<DesignDataField> getDataFieldByTableKeyAndKind(String key) {
        return this.designDataSchemeService.getDataFieldByTableKeyAndKind(key, new DataFieldKind[]{DataFieldKind.FIELD, DataFieldKind.FIELD_ZB, DataFieldKind.TABLE_FIELD_DIM});
    }

    private void allDataFieldByNode(String nodeKey, List<DesignDataField> allField) {
        List dataGroupByParent;
        List dataTableByGroup = this.designDataSchemeService.getDataTableByGroup(nodeKey);
        if (null != dataTableByGroup && dataTableByGroup.size() != 0) {
            dataTableByGroup.forEach(t -> {
                List<DesignDataField> dataFieldByTable = this.getDataFieldByTableKeyAndKind(t.getKey());
                if (null != dataFieldByTable && dataFieldByTable.size() != 0) {
                    allField.addAll(dataFieldByTable);
                }
            });
        }
        if (null != (dataGroupByParent = this.designDataSchemeService.getDataGroupByParent(nodeKey)) && dataGroupByParent.size() != 0) {
            dataGroupByParent.forEach(e -> this.allDataFieldByNode(e.getKey(), allField));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u4e3b\u4f53\u5217\u8868")
    @RequestMapping(value={"/entitys-task/{id}"}, method={RequestMethod.GET})
    public List<EntityTables> getEntityLists(@PathVariable(value="id") String taskKey) throws JQException {
        String taskItem = taskKey.substring(0, 36);
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskItem);
        if (designTaskDefine == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_001);
        }
        String datascheme = designTaskDefine.getDataScheme();
        if (!StringUtils.isEmpty((String)datascheme)) {
            List dimensions = this.designDataSchemeService.getDataSchemeDimension(datascheme);
            return this.queryEntityFromDim(dimensions);
        }
        return null;
    }

    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u53e3\u5f84\u5217\u8868")
    @RequestMapping(value={"/releated/task/entitys/{formSchemeId}"}, method={RequestMethod.GET})
    public List<TaskOrgVO> getRelatedTaskEntitys(@PathVariable(value="formSchemeId") String formSchemeId) throws JQException {
        ArrayList<TaskOrgVO> orgList = new ArrayList<TaskOrgVO>();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeId);
        if (formScheme == null) {
            return Collections.emptyList();
        }
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(formScheme.getTaskKey());
        if (!CollectionUtils.isEmpty(taskOrgLinkDefines)) {
            for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
                orgList.add(new TaskOrgVO(taskOrgLinkDefine, this.iEntityMetaService));
            }
        }
        return orgList;
    }

    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u60c5\u666f\u5217\u8868")
    @RequestMapping(value={"/releated/task/dims/{formSchemeId}"}, method={RequestMethod.GET})
    public List<RelatedTaskDimVO> getRelatedTaskDims(@PathVariable(value="formSchemeId") String formSchemeId) throws JQException {
        ArrayList<RelatedTaskDimVO> dimList = new ArrayList<RelatedTaskDimVO>();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeId);
        if (formScheme == null) {
            return dimList;
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        String dims = task.getDims();
        if (!StringUtils.isEmpty((String)dims)) {
            String[] dimKeys = dims.split(";");
            for (int i = 0; i < dimKeys.length; ++i) {
                try {
                    String currentDimKey = dimKeys[i];
                    IEntityDefine entityDefine = this.iEntityMetaService.queryEntity(currentDimKey);
                    if (!this.isNeedChoose(entityDefine, task.getKey())) continue;
                    RelatedTaskDimVO relatedTaskDimVO = new RelatedTaskDimVO(entityDefine.getId(), entityDefine.getCode(), entityDefine.getTitle());
                    ArrayList<RelatedTaskDimVO> datas = new ArrayList<RelatedTaskDimVO>();
                    List<IEntityRow> entityRows = this.getEntityRows(entityDefine);
                    for (IEntityRow entityRow : entityRows) {
                        RelatedTaskDimVO relatedTaskDimData = new RelatedTaskDimVO(entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle());
                        datas.add(relatedTaskDimData);
                    }
                    relatedTaskDimVO.setData(datas);
                    dimList.add(relatedTaskDimVO);
                    continue;
                }
                catch (Exception e) {
                    throw new JQException(new ErrorEnum(){

                        public String getCode() {
                            return null;
                        }

                        public String getMessage() {
                            return "\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u9879\u5f02\u5e38";
                        }
                    }, (Throwable)e);
                }
            }
        }
        return dimList;
    }

    private boolean isNeedChoose(IEntityDefine dimEntity, String taskKey) {
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(taskKey);
        IEntityDefine unitEntity = null;
        if (taskOrgLinkDefines.size() == 1) {
            unitEntity = this.iEntityMetaService.queryEntity(((TaskOrgLinkDefine)taskOrgLinkDefines.get(0)).getEntity());
        } else if (taskOrgLinkDefines.size() > 1) {
            unitEntity = this.iEntityMetaService.queryEntity("MD_ORG@ORG");
        }
        if (unitEntity != null) {
            List entityRefer = this.iEntityMetaService.getEntityRefer(unitEntity.getId());
            IEntityModel entityModel = this.iEntityMetaService.getEntityModel(unitEntity.getId());
            Optional<IEntityRefer> findRefer = entityRefer.stream().filter(e -> e.getReferEntityId().equals(dimEntity.getId())).findAny();
            if (findRefer.isPresent()) {
                IEntityAttribute attribute = entityModel.getAttribute(findRefer.get().getOwnField());
                if (attribute.isMultival()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private List<IEntityRow> getEntityRows(IEntityDefine entityDefine) throws Exception {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityDefine.getId());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
        List allRows = iEntityTable.getAllRows();
        return allRows;
    }

    private List<EntityTables> queryEntityFromDim(List<DesignDataDimension> dims) {
        ArrayList<EntityTables> entityTablesObjList = new ArrayList<EntityTables>();
        if (null != dims && dims.size() != 0) {
            for (DesignDataDimension dim : dims) {
                EntityLinkageObject linkageObj;
                EntityTables entityTables;
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                if (periodAdapter.isPeriodEntity(dim.getDimKey())) {
                    IPeriodEntity iPeriodByTableKey = periodAdapter.getPeriodEntity(dim.getDimKey());
                    entityTables = new EntityTables();
                    entityTables.setID(iPeriodByTableKey.getKey());
                    entityTables.setTitle(iPeriodByTableKey.getTitle());
                    linkageObj = this.initParamObjPropertyUtil.getEntityLinkageObj(iPeriodByTableKey.getKey());
                    entityTables.setEntityLinkageObject(linkageObj);
                    entityTables.setKind(TableKind.TABLE_KIND_ENTITY_PERIOD);
                    entityTables.setCode(iPeriodByTableKey.getCode());
                    entityTablesObjList.add(entityTables);
                    continue;
                }
                IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(dim.getDimKey());
                if (iEntityDefine == null) continue;
                entityTables = new EntityTables();
                entityTables.setID(iEntityDefine.getId());
                entityTables.setTitle(iEntityDefine.getTitle());
                linkageObj = this.initParamObjPropertyUtil.getEntityLinkageObj(iEntityDefine.getId());
                entityTables.setEntityLinkageObject(linkageObj);
                entityTables.setKind(TableKind.TABLE_KIND_ENTITY);
                entityTables.setCode(iEntityDefine.getCode());
                entityTablesObjList.add(entityTables);
            }
        }
        return entityTablesObjList;
    }

    @ApiOperation(value="\u6839\u636e\u65b9\u6848\u83b7\u53d6\u65f6\u671f\u7c7b\u578b")
    @RequestMapping(value={"/period-type-scheme/{id}"}, method={RequestMethod.GET})
    public int getPeriodTypeByScheme(@PathVariable(value="id") String key) throws JQException {
        if (key == null || key.isEmpty()) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_204);
        }
        DesignFormSchemeDefine queryFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(key);
        if (queryFormSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_204);
        }
        DesignTaskDefine queryTaskDefine = this.nrDesignTimeController.queryTaskDefine(queryFormSchemeDefine.getTaskKey());
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(queryTaskDefine.getDataScheme());
        for (int i = 0; i < dimensions.size(); ++i) {
            DesignDataDimension dimension = (DesignDataDimension)dimensions.get(i);
            if (dimension.getDimensionType().getValue() != DimensionType.PERIOD.getValue()) continue;
            return this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimension.getDimKey()).getPeriodType().type();
        }
        return PeriodType.YEAR.type();
    }

    @ApiOperation(value="\u6839\u636e\u65b9\u6848\u83b7\u53d6\u4efb\u52a1\u7684\u65f6\u671fkey")
    @RequestMapping(value={"/task-datetime-scheme/{id}"}, method={RequestMethod.GET})
    public String getDateTimeByScheme(@PathVariable(value="id") String key) throws JQException {
        if (key == null || key.isEmpty()) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_204);
        }
        try {
            DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(key);
            DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
            return designTaskDefine.getDateTime() == null || designTaskDefine.getDateTime().isEmpty() ? "" : designTaskDefine.getDateTime();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_204, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u65b9\u6848\u83b7\u53d6\u4efb\u52a1\u7684\u4e0d\u5b9a\u65f6\u671f\u6570\u636e")
    @RequestMapping(value={"/task-notimeperiod-scheme/{id}"}, method={RequestMethod.GET})
    public List<NoTimePeriod> getNoTimePeriodByScheme(@PathVariable(value="id") String key) throws JQException {
        if (key == null || key.isEmpty()) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_204);
        }
        try {
            DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(key);
            DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
            ArrayList<NoTimePeriod> noTimePeriods = new ArrayList<NoTimePeriod>();
            if (StringUtils.isNotEmpty((String)designTaskDefine.getDateTime())) {
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime());
                TaskDataSchemeController.customPeriod(noTimePeriods, periodEntity, this.periodEngineService);
            }
            return noTimePeriods;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_204, (Throwable)e);
        }
    }

    public static void customPeriod(List<NoTimePeriod> noTimePeriods, IPeriodEntity periodEntity, PeriodEngineService periodEngineService) {
        if (PeriodType.CUSTOM.code() == periodEntity.getPeriodType().code()) {
            List iPeriodRows = periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey()).getPeriodItems();
            for (IPeriodRow periodRow : iPeriodRows) {
                NoTimePeriod noTimePeriod = new NoTimePeriod();
                noTimePeriod.setTitle(periodRow.getTitle());
                noTimePeriod.setCode(periodRow.getCode());
                noTimePeriod.setNbCode(periodRow.getAlias());
                noTimePeriod.setStartPeriod(periodRow.getStartDate());
                noTimePeriod.setEndPeriod(periodRow.getEndDate());
                noTimePeriods.add(noTimePeriod);
            }
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5173\u8054\u679a\u4e3e\u6570\u636e\u7684\u6700\u5927\u6df1\u5ea6")
    @RequestMapping(value={"queryEntityMaxDeep/{entityKey}"}, method={RequestMethod.GET})
    public int queryEntityMaxDeep(@PathVariable String entityKey) {
        int maxDepth = 0;
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityKey));
        IContext executorContext = null;
        try {
            IEntityTable executeReader = entityQuery.executeReader(executorContext);
            maxDepth = executeReader.getMaxDepth();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return maxDepth;
    }

    @ApiOperation(value="\u67e5\u8be2\u5b9e\u4f53\u8054\u52a8\u5173\u7cfb")
    @RequestMapping(value={"queryEntityRefers/{entityKey}"}, method={RequestMethod.GET})
    public List<IEntityRefer> queryEntityRefers(@PathVariable String entityKey) throws Exception {
        return this.iEntityMetaService.getEntityRefer(entityKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u8868\u5185\u7ef4\u5ea6")
    @RequestMapping(value={"queryInTableDim/{tableKey}"}, method={RequestMethod.GET})
    public List<DesignDataField> queryInTableDim(@PathVariable String tableKey) {
        return this.designDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
    }

    @ApiOperation(value="\u6811\u5f62\u521d\u59cb\u5316")
    @RequestMapping(value={"tree/root"}, method={RequestMethod.POST})
    public List<ITree<DataSchemeNode>> querySchemeTreeRoot(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        String dataSchemeKey = param.getDataSchemeKey();
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DesignTaskDefine queryTaskDefine = this.nrDesignTimeController.queryTaskDefine(param.getDimKey());
        IEntityDefine entityByViewKey = this.entityMetaService.queryEntity(queryTaskDefine.getDw());
        int interestType = this.allField();
        interestType = StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_NEWFMDM.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.TABLE.getValue() | NodeType.SCHEME.getValue() | NodeType.MD_INFO.getValue() | NodeType.DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD_ZB.getValue() : (StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_ACCOUNT.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.SCHEME.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.TABLE.getValue() | NodeType.TABLE_DIM.getValue() : NodeType.MD_INFO.getValue() | NodeType.SCHEME.getValue() | NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue());
        TaskSchemeNodeFilter filter = new TaskSchemeNodeFilter(entityByViewKey.getId(), dataSchemeKey, dimensions, FormType.forValue((int)Integer.parseInt(param.getFilter())), this.designDataSchemeService, true);
        return this.iDesignDataSchemeTreeService.getRootTree(dataSchemeKey, interestType, (NodeFilter)filter);
    }

    @ApiOperation(value="\u67e5\u8be2\u6811\u5f62\u5b50\u8282\u70b9")
    @RequestMapping(value={"tree/children"}, method={RequestMethod.POST})
    public List<ITree<DataSchemeNode>> querySchemeTreeChildren(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO parent = (DataSchemeNodeDTO)param.getDataSchemeNode();
        int interestType = this.allField();
        interestType = StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_NEWFMDM.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.TABLE.getValue() | NodeType.SCHEME.getValue() | NodeType.MD_INFO.getValue() | NodeType.DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD_ZB.getValue() : (StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_ACCOUNT.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue() ? NodeType.SCHEME.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.GROUP.getValue() | NodeType.FIELD.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.TABLE.getValue() | NodeType.TABLE_DIM.getValue() : NodeType.MD_INFO.getValue() | NodeType.SCHEME.getValue() | NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue());
        TaskSchemeNodeFilter filter = new TaskSchemeNodeFilter("", param.getDataSchemeKey(), new ArrayList<DesignDataDimension>(), FormType.forValue((int)Integer.parseInt(param.getFilter())), this.designDataSchemeService, false);
        return this.iDesignDataSchemeTreeService.getChildTree((DataSchemeNode)parent, interestType, (NodeFilter)filter);
    }

    @ApiOperation(value="\u6811\u5f62\u8282\u70b9\u5b9a\u4f4d")
    @RequestMapping(value={"tree/path"}, method={RequestMethod.POST})
    public List<ITree<DataSchemeNode>> querySchemeTreePath(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO node = (DataSchemeNodeDTO)param.getDataSchemeNode();
        String dataSchemeKey = param.getDataSchemeKey();
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DesignTaskDefine queryTaskDefine = this.nrDesignTimeController.queryTaskDefine(param.getDimKey());
        IEntityDefine entityByViewKey = this.entityMetaService.queryEntity(queryTaskDefine.getDw());
        int interestType = this.allField();
        if (StringUtils.isNotEmpty((String)param.getFilter()) && FormType.FORM_TYPE_NEWFMDM.getValue() == FormType.forValue((int)Integer.parseInt(param.getFilter())).getValue()) {
            interestType = NodeType.FIELD_ZB.getValue() | NodeType.DIM.getValue() | NodeType.SCHEME.getValue() | NodeType.TABLE.getValue() | NodeType.ENTITY_ATTRIBUTE.getValue() | NodeType.MD_INFO.getValue();
        }
        TaskSchemeNodeFilter filter = new TaskSchemeNodeFilter(entityByViewKey.getId(), dataSchemeKey, dimensions, FormType.forValue((int)Integer.parseInt(param.getFilter())), this.designDataSchemeService, true);
        return this.iDesignDataSchemeTreeService.getSpecifiedTree((DataSchemeNode)node, param.getDataSchemeKey(), interestType, (NodeFilter)filter);
    }

    @ApiOperation(value="\u6811\u5f62\u641c\u7d22")
    @RequestMapping(value={"tree/filter"}, method={RequestMethod.POST})
    public List<DataSchemeNode> querySchemeTreeFilter(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        TreeSearchQuery searchQuery = new TreeSearchQuery(param.getFilter(), param.getDataSchemeKey());
        searchQuery.setSearchType(NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue());
        return this.treeService.search(searchQuery);
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u6784\u5efa\uff1a\u521d\u59cb\u5316\u6811\u5f62")
    @RequestMapping(value={"tree/field_tree/init"}, method={RequestMethod.POST})
    public List<ITree<DataSchemeNode>> fieldTreeInitData(@RequestBody FieldTreeNode parent) {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        DesignTaskDefine queryTaskDefine = this.nrDesignTimeController.queryTaskDefine(parent.getKey());
        DesignTaskDefine settingTask = this.nrDesignTimeController.queryTaskDefine(parent.getParentId());
        if (null == queryTaskDefine) {
            DesignTaskLinkDefine designTaskLinkDefine = this.nrDesignTimeController.queryDesignByKey(parent.getKey());
            DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(designTaskLinkDefine.getRelatedFormSchemeKey());
            queryTaskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
        }
        if (null == settingTask) {
            settingTask = queryTaskDefine;
        }
        ArrayList otherTree = new ArrayList();
        ArrayList<ITree<DataSchemeNode>> firstTree = new ArrayList<ITree<DataSchemeNode>>();
        String currentDataScheme = "";
        if (null != settingTask) {
            currentDataScheme = settingTask.getDataScheme();
        }
        for (DesignDataScheme designDataScheme : allDataScheme) {
            if (!this.dataSchemeAuthService.canReadScheme(designDataScheme.getKey())) continue;
            List dimensions = this.designDataSchemeService.getDataSchemeDimension(designDataScheme.getKey());
            String entityKey = "";
            for (DesignDataDimension dimension : dimensions) {
                if (!dimension.getDimensionType().equals((Object)DimensionType.UNIT)) continue;
                entityKey = dimension.getDimKey();
            }
            if (null == settingTask || !settingTask.getDataScheme().equals(designDataScheme.getKey())) {
                entityKey = "";
            }
            if ("".equals(currentDataScheme)) {
                currentDataScheme = designDataScheme.getKey();
            }
            RunTaskSchemeNodeFilter filter = new RunTaskSchemeNodeFilter(currentDataScheme);
            int interestType = currentDataScheme.equals(designDataScheme.getKey()) ? this.noField() : this.noDimAndField();
            List rootTree = this.iDesignDataSchemeTreeService.getRootTree(designDataScheme.getKey(), interestType, (NodeFilter)filter);
            if (null == rootTree || rootTree.size() < 1) continue;
            if (null != queryTaskDefine && queryTaskDefine.getDataScheme().equals(designDataScheme.getKey())) {
                ((ITree)rootTree.get(0)).setSelected(true);
                ((ITree)rootTree.get(0)).setExpanded(true);
            } else {
                ((ITree)rootTree.get(0)).setSelected(false);
                ((ITree)rootTree.get(0)).setExpanded(false);
            }
            if (null != settingTask && settingTask.getDataScheme().equals(designDataScheme.getKey())) {
                firstTree.add((ITree<DataSchemeNode>)rootTree.get(0));
                continue;
            }
            otherTree.add(rootTree.get(0));
        }
        if (otherTree.size() != 0) {
            firstTree.addAll(otherTree);
        }
        return firstTree;
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u6784\u5efa\uff1a\u521d\u59cb\u5316\u8fd0\u884c\u671f\u6811\u5f62")
    @RequestMapping(value={"tree/field_tree/runtimeInit"}, method={RequestMethod.POST})
    public List<ITree<RuntimeDataSchemeNode>> runtimeFieldTreeInitData(@RequestBody FieldTreeNode parent) {
        TaskLinkDefine taskLinkDefine;
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        TaskDefine queryTaskDefine = this.IRunTimeViewController.queryTaskDefine(parent.getKey());
        TaskDefine settingTask = this.IRunTimeViewController.queryTaskDefine(parent.getParentId());
        if (null == queryTaskDefine && null != (taskLinkDefine = this.taskLinkService.queryTaskLinkByKey(parent.getKey()))) {
            FormSchemeDefine formSchemeDefine = this.IRunTimeViewController.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
            queryTaskDefine = this.IRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        }
        if (null == settingTask) {
            settingTask = queryTaskDefine;
        }
        ArrayList otherTree = new ArrayList();
        ArrayList<ITree<RuntimeDataSchemeNode>> firstTree = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        String currentDataScheme = "";
        if (null != settingTask) {
            currentDataScheme = settingTask.getDataScheme();
        }
        for (DataScheme designDataScheme : allDataScheme) {
            if (!this.dataSchemeAuthService.canReadScheme(designDataScheme.getKey())) continue;
            List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(designDataScheme.getKey());
            String entityKey = "";
            for (DataDimension dimension : dimensions) {
                if (!dimension.getDimensionType().equals((Object)DimensionType.UNIT)) continue;
                entityKey = dimension.getDimKey();
            }
            if (null == settingTask || !settingTask.getDataScheme().equals(designDataScheme.getKey())) {
                entityKey = "";
            }
            if ("".equals(currentDataScheme)) {
                currentDataScheme = designDataScheme.getKey();
            }
            RunTaskSchemeNodeFilter filter = new RunTaskSchemeNodeFilter(currentDataScheme);
            int interestType = currentDataScheme.equals(designDataScheme.getKey()) ? this.noField() : this.noDimAndField();
            List rootTree = this.iRuntimeDataSchemeTreeService.getRootTree(designDataScheme.getKey(), interestType, (NodeFilter)filter);
            if (null == rootTree || rootTree.size() < 1) continue;
            if (null != queryTaskDefine && queryTaskDefine.getDataScheme().equals(designDataScheme.getKey())) {
                ((ITree)rootTree.get(0)).setSelected(true);
                ((ITree)rootTree.get(0)).setExpanded(true);
            } else {
                ((ITree)rootTree.get(0)).setSelected(false);
                ((ITree)rootTree.get(0)).setExpanded(false);
            }
            if (null != settingTask && settingTask.getDataScheme().equals(designDataScheme.getKey())) {
                firstTree.add((ITree<RuntimeDataSchemeNode>)rootTree.get(0));
                continue;
            }
            otherTree.add(rootTree.get(0));
        }
        if (otherTree.size() != 0) {
            firstTree.addAll(otherTree);
        }
        return firstTree;
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u6784\u5efa\uff1a\u52a0\u8f7d\u5b50\u5206\u7ec4\u8282\u70b9")
    @RequestMapping(value={"tree/field_tree/loadGroup"}, method={RequestMethod.POST})
    public List<ITree<DataSchemeNode>> fieldTreeLoadDataGroup(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO parent = (DataSchemeNodeDTO)param.getDataSchemeNode();
        int interestType = this.noField();
        return this.iDesignDataSchemeTreeService.getChildTree((DataSchemeNode)parent, interestType, null);
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u6784\u5efa\uff1a\u52a0\u8f7d\u8fd0\u884c\u671f\u5b50\u5206\u7ec4\u8282\u70b9")
    @RequestMapping(value={"tree/field_tree/runtimeloadGroup"}, method={RequestMethod.POST})
    public List<ITree<RuntimeDataSchemeNode>> runtimeFieldTreeLoadDataGroup(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) {
        RuntimeDataSchemeNodeDTO parent = (RuntimeDataSchemeNodeDTO)param.getDataSchemeNode();
        int interestType = this.noField();
        return this.iRuntimeDataSchemeTreeService.getChildTree((RuntimeDataSchemeNode)parent, interestType, null);
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u6784\u5efa\uff1a\u52a0\u8f7d\u5b50\u6307\u6807\u5217\u8868")
    @RequestMapping(value={"tree/field_tree/loadFields"}, method={RequestMethod.POST})
    public List<DesignFieldDefine> fieldTreeLoadFields(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO dataSchemeNode = (DataSchemeNodeDTO)param.getDataSchemeNode();
        ArrayList<DesignFieldDefine> cildren = new ArrayList<DesignFieldDefine>();
        int interestType = this.allField();
        List childTree = this.iDesignDataSchemeTreeService.getChildTree((DataSchemeNode)dataSchemeNode, interestType, null);
        HashSet<String> infoKeys = new HashSet<String>();
        List allDataTable = this.designDataSchemeService.getAllDataTable();
        for (DesignDataTable designDataTable : allDataTable) {
            if (!DataTableType.MD_INFO.equals((Object)designDataTable.getDataTableType())) continue;
            infoKeys.add(designDataTable.getKey());
        }
        for (ITree next : childTree) {
            if (!next.isLeaf()) continue;
            DesignFieldDefineImpl define = new DesignFieldDefineImpl();
            define.setKey(((DataSchemeNode)next.getData()).getKey());
            define.setCode(((DataSchemeNode)next.getData()).getCode());
            define.setTitle(((DataSchemeNode)next.getData()).getTitle());
            DesignDataFieldDO data = null;
            try {
                if (((DataSchemeNode)next.getData()).getData() instanceof DesignDataFieldDO) {
                    data = (DesignDataFieldDO)((DataSchemeNode)next.getData()).getData();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (((DataSchemeNode)next.getData()).getType() == NodeType.ENTITY_ATTRIBUTE.getValue() || null != data && infoKeys.contains(data.getDataTableKey())) {
                define.setReferFieldKey("entity");
            } else {
                define.setReferFieldKey("field");
            }
            cildren.add((DesignFieldDefine)define);
        }
        return cildren;
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u6784\u5efa\uff1a\u52a0\u8f7d\u8fd0\u884c\u671f\u5b50\u6307\u6807\u5217\u8868")
    @RequestMapping(value={"tree/field_tree/runtimeloadFields"}, method={RequestMethod.POST})
    public List<DesignFieldDefine> runtimeFieldTreeLoadFields(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) {
        RuntimeDataSchemeNodeDTO dataSchemeNode = (RuntimeDataSchemeNodeDTO)param.getDataSchemeNode();
        ArrayList<DesignFieldDefine> cildren = new ArrayList<DesignFieldDefine>();
        int interestType = this.allField();
        List childTree = this.iRuntimeDataSchemeTreeService.getChildTree((RuntimeDataSchemeNode)dataSchemeNode, interestType, null);
        for (ITree next : childTree) {
            if (!next.isLeaf()) continue;
            DesignFieldDefineImpl define = new DesignFieldDefineImpl();
            define.setKey(((RuntimeDataSchemeNode)next.getData()).getKey());
            define.setCode(((RuntimeDataSchemeNode)next.getData()).getCode());
            define.setTitle(((RuntimeDataSchemeNode)next.getData()).getTitle());
            if (((RuntimeDataSchemeNode)next.getData()).getType() == NodeType.ENTITY_ATTRIBUTE.getValue()) {
                define.setReferFieldKey("entity");
            } else {
                define.setReferFieldKey("field");
            }
            cildren.add((DesignFieldDefine)define);
        }
        return cildren;
    }

    private int allField() {
        int interestType = 0;
        for (NodeType value : NodeType.values()) {
            if (value.getValue() <= NodeType.SCHEME_GROUP.getValue() || value.getValue() == NodeType.FMDM_TABLE.getValue()) continue;
            interestType |= value.getValue();
        }
        return interestType;
    }

    private int noDimAndField() {
        int interestType = 0;
        block3: for (NodeType value : NodeType.values()) {
            switch (value) {
                case GROUP: 
                case SCHEME: 
                case ACCOUNT_TABLE: 
                case TABLE: 
                case DETAIL_TABLE: 
                case MUL_DIM_TABLE: {
                    interestType |= value.getValue();
                    continue block3;
                }
                default: {
                    continue block3;
                }
            }
        }
        return interestType;
    }

    private int noField() {
        int interestType = 0;
        block3: for (NodeType value : NodeType.values()) {
            switch (value) {
                case GROUP: 
                case SCHEME: 
                case ACCOUNT_TABLE: 
                case TABLE: 
                case DETAIL_TABLE: 
                case MUL_DIM_TABLE: 
                case DIM: 
                case MD_INFO: {
                    interestType |= value.getValue();
                    continue block3;
                }
                default: {
                    continue block3;
                }
            }
        }
        return interestType;
    }

    @RequestMapping(value={"allfmdmattribute"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:task_form:design"})
    public List<FieldObject> allfmdmattribute(@RequestBody ParamToDesigner paramToDesigner) throws Exception {
        DesignTaskDefine queryTaskDefine = this.nrDesignTimeController.queryTaskDefine(paramToDesigner.getActivedTaskId());
        String dw = "";
        String dims = "";
        dw = StringUtils.isNotEmpty((String)paramToDesigner.getDw()) ? paramToDesigner.getDw() : queryTaskDefine.getDw();
        dims = StringUtils.isNotEmpty((String)paramToDesigner.getDimKey()) ? paramToDesigner.getDimKey() : queryTaskDefine.getDims();
        IEntityModel entityModel1 = this.entityMetaService.getEntityModel(dw);
        List attributes1 = entityModel1.getShowFields();
        ArrayList<FieldObject> list = new ArrayList<FieldObject>();
        for (IEntityAttribute iEntityAttribute : attributes1) {
            list.add(this.toEntityObject(iEntityAttribute, "fmdm"));
        }
        if (StringUtils.isNotEmpty((String)dims)) {
            String[] split;
            for (String view : split = dims.split(";")) {
                IEntityModel entityModel;
                IEntityDefine entityByViewKey = this.entityMetaService.queryEntity(view);
                if (!Objects.nonNull(entityByViewKey) || !Objects.nonNull(entityModel = this.iEntityMetaService.getEntityModel(entityByViewKey.getId()))) continue;
                Iterator attributes = entityModel.getAttributes();
                while (attributes.hasNext()) {
                    IEntityAttribute next = (IEntityAttribute)attributes.next();
                    list.add(this.toEntityObject(next, "dims"));
                }
            }
        }
        return list;
    }

    private FieldObject toEntityObject(IEntityAttribute ea, String type) throws JQException {
        TableModelDefine tableModel;
        DesignFieldDefineImpl fieldDefine = new DesignFieldDefineImpl();
        fieldDefine.setKey(ea.getID());
        fieldDefine.setCode(ea.getCode());
        fieldDefine.setTitle(ea.getTitle());
        fieldDefine.setDescription(ea.getDesc());
        if (null != ea.getAggrType()) {
            fieldDefine.setGatherType(FieldGatherType.forValue((int)ea.getAggrType().getValue()));
        }
        fieldDefine.setType(DefinitionTransUtils.valueOf((ColumnModelType)ea.getColumnType()));
        fieldDefine.setOwnerTableKey(ea.getTableID());
        fieldDefine.setSize(ea.getPrecision());
        fieldDefine.setFractionDigits(ea.getDecimal());
        fieldDefine.setDefaultValue(ea.getDefaultValue());
        fieldDefine.setMeasureUnit(ea.getMeasureUnit());
        fieldDefine.setNullable(ea.isNullAble());
        fieldDefine.setEntityKey(ea.getCatagory());
        fieldDefine.setReferFieldKey(ea.getReferTableID());
        fieldDefine.setAllowMultipleSelect(false);
        fieldDefine.setSecretLevel(0);
        fieldDefine.setShowFormat(ea.getShowFormat());
        if (StringUtils.isNotEmpty((String)ea.getShowFormat())) {
            FormatProperties formatProperties = new FormatProperties();
            formatProperties.setFormatType(Integer.valueOf(1));
            formatProperties.setPattern(ea.getShowFormat());
            fieldDefine.setFormatProperties(formatProperties);
        }
        fieldDefine.setUseAuthority(true);
        fieldDefine.setVerification("");
        fieldDefine.setOwnerLevelAndId("");
        fieldDefine.setVersion("");
        fieldDefine.setOrder(ea.getOrder() + "");
        fieldDefine.setUpdateTime(null);
        if (ea.getCode().equals("BIZKEYORDER")) {
            fieldDefine.setValueType(FieldValueType.FIELD_VALUE_BIZKEY_ORDER);
        } else if (ea.getCode().equals("FLOATORDER")) {
            fieldDefine.setValueType(FieldValueType.FIELD_VALUE_INPUT_ORDER);
        }
        FieldObject setFieldObjectProperty = this.initParamObjPropertyUtil.setFieldObjectProperty((FieldDefine)fieldDefine);
        if (StringUtils.isNotEmpty((String)ea.getReferTableID()) && null != (tableModel = this.dataModelService.getTableModelDefineById(ea.getReferTableID()))) {
            setFieldObjectProperty.setReferField(ea.getReferTableID());
            IEntityDefine queryEntityByCode = this.iEntityMetaService.queryEntityByCode(tableModel.getCode());
            setFieldObjectProperty.setReferFieldName(queryEntityByCode.getTitle());
            setFieldObjectProperty.setReferFieldCode(queryEntityByCode.getCode());
            setFieldObjectProperty.setEntityKey(queryEntityByCode.getId());
        }
        setFieldObjectProperty.setPropertyType(type);
        return setFieldObjectProperty;
    }

    private List<FieldObject> toEntityObject(List<IFMDMAttribute> attributes, String type) throws JQException {
        ArrayList<FieldObject> list = new ArrayList<FieldObject>();
        for (IFMDMAttribute attribute : attributes) {
            TableModelDefine tableModel;
            FieldObject fieldObj = new FieldObject();
            fieldObj.setCode(attribute.getCode());
            fieldObj.setTitle(attribute.getTitle());
            fieldObj.setSize(attribute.getPrecision());
            fieldObj.setID(attribute.getID());
            FieldType fieldType = DefinitionTransUtils.valueOf((ColumnModelType)attribute.getColumnType());
            fieldObj.setType(fieldType == FieldType.FIELD_TYPE_FILE ? FieldType.FIELD_TYPE_BINARY.getValue() : fieldType.getValue());
            fieldObj.setFractionDigits(attribute.getDecimal());
            fieldObj.setOwnerTableID(attribute.getTableID());
            fieldObj.setDescription(attribute.getDesc());
            fieldObj.setNullable(attribute.isNullAble());
            fieldObj.setDuplicateValidate(false);
            fieldObj.setDefaultValue(attribute.getDefaultValue());
            fieldObj.setAllowMultiMap(attribute.isMultival());
            fieldObj.setSameServeCode(this.serveCodeService.isSameServeCode(""));
            fieldObj.setReferField(attribute.getReferColumnID());
            if (StringUtils.isNotEmpty((String)attribute.getReferTableID()) && null != (tableModel = this.dataModelService.getTableModelDefineById(attribute.getReferTableID()))) {
                IEntityDefine queryEntityByCode = this.iEntityMetaService.queryEntityByCode(tableModel.getCode());
                fieldObj.setReferFieldName(queryEntityByCode.getTitle());
                fieldObj.setReferFieldCode(queryEntityByCode.getCode());
                fieldObj.setEntityKey(queryEntityByCode.getId());
            }
            fieldObj.setPropertyType(type);
            list.add(fieldObj);
        }
        return list;
    }

    @ApiOperation(value="\u6839\u636e\u65f6\u671f\u89c6\u56fe\u67e5\u8be2\u5f00\u59cb\u7ed3\u675f\u65f6\u671f")
    @RequestMapping(value={"queryDateByPeriodView/{viewKey}"}, method={RequestMethod.GET})
    public String[] getPeriodCodeRegion(@PathVariable String viewKey) {
        if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(viewKey)) {
            IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(viewKey);
            return this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodByViewKey.getKey()).getPeriodCodeRegion();
        }
        return null;
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u5b58\u50a8\u8868Key\u67e5\u8be2\u5b58\u50a8\u8868\u4fe1\u606f")
    @GetMapping(value={"table/{key}"})
    public TableDefine queryTableDefine(@PathVariable(value="key") String id) {
        DesignTableDefine tableDefine = null;
        try {
            DesignFieldDefine queryFieldDefine = this.dataDefinitionDesignTimeController2.queryFieldDefine(id);
            tableDefine = this.dataDefinitionDesignTimeController2.queryTableDefine(queryFieldDefine.getOwnerTableKey());
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return tableDefine;
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u5b58\u50a8\u8868Key\u67e5\u8be2\u5b58\u50a8\u8868\u4fe1\u606f")
    @GetMapping(value={"table/runtime/{key}"})
    public TableDefine queryRuntimeTableDefine(@PathVariable(value="key") String id) {
        TableDefine tableDefine = null;
        try {
            FieldDefine queryFieldDefine = this.dataDefinitionRuntimeController2.queryFieldDefine(id);
            tableDefine = this.dataDefinitionRuntimeController2.queryTableDefine(queryFieldDefine.getOwnerTableKey());
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return tableDefine;
    }

    @ApiOperation(value="\u67e5\u8be2\u5b9e\u4f53\u7684\u7236\u8282\u70b9\u5c5e\u6027")
    @RequestMapping(value={"getParentCodeAttr/{entity}"}, method={RequestMethod.GET})
    public IEntityAttribute getParentCodeAttr(@PathVariable(value="entity") String entity) {
        IEntityAttribute parentField = null;
        if (StringUtils.isNotEmpty((String)entity)) {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(entity);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(tableModelDefine.getCode());
            parentField = entityModel.getParentField();
        }
        return parentField;
    }

    @Deprecated
    @ApiOperation(value="\u5224\u65ad\u662f\u5426\u4e3b\u7ef4\u5ea6\u4e3a\u57fa\u7840\u6570\u636e")
    @RequestMapping(value={"isCreateFMDM/{taskid}"}, method={RequestMethod.GET})
    public boolean isCreateFmdm(@PathVariable(value="taskid") String taskid) {
        return true;
    }

    @ApiOperation(value="\u83b7\u53d6\u8bbe\u8ba1\u671f\u4e0a\u4e0b\u6587")
    @RequestMapping(value={"get/design/context/{taskKey}"}, method={RequestMethod.GET})
    public DesignContextVO getDesignContext(@PathVariable String taskKey) {
        DesignContextVO vo = new DesignContextVO();
        vo.setTaskKey(taskKey);
        vo.setEnableAccount(this.designDataSchemeService.enableAccountTable());
        return vo;
    }

    @ApiOperation(value="\u5efa\u6a21\u4efb\u52a1\u6570\u91cf\u9a8c\u8bc1")
    @RequestMapping(value={"/auth/tasknum"}, method={RequestMethod.GET})
    public NumAuthInfo queryTaskAuthInfo() {
        NumAuthInfo authInfo = new NumAuthInfo();
        int taskMaxNumber = this.paramMaxNumberConfig.getTaskMaxNumber();
        int taskNumber = this.nrDesignTimeController.countTask();
        if (taskMaxNumber != 0) {
            if (taskNumber >= taskMaxNumber) {
                authInfo.setMaxNumber(taskMaxNumber);
                authInfo.setInfo("\u4efb\u52a1\u6570\u91cf\u8d85\u8fc7\u6388\u6743\u9650\u5236\uff0c\u7981\u6b62\u521b\u5efa\u4efb\u52a1\uff01");
                authInfo.setParamNum(ParamNum.TASK);
            } else {
                int formMaxNumber = this.paramMaxNumberConfig.getFormMaxNumber();
                int formNumber = this.nrDesignTimeController.countForm();
                if (formMaxNumber != 0 && formNumber >= formMaxNumber) {
                    authInfo.setMaxNumber(formMaxNumber);
                    authInfo.setInfo("\u8868\u5355\u6570\u91cf\u8d85\u8fc7\u6388\u6743\u9650\u5236\uff0c\u7981\u6b62\u521b\u5efa\u4efb\u52a1\uff01");
                    authInfo.setParamNum(ParamNum.FORM);
                }
            }
        }
        return authInfo;
    }

    @ApiOperation(value="\u5efa\u6a21\u62a5\u8868\u6570\u91cf\u9a8c\u8bc1")
    @RequestMapping(value={"/auth/formnum"}, method={RequestMethod.GET})
    public NumAuthInfo queryFormAuthInfo() {
        NumAuthInfo authInfo = new NumAuthInfo();
        int maxNumber = this.paramMaxNumberConfig.getFormMaxNumber();
        int formNumber = this.nrDesignTimeController.countForm();
        if (maxNumber != 0 && formNumber >= maxNumber) {
            authInfo.setMaxNumber(maxNumber);
            authInfo.setInfo("\u62a5\u8868\u6570\u91cf\u8d85\u8fc7\u6388\u6743\u9650\u5236\uff0c\u7981\u6b62\u65b0\u5efa\u8868\u5355\uff01");
            authInfo.setParamNum(ParamNum.FORM);
        }
        return authInfo;
    }

    @ApiOperation(value="\u67e5\u8be2\u8868\u5185\u65e0\u9ed8\u8ba4\u503c\u7ef4\u5ea6")
    @RequestMapping(value={"tabledim"}, method={RequestMethod.POST})
    public Map<String, List<String>> queryTableDim(@RequestBody List<String> tableKeys) {
        HashMap<String, List<String>> tableDimMap = new HashMap<String, List<String>>();
        if (null != tableKeys) {
            for (String keys : tableKeys) {
                String regionKey = keys.split(";")[0];
                String tableKey = keys.split(";")[1];
                DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
                if (null == dataTable) continue;
                for (String bizKey : dataTable.getBizKeys()) {
                    DesignDataField dataField = this.designDataSchemeService.getDataField(bizKey);
                    if (null == dataField || !DataFieldKind.TABLE_FIELD_DIM.equals((Object)dataField.getDataFieldKind()) || !StringUtils.isEmpty((String)dataField.getDefaultValue())) continue;
                    if (null == tableDimMap.get(regionKey)) {
                        tableDimMap.put(regionKey, new ArrayList());
                        ((List)tableDimMap.get(regionKey)).add(dataField.getKey());
                        continue;
                    }
                    ((List)tableDimMap.get(regionKey)).add(dataField.getKey());
                }
            }
        }
        return tableDimMap;
    }
}

