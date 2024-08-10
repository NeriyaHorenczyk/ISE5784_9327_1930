
/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests
{

	Vector vecY = new Vector(0, 1, 0);
	/**
	 * Scene for the tests
	 */
	private final Scene scene = new Scene("Test scene");
	/**
	 * Camera builder for the tests with triangles
	 */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), Vector.Y)
			.setRayTracer(new SimpleRayTracer(scene));

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres()
	{
		scene.geometries.add(
				new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)),
				new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)));
		scene.lights.add(
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
						.setKl(0.0004).setKq(0.0000006));


		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
				.setVpSize(150, 150)
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
				.build()
				.renderImage()
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors()
	{
		scene.geometries.add(
				new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)
								.setKT(new Double3(0.5, 0, 0))),
				new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(670, 670, 3000))
						.setEmission(new Color(20, 20, 20))
						.setMaterial(new Material().setKR(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000))
						.setEmission(new Color(20, 20, 20))
						.setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
				.setKl(0.00001).setKq(0.000005));

		cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
				.setVpSize(2500, 2500)
				.setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
				.build()
				.renderImage()
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a
	 * partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere()
	{
		scene.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
						new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.lights.add(
				new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
						.setKl(4E-5).setKq(2E-7));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
				.setVpSize(200, 200)
				.setImageWriter(new ImageWriter("refractionShadow", 600, 600))
				.build()
				.renderImage()
				.writeToImage();
	}


	/**
	 * produce 4 objects that shows all the effects.
	 */
	@Test
	public void fourObjectsTest()
	{
		// Create four spheres with different levels of transparency and shininess
		scene.geometries.add(
				new Sphere(40d, new Point(-100, 0, -50)).setEmission(new Color(RED))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(10).setKT(0.3)),
				new Sphere(40d, new Point(0, 0, -50)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(50).setKT(0.5)),
				new Sphere(40d, new Point(100, 0, -50)).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.7)),
				new Sphere(40d, new Point(200, 0, -100)).setEmission(new Color(YELLOW))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(1))
		);

		// Add a light source to the scene
		scene.lights.add(
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
						.setKl(0.0004).setKq(0.0000006)
		);


		// Render the image and write it to a file
		cameraBuilder.setLocation(new Point(0, 0, 2500)).setVpDistance(1000)
				.setDirection(new Vector(0.02, 0, -1), vecY)
				.setVpSize(150, 150)
				.setImageWriter(new ImageWriter("fourObjectsTest", 500, 500))
				.build()
				.renderImage()
				.writeToImage();
	}

	/**
	 * test all the effects together and create a super image
	 */
	@Test
	void multiTest1()
	{
		Scene scene = new Scene("Super Test")
				.setAmbientLight(new AmbientLight(new Color(0, 0, 127), new Double3(0.15)))
				.setBackground(new Color(64, 64, 255));

		// add spirals of spheres
		double R = 500; // radius of the spiral
		double N = 15;  // number of spheres in the spiral
		Color[] colors = {
				new Color(255, 0, 255),
				new Color(0, 0, 255),
				new Color(255, 255, 0),
				new Color(0, 255, 0),
				new Color(255, 0, 0)
		};
		Material mat = new Material().setKD(0.5).setKS(0.5).setShininess(100);
		Material mat2 = new Material().setKD(0.5).setKS(0.5).setShininess(30).setKR(0.15);
		double x, y, z;

		for (int i = 0; i < N * 3; i++)
		{
			x = Math.sqrt(R) * Math.cos(2 * Math.PI * i / N);
			y = Math.sqrt(R) * Math.sin(2 * Math.PI * i / N);
			z = i * 5;

			// add spheres in the spiral
			scene.geometries.add(
					new Sphere(5, new Point(x, y, z))
							.setEmission(colors[i % colors.length]).setMaterial(mat2)
			);

			// add polygons around the spiral
			scene.geometries.add(
					new Polygon(new Point(x, y, z), new Point(x + 10, y + 10, z), new Point(
							x + 10, y, z + 10), new Point(x, y - 10, z + 10))
							.setEmission(colors[(i + 2) % colors.length]).setMaterial(mat)
			);

			R += 50; // increase the radius of the spiral
		}

		// base plane
		scene.geometries.add(new Plane(new Point(-1, -1, 0), new Point(1, -1, 0), new Point(1, 1, 0))
				.setEmission(new Color(50, 50, 50)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(50).setKR(0.01)));

		// bubble sphere around the spiral
		scene.geometries.add(new Sphere(220, new Point(0, 0, 0))
				.setEmission(new Color(0, 0, 127)).setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setKT(0.8)));

		// add lights
		scene.lights.add(
				new SpotLight(new Color(255, 128, 0), new Point(-250, 500, 500), new Vector(1, -1, -1))
		);
		scene.lights.add(
				new PointLight(new Color(0, 255, 0), new Point(50, -20, 220.0))
		);


		Camera.Builder builder = Camera.getBuilder()
				.setRayTracer(new SimpleRayTracer(scene));


		builder.setImageWriter(new ImageWriter("multiTest1-a", 800, 800))
				.setLocation(new Point(-700, 0, 150))
				.lookAt(new Point(0, 0, 50))
				.setVpSize(200, 200)
				.setVpDistance(300)
				.setDirection(new Vector(1, 0, 0), new Vector(0, 0, 1))
				.build()
				.renderImage()
				.writeToImage();



		builder.setImageWriter(new ImageWriter("multiTest1-b", 800, 800))
				.setLocation(new Point(0, 0, 150))
				.lookAt(new Point(0, 0, 50))
				.setVpSize(500, 500)
				.setVpDistance(50);



		builder.setImageWriter(new ImageWriter("multiTest1-c", 800, 800))
				.setLocation(new Point(200, -100, 150))
				.lookAt(new Point(0, 100, 0))
				.setVpSize(500, 500)
				.setVpDistance(50);


		builder.build().renderImage().writeToImage();

	}

	/**
	 * test all the effects together and create an amazing image
	 */
	@Test
	void multiTest2()
	{
		// Build a pyramid
		Geometry side1 = new Triangle(new Point(0, 0, 0), new Point(100, 0, 0), new Point(50, 50, 100))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));
		Geometry side2 = new Triangle(new Point(100, 0, 0), new Point(50, 100, 0), new Point(50, 50, 100))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));
		Geometry side3 = new Triangle(new Point(50, 100, 0), new Point(0, 0, 0), new Point(50, 50, 100))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));

		Geometry base = new Plane(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0))
				.setEmission(new Color(0, 127, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60).setKR(0.4));

		Geometry sphere = new Sphere(25, new Point(80, 200, 100))
				.setEmission(new Color(127, 0, 200)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));

		Scene scene = new Scene("Amazing image")
				.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.3)));

		scene.geometries.add(side1, side2, side3, base, sphere);

		// add light-blue sphere around all the scene
		scene.geometries.add(new Sphere(1000, new Point(0, 0, 0))
				.setEmission(new Color(0, 0, 127)).setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setKR(0.5)));

		scene.lights.add(new SpotLight(new Color(500, 300, 0), new Point(50, 50, 150), new Vector(0, 0, -1)).setKl(0.0001).setKq(0.000005));

		Camera cam = Camera.getBuilder()
				.setRayTracer(new SimpleRayTracer(scene))
				.setLocation(new Point(-700, -700, 30))
				.setDirection(new Vector(1, 1, 0), new Vector(0, 0, 1))
				.setVpSize(200, 200).setVpDistance(1000)
				.setImageWriter(new ImageWriter("multiTest2", 1000, 1000))
				.build();

		cam.renderImage().writeToImage();
	}


	@Test
	void antiAlisingTest() {
		// Build a pyramid
		Geometry side1 = new Triangle(new Point(0, 0, 0), new Point(100, 0, 0), new Point(50, 50, 100))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));
		Geometry side2 = new Triangle(new Point(100, 0, 0), new Point(50, 100, 0), new Point(50, 50, 100))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));
		Geometry side3 = new Triangle(new Point(50, 100, 0), new Point(0, 0, 0), new Point(50, 50, 100))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));

		Geometry base = new Plane(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0))
				.setEmission(new Color(0, 127, 0)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60).setKR(0.4));

		Geometry sphere = new Sphere(25, new Point(80, 200, 100))
				.setEmission(new Color(127, 0, 200)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(60));

		Scene scene = new Scene("Amazing image - Anti Alising")
				.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.3))); // Increase ambient light

		scene.geometries.add(side1, side2, side3, base, sphere);

		// add light-blue sphere around all the scene
		scene.geometries.add(new Sphere(1000, new Point(0, 0, 0))
				.setEmission(new Color(0, 0, 127)).setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setKR(0.5)));

		// Adjust light position and direction
		scene.lights.add(new SpotLight(new Color(500, 300, 0), new Point(50, 50, 150), new Vector(0, 0, -1))
				.setKl(0.0001).setKq(0.000005));

		Camera cam = Camera.getBuilder()
				.setRayTracer(new SimpleRayTracer(scene))
				.setLocation(new Point(-700, -700, 30)) // Adjust camera position
				.setDirection(new Vector(1, 1, 0), new Vector(0, 0, 1))
				.setVpSize(200, 200).setVpDistance(1000)
				.setImageWriter(new ImageWriter("antiAllisingTest", 1000, 1000))
				.build();

		cam.renderImageAntiAlising(17,true).writeToImage();
	}

}
