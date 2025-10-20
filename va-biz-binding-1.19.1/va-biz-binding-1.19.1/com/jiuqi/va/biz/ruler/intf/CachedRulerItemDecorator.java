/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class CachedRulerItemDecorator
implements RulerItem {
    private Map<String, Map<String, Boolean>> cachedTriggerFields;
    private Map<String, Map<String, Boolean>> cachedAssignFields;
    private Set<String> cachedTriggerTypes;
    private final RulerItem delegate;

    public CachedRulerItemDecorator(RulerItem delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getTitle() {
        return this.delegate.getTitle();
    }

    @Override
    public String getRulerType() {
        return this.delegate.getRulerType();
    }

    @Override
    public Set<String> getTriggerTypes() {
        return this.cachedTriggerTypes;
    }

    @Override
    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine modelDefine) {
        if (this.delegate.enableCaching()) {
            return this.cachedTriggerFields;
        }
        return this.delegate.getTriggerFields(modelDefine);
    }

    @Override
    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine modelDefine) {
        if (this.delegate.enableCaching()) {
            return this.cachedAssignFields;
        }
        return this.delegate.getAssignFields(modelDefine);
    }

    @Override
    public UUID getObjectId() {
        return this.delegate.getObjectId();
    }

    @Override
    public String getPropertyType() {
        return this.delegate.getPropertyType();
    }

    @Override
    public String getObjectType() {
        return this.delegate.getObjectType();
    }

    @Override
    public String getTriggerType() {
        return this.delegate.getTriggerType();
    }

    @Override
    public FormulaType getFormulaType() {
        return this.delegate.getFormulaType();
    }

    @Override
    public Object executeReturn(Model model, Stream<TriggerEvent> events) {
        return this.delegate.executeReturn(model, events);
    }

    @Override
    public void execute(Model model, Stream<TriggerEvent> events) {
        this.delegate.execute(model, events);
    }

    public RulerItem getDelegate() {
        return this.delegate;
    }

    @Override
    public boolean enableCaching() {
        return this.delegate.enableCaching();
    }

    @Override
    public boolean accept(ModelDefine modelDefine, TriggerEvent event) {
        return this.delegate.accept(modelDefine, event);
    }

    public void initCache(ModelDefine modelDefine) {
        this.cachedTriggerTypes = this.delegate.getTriggerTypes();
        if (this.delegate.enableCaching()) {
            this.cachedTriggerFields = this.delegate.getTriggerFields(modelDefine);
            this.cachedAssignFields = this.delegate.getAssignFields(modelDefine);
        }
    }
}

