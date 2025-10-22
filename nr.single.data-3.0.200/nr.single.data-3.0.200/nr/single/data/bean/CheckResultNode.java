/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.utils.StringUtils
 */
package nr.single.data.bean;

import com.jiuqi.nr.period.common.utils.StringUtils;
import java.io.Serializable;

public class CheckResultNode
implements Serializable {
    private static final long serialVersionUID = 8756160320815423878L;
    private String unitKey;
    private String unitCode;
    private String unitTitle;
    private String unitZdm;
    private String orgCode;
    private String qydmCode;
    private String bblxCode;
    private String sjdmCode;
    private String zbdmCode;
    private String parentKey;
    private String parentCode;
    private String parentTitle;
    private String parentZdm;
    private String parentOrgCode;
    private String parentQydm;
    private String parentBblx;
    private String parentSjdm;
    private String parentZbdm;
    private int errorType;
    private String errorMsg;
    private String errorDetail;
    private boolean isSjdmField = true;

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getUnitCode() {
        return StringUtils.isNotEmpty((String)this.unitCode) ? this.unitCode : "";
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getParentCode() {
        return StringUtils.isNotEmpty((String)this.parentCode) ? this.parentCode : "";
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getUnitTitle() {
        return StringUtils.isNotEmpty((String)this.unitTitle) ? this.unitTitle : "";
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnitZdm() {
        return StringUtils.isNotEmpty((String)this.unitZdm) ? this.unitZdm : "";
    }

    public void setUnitZdm(String unitZdm) {
        this.unitZdm = unitZdm;
    }

    public String getQydmCode() {
        return StringUtils.isNotEmpty((String)this.qydmCode) ? this.qydmCode : "";
    }

    public void setQydmCode(String qydmCode) {
        this.qydmCode = qydmCode;
    }

    public String getBblxCode() {
        return StringUtils.isNotEmpty((String)this.bblxCode) ? this.bblxCode : "";
    }

    public void setBblxCode(String bblxCode) {
        this.bblxCode = bblxCode;
    }

    public String getSjdmCode() {
        return StringUtils.isNotEmpty((String)this.sjdmCode) ? this.sjdmCode : "";
    }

    public void setSjdmCode(String sjdmCode) {
        this.sjdmCode = sjdmCode;
    }

    public String getZbdmCode() {
        return StringUtils.isNotEmpty((String)this.zbdmCode) ? this.zbdmCode : "";
    }

    public void setZbdmCode(String zbdmCode) {
        this.zbdmCode = zbdmCode;
    }

    public String getParentKey() {
        return StringUtils.isNotEmpty((String)this.parentKey) ? this.parentKey : "";
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getParentTitle() {
        return StringUtils.isNotEmpty((String)this.parentTitle) ? this.parentTitle : "";
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getParentZdm() {
        return StringUtils.isNotEmpty((String)this.parentZdm) ? this.parentZdm : "";
    }

    public void setParentZdm(String parentZdm) {
        this.parentZdm = parentZdm;
    }

    public String getParentQydm() {
        return StringUtils.isNotEmpty((String)this.parentQydm) ? this.parentQydm : "";
    }

    public void setParentQydm(String parentQydm) {
        this.parentQydm = parentQydm;
    }

    public String getParentBblx() {
        return StringUtils.isNotEmpty((String)this.parentBblx) ? this.parentBblx : "";
    }

    public void setParentBblx(String parentBblx) {
        this.parentBblx = parentBblx;
    }

    public String getParentSjdm() {
        return StringUtils.isNotEmpty((String)this.parentSjdm) ? this.parentSjdm : "";
    }

    public void setParentSjdm(String parentSjdm) {
        this.parentSjdm = parentSjdm;
    }

    public String getParentZbdm() {
        return StringUtils.isNotEmpty((String)this.parentZbdm) ? this.parentZbdm : "";
    }

    public void setParentZbdm(String parentZbdm) {
        this.parentZbdm = parentZbdm;
    }

    public int getErrorType() {
        return this.errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public boolean isSjdmField() {
        return this.isSjdmField;
    }

    public void setSjdmField(boolean isSjdmField) {
        this.isSjdmField = isSjdmField;
    }

    public String getOrgCode() {
        return StringUtils.isNotEmpty((String)this.orgCode) ? this.orgCode : "";
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getParentOrgCode() {
        return StringUtils.isNotEmpty((String)this.parentOrgCode) ? this.parentOrgCode : "";
    }

    public void setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
    }

    public String getParentFJDCode() {
        if (StringUtils.isNotEmpty((String)this.parentOrgCode)) {
            return this.parentOrgCode;
        }
        if (StringUtils.isNotEmpty((String)this.parentCode)) {
            return this.parentCode;
        }
        return "";
    }

    public void copyFrom(CheckResultNode node) {
        this.unitKey = node.getUnitKey();
        this.unitCode = node.getUnitCode();
        this.unitTitle = node.getUnitTitle();
        this.unitZdm = node.getUnitZdm();
        this.qydmCode = node.getQydmCode();
        this.bblxCode = node.getBblxCode();
        this.sjdmCode = node.getSjdmCode();
        this.zbdmCode = node.getZbdmCode();
        this.orgCode = node.getOrgCode();
        this.parentKey = node.getParentKey();
        this.parentCode = node.getParentCode();
        this.parentTitle = node.getParentTitle();
        this.parentZdm = node.getParentZdm();
        this.parentQydm = node.getParentQydm();
        this.parentBblx = node.getParentBblx();
        this.parentSjdm = node.getParentSjdm();
        this.parentZbdm = node.getParentZbdm();
        this.parentOrgCode = node.getParentOrgCode();
    }
}

