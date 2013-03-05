/*
 * Copyright 2012-2013 Denis Neuling 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.apitrary.orm.codec.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.apitrary.orm.codec.test.support.ResourceBasedTest;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
@Ignore
/*
 * FIXME
 */
public class ImageGIFCodecTest extends ResourceBasedTest {

	private final String FORMAT = "git";
	private final String file = "git.git";

	private ImagePNGCodec codec;
	
	private InputStream inputStream;
	private Image image;

	@Before
	public void setUp() throws IOException {
		codec = new ImagePNGCodec();
		
		inputStream = this.resolveResourceInputStream(file);
		image = ImageIO.read(inputStream);
	}

	@Test
	public void test_codec() throws IOException{
		String encoded = codec.encode(image);
		Image decoded = codec.decode(encoded);
		
		Assert.assertTrue(Arrays.equals(asBuffer(image), asBuffer(decoded)));
	}
	
	private byte[] asBuffer(Image image) throws IOException{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    ImageIO.write((BufferedImage)image, FORMAT, byteArrayOutputStream);
	    byte[] imageData = byteArrayOutputStream.toByteArray();
	    return imageData;
	}

	@After
	public void tearDown() throws IOException {
		inputStream.close();
	}
}