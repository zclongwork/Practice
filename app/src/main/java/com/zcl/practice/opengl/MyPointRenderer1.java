package com.zcl.practice.opengl;

import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 点渲染器,绘制螺旋线
 */
public class MyPointRenderer1 extends AbstractMyRenderer{

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
		List<Float> coordsList = new ArrayList<Float>();
		float x = 0f,y = 0f,z = 1f ;
		float zstep = 0.01f ;
		for(float alpha = 0f ; alpha < Math.PI * 6 ; alpha = (float) (alpha + Math.PI / 16)){
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			z = z - zstep ;
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
		}

		//转换点成为缓冲区
		ByteBuffer ibb = ByteBuffer.allocateDirect(coordsList.size() * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		for(float f : coordsList){
			fbb.put(f);
		}
		ibb.position(0);

		//指定顶点指针
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
		gl.glDrawArrays(GL10.GL_POINTS, 0, coordsList.size() / 3);
	}
}