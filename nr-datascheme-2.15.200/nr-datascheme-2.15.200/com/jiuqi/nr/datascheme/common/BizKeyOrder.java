/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class BizKeyOrder
implements Comparable<BizKeyOrder> {
    private final String value;
    private final BizKeyType type;
    private final String order;

    @Override
    public int compareTo(@NonNull BizKeyOrder that) {
        if (this == that) {
            return 0;
        }
        if (this.getType() == that.getType()) {
            return this.getOrder().compareTo(that.getOrder());
        }
        return this.type.order - that.type.order;
    }

    public static <DF extends DesignDataField> String[] order(Collection<DF> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return new String[0];
        }
        ArrayList<BizKeyOrder> bizKeyOrders = new ArrayList<BizKeyOrder>(fields.size());
        BizKeyOrder bizKeyOrder = null;
        for (DesignDataField field : fields) {
            DataFieldKind dataFieldKind = field.getDataFieldKind();
            if ((dataFieldKind.getValue() & DataFieldKind.PUBLIC_FIELD_DIM.getValue()) != 0) {
                bizKeyOrder = "MDCODE".equals(field.getCode()) ? new BizKeyOrder(field.getKey(), BizKeyType.UNIT, field.getOrder()) : ("DATATIME".equals(field.getCode()) ? new BizKeyOrder(field.getKey(), BizKeyType.PERIOD, field.getOrder()) : new BizKeyOrder(field.getKey(), BizKeyType.DIMENSION, field.getOrder()));
            } else if ((dataFieldKind.getValue() & DataFieldKind.BUILT_IN_FIELD.getValue()) != 0 && "BIZKEYORDER".equals(field.getCode())) {
                bizKeyOrder = new BizKeyOrder(field.getKey(), BizKeyType.BIZ_KEY_ORDER, field.getOrder());
            } else {
                if ((dataFieldKind.getValue() & DataFieldKind.TABLE_FIELD_DIM.getValue()) == 0) continue;
                bizKeyOrder = new BizKeyOrder(field.getKey(), BizKeyType.TABLE_FIELD_DIM, field.getOrder());
            }
            bizKeyOrders.add(bizKeyOrder);
        }
        bizKeyOrders.sort(null);
        return (String[])bizKeyOrders.stream().map(BizKeyOrder::getValue).distinct().toArray(String[]::new);
    }

    private BizKeyOrder(String value, BizKeyType type, String order) {
        Assert.notNull((Object)value, "the value argument must be not null");
        Assert.notNull((Object)type, "the type argument must be not null");
        Assert.notNull((Object)order, "the order argument must be not null");
        this.value = value;
        this.type = type;
        this.order = order;
    }

    public String getValue() {
        return this.value;
    }

    public BizKeyType getType() {
        return this.type;
    }

    public String getOrder() {
        return this.order;
    }

    private static enum BizKeyType {
        UNIT(0),
        PERIOD(1),
        DIMENSION(2),
        TABLE_FIELD_DIM(3),
        BIZ_KEY_ORDER(4);

        private final int order;

        private BizKeyType(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }
    }
}

