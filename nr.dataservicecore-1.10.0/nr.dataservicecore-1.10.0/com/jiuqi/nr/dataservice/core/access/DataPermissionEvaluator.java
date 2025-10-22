/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionException;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;
import java.util.Set;

public interface DataPermissionEvaluator {
    public boolean haveAccess(DimensionCombination var1, String var2, AuthType var3) throws DataPermissionException;

    public boolean haveAccess(DimensionCombination var1, String var2, AuthType var3, Set<String> var4) throws DataPermissionException;

    public DataPermission haveAccess(DimensionCollection var1, Collection<String> var2, AuthType var3) throws DataPermissionException;

    public DataPermission haveAccess(DimensionCollection var1, Collection<String> var2, AuthType var3, Set<String> var4) throws DataPermissionException;

    public MergeDataPermission mergeAccess(DimensionCollection var1, Collection<String> var2, AuthType var3) throws DataPermissionException;

    public MergeDataPermission mergeAccess(DimensionCollection var1, Collection<String> var2, AuthType var3, Set<String> var4) throws DataPermissionException;
}

