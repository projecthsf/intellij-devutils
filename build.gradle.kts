// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

plugins {
  id("java")
  id("org.jetbrains.intellij.platform") version "2.6.0"
}

group = "io.github.projecthsf.devutils"
version = "1.1.0"

repositories {
  mavenCentral()

  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  implementation("io.github.projecthsf:name-case-util:1.0.1")
  implementation("com.opencsv:opencsv:5.12.0")
  implementation("com.github.jsqlparser", "jsqlparser", "5.3")
  intellijPlatform {
    intellijIdeaCommunity("2024.2.6")
    //pycharmCommunity("2024.2.6")
    //datagrip("2025.1.3")
    //bundledPlugin("com.jetbrains.python")
  // bundledPlugin("com.intellij.java")
  }
}

intellijPlatform {
  buildSearchableOptions = false

  pluginConfiguration {
    ideaVersion {
      sinceBuild = "241"
    }
  }
  pluginVerification  {
    ides {
      recommended()
    }
  }
}
