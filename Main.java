import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Implementação do Quicksort Recursivo com testes de performance
 * Demonstra melhor caso, pior caso e array aleatório de 10000 elementos
 */
public class Main {

    private static final Random random = new Random();
    
    // Contadores globais para comparações e trocas
    private static long comparacoes = 0;
    private static long trocas = 0;
    private static final int NUM_EXECUCOES = 10; // Execuções para obter médias

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

    // Executa os testes (aleatório, ordenado, inverso, repetidos, quase ordenado, todos iguais)
    private static void executarTestes(int algoritmo, int M) {
        System.out.println("=== QUICKSORT - ANÁLISE DE PERFORMANCE ===\n");

        System.out.println("1. ARRAY ALEATÓRIO (10000 elementos)");
        System.out.println("=====================================");
        int[] arrayAleatorio = gerarArrayAleatorio(10000);
        testarQuicksort(arrayAleatorio, "Array Aleatório", algoritmo, M);

        System.out.println("\n2. ARRAY ORDENADO (10000 elementos)");
        System.out.println("====================================");
        int[] arrayOrdenado = gerarArrayOrdenado(10000);
        testarQuicksort(arrayOrdenado, "Array Ordenado", algoritmo, M);

        System.out.println("\n3. ARRAY ORDENADO INVERSAMENTE - Pior Caso (10000 elementos)");
        System.out.println("=============================================================");
        int[] arrayInverso = gerarArrayInverso(10000);
        testarQuicksort(arrayInverso, "Array Inverso (Pior Caso)", algoritmo, M);

        System.out.println("\n4. ARRAY COM ELEMENTOS REPETIDOS (10000 elementos)");
        System.out.println("===================================================");
        int[] arrayRepetidos = gerarArrayComRepeticoes(10000, 100);
        testarQuicksort(arrayRepetidos, "Array com Repetições", algoritmo, M);

        System.out.println("\n5. ARRAY QUASE ORDENADO (10000 elementos, 10% desordenado)");
        System.out.println("============================================================");
        int[] arrayQuaseOrdenado = gerarArrayQuaseOrdenado(10000, 10);
        testarQuicksort(arrayQuaseOrdenado, "Array Quase Ordenado", algoritmo, M);

        System.out.println("\n6. ARRAY COM TODOS ELEMENTOS IGUAIS (10000 elementos)");
        System.out.println("======================================================");
        int[] arrayTodosIguais = gerarArrayTodosIguais(10000);
        testarQuicksort(arrayTodosIguais, "Array Todos Iguais", algoritmo, M);

        String linha = new String(new char[70]).replace('\0', '=');
        System.out.println("\n" + linha);
        System.out.println("RESUMO DOS RESULTADOS");
        System.out.println(linha);
        System.out.println("• Caso médio: Array aleatório - O(n log n)");
        System.out.println("• Melhor caso: Array já ordenado- O(n log n)");
        System.out.println("• Pior caso: Array ordenado inversamente (pior caso) - O(n²)");
        System.out.println("• Repetidos: Muitos elementos duplicados - O(n log n)");
        System.out.println("• Quase ordenado: 90% ordenado, 10% desordenado - O(n log n)");
        System.out.println("• Todos iguais: Todos os elementos com mesmo valor - O(n²)");
    }

