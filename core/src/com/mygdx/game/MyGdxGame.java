package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class MyGdxGame extends ApplicationAdapter {
	public static Player jogador = new Player();
	public static Lixo lixo = new Lixo();
	public static Levels levels = new Levels();
	public static Menu menu = new Menu();

	@Override
	public void create () {
		menu.Criar();
		jogador.CriarCamera();
		jogador.Criar();
		lixo.Criar();
		lixo.xx = 64;
		levels.Quantidade();
		levels.criaLixeira();
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menu.DesenharFundo();
		if(menu.tela == 1) {
			jogador.Tremer();
			jogador.Desenhar();
			jogador.Movimentar();
			levels.Quantidade();
			levels.invocaLixo();
		}
		menu.Desenhar();
	}
	
	@Override
	public void dispose () {
		jogador.Finalizar();
		lixo.Finalizar();
		menu.Finalizar();
	}
}
