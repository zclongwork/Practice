package com.zcl.practice.opengl;

import android.opengl.GLU;

import com.zcl.practice.opengl.util.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;


/**
 * 线s渲染器
 */
public class MyLinesRenderer extends AbstractMyRenderer{

	public void onDrawFrame(GL10 gl) {
		//清除颜色缓冲区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//设置绘图颜色
		gl.glColor4f(1f, 0f, 0f, 1f);

		//操作模型视图矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//设置眼球的参数
		GLU.gluLookAt(gl,0f,0f,5f, 0f, 0f, 0f, 0f,1f,0f);

		//旋转角度
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);

		//计算点坐标
		float r = 0.5f ;//半径
		float x = 0f,y = 0f,z = 0f ;

		List<Float> coordsList = new ArrayList<Float>();

		//循环绘制点
		for(float alpha = 0f ; alpha < Math.PI * 6 ; alpha = (float) (alpha + Math.PI / 16)){
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			coordsList.add(0f);
			coordsList.add(0f);
			coordsList.add(0f);
			//
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
		}

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
		gl.glDrawArrays(GL10.GL_LINES, 0, coordsList.size() / 3);

	}
}
