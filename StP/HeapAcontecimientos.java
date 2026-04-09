package StP;
import java.util.ArrayList;
import java.util.Objects;

public class HeapAcontecimientos<T> {

    public static class Handle<T> {
        private HeapNode<T> node;
    }

    private static class HeapNode<T> {
        T element;
        int priority;
        int index;
        Handle<T> handle;
    }

    private ArrayList<HeapNode<T>> heap = new ArrayList<>();

    public Handle<T> insert(T element, int priority){
        Objects.requireNonNull(element, "El elemento no puede ser nulo");

        Handle<T> handle = new Handle<>();
        HeapNode<T> node = new HeapNode<>();

        node.element = element;
        node.priority = priority;
        node.index = heap.size();
        node.handle = handle;

        handle.node = node;
        
        heap.add(node);

        // Como .add() agrega al final del Array, tenemos que subir el nodo HASTA DONDE LE CORRESPONDA SEGÚN SU PRIORIDAD (Mayor sube)
        bubbleUp(node.index);

        return handle;
    }

    private void bubbleUp(int i){
        while (i > 0) {
            int parent = (i - 1)/2;

            // En un MaxHeap, si el padre ya es mayor o igual que el hijo, dejamos de subir.
            if (heap.get(parent).priority >= heap.get(i).priority) 
                break;
            
            // Si la prioridad del padre es MENOR, swap() entre los dos para que el más grande suba
            swap(i, parent);

            i = parent;
        }
    }

    private void swap(int i, int j){
        HeapNode<T> a = heap.get(i);
        HeapNode<T> b = heap.get(j);

        heap.set(i, b);
        heap.set(j, a);

        a.index = j;
        b.index = i;
    }

    public T extractMax() {
        if (heap.isEmpty()) return null;

        // Guardamos el máximo (la raíz)
        HeapNode<T> max = heap.get(0);

        // Guardamos y eliminamos al último
        HeapNode<T> last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()){
            // Como raíz ponemos al último para mantener la estructura de árbol completo
            heap.set(0, last);
            last.index = 0;

            // Luego, lo bajamos para que el más grande vuelva a estar arriba
            bubbleDown(0);
        }

        // Desconectamos el handle del nodo eliminado
        max.handle.node = null;

        return max.element;
    }

    private void bubbleDown(int i){
        int size = heap.size();

        while (true) {
            int left = 2*i + 1;
            int right = 2*i + 2;

            // Consideramos al más grande entre el padre y sus hijos
            int largest = i;

            // Hacemos las pruebas para ver si los hijos tienen mayor prioridad
            if (left < size && heap.get(left).priority > heap.get(largest).priority) {
                largest = left;
            }

            if (right < size && heap.get(right).priority > heap.get(largest).priority) {
                largest = right;
            }

            // Si el padre ya es el más grande, finalizó el reordenamiento
            if (largest == i) 
                break;

            // Si no, intercambiamos con el hijo más grande para que la prioridad máxima suba
            swap(i, largest);

            i = largest;
        }
    }

    public void changeKey(Handle<T> handle, int newPriority){
        if (handle == null || handle.node == null) return; 

        HeapNode<T> node = handle.node;
        int old = node.priority;
        node.priority = newPriority;

        // En un MaxHeap, si la nueva prioridad es MAYOR, intentamos subirlo
        if (newPriority > old)
            bubbleUp(node.index);
        // Si es MENOR, intentamos bajarlo
        else
            bubbleDown(node.index);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}