/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 */
package com.jiuqi.nr.fmdm.service;

import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.fmdm.domain.AbstractFMDMDataDO;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import java.util.List;

public interface IFMDMDataQueryService {
    public List<AbstractFMDMDataDO> list(QueryParamDTO var1);

    public EntityUpdateResult add(QueryParamDTO var1, FMDMModifyDTO var2) throws FMDMUpdateException;

    public EntityUpdateResult update(QueryParamDTO var1, FMDMModifyDTO var2) throws FMDMUpdateException;

    public EntityUpdateResult delete(QueryParamDTO var1) throws FMDMUpdateException;

    public EntityUpdateResult batchAdd(QueryParamDTO var1, List<FMDMModifyDTO> var2) throws FMDMUpdateException;

    public EntityUpdateResult batchUpdate(QueryParamDTO var1, List<FMDMModifyDTO> var2) throws FMDMUpdateException;
}

