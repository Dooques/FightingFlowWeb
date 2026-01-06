import org.gradle.kotlin.dsl.testImplementation

plugins {
	id("org.jetbrains.kotlin.jvm") version "2.3.0"
	id("org.jetbrains.kotlin.plugin.spring") version "2.3.0"
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.dooques"
version = "0.0.1-SNAPSHOT"
description = "Fighting Flow Web Service"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.google.firebase:firebase-admin:9.2.0")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310") // Add this
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// MySQL
	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("com.google.cloud.sql:mysql-socket-factory-connector-j-8:1.15.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation ("org.springframework.boot:spring-boot-starter-restclient-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
