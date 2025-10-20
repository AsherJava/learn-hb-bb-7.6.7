/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.Utils;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface RulerItem {
    public String getName();

    public String getTitle();

    public String getRulerType();

    default public boolean enableCaching() {
        return true;
    }

    default public String getTriggerType() {
        Set<String> triggerTypes = this.getTriggerTypes();
        if (triggerTypes == null || triggerTypes.size() == 0) {
            return null;
        }
        String triggerType = triggerTypes.iterator().next();
        if (triggerType.equals("after-add-row")) {
            return "AfterAddRow";
        }
        if (triggerType.equals("after-del-row")) {
            return "AfterDelRow";
        }
        if (triggerType.equals("after-set-value")) {
            return "AfterSetValue";
        }
        if (triggerType.startsWith("before-")) {
            return triggerType.substring(triggerType.indexOf("-") + 1);
        }
        return triggerType;
    }

    default public FormulaType getFormulaType() {
        return null;
    }

    public Set<String> getTriggerTypes();

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine var1);

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine var1);

    default public boolean accept(ModelDefine modelDefine, TriggerEvent event) {
        String triggerType = event.getTriggerType();
        if (triggerType.equals("after-set-value")) {
            Map<String, Map<String, Boolean>> triggerFields = this.getTriggerFields(modelDefine);
            if (triggerFields == null) {
                return true;
            }
            Map<String, Boolean> fields = triggerFields.get(event.getTable().getName());
            if (fields == null) {
                return false;
            }
            if (fields.isEmpty()) {
                return false;
            }
            if (fields.containsKey("*")) {
                return true;
            }
            return fields.containsKey(event.getField().getName());
        }
        if (triggerType.equals("after-add-row")) {
            Map<String, Map<String, Boolean>> assignFields = this.getAssignFields(modelDefine);
            if (assignFields == null) {
                return true;
            }
            if (assignFields.containsKey(event.getTable().getName())) {
                return true;
            }
            Map<String, Map<String, Boolean>> triggerFields = this.getTriggerFields(modelDefine);
            if (triggerFields == null) {
                return true;
            }
            Map<String, Boolean> fields = triggerFields.get(event.getTable().getName());
            if (fields == null) {
                return false;
            }
            return fields.values().stream().filter(o -> o == false).findFirst().orElse(true) == false;
        }
        if (triggerType.equals("after-del-row")) {
            Map<String, Map<String, Boolean>> triggerFields = this.getTriggerFields(modelDefine);
            if (triggerFields == null) {
                return true;
            }
            Map<String, Boolean> fields = triggerFields.get(event.getTable().getName());
            if (fields == null) {
                return false;
            }
            return fields.values().stream().filter(o -> o == false).findFirst().orElse(true) == false;
        }
        return true;
    }

    public void execute(Model var1, Stream<TriggerEvent> var2);

    default public Object executeReturn(Model model, Stream<TriggerEvent> events) {
        this.execute(model, null);
        return null;
    }

    default public UUID getObjectId() {
        Set<String> triggerTypes = this.getTriggerTypes();
        if (triggerTypes == null || triggerTypes.size() == 0) {
            return null;
        }
        String triggerType = triggerTypes.iterator().next();
        if (triggerType.startsWith("before-")) {
            return Utils.normalizeId(triggerType.substring(triggerType.indexOf("-") + 1));
        }
        if (this.getObjectType().equals("event")) {
            return Utils.normalizeId("data");
        }
        throw new RuntimeException(String.format("%s\u89c4\u5219\u672a\u5b9a\u4e49objectId", this.getName()));
    }

    default public String getPropertyType() {
        Set<String> triggerTypes = this.getTriggerTypes();
        if (triggerTypes == null || triggerTypes.size() == 0) {
            return null;
        }
        String triggerType = triggerTypes.iterator().next();
        if (triggerType.equals("after-add-row")) {
            return "AfterAddRow";
        }
        if (triggerType.equals("after-del-row")) {
            return "AfterDelRow";
        }
        if (triggerType.equals("after-set-value")) {
            return "AfterSetValue";
        }
        if (triggerType.startsWith("before-")) {
            return "before";
        }
        throw new RuntimeException(String.format("%s\u89c4\u5219\u672a\u5b9a\u4e49propertyType", this.getName()));
    }

    default public String getObjectType() {
        Set<String> triggerTypes = this.getTriggerTypes();
        if (triggerTypes == null || triggerTypes.size() == 0) {
            return null;
        }
        String triggerType = triggerTypes.iterator().next();
        if (triggerType.equals("after-add-row") || triggerType.equals("after-del-row") || triggerType.equals("after-set-value") || triggerType.equals("after-reload")) {
            return "event";
        }
        if (triggerType.startsWith("before-")) {
            return "action";
        }
        throw new RuntimeException(String.format("%s\u89c4\u5219\u672a\u5b9a\u4e49ObjectType", this.getName()));
    }
}

