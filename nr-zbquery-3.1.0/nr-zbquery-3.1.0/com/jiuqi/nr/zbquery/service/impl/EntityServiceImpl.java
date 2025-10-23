/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zbquery.rest.vo.OrgInfoVO;
import com.jiuqi.nr.zbquery.service.EntityService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EntityServiceImpl
implements EntityService {
    private Logger logger = LoggerFactory.getLogger(EntityServiceImpl.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    @Override
    public Map<String, List<String>> getUnitDirectChildren(List<OrgInfoVO> orgInfoVOList) throws Exception {
        this.logger.debug("getUnitDirectChildren.");
        LinkedHashMap<String, List<String>> unitChildrenMap = new LinkedHashMap<String, List<String>>();
        Map<String, List<OrgInfoVO>> orgInfoMap = orgInfoVOList.stream().collect(Collectors.groupingBy(OrgInfoVO::getPeriodValue));
        for (Map.Entry<String, List<OrgInfoVO>> entry : orgInfoMap.entrySet()) {
            List<OrgInfoVO> orgInfoVOs = entry.getValue();
            IEntityTable entityReader = this.getEntityReader(orgInfoVOs.get(0));
            orgInfoVOs.forEach(e -> {
                List childRows = entityReader.getChildRows(e.getOrgValue());
                ArrayList<String> childCodes = new ArrayList<String>();
                childCodes.add(e.getOrgValue());
                childCodes.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
                unitChildrenMap.put(e.getOrgValue(), childCodes);
            });
        }
        return unitChildrenMap;
    }

    @Override
    public Map<String, List<String>> getUnitDirectChildrenOnePeriod(OrgInfoVO orgInfoVO) throws Exception {
        LinkedHashMap<String, List<String>> unitChildrenMap = new LinkedHashMap<String, List<String>>();
        IEntityTable entityReader = this.getEntityReader(orgInfoVO);
        if (orgInfoVO.getOrgValues().size() > 0) {
            this.logger.debug("getUnitDirectChildrenOnePeriod:start{}", (Object)orgInfoVO.getOrgValues().stream().collect(Collectors.joining(",")));
            for (String orgValue : orgInfoVO.getOrgValues()) {
                List childRows = entityReader.getChildRows(orgValue);
                ArrayList<String> childCodes = new ArrayList<String>();
                childCodes.add(orgValue);
                childCodes.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
                unitChildrenMap.put(orgValue, childCodes);
            }
            this.logger.debug("getUnitDirectChildrenOnePeriod:end");
        } else if (StringUtils.hasText(orgInfoVO.getOrgValue())) {
            List childRows = entityReader.getChildRows(orgInfoVO.getOrgValue());
            ArrayList<String> childCodes = new ArrayList<String>();
            childCodes.add(orgInfoVO.getOrgValue());
            childCodes.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
            unitChildrenMap.put(orgInfoVO.getOrgValue(), childCodes);
        }
        return unitChildrenMap;
    }

    private IEntityTable getEntityReader(OrgInfoVO orgInfoVO) throws Exception {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(orgInfoVO.getDataSchemeCode());
        List periodDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.PERIOD);
        String periodDimKey = ((DataDimension)periodDimension.get(0)).getDimKey();
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(orgInfoVO.getEntityCode());
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityDefine.getId());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)orgInfoVO.getPeriodValue());
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(periodDimKey);
        return query.executeFullBuild((IContext)context);
    }
}

