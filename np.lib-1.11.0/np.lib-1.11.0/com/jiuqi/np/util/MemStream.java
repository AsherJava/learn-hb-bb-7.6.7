/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.util;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.util.ArrayList;
import java.util.List;

public class MemStream
extends Stream {
    private static final int PAGE_SIZE = 1024;
    private List pages = new ArrayList();
    private long size = 0L;
    private int curPageNo = 0;
    private int curPos = 0;

    private byte[] curPage() {
        return this.curPageNo < this.pages.size() ? (byte[])this.pages.get(this.curPageNo) : null;
    }

    public void setSize(long newSize) throws StreamException {
        if (newSize < 0L) {
            throw new StreamException("Stream size error");
        }
        this.size = newSize;
        int pagecount = (int)(this.size / 1024L);
        if (this.size % 1024L > 0L) {
            ++pagecount;
        }
        if (pagecount > this.pages.size()) {
            int k = pagecount - this.pages.size();
            for (int i = 0; i < k; ++i) {
                this.pages.add(new byte[1024]);
            }
        } else {
            int k = this.pages.size() - pagecount;
            for (int i = 0; i < k; ++i) {
                this.pages.remove(this.pages.size() - 1);
            }
        }
        if ((long)(this.curPageNo * 1024 + this.curPos) > this.size) {
            this.curPageNo = (int)(this.size / 1024L);
            this.curPos = (int)(this.size % 1024L);
        }
    }

    public int read(byte[] buffer, int pos, int count) throws StreamException {
        if (pos < 0 || count < 0) {
            throw new StreamException("Stream read error");
        }
        if (count == 0 || pos >= buffer.length) {
            return 0;
        }
        if (pos + count >= buffer.length) {
            count = buffer.length - pos;
        }
        if (this.size - (long)(this.curPageNo * 1024) - (long)this.curPos < (long)count) {
            count = (int)(this.size - (long)(this.curPageNo * 1024) - (long)this.curPos);
        }
        int readed = 0;
        while (readed < count) {
            byte[] page = this.curPage();
            int canread = count - readed;
            if (canread > 1024 - this.curPos) {
                canread = 1024 - this.curPos;
            }
            System.arraycopy(page, this.curPos, buffer, pos, canread);
            pos += canread;
            readed += canread;
            this.curPos += canread;
            if (this.curPos != 1024) continue;
            ++this.curPageNo;
            this.curPos = 0;
        }
        return count;
    }

    public byte read() throws StreamException {
        byte[] page = this.curPage();
        if (page != null) {
            byte rt = page[this.curPos];
            ++this.curPos;
            if (this.curPos == 1024) {
                this.curPos = 0;
                ++this.curPageNo;
            }
            return rt;
        }
        throw new StreamException("Stream read error");
    }

    public int write(byte[] buffer, int pos, int count) throws StreamException {
        if (pos < 0 || count < 0) {
            throw new StreamException("Stream read error");
        }
        if (count == 0 || pos >= buffer.length) {
            return 0;
        }
        if (pos + count >= buffer.length) {
            count = buffer.length - pos;
        }
        long p = this.getPosition();
        int writed = 0;
        while (writed < count) {
            int canwrite;
            byte[] page = this.curPage();
            if (page == null) {
                page = new byte[1024];
                this.pages.add(page);
            }
            if ((canwrite = 1024 - this.curPos) > count - writed) {
                canwrite = count - writed;
            }
            System.arraycopy(buffer, pos, page, this.curPos, canwrite);
            writed += canwrite;
            if ((p += (long)canwrite) > this.size) {
                this.size = p;
            }
            this.curPos += canwrite;
            pos += canwrite;
            if (this.curPos != 1024) continue;
            this.curPos = 0;
            ++this.curPageNo;
        }
        return count;
    }

    public void write(byte value) throws StreamException {
        byte[] page = this.curPage();
        if (page == null) {
            page = new byte[1024];
            this.pages.add(page);
        }
        page[this.curPos] = value;
        ++this.curPos;
        if (this.curPos >= 1024) {
            this.curPos = 0;
            ++this.curPageNo;
        }
        if ((long)(this.curPageNo * 1024 + this.curPos) > this.size) {
            ++this.size;
        }
    }

    public long seek(long offset, int origin) throws StreamException {
        switch (origin) {
            case 0: {
                if (offset < 0L || offset > this.size) {
                    throw new StreamException("Stream seek error");
                }
                this.curPageNo = (int)(offset / 1024L);
                this.curPos = (int)(offset % 1024L);
                break;
            }
            case 1: {
                long x = offset + (long)(this.curPageNo * 1024) + (long)this.curPos;
                if (x < 0L || x > this.size) {
                    throw new StreamException("Stream seek error");
                }
                this.curPageNo = (int)(x / 1024L);
                this.curPos = (int)(x % 1024L);
                break;
            }
            case 2: {
                if (offset > 0L || offset + this.size < 0L) {
                    throw new StreamException("Stream seek error");
                }
                long x = offset + this.size;
                this.curPageNo = (int)(x / 1024L);
                this.curPos = (int)(x % 1024L);
            }
        }
        return this.curPageNo * 1024 + this.curPos;
    }
}

