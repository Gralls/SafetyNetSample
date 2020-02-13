# SafetyNetSample
Simple use case of Google's SafetyNet Attestation API

## Android App configuration

### Secrets
In order to run the app file `secret.properties` needs to be created inside `Application/` directory. This file should contains
two variable:
- SafetynetApiKey=yourApiKey
- ApiBaseUrl=http://api.url:8080/

### Signing
Signign configuration of app should be placed inside `signing.gradle` file inside `Application/` directory.

## Server
To start server run `./gradlew run` command inside `Server/` directory.

### Application data
In order to verify device create `app.properties` file inside `Server/resources/` directory and add properties listed below:
- package=pl.patryk.springer.safetynetsample
- certFingerprint=Fingerprint of certificate used to sign application
