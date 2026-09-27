import com.google.gms.googleservices.GoogleServicesPlugin.GoogleServicesPluginConfig
import com.google.gms.googleservices.GoogleServicesPlugin.MissingGoogleServicesStrategy

plugins { id("com.google.gms.google-services") }

extensions.configure<GoogleServicesPluginConfig> {
    missingGoogleServicesStrategy = MissingGoogleServicesStrategy.IGNORE
}
