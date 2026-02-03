package org.allaymc.protocol.extension.v766;

import io.netty.buffer.ByteBuf;
import org.allaymc.protocol.extension.packet.NetEaseJsonPacket;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;

/**
 * @author daoge_cmd
 */
public class NetEaseJsonSerializer_v766_NetEase implements BedrockPacketSerializer<NetEaseJsonPacket> {
    public static final NetEaseJsonSerializer_v766_NetEase INSTANCE = new NetEaseJsonSerializer_v766_NetEase();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, NetEaseJsonPacket packet) {
        helper.writeString(buffer, packet.getJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, NetEaseJsonPacket packet) {
        packet.setJson(helper.readString(buffer));
    }
}
