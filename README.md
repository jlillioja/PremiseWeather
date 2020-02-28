# PremiseWeather
The Android Challenge for Premise

This is a simple weather application using the MetaWeather API to provide weather forecasts for given location searches.
Presently, it shares the limitations of the MetaWeather API:
  - It has good support for searching locations by city or country name, but not postal code
  - It provides only a 5-day forecast
  
## Key Technologies
  - Retrofit, Gson, and RxJava for networking
  - Dagger 2 for dependency injection
  - Espresso for instrumentation testing
  - Mockito for unit testing
While it is admittedly a bit overengineered for such a small application, my intent in including all these technologies was to demonstrate how they can work together.
The Retrofit/RxJava stack allows reactive and readable network code - notice, in `WeatherProvider`, how two network calls are required to use the API, but can be chained using `flatmap` without nested callbacks.
Dagger allows alternate components to be provided to application elements during testing, so that any injected dependency can be mocked. You can see a mock `WeatherProvider` used in `MainActivityInstrumentedTest`.
Both of these patterns are common in high-quality Android apps, and while they take more up-front engineering, adding complexity to this application will be much easier and safer for much longer than would otherwise be possible.
