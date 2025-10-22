/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.GradeLink;
import com.jiuqi.nr.datacrud.IMetaData;
import java.util.List;
import java.util.Objects;

public class RegionGradeInfo {
    private String formulaSchemeKey;
    private boolean grade = true;
    private boolean queryDetails;
    private boolean querySummary;
    private boolean collapseTotal;
    private boolean hideSingleDetail;
    private List<GradeLink> gradeLinks;
    private List<Integer> gradeLevels;
    private List<Integer> precisions;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public boolean isGrade() {
        return this.grade;
    }

    public void setGrade(boolean grade) {
        this.grade = grade;
    }

    public boolean isQueryDetails() {
        return this.queryDetails;
    }

    public void setQueryDetails(boolean queryDetails) {
        this.queryDetails = queryDetails;
    }

    public boolean isQuerySummary() {
        return this.querySummary;
    }

    public void setQuerySummary(boolean querySummary) {
        this.querySummary = querySummary;
    }

    public boolean isCollapseTotal() {
        return this.collapseTotal;
    }

    public void setCollapseTotal(boolean collapseTotal) {
        this.collapseTotal = collapseTotal;
    }

    public boolean isHideSingleDetail() {
        return this.hideSingleDetail;
    }

    public void setHideSingleDetail(boolean hideSingleDetail) {
        this.hideSingleDetail = hideSingleDetail;
    }

    public List<GradeLink> getGradeLinks() {
        return this.gradeLinks;
    }

    public void setGradeLinks(List<GradeLink> gradeLinks) {
        this.gradeLinks = gradeLinks;
    }

    public List<Integer> getGradeLevels() {
        return this.gradeLevels;
    }

    public void setGradeLevels(List<Integer> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }

    public List<Integer> getPrecisions() {
        return this.precisions;
    }

    public void setPrecisions(List<Integer> precisions) {
        this.precisions = precisions;
    }

    public boolean isRemoveEnd0Link(IMetaData metaData) {
        if (this.isGrade() && metaData != null && this.getGradeLinks() != null) {
            for (GradeLink gradeLink : this.getGradeLinks()) {
                if (!gradeLink.isHideEnd0()) continue;
                return Objects.equals(gradeLink.getLinkKey(), metaData.getLinkKey()) || Objects.equals(gradeLink.getFieldKey(), metaData.getFieldKey());
            }
        }
        return false;
    }
}

