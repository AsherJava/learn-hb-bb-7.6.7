/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.web.result.OrgTreeNode;
import com.jiuqi.nr.multcheck2.web.result.ResultItemPMVO;
import com.jiuqi.nr.multcheck2.web.result.ResultItemTBVO;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleScheme {
    private String desc;
    private List<String> orgCodes;
    private List<ITree<OrgTreeNode>> orgTree;
    private List<ResultItemTBVO> itemTB;
    private Map<String, ResultItemPMVO> itemPM;
    private int failed;
    private ReportDimVO reportDimVO;
    private Map<String, DimSetDTO> orgDims;
    private Map<String, Map<String, DimSetDTO>> itemsOrgDims;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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

    public List<ITree<OrgTreeNode>> getOrgTree() {
        return this.orgTree;
    }

    public void setOrgTree(List<ITree<OrgTreeNode>> orgTree) {
        this.orgTree = orgTree;
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

    public int getFailed() {
        return this.failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public Map<String, DimSetDTO> getOrgDims() {
        return this.orgDims;
    }

    public void setOrgDims(Map<String, DimSetDTO> orgDims) {
        this.orgDims = orgDims;
    }

    public ReportDimVO getReportDimVO() {
        return this.reportDimVO;
    }

    public void setReportDimVO(ReportDimVO reportDimVO) {
        this.reportDimVO = reportDimVO;
    }

    public Map<String, Map<String, DimSetDTO>> getItemsOrgDims() {
        return this.itemsOrgDims;
    }

    public void setItemsOrgDims(Map<String, Map<String, DimSetDTO>> itemsOrgDims) {
        this.itemsOrgDims = itemsOrgDims;
    }
}

