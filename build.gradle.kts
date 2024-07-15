plugins {
    alias(libs.plugins.android.application) apply false

}

val apiKey: String? = System.getenv("API_KEY")

subprojects {
    afterEvaluate {
        project.extensions.extraProperties.set("apiKey", apiKey)
    }
}
