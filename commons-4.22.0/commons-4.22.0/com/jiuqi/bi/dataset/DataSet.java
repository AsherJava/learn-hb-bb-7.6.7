/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.StringUtils
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BIFFSerializer;
import com.jiuqi.bi.dataset.CSVSerializer;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DBSerializer;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.ExcelSerializer;
import com.jiuqi.bi.dataset.GridSerializer;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.io.BIFFReader;
import com.jiuqi.bi.io.BIFFWriter;
import com.jiuqi.bi.util.StringUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public abstract class DataSet<T>
implements Iterable<DataRow>,
Cloneable,
Externalizable {
    protected transient Metadata<T> metadata;
    protected final transient Class<T> infoClass;
    public static final int CSV_NOFIELDNAMES = 1;
    public static final int CSV_FIELDMAPPING = 2;
    public static final int CSV_SUPPORT_ANYTYPE = 4;
    public static final int CSV_SIFT_NULL_EMPTY = 8;

    public DataSet() {
        this(null, null);
    }

    public DataSet(Metadata<T> metadata) {
        this(null, metadata);
    }

    public DataSet(Class<T> infoClass) {
        this(infoClass, null);
    }

    public DataSet(Class<T> infoClass, Metadata<T> metadata) {
        if (metadata == null) {
            this.metadata = new Metadata(this);
        } else {
            this.metadata = metadata;
            this.metadata.setOwner(this);
        }
        this.infoClass = infoClass;
    }

    void doAddColumns(int start, int count) throws DataSetException {
        for (DataRow row : this) {
            Object[] buffer = row.getBuffer();
            if (buffer == null) continue;
            Object[] newBuffer = new Object[buffer.length + count];
            if (start > 0) {
                System.arraycopy(buffer, 0, newBuffer, 0, start);
            }
            if (start < buffer.length) {
                System.arraycopy(buffer, start, newBuffer, start + count, buffer.length - start);
            }
            row._setBuffer(newBuffer);
            row.commit();
        }
    }

    void doRemoveColumns(int start, int count) throws DataSetException {
        for (DataRow row : this) {
            Object[] buffer = row.getBuffer();
            if (buffer == null) continue;
            Object[] newBuffer = new Object[buffer.length - count];
            if (start > 0) {
                System.arraycopy(buffer, 0, newBuffer, 0, start);
            }
            if (start + count < buffer.length) {
                System.arraycopy(buffer, start + count, newBuffer, start, buffer.length - start - count);
            }
            row._setBuffer(newBuffer);
            row.commit();
        }
    }

    public Metadata<T> getMetadata() {
        return this.metadata;
    }

    public abstract DataRow add() throws DataSetException;

    public boolean add(Object[] rowData) throws DataSetException {
        DataRow row = this.add();
        System.arraycopy(rowData, 0, row.getBuffer(), 0, this.getMetadata().size());
        return row.commit();
    }

    public void add(DataSet<?> dataset) throws DataSetException {
        for (DataRow srcRow : dataset) {
            DataRow destRow = this.add();
            Object[] buffer = srcRow.getBuffer();
            if (buffer == null) {
                Arrays.fill(destRow.getBuffer(), null);
            } else {
                System.arraycopy(buffer, 0, destRow.getBuffer(), 0, this.getMetadata().size());
            }
            destRow.commit();
        }
    }

    public int size() {
        int c = 0;
        Iterator i = this.iterator();
        while (i.hasNext()) {
            i.next();
            ++c;
        }
        return c;
    }

    public boolean isEmpty() {
        return this.iterator().hasNext();
    }

    public abstract void clear() throws DataSetException;

    public DataSet<T> filter(IDataRowFilter filter) throws DataSetException {
        MemoryDataSet fds = new MemoryDataSet();
        fds.metadata = this.metadata;
        for (DataRow row : this) {
            if (!filter.filter(row)) continue;
            fds.add(row.getBuffer());
        }
        return fds;
    }

    public void sort() throws DataSetException {
        this.sort(new Comparator<DataRow>(){

            @Override
            public int compare(DataRow o1, DataRow o2) {
                for (int i = 0; i < DataSet.this.metadata.size(); ++i) {
                    Object val2;
                    Object val1 = o1.getValue(i);
                    if (val1 == (val2 = o2.getValue(i))) continue;
                    if (val1 == null) {
                        return -1;
                    }
                    if (val2 == null) {
                        return 1;
                    }
                    int c = ((Comparable)val1).compareTo(val2);
                    if (c == 0) continue;
                    return c;
                }
                return 0;
            }
        });
    }

    public void sort(Comparator<DataRow> c) throws DataSetException {
        if (this.isEmpty()) {
            return;
        }
        ArrayList<Object[]> rows = new ArrayList<Object[]>();
        for (DataRow row : this) {
            rows.add(row.getBuffer());
        }
        this.sortRows(rows, c);
        this.clear();
        for (Object[] buffer : rows) {
            this.add(buffer);
        }
    }

    protected void sortRows(List<Object[]> rows, final Comparator<DataRow> c) {
        final MemoryDataRow row1 = new MemoryDataRow();
        final MemoryDataRow row2 = new MemoryDataRow();
        Collections.sort(rows, new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                row1._setBuffer(o1);
                row2._setBuffer(o2);
                return c.compare(row1, row2);
            }
        });
    }

    public MemoryDataSet<T> toMemory() throws DataSetException {
        MemoryDataSet mds = new MemoryDataSet();
        mds.metadata = this.metadata.clone();
        mds.metadata.setOwner(mds);
        for (DataRow row : this) {
            mds.add((Object[])row.getBuffer().clone());
        }
        return mds;
    }

    public Stream<DataRow> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    protected Object clone() {
        try {
            DataSet result = (DataSet)super.clone();
            result.metadata = this.metadata.clone();
            result.metadata.setOwner(this);
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        byte[] data = this.save();
        out.writeInt(data.length);
        out.write(data);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        byte[] data = new byte[len];
        in.readFully(data);
        this.load(data);
    }

    void beginUpdate() throws DataSetException {
    }

    void endUpdate() throws DataSetException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save(OutputStream outStream) throws IOException {
        try (BIFFWriter writer = new BIFFWriter(outStream);){
            BIFFSerializer serializer = new BIFFSerializer(this);
            serializer.save(writer);
        }
    }

    public void load(InputStream inStream) throws IOException {
        BIFFReader reader = new BIFFReader(inStream);
        BIFFSerializer serializer = new BIFFSerializer(this);
        serializer.load(reader);
    }

    public void save(String filename) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(filename);){
            this.save(outStream);
        }
    }

    public void load(String filename) throws IOException {
        try (FileInputStream inStream = new FileInputStream(filename);){
            this.load(inStream);
        }
    }

    public byte[] save() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        this.save(buffer);
        return buffer.toByteArray();
    }

    public void load(byte[] data) throws IOException {
        ByteArrayInputStream buffer = new ByteArrayInputStream(data);
        this.load(buffer);
    }

    public void print(Writer writer) throws IOException {
        for (Column<T> column : this.getMetadata()) {
            if (column.getIndex() > 0) {
                writer.write(",");
            }
            writer.write(column.getName());
        }
        writer.write(StringUtils.LINE_SEPARATOR);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int size = 0;
        for (DataRow row : this) {
            for (Column<T> column : this.getMetadata()) {
                Object value;
                if (column.getIndex() > 0) {
                    writer.write(",");
                }
                if ((value = row.getValue(column.getIndex())) == null) continue;
                if (column.getDataType() == 2) {
                    writer.write(dateFormat.format(((Calendar)value).getTime()));
                    continue;
                }
                if (column.getDataType() == 5 || column.getDataType() == 3) {
                    writer.write(DataSet.formatNumber(((Number)value).doubleValue()));
                    continue;
                }
                writer.write(value.toString());
            }
            writer.write(StringUtils.LINE_SEPARATOR);
            ++size;
        }
        writer.write(Integer.toString(size));
        writer.write(" records.");
    }

    public void saveToCSV(Writer writer, int options) throws IOException {
        new CSVSerializer(this).options(options).save(writer);
    }

    public void saveToCSV(Writer writer) throws IOException {
        this.saveToCSV(writer, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveToCSV(String fileName, int options) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(fileName);
             BufferedOutputStream bufferStream = new BufferedOutputStream(outStream);
             OutputStreamWriter writer = new OutputStreamWriter(bufferStream);){
            this.saveToCSV(writer, options);
        }
    }

    public void saveToCSV(String fileName) throws IOException {
        this.saveToCSV(fileName, 0);
    }

    public void loadFromCSV(Reader reader, int options) throws IOException, DataSetException {
        new CSVSerializer(this).options(options).load(reader);
    }

    public void loadFromCSV(Reader reader) throws IOException, DataSetException {
        this.loadFromCSV(reader, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadFromCSV(String fileName, int options) throws IOException, DataSetException {
        try (FileInputStream inStream = new FileInputStream(fileName);
             InputStreamReader reader = new InputStreamReader(inStream);){
            this.loadFromCSV(reader, options);
        }
    }

    public void loadFromCSV(String fileName) throws IOException, DataSetException {
        this.loadFromCSV(fileName, 0);
    }

    public void saveToExcel(Sheet workSheet) throws IOException {
        ExcelSerializer serializer = new ExcelSerializer(this);
        serializer.save(workSheet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveToExcel(OutputStream outStream) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook();){
            SXSSFSheet sheet = workbook.createSheet("Sheet1");
            this.saveToExcel((Sheet)sheet);
            workbook.write(outStream);
        }
    }

    static String formatNumber(double value) {
        long l = (long)value;
        if (Math.abs(value - (double)l) < 1.0E-10) {
            return Long.toString(l);
        }
        return Double.toString(value);
    }

    public void print(OutputStream stream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        try {
            this.saveToCSV(writer);
        }
        finally {
            ((Writer)writer).flush();
        }
    }

    public void toGrid(GridData gridData) {
        GridSerializer serializer = new GridSerializer(this);
        serializer.save(gridData);
    }

    public void toGrid(GridData gridData, CellField region) {
        GridSerializer serializer = new GridSerializer(this);
        serializer.save(gridData, region);
    }

    public void fromGrid(GridData gridData) throws DataSetException {
        GridSerializer serializer = new GridSerializer(this);
        serializer.load(gridData);
    }

    public void fromGrid(GridData gridData, CellField region) throws DataSetException {
        GridSerializer serializer = new GridSerializer(this);
        serializer.load(gridData, region);
    }

    public void insertToDB(Connection connection, String tableName) throws DataSetException {
        DBSerializer serializer = new DBSerializer(connection, this);
        try {
            serializer.insert(tableName);
        }
        catch (SQLException e) {
            throw new DataSetException(e);
        }
    }

    public void refactor(Metadata<T> newMetadata) throws DataSetException {
        Objects.requireNonNull(newMetadata);
        int[] colMaps = DataSet.createColMaps(this.metadata, newMetadata, Collections.emptyMap());
        List<Object[]> datas = this.rebuildDatas(newMetadata, colMaps);
        this.resetDataSet(newMetadata, datas);
    }

    public int fill(DataSet<?> dataset) throws DataSetException {
        return this.fill(dataset, Collections.emptyMap());
    }

    public int fill(DataSet<?> dataset, Map<String, String> fieldMaps) throws DataSetException {
        Objects.requireNonNull(dataset);
        Objects.requireNonNull(fieldMaps);
        int[] colMaps = DataSet.createColMaps(dataset.getMetadata(), this.metadata, fieldMaps);
        return this.fillDataSet(dataset, colMaps);
    }

    private int fillDataSet(DataSet<?> dataset, int[] colMaps) throws DataSetException {
        int count = 0;
        for (DataRow srcRow : dataset) {
            DataRow destRow = this.add();
            for (int i = 0; i < colMaps.length; ++i) {
                if (colMaps[i] < 0) continue;
                Object value = srcRow.getValue(colMaps[i]);
                destRow.setValue(i, value);
            }
            destRow.commit();
            ++count;
        }
        return count;
    }

    private static int[] createColMaps(Metadata<?> srcMetadata, Metadata<?> destMetadata, Map<String, String> fieldMaps) {
        int[] colMaps = new int[destMetadata.getColumnCount()];
        for (int i = 0; i < destMetadata.getColumnCount(); ++i) {
            Column<?> destCol = destMetadata.getColumn(i);
            String fieldName = fieldMaps.getOrDefault(destCol.getName(), destCol.getName());
            Column<?> srcCol = srcMetadata.find(fieldName);
            colMaps[i] = srcCol != null && srcCol.getDataType() == destCol.getDataType() ? srcCol.getIndex() : -1;
        }
        return colMaps;
    }

    private List<Object[]> rebuildDatas(Metadata<T> newMetadata, int[] colMaps) {
        ArrayList<Object[]> datas = new ArrayList<Object[]>();
        if (colMaps == null) {
            return datas;
        }
        for (DataRow row : this) {
            boolean isNull = true;
            Object[] record = new Object[newMetadata.getColumnCount()];
            for (int i = 0; i < newMetadata.getColumnCount(); ++i) {
                int p = colMaps[i];
                if (p < 0) continue;
                record[i] = row.getValue(p);
                if (record[i] == null) continue;
                isNull = false;
            }
            if (isNull) continue;
            datas.add(record);
        }
        return datas;
    }

    private void resetDataSet(Metadata<T> newMetadata, List<Object[]> datas) throws DataSetException {
        this.clear();
        this.metadata.setOwner(null);
        this.metadata = newMetadata;
        this.metadata.setOwner(this);
        for (Object[] record : datas) {
            this.add(record);
        }
    }

    public String toString() {
        if (this.metadata.getColumnCount() == 0) {
            return "(UNDEFINED)";
        }
        StringWriter writer = new StringWriter();
        try {
            this.saveToCSV(writer, 4);
        }
        catch (IOException e) {
            return e.toString();
        }
        return writer.toString();
    }
}

