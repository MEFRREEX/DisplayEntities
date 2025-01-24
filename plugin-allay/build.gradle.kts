plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven("https://www.jitpack.io/")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

dependencies {
    api(project(":core"))
    compileOnlyApi("org.allaymc.allay:api:master-SNAPSHOT")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.withType<ProcessResources> {
    filesMatching("*.json") {
        expand(project.properties)
    }
}

tasks.withType<Jar> {
    archiveFileName.set("DisplayEntities-Allay-${project.version}.jar")
}