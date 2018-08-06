# PIP-GUI
Aplicación que muestra los paquetes con actualizaciones disponibles.

## Uso
Al abrirlo se ejecuta automáticamente la búsqueda de paquetes actualizables. Cuando termina aparecen en la tabla todos los paquetes.

El comando que se utiliza para buscar los paquetes a actualizar es: `pip list --outdated`

Haciendo click en la fila se actualiza el paquete.

El comando que se utiliza para actualizar el paquete seleccionado es: `pip install <paquete>`
