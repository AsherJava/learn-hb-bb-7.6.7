/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.utils.ZbSchemeConvert;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbInfoVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ZbInfoUtils {
    private static final Logger log = LoggerFactory.getLogger(ZbInfoUtils.class);
    private final IEntityMetaService entityMetaService;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final IEntityDataService entityDataService;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final Map<String, String> entityTitleMap;
    private final Map<String, List<IEntityRow>> rowMap;

    public ZbInfoUtils(IEntityMetaService entityMetaService, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IEntityDataService entityDataService, IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityMetaService = entityMetaService;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.entityDataService = entityDataService;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.entityTitleMap = new HashMap<String, String>();
        this.rowMap = new HashMap<String, List<IEntityRow>>();
    }

    public List<PropInfoVO> cvo(List<PropInfo> propList) {
        if (propList != null) {
            ArrayList<PropInfoVO> result = new ArrayList<PropInfoVO>(propList.size());
            for (PropInfo propInfo : propList) {
                PropInfoVO propInfoVO = ZbSchemeConvert.cvo(propInfo);
                if (propInfoVO == null) continue;
                if (StringUtils.hasLength(propInfoVO.getReferEntityId())) {
                    String entityTitle = this.getEntityTitle(propInfoVO.getReferEntityId());
                    propInfoVO.setReferEntityTitle(entityTitle);
                }
                if (propInfoVO.getValue() != null) {
                    switch (propInfo.getDataType()) {
                        case BOOLEAN: {
                            propInfoVO.setValueName(Boolean.TRUE.equals(propInfoVO.getValue()) ? "\u662f" : "\u5426");
                            break;
                        }
                        case STRING: {
                            List allRows;
                            if (this.rowMap.containsKey(propInfoVO.getReferEntityId())) {
                                allRows = this.rowMap.get(propInfoVO.getReferEntityId());
                            } else {
                                try {
                                    ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                                    EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(propInfoVO.getReferEntityId());
                                    IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                                    entityQuery.setEntityView(entityViewDefine);
                                    IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
                                    allRows = iEntityTable.getAllRows();
                                }
                                catch (Exception e) {
                                    log.error("\u83b7\u53d6\u5c5e\u6027\u503c\u540d\u79f0\u5931\u8d25", e);
                                    allRows = new ArrayList<IEntityRow>();
                                }
                            }
                            ArrayList<String> titles = new ArrayList<String>();
                            HashSet<String> codes = new HashSet<String>();
                            if (propInfoVO.isMultiple()) {
                                codes.addAll((List)propInfoVO.getValue());
                            } else {
                                codes.add((String)propInfoVO.getValue());
                            }
                            for (IEntityRow row : allRows) {
                                if (!codes.contains(row.getCode())) continue;
                                titles.add(row.getTitle());
                            }
                            propInfoVO.setValueName(Strings.join(titles, ';'));
                            break;
                        }
                        default: {
                            propInfoVO.setValueName(propInfoVO.getValue().toString());
                        }
                    }
                }
                result.add(propInfoVO);
            }
            return result;
        }
        return Collections.emptyList();
    }

    public ZbInfoVO cvo(ZbInfo dto) {
        ZbInfoVO infoVO = ZbSchemeConvert.cvo(dto);
        String refEntityId = infoVO.getRefEntityId();
        if (refEntityId != null) {
            String entityTitle = this.getEntityTitle(refEntityId);
            infoVO.setRefEntityTitle(entityTitle);
        }
        return infoVO;
    }

    public String getEntityTitle(String entityId) {
        if (this.entityTitleMap.containsKey(entityId)) {
            return this.entityTitleMap.get(entityId);
        }
        IEntityDefine queryEntity = this.entityMetaService.queryEntity(entityId);
        this.entityTitleMap.put(entityId, queryEntity.getTitle());
        return queryEntity.getTitle();
    }
}

