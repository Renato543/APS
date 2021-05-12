package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Lixo {
	private Rectangle colisor;
	SpriteBatch batch;
	Texture img;
	private Sprite sprite;
	Random num = new Random();
	float xx, yy;
	float speed = 1;
	private ShapeRenderer shape;
	private int angle;
	private int velangle;
	
	// Tipo de lixo 1 - Papel, 2 - Plasticos, 3 - Vidros, 4 - Metais
	private int tipo = 1;
	private String imagem;
	
	private String[] imagens = new String[3];	
	
	// Efeito Sonoro
	private Sound errado;
	private Sound certo;
	
	public void Criar() { 
		errado = Gdx.audio.newSound(Gdx.files.internal("Efeitos/Errado.wav"));	
		tipo = num.nextInt(5 - 1) + 1;
		shape = new ShapeRenderer();
		colisor = new Rectangle();
		img = new Texture(tipoLixo());
		sprite = new Sprite(img);
		batch = new SpriteBatch();
		yy = Gdx.graphics.getHeight();
		int ww = Gdx.graphics.getWidth();
		int soma = (int)((ww - sprite.getWidth()) - sprite.getWidth());
		int xxx = num.nextInt(soma) + 32;
		xx = xxx;
		int min = 2;
		int max = 5;
		speed = num.nextInt(max - min) + min;
	}
	
	public String tipoLixo() { 
			switch(tipo) {
				case 1:
					//Papel
					imagens[0] = "Lixos/Papel-Jornal.png";
					imagens[1] = "Lixos/Papel-PapelAmassado.png";
					imagens[2] = "Lixos/Papel-Papelao.png";
					certo = Gdx.audio.newSound(Gdx.files.internal("Efeitos/Papel.mp3"));
				break;
							
				case 2:
					//Plastico
					imagens[0] = "Lixos/Plastico-Garrafa.png";
					imagens[1] = "Lixos/Plastico-Copo.png";
					imagens[2] = "Lixos/Plastico-Sacola.png";
					certo = Gdx.audio.newSound(Gdx.files.internal("Efeitos/Plastico.mp3"));
				break;
					
				case 3:
					// Vidros
					imagens[0] = "Lixos/Vidro-Espelho.png";
					imagens[1] = "Lixos/Vidro-Garrafa.png";
					imagens[2] = "Lixos/Vidro-Taca.png";
					certo = Gdx.audio.newSound(Gdx.files.internal("Efeitos/Vidro.mp3"));
				break;
					
				case 4:
					// Metais
					imagens[0] = "Lixos/Metal-Lata.png";
					imagens[1] = "Lixos/Metal-Faca.png";
					imagens[2] = "Lixos/Metal-ChaveFixa.png";
					certo = Gdx.audio.newSound(Gdx.files.internal("Efeitos/Metal.mp3"));
				break;
				
			}
			return imagens[num.nextInt(3)];
	}
	
	public void Desenhar() { 
		yy -= speed;
		
		if(yy < 0) { 
			
			if(sprite != null ) {
				long id = errado.play(.8f);
				errado.setLooping(id, false);
				MyGdxGame.jogador.decreaseVida();
				MyGdxGame.jogador.DefinirTremer(50, 8);
			}
			sprite = null;
		}
		if(sprite != null) {
			float ww = sprite.getWidth();
			float hh = sprite.getHeight();
			
			
			colisor.width = ww;
			colisor.height = hh;
			colisor.x = xx;
			colisor.y = yy;
			
			
			
			angle += speed;
			batch.setProjectionMatrix(MyGdxGame.jogador.camera.combined);
			batch.begin();
			batch.draw(sprite, xx, yy, ww/2, hh/2, ww, hh, 1, 1, angle);
			
			batch.end();
		}
		
	}
	
	public void Colidiu(Rectangle player) { 
		
		if(colisor.overlaps(player) && sprite != null) {
			sprite = null;
			MyGdxGame.jogador.scalex = 1.5f;
			MyGdxGame.jogador.scaley = .5f;
			if(MyGdxGame.jogador.getTipo() == tipo) {
				// Acertou o lixo
				long id = certo.play(.75f);
				certo.setLooping(id, false);
				MyGdxGame.jogador.IncrementPoint();	
				
			}
			else { 
				// Não acertou o lixo
				long id = errado.play(.8f);
				errado.setLooping(id, false);
				MyGdxGame.jogador.decreaseVida();
				MyGdxGame.jogador.DefinirTremer(50, 8);
				
			}			
		}
	}
	
	public void Finalizar()  {
		img.dispose();
		batch.dispose();	
	}	
}
