package org.mahout.examples.MusicRecommendation;

public class KMP {
    private String substring;
    private int[] next;

    public KMP(String substring) {
        this.substring = substring;
        int M = substring.length();
        next = new int[M];
        int j = -1;
        for (int i = 0; i < M; i++) {
            if (i == 0) next[i] = -1;
            else if (substring.charAt(i) != substring.charAt(j)) next[i] = j;
            else next[i] = next[j];
            while (j >= 0 && substring.charAt(i) != substring.charAt(j)) {
                j = next[j];
            }
            j++;
        }

        for (int i = 0; i < M; i++)
            System.out.println("next[" + i + "] = " + next[i]);
    }

    // return offset of first occurrence of text in pattern (or -1 if no match)
    public int search(String text) {
        int M = substring.length();
        int N = text.length();
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            while (j >= 0 && text.charAt(i) != substring.charAt(j))
                j = next[j];
            j++;
        }
        if (j == M) return i - M;
        return -1;
    }
}