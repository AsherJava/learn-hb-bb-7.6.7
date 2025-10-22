/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.service;

import com.jiuqi.nr.data.logic.facade.param.input.BatchDelCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.BatchSaveCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CKDTransferPar;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CKDSaveResult;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import java.util.List;

public interface ICheckErrorDescriptionService {
    public List<CheckDesObj> queryFormulaCheckDes(CheckDesQueryParam var1);

    public void batchSaveFormulaCheckDes(CheckDesBatchSaveObj var1);

    @Deprecated
    public void batchSaveFormulaCheckDes(BatchSaveCheckDesParam var1);

    public CKDSaveResult batchSaveCKD(BatchSaveCheckDesParam var1);

    @Deprecated
    public CheckDesObj saveFormulaCheckDes(CheckDesObj var1);

    public CKDSaveResult saveCKD(CheckDesObj var1);

    public void removeFormulaCheckDes(CheckDesObj var1);

    public void batchDelCheckDes(BatchDelCheckDesParam var1);

    public DesCheckResult desCheckResult(CheckResultQueryParam var1);

    public List<FormulaData> queryCheckResultFormulas(BatchSaveCheckDesParam var1);

    public void transferCheckDes(CKDTransferPar var1) throws Exception;

    public void reviseCheckDesKey();

    public void checkDes(CheckDesParam var1);
}

