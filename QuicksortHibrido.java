/**
 * Projeto e Análise de Algoritmos 
 * Pontifícia Universidade de Minas Gerais
 * Instituto de Ciências Exatas e Informática
 * Engenharia de Computação
 * Última Atualização: 05/10/2025
 * Integrantes: João Miguel de Abreu Constâncio, Paulo Ricardo Ferreira Gualberto, Lucas Bretz Araújo Petinga.
 */


public class QuicksortHibrido {

    // Quicksort híbrido: para subvetores com tamanho <= M usa Insertion Sort
    public static void quicksortHibrido(int[] array, int inicio, int fim, int M) {
        if (inicio < fim) {
            if (fim - inicio + 1 <= M) {
                insertionSort(array, inicio, fim);
            } else {
                int pivoIndex = particionar(array, inicio, fim);
                quicksortHibrido(array, inicio, pivoIndex - 1, M);
                quicksortHibrido(array, pivoIndex + 1, fim, M);
            }
        }
    }

    private static int particionar(int[] array, int inicio, int fim) {
        int pivo = array[fim];
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
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static void insertionSort(int[] array, int inicio, int fim) {
        for (int i = inicio + 1; i <= fim; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= inicio && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}
