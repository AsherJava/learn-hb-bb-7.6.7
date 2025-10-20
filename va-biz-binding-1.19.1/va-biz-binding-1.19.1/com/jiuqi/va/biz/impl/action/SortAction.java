/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.utils.ListUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SortAction
extends ActionBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SortAction.class);

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public String getTitle() {
        return "\u6392\u5e8f";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public boolean isInner() {
        return true;
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        String tableName = (String)params.get("tableName");
        if (!StringUtils.hasText(tableName)) {
            return;
        }
        List sortInfos = (List)params.get("sortInfos");
        if (CollectionUtils.isEmpty(sortInfos)) {
            return;
        }
        DataImpl data = model.getPlugins().get(DataImpl.class);
        DataTableImpl table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(tableName);
        HashMap nullMaxMap = new HashMap();
        table.sort((o1, o2) -> {
            int ret = 0;
            try {
                for (Map sortInfo : sortInfos) {
                    String columnName = (String)sortInfo.get("columnName");
                    nullMaxMap.putIfAbsent(columnName, false);
                    ret = ListUtils.compareObject(columnName, "asc".equals(sortInfo.get("sort")), (Boolean)nullMaxMap.get(columnName), o1.getData(), o2.getData());
                    if (0 == ret) continue;
                    break;
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            return ret;
        });
    }
}

