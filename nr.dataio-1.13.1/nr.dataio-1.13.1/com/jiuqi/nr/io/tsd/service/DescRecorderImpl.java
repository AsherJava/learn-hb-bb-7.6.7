/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.BloomFilter
 *  com.google.common.hash.Funnel
 *  com.google.common.hash.Funnels
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.data.common.service.DescRecorder
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 */
package com.jiuqi.nr.io.tsd.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.data.common.service.DescRecorder;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.io.record.bean.UnitFailureRecord;
import com.jiuqi.nr.io.record.bean.UnitFailureSubRecord;
import com.jiuqi.nr.io.record.service.UnitFailureService;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class DescRecorderImpl
implements DescRecorder {
    private final UnitFailureService unitFailureService;
    private final String factoryId;
    private final String recKey;
    private static final int EXPECTED_INSERTIONS = 100000;
    private static final double FPP = 0.05;
    private final Map<String, IEntityDataDTO> unitMap;
    private String periodValue;
    private String taskKey;
    private final List<UnitFailureRecord> records = new ArrayList<UnitFailureRecord>();
    private final List<UnitFailureSubRecord> unitFailureSubRecords = new ArrayList<UnitFailureSubRecord>();
    private final BloomFilter<String> bloomFilter;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public DescRecorderImpl(UnitFailureService unitFailureService, String recKey, String factoryId, String taskKey, String periodValue, List<IEntityDataDTO> importUnits) {
        this.unitFailureService = unitFailureService;
        this.factoryId = factoryId;
        this.recKey = recKey;
        this.periodValue = periodValue;
        this.taskKey = taskKey;
        this.unitMap = new HashMap<String, IEntityDataDTO>();
        for (IEntityDataDTO addUnit : importUnits) {
            this.unitMap.put(addUnit.getKey(), addUnit);
        }
        this.bloomFilter = BloomFilter.create((Funnel)Funnels.stringFunnel((Charset)Charset.defaultCharset()), (int)100000, (double)0.05);
    }

    public void addDesc(List<String> dw, String description) {
        for (String dwKey : dw) {
            this.addDesc(dwKey, description);
        }
    }

    public void addDesc(String dw, String description) {
        if (!this.bloomFilter.mightContain((Object)dw)) {
            this.bloomFilter.put((Object)dw);
            UnitFailureRecord dwRecorde = new UnitFailureRecord();
            dwRecorde.setKey(UUIDUtils.getKey());
            dwRecorde.setDwKey(dw);
            dwRecorde.setRecKey(this.recKey);
            dwRecorde.setFactoryId(this.factoryId);
            this.records.add(dwRecorde);
            if (this.records.size() == 1000) {
                for (UnitFailureRecord record : this.records) {
                    IEntityDataDTO byEntityKey = this.unitMap.get(record.getDwKey());
                    if (byEntityKey != null) {
                        record.setDwCode(byEntityKey.getCode());
                        record.setDwTitle(byEntityKey.getTitle());
                        record.setDwOrder(byEntityKey.getOrder());
                        continue;
                    }
                    record.setDwCode("00000000");
                    record.setDwTitle("\u672a\u627e\u5230\u8be5\u5355\u4f4d");
                    record.setDwOrder(BigDecimal.ZERO);
                }
                this.unitFailureService.saveFailureRecords(this.records);
                this.records.clear();
            }
        }
        UnitFailureSubRecord unitFailureSubRecord = new UnitFailureSubRecord();
        unitFailureSubRecord.setKey(UUIDUtils.getKey());
        unitFailureSubRecord.setRecKey(this.recKey);
        unitFailureSubRecord.setDwKey(dw);
        unitFailureSubRecord.setFactoryId(this.factoryId);
        unitFailureSubRecord.setDesc(description);
        this.unitFailureSubRecords.add(unitFailureSubRecord);
        if (this.unitFailureSubRecords.size() == 1000) {
            this.unitFailureService.saveFailureSubRecords(this.unitFailureSubRecords);
            this.unitFailureSubRecords.clear();
        }
    }

    public void addDesc(String dw, List<String> descriptions) {
        for (String description : descriptions) {
            this.addDesc(dw, description);
        }
    }

    public void flush() {
        if (!CollectionUtils.isEmpty(this.records)) {
            for (UnitFailureRecord record : this.records) {
                IEntityDataDTO iEntityDataDTO = this.unitMap.get(record.getDwKey());
                if (iEntityDataDTO != null) {
                    record.setDwCode(iEntityDataDTO.getCode());
                    record.setDwTitle(iEntityDataDTO.getTitle());
                    record.setDwOrder(iEntityDataDTO.getOrder());
                    continue;
                }
                record.setDwCode("00000000");
                record.setDwTitle("\u672a\u627e\u5230\u8be5\u5355\u4f4d");
                record.setDwOrder(BigDecimal.ZERO);
            }
            this.unitFailureService.saveFailureRecords(this.records);
            this.records.clear();
        }
        if (!CollectionUtils.isEmpty(this.unitFailureSubRecords)) {
            this.unitFailureService.saveFailureSubRecords(this.unitFailureSubRecords);
            this.unitFailureSubRecords.clear();
        }
    }
}

