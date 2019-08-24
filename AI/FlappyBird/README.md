# Flappy bird

[What is Flappy Bird](https://en.wikipedia.org/wiki/Flappy_Bird)

[What is NEAT](https://en.wikipedia.org/wiki/Neuroevolution_of_augmenting_topologies)

[What is NeuralNetwork](https://en.wikipedia.org/wiki/Artificial_neural_network)
## Overview
I'm using NEAT - a Genetic Algorithm to make an AI that plays FlappyBird for me. I tweaked a lot of stuff and coded through my understanding of the algorithm itself.

## General Ideas


1. We will try to direct the "evolution" toward the goal that we want. In this case, it is the bird that we want to go through every settings of pipe that the game have to offer. I manipulated the algorithm a little bit, but the main structure is still NEAT
2. Let say that K is the amount of the most optimal genes that we have so far for each generations, **finest_genes** is an array that is sorted in descending order and contains the best set of genes that we have so far. This can be achieved by following these steps:

	- Choose the **K** of the most optimal genes, and then save a copy for each
	- Choose the **K** again, but this time, we mutate their connections a little bit
	- Simultaneously, we pick out the finest genes until we have collected all of them. We then cross them over with the better genes
	- Then we create a random population, cross them over with the random genes in **finest_genes**
	- Repeat until we have achieved the desire bird
3. In my case, i decided to choose **K** as 4

## Detail description

