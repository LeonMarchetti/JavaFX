# FFMpeg-GUI
Aplicación que ejecuta algunas conversiones usando FFmpeg.

## Convertir a GIF
Convierte un video a una imagen animada, indicando tiempo de inicio y de fin. Opcionalmente se puede especificar la escala de salida. La escala se especifica como "ancho:alto".

El comando usado para convertir un video es: `ffmpeg -i <video> -ss <inicio> -to <fin> <salida> -hide_banner -y"`

El comando usado para cambiar la escala de la imagen es: `ffmpeg -i <imagen> -vf scale=<escala> <salida> -hide_banner -y`

## Recortar Imagen
Recorta una imagen, indicando ancho y alto y la posición del recorte

El comando usado para recortar una imagen es: `ffmpeg -i <imagen> -vf "crop=<ancho>:<alto>:<x>:<y>" <salida> -y`
