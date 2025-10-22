/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 */
package com.jiuqi.nr.data.logic.common;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.logic.internal.obj.NvwaQueryPageInfo;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class CommonUtils {
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        ConcurrentHashMap seen = new ConcurrentHashMap();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void addVariables2Context(Map<String, Object> variableMap) {
        if (CollectionUtils.isEmpty(variableMap)) {
            return;
        }
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext.getVariables() == null) {
            if (dsContext instanceof DsContextImpl) {
                ((DsContextImpl)dsContext).setVariables(new ArrayList());
            } else {
                return;
            }
        }
        Set varNameSet = dsContext.getVariables().stream().map(Variable::getVarName).collect(Collectors.toSet());
        for (Map.Entry<String, Object> e : variableMap.entrySet()) {
            if (!varNameSet.add(e.getKey())) continue;
            dsContext.getVariables().add(new Variable(e.getKey(), null, 6, e.getValue()));
        }
    }

    public static String getSelectSql(ITempTable tempTable) {
        return "select TEMP_KEY from " + tempTable.getTableName();
    }

    public static String getExistsSelectSql(ITempTable tempTable, String relationCol) {
        return "(Select 1 From " + tempTable.getTableName() + " where " + "TEMP_KEY" + "=" + relationCol + ")";
    }

    @Nullable
    public static NvwaQueryPageInfo getNvwaQueryPageInfo(PagerInfo pagerInfo) {
        if (pagerInfo == null) {
            return null;
        }
        int offset = pagerInfo.getOffset();
        int limit = pagerInfo.getLimit();
        int start = offset * limit;
        int end = start + limit;
        if (start > -1 && end > -1) {
            return new NvwaQueryPageInfo(start, end);
        }
        return null;
    }
}

