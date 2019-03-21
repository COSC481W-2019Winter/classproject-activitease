package com.example.activitease;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer {
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private circle outerCircle;
    private circle innerCircle;

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glClearColor(1, 1, 1, 0);
        innerCircle = new circle(1);
        outerCircle = new circle((float)0.75);
        //Code to draw geometry of clock
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        //Sets viewport, calls to change size of frame if necessary

        for(int i = 0; i < 16; i++)
        {
            mProjectionMatrix[i] = 0.0f;
            mViewMatrix[i] = 0.0f;
            mMVPMatrix[i] = 0.0f;
        }

        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

    }
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        innerCircle.draw(mMVPMatrix);
        outerCircle.draw(mMVPMatrix);
        //Calls to do animation of timer
    }
    public class circle
    {
        private final String vertexShaderCode =
                "attribute vec4 vPosition;"
              + "uniform mat4 uMVPMatrix;"
              +    "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";
        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";
       private final int mProgram;
       private int positionHandle;
       private int colorHandle;
       private FloatBuffer vertexBuffer;

       private float vertices[];
       private int MVPmatrixhandle;
       int COORDS_PER_VERTEX = 3;
       private final int vertexCount = 364 * 3 / COORDS_PER_VERTEX;
       private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
       float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

       public circle(float scalingFactor)
       {
           int vertexShader = GLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                   vertexShaderCode);
           int fragmentShader = GLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                   fragmentShaderCode);

           vertices = new float[364 * 3];
           vertices[0] = scalingFactor;
           for(int i = 1; i <364; i++){
               vertices[(i * 3 + 0)] = (float) (scalingFactor * Math.cos((3.14 / 180) * (float)i));
               vertices[(i * 3+ 1)] = (float) (scalingFactor * Math.sin((3.14 / 180) * (float)i));
               vertices[(i * 3+ 2)] = 0;
           }

           Log.v("Thread", "" + vertices[0] + "," + vertices[1] + "," + vertices[2]);
           // initialize vertex byte buffer for shape coordinates
           ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
           // use the device hardware's native byte order
           bb.order(ByteOrder.nativeOrder());
           // create a floating point buffer from the ByteBuffer
           vertexBuffer = bb.asFloatBuffer();
           // add the coordinates to the FloatBuffer
           vertexBuffer.put(vertices);
           // set the buffer to read the first coordinate
           vertexBuffer.position(0);



             // create empty OpenGL ES Program
             mProgram = GLES20.glCreateProgram();

             // add the vertex shader to program
             GLES20.glAttachShader(mProgram, vertexShader);

             // add the fragment shader to program
             GLES20.glAttachShader(mProgram, fragmentShader);

              // creates OpenGL ES program executables
             GLES20.glLinkProgram(mProgram);
        }
        public void draw(float[] mvpMatrix)
        {
            // Add program to OpenGL ES environment
            GLES20.glUseProgram(mProgram);

            // get handle to vertex shader's vPosition member
            positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(positionHandle);

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            // get handle to fragment shader's vColor member
            colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

            // Set color for drawing the circle
            GLES20.glUniform4fv(colorHandle, 1, color, 0);
            MVPmatrixhandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
            //Apply projection and view transformation
            GLES20.glUniformMatrix4fv(MVPmatrixhandle, 1, false, mvpMatrix, 0);
            // Draw the circle
            GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(positionHandle);
        }

    }

}
