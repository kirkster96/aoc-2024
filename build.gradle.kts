plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.13"
    kotlin("plugin.allopen") version "2.0.20"

}
allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.13")
}

benchmark {
    targets {
        register("main")
    }
}

repositories {
    mavenCentral()
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}


