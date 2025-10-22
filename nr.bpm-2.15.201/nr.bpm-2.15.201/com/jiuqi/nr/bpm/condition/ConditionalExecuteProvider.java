/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.condition;

import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.condition.IConditionalExecuteProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConditionalExecuteProvider
implements IConditionalExecuteProvider {
    @Autowired
    private List<IConditionalExecute> conditionalExecutes;

    @Override
    public List<IConditionalExecute> getAllConditionalExecute() {
        return this.conditionalExecutes;
    }
}

