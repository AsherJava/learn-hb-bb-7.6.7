/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.io.BIFFErrorException;
import com.jiuqi.bi.io.BIFFReader;
import com.jiuqi.bi.io.BIFFWriter;
import com.jiuqi.bi.io.IBIFFInput;
import com.jiuqi.bi.io.IBIFFOutput;
import com.jiuqi.bi.io.IBIFFSerializer;
import com.jiuqi.bi.util.collection.BitSet;
import com.jiuqi.bi.util.tuples.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.function.Function;

final class BIFFSerializer
implements IBIFFSerializer {
    private DataSet<?> dataset;
    private int version;
    private Set<String> dictColumns;
    private Map<Integer, Pair<BiConsumer<IBIFFOutput, Object>, Function<IBIFFInput, Object>>> customSerializers;
    private static final int DATA_VER_1_0 = 0x1000000;
    private static final int DATA_VER_1_1 = 0x1010000;
    private static final int DATA_VER_1_2 = 0x1020000;
    private static final int DATA_VER_1_2_1 = 0x1020001;
    private static final int DATA_VER_2_0 = 0x2000000;
    private static final int DATA_VER_2_0_1 = 0x2000001;
    public static final int DATA_VERSION = 0x2000001;
    private static final int VER_MAJOR_MASK = -16777216;
    private static final byte BIFF_HEADER = 17;
    private static final byte BIFF_ROW = 18;
    private static final byte BIFF_DICTS = 19;

    public static int[] supportedVersions() {
        return new int[]{0x1000000, 0x1010000, 0x1020000, 0x1020001, 0x2000000};
    }

    public BIFFSerializer(DataSet<?> dataset) {
        this.dataset = dataset;
        this.version = 0x2000001;
        this.dictColumns = new HashSet<String>();
        this.customSerializers = new HashMap<Integer, Pair<BiConsumer<IBIFFOutput, Object>, Function<IBIFFInput, Object>>>();
    }

    public Set<String> getDictColumns() {
        return this.dictColumns;
    }

    public void defineSerializer(int dataType, BiConsumer<IBIFFOutput, Object> writer, Function<IBIFFInput, Object> reader) {
        Objects.requireNonNull(writer);
        Objects.requireNonNull(reader);
        this.customSerializers.put(dataType, Pair.with(writer, reader));
    }

    @Override
    public void save(BIFFWriter writer) throws IOException {
        Context context = this.createWriteContext(writer);
        for (DataRow row : this.dataset) {
            context.write(writer, row);
        }
    }

    public Context createWriteContext(BIFFWriter writer) throws IOException {
        writer.write((byte)1, this.resolveVersion());
        if (!this.dictColumns.isEmpty()) {
            String dictText = String.join((CharSequence)",", this.dictColumns);
            writer.write((byte)19, dictText);
        }
        try (IBIFFOutput out = writer.add((byte)17);){
            this.dataset.getMetadata().save(out.toWriter());
        }
        List<DataSerializer> writers = this.createColSerializers();
        return new Context(writers);
    }

    private int resolveVersion() {
        for (Column<?> column : this.dataset.getMetadata()) {
            if (!(column.getInfo() instanceof IBIFFSerializer)) continue;
            return 0x2000001;
        }
        return 0x1020001;
    }

    @Override
    public void load(BIFFReader reader) throws IOException, BIFFErrorException {
        try {
            Metadata<?> metadata = this.dataset.getMetadata();
            this.dataset.clear();
            metadata.getColumns().clear();
            Context context = null;
            boolean reading = true;
            while (reading && reader.hasNext()) {
                IBIFFInput biff = reader.next();
                switch (biff.sign()) {
                    case 1: {
                        this.version = biff.readInt();
                        if ((this.version & 0xFF000000) <= 0x2000000) break;
                        throw new IOException(String.format("\u8bfb\u53d6\u7684\u6570\u636e\u6587\u4ef6\u7248\u672c(%x)\u9ad8\u4e8e\u5f53\u524d\u7a0b\u5e8f\u7248\u672c(%x)\uff0c\u65e0\u6cd5\u8fdb\u884c\u6570\u636e\u8bfb\u53d6", this.version, 0x2000001));
                    }
                    case 17: {
                        metadata.load(biff.toReader());
                        List<DataSerializer> readers = this.createColSerializers();
                        context = new Context(readers);
                        this.dataset.beginUpdate();
                        break;
                    }
                    case 19: {
                        String dictText = biff.readString();
                        this.dictColumns.clear();
                        StringTokenizer i = new StringTokenizer(dictText, ",");
                        while (i.hasMoreElements()) {
                            String dictCol = i.nextToken();
                            this.dictColumns.add(dictCol);
                        }
                        if (this.dictColumns.isEmpty() || context == null) break;
                        context.serializers = this.createColSerializers();
                        break;
                    }
                    case 18: {
                        if (context == null) {
                            throw new IOException("\u6570\u636e\u6587\u4ef6\u5934\u4fe1\u606f\u4e22\u5931\uff01");
                        }
                        if (context.read(biff)) break;
                        reading = false;
                    }
                }
            }
            this.dataset.endUpdate();
        }
        catch (DataSetException e) {
            throw new IOException(e);
        }
    }

    private List<DataSerializer> createColSerializers() throws IOException {
        Metadata<?> metadata = this.dataset.getMetadata();
        ArrayList<DataSerializer> serializers = new ArrayList<DataSerializer>(metadata.size());
        for (Column<?> col : metadata) {
            DataSerializer s;
            switch (col.getDataType()) {
                case 3: {
                    s = new DoubleSerializer(serializers.size());
                    break;
                }
                case 6: {
                    if (this.version > 0x1010000 && (this.dictColumns.isEmpty() || this.dictColumns.contains(col.getName()))) {
                        s = new DictStrSerializer(serializers.size());
                        break;
                    }
                    s = new StringSerializer(serializers.size());
                    break;
                }
                case 5: {
                    if (this.version <= 0x1000000) {
                        s = new RawIntSerializer(serializers.size());
                        break;
                    }
                    s = new IntSerializer(serializers.size());
                    break;
                }
                case 8: {
                    s = new LongSerializer(serializers.size());
                    break;
                }
                case 1: {
                    s = new BooleanSerializer(serializers.size());
                    break;
                }
                case 2: {
                    s = new DateTimeSerializer(serializers.size());
                    break;
                }
                case 9: {
                    s = new BlobSerializer(serializers.size());
                    break;
                }
                case 10: {
                    s = new BigDecimalSerializer(serializers.size());
                    break;
                }
                default: {
                    Pair<BiConsumer<IBIFFOutput, Object>, Function<IBIFFInput, Object>> customs = this.customSerializers.get(col.getDataType());
                    if (customs == null) {
                        throw new IOException("\u672a\u652f\u6301\u5b57\u6bb5[" + col.getName() + "]\u7684\u5e8f\u5217\u5316\u7c7b\u578b\uff1a" + col.getDataType());
                    }
                    s = new CustomSerializer(serializers.size(), customs.get_0(), customs.get_1());
                }
            }
            serializers.add(s);
        }
        return serializers;
    }

    public class Context {
        private List<DataSerializer> serializers;
        private final BitSet flags;

        private Context(List<DataSerializer> serializers) {
            this.serializers = serializers;
            this.flags = new BitSet(serializers.size());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void write(BIFFWriter writer, DataRow row) throws IOException {
            try (IBIFFOutput biff = writer.add((byte)18);){
                int i;
                this.flags.clear();
                for (i = 0; i < this.serializers.size(); ++i) {
                    if (!row.wasNull(i)) continue;
                    this.flags.set(i);
                }
                biff.data().write(this.flags.getBytes());
                for (i = 0; i < this.serializers.size(); ++i) {
                    if (row.wasNull(i)) continue;
                    this.serializers.get(i).write(biff, row);
                }
            }
        }

        public boolean read(IBIFFInput biff) throws IOException {
            biff.data().readFully(this.flags.getBytes());
            try {
                DataRow row = BIFFSerializer.this.dataset.add();
                for (int i = 0; i < this.serializers.size(); ++i) {
                    if (this.flags.get(i)) {
                        row.setNull(i);
                        continue;
                    }
                    this.serializers.get(i).read(biff, row);
                }
                return row.commit();
            }
            catch (DataSetException e) {
                throw new IOException(e);
            }
        }
    }

    private abstract class DataSerializer {
        protected final int index;

        public DataSerializer(int index) {
            this.index = index;
        }

        public abstract void read(IBIFFInput var1, DataRow var2) throws IOException;

        public abstract void write(IBIFFOutput var1, DataRow var2) throws IOException;
    }

    private final class CustomSerializer
    extends DataSerializer {
        private final BiConsumer<IBIFFOutput, Object> writer;
        private final Function<IBIFFInput, Object> reader;

        public CustomSerializer(int index, BiConsumer<IBIFFOutput, Object> writer, Function<IBIFFInput, Object> reader) {
            super(index);
            this.writer = writer;
            this.reader = reader;
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            Object value = this.reader.apply(in);
            row.setValue(this.index, value);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            Object value = row.getValue(this.index);
            this.writer.accept(out, value);
        }
    }

    private final class BigDecimalSerializer
    extends DataSerializer {
        private BigDecimalSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            BIFFWriter.writeBigDecimal(out.data(), row.getBigDecimal(this.index));
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            row.setBigDecimal(this.index, BIFFReader.readBigDecimal(in.data()));
        }
    }

    private final class DictStrSerializer
    extends DataSerializer {
        private List<String> strings;
        private Map<String, Integer> finder;

        public DictStrSerializer(int index) {
            super(index);
            this.strings = new ArrayList<String>();
            this.finder = new HashMap<String, Integer>();
            this.strings.add("");
            this.finder.put("", 0);
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            String value;
            int len = BIFFReader.readIntSize(in.data());
            if (len > 0) {
                value = BIFFReader.readString(in.data(), len);
                this.putDict(value);
            } else {
                value = this.strings.get(-len);
            }
            row.setString(this.index, value);
        }

        private void putDict(String value) {
            int id = this.strings.size();
            this.strings.add(value);
            this.finder.put(value, id);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            String value = row.getString(this.index);
            if (value == null || value.length() == 0) {
                BIFFWriter.writeSize(out.data(), 0);
                return;
            }
            Integer id = this.finder.get(value);
            if (id == null) {
                BIFFWriter.writeString(out.data(), value);
                this.putDict(value);
            } else {
                BIFFWriter.writeSize(out.data(), -id.intValue());
            }
        }
    }

    private final class StringSerializer
    extends DataSerializer {
        private StringSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            BIFFWriter.writeString(out.data(), row.getString(this.index));
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            String value = BIFFReader.readString(in.data());
            row.setString(this.index, value == null ? "" : value);
        }
    }

    private final class BlobSerializer
    extends DataSerializer {
        private BlobSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            out.data().writeInt(row.getBlob(this.index).length());
            if (row.getBlob(this.index).length() > 0) {
                out.data().write(row.getBlob(this.index)._getBytes());
            }
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            int len = in.readInt();
            if (len == 0) {
                row.setBlob(this.index, BlobValue.NULL);
            } else {
                byte[] data = new byte[len];
                in.data().readFully(data);
                row.setBlob(this.index, new BlobValue(data));
            }
        }
    }

    private final class DateTimeSerializer
    extends DataSerializer {
        private DateTimeSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            Object value = row.getValue(this.index);
            if (value instanceof Long) {
                out.data().writeLong((Long)value);
            } else {
                BIFFWriter.writeCalendar(out.data(), (Calendar)value);
            }
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            row.setDate(this.index, BIFFReader.readCalendar(in.data()));
        }
    }

    private final class LongSerializer
    extends DataSerializer {
        private LongSerializer(int index) {
            super(index);
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            long value = in.readLong();
            row.setValue(this.index, value);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            out.data().writeLong(row.getLong(this.index));
        }
    }

    private final class IntSerializer
    extends DataSerializer {
        private IntSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            out.data().writeLong(row.getLong(this.index));
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            long value = in.readLong();
            if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
                row.setValue(this.index, (int)value);
            } else {
                row.setValue(this.index, value);
            }
        }
    }

    private final class RawIntSerializer
    extends DataSerializer {
        private RawIntSerializer(int index) {
            super(index);
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            row.setInt(this.index, in.readInt());
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            out.data().writeInt(row.getInt(this.index));
        }
    }

    private final class DoubleSerializer
    extends DataSerializer {
        private DoubleSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            out.data().writeDouble(row.getDouble(this.index));
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            row.setDouble(this.index, in.readDouble());
        }
    }

    private final class BooleanSerializer
    extends DataSerializer {
        private BooleanSerializer(int index) {
            super(index);
        }

        @Override
        public void write(IBIFFOutput out, DataRow row) throws IOException {
            out.data().writeBoolean(row.getBoolean(this.index));
        }

        @Override
        public void read(IBIFFInput in, DataRow row) throws IOException {
            row.setBoolean(this.index, in.readBoolean());
        }
    }
}

