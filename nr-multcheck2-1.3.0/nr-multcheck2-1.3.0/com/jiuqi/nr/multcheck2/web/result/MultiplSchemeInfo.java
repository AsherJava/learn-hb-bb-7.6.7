/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.web.result.ResultItemTBVO;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiplSchemeInfo {
    private String title;
    private int success;
    private int failed;
    private int itemSuccess;
    private int itemSuccessHasError;
    private int itemFailed;
    private int itemWarn;
    private String time;
    private List<String> orgCodes;
    private List<ResultItemTBVO> schemeItemState;
    private ReportDimVO reportDimVO;
    private Map<String, DimSetDTO> orgDims;
    private Map<String, Map<String, DimSetDTO>> itemsOrgDims;

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void addOrg(String org) {
        if (this.orgCodes == null) {
            this.orgCodes = new ArrayList<String>();
        }
        this.orgCodes.add(org);
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailed() {
        return this.failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getItemSuccess() {
        return this.itemSuccess;
    }

    public void setItemSuccess(int itemSuccess) {
        this.itemSuccess = itemSuccess;
    }

    public int getItemSuccessHasError() {
        return this.itemSuccessHasError;
    }

    public void setItemSuccessHasError(int itemSuccessHasError) {
        this.itemSuccessHasError = itemSuccessHasError;
    }

    public int getItemFailed() {
        return this.itemFailed;
    }

    public void setItemFailed(int itemFailed) {
        this.itemFailed = itemFailed;
    }

    public int getItemWarn() {
        return this.itemWarn;
    }

    public void setItemWarn(int itemWarn) {
        this.itemWarn = itemWarn;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ResultItemTBVO> getSchemeItemState() {
        return this.schemeItemState;
    }

    public void setSchemeItemState(List<ResultItemTBVO> schemeItemState) {
        this.schemeItemState = schemeItemState;
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

