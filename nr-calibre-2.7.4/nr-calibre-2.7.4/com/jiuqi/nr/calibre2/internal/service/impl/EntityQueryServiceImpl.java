/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.calibre2.internal.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.calibre2.domain.EntityQueryParam;
import com.jiuqi.nr.calibre2.exception.CalibreException;
import com.jiuqi.nr.calibre2.internal.service.IEntityQueryService;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.calibre2.vo.SelectedEntityVO;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EntityQueryServiceImpl
implements IEntityQueryService {
    private static final Logger log = LoggerFactory.getLogger(EntityQueryServiceImpl.class);
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    @Override
    public List<SelectedEntityVO> querySelectedEntity(CalibreDataVO dataVO) throws JQException {
        EntityQueryParam entityQueryParam = new EntityQueryParam();
        entityQueryParam.setEntityId(dataVO.getEntityId());
        Object selected = dataVO.getValue().getExpression();
        if (selected instanceof List) {
            List selectedList = (List)selected;
            entityQueryParam.setEntityKeys(selectedList);
        }
        return this.querySelectedEntity(entityQueryParam);
    }

    @Override
    public List<SelectedEntityVO> querySelectedEntity(EntityQueryParam entityQueryParam) throws JQException {
        IEntityTable entityTable;
        ArrayList<SelectedEntityVO> selectedEntityVOS = new ArrayList<SelectedEntityVO>();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityQueryParam.getEntityId());
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.sortedByQuery(false);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            entityTable = iEntityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)CalibreException.ENTITY_QUERY, "\u67e5\u8be2\u5355\u4f4d\u6570\u636e\u5f02\u5e38");
        }
        HashSet<String> entityKeys = new HashSet<String>(entityQueryParam.getEntityKeys());
        Map allRowsMap = entityTable.findByEntityKeys(entityKeys);
        Set keySet = allRowsMap.keySet();
        for (String key : keySet) {
            IEntityRow row = (IEntityRow)allRowsMap.get(key);
            SelectedEntityVO selectedEntity = new SelectedEntityVO();
            selectedEntity.setKey(row.getEntityKeyData());
            selectedEntity.setCode(row.getCode());
            selectedEntity.setTitle(row.getTitle());
            selectedEntity.setOrder((BigDecimal)row.getEntityOrder());
            selectedEntity.setParent(row.getParentEntityKey());
            if (entityQueryParam.isQueryParentName() && StringUtils.hasText(row.getParentEntityKey())) {
                IEntityRow parentRow = entityTable.quickFindByEntityKey(row.getParentEntityKey());
                selectedEntity.setParentName(parentRow.getTitle());
            }
            selectedEntityVOS.add(selectedEntity);
        }
        return selectedEntityVOS;
    }
}

