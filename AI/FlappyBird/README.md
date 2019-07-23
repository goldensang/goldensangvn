## Update Later!
Im using N.E.A.T - a Genetic Algorithm to make an AI that play FlappyBird for me, i tweaked a lot of stuff and code through my understanding of the 
## Reminder
If you want to test the code your self, make sure to change the pakages name and the file directory in FComp.java 

## General Ideas

* We will try to direct the "evolution" arrow toward the goal that we want, in this case is the bird can go through every settings of pipe that the game have to offer. I manipulated the algorithm a bit, but the main structure is still N.E.A.T. 

* Let's say **K** is the amount of best genes that we have so far for each generations, **finest_genes** is an Array that is sorted in descending oders and contains the best set of genes that we have so far . I did the following steps:

    * Pick **K** of the best genes, save the copy of them.
    * Pick another **K** of the best genes, but this time, we mutate their connections a bit
    * Simultaneously pick out of the finest_genes until you've picked all of them, crossover them with better genes (can crossover multiple better genes)
    * Then create an random population, crossover them with a random genes in **finest_genes**
    * Repeat until we achieved the desired bird
* I choosed K is 4 in my program
