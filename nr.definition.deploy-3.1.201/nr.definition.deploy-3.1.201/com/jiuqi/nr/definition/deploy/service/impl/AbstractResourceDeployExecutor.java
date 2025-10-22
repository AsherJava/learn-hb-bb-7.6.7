/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.nr.definition.deploy.ParamDeployController;
import com.jiuqi.nr.definition.deploy.service.IResourceDeployExecutor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractResourceDeployExecutor
implements IResourceDeployExecutor {
    protected static final Logger LOGGER = ParamDeployController.LOGGER;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
}

