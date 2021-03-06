package io.github.joealisson.mmocore;

import org.junit.Assert;

import java.nio.ByteBuffer;

public class AsyncClientPongPacket extends ReadablePacket<AsyncClient> {

    private long varLong;
    private double varDouble;
    private float varFloat;
    private int varInt;
    private short varShort;
    private byte varByte;
    private String varString;
    private String varSizedString;

    @Override
    protected boolean read(ByteBuffer buffer) {
        varLong = buffer.getLong();
        varDouble = buffer.getDouble();
        varInt = buffer.getInt();
        varFloat = buffer.getFloat();
        varShort = buffer.getShort();
        varByte = buffer.get();
        varString = readString(buffer);
        varSizedString = readSizedString(buffer);
        return true;
    }

    @Override
    public void run() {
        try {
            Assert.assertEquals(Long.MAX_VALUE, varLong);
            Assert.assertEquals(Double.MAX_VALUE, varDouble, 0);
            Assert.assertEquals(Integer.MAX_VALUE, varInt);
            Assert.assertEquals(Float.MAX_VALUE, varFloat, 0);
            Assert.assertEquals(Short.MAX_VALUE, varShort);
            Assert.assertEquals(Byte.MAX_VALUE, varByte);
            Assert.assertEquals("Pong", varString);
            Assert.assertEquals("Packet", varSizedString);
        } catch (Exception e) {
            CommunicationTest.shutdown(false);
        }

        getClient().sendPacket(new AsyncClientClosePacket());
    }
}