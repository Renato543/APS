package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Levels {
	private int level = 12;
	private int quantidade = 0;
	private List<Lixo> lixeira = new ArrayList<Lixo>();
	public int delayatual = 120;
	public int delay = 120;
	public int possivel = 0;	
	
	
	
	
	public void incrementLevel() { 
		level++;
		
	} 
	
	public int getLevel() {
		return level;
	} 
	
	public void Quantidade() { 
		quantidade = level * 10;
	}
	
	public void criaLixeira() { 
		lixeira.clear();
		for(int i = 0; i < quantidade; i++) { 
			lixeira.add(new Lixo());
		}
		
		for(Lixo lixo : lixeira) { 
			lixo.Criar();

			
			
		}

	}
	
	
	public List<Lixo> getLixeira() { 
		return lixeira;
	}
	

	public void invocaLixo() { 

		delayatual--;
		if(delayatual < 0) { delayatual = 0; }
		
		if(delayatual == 0 ) {
			possivel++;
			delayatual = delay;
			delay *= .99;
		}
		
		for(Lixo lixo : getLixeira()) {

			if(getLixeira().indexOf(lixo) <= possivel) {
				lixo.Desenhar();
				lixo.Colidiu(MyGdxGame.jogador.colisor);
			}
		}
		
	}
	
	
}
