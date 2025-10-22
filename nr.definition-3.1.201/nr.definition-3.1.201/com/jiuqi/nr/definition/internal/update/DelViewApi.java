/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.nr.definition.internal.update.DeleteViewExecutor;
import com.jiuqi.nr.definition.internal.update.DesDeleteViewExecutor;
import com.jiuqi.nr.definition.internal.update.EntityViewCache;
import com.jiuqi.nr.definition.internal.update.dao.DesTimeEntityViewDefineDao;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineUp;
import com.jiuqi.nr.definition.internal.update.dao.RunTimeEntityViewDefineDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelViewApi {
    @Autowired
    private DeleteViewExecutor deleteViewExecutor;
    @Autowired
    private DesDeleteViewExecutor desDeleteViewExecutor;
    @Autowired
    private RunTimeEntityViewDefineDao runTimeEntityViewDefineDao;
    @Autowired
    private DesTimeEntityViewDefineDao desTimeEntityViewDefineDao;

    public boolean update() {
        return this.update(false);
    }

    public boolean update(boolean auto) {
        if (!auto) {
            try {
                EntityViewDefineUp lock = new EntityViewDefineUp();
                lock.setKey("UPDATE_SET");
                this.runTimeEntityViewDefineDao.insert(lock);
            }
            catch (Exception e) {
                Logger logger = LoggerFactory.getLogger(DelViewApi.class);
                logger.error("\u8bf7\u52ff\u91cd\u590d\u8fdb\u884c\u89c6\u56fe\u5347\u7ea7", e);
                return false;
            }
        }
        EntityViewCache cache = new EntityViewCache(this.runTimeEntityViewDefineDao);
        this.deleteViewExecutor.update(cache);
        EntityViewCache cacheDes = new EntityViewCache(this.desTimeEntityViewDefineDao);
        this.desDeleteViewExecutor.update(cacheDes);
        return true;
    }
}

