package org.allaymc.protocol.extension.v766;

import org.allaymc.protocol.extension.common.ConfirmSkinSerializer;
import org.allaymc.protocol.extension.common.NetEaseJsonSerializer;
import org.allaymc.protocol.extension.common.PyRpcSerializer;
import org.allaymc.protocol.extension.common.StoreBuySuccessSerializer;
import org.allaymc.protocol.extension.packet.ConfirmSkinPacket;
import org.allaymc.protocol.extension.packet.NetEaseJsonPacket;
import org.allaymc.protocol.extension.packet.PyRpcPacket;
import org.allaymc.protocol.extension.packet.StoreBuySuccessPacket;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v712.Bedrock_v712;
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerEnchantOptionsPacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

/**
 * NetEase variant of the v766 codec.
 *
 * @author daoge_cmd
 */
public class Bedrock_v766_NetEase extends Bedrock_v766 {

    protected static final TypeMap<ContainerSlotType> CONTAINER_SLOT_TYPES = Bedrock_v712.CONTAINER_SLOT_TYPES
            .toBuilder()
            // NetEase: RECIPE_CUSTOM
            .shift(17, 1)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v766.CODEC.toBuilder()
            .raknetProtocolVersion(8)
            .helper(() -> new BedrockCodecHelper_v766_NetEase(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v766_NetEase.INSTANCE)
            .updateSerializer(TextPacket.class, TextSerializer_v766_NetEase.INSTANCE)
            .updateSerializer(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsSerializer_v766_NetEase.INSTANCE)
            .registerPacket(PyRpcPacket::new, PyRpcSerializer.INSTANCE, 200, PacketRecipient.BOTH)
            .registerPacket(StoreBuySuccessPacket::new, StoreBuySuccessSerializer.INSTANCE, 202, PacketRecipient.BOTH) // TODO: check packet recipient
            .registerPacket(NetEaseJsonPacket::new, NetEaseJsonSerializer.INSTANCE, 203, PacketRecipient.BOTH)
            .registerPacket(ConfirmSkinPacket::new, ConfirmSkinSerializer.INSTANCE, 228, PacketRecipient.CLIENT)
            .build();
}
