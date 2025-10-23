/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.web.result.ResultItemPMVO;
import com.jiuqi.nr.multcheck2.web.result.ResultItemTBVO;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import java.util.List;
import java.util.Map;

public class SingleOrg {
    private String orgCode;
    private boolean checkRes;
    private String desc;
    private List<ResultItemTBVO> itemTB;
    private Map<String, ResultItemPMVO> itemPM;
    private Map<String, FailedOrgInfo> failed;
    private List<String> ignore;
    private List<String> successWithExplain;
    private ReportDimVO reportDimVO;
    private Map<String, DimSetDTO> orgDims;
    private Map<String, Map<String, DimSetDTO>> itemsOrgDims;

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public boolean isCheckRes() {
        return this.checkRes;
    }

    public void setCheckRes(boolean checkRes) {
        this.checkRes = checkRes;
    }

    public Map<String, FailedOrgInfo> getFailed() {
        return this.failed;
    }

    public void setFailed(Map<String, FailedOrgInfo> failed) {
        this.failed = failed;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ResultItemTBVO> getItemTB() {
        return this.itemTB;
    }

    public void setItemTB(List<ResultItemTBVO> itemTB) {
        this.itemTB = itemTB;
    }

    public Map<String, ResultItemPMVO> getItemPM() {
        return this.itemPM;
    }

    public void setItemPM(Map<String, ResultItemPMVO> itemPM) {
        this.itemPM = itemPM;
    }

    public List<String> getIgnore() {
        return this.ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }

    public List<String> getSuccessWithExplain() {
        return this.successWithExplain;
    }

    public void setSuccessWithExplain(List<String> successWithExplain) {
        this.successWithExplain = successWithExplain;
    }

    public ReportDimVO getReportDimVO() {
        return this.reportDimVO;
    }

    public void setReportDimVO(ReportDimVO reportDimVO) {
        this.reportDimVO = reportDimVO;
    }

    public Map<String, DimSetDTO> getOrgDims() {
        return this.orgDims;
    }

    public void setOrgDims(Map<String, DimSetDTO> orgDims) {
        this.orgDims = orgDims;
    }

    public Map<String, Map<String, DimSetDTO>> getItemsOrgDims() {
        return this.itemsOrgDims;
    }

    public void setItemsOrgDims(Map<String, Map<String, DimSetDTO>> itemsOrgDims) {
        this.itemsOrgDims = itemsOrgDims;
    }
}

