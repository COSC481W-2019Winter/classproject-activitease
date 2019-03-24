package com.example.activitease;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {

        public MyGLSurfaceView(Context content){
            super(content);
            init();
            // Create an OpenGL ES 2.0 context
            // Set the Renderer for drawing on the GLSurfaceView
        }


    public MyGLSurfaceView(Context content, AttributeSet attrs)
        {
            super(content, attrs);
            init();
        }
        public void init()
        {
            setEGLContextClientVersion(2);
            setPreserveEGLContextOnPause(true);
            setRenderer(new GLRenderer());
        }
}
