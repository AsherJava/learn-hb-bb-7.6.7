/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data;

public class SingleDataError {
    private String formName;
    private String formCode;
    private String formKey;
    private String companyName;
    private String companyKey;
    private String errorInfo;
    private String errorCode;
    private String companyCode;
    private String singleCode;

    public SingleDataError() {
    }

    public SingleDataError(String formName, String formCode, String errorInfo, String errorCode, String companyKey, String companyName, String companyCode) {
        this.formName = formName;
        this.formCode = formCode;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.companyKey = companyKey;
        this.companyName = companyName;
        this.companyCode = companyCode;
    }

    public SingleDataError(String formName, String formCode, String errorInfo, String errorCode, String companyKey, String companyName, String companyCode, String singleCode) {
        this.formName = formName;
        this.formCode = formCode;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.companyKey = companyKey;
        this.companyName = companyName;
        this.singleCode = singleCode;
        this.setCompanyCode(companyCode);
    }

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCompanyKey() {
        return this.companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSingleCode() {
        return this.singleCode;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

