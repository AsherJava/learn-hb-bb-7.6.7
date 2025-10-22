/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.batch.gather.gzw.service.dao.IGatherEntityCodeMappingDao
 *  com.jiuqi.nr.batch.gather.gzw.service.entity.GatherEntityCodeMapping
 *  com.jiuqi.nr.batch.gather.gzw.service.executor.EntityExecutor
 *  com.jiuqi.nr.batch.gather.gzw.service.provider.ConditionTargetDimGZWProvider
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper
 *  com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryPara
 *  com.jiuqi.nr.batch.summary.service.targetdim.RangeOfAllEntities
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nr.zbquery.model.ZBQueryModel
 *  com.jiuqi.nr.zbquery.rest.DataEntryController
 *  com.jiuqi.nr.zbquery.rest.vo.DataEntryVO
 *  com.jiuqi.nr.zbquery.rest.vo.DimensionVO
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.batch.gather.gzw.web;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.batch.gather.gzw.service.dao.IGatherEntityCodeMappingDao;
import com.jiuqi.nr.batch.gather.gzw.service.entity.GatherEntityCodeMapping;
import com.jiuqi.nr.batch.gather.gzw.service.executor.EntityExecutor;
import com.jiuqi.nr.batch.gather.gzw.service.provider.ConditionTargetDimGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.func.para.OpenFuncParamImpl;
import com.jiuqi.nr.batch.gather.gzw.web.vo.CorporateTypeVO;
import com.jiuqi.nr.batch.gather.gzw.web.vo.DimValueVO;
import com.jiuqi.nr.batch.gather.gzw.web.vo.SingleDimVO;
import com.jiuqi.nr.batch.gather.gzw.web.vo.TaskOrgLinkVO;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper;
import com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryPara;
import com.jiuqi.nr.batch.summary.service.targetdim.RangeOfAllEntities;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.DataEntryController;
import com.jiuqi.nr.zbquery.rest.vo.DataEntryVO;
import com.jiuqi.nr.zbquery.rest.vo.DimensionVO;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/batch-gather-GZW/show-data"})
@Api(tags={"\u6c47\u603b\u6570\u636e\u67e5\u770b-API"})
public class BatchGatherGZWViewDataController {
    public static final String CORPORATE_DEFAULT_VALUE = "DEFAULT_TEMP_COPORATE_VALUE";
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private com.jiuqi.nr.definition.controller.IRunTimeViewController iRunTimeViewController;
    @Resource
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Resource
    private CustomConditionHelper customConditionHelper;
    @Resource
    private TargetDimProviderFactoryImpl targetDimFactory;
    @Resource
    private DataEntryController dataEntryController;
    @Resource
    private EntityExecutor entityExecutor;
    @Resource
    private EntityFrameExtendHelper entityFrameExtendHelper;
    @Resource
    private IGatherEntityCodeMappingDao gatherEntityCodeMappingDao;
    @Resource
    private IRunTimeViewController runTimeViewController;

