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
    private float[] colors = new float[2920]; // 2190 vertices and 4 color values per vertice.
    private static boolean timerRunning = false;
    private boolean initialTimerDrawn = false;
    private static int numIterations;
    private static double activityLengthMillis;
    private timer timer;
    private static int millisIterationTime;
    private static int nanosIterationTime;


    public static void setTimerRunning(boolean theTimerRunning)
    {
        timerRunning = theTimerRunning;
    }
    public static void setActivityLength(double activityLength)
    {
        activityLengthMillis = activityLength;
        double iterationTime = activityLengthMillis/91;   //Activity length divided by total number of iterations
        millisIterationTime = (int) iterationTime;
        nanosIterationTime = (int) iterationTime % 1;


    }
    public static void setNumIterations(int iterations)
    {
        numIterations = iterations;
    }
    public static int getNumIterations()
    {
        return numIterations;
    }



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

        GLES20.glClearColor((float) 0.98, (float)0.98, (float)0.98, 1);

        timer = new timer((float)1, (float)0.98);

        //Code to draw geometry of clock
        timer.initialTimerDraw(mMVPMatrix, numIterations);
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
        if(!initialTimerDrawn)
        {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

            timer.initialTimerDraw(mMVPMatrix, numIterations);
            initialTimerDrawn = true;
        }
        //Animation of timer;
        else if(timerRunning && numIterations < 365 && initialTimerDrawn)
        {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

            timer.draw(mMVPMatrix, numIterations);

            synchronized (this) {
                try {
                    wait(millisIterationTime, nanosIterationTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                numIterations += 4;
            }
        }
    }
    public class timer
    {
        private final String vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vColor;" +
                        "varying mediump vec4 vaColor;" +
                        "void main() {" +
                        "  vaColor = vColor;" +
                        "  gl_Position = vPosition;" +
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying mediump vec4 vaColor;" +
                        "void main() {" +
                        "  gl_FragColor = vaColor;" +
                        "}";

        private final int mProgram;
        private int positionHandle;
        private int colorHandle;
        private FloatBuffer vertexBuffer;
        private FloatBuffer colorBuffer;
        private float vertices[];
        private int MVPmatrixhandle;
        int COORDS_PER_VERTEX = 3;
        int COORDS_PER_COLOR = 4;
        private final int vertexCount = 365 * 6 / COORDS_PER_VERTEX;
        private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
        private final int colorStride = COORDS_PER_COLOR * 4;

        public timer(float scalingFactor, float scalingFactor2)
        {
            int vertexShader = GLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = GLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            vertices = new float[365 * 6]; //Outer Circle
            vertices[0] = scalingFactor;
            for (int i = 1; i < 365; i++) {
                vertices[(i * 3 + 0)] = (float) (scalingFactor * Math.cos((3.14 / 180) * (float) i));
                vertices[(i * 3 + 1)] = (float) (scalingFactor * Math.sin((3.14 / 180) * (float) i));
                vertices[(i * 3 + 2)] = 0;
            }
            vertices[365] = scalingFactor2; //Inner Circle
            for(int i = 365; i < 730; i++)
            {
                vertices[(i * 3) + 0] = (float) (scalingFactor2 * Math.cos((3.14 / 180) * (float) i));
                vertices[(i * 3) + 1] = (float) (scalingFactor2 * Math.sin((3.14 / 180) * (float) i));
                vertices[(i * 3) + 2] = 0;
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
        public void draw(float[] mvpMatrix, int n) //Draws and updates timer while running
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

            for(int x = 0; x < n; x++) //Animation loop
            {
                colors[x * 4 + 0] = 0.98f;
                colors[x * 4 + 1] = 0.98f;
                colors[x * 4  + 2] = 0.98f;
                colors[x * 4 + 3] = 0.98f;
            }
            for(int x = n; x < 365; x++) //Draw outer circle
            {
                colors[(x * 4) + 0] = .251f;
                colors[(x * 4) + 1] = .879f;
                colors[(x * 4)  + 2] = .816f;
                colors[(x * 4) + 3] = 1.0f;
            }
            for(int x = 365; x < 730; x++) //Draw inner circle
            {
                colors[x * 4 + 0] = 1f;
                colors[x * 4 + 1] = 1f;
                colors[x * 4  + 2] = 1f;
                colors[x * 4 + 3] = 1f;
            }
            // initialize color byte buffer for color values
            ByteBuffer cb = ByteBuffer.allocateDirect(colors.length * 4);
            // use the device hardware's native byte order
            cb.order(ByteOrder.nativeOrder());
            // Create a floating point buffer from byte buffer
            colorBuffer = cb.asFloatBuffer();
            //add the coordinates to the float buffer
            colorBuffer.put(colors);
            // set the buffer to read the first color
            colorBuffer.position(0);


            // get handle to fragment shader's vColor member
            colorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
            GLES20.glEnableVertexAttribArray(colorHandle);
            GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

            // Set color for drawing the circle
            MVPmatrixhandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
            //Apply projection and view transformation
            GLES20.glUniformMatrix4fv(MVPmatrixhandle, 1, false, mvpMatrix, 0);
            // Draw the circle
            int count = 364;
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, count);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 365, count);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(positionHandle);
        }
        public void initialTimerDraw(float[] mvpMatrix, int z) //Draws initial timer without animation parameters
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

            for(int x = 0; x < z; x++) //Animation loop
            {
                colors[x * 4 + 0] = 0.98f;
                colors[x * 4 + 1] = 0.98f;
                colors[x * 4  + 2] = 0.98f;
                colors[x * 4 + 3] =0.98f;
            }
            for(int x = numIterations; x < 365; x++) //Draw outer circle
            {
                colors[(x * 4) + 0] = .251f;
                colors[(x * 4) + 1] = .879f;
                colors[(x * 4)  + 2] = .816f;
                colors[(x * 4) + 3] = 1.0f;
            }
            for(int x = 365; x < 730; x++) //Draw inner circle
            {
                colors[x * 4 + 0] = 0.98f;
                colors[x * 4 + 1] = 0.98f;
                colors[x * 4  + 2] = 0.98f;
                colors[x * 4 + 3] = 0.98f;
            }
            // initialize color byte buffer for color values
            ByteBuffer cb = ByteBuffer.allocateDirect(colors.length * 4);
            // use the device hardware's native byte order
            cb.order(ByteOrder.nativeOrder());
            // Create a floating point buffer from byte buffer
            colorBuffer = cb.asFloatBuffer();
            //add the coordinates to the float buffer
            colorBuffer.put(colors);
            // set the buffer to read the first color
            colorBuffer.position(0);


            // get handle to fragment shader's vColor member
            colorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
            GLES20.glEnableVertexAttribArray(colorHandle);
            GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

            // Set color for drawing the circle
            MVPmatrixhandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
            //Apply projection and view transformation
            GLES20.glUniformMatrix4fv(MVPmatrixhandle, 1, false, mvpMatrix, 0);
            // Draw the circle
            int count = 364;
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, count);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 365, count);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(positionHandle);
        }
    }

}