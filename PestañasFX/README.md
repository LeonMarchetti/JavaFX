# PestañasFX
Proyecto para mostrar como usar un grupo de pestañas y poder definir el diseño
de cada una en un archivo .fxml diferente, así como su controlador.

## Diseño pantalla principal:
Hay que incluir el/los diseños de las pestañas en este lugar, usando:

> <Tab text="Untitled Tab 1">
>   <content>
>       <fx:include fx:id="ctrl1" source="Pestaña1.fxml" />
>   </content>
> </Tab>

Aquí se incluyó el archivo `Pestaña1.fxml`, y poniéndole el identificador 
`ctrl1`.

En `Pestaña1.fxml` se indica su propio controlador, en este caso 
`application.Controlador1`.

En el controlador principal, se puede obtener un atributo inyectado al 
nombrarlo según el identificador en el fxml y agregándole el sufijo 
`Controller`, e indicando la clase del controlador indicado en el diseño de la 
pestaña, quedando así el atributo `@FXML Controlador1 ctrl1Controller;`.

## Controlador de la pestaña
Se pueden indicar métodos para poder acceder a los controles y poder 
modificarlos desde el controlador principal, directa o indirectamente.

## Otras indicaciones
No pude hacer a inclusión del diseño de la pestaña en la pantalla principal 
desde el SceneBuilder, ya que da un error "Failed to include 'Pestaña1.fxml' 
under 'TabPane'", por lo que tuve que incluir el código usando un editor de 
texto. Después de esto, se buggea SceneBuilder y no permite realizar otras
modificaciones.

## Fuente:
* [StackOverflow - JavaFX 8 - Tabpanes and tabs with separate FXML and controllers for each tab](https://stackoverflow.com/questions/39164050/javafx-8-tabpanes-and-tabs-with-separate-fxml-and-controllers-for-each-tab)