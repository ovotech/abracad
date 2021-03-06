package abracad.avro;

import java.io.IOException;
import java.nio.ByteBuffer;

import clojure.lang.Keyword;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.ResolvingDecoder;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class ClojureDatumReader extends GenericDatumReader<Object> {

    private static class Vars {
        private static final String NS = "abracad.avro.read";

        private static final Var readRecord = RT.var(NS, "read-record");
        private static final Var readEnum = RT.var(NS, "read-enum");
        private static final Var readArray = RT.var(NS, "read-array");
        private static final Var readMap = RT.var(NS, "read-map");
        private static final Var readFixed = RT.var(NS, "read-fixed");
        private static final Var readBytes = RT.var(NS, "read-bytes");

        static {
            RT.var("clojure.core", "require").invoke(Symbol.intern(NS));
        }
    }

    public ClojureDatumReader(Schema writer, Schema reader, ClojureData data) {
        super(writer, reader, data);
    }

    @Override
    public Object read(Object old, Schema expected, ResolvingDecoder in) throws IOException {
        return super.read(old, expected, in);
    }

    @Override
    protected Object readRecord(Object old, Schema expected, ResolvingDecoder in) {
        return Vars.readRecord.invoke(this, expected, in);
    }

    @Override
    protected Object readEnum(Schema expected, Decoder in) {
        return Vars.readEnum.invoke(this, expected, in);
    }

    @Override
    protected Object readArray(Object old, Schema expected, ResolvingDecoder in) {
        return Vars.readArray.invoke(this, expected, in);
    }

    @Override
    protected Object readMap(Object old, Schema expected, ResolvingDecoder in) {
        return Vars.readMap.invoke(this, expected, in);
    }

    @Override
    protected Object readString(Object old, Schema expected, Decoder in) throws IOException {
        String stringValue = in.readString();
        if ("keyword".equals(expected.getProp(ClojureData.CLOJURE_TYPE_PROP))) {
            return Keyword.intern(stringValue);
        } else {
            return stringValue;
        }
    }

    @Override
    protected Object readFixed(Object old, Schema expected, Decoder in) {
        Object bytes = Vars.readFixed.invoke(this, expected, in);
        if (expected.getLogicalType() != null) {
            // Logical type conversion expects generic fixed
            return getData().createFixed(old, (byte[]) bytes, expected);
        }
        return bytes;
    }

    @Override
    protected Object readBytes(Object old, Schema expected, Decoder in) {
        Object bytes = Vars.readBytes.invoke(this, expected, in);
        if (expected.getLogicalType() != null) {
            // Logical type conversions expect byte buffers
            return ByteBuffer.wrap((byte[]) bytes);
        }
        return bytes;
    }

}
