package main;

import edu.princeton.cs.algs4.*;

import java.util.Iterator;

/********************************************************
 *
 *  Project :  <A06 WordNet>
 *  File    :  <SAP.java>
 *  Name    :  <Alfredo Rodriguez && Salvador Gutierrez>
 *  Date    : <4/23/17>
 * 	Class	: CSIS 2420
 * 	Teacher	: Gene Riggs
 *	Description: Creates the BFS and verifies info within
 *				 The digraph such as: Out of bounds, length
 *				 etc.
 *
 ********************************************************/

public class SAP
{

	//Fields
	private final Digraph digraph;
	private final BFSCache vcache, wcache;

	/**
	 * New BFS class which checks through the hash map and digraph in WordNet
	 */
	private class BFSCache implements Iterable<Integer> {

		//Fields
		private final boolean[] visited;
		private final int[] distanceTo;
		private final Queue<Integer> modified = new Queue<Integer>();
		private final Queue<Integer> queue = new Queue<Integer>();

		/**
		 * Constructor
		 * @param size
		 * Checks every vertex in the queue to check if has been visited or not
		 */
		public BFSCache(int size) {
			visited = new boolean[size];
			distanceTo = new int[size];

			for (int i = 0; i < size; i++) {
				visited[i] = false;
				distanceTo[i] = -1;
			}
		}

		/**
		 *
		 * @return
		 */
		public Iterator<Integer> iterator() {
			return modified.iterator();
		}

		/**
		 * Clears any vertex not visited
		 */
		public void clear() {
			while (!modified.isEmpty()) {
				int v = modified.dequeue();
				visited[v] = false;
				distanceTo[v] = -1;
			}
		}

		/**
		 *
		 * @param v spot that the vertex is checking at the moment
		 *          If visited the its added to the queue if not its left alone
		 */
		public void BFS(int v) {
			visited[v] = true;
			distanceTo[v] = 0;

			modified.enqueue(v);
			queue.enqueue(v);

			while (!queue.isEmpty()) {
				int w = queue.dequeue();

				for (int next : digraph.adj(w)) {
					if (!visited[next]) {
						visited[next] = true;
						distanceTo[next] = distanceTo[w] + 1;
						modified.enqueue(next);
						queue.enqueue(next);
					}
				}
			}
		}

		/**
		 *
		 * @param v
		 */
		public void BFS(Iterable<Integer> v) {
			for (int w : v) {
				visited[w] = true;
				distanceTo[w] = 0;

				modified.enqueue(w);
				queue.enqueue(w);
			}

			while (!queue.isEmpty()) {
				int w = queue.dequeue();

				for (int next : digraph.adj(w)) {
					if (!visited[next]) {
						visited[next] = true;
						distanceTo[next] = distanceTo[w] + 1;
						modified.enqueue(next);
						queue.enqueue(next);
					}
				}
			}
		}

		/**
		 *
		 * @param v
		 * @return whether the is has been visted in the DAG
		 */
		public boolean isDAG(int v) {
			return visited[v];
		}

		/**
		 *
		 * @param v
		 * @return check whats the distance it is to the Root
		 */
		public int isRootedDAG(int v) {
			return distanceTo[v];
		}
	}

	/**
	 *
	 * @param G
	 */
	public SAP(Digraph G)
	{
		this.digraph = new Digraph(G);
		this.vcache = new BFSCache(G.V());
		this.wcache = new BFSCache(G.V());
	}

	/**
	 *
	 * @param v
	 * @param w
	 * @return precalculates the
	 */
	public int length(int v, int w)
	{
		preCalculations(v, w);

		return findDistance();
	}

	/**
	 *
	 * @param v
	 * @param w
	 * @return Precaculates all the info said below and finds the ancestor
	 */
	public int ancestor(int v, int w)
	{
		preCalculations(v, w);

		return findAncestor();

	}

	/**
	 *
	 * @param v
	 * @param w
	 * @return Precaculates all the info said below and finds the length
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w)
	{
		preCalculations(v, w);

		return findAncestor();
	}

	/**
	 *
	 * @param v
	 * @param w
	 * @return Precaculates all the info said below and finds the ancestor
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
	{
		preCalculations(v, w);

		return findAncestor();
	}

	/**
	 *
	 * @return the distance of the vertex
	 * Checks the DAG and the root based up on it
	 */
	private int findDistance() {
	int result = -1;
	BFSCache[] caches = { vcache, wcache };

	for (BFSCache cache : caches) {
		for (int v : cache) {
			if (vcache.isDAG(v) && wcache.isDAG(v)) {
				int distance = vcache.isRootedDAG(v) + wcache.isRootedDAG(v);

				if (result == -1 || distance < result) {
					result = distance;
				}
			}
		}
	}

	return result;
}

	/**
	 *
	 * @return the ancestors if not found then left alone
	 */
	private int findAncestor() {
		int minDistance = -1;
		int ancestor = -1;
		BFSCache[] caches = { vcache, wcache };

		for (BFSCache cache : caches) {
			for (int v : cache) {
				if (vcache.isDAG(v) && wcache.isDAG(v)) {
					int distance = vcache.isRootedDAG(v) + wcache.isRootedDAG(v);

					if (minDistance < 0 || distance < minDistance) {
						minDistance = distance;
						ancestor = v;
					}
				}
			}
		}

		return ancestor;
	}

	/**
	 *
	 * @param v
	 * @param w
	 * Same deal as the iterable
	 */
	private void preCalculations(int v, int w) {
		verifyInput(v);
		verifyInput(w);

		vcache.clear();
		wcache.clear();

		vcache.BFS(v);
		wcache.BFS(w);
	}

	/**
	 *
	 * @param v
	 * @param w
	 * Returns the the BFS, verifies that the input is valid
	 * and the it clears anything the cache
	 */
	private void preCalculations(Iterable<Integer> v, Iterable<Integer> w) {
		verifyInput(v);
		verifyInput(w);

		vcache.clear();
		wcache.clear();

		vcache.BFS(v);
		wcache.BFS(w);
	}

	/**
	 *
	 * @param v int
	 *          Verifies that the vertex is not out of bounds
	 */
	private void verifyInput(int v) {
		if (v < 0 || v >= digraph.V())
			throw new java.lang.IndexOutOfBoundsException();
	}

	/**
	 *
	 * @param v
	 * checks that the iterable is not out bounds either
	 */
	private void verifyInput(Iterable<Integer> v) {
		for (int w : v) {
			verifyInput(w);
		}
	}

	/**
	 *
	 * @param args
	 * checks the file and returns the length and ancestor of those specified
	 *
	 */
	public static void main(String[] args) {
		String filename = "digraph1.txt";

		In in = new In(filename);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);

		int length = sap.length(3, 11);
		int ancestor = sap.ancestor(3, 11);
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

		length = sap.length(7, 2);
		ancestor = sap.ancestor(7, 2);
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

		length = sap.length(1, 6);
		ancestor = sap.ancestor(1, 6);
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

		Queue<Integer> v = new Queue<Integer>();
		v.enqueue(11);
		v.enqueue(12);

		Queue<Integer> w = new Queue<Integer>();
		w.enqueue(7);
		w.enqueue(8);
	}
}
