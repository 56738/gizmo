plugins {
    id("gizmo.base")
}

repositories {
    maven("https://repo.viaversion.com")
}

dependencies {
    api(project(":gizmo-common"))
    compileOnly(libs.spigot.api) {
        isTransitive = false
    }
    compileOnly(libs.viaversion.api)
    compileOnly(libs.viaversion.common)
}
