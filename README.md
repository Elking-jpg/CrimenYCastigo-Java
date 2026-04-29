# 🪓 Project: Crime & Punishment - Psychological Simulation

This project is an interactive simulation based on Fyodor Dostoevsky's masterpiece, "Crime and Punishment". The core objective was to "embody" the protagonist's (Rodion Raskolnikov) psychological decay through advanced programming logic.

### Technical Architecture
* **Dynamic Graphs (City Model)**: St. Petersburg is modeled as a graph where each node is a class extending an abstract `Scenario` class. The graph is mutable: as the story progresses, the system dynamically removes nodes and reconnects edges to simulate the protagonist’s mental claustrophobia.
* **Max-Priority Queue (MaxHeap) with Handles**: Implemented a **MaxHeap** to manage game events. It extracts the most critical crisis (Delirium, Police pressure, etc.) in $O(\log n)$ based on real-time psychological stats. The use of **Handles** allows priority updates in real-time without losing efficiency.
* **Polymorphic Logic & Stat Control**: Each scenario has its own action implementation, allowing for a clean, decoupled architecture. Attributes are managed with clamping logic to ensure they stay within range (0-100).

### How to Run
1. Download `StPetesburgo.jar`.
2. Ensure **Java 17** or superior is installed.
3. Run: `java -jar StPetesburgo.jar`.
