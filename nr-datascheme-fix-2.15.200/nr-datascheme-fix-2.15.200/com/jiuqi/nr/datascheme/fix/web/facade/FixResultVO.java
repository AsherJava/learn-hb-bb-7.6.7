/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

import com.jiuqi.nr.datascheme.fix.core.DeployFailFixHelper;
import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixItem;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.util.StringUtil;

public class FixResultVO
extends FixItem {
    private String exDesc;
    private String fixScheme;
    private String ptName;
    private String newTableName;
    private String fixReultDesc;
    private int fixResultValue;
    private Instant fixTime;
    private String selectedTime;
    private boolean transferData;

    public FixResultVO() {
    }

    public FixResultVO(DeployFixResultDTO fixResult) {
        this.dataTableCode = fixResult.getDataTableCode();
        this.dataTableTitle = fixResult.getDataTableTitle();
        this.exDesc = fixResult.getExType().getTitle();
        this.fixScheme = fixResult.getFixType().getTitle();
        this.newTableName = fixResult.getNewTableName() != null && fixResult.getNewTableName().size() != 0 ? StringUtil.join(fixResult.getNewTableName().toArray(), "\uff0c") : null;
        this.fixReultDesc = fixResult.getFixResultType().getTitle();
        this.fixResultValue = fixResult.getFixResultType().getValue();
        this.transferData = fixResult.isTransfer();
        ArrayList<String> ptNames = new ArrayList<String>();
        if (fixResult.getTmKeyAndLtName().keySet().size() != 0) {
            for (String tmKey : fixResult.getTmKeyAndLtName().keySet()) {
                ptNames.add(fixResult.getTmKeyAndLtName().get(tmKey));
            }
            this.ptName = StringUtil.join(ptNames.toArray(), "\uff0c");
        } else {
            this.ptName = null;
        }
    }

    public FixResultVO(DeployFailFixLogDO failFixLogDO) {
        this.dataTableKey = failFixLogDO.getDataTableKey();
        this.exDesc = DeployFailFixHelper.getexDesxc(failFixLogDO.getExType());
        this.fixScheme = DeployFailFixHelper.getFixSchemeDesc(failFixLogDO.getFixScheme());
        this.fixReultDesc = failFixLogDO.getFixResult();
        this.newTableName = failFixLogDO.getNewTableName() != null && failFixLogDO.getNewTableName().length != 0 ? StringUtil.join(failFixLogDO.getNewTableName(), "\uff0c") : null;
        this.fixTime = failFixLogDO.getDeployFailFixTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = Date.from(failFixLogDO.getDeployFailFixTime());
        this.selectedTime = simpleDateFormat.format(time);
        this.transferData = failFixLogDO.isTransfer();
    }

    public String getExDesc() {
        return this.exDesc;
    }

    public void setExDesc(String exDesc) {
        this.exDesc = exDesc;
    }

    public String getFixScheme() {
        return this.fixScheme;
    }

    public void setFixScheme(String fixScheme) {
        this.fixScheme = fixScheme;
    }

    public String getFixReultDesc() {
        return this.fixReultDesc;
    }

    public void setFixReultDesc(String fixReultDesc) {
        this.fixReultDesc = fixReultDesc;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getPtName() {
        return this.ptName;
    }

    public int getFixResultValue() {
        return this.fixResultValue;
    }

    public void setFixReultValue(int fixReultValue) {
        this.fixResultValue = fixReultValue;
    }

    public Instant getFixTime() {
        return this.fixTime;
    }

    public void setFixTime(Instant fixTime) {
        this.fixTime = fixTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public void setFixResultValue(int fixResultValue) {
        this.fixResultValue = fixResultValue;
    }

    public String getSelectedTime() {
        return this.selectedTime;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    @Override
    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public boolean isTransferData() {
        return this.transferData;
    }

    public void setTransferData(boolean transferData) {
        this.transferData = transferData;
    }

    public String getNewTableName() {
        return this.newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }
}

