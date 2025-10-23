/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api.core;

public interface IObjectConvert<DO, DTO, VO> {
    public DTO DO2DTO(DO var1);

    public DO DTO2DO(DTO var1);

    public VO DTO2VO(DTO var1);

    public DTO VO2DTO(VO var1);
}

