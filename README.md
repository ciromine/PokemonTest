# PokemonTest

¡Bienvenido a PokemonTest! Esta es una aplicación para explorar el fascinante mundo de los Pokémon, permitiéndote navegar por una lista, ver detalles individuales y marcar tus favoritos.

**Un Diseño Hecho con Cariño (Sin Figma, ya que nunca me dieron acceso al solicitado en las instrucciones):**

Es importante destacar que el diseño visual de esta aplicación fue concebido y desarrollado íntegramente por el creador, sin acceso a un diseño previo en Figma. Cada pantalla y componente se construyó con dedicación y un enfoque en la experiencia del usuario. ¡Espero que lo disfrutes!

## Características Principales (A desarrollar según tu implementación)

* **Login:** Un Login mock OAuth.
* **Lista de Pokémon:** Navega por una lista paginada de Pokémon.
* **Detalles del Pokémon:** Visualiza información detallada de cada Pokémon, como sus habilidades, sprites, etc.
* **Favoritos:** Marca Pokémon como favoritos para acceder a ellos fácilmente.
* **Estadpsiticas:** Hay una sección de Perfil con estadísticas básicas del uso de la app.

## Arquitectura 

MVVM.

## Compatibilidad con Android

Esta aplicación está diseñada para funcionar en dispositivos con las siguientes especificaciones:

* **compileSdk:** 35 (Utiliza las últimas APIs de Android para un rendimiento y características modernos)
* **minSdk:** 24 (Compatible con dispositivos Android 7.0 Nougat y versiones superiores)
* **targetSdk:** 35 (Optimizada para la última versión de Android)

## Librerías Utilizadas

PokemonTest se construyó utilizando las siguientes librerías clave para ofrecer una experiencia robusta y eficiente:

* **Navigation Component:** (`androidx.navigation:navigation-fragment-ktx`, `androidx.navigation:navigation-ui-ktx`)
    * Facilita la navegación fluida entre las diferentes pantallas (Fragments) de la aplicación.
* **Kotlin Coroutines:** (`org.jetbrains.kotlinx:kotlinx-coroutines-core`, `org.jetbrains.kotlinx:kotlinx-coroutines-android`)
    * Proporciona una forma concisa y eficiente de manejar tareas asíncronas, como llamadas de red y operaciones en segundo plano.
* **Hilt:** (`com.google.dagger:hilt-android:2.56.2`, `com.google.dagger:hilt-android-compiler:2.56.2` - a través de KSP)
    * Una librería de inyección de dependencias que simplifica la gestión de dependencias en toda la aplicación, mejorando la modularidad y la capacidad de prueba.
* **DataStore Preferences:** (`androidx.datastore:datastore-preferences`)
    * Una solución moderna y segura para almacenar datos clave-valor de forma asíncrona y con consistencia transaccional. Se utiliza para gestionar las preferencias del usuario, como los Pokémon favoritos.
* **Retrofit:** (`com.squareup.retrofit2:retrofit`, `com.squareup.retrofit2:converter-gson` o `com.squareup.retrofit2:converter-moshi`)
    * Una potente librería para realizar llamadas de red HTTP de forma sencilla. Se utiliza para comunicarse con la API de Pokémon.
    * **Gson/Moshi Converter:** Se utiliza para convertir las respuestas JSON de la API a objetos Kotlin.
* **OkHttp Logging Interceptor:** (`com.squareup.okhttp3:logging-interceptor`)
    * Un interceptor para OkHttp que registra las solicitudes y respuestas HTTP, lo que facilita la depuración de problemas de red.
* **Picasso:** (`com.squareup.picasso:picasso`)
    * Una librería popular para la carga y gestión eficiente de imágenes desde URLs. Se utiliza para mostrar las imágenes de los Pokémon.
* **MockK:** (`io.mockk:mockk`)
    * Una librería de mocking para Kotlin que facilita la creación de mocks para pruebas unitarias, permitiendo aislar y verificar el comportamiento de los componentes de la aplicación.

¡Gracias por explorar PokemonTest! ¡Esperamos que disfrutes descubriendo a tus Pokémon favoritos!