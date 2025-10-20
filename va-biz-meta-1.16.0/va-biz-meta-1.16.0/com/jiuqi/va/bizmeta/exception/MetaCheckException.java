/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 */
package com.jiuqi.va.bizmeta.exception;

import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import java.util.List;

public class MetaCheckException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private List<PluginCheckResultVO> pluginCheckResultVOS;

    public MetaCheckException() {
    }

    public MetaCheckException(String message) {
        super(message);
    }

    public MetaCheckException(String message, List<PluginCheckResultVO> pluginCheckResultVOS) {
        super(message);
        this.pluginCheckResultVOS = pluginCheckResultVOS;
    }

    public List<PluginCheckResultVO> getPluginCheckResultVOS() {
        return this.pluginCheckResultVOS;
    }

    public void setPluginCheckResultVOS(List<PluginCheckResultVO> pluginCheckResultVOS) {
        this.pluginCheckResultVOS = pluginCheckResultVOS;
    }
}

