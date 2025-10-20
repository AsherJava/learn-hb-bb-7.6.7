/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.datamapping.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataRefSaveVO
implements Serializable {
    private static final long serialVersionUID = 7895295908256342492L;
    private Integer total;
    private Integer success;
    private Map<Integer, String> errorMessage;
    private List<DataRefDTO> createData;
    private List<DataRefDTO> updateData;
    private List<DataRefDTO> deleteData;
    static final String ERROR_MSG = "\u7b2c%1$d\u6761\uff0c%2$s <br>";

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccess() {
        return this.success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Map<Integer, String> getErrorMessage() {
        return this.errorMessage;
    }

    public String getErrorMessageStr() {
        StringBuilder errorMsg = new StringBuilder();
        for (Map.Entry<Integer, String> entry : this.errorMessage.entrySet()) {
            errorMsg.append(String.format(ERROR_MSG, entry.getKey() + 1, entry.getValue()));
        }
        if (errorMsg.length() > 0) {
            errorMsg.delete(errorMsg.length() - 4, errorMsg.length());
        }
        return errorMsg.toString();
    }

    public void setErrorMessage(Map<Integer, String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DataRefDTO> getCreateData() {
        return this.createData;
    }

    public void setCreateData(List<DataRefDTO> createData) {
        this.createData = createData;
    }

    public List<DataRefDTO> getUpdateData() {
        return this.updateData;
    }

    public void setUpdateData(List<DataRefDTO> updateData) {
        this.updateData = updateData;
    }

    public List<DataRefDTO> getDeleteData() {
        return this.deleteData;
    }

    public void setDeleteData(List<DataRefDTO> deleteData) {
        this.deleteData = deleteData;
    }
}

