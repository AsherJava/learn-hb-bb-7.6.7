/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.transfer.service.TransferService
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.gcreport.clbr.service.ClbrTranferService;
import com.jiuqi.gcreport.transfer.service.TransferService;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClbrTranferServiceImpl
implements ClbrTranferService {
    private static final Set<String> sysFields = new HashSet<String>();
    @Autowired
    private TransferService transferService;

    @Override
    public List<TransferColumnVo> getGenerateNotConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRTIME");
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getGenerateConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getProcessNotConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRTIME");
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("REMARK");
        excludeFields.add("REJECTIONMESSAGE");
        excludeFields.add("ARBITRATIONUSERNAME");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getProcessConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("OPPCLBRTYPE");
        excludeFields.add("VERIFYEDAMOUNT");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("REMARK");
        excludeFields.add("REJECTIONMESSAGE");
        excludeFields.add("ARBITRATIONUSERNAME");
        List<TransferColumnVo> finalFields = this.filterFields(excludeFields);
        for (TransferColumnVo transferVo : finalFields) {
            if (!"CLBRTYPE".equals(transferVo.getKey())) continue;
            transferVo.setLabel("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b");
        }
        TransferColumnVo transferVo = new TransferColumnVo();
        transferVo.setAlign("right");
        transferVo.setDefaultWidth(Integer.valueOf(100));
        transferVo.setKey("CLBRAMOUNT");
        transferVo.setLabel("\u786e\u8ba4\u91d1\u989d");
        transferVo.setColumnType(ColumnModelType.BIGDECIMAL.name());
        finalFields.add(transferVo);
        return finalFields;
    }

    @Override
    public List<TransferColumnVo> getProcessInitiatorNotConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("OPPCLBRTYPE");
        excludeFields.add("VERIFYEDAMOUNT");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("REMARK");
        excludeFields.add("REJECTIONMESSAGE");
        excludeFields.add("ARBITRATIONUSERNAME");
        List<TransferColumnVo> finalFields = this.filterFields(excludeFields);
        for (TransferColumnVo transferVo : finalFields) {
            if (!"CLBRTYPE".equals(transferVo.getKey())) continue;
            transferVo.setLabel("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b");
        }
        TransferColumnVo transferVo = new TransferColumnVo();
        transferVo.setAlign("right");
        transferVo.setDefaultWidth(Integer.valueOf(100));
        transferVo.setKey("CLBRAMOUNT");
        transferVo.setLabel("\u786e\u8ba4\u91d1\u989d");
        transferVo.setColumnType(ColumnModelType.BIGDECIMAL.name());
        finalFields.add(transferVo);
        return finalFields;
    }

    @Override
    public List<TransferColumnVo> getProcessReceiverNotConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("OPPCLBRTYPE");
        excludeFields.add("VERIFYEDAMOUNT");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("REMARK");
        excludeFields.add("REJECTIONMESSAGE");
        excludeFields.add("ARBITRATIONUSERNAME");
        List<TransferColumnVo> finalFields = this.filterFields(excludeFields);
        for (TransferColumnVo transferVo : finalFields) {
            if (!"CLBRTYPE".equals(transferVo.getKey())) continue;
            transferVo.setLabel("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b");
        }
        TransferColumnVo transferVo = new TransferColumnVo();
        transferVo.setAlign("right");
        transferVo.setDefaultWidth(Integer.valueOf(100));
        transferVo.setKey("CLBRAMOUNT");
        transferVo.setLabel("\u786e\u8ba4\u91d1\u989d");
        transferVo.setColumnType(ColumnModelType.BIGDECIMAL.name());
        finalFields.add(transferVo);
        return finalFields;
    }

    @Override
    public List<TransferColumnVo> getDataQueryTotalAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("CONFIRMTYPE");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getDataQueryPartConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("CONFIRMTYPE");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getDataQueryConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("VERIFYEDAMOUNT");
        excludeFields.add("NOVERIFYAMOUNT");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getDataQueryNotConfirmAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("VERIFYEDAMOUNT");
        excludeFields.add("NOVERIFYAMOUNT");
        excludeFields.add("CLBRCODE");
        excludeFields.add("CLBRTIME");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getDataQueryRejectAllField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("CONFIRMTYPE");
        excludeFields.add("VERIFYEDAMOUNT");
        excludeFields.add("NOVERIFYAMOUNT");
        excludeFields.add("CLBRCODE");
        excludeFields.add("CLBRTIME");
        return this.filterFields(excludeFields);
    }

    @Override
    public List<TransferColumnVo> getDataQueryArbitrationField() {
        HashSet<String> excludeFields = new HashSet<String>(sysFields);
        excludeFields.add("CLBRBILLTYPE");
        excludeFields.add("REJECTTIME");
        excludeFields.add("REJECTUSERNAME");
        return this.filterFields(excludeFields);
    }

    private List<TransferColumnVo> filterFields(Set<String> excludeFields) {
        List allFields = this.transferService.getAllField("GC_CLBR_BILL");
        ArrayList<TransferColumnVo> filterFields = new ArrayList<TransferColumnVo>();
        for (TransferColumnVo transferColumnVo : allFields) {
            if (excludeFields.contains(transferColumnVo.getKey())) continue;
            filterFields.add(transferColumnVo);
        }
        return this.transferVoConvert(filterFields);
    }

    private List<TransferColumnVo> transferVoConvert(List<TransferColumnVo> transferColumnVos) {
        return transferColumnVos.stream().map(transferColumnVo -> {
            transferColumnVo.setDefaultWidth(Integer.valueOf(200));
            transferColumnVo.setAlign("left");
            if (ColumnModelType.BIGDECIMAL.name().equals(transferColumnVo.getColumnType())) {
                transferColumnVo.setAlign("right");
                transferColumnVo.setDefaultWidth(Integer.valueOf(100));
            }
            return transferColumnVo;
        }).collect(Collectors.toList());
    }

    static {
        sysFields.add("CLBRSCHEMEID");
        sysFields.add("CURRENCY");
        sysFields.add("FLOWCONTROLTYPE");
        sysFields.add("ID");
        sysFields.add("MODIFYTIME");
        sysFields.add("NEXTUSERNAME");
        sysFields.add("OPPORGCODE");
        sysFields.add("OPPUSERNAME");
        sysFields.add("ORGCODE");
        sysFields.add("RECVER");
        sysFields.add("SN");
        sysFields.add("SRCID");
        sysFields.add("SYSCODE");
        sysFields.add("USERTITLE");
        sysFields.add("VCHRCONTROLTYPE");
        sysFields.add("USERNAME");
        sysFields.add("BILLSTATE");
        sysFields.add("OPPSRCID");
        sysFields.add("OPPCLBRBILLCODE");
    }
}

