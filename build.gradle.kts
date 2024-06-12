plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "dev.artisra"
version = "1.0-SNAPSHOT"

application {
    mainClass = "dev.artisra.MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "dev.artisra.MainKt"
    }
    val dependencies = configurations.runtimeClasspath.get()
        .filter { it.name.endsWith("jar") }
        .map { zipTree(it) }

    from(dependencies)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kotlin {
    jvmToolchain(17)
}