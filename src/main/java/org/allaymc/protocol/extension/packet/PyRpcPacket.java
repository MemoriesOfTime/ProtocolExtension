package org.allaymc.protocol.extension.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * NetEase packet used for Python Scripting API RPC calls.
 * <p>
 * This packet transports MsgPack encoded data for the NetEase Python engine events and callbacks.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PyRpcPacket implements BedrockPacket {

    /**
     * MsgPack message
     */
    private byte[] data;
    /**
     * Protocol Magic Number.
     * Must be 98247598 (0x05DB1EEE) for the packet to be accepted by the server.
     */
    private long msgId;

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
            return (PyRpcPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}