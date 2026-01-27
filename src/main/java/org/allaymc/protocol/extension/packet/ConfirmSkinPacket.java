package org.allaymc.protocol.extension.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * NetEase packet solely used for skin censorship.
 * <p>
 * This packet is used by the server to whitelist skins that have passed censorship checks.
 * Skins not confirmed by this packet will not be rendered by the client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ConfirmSkinPacket implements BedrockPacket {
    private List<SkinEntry> entries = new ArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return PacketSignal.UNHANDLED;
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SIMULATION_TYPE;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (ConfirmSkinPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Data
    public static class SkinEntry {
        private boolean valid;
        private UUID uuid;
        private byte[] skinBytes;
        private String uidStr;
        private String geoStr;
    }
}
