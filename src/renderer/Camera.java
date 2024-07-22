
package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static java.lang.Math.min;
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
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;


	private Point pCenter; // center of the view plane

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
	 * constructRayThroughPixel function creates rays from the camera to a specific pixel in the view plane.
	 * The rays are spread out within the pixel.
	 *
	 * @param nX      - number of pixels in the x-axis.
	 * @param nY      - number of pixels in the y-axis.
	 * @param j       - the pixel number in the x-axis.
	 * @param i       - the pixel number in the y-axis.
	 * @param samples - the number of rays to generate within the pixel.
	 * @return the list of rays from the camera to the pixel.
	 */

	public List<Ray> constructRayThroughPixel(int nX, int nY, int j, int i, int samples)
	{
		List<Ray> rays = new ArrayList<>();

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
		// Generate multiple rays spread out evenly within the pixel
		//פה הוספת קרניים ונודא שזה לא יחרוג מהמסגרת
		for (int p = -samples/2; p <= samples/2; p++)
			for (int q = -samples/2; q <= samples/2; q++)
			{
				Point temp =pIJ;
				if (p!=0 )
					temp=temp.add(vRight.scale(p*min(rX,rY)/samples));
				if (q!=0 )
					temp=temp.add(vUp.scale(q*min(rX,rY)/samples));

				rays.add(new Ray(position, temp.subtract(position)));
			}

		return rays;
	}

	/**
	 * castRay function casts a ray from the camera to a specific pixel in the view plane.
	 *
	 * @param nX     - number of pixels in the x-axis.
	 * @param nY     - number of pixels in the y-axis.
	 * @param row    - the pixel number in the x-axis.
	 * @param column - the pixel number in the y-axis.
	 */
	public void castRay(int nX, int nY, int row, int column)
	{
		Ray ray = constructRay(nX, nY, row, column);
		Color pixelColor = rayTracer.traceRay(ray);
		imageWriter.writePixel(row, column, pixelColor);
	}
	//תיעוד
	//i add parmter samples for give us to diside the samples in the test and not the samples will be 3 over time
	private void castRayAntiAlising(int nx, int ny, int row, int column,int samples)
	{
		List<Ray> rays = constructRayThroughPixel(nx, ny, column, row, samples);
		Color pixelColor = rayTracer.traceRay(rays);
		imageWriter.writePixel(column, row, pixelColor);

	}

	/**
	 * renderImage function renders the image.
	 */
	public Camera renderImage()
	{
		for (int row = 0; row < imageWriter.getNy(); row++)
			for (int column = 0; column < imageWriter.getNx(); column++)
				castRay(imageWriter.getNx(), imageWriter.getNy(), row, column);
		return this;
	}

	/**
	 * תיעוד חדש
	 */
	public Camera renderImageAntiAlising(int samples)
	{
		for (int row = 0; row < imageWriter.getNy(); row++)
			for (int column = 0; column < imageWriter.getNx(); column++)
			{
				castRayAntiAlising(imageWriter.getNx(), imageWriter.getNy(), row, column,samples);
			}
		return this;

	}



	/**
	 * printGrid function prints a grid on the image.
	 *
	 * @param interval - the interval between the lines of the grid.
	 * @param color    - the color of the grid.
	 */
	public Camera printGrid(int interval, Color color)
	{
		for (int i = 0; i < imageWriter.getNx(); i++)
			for (int j = 0; j < imageWriter.getNy(); j += interval)
				imageWriter.writePixel(i, j, color);

		for (int i = 0; i < imageWriter.getNx(); i += interval)
			for (int j = 0; j < imageWriter.getNy(); j++)
				imageWriter.writePixel(i, j, color);
		return this;
	}

	/**
	 * writeToImage function writes the image to a file.
	 */
	public Camera writeToImage()
	{
		imageWriter.writeToImage();
		return this;
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

		public Builder setRayTracer(RayTracerBase rayTracer)
		{
			camera.rayTracer = rayTracer;
			return this;
		}

		public Builder setImageWriter(ImageWriter imageWriter)
		{
			camera.imageWriter = imageWriter;
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
			if (!isZero(to.dotProduct(up)))
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

			if (camera.imageWriter == null)
				throw new MissingResourceException(missingResource, Camera.class.getSimpleName(), "missing image writer");

			if (camera.rayTracer == null)
				throw new MissingResourceException(missingResource, cameraMsg, "missing ray Tracer");
			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
			camera.pCenter = camera.position.add(camera.vTo.scale(camera.distance));


			return (Camera) camera.clone();

		}


		/**
		 * rotate the camera view to the given point
		 *
		 * @param p point to watch there
		 * @return the builder
		 */
		public Builder lookAt(Point p)
		{ // better name: lookAt
			camera.vTo = p.subtract(camera.getPosition()).normalize();
			// vector Y with little angle so it will be perpendicular to vTo
			// TODO: make sure this calculation is correct
			camera.vRight = Vector.Y.crossProduct(camera.vTo).normalize();
			camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
			return this;
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
