import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.toolgeo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.qcloud:cos_api:5.6.93")
    implementation("com.qcloud:cos-sts_api:3.1.1")
    implementation("cn.hutool:hutool-all:5.8.3")
    implementation("cn.dev33:sa-token-spring-boot-starter:1.30.0")
    implementation("cn.dev33:sa-token-jwt:1.30.0")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.2")
    implementation("com.baomidou:mybatis-plus-generator:3.5.2")
    implementation("org.freemarker:freemarker:2.3.31")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
