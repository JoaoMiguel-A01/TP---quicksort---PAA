public class QuicksortHibrido_C {

    /*Quicksort híbrido que usa Insertion Sort para subvetores pequenos (tamanho <= M)
    e a técnica de mediana-de-três para a escolha do pivô.*/

    public static void quicksortHibridoMedianaDeTres(int[] array, int inicio, int fim, int M) {
        if (inicio < fim) {
            if (fim - inicio + 1 <= M) {
                insertionSort(array, inicio, fim);
            } else {
                int pivo = medianaDeTres(array, inicio, fim);
                int pivoIndex = particionar(array, inicio, fim, pivo);
                quicksortHibridoMedianaDeTres(array, inicio, pivoIndex - 1, M);
                quicksortHibridoMedianaDeTres(array, pivoIndex + 1, fim, M);
            }
        }
    }
    private static int medianaDeTres(int[] array, int inicio, int fim) {
        int meio = inicio + (fim - inicio) / 2;

        if (array[inicio] > array[meio]) {
            trocar(array, inicio, meio);
        }
        if (array[inicio] > array[fim]) {
            trocar(array, inicio, fim);
        }
        if (array[meio] > array[fim]) {
            trocar(array, meio, fim);
        }
        trocar(array, meio, fim);
        return array[fim];
    }
    private static int particionar(int[] array, int inicio, int fim, int pivo) {
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (array[j] <= pivo) {
                i++;
                trocar(array, i, j);
            }
        }
        trocar(array, i + 1, fim);
        return i + 1;
    }
    private static void trocar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void insertionSort(int[] array, int inicio, int fim) {
        for (int i = inicio + 1; i <= fim; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= inicio && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}