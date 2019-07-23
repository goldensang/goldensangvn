package me.Goldensang;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.Timer;

import javafx.util.Pair;

public class Main implements ActionListener{
	
	public int cnt = 0;
	public boolean gameOver;
	public static Main main;
	public HashMap<Integer,Boolean> map;
	public int gen = 0;
	boolean bo = true;
	
	private Timer t;
	
	public final int dist = 300;
	public int tick;
	
	public Rectangle background, dirt;
	
	public ArrayList<Pair<Rectangle,Integer>> bird;
	public ArrayList<Pair<NeuralNetwork, Integer>> id; 
	public ArrayList<Integer> score, gravScale, idx;
	public ArrayList<Pair<NeuralNetwork,Integer>> finest_gen;
	public JFrame j;
	
	public ArrayList<Pipe> pipe;	
	public FComp fc;
	private int xpos;
	private boolean firstTime = true;
	public final int w = 800, h = 800;
	private int population = 200;
	public NeuralNetwork finest;
	
	public void genbird(int amount) {
		finest_gen = new ArrayList<Pair<NeuralNetwork, Integer>>();
		for(int i = 0;i < amount;i++) {
			Rectangle tmp = new Rectangle(w / 2 - 100, h / 2 - 100, 50, 40);
			bird.add(new Pair<>(tmp, 0));
			id.add(new Pair<>(new NeuralNetwork(tmp, true), 0));
			gravScale.add(0);
			score.add(0);
		}

		j = new JFrame();

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setVisible(true);
		j.setSize(w, h);
		j.setResizable(false);
	}
	public void replicate(NeuralNetwork finest) {
		bird = new ArrayList<Pair<Rectangle,Integer>>();
		id = new ArrayList<Pair<NeuralNetwork, Integer>>();
		for(int i = 0; i < population;i++) {
			Rectangle tmp = new Rectangle(w / 2 - 100, h / 2 - 100, 50, 40);
			bird.add(new Pair<>(tmp, 0));
			id.add(new Pair<>(new NeuralNetwork(tmp, true), 0));
			gravScale.add(0);
			score.add(0);
		}
		finest_gen.sort(new Comparator<Pair<NeuralNetwork, Integer>>(){
			@Override
			public int compare(Pair<NeuralNetwork, Integer> o1, Pair<NeuralNetwork, Integer> o2) {
				return o1.getValue() > o2.getValue() ? -1 : (o1.getValue() < o2.getValue()) ? 1 : 0;
			}
			
		});
		int k = Math.min(3, finest_gen.size());
		id.set(0, new Pair<>(finest_gen.get(0).getKey(), 0));
		for(int i = 1;i < k;i++)
			id.set(i, new Pair<>(finest_gen.get(i).getKey(), 0));
		int cnt = 0;
		for(int i = k;i < 2 * k;i++) {
			NeuralNetwork tmp = finest_gen.get(cnt).getKey();
			tmp.mutateweight();
			id.set(i, new Pair<>(tmp, 0));
			cnt++;
		}
		for(int i = 2 * k;i < 4 * k;i++) {
			NeuralNetwork tmp  = id.get((int) Math.random() * 4).getKey();
			for(int j = 0; j < Math.random() * i;j++)
				tmp.crossOver(finest_gen.get((int) Math.random() * finest_gen.size()).getKey());
			id.set(i, new Pair<>(tmp, 0));
		}
		for(int i = 4 * k;i < population;i++) {
			NeuralNetwork tmp = id.get(i).getKey();
			tmp.crossOver(id.get((int) Math.random() * 4 * k).getKey());
			tmp.mutateweight();
			id.set(i, new Pair<>(tmp, 0));
		}
	}
	public void findBest() {
		int max = 0;
		int pmax = -1;
		for(int i = 0;i < population;i++) {
			if(score.get(i) > max) {
				max = score.get(i);
				pmax = i;
			}
		}
		finest = id.get(pmax).getKey();
		finest_gen.add(new Pair<>(finest, max));
	}
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
	public Main() {
		this.init();
	}
	public static void main(String[] args) {
		main = new Main();
	}
	
	public void addY(int index, int val) {
		Rectangle b = bird.get(index).getKey();
		b.y += val;
	}
	
	public void decY(int index, int val) {
		Rectangle b = bird.get(index).getKey();
		b.y -= val;
	}
	
	public void repaint() {
		j.repaint();
		
	}
	
	public void setPipe(ArrayList<Pipe> p) {
		this.pipe = p;
	}

	public void addPipe() {
		int x = (int) (Math.random() * 300) + 1;
		Pipe last, p;
		cnt++;
		if(this.pipe.size() == 0) {
			last = new Pipe(this.w,100 + x,this.h - 150, 100, cnt);
			p = last;
		}else {
			last = this.pipe.get(this.pipe.size() - 1);
			p = new Pipe(last.p1upx + 400, 100 + x, this.h - 150 ,100, cnt);
		}
		this.pipe.add(p);
	}
	
	public void moveUp(int index) {
		if(!this.gameOver) {
			int gravity = gravScale.get(index);
			if(gravity > 0) gravity = 0;
			gravity = -10;
			gravScale.set(index, gravity);
			decY(index, 20);
		}
		
	}
	public void stopGame() {
		t.stop();
		init();
	}
	
	public final int eps = 5;
	public int inter(Pipe p, Rectangle bird) {
		if(bird.y > h - dirt.height || bird.y < 0) return -1;
		if(bird.intersects(p.r1) || bird.intersects(p.r2)) return -1;
		if(bird.x + bird.width - eps >= p.p1upx && bird.x + bird.width <= p.p1upx + p.w) {
			if(map.containsKey(p.id)) return 0;
			map.put(p.id, true);
			return 1;
		}
		return 0;
		
	}
	public Pipe findClosest() {
		for(int i = 0;i < pipe.size();i++) {
			if(pipe.get(i).p1upx - xpos < 0 && pipe.get(i).p1upx + pipe.get(i).w - xpos < 0) continue;
			return pipe.get(i);
		}
		return null;
	}
	
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
}	