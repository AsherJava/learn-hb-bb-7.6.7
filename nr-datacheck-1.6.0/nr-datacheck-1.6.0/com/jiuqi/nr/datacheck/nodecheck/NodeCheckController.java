/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.collection.ArrayMap
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datacheck.nodecheck;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.collection.ArrayMap;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.nodecheck.bean.DimInfoItem;
import com.jiuqi.nr.datacheck.nodecheck.bean.NodeCheckResultVO;
import com.jiuqi.nr.datacheck.nodecheck.bean.RequestDimTitileParam;
import com.jiuqi.nr.datacheck.nodecheck.bean.RequestParamVO;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datacheck/nodecheck"})
public class NodeCheckController {
    private static final Logger logger = LoggerFactory.getLogger(NodeCheckController.class);
    @Autowired
    private MultCheckResDao multCheckResDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;

    @PostMapping(value={"/get-result"})
    @ApiOperation(value="\u8282\u70b9\u68c0\u67e5\u7ed3\u679c", notes="\u8282\u70b9\u68c0\u67e5\u7ed3\u679c")
    public NodeCheckResultVO getNodeCheckResult(@RequestBody RequestParamVO param) throws Exception {
        String runId = param.getRunId();
        String itemKey = param.getItemKey();
        if (!StringUtils.hasText(runId) || !StringUtils.hasText(itemKey)) {
            return new NodeCheckResultVO();
        }
        MultCheckRes result = this.multCheckResDao.findById(UUIDMerger.merge((String)runId, (String)itemKey));
        return SerializeUtil.deserializeFromJson(result.getData(), NodeCheckResultVO.class);
    }

    @PostMapping(value={"/get-dim-title"})
    @ApiOperation(value="\u83b7\u53d6\u7ef4\u5ea6\u540d\u79f0", notes="\u83b7\u53d6\u7ef4\u5ea6\u540d\u79f0")
    @NRContextBuild
    public Map<String, List<DimInfoItem>> getDimTitle(@RequestBody RequestDimTitileParam param) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List dims = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        Map<String, String> dimSet = param.getDimSet();
        ArrayMap map = new ArrayMap();
        for (DataDimension dim : dims) {
            String dimensionName = this.dimensionProvider.getDimensionNameByEntityId(dim.getDimKey());
            String values = dimSet.get(dimensionName);
            if (!dimSet.containsKey(dimensionName)) continue;
            if ("MD_CURRENCY".equals(dimensionName) && !values.contains(";") && ("PROVIDER_BASECURRENCY".equals(values) || "PROVIDER_PBASECURRENCY".equals(values))) {
                values = this.getCurrencyValue(taskDefine.getDw(), param, dimensionName);
            }
            if ("ADJUST".equals(dimensionName)) continue;
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
            String[] split = values.split(";");
            IEntityTable entityTable = this.getEntityTable(dim.getDimKey(), param.getFormSchemeKey(), dimensionName, Arrays.asList(split), param.getPeriod());
            List allRows = entityTable.getAllRows();
            Map<String, String> collect = allRows.stream().collect(Collectors.toMap(IEntityItem::getCode, IEntityItem::getTitle));
            for (String key : split) {
                if (!collect.containsKey(key)) continue;
                DimInfoItem item = new DimInfoItem();
                item.setCode(key);
                item.setTitle(collect.get(key));
                item.setEntityTitle(entityDefine.getTitle());
                map.computeIfAbsent(dimensionName, k -> new ArrayList()).add(item);
            }
        }
        return map;
    }

    private String getCurrencyValue(String entityId, RequestDimTitileParam param, String currencyDimName) {
        if (param.getOrgEntity() != null) {
            entityId = param.getOrgEntity();
        }
        String dwName = this.dimensionProvider.getDimensionNameByEntityId(entityId);
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue dwDimensionValue = new DimensionValue();
        dwDimensionValue.setName(dwName);
        dwDimensionValue.setValue(String.join((CharSequence)";", param.getOrgCode()));
        dimensionSet.put(dwName, dwDimensionValue);
        DimensionValue dataTimeDim = new DimensionValue();
        dataTimeDim.setName("DATATIME");
        dataTimeDim.setValue(param.getPeriod());
        dimensionSet.put("DATATIME", dataTimeDim);
        Map<String, String> dims = param.getDimSet();
        for (Map.Entry<String, String> dimNameValue : dims.entrySet()) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(dimNameValue.getKey());
            dimensionValue.setValue(dimNameValue.getValue());
            dimensionSet.put(dimNameValue.getKey(), dimensionValue);
        }
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, param.getFormSchemeKey());
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        HashSet<String> valueSet = new HashSet<String>();
        StringBuilder newValues = new StringBuilder();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            String value = (String)dimensionCombination.getValue(currencyDimName);
            if (valueSet.contains(value)) continue;
            valueSet.add(value);
            newValues.append(value).append(";");
        }
        newValues.setLength(newValues.length() - 1);
        return newValues.toString();
    }

    private IEntityTable getEntityTable(String entityID, String formSchemeKey, String dimName, List<String> value, String periodCode) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityID);
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        masterKeys.setValue(dimName, value);
        entityQuery.setMasterKeys(masterKeys);
        executorContext.setVarDimensionValueSet(masterKeys);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        executorContext.setPeriodView(formScheme.getDateTime());
        IEntityTable entityTable = null;
        try {
            entityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u5931\u8d25\uff01");
        }
        return entityTable;
    }
}

