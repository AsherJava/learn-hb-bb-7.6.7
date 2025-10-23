/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.storage.IObjectMetaAdapter
 *  com.jiuqi.bi.oss.storage.IObjectStorage
 *  com.jiuqi.bi.oss.storage.ObjectStorageFactory
 *  com.jiuqi.bi.oss.storage.StorageConfig
 *  com.jiuqi.np.blob.BlobContainerProvider
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 */
package com.jiuqi.nr.file.oss;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.np.blob.BlobContainerProvider;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.file.oss.BlobOSSObjectStorageImpl;
import com.jiuqi.nr.file.oss.BlobStorageConfig;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BlobStorageFactory
extends ObjectStorageFactory
implements BeanPostProcessor {
    protected static final String NAME = "default";
    private BlobContainerProvider provider;

    public BlobStorageFactory(BlobContainerProvider provider) {
        this.provider = provider;
    }

    public IObjectStorage createStorage(StorageConfig context, IObjectMetaAdapter metaAdapter) throws ObjectStorageException {
        BlobOSSObjectStorageImpl storage = new BlobOSSObjectStorageImpl(metaAdapter, this.provider);
        storage.initialize(context);
        return storage;
    }

    public String getType() {
        return "\u62a5\u8868\u5b58\u50a8";
    }

    public String getTitle() {
        return "\u62a5\u88681.0\u517c\u5bb9\u5b58\u50a8\u6a21\u5f0f";
    }

    public StorageConfig newInstanceConfig() {
        return new BlobStorageConfig();
    }

    public static boolean isHostConnectable(String host, int port) {
        try (Socket socket = new Socket();){
            socket.connect(new InetSocketAddress(host, port));
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean isHidden() {
        NrdbHelper nrdbHelper = (NrdbHelper)BeanUtils.getBean(NrdbHelper.class);
        return nrdbHelper.isEnableNrdb();
    }
}

