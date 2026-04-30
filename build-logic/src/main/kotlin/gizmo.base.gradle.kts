plugins {
    id("java-library")
    id("maven-publish")
}

group = "me.m56738.gizmo"

repositories {
    maven("https://repo.papermc.io/repository/maven-public")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(8)
    }

    javadoc {
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

publishing {
    publications {
        register("maven", MavenPublication::class) {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "m56738"
            credentials(PasswordCredentials::class)
            if (project.version.toString().endsWith("-SNAPSHOT")) {
                setUrl("https://repo.56738.me/repository/maven-snapshots/")
            } else {
                setUrl("https://repo.56738.me/repository/maven-releases/")
            }
        }
    }
}
