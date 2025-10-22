/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.impl.dto;

import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.HashMap;
import java.util.Map;

public class ActuatorConfigDTO
implements ActuatorConfig {
    private int actuatorType = 2;
    private String destTable;
    private String destPeriod;
    private ImpMode impMode = ImpMode.FULL;
    private Map<String, String> field2DimMap = new HashMap<String, String>();
    private boolean specifySbId;
    private boolean batchByUnit;
    private boolean rowByDw;

    @Override
    public int getActuatorType() {
        return this.actuatorType;
    }

    @Override
    public String getDestTable() {
        return this.destTable;
    }

    @Override
    public String getDestPeriod() {
        return this.destPeriod;
    }

    @Override
    public ImpMode getMode() {
        return this.impMode;
    }

    @Override
    public Map<String, String> getField2DimMap() {
        return this.field2DimMap;
    }

    @Override
    public boolean isSpecifySbId() {
        return this.specifySbId;
    }

    public void setActuatorType(int actuatorType) {
        this.actuatorType = actuatorType;
    }

    public void setDestTable(String destTable) {
        this.destTable = destTable;
    }

    public void setDestPeriod(String destPeriod) {
        this.destPeriod = destPeriod;
    }

    public void setImpMode(ImpMode impMode) {
        this.impMode = impMode;
    }

    public void setField2DimMap(Map<String, String> field2DimMap) {
        this.field2DimMap = field2DimMap;
    }

    public void setSpecifySbid(boolean specifySbid) {
        this.specifySbId = specifySbid;
    }

    @Override
    public boolean isBatchByUnit() {
        return this.batchByUnit;
    }

    public void setBatchByUnit(boolean batchByUnit) {
        this.batchByUnit = batchByUnit;
    }

    @Override
    public boolean isRowByDw() {
        return this.rowByDw;
    }

    public void setRowByDw(boolean dwOrder) {
        this.rowByDw = dwOrder;
    }

    public static ActuatorConfigDTO copy(ActuatorConfig dto) {
        ActuatorConfigDTO cfg = new ActuatorConfigDTO();
        cfg.setActuatorType(dto.getActuatorType());
        cfg.setDestTable(dto.getDestTable());
        cfg.setDestPeriod(dto.getDestPeriod());
        cfg.setImpMode(dto.getMode());
        cfg.setField2DimMap(dto.getField2DimMap());
        cfg.setSpecifySbid(dto.isSpecifySbId());
        cfg.setBatchByUnit(dto.isBatchByUnit());
        cfg.setRowByDw(dto.isRowByDw());
        return cfg;
    }
}

