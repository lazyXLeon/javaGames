package com.xly;

import com.xly.obj.BodyObj;
import com.xly.obj.FoodObj;
import com.xly.obj.GameObj;
import com.xly.obj.HeadObj;
import com.xly.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame {
    //设置窗口信息
    public void launch() throws InterruptedException {
        //设置窗口是否可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(winWidth,winHeight);
        //设置窗口位置
        this.setLocationRelativeTo(null);
        //设置窗口标题
        this.setTitle("贪吃蛇");

        bodyObjList.add(new BodyObj(GameUtils.bodyImg, 30, 570, this));
        bodyObjList.add(new BodyObj(GameUtils.bodyImg, 0, 570, this));

        //键盘事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    switch (state) {
                        case 0, 2 -> state = 1;
                        case 1 -> {
                            state = 2;
                            repaint();
                        }
                        case 3 -> {
                            state = 5;
                            break;
                        }
                    }
                }
            }
        });

        while(true){
            if(state == 1) repaint();
            if(state == 5) {
                state = 0;
                resetGame();
            }
            try {
                //1秒1000毫秒
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //初始化双缓存图片
        if(offScreenImage == null){
            offScreenImage = this.createImage(winWidth,winHeight);
        }
        //获取图片对应的graphics对象
        Graphics gImage = offScreenImage.getGraphics();
        //灰色背景
        gImage.setColor(Color.gray);
        gImage.fillRect(0,0,winWidth,winHeight);
        //绘制网格线
        gImage.setColor(Color.black);

        for (int i = 0; i <= 20; i++) {
            gImage.drawLine(0,30*i,600,30*i);
            gImage.drawLine(30*i,0, 30*i, 600);
        }
        //绘制蛇身
        for (int i = bodyObjList.size()-1; i >= 0; i--) {
            bodyObjList.get(i).paintSelf(gImage);
        }
        //绘制蛇头
        headObj.paintSelf(gImage);
        //绘制食物
        foodObj.paintSelf(gImage);
        //绘制分数
        GameUtils.drawWord(gImage,score+"分", Color.blue, 50, 650, 300);
        //绘制提示语
        gImage.setColor(Color.gray);
        prompt(gImage);
        //将双缓存的图片绘制到窗口中
        g.drawImage(offScreenImage,0,0,null);
    }

    void prompt(Graphics g){
        //未开始
        if (state == 0){
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "按下空格开始游戏", Color.yellow, 35,150, 290);
        } else if (state == 3) {
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "咬到自己啦！按空格重新开始", Color.red, 35,150, 290);
        } else if(state == 2){
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "游戏暂停", Color.yellow, 35,150, 290);
        }
        else if(state == 4){
            g.fillRect(120, 240, 400, 70);
            GameUtils.drawWord(g, "恭喜你游戏通关！", Color.green, 35, 150, 290);
        }
    }

    //游戏重置
    void resetGame() throws InterruptedException {
        //关闭当前窗口
        this.dispose();
        //开启新窗口
        String[] args = {};
        main(args);
    }

    //分数
    public int score = 0;
    //游戏状态：0:未开始 1:游戏中 2:暂停 3:失败 4:通关 5:失败后重新开始
    public static int state = 0;

    //定义双缓存图片
    Image offScreenImage = null;

    //窗口宽高
    int winWidth = 800;
    int winHeight = 600;

    //蛇头
    HeadObj headObj = new HeadObj(GameUtils.rightImg, 60, 570, this);

    //蛇身
    public List<BodyObj> bodyObjList = new ArrayList<>();

    //食物
    public FoodObj foodObj = new FoodObj().getFood();
    public static void main(String[] args) throws InterruptedException {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
