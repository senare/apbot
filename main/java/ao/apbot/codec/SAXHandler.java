package ao.apbot.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

	private static Logger log = LoggerFactory.getLogger(SAXHandler.class);

	Safepay safepay = null;
	String content = null;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		switch (qName) {
		case "do":
			attributes.getValue("function");
			break;
		case "safepay":
			String id = attributes.getValue("id");
			String location = attributes.getValue("location");
			if (id != null && location != null) {
				safepay = Safepay.createResponse(id, location);
			} else {
				safepay = Safepay.createRequest();
			}
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "safepay":
			throw new EndParserException("We parsed the whole document now comes some ugly extra bits so we bailing ...");
			// break;
		case "status":
			((Request) safepay).status = content;
			break;
		case "info":
			((Request) safepay).info = content;
			break;
		case "data":
			log.debug("Data is {} ", content);
			((Request) safepay).data = content;
			break;
		case "do":
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		content = String.copyValueOf(ch, start, length).trim();
	}

	public class EndParserException extends SAXException {
		private static final long serialVersionUID = 1L;

		public EndParserException(String string) {
			super(string);
		}
	}
}
