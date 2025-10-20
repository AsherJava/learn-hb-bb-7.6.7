/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import java.util.ArrayList;
import java.util.List;

public class MemStream2
extends Stream2 {
    private static final int PAGE_SIZE = 1024;
    private List pages = new ArrayList();
    private long size = 0L;
    private int curPageNo = 0;
    private int curPos = 0;

    private byte[] curPage() {
        return this.curPageNo < this.pages.size() ? (byte[])this.pages.get(this.curPageNo) : null;
    }

    @Override
    public void setSize(long newSize) throws StreamException2 {
        if (newSize < 0L) {
            throw new StreamException2("Stream size error");
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

    @Override
    public int read(byte[] buffer, int pos, int count) throws StreamException2 {
        if (pos < 0 || count < 0) {
            throw new StreamException2("Stream read error");
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

    @Override
    public byte read() throws StreamException2 {
        if (this.getPosition() >= this.getSize()) {
            return -1;
        }
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
        throw new StreamException2("Stream read error");
    }

    @Override
    public int write(byte[] buffer, int pos, int count) throws StreamException2 {
        if (pos < 0 || count < 0) {
            throw new StreamException2("Stream read error");
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

    @Override
    public void write(byte value) throws StreamException2 {
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

    @Override
    public long seek(long offset, int origin) throws StreamException2 {
        switch (origin) {
            case 0: {
                if (offset < 0L || offset > this.size) {
                    throw new StreamException2("Stream seek error");
                }
                this.curPageNo = (int)(offset / 1024L);
                this.curPos = (int)(offset % 1024L);
                break;
            }
            case 1: {
                long x = offset + (long)(this.curPageNo * 1024) + (long)this.curPos;
                if (x < 0L || x > this.size) {
                    throw new StreamException2("Stream seek error");
                }
                this.curPageNo = (int)(x / 1024L);
                this.curPos = (int)(x % 1024L);
                break;
            }
            case 2: {
                if (offset > 0L || offset + this.size < 0L) {
                    throw new StreamException2("Stream seek error");
                }
                long x = offset + this.size;
                this.curPageNo = (int)(x / 1024L);
                this.curPos = (int)(x % 1024L);
            }
        }
        return this.curPageNo * 1024 + this.curPos;
    }

    @Override
    public long skip(long offset) throws StreamException2 {
        return this.seek(offset, 1);
    }
}

