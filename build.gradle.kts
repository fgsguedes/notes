buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(kotlin(Deps.kotlinGradlePlugin, Version.kotlin))
        classpath(Deps.dexcountGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

tasks {
    withType<Test>().all {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = Version.gradle
        distributionType = Wrapper.DistributionType.ALL
    }

    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
