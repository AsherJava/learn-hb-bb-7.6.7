/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.sequencecondition.relation;

import com.jiuqi.va.workflow.domain.RelationEnum;
import com.jiuqi.va.workflow.service.sequencecondition.check.Condition;
import com.jiuqi.va.workflow.service.sequencecondition.relation.Relation;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public final class RelationCollection {
    private static Relation andRelation;
    private static Relation orRelation;

    private RelationCollection() {
    }

    public static Relation andRelation() {
        if (andRelation == null) {
            andRelation = new Relation(){

                @Override
                public String getName() {
                    return RelationEnum.AND.getName();
                }

                @Override
                public boolean execute(List<Condition> conditions, Map<String, Object> data) {
                    if (CollectionUtils.isEmpty(conditions)) {
                        return true;
                    }
                    for (Condition condition : conditions) {
                        if (condition.check(data)) continue;
                        return false;
                    }
                    return true;
                }
            };
        }
        return andRelation;
    }

    public static Relation orRelation() {
        if (orRelation == null) {
            orRelation = new Relation(){

                @Override
                public String getName() {
                    return RelationEnum.OR.getName();
                }

                @Override
                public boolean execute(List<Condition> conditions, Map<String, Object> data) {
                    if (CollectionUtils.isEmpty(conditions)) {
                        return true;
                    }
                    for (Condition condition : conditions) {
                        if (!condition.check(data)) continue;
                        return true;
                    }
                    return false;
                }
            };
        }
        return orRelation;
    }
}

