/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2;

import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.BatchCalibreDataOptionsDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import java.util.List;

public interface ICalibreDataService {
    public Result<List<CalibreDataDTO>> list(CalibreDataDTO var1);

    public Result<CalibreDataDTO> get(CalibreDataDTO var1);

    public Result<UpdateResult> add(CalibreDataDTO var1);

    public Result<UpdateResult> delete(CalibreDataDTO var1);

    public Result<UpdateResult> update(CalibreDataDTO var1);

    public Result<List<UpdateResult>> batchAdd(BatchCalibreDataOptionsDTO var1);

    public Result<List<UpdateResult>> batchUpdate(BatchCalibreDataOptionsDTO var1);

    public Result<List<UpdateResult>> batchDelete(BatchCalibreDataOptionsDTO var1, Boolean var2);

    public Result<List<UpdateResult>> batchChangeOrder(BatchCalibreDataOptionsDTO var1);

    public Result<List<CalibreDataDO>> listAll(String var1);
}

