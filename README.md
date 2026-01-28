# Protocol Extension

[![protocol-extension](https://img.shields.io/maven-central/v/org.allaymc/protocol-extension?label=protocol-extension)](https://central.sonatype.com/artifact/org.allaymc/protocol-extension)

A NetEase (China) Minecraft protocol extension library for [CloudburstMC/Protocol](https://github.com/CloudburstMC/Protocol).

## Features

- NetEase client compression support (raw deflate format)
- NetEase login chain validation with NetEase public key

## Supported Protocols

| Protocol Version | Minecraft Version |
|------------------|-------------------|
| v766             | 1.21.50           |

## Installation

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.allaymc:protocol-extension:<version>")
}
```

### Maven

```xml
<dependency>
    <groupId>org.allaymc</groupId>
    <artifactId>protocol-extension</artifactId>
    <version><version></version>
</dependency>
```

## Usage

### Using NetEase Codec

```java
import org.allaymc.protocol.extension.v766.Bedrock_v766_NetEase;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;

BedrockCodec codec = Bedrock_v766_NetEase.CODEC;
session.setCodec(codec);
```

### Using NetEase Compression

```java
import org.allaymc.protocol.extension.NetEaseCompression;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.BatchCompression;

BatchCompression compression = new NetEaseCompression();
session.getPeer().setCompression(new SimpleCompressionStrategy(compression));
```

### Validating NetEase Login Chain

```java
import org.allaymc.protocol.extension.NetEaseEncryptionUtils;
import org.cloudburstmc.protocol.bedrock.util.ChainValidationResult;

ChainValidationResult result = NetEaseEncryptionUtils.validateChain(chainData);
if (result.signed()) {
    // Chain is valid and signed by NetEase
}
```

### Detecting NetEase Clients

NetEase clients use RakNet protocol version 8:

```java
int NETEASE_RAKNET_VERSION = 8;
boolean isNetEaseClient = session.getPeer().getRakVersion() == NETEASE_RAKNET_VERSION;
```

## License

LGPL 3.0 - see [LICENSE](LICENSE) for details.

## Credits

- [AllayMC](https://github.com/AllayMC/Allay) - Original implementation
- [CloudburstMC/Protocol](https://github.com/CloudburstMC/Protocol) - Base protocol library
