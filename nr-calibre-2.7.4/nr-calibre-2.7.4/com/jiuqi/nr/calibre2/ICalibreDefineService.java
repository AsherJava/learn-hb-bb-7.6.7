/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2;

import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.exception.CalibreDefineServiceException;
import com.jiuqi.nr.calibre2.exception.CalibreGroupServiceException;
import java.util.List;

public interface ICalibreDefineService {
    public Result<List<CalibreDefineDTO>> list(CalibreDefineDTO var1);

    public Result<CalibreDefineDTO> get(CalibreDefineDTO var1);

    public Result<List<CalibreDefineDTO>> getByRefer(CalibreDefineDTO var1);

    public Result<UpdateResult> add(CalibreDefineDTO var1) throws CalibreGroupServiceException;

    public Result<UpdateResult> delete(String var1) throws CalibreDefineServiceException;

    public Result<UpdateResult> update(CalibreDefineDTO var1) throws CalibreDefineServiceException;

    public Result<List<UpdateResult>> batchDelete(List<String> var1) throws CalibreDefineServiceException;
}

