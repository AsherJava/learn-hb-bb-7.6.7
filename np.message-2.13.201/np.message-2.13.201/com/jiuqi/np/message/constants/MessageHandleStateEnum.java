/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.constants;

@Deprecated
public enum MessageHandleStateEnum {
    HANDLING(1),
    SUCCESS(2),
    FAIL(3),
    UNHANDLED(0);

    Integer num;

    private MessageHandleStateEnum(Integer num) {
        this.num = num;
    }

    public static MessageHandleStateEnum fromNum(Integer num) {
        for (MessageHandleStateEnum state : MessageHandleStateEnum.values()) {
            if (state.getNum() != num.intValue()) continue;
            return state;
        }
        return null;
    }

    public int getNum() {
        return this.num;
    }
}

