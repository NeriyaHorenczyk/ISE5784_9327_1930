
package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.*;
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
	private void castRayAntiAlising(int nx, int ny, int row, int column,int samples,boolean isAdptive)
	{
		if(isAdptive){
			imageWriter.writePixel(column, row, castRaysRecursive(nx,ny,column,row,(int)Math.log(samples-1)));

		}else {
			List<Ray> rays = constructRayThroughPixel(nx, ny, column, row, samples);
			Color pixelColor = rayTracer.traceRay(rays);
			imageWriter.writePixel(column, row, pixelColor);
		}


	}
	public Point findPixel(int nX, int nY, int j, int i)
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

		return pIJ;

	}

	/**
	 * The starting method of the adaptive ss
	 *
	 * @param nX    the number of columns in the resolution
	 * @param nY    the number of rows in the resolution
	 * @param j     for casting ray in that column
	 * @param i     for casting ray in that row
	 * @param level the level of recursion
	 * @return the color of the cell
	 */
	private Color castRaysRecursive(int nX, int nY, int j, int i, int level) {
		Point pIJ = findPixel(nX, nY, j, i);//מוצא נק אמצע של הפיקסל
		Color sumAll = Color.BLACK;
		List<Point> pointList = scatterFourPoints(pIJ,Math.min(height / nY, width / nX));
		Map<Vector, Color> dictionary = new HashMap<>();
		Point p0 = pointList.getFirst();
		Point p1 = pointList.get(1);
		Point p2 = pointList.get(2);
		Point p3 = pointList.getLast();
		Vector v0 = p0.subtract(position);
		Color color0 = rayTracer.traceRay(new Ray(position, v0));
		dictionary.put(v0, color0);
		Vector v1 = p1.subtract(position);
		Color color1 = rayTracer.traceRay(new Ray(position, v1));
		dictionary.put(v1, color1);
		Vector v2 = p2.subtract(position);
		Color color2 = rayTracer.traceRay(new Ray(position, v2));
		dictionary.put(v2, color2);
		Vector v3 = p3.subtract(position);
		Color color3 = rayTracer.traceRay(new Ray(position, v3));
		dictionary.put(v3, color3);

		if ((color0.equals(color1) && color1.equals(color2) && color3.equals(color0)) || level <= 0)
			return color0.add(color1, color2, color3).reduce(4);//אם הצבעים שןןים תחזיר אותם
		double ribOverTwo = Math.min(height / nY, width / nX) / 2;
		//להוסיף בפוינט את האמצע
		dictionary = castRaysRecursive(ribOverTwo, pIJ.middle(p0), level - 1, dictionary);
		dictionary = castRaysRecursive(ribOverTwo, pIJ.middle(p1), level - 1, dictionary);
		dictionary = castRaysRecursive(ribOverTwo, pIJ.middle(p2), level - 1, dictionary);
		dictionary = castRaysRecursive(ribOverTwo, pIJ.middle(p3), level - 1, dictionary);
		for (Map.Entry<Vector, Color> vectorColorEntry : dictionary.entrySet())
			sumAll = sumAll.add(vectorColorEntry.getValue());
		return sumAll.reduce(dictionary.size());
	}

	/**
	 * Recursive method for the adaptive ss
	 *
	 * @param rib        the size of the rib of the cell
	 * @param center     the center of the cell
	 * @param level      the level of the recursion
	 * @param dictionary for keeping the color for not calculating twice the same ray
	 * @return the dictionary
	 */
	private Map<Vector, Color> castRaysRecursive(double rib, Point center, int level, Map<Vector, Color> dictionary) {
		List<Point> pointList =scatterFourPoints(center, rib);

		Point p0 = pointList.getFirst();
		Point p1 = pointList.get(1);
		Point p2 = pointList.get(2);
		Point p3 = pointList.getLast();

		Vector v0 = p0.subtract(position);
		Vector v1 = p1.subtract(position);
		Vector v2 = p2.subtract(position);
		Vector v3 = p3.subtract(position);
		Color color0 = dictionary.get(v0), color1 = dictionary.get(v1), color2 = dictionary.get(v2), color3 = dictionary.get(v3);

		if (color0 == null) {
			color0 = rayTracer.traceRay(new Ray(position, v0));
			dictionary.put(v0, color0);
		}
		dictionary.put(v0, color0);
		if (color1 == null) {
			color1 = rayTracer.traceRay(new Ray(position, v1));
			dictionary.put(v1, color1);
		}
		if (color2 == null) {
			color2 = rayTracer.traceRay(new Ray(position, v2));
			dictionary.put(v2, color2);
		}
		if (color3 == null) {
			color3 = rayTracer.traceRay(new Ray(position, v3));
			dictionary.put(v3, color3);
		}
		double ribOverTwo = rib / 2;
		if (!(color0.equals(color1) || !color1.equals(color2) || !color3.equals(color0)) && level > 0) {
			dictionary = castRaysRecursive(ribOverTwo, center.middle(p0), level - 1, dictionary);
			dictionary = castRaysRecursive(ribOverTwo, center.middle(p1), level - 1, dictionary);
			dictionary = castRaysRecursive(ribOverTwo, center.middle(p2), level - 1, dictionary);
			dictionary = castRaysRecursive(ribOverTwo, center.middle(p3), level - 1, dictionary);
		}
		return dictionary;
	}
	/**
	 * Scatter four points at the edges of the cell according to it's middle
	 *
	 * @param center the center of the cell
	 * @param rib    the size of the rib of the cell
	 * @return the points
	 */
	public List<Point> scatterFourPoints( Point center, double rib) {

		double ribOverTwo = rib / 2;
		return List.of(center.add(vRight.scale(ribOverTwo)).add(vUp.scale(ribOverTwo)), center.add(vRight.scale(ribOverTwo)).add(vUp.scale(-ribOverTwo)),
				center.add(vRight.scale(-ribOverTwo)).add(vUp.scale(ribOverTwo)), center.add(vRight.scale(-ribOverTwo)).add(vUp.scale(-ribOverTwo)));
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
	public Camera renderImageAntiAlising(int samples,boolean isAduptiv)
	{
		for (int row = 0; row < imageWriter.getNy(); row++)
			for (int column = 0; column < imageWriter.getNx(); column++)
			{
				castRayAntiAlising(imageWriter.getNx(), imageWriter.getNy(), row, column,samples,isAduptiv);
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
