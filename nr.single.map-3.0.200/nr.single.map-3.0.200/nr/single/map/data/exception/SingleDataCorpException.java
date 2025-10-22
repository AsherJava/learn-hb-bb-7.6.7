/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.exception;

public class SingleDataCorpException
extends Exception {
    private String entityCode;
    private String formCode;
    private String fieldCode;
    private static final long serialVersionUID = 1L;

    public SingleDataCorpException() {
    }

    public SingleDataCorpException(String message) {
        super(message);
    }

    public SingleDataCorpException(Throwable cause) {
        super(cause);
    }

    public SingleDataCorpException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public void setFieldInfo(String entityCode, String formCode, String fieldCode) {
        this.entityCode = entityCode;
        this.formCode = formCode;
        this.fieldCode = fieldCode;
    }
}