    @ResponseBody
    @ApiOperation(value="\u67e5\u770b\u6c47\u603b\u6570\u636e-\u8bf7\u6c42\u529f\u80fd\u53c2\u6570")
    @PostMapping(value={"/load-func-para"})
    public IReturnObject<Map<String, String>> openDataEntryPage(@RequestBody OpenFuncParamImpl funcParam) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        String schemeKey = funcParam.getContextData().getBatchGatherSchemeKey();
        List mappings = this.gatherEntityCodeMappingDao.queryCodeMapping(schemeKey);
        if (mappings == null) {
            return IReturnObject.getErrorInstance((String)"\u8be5\u65b9\u6848\u672a\u6267\u884c\u8fc7\u6c47\u603b \u6ca1\u6709\u76f8\u5173\u6620\u5c04\u5173\u7cfb");
        }
        Optional<GatherEntityCodeMapping> latestCodeMapping = mappings.stream().max(Comparator.comparing(GatherEntityCodeMapping::getExecuteDatetime));
        if (!latestCodeMapping.isPresent()) {
            return IReturnObject.getErrorInstance((String)"\u672a\u627e\u5230\u5bf9\u5e94\u6c47\u603b\u5355\u4f4d");
        }
        resultMap.put("period", latestCodeMapping.get().getPeriod());
        resultMap.put("gatherEntityId", latestCodeMapping.get().getEntityCode());
        resultMap.put("entityId", latestCodeMapping.get().getEntityId());
        return IReturnObject.getSuccessInstance(resultMap);
    }

    @ResponseBody
    @ApiOperation(value="\u7a7f\u900f\u660e\u7ec6\u67e5\u8be2-\u8bf7\u6c42\u529f\u80fd\u53c2\u6570")
    @PostMapping(value={"/zb-query/load-func-para"})
    @NRContextBuild
    public IReturnObject<ZBQueryModel> loadFunctionParam(@RequestBody ZBQueryEntryPara funcParam) {
        IReturnObject instance;
        ZBQueryModel queryModel = null;
        try {
            queryModel = this.newZBQueryModel(funcParam);
            instance = IReturnObject.getSuccessInstance((Object)queryModel);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)queryModel);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u5224\u65ad\u5b9e\u4f53\u662f\u5426\u5305\u542b\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u60c5\u666f \u82e5\u5305\u542b\u5219\u8fd4\u56de\u6240\u6709\u7684\u5408\u5e76\u5355\u4f4d\u7c7b\u578b")
    @GetMapping(value={"/corporate-entity-type"})
    @Deprecated
    public IReturnObject<List<Map<String, String>>> getCorporateEntityType(@RequestParam(name="taskId") String taskId, @RequestParam(name="period") String period) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        Optional<DataDimension> corporateEntity = this.iRuntimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION).stream().filter(dim -> this.entityFrameExtendHelper.isCorporate(taskDefine, dim)).findFirst();
        if (!corporateEntity.isPresent()) {
            return IReturnObject.getErrorInstance((String)"\u8be5\u62a5\u8868\u4efb\u52a1\u672a\u5305\u542b\"\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\"\u60c5\u666f", new ArrayList());
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        List entityRows = this.entityExecutor.getEntityData(corporateEntity.get().getDimKey(), dimensionValueSet, taskDefine.getDateTime());
        List corporateEntityTypeList = entityRows.stream().map(row -> {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("value", row.getCode());
            item.put("label", row.getTitle());
            return item;
        }).collect(Collectors.toList());
        return IReturnObject.getSuccessInstance(corporateEntityTypeList);
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u7684\u6240\u6709\u5355\u4e00\u7ef4\u5ea6")
    @GetMapping(value={"/single-dim"})
    public IReturnObject<CorporateTypeVO> getCorporateType(@RequestParam(name="taskId") String taskId) {
        CorporateTypeVO corporateTypeVO = new CorporateTypeVO();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        List taskOrgLinks = this.runTimeViewController.listTaskOrgLinkByTask(taskDefine.getKey());
        if (!CollectionUtils.isEmpty(taskOrgLinks)) {
            List<TaskOrgLinkVO> taskOrgTypes;
            if (taskOrgLinks.size() == 1) {
                if (!taskDefine.getDw().equals(((TaskOrgLinkDefine)taskOrgLinks.get(0)).getEntity())) {
                    taskOrgTypes = this.getTaskOrgLinkVOS(taskOrgLinks);
                    corporateTypeVO.setTaskOrgTypes(taskOrgTypes);
                }
            } else {
                taskOrgTypes = this.getTaskOrgLinkVOS(taskOrgLinks);
                corporateTypeVO.setTaskOrgTypes(taskOrgTypes);
            }
        }
        List dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        ArrayList<SingleDimVO> singleDims = new ArrayList<SingleDimVO>();
        corporateTypeVO.setSingleDims(singleDims);
        for (DataDimension dimension : dataSchemeDimension) {
            IEntityAttribute attribute;
            boolean singDim;
            String dimKey = dimension.getDimKey();
            String dimAttribute = dimension.getDimAttribute();
            if (!StringUtils.isNotEmpty((String)dimAttribute) || !StringUtils.isNotEmpty((String)dimKey) || !(singDim = (attribute = dwEntityModel.getAttribute(dimAttribute)) != null && !attribute.isMultival())) continue;
            SingleDimVO singleDimVO = new SingleDimVO();
            singleDimVO.setEntityId(dimKey);
            IEntityDefine define = this.entityMetaService.queryEntity(dimKey);
            singleDimVO.setTitle(define.getTitle());
            singleDims.add(singleDimVO);
            ArrayList<DimValueVO> dimValues = new ArrayList<DimValueVO>();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            List entityRows = this.entityExecutor.getEntityData(dimKey, dimensionValueSet, taskDefine.getDateTime());
            for (IEntityRow entityRow : entityRows) {
                DimValueVO dimValueVO = new DimValueVO();
                dimValueVO.setValue(entityRow.getCode());
                dimValueVO.setLabel(entityRow.getTitle());
                dimValues.add(dimValueVO);
            }
            singleDimVO.setDimValues(dimValues);
        }
        return IReturnObject.getSuccessInstance((Object)corporateTypeVO);
    }

    private List<TaskOrgLinkVO> getTaskOrgLinkVOS(List<TaskOrgLinkDefine> taskOrgLinks) {
        ArrayList<TaskOrgLinkVO> taskOrgTypes = new ArrayList<TaskOrgLinkVO>();
        for (TaskOrgLinkDefine taskOrgLink : taskOrgLinks) {
            TaskOrgLinkVO taskOrgLinkVO = new TaskOrgLinkVO();
            taskOrgLinkVO.setEntityId(taskOrgLink.getEntity());
            String entity = taskOrgLink.getEntity();
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entity);
            taskOrgLinkVO.setTitle(entityDefine.getTitle());
            taskOrgTypes.add(taskOrgLinkVO);
        }
        return taskOrgTypes;
    }

    public ZBQueryModel newZBQueryModel(ZBQueryEntryPara funcParam) throws Exception {
        String contextEntityId = funcParam.getContextEntityId();
        Optional<GatherEntityCodeMapping> gatherEntityCodeMapping = this.gatherEntityCodeMappingDao.queryCodeMappingByDw(contextEntityId, funcParam.getDimValue(), funcParam.getTaskKey(), funcParam.getPeriod()).stream().max(Comparator.comparing(GatherEntityCodeMapping::getExecuteDatetime));
        if (!gatherEntityCodeMapping.isPresent()) {
            throw new NullPointerException();
        }
        String gatherSchemeKey = gatherEntityCodeMapping.get().getGatherSchemeKey();
        SummaryScheme summaryScheme = this.schemeService.findScheme(gatherSchemeKey);
        ConditionTargetDimGZWProvider targetDimProvider = new ConditionTargetDimGZWProvider(summaryScheme, this.customConditionHelper.getTreeProvider(summaryScheme.getKey()), (TargetRangeUnitProvider)new RangeOfAllEntities(), this.targetDimFactory, this.gatherEntityCodeMappingDao);
        List entityRowKeys = targetDimProvider.getEntityRowKeys(funcParam.getPeriod(), funcParam.getDimValue());
        if (entityRowKeys == null || entityRowKeys.isEmpty()) {
            return null;
        }
        entityRowKeys.add(0, funcParam.getDimValue());
        String strUnitKeys = String.join((CharSequence)";", entityRowKeys);
        if (entityRowKeys.size() == 1) {
            strUnitKeys = strUnitKeys + ";";
        }
        ((DimensionVO)funcParam.getDimensionVOList().get(0)).setValue(strUnitKeys);
        return this.dataEntryController.getQueryModelObject((DataEntryVO)funcParam);
    }
}

