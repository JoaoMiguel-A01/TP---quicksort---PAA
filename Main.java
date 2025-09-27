import java.util.Arrays;
import java.util.Random;

/**
 * Implementação do Quicksort Recursivo com testes de performance
 * Demonstra melhor caso, pior caso e array aleatório de 10000 elementos
 */
public class Main {
    
    private static final Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("=== QUICKSORT RECURSIVO - ANÁLISE DE PERFORMANCE ===\n");
        
        // Teste 1: Array aleatório de 10000 elementos
        System.out.println("1. ARRAY ALEATÓRIO (10000 elementos)");
        System.out.println("=====================================");
        int[] arrayAleatorio = gerarArrayAleatorio(10000);
        testarQuicksort(arrayAleatorio, "Array Aleatório");
        
        // Teste 2: Melhor caso - array já ordenado
        System.out.println("\n2. MELHOR CASO - Array já ordenado (10000 elementos)");
        System.out.println("=====================================================");
        int[] arrayOrdenado = gerarArrayOrdenado(10000);
        testarQuicksort(arrayOrdenado, "Melhor Caso");
        
        // Teste 3: Pior caso - array ordenado inversamente
        System.out.println("\n3. PIOR CASO - Array ordenado inversamente (10000 elementos)");
        System.out.println("===========================================================");
        int[] arrayInverso = gerarArrayInverso(10000);
        testarQuicksort(arrayInverso, "Pior Caso");
        
        // Resumo dos resultados
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RESUMO DOS RESULTADOS");
        System.out.println("=".repeat(60));
        System.out.println("• Melhor caso: Array já ordenado - O(n log n)");
        System.out.println("• Pior caso: Array ordenado inversamente - O(n²)");
        System.out.println("• Caso médio: Array aleatório - O(n log n) em média");
    }
    
    /**
     * Testa o Quicksort em um array e exibe os resultados
     */
    private static void testarQuicksort(int[] array, String tipo) {
        // Criar cópia do array original
        int[] arrayParaOrdenar = Arrays.copyOf(array, array.length);
        
        // Mostrar estatísticas do array original
        mostrarEstatisticas(array, tipo);
        
        // Medir tempo de execução
        long inicio = System.currentTimeMillis();
        quicksort(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1);
        long fim = System.currentTimeMillis();
        
        long tempoExecucao = fim - inicio;
        
        // Verificar se está ordenado
        boolean ordenado = verificarOrdenacao(arrayParaOrdenar);
        
        // Exibir resultados
        System.out.println("Tempo de execução: " + tempoExecucao + " ms");
        System.out.println("Array ordenado corretamente: " + (ordenado ? "✓ SIM" : "✗ NÃO"));
        
        // Mostrar alguns elementos do array ordenado
        if (arrayParaOrdenar.length <= 20) {
            System.out.println("Array ordenado: " + Arrays.toString(arrayParaOrdenar));
        } else {
            System.out.println("Primeiros 10 elementos: " + 
                Arrays.toString(Arrays.copyOf(arrayParaOrdenar, 10)));
            System.out.println("Últimos 10 elementos: " + 
                Arrays.toString(Arrays.copyOfRange(arrayParaOrdenar, 
                    arrayParaOrdenar.length-10, arrayParaOrdenar.length)));
        }
    }
    
    /**
     * Implementação do algoritmo Quicksort recursivo
     */
    public static void quicksort(int[] array, int inicio, int fim) {
        if (inicio < fim) {
            // Particionar o array e obter o índice do pivô
            int indicePivo = particionar(array, inicio, fim);
            
            // Recursivamente ordenar os elementos antes e depois do pivô
            quicksort(array, inicio, indicePivo - 1);
            quicksort(array, indicePivo + 1, fim);
        }
    }
    
    /**
     * Função de particionamento do Quicksort
     */
    private static int particionar(int[] array, int inicio, int fim) {
        // Escolher o último elemento como pivô
        int pivo = array[fim];
        int i = inicio - 1; // Índice do menor elemento
        
        for (int j = inicio; j < fim; j++) {
            // Se o elemento atual é menor ou igual ao pivô
            if (array[j] <= pivo) {
                i++;
                trocar(array, i, j);
            }
        }
        
        // Colocar o pivô na posição correta
        trocar(array, i + 1, fim);
        return i + 1;
    }
    
    /**
     * Função auxiliar para trocar elementos do array
     */
    private static void trocar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Gera um array com números aleatórios
     */
    private static int[] gerarArrayAleatorio(int tamanho) {
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            // Gera números aleatórios de -50000 a +50000 para maior variabilidade
            array[i] = random.nextInt(100001) - 50000;
        }
        return array;
    }
    
    /**
     * Gera um array já ordenado (melhor caso)
     */
    private static int[] gerarArrayOrdenado(int tamanho) {
        int[] array = new int[tamanho];
        // Gera números aleatórios e depois ordena para simular melhor caso
        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt(100001) - 50000;
        }
        Arrays.sort(array); // Ordena para simular melhor caso
        return array;
    }
    
    /**
     * Gera um array ordenado inversamente (pior caso)
     */
    private static int[] gerarArrayInverso(int tamanho) {
        int[] array = new int[tamanho];
        // Gera números aleatórios e depois ordena inversamente para simular pior caso
        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt(100001) - 50000;
        }
        Arrays.sort(array); // Ordena primeiro
        // Inverte o array ordenado
        for (int i = 0; i < tamanho / 2; i++) {
            int temp = array[i];
            array[i] = array[tamanho - 1 - i];
            array[tamanho - 1 - i] = temp;
        }
        return array;
    }
    
    /**
     * Verifica se um array está ordenado corretamente
     */
    private static boolean verificarOrdenacao(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Mostra estatísticas básicas do array
     */
    private static void mostrarEstatisticas(int[] array, String tipo) {
        System.out.println("Tipo: " + tipo);
        System.out.println("Tamanho: " + array.length);
        
        if (array.length > 0) {
            int min = Arrays.stream(array).min().orElse(0);
            int max = Arrays.stream(array).max().orElse(0);
            System.out.println("Valor mínimo: " + min);
            System.out.println("Valor máximo: " + max);
            
            // Contar elementos únicos
            long elementosUnicos = Arrays.stream(array).distinct().count();
            System.out.println("Elementos únicos: " + elementosUnicos);
        }
        
        if (array.length <= 20) {
            System.out.println("Array original: " + Arrays.toString(array));
        } else {
            System.out.println("Primeiros 10 elementos: " + 
                Arrays.toString(Arrays.copyOf(array, 10)));
        }
    }
}