    // Testador unificado: 1=padrão, 2=híbrido, 3=híbrido com mediana-de-três
    private static void testarQuicksort(int[] array, String tipo, int algoritmo, int M) {
        mostrarEstatisticas(array, tipo);
        
        // Executar múltiplas vezes e calcular médias
        long somaTempos = 0;
        long somaComparacoes = 0;
        long somaTrocas = 0;
        boolean todosOrdenados = true;
        
        System.out.println("\nExecutando " + NUM_EXECUCOES + " vezes...");
        for (int i = 0; i < NUM_EXECUCOES; i++) {
            int[] arrayParaOrdenar = Arrays.copyOf(array, array.length);
            
            // Resetar contadores
            comparacoes = 0;
            trocas = 0;
            
            // Medir tempo usando relógio da máquina
            long inicio = System.currentTimeMillis();
            if (algoritmo == 1) {
                quicksort(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1);
            } else if (algoritmo == 2) {
                QuicksortHibrido.quicksortHibrido(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1, M);
            } else if (algoritmo == 3) {
                QuicksortHibrido_C.quicksortHibridoMedianaDeTres(arrayParaOrdenar, 0, arrayParaOrdenar.length - 1, M);
            }
            long fim = System.currentTimeMillis();
            
            if (!verificarOrdenacao(arrayParaOrdenar)) {
                todosOrdenados = false;
            }
            
            somaTempos += (fim - inicio);
            somaComparacoes += comparacoes;
            somaTrocas += trocas;
            
            System.out.print(".");
            if ((i + 1) % 10 == 0) System.out.print(" ");
        }
        
        // Calcular médias
        double mediaTempos = (double) somaTempos / NUM_EXECUCOES;
        double mediaComparacoes = (double) somaComparacoes / NUM_EXECUCOES;
        double mediaTrocas = (double) somaTrocas / NUM_EXECUCOES;
        
        // Exibir resultados
        System.out.println("\n\n--- RESULTADOS (Média de " + NUM_EXECUCOES + " execuções) ---");
        System.out.printf("Tempo médio de execução: %.2f ms\n", mediaTempos);
        System.out.printf("Comparações médias: %.0f\n", mediaComparacoes);
        System.out.printf("Trocas médias: %.0f\n", mediaTrocas);
        System.out.println("Array ordenado corretamente: " + (todosOrdenados ? "✓ SIM" : "✗ NÃO"));
        
        // Mostrar amostra de um array ordenado
        int[] arrayParaMostrar = Arrays.copyOf(array, array.length);
        comparacoes = 0;
        trocas = 0;
        if (algoritmo == 1) {
            quicksort(arrayParaMostrar, 0, arrayParaMostrar.length - 1);
        } else if (algoritmo == 2) {
            QuicksortHibrido.quicksortHibrido(arrayParaMostrar, 0, arrayParaMostrar.length - 1, M);
        } else if (algoritmo == 3) {
            QuicksortHibrido_C.quicksortHibridoMedianaDeTres(arrayParaMostrar, 0, arrayParaMostrar.length - 1, M);
        }
        
        if (arrayParaMostrar.length <= 20) {
            System.out.println("Array ordenado: " + Arrays.toString(arrayParaMostrar));
        } else {
            System.out.println("Primeiros 10 elementos: " +
                Arrays.toString(Arrays.copyOf(arrayParaMostrar, 10)));
            System.out.println("Últimos 10 elementos: " +
                Arrays.toString(Arrays.copyOfRange(arrayParaMostrar,
                    arrayParaMostrar.length - 10, arrayParaMostrar.length)));
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
     * Implementação do algoritmo Quicksort recursivo com contadores
     */
    public static void quicksort(int[] array, int inicio, int fim) {
        if (inicio < fim) {
            comparacoes++; // Conta comparação inicio < fim
            
            // Particionar o array e obter o índice do pivô
            int indicePivo = particionar(array, inicio, fim);
            
            // Recursivamente ordenar os elementos antes e depois do pivô
            quicksort(array, inicio, indicePivo - 1);
            quicksort(array, indicePivo + 1, fim);
        } else {
            comparacoes++; // Conta comparação inicio < fim (caso falso)
        }
    }

    /**
     * Função de particionamento do Quicksort com contadores
     */
    private static int particionar(int[] array, int inicio, int fim) {
        // Escolher o último elemento como pivô
        int pivo = array[fim];
        int i = inicio - 1; // Índice do menor elemento
        
        for (int j = inicio; j < fim; j++) {
            comparacoes++; // Conta comparação array[j] <= pivo
            
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
     * Função auxiliar para trocar elementos do array com contador
     */
    private static void trocar(int[] array, int i, int j) {
        if (i != j) { // Só conta troca se for em posições diferentes
            trocas++;
        }
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
     * Gera um array com elementos repetidos
     * @param tamanho tamanho do array
     * @param numValoresUnicos número de valores únicos diferentes
     */
    private static int[] gerarArrayComRepeticoes(int tamanho, int numValoresUnicos) {
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            // Gera valores entre 0 e numValoresUnicos-1, causando muitas repetições
            array[i] = random.nextInt(numValoresUnicos);
        }
        return array;
    }

    /**
     * Gera um array quase ordenado
     * @param tamanho tamanho do array
     * @param percentualDesordenado percentual de elementos fora de ordem (0-100)
     */
    private static int[] gerarArrayQuaseOrdenado(int tamanho, int percentualDesordenado) {
        int[] array = gerarArrayOrdenado(tamanho);
        
        // Calcular número de trocas para desorganizar o array
        int numTrocas = (tamanho * percentualDesordenado) / 100;
        
        for (int i = 0; i < numTrocas; i++) {
            int pos1 = random.nextInt(tamanho);
            int pos2 = random.nextInt(tamanho);
            int temp = array[pos1];
            array[pos1] = array[pos2];
            array[pos2] = temp;
        }
        
        return array;
    }

    /**
     * Gera um array com todos os elementos iguais
     * @param tamanho tamanho do array
     */
    private static int[] gerarArrayTodosIguais(int tamanho) {
        int[] array = new int[tamanho];
        int valor = random.nextInt(1000); // Um valor aleatório qualquer
        
        for (int i = 0; i < tamanho; i++) {
            array[i] = valor;
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