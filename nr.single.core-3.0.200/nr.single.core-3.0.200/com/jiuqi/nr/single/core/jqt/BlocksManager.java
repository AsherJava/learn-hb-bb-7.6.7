/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.jqt;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.jqt.BlockMarkList;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class BlocksManager {
    private int streamSize;
    private int blockInfoPtrOffSet;
    private int ptrEncodeKey;
    private boolean autoSaveBlockInfo;
    private BlockMarkList usedBlocks;
    private BlockMarkList unUsedBlocks;

    public BlocksManager(Stream stream, boolean beenBlocked, int blockInfoPtrOffSet, int encodeKey) throws SingleFileException {
        this.ptrEncodeKey = encodeKey;
        this.blockInfoPtrOffSet = blockInfoPtrOffSet;
        try {
            if (blockInfoPtrOffSet < 0 || (long)(blockInfoPtrOffSet + 24) > stream.getSize()) {
                throw new SingleFileException("\u5757\u4fe1\u606f\u6307\u9488\u975e\u6cd5\u6216\u4fdd\u7559\u5757\u592a\u5c0f");
            }
            this.streamSize = (int)stream.getSize();
            this.autoSaveBlockInfo = true;
            this.usedBlocks = new BlockMarkList();
            this.unUsedBlocks = new BlockMarkList();
            if (beenBlocked) {
                this.loadBlocksInfo(stream);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void loadBlocksInfo(Stream stream) throws StreamException, IOException, SingleFileException {
        stream.seek((long)this.blockInfoPtrOffSet, 0);
        int[] reserved = new int[2];
        byte deNum = ReadUtil.decodeLoad2(stream, reserved, 2, this.ptrEncodeKey);
        int reservedHigh = reserved[0];
        int reservedLow = reserved[1];
        if (deNum > 1 || reservedLow < 24 && reservedLow != 0) {
            throw new SingleFileException("\u65e0\u6548\u7684\u6587\u4ef6\u7c7b\u578b\u6216\u6570\u636e\u88ab\u7834\u574f\uff01");
        }
        this.streamSize = reservedLow;
        stream.seek((long)reservedLow, 0);
        this.getUsedBlocks().loadFromStream(stream);
        this.getUnUsedBlocks().loadFromStream(stream);
    }

    private void saveBlocksInfo() {
    }

    public int getStreamSize() {
        return this.streamSize;
    }

    public void setStreamSize(int streamSize) {
        this.streamSize = streamSize;
    }

    public int getBlockInfoPtrOffSet() {
        return this.blockInfoPtrOffSet;
    }

    public void setBlockInfoPtrOffSet(int blockInfoPtrOffSet) {
        this.blockInfoPtrOffSet = blockInfoPtrOffSet;
    }

    public BlockMarkList getUsedBlocks() {
        if (this.usedBlocks == null) {
            this.usedBlocks = new BlockMarkList();
        }
        return this.usedBlocks;
    }

    public void setUsedBlocks(BlockMarkList usedBlocks) {
        this.usedBlocks = usedBlocks;
    }

    public BlockMarkList getUnUsedBlocks() {
        if (this.unUsedBlocks == null) {
            this.unUsedBlocks = new BlockMarkList();
        }
        return this.unUsedBlocks;
    }

    public void setUnUsedBlocks(BlockMarkList unUsedBlocks) {
        this.unUsedBlocks = unUsedBlocks;
    }
}

