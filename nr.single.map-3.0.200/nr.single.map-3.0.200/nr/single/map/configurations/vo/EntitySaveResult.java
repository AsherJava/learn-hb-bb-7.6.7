/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.vo;

import java.util.ArrayList;
import java.util.List;

public class EntitySaveResult {
    private int successTotal;
    private int failTotal;
    private List<errorSave> error;

    public int getSuccessTotal() {
        return this.successTotal;
    }

    public void setSuccessTotal(int successTotal) {
        this.successTotal = successTotal;
    }

    public int getFailTotal() {
        return this.failTotal;
    }

    public void setFailTotal(int failTotal) {
        this.failTotal = failTotal;
    }

    public List<errorSave> getError() {
        if (this.error == null) {
            this.error = new ArrayList<errorSave>();
        }
        return this.error;
    }

    public void setError(List<errorSave> error) {
        this.error = error;
    }

    public void addSuccess() {
        ++this.successTotal;
    }

    public void addError(String key, String message) {
        ++this.failTotal;
        this.getError().add(new errorSave(key, message));
    }

    class errorSave {
        private String key;
        private String message;

        public errorSave() {
        }

        public errorSave(String key, String message) {
            this.key = key;
            this.message = message;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

