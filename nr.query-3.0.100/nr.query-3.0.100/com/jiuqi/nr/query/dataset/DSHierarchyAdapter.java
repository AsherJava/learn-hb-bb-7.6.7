/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.query.dataset.QueryDSField;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DSHierarchyAdapter {
    public DSHierarchy getHierarchy(ExecutorContext executorContext, FieldDefine field, FieldDefine refField) throws ParseException {
        return null;
    }

    public DSHierarchy getHierarchy(NrQueryDSContext context, QueryDSField dsField) throws ParseException {
        DSHierarchy hier = null;
        ExecutorContext executorContext = context.getExecutorContext();
        TableRunInfo info = executorContext.getCache().getDataDefinitionsCache().getTableInfo(dsField.getEntityTableName());
        if (info != null) {
            hier = new DSHierarchy();
            List levels = hier.getLevels();
            hier.setName(dsField.getName() + "_" + dsField.getEntityTableName());
            hier.setTitle(info.getTableDefine().getTitle());
            levels.add(dsField.getKeyFieldName());
            hier.setType(DSHierarchyType.PARENT_HIERARCHY);
            hier.setParentFieldName(dsField.getParentFieldName());
        }
        return hier;
    }
}

