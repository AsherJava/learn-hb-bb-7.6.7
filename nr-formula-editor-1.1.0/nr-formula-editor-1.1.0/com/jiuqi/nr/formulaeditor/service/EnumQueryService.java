/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.formulaeditor.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.formulaeditor.vo.EntityValueData;
import com.jiuqi.nr.formulaeditor.vo.EnumQueryParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EnumQueryService {
    private static final Logger logger = LoggerFactory.getLogger(EnumQueryService.class);
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public List<EntityValueData> queryEnumData(EnumQueryParam queryParam) {
        ArrayList<EntityValueData> result = new ArrayList<EntityValueData>();
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(queryParam.getEnumId()));
        iEntityQuery.sorted(true);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityTable entityTable = null;
        try {
            entityTable = iEntityQuery.executeReader((IContext)context);
        }
        catch (Exception e2) {
            logger.error("\u67e5\u8be2\u679a\u4e3e{}\u6570\u636e\u65f6\u5f02\u5e38", (Object)queryParam.getEnumId(), (Object)e2);
        }
        if (entityTable != null) {
            List rows = entityTable.getAllRows();
            if (StringUtils.hasText(queryParam.getKeyWords())) {
                String keyWords = queryParam.getKeyWords().toLowerCase(Locale.ROOT);
                rows = rows.stream().filter(e -> e.getTitle().toLowerCase(Locale.ROOT).contains(keyWords) || e.getCode().toLowerCase(Locale.ROOT).contains(keyWords)).collect(Collectors.toList());
            }
            if (queryParam.getPage() != null && queryParam.getPageSize() != null) {
                rows = EnumQueryService.paginate(rows, queryParam.getPage(), queryParam.getPageSize());
            }
            for (IEntityRow row : rows) {
                result.add(new EntityValueData(row.getEntityKeyData(), row.getCode(), row.getTitle()));
            }
        }
        return result;
    }

    public static <T> List<T> paginate(List<T> fullList, int pageNumber, int pageSize) {
        if (fullList == null || fullList.isEmpty()) {
            return Collections.emptyList();
        }
        int total = fullList.size();
        int fromIndex = Math.max(0, (pageNumber - 1) * pageSize);
        int toIndex = Math.min(fromIndex + pageSize, total);
        if (fromIndex >= total) {
            return Collections.emptyList();
        }
        return fullList.subList(fromIndex, toIndex);
    }
}

