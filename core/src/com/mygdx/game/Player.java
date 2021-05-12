package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.PixmapPackerRectangle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
	// Sprite/Animação --------------------------------------------------------
	SpriteBatch batch;
	Texture img;
	private Sprite sprite;
	Animation<TextureRegion> Animacao;
	private static final int Colunas = 2, Linhas = 2; 
	float Tempo;
	private float ww = 0;
	private float hh = 0;
	// ------------------------------------------------------------------------
	
	
	// Movimento --------------------------------------------------------------
	float xx = 0;
	float yy = 0;
	float hsp = 0;
	float vsp = 0;
	int speed = 8;
	float hspp = 0;
	float vspp = 0;
	float angle = 0;
	private final Vector2 mousePoint = new Vector2();
	public float scalex = 1;
	public float scaley = 1;
	// ------------------------------------------------------------------------
	
	// Mask
	Rectangle colisor = new Rectangle();
	//private ShapeRenderer shape;
	
	// Pontuação e vida -------------------------------------------------------
	Texture img3;
	private int vida = 5;
	private int points = 0;
	BitmapFont font;
	
	// ------------------------------------------------------------------------
	
	
	// Camera -----------------------------------------------------------------
	public static OrthographicCamera camera;
	private Vector3 position;
	private Random random = new Random();
	private int forcaTremer = 3;
	public static boolean tremer;
	private int tempoTremer = 60;
	
	// ------------------------------------------------------------------------
	
	// Mudança ----------------------------------------------------------------
	private int tipo = 1;
	private String imagem = "";
	// ------------------------------------------------------------------------
	
	// Aviso lixo -------------------------------------------------------------
	Texture img2;
	private String aviso = "";
	// ------------------------------------------------------------------------
	
	// Som --------------------------------------------------------------------
	private Sound troca;
	// ------------------------------------------------------------------------
	
	
	
	
	private void tiposLixeiros(int tipo) { 
		switch(tipo) { 
			case 1:
				// Papel
				imagem = "Jogador/Azul.png";
				aviso = "Avisos/PapelAviso.png";
			break;
			
			case 2:
				// Plástico
				imagem = "Jogador/Vermelho.png";
				aviso = "Avisos/PlasticoAviso.png";
			break;
			
			case 3:
				// Vidro
				imagem = "Jogador/Verde.png";
				aviso = "Avisos/VidroAviso.png";
			break;
			
			case 4:
				// Metal
				imagem = "Jogador/Amarelo.png";
				aviso = "Avisos/MetalAviso.png";
			break;
			
		
		}
		
	} 
	
	public void CriarCamera() { 
		float www = Gdx.graphics.getWidth();
		float hhh = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(www,hhh);
		position = new Vector3(0,0,0);
		camera.position.set(www/2, hhh/2, 0);
		camera.update();
		position = camera.position;
	}
	
	public void DefinirTremer(int tempo, int forca) { 
		tremer = true;
		tempoTremer = tempo;
		forcaTremer = forca;
			
		
	}
	
	public void Tremer() { 
		tempoTremer--;
		if(tremer) { 
			if(tempoTremer > 0) {
				int xx = (random.nextInt((forcaTremer + 1) + forcaTremer) - forcaTremer) + Gdx.graphics.getWidth()/2;
				int yy = (random.nextInt((forcaTremer + 1) + forcaTremer) - forcaTremer) + Gdx.graphics.getHeight()/2;
				
				position.x = xx;
				position.y = yy;
				
				camera.position.set(position);
				camera.update();
			}
			else { 
				tremer = false;
				
			}
			
		}
		else { 
			position.x = Gdx.graphics.getWidth()/2;
			position.y = Gdx.graphics.getHeight()/2;
			
			camera.position.set(position);
			camera.update();
			
		}
		
		
	}
	
	
	public void Criar() {
		//shape = new ShapeRenderer();
		img3 = new Texture("Jogador/Coracao.png");
		troca = Gdx.audio.newSound(Gdx.files.internal("Efeitos/Troca.mp3"));
		font = new BitmapFont();
		font.getData().setScale(1.5f);
		tiposLixeiros(tipo);
		img = new Texture(imagem);
		img2 = new Texture(aviso);
		
		TextureRegion[][] tmp = TextureRegion.split(img, img.getWidth() / Colunas, img.getHeight() / Linhas);
		
		TextureRegion[] frames = new TextureRegion[Colunas * Linhas];
		int index = 0;
		
		for(int i = 0; i < Linhas; i++) { 
			for(int j = 0; j < Colunas; j++) { 
				frames[index++] = tmp[i][j];
			}
			
			
		}
		
		Animacao = new Animation<TextureRegion>(0.1f, frames);
		
		Tempo = 0f;
			
			
		batch = new SpriteBatch();
		
	} 
	
	public void Desenhar() {
		
		Tempo += Gdx.graphics.getDeltaTime();
		TextureRegion frameAtual = Animacao.getKeyFrame(Tempo, true);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			ww = frameAtual.getRegionWidth();
			hh = frameAtual.getRegionHeight();
		
			colisor.width = ww;
			colisor.height = hh;
			colisor.x = xx;
			colisor.y = yy;
			for(int i = 0; i < vida; i++) { 
				batch.draw(img3, Gdx.graphics.getWidth() - (vida * (img3.getWidth() + 10)) + i * (img3.getWidth() + 10), Gdx.graphics.getHeight() - img3.getHeight() - 20);
			}
			font.draw(batch,"Pontos: " + points, 35, Gdx.graphics.getHeight() - 100);
			batch.draw(img2, 35, Gdx.graphics.getHeight() - img2.getHeight() - 20);
			batch.draw(frameAtual, xx, yy, ww, 0, ww, hh, scalex, scaley, 0);
			
		batch.end();
		
		scalex += (1 - scalex) * .1;
		scaley += (1 - scaley) * .1;
		
	}
	
	public void Movimentar() { 
		int right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : 0;
		int left = Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 1 : 0;
		
		boolean one = Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1);
		boolean two = Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2);
		boolean three = Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3);
		boolean four = Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4);
		
		if(one || two || three || four) { 
			scalex = 1.2f;
			scaley = .8f;
			DefinirTremer(20, 2);
			long id = troca.play(.8f);
			troca.setLooping(id, false);
			if(one) tipo = 1;
			if(two) tipo = 2;
			if(three) tipo = 3;
			if(four) tipo = 4;
			tiposLixeiros(tipo);
			Criar();
		}
		
		
		
		hspp = (right - left) * speed;
		
		//LERP
		hsp += (hspp - hsp) * .1f;
		
		
		if(xx < 0 && left == 1) {
			hsp = 0;
		}
		else if(xx > Gdx.graphics.getWidth() - ww && right == 1) { 
			hsp = 0;
		}
		
		xx += hsp;
		yy = -25;
		
		if(vida <= 0) { 	
			position.x = Gdx.graphics.getWidth()/2;
			position.y = Gdx.graphics.getHeight()/2;
			camera.position.set(position);
			camera.update();
			
			MyGdxGame.menu.trocou = false;
			MyGdxGame.menu.tela = 2;
			vida = 5;
		}
	}
	
	public void Finalizar()  {
		batch.dispose();
		img.dispose();		
	}
	
	public void IncrementPoint() { 
		points++;
	}
	
	public float getPoint() { 
		return points;
	}
	
	public void setPoint(int point) { 
		this.points = point;
	}
	
	public int getTipo() {
		return tipo;
		
	}
	
	public int getVida() { 
		return vida;
	}
	
	public void decreaseVida() { 
		vida--;
	}
}
