/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    //extra.versions =
    val kotlinVersion = "1.3.61"
    extra.set("versions" , mapOf(
            "kotlin"            to kotlinVersion,
            "playcore"          to "1.6.4",
            "appcompat"         to "1.1.0",
            "espresso"          to "3.2.0",
            "extJunit"          to "1.1.1",
            "testRules"         to "1.2.0",
            "testRunner"        to "1.2.0"
    ))

    extra.set("names",  mapOf(
            "applicationId" to "com.google.android.samples.dynamicfeatures.ondemand"
    ))

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {

    repositories {
        google()
        jcenter()
    }

    afterEvaluate {
        extensions.findByType(com.android.build.gradle.BaseExtension::class)?.apply {
            compileSdkVersion(28)
            defaultConfig {
                minSdkVersion(24)
                targetSdkVersion(28)
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            // dynamic features only
            if (pluginManager.hasPlugin("com.android.dynamic-feature")) {
                extensions.findByType(com.android.build.gradle.AppExtension::class)?.apply {
                    buildTypes {
                        getByName("debug") {}
                        getByName("release") {}
                    }
                    flavorDimensions("default")
                    productFlavors {
                        maybeCreate("develop")
                        maybeCreate("production")
                    }
                }
            }
        }
    }
}
