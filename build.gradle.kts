import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    id("java-library")
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "org.allaymc"
version = "0.1.4-SNAPSHOT"
description = "NetEase protocol extension for CloudburstMC/Protocol"

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/maven-snapshots")
    maven("https://repo.opencollab.dev/maven-releases")
}

dependencies {
    api(group = "org.cloudburstmc.protocol", name = "bedrock-connection", version = "3.0.0.Beta11-20260105.115034-18")
    api(group = "org.cloudburstmc.protocol", name = "common", version = "3.0.0.Beta11-20260105.115034-17")
    api(group = "org.cloudburstmc.protocol", name = "bedrock-codec", version = "3.0.0.Beta11-20260105.115034-18")
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.34")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.34")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        configureEach {
            options.isFork = true
        }
    }

    // We already have sources jar, so no need to build Javadoc, which would cause a lot of warnings
    withType<Javadoc> {
        enabled = false
    }

    withType<Copy> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configure<MavenPublishBaseExtension> {
    publishToMavenCentral()
    signAllPublications()

    coordinates(project.group.toString(), "protocol-extension", project.version.toString())

    pom {
        name.set("Protocol Extension")
        description.set("NetEase protocol extension for CloudburstMC/Protocol")
        inceptionYear.set("2026")
        url.set("https://github.com/AllayMC/ProtocolExtension")

        scm {
            connection.set("scm:git:git://github.com/AllayMC/ProtocolExtension.git")
            url.set("https://github.com/AllayMC/ProtocolExtension")
        }

        licenses {
            license {
                name.set("LGPL 3.0")
                url.set("https://www.gnu.org/licenses/lgpl-3.0.en.html")
            }
        }

        developers {
            developer {
                name.set("AllayMC Team")
                organization.set("AllayMC")
                organizationUrl.set("https://github.com/AllayMC")
            }
        }
    }
}
