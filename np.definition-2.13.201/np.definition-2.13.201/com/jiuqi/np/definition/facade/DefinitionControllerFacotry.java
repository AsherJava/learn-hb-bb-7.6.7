/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DefinitionControllerFacotry {
    private final Logger logger = LoggerFactory.getLogger(DefinitionControllerFacotry.class);
    @Autowired
    IDataDefinitionDesignTimeController designController;
    @Autowired
    IDataDefinitionRuntimeController runTimeController;

    public IDataDefinitionDesignTimeController getDesignTimeController() {
        return this.designController;
    }

    public IDataDefinitionRuntimeController getRunTimeController() {
        return this.runTimeController;
    }

    public IDataDefinitionRuntimeController newRuntimeController(InputStream is) {
        return this.runTimeController;
    }

    public IDataDefinitionRuntimeController newRuntimeController(URI path) {
        return null;
    }

    public IDataDefinitionRuntimeController newRuntimeController(List<URI> paths) {
        return null;
    }

    public IDataDefinitionRuntimeController appendToRuntimeController(URI path) {
        return null;
    }

    public IDataDefinitionRuntimeController appendToRuntimeController(List<URI> paths) {
        return null;
    }
}

