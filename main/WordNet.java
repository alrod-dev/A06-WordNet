package main;

import edu.princeton.cs.algs4.*;

import java.util.HashMap;

/********************************************************
 *
 *  Project :  <A06 WordNet>
 *  File    :  <WordNet.java>
 *  Name    :  <Alfredo Rodriguez && Salvador Gutierrez>
 *  Date    : <4/23/17>
 * 	Class	: CSIS 2420
 * 	Teacher	: Gene Riggs
 *	Description: Creates a hash map that read in the Synsets
 *				 Hypernyms
 *
 ********************************************************/

public class WordNet
{

	/**
	 * Fields
	 */
	private final SAP sap;
	private final HashMap<Integer, String> id2synset;
	private final HashMap<String, Bag<Integer>> noun2ids;

	/**
	 *
	 * @param synsets = reads in a new synset
	 * @param hypernyms = reads in a new Hypernyms
	 *
	 */
	public WordNet(String synsets, String hypernyms)
	{
		id2synset = new HashMap<Integer, String>();
		noun2ids = new HashMap<String, Bag<Integer>>();

		readSynsets(synsets);
		readHypernyms(hypernyms);

		sap = new SAP(readHypernyms(hypernyms));
	}

	/**
	 *
	 * @param synsetsFile == Reads a synset file and loots at every iteration
	 */
	private void readSynsets(String synsetsFile)
	{
		In input = new In(synsetsFile);
		Bag<Integer> bag;

		while (input.hasNextLine())
		{
			String[] tokens = input.readLine().split(",");
			int id = Integer.parseInt(tokens[0]);
			id2synset.put(id, tokens[1]);

			for (String noun : tokens[1].split(" "))
			{
				bag = noun2ids.get(noun);

				if (bag == null)
				{
					bag = new Bag<Integer>();
					bag.add(id);
					noun2ids.put(noun, bag);
				} else
				{
					bag.add(id);
				}
			}
		}
	}

	/**
	 *
	 * @param hypernymsFile = reads a new Hypernym file
	 * @return the file reads with all iterations
	 */
	private Digraph readHypernyms(String hypernymsFile)
	{
		Digraph digraph = new Digraph(id2synset.size());
		In input = new In(hypernymsFile);

		while (input.hasNextLine())
		{
			String[] tokens = input.readLine().split(",");
			int id = Integer.parseInt(tokens[0]);
			for (int i = 1, sz = tokens.length; i < sz; i++)
			{
				digraph.addEdge(id, Integer.parseInt(tokens[i]));
			}
		}

		verifyCycle(digraph);
		verifyRoot(digraph);

		return digraph;
	}

	/**
	 *
	 * @param digraph verifies the new digraph
	 *             	  checks whether it has any illegal exceptions
	 */
	private void verifyCycle(Digraph digraph)
	{
		DirectedCycle directedCycle = new DirectedCycle(digraph);

		if (directedCycle.hasCycle())
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 *
	 * @param digraph verifies the root of the digraph
	 *
	 */
	private void verifyRoot(Digraph digraph)
	{
		int roots = 0;

		for (int i = 0, sz = digraph.V(); i < sz; i++)
		{
			if (!digraph.adj(i).iterator().hasNext())
			{
				roots += 1;
			}
		}

		if (roots != 1)
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 *
	 * @return a hash map of the key sets
	 */
	public Iterable<String> nouns()
	{
		return noun2ids.keySet();
	}

	/**
	 *
	 * @param word checks a new iteration of word
	 * @return a hash map of that word contained within the file
	 */
	public boolean isNoun(String word)
	{
		return noun2ids.containsKey(word);
	}

	/**
	 *
	 * @param nounA passes first noun
	 * @param nounB passes second dnoun
	 * @return the lenght of the words and checks the distance
	 */
	public int distance(String nounA, String nounB)
	{
		verifyNoun(nounA);
		verifyNoun(nounB);

		return sap.length(noun2ids.get(nounA), noun2ids.get(nounB));
	}

	/**
	 *
	 * @param nounA
	 * @param nounB
	 * @return any ancestor pertaining to those nouns
	 */
	public String sap(String nounA, String nounB)
	{
		verifyNoun(nounA);
		verifyNoun(nounB);

		return id2synset.get(sap.ancestor(noun2ids.get(nounA), noun2ids.get(nounB)));
	}

	/**
	 *
	 * @param noun verifies that it is noun
	 */
	private void verifyNoun(String noun)
	{
		if (!isNoun(noun))
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 *
	 * @param args checks whether the test were made correctly and reads in the files
	 */
	public static void main(String[] args)
	{
		check1();
		check2();
	}

	/**
	 * Checks the size of the synsets and hypernyms
	 * and checks whether the ancestors to return
	 * Works very good!
	 */
	private static void check1() {
		WordNet w = new WordNet("synsets.txt", "hypernyms.txt");

		// isNoun
		assert w.isNoun("1750s");
		assert w.isNoun("Ab");
		assert w.isNoun("Aberdare");
		assert w.isNoun("Abkhaz");
		assert w.isNoun("party_line");

		assert !w.isNoun("Abkhaz Abkhazian Abkhas Abkhasian");
		assert !w.isNoun("asdfasdf;lkwqejrw");

		// nouns
		assert w.nouns() != null;
		int size = 0;
		for (@SuppressWarnings("unused")
				String noun : w.nouns()) {
			size++;
		}
		assert size > 82191;

		// distance
		assert w.distance("Black_Plague", "black_marlin") == 33;
		assert w.distance("American_water_spaniel", "histology") == 27;
		assert w.distance("Brown_Swiss", "barrel_roll") == 29;

		// sap
		assert w.sap("municipality", "region").equals("region");
		assert w.sap("individual", "edible_fruit").equals("physical_entity");
	}

	/**
	 * Checks the illegal cycles and the Invalid roots which throw illegal arguments
	 */
	private static void check2() {
		try {
			new WordNet("synsets.txt", "hypernymsInvalidCycle.txt");
			assert false;
		} catch (IllegalArgumentException e) {
			System.err.println(e);
		}

		try {
			new WordNet("synsets.txt", "hypernymsInvalidTwoRoots.txt");
			assert false;
		} catch (IllegalArgumentException e) {
			System.err.println(e);
		}
	}


}
