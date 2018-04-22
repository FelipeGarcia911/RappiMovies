## ARQUITECTURA DE LA APLICACION MOVIL

## _**Rappi Movies**_

A continuacion se describira a grandes razgos como fue construida la aplicación RappiMovies, explicando cual fue la arquitectura seleccionada y la funcionalidad de cada bloque.
    ![Arquitectura Clean](http://www.tempos21.com/web/wp-content/uploads/2015/03/clean_architecture1.png)

*   Arquitectura CLEAR en division de 4 capa (UI - Presenter - Interacto - Repository).
*   Libreria de AsyncHTTP.
*   Libreria EventBus para comuncacion entre bloques.
*   Libreria ButterKnife para el manejo de los elemento graficos
*   Libreria RecyclerView y Swipe para manejo de las listas.

**DESCRIPCION DE LAS CAPAS**
1\. UI: Es la capa encargada de obtener y entregar informacion al usuario, en esta capa se encuentran las clases tipo Activity y Fragment

2\. Presenter: Es la capa encargada de controlar los datos e interacciones desde y hacia la UI. En esta capa se encuentran todas clases 'PresenterImp'

3\. Interactor: Es la capa encargada de la logica de negocio. En esta capa se encuentran todas las clases 'InteractorImp'.

4\. Repository: Esta es la capa encargada de administrar los datos (locales y remotos). En esta capa se encuentran todas las clases 'RepositoryImp'.

**DESCRIPCION DE LOS BLOQUES FUNCIONALES**
La aplicacion fue agrupada por interfaces y funcionalidades acontinuacion se describiran los bloques principales

1.  HomeList: Encargado de administrar la lista de las peliculas.
2.  Details: Encargado de administrar los detalles de las peliculas.
3.  About: Encargado de administrar la interfaz de Acerca de.
4.  Splash: Encargado de administrar la interfaz de Splash
5.  Main: Encargado de administrar la actividad principal y la navegacion.
6.  Helpers: Encargado de almacenar todas las clases y objectos de apoyo
7.  Models: Encargado de almacenar las clases del modelo de negocio.

**PREGUNTAS**
1\. En qué consiste el principio de responsabilidad única? Cuál es su propósito?
El principio de responsabilidad consiste en que una clase solo debe tener una unica funcion y no deberia tener mas, ya que esto implicaria una gran carga de logica y codigo en un sola porcion.
El objetivo es basicamente tratar garantizar que una clase no tenga mas de una responsabilidad y solo se ocupe de lo que realmente le corresponde.

2\. Qué características tiene, según su opinión, un “buen” código o código limpio?
1.  Arquitectura definida claramente (MVP - MVC, Clean, etc..).
2.  Codigo correctamente identado.
3.  Usar el Principio de Responsabilidad Unica.
4.  Planear que se va a ejecutar antes de escribir una sola linea de codigo.
5.  Realizar pruebas y/o validaciones del codigo escrito.
6.  Se sigue un estandar al nombrar las variables y clases
7.  Comentar las partes o secciones que lo requieran
8.  Es eficiente en el uso de recursos de hardware
9.  En la POO se definen clases, objectos y capas de manera organizada
