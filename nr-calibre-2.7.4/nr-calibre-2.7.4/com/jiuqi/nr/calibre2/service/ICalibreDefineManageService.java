/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.calibre2.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.exception.CalibreDefineServiceException;
import com.jiuqi.nr.calibre2.exception.CalibreGroupServiceException;
import com.jiuqi.nr.calibre2.vo.ReferenceCalibreVO;
import com.jiuqi.nr.calibre2.vo.ReferenceCheckVO;
import java.util.List;

public interface ICalibreDefineManageService {
    public int[] moveCalibre(CalibreDefineDTO var1) throws Exception;

    public Boolean isSameCalibreCode(String var1) throws CalibreDefineServiceException;

    public Result<List<UpdateResult>> batchDeleteCalibreDefine(List<String> var1) throws CalibreDefineServiceException, JQException;

    public ReferenceCheckVO deleteCheck(ReferenceCalibreVO var1);

    public ReferenceCheckVO betchDeleteCheck(List<String> var1);

    public Result<List<UpdateResult>> copy(CalibreDefineDTO var1) throws CalibreGroupServiceException, JQException;
}

