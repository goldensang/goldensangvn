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

Let's start with the basic function
```java
public Main() {
		this.init();
	}
public static void main(String[] args) {
		main = new Main();
}	
```
Just a normal initilization, here we can see the main function point to **init()**
```java
public void init() {

		gen++;
		gravScale = new ArrayList<Integer>();
        
		if(firstTime) {
			bird = new ArrayList<Pair<Rectangle,Integer>>();
			id = new ArrayList<Pair<NeuralNetwork, Integer>>();
			score = new ArrayList<Integer>();
			firstTime = false;
			genbird(population);
		}else {
			findBest();
			score = new ArrayList<Integer>();
			replicate(finest);
		}
        
		idx = new ArrayList<Integer>();
		tick = 0;
		gameOver = false;
		xpos = w / 2 - 100;
		
		t = new Timer(5, this); // tempo of the game, lower = faster
		pipe = new ArrayList<Pipe>();
		map = new HashMap<Integer,Boolean>();
		
		
		background = new Rectangle(0 , 0, w, h);
		dirt = new Rectangle(0, h - 150, w, 150);

		fc = new FComp();
		j.add(fc);
		
		addPipe();
		addPipe();
		addPipe();
		addPipe();
		
		t.start();
	}
```
##### Varible description
- **gen**: number of generation
- **gravScale**: contain a gravity scale value for each bird
	+ gravity scale is basically a value that determine how fast the bird drop to the ground
	+ the higher the value the faster the bird falls
- **id**: contains the bird's id, and its neuralnetwork
- **bird**: contains the bird "physical body" and its score (the score which is suppose to be displayed on the screen)
- **score**: a.k.a the finest function, basically a duration value, the longer the bird lives the better its performance is
- **genbird()**: generate a population of birds
- **findbest()**: find the best bird which is described in the general idea section
- **pipe**: contains all the pipe 
- **map**: a marking array, it will tell if the bird has died or not. the key is the id of the bird and the value is the state
- **fcomp**: is a class that contains all of the graphic section of the game.

That is basically it, all are initilizations.



Since main.java **extends** ActionListener, it must has **actionPerformed()** build in it
```java
	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 10;
		tick++;
		for(int i = 0;i < id.size();i++) {
			if(!idx.contains(i)) 
				score.set(i, tick);
			if(tick % 2 == 0 && bird.get(i).getValue() < 15) 
				gravScale.set(i, gravScale.get(i) + 2);
			
		}
		Pipe closest = findClosest();
		for(int i = 0;i < bird.size();i++) {
			if(!idx.contains(i)) {
				NeuralNetwork n = id.get(i).getKey();
				n.update(bird.get(i).getKey(), closest, gravScale.get(i));
				if(n.jumpornot()) moveUp(i);
			}
		}
		for(int i = 0;i < pipe.size();i++) {
			Pipe p = this.pipe.get(i);
			p.transLeft(speed);
		}
		for(int i = 0;i < pipe.size();i++) {
			Pipe p = pipe.get(i);
			if(p.p1upx + p.w < 0) {
				pipe.remove(p);
				map.remove(p.id);
				addPipe();
			}
		}
		for(Pipe p : pipe) {
			for(int i = 0;i < bird.size();i++) {
				Pair<Rectangle, Integer> b = bird.get(i);
				if(idx.contains(i)) continue;
				int t = inter(p, b.getKey());
				if(t == -1) idx.add(i);
				else {
					b = new Pair<>(b.getKey(), b.getValue() + t);
				}
				cnt++;
			}
		}
		for(int i = 0;i < bird.size();i++) {
			if(idx.contains(i)) continue;
			addY(i, gravScale.get(i)); 
		}
		if(idx.size() == bird.size()) stopGame();
		this.repaint();
		
		
	}
````
##### Varible description
- **tick**: indicate how long has the game past

##### Code explanation
```java
for(int i = 0;i < id.size();i++) {
            if(!idx.contains(i)) 
                score.set(i, tick);
            if(tick % 2 == 0 && bird.get(i).getValue() < 15) 
                gravScale.set(i, gravScale.get(i) + 2);
            
        }
```
Loop through all of the bird, set their score to the current time, also increase their gravity scale

```java
  Pipe closest = findClosest();
  for(int i = 0;i < bird.size();i++) {
       if(!idx.contains(i)) {
            NeuralNetwork n = id.get(i).getKey();
            n.update(bird.get(i).getKey(), closest, gravScale.get(i));
            if(n.jumpornot()) moveUp(i);
          }
     }
```
In NeuralNetwork class we have a closest pipe value, so we update it here

```java
for(int i = 0;i < pipe.size();i++) {
     Pipe p = this.pipe.get(i);
     p.transLeft(speed);
  }
for(int i = 0;i < pipe.size();i++) {
     Pipe p = pipe.get(i);
     if(p.p1upx + p.w < 0) {
          pipe.remove(p);
          map.remove(p.id);
          addPipe();
      }
  }
```
We move the pipe to the left, and check whether the pipe has got out of the screen or not, if so we just simply remove it, add one more
```java
for(Pipe p : pipe) {
   for(int i = 0;i < bird.size();i++) {
  	     Pair<Rectangle, Integer> b = bird.get(i);
         if(idx.contains(i)) continue;
         int t = inter(p, b.getKey());
         if(t == -1) idx.add(i);
         else {
            b = new Pair<>(b.getKey(), b.getValue() + t);
         }
         cnt++;
     }
 }
```
We iterate all of the pipes and birds, check if they collide into each other or not, if so we just simply remove the bird from the game. Otherwise we add the score to the current bird, sometimes it is 0, sometimes it is 1 we will se in details in the **inter()** function.
```java
for(int i = 0;i < bird.size();i++) {
			if(idx.contains(i)) continue;
			addY(i, gravScale.get(i)); 
		}
if(idx.size() == bird.size()) stopGame();
this.repaint();
```
We loop through all of the bird, if it still alive we add some gravitational force to it. And check if all the bird had died or not, then we will call the **stopGame()** function which is restart the training process.


