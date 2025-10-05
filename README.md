# Quicksort Híbrido — Análise de Performance

Projeto acadêmico de **Projeto e Análise de Algoritmos** que implementa e avalia variações do Quicksort em diferentes cenários de entrada. Inclui versões **padrão**, **híbridas** (com *Insertion Sort* em subvetores pequenos) e **híbridas com Mediana-de-Três** para seleção de pivô. O programa executa baterias de testes, mede tempos médios e valida a ordenação.  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)

> **Última atualização:** 05/10/2025  
> **Autores:** João Miguel de Abreu Constâncio, Paulo Ricardo Ferreira Gualberto, Lucas Bretz Araújo Petinga  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)[2](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/QuicksortHibrido.java)[3](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/QuicksortHibrido_C.java)

---

## 📂 Estrutura do Projeto

- `Main.java` — Interface de menu, geração dos arrays de teste, laço de medições (100 execuções por cenário), contadores globais de comparações e trocas (para o Quicksort padrão), e rotina para **determinar empiricamente o melhor M**.  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)
- `QuicksortHibrido.java` — Quicksort híbrido: quando o subvetor tem tamanho `<= M`, usa **Insertion Sort**; caso contrário, particiona (pivô determinístico no fim) e recursa.  [2](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/QuicksortHibrido.java)
- `QuicksortHibrido_C.java` — Quicksort híbrido com **Mediana-de-Três** para escolha do pivô + *Insertion Sort* em subvetores `<= M`.  [3](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/QuicksortHibrido_C.java)

---

## 🧠 Algoritmos Implementados

1. **Quicksort padrão (recursivo):** particionamento com pivô no fim do subarray; contabiliza comparações e trocas.  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)
2. **Quicksort híbrido (pivô determinístico):** usa *Insertion Sort* em subproblemas de tamanho `<= M`.  [2](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/QuicksortHibrido.java)
3. **Quicksort híbrido com Mediana-de-Três:** reduz partições degeneradas escolhendo pivô por mediana entre `início`, `meio` e `fim`, além do *Insertion Sort* para subvetores pequenos.  [3](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/QuicksortHibrido_C.java)

---

## 🧪 Cenários de Teste

O programa gera e testa automaticamente os seguintes perfis de entrada (para tamanho configurável, padrão **10.000**):

- **Aleatório** (valores entre −50.000 e +50.000)  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)  
- **Ordenado** (melhor caso para alguns sorters; sensível para pivô determinístico)  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)  
- **Ordenado inversamente (pior caso)**  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)  
- **Com muitos repetidos** (valores no intervalo `[0, k)`, padrão `k=100`)  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)  
- **Quase ordenado** (90% ordenado; embaralhamento controlado por trocas aleatórias)  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)  
- **Todos os elementos iguais** (um único valor fixo)  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)

Cada cenário é executado **100 vezes** e o programa exibe **médias** e verifica se o resultado está ordenado.  [1](https://sgapucminasbr-my.sharepoint.com/personal/1336147_sga_pucminas_br/Documents/Arquivos%20de%20Microsoft%20Copilot%20Chat/Main.java)

---

## 🖥️ Como Executar

### Pré-requisitos
- **Java 8+** instalado (JDK).

### Compilar

javac Main.java QuicksortHibrido.java QuicksortHibrido_C.java
