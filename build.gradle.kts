plugins {
	id("java")
	id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.dwilden"
version = "1.4"

repositories {
	mavenCentral()
}

dependencies {
	implementation("net.redhogs.cronparser", "cron-parser-core", "3.5") {
		exclude("org.slf4j", "slf4j-api")
	}
	testImplementation("junit", "junit", "4.12")
	testImplementation("org.assertj", "assertj-core", "3.23.1")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
	version.set("2022.1.4")
	type.set("IC") // Target IDE Platform

	plugins.set(listOf("org.jetbrains.kotlin:221-1.7.10-release-333-IJ5591.52", "java", "java-i18n", "properties", "yaml"))
}

tasks {
	// Set the JVM compatibility versions
	withType<JavaCompile> {
		sourceCompatibility = "11"
		targetCompatibility = "11"
	}

	patchPluginXml {
		sinceBuild.set("213")
		untilBuild.set("")
		changeNotes.set("""
      <B>1.0</B> Initial Release of plugin<br>
      <B>1.1</B> localization, 24h time format<br>
      <B>1.2</B> Improved support for intellij builds, fix for dayOfWeek<br>
	  <B>1.3</B> Support for more versions<br>
	  <B>1.4</B> Remove deprecated API calls<br>
      """)
	}

	signPlugin {
		certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
		privateKey.set(System.getenv("PRIVATE_KEY"))
		password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
	}

	publishPlugin {
		token.set(System.getenv("PUBLISH_TOKEN"))
	}
}
