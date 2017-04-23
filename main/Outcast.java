package main;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/********************************************************
 *
 *  Project :  <A06 WordNet>
 *  File    :  <Outcast.java>
 *  Name    :  <Alfredo Rodriguez && Salvador Gutierrez>
 *  Date    : <4/23/17>
 * 	Class	: CSIS 2420
 * 	Teacher	: Gene Riggs
 *	Description:
 *
 ********************************************************/

public class Outcast {

	//Fields
	private final WordNet wordNet;

	/**
	 *
	 * @param wordNet returns a new iteration of wordnet
	 */
	public Outcast(WordNet wordNet) {
		this.wordNet = wordNet;
	}

	/**
	 *
	 * @param nouns
	 * @return the distances of the nouns and checks if any iteration are out of the loop
	 */
	public String outcast(String[] nouns) {
		int[][] distances = new int[nouns.length][nouns.length];

		for (int i = 0, sz = nouns.length; i < sz; i++) {
			for (int j = i + 1; j < sz; j++) {
				distances[i][j] = wordNet.distance(nouns[i], nouns[j]);
			}
		}

		return nouns[findMaximumDistance(distances)];
	}

	/**
	 *
	 * @param distances
	 * @return the maximum distance of the result found
	 */
	private int findMaximumDistance(int[][] distances) {
		int result = 0, maximumDistance = 0, sum = 0;

		for (int i = 0, sz = distances.length; i < sz; i++) {
			sum = 0;

			for (int j = 0, sz2 = distances[i].length; j < sz2; j++) {
				if (j < i) {
					sum += distances[j][i];
				} else {
					sum += distances[i][j];
				}

				if (i == 0 || sum > maximumDistance) {
					maximumDistance = sum;
					result = i;
				}
			}
		}

		return result;
	}

	/**
	 *
	 * @param args
	 * runs the files and checks through every iteration
	 */
	public static void main(String[] args) {
		WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
		Outcast outcast = new Outcast(wordnet);

		for (int t = 2; t < args.length; t++) {
			String[] nouns = In.readStrings(args[t]);
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}