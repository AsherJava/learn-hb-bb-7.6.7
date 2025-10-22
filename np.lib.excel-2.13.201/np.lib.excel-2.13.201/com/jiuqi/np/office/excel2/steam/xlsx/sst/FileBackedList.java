/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.sst;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileBackedList
implements AutoCloseable {
    private final List<Long> pointers = new ArrayList<Long>();
    private final RandomAccessFile raf;
    private final FileChannel channel;
    private final Map<Integer, String> cache;
    private long filesize;

    public FileBackedList(File file, final int cacheSize) throws IOException {
        this.raf = new RandomAccessFile(file, "rw");
        this.channel = this.raf.getChannel();
        this.filesize = this.raf.length();
        this.cache = new LinkedHashMap<Integer, String>(cacheSize, 0.75f, true){

            @Override
            public boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > cacheSize;
            }
        };
    }

    public void add(String str) {
        try {
            this.writeToFile(str);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAt(int index) {
        if (this.cache.containsKey(index)) {
            return this.cache.get(index);
        }
        try {
            String val = this.readFromFile(this.pointers.get(index));
            this.cache.put(index, val);
            return val;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeToFile(String str) throws IOException {
        FileChannel fileChannel = this.channel;
        synchronized (fileChannel) {
            ByteBuffer bytes = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
            ByteBuffer length = ByteBuffer.allocate(4).putInt(bytes.array().length);
            this.channel.position(this.filesize);
            this.pointers.add(this.channel.position());
            length.flip();
            this.channel.write(length);
            this.channel.write(bytes);
            this.filesize += (long)(4 + bytes.array().length);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String readFromFile(long pointer) throws IOException {
        FileChannel fileChannel = this.channel;
        synchronized (fileChannel) {
            FileChannel fc = this.channel.position(pointer);
            ByteBuffer buffer = ByteBuffer.wrap(new byte[4]);
            fc.read(buffer);
            buffer.flip();
            int length = buffer.getInt();
            buffer = ByteBuffer.wrap(new byte[length]);
            fc.read(buffer);
            buffer.flip();
            return new String(buffer.array(), StandardCharsets.UTF_8);
        }
    }

    @Override
    public void close() {
        try {
            this.raf.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

