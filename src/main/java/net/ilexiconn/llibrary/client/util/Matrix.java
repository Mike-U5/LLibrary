package net.ilexiconn.llibrary.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import javax.vecmath.*;
import java.util.Stack;

/**
 * @author pau101
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class Matrix {
    public Stack<Matrix4d> matrixStack;

    public Matrix() {
        this.matrixStack = new Stack<>();
        Matrix4d matrix = new Matrix4d();
        matrix.setIdentity();
        this.matrixStack.push(matrix);
    }

    public void push() {
        this.matrixStack.push(new Matrix4d(this.matrixStack.peek()));
    }

    public void pop() {
        if (this.matrixStack.size() < 2) {
            throw new StackUnderflowError();
        }
        this.matrixStack.pop();
    }

    public void translate(double x, double y, double z) {
        Matrix4d matrix = this.matrixStack.peek();
        Matrix4d translation = new Matrix4d();
        translation.setIdentity();
        translation.setTranslation(new Vector3d(x, y, z));
        matrix.mul(translation);
    }

    public void rotate(double angle, double x, double y, double z) {
        Matrix4d matrix = this.matrixStack.peek();
        Matrix4d rotation = new Matrix4d();
        rotation.setIdentity();
        rotation.setRotation(new AxisAngle4d(x, y, z, angle * (Math.PI / 180)));
        matrix.mul(rotation);
    }

    public void scale(double x, double y, double z) {
        Matrix4d matrix = this.matrixStack.peek();
        Matrix4d scale = new Matrix4d();
        scale.m00 = x;
        scale.m11 = y;
        scale.m22 = z;
        scale.m33 = 1;
        matrix.mul(scale);
    }

    public void transform(Point3f point) {
        Matrix4d matrix = matrixStack.peek();
        matrix.transform(point);
    }

    public void transform(Vector3f point) {
        Matrix4d matrix = matrixStack.peek();
        matrix.transform(point);
    }

    public Point3f getTranslation() {
        Matrix4d matrix = matrixStack.peek();
        Point3f translation = new Point3f();
        matrix.transform(translation);
        return translation;
    }

    public Quat4f getRotation() {
        Matrix4d matrix = matrixStack.peek();
        Quat4f rotation = new Quat4f();
        matrix.get(rotation);
        return rotation;
    }

    public Vector3f getScale() {
        Matrix4d matrix = matrixStack.peek();
        float x = (float) Math.sqrt(matrix.m00 * matrix.m00 + matrix.m10 * matrix.m10 + matrix.m20 * matrix.m20);
        float y = (float) Math.sqrt(matrix.m01 * matrix.m01 + matrix.m11 * matrix.m11 + matrix.m21 * matrix.m21);
        float z = (float) Math.sqrt(matrix.m02 * matrix.m02 + matrix.m12 * matrix.m12 + matrix.m22 * matrix.m22);
        return new Vector3f(x, y, z);
    }
}
