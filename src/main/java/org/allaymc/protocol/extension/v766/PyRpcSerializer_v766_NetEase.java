package org.allaymc.protocol.extension.v766;

import io.netty.buffer.ByteBuf;
import org.allaymc.protocol.extension.packet.PyRpcPacket;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;

public class PyRpcSerializer_v766_NetEase implements BedrockPacketSerializer<PyRpcPacket> {
    public static final PyRpcSerializer_v766_NetEase INSTANCE = new PyRpcSerializer_v766_NetEase();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PyRpcPacket packet) {
        helper.writeByteArray(buffer, packet.getData());
        buffer.writeIntLE((int)packet.getMsgId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PyRpcPacket packet) {
        packet.setData(helper.readByteArray(buffer));
        packet.setMsgId(buffer.readUnsignedIntLE());
    }
}
