/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.provider.impl;

import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomSystemTableProvider;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomTableProvider;
import com.jiuqi.nr.subdatabase.service.SubDataBaseCheckTableService;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubDataBaseCheckTableProvider
implements SubDataBaseCustomTableProvider,
SubDataBaseCustomSystemTableProvider {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseCheckTableProvider.class);
    @Autowired
    private SubDataBaseCheckTableService checkTableService;

    @Override
    public void createCustomTable(String taskKey, SubDataBase subDataBase) {
        try {
            this.checkTableService.createCheckTableWithModel(taskKey, subDataBase);
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u8868\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    @Override
    public void deleteCustomTable(String taskKey, SubDataBase subDataBase) {
        try {
            this.checkTableService.deleteCheckTableWithModel(taskKey, subDataBase);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u8868\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    @Override
    public void createSystemCustomTable(SubDataBase subDataBase) {
        try {
            this.checkTableService.createCheckTableWithoutModel(subDataBase);
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u8868\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    @Override
    public void deleteSystemCustomTable(SubDataBase subDataBase) {
        try {
            this.checkTableService.deleteCheckTableWithoutModel(subDataBase);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u8868\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    @Override
    public void syncCustomTable(String taskKey, SubDataBase subDataBase) {
    }

    @Override
    public Set<String> getCustomTableNames(String taskKey) {
        return this.checkTableService.getCheckTableNames(taskKey);
    }
}

