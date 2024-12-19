# Weather Forecast App

[🚧] sample android weather app written entirely in Kotlin and jetpack compose. The app is fully functional and following the best practices.

## Architecture

This project is leveraging hybrid model architecture(by layer + by feature)
* `app` primarily managing navigation logic.
* `Feature` has separate modules for forecast and camera features.
* `Core` has separate modules for networking and database, UI Components and shared code across application.

## Stack

* UI: [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAvdCrBhBREiwAX6-6UogmclLihuJq1CXQKPCG3q9b2vidq9mAjzYjtyXgOhLz34GKmeo7-hoCh7QQAvD_BwE&gclsrc=aw.ds)
* Database: [Room database](https://developer.android.com/training/data-storage/room)
* Networking: [Retrofit](https://square.github.io/retrofit/)
* DI: [DaggerHilt](https://developer.android.com/training/dependency-injection/hilt-android)

## Data

Data is provided by [OpenWeatherMap V3.0](https://openweathermap.org/api).
To get started and make the app work, you need to get a key. Go to the [OpenWeatherMap](https://openweathermap.org/api) and generate a one call key(which will be a v3.0 an NOT V2.5) then copy the key to `secrets.default.properties` and then past it for the `API_KEY` property. Sync the gradle and you are good to go.
Your `secrets.default.properties` file shoud have the following proprties:



