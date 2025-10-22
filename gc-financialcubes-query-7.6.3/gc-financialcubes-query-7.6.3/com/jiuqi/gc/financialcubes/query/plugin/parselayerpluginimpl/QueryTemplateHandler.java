/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gc.financialcubes.query.plugin.parselayerpluginimpl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.enums.PenetrationType;
import com.jiuqi.gc.financialcubes.query.plugin.ParseLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationTaskUtils;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryTemplateHandler
implements ParseLayerPenetrationPlugin {
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private PenetrationTaskUtils penetrationTaskUtils;

    @Override
    public PenetrationType getType() {
        return PenetrationType.QUERY_TEMPLATE;
    }

    @Override
    public Map<String, String> handle(String linkMsg, PenetrationContextInfo context) {
        return this.preProcess(context, linkMsg);
    }

    private Map<String, String> preProcess(PenetrationContextInfo context, String linkMsg) {
        HashMap<String, String> msgMap = new HashMap<String, String>();
        Map linkmsgMap = JSONUtil.parseMap((String)linkMsg);
        String tableName = PenetrationValueUtil.getTableName(linkmsgMap);
        context.setDataSchemeTableCode(tableName);
        context.setDataSchemeKey(this.iRuntimeDataSchemeService.getDataTableByCode(tableName).getDataSchemeKey());
        String expression = this.iRuntimeDataSchemeService.getDataTableByCode(tableName).getExpression();
        if (expression == null) {
            throw new BusinessRuntimeException("\u76ee\u524d\u4e0d\u652f\u6301\u6b64\u60c5\u5f62\u7684\u7a7f\u900f\uff0c\u7f3a\u5c11orgtype");
        }
        for (Map.Entry entry : linkmsgMap.entrySet()) {
            ArrayList valueList;
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            String targetValue = "";
            if (key.endsWith("BIZKEYORDER") || key.endsWith("MESSAGE_NAME") || "SHOWTEXT".equals(key) || "CONTEXT".equals(key) || "CONDITIONTYPES".equals(key) || "ZBFULLNAMES".equals(key) || "ZBNAME".equals(key)) continue;
            if (key.startsWith(this.penetrationTaskUtils.getSchemeNameById(context.getDataSchemeKey()))) {
                key = key.substring(key.indexOf(46) + 1);
            }
            if (value instanceof ArrayList && !(valueList = (ArrayList)value).isEmpty()) {
                targetValue = String.join((CharSequence)",", (CharSequence[])valueList.stream().map(Object::toString).toArray(String[]::new));
            }
            msgMap.put(key, targetValue);
        }
        return msgMap;
    }
}

