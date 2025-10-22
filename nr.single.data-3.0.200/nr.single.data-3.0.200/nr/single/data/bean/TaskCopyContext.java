/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.data.bean.TaskCopyParam;

public class TaskCopyContext {
    private List<String> copyUnitCodes;
    private TaskCopyParam copyParam;
    private Map<String, Object> intfObjects;
    private String entityCompanyType;
    private String entityId;
    private String oldEntityCompanyType;
    private String oldEntityId;

    public List<String> getCopyUnitCodes() {
        if (this.copyUnitCodes == null) {
            this.copyUnitCodes = new ArrayList<String>();
        }
        return this.copyUnitCodes;
    }

    public void setCopyUnitCodes(List<String> copyUnitCodes) {
        this.copyUnitCodes = copyUnitCodes;
    }

    public TaskCopyParam getCopyParam() {
        return this.copyParam;
    }

    public void setCopyParam(TaskCopyParam copyParam) {
        this.copyParam = copyParam;
    }

    public Map<String, Object> getIntfObjects() {
        if (null == this.intfObjects) {
            this.intfObjects = new HashMap<String, Object>();
        }
        return this.intfObjects;
    }

    public String getEntityCompanyType() {
        return this.entityCompanyType;
    }

    public void setEntityCompanyType(String entityCompanyType) {
        this.entityCompanyType = entityCompanyType;
    }

    public String getOldEntityCompanyType() {
        return this.oldEntityCompanyType;
    }

    public void setOldEntityCompanyType(String oldEntityCompanyType) {
        this.oldEntityCompanyType = oldEntityCompanyType;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOldEntityId() {
        return this.oldEntityId;
    }

    public void setOldEntityId(String oldEntityId) {
        this.oldEntityId = oldEntityId;
    }
}

