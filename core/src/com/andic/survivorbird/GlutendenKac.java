package com.andic.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;
import java.util.Random;


public class GlutendenKac extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;

	float birdX = 0;
	float birdY =  0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.9f;
	float enemyVelocity = 8;
	Random random;

	Circle birdCircle;
	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyY1 = new float[numberOfEnemies];
	float [] enemyY2 = new float[numberOfEnemies];
	float [] enemyY3 = new float[numberOfEnemies];
	//float [][] enemyLoc = new float [4][numberOfEnemies];

	float distance = 0;
	float yControl = 1;
	int score = 0;
	int scoredEnemy = 0;

	BitmapFont font;

	//float enemyWidth = 0;
	//float enemyHeight = 0;

	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");

		bird = new Texture("didem.png");
		bee1 = new Texture("bee3.png");
		bee2 = new Texture("bee3.png");
		bee3 = new Texture("bee3.png");

		distance = Gdx.graphics.getWidth() / 2;

		random = new Random();

		birdX = Gdx.graphics.getWidth()/5;
		birdY = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircle1 = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.FIREBRICK);
		font.getData().setScale(5);

		for (int i = 0; i < numberOfEnemies; i++) {

			while (yControl == 1) {
				enemyY1[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
				enemyY2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
				enemyY3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

				if (((Math.abs(enemyY1[i] - enemyY2[i])) < (bee1.getHeight()/5))
						|| ((Math.abs(enemyY1[i] - enemyY3[i])) < (bee1.getHeight()/5))
						|| ((Math.abs(enemyY2[i] - enemyY3[i])) < (bee1.getHeight()/5))
						|| (enemyY1[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
						|| (enemyY2[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
						|| (enemyY3[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
						|| (enemyY1[i] < (Gdx.graphics.getHeight()/12))
						|| (enemyY2[i] < (Gdx.graphics.getHeight()/12))
						|| (enemyY3[i] < (Gdx.graphics.getHeight()/12))) {
					yControl = 1;
				} else {
					yControl = 0;
				}
			}

			enemyX[i] = Gdx.graphics.getWidth() - (bee1.getWidth() / 2) + (i * distance);

			enemyCircle1[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();

			yControl = 1;
		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {  	//eğer oyun başladı ise

			font.draw(batch, String.valueOf(score),200,900);
			// skor yazdırma.
			if (enemyX[scoredEnemy] < birdX) {
				score++;

				if (scoredEnemy < numberOfEnemies-1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}
			}



			if (Gdx.input.isTouched()) {	//jump
				if (birdY > (Gdx.graphics.getHeight() - 80)) {
					velocity = 0;
				} else {
					velocity = -12;
				}
			}


			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < -bee1.getWidth()) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					while (yControl == 1) {
						enemyY1[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						enemyY2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						enemyY3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

						if (((Math.abs(enemyY1[i] - enemyY2[i])) < (bee1.getHeight()/5))
								|| ((Math.abs(enemyY1[i] - enemyY3[i])) < (bee1.getHeight()/5))
								|| ((Math.abs(enemyY2[i] - enemyY3[i])) < (bee1.getHeight()/5))
								|| (enemyY1[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
								|| (enemyY2[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
								|| (enemyY3[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
								|| (enemyY1[i] < (Gdx.graphics.getHeight()/12))
								|| (enemyY2[i] < (Gdx.graphics.getHeight()/12))
								|| (enemyY3[i] < (Gdx.graphics.getHeight()/12))) {
							yControl = 1;
						} else {
							yControl = 0;
						}
					}
				yControl = 1;

				}
				else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				//float enemyWidth = Gdx.graphics.getWidth()/13.5f;
				//float enemyHeight = Gdx.graphics.getHeight()/9;

				batch.draw(bee1, enemyX[i], enemyY1[i], 100, 100);
				batch.draw(bee2, enemyX[i], enemyY2[i], 100, 100);
				batch.draw(bee3, enemyX[i], enemyY3[i], 100, 100);

				enemyCircle1[i] = new Circle(enemyX[i] + 50, enemyY1[i] + 50, 50);
				enemyCircle2[i] = new Circle(enemyX[i] + 50, enemyY2[i] + 50, 50);
				enemyCircle3[i] = new Circle(enemyX[i] + 50, enemyY3[i] + 50, 50);


			}


			if (birdY > Gdx.graphics.getHeight() / 20) {	//kuş zemine gelirse dursun
				velocity = velocity + gravity;
				birdY =  birdY - velocity;
			} else {
				gameState = 2;
			}



		} else if (gameState == 0) {	// oyun başlamadı. Ekrana dokununca başlar.
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}

			font.draw(batch,"Gluten'den Kacmaya Hazir misin?",Gdx.graphics.getWidth()/3, (Gdx.graphics.getHeight()/2)+100);

		} else if (gameState == 2) {

			font.draw(batch,"Glutenden " + String.valueOf(score) + " Defa Kaçabildin!", Gdx.graphics.getWidth()/3, (Gdx.graphics.getHeight()/2)+130);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight()/2;
				velocity = 0;
				score = 0;
				scoredEnemy = 0;

				for (int i = 0; i < numberOfEnemies; i++) {

					while (yControl == 1) {
						enemyY1[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						enemyY2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						enemyY3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

						if (((Math.abs(enemyY1[i] - enemyY2[i])) < (bee1.getHeight()/5))
								|| ((Math.abs(enemyY1[i] - enemyY3[i])) < (bee1.getHeight()/5))
								|| ((Math.abs(enemyY2[i] - enemyY3[i])) < (bee1.getHeight()/5))
								|| (enemyY1[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
								|| (enemyY2[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
								|| (enemyY3[i] > (Gdx.graphics.getHeight() - bee2.getHeight()/6))
								|| (enemyY1[i] < (Gdx.graphics.getHeight()/12))
								|| (enemyY2[i] < (Gdx.graphics.getHeight()/12))
								|| (enemyY3[i] < (Gdx.graphics.getHeight()/12))) {
							yControl = 1;
						} else {
							yControl = 0;
						}
					}

					enemyX[i] = Gdx.graphics.getWidth() - (bee1.getWidth() / 2) + (i * distance);

					enemyCircle1[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();

					yControl = 1;
				}
			}
		}

		//kuşu çiz
		//batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);
		batch.draw(bird, birdX, birdY, 150, 150);


		batch.end();

		birdCircle.set(birdX + 75,birdY + 75,75);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//		shapeRenderer.setColor(Color.BLACK);
//		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i = 0; i < numberOfEnemies; i++) {
//			shapeRenderer.circle(enemyX[i] + 50, enemyY1[i] + 50, 50);
//			shapeRenderer.circle(enemyX[i] + 50, enemyY2[i] + 50, 50);
//			shapeRenderer.circle(enemyX[i] + 50, enemyY3[i] + 50, 50);

			if (Intersector.overlaps(birdCircle, enemyCircle1[i])
					|| Intersector.overlaps(birdCircle, enemyCircle2[i])
					|| Intersector.overlaps(birdCircle, enemyCircle3[i])) {

				gameState = 2;
			}
		}
//		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}