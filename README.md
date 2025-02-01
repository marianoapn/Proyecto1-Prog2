📌 Soliflips - Juego Lógico en Consola
Soliflips es un juego de lógica programado en Java, desarrollado como parte del Proyecto 1 de Programación 2 (2023). No cuenta con interfaz gráfica, sino que se ejecuta completamente en consola.

🎮 ¿Cómo Jugar?
El objetivo del juego es lograr que todo el tablero tenga el mismo color. El tablero es una matriz de colores (rojo 🔴 y azul 🔵), donde cada celda tiene un símbolo especial que afecta a los colores al ser seleccionado.

🔢 Configuración Inicial
Al iniciar el juego, se debe elegir una de las siguientes opciones:

Tomar datos desde un archivo (datos.txt).
Usar un tablero predefinido (hay dos configuraciones disponibles).
Generar un tablero aleatorio con el tamaño elegido por el jugador.
🧩 Mecánica del Juego
Cada celda tiene uno de estos símbolos aleatorios:

"-" (Guion): Al cambiar el color de esta celda, también cambia toda la fila al color opuesto.
"|" (Barra vertical): Al cambiar el color de esta celda, también cambia toda la columna al color opuesto.
"/" (Barra diagonal): Al cambiar el color de esta celda, también cambia la diagonal principal al color opuesto.
"\ " (Barra inversa): Al cambiar el color de esta celda, también cambia la diagonal secundaria al color opuesto.
El jugador selecciona la casilla a modificar ingresando las coordenadas de la matriz (Ejemplo: 1,2 para la fila 1, columna 2).

🎮 Controles del Juego
Ingresar coordenadas: Para cambiar una celda, el jugador debe ingresar las coordenadas (fila, columna) de la matriz.
-1, -1 → Deshacer el último movimiento y volver atrás.
s → Muestra la solución óptima para completar el tablero.
h → Muestra el historial de movimientos realizados.
x → Termina la partida.
📌 Autor
📍 Mariano Pérez

Este proyecto fue desarrollado de forma individual como parte del Proyecto 1 de la materia Programación 2 (2023).

Si tenés dudas o querés más detalles sobre el funcionamiento, podés revisar el código fuente incluido. 😊
