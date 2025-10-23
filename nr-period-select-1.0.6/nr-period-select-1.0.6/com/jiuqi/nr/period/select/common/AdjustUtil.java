/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO
 *  com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.period.select.common;

import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.period.select.page.Adjust;
import com.jiuqi.nr.period.select.vo.AdjustData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjustUtil {
    public static List<AdjustData> desTo(List<DesignAdjustPeriodDTO> list) {
        HashMap data = new HashMap();
        for (DesignAdjustPeriodDTO designAdjustPeriodDTO : list) {
            if (null != data.get(designAdjustPeriodDTO.getPeriod())) {
                ((List)data.get(designAdjustPeriodDTO.getPeriod())).add(AdjustUtil.to(designAdjustPeriodDTO));
                continue;
            }
            ArrayList<AdjustPeriod> adjustPeriodList = new ArrayList<AdjustPeriod>();
            data.put(designAdjustPeriodDTO.getPeriod(), adjustPeriodList);
            adjustPeriodList.add(AdjustUtil.to(designAdjustPeriodDTO));
        }
        ArrayList<AdjustData> adjustDataList = new ArrayList<AdjustData>();
        for (String code : data.keySet()) {
            AdjustData adjustData = new AdjustData();
            adjustData.setCode(code);
            adjustData.setAdjustList((List)data.get(code));
            adjustDataList.add(adjustData);
        }
        return adjustDataList;
    }

    public static List<AdjustData> to(List<AdjustPeriod> list) {
        HashMap data = new HashMap();
        for (AdjustPeriod adjustPeriod : list) {
            if (null != data.get(adjustPeriod.getPeriod())) {
                ((List)data.get(adjustPeriod.getPeriod())).add(adjustPeriod);
                continue;
            }
            ArrayList<AdjustPeriod> adjustPeriodList = new ArrayList<AdjustPeriod>();
            data.put(adjustPeriod.getPeriod(), adjustPeriodList);
            adjustPeriodList.add(adjustPeriod);
        }
        ArrayList<AdjustData> adjustDataList = new ArrayList<AdjustData>();
        for (String code : data.keySet()) {
            AdjustData adjustData = new AdjustData();
            adjustData.setCode(code);
            adjustData.setAdjustList((List)data.get(code));
            adjustDataList.add(adjustData);
        }
        return adjustDataList;
    }

    public static List<Adjust> toAdjust(List<AdjustPeriod> list) {
        ArrayList<Adjust> data = new ArrayList<Adjust>();
        for (AdjustPeriod adjustPeriod : list) {
            data.add(new Adjust(adjustPeriod.getCode(), adjustPeriod.getTitle(), adjustPeriod.getPeriod()));
        }
        return data;
    }

    public static List<Adjust> sortNoAdjust(List<Adjust> adjusts) {
        ArrayList<Adjust> noAdjust = new ArrayList<Adjust>();
        ArrayList<Adjust> dataAdjust = new ArrayList<Adjust>();
        for (Adjust adjustDatum : adjusts) {
            if ("0".equals(adjustDatum.getCode())) {
                noAdjust.add(adjustDatum);
                continue;
            }
            dataAdjust.add(adjustDatum);
        }
        noAdjust.addAll(dataAdjust);
        return noAdjust;
    }

    public static AdjustPeriod to(DesignAdjustPeriodDTO dto) {
        AdjustPeriodDO addo = new AdjustPeriodDO();
        addo.setCode(dto.getCode());
        addo.setOrder(dto.getOrder());
        addo.setPeriod(dto.getPeriod());
        addo.setTitle(dto.getTitle());
        addo.setDataSchemeKey(dto.getDataSchemeKey());
        return addo;
    }

    public static Map<String, List<AdjustPeriod>> toMap(List<AdjustData> adjustData) {
        HashMap<String, List<AdjustPeriod>> data = new HashMap<String, List<AdjustPeriod>>();
        for (AdjustData adjustDatum : adjustData) {
            data.put(adjustDatum.getCode(), adjustDatum.getAdjustList());
        }
        return data;
    }

    public static void calcNoAdjust(List<Adjust> adjustData) {
        boolean isSelect = false;
        for (Adjust adjust : adjustData) {
            if (!adjust.isSelected()) continue;
            isSelect = true;
        }
        if (!isSelect) {
            for (Adjust adjust : adjustData) {
                if (!adjust.getCode().equals("0")) continue;
                adjust.setSelected(true);
            }
        }
    }
}

