plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.grpc:grpc-netty:1.49.0") // Cambia la versión según sea necesario
    implementation("io.grpc:grpc-protobuf:1.49.0")
    implementation("io.grpc:grpc-stub:1.49.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2") // Para anotaciones
    implementation("com.google.protobuf:protobuf-java:4.28.3") // Agrega esta línea

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}