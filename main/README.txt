/******************************************************************************
 *  Name: Alfredo Rodriguez
 *  Partner Name: Salvador Gutierrez
 *
 *  Hours to complete assignment (optional): 20
 *
 ******************************************************************************/


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/

Hash map and BFS to use as our symbol tables to verify the integration of the files
We made this choice because knowing how big this information is it handle it 
perfectly and precise in logarithmic timing

/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/

Hash map and BFS to use as our symbol tables to verify the integration of the files
We made this choice because knowing how big this information is it handle it 
perfectly and precise in logarithmic timing

/******************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithms as a function of the number of vertices V and the
 *  number of edges E in the digraph?
 *****************************************************************************/

Description: Well we created a BFS to check every vertex then we did a precalculations
method which checked whether the item was out of bounds, cleared any sort of cache stored before
and store it into the BFS. Then, it would use the precalculations methods to checks it at the ancestor,
length, ect to find those certain parameters


Order of growth of running time: N log N

/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. What is the order of growth of
 *  the running time of your methods as a function of the number of
 *  vertices V and the number of edges E in the digraph? What is the
 *  order of growth of the best-case running time?
 *
 *  If you use hashing, you should assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't
 *  forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)			N			N^2

ancestor(int v, int w)			N			N^2

length(Iterable<Integer> v,		NlogN			N^2
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,		NlogN			N^2
         Iterable<Integer> w)

/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/

Not any that I'm aware and no limitations either

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

Not much help other than Stackoverflow and Princeton algorithms

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/

N/A

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/

We both worked sidde by side and helped each other out at about 50%

/**********************************************************************
 *  Have you completed the mid-semester survey? If you haven't yet,
 *  please complete the brief mid-course survey at https://goo.gl/gB3Gzw
 * 
 ***********************************************************************/

No I have not will do so after I'm done

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

Assignment was difficult and challenging and it felt a little like the autocomplete