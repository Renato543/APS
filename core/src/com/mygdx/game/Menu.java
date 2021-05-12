package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Menu {
	
	private Texture img;
	private Texture imgFundo;
	private Sprite spriteFundo;
	private Sprite sprite;
	private Sprite spriteGameOver;
	private SpriteBatch batch;
	public static Rectangle colisorMouse;
	private Rectangle colisorBotao;
	public static boolean start = false;
	private float xscale = 1;
	private float yscale = 1;
	private float rotation = 0;
	public static int tela = 0;
	public boolean trocou = false;
	private Music musica;
	private BitmapFont font;
	
		
	
	public void Criar() { 
		font = new BitmapFont();
		font.getData().setScale(2);
		musica = Gdx.audio.newMusic(Gdx.files.internal("Musicas/SilentWalk.mp3"));
		musica.setVolume(.15f);
		musica.setLooping(true);
		
		colisorBotao = new Rectangle();
		colisorMouse = new Rectangle();
		colisorMouse.width = 1;
		colisorMouse.height = 1;
		imgFundo = new Texture("Cenario/Cidade.png");
		spriteFundo = new Sprite(imgFundo);
		img = new Texture("Menu/btnPlay.png");
		sprite = new Sprite(img);
		img = new Texture("Menu/GAMEOVER.png");
		spriteGameOver = new Sprite(img);
		colisorBotao.width = img.getWidth();
		colisorBotao.height = img.getHeight();
		batch = new SpriteBatch();
	}
	
	public void Desenhar() { 
		float ww = 0, hh = 0, xx = 0, yy = 0, wwg, hhg, xxg, yyg;
		ww = sprite.getWidth() * xscale;
		hh = sprite.getHeight() * yscale;
		wwg = spriteGameOver.getWidth();
		hhg = spriteGameOver.getHeight();
		xxg = Gdx.graphics.getWidth()/2 - wwg/2;
		yyg = Gdx.graphics.getHeight()* 3/4 - hhg/2; 
		
		
		boolean mouse_hover = colisorMouse.overlaps(colisorBotao);
		boolean mouse_click = Gdx.input.justTouched();

		// Menu inicial
		if(tela == 0) {
			if(!trocou) {
				spriteFundo.setTexture(new Texture("Cenario/Inicial.png"));
				sprite.setTexture(new Texture("Menu/btnPlay.png"));
				trocou = true;
			}
			xx = Gdx.graphics.getWidth()/2 - ww/2;
			yy = Gdx.graphics.getHeight()/2 - hh/2;
			
			
			
		}
		else if(tela == 1) {
			if(!trocou) {
				spriteFundo.setTexture(new Texture("Cenario/Cidade.png"));
				trocou = true;
			}
		}
		else if(tela == 2) { 
			if(!trocou) {
				spriteFundo.setTexture(new Texture("Cenario/FundoGameOver.png"));
				sprite.setTexture(new Texture("Menu/btnPlayAgain.png"));
				trocou = true;
			}
			musica.stop();
			
			
			xx = Gdx.graphics.getWidth()/2 - ww/2;
			yy = Gdx.graphics.getHeight()/2 - hh/2;
			
			//Tela de game over
			
			
		}
		
		if(mouse_hover) { 
			if(mouse_click) {
				trocou = false;
				if(tela == 0) {
					musica.play();
					tela = 1;
				}
				else if(tela == 2) {
					Resetar();
					tela = 0;
				}
				
			}
			if(xscale < 1.1) {
				xscale += (1.1 - 1) * .1;
				yscale += (1.1 - 1) * .1;
			}
		}
		else { 
			if(xscale > 1) {
				xscale -= (1.1 - 1) * .1;
				yscale -= (1.1 - 1) * .1;
			}
			
		}
		
		colisorMouse.x = Gdx.input.getX();
		colisorMouse.y = Gdx.input.getY();
		colisorBotao.x = xx;
		colisorBotao.y = yy;
		
		batch.setProjectionMatrix(MyGdxGame.jogador.camera.combined);
		batch.begin();
		if(tela == 0 || tela == 2) batch.draw(sprite, xx, yy, xx, yy, ww, hh, xscale, yscale, rotation);
		if (tela == 2) {
			font.setColor(Color.BLACK);
			font.draw(batch,"Pontos: " + MyGdxGame.jogador.getPoint(), Gdx.graphics.getWidth()/2 - font.getRegion().getRegionWidth()/2,  1 *Gdx.graphics.getHeight()/3);
			batch.draw(spriteGameOver, xxg, yyg);
		}
		batch.end();
	}
	
	public void Resetar() { 
		MyGdxGame.levels.delayatual = 120;
		MyGdxGame.levels.delay = 120;
		MyGdxGame.jogador.tremer = false;
		MyGdxGame.levels.possivel = 0;
		MyGdxGame.jogador.setPoint(0);
		MyGdxGame.lixo.Criar();
		MyGdxGame.lixo.xx = 64;
		MyGdxGame.levels.Quantidade();
		MyGdxGame.levels.criaLixeira();
		
	}
	
	public void DesenharFundo()  {
		batch.begin();
		batch.draw(spriteFundo, 0, 0);
		batch.end();
	}
	
	public void Finalizar() { 
		
		img.dispose();
		batch.dispose();
		
		
	}
}
