/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.runtime.dto.DataRegionDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupLinkDTO;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormParamCacheService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FormDTO {
    public static final FormDTO EMPTY_FORM = new FormDTO(null);
    private final FormDefine formDefine;
    private final List<DataRegionDTO> dataRegions;
    private final List<FormGroupLinkDTO> formGroupLinks;
    private final List<DataLinkMappingDefine> dataLinkMapping;
    private RunTimeBigDataTable formData;
    private RunTimeBigDataTable formGuide;
    private RunTimeBigDataTable formSurvey;
    private RunTimeBigDataTable formScript;
    private Map<NrFormParamCacheService.Point, DataLinkDefine> dataLinkPosMap;
    private Map<NrFormParamCacheService.Point, DataLinkDefine> dataLinkNumMap;
    private Map<String, DataLinkDefine> dataLinkCodeMap;
    private Map<String, List<DataLinkDefine>> dataLinkExpMap;

    public FormDTO(FormDefine formDefine) {
        this.formDefine = formDefine;
        this.dataRegions = new ArrayList<DataRegionDTO>();
        this.formGroupLinks = new ArrayList<FormGroupLinkDTO>();
        this.dataLinkMapping = new ArrayList<DataLinkMappingDefine>();
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public List<DataRegionDTO> getDataRegions() {
        return this.dataRegions;
    }

    public List<FormGroupLinkDTO> getFormGroupLinks() {
        return this.formGroupLinks;
    }

    public List<DataLinkMappingDefine> getDataLinkMapping() {
        return this.dataLinkMapping;
    }

    public void setFormData(RunTimeBigDataTable formData) {
        this.formData = formData;
    }

    public void setFormGuide(RunTimeBigDataTable formGuide) {
        this.formGuide = formGuide;
    }

    public void setFormSurvey(RunTimeBigDataTable formSurvey) {
        this.formSurvey = formSurvey;
    }

    public void setFormScript(RunTimeBigDataTable formScript) {
        this.formScript = formScript;
    }

    public RunTimeBigDataTable getFormData() {
        return this.formData;
    }

    public RunTimeBigDataTable getFormGuide() {
        return this.formGuide;
    }

    public RunTimeBigDataTable getFormSurvey() {
        return this.formSurvey;
    }

    public RunTimeBigDataTable getFormScript() {
        return this.formScript;
    }

    private synchronized void loadDataLink() {
        HashMap<NrFormParamCacheService.Point, DataLinkDefine> posMap = new HashMap<NrFormParamCacheService.Point, DataLinkDefine>();
        HashMap<NrFormParamCacheService.Point, DataLinkDefine> numMap = new HashMap<NrFormParamCacheService.Point, DataLinkDefine>();
        HashMap<String, DataLinkDefine> codeMap = new HashMap<String, DataLinkDefine>();
        HashMap<String, List<DataLinkDefine>> expMap = new HashMap<String, List<DataLinkDefine>>();
        for (DataRegionDTO dataRegion : this.dataRegions) {
            for (DataLinkDefine dataLinkDefine : dataRegion.getDataLinks()) {
                NrFormParamCacheService.Point point = NrFormParamCacheService.POINT_POOL.computeIfAbsent(new NrFormParamCacheService.Point(dataLinkDefine.getPosX(), dataLinkDefine.getPosY()), k -> k);
                posMap.put(point, dataLinkDefine);
                point = NrFormParamCacheService.POINT_POOL.computeIfAbsent(new NrFormParamCacheService.Point(dataLinkDefine.getColNum(), dataLinkDefine.getRowNum()), k -> k);
                numMap.put(point, dataLinkDefine);
                codeMap.put(dataLinkDefine.getUniqueCode(), dataLinkDefine);
                if (null == dataLinkDefine.getLinkExpression()) continue;
                expMap.computeIfAbsent(dataLinkDefine.getLinkExpression(), k -> new ArrayList()).add(dataLinkDefine);
            }
        }
        this.dataLinkPosMap = posMap;
        this.dataLinkNumMap = numMap;
        this.dataLinkCodeMap = codeMap;
        this.dataLinkExpMap = expMap;
    }

    public DataLinkDefine getDataLinkByPos(int x, int y) {
        if (this.dataLinkPosMap == null) {
            this.loadDataLink();
        }
        return this.dataLinkPosMap.get(new NrFormParamCacheService.Point(x, y));
    }

    public DataLinkDefine getDataLinkByNum(int col, int row) {
        if (this.dataLinkNumMap == null) {
            this.loadDataLink();
        }
        return this.dataLinkNumMap.get(new NrFormParamCacheService.Point(col, row));
    }

    public DataLinkDefine getDataLinkByCode(String code) {
        if (this.dataLinkCodeMap == null) {
            this.loadDataLink();
        }
        return this.dataLinkCodeMap.get(code);
    }

    public List<DataLinkDefine> getDataLinkByExp(String exp) {
        if (this.dataLinkExpMap == null) {
            this.loadDataLink();
        }
        return this.dataLinkExpMap.get(exp);
    }

    public Set<String> getDataLinkExp() {
        if (this.dataLinkExpMap == null) {
            this.loadDataLink();
        }
        return this.dataLinkExpMap.keySet();
    }
}

