package me.Goldensang;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.util.Pair;

public class FComp extends JPanel{
	
	
	private static final long serialVersionUID = 1L;
	
	
	public void paint(Graphics g) {
		
		BufferedImage i1 = null;
		BufferedImage i2 = null;
		
		try {
			i1 = ImageIO.read(new File("D:\\background-day.png"));
			i2 = ImageIO.read(new File(("D:\\base.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Rectangle background = Main.main.background;
		Rectangle dirt = Main.main.dirt;
		
		g.drawImage(i1, background.x, background.y, Main.main.w, Main.main.h,null);
		g.drawImage(i2, dirt.x, dirt.y, dirt.width, dirt.height, null);
		
		BufferedImage pipeup = null;
		BufferedImage pipedown = null;
		
		try {
			pipeup = ImageIO.read(new File("D:\\pipe-greenfu.png"));
			pipedown = ImageIO.read(new File("D:\\pipe-greenfd.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		for(int i = 0;i < Main.main.pipe.size();i++) {
			Pipe p = Main.main.pipe.get(i);
			
			g.drawImage(pipedown, p.p1upx, p.p1upy, p.w, p.p1h, null);
			g.drawImage(pipeup, p.p2downx, p.p2downy, p.w, p.p2h, null);
		}
		BufferedImage bird = null;
		try {
			bird = ImageIO.read(new File("D:\\yellowbird-midflap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0;i < Main.main.bird.size();i++) {
			Pair<Rectangle, Integer> bi = Main.main.bird.get(i);
			if(Main.main.idx.contains(i)) continue;
			Rectangle b = bi.getKey();
			g.drawImage(bird, b.x, b.y, b.width, b.height, null);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		if(!Main.main.gameOver) {
			g.setFont(new Font("Arial", 1, 30));
			g.drawString("Population: " + String.valueOf(Main.main.bird.size() - Main.main.idx.size()), 50, 50);
			g.drawString("Gen:  " + String.valueOf(Main.main.gen), 50, 100);
			
		}
		
		
	}

	
}
