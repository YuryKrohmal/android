package yura.android.elements.Opengl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;

public class OpenGl_Base implements GLEventListener {

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glBegin (GL2.GL_LINES);

        //drawing the base
        gl.glBegin (GL2.GL_LINES);
        gl.glVertex3f(-1.0f, -1.0f, 0);
        gl.glVertex3f(1.0f, -1.0f, 0);
        gl.glEnd();

        //drawing the right edge
        gl.glBegin (GL2.GL_LINES);
        gl.glVertex3f(0f, 1.0f, 0);
        gl.glVertex3f(-1.0f, -1.0f, 0);
        gl.glEnd();

        //drawing the lft edge
        gl.glBegin (GL2.GL_LINES);
        gl.glVertex3f(0f, 1.0f, 0);
        gl.glVertex3f(1.0f, -1.0f, 0);
        gl.glEnd();

        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // method body
    }

    public static void main(String[] args) {
        //creating frame
        final JFrame frame = new JFrame ("OpenGl_Base");

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener( new OpenGl_Base() );
        glcanvas.setSize(200, 200);

        frame.getContentPane().add( glcanvas); //adding canvas to frame
        frame.setSize(frame.getContentPane().getPreferredSize());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }//end of main

}
