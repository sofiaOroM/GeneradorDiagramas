plugins {
    id("java-library")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
dependencies {
    implementation(files("libs/java-cup-11b.jar"))
}