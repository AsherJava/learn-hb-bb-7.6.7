/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.FrontEndParams
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO
 *  com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO
 *  com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.gcreport.org.impl.io.dto;

import com.jiuqi.gcreport.org.api.vo.FrontEndParams;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.ExportMessageVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadContext {
    private ExportConditionVO conditionVO;
    private Map<String, OrgFiledComponentVO> filedComponentMap;
    private Map<String, BaseDataDO> baseDataCache;
    private Map<String, OrgToJsonVO> orgDataCache;
    private List<ExportMessageVO> resultList = new ArrayList<ExportMessageVO>();
    private boolean enableTransformOrgField = false;
    private boolean autoCalcField = false;
    private List<OrgToJsonVO> secondUploadData;
    private long processStartTime = 0L;
    private FrontEndParams frontEnd;

    public long getProcessStartTime() {
        return this.processStartTime;
    }

    public void setProcessStartTime(long processStartTime) {
        this.processStartTime = processStartTime;
    }

    public ExportConditionVO getConditionVO() {
        return this.conditionVO;
    }

    public void setConditionVO(ExportConditionVO conditionVO) {
        this.conditionVO = conditionVO;
    }

    public Map<String, OrgFiledComponentVO> getFiledComponentMap() {
        return this.filedComponentMap;
    }

    public void setFiledComponentMap(Map<String, OrgFiledComponentVO> filedComponentMap) {
        this.filedComponentMap = filedComponentMap;
    }

    public Map<String, BaseDataDO> getBaseDataCache() {
        return this.baseDataCache;
    }

    public void setBaseDataCache(Map<String, BaseDataDO> baseDataCache) {
        this.baseDataCache = baseDataCache;
    }

    public List<ExportMessageVO> getResultList() {
        return this.resultList;
    }

    public void setResultList(List<ExportMessageVO> resultList) {
        this.resultList = resultList;
    }

    public boolean isEnableTransformOrgField() {
        return this.enableTransformOrgField;
    }

    public void setEnableTransformOrgField(boolean enableTransformOrgField) {
        this.enableTransformOrgField = enableTransformOrgField;
    }

    public Map<String, OrgToJsonVO> getOrgDataCache() {
        return this.orgDataCache;
    }

    public void setOrgDataCache(Map<String, OrgToJsonVO> orgDataCache) {
        this.orgDataCache = orgDataCache;
    }

    public List<OrgToJsonVO> getSecondUploadData() {
        return this.secondUploadData;
    }

    public void setSecondUploadData(List<OrgToJsonVO> secondUploadData) {
        this.secondUploadData = secondUploadData;
    }

    public FrontEndParams getFrontEnd() {
        return this.frontEnd;
    }

    public void setFrontEnd(FrontEndParams frontEnd) {
        this.frontEnd = frontEnd;
    }

    public boolean isAutoCalcField() {
        return this.autoCalcField;
    }

    public void setAutoCalcField(boolean autoCalcField) {
        this.autoCalcField = autoCalcField;
    }
}

