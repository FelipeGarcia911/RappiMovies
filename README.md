# RappiMovies
The Movie Database Androd App to Rappi Android Developer Test
**ARQUITECTURA DE LA APLICACION MOVIL**

A continuacion se describira a grandes razgos como fue construida la aplicacion RappiMovies, explicando cual fue la arquitectua seleccionada y la funcionalidad de cada bloque.

*   Arquitectura CLEAR en division de 4 capa (UI - Presenter - Interacto - Repository).
*   Libreria de AsyncHTTP.
*   Libreria EventBus para comuncacion entre bloques.

**DESCRIPCION DE LAS CAPAS**

1\. UI: Es la capa encargada de obtener y entregar informacion al usuario, en esta capa se encuentran las clases tipo Activity y Fragment

2\. Presenter: Es la capa encargada de controlar los datos e interacciones desde y hacia la UI. En esta capa se encuentran todas clases 'PresenterImp'

3\. Interactor: Es la capa encargada de la logica de negocio. En esta capa se encuentran todas las clases 'InteractorImp'.

4\. Repository: Esta es la capa encargada de administrar los datos (locales y remotos). En esta capa se encuentran todas las clases 'RepositoryImp'.

**DESCRIPCION DE LOS BLOQUES FUNCIONALES**

La aplicacion fue agrupada por interfaces y funcionalidades. Dentro del bloque grande tenemos

1.  Bloques y/o Interfaces
    1.  HomeList: Bloque encargado de administrar la lista de las peliculas.
    2.  Details: Bloque encargado de administrar los detalles de las peliculas.
    3.  About: Bloque encargado de administrar la interfaz de Acerca de.
    4.  Splash: Bloque encargado de administrar la interfaz de Splash 
    5.  Main: Bloque encargado de administrar la actividad principal y la navegacion.
2.  Elementos de Apoyo 
    1.  Helpers: Bloque encargado de almacenar todas las clases y objectos de apoyo
3.  Modelos
    1.  Models: Bloque encargado de almacenar las clases del modelo de negocio.

**PREGUNTAS**

1\. En qué consiste el principio de responsabilidad única? Cuál es su propósito?

El principio de responsabilidad consiste en que una clase solo debe tener una unica funcion y no deberia tener mas, ya que esto implicaria una gran carga de logica y codigo en un sola porcion.

El objetivo es basicamente tratar garantizar que una clase no tenga mas de una responsabilidad.

2\. Qué características tiene, según su opinión, un “buen” código o código limpio?

1.  Arquitectura definida claramente (MVP - MVC, etc..).
2.  Codigo correctamente identado.
3.  Usar el Principio de Responsabilidad Unica.
4.  Planear que se va a ejecutar antes de escribir una sola linea de codigo.
5.  Realizar pruebas y/o validaciones del codigo escrito.
