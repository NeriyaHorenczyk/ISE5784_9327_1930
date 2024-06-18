package renderer;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the 3D space.
 */
public class Camera implements Cloneable
{
	private Point position;
	private Vector vRight;
	private Vector vUp;
	private Vector vTo;
	private double height = 0.0;
	private double width = 0.0;
	private double distance = 0.0;


	private Point pCenter;

	/**
	 * getPosition function returns the camera position.
	 *
	 * @return the camera position.
	 */
	public Point getPosition()
	{
		return position;
	}

	/**
	 * getvRight function returns the right vector of the camera.
	 *
	 * @return the right vector of the camera.
	 */
	public Vector getvRight()
	{
		return vRight;
	}

	/**
	 * getvUp function returns the up vector of the camera.
	 *
	 * @return the up vector of the camera.
	 */
	public Vector getvUp()
	{
		return vUp;
	}

	/**
	 * getvTo function returns the to vector of the camera.
	 *
	 * @return the to vector of the camera.
	 */
	public Vector getvTo()
	{
		return vTo;
	}

	/**
	 * getHeight function returns the height of the view plane.
	 *
	 * @return the height of the view plane.
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * getWidth function returns the width of the view plane.
	 *
	 * @return the width of the view plane.
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * getDistance function returns the distance of the view plane from the camera.
	 *
	 * @return the distance of the view plane from the camera.
	 */
	public double getDistance()
	{
		return distance;
	}

	/**
	 * Camera default constructor.
	 * without parameters.
	 */
	private Camera()
	{

	}

	/**
	 * @return
	 */
	public static Builder getBuilder()
	{
		return new Builder();
	}

	/**
	 * constructRay function creates a ray from the camera to a specific pixel in the view plane.
	 *
	 * @param nX - number of pixels in the x-axis.
	 * @param nY - number of pixels in the y-axis.
	 * @param j  - the pixel number in the x-axis.
	 * @param i  - the pixel number in the y-axis.
	 * @return the ray from the camera to the pixel.
	 */
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		Point pC = pCenter;
		double rX = width / nX;
		double rY = height / nY;
		double xJ = (j - (nX - 1) / 2.0) * rX;
		double yI = -(i - (nY - 1) / 2.0) * rY;
		Point pIJ = pC;

		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));

		Vector vIJ = pIJ.subtract(position).normalize();
		return new Ray(position, vIJ);

	}


	/**
	 * Builder class is a static inner class of Camera class.
	 * Builder class is used to build a Camera object.
	 */
	public static class Builder
	{
		private final Camera camera = new Camera();

		/**
		 * set the camera location.
		 *
		 * @param p - the location of the camera.
		 * @return the Builder object.
		 */
		public Builder setLocation(Point p)
		{
			camera.position = p;
			return this;
		}

		/**
		 * set the camera direction.
		 *
		 * @param to - the direction of the camera.
		 * @param up - the up vector of the camera.
		 * @return the Builder object.
		 * @throws IllegalArgumentException in case the vectors are not orthogonal.
		 */
		public Builder setDirection(Vector to, Vector up) throws IllegalArgumentException
		{
			if (to.dotProduct(up) != 0)
			{
				throw new IllegalArgumentException("vectors are not orthogonal");
			}
			camera.vTo = to.normalize();
			camera.vUp = up.normalize();
			return this;
		}

		/**
		 * set the view plane size.
		 *
		 * @param width  - the width of the view plane.
		 * @param height - the height of the view plane.
		 * @return the Builder object.
		 */
		public Builder setVpSize(double width, double height)
		{
			camera.width = width;
			camera.height = height;
			return this;
		}

		/**
		 * set the view plane distance from the camera.
		 *
		 * @param distance - the distance from the camera to the view plane.
		 * @return the Builder object.
		 */
		public Builder setVpDistance(double distance)
		{
			camera.distance = distance;
			return this;
		}

		/**
		 * Builds the Camera object.
		 *
		 * @return a clone of the Camera object.
		 * @throws MissingResourceException in case of missing resource.
		 */
		public Camera build() throws MissingResourceException
		{

			final String missingResource = "missing resource";
			final String cameraMsg = "Camera";
			// check if the Point is missing
			if (camera.position == null)
				throw new MissingResourceException(missingResource, cameraMsg, "The Point is missing");

			//check if the vectors are missing
			if (camera.vUp == null || camera.vTo == null)
				throw new MissingResourceException(missingResource, cameraMsg, "The Vectors are missing");
			if (camera.vUp.equals(Vector.ZERO) || camera.vTo.equals(Vector.ZERO))
				throw new IllegalArgumentException("The Vectors are zero");

			//check if the distance, height and width are missing
			if (camera.distance == 0 || camera.height == 0 || camera.width == 0)
				throw new MissingResourceException(missingResource, cameraMsg, "The distance, height or width are missing");

			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
			camera.pCenter = camera.position.add(camera.vTo.scale(camera.distance));
			
			
			return (Camera) camera.clone();

		}


	}

	@Override
	public Camera clone()
	{
		try
		{
			return (Camera) super.clone();
		} catch (CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}
}
