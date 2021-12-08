import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://papermc.io/repo/repository/maven-public/")
    maven(url = "https://jitpack.io/")
    maven(url = "https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    compileOnly(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0-SNAPSHOT")

    implementation("com.github.monun:tap:3.6.0")
    implementation("com.github.monun:kommand:1.0.0")
    implementation("com.github.donghune:minecraft-namulibrary:1.0.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:1.4.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:1.4.0")
    implementation("io.insert-koin:koin-core-ext:3.0.2")
    implementation("com.github.EngineHub.WorldEdit:bukkit:7.2.3")
}

/*//ProtocolLib
repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
}
dependencies {
    compileOnly("com.comphenix.protocol:ProtocolLib:4.6.0")
}*/

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("sourcesJar") {
        from(sourceSets["main"].allSource)
        archiveClassifier.set("sources")
    }

    fun ShadowJar.pluginJar(classifier: String = "") {
        archiveBaseName.set(project.property("pluginName").toString())
        archiveVersion.set(project.property("version").toString()) // For bukkit plugin update
        archiveClassifier.set(classifier)
        from(sourceSets["main"].output)
        configurations = listOf(project.configurations.implementation.get().apply { isCanBeResolved = true })
    }

    create<ShadowJar>("pluginJar") {
        pluginJar()
        relocate("com.github.monun.kommand", "${rootProject.group}.${rootProject.name}.kommand")
        relocate("com.github.monun.tap", "${rootProject.group}.${rootProject.name}.tap")
        relocate("com.github.donghune.namulibrary", "${rootProject.group}.${rootProject.name}.namulibrary")
        relocate("com.github.shynixn.mccoroutine", "${rootProject.group}.${rootProject.name}.mccoroutine")
    }

    create<ShadowJar>("makePluginJar") {
        pluginJar("SNAPSHOT")
    }

    build {
        dependsOn(named("pluginJar"))
    }

    create<Copy>("copyToServer") {
        val plugins = File("./.server/plugins")
        named("makePluginJar").run {
            from(this)
            into(plugins)
        }
    }

    create<DefaultTask>("setupWorkspace") {
        doLast {

            // mkdir
            val folder = File("./.server")

            folder.mkdirs()

            if (File(folder, "paper-1.16.5-777.jar").exists()) {
                return@doLast
            }

            // w-get
            val download by registering(de.undercouch.gradle.tasks.download.Download::class) {
                src("https://papermc.io/api/v2/projects/paper/versions/1.16.5/builds/777/downloads/paper-1.16.5-777.jar")
                dest(folder)
            }
            download.get().download()

            // eula
            val eula = File(folder, "eula.txt")
            val outputStream = eula.outputStream()
            outputStream.write("eula=true".toByteArray())
            outputStream.flush()
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(project.property("pluginName").toString()) {
            artifactId = project.name
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}

//googleDrive {
//    // Missing folders will be created
//    destinationFolderPath = 'test/upload'
//
//    // Or you can specify a folder id
//    destinationFolderId = '1abcedefg'
//
//    // If not specified, simple file name is used
//    destinationName = 'cute_picture.jpg'
//
//    file = file('c:\\Users\\User\\Pictures\\kittens.jpg')
//
//    // Default is true
//    updateIfExists = false
//
//    // Do not put those directly into the build file
//    clientId  = '<YOUR CLIENT ID>'
//    clientSecret = '<YOUR CLIENT SECRET>'
//
//    // Location where Google Drive client's credentials will be stored. You may
//    // want to have a separate dir for each project.
//    // Default is `${user.home}/.credentials/google-drive-uploader`, i.e.
//    // shared between all projects.
//    credentialsDir = file('.gradle/google-drive-uploader/credentials/')
//}
