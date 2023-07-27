package com.xly.obj;

import com.xly.GameWin;
import com.xly.utils.GameUtils;

import java.awt.*;
import java.util.Random;

public class FoodObj extends GameObj{
    //随机生成食物
    Random r = new Random();

    //生成食物
    public FoodObj getFood() {
        return new FoodObj(GameUtils.foodImg, r.nextInt(20)*30, (r.nextInt(19) +1) * 30, this.frame);
    }

    public FoodObj() {
        super();
    }

    public FoodObj(Image img, int x, int y, GameWin frame) {
        super(img, x, y, frame);
    }

    @Override
    public void paintSelf(Graphics g) {
        super.paintSelf(g);
    }
}
