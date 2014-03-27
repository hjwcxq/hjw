
package com.orientmedia.app.cfddj.tool;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Vector;


/***
 * Decodes xmls' content fetched from remote server
 */
public class XmlParser {
    private static final String TAG = "XmlParser";
    /***
	 * filter the xml file stream content, remove anything (eg: BOM ) before the first char "<"
	 * @see 
	 * @param xml
	 * @return the xml stream without BOM
	 * @throws IOException
	 */
    private static PushbackInputStream getWrappedXmlInputStreamWithouBOM(InputStream xml)
            throws IOException {
        PushbackInputStream pushInput = new PushbackInputStream(xml);
        // tag for the first node element
        char startTag = '<';
        int startByte = (int) startTag;
        int singleByte = 0;

        Vector<Byte> preBytes = new Vector<Byte>();
        while ((singleByte = xml.read()) != -1) {
            preBytes.add((byte) singleByte);
            if (singleByte == startByte) {
                break;
            }
        }
        int allReadBytesCnt = preBytes.size();
        if (allReadBytesCnt > 0) {
            pushInput.unread(startByte);
        }
        return pushInput;
    }
   
    /**
     * Closes the specified stream.
     * 
     * @param stream The stream to close.
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }
    

    /***
     * 读取 首页列表
     * 
     * @param fileName
     */

}
