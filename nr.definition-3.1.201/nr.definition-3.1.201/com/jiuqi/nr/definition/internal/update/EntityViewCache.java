/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.nr.definition.internal.update.dao.DesTimeEntityViewDefineDao;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineUp;
import com.jiuqi.nr.definition.internal.update.dao.RunTimeEntityViewDefineDao;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityViewCache {
    private final Map<String, EntityViewDefineUp> map = new HashMap<String, EntityViewDefineUp>();
    private RunTimeEntityViewDefineDao runTimeEntityViewDefineDao;
    private DesTimeEntityViewDefineDao desTimeEntityViewDefineDao;
    private final Logger logger = LoggerFactory.getLogger(EntityViewCache.class);

    public EntityViewCache(RunTimeEntityViewDefineDao runTimeEntityViewDefineDao) {
        this.runTimeEntityViewDefineDao = runTimeEntityViewDefineDao;
    }

    public EntityViewCache(DesTimeEntityViewDefineDao desTimeEntityViewDefineDao) {
        this.desTimeEntityViewDefineDao = desTimeEntityViewDefineDao;
    }

    public EntityViewDefineUp getValue(String key) {
        EntityViewDefineUp viewDefineUp = this.map.get(key);
        if (viewDefineUp == null) {
            try {
                viewDefineUp = this.runTimeEntityViewDefineDao == null ? this.desTimeEntityViewDefineDao.getDefineByKey(key) : this.runTimeEntityViewDefineDao.getDefineByKey(key);
                if (viewDefineUp == null) {
                    this.logger.warn("\u672a\u627e\u5230\u89c6\u56fe\uff0c\u8df3\u8fc7\u5347\u7ea7 {}", (Object)key);
                    return null;
                }
                this.map.put(viewDefineUp.getKey(), viewDefineUp);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return viewDefineUp;
    }
}

