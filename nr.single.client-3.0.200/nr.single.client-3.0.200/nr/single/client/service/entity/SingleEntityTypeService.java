/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.client.service.entity;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import nr.single.map.data.TaskDataContext;

public interface SingleEntityTypeService {
    public void getEntityType(TaskDataContext var1, String var2, Map<String, DimensionValue> var3);
}

