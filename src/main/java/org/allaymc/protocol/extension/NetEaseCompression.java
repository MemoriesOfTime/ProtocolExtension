package org.allaymc.protocol.extension;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.BatchCompression;
import org.cloudburstmc.protocol.common.util.Zlib;

import java.util.zip.DataFormatException;

/**
 * A zlib compression implementation with fallback decompression support.
 * <p>
 * This class is designed to handle NetEase (China) Minecraft clients which
 * use raw deflate format (without zlib headers), while also supporting
 * international clients that use standard zlib format.
 * <p>
 * Decompression strategy:
 * <ol>
 *     <li>Try raw deflate (ZLIB_RAW) first</li>
 *     <li>If that fails, try standard zlib (with headers)</li>
 *     <li>If both fail, log warning and return raw data</li>
 * </ol>
 * <p>
 * Compression uses raw deflate format (ZLIB_RAW).
 *
 * @author daoge_cmd
 */
@Slf4j
public class NetEaseCompression implements BatchCompression {

    private static final int MAX_DECOMPRESSED_BYTES = Integer.getInteger("bedrock.maxDecompressedBytes", 1024 * 1024 * 10);

    @Getter
    @Setter
    private int level = 7;

    @Override
    public ByteBuf encode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf outBuf = ctx.alloc().ioBuffer(msg.readableBytes() << 3);
        try {
            Zlib.RAW.deflate(msg, outBuf, level);
            return outBuf.retain();
        } finally {
            outBuf.release();
        }
    }

    @Override
    public ByteBuf decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (!msg.isReadable()) {
            throw new DataFormatException("Cannot decompress empty data");
        }

        // Mark the reader index so we can reset if decompression fails
        msg.markReaderIndex();

        // Try raw deflate first (NetEase clients use this)
        try {
            return Zlib.RAW.inflate(msg, MAX_DECOMPRESSED_BYTES);
        } catch (DataFormatException e) {
            log.warn("Raw deflate decompression failed: {}", e.getMessage());
            // Raw deflate failed, reset and try standard zlib
            msg.resetReaderIndex();
        }

        // Try standard zlib (with headers)
        try {
            return Zlib.DEFAULT.inflate(msg, MAX_DECOMPRESSED_BYTES);
        } catch (DataFormatException e) {
            log.warn("Standard zlib decompression also failed: {}", e.getMessage());
            msg.resetReaderIndex();
        }

        // All decompression methods failed, return raw data with warning
        log.warn("All decompression methods failed, returning raw data");
        return msg.retainedSlice();
    }

    @Override
    public CompressionAlgorithm getAlgorithm() {
        return PacketCompressionAlgorithm.ZLIB;
    }
}
