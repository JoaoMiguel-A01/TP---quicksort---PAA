import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Implementação do Quicksort Recursivo com testes de performance
 * Demonstra melhor caso, pior caso e array aleatório de 10000 elementos
 */
public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== QUICKSORT - MENU ===");
            System.out.println("1 - Quicksort padrão (recursivo)");
            System.out.println("2 - Quicksort híbrido (determinar M empiricamente)");
            System.out.println("3 - Quicksort híbrido (inserir M manualmente)");
            System.out.println("4 - Quicksort híbrido com Mediana-de-Três (determinar M empiricamente)");
            System.out.println("5 - Quicksort híbrido com Mediana-de-Três (inserir M manualmente)");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    executarTestes(1, 0);
                    break;
                case 2:
                    int melhorM = determinarMelhorM(false);
                    System.out.println("Melhor M determinado (Híbrido Padrão): " + melhorM);
                    executarTestes(2, melhorM);
                    break;
                case 3:
                    System.out.print("Digite o valor de M (subvetor <= M usa Insertion Sort): ");
                    int M = sc.nextInt();
                    executarTestes(2, M);
                    break;
                case 4:
                    int melhorMC = determinarMelhorM(true);
                    System.out.println("Melhor M determinado (Híbrido com Mediana-de-Três): " + melhorMC);
                    executarTestes(3, melhorMC);
                    break;
                case 5:
                    System.out.print("Digite o valor de M (subvetor <= M usa Insertion Sort): ");
                    int MC = sc.nextInt();
                    executarTestes(3, MC);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Executa os 3 testes (aleatório, melhor caso, pior caso) usando o algoritmo escolhido
    private static void executarTestes(int algoritmo, int M) {
        System.out.println("=== QUICKSORT - ANÁLISE DE PERFORMANCE ===\n");

        System.out.println("1. ARRAY ALEATÓRIO (10000 elementos)");
        System.out.println("=====================================");
        int[] arrayAleatorio = gerarArrayAleatorio(10000);
        testarQuicksort(arrayAleatorio, "Array Aleatório", algoritmo, M);

        System.out.println("\n2. MELHOR CASO - Array já ordenado (10000 elementos)");
        System.out.println("=====================================================");
        int[] arrayOrdenado = gerarArrayOrdenado(10000);
        testarQuicksort(arrayOrdenado, "Melhor Caso", algoritmo, M);

        System.out.println("\n3. PIOR CASO - Array ordenado inversamente (10000 elementos)");
        System.out.println("===========================================================");
        int[] arrayInverso = gerarArrayInverso(10000);
        testarQuicksort(arrayInverso, "Pior Caso", algoritmo, M);

        String linha = new String(new char[60]).replace('\0', '=');
        System.out.println("\n" + linha);
        System.out.println("RESUMO DOS RESULTADOS");
        System.out.println(linha);
        System.out.println("• Melhor caso: Array já ordenado - O(n log n)");
        System.out.println("• Pior caso: Array ordenado inversamente - O(n²)");
        System.out.println("• Caso médio: Array aleatório - O(n log n) em média");
    }

    // Testador unificado: 1=padrão, 2=híbrido, 3=híbrido com mediana-de-três
    private static void testarQuicksort(int[] array, String tipo, int algoritmo, int M) {
        int[] arrayParaOrdenar = Arrays.copyOf(array, array.length);
        mostrarEstatisticas(array, tipo);
        long inicio = System.currentTimeMillis();
        if (algoritmo == 1) {
            quicksort(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1);
        } else if (algoritmo == 2) {
            QuicksortHibrido.quicksortHibrido(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1, M);
        } else if (algoritmo == 3) {
            QuicksortHibrido_C.quicksortHibridoMedianaDeTres(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1, M);
        }
        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;
        boolean ordenado = verificarOrdenacao(arrayParaOrdenar);
        System.out.println("Tempo de execução: " + tempoExecucao + " ms");
        System.out.println("Array ordenado corretamente: " + (ordenado ? "✓ SIM" : "✗ NÃO"));
        if (arrayParaOrdenar.length <= 20) {
            System.out.println("Array ordenado: " + Arrays.toString(arrayParaOrdenar));
        } else {
            System.out.println("Primeiros 10 elementos: " +
                Arrays.toString(Arrays.copyOf(arrayParaOrdenar, 10)));
            System.out.println("Últimos 10 elementos: " +
                Arrays.toString(Arrays.copyOfRange(arrayParaOrdenar,
                    arrayParaOrdenar.length - 10, arrayParaOrdenar.length)));
        }
    }

    // Determina empiricamente o melhor M usando vetores de 1000 elementos
    private static int determinarMelhorM(boolean usarMedianaDeTres) {
        int melhorM = 2;
        long melhorTempo = Long.MAX_VALUE;
        final int TAM = 1000;
        final int TESTES_POR_M = 10; // reduzir se estiver lento
        for (int M = 2; M <= 50; M++) {
            long somaTempo = 0;
            for (int t = 0; t < TESTES_POR_M; t++) {
                int[] arr = gerarArrayAleatorio(TAM);
                int[] copia = Arrays.copyOf(arr, arr.length);
                long inicio = System.currentTimeMillis();
                if (usarMedianaDeTres) {
                    QuicksortHibrido_C.quicksortHibridoMedianaDeTres(copia, 0, copia.length - 1, M);
                } else {
                    QuicksortHibrido.quicksortHibrido(copia, 0, copia.length - 1, M);
                }
                long fim = System.currentTimeMillis();
                somaTempo += (fim - inicio);
            }
            long media = somaTempo / TESTES_POR_M;
            //System.out.println("M=" + M + " -> " + media + " ms");
            if (media < melhorTempo) {
                melhorTempo = media;
                melhorM = M;
            }
        }
        return melhorM;
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