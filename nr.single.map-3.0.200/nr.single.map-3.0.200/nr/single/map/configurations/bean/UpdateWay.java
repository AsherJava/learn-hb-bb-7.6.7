/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.io.Serializable;
import java.util.List;

public class UpdateWay
implements Serializable {
    private static final long serialVersionUID = -8720815291465641651L;
    private boolean update;
    private boolean increment;
    private List<String> ignoreAttribute;

    public UpdateWay() {
    }

    public UpdateWay(boolean update, boolean increment) {
        this.update = update;
        this.increment = increment;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isIncrement() {
        return this.increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    public static UpdateWay buildDefault() {
        return new UpdateWay(false, true);
    }

    public List<String> getIgnoreAttribute() {
        return this.ignoreAttribute;
    }

    public void setIgnoreAttribute(List<String> ignoreAttribute) {
        this.ignoreAttribute = ignoreAttribute;
    }
}

