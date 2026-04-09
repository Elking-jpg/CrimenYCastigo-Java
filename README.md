# 🪓 Crimen Y Castigo:

Este proyecto es una simulación interactiva del libro *Crimen y Castigo*, novela del autor Fiódor Dostoievski. El objetivo central fue tratar de **"encarnar" los trastornos psicológicos** del protagonista, Rodion Raskolnikov, a través de la lógica de programación.

### Arquitectura del Colapso (Lo técnico)

Fuera del endulzamiento literario, el motor se sostiene sobre tres pilares fundamentales:

* **Grafos Dinámicos (La Ciudad)**: San Petersburgo está modelada como un grafo donde cada nodo es una clase que extiende de la clase abstracta `Escenario`. No es un mapa estático: el grafo muta según la situación temporal y emocional. Tras el asesinato, el mundo se "achica": el código elimina nodos y reconecta aristas para simular la asfixia y el encierro del protagonista.

* **Heap con Handle ("Bazuca para un mosquito")**: Implementé una **Max-Priority Queue (MaxHeap)** para gestionar los acontecimientos. Aunque parezca excesivo para la escala actual, permite que el sistema extraiga la crisis más relevante (Delirio, Suicidio, Comisaría) en $O(\log n)$ según prioridades calculadas por los stats de Rodion en cada paso. El uso de **Handles** permite actualizar estas prioridades en tiempo real sin perder eficiencia. (Más detalladamente en Main.java linea 90)

* **Lógica Polimórfica y Control de Stats**: Cada escenario tiene su propia implementación de acciones, permitiendo una organización limpia del proyecto. Además, se incluyó una lógica de "clamping" para asegurar que los atributos físicos y psicológicos se mantengan siempre entre **0 y 100**, evitando desbordamientos en el modelo de simulación.

### Cómo jugar

1. Descargá el archivo `StPetesburgo.jar`.
2. Asegurate de tener **Java 17** o superior instalado.
3. Abrí una terminal en la carpeta del archivo y ejecutá:
   ```bash
   java -jar StPetesburgo.jar
