/*
ID: justin.40
TASK: fact4
LANG: JAVA
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
public class fact4 {
	public static int solveFact4(int N) {
		// general idea: multiply all numbers after taking the 2s and 5s out
		// equivalent to the prime factorization of N with no 2s or 5s
		if (N==1) return 1;
		int digit = 1;
		for (int i=1; i<=N; i++) {
			int j = i;
			while (j%2==0) j/=2; 
			while (j%5==0) j/=5;
			digit = (digit*j)%10;
		}
		
		// then if the prime factorization of N contains 2^p and 5^q, we want to multiply 
		// the digit by 2^(p-q) 
		int powersOf2 = 0; int powersOf5 = 0;
		for (int i=1; i<=12; i++) {
			powersOf2 += N/Math.pow(2, i); 
		}
		for (int i=1; i<=5; i++) {
			powersOf5 += N/Math.pow(5, i);
		}
		
		int[] unitDigits = {6,2,4,8};
		digit = (digit*unitDigits[(powersOf2-powersOf5)%4])%10;
		return digit;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("fact4.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
		int N = Integer.parseInt(f.readLine());
		out.println(solveFact4(N));
		f.close(); out.close();
	}
}
