/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package nr.midstore2.design.vo;

import com.jiuqi.nr.common.itree.INode;
import java.util.List;
import nr.midstore2.design.vo.LabelVO;

public class TaskTreeNodeVO
implements INode {
    private String key;
    private String code;
    private String title;
    private String type;
    private String dataScheme;
    private String periodType;
    private String periodEntity;
    private List<LabelVO> orgLinks;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(String periodEntity) {
        this.periodEntity = periodEntity;
    }

    public List<LabelVO> getOrgLinks() {
        return this.orgLinks;
    }

    public void setOrgLinks(List<LabelVO> orgLinks) {
        this.orgLinks = orgLinks;
    }
}

