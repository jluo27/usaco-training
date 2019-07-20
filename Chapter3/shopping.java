/*
ID: justin.40
TASK: shopping
LANG: JAVA
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
public class shopping {
	
	/* 
	 * General approach: 5d DP, tedious to code but faster than 5 digit numbers
	 * O(I^L * S) where I is # item types, L is max # items, S is # special offers
	*/
	
	public static int solveShopping(int s, int[][] offers,
		HashMap<Integer,Integer> codeToIndex, int b, int[][] groceryList) {

		// target indices
		int target0 = groceryList[0][1]; 
		int target1 = groceryList[1][1];
		int target2 = groceryList[2][1];
		int target3 = groceryList[3][1];
		int target4 = groceryList[4][1];

		int[][][][][] dp = new int[6][6][6][6][6];
		for (int i0=0; i0<6; i0++) {
			for (int i1=0; i1<6; i1++) {
				for (int i2=0; i2<6; i2++) {
					for (int i3=0; i3<6; i3++) {
						for (int i4=0; i4<6; i4++) {
							dp[i0][i1][i2][i3][i4] = Integer.MAX_VALUE;
						}
					}
				}
			}
		}
		dp[0][0][0][0][0] = 0;
		
		// SPECIAL OFFERS

		for (int i0=0; i0<=target0; i0++) {
			for (int i1=0; i1<=target1; i1++) {
				for (int i2=0; i2<=target2; i2++) {
					for (int i3=0; i3<=target3; i3++) {
						for (int i4=0; i4<=target4; i4++) {
							dp[i0][i1][i2][i3][i4] = Math.min(dp[i0][i1][i2][i3][i4], i0*groceryList[0][2] + i1*groceryList[1][2] 
									+ i2*groceryList[2][2] + i3*groceryList[3][2] + i4*groceryList[4][2]);
							for (int i=0; i<s; i++) {
								int j0 = i0 + offers[i][0];
								int j1 = i1 + offers[i][1];
								int j2 = i2 + offers[i][2];
								int j3 = i3 + offers[i][3];
								int j4 = i4 + offers[i][4];
								if (j0>target0 || j1>target1 || j2>target2 || j3>target3 || j4>target4) continue;
								int newPrice = dp[i0][i1][i2][i3][i4] + offers[i][5];
								dp[j0][j1][j2][j3][j4] = Math.min(dp[j0][j1][j2][j3][j4], newPrice);
							}
						}
					}
				}
			}
		}

		// GENERAL OFFERS

		for (int i0=0; i0<6; i0++) {
			for (int i1=0; i1<6; i1++) {
				for (int i2=0; i2<6; i2++) {
					for (int i3=0; i3<6; i3++) {
						for (int i4=0; i4<6; i4++) {
							int purchase = dp[i0][i1][i2][i3][i4];
							if (i0!=5) {
								dp[i0+1][i1][i2][i3][i4] = Math.min(dp[i0+1][i1][i2][i3][i4], purchase + groceryList[0][2]);
							}
							if (b==1) break;
							if (i1!=5) {
								dp[i0][i1+1][i2][i3][i4] = Math.min(dp[i0][i1+1][i2][i3][i4], purchase + groceryList[1][2]);
							}
							if (b==2) break;
							if (i2!=5) {
								dp[i0][i1][i2+1][i3][i4] = Math.min(dp[i0][i1][i2+1][i3][i4], purchase + groceryList[2][2]);
							}
							if (b==3) break;
							if (i3!=5) {
								dp[i0][i1][i2][i3+1][i4] = Math.min(dp[i0][i1][i2][i3+1][i4], purchase + groceryList[3][2]); 
							}
							if (b==4) break;
							if (i4!=5) {
								dp[i0][i1][i2][i3][i4+1] = Math.min(dp[i0][i1][i2][i3][i4+1], purchase + groceryList[4][2]); 
							}
							if (i0==4 && i1==4 && i2==4 && i3==4 && i4==4) System.out.println("Kalamazoo");
						}
					}
				}
			}
		}
		
		return dp[target0][target1][target2][target3][target4];

	}
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("shopping.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		int s = Integer.parseInt(f.readLine());
		String[] offersInit = new String[s];
		for (int i=0; i<s; i++) {
			offersInit[i] = f.readLine();
		}
		int b = Integer.parseInt(f.readLine());
		int[][] groceryList = new int[5][3];
		for (int i=0; i<b; i++) {
			StringTokenizer st = new StringTokenizer(f.readLine());
			groceryList[i][0] = Integer.parseInt(st.nextToken());
			groceryList[i][1] = Integer.parseInt(st.nextToken());
			groceryList[i][2] = Integer.parseInt(st.nextToken());
		}
		if (b!=5) {
			for (int i=b; i<5; i++) {
				groceryList[i][1] = 0;
			}
		}

		HashMap<Integer,Integer> codeToIndex = new HashMap<Integer,Integer>();
		for (int i=0; i<b; i++) {
			codeToIndex.put(groceryList[i][0], i);
		}

		int[][] offers = new int[s][6];
		for (int i=0; i<s; i++) {
			StringTokenizer st = new StringTokenizer(offersInit[i]);
			int n = Integer.parseInt(st.nextToken());
			for (int j=0; j<n; j++) {
				int c = Integer.parseInt(st.nextToken());
				int k = Integer.parseInt(st.nextToken());
				offers[i][codeToIndex.get(c)] = k;
			}
			offers[i][5] = Integer.parseInt(st.nextToken());
		}
		
		int lowestPrice = solveShopping(s, offers, codeToIndex, b, groceryList);
		out.println(lowestPrice);
		f.close(); out.close();
	}
}
