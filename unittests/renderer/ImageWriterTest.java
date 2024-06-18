package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest
{
	//view plane 10x16 resulotion 800x500
	ImageWriter iw = new ImageWriter("test", 800, 500);


	@Test
	void testWriteToImage()
	{
		iw.writeToImage();
	}

	@Test
	void testWritePixel()
	{

		for(int i = 0; i < iw.getNx(); i++)
			for(int j = 0; j < iw.getNy(); j++)
				if(i % 50 == 0 || j % 50 == 0)
					iw.writePixel(i, j, new Color(255, 255, 255));
				else
					iw.writePixel(i, j, new Color(0, 0, 0));
		iw.writeToImage();
	}
}