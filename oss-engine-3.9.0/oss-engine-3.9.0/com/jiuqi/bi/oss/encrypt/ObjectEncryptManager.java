/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.oss.encrypt;

import com.jiuqi.bi.oss.encrypt.AESEncryptFactory;
import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.EncryptException;
import com.jiuqi.bi.oss.encrypt.IEncryptFactory;
import com.jiuqi.bi.oss.encrypt.IEncryptProvider;
import com.jiuqi.bi.oss.encrypt.NonEncryptProvider;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectEncryptManager {
    private final Map<String, IEncryptFactory> factories = new HashMap<String, IEncryptFactory>();
    private String defaultEncryptName = "";
    private String defaultEncryptKey = "";
    private static final ObjectEncryptManager instance = new ObjectEncryptManager();

    private ObjectEncryptManager() {
        this.registerEncryptFactory("TYPE_AES", new AESEncryptFactory());
    }

    public static ObjectEncryptManager getInstance() {
        return instance;
    }

    public void setDefaultEncryptName(String defaultEncryptName) {
        this.defaultEncryptName = defaultEncryptName;
    }

    public void setDefaultEncryptKey(String defaultEncryptKey) {
        this.defaultEncryptKey = defaultEncryptKey;
    }

    public String getDefaultEncryptName() {
        return this.defaultEncryptName;
    }

    public String getDefaultEncryptKey() {
        return this.defaultEncryptKey;
    }

    public void registerEncryptFactory(String typeId, IEncryptFactory factory) {
        this.factories.put(typeId, factory);
    }

    public List<IEncryptFactory> providers() {
        return new ArrayList<IEncryptFactory>(this.factories.values());
    }

    public static IEncryptProvider newEncryptProvider(String typeId, EncryptContext context) throws EncryptException {
        return ObjectEncryptManager.getInstance().createEncryptProvider(typeId, context);
    }

    public static IEncryptProvider newEncryptProvider(String typeId, String key) throws EncryptException {
        return ObjectEncryptManager.getInstance().createEncryptProvider(typeId, new EncryptContext(key));
    }

    public IEncryptProvider createEncryptProvider(String typeId, EncryptContext context) throws EncryptException {
        if (StringUtils.isEmpty((String)typeId)) {
            return new NonEncryptProvider();
        }
        IEncryptFactory factory = this.factories.get(typeId);
        if (factory == null) {
            throw new EncryptException("\u4e0d\u652f\u6301\u7684\u52a0\u5bc6\u7b97\u6cd5\uff1a" + typeId);
        }
        return factory.newEncryptProvider(context);
    }
}

