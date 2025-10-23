/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2;

import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.exception.CalibreGroupServiceException;
import java.util.List;

public interface ICalibreGroupService {
    public Result<List<CalibreGroupDTO>> list(CalibreGroupDTO var1);

    public Result<CalibreGroupDTO> get(String var1);

    public Result<UpdateResult> add(CalibreGroupDTO var1) throws CalibreGroupServiceException;

    public Result<UpdateResult> delete(CalibreGroupDTO var1) throws CalibreGroupServiceException;

    public Result<UpdateResult> update(CalibreGroupDTO var1) throws CalibreGroupServiceException;
}

