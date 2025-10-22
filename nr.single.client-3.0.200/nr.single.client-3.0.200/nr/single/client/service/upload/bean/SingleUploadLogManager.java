/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.JIOFormImportResult
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package nr.single.client.service.upload.bean;

import com.jiuqi.nr.dataentry.bean.JIOFormImportResult;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.HashMap;
import java.util.Map;

public class SingleUploadLogManager {
    private Map<String, JIOUnitImportResult> successUnits;
    private Map<String, JIOUnitImportResult> errorUnits;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addSuccessForm(FormDefine formDefine, String netEntityKey) {
        SingleUploadLogManager singleUploadLogManager = this;
        synchronized (singleUploadLogManager) {
            Map formResults;
            JIOFormImportResult formImportResult;
            Map formMapResults;
            JIOFormImportResult errorFormResult;
            JIOUnitImportResult errorUnitImportResult = this.getErrorUnits().get(netEntityKey);
            if (null != errorUnitImportResult && null != (errorFormResult = (JIOFormImportResult)(formMapResults = errorUnitImportResult.getFormMapResults()).get(formDefine.getKey()))) {
                return;
            }
            JIOUnitImportResult successUnitImportResult = this.getSuccessUnits().get(netEntityKey);
            if (null == successUnitImportResult) {
                successUnitImportResult = new JIOUnitImportResult();
                successUnitImportResult.setUnitKey(netEntityKey);
                this.getSuccessUnits().put(netEntityKey, successUnitImportResult);
            }
            if (null == (formImportResult = (JIOFormImportResult)(formResults = successUnitImportResult.getFormMapResults()).get(formDefine.getKey()))) {
                formImportResult = new JIOFormImportResult();
                formImportResult.setFormCode(formDefine.getFormCode());
                formImportResult.setFormKey(formDefine.getKey());
                formImportResult.setFormTitle(formDefine.getTitle());
                formResults.put(formDefine.getKey(), formImportResult);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addErrorForm(FormDefine formDefine, String netEntityKey, FormReadWriteAccessData result, String message) {
        SingleUploadLogManager singleUploadLogManager = this;
        synchronized (singleUploadLogManager) {
            Map successformResults;
            JIOUnitImportResult successUnitImportResult;
            Map formResults;
            JIOFormImportResult errorForm;
            JIOUnitImportResult errorUnitImportResult = this.getErrorUnits().get(netEntityKey);
            if (null == errorUnitImportResult) {
                errorUnitImportResult = new JIOUnitImportResult();
                errorUnitImportResult.setUnitKey(netEntityKey);
                this.getErrorUnits().put(netEntityKey, errorUnitImportResult);
            }
            if (null == (errorForm = (JIOFormImportResult)(formResults = errorUnitImportResult.getFormMapResults()).get(formDefine.getKey()))) {
                errorForm = new JIOFormImportResult();
                errorForm.setFormCode(formDefine.getFormCode());
                errorForm.setFormKey(formDefine.getKey());
                errorForm.setFormTitle(formDefine.getTitle());
                if (null != result) {
                    errorForm.setMessage(result.getOneFormKeyReason(formDefine.getKey()));
                } else {
                    errorForm.setMessage(message);
                }
                formResults.put(formDefine.getKey(), errorForm);
            }
            if (null != (successUnitImportResult = this.successUnits.get(netEntityKey)) && (successformResults = successUnitImportResult.getFormMapResults()).containsKey(formDefine.getKey())) {
                successformResults.remove(formDefine.getKey());
            }
        }
    }

    public void addErrorForm(FormDefine formDefine, String netEntityKey, String message) {
        this.addErrorForm(formDefine, netEntityKey, null, message);
    }

    public Map<String, JIOUnitImportResult> getSuccessUnits() {
        if (this.successUnits == null) {
            this.successUnits = new HashMap<String, JIOUnitImportResult>();
        }
        return this.successUnits;
    }

    public void setSuccessUnits(Map<String, JIOUnitImportResult> successUnits) {
        this.successUnits = successUnits;
    }

    public Map<String, JIOUnitImportResult> getErrorUnits() {
        if (this.errorUnits == null) {
            this.errorUnits = new HashMap<String, JIOUnitImportResult>();
        }
        return this.errorUnits;
    }

    public void setErrorUnits(Map<String, JIOUnitImportResult> errorUnits) {
        this.errorUnits = errorUnits;
    }
}

