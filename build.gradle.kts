plugins {
    kotlin("multiplatform") version "1.5.10"
}

group = "me.user"
version = "1.0"

val ktor_version = "1.6.1"

repositories {
    mavenCentral()
}


kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        val nativeMain by getting
        val nativeTest by getting

        nativeMain.dependencies {
            implementation("io.ktor:ktor-client-curl:$ktor_version")
        }
    }
}
