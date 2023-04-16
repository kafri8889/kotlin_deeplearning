import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.anafthdev"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.5.1")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-tensorflow:0.5.1")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-visualization:0.5.1")

    implementation("ch.qos.logback:logback-classic:1.4.6")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")

}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}