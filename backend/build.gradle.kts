plugins {
    kotlin("jvm") version "2.0.21"
}

group = "nodove.com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.seleniumhq.selenium:selenium-java:4.27.0")
    testImplementation("org.slf4j:slf4j-log4j12:2.0.16")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}