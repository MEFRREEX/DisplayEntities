plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/maven-snapshots")
    maven("https://repo.opencollab.dev/maven-releases")
}

dependencies {
    api(project(":core"))
    compileOnlyApi("cn.nukkit:nukkit:1.0-SNAPSHOT")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.withType<ProcessResources> {
    filesMatching("*.yml") {
        expand(project.properties)
    }
}

tasks.withType<Jar> {
    archiveFileName.set("DisplayEntities-${project.version}.jar")
}