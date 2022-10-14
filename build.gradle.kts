plugins {
    java

    id("fabric-loom") version "1.0.+"

    `maven-publish`
}

group = "com.starl0stgaming"
version = "0.0.1"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net")
}

val minecraftVersion: String by project
val fabricLoaderVersion: String by project
val fabricApiVersion: String by project



dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.4")

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion+$minecraftVersion")

    modApi("teamreborn:energy:2.2.0")

    include("teamreborn:energy:2.2.0") {
        exclude("net.fabricmc.fabric-api")
    }
}

loom {
    runs {
        register("datagenClient") {
            client()
            name("Data Generation")
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.strict-validation")

            ideConfigGenerated(true)
            runDir("build/datagen")
        }


    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/generated/resources", "src/main/resources")
        }
    }
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    withSourcesJar()
}
