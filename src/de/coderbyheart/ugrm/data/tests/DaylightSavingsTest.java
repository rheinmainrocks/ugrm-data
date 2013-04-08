package de.coderbyheart.ugrm.data.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * @author Markus Tacker <m@coderbyheart.de>
 */
public class DaylightSavingsTest {
	/**
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testDateHasCorrectTimeZone()
			throws ParserConfigurationException, SAXException, IOException {
		int filesChecked = 0;
		// String ugdatadir = System.getProperty("ugdatadir");
		String ugdatadir = "./usergroup";
		TimeZone tz = TimeZone.getTimeZone("Europe/Berlin");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		df.setTimeZone(tz);
		for (File xmlFile : usergroupData(ugdatadir)) {
			Document ug = parseDocument(xmlFile);

			NodeList times = ug.getElementsByTagName("time");
			for (int i = 0; i < times.getLength(); i++) {
				Node time = times.item(i);
				String meetingTime = time.getTextContent();
				Calendar c = javax.xml.bind.DatatypeConverter
						.parseDateTime(meetingTime);
				String expectedTime = df.format(c.getTime());
				String msg = "The meeting time " + meetingTime + " in "
						+ xmlFile.getName() + " must be ";
				if (tz.inDaylightTime(c.getTime())) {
					// Datum liegt in Sommerzeit -> Zeitzone ist GMT+2
					expectedTime = expectedTime + "+02:00";
					msg = msg + "GMT+02.";

				} else {
					// Datum liegt in Winterzeit -> Zeitzone ist GMT+1
					expectedTime = expectedTime + "+01:00";
					msg = msg + "GMT+01.";
				}
				assertEquals(msg, expectedTime, meetingTime);
			}
			filesChecked++;
		}
		assertTrue(filesChecked > 0);
	}

	List<File> usergroupData(String directory) {
		List<File> xmlFiles = new ArrayList<File>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".xml"))) {
				xmlFiles.add(file);
			}
		}
		return xmlFiles;
	}

	/**
	 * Parst ein XML-File
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @return Document
	 * @see <a
	 *      href="http://crumpling-rumblings.blogspot.com/2008/05/java-how-to-make-getelementbyid-work.html">Crumpling
	 *      Rumblings: Java: How to make getElementById() work using xml
	 *      schema</a>
	 */
	protected Document parseDocument(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory
				.newInstance();
		domBuilderFactory.setIgnoringElementContentWhitespace(true);
		domBuilderFactory.setIgnoringComments(true);
		domBuilderFactory.setNamespaceAware(true);
		domBuilderFactory.setValidating(true);
		domBuilderFactory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
		domBuilderFactory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaSource",
				getXmlSchema(xmlFile));

		DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
		Document xmlDom = domBuilder.parse(xmlFile);
		removeWhitespaceNodes(xmlDom.getDocumentElement());
		return xmlDom;
	}

	protected File getXmlSchema(File xmlFile) {
		return new File(xmlFile.getParent(), "../xsd/usergroup.xsd");
	}

	/**
	 * Entfernt whitespace aus dem Document
	 * 
	 * @see <a href="http://forums.java.net/node/667186?#comment-684626">Java 6
	 *      parsers not ignoring whitespace | Java.net</a>
	 * @param e
	 */
	private static void removeWhitespaceNodes(Element e) {
		NodeList children = e.getChildNodes();
		for (int i = children.getLength() - 1; i >= 0; i--) {
			Node child = children.item(i);
			if (child instanceof Text
					&& ((Text) child).getData().trim().length() == 0) {
				e.removeChild(child);
			} else if (child instanceof Element) {
				removeWhitespaceNodes((Element) child);
			}
		}
	}

}