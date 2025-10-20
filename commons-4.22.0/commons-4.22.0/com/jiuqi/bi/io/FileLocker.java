/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import com.jiuqi.bi.io.FileLockException;
import com.jiuqi.bi.logging.LogManager;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class FileLocker
implements Closeable {
    private final File lockFile;
    private final FileChannel channel;
    private final FileLock lock;

    public static FileLocker lock(File lockFile) throws IOException {
        return new FileLocker(lockFile);
    }

    public FileLocker(File lockFile) throws IOException, FileLockException {
        this.lockFile = Objects.requireNonNull(lockFile, "\u672a\u6307\u5b9a\u9501\u6587\u4ef6");
        this.channel = FileChannel.open(lockFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        FileLock result = null;
        try {
            result = this.channel.tryLock();
        }
        catch (OverlappingFileLockException e) {
            throw new FileLockException("\u9501\u5b9a\u6587\u4ef6\u5df2\u7ecf\u88ab\u5f53\u524d\u8fdb\u7a0b\u9501\u5b9a", e);
        }
        finally {
            if (result == null) {
                this.closeVoid(this.channel);
            }
        }
        if (result == null) {
            throw new FileLockException("\u9501\u5b9a\u6587\u4ef6\u5df2\u7ecf\u88ab\u5176\u5b83\u8fdb\u7a0b\u9501\u5b9a");
        }
        this.lock = result;
    }

    public boolean isValid() {
        return this.lock.isValid();
    }

    @Override
    public void close() throws IOException {
        try {
            this.lock.release();
        }
        finally {
            this.closeVoid(this.channel);
        }
    }

    private void closeVoid(Closeable obj) {
        try {
            obj.close();
        }
        catch (IOException e) {
            LogManager.getLogger(this.getClass()).error("\u6587\u4ef6\u9501\u91ca\u653e\u8d44\u6e90\u5931\u8d25\uff1a" + this.lockFile.getAbsolutePath(), e);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("{").append(this.lock).append('@').append(this.lockFile).append("}");
        return buffer.toString();
    }
}

