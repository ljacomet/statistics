plugins {
    id("java-library")
    id("findbugs")
}

group = "org.terracotta"
version = "2.0-SNAPSHOT"

description = """Terracotta Statistics"""

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven { setUrl("http://snapshots.terracotta.org") }
    maven { setUrl("http://repo.terracotta.org/maven2") }
}

dependencies {
    compileOnly("com.google.code.findbugs:findbugs-annotations:3.0.1")
    implementation("org.slf4j:slf4j-api:1.7.25")

    testImplementation("junit:junit:4.12")
    testImplementation("org.slf4j:slf4j-jdk14:1.7.25")
    testImplementation("org.hamcrest:hamcrest-library:1.3")
    testImplementation ("org.apache.commons:commons-math3:3.6.1")
    testImplementation ("org.mockito:mockito-core:2.10.0")
}

findbugs {
    sourceSets = listOf(java.sourceSets["main"])
}

val sourceJar = tasks.create("sourceJar", Jar::class.java) {
    from(java.sourceSets["main"].allSource)
    classifier = "source"
}

artifacts {
    add("archives", sourceJar)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs = mutableListOf("-Werror")
}
