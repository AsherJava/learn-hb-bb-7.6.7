/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import java.util.List;

public interface ClbrTranferService {
    public List<TransferColumnVo> getGenerateNotConfirmAllField();

    public List<TransferColumnVo> getGenerateConfirmAllField();

    public List<TransferColumnVo> getProcessNotConfirmAllField();

    public List<TransferColumnVo> getProcessConfirmAllField();

    public List<TransferColumnVo> getProcessInitiatorNotConfirmAllField();

    public List<TransferColumnVo> getProcessReceiverNotConfirmAllField();

    public List<TransferColumnVo> getDataQueryTotalAllField();

    public List<TransferColumnVo> getDataQueryPartConfirmAllField();

    public List<TransferColumnVo> getDataQueryConfirmAllField();

    public List<TransferColumnVo> getDataQueryNotConfirmAllField();

    public List<TransferColumnVo> getDataQueryRejectAllField();

    public List<TransferColumnVo> getDataQueryArbitrationField();
}

