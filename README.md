# PokemonTest

¡Bienvenido a PokemonTest! Esta es una aplicación para explorar el fascinante mundo de los Pokémon, permitiéndote navegar por una lista, ver detalles individuales y marcar tus favoritos.

## Consideraciones 

* **Sin Figma:** Como nunca me dieron acceso a Figma, hice lo que se me ocurrió.
* **App Simple:** La app se ve simple, tiene una lista con todos los pokemones (sin paginar), tiene un detalle bien simple, ya que más que hacer un detalle complejo que es como leer bien el JSON, preferí preocuparme de la arquitectura y los test.
* **Favoritos:** Al igual que el punto anterior, favoritos solo marca o desmarca el icono de favoritos y suma estadística, por temas de tiempo y para enfocarme en la arquitectura y los test.
* **Sin Compose:** Se usar compose, pero preferí no usarlo porque me base en un proyecto anterior que realicé https://github.com/ciromine/MovieAppV4

## Características Principales

* **Login:** Un Login mock OAuth.
* **Lista de Pokémon:** Navega por una lista de Pokémones.
* **Detalles del Pokémon:** Visualiza información de cada Pokémon, como sus habilidades y sprites.
* **Favoritos:** Marca Pokémon como favoritos.
* **Estadísticas:** Hay una sección de Perfil con estadísticas básicas del uso de la app, para acceder a esta vista desde el menu de perfil arriba a la derecha.

## Test unitarios

Para correr test unitarios: ```./gradlew test```

## Arquitectura 

MVVM con Clean Arquitecture.

## Compatibilidad con Android

Esta aplicación está diseñada para funcionar en dispositivos con las siguientes especificaciones:

* **compileSdk:** 35 (Utiliza las últimas APIs de Android para un rendimiento y características modernos)
* **minSdk:** 24 (Compatible con dispositivos Android 7.0 Nougat y versiones superiores)
* **targetSdk:** 35 (Optimizada para la última versión de Android)

## Librerías Utilizadas

PokemonTest se construyó utilizando las siguientes librerías clave para ofrecer una experiencia robusta y eficiente:

* **Navigation Component:**
    * Facilita la navegación fluida entre las diferentes pantallas (Fragments) de la aplicación.
* **Kotlin Coroutines:** 
    * Proporciona una forma concisa y eficiente de manejar tareas asíncronas, como llamadas de red y operaciones en segundo plano.
* **Hilt:** 
    * Una librería de inyección de dependencias que simplifica la gestión de dependencias en toda la aplicación, mejorando la modularidad y la capacidad de prueba.
* **DataStore Preferences:** 
    * Una solución moderna y segura para almacenar datos clave-valor de forma asíncrona y con consistencia transaccional. Se utiliza para gestionar las preferencias del usuario, como los Pokémon favoritos.
* **Retrofit:** 
    * Una potente librería para realizar llamadas de red HTTP de forma sencilla. Se utiliza para comunicarse con la API de Pokémon.
    * **Gson/Moshi Converter:** Se utiliza para convertir las respuestas JSON de la API a objetos Kotlin.
* **OkHttp Logging Interceptor:**
    * Un interceptor para OkHttp que registra las solicitudes y respuestas HTTP, lo que facilita la depuración de problemas de red.
* **Picasso:**
    * Una librería popular para la carga y gestión eficiente de imágenes desde URLs. Se utiliza para mostrar las imágenes de los Pokémon.
* **MockK:**
    * Una librería de mocking para Kotlin que facilita la creación de mocks para pruebas unitarias, permitiendo aislar y verificar el comportamiento de los componentes de la aplicación.

¡Gracias por explorar PokemonTest! ¡Espero que disfrutes descubriendo a tus Pokémon favoritos!