package net.ledestudio.example.mod.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class User {

    private static final int ID = 1;
    private final String name;
    private final int level;

    public User(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public User(ByteBuf buf) {
        int length = buf.readInt();
        ByteBuf nameArr = buf.readBytes(length);
        buf.readBytes(nameArr);
        name = nameArr.toString(StandardCharsets.UTF_8);
        level = buf.readInt();
    }

    public ByteBuf toByteBuf() {
        ByteBuf buf = Unpooled.buffer();
        byte[] nameArr = name.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(nameArr.length);
        buf.writeBytes(nameArr);

        buf.writeInt(ID);

        buf.writeInt(level);
        return buf;
    }
}
