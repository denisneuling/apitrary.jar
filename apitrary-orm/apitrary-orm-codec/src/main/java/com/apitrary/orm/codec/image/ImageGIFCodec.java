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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.apitrary.orm.core.codec.Codec;

/**
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class ImageGIFCodec extends Codec<Image>{

	private static final String FORMAT = "gif";
	
	/** {@inheritDoc} */
	@Override
	public Image decode(String value) {
		byte[] buffer = Base64.decodeBase64(value);
		InputStream in = new ByteArrayInputStream(buffer);
		BufferedImage bImageFromConvert;
		try {
			bImageFromConvert = ImageIO.read(in);
			return bImageFromConvert;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String encode(Image image) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try {
			ImageIO.write((BufferedImage)image, FORMAT, byteArrayOutputStream);
			byte[] imageData = byteArrayOutputStream.toByteArray();
			return Base64.encodeBase64String(imageData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